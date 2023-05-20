package com.ffusion.beans.user;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.XMLHandler;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class BusinessEmployee
  extends User
{
  public static final String BUSINESSEMPLOYEEINFO = "BUSINESSEMPLOYEEINFO";
  public static final String BUSINESSID = "BUSINESSID";
  public static final String BUSINESSCUSTID = "BUSINESSCUSTID";
  public static final String PRIMARYUSER = "PRIMARYUSER";
  public static final String ENTITLEMENTGROUPID = "ENTITLEMENTGROUPID";
  private static final String BEAN_NAME = BusinessEmployee.class.getName();
  public static final String LASTVIEWEDINTRADAYTRANSDATE = "LASTVIEWEDINTRADAYTRANSDATE";
  protected int businessId;
  protected String businessCustId;
  protected String primaryUser;
  protected String confirmPassword;
  protected DateTime lastViewedIntradayTransDate = null;
  
  public BusinessEmployee() {}
  
  public BusinessEmployee(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setBusinessId(String paramString)
  {
    try
    {
      this.businessId = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setBusinessId(int paramInt)
  {
    this.businessId = paramInt;
  }
  
  public int getBusinessId()
  {
    return this.businessId;
  }
  
  public String getBusinessIdString()
  {
    return String.valueOf(this.businessId);
  }
  
  public void setBusinessCustId(String paramString)
  {
    this.businessCustId = paramString;
  }
  
  public void setConfirmPassword(String paramString)
  {
    this.confirmPassword = paramString;
  }
  
  public String getBusinessCustId()
  {
    return this.businessCustId;
  }
  
  public String getConfirmPassword()
  {
    return this.confirmPassword;
  }
  
  public void setPrimaryUser(String paramString)
  {
    this.primaryUser = paramString;
  }
  
  public String getPrimaryUser()
  {
    return this.primaryUser;
  }
  
  public SecureUser getSecureUser()
  {
    SecureUser localSecureUser = super.getSecureUser();
    localSecureUser.setBusinessID(getBusinessId());
    localSecureUser.setBusinessCustId(getBusinessCustId());
    return localSecureUser;
  }
  
  public EntitlementGroupMember getEntitlementGroupMember()
  {
    return EntitlementsUtil.getEntitlementGroupMember(getSecureUser());
  }
  
  public DateTime getLastViewedIntradayTransDate()
  {
    return this.lastViewedIntradayTransDate;
  }
  
  public void setLastViewedIntradayTransDate(DateTime paramDateTime)
  {
    this.lastViewedIntradayTransDate = paramDateTime;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    int i = 0;
    BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramObject;
    if (getBusinessId() == localBusinessEmployee.getBusinessId())
    {
      i = 0;
      i = super.compareTo(paramObject);
    }
    else if (getBusinessId() > localBusinessEmployee.getBusinessId())
    {
      i = 1;
    }
    else if (getBusinessId() < localBusinessEmployee.getBusinessId())
    {
      i = -1;
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    int i = 1;
    if (!(paramObject instanceof BusinessEmployee)) {
      return super.compare(paramObject, paramString);
    }
    BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramObject;
    Collator localCollator = doGetCollator();
    if (paramString.equals("BUSINESSID"))
    {
      if (getBusinessId() == localBusinessEmployee.getBusinessId()) {
        i = 0;
      } else if (getBusinessId() > localBusinessEmployee.getBusinessId()) {
        i = 1;
      } else if (getBusinessId() < localBusinessEmployee.getBusinessId()) {
        i = -1;
      }
    }
    else if ((paramString.equals("BUSINESSCUSTID")) && (getBusinessCustId() != null) && (localBusinessEmployee.getBusinessCustId() != null)) {
      i = localCollator.compare(getBusinessCustId(), localBusinessEmployee.getBusinessCustId());
    } else if ((paramString.equals("PRIMARYUSER")) && (getPrimaryUser() != null) && (localBusinessEmployee.getPrimaryUser() != null)) {
      i = localCollator.compare(getPrimaryUser(), localBusinessEmployee.getPrimaryUser());
    } else if (paramString.equals("ENTITLEMENTGROUPID"))
    {
      if (getEntitlementGroupId() == localBusinessEmployee.getEntitlementGroupId()) {
        i = 0;
      } else if (getEntitlementGroupId() > localBusinessEmployee.getEntitlementGroupId()) {
        i = 1;
      } else if (getEntitlementGroupId() < localBusinessEmployee.getEntitlementGroupId()) {
        i = -1;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      if ((str1.equals("PRIMARYUSER")) && (getPrimaryUser() != null)) {
        return isFilterable(getPrimaryUser(), str2, str3);
      }
      return super.isFilterable(paramString);
    }
    return false;
  }
  
  public void set(BusinessEmployee paramBusinessEmployee)
  {
    setBusinessId(paramBusinessEmployee.getBusinessId());
    setBusinessCustId(paramBusinessEmployee.getBusinessCustId());
    setPrimaryUser(paramBusinessEmployee.getPrimaryUser());
    setEntitlementGroupId(paramBusinessEmployee.getEntitlementGroupId());
    setLastViewedIntradayTransDate(paramBusinessEmployee.getLastViewedIntradayTransDate());
    super.set(paramBusinessEmployee);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("BUSINESSID"))
    {
      setBusinessId(Integer.parseInt(paramString2));
    }
    else if (paramString1.equals("BUSINESSCUSTID"))
    {
      setBusinessCustId(paramString2);
    }
    else if (paramString1.equals("PRIMARYUSER"))
    {
      setPrimaryUser(paramString2);
    }
    else if (paramString1.equals("ENTITLEMENTGROUPID"))
    {
      setEntitlementGroupId(Integer.parseInt(paramString2));
    }
    else if (paramString1.equals("LASTVIEWEDINTRADAYTRANSDATE"))
    {
      if (this.lastViewedIntradayTransDate == null)
      {
        this.lastViewedIntradayTransDate = new DateTime(this.locale);
        this.lastViewedIntradayTransDate.setFormat(this.datetype);
      }
      this.lastViewedIntradayTransDate.fromXMLFormat(paramString2);
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, BusinessEmployee paramBusinessEmployee, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "BUSINESSID", paramBusinessEmployee.businessId, this.businessId, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BUSINESSCUSTID", paramBusinessEmployee.businessCustId, this.businessCustId, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRIMARYUSER", paramBusinessEmployee.primaryUser, this.primaryUser, paramString);
    super.logChanges(paramHistoryTracker, paramBusinessEmployee, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, BusinessEmployee paramBusinessEmployee, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "BUSINESSID", paramBusinessEmployee.businessId, this.businessId, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BUSINESSCUSTID", paramBusinessEmployee.businessCustId, this.businessCustId, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRIMARYUSER", paramBusinessEmployee.primaryUser, this.primaryUser, paramILocalizable);
    super.logChanges(paramHistoryTracker, paramBusinessEmployee, paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "BUSINESSID", this.businessId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "BUSINESSCUSTID", this.businessCustId, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PRIMARYUSER", this.primaryUser, paramString);
    super.logDeletion(paramHistoryTracker, paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "BUSINESSID", this.businessId, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "BUSINESSCUSTID", this.businessCustId, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PRIMARYUSER", this.primaryUser, paramILocalizable);
    super.logDeletion(paramHistoryTracker, paramILocalizable);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BUSINESSEMPLOYEEINFO");
    XMLHandler.appendTag(localStringBuffer, "BUSINESSID", Integer.toString(this.businessId));
    XMLHandler.appendTag(localStringBuffer, "BUSINESSCUSTID", this.businessCustId);
    XMLHandler.appendTag(localStringBuffer, "PRIMARYUSER", this.primaryUser);
    XMLHandler.appendTag(localStringBuffer, "ENTITLEMENTGROUPID", Integer.toString(this.entitlementGroupId));
    XMLHandler.appendTag(localStringBuffer, "LASTVIEWEDINTRADAYTRANSDATE", this.lastViewedIntradayTransDate.toXMLFormat());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BUSINESSEMPLOYEEINFO");
    return localStringBuffer.toString();
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      BusinessEmployee.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.BusinessEmployee
 * JD-Core Version:    0.7.0.1
 */