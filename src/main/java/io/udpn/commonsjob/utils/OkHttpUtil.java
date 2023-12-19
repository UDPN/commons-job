package io.udpn.commonsjob.utils;

import java.util.Iterator;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class OkHttpUtil {

  private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  @Autowired
  private OkHttpClient okHttpClient;

  /**
   * post
   */
  public String doPost(String url, String json,Map<String, String> headers) {
    RequestBody requestBody = RequestBody.create(json, JSON);
    Request.Builder builder = new Request.Builder();
    if (headers != null && !headers.isEmpty()) {
      for (String header:headers.keySet()){
        builder.addHeader(header, headers.get(header));
      }
    }
    Request request = builder.url(url).post(requestBody).build();

    Response response = null;

    try {
      response = okHttpClient.newCall(request).execute();
      if (response.isSuccessful()) {
        return response.body().string();
      }
    } catch (Exception e) {
      logger.error("udpn job http >>>>> {}",e);
    } finally {
      if (response != null) {
        response.close();
      }
    }
    return "";
  }

}
