# Soap Easier

<p align="center">
  <img src="https://raw.githubusercontent.com/jrichardsz/static_resources/master/soap-easier/soap-easier.png" />
</p>


- Are you working with soap and legacy and awful services?
- wsdl does not meet the standard?
- security is pain in your #$%&?

If the above is your case, forget the complicated libraries and use **Soap Easier**.

Soap Easier is a minimal java library to work with soap services. Is based on simple string templates to create the soap message and use a very low level to establish the http connection.

# Steps

- Try to consume your service with [SoapUI](https://www.soapui.org). If SoapUI can not, very few people in this world can do it.
- Add to your pom

```xml
<dependency>
  <groupId>org.jrichardsz.soapeasier</groupId>
  <artifactId>soap-easier</artifactId>
  <version>1.0.0</version>
</dependency>
```

- create a file in your src/main/resources with a xml string. This string must be the soap request of a success execution with soapui. Example : **add-integer-request.xml**

```xml
<soapenv:Envelope ...>
   <soapenv:Header/>
   <soapenv:Body>
      <tem:AddInteger>
         <tem:Arg1>5</tem:Arg1>
         <tem:Arg2>6</tem:Arg2>
      </tem:AddInteger>
   </soapenv:Body>
</soapenv:Envelope>
```
- Just consume your soap operation:

```java
SimpleHttpSoapClient client = new SimpleHttpSoapClient();

client.setServiceUrl("http://www.crcind.com/csp/samples/SOAP.Demo.cls");
client.setContentType("text/xml; charset=utf-8");
client.setSoapAction("http://tempuri.org/SOAP.Demo.AddInteger");

client.configure();

SoapClasspathMessage message = new SoapClasspathMessage();
message.setXmlLocation("/add-integer-request.xml");
String response = client.performOperation(message);
```

That's All!


# Parameterized requests

In the background [The Apache Velocity Project](https://velocity.apache.org/) is used. Take a look this [test](https://github.com/software-architect-tools/soap-easier/blob/master/src/test/java/org/jrichardsz/soapeasier/SimpleHttpSoapClientWithParametersTest.java) !

# Secure requests

Currently, only basic authentication is supported. Take a look this [test](https://github.com/software-architect-tools/soap-easier/blob/master/src/test/java/org/jrichardsz/soapeasier/SimpleHttpsSoapClientWithBasicAuthTest.java) !

# Roadmap

- Add more security strategies
- Split message in header and body
- Add Pojos as an alternative to HashMap parameters
