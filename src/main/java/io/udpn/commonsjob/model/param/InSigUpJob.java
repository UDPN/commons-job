package io.udpn.commonsjob.model.param;

public class InSigUpJob {

  private Integer jobGroup;
  private String scheduleConf;
  private String executorHandler;
  private String scheduleType;
  private String author;
  private String jobDesc;
  private String glueType;
  private String executorRouteStrategy;
  private String misfireStrategy;
  private String executorBlockStrategy;
  private Integer executorTimeout;
  private Integer executorFailRetryCount;
  private Boolean timelyUpdate;
  private String groupName;
  private String alarmEmail;
  private String executorParam;
  private String childJobId;

  public Integer getJobGroup() {
    return jobGroup;
  }

  public void setJobGroup(Integer jobGroup) {
    this.jobGroup = jobGroup;
  }

  public String getScheduleConf() {
    return scheduleConf;
  }

  public void setScheduleConf(String scheduleConf) {
    this.scheduleConf = scheduleConf;
  }

  public String getExecutorHandler() {
    return executorHandler;
  }

  public void setExecutorHandler(String executorHandler) {
    this.executorHandler = executorHandler;
  }

  public String getScheduleType() {
    return scheduleType;
  }

  public void setScheduleType(String scheduleType) {
    this.scheduleType = scheduleType;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getJobDesc() {
    return jobDesc;
  }

  public void setJobDesc(String jobDesc) {
    this.jobDesc = jobDesc;
  }

  public String getGlueType() {
    return glueType;
  }

  public void setGlueType(String glueType) {
    this.glueType = glueType;
  }

  public String getExecutorRouteStrategy() {
    return executorRouteStrategy;
  }

  public void setExecutorRouteStrategy(String executorRouteStrategy) {
    this.executorRouteStrategy = executorRouteStrategy;
  }

  public String getMisfireStrategy() {
    return misfireStrategy;
  }

  public void setMisfireStrategy(String misfireStrategy) {
    this.misfireStrategy = misfireStrategy;
  }

  public String getExecutorBlockStrategy() {
    return executorBlockStrategy;
  }

  public void setExecutorBlockStrategy(String executorBlockStrategy) {
    this.executorBlockStrategy = executorBlockStrategy;
  }

  public Integer getExecutorTimeout() {
    return executorTimeout;
  }

  public void setExecutorTimeout(Integer executorTimeout) {
    this.executorTimeout = executorTimeout;
  }

  public Integer getExecutorFailRetryCount() {
    return executorFailRetryCount;
  }

  public void setExecutorFailRetryCount(Integer executorFailRetryCount) {
    this.executorFailRetryCount = executorFailRetryCount;
  }

  public Boolean getTimelyUpdate() {
    return timelyUpdate;
  }

  public void setTimelyUpdate(Boolean timelyUpdate) {
    this.timelyUpdate = timelyUpdate;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getAlarmEmail() {
    return alarmEmail;
  }

  public void setAlarmEmail(String alarmEmail) {
    this.alarmEmail = alarmEmail;
  }

  public String getExecutorParam() {
    return executorParam;
  }

  public void setExecutorParam(String executorParam) {
    this.executorParam = executorParam;
  }

  public String getChildJobId() {
    return childJobId;
  }

  public void setChildJobId(String childJobId) {
    this.childJobId = childJobId;
  }
}
