package com.ffusion.beans.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.Frequencies;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.Localeable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.RoutingNumberUtil;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;

public class ACHBatch
  extends FundsTransaction
  implements Localeable, XMLable, Sortable, Cloneable, Serializable, ACHAccountTypes, Frequencies, ACHStatus, ACHClassCode
{
  private static final String BEAN_NAME = ACHBatch.class.getName();
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.ach.resources";
  public static final String ACH_BATCH_TYPE = "ACHBatchType";
  public static final String TAXFORMID = "TAXFORMID";
  public static final String KEY_ACH_STATUS_PREFIX = "ACHStatus";
  public static final String KEY_ACH_FREQUENCIES_PREFIX = "ACHFrequencies";
  public static final String KEY_ACH_ACCOUNT_TYPES_PREFIX = "ACHAccountType";
  public static final String KEY_ACH_TYPES_PREFIX = "ACHType";
  public static final String KEY_ACH_CATEGORIES_PREFIX = "ACHCategory";
  public static final String KEY_ACH_TEMPLATE_SCOPE_PREFIX = "ACHTemplateScope";
  protected int numberPayments;
  protected int frequency;
  protected String name;
  protected ACHOffsetAccounts offsetAccounts;
  protected ACHOffsetAccount offsetAccount;
  protected String achType = "ACHBatch";
  protected String serviceClassCode;
  protected String debitBatch = "";
  protected String coName;
  protected String headerCompName;
  protected String coDiscretionaryData;
  protected String coID;
  protected String companyID;
  protected String standardEntryClassCode;
  protected String coEntryDesc;
  protected String origDFIID;
  protected Object bpwBalancedBatchObject;
  protected int numberEntries;
  protected ACHEntries entries = new ACHEntries();
  protected String fileId;
  protected int batchType = 1;
  protected DateTime date;
  protected String recID = "";
  protected int status;
  protected DateTime processedOnDate;
  protected TaxForm taxForm;
  protected String taxFormID;
  protected DateTime submitdate;
  protected String category;
  protected String originalID;
  protected Currency totalCreditAmount;
  protected Currency totalDebitAmount;
  protected int totalNumberCredits;
  protected int totalNumberDebits;
  protected boolean canEdit = true;
  protected boolean canDelete = true;
  protected String lastModifier = null;
  
  public ACHBatch()
  {
    this.funds_type = 9;
    this.offsetAccounts = new ACHOffsetAccounts(this.locale);
    this.offsetAccount = new ACHOffsetAccount();
  }
  
  public ACHBatch(String paramString)
  {
    super(paramString);
    this.funds_type = 9;
    this.offsetAccounts = new ACHOffsetAccounts(this.locale);
    this.offsetAccount = new ACHOffsetAccount();
  }
  
  public ACHBatch(Locale paramLocale)
  {
    super(paramLocale);
    this.funds_type = 9;
    this.offsetAccounts = new ACHOffsetAccounts(paramLocale);
    this.offsetAccount = new ACHOffsetAccount();
  }
  
  public Object create()
  {
    ACHEntry localACHEntry = (ACHEntry)this.entries.create();
    localACHEntry.setDateFormat(this.datetype);
    if ((this.debitBatch != null) && (this.debitBatch.equalsIgnoreCase("0"))) {
      localACHEntry.setAmountIsDebit(false);
    }
    return localACHEntry;
  }
  
  public Object createNoAdd()
  {
    ACHEntry localACHEntry = (ACHEntry)this.entries.createNoAdd();
    localACHEntry.setDateFormat(this.datetype);
    if ((this.debitBatch != null) && (this.debitBatch.equalsIgnoreCase("0"))) {
      localACHEntry.setAmountIsDebit(false);
    }
    return localACHEntry;
  }
  
  public boolean add(Object paramObject)
  {
    ACHEntry localACHEntry = (ACHEntry)paramObject;
    localACHEntry.setLocale(this.locale);
    return this.entries.add(localACHEntry);
  }
  
  public String getCanEdit()
  {
    return "" + this.canEdit;
  }
  
  public void setCanEdit(String paramString)
  {
    this.canEdit = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCanDelete()
  {
    return "" + this.canDelete;
  }
  
  public void setCanDelete(String paramString)
  {
    this.canDelete = Boolean.valueOf(paramString).booleanValue();
  }
  
  public ACHEntries getACHEntries()
  {
    return this.entries;
  }
  
  public void setACHEntries(ACHEntries paramACHEntries)
  {
    this.entries = paramACHEntries;
  }
  
  public ACHOffsetAccounts getOffsetAccounts()
  {
    return this.offsetAccounts;
  }
  
  public ACHOffsetAccount getOffsetAccount()
  {
    return this.offsetAccount;
  }
  
  public String getOffsetAccountID()
  {
    return getOffsetAccount().getID();
  }
  
  public void setOffsetAccountID(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      getOffsetAccount().setID(paramString);
    }
  }
  
  public String getOffsetAccountType()
  {
    return getOffsetAccount().getType();
  }
  
  public int getOffsetAccountTypeValue()
  {
    return getOffsetAccount().getTypeValue();
  }
  
  public void setOffsetAccountType(String paramString)
  {
    getOffsetAccount().setType(paramString);
  }
  
  public void setOffsetAccountType(int paramInt)
  {
    getOffsetAccount().setType(paramInt);
  }
  
  public String getOffsetAccountBankID()
  {
    return getOffsetAccount().getRoutingNum();
  }
  
  public void setOffsetAccountBankID(String paramString)
  {
    getOffsetAccount().setRoutingNum(paramString);
  }
  
  public String getOffsetAccountNumber()
  {
    return getOffsetAccount().getNumber();
  }
  
  public void setOffsetAccountNumber(String paramString)
  {
    getOffsetAccount().setNumber(paramString);
  }
  
  public String getOffsetAccountName()
  {
    return getOffsetAccount().getNickName();
  }
  
  public void setOffsetAccountName(String paramString)
  {
    getOffsetAccount().setNickName(paramString);
  }
  
  public String getCategory()
  {
    return this.category;
  }
  
  public void setCategory(String paramString)
  {
    this.category = paramString;
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
    return null;
  }
  
  public void setProcessedOnDate(DateTime paramDateTime)
  {
    this.processedOnDate = paramDateTime;
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
  
  public DateTime getDateValue()
  {
    return this.date;
  }
  
  public String getDate()
  {
    if (this.date != null) {
      return this.date.toString();
    }
    return null;
  }
  
  public void setDate(String paramString)
  {
    try
    {
      if (this.date == null) {
        this.date = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.date.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.date = null;
    }
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.date = paramDateTime;
  }
  
  public DateTime getSubmitDateValue()
  {
    return this.submitdate;
  }
  
  public String getSubmitDate()
  {
    if (this.submitdate != null) {
      return this.submitdate.toString();
    }
    return null;
  }
  
  public void setSubmitDate(String paramString)
  {
    try
    {
      if (this.submitdate == null) {
        this.submitdate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.submitdate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.submitdate = null;
    }
  }
  
  public void setSubmitDate(DateTime paramDateTime)
  {
    this.submitdate = paramDateTime;
  }
  
  public TaxForm getTaxForm()
  {
    return this.taxForm;
  }
  
  public void setTaxForm(TaxForm paramTaxForm)
  {
    this.taxForm = paramTaxForm;
    if (paramTaxForm != null) {
      this.taxFormID = paramTaxForm.getID();
    }
  }
  
  public String getTaxFormID()
  {
    return this.taxFormID;
  }
  
  public void setTaxFormID(String paramString)
  {
    this.taxFormID = paramString;
  }
  
  public String getRecID()
  {
    return this.recID;
  }
  
  public void setRecID(String paramString)
  {
    this.recID = paramString;
  }
  
  public String getDebitBatch()
  {
    return this.debitBatch;
  }
  
  public void setDebitBatch(String paramString)
  {
    this.debitBatch = paramString;
  }
  
  public Object getBpwBalancedBatchObject()
  {
    return this.bpwBalancedBatchObject;
  }
  
  public void setBpwBalancedBatchObject(Object paramObject)
  {
    this.bpwBalancedBatchObject = paramObject;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public void setStatus(String paramString)
  {
    try
    {
      this.status = Integer.parseInt(paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public int getStatusValue()
  {
    return this.status;
  }
  
  public String getStatus()
  {
    return ResourceUtil.getString("ACHStatus" + this.status, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public int size()
  {
    return this.entries.size();
  }
  
  public int getApprovalType()
  {
    return super.getApprovalType();
  }
  
  public int getApprovalSubType()
  {
    switch (getType())
    {
    case 9: 
      return 7;
    case 10: 
      return 8;
    case 12: 
      return 9;
    case 13: 
      return 12;
    case 17: 
      return 13;
    case 18: 
      return 14;
    }
    return 7;
  }
  
  public String getApprovalSubTypeName()
  {
    switch (getType())
    {
    case 9: 
      return "ACH Batch";
    case 10: 
      return "Recurring ACH Batch";
    case 12: 
      return "Tax Payment";
    case 13: 
      return "Recurring Tax Payment";
    case 17: 
      return "Child Support Payment";
    case 18: 
      return "Recurring Child Support Payment";
    }
    return "ACH Batch";
  }
  
  public Currency getApprovalAmount()
  {
    fixupEntriesAmount();
    return super.getApprovalAmount();
  }
  
  public DateTime getApprovalDueDate()
  {
    return getDateValue();
  }
  
  public String getSECEntitlementName()
  {
    String str = null;
    if ((getTaxForm() != null) || (getTaxFormID() != null)) {
      if (getTaxForm() != null)
      {
        if (getTaxForm().getAddendaFormat().equals("DED")) {
          str = "CCD + DED";
        } else if (getTaxForm().getAddendaFormat().equals("TXP")) {
          str = "CCD + TXP";
        }
      }
      else {
        switch (getType())
        {
        case 12: 
        case 13: 
          str = "CCD + TXP";
          break;
        case 17: 
        case 18: 
          str = "CCD + DED";
        }
      }
    }
    if (str == null) {
      str = getStandardEntryClassCode();
    }
    return str;
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
  
  public Object get(int paramInt)
  {
    return this.entries.get(paramInt);
  }
  
  public void set(ACHBatch paramACHBatch)
  {
    if ((paramACHBatch == null) || (paramACHBatch == this)) {
      return;
    }
    this.entries.clear();
    this.id = paramACHBatch.getID();
    this.name = paramACHBatch.getName();
    this.numberPayments = paramACHBatch.getNumberPaymentsValue();
    this.frequency = paramACHBatch.getFrequencyValue();
    this.templateScope = paramACHBatch.getBatchScope();
    this.templateID = paramACHBatch.getTemplateID();
    this.templateName = paramACHBatch.getTemplateName();
    this.originalID = paramACHBatch.getOriginalID();
    this.offsetAccount.set(paramACHBatch.getOffsetAccount());
    if (paramACHBatch.offsetAccounts.size() > 0)
    {
      this.offsetAccounts.clear();
      for (int i = 0; i < paramACHBatch.offsetAccounts.size(); i++)
      {
        ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)paramACHBatch.offsetAccounts.get(i);
        this.offsetAccounts.add(localACHOffsetAccount.clone());
      }
    }
    this.achType = paramACHBatch.getACHType();
    this.serviceClassCode = paramACHBatch.serviceClassCode;
    this.debitBatch = paramACHBatch.debitBatch;
    this.coName = paramACHBatch.coName;
    this.headerCompName = paramACHBatch.headerCompName;
    this.coDiscretionaryData = paramACHBatch.coDiscretionaryData;
    this.coID = paramACHBatch.coID;
    this.companyID = paramACHBatch.companyID;
    this.standardEntryClassCode = paramACHBatch.standardEntryClassCode;
    this.coEntryDesc = paramACHBatch.coEntryDesc;
    this.origDFIID = paramACHBatch.origDFIID;
    this.bpwBalancedBatchObject = paramACHBatch.getBpwBalancedBatchObject();
    this.numberEntries = paramACHBatch.numberEntries;
    this.fileId = paramACHBatch.getFileId();
    this.batchType = paramACHBatch.getBatchTypeValue();
    if (paramACHBatch.getDateValue() != null) {
      this.date = ((DateTime)paramACHBatch.getDateValue().clone());
    } else {
      this.date = null;
    }
    this.recID = paramACHBatch.getRecID();
    this.status = paramACHBatch.getStatusValue();
    if (paramACHBatch.getProcessedOnDateValue() != null) {
      this.processedOnDate = ((DateTime)paramACHBatch.getProcessedOnDateValue().clone());
    } else {
      this.processedOnDate = null;
    }
    if (paramACHBatch.getTaxForm() != null) {
      this.taxForm = ((TaxForm)paramACHBatch.getTaxForm().clone());
    } else {
      this.taxForm = null;
    }
    this.taxFormID = paramACHBatch.getTaxFormID();
    if (paramACHBatch.getSubmitDateValue() != null) {
      this.submitdate = ((DateTime)paramACHBatch.getSubmitDateValue().clone());
    } else {
      this.submitdate = null;
    }
    this.category = paramACHBatch.category;
    if (paramACHBatch.getTotalCreditAmountValue() != null) {
      this.totalCreditAmount = ((Currency)paramACHBatch.getTotalCreditAmountValue().clone());
    } else {
      this.totalCreditAmount = null;
    }
    if (paramACHBatch.getTotalDebitAmountValue() != null) {
      this.totalDebitAmount = ((Currency)paramACHBatch.getTotalDebitAmountValue().clone());
    } else {
      this.totalDebitAmount = null;
    }
    this.totalNumberCredits = paramACHBatch.getTotalNumberCredits();
    this.totalNumberDebits = paramACHBatch.getTotalNumberDebits();
    ACHEntries localACHEntries = paramACHBatch.getACHEntries();
    for (int j = 0; j < localACHEntries.size(); j++) {
      this.entries.add(((ACHEntry)localACHEntries.get(j)).clone());
    }
    super.set(paramACHBatch);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ACHTYPE"))
      {
        this.achType = paramString2;
      }
      else if (paramString1.equalsIgnoreCase("SUBMITDATE"))
      {
        if (this.submitdate == null)
        {
          this.submitdate = new DateTime(this.locale);
          this.submitdate.setFormat(this.datetype);
        }
        this.submitdate.fromXMLFormat(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("DATE"))
      {
        if (this.date == null)
        {
          this.date = new DateTime(this.locale);
          this.date.setFormat(this.datetype);
        }
        this.date.fromXMLFormat(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("PROCESSEDONDATE"))
      {
        if (this.processedOnDate == null)
        {
          this.processedOnDate = new DateTime(this.locale);
          this.processedOnDate.setFormat(this.datetype);
        }
        this.processedOnDate.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("ID"))
      {
        this.id = paramString2;
      }
      else if (paramString1.equals("BATCHTYPE"))
      {
        setBatchType(paramString2);
      }
      else if (paramString1.equals("SCOPE"))
      {
        setBatchScope(paramString2);
      }
      else if (paramString1.equals("RECID"))
      {
        setRecID(paramString2);
      }
      else if (paramString1.equals("TEMPLATE_ID"))
      {
        setTemplateID(paramString2);
      }
      else if (paramString1.equals("TAXFORMID"))
      {
        setTaxFormID(paramString2);
      }
      else if (paramString1.equals("TEMPLATE_NAME"))
      {
        setTemplateName(paramString2);
      }
      else if (paramString1.equals("ORIGINAL_ID"))
      {
        setOriginalID(paramString2);
      }
      else if (paramString1.equals("OFFSETACCOUNTTYPE"))
      {
        setOffsetAccountType(paramString2);
      }
      else if (paramString1.equals("OFFSETACCOUNTBANKID"))
      {
        setOffsetAccountBankID(paramString2);
      }
      else if (paramString1.equals("OFFSETACCOUNTID"))
      {
        setOffsetAccountID(paramString2);
      }
      else if (paramString1.equals("OFFSETACCOUNTNAME"))
      {
        setOffsetAccountName(paramString2);
      }
      else if (paramString1.equals("OFFSETACCOUNTNUMBER"))
      {
        setOffsetAccountNumber(paramString2);
      }
      else if (paramString1.equals("NAME"))
      {
        this.name = paramString2;
      }
      else if (paramString1.equals("STATUS"))
      {
        this.status = Integer.parseInt(paramString2);
      }
      else if (paramString1.equals("CATEGORY"))
      {
        this.category = paramString2;
      }
      else if (paramString1.equals("SERVICECLASSCODE"))
      {
        this.serviceClassCode = paramString2;
      }
      else if (paramString1.equals("DEBITBATCH"))
      {
        this.debitBatch = paramString2;
      }
      else if (paramString1.equals("CONAME"))
      {
        this.coName = paramString2;
      }
      else if (paramString1.equals("HEADERCOMPNAME"))
      {
        this.headerCompName = paramString2;
      }
      else if (paramString1.equals("CODISCRETIONARYDATA"))
      {
        this.coDiscretionaryData = paramString2;
      }
      else if (paramString1.equals("COID"))
      {
        this.coID = paramString2;
      }
      else if (paramString1.equals("COMPANYID"))
      {
        this.companyID = paramString2;
      }
      else if (paramString1.equals("STANDARDENTRYCLASSCODE"))
      {
        this.standardEntryClassCode = paramString2;
      }
      else if (paramString1.equals("COENTRYDESC"))
      {
        this.coEntryDesc = paramString2;
      }
      else if (paramString1.equals("ORIGDFIID"))
      {
        this.origDFIID = paramString2;
      }
      else if (paramString1.equalsIgnoreCase("NUMBERPAYMENTS"))
      {
        this.numberPayments = Integer.parseInt(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("FREQUENCY"))
      {
        this.frequency = Integer.parseInt(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("NUMBERENTRIES"))
      {
        this.numberEntries = Integer.parseInt(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("TOTAL_CREDIT"))
      {
        setTotalCreditAmount(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("TOTAL_DEBIT"))
      {
        setTotalDebitAmount(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("TOTAL_NUM_CREDIT"))
      {
        setTotalNumberCredits(paramString2);
      }
      else if (paramString1.equalsIgnoreCase("TOTAL_NUM_DEBIT"))
      {
        setTotalNumberDebits(paramString2);
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
  
  public void addACHPayees(ACHPayees paramACHPayees)
  {
    for (int i = 0; i < this.entries.size(); i++)
    {
      ACHEntry localACHEntry = (ACHEntry)this.entries.get(i);
      String str = localACHEntry.getAchPayeeID();
      if ((str != null) && (str.length() > 0))
      {
        ACHPayee localACHPayee = paramACHPayees.getByID(str);
        if (localACHPayee != null) {
          localACHEntry.setAchPayee(localACHPayee);
        }
      }
    }
  }
  
  public Object clone()
  {
    try
    {
      ACHBatch localACHBatch = (ACHBatch)super.clone();
      ACHEntries localACHEntries1 = new ACHEntries();
      ACHEntries localACHEntries2 = localACHBatch.getACHEntries();
      localACHBatch.setACHEntries(localACHEntries1);
      for (int i = 0; i < localACHEntries2.size(); i++) {
        localACHEntries1.add(i, ((ACHEntry)localACHEntries2.get(i)).clone());
      }
      localACHBatch.getOffsetAccount().set(this.offsetAccount);
      if (this.offsetAccounts.size() > 0)
      {
        localACHBatch.getOffsetAccounts().clear();
        ACHOffsetAccounts localACHOffsetAccounts = localACHBatch.getOffsetAccounts();
        for (int j = 0; j < this.offsetAccounts.size(); j++)
        {
          ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)this.offsetAccounts.get(j);
          localACHOffsetAccounts.add(localACHOffsetAccount.clone());
        }
      }
      if (this.date != null) {
        localACHBatch.setDate((DateTime)this.date.clone());
      }
      if (this.submitdate != null) {
        localACHBatch.setSubmitDate((DateTime)this.submitdate.clone());
      }
      if (this.processedOnDate != null) {
        localACHBatch.setProcessedOnDate((DateTime)this.processedOnDate.clone());
      }
      if (this.totalCreditAmount != null) {
        localACHBatch.setTotalCreditAmount((Currency)this.totalCreditAmount.clone());
      }
      if (this.totalDebitAmount != null) {
        localACHBatch.setTotalDebitAmount((Currency)this.totalDebitAmount.clone());
      }
      if (this.taxForm != null) {
        localACHBatch.setTaxForm((TaxForm)this.taxForm.clone());
      }
      return localACHBatch;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public ACHEntry getByID(String paramString)
  {
    return (ACHEntry)this.entries.getFirstByFilter("ID=" + paramString);
  }
  
  public ACHEntry getByRecID(String paramString)
  {
    return (ACHEntry)this.entries.getFirstByFilter("RECID=" + paramString);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    int i = 1;
    i = compare(paramObject, "NAME");
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ACHBatch localACHBatch = (ACHBatch)paramObject;
    int i = 1;
    if ((paramString.equals("OFFSETACCOUNTBANKID")) && (getOffsetAccountBankID() != null) && (localACHBatch.getOffsetAccountBankID() != null))
    {
      i = numStringCompare(getOffsetAccountBankID(), localACHBatch.getOffsetAccountBankID());
    }
    else if ((paramString.equals("OFFSETACCOUNTNAME")) && (getOffsetAccountName() != null) && (localACHBatch.getOffsetAccountName() != null))
    {
      i = getOffsetAccountName().compareTo(localACHBatch.getOffsetAccountName());
    }
    else if (paramString.equals("OFFSETACCOUNTTYPE"))
    {
      i = getOffsetAccountTypeValue() - localACHBatch.getOffsetAccountTypeValue();
    }
    else if (paramString.equals("OFFSETACCOUNTTYPESTRING"))
    {
      i = getOffsetAccountType().compareTo(localACHBatch.getOffsetAccountType());
    }
    else if ((paramString.equals("OFFSETACCOUNTNUMBER")) && (getOffsetAccountNumber() != null) && (localACHBatch.getOffsetAccountNumber() != null))
    {
      i = numStringCompare(getOffsetAccountNumber(), localACHBatch.getOffsetAccountNumber());
    }
    else if ((paramString.equals("ACHTYPE")) && (this.achType != null) && (localACHBatch.getACHType() != null))
    {
      i = this.achType.compareTo(localACHBatch.getACHType());
    }
    else if ((paramString.equals("TEMPLATE_ID")) && (this.templateID != null) && (localACHBatch.getTemplateID() != null))
    {
      i = this.templateID.compareTo(localACHBatch.getTemplateID());
    }
    else if ((paramString.equals("ORIGINAL_ID")) && (this.originalID != null) && (localACHBatch.getOriginalID() != null))
    {
      i = this.originalID.compareTo(localACHBatch.getOriginalID());
    }
    else if ((paramString.equals("TEMPLATE_NAME")) && (this.templateName != null) && (localACHBatch.getTemplateName() != null))
    {
      i = this.templateName.compareTo(localACHBatch.getTemplateName());
    }
    else if ((paramString.equals("SCOPE")) && (this.templateScope != null) && (localACHBatch.getBatchScope() != null))
    {
      i = this.templateScope.compareTo(localACHBatch.getBatchScope());
    }
    else if ((paramString.equals("STANDARDENTRYCLASSCODE")) && (this.standardEntryClassCode != null) && (localACHBatch.getStandardEntryClassCode() != null))
    {
      String str1 = this.standardEntryClassCode + this.debitBatch;
      String str2 = localACHBatch.getStandardEntryClassCode() + localACHBatch.getDebitBatch();
      i = str1.compareTo(str2);
    }
    else if (paramString.equals("NUMBERPAYMENTS"))
    {
      i = getNumberPaymentsValue() - localACHBatch.getNumberPaymentsValue();
    }
    else if ((paramString.equals("CATEGORY")) && (this.category != null) && (localACHBatch.getCategory() != null))
    {
      i = this.category.compareTo(localACHBatch.getCategory());
    }
    else if ((paramString.equals("SUBMITDATE")) && (this.submitdate != null) && (localACHBatch.getSubmitDateValue() != null))
    {
      i = getSubmitDateValue().compare(localACHBatch.getSubmitDateValue());
    }
    else if (paramString.equals("REMAININGPAYMENTS"))
    {
      i = getRemainingPaymentsValue() - localACHBatch.getRemainingPaymentsValue();
    }
    else if (paramString.equals("FREQUENCY"))
    {
      i = getFrequencyValue() - localACHBatch.getFrequencyValue();
    }
    else if (paramString.equals("FREQUENCYSTRING"))
    {
      i = getFrequency().compareTo(localACHBatch.getFrequency());
    }
    else if ((paramString.equals("STATUS")) || (paramString.equals("STATUSSTRING")))
    {
      i = getStatus().compareTo(localACHBatch.getStatus());
    }
    else if ((paramString.equals("NEXTDATE")) && (getNextDateValue() != null) && (localACHBatch.getNextDateValue() != null))
    {
      i = getNextDateValue().equals(localACHBatch.getNextDateValue()) ? 0 : getNextDateValue().before(localACHBatch.getNextDateValue()) ? -1 : 1;
    }
    else if ((paramString.equals("DATE")) && (getDateValue() != null) && (localACHBatch.getDateValue() != null))
    {
      i = getDateValue().compare(localACHBatch.getDateValue());
    }
    else if ((paramString.equals("PROCESSEDONDATE")) && (getProcessedOnDateValue() != null) && (localACHBatch.getProcessedOnDateValue() != null))
    {
      i = getProcessedOnDateValue().compare(localACHBatch.getProcessedOnDateValue());
    }
    else if ((paramString.equals("NAME")) && (this.name != null) && (localACHBatch.getName() != null))
    {
      i = this.name.compareToIgnoreCase(localACHBatch.getName());
    }
    else if ((paramString.equals("CONAME")) && (this.coName != null) && (localACHBatch.getCoName() != null))
    {
      i = this.coName.compareToIgnoreCase(localACHBatch.getCoName());
    }
    else if (paramString.equals("NUMBERENTRIES"))
    {
      i = getNumberEntriesValue() - localACHBatch.getNumberEntriesValue();
    }
    else if ((paramString.equals("TOTAL_CREDIT")) && (this.totalCreditAmount != null) && (localACHBatch.getTotalCreditAmountValue() != null))
    {
      i = this.totalCreditAmount.compareTo(localACHBatch.getTotalCreditAmountValue());
    }
    else if ((paramString.equals("TOTAL_DEBIT")) && (this.totalDebitAmount != null) && (localACHBatch.getTotalDebitAmountValue() != null))
    {
      i = this.totalDebitAmount.compareTo(localACHBatch.getTotalDebitAmountValue());
    }
    else if ((paramString.equals("COMPANYID")) && (this.companyID != null) && (localACHBatch.getCompanyID() != null))
    {
      i = this.companyID.compareToIgnoreCase(localACHBatch.getCompanyID());
    }
    else
    {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("NAME")) {
      return isFilterable(getName(), paramString2, paramString3);
    }
    if ((paramString1.equals("TEMPLATE_ID")) && (getTemplateID() != null)) {
      return isFilterable(getTemplateID(), paramString2, paramString3);
    }
    if ((paramString1.equals("ORIGINAL_ID")) && (getOriginalID() != null)) {
      return isFilterable(getOriginalID(), paramString2, paramString3);
    }
    if ((paramString1.equals("TEMPLATE_NAME")) && (getTemplateName() != null)) {
      return isFilterable(getTemplateName(), paramString2, paramString3);
    }
    if ((paramString1.equals("SCOPE")) && (getBatchScope() != null)) {
      return isFilterable(getBatchScope(), paramString2, paramString3);
    }
    if (paramString1.equals("CONAME")) {
      return isFilterable(getCoName(), paramString2, paramString3);
    }
    if (paramString1.equals("HEADERCOMPNAME")) {
      return isFilterable(getHeaderCompName(), paramString2, paramString3);
    }
    if (paramString1.equals("COID")) {
      return isFilterable(getCoID(), paramString2, paramString3);
    }
    if (paramString1.equals("COMPANYID")) {
      return isFilterable(getCompanyID(), paramString2, paramString3);
    }
    if (paramString1.equals("OFFSETACCOUNTBANKID")) {
      return isFilterable(getOffsetAccountBankID(), paramString2, paramString3);
    }
    if (paramString1.equals("OFFSETACCOUNTID")) {
      return isFilterable(getOffsetAccountID(), paramString2, paramString3);
    }
    if (paramString1.equals("OFFSETACCOUNTNAME")) {
      return isFilterable(getOffsetAccountName(), paramString2, paramString3);
    }
    if (paramString1.equals("OFFSETACCOUNTTYPE")) {
      return isFilterable(getOffsetAccountType(), paramString2, paramString3);
    }
    if (paramString1.equals("OFFSETACCOUNTNUMBER")) {
      return isFilterable(getOffsetAccountNumber(), paramString2, paramString3);
    }
    if (paramString1.equals("CATEGORY")) {
      return isFilterable(getCategory(), paramString2, paramString3);
    }
    if (paramString1.equals("ACHTYPE")) {
      return isFilterable(getACHType(), paramString2, paramString3);
    }
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatusValue()), paramString2, paramString3);
    }
    if (paramString1.equals("STANDARDENTRYCLASSCODE"))
    {
      String str = getStandardEntryClassCode();
      if ((paramString3 != null) && (paramString3.length() > 3)) {
        str = str + getDebitBatch();
      }
      return isFilterable(str, paramString2, paramString3);
    }
    if (paramString1.equals("NUMBERPAYMENTS")) {
      return isFilterable(getNumberPayments(), paramString2, paramString3);
    }
    if (paramString1.equals("REMAININGPAYMENTS")) {
      return isFilterable(getRemainingPayments(), paramString2, paramString3);
    }
    if (paramString1.equals("FREQUENCY")) {
      return isFilterable(getFrequency(), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTAL_CREDIT")) && (this.totalCreditAmount != null)) {
      return this.totalCreditAmount.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_DEBIT")) && (this.totalDebitAmount != null)) {
      return this.totalDebitAmount.isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.totalCreditAmount != null) {
      this.totalCreditAmount.setLocale(paramLocale);
    }
    if (this.totalDebitAmount != null) {
      this.totalDebitAmount.setLocale(paramLocale);
    }
    if (this.entries != null)
    {
      Iterator localIterator = this.entries.iterator();
      while (localIterator.hasNext())
      {
        ACHEntry localACHEntry = (ACHEntry)localIterator.next();
        localACHEntry.setLocale(this.locale);
      }
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.date != null) {
      this.date.setFormat(this.datetype);
    }
    if (this.processedOnDate != null) {
      this.processedOnDate.setFormat(this.datetype);
    }
    if (this.submitdate != null) {
      this.submitdate.setFormat(this.datetype);
    }
    if (this.entries != null)
    {
      Iterator localIterator = this.entries.iterator();
      while (localIterator.hasNext())
      {
        ACHEntry localACHEntry = (ACHEntry)localIterator.next();
        localACHEntry.setDateFormat(this.datetype);
      }
    }
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setACHType(String paramString)
  {
    this.achType = paramString;
    if (this.achType != null) {
      if (this.achType.toUpperCase().startsWith("TAX")) {
        setType(12);
      } else if (this.achType.toUpperCase().startsWith("CHILD")) {
        setType(17);
      } else {
        setType(9);
      }
    }
  }
  
  public String getACHType()
  {
    return this.achType;
  }
  
  public void setServiceClassCode(String paramString)
  {
    this.serviceClassCode = paramString;
  }
  
  public String getServiceClassCode()
  {
    return this.serviceClassCode;
  }
  
  public void setCoName(String paramString)
  {
    this.coName = paramString;
  }
  
  public String getCoName()
  {
    return this.coName;
  }
  
  public void setHeaderCompName(String paramString)
  {
    this.headerCompName = paramString;
  }
  
  public String getHeaderCompName()
  {
    return this.headerCompName;
  }
  
  public void setCoDiscretionaryData(String paramString)
  {
    this.coDiscretionaryData = paramString;
  }
  
  public String getCoDiscretionaryData()
  {
    return this.coDiscretionaryData;
  }
  
  public void setCoID(String paramString)
  {
    this.coID = paramString;
  }
  
  public String getCoID()
  {
    return this.coID;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setStandardEntryClassCode(String paramString)
  {
    if (paramString != null)
    {
      paramString = paramString.toUpperCase();
      if (paramString.length() > 3)
      {
        setDebitBatch(paramString.substring(3));
        paramString = paramString.substring(0, 3);
      }
      else
      {
        setDebitBatch("");
      }
      for (int i = 0; i < ACHClassCode.ACHClassCodes.length; i++)
      {
        String str = ResourceUtil.getString("ACHClassCode" + ACHClassCode.ACHClassCodes[i], "com.ffusion.beans.ach.resources", this.locale);
        if (str.equals(paramString))
        {
          this.standardEntryClassCode = paramString;
          break;
        }
      }
    }
  }
  
  public String getStandardEntryClassCode()
  {
    return this.standardEntryClassCode;
  }
  
  public int getStandardEntryClassCodeValue()
  {
    for (int i = 0; i < ACHClassCode.ACHClassCodes.length; i++)
    {
      String str = ResourceUtil.getString("ACHClassCode" + ACHClassCode.ACHClassCodes[i], "com.ffusion.beans.ach.resources", this.locale);
      if (str.equals(this.standardEntryClassCode)) {
        return ACHClassCode.ACHClassCodes[i];
      }
    }
    return 0;
  }
  
  public String getStandardEntryClassCodeDesc()
  {
    String str = ResourceUtil.getString("ACHClassCodeDesc" + getStandardEntryClassCodeValue(), "com.ffusion.beans.ach.resources", this.locale);
    return str;
  }
  
  public void setCoEntryDesc(String paramString)
  {
    this.coEntryDesc = paramString;
  }
  
  public String getCoEntryDesc()
  {
    return this.coEntryDesc;
  }
  
  public void setOrigDFIID(String paramString)
  {
    this.origDFIID = paramString;
  }
  
  public String getOrigDFIID()
  {
    return this.origDFIID;
  }
  
  public String getFileId()
  {
    return this.fileId;
  }
  
  public void setFileId(String paramString)
  {
    this.fileId = paramString;
  }
  
  public String getOriginalID()
  {
    return this.originalID;
  }
  
  public void setOriginalID(String paramString)
  {
    this.originalID = paramString;
  }
  
  public String getBatchScope()
  {
    return this.templateScope;
  }
  
  public void setBatchScope(String paramString)
  {
    this.templateScope = paramString;
  }
  
  public int getBatchTypeValue()
  {
    return this.batchType;
  }
  
  public String getBatchType()
  {
    return ResourceUtil.getString("ACHBatchType" + this.batchType, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public void setBatchType(int paramInt)
  {
    this.batchType = paramInt;
  }
  
  public void setBatchType(String paramString)
  {
    for (int i = 1; i < 4; i++)
    {
      String str = ResourceUtil.getString("ACHBatchType" + i, "com.ffusion.beans.ach.resources", this.locale);
      if (str.equalsIgnoreCase(paramString))
      {
        setBatchType(i);
        break;
      }
    }
  }
  
  public void setBatchTypeValue(String paramString)
  {
    try
    {
      setBatchType(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setBatchType(1);
    }
  }
  
  public void setNumberPayments(String paramString)
  {
    try
    {
      setNumberPayments(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setNumberPayments(0);
    }
  }
  
  public void setNumberPayments(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    this.numberPayments = paramInt;
  }
  
  public int getNumberPaymentsValue()
  {
    return this.numberPayments;
  }
  
  public String getNumberPayments()
  {
    return String.valueOf(this.numberPayments);
  }
  
  public int getRemainingPaymentsValue()
  {
    return getEstRemainingPayments(new GregorianCalendar());
  }
  
  public String getRemainingPayments()
  {
    return String.valueOf(getRemainingPaymentsValue());
  }
  
  public void setFrequencyValue(String paramString)
  {
    try
    {
      setFrequency(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setFrequency(4);
    }
  }
  
  public void setFrequency(String paramString)
  {
    for (int i = 0; i < 10; i++)
    {
      String str = ResourceUtil.getString("ACHFrequencies" + i, "com.ffusion.beans.ach.resources", this.locale);
      if (str.equalsIgnoreCase(paramString))
      {
        setFrequency(i);
        break;
      }
    }
  }
  
  public void setFrequency(int paramInt)
  {
    this.frequency = paramInt;
  }
  
  public int getFrequencyValue()
  {
    return this.frequency;
  }
  
  public String getFrequency()
  {
    return ResourceUtil.getString("ACHFrequencies" + this.frequency, "com.ffusion.beans.ach.resources", this.locale);
  }
  
  public Calendar getNextDateValue()
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    Object localObject = getDateValue();
    Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
    int i = this.numberPayments;
    localCalendar.set(localGregorianCalendar.get(1), localGregorianCalendar.get(2), localGregorianCalendar.get(5));
    while ((((Calendar)localObject).before(localCalendar)) && (i > 0))
    {
      localObject = getEstNextDate((Calendar)localObject);
      i--;
    }
    return localObject;
  }
  
  public String getNextDate()
  {
    DateTime localDateTime = new DateTime(getNextDateValue(), this.locale);
    localDateTime.setFormat(this.datetype);
    return localDateTime.toString();
  }
  
  public Calendar getEstNextDate(Calendar paramCalendar)
  {
    Calendar localCalendar = (Calendar)paramCalendar.clone();
    switch (this.frequency)
    {
    case 1: 
      localCalendar.add(5, 7);
      break;
    case 2: 
      localCalendar.add(5, 14);
      break;
    case 5: 
      localCalendar.add(5, 28);
      break;
    case 4: 
      localCalendar.add(2, 1);
      break;
    case 6: 
      localCalendar.add(2, 2);
      break;
    case 7: 
      localCalendar.add(2, 3);
      break;
    case 9: 
      localCalendar.add(1, 1);
      break;
    case 8: 
      localCalendar.add(2, 6);
      break;
    case 3: 
      int i = localCalendar.get(5);
      if (i == 16)
      {
        localCalendar.set(5, 1);
        localCalendar.add(2, 1);
      }
      else if (i == 1)
      {
        localCalendar.set(5, 16);
      }
      else
      {
        localCalendar.add(5, 15);
      }
      break;
    }
    return localCalendar;
  }
  
  public int getEstRemainingPayments(Calendar paramCalendar)
  {
    Object localObject = getDateValue();
    Calendar localCalendar = (Calendar)((Calendar)localObject).clone();
    int i = this.numberPayments;
    localCalendar.set(paramCalendar.get(1), paramCalendar.get(2), paramCalendar.get(5));
    while ((((Calendar)localObject).before(localCalendar)) && (i > 0))
    {
      localObject = getEstNextDate((Calendar)localObject);
      i--;
    }
    return i;
  }
  
  protected void getInquireComment(StringBuffer paramStringBuffer)
  {
    super.getInquireComment(paramStringBuffer);
    String str1 = getDate();
    if (str1 != null) {
      paramStringBuffer.append("Date is ").append(str1).append(".\n");
    }
    if (getName() != null) {
      paramStringBuffer.append("Name is ").append(getName()).append(".\n");
    }
    paramStringBuffer.append("Number of entries is ").append(getNumberEntriesValue()).append(".\n");
    String str2 = getFrequency();
    if ((str2 == null) || (str2.toString().length() == 0)) {
      paramStringBuffer.append("No Frequency.\n");
    } else {
      paramStringBuffer.append("Frequency is ").append(str2).append(".\n");
    }
    if (getStatus() == null) {
      paramStringBuffer.append("No status.\n");
    } else {
      paramStringBuffer.append("Status is ").append(getStatus()).append(".\n");
    }
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHBATCH");
    XMLHandler.appendTag(localStringBuffer, "NAME", this.name);
    XMLHandler.appendTag(localStringBuffer, "BATCHTYPE", getBatchType());
    if (this.offsetAccount != null) {
      localStringBuffer.append(this.offsetAccount.getXML());
    }
    if (this.offsetAccounts.size() > 0) {
      localStringBuffer.append(this.offsetAccounts.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "RECID", getRecID());
    if (this.date != null) {
      XMLHandler.appendTag(localStringBuffer, "DATE", this.date.toXMLFormat());
    }
    if (this.processedOnDate != null) {
      XMLHandler.appendTag(localStringBuffer, "PROCESSEDONDATE", this.processedOnDate.toXMLFormat());
    }
    if (this.submitdate != null) {
      XMLHandler.appendTag(localStringBuffer, "SUBMITDATE", this.submitdate.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "ACHTYPE", this.achType);
    if (this.entries != null) {
      localStringBuffer.append(this.entries.getXML());
    }
    if (this.taxForm != null) {
      localStringBuffer.append(this.taxForm.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "TAXFORMID", this.taxFormID);
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    XMLHandler.appendTag(localStringBuffer, "SERVICECLASSCODE", this.serviceClassCode);
    XMLHandler.appendTag(localStringBuffer, "DEBITBATCH", this.debitBatch);
    XMLHandler.appendTag(localStringBuffer, "CONAME", this.coName);
    XMLHandler.appendTag(localStringBuffer, "HEADERCOMPNAME", this.headerCompName);
    XMLHandler.appendTag(localStringBuffer, "CODISCRETIONARYDATA", this.coDiscretionaryData);
    XMLHandler.appendTag(localStringBuffer, "COID", this.coID);
    XMLHandler.appendTag(localStringBuffer, "COMPANYID", this.companyID);
    XMLHandler.appendTag(localStringBuffer, "STANDARDENTRYCLASSCODE", this.standardEntryClassCode);
    XMLHandler.appendTag(localStringBuffer, "COENTRYDESC", this.coEntryDesc);
    XMLHandler.appendTag(localStringBuffer, "ORIGDFIID", this.origDFIID);
    XMLHandler.appendTag(localStringBuffer, "CATEGORY", this.category);
    XMLHandler.appendTag(localStringBuffer, "SCOPE", this.templateScope);
    XMLHandler.appendTag(localStringBuffer, "TEMPLATE_ID", this.templateID);
    XMLHandler.appendTag(localStringBuffer, "TEMPLATE_NAME", this.templateName);
    XMLHandler.appendTag(localStringBuffer, "ORIGINAL_ID", this.originalID);
    if (this.totalCreditAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_CREDIT", this.totalCreditAmount.doubleValue());
    }
    if (this.totalDebitAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_DEBIT", this.totalDebitAmount.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "TOTAL_NUM_CREDIT", this.totalNumberCredits);
    XMLHandler.appendTag(localStringBuffer, "TOTAL_NUM_DEBIT", this.totalNumberDebits);
    XMLHandler.appendTag(localStringBuffer, "NUMBERPAYMENTS", this.numberPayments);
    XMLHandler.appendTag(localStringBuffer, "FREQUENCY", this.frequency);
    XMLHandler.appendTag(localStringBuffer, "NUMBERENTRIES", this.numberEntries);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACHBATCH");
    return localStringBuffer.toString();
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHBatch paramACHBatch, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHBatch.getID(), getID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", paramACHBatch.getTrackingID(), getTrackingID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramACHBatch.getName(), getName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BATCHTYPE", paramACHBatch.getBatchType(), getBatchType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTID", paramACHBatch.getOffsetAccountID(), getOffsetAccountID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTBANKID", paramACHBatch.getOffsetAccountBankID(), getOffsetAccountBankID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTNAME", paramACHBatch.getOffsetAccountName(), getOffsetAccountName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTNUMBER", paramACHBatch.getOffsetAccountNumber(), getOffsetAccountNumber(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTTYPE", paramACHBatch.getOffsetAccountType(), getOffsetAccountType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DATE", paramACHBatch.getDate(), getDate(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PROCESSEDONDATE", paramACHBatch.getProcessedOnDate(), getProcessedOnDate(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "SUBMITDATE", paramACHBatch.getSubmitDate(), getSubmitDate(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACHTYPE", paramACHBatch.getACHType(), getACHType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", paramACHBatch.getStatus(), getStatus(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "DEBITBATCH", paramACHBatch.getDebitBatch(), getDebitBatch(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CONAME", paramACHBatch.getCoName(), getCoName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "HEADERCOMPNAME", paramACHBatch.getHeaderCompName(), getHeaderCompName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CODISCRETIONARYDATA", paramACHBatch.getCoDiscretionaryData(), getCoDiscretionaryData(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COID", paramACHBatch.getCoID(), getCoID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYID", paramACHBatch.getCompanyID(), getCompanyID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "STANDARDENTRYCLASSCODE", paramACHBatch.getStandardEntryClassCode(), getStandardEntryClassCode(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COENTRYDESC", paramACHBatch.getCoEntryDesc(), getCoEntryDesc(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ORIGDFIID", paramACHBatch.getOrigDFIID(), getOrigDFIID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CATEGORY", paramACHBatch.getCategory(), getCategory(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "SCOPE", paramACHBatch.getBatchScope(), getBatchScope(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TEMPLATE_ID", paramACHBatch.getTemplateID(), getTemplateID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TEMPLATE_NAME", paramACHBatch.getTemplateName(), getTemplateName(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ORIGINAL_ID", paramACHBatch.getOriginalID(), getOriginalID(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NUMBERPAYMENTS", paramACHBatch.getNumberPayments(), getNumberPayments(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "FREQUENCY", paramACHBatch.getFrequency(), getFrequency(), paramString);
    if (this.taxForm != null) {
      this.taxForm.logChanges(paramHistoryTracker, paramACHBatch.getTaxForm(), paramString);
    }
    ACHEntries localACHEntries1 = new ACHEntries();
    ACHEntries localACHEntries2 = new ACHEntries();
    if (this.entries != null) {
      localACHEntries1.addAll(this.entries);
    }
    if (paramACHBatch.getACHEntries() != null) {
      localACHEntries2.addAll(paramACHBatch.getACHEntries());
    }
    Iterator localIterator = localACHEntries2.iterator();
    ACHEntry localACHEntry2;
    while (localIterator.hasNext())
    {
      ACHEntry localACHEntry1 = (ACHEntry)localIterator.next();
      localACHEntry2 = localACHEntries1.getByID(localACHEntry1.getID());
      if (localACHEntry2 == null)
      {
        localACHEntry1.logDeletion(paramHistoryTracker, paramString + paramHistoryTracker.lookupField(BEAN_NAME, "ACHENTRY") + localACHEntry1.getID());
      }
      else
      {
        localACHEntry2.logChanges(paramHistoryTracker, localACHEntry1, paramString + paramHistoryTracker.lookupField(BEAN_NAME, "ACHENTRY") + localACHEntry1.getID());
        localACHEntries1.remove(localACHEntry2);
      }
    }
    localIterator = localACHEntries1.iterator();
    while (localIterator.hasNext())
    {
      localACHEntry2 = (ACHEntry)localIterator.next();
      localACHEntry2.logCreation(paramHistoryTracker, paramString + paramHistoryTracker.lookupField(BEAN_NAME, "ACHENTRY") + localACHEntry2.getID());
    }
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramACHBatch, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, ACHBatch paramACHBatch, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramACHBatch.getID(), getID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", paramACHBatch.getTrackingID(), getTrackingID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NAME", paramACHBatch.getName(), getName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BATCHTYPE", new LocalizableString("com.ffusion.beans.ach.resources", "ACHBatchType" + paramACHBatch.getBatchTypeValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHBatchType" + getBatchTypeValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTID", paramACHBatch.getOffsetAccountID(), getOffsetAccountID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTBANKID", paramACHBatch.getOffsetAccountBankID(), getOffsetAccountBankID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTNAME", paramACHBatch.getOffsetAccountName(), getOffsetAccountName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTNUMBER", paramACHBatch.getOffsetAccountNumber(), getOffsetAccountNumber(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OFFSETACCOUNTTYPE", new LocalizableString("com.ffusion.beans.ach.resources", "ACHAccountType" + paramACHBatch.getOffsetAccountTypeValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHAccountType" + getOffsetAccountTypeValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "DATE", paramACHBatch.getDateValue() == null ? null : new LocalizableDate(paramACHBatch.getDateValue(), false), getDateValue() == null ? null : new LocalizableDate(getDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PROCESSEDONDATE", paramACHBatch.getProcessedOnDateValue() == null ? null : new LocalizableDate(paramACHBatch.getProcessedOnDateValue(), false), getProcessedOnDateValue() == null ? null : new LocalizableDate(getProcessedOnDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "SUBMITDATE", paramACHBatch.getSubmitDateValue() == null ? null : new LocalizableDate(paramACHBatch.getSubmitDateValue(), false), getSubmitDateValue() == null ? null : new LocalizableDate(getSubmitDateValue(), false), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACHTYPE", paramACHBatch.getACHType() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHType" + paramACHBatch.getACHType(), null), getACHType() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHType" + getACHType(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", new LocalizableString("com.ffusion.beans.ach.resources", "ACHStatus" + paramACHBatch.getStatusValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHStatus" + getStatusValue(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "DEBITBATCH", paramACHBatch.getDebitBatch(), getDebitBatch(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CONAME", paramACHBatch.getCoName(), getCoName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "HEADERCOMPNAME", paramACHBatch.getHeaderCompName(), getHeaderCompName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CODISCRETIONARYDATA", paramACHBatch.getCoDiscretionaryData(), getCoDiscretionaryData(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COID", paramACHBatch.getCoID(), getCoID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COMPANYID", paramACHBatch.getCompanyID(), getCompanyID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "STANDARDENTRYCLASSCODE", paramACHBatch.getStandardEntryClassCode(), getStandardEntryClassCode(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COENTRYDESC", paramACHBatch.getCoEntryDesc(), getCoEntryDesc(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ORIGDFIID", paramACHBatch.getOrigDFIID(), getOrigDFIID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CATEGORY", paramACHBatch.getCategory() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHCategory" + paramACHBatch.getCategory(), null), getCategory() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHCategory" + getCategory(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "SCOPE", paramACHBatch.getBatchScope() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHTemplateScope" + paramACHBatch.getBatchScope(), null), getBatchScope() == null ? null : new LocalizableString("com.ffusion.beans.ach.resources", "ACHTemplateScope" + getBatchScope(), null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TEMPLATE_ID", paramACHBatch.getTemplateID(), getTemplateID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TEMPLATE_NAME", paramACHBatch.getTemplateName(), getTemplateName(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ORIGINAL_ID", paramACHBatch.getOriginalID(), getOriginalID(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NUMBERPAYMENTS", paramACHBatch.getNumberPayments(), getNumberPayments(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "FREQUENCY", new LocalizableString("com.ffusion.beans.ach.resources", "ACHFrequencies" + paramACHBatch.getFrequencyValue(), null), new LocalizableString("com.ffusion.beans.ach.resources", "ACHFrequencies" + getFrequencyValue(), null), paramILocalizable);
    if (this.taxForm != null) {
      this.taxForm.logChanges(paramHistoryTracker, paramACHBatch.getTaxForm(), paramILocalizable);
    }
    ACHEntries localACHEntries1 = new ACHEntries();
    ACHEntries localACHEntries2 = new ACHEntries();
    if (this.entries != null) {
      localACHEntries1.addAll(this.entries);
    }
    if (paramACHBatch.getACHEntries() != null) {
      localACHEntries2.addAll(paramACHBatch.getACHEntries());
    }
    Iterator localIterator = localACHEntries2.iterator();
    ACHEntry localACHEntry2;
    Object[] arrayOfObject;
    LocalizableString localLocalizableString;
    while (localIterator.hasNext())
    {
      ACHEntry localACHEntry1 = (ACHEntry)localIterator.next();
      localACHEntry2 = localACHEntries1.getByID(localACHEntry1.getID());
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramILocalizable;
      arrayOfObject[1] = localACHEntry1.getID();
      localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "ACH_ENTRY", arrayOfObject);
      if (localACHEntry2 == null)
      {
        localACHEntry1.logDeletion(paramHistoryTracker, localLocalizableString);
      }
      else
      {
        localACHEntry2.logChanges(paramHistoryTracker, localACHEntry1, localLocalizableString);
        localACHEntries1.remove(localACHEntry2);
      }
    }
    localIterator = localACHEntries1.iterator();
    while (localIterator.hasNext())
    {
      localACHEntry2 = (ACHEntry)localIterator.next();
      arrayOfObject = new Object[2];
      arrayOfObject[0] = paramILocalizable;
      arrayOfObject[1] = localACHEntry2.getID();
      localLocalizableString = new LocalizableString("com.ffusion.beans.history.resources", "ACH_ENTRY", arrayOfObject);
      localACHEntry2.logCreation(paramHistoryTracker, localLocalizableString);
    }
    super.logChanges(paramHistoryTracker, BEAN_NAME, paramACHBatch, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
  
  public void fixupEntriesAmount()
  {
    Currency localCurrency1 = new Currency("0", this.locale);
    Currency localCurrency2 = new Currency("0", this.locale);
    Currency localCurrency3 = new Currency("0", this.locale);
    this.totalNumberCredits = 0;
    this.totalNumberDebits = 0;
    Iterator localIterator = this.entries.iterator();
    int i = 0;
    if (this.batchType == 3) {
      this.offsetAccounts.clear();
    }
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (ACHEntry)localIterator.next();
      if (((ACHEntry)localObject).getActiveValue() == true)
      {
        localCurrency1.addAmount(((ACHEntry)localObject).getAmountValue());
        ACHOffsetAccount localACHOffsetAccount = null;
        if (this.batchType == 3)
        {
          localACHOffsetAccount = getOffsetAccounts().getByID(((ACHEntry)localObject).getOffsetAccountID());
          if (localACHOffsetAccount == null)
          {
            localACHOffsetAccount = getOffsetAccounts().create();
            localACHOffsetAccount.setID(((ACHEntry)localObject).getOffsetAccountID());
            localACHOffsetAccount.setRoutingNum(RoutingNumberUtil.getRoutingNumber_EightDigits(((ACHEntry)localObject).getOffsetAccountBankID()) + RoutingNumberUtil.getRoutingNumber_CheckDigit(((ACHEntry)localObject).getOffsetAccountBankID()));
            localACHOffsetAccount.setNumber(((ACHEntry)localObject).getOffsetAccountNumber());
            localACHOffsetAccount.setNickName(((ACHEntry)localObject).getOffsetAccountName());
            localACHOffsetAccount.setType(((ACHEntry)localObject).getOffsetAccountType());
          }
        }
        if (((ACHEntry)localObject).getAmountIsDebitValue())
        {
          localCurrency2.addAmount(((ACHEntry)localObject).getAmountValue());
          this.totalNumberDebits += 1;
          if (localACHOffsetAccount != null)
          {
            localACHOffsetAccount.setTotalNumberCredits(localACHOffsetAccount.getTotalNumberCredits() + 1);
            localACHOffsetAccount.addTotalCreditAmount(((ACHEntry)localObject).getAmountValue().getAmountValue().abs().multiply(new BigDecimal(100.0D)).longValue());
          }
        }
        else
        {
          localCurrency3.addAmount(((ACHEntry)localObject).getAmountValue());
          this.totalNumberCredits += 1;
          if (localACHOffsetAccount != null)
          {
            localACHOffsetAccount.setTotalNumberDebits(localACHOffsetAccount.getTotalNumberDebits() + 1);
            localACHOffsetAccount.addTotalDebitAmount(((ACHEntry)localObject).getAmountValue().getAmountValue().abs().multiply(new BigDecimal(100.0D)).longValue());
          }
        }
        i++;
      }
    }
    setNumberEntries(i);
    if (this.batchType == 2)
    {
      localObject = getOffsetAccount();
      if (localObject != null) {
        if (localCurrency3.doubleValue() > localCurrency2.doubleValue())
        {
          ((ACHOffsetAccount)localObject).setTotalNumberDebits(1);
          ((ACHOffsetAccount)localObject).setTotalDebitAmount(localCurrency3.getAmountValue().abs().multiply(new BigDecimal(100.0D)).longValue());
        }
        else if (localCurrency2.doubleValue() > localCurrency3.doubleValue())
        {
          ((ACHOffsetAccount)localObject).setTotalNumberCredits(1);
          ((ACHOffsetAccount)localObject).setTotalCreditAmount(localCurrency2.getAmountValue().abs().multiply(new BigDecimal(100.0D)).longValue());
        }
      }
    }
    setAmount(localCurrency1);
    setTotalCreditAmount(localCurrency3);
    setTotalDebitAmount(localCurrency2);
  }
  
  public void setTotalCreditAmount(String paramString)
  {
    if (this.totalCreditAmount == null) {
      this.totalCreditAmount = new Currency(paramString, this.locale);
    } else {
      this.totalCreditAmount.fromString(paramString);
    }
  }
  
  public void setTotalCreditAmount(BigDecimal paramBigDecimal)
  {
    if (this.totalCreditAmount == null) {
      this.totalCreditAmount = new Currency(paramBigDecimal, this.locale);
    } else {
      this.totalCreditAmount.setAmount(paramBigDecimal);
    }
  }
  
  public void setTotalCreditAmount(Currency paramCurrency)
  {
    this.totalCreditAmount = paramCurrency;
  }
  
  public String getTotalCreditAmount()
  {
    if (this.totalCreditAmount != null) {
      return this.totalCreditAmount.toString();
    }
    return "";
  }
  
  public Currency getTotalCreditAmountValue()
  {
    return this.totalCreditAmount;
  }
  
  public void setTotalDebitAmount(String paramString)
  {
    if (this.totalDebitAmount == null) {
      this.totalDebitAmount = new Currency(paramString, this.locale);
    } else {
      this.totalDebitAmount.fromString(paramString);
    }
  }
  
  public void setTotalDebitAmount(BigDecimal paramBigDecimal)
  {
    if (this.totalDebitAmount == null) {
      this.totalDebitAmount = new Currency(paramBigDecimal, this.locale);
    } else {
      this.totalDebitAmount.setAmount(paramBigDecimal);
    }
  }
  
  public void setTotalDebitAmount(Currency paramCurrency)
  {
    this.totalDebitAmount = paramCurrency;
  }
  
  public String getTotalDebitAmount()
  {
    if (this.totalDebitAmount != null) {
      return this.totalDebitAmount.toString();
    }
    return "";
  }
  
  public Currency getTotalDebitAmountValue()
  {
    return this.totalDebitAmount;
  }
  
  public int getTotalNumberCredits()
  {
    return this.totalNumberCredits;
  }
  
  public void setTotalNumberCredits(int paramInt)
  {
    this.totalNumberCredits = paramInt;
  }
  
  public void setTotalNumberCredits(String paramString)
  {
    try
    {
      setTotalNumberCredits(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setTotalNumberCredits(0);
    }
  }
  
  public int getTotalNumberDebits()
  {
    return this.totalNumberDebits;
  }
  
  public void setTotalNumberDebits(int paramInt)
  {
    this.totalNumberDebits = paramInt;
  }
  
  public void setTotalNumberDebits(String paramString)
  {
    try
    {
      setTotalNumberDebits(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setTotalNumberDebits(0);
    }
  }
  
  public void setNumberEntries(int paramInt)
  {
    this.numberEntries = paramInt;
  }
  
  public String getNumberEntries()
  {
    return String.valueOf(this.numberEntries);
  }
  
  public int getNumberEntriesValue()
  {
    return this.numberEntries;
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public String getLastModifier()
  {
    return this.lastModifier;
  }
  
  public void setLastModifier(String paramString)
  {
    this.lastModifier = paramString;
  }
  
  public static int mapBPWStatusToInt(String paramString)
  {
    if ((paramString == null) || (paramString.equals("WILLPROCESSON"))) {
      return 2;
    }
    if (paramString.equals("CREATED")) {
      return 11;
    }
    if (paramString.equals("PROCESSEDON")) {
      return 5;
    }
    if (paramString.equals("POSTEDON")) {
      return 5;
    }
    if (paramString.equals("NOFUNDSON")) {
      return 7;
    }
    if (paramString.equals("FAILEDON")) {
      return 6;
    }
    if (paramString.equals("LIMIT_CHECK_FAILED")) {
      return 17;
    }
    if (paramString.equals("LIMIT_REVERT_FAILED")) {
      return 18;
    }
    if (paramString.equals("APPROVAL_FAILED")) {
      return 19;
    }
    if (paramString.equals("APPROVAL_PENDING")) {
      return 8;
    }
    if (paramString.equals("APPROVAL_REJECTED")) {
      return 10;
    }
    if (paramString.equals("CANCELEDON")) {
      return 3;
    }
    if (paramString.equals("APPROVAL_NOT_ALLOWED")) {
      return 20;
    }
    return -1;
  }
  
  public static String mapStatusToBPWStatus(int paramInt)
  {
    String str = "UNKNOWN";
    switch (paramInt)
    {
    case 2: 
      str = "WILLPROCESSON";
      break;
    case 11: 
      str = "CREATED";
      break;
    case 5: 
      str = "POSTEDON";
      break;
    case 6: 
      str = "FAILEDON";
      break;
    case 17: 
      str = "LIMIT_CHECK_FAILED";
      break;
    case 18: 
      str = "LIMIT_REVERT_FAILED";
      break;
    case 19: 
      str = "APPROVAL_FAILED";
      break;
    case 3: 
      str = "CANCELEDON";
      break;
    case 8: 
      str = "APPROVAL_PENDING";
      break;
    case 10: 
      str = "APPROVAL_REJECTED";
      break;
    case 20: 
      str = "APPROVAL_NOT_ALLOWED";
      break;
    case 4: 
    case 7: 
    case 9: 
    case 12: 
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    default: 
      str = "UNKNOWN";
    }
    return str;
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("ACHENTRIES"))
      {
        ACHBatch.this.entries = new ACHEntries();
        ACHBatch.this.entries.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("TAXFORM"))
      {
        ACHBatch.this.taxForm = new TaxForm();
        ACHBatch.this.taxForm.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("OFFSETACCOUNTS"))
      {
        ACHBatch.this.offsetAccounts = new ACHOffsetAccounts(ACHBatch.this.locale);
        ACHBatch.this.offsetAccounts.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("OFFSETACCOUNT"))
      {
        ACHBatch.this.offsetAccount = new ACHOffsetAccount();
        ACHBatch.this.offsetAccount.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ACHBatch.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHBatch
 * JD-Core Version:    0.7.0.1
 */