package io.udpn.commonsjob.model.pps;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xxl.job.admin")
public class JobDiscoveryAdmin {

  private String addresses;

  public String getAddresses() {
    return addresses;
  }

  public void setAddresses(String addresses) {
    this.addresses = addresses;
  }


}
