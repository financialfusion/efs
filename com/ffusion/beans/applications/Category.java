package com.ffusion.beans.applications;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class Category
  extends ExtendABean
{
  protected String ID;
  protected String name;
  protected String bankID;
  private static final String aWF = "com.ffusion.beans.applications.resources";
  
  public void setID(String paramString)
  {
    this.ID = paramString;
  }
  
  public String getID()
  {
    return this.ID;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getName()
  {
    return ResourceUtil.getString("Category" + this.ID, "com.ffusion.beans.applications.resources", this.locale);
  }
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("NAME")) {
      this.name = paramString2;
    } else if (paramString1.equalsIgnoreCase("CATEGORY_ID")) {
      this.ID = paramString2;
    } else if (paramString1.equalsIgnoreCase("BANK_ID")) {
      this.bankID = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "CATEGORY");
    XMLHandler.appendTag(localStringBuffer, "CATEGORY_ID", this.ID);
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.bankID);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.name);
    XMLHandler.appendEndTag(localStringBuffer, "CATEGORY");
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
 * Qualified Name:     com.ffusion.beans.applications.Category
 * JD-Core Version:    0.7.0.1
 */