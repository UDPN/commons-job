package com.reddata.job.core.conf;

import com.reddata.job.utils.JobDiscoveryProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class JobConfig {

  @Bean
  public JobDiscoveryProperties jobDiscoveryProperties(){
    return new JobDiscoveryProperties();
  }

  @Bean
  public JobRegister JobRegister(){
    return new JobRegister();
  }

}
