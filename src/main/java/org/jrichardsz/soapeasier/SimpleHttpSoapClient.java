package org.jrichardsz.soapeasier;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.jrichardsz.soapeasier.common.CustomHostnameVerifier;
import org.jrichardsz.soapeasier.core.impl.SoapClasspathBody;
import org.jrichardsz.soapeasier.core.impl.SoapClasspathHeader;
import org.jrichardsz.soapeasier.core.impl.SoapClasspathMessage;

public class SimpleHttpSoapClient {

  private String basicAuthUser;
  private String basicAuthPassword;
  private String serviceUrl;
  private String contentType;
  private String soapAction;
  private boolean skipCertificateValidation = false;
  private String allowedStringHostnames;
  private String authHeaderValue;
  private RuntimeServices templateRuntimeService = null;

  public void configure() throws Exception {
    if (skipCertificateValidation && allowedStringHostnames != null
        && allowedStringHostnames.length() > 0) {
      CustomHostnameVerifier hostnameVerifier = new CustomHostnameVerifier();
      hostnameVerifier.configure(allowedStringHostnames);
      HttpsURLConnection
          .setDefaultSSLSocketFactory(hostnameVerifier.getSslContext().getSocketFactory());
      HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier.getVerifier());
    }

    if (basicAuthUser != null && basicAuthUser.length() > 0) {
      if (basicAuthPassword != null && basicAuthPassword.length() > 0) {
        String auth = basicAuthUser + ":" + basicAuthPassword;
        String encodedAuth =
            Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        authHeaderValue = "Basic " + encodedAuth;
      }
    }
  }

  public String performOperation(SoapClasspathHeader header, SoapClasspathBody body)
      throws Exception {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  public String performOperation(SoapClasspathMessage message) throws Exception {
    return performOperation(message, null);
  }

  public String performOperation(SoapClasspathMessage message, HashMap<String, Object> parameters)
      throws Exception {
    try {
      URL oURL = new URL(serviceUrl);
      HttpURLConnection con = (HttpURLConnection) oURL.openConnection();

      if (authHeaderValue != null) {
        con.setRequestProperty("Authorization", authHeaderValue);
      }

      con.setRequestMethod("POST");
      con.setRequestProperty("Content-type", contentType);
      con.setRequestProperty("SOAPAction", soapAction);
      con.setDoOutput(true);

      String reqXml = null;
      if (parameters != null && !parameters.isEmpty()) {

        if (templateRuntimeService == null) {
          templateRuntimeService = RuntimeSingleton.getRuntimeServices();
        }
        reqXml = message.getXmlAsString(templateRuntimeService, parameters);
      } else {
        reqXml = message.getXmlAsString();
      }

      OutputStream reqStream = con.getOutputStream();
      reqStream.write(reqXml.getBytes());

      InputStream resStream = con.getInputStream();
      String result = IOUtils.toString(resStream, StandardCharsets.UTF_8);

      reqStream.close();
      resStream.close();
      return result;
    } catch (Exception e) {
      throw new Exception("An error ocurred when soap operation was being consumed", e);
    }
  }

  public String getServiceUrl() {
    return serviceUrl;
  }

  public void setServiceUrl(String serviceUrl) {
    this.serviceUrl = serviceUrl;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getSoapAction() {
    return soapAction;
  }

  public void setSoapAction(String soapAction) {
    this.soapAction = soapAction;
  }

  public boolean isSkipCertificateValidation() {
    return skipCertificateValidation;
  }

  public void setSkipCertificateValidation(boolean skipCertificateValidation) {
    this.skipCertificateValidation = skipCertificateValidation;
  }

  public String getAllowedStringHostnames() {
    return allowedStringHostnames;
  }

  public void setAllowedStringHostnames(String allowedStringHostnames) {
    this.allowedStringHostnames = allowedStringHostnames;
  }

  public String getBasicAuthUser() {
    return basicAuthUser;
  }

  public void setBasicAuthUser(String basicAuthUser) {
    this.basicAuthUser = basicAuthUser;
  }

  public String getBasicAuthPassword() {
    return basicAuthPassword;
  }

  public void setBasicAuthPassword(String basicAuthPassword) {
    this.basicAuthPassword = basicAuthPassword;
  }
}
