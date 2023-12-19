package io.udpn.commonsjob.model.pps;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xxl.job")
public class JobDiscovery {

  private String accessToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
