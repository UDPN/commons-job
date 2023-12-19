package io.udpn.commonssjob.core.conf;

import io.udpn.commonssjob.utils.JobDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
