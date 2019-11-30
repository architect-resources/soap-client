package org.jrichardsz.soapeasier.core.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.jrichardsz.soapeasier.SimpleHttpSoapClient;
import org.jrichardsz.soapeasier.common.SoapEasierCommon;
import org.jrichardsz.soapeasier.core.AbstractSoapStringPart;

public class SoapClasspathPart extends AbstractSoapStringPart {

  @Override
  public String getXmlAsString() throws Exception {
    InputStream inputStream = SimpleHttpSoapClient.class.getResourceAsStream(xmlLocation);
    try {
      return SoapEasierCommon.readFromInputStream(inputStream);
    } catch (IOException e) {
      throw new Exception(
          "An error ocurred when classpath file was being converted to string:" + xmlLocation, e);
    }
  }

  @Override
  public String getXmlAsString(RuntimeServices runtimeServices, HashMap<String, Object> parameters)
      throws Exception {

    if (template == null) {
      StringReader stringTemplate = new StringReader(getXmlAsString());
      SimpleNode sn = runtimeServices.parse(stringTemplate, "dynamicTemplate");

      template = new Template();
      template.setRuntimeServices(runtimeServices);
      template.setData(sn);
      template.initDocument();

    }

    try {
      VelocityContext vc = new VelocityContext();
      SoapEasierCommon.mergeParameters(vc, parameters);

      StringWriter sw = new StringWriter();
      template.merge(vc, sw);

      return sw.toString();
    } catch (Exception e) {
      throw new Exception(
          "An error ocurred when classpath as template file was being converted to string:"
              + xmlLocation,
          e);
    }
  }

}
