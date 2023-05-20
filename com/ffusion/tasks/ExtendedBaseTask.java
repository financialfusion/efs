package com.ffusion.tasks;

import java.util.Locale;

public abstract class ExtendedBaseTask
  extends BaseTask
{
  protected String beanSessionName = null;
  protected String collectionSessionName = null;
  protected String id = null;
  protected Locale locale = null;
  protected String dateFormat = null;
  protected String validate;
  
  public void setBeanSessionName(String paramString)
  {
    this.beanSessionName = paramString;
  }
  
  public String getBeanSessionName()
  {
    return this.beanSessionName;
  }
  
  public void setCollectionSessionName(String paramString)
  {
    this.collectionSessionName = paramString;
  }
  
  public String getCollectionSessionName()
  {
    return this.collectionSessionName;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public void setLocale(String paramString)
  {
    int i = paramString.indexOf("_");
    if (i != -1)
    {
      String str1 = paramString.substring(0, i);
      String str2 = paramString.substring(i + 1);
      this.locale = new Locale(str1, str2);
    }
    else
    {
      this.locale = Locale.getDefault();
    }
  }
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateFormat = paramString;
  }
  
  public String getDateFormat()
  {
    return this.dateFormat;
  }
  
  public void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ExtendedBaseTask
 * JD-Core Version:    0.7.0.1
 */