package com.ffusion.beans.exttransfers;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import java.text.Collator;
import java.util.Locale;

public class ExtTransferCompany
  extends ExtendABean
{
  static final String BEAN_NAME = ExtTransferCompany.class.getName();
  public static final String EXTTRANSFERCOMPANYINFO = "EXTTRANSFERCOMPANYINFO";
  protected String bpwID;
  protected String custID;
  protected String companyName;
  protected String companyID;
  protected String batchDescription;
  
  public ExtTransferCompany() {}
  
  public ExtTransferCompany(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public Object clone()
  {
    try
    {
      ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)super.clone();
      return localExtTransferCompany;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void setBpwID(String paramString)
  {
    this.bpwID = paramString;
  }
  
  public String getBpwID()
  {
    return this.bpwID;
  }
  
  public void setCustID(String paramString)
  {
    this.custID = paramString;
  }
  
  public String getCustID()
  {
    return this.custID;
  }
  
  public void setCompanyName(String paramString)
  {
    this.companyName = paramString;
  }
  
  public String getCompanyName()
  {
    return this.companyName;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setBatchDescription(String paramString)
  {
    this.batchDescription = paramString;
  }
  
  public String getBatchDescription()
  {
    return this.batchDescription;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("BPWID")) && (this.bpwID != null) && (localExtTransferCompany.getBpwID() != null)) {
      i = localCollator.compare(getBpwID(), localExtTransferCompany.getBpwID());
    } else if ((paramString.equals("CUSTID")) && (this.custID != null) && (localExtTransferCompany.getCustID() != null)) {
      i = localCollator.compare(getCustID(), localExtTransferCompany.getCustID());
    } else if ((paramString.equals("COMPANYNAME")) && (this.companyName != null) && (localExtTransferCompany.getCompanyName() != null)) {
      i = localCollator.compare(getCompanyName(), localExtTransferCompany.getCompanyName());
    } else if ((paramString.equals("COMPANYID")) && (this.companyID != null) && (localExtTransferCompany.getCompanyID() != null)) {
      i = localCollator.compare(getCompanyID(), localExtTransferCompany.getCompanyID());
    } else if ((paramString.equals("BATCHDESCRIPTION")) && (this.batchDescription != null) && (localExtTransferCompany.getBatchDescription() != null)) {
      i = localCollator.compare(getBatchDescription(), localExtTransferCompany.getBatchDescription());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("BPWID")) && (this.bpwID != null)) {
      return isFilterable(this.bpwID, paramString2, paramString3);
    }
    if ((paramString1.equals("CUSTID")) && (this.custID != null)) {
      return isFilterable(this.custID, paramString2, paramString3);
    }
    if ((paramString1.equals("COMPANYNAME")) && (this.companyName != null)) {
      return isFilterable(this.companyName, paramString2, paramString3);
    }
    if ((paramString1.equals("COMPANYID")) && (this.companyID != null)) {
      return isFilterable(this.companyID, paramString2, paramString3);
    }
    if ((paramString1.equals("BATCHDESCRIPTION")) && (this.batchDescription != null)) {
      return isFilterable(this.batchDescription, paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(ExtTransferCompany paramExtTransferCompany)
  {
    super.set(paramExtTransferCompany);
    this.bpwID = paramExtTransferCompany.getBpwID();
    this.custID = paramExtTransferCompany.getCustID();
    this.companyID = paramExtTransferCompany.getCompanyID();
    this.companyName = paramExtTransferCompany.getCompanyName();
    this.batchDescription = paramExtTransferCompany.getBatchDescription();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("BPWID")) {
      setBpwID(paramString2);
    } else if (paramString1.equals("CUSTID")) {
      this.custID = paramString2;
    } else if (paramString1.equals("COMPANYNAME")) {
      this.companyName = paramString2;
    } else if (paramString1.equals("COMPANYID")) {
      this.companyID = paramString2;
    } else if (paramString1.equals("BATCHDESCRIPTION")) {
      this.batchDescription = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendTag(localStringBuffer, "BPWID", this.bpwID);
    XMLHandler.appendTag(localStringBuffer, "CUSTID", this.custID);
    XMLHandler.appendTag(localStringBuffer, "COMPANYNAME", this.companyName);
    XMLHandler.appendTag(localStringBuffer, "COMPANYID", this.companyID);
    XMLHandler.appendTag(localStringBuffer, "BATCHDESCRIPTION", this.batchDescription);
    localStringBuffer.append(super.getXML());
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ExtTransferCompany paramExtTransferCompany, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramExtTransferCompany.getBpwID(), getBpwID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CUSTID", paramExtTransferCompany.getCustID(), getCustID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYID", paramExtTransferCompany.getCompanyID(), getCompanyID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYNAME", paramExtTransferCompany.getCompanyName(), getCompanyName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BATCHDESCRIPTION", paramExtTransferCompany.getBatchDescription(), getBatchDescription(), paramString);
    super.logChanges(paramHistoryTracker, paramExtTransferCompany, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "BPWID", getBpwID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "BPWID", getBpwID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ExtTransferCompany paramExtTransferCompany, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramExtTransferCompany.getBpwID(), getBpwID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CUSTID", paramExtTransferCompany.getCustID(), getCustID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYID", paramExtTransferCompany.getCompanyID(), getCompanyID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYNAME", paramExtTransferCompany.getCompanyName(), getCompanyName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BATCHDESCRIPTION", paramExtTransferCompany.getBatchDescription(), getBatchDescription(), paramILocalizable);
    super.logChanges(paramHistoryTracker, paramExtTransferCompany, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "BPWID", getBpwID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "BPWID", getBpwID(), null, paramILocalizable);
  }
  
  public String getInquireComment()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    getInquireComment(localStringBuffer);
    return localStringBuffer.toString();
  }
  
  protected void getInquireComment(StringBuffer paramStringBuffer)
  {
    if (getBpwID() == null) {
      paramStringBuffer.append("No BPWID.\n");
    } else {
      paramStringBuffer.append("Bpw ID is ").append(getBpwID()).append(".\n");
    }
    if (getCustID() == null) {
      paramStringBuffer.append("No custID.\n");
    } else {
      paramStringBuffer.append("CustID is ").append(getCustID()).append(".\n");
    }
    if (getCompanyName() == null) {
      paramStringBuffer.append("No companyName.\n");
    } else {
      paramStringBuffer.append("CompanyName is ").append(getCompanyName()).append(".\n");
    }
    if (getCompanyID() == null) {
      paramStringBuffer.append("No companyID.\n");
    } else {
      paramStringBuffer.append("CompanyID is ").append(getCompanyID()).append(".\n");
    }
    if (getBatchDescription() == null) {
      paramStringBuffer.append("No batchDescription.\n");
    } else {
      paramStringBuffer.append("BatchDescription is ").append(getBatchDescription()).append(".\n");
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.exttransfers.ExtTransferCompany
 * JD-Core Version:    0.7.0.1
 */