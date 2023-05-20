package com.ffusion.beans.authentication;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;

public class Credentials
  extends FilteredList
{
  public static final String CREDENTIALS = "CREDENTIALS";
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CREDENTIALS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Credential)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "CREDENTIALS");
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
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("CREDENTIAL"))
      {
        Credential localCredential = new Credential();
        localCredential.continueXMLParsing(getHandler());
        Credentials.this.add(localCredential);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.authentication.Credentials
 * JD-Core Version:    0.7.0.1
 */