package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class BPWConfigWrapper
{
  public boolean supportBackwardCompatibility = true;
  private static BPWConfigWrapper a = null;
  public c _compatibleConfig;
  public static final String TRANSFER_HANDLER = "Transfer-Handler";
  public a _bpwConfig;
  public static boolean initialized = false;
  InputStream jdField_if = null;
  InputStream jdField_do = null;
  
  private BPWConfigWrapper()
    throws Exception
  {}
  
  public void setCompatibleBPWConfig(c paramc)
  {
    this._compatibleConfig = paramc;
  }
  
  public void setBPWConfig(a parama)
  {
    this._bpwConfig = parama;
  }
  
  public static synchronized BPWConfigWrapper getInstance()
    throws Exception
  {
    if (a == null) {
      a = new BPWConfigWrapper();
    }
    return a;
  }
  
  public static synchronized void invalidate()
    throws Exception
  {
    if (a != null)
    {
      a = null;
      initialized = false;
    }
  }
  
  public synchronized void initialize(String paramString, boolean paramBoolean)
    throws Exception
  {
    if (initialized) {
      return;
    }
    b localb = new b(paramString, paramBoolean);
    if (this.supportBackwardCompatibility)
    {
      if (a())
      {
        DebugLog.log("BPWConfigWrapper.initialise legacy files found ( LogLanguages.xml and LimitCheckApproval.xml)");
        setCompatibleBPWConfig(new d());
        setBPWConfig(localb);
      }
      else
      {
        DebugLog.log("BPWConfigWrapper.initialise legacy files not found (LogLanguages.xml and LimitCheckApproval.xml) using bpwconfig.xml");
        setCompatibleBPWConfig(localb);
        setBPWConfig(localb);
      }
    }
    else
    {
      setCompatibleBPWConfig(localb);
      setBPWConfig(localb);
    }
    if (localb != null) {
      initialized = true;
    }
  }
  
  private boolean a()
  {
    this.jdField_if = FFSUtil.getResourceAsStream(this, "LimitCheckApproval.xml");
    this.jdField_do = FFSUtil.getResourceAsStream(this, "LogLanguages.xml");
    return (this.jdField_if != null) && (this.jdField_do != null);
  }
  
  public ArrayList getLogLanguages()
    throws FFSException
  {
    return this._compatibleConfig.a();
  }
  
  public Properties getLimitCheckConfiguration()
    throws FFSException
  {
    return this._compatibleConfig.jdField_if();
  }
  
  public String getBackendHandlerClassName(String paramString)
    throws FFSException
  {
    return this._bpwConfig.jdField_if(paramString);
  }
  
  public Properties getBackendHandlerParameters(String paramString)
    throws FFSException
  {
    return this._bpwConfig.a(paramString);
  }
  
  public String getSWIFTMapperClassName(String paramString1, String paramString2)
    throws FFSException
  {
    return this._bpwConfig.a(paramString1, paramString2);
  }
  
  public boolean isSupportBackwardCompatibility()
  {
    return this.supportBackwardCompatibility;
  }
  
  public void setSupportBackwardCompatibility(boolean paramBoolean)
  {
    this.supportBackwardCompatibility = paramBoolean;
  }
  
  BPWConfigWrapper(1 param1)
    throws Exception
  {
    this();
  }
  
  private class b
    implements BPWConfigWrapper.a
  {
    String jdField_case;
    boolean jdField_byte;
    InputStream jdField_try = null;
    Document jdField_int = null;
    public String jdField_new = "bpwconfig.xsd";
    
    public b(String paramString, boolean paramBoolean)
    {
      this.jdField_case = paramString;
      this.jdField_byte = paramBoolean;
      jdMethod_for();
    }
    
    public void jdMethod_for()
    {
      try
      {
        this.jdField_try = FFSUtil.getResourceAsStream(this, this.jdField_case);
        InputSource localInputSource = new InputSource(this.jdField_try);
        DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        localDocumentBuilderFactory.setNamespaceAware(false);
        localDocumentBuilderFactory.setValidating(false);
        if (this.jdField_byte)
        {
          localObject1 = FFSUtil.getResourceAsStream(this, this.jdField_new);
          localObject2 = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
          localObject3 = "http://www.w3.org/2001/XMLSchema";
          String str = "http://java.sun.com/xml/jaxp/properties/schemaSource";
          localDocumentBuilderFactory.setNamespaceAware(false);
          localDocumentBuilderFactory.setValidating(true);
          try
          {
            localDocumentBuilderFactory.setAttribute((String)localObject2, localObject3);
            localDocumentBuilderFactory.setAttribute(str, localObject1);
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            System.err.println("The DOM parser is not JAXP 1.2 compliant.");
          }
        }
        Object localObject1 = localDocumentBuilderFactory.newDocumentBuilder();
        ((DocumentBuilder)localObject1).setErrorHandler(new a(null));
        this.jdField_int = ((DocumentBuilder)localObject1).parse(localInputSource);
        Object localObject2 = new XMLSerializer();
        ((XMLSerializer)localObject2).setOutputByteStream(System.out);
        ((XMLSerializer)localObject2).serialize(this.jdField_int);
        this.jdField_try.close();
        Object localObject3 = XMLUtil.readXmlToHashMap(new BPWConfigWrapper(null), this.jdField_case);
        CommBankIdentifier.initialize((HashMap)localObject3);
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.initialise " + this.jdField_case + " failed");
      }
    }
    
    public ArrayList a()
      throws FFSException
    {
      if (this.jdField_int == null)
      {
        DebugLog.log("BPWConfigWrapper.getLogLanguages - Failed to load " + this.jdField_case);
        throw new FFSException("Failed to load log langauges from " + this.jdField_case);
      }
      try
      {
        ArrayList localArrayList = new ArrayList();
        XPath localXPath = new XPath("/Bpw-Config/Log-Languages/Log-Language", null, null, 0);
        XObject localXObject = localXPath.execute(new XPathContext(), this.jdField_int, null);
        NodeList localNodeList = localXObject.nodelist();
        for (int i = 0; i < localNodeList.getLength(); i++)
        {
          Node localNode = localNodeList.item(i);
          if (localNode != null)
          {
            localNode = localNode.getFirstChild();
            localArrayList.add(localNode.getNodeValue());
          }
        }
        return localArrayList;
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.getLogLanguages - Failed to load log langauges from  " + this.jdField_case);
        throw new FFSException("Failed to load log langauges from " + this.jdField_case);
      }
    }
    
    public Properties jdMethod_if()
      throws FFSException
    {
      if (this.jdField_int == null)
      {
        DebugLog.log("BPWConfigWrapper.getLimitCheckConfiguration - Failed to load imit check properties from " + this.jdField_case);
        throw new FFSException("Failed to load imit check properties from " + this.jdField_case);
      }
      try
      {
        Properties localProperties = new Properties();
        String str = null;
        str = a("/Bpw-Config/Limit-Check/Context-Factory", this.jdField_int);
        if (str != null) {
          localProperties.put("CONTEXTFACTORY", str);
        }
        str = a("/Bpw-Config/Limit-Check/Url", this.jdField_int);
        if (str != null) {
          localProperties.put("URL", str);
        }
        str = a("/Bpw-Config/Limit-Check/JNDIName", this.jdField_int);
        if (str != null) {
          localProperties.put("JNDINAME", str);
        }
        str = a("/Bpw-Config/Limit-Check/User", this.jdField_int);
        if (str != null) {
          localProperties.put("USER", str);
        }
        str = a("/Bpw-Config/Limit-Check/Password", this.jdField_int);
        if (str != null) {
          localProperties.put("PASSWORD", str);
        }
        str = a("/Bpw-Config/Limit-Check/InitialContext", this.jdField_int);
        if (str != null) {
          localProperties.put("INITIALCONTEXTS", str);
        }
        str = a("/Bpw-Config/Limit-Check/IncrementalContexts", this.jdField_int);
        if (str != null) {
          localProperties.put("INCREMENTALCONTEXTS", str);
        }
        str = a("/Bpw-Config/Limit-Check/MaxContexts", this.jdField_int);
        if (str != null) {
          localProperties.put("MAXCONTEXTS", str);
        }
        str = a("/Bpw-Config/Limit-Check/DefaultConnections", this.jdField_int);
        if (str != null) {
          localProperties.put("DefaultConnections", str);
        }
        str = a("/Bpw-Config/Limit-Check/MaxConnections", this.jdField_int);
        if (str != null) {
          localProperties.put("MaxConnections", str);
        }
        return localProperties;
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.getLimitCheckConfiguration - Failed to load limit check properties from" + this.jdField_case);
        throw new FFSException("Failed to load limit check properties from " + this.jdField_case);
      }
    }
    
    public String jdMethod_if(String paramString)
      throws FFSException
    {
      String str = null;
      try
      {
        if (paramString != null) {
          str = a("/Bpw-Config/Backend-Handlers/" + paramString + "/Handler-Class", this.jdField_int);
        }
        return str;
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.getBackendHandlerClassName - Failed to load backend handler class name from " + this.jdField_case);
        throw new FFSException("Failed to load backend handler class name from  " + this.jdField_case);
      }
    }
    
    public Properties a(String paramString)
      throws FFSException
    {
      Properties localProperties = new Properties();
      try
      {
        XPath localXPath = new XPath("/Bpw-Config/Backend-Handlers/" + paramString + "/Init-Params/Param", null, null, 0);
        XObject localXObject = localXPath.execute(new XPathContext(), this.jdField_int, null);
        NodeList localNodeList = localXObject.nodelist();
        for (int i = 0; i < localNodeList.getLength(); i++)
        {
          Node localNode = localNodeList.item(i);
          if (localNode != null) {
            a(localProperties, localNode);
          }
        }
        return localProperties;
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.getBackendHandlerClassName - Failed to load backend handler class name from " + this.jdField_case);
        throw new FFSException("Failed to load backend handler class name from  " + this.jdField_case);
      }
    }
    
    private void a(Properties paramProperties, Node paramNode)
      throws Exception
    {
      XPath localXPath = new XPath("Name", null, null, 0);
      XObject localXObject = localXPath.execute(new XPathContext(), paramNode, null);
      NodeList localNodeList1 = localXObject.nodelist();
      Node localNode1 = localNodeList1.item(0);
      String str1 = localNode1.getFirstChild().getNodeValue();
      localXPath = new XPath("Value", null, null, 0);
      localXObject = localXPath.execute(new XPathContext(), paramNode, null);
      NodeList localNodeList2 = localXObject.nodelist();
      Node localNode2 = localNodeList2.item(0);
      String str2 = localNode2.getFirstChild().getNodeValue();
      if (paramProperties != null) {
        paramProperties.put(str1, str2);
      }
    }
    
    public String a(String paramString1, String paramString2)
      throws FFSException
    {
      String str = null;
      try
      {
        XPath localXPath = new XPath("//SWIFT/Swift-Message[Swift-Message-Type='" + paramString2 + "']", null, null, 0);
        XObject localXObject = localXPath.execute(new XPathContext(), this.jdField_int, null);
        NodeList localNodeList = localXObject.nodelist();
        Node localNode1 = localNodeList.item(0);
        localXPath = new XPath("Supported-Beans/Supported-Bean[Bean-Class='" + paramString1 + "']", null, null, 0);
        localXObject = localXPath.execute(new XPathContext(), localNode1, null);
        localNodeList = localXObject.nodelist();
        Node localNode2 = localNodeList.item(0);
        localXPath = new XPath("Mapper-Class", null, null, 0);
        localXObject = localXPath.execute(new XPathContext(), localNode2, null);
        localNodeList = localXObject.nodelist();
        Node localNode3 = localNodeList.item(0);
        str = localNode3.getFirstChild().getNodeValue();
        return str;
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.getSWIFTMapperClassName - Failed to swift mapper class name from " + this.jdField_case);
        throw new FFSException("Failed to load swift mapper class name from  " + this.jdField_case);
      }
    }
    
    public String a(String paramString, Document paramDocument)
      throws Exception
    {
      XPath localXPath = new XPath(paramString, null, null, 0);
      XObject localXObject = localXPath.execute(new XPathContext(), paramDocument, null);
      NodeList localNodeList = localXObject.nodelist();
      Node localNode1 = localNodeList.item(0);
      Node localNode2 = localNode1.getFirstChild();
      if (localNode2 != null) {
        return localNode2.getNodeValue();
      }
      return null;
    }
    
    private class a
      extends DefaultHandler
    {
      private a() {}
      
      public void warning(SAXParseException paramSAXParseException)
        throws SAXException
      {
        DebugLog.log("bpwconfig.xml validation using xsd Warning: ");
        a(paramSAXParseException);
      }
      
      public void error(SAXParseException paramSAXParseException)
        throws SAXException
      {
        DebugLog.log("bpwconfig.xml validation using xsd Error: ");
        a(paramSAXParseException);
      }
      
      public void fatalError(SAXParseException paramSAXParseException)
        throws SAXException
      {
        DebugLog.log("bpwconfig.xml validation using xsd Fatal error: ");
        a(paramSAXParseException);
      }
      
      private void a(SAXParseException paramSAXParseException)
      {
        DebugLog.log("   Line number: " + paramSAXParseException.getLineNumber());
        DebugLog.log("   Column number: " + paramSAXParseException.getColumnNumber());
        DebugLog.log("   Message: " + paramSAXParseException.getMessage());
      }
      
      a(BPWConfigWrapper.1 param1)
      {
        this();
      }
    }
  }
  
  private class d
    implements BPWConfigWrapper.c
  {
    public String jdField_if = "LimitCheckApproval.xml";
    public String jdField_do = "LogLanguages.xml";
    Document a = null;
    Document jdField_for = null;
    
    public d()
    {
      jdField_do();
    }
    
    public void jdField_do()
    {
      try
      {
        InputSource localInputSource = new InputSource(BPWConfigWrapper.this.jdField_if);
        DOMParser localDOMParser = new DOMParser();
        localDOMParser.setFeature("http://apache.org/xml/features/validation/schema", false);
        localDOMParser.parse(localInputSource);
        this.a = localDOMParser.getDocument();
        XMLSerializer localXMLSerializer = new XMLSerializer();
        localXMLSerializer.setOutputByteStream(System.out);
        localXMLSerializer.serialize(this.a);
        BPWConfigWrapper.this.jdField_if.close();
        localInputSource = new InputSource(BPWConfigWrapper.this.jdField_do);
        localDOMParser = new DOMParser();
        localDOMParser.parse(localInputSource);
        this.jdField_for = localDOMParser.getDocument();
        localXMLSerializer.serialize(this.jdField_for);
        BPWConfigWrapper.this.jdField_do.close();
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.initialise legacy files");
      }
    }
    
    public ArrayList a()
      throws FFSException
    {
      if (this.jdField_for == null)
      {
        DebugLog.log("BPWConfigWrapper.getLogLanguages - Failed to load LogLanguages.xml");
        throw new FFSException("Failed to load LogLanguages.xml");
      }
      try
      {
        ArrayList localArrayList = new ArrayList();
        XPath localXPath = new XPath("/SETTINGS/LOG_LANGUAGE_LIST/LOG_LANGUAGE", null, null, 0);
        XObject localXObject = localXPath.execute(new XPathContext(), this.jdField_for, null);
        NodeList localNodeList = localXObject.nodelist();
        for (int i = 0; i < localNodeList.getLength(); i++)
        {
          Node localNode = localNodeList.item(i);
          if (localNode != null)
          {
            localNode = localNode.getFirstChild();
            localArrayList.add(localNode.getNodeValue());
          }
        }
        return localArrayList;
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.getLogLanguages - Failed to load LogLanguages.xml");
        throw new FFSException("Failed to load LogLanguages.xml");
      }
    }
    
    public Properties jdField_if()
      throws FFSException
    {
      if (this.a == null)
      {
        DebugLog.log("BPWConfigWrapper.getLimitCheckConfiguration - Failed to load LimitCheckApproval.xml");
        throw new FFSException("Failed to load LimitCheckApproval.xml");
      }
      try
      {
        Properties localProperties = new Properties();
        XPath localXPath = new XPath("/data/appvar", null, null, 0);
        XObject localXObject = localXPath.execute(new XPathContext(), this.a, null);
        NodeList localNodeList = localXObject.nodelist();
        for (int i = 0; i < localNodeList.getLength(); i++)
        {
          Node localNode = localNodeList.item(i);
          if (localNode.getAttributes().getNamedItem("CONTEXTFACTORY") != null) {
            localProperties.put("CONTEXTFACTORY", localNode.getAttributes().getNamedItem("CONTEXTFACTORY").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("URL") != null) {
            localProperties.put("URL", localNode.getAttributes().getNamedItem("URL").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("JNDINAME") != null) {
            localProperties.put("JNDINAME", localNode.getAttributes().getNamedItem("JNDINAME").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("USER") != null) {
            localProperties.put("USER", localNode.getAttributes().getNamedItem("USER").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("PASSWORD") != null) {
            localProperties.put("PASSWORD", localNode.getAttributes().getNamedItem("PASSWORD").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("INITIALCONTEXTS") != null) {
            localProperties.put("INITIALCONTEXTS", localNode.getAttributes().getNamedItem("INITIALCONTEXTS").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("INCREMENTALCONTEXTS") != null) {
            localProperties.put("INCREMENTALCONTEXTS", localNode.getAttributes().getNamedItem("INCREMENTALCONTEXTS").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("MAXCONTEXTS") != null) {
            localProperties.put("MAXCONTEXTS", localNode.getAttributes().getNamedItem("MAXCONTEXTS").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("DefaultConnections") != null) {
            localProperties.put("DefaultConnections", localNode.getAttributes().getNamedItem("DefaultConnections").getNodeValue());
          }
          if (localNode.getAttributes().getNamedItem("MaxConnections") != null) {
            localProperties.put("MaxConnections", localNode.getAttributes().getNamedItem("MaxConnections").getNodeValue());
          }
        }
        return localProperties;
      }
      catch (Exception localException)
      {
        DebugLog.log("BPWConfigWrapper.getLimitCheckConfiguration - Failed to load LimitCheckApproval.xml");
        throw new FFSException("Failed to load LimitCheckApproval.xml");
      }
    }
  }
  
  private static abstract interface a
    extends BPWConfigWrapper.c
  {
    public abstract String jdMethod_if(String paramString)
      throws FFSException;
    
    public abstract Properties a(String paramString)
      throws FFSException;
    
    public abstract String a(String paramString1, String paramString2)
      throws FFSException;
  }
  
  private static abstract interface c
  {
    public abstract ArrayList a()
      throws FFSException;
    
    public abstract Properties jdMethod_if()
      throws FFSException;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.BPWConfigWrapper
 * JD-Core Version:    0.7.0.1
 */