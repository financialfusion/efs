package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class Product
  extends ProductI18N
  implements Comparable
{
  private String uQ = "";
  private String uS = "";
  private String uU = "";
  private HashMap uT;
  public static final String defaultLanguage = "en_US";
  private String uR = "en_US";
  private String currentLanguage = "en_US";
  
  public String getBankID()
  {
    return this.uQ;
  }
  
  public void setBankID(String paramString)
  {
    this.uQ = paramString;
  }
  
  public String getCategoryID()
  {
    return this.uS;
  }
  
  public void setCategoryID(String paramString)
  {
    this.uS = paramString;
  }
  
  public String getFormID()
  {
    return this.uU;
  }
  
  public void setFormID(String paramString)
  {
    this.uU = paramString;
  }
  
  public String getTitle()
  {
    return getTitle(this.currentLanguage);
  }
  
  public String getTitle(String paramString)
  {
    if (paramString.equals("en_US")) {
      return this.title;
    }
    if (this.uT == null) {
      return null;
    }
    ProductI18N localProductI18N = (ProductI18N)this.uT.get(paramString);
    if (localProductI18N == null) {
      return null;
    }
    return localProductI18N.getTitle();
  }
  
  public String getFirstAvailableTitle()
  {
    String str1 = getTitle();
    if ((str1 != null) && (str1.length() != 0)) {
      return str1;
    }
    if ((this.title != null) && (this.title.length() != 0)) {
      return this.title;
    }
    Iterator localIterator = getLanguages();
    if (localIterator == null) {
      return null;
    }
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      str1 = getTitle(str2);
      if ((str1 != null) && (str1.length() > 0)) {
        return str1;
      }
    }
    return null;
  }
  
  public void setTitle(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US"))
    {
      this.title = paramString2;
      return;
    }
    if (this.uT == null) {
      this.uT = new HashMap();
    }
    ProductI18N localProductI18N = null;
    if (!this.uT.containsKey(paramString1))
    {
      localProductI18N = new ProductI18N();
      localProductI18N.setLanguage(paramString1);
      this.uT.put(paramString1, localProductI18N);
    }
    else
    {
      localProductI18N = (ProductI18N)this.uT.get(paramString1);
    }
    localProductI18N.setTitle(paramString2);
  }
  
  public String getDescription()
  {
    return getDescription(this.currentLanguage);
  }
  
  public String getDescription(String paramString)
  {
    if (paramString.equals("en_US")) {
      return this.description;
    }
    if (this.uT == null) {
      return null;
    }
    ProductI18N localProductI18N = (ProductI18N)this.uT.get(paramString);
    if (localProductI18N == null) {
      return null;
    }
    return localProductI18N.getDescription();
  }
  
  public void setDescription(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US"))
    {
      this.description = paramString2;
      return;
    }
    if (this.uT == null) {
      this.uT = new HashMap();
    }
    ProductI18N localProductI18N = null;
    if (!this.uT.containsKey(paramString1))
    {
      localProductI18N = new ProductI18N();
      localProductI18N.setLanguage(paramString1);
      this.uT.put(paramString1, localProductI18N);
    }
    else
    {
      localProductI18N = (ProductI18N)this.uT.get(paramString1);
    }
    localProductI18N.setDescription(paramString2);
  }
  
  public Iterator getLanguages()
  {
    if (this.uT == null) {
      this.uT = new HashMap();
    }
    return this.uT.keySet().iterator();
  }
  
  public void setCurrentLanguage(String paramString)
  {
    this.currentLanguage = paramString;
  }
  
  public String getCurrentLanguage()
  {
    return this.currentLanguage;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.uR = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.uR;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("BANK_ID")) {
      this.uQ = paramString2;
    } else if (paramString1.equalsIgnoreCase("CATEGORY_ID")) {
      this.uS = paramString2;
    } else if (paramString1.equalsIgnoreCase("FORM_ID")) {
      this.uU = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(Product paramProduct)
  {
    setBankID(paramProduct.getBankID());
    setCategoryID(paramProduct.getCategoryID());
    setFormID(paramProduct.getFormID());
    if (this.uT != null) {
      this.uT.clear();
    }
    Iterator localIterator = paramProduct.getLanguages();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      setTitle(str, paramProduct.getTitle(str));
      setDescription(str, paramProduct.getDescription(str));
    }
    super.set(paramProduct);
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equals("BANK_ID")) {
        return this.uQ.equals(str2);
      }
    }
    return false;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PRODUCT");
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.uQ);
    XMLHandler.appendTag(localStringBuffer, "FORM_ID", this.uU);
    XMLHandler.appendTag(localStringBuffer, "CATEGORY_ID", this.uS);
    XMLHandler.appendTag(localStringBuffer, "PRODUCT_ID", this.productID);
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", this.description);
    XMLHandler.appendTag(localStringBuffer, "TITLE", this.title);
    XMLHandler.appendEndTag(localStringBuffer, "PRODUCT");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Product
 * JD-Core Version:    0.7.0.1
 */