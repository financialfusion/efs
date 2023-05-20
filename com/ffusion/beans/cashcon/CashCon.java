package com.ffusion.beans.cashcon;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;

public class CashCon
  extends FundsTransaction
  implements CashConStatus, Cloneable, Comparable, Serializable, XMLable
{
  private static final String BEAN_NAME = CashCon.class.getName();
  public static final String CASHCON_ADDED = "CASHCON_ADDED";
  public static final String CASHCON_CANCELED = "CASHCON_CANCELED";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.cashcon.resources";
  public static final String KEY_CASH_CON_STATUS_PREFIX = "CashConStatus";
  public DateTime submitDate;
  public DateTime approvalDate;
  public DateTime processedOnDate;
  public String divisionID;
  public String divisionName;
  public String locationID;
  public String locationName;
  public String companyID;
  public int status;
  public String ACHFileName;
  public Object bpwObject;
  protected boolean canEdit = true;
  protected boolean canView = true;
  protected boolean canDelete = true;
  protected String lastModifier = null;
  
  public CashCon()
  {
    this.funds_type = 15;
  }
  
  public CashCon(String paramString)
  {
    super(paramString);
    this.funds_type = 15;
  }
  
  public CashCon(Locale paramLocale)
  {
    super(paramLocale);
    this.funds_type = 15;
  }
  
  public Object clone()
  {
    try
    {
      CashCon localCashCon = (CashCon)super.clone();
      if (localCashCon.getSubmitDateValue() != null) {
        localCashCon.setSubmitDate((DateTime)localCashCon.getSubmitDateValue().clone());
      }
      if (localCashCon.getApprovalDateValue() != null) {
        localCashCon.setApprovalDate((DateTime)localCashCon.getApprovalDateValue().clone());
      }
      if (localCashCon.getProcessedOnDateValue() != null) {
        localCashCon.setProcessedOnDate((DateTime)localCashCon.getProcessedOnDateValue().clone());
      }
      return localCashCon;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.submitDate != null) {
      this.submitDate.setFormat(paramString);
    }
    if (this.approvalDate != null) {
      this.approvalDate.setFormat(paramString);
    }
    if (this.processedOnDate != null) {
      this.processedOnDate.setFormat(paramString);
    }
  }
  
  public String getCanEdit()
  {
    return "" + this.canEdit;
  }
  
  public void setCanEdit(String paramString)
  {
    this.canEdit = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCanView()
  {
    return "" + this.canView;
  }
  
  public void setCanView(String paramString)
  {
    this.canView = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCanDelete()
  {
    return "" + this.canDelete;
  }
  
  public void setCanDelete(String paramString)
  {
    this.canDelete = Boolean.valueOf(paramString).booleanValue();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "AMOUNT");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    CashCon localCashCon = (CashCon)paramObject;
    int i = 1;
    if ((paramString.equals("SUBMITDATE")) && (this.submitDate != null) && (localCashCon.getSubmitDateValue() != null)) {
      i = this.submitDate.compare(localCashCon.getSubmitDateValue());
    } else if (paramString.equals("STATUS")) {
      i = this.status - localCashCon.getStatus();
    } else if ((paramString.equals("STATUSSTRING")) || (paramString.equals("STATUSNAME"))) {
      i = getStatusName().compareTo(localCashCon.getStatusName());
    } else if ((paramString.equals("APPROVAL_DATE")) && (this.approvalDate != null) && (localCashCon.getApprovalDateValue() != null)) {
      i = this.approvalDate.compare(localCashCon.getApprovalDateValue());
    } else if ((paramString.equals("PROCESSEDONDATE")) && (this.processedOnDate != null) && (localCashCon.getProcessedOnDateValue() != null)) {
      i = this.processedOnDate.compare(localCashCon.getProcessedOnDateValue());
    } else if ((paramString.equals("DIVISION_ID")) && (this.divisionID != null) && (localCashCon.getDivisionID() != null)) {
      i = numStringCompare(this.divisionID, localCashCon.getDivisionID());
    } else if ((paramString.equals("DIVISION_NAME")) && (this.divisionName != null) && (localCashCon.getDivisionName() != null)) {
      i = this.divisionName.compareTo(localCashCon.getDivisionName());
    } else if ((paramString.equals("LOCATION_ID")) && (this.locationID != null) && (localCashCon.getLocationID() != null)) {
      i = numStringCompare(this.locationID, localCashCon.getLocationID());
    } else if ((paramString.equals("LOCATION_NAME")) && (this.locationName != null) && (localCashCon.getLocationName() != null)) {
      i = this.locationName.compareTo(localCashCon.getLocationName());
    } else if ((paramString.equals("ACHFILENAME")) && (this.ACHFileName != null) && (localCashCon.getACHFileName() != null)) {
      i = this.ACHFileName.compareTo(localCashCon.getACHFileName());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("SUBMITDATE")) && (this.submitDate != null)) {
      return isFilterable(getSubmitDate(), paramString2, paramString3);
    }
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if ((paramString1.equals("APPROVAL_DATE")) && (this.approvalDate != null)) {
      return isFilterable(getApprovalDate(), paramString2, paramString3);
    }
    if ((paramString1.equals("PROCESSEDONDATE")) && (this.processedOnDate != null)) {
      return isFilterable(getProcessedOnDate(), paramString2, paramString3);
    }
    if ((paramString1.equals("DIVISION_ID")) && (this.divisionID != null)) {
      return isFilterable(getDivisionID(), paramString2, paramString3);
    }
    if ((paramString1.equals("DIVISION_NAME")) && (this.divisionName != null)) {
      return isFilterable(getDivisionName(), paramString2, paramString3);
    }
    if ((paramString1.equals("LOCATION_ID")) && (this.locationID != null)) {
      return isFilterable(getLocationID(), paramString2, paramString3);
    }
    if ((paramString1.equals("LOCATION_NAME")) && (this.locationName != null)) {
      return isFilterable(getLocationName(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACHFILENAME")) && (this.ACHFileName != null)) {
      return isFilterable(getACHFileName(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(CashCon paramCashCon)
  {
    if (paramCashCon.getSubmitDateValue() == null) {
      this.submitDate = null;
    } else {
      setSubmitDate(paramCashCon.getSubmitDateValue());
    }
    if (paramCashCon.getApprovalDateValue() == null) {
      this.approvalDate = null;
    } else {
      setApprovalDate(paramCashCon.getApprovalDateValue());
    }
    if (paramCashCon.getProcessedOnDateValue() == null) {
      this.processedOnDate = null;
    } else {
      setProcessedOnDate(paramCashCon.getProcessedOnDateValue());
    }
    setDivisionID(paramCashCon.getDivisionID());
    setDivisionName(paramCashCon.getDivisionName());
    setCompanyID(paramCashCon.getCompanyID());
    setLocationID(paramCashCon.getLocationID());
    setLocationName(paramCashCon.getLocationName());
    setStatus(paramCashCon.getStatus());
    setACHFileName(paramCashCon.getACHFileName());
    super.set(paramCashCon);
  }
  
  public DateTime getSubmitDateValue()
  {
    return this.submitDate;
  }
  
  public String getSubmitDate()
  {
    if (this.submitDate != null) {
      return this.submitDate.toString();
    }
    return "";
  }
  
  public void setSubmitDate(DateTime paramDateTime)
  {
    if (this.submitDate == null)
    {
      this.submitDate = new DateTime(this.locale);
      this.submitDate.setFormat(this.datetype);
    }
    this.submitDate.setTime(paramDateTime.getTime());
  }
  
  public void setSubmitDate(String paramString)
  {
    try
    {
      if (this.submitDate == null) {
        this.submitDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.submitDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.submitDate = null;
    }
  }
  
  public DateTime getProcessedOnDateValue()
  {
    return this.processedOnDate;
  }
  
  public String getProcessedOnDate()
  {
    if (this.processedOnDate != null) {
      return this.processedOnDate.toString();
    }
    return "";
  }
  
  public void setProcessedOnDate(DateTime paramDateTime)
  {
    if (this.processedOnDate == null)
    {
      this.processedOnDate = new DateTime(this.locale);
      this.processedOnDate.setFormat(this.datetype);
    }
    this.processedOnDate.setTime(paramDateTime.getTime());
  }
  
  public void setProcessedOnDate(String paramString)
  {
    try
    {
      if (this.processedOnDate == null) {
        this.processedOnDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.processedOnDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.processedOnDate = null;
    }
  }
  
  public DateTime getApprovalDateValue()
  {
    return this.approvalDate;
  }
  
  public String getApprovalDate()
  {
    if (this.approvalDate != null) {
      return this.approvalDate.toString();
    }
    return "";
  }
  
  public void setApprovalDate(DateTime paramDateTime)
  {
    if (this.approvalDate == null)
    {
      this.approvalDate = new DateTime(this.locale);
      this.approvalDate.setFormat(this.datetype);
    }
    this.approvalDate.setTime(paramDateTime.getTime());
  }
  
  public void setApprovalDate(String paramString)
  {
    try
    {
      if (this.approvalDate == null) {
        this.approvalDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.approvalDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.approvalDate = null;
    }
  }
  
  public String getDivisionID()
  {
    return this.divisionID;
  }
  
  public void setDivisionID(String paramString)
  {
    this.divisionID = paramString;
  }
  
  public String getDivisionName()
  {
    return this.divisionName;
  }
  
  public void setDivisionName(String paramString)
  {
    this.divisionName = paramString;
  }
  
  public String getLocationID()
  {
    return this.locationID;
  }
  
  public void setLocationID(String paramString)
  {
    this.locationID = paramString;
  }
  
  public String getLocationName()
  {
    return this.locationName;
  }
  
  public void setLocationName(String paramString)
  {
    this.locationName = paramString;
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public String getStatusName()
  {
    return ResourceUtil.getString("CashConStatus" + this.status, "com.ffusion.beans.cashcon.resources", this.locale);
  }
  
  public String getACHFileName()
  {
    return this.ACHFileName;
  }
  
  public void setACHFileName(String paramString)
  {
    this.ACHFileName = paramString;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("SUBMITDATE")) {
        setSubmitDate(paramString2);
      } else if (paramString1.equals("APPROVAL_DATE")) {
        setApprovalDate(paramString2);
      } else if (paramString1.equals("PROCESSEDONDATE")) {
        setProcessedOnDate(paramString2);
      } else if (paramString1.equals("DIVISION_ID")) {
        this.divisionID = paramString2;
      } else if (paramString1.equals("DIVISION_NAME")) {
        this.divisionName = paramString2;
      } else if (paramString1.equals("COMPANYID")) {
        this.companyID = paramString2;
      } else if (paramString1.equals("LOCATION_ID")) {
        this.locationID = paramString2;
      } else if (paramString1.equals("LOCATION_NAME")) {
        this.locationName = paramString2;
      } else if (paramString1.equals("ACHFILENAME")) {
        this.ACHFileName = paramString2;
      } else if (paramString1.equals("STATUS")) {
        this.status = Integer.parseInt(paramString2);
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception", localException);
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
    XMLHandler.appendBeginTag(localStringBuffer, "CASHCON");
    if (this.submitDate != null) {
      XMLHandler.appendTag(localStringBuffer, "SUBMITDATE", this.submitDate.toXMLFormat());
    }
    if (this.approvalDate != null) {
      XMLHandler.appendTag(localStringBuffer, "APPROVAL_DATE", this.approvalDate.toXMLFormat());
    }
    if (this.processedOnDate != null) {
      XMLHandler.appendTag(localStringBuffer, "PROCESSEDONDATE", this.processedOnDate.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "DIVISION_ID", getDivisionID());
    XMLHandler.appendTag(localStringBuffer, "DIVISION_NAME", getDivisionName());
    XMLHandler.appendTag(localStringBuffer, "COMPANYID", getCompanyID());
    XMLHandler.appendTag(localStringBuffer, "LOCATION_ID", getLocationID());
    XMLHandler.appendTag(localStringBuffer, "LOCATION_NAME", getLocationName());
    XMLHandler.appendTag(localStringBuffer, "ACHFILENAME", getACHFileName());
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "CASHCON");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, CashCon paramCashCon, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "SUBMITDATE", paramCashCon.getSubmitDate(), getSubmitDate(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "APPROVAL_DATE", paramCashCon.getApprovalDate(), getApprovalDate(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PROCESSEDONDATE", paramCashCon.getProcessedOnDate(), getProcessedOnDate(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DIVISION_ID", paramCashCon.getDivisionID(), getDivisionID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DIVISION_NAME", paramCashCon.getDivisionName(), getDivisionName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCATION_ID", paramCashCon.getLocationID(), getLocationID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCATION_NAME", paramCashCon.getLocationName(), getLocationName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACHFILENAME", paramCashCon.getACHFileName(), getACHFileName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", paramCashCon.getStatusName(), getStatusName(), paramString);
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramCashCon, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "CASHCON_ADDED", getID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "CASHCON_CANCELED", getID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, CashCon paramCashCon, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "SUBMITDATE", paramCashCon.getSubmitDateValue() == null ? null : new LocalizableDate(paramCashCon.getSubmitDateValue(), false), getSubmitDateValue() == null ? null : new LocalizableDate(getSubmitDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "APPROVAL_DATE", paramCashCon.getApprovalDateValue() == null ? null : new LocalizableDate(paramCashCon.getApprovalDateValue(), false), getApprovalDateValue() == null ? null : new LocalizableDate(getApprovalDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "DIVISION_ID", paramCashCon.getDivisionID(), getDivisionID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "DIVISION_NAME", paramCashCon.getDivisionName(), getDivisionName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCATION_ID", paramCashCon.getLocationID(), getLocationID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCATION_NAME", paramCashCon.getLocationName(), getLocationName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACHFILENAME", paramCashCon.getACHFileName(), getACHFileName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", new LocalizableString("com.ffusion.beans.cashcon.resources", "CashConStatus" + paramCashCon.getStatus(), null), new LocalizableString("com.ffusion.beans.cashcon.resources", "CashConStatus" + getStatus(), null), paramILocalizable);
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramCashCon, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "CASHCON_ADDED", getID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "CASHCON_CANCELED", getID(), null, paramILocalizable);
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
  
  public String getLastModifier()
  {
    return this.lastModifier;
  }
  
  public void setLastModifier(String paramString)
  {
    this.lastModifier = paramString;
  }
  
  public int getApprovalType()
  {
    return super.getApprovalType();
  }
  
  public int getApprovalSubType()
  {
    return 11;
  }
  
  public String getApprovalSubTypeName()
  {
    String str = null;
    if (getType() == 15) {
      str = "Cash Concentration Deposit Entry";
    } else {
      str = "Cash Concentration Disbursement Request";
    }
    return str;
  }
  
  public Currency getApprovalAmount()
  {
    return super.getApprovalAmount();
  }
  
  public DateTime getApprovalDueDate()
  {
    return getSubmitDateValue();
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
  {
    return getAuditRecord(paramSecureUser, new LocalizableString("dummy", paramString1, null), paramInt, paramString2);
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
      i = paramSecureUser.getPrimaryUserID();
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, paramILocalizable, getTrackingID(), paramInt, paramSecureUser.getBusinessID(), getAmountValue().getAmountValue(), getAmountValue().getCurrencyCode(), getID(), paramString, null, null, null, null, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  public static int mapBPWStatusToInt(String paramString)
  {
    if (paramString.equals("WILLPROCESSON")) {
      return 5;
    }
    if (paramString.equals("POSTEDON")) {
      return 6;
    }
    if (paramString.equals("FAILEDON")) {
      return 10;
    }
    if (paramString.equals("LIMIT_CHECK_FAILED")) {
      return 8;
    }
    if (paramString.equals("LIMIT_REVERT_FAILED")) {
      return 9;
    }
    if (paramString.equals("APPROVAL_PENDING")) {
      return 1;
    }
    if (paramString.equals("APPROVAL_REJECTED")) {
      return 11;
    }
    if (paramString.equals("APPROVAL_FAILED")) {
      return 4;
    }
    if (paramString.equals("CANCELEDON")) {
      return 7;
    }
    return 0;
  }
  
  public static String mapStatusToBPWStatus(int paramInt)
  {
    String str = "UNKNOWN";
    switch (paramInt)
    {
    case 5: 
      str = "WILLPROCESSON";
      break;
    case 6: 
      str = "POSTEDON";
      break;
    case 10: 
      str = "FAILEDON";
      break;
    case 7: 
      str = "CANCELEDON";
      break;
    case 8: 
      str = "LIMIT_CHECK_FAILED";
      break;
    case 9: 
      str = "LIMIT_REVERT_FAILED";
      break;
    case 4: 
      str = "APPROVAL_FAILED";
      break;
    case 1: 
      str = "APPROVAL_PENDING";
      break;
    case 11: 
      str = "APPROVAL_REJECTED";
      break;
    case 2: 
    case 3: 
    default: 
      str = "UNKNOWN";
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashCon
 * JD-Core Version:    0.7.0.1
 */