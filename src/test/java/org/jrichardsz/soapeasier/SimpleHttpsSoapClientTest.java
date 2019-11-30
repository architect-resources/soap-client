package org.jrichardsz.soapeasier;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.jrichardsz.soapeasier.core.impl.SoapClasspathMessage;
import org.junit.Test;

public class SimpleHttpsSoapClientTest {

  // Expected response from :
  // http://www.crcind.com/csp/samples/SOAP.Demo.cls
  // Operation:
  // http://www.crcind.com/csp/samples/%25SOAP.WebServiceInvoke.cls?CLS=SOAP.Demo&OP=AddInteger

  @Test
  public void testHttps() throws Exception {
    SimpleHttpSoapClient client = new SimpleHttpSoapClient();

    client.setServiceUrl("http://www.crcind.com/csp/samples/SOAP.Demo.cls");
    client.setContentType("text/xml; charset=utf-8");
    client.setSoapAction("http://tempuri.org/SOAP.Demo.AddInteger");

    client.setSkipCertificateValidation(true);
    client.setAllowedStringHostnames("crcind.com");

    client.configure();

    SoapClasspathMessage message = new SoapClasspathMessage();
    message.setXmlLocation("/soap-message-sample.xml");
    String response = client.performOperation(message);
    assertNotNull(response);
    assertTrue(response.contains("<AddIntegerResult>11</AddIntegerResult>"));
  }
}
