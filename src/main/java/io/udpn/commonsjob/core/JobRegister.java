package io.udpn.commonsjob.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.udpn.commonsjob.core.annotation.JobParam;
import io.udpn.commonsjob.enums.GlueTypeEnum;
import io.udpn.commonsjob.enums.InterfaceEnum;
import io.udpn.commonsjob.model.param.InSigUpGroup;
import io.udpn.commonsjob.model.param.InSigUpJob;
import io.udpn.commonsjob.model.pps.JobDiscovery;
import io.udpn.commonsjob.model.pps.JobDiscoveryAdmin;
import io.udpn.commonsjob.model.pps.JobDiscoveryExecutor;
import io.udpn.commonsjob.model.pps.JobDiscoveryScanPackage;
import io.udpn.commonsjob.utils.OkHttpUtil;
import io.udpn.commonsjob.utils.ScheduleType;
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

      boolean timelyUpdate = true;

      if (jobDiscoveryExecutor != null || jobDiscoveryExecutor.getUpdate() != null) {
        timelyUpdate = jobDiscoveryExecutor.getUpdate();
      }

      JSONArray jobs = new JSONArray();

      for (Method method : methods) {

        JobParam annotation = method.getAnnotation(JobParam.class);

        InSigUpJob inSigUpJob = new InSigUpJob();
        inSigUpJob.setJobGroup(groupId);
        inSigUpJob.setScheduleConf(annotation.scheduleConf());
        inSigUpJob.setExecutorHandler(annotation.handler());
        inSigUpJob.setScheduleType(annotation.scheduleType());
        inSigUpJob.setAuthor(annotation.author());
        inSigUpJob.setJobDesc(annotation.jobDesc());
        inSigUpJob.setGlueType(GlueTypeEnum.BEAN.getGlueType());
        inSigUpJob.setExecutorRouteStrategy(annotation.routeStrategy());
        inSigUpJob.setMisfireStrategy(annotation.expirationStrategy());
        inSigUpJob.setExecutorBlockStrategy(annotation.blockingStrategy());
        inSigUpJob.setExecutorTimeout(annotation.timeoutSecond());
        inSigUpJob.setExecutorFailRetryCount(annotation.failRetryCount());
        inSigUpJob.setTimelyUpdate(timelyUpdate);
        inSigUpJob.setGroupName(jobDiscoveryExecutor.getAppname());
        inSigUpJob.setAlarmEmail(annotation.alarmEmail());
        inSigUpJob.setExecutorParam(annotation.executorParam());
        inSigUpJob.setChildJobId(annotation.childJobId());

        jobs.add(JSONObject.toJSON(inSigUpJob));

      }

      Map<String, String> headers = new HashMap<String, String>();
      headers.put("XXL-JOB-ACCESS-TOKEN", jobDiscovery.getAccessToken());

      String addJobStr = okHttpUtil.doPost(
          jobDiscoveryAdmin.getAddresses() + InterfaceEnum.SIG_UP_JOB.getInterfaceUrl(),
          jobs.toJSONString(),
          headers);

      logger.info("UDPN job >>>>> add job return value:{}", JSONObject.toJSONString(addJobStr));

    } else {
      logger.error("UDPN job >>>>> Unable to find method marked by @JobParam()");
    }

  }

  private Integer sigUpGroup() {

    InSigUpGroup inSigUpGroup = new InSigUpGroup();
    inSigUpGroup.setAppname(jobDiscoveryExecutor.getAppname());
    inSigUpGroup.setTitle(jobDiscoveryExecutor.getAppname());
    inSigUpGroup.setAddressType(0);

    Map<String, String> headers = new HashMap<String, String>();
    headers.put("XXL-JOB-ACCESS-TOKEN", jobDiscovery.getAccessToken());

    String jobGroupJsonStr = okHttpUtil.doPost(
        jobDiscoveryAdmin.getAddresses() + InterfaceEnum.SIG_UP_GROUP.getInterfaceUrl(),
        JSONObject.toJSONString(inSigUpGroup),
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
