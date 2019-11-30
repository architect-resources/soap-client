package org.jrichardsz.soapeasier.core;

import java.util.HashMap;
import org.apache.velocity.runtime.RuntimeServices;

public interface SoapPart {

  public String getXmlAsString() throws Exception;

  public String getXmlAsString(RuntimeServices runtimeServices, HashMap<String, Object> parameters)
      throws Exception;

}
