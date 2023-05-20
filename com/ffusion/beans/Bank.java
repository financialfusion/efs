package com.ffusion.beans;

import com.ffusion.util.XMLHandler;

public class Bank
  extends Contact
{
  private String HX;
  private String HT;
  private String HW;
  private String HS;
  private String HV;
  private String HU;
  
  public void setID(String paramString)
  {
    this.HX = paramString;
  }
  
  public String getID()
  {
    return this.HX;
  }
  
  public void setName(String paramString)
  {
    this.HT = paramString;
  }
  
  public String getName()
  {
    return this.HT;
  }
  
  public void setCustomerSupportPhone(String paramString)
  {
    this.HW = paramString;
  }
  
  public String getCustomerSupportPhone()
  {
    return this.HW;
  }
  
  public void setTechnicalSupportPhone(String paramString)
  {
    this.HS = paramString;
  }
  
  public String getTechnicalSupportPhone()
  {
    return this.HS;
  }
  
  public void setFax(String paramString)
  {
    this.HV = paramString;
  }
  
  public String getFax()
  {
    return this.HV;
  }
  
  public void setURL(String paramString)
  {
    this.HU = paramString;
  }
  
  public String getURL()
  {
    return this.HU;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      this.HX = paramString2;
    } else if (paramString1.equals("NAME")) {
      this.HT = paramString2;
    } else if (paramString1.equals("CUSTOMERSUPPORTPHONE")) {
      this.HW = paramString2;
    } else if (paramString1.equals("TECHNICALSUPPORTPHONE")) {
      this.HS = paramString2;
    } else if (paramString1.equals("FAX")) {
      this.HV = paramString2;
    } else if (paramString1.equals("URL")) {
      this.HU = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Bank localBank = (Bank)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (getID() != null) && (localBank.getID() != null)) {
      i = getID().compareTo(localBank.getID());
    } else if ((paramString.equals("NAME")) && (getName() != null) && (localBank.getName() != null)) {
      i = getName().compareTo(localBank.getName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((paramString1.equals("NAME")) && (getName() != null)) {
      return isFilterable(getName(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(Bank paramBank)
  {
    setID(paramBank.getID());
    setName(paramBank.getName());
    setTechnicalSupportPhone(paramBank.getTechnicalSupportPhone());
    setCustomerSupportPhone(paramBank.getCustomerSupportPhone());
    setFax(paramBank.getFax());
    setURL(paramBank.getURL());
    super.set(paramBank);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BANK");
    XMLHandler.appendTag(localStringBuffer, "ID", this.HX);
    XMLHandler.appendTag(localStringBuffer, "NAME", this.HT);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendTag(localStringBuffer, "CUSTOMERSUPPORTPHONE", this.HW);
    XMLHandler.appendTag(localStringBuffer, "TECHNICALSUPPORTPHONE", this.HS);
    XMLHandler.appendTag(localStringBuffer, "FAX", this.HV);
    XMLHandler.appendTag(localStringBuffer, "URL", this.HU);
    XMLHandler.appendEndTag(localStringBuffer, "BANK");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.Bank
 * JD-Core Version:    0.7.0.1
 */