package org.jrichardsz.soapeasier.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.velocity.VelocityContext;

public class SoapEasierCommon {

  public static String readFromInputStream(InputStream inputStream) throws IOException {
    StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        resultStringBuilder.append(line).append("\n");
      }
    }
    return resultStringBuilder.toString();
  }

  public static void mergeParameters(VelocityContext vc, HashMap<String, Object> parameters)
      throws IOException {
    for (Entry<String, Object> entry : parameters.entrySet()) {
      vc.put(entry.getKey(), entry.getValue());
    }
  }

}
