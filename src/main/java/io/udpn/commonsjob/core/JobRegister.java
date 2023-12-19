package io.udpn.commonsjob.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.udpn.commonsjob.core.annotation.JobParam;
import io.udpn.commonsjob.model.pps.JobDiscovery;
import io.udpn.commonsjob.model.pps.JobDiscoveryAdmin;
import io.udpn.commonsjob.model.pps.JobDiscoveryExecutor;
import io.udpn.commonsjob.model.pps.JobDiscoveryScanPackage;
import io.udpn.commonsjob.utils.OkHttpUtil;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;

public class JobRegister implements SmartInitializingSingleton {

  private static final Logger logger = LoggerFactory.getLogger(JobRegister.class);

  @Autowired
  private JobDiscovery jobDiscovery;
  @Autowired
  private JobDiscoveryAdmin jobDiscoveryAdmin;
  @Autowired
  private JobDiscoveryScanPackage jobDiscoveryScanPackage;
  @Autowired
  private JobDiscoveryExecutor jobDiscoveryExecutor;
  @Autowired
  private OkHttpUtil okHttpUtil;

  @Override
  public void afterSingletonsInstantiated() {

    if (checkConfigure()) {

      Reflections reflections = new Reflections(new ConfigurationBuilder()
          .forPackages(jobDiscoveryScanPackage.getUrl())
          .addScanners(new MethodAnnotationsScanner()));

      Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(JobParam.class);

      int groupId = sigUpGroup();

      if (groupId > 0) {
        sigUpJob(groupId, methodsAnnotatedWith);
      }

    }

  }

  private void sigUpJob(Integer groupId, Set<Method> methods) {

    if (methods != null && methods.size() > 0) {

      JSONArray jobs = new JSONArray();

      for (Method method : methods) {

        JobParam annotation = method.getAnnotation(JobParam.class);

        JSONObject job = new JSONObject();
        job.put("jobGroup", groupId);
        job.put("scheduleConf", annotation.cron());
        job.put("executorHandler", annotation.handler());
        job.put("scheduleType", "CRON");
        job.put("author", "UDPN");
        job.put("jobDesc", "UDPN Task");
        job.put("glueType", "BEAN");
        job.put("executorRouteStrategy", "ROUND");
        job.put("misfireStrategy", "DO_NOTHING");
        job.put("executorBlockStrategy", "SERIAL_EXECUTION");
        job.put("executorTimeout", 0);
        job.put("executorFailRetryCount", 0);

        jobs.add(job);
      }

      Map<String, String> headers = new HashMap<String, String>();
      headers.put("XXL-JOB-ACCESS-TOKEN", jobDiscovery.getAccessToken());

      String addJobStr = okHttpUtil.doPost(
          jobDiscoveryAdmin.getAddresses() + "/api/addJob",
          jobs.toJSONString(),
          headers);

      logger.info("UDPN job >>>>> add job return value:{}", JSONObject.toJSONString(addJobStr));

    } else {
      logger.error("UDPN job >>>>> Unable to find method marked by @JobParam()");
    }

  }

  private Integer sigUpGroup() {

    JSONObject param = new JSONObject();
    param.put("appname", jobDiscoveryExecutor.getAppname());
    param.put("title", jobDiscoveryExecutor.getAppname());
    param.put("addressType", 0);

    Map<String, String> headers = new HashMap<String, String>();
    headers.put("XXL-JOB-ACCESS-TOKEN", jobDiscovery.getAccessToken());

    String jobGroupJsonStr = okHttpUtil.doPost(
        jobDiscoveryAdmin.getAddresses() + "/api/jobGroupSave",
        param.toJSONString(),
        headers);

    logger.info("UDPN job >>>>> add group return value:{}", jobGroupJsonStr);

    JSONObject jobGroupJson = JSONObject.parseObject(jobGroupJsonStr);

    if (jobGroupJson.getInteger("code").equals(200)) {
      return jobGroupJson.getJSONObject("content").getInteger("groupId");
    }

    return 0;
  }

  private boolean checkConfigure() {

    boolean check = true;

    if (jobDiscoveryAdmin == null
        || jobDiscoveryAdmin.getAddresses() == null
        || jobDiscoveryAdmin.getAddresses().equals("")) {

      check = false;
      logger.error(
          "UDPN job >>>>> Unable to find relevant configuration please check xxl. job.admin.addresses");
    }
    if (jobDiscoveryScanPackage == null
        || jobDiscoveryScanPackage.getUrl() == null
        || jobDiscoveryScanPackage.getUrl().equals("")) {

      check = false;
      logger.error(
          "UDPN job >>>>> Unable to find relevant configuration please check xxl.job.scan.package.url");
    }
    if (jobDiscovery == null
        || jobDiscovery.getAccessToken() == null
        || jobDiscovery.getAccessToken().equals("")) {

      check = false;
      logger.error(
          "UDPN job >>>>> Unable to find relevant configuration please check xxl.job.accessToken");
    }
    if (jobDiscoveryExecutor == null
        || jobDiscoveryExecutor.getAppname() == null
        || jobDiscoveryExecutor.getAppname().equals("")) {

      check = false;
      logger.error(
          "UDPN job >>>>> Unable to find relevant configuration please check xxl.job.executor.appname");
    }

    return check;
  }


}
