package org.jrichardsz.soapeasier.common;

import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class CustomHostnameVerifier {

  private SSLContext sslContext;
  private HostnameVerifier verifier;

  public void configure(String allowedStringHostnames) throws Exception {
    List<String> allowedHostnames = Arrays.asList(allowedStringHostnames.split(","));

    try {
      // Create a trust manager that does not validate certificate chains
      TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
      }};

      // Install the all-trusting trust manager
      sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

      // Create all-trusting host name verifier
      verifier = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
          return allowedHostnames.contains(hostname);
        }
      };
    } catch (Exception e) {
      throw new Exception("An error ocurred when a custom hostname verifier was being configured",
          e);
    }

  }

  public SSLContext getSslContext() {
    return sslContext;
  }

  public HostnameVerifier getVerifier() {
    return verifier;
  }
}
