package io.udpn.commonsjob.utils;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xxl.job")
public class JobDiscoveryProperties {

  private String addresses;

  public String getAddresses() {
    return addresses;
  }

  public void setAddresses(String addresses) {
    this.addresses = addresses;
  }

  public static class scanpackage{
    private String url;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }

}
