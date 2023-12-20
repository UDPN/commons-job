package io.udpn.commonsjob.enums;

public enum InterfaceEnum {
  SIG_UP_GROUP("/api/jobGroupSave"),
  SIG_UP_JOB("/api/addJob");

  String interfaceUrl;

  InterfaceEnum(String interfaceUrl) {
    this.interfaceUrl = interfaceUrl;
  }

  public String getInterfaceUrl() {
    return interfaceUrl;
  }

  public void setInterfaceUrl(String interfaceUrl) {
    this.interfaceUrl = interfaceUrl;
  }
}
