package com.ffusion.beans.applications;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.text.Collator;

public class ProductI18N
  extends ExtendABean
  implements Comparable
{
  protected String productID = "";
  protected String language = "";
  protected String description = "";
  protected String title = "";
  
  public String getProductID()
  {
    return this.productID;
  }
  
  public void setProductID(String paramString)
  {
    this.productID = paramString;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public void setLanguage(String paramString)
  {
    this.language = paramString;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("PRODUCT_ID")) {
      this.productID = paramString2;
    } else if (paramString1.equalsIgnoreCase("LANGUAGE")) {
      this.language = paramString2;
    } else if (paramString1.equalsIgnoreCase("DESCRIPTION")) {
      this.description = paramString2;
    } else if (paramString1.equalsIgnoreCase("TITLE")) {
      this.title = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(ProductI18N paramProductI18N)
  {
    this.productID = paramProductI18N.productID;
    this.language = paramProductI18N.language;
    this.description = paramProductI18N.description;
    this.title = paramProductI18N.title;
    super.set(paramProductI18N);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "TITLE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ProductI18N localProductI18N = (ProductI18N)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if (paramString.equals("TITLE")) {
      i = localCollator.compare(getTitle(), localProductI18N.getTitle());
    } else if (paramString.equals("DESCRIPTION")) {
      i = localCollator.compare(getDescription(), localProductI18N.getDescription());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
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
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("PRODUCT")) {
        int i = 1;
      } else {
        super.startElement(paramString);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.ProductI18N
 * JD-Core Version:    0.7.0.1
 */