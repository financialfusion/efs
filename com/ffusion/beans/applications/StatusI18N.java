package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class StatusI18N
  extends ExtendABean
{
  protected String Id;
  protected String language;
  protected String name;
  
  public void setID(String paramString)
  {
    this.Id = paramString;
  }
  
  public String getID()
  {
    return this.Id;
  }
  
  public void setLanguage(String paramString)
  {
    this.language = paramString;
  }
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void set(StatusI18N paramStatusI18N)
  {
    this.Id = paramStatusI18N.Id;
    this.language = paramStatusI18N.language;
    this.name = paramStatusI18N.name;
    super.set(paramStatusI18N);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("NAME")) {
      this.name = paramString2;
    } else if (paramString1.equalsIgnoreCase("STATUS_ID")) {
      this.Id = paramString2;
    } else if (paramString1.equalsIgnoreCase("LANGUAGE")) {
      this.language = paramString2;
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
 * Qualified Name:     com.ffusion.beans.applications.StatusI18N
 * JD-Core Version:    0.7.0.1
 */