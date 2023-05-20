package com.ffusion.beans.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.Localeable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Sortable;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class ACHCompany
  extends ExtendABean
  implements Sortable, Localeable, XMLable, ACHAccountTypes, ACHClassCode
{
  private static final String BEAN_NAME = ACHCompany.class.getName();
  public static String ACH_BATCH_TYPE = "ACH_BATCH_TYPE";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.ach.resources";
  public static final String KEY_ACH_BATCH_TYPE_PREFIX = "ACHBatchType";
  public static final String COMPANYSUMMARY = "COMPANYSUMMARY";
  protected String id;
  protected String custID;
  protected String companyID;
  protected String companyName;
  protected ArrayList entryClasses;
  protected ACHOffsetAccounts accts;
  protected ACHCompanySummary summary;
  protected String trackingID;
  protected String submittedBy;
  protected String currentClassCode;
  protected int achBatchType;
  protected boolean active = true;
  
  public ACHCompany() {}
  
  public ACHCompany(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.id = paramString1;
    this.custID = paramString2;
    this.companyID = paramString3;
    this.companyName = paramString4;
  }
  
  public ACHCompany(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public String getCustID()
  {
    return this.custID;
  }
  
  public void setCustID(String paramString)
  {
    this.custID = paramString;
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
  
  public String getCompanyName()
  {
    return this.companyName;
  }
  
  public void setCompanyName(String paramString)
  {
    this.companyName = paramString;
  }
  
  public ACHCompanySummary getCompanySummary()
  {
    return this.summary;
  }
  
  public String getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public String getCompanySummaryNumBatches()
  {
    if (this.summary == null) {
      return ResourceUtil.getString("ACHCompanySummaryNotAvailable", "com.ffusion.beans.ach.resources", this.locale);
    }
    return Long.toString(this.summary.getNumBatches());
  }
  
  public String getCompanySummaryNumEntries()
  {
    if (this.summary == null) {
      return ResourceUtil.getString("ACHCompanySummaryNotAvailable", "com.ffusion.beans.ach.resources", this.locale);
    }
    return Long.toString(this.summary.getNumBatchEntries());
  }
  
  public String getCompanySummaryGrandTotal()
  {
    if ((this.summary == null) || (this.summary.getGrandTotal() == null)) {
      return ResourceUtil.getString("ACHCompanySummaryNotAvailable", "com.ffusion.beans.ach.resources", this.locale);
    }
    return this.summary.getGrandTotal().toString();
  }
  
  public String getCompanySummaryTotalCredit()
  {
    if ((this.summary == null) || (this.summary.getTotalCredit() == null)) {
      return ResourceUtil.getString("ACHCompanySummaryNotAvailable", "com.ffusion.beans.ach.resources", this.locale);
    }
    return this.summary.getTotalCredit().toString();
  }
  
  public Currency getCompanySummaryTotalCreditValue()
  {
    if ((this.summary == null) || (this.summary.getTotalCredit() == null)) {
      return new Currency("0", this.locale);
    }
    return this.summary.getTotalCredit();
  }
  
  public String getCompanySummaryTotalDebit()
  {
    if ((this.summary == null) || (this.summary.getTotalDebit() == null)) {
      return ResourceUtil.getString("ACHCompanySummaryNotAvailable", "com.ffusion.beans.ach.resources", this.locale);
    }
    return this.summary.getTotalDebit().toString();
  }
  
  public Currency getCompanySummaryTotalDebitValue()
  {
    if ((this.summary == null) || (this.summary.getTotalDebit() == null)) {
      return new Currency("0", this.locale);
    }
    return this.summary.getTotalDebit();
  }
  
  public void setCompanySummary(ACHCompanySummary paramACHCompanySummary)
  {
    this.summary = paramACHCompanySummary;
  }
  
  public ArrayList getEntryClasses()
  {
    return this.entryClasses;
  }
  
  public void setEntryClasses(ArrayList paramArrayList)
  {
    this.entryClasses = paramArrayList;
  }
  
  public String getCurrentClassCode()
  {
    return this.currentClassCode;
  }
  
  public void setCurrentClassCode(String paramString)
  {
    if (paramString.length() > 3) {
      paramString = paramString.substring(0, 3);
    }
    this.currentClassCode = paramString.toUpperCase();
  }
  
  public String getClassCodeEntitled()
  {
    return new Boolean(getClassCodeEntitled(this.currentClassCode)).toString().toUpperCase();
  }
  
  public String getClassCodeAddendaEntitled()
  {
    return new Boolean(getClassCodeAddendaEntitled(this.currentClassCode)).toString().toUpperCase();
  }
  
  public String getACHPaymentEntitled()
  {
    return new Boolean(getACHPaymentEntitledValue()).toString().toUpperCase();
  }
  
  public String getTaxPaymentEntitled()
  {
    return new Boolean(getTaxPaymentEntitledValue()).toString().toUpperCase();
  }
  
  public boolean getTaxPaymentEntitledValue()
  {
    if (this.entryClasses == null) {
      return true;
    }
    Iterator localIterator = this.entryClasses.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.toUpperCase().indexOf("TXP") > 0) {
        return true;
      }
    }
    return false;
  }
  
  public String getChildSupportPaymentEntitled()
  {
    return new Boolean(getChildSupportPaymentEntitledValue()).toString().toUpperCase();
  }
  
  public boolean getChildSupportPaymentEntitledValue()
  {
    if (this.entryClasses == null) {
      return true;
    }
    Iterator localIterator = this.entryClasses.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.toUpperCase().indexOf("DED") > 0) {
        return true;
      }
    }
    return false;
  }
  
  public boolean getACHPaymentEntitledValue()
  {
    if (this.entryClasses == null) {
      return true;
    }
    Iterator localIterator = this.entryClasses.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      str = str.toUpperCase();
      if ((str.indexOf("TXP") <= 0) && (str.indexOf("DED") <= 0) && (str.indexOf("ADDENDA") <= 0)) {
        return true;
      }
    }
    return false;
  }
  
  public String getFirstEntitledClassCode()
  {
    Iterator localIterator = this.entryClasses.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      str = str.toUpperCase();
      if (str.length() <= 3) {
        return str;
      }
    }
    return "";
  }
  
  public boolean getClassCodeEntitled(String paramString)
  {
    if ((this.entryClasses == null) || (paramString == null)) {
      return true;
    }
    String str1 = paramString.toUpperCase();
    if (str1.length() > 3) {
      str1 = str1.substring(0, 3);
    }
    Iterator localIterator = this.entryClasses.iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      str2 = str2.toUpperCase();
      if (str2.length() <= 3) {
        if (str2.equals(str1)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean getClassCodeAddendaEntitled(String paramString)
  {
    if ((this.entryClasses == null) || (paramString == null)) {
      return true;
    }
    String str = paramString.toUpperCase();
    if (str.length() > 3) {
      str = str.substring(0, 3);
    }
    return this.entryClasses.contains(str + " + Addenda");
  }
  
  public void setActive(String paramString)
  {
    this.active = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getActive()
  {
    return "" + this.active;
  }
  
  public boolean getActiveValue()
  {
    return this.active;
  }
  
  public ACHOffsetAccounts getAccts()
  {
    return this.accts;
  }
  
  public void setAccts(ACHOffsetAccounts paramACHOffsetAccounts)
  {
    this.accts = paramACHOffsetAccounts;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.accts != null) {
      this.accts.setLocale(paramLocale);
    }
    if (this.summary != null) {
      this.summary.setLocale(paramLocale);
    }
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHCOMPANY");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "TRACKINGID", this.trackingID);
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", this.custID);
    XMLHandler.appendTag(localStringBuffer, "COID", this.companyID);
    XMLHandler.appendTag(localStringBuffer, "CONAME", this.companyName);
    XMLHandler.appendTag(localStringBuffer, "SUBMITTED_BY", this.submittedBy);
    XMLHandler.appendTag(localStringBuffer, "ACTIVE", getActive());
    XMLHandler.appendTag(localStringBuffer, ACH_BATCH_TYPE, this.achBatchType);
    if (this.entryClasses != null) {
      XMLHandler.appendTag(localStringBuffer, "STANDARDENTRYCLASSCODE", this.entryClasses.toString());
    }
    if (this.accts != null) {
      this.accts.getXML();
    }
    if (this.summary != null) {
      this.summary.getXML();
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACHCOMPANY");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ID"))
      {
        this.id = paramString2;
      }
      else if (paramString1.equals("TRACKINGID"))
      {
        this.trackingID = paramString2;
      }
      else if (paramString1.equals("CUSTOMER_ID"))
      {
        this.custID = paramString2;
      }
      else if (paramString1.equals("COID"))
      {
        this.companyID = paramString2;
      }
      else if (paramString1.equals("CONAME"))
      {
        this.companyName = paramString2;
      }
      else if (paramString1.equals("STANDARDENTRYCLASSCODE"))
      {
        this.entryClasses = new ArrayList();
        paramString2 = Strings.removeChars(paramString2, '[');
        paramString2 = Strings.removeChars(paramString2, ']');
        StringTokenizer localStringTokenizer = new StringTokenizer(paramString2, ",");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str = localStringTokenizer.nextToken();
          Integer localInteger = new Integer(str);
          this.entryClasses.add(localInteger);
        }
      }
      else if (paramString1.equals("ACTIVE"))
      {
        setActive(paramString2);
      }
      else if (paramString1.equals(ACH_BATCH_TYPE))
      {
        this.achBatchType = Integer.parseInt(paramString2);
      }
      else if (paramString1.equals("SUBMITTED_BY"))
      {
        this.submittedBy = paramString2;
      }
      else
      {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
  }
  
  public boolean set(ACHCompany paramACHCompany)
  {
    boolean bool = true;
    try
    {
      this.id = paramACHCompany.getID();
      this.custID = paramACHCompany.getCustID();
      this.companyID = paramACHCompany.getCompanyID();
      this.trackingID = paramACHCompany.getTrackingID();
      this.companyName = paramACHCompany.getCompanyName();
      this.submittedBy = paramACHCompany.getSubmittedBy();
      this.active = paramACHCompany.getActiveValue();
      setACHBatchType(paramACHCompany.getACHBatchTypeValue());
      if (paramACHCompany.getEntryClasses() != null) {
        this.entryClasses = ((ArrayList)paramACHCompany.getEntryClasses().clone());
      } else {
        this.entryClasses = null;
      }
      if (paramACHCompany.getAccts() != null) {
        this.accts = ((ACHOffsetAccounts)paramACHCompany.getAccts().clone());
      } else {
        this.accts = null;
      }
      if (paramACHCompany.getCompanySummary() != null) {
        this.summary = ((ACHCompanySummary)paramACHCompany.getCompanySummary().clone());
      } else {
        this.summary = null;
      }
      super.set(paramACHCompany);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
    }
    return bool;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ACHCompany localACHCompany = (ACHCompany)paramObject;
    int i = 1;
    if ((paramString.equals("CUSTOMER_ID")) && (this.custID != null) && (localACHCompany.getCustID() != null)) {
      i = this.custID.compareTo(localACHCompany.getCustID());
    } else if ((paramString.equals("COID")) && (this.companyID != null) && (localACHCompany.getCompanyID() != null)) {
      i = this.companyID.compareToIgnoreCase(localACHCompany.getCompanyID());
    } else if ((paramString.equals("CONAME")) && (this.companyName != null) && (localACHCompany.getCompanyName() != null)) {
      i = this.companyName.compareToIgnoreCase(localACHCompany.getCompanyName());
    } else if ((paramString.equals("COSUMMARYNUMBATCHES")) && (this.summary != null) && (localACHCompany.getCompanySummary() != null)) {
      i = this.summary.compare(localACHCompany.getCompanySummary(), "COSUMMARYNUMBATCHES");
    } else if ((paramString.equals("COSUMMARYNUMENTRIES")) && (this.summary != null) && (localACHCompany.getCompanySummary() != null)) {
      i = this.summary.compare(localACHCompany.getCompanySummary(), "COSUMMARYNUMENTRIES");
    } else if ((paramString.equals("COSUMMARYTOTALDEBIT")) && (this.summary != null) && (localACHCompany.getCompanySummary() != null)) {
      i = this.summary.compare(localACHCompany.getCompanySummary(), "COSUMMARYTOTALDEBIT");
    } else if ((paramString.equals("COSUMMARYTOTALCREDIT")) && (this.summary != null) && (localACHCompany.getCompanySummary() != null)) {
      i = this.summary.compare(localACHCompany.getCompanySummary(), "COSUMMARYTOTALCREDIT");
    } else if ((paramString.equals("COSUMMARYGRANDTOTAL")) && (this.summary != null) && (localACHCompany.getCompanySummary() != null)) {
      i = this.summary.compare(localACHCompany.getCompanySummary(), "COSUMMARYGRANDTOTAL");
    } else if ((paramString.equals("SUBMITTED_BY")) && (this.submittedBy != null) && (localACHCompany.getSubmittedBy() != null)) {
      i = this.submittedBy.compareTo(localACHCompany.getSubmittedBy());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHCompany paramACHCompany, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHCompany.getID(), getID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", paramACHCompany.getTrackingID(), getTrackingID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CUSTOMER_ID", paramACHCompany.getCustID(), getCustID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COID", paramACHCompany.getCompanyID(), getCompanyID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CONAME", paramACHCompany.getCompanyName(), getCompanyName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, ACH_BATCH_TYPE, paramACHCompany.getACHBatchType(), getACHBatchType(), paramString);
    super.logChanges(paramHistoryTracker, paramACHCompany, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHCompany paramACHCompany, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHCompany.getID(), getID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", paramACHCompany.getTrackingID(), getTrackingID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CUSTOMER_ID", paramACHCompany.getCustID(), getCustID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COID", paramACHCompany.getCompanyID(), getCompanyID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CONAME", paramACHCompany.getCompanyName(), getCompanyName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, ACH_BATCH_TYPE, new LocalizableString("com.ffusion.beans.ach.resources", "ACHBatchType" + paramACHCompany.getACHBatchTypeValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHBatchType" + getACHBatchTypeValue(), null), paramILocalizable);
    super.logChanges(paramHistoryTracker, paramACHCompany, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void setACHBatchType(int paramInt)
  {
    this.achBatchType = paramInt;
  }
  
  public void setACHBatchType(String paramString)
  {
    try
    {
      this.achBatchType = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getACHBatchType()
  {
    return ResourceUtil.getString("ACHBatchType" + this.achBatchType, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public int getACHBatchTypeValue()
  {
    return this.achBatchType;
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ACHCompany.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
    {
      Object localObject;
      if (paramString.equals("OFFSETACCOUNTS"))
      {
        localObject = new ACHOffsetAccounts(ACHCompany.this.locale);
        ((ACHOffsetAccounts)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equals("COMPANYSUMMARY"))
      {
        localObject = new ACHCompanySummary(ACHCompany.this.locale);
        ((ACHCompanySummary)localObject).continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHCompany
 * JD-Core Version:    0.7.0.1
 */