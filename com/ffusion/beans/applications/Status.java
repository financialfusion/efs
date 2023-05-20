package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class Status
  extends StatusI18N
{
  protected String productId;
  protected String bankId;
  private HashMap uG;
  public static final String defaultLanguage = "en_US";
  private String uI = "en_US";
  private String uH = "en_US";
  
  public void setProductID(String paramString)
  {
    this.productId = paramString;
  }
  
  public String getProductID()
  {
    return this.productId;
  }
  
  public void setBankID(String paramString)
  {
    this.bankId = paramString;
  }
  
  public String getBankID()
  {
    return this.bankId;
  }
  
  public String getName(String paramString)
  {
    if (paramString.equals("en_US")) {
      return this.name;
    }
    if (this.uG == null) {
      return null;
    }
    StatusI18N localStatusI18N = (StatusI18N)this.uG.get(paramString);
    if (localStatusI18N == null) {
      return null;
    }
    return localStatusI18N.getName();
  }
  
  public void setName(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US"))
    {
      this.name = paramString2;
      return;
    }
    if (this.uG == null) {
      this.uG = new HashMap();
    }
    StatusI18N localStatusI18N = null;
    if (!this.uG.containsKey(paramString1))
    {
      localStatusI18N = new StatusI18N();
      localStatusI18N.setLanguage(paramString1);
      this.uG.put(paramString1, localStatusI18N);
    }
    else
    {
      localStatusI18N = (StatusI18N)this.uG.get(paramString1);
    }
    localStatusI18N.setName(paramString2);
  }
  
  public String getName()
  {
    return getName(this.uH);
  }
  
  public Iterator getLanguages()
  {
    if (this.uG == null) {
      this.uG = new HashMap();
    }
    return this.uG.keySet().iterator();
  }
  
  public void setCurrentLanguage(String paramString)
  {
    this.uH = paramString;
  }
  
  public String getCurrentLanguage()
  {
    return this.uH;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.uI = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.uI;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equals("PRODUCT_ID")) {
        return this.productId.equals(str2);
      }
    }
    return false;
  }
  
  public void set(Status paramStatus)
  {
    setProductID(paramStatus.getProductID());
    setBankID(paramStatus.getBankID());
    super.set(paramStatus);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("PRODUCT_ID")) {
      this.productId = paramString2;
    } else if (paramString1.equalsIgnoreCase("BANK_ID")) {
      this.bankId = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "APP_STATUS");
    XMLHandler.appendTag(localStringBuffer, "STATUS_ID", this.Id);
    XMLHandler.appendTag(localStringBuffer, "LANGUAGE", this.name);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.name);
    XMLHandler.appendTag(localStringBuffer, "PRODUCT_ID", this.productId);
    XMLHandler.appendEndTag(localStringBuffer, "APP_STATUS");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Status
 * JD-Core Version:    0.7.0.1
 */