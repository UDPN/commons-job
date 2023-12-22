# commons-job
## Introduce
### This tool is used for executor projects. Using this tool, scheduled tasks in the executor can be automatically registered in the dispatch center. Before using this tool, please confirm that the xxljob service has been integrated in the executor project.
### Before using this tool, please confirm that the xxljob service has been integrated in the executor project.
## Instructions
### 1.Add the commons-job reference to your project's pom.xml file. The example is as follows:
~~~
<dependency>
    <groupId>io.udpn</groupId>
    <artifactId>commons-job</artifactId>
    <version>0.0.1-RELEASE</version>
</dependency>
~~~
### 2.Add the required configuration in your project application-common.properties file, the example is as follows:
~~~
xxl.job.admin.addresses=http://127.0.0.1:8080/xxl-job-admin
xxl.job.executor.appname=xxxx
xxl.job.accessToken=xxxxxxxxxx
xxl.job.scan.package.url=xxxx.xxxx.xxx.xx
xxl.job.executor.update=false
~~~
xxl.job.admin.addresses (address of the dispatch center)<br/>
xxl.job.executor.appname (The name of the executor)<br/>
xxl.job.accessToken (Token for dispatch center verification)<br/>
xxl.job.scan.package.url (The package path that needs to be scanned. Your scheduled tasks need to be placed under this package to automatically register with the dispatch center.)<br/>
xxl.job.executor.update (Whether to re-register scheduled tasks with the dispatch center each time the project is started, the default is true)<br/>

The above configuration is essential for using commons-job, some of them are the same as xxljob, the same configuration does not need to be repeated, you can use the same one.
### 3.Add a @JobParam() annotation to your scheduled task method, as shown below:
~~~
@XxlJob("demoTask")
@JobParam(handler = "demoTask", scheduleType = ScheduleType.FIX_RATE, scheduleConf = "3", author = "xxx", jobDesc = "xxx")
public ReturnT<String> demoTask1(String param){
  try {
    System.out.println("DEMO TASK:" + System.currentTimeMillis());
  }catch (Exception e){
    e.printStackTrace();
  }
  return ReturnT.SUCCESS;
}
~~~
@JobParam() The relevant parameters are described in it, and you can go to the @JobParam annotation to see the relevant description