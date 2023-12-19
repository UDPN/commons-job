package io.udpn.commonsjob.core.conf;

import io.udpn.commonsjob.core.JobRegister;
import io.udpn.commonsjob.model.pps.JobDiscovery;
import io.udpn.commonsjob.model.pps.JobDiscoveryAdmin;
import io.udpn.commonsjob.model.pps.JobDiscoveryExecutor;
import io.udpn.commonsjob.model.pps.JobDiscoveryScanPackage;
import io.udpn.commonsjob.utils.OkHttpUtil;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

  // --------- configuration parameter start ----------
  @Bean
  public JobDiscovery jobDiscovery(){
    return new JobDiscovery();
  }
  @Bean
  public JobDiscoveryAdmin jobDiscoveryAdmin(){
    return new JobDiscoveryAdmin();
  }
  @Bean
  public JobDiscoveryExecutor jobDiscoveryExecutor(){
    return new JobDiscoveryExecutor();
  }
  @Bean
  public JobDiscoveryScanPackage jobDiscoveryScanPackage(){
    return new JobDiscoveryScanPackage();
  }
  // --------- configuration parameter start ----------


  // --------- okhttp3 start ----------
  @Bean
  public X509TrustManager x509TrustManager(){
    return new X509TrustManager() {
      @Override
      public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
      }
      @Override
      public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
      }
      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
      }
    };
  }
  @Bean
  public SSLSocketFactory sslSocketFactory() {
    try {
      // Trust any link
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
      return sslContext.getSocketFactory();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    }
    return null;
  }
  @Bean
  public ConnectionPool pool() {
    return new ConnectionPool(200, 5, TimeUnit.MINUTES);
  }
  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory(), x509TrustManager())
        .retryOnConnectionFailure(false)
        .connectionPool(pool())
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(10L, TimeUnit.SECONDS)
        .build();
  }
  @Bean
  public OkHttpUtil okHttpUtil(){
    return new OkHttpUtil();
  }
  // --------- okhttp3 end ----------

  @Bean
  public JobRegister JobRegister(){
    return new JobRegister();
  }


}
