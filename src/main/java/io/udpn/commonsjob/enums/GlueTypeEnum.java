package io.udpn.commonsjob.enums;

public enum GlueTypeEnum {

  BEAN("BEAN"),
  GLUE_GROOVY("GLUE_GROOVY"),
  GLUE_SHELL("GLUE_SHELL"),
  GLUE_PYTHON("GLUE_PYTHON"),
  GLUE_PHP("GLUE_PHP"),
  GLUE_NODEJS("GLUE_NODEJS"),
  GLUE_POWERSHELL("GLUE_POWERSHELL");

  String glueType;

  GlueTypeEnum(String glueType) {
    this.glueType = glueType;
  }

  public String getGlueType() {
    return glueType;
  }

  public void setGlueType(String glueType) {
    this.glueType = glueType;
  }
}
