package com.ffusion.beans.cashcon;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;

public class CashConCompany
  extends ExtendABean
  implements Comparable
{
  private static final String BEAN_NAME = CashConCompany.class.getName();
  private String a0n;
  private boolean a0i = true;
  private boolean a0m = false;
  private String a0d;
  private String a0p;
  private String a0f;
  private int a0e = 0;
  private boolean a0l = true;
  private CashConAccounts a0j = new CashConAccounts();
  private CutOffTimes a0o = new CutOffTimes();
  private boolean a0h = true;
  private CashConAccounts a0k = new CashConAccounts();
  private CutOffTimes a0g = new CutOffTimes();
  protected String trackingID;
  
  public void set(CashConCompany paramCashConCompany)
  {
    this.a0n = paramCashConCompany.getBPWID();
    this.a0i = paramCashConCompany.getActive();
    this.a0m = paramCashConCompany.getIsDeleted();
    this.a0d = paramCashConCompany.getCustID();
    this.a0p = paramCashConCompany.getCompanyName();
    this.a0f = paramCashConCompany.getCompanyID();
    this.a0e = paramCashConCompany.getBatchType();
    this.a0l = paramCashConCompany.getConcEnabled();
    this.a0j = ((CashConAccounts)paramCashConCompany.getConcAccounts().clone());
    this.a0o = ((CutOffTimes)paramCashConCompany.getConcCutOffs().clone());
    this.a0h = paramCashConCompany.getDisbEnabled();
    this.a0k = ((CashConAccounts)paramCashConCompany.getDisbAccounts().clone());
    this.a0g = ((CutOffTimes)paramCashConCompany.getDisbCutOffs().clone());
  }
  
  public void setBPWID(String paramString)
  {
    this.a0n = paramString;
  }
  
  public void setActive(boolean paramBoolean)
  {
    this.a0i = paramBoolean;
  }
  
  public void setActive(String paramString)
  {
    this.a0i = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setIsDeleted(boolean paramBoolean)
  {
    this.a0m = paramBoolean;
  }
  
  public void setIsDeleted(String paramString)
  {
    this.a0m = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCustID(String paramString)
  {
    this.a0d = paramString;
  }
  
  public void setCompanyName(String paramString)
  {
    this.a0p = paramString;
  }
  
  public void setCompanyID(String paramString)
  {
    this.a0f = paramString;
  }
  
  public void setBatchType(int paramInt)
  {
    this.a0e = paramInt;
  }
  
  public void setBatchType(String paramString)
  {
    try
    {
      this.a0e = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setConcEnabled(boolean paramBoolean)
  {
    this.a0l = paramBoolean;
  }
  
  public void setConcEnabled(String paramString)
  {
    this.a0l = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setConcAccounts(CashConAccounts paramCashConAccounts)
  {
    this.a0j = paramCashConAccounts;
  }
  
  public void setConcCutOffs(CutOffTimes paramCutOffTimes)
  {
    this.a0o = paramCutOffTimes;
  }
  
  public void setDisbEnabled(boolean paramBoolean)
  {
    this.a0h = paramBoolean;
  }
  
  public void setDisbEnabled(String paramString)
  {
    this.a0h = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDisbAccounts(CashConAccounts paramCashConAccounts)
  {
    this.a0k = paramCashConAccounts;
  }
  
  public void setDisbCutOffs(CutOffTimes paramCutOffTimes)
  {
    this.a0g = paramCutOffTimes;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public String getBPWID()
  {
    return this.a0n;
  }
  
  public boolean getActive()
  {
    return this.a0i;
  }
  
  public boolean getIsDeleted()
  {
    return this.a0m;
  }
  
  public String getCustID()
  {
    return this.a0d;
  }
  
  public String getCompanyName()
  {
    return this.a0p;
  }
  
  public String getCompanyID()
  {
    return this.a0f;
  }
  
  public int getBatchType()
  {
    return this.a0e;
  }
  
  public boolean getConcEnabled()
  {
    return this.a0l;
  }
  
  public String getConcEnabledString()
  {
    return "" + getConcEnabled();
  }
  
  public CashConAccounts getConcAccounts()
  {
    return this.a0j;
  }
  
  public CutOffTimes getConcCutOffs()
  {
    return this.a0o;
  }
  
  public boolean getDisbEnabled()
  {
    return this.a0h;
  }
  
  public String getDisbEnabledString()
  {
    return "" + getDisbEnabled();
  }
  
  public CashConAccounts getDisbAccounts()
  {
    return this.a0k;
  }
  
  public CutOffTimes getDisbCutOffs()
  {
    return this.a0g;
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public int compareTo(Object paramObject)
  {
    return compare(paramObject, "ID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    CashConCompany localCashConCompany = (CashConCompany)paramObject;
    if (paramString.equals("ID")) {
      return compareStringsIgnoreCase(this.a0f, localCashConCompany.getCompanyID());
    }
    if (paramString.equals("NAME")) {
      return compareStringsIgnoreCase(this.a0p, localCashConCompany.getCompanyName());
    }
    return super.compare(paramObject, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, CashConCompany paramCashConCompany, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ACTIVE", paramCashConCompany.getActive(), getActive(), "");
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramCashConCompany.getCompanyName(), getCompanyName(), "");
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramCashConCompany.getCompanyID(), getCompanyID(), "");
    paramHistoryTracker.detectChange(BEAN_NAME, "BATCH_TYPE", ResourceUtil.getString("ACHBatchType" + paramCashConCompany.getBatchType(), "com.ffusion.beans.ach.resources", paramHistoryTracker.getLocale()), ResourceUtil.getString("ACHBatchType" + getBatchType(), "com.ffusion.beans.ach.resources", paramHistoryTracker.getLocale()), "");
    paramHistoryTracker.detectChange(BEAN_NAME, "CONC_ENABLED", paramCashConCompany.getConcEnabled(), getConcEnabled(), "");
    paramHistoryTracker.detectChange(BEAN_NAME, "DISB_ENABLED", paramCashConCompany.getDisbEnabled(), getDisbEnabled(), "");
    paramHistoryTracker.detectAndLogChange(paramHistoryTracker.lookupField(BEAN_NAME, "CONC_ACCOUNT"), paramCashConCompany.getConcAccounts(), getConcAccounts(), new CashConAccountHistAnalyzer(), paramHistoryTracker.lookupComment(3), paramHistoryTracker.lookupComment(4));
    paramHistoryTracker.detectAndLogChange(paramHistoryTracker.lookupField(BEAN_NAME, "CONC_CUTOFF"), paramCashConCompany.getConcCutOffs(), getConcCutOffs(), new CashConCutOffHistAnalyzer(), paramHistoryTracker.lookupComment(18), paramHistoryTracker.lookupComment(19));
    paramHistoryTracker.detectAndLogChange(paramHistoryTracker.lookupField(BEAN_NAME, "DISB_ACCOUNT"), paramCashConCompany.getDisbAccounts(), getDisbAccounts(), new CashConAccountHistAnalyzer(), paramHistoryTracker.lookupComment(3), paramHistoryTracker.lookupComment(4));
    paramHistoryTracker.detectAndLogChange(paramHistoryTracker.lookupField(BEAN_NAME, "DISB_CUTOFF"), paramCashConCompany.getDisbCutOffs(), getDisbCutOffs(), new CashConCutOffHistAnalyzer(), paramHistoryTracker.lookupComment(18), paramHistoryTracker.lookupComment(19));
    super.logChanges(paramHistoryTracker, paramCashConCompany, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, CashConCompany paramCashConCompany, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ACTIVE", paramCashConCompany.getActive(), getActive(), (ILocalizable)null);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramCashConCompany.getCompanyName(), getCompanyName(), (ILocalizable)null);
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramCashConCompany.getCompanyID(), getCompanyID(), (ILocalizable)null);
    paramHistoryTracker.detectChange(BEAN_NAME, "BATCH_TYPE", new LocalizableString("com.ffusion.beans.ach.resources", "ACHBatchType" + paramCashConCompany.getBatchType(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHBatchType" + getBatchType(), null), (ILocalizable)null);
    paramHistoryTracker.detectChange(BEAN_NAME, "CONC_ENABLED", paramCashConCompany.getConcEnabled(), getConcEnabled(), (ILocalizable)null);
    paramHistoryTracker.detectChange(BEAN_NAME, "DISB_ENABLED", paramCashConCompany.getDisbEnabled(), getDisbEnabled(), (ILocalizable)null);
    paramHistoryTracker.detectAndLogChange(BEAN_NAME, "CONC_ACCOUNT", paramCashConCompany.getConcAccounts(), getConcAccounts(), new CashConAccountHistAnalyzer(), paramHistoryTracker.buildLocalizableComment(3), paramHistoryTracker.buildLocalizableComment(4));
    paramHistoryTracker.detectAndLogChange(BEAN_NAME, "CONC_CUTOFF", paramCashConCompany.getConcCutOffs(), getConcCutOffs(), new CashConCutOffHistAnalyzer(), paramHistoryTracker.buildLocalizableComment(18), paramHistoryTracker.buildLocalizableComment(19));
    paramHistoryTracker.detectAndLogChange(BEAN_NAME, "DISB_ACCOUNT", paramCashConCompany.getDisbAccounts(), getDisbAccounts(), new CashConAccountHistAnalyzer(), paramHistoryTracker.buildLocalizableComment(3), paramHistoryTracker.buildLocalizableComment(4));
    paramHistoryTracker.detectAndLogChange(BEAN_NAME, "DISB_CUTOFF", paramCashConCompany.getDisbCutOffs(), getDisbCutOffs(), new CashConCutOffHistAnalyzer(), paramHistoryTracker.buildLocalizableComment(18), paramHistoryTracker.buildLocalizableComment(19));
    super.logChanges(paramHistoryTracker, paramCashConCompany, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    if (paramString1.equals("ACTIVE")) {
      bool = isFilterable("" + getActive(), paramString2, paramString3);
    } else if (paramString1.equals("CONC_ENABLED")) {
      bool = isFilterable("" + getConcEnabled(), paramString2, paramString3);
    } else if (paramString1.equals("DISB_ENABLED")) {
      bool = isFilterable("" + getDisbEnabled(), paramString2, paramString3);
    } else {
      bool = super.isFilterablePreParsed(paramString1, paramString2, paramString3);
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashConCompany
 * JD-Core Version:    0.7.0.1
 */