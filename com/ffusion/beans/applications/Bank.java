package com.ffusion.beans.applications;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.io.Serializable;

public class Bank
  extends ExtendABean
  implements Serializable
{
  protected String bankID;
  protected String bankName;
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankName(String paramString)
  {
    this.bankName = paramString;
  }
  
  public String getBankName()
  {
    return this.bankName;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("BANK_ID")) {
      this.bankID = paramString2;
    } else if (paramString1.equalsIgnoreCase("BANK_NAME")) {
      this.bankName = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BANK");
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.bankID);
    XMLHandler.appendTag(localStringBuffer, "BANK_NAME", this.bankName);
    XMLHandler.appendBeginTag(localStringBuffer, "BANK");
    return localStringBuffer.toString();
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
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Bank
 * JD-Core Version:    0.7.0.1
 */