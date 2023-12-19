package io.udpn.commonssjob.core.conf;

import io.udpn.commonssjob.core.annotation.JobParam;
import io.udpn.commonssjob.utils.JobDiscoveryProperties;
import java.lang.reflect.Method;
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
  private JobDiscoveryProperties jobDiscoveryProperties;

  @Override
  public void afterSingletonsInstantiated() {

    if (jobDiscoveryProperties.getAddresses() != null && !jobDiscoveryProperties.getAddresses().equals("")) {
      logger.info("===== 地址是：" + jobDiscoveryProperties.getAddresses());
    } else {
      logger.info("===== 地址是空的！");
    }

    Reflections reflections = new Reflections(new ConfigurationBuilder()
        .forPackages(jobDiscoveryProperties.getAddresses())
        .addScanners(new MethodAnnotationsScanner()));

    Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(JobParam.class);

    for (Method method : methodsAnnotatedWith) {

      JobParam annotation = method.getAnnotation(JobParam.class);

      logger.info("表达式：{}",annotation.cron());
      logger.info("Handler：{}",annotation.handler());

    }


  }



}