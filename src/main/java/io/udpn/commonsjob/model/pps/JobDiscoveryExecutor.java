package io.udpn.commonsjob.model.pps;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xxl.job.executor")
public class JobDiscoveryExecutor {

  private String appname;

  private Boolean update;

  public String getAppname() {
    return appname;
  }

  public void setAppname(String appname) {
    this.appname = appname;
  }

  public Boolean getUpdate() {
    return update;
  }

  public void setUpdate(Boolean update) {
    this.update = update;
  }
}
