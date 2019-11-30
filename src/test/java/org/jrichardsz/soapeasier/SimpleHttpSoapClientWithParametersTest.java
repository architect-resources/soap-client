package org.jrichardsz.soapeasier;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import org.jrichardsz.soapeasier.core.impl.SoapClasspathMessage;
import org.junit.Test;

public class SimpleHttpSoapClientWithParametersTest {

  // Expected response from :
  // http://www.crcind.com/csp/samples/SOAP.Demo.cls
  // Operation:
  // http://www.crcind.com/csp/samples/%25SOAP.WebServiceInvoke.cls?CLS=SOAP.Demo&OP=AddInteger

  @Test
  public void testHttp() throws Exception {
    SimpleHttpSoapClient client = new SimpleHttpSoapClient();

    client.setServiceUrl("http://www.crcind.com/csp/samples/SOAP.Demo.cls");
    client.setContentType("text/xml; charset=utf-8");
    client.setSoapAction("http://tempuri.org/SOAP.Demo.AddInteger");

    client.configure();

    SoapClasspathMessage message = new SoapClasspathMessage();
    message.setXmlLocation("/soap-message-sample-with-parameters.xml");
    
    HashMap<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("addend1", 5);
    parameters.put("addend2", 140);   
    
    String response = client.performOperation(message, parameters);
    assertNotNull(response);
    assertTrue(response.contains("<AddIntegerResult>145</AddIntegerResult>"));
  }
}
