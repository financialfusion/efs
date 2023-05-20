package com.ffusion.beans.authentication;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class Credential
  extends ExtendABean
{
  public static final String CREDENTIAL = "CREDENTIAL";
  public static final String TYPE = "TYPE";
  public static final String REQUEST = "REQUEST";
  public static final String LOCALIZED_REQUEST = "LOCALIZED_REQUEST";
  public static final String RESPONSE = "RESPONSE";
  private int jdField_do;
  private String a = null;
  private String jdField_for = null;
  private String jdField_if = null;
  
  public Credential(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public Credential() {}
  
  public void setType(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public void setType(String paramString)
  {
    try
    {
      this.jdField_do = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public int getType()
  {
    return this.jdField_do;
  }
  
  public void setRequest(String paramString)
  {
    this.a = paramString;
  }
  
  public String getRequest()
  {
    return this.a;
  }
  
  public void setLocalizedRequest(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getLocalizedRequest()
  {
    return this.jdField_for;
  }
  
  public void setResponse(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getResponse()
  {
    return this.jdField_if;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CREDENTIAL");
    XMLHandler.appendTag(localStringBuffer, "TYPE", this.jdField_do);
    XMLHandler.appendTag(localStringBuffer, "REQUEST", this.a);
    XMLHandler.appendTag(localStringBuffer, "LOCALIZED_REQUEST", this.jdField_for);
    XMLHandler.appendTag(localStringBuffer, "RESPONSE", this.jdField_if);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "CREDENTIAL");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new ExtendABean.InternalXMLHandler(this), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("TYPE")) {
      try
      {
        this.jdField_do = Integer.parseInt(paramString2);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return false;
      }
    } else if (paramString1.equals("REQUEST")) {
      this.a = paramString2;
    } else if (paramString1.equals("LOCALIZED_REQUEST")) {
      this.jdField_for = paramString2;
    } else if (paramString1.equals("RESPONSE")) {
      this.jdField_if = paramString2;
    } else {
      return super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.authentication.Credential
 * JD-Core Version:    0.7.0.1
 */