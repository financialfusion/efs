package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;

public class EmployeeNotification
  implements Serializable
{
  public static final String PRODUCT_ID = "PRODUCT_ID";
  public static final String BANK_ID = "BANK_ID";
  public static final String EMPLOYEE_ID = "EMPLOYEE_ID";
  public static final String EMPLOYEE_NOTIFY = "EMPLOYEE_NOTIFY";
  protected String productID;
  protected String bankID;
  protected String employeeID;
  
  public String getProductID()
  {
    return this.productID;
  }
  
  public void setProductID(String paramString)
  {
    this.productID = paramString;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public String getEmployeeID()
  {
    return this.employeeID;
  }
  
  public void setEmployeeID(String paramString)
  {
    this.employeeID = paramString;
  }
  
  public void set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("PRODUCT_ID")) {
      this.productID = paramString2;
    } else if (paramString1.equalsIgnoreCase("BANK_ID")) {
      this.bankID = paramString2;
    } else if (paramString1.equalsIgnoreCase("EMPLOYEE_ID")) {
      this.employeeID = paramString2;
    }
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
    XMLHandler.appendBeginTag(localStringBuffer, "EMPLOYEE_NOTIFY");
    XMLHandler.appendTag(localStringBuffer, "PRODUCT_ID", this.productID);
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.bankID);
    XMLHandler.appendTag(localStringBuffer, "EMPLOYEE_ID", this.employeeID);
    XMLHandler.appendEndTag(localStringBuffer, "EMPLOYEE_NOTIFY");
    return localStringBuffer.toString();
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
    
    public void startElement(String paramString) {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      EmployeeNotification.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.EmployeeNotification
 * JD-Core Version:    0.7.0.1
 */