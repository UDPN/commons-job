package io.udpn.commonsjob.core.conf;

import io.udpn.commonsjob.utils.JobDiscoveryProperties;
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
