package com.ffusion.beans;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ValidationError;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Locale;

public class FundsTransaction
  extends ExtendABean
  implements IApprovable, FundsTransactionTypes, Cloneable
{
  public static final String FUNDS_RESOURCE_BUNDLE = "com.ffusion.beans.banking.resources";
  protected static final String BEAN_NAME = FundsTransaction.class.getName();
  protected int funds_type;
  protected String trackingID;
  protected Currency amount;
  protected String id = "";
  protected String referenceNumber;
  protected String rejectReason;
  protected String approverName;
  protected boolean approverIsGroup;
  protected String submittedBy;
  protected String externalID;
  protected String approvalID;
  protected String userName;
  protected ErrorMessages validationErrors;
  protected String action;
  protected Integer recordNumber;
  protected Integer lineNumber;
  protected String lineContent;
  protected String currentFieldName;
  protected long transactionIndex;
  protected boolean _autoEntitleTransaction = true;
  protected String templateID;
  protected String templateName;
  protected String templateScope;
  protected int version = 0;
  public static final String TRACKING_ID = "TRACKINGID";
  
  public FundsTransaction()
  {
    this.datetype = "SHORT";
  }
  
  public FundsTransaction(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new IllegalArgumentException();
    }
    this.id = paramString;
    this.datetype = "SHORT";
  }
  
  public FundsTransaction(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
  }
  
  public Object clone()
  {
    try
    {
      FundsTransaction localFundsTransaction = (FundsTransaction)super.clone();
      if (localFundsTransaction.getAmountValue() != null) {
        localFundsTransaction.setAmount((Currency)localFundsTransaction.getAmountValue().clone());
      }
      return localFundsTransaction;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.amount != null) {
      this.amount.setLocale(paramLocale);
    }
  }
  
  public void setAmount(String paramString)
  {
    if (this.amount == null) {
      this.amount = new Currency(paramString, this.locale);
    } else {
      this.amount.fromString(paramString);
    }
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    if (this.amount == null) {
      this.amount = new Currency(paramBigDecimal, this.locale);
    } else {
      this.amount.setAmount(paramBigDecimal);
    }
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.amount = paramCurrency;
  }
  
  public String getAmount()
  {
    if (this.amount != null) {
      return this.amount.toString();
    }
    return "";
  }
  
  public Currency getAmountValue()
  {
    return this.amount;
  }
  
  public String getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public void setSubmittedBy(int paramInt)
  {
    this.submittedBy = String.valueOf(paramInt);
  }
  
  public String getExternalID()
  {
    return this.externalID;
  }
  
  public void setExternalID(String paramString)
  {
    this.externalID = paramString;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public void setTransactionIndex(long paramLong)
  {
    this.transactionIndex = paramLong;
  }
  
  public void setTransactionIndex(String paramString)
  {
    try
    {
      this.transactionIndex = Long.parseLong(paramString);
    }
    catch (Exception localException) {}
  }
  
  public long getTransactionIndex()
  {
    return this.transactionIndex;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    FundsTransaction localFundsTransaction = (FundsTransaction)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (this.id != null) && (localFundsTransaction.getID() != null)) {
      i = numStringCompare(this.id, localFundsTransaction.getID());
    } else if (paramString.equals("FUNDS_TYPE")) {
      i = getType() - localFundsTransaction.getType();
    } else if ((paramString.equals("SUBMITTED_BY")) && (this.submittedBy != null) && (localFundsTransaction.getSubmittedBy() != null)) {
      i = this.submittedBy.compareTo(localFundsTransaction.getSubmittedBy());
    } else if ((paramString.equals("AMOUNT")) && (this.amount != null) && (localFundsTransaction.getAmountValue() != null)) {
      i = this.amount.compareTo(localFundsTransaction.getAmountValue());
    } else if ((paramString.equals("REFERENCENUMBER")) && (this.referenceNumber != null) && (localFundsTransaction.getReferenceNumber() != null)) {
      i = numStringCompare(this.referenceNumber, localFundsTransaction.getReferenceNumber());
    } else if ((paramString.equals("EXTERNAL_ID")) && (this.externalID != null) && (localFundsTransaction.getExternalID() != null)) {
      i = this.externalID.compareTo(localFundsTransaction.getExternalID());
    } else if ((paramString.equals("TEMPLATENAME")) && (this.templateName != null) && (localFundsTransaction.getTemplateName() != null)) {
      i = numStringCompare(this.templateName, localFundsTransaction.getTemplateName());
    } else if ((paramString.equals("TEMPLATEID")) && (this.templateID != null) && (localFundsTransaction.getTemplateID() != null)) {
      i = numStringCompare(this.id, localFundsTransaction.getTemplateID());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (this.id != null)) {
      return isFilterable(this.id, paramString2, paramString3);
    }
    if (paramString1.equals("SUBMITTED_BY")) {
      return isFilterable(getSubmittedBy(), paramString2, paramString3);
    }
    if ((paramString1.equals("AMOUNT")) && (this.amount != null)) {
      return this.amount.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("AMOUNTABSOLUTE")) && (this.amount != null))
    {
      Currency localCurrency = new Currency(this.amount.toStringAbsolute(), this.locale);
      return localCurrency.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("REFERENCENUMBER")) && (this.referenceNumber != null)) {
      return isFilterable(this.referenceNumber, paramString2, paramString3);
    }
    if (paramString1.equals("FUNDS_TYPE")) {
      return isFilterable(String.valueOf(getType()), paramString2, paramString3);
    }
    if (paramString1.equals("EXTERNAL_ID")) {
      return isFilterable(getExternalID(), paramString2, paramString3);
    }
    if (paramString1.equals("TRANSACTION_INDEX")) {
      return isFilterable(String.valueOf(getTransactionIndex()), paramString2, paramString3);
    }
    if ((paramString1.equals("TEMPLATEID")) && (this.templateID != null)) {
      return isFilterable(this.templateID, paramString2, paramString3);
    }
    if ((paramString1.equals("TRACKINGID")) && (this.trackingID != null)) {
      return isFilterable(this.trackingID, paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(FundsTransaction paramFundsTransaction)
  {
    super.set(paramFundsTransaction);
    this.submittedBy = paramFundsTransaction.submittedBy;
    setType(paramFundsTransaction.getType());
    this.trackingID = paramFundsTransaction.getTrackingID();
    this.id = paramFundsTransaction.getID();
    if (paramFundsTransaction.getAmountValue() != null) {
      this.amount = ((Currency)paramFundsTransaction.getAmountValue().clone());
    } else {
      this.amount = null;
    }
    setTransactionIndex(paramFundsTransaction.getTransactionIndex());
    setReferenceNumber(paramFundsTransaction.getReferenceNumber());
    setExternalID(paramFundsTransaction.getExternalID());
    setTemplateID(paramFundsTransaction.getTemplateID());
    setTemplateName(paramFundsTransaction.getTemplateName());
  }
  
  public void merge(FundsTransaction paramFundsTransaction)
  {
    int i = paramFundsTransaction.getType();
    if (i != 0) {
      setType(i);
    }
    String str = paramFundsTransaction.getTrackingID();
    if (str != null) {
      setTrackingID(str);
    }
    Currency localCurrency = paramFundsTransaction.getAmountValue();
    if (localCurrency != null) {}
    setAmount(localCurrency);
    str = paramFundsTransaction.getID();
    if ((str != null) && (str.length() > 0)) {
      this.id = str;
    }
    str = paramFundsTransaction.getReferenceNumber();
    if (str != null) {
      setReferenceNumber(str);
    }
    str = paramFundsTransaction.getAction();
    if (str != null) {
      setAction(str);
    }
    if ((paramFundsTransaction.getValidationErrors() != null) && (paramFundsTransaction.getValidationErrors().size() > 0))
    {
      if (this.validationErrors == null) {
        this.validationErrors = new ErrorMessages();
      }
      this.validationErrors.addAll(paramFundsTransaction.getValidationErrors());
    }
  }
  
  public void setAction(String paramString)
  {
    this.action = paramString;
  }
  
  public String getAction()
  {
    return this.action;
  }
  
  public boolean validate(int paramInt, boolean paramBoolean)
  {
    this.validationErrors = null;
    if (this.funds_type == 0)
    {
      if (paramBoolean) {
        addValidationError(getImportError("FundsTypeNotInitialized"), getImportError("InternalError"));
      }
    }
    else if (getTypeString() == null)
    {
      Object[] arrayOfObject = { String.valueOf(this.funds_type) };
      addValidationError(getImportError("InvalidValueForFundsType"), getImportError("ValueIs", arrayOfObject));
    }
    if ((paramBoolean) && (this.amount == null)) {
      addValidationError(getImportError("AmountNotSet"), getImportError("ValueNotSet"));
    }
    if (((paramBoolean) && (this.id == null)) || (this.id.length() == 0)) {
      addValidationError(getImportError("IDNotSet"), getImportError("ValueNotSet"));
    }
    return this.validationErrors == null;
  }
  
  public String getResourceBundleName()
  {
    return "com.ffusion.beans.banking.resources";
  }
  
  public String getErrorPrefix()
  {
    return "ErrorMessage_";
  }
  
  public void addValidationError(ValidationError paramValidationError)
  {
    if (this.validationErrors == null) {
      this.validationErrors = new ErrorMessages();
    }
    this.validationErrors.add(paramValidationError);
  }
  
  public void addValidationError(String paramString1, String paramString2)
  {
    addValidationError(new ValidationError(paramString1, paramString2));
  }
  
  public void addValidationError(String paramString1, String paramString2, String paramString3)
  {
    addValidationError(new ValidationError(paramString1, paramString2, paramString3));
  }
  
  public String getImportError(String paramString)
  {
    return ResourceUtil.getString(getErrorPrefix() + paramString, getResourceBundleName(), this.locale);
  }
  
  public String getImportError(String paramString, Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject == null) {
      return ResourceUtil.getString(getErrorPrefix() + paramString, getResourceBundleName(), this.locale);
    }
    return MessageFormat.format(ResourceUtil.getString(getErrorPrefix() + paramString, getResourceBundleName(), this.locale), paramArrayOfObject);
  }
  
  public void resetValidationErrors()
  {
    this.validationErrors = null;
  }
  
  public String getHasValidationErrors()
  {
    return String.valueOf(this.validationErrors != null);
  }
  
  public String getCurrentFieldHasValidationErrors()
  {
    if (this.validationErrors == null) {
      return String.valueOf(false);
    }
    if ((this.currentFieldName == null) || (this.currentFieldName.length() == 0)) {
      return String.valueOf(false);
    }
    Iterator localIterator = this.validationErrors.iterator();
    while (localIterator.hasNext())
    {
      ValidationError localValidationError = (ValidationError)localIterator.next();
      if ((localValidationError.getField() != null) && (this.currentFieldName.toUpperCase().indexOf(localValidationError.getField()) != -1)) {
        return String.valueOf(true);
      }
    }
    return String.valueOf(false);
  }
  
  public String getFirstErrorField()
  {
    if (this.validationErrors == null) {
      return "";
    }
    Iterator localIterator = this.validationErrors.iterator();
    while (localIterator.hasNext())
    {
      ValidationError localValidationError = (ValidationError)localIterator.next();
      if ((localValidationError.getField() != null) && (localValidationError.getField().length() > 0)) {
        return localValidationError.getField();
      }
    }
    return "";
  }
  
  public String getCurrentFieldName()
  {
    return this.currentFieldName;
  }
  
  public void setCurrentFieldName(String paramString)
  {
    this.currentFieldName = paramString;
  }
  
  public ErrorMessages getValidationErrors()
  {
    return this.validationErrors;
  }
  
  public ErrorMessages getCurrentFieldValidationErrors()
  {
    ErrorMessages localErrorMessages = new ErrorMessages();
    if (this.validationErrors == null) {
      return localErrorMessages;
    }
    Iterator localIterator = this.validationErrors.iterator();
    while (localIterator.hasNext())
    {
      ValidationError localValidationError = (ValidationError)localIterator.next();
      if ((this.currentFieldName == null) || (this.currentFieldName.length() == 0))
      {
        if ((localValidationError.getField() == null) || (localValidationError.getField().length() == 0)) {
          localErrorMessages.add(localValidationError);
        }
      }
      else if (this.currentFieldName.toUpperCase().indexOf(localValidationError.getField()) != -1) {
        localErrorMessages.add(localValidationError);
      }
    }
    return localErrorMessages;
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("TYPE")) {
      try
      {
        setType(Integer.parseInt(paramString2));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localNumberFormatException.printStackTrace();
      }
    } else if (paramString1.equals("TRACKINGID")) {
      setTrackingID(paramString2);
    } else if (paramString1.equals("ID")) {
      this.id = paramString2;
    } else if (paramString1.equals("AMOUNT"))
    {
      if (this.amount == null) {
        this.amount = new Currency(paramString2, this.locale);
      } else {
        this.amount.fromString(paramString2);
      }
    }
    else if (paramString1.equals("REFERENCENUMBER")) {
      this.referenceNumber = paramString2;
    } else if (paramString1.equals("SUBMITTED_BY")) {
      this.submittedBy = paramString2;
    } else if (paramString1.equals("EXTERNAL_ID")) {
      this.externalID = paramString2;
    } else if (paramString1.equalsIgnoreCase("TRANSACTION_INDEX")) {
      setTransactionIndex(paramString2);
    } else if (paramString1.equals("AUTO_ENTITLE")) {
      this._autoEntitleTransaction = Boolean.valueOf(paramString2).booleanValue();
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendTag(localStringBuffer, "TYPE", getType());
    if (this.amount != null) {
      XMLHandler.appendTag(localStringBuffer, "AMOUNT", this.amount.getCurrencyStringNoSymbolNoComma());
    }
    XMLHandler.appendTag(localStringBuffer, "SUBMITTED_BY", this.submittedBy);
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "REFERENCENUMBER", this.referenceNumber);
    XMLHandler.appendTag(localStringBuffer, "TRANSACTION_INDEX", getTransactionIndex());
    XMLHandler.appendTag(localStringBuffer, "TRACKINGID", this.trackingID);
    XMLHandler.appendTag(localStringBuffer, "EXTERNAL_ID", this.externalID);
    XMLHandler.appendTag(localStringBuffer, "AUTO_ENTITLE", String.valueOf(this._autoEntitleTransaction));
    localStringBuffer.append(super.getXML());
    return localStringBuffer.toString();
  }
  
  public int getType()
  {
    return this.funds_type;
  }
  
  public String getTypeString()
  {
    return ResourceUtil.getString("FundsTransactionTypes" + getType(), "com.ffusion.beans.banking.resources", this.locale);
  }
  
  public void setType(int paramInt)
  {
    this.funds_type = paramInt;
  }
  
  public void setType(String paramString)
  {
    try
    {
      this.funds_type = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setReferenceNumber(String paramString)
  {
    this.referenceNumber = paramString;
  }
  
  public String getReferenceNumber()
  {
    return this.referenceNumber;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setRecordNumber(Integer paramInteger)
  {
    this.recordNumber = paramInteger;
  }
  
  public String getRecordNumber()
  {
    return this.recordNumber == null ? null : String.valueOf(this.recordNumber);
  }
  
  public void setLineNumber(Integer paramInteger)
  {
    this.lineNumber = paramInteger;
  }
  
  public String getLineNumber()
  {
    return this.lineNumber == null ? null : String.valueOf(this.lineNumber);
  }
  
  public void setLineContent(String paramString)
  {
    this.lineContent = paramString;
  }
  
  public String getLineContent()
  {
    return this.lineContent;
  }
  
  public String getInquireComment()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    getInquireComment(localStringBuffer);
    return localStringBuffer.toString();
  }
  
  protected void getInquireComment(StringBuffer paramStringBuffer)
  {
    if (getTypeString() == null) {
      paramStringBuffer.append("No type.\n");
    } else {
      paramStringBuffer.append("Type is ").append(getTypeString()).append(".\n");
    }
    if (getID() == null) {
      paramStringBuffer.append("No ID.\n");
    } else {
      paramStringBuffer.append("ID is ").append(getID()).append(".\n");
    }
    if (getAmount() == null) {
      paramStringBuffer.append("No amount.\n");
    } else {
      paramStringBuffer.append("Amount is ").append(getAmount()).append(".\n");
    }
    if (getReferenceNumber() == null) {
      paramStringBuffer.append("No Reference number.\n");
    } else {
      paramStringBuffer.append("Reference number is ").append(getReferenceNumber()).append(".\n");
    }
  }
  
  public String getRejectReason()
  {
    return this.rejectReason;
  }
  
  public void setRejectReason(String paramString)
  {
    this.rejectReason = paramString;
  }
  
  public String getApproverName()
  {
    return this.approverName;
  }
  
  public void setApproverName(String paramString)
  {
    this.approverName = paramString;
  }
  
  public int getApprovalType()
  {
    return 1;
  }
  
  public int getApprovalSubType()
  {
    return -1;
  }
  
  public String getApprovalSubTypeName()
  {
    return null;
  }
  
  public Currency getApprovalAmount()
  {
    return getAmountValue();
  }
  
  public DateTime getApprovalDueDate()
  {
    return null;
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2)
  {
    return null;
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString)
  {
    return null;
  }
  
  public void setApproverIsGroup(String paramString)
  {
    this.approverIsGroup = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getApproverIsGroup()
  {
    return "" + this.approverIsGroup;
  }
  
  public void setApproverIsGroup(boolean paramBoolean)
  {
    this.approverIsGroup = paramBoolean;
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, FundsTransaction paramFundsTransaction, String paramString)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramFundsTransaction, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString1, FundsTransaction paramFundsTransaction, String paramString2)
  {
    paramHistoryTracker.detectChange(paramString1, "AMOUNT", paramFundsTransaction.getAmountValue(), this.amount, paramString2);
    String str1 = paramFundsTransaction.getSubmittedBy();
    String str2 = getSubmittedBy();
    if ((str2 != null) && (!str2.equals(str1)))
    {
      if ((str1 != null) && (str1.length() > 0)) {
        try
        {
          User localUser1 = CustomerAdapter.getUserById(Integer.parseInt(str1));
          if (localUser1 != null) {
            str1 = localUser1.getName();
          }
        }
        catch (Exception localException1) {}
      } else {
        str1 = "";
      }
      if (str2.length() > 0) {
        try
        {
          User localUser2 = CustomerAdapter.getUserById(Integer.parseInt(str2));
          if (localUser2 != null) {
            str2 = localUser2.getName();
          }
        }
        catch (Exception localException2) {}
      }
      paramHistoryTracker.detectChange(paramString1, "SUBMITTED_BY", str1, str2, paramString2);
    }
    super.logChanges(paramHistoryTracker, paramFundsTransaction, paramString2);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, FundsTransaction paramFundsTransaction, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramFundsTransaction, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, FundsTransaction paramFundsTransaction, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(paramString, "AMOUNT", paramFundsTransaction.getAmountValue(), this.amount, paramILocalizable);
    paramHistoryTracker.detectChange(paramString, "TEMPLATENAME", paramFundsTransaction.getTemplateName(), this.templateName, paramILocalizable);
    String str1 = paramFundsTransaction.getSubmittedBy();
    String str2 = getSubmittedBy();
    if ((str2 != null) && (!str2.equals(str1)))
    {
      if ((str1 != null) && (str1.length() > 0)) {
        try
        {
          User localUser1 = CustomerAdapter.getUserById(Integer.parseInt(str1));
          if (localUser1 != null) {
            str1 = localUser1.getName();
          }
        }
        catch (Exception localException1) {}
      } else {
        str1 = "";
      }
      if (str2.length() > 0) {
        try
        {
          User localUser2 = CustomerAdapter.getUserById(Integer.parseInt(str2));
          if (localUser2 != null) {
            str2 = localUser2.getName();
          }
        }
        catch (Exception localException2) {}
      }
      paramHistoryTracker.detectChange(paramString, "SUBMITTED_BY", str1, str2, paramILocalizable);
    }
    super.logChanges(paramHistoryTracker, paramFundsTransaction, paramILocalizable);
  }
  
  public void setAutoEntitleTransaction(boolean paramBoolean)
  {
    this._autoEntitleTransaction = paramBoolean;
  }
  
  public boolean getAutoEntitleTransaction()
  {
    return this._autoEntitleTransaction;
  }
  
  public String getApprovalID()
  {
    return this.approvalID;
  }
  
  public void setApprovalID(String paramString)
  {
    this.approvalID = paramString;
  }
  
  public int getVersion()
  {
    return this.version;
  }
  
  public void setVersion(int paramInt)
  {
    this.version = paramInt;
  }
  
  public String getTemplateID()
  {
    return this.templateID;
  }
  
  public void setTemplateID(String paramString)
  {
    this.templateID = paramString;
  }
  
  public String getTemplateName()
  {
    return this.templateName;
  }
  
  public void setTemplateName(String paramString)
  {
    this.templateName = paramString;
  }
  
  public String getTemplateScope()
  {
    return this.templateScope;
  }
  
  public void setTemplateScope(String paramString)
  {
    this.templateScope = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.FundsTransaction
 * JD-Core Version:    0.7.0.1
 */