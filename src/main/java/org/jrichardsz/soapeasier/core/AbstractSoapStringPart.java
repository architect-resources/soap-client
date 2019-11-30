package org.jrichardsz.soapeasier.core;

import org.apache.velocity.Template;

public abstract class AbstractSoapStringPart implements SoapPart {

  public String xmlLocation;
  public Template template = null;

  public String getXmlLocation() {
    return xmlLocation;
  }

  public void setXmlLocation(String xmlLocation) {
    this.xmlLocation = xmlLocation;
  }
}
