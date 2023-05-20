package com.ffusion.beans.applications;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class ProductAccess
  extends ExtendABean
{
  private String aWJ;
  private String aWI;
  private String aWK;
  
  public String getProductID()
  {
    return this.aWJ;
  }
  
  public void setProductID(String paramString)
  {
    this.aWJ = paramString;
  }
  
  public String getBankID()
  {
    return this.aWI;
  }
  
  public void setBankID(String paramString)
  {
    this.aWI = paramString;
  }
  
  public String getEmployeeID()
  {
    return this.aWK;
  }
  
  public void setEmployeeID(String paramString)
  {
    this.aWK = paramString;
  }
  
  public void set(ProductAccess paramProductAccess)
  {
    setProductID(paramProductAccess.getProductID());
    setBankID(paramProductAccess.getBankID());
    setEmployeeID(paramProductAccess.getEmployeeID());
    super.set(paramProductAccess);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("PRODUCT_ID")) {
      this.aWJ = paramString2;
    } else if (paramString1.equalsIgnoreCase("BANK_ID")) {
      this.aWI = paramString2;
    } else if (paramString1.equalsIgnoreCase("EMPLOYEE_ID")) {
      this.aWK = paramString2;
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
    XMLHandler.appendBeginTag(localStringBuffer, "PRODUCT_ACCESS");
    XMLHandler.appendTag(localStringBuffer, "PRODUCT_ID", this.aWJ);
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.aWI);
    XMLHandler.appendTag(localStringBuffer, "EMPLOYEE_ID", this.aWK);
    XMLHandler.appendEndTag(localStringBuffer, "PRODUCT_ACCESS");
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
 * Qualified Name:     com.ffusion.beans.applications.ProductAccess
 * JD-Core Version:    0.7.0.1
 */