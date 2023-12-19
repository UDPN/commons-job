package io.udpn.commonsjob.model.pps;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xxl.job.executor")
public class JobDiscoveryExecutor {

  private String appname;

  public String getAppname() {
    return appname;
  }

  public void setAppname(String appname) {
    this.appname = appname;
  }
}
