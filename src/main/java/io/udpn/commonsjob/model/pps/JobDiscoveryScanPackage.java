package io.udpn.commonsjob.model.pps;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xxl.job.scan.package")
public class JobDiscoveryScanPackage {

  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
