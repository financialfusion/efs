package com.ffusion.beans.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import java.math.BigDecimal;
import java.text.Collator;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

public class TransferBatch
  extends FundsTransaction
  implements TransferDefines
{
  protected static final String BEAN_NAME = TransferBatch.class.getName();
  private String eP;
  private String eK;
  private String eJ = "BATCH";
  private int eM;
  private com.ffusion.util.beans.DateTime eI;
  private String eL;
  private String eO;
  protected int error;
  private Transfers eN;
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.eI != null) {
      this.eI.setFormat(paramString);
    }
    if (this.eN != null)
    {
      Iterator localIterator = this.eN.iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        localTransfer.setDateFormat(paramString);
      }
    }
  }
  
  public void setError(int paramInt)
  {
    this.error = paramInt;
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public TransferBatch() {}
  
  public TransferBatch(String paramString)
  {
    super(paramString);
  }
  
  public TransferBatch(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getBankID()
  {
    return this.eP;
  }
  
  public void setBankID(String paramString)
  {
    this.eP = paramString;
  }
  
  public String getCustomerID()
  {
    return this.eK;
  }
  
  public void setCustomerID(String paramString)
  {
    this.eK = paramString;
  }
  
  public void setCustomerID(int paramInt)
  {
    this.eK = String.valueOf(paramInt);
  }
  
  public String getBatchType()
  {
    return this.eJ;
  }
  
  public void setBatchType(String paramString)
  {
    this.eJ = paramString;
    if ((this.eN != null) && (this.eN.size() > 0))
    {
      Iterator localIterator = this.eN.iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        if (paramString.equals("TEMPLATE")) {
          localTransfer.setTransferType("TEMPLATE");
        } else {
          localTransfer.setTransferType("Current");
        }
      }
    }
  }
  
  public int getTransferCountValue()
  {
    return this.eM;
  }
  
  public String getTransferCount()
  {
    return String.valueOf(this.eM);
  }
  
  public void setTransferCount(int paramInt)
  {
    this.eM = paramInt;
  }
  
  public void setTransferCount(String paramString)
  {
    this.eM = Integer.parseInt(paramString);
  }
  
  public com.ffusion.util.beans.DateTime getSubmitDate()
  {
    return this.eI;
  }
  
  public void setSubmitDate(com.ffusion.util.beans.DateTime paramDateTime)
  {
    this.eI = paramDateTime;
  }
  
  public void setSubmitDate(String paramString)
  {
    try
    {
      if (this.eI == null) {
        this.eI = new com.ffusion.beans.DateTime(paramString, this.locale, this.datetype);
      } else {
        this.eI.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getStatus()
  {
    return this.eL;
  }
  
  public void setStatus(String paramString)
  {
    this.eL = paramString;
  }
  
  public String getMemo()
  {
    return this.eO;
  }
  
  public void setMemo(String paramString)
  {
    this.eO = paramString;
  }
  
  public boolean getHasMultiCurrency()
  {
    if (this.eN == null) {
      return false;
    }
    return this.eN.getHasMultiCurrency();
  }
  
  public boolean getHasSingleUserAssignedAmountCurrency()
  {
    if (this.eN == null) {
      return false;
    }
    return this.eN.getHasSingleUserAssignedAmountCurrency();
  }
  
  public Account getCommonFromAccount()
  {
    if (this.eN == null) {
      return null;
    }
    return this.eN.getCommonFromAccount();
  }
  
  public Account getCommonToAccount()
  {
    if (this.eN == null) {
      return null;
    }
    return this.eN.getCommonToAccount();
  }
  
  public String getCommonDestination()
  {
    if (this.eN == null) {
      return null;
    }
    return this.eN.getCommonDestination();
  }
  
  public Transfers getTransfers()
  {
    return this.eN;
  }
  
  public void setTransfers(Transfers paramTransfers)
  {
    this.eN = paramTransfers;
  }
  
  public void set(TransferBatch paramTransferBatch)
  {
    super.set(paramTransferBatch);
    this.eP = paramTransferBatch.getBankID();
    this.eK = paramTransferBatch.getCustomerID();
    this.templateName = paramTransferBatch.getTemplateName();
    this.eJ = paramTransferBatch.getBatchType();
    this.eM = paramTransferBatch.getTransferCountValue();
    this.eI = paramTransferBatch.getSubmitDate();
    this.eL = paramTransferBatch.getStatus();
    this.eO = paramTransferBatch.getMemo();
    this.trackingID = paramTransferBatch.getTrackingID();
    if (paramTransferBatch.getTransfers() != null)
    {
      this.eN = new Transfers();
      Iterator localIterator = paramTransferBatch.getTransfers().iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)this.eN.create();
        localTransfer.set((Transfer)localIterator.next());
      }
    }
  }
  
  public void setTransferBatchInfo(TransferBatchInfo paramTransferBatchInfo)
  {
    if (paramTransferBatchInfo == null) {
      return;
    }
    setID(paramTransferBatchInfo.getBatchID());
    setBankID(paramTransferBatchInfo.getFIID());
    setCustomerID(paramTransferBatchInfo.getCustomerId());
    setTemplateName(paramTransferBatchInfo.getBatchName());
    setBatchType(paramTransferBatchInfo.getBatchType());
    setAmount(new Currency(paramTransferBatchInfo.getTotalAmount(), paramTransferBatchInfo.getAmountCurrency(), this.locale));
    setTransferCount(paramTransferBatchInfo.getTransferCount());
    String str1 = getDateFormat();
    setDateFormat("yyyyMMdd");
    String str2 = paramTransferBatchInfo.getSubmitDate();
    if ((str2 != null) && (str2.length() > 8)) {
      str2 = str2.substring(0, 8);
    }
    setSubmitDate(str2);
    setDateFormat(str1);
    setStatus(paramTransferBatchInfo.getBatchStatus());
    setMemo(paramTransferBatchInfo.getMemo());
    setTrackingID(paramTransferBatchInfo.getLogId());
    setError(paramTransferBatchInfo.getStatusCode());
    setSubmittedBy(paramTransferBatchInfo.getSubmittedBy());
    Accounts localAccounts = (Accounts)get("Accounts");
    if ((paramTransferBatchInfo.getTransfers() != null) && (paramTransferBatchInfo.getTransfers().length > 0))
    {
      Transfers localTransfers = new Transfers();
      setTransfers(localTransfers);
      for (int i = 0; i < paramTransferBatchInfo.getTransfers().length; i++)
      {
        TransferInfo localTransferInfo = paramTransferBatchInfo.getTransfers()[i];
        Transfer localTransfer = (Transfer)localTransfers.create();
        localTransfer.setTransferInfo(localTransferInfo, localAccounts);
      }
    }
  }
  
  public TransferBatchInfo getTransferBatchInfo()
  {
    return getTransferBatchInfo(null);
  }
  
  public TransferBatchInfo getTransferBatchInfo(String paramString)
  {
    TransferBatchInfo localTransferBatchInfo = new TransferBatchInfo();
    copyToTransferBatchInfo(localTransferBatchInfo, paramString);
    return localTransferBatchInfo;
  }
  
  protected void populateExtraInfo(TransferInfo paramTransferInfo, String paramString)
  {
    if ((this.map != null) && (this.map.size() > 0))
    {
      Hashtable localHashtable = paramTransferInfo.getExtInfo();
      if (localHashtable == null)
      {
        localHashtable = new Hashtable();
        paramTransferInfo.setExtInfo(localHashtable);
      }
      Iterator localIterator = this.map.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (((localEntry.getKey() instanceof String)) && ((localEntry.getValue() instanceof String))) {
          if ((!localEntry.getKey().equals("FrequencyValue")) && (!localEntry.getKey().equals("EXTACCTID")))
          {
            ValueInfo localValueInfo = new ValueInfo();
            localValueInfo.setValue(localEntry.getValue());
            localValueInfo.setAction(paramString);
            localHashtable.put(localEntry.getKey(), localValueInfo);
          }
        }
      }
    }
  }
  
  public void copyToTransferBatchInfo(TransferBatchInfo paramTransferBatchInfo, String paramString)
  {
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    paramTransferBatchInfo.setBatchID(getID());
    paramTransferBatchInfo.setFIID(getBankID());
    paramTransferBatchInfo.setCustomerId(getCustomerID());
    paramTransferBatchInfo.setBatchName(getTemplateName());
    paramTransferBatchInfo.setBatchType(getBatchType());
    paramTransferBatchInfo.setTotalAmount(getAmount());
    paramTransferBatchInfo.setTransferCount(getTransferCount());
    if (getAmountValue() != null) {
      paramTransferBatchInfo.setAmountCurrency(getAmountValue().getCurrencyCode());
    }
    if (getSubmitDate() != null) {
      paramTransferBatchInfo.setSubmitDate(localDateFormat.format(getSubmitDate().getTime()));
    }
    paramTransferBatchInfo.setBatchStatus(getStatus());
    paramTransferBatchInfo.setMemo(getMemo());
    paramTransferBatchInfo.setLogId(getTrackingID());
    paramTransferBatchInfo.setStatusCode(getErrorValue());
    paramTransferBatchInfo.setSubmittedBy(getSubmittedBy());
    if ((getTransfers() != null) && (getTransfers().size() > 0))
    {
      TransferInfo[] arrayOfTransferInfo = new TransferInfo[getTransfers().size()];
      for (int i = 0; i < getTransfers().size(); i++)
      {
        Transfer localTransfer = (Transfer)getTransfers().get(i);
        TransferInfo localTransferInfo = localTransfer.getTransferInfo(paramString);
        if (paramString != null) {
          populateExtraInfo(localTransferInfo, paramString);
        }
        arrayOfTransferInfo[i] = localTransferInfo;
      }
      paramTransferBatchInfo.setTransfers(arrayOfTransferInfo);
    }
  }
  
  public AuditLogRecord getAuditRecord(SecureUser paramSecureUser, ILocalizable paramILocalizable, int paramInt, String paramString)
  {
    String str1 = "";
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
    BigDecimal localBigDecimal = null;
    String str4 = null;
    if (getAmountValue() != null)
    {
      localBigDecimal = getAmountValue().getAmountValue();
      str4 = getAmountValue().getCurrencyCode();
    }
    AuditLogRecord localAuditLogRecord = new AuditLogRecord(str1, i, str2, str3, paramILocalizable, getTrackingID(), paramInt, paramSecureUser.getBusinessID(), localBigDecimal, str4, getID(), paramString, null, null, null, null, AuditLogUtil.getModuleFromTranType(paramInt));
    return localAuditLogRecord;
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    if (getTransfers() != null)
    {
      Iterator localIterator = getTransfers().iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        localTransfer.logCreation(paramHistoryTracker, paramILocalizable);
      }
    }
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    if (getTransfers() != null)
    {
      Iterator localIterator = getTransfers().iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        localTransfer.logCreation(paramHistoryTracker, paramString);
      }
    }
    paramHistoryTracker.logCreate(BEAN_NAME, "TRACKINGID", getTrackingID(), paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, TransferBatch paramTransferBatch, String paramString)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramTransferBatch, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString1, TransferBatch paramTransferBatch, String paramString2)
  {
    if (paramTransferBatch == null) {
      return;
    }
    if (!BEAN_NAME.equals(paramString1)) {
      paramString1 = paramString1 + "," + BEAN_NAME;
    }
    if ((getTransfers() != null) && (paramTransferBatch.getTransfers() != null))
    {
      Iterator localIterator = getTransfers().iterator();
      Transfer localTransfer1;
      Transfer localTransfer2;
      while (localIterator.hasNext())
      {
        localTransfer1 = (Transfer)localIterator.next();
        localTransfer2 = paramTransferBatch.getTransfers().getByID(localTransfer1.getID());
        if (localTransfer2 == null) {
          localTransfer1.logCreation(paramHistoryTracker, paramString2);
        } else {
          localTransfer1.logChanges(paramHistoryTracker, Transfer.BEAN_NAME, localTransfer2, paramString2);
        }
      }
      localIterator = paramTransferBatch.getTransfers().iterator();
      while (localIterator.hasNext())
      {
        localTransfer1 = (Transfer)localIterator.next();
        localTransfer2 = getTransfers().getByID(localTransfer1.getID());
        if (localTransfer2 == null) {
          localTransfer1.logDeletion(paramHistoryTracker, paramString2);
        }
      }
    }
    super.logChanges(paramHistoryTracker, paramString1, paramTransferBatch, paramString2);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, TransferBatch paramTransferBatch, ILocalizable paramILocalizable)
  {
    logChanges(paramHistoryTracker, BEAN_NAME, paramTransferBatch, paramILocalizable);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, String paramString, TransferBatch paramTransferBatch, ILocalizable paramILocalizable)
  {
    if (paramTransferBatch == null) {
      return;
    }
    if (!BEAN_NAME.equals(paramString)) {
      paramString = paramString + "," + BEAN_NAME;
    }
    if ((getTransfers() != null) && (paramTransferBatch.getTransfers() != null))
    {
      Iterator localIterator = getTransfers().iterator();
      Transfer localTransfer1;
      Transfer localTransfer2;
      while (localIterator.hasNext())
      {
        localTransfer1 = (Transfer)localIterator.next();
        localTransfer2 = paramTransferBatch.getTransfers().getByID(localTransfer1.getID());
        if (localTransfer2 == null) {
          localTransfer1.logCreation(paramHistoryTracker, paramILocalizable);
        } else {
          localTransfer1.logChanges(paramHistoryTracker, Transfer.BEAN_NAME, localTransfer2, paramILocalizable);
        }
      }
      localIterator = paramTransferBatch.getTransfers().iterator();
      while (localIterator.hasNext())
      {
        localTransfer1 = (Transfer)localIterator.next();
        localTransfer2 = getTransfers().getByID(localTransfer1.getID());
        if (localTransfer2 == null) {
          localTransfer1.logDeletion(paramHistoryTracker, paramILocalizable);
        }
      }
    }
    super.logChanges(paramHistoryTracker, paramString, paramTransferBatch, paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    if (getTransfers() != null)
    {
      Iterator localIterator = getTransfers().iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        localTransfer.logDeletion(paramHistoryTracker, paramILocalizable);
      }
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    if (getTransfers() != null)
    {
      Iterator localIterator = getTransfers().iterator();
      while (localIterator.hasNext())
      {
        Transfer localTransfer = (Transfer)localIterator.next();
        localTransfer.logDeletion(paramHistoryTracker, paramString);
      }
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKINGID", getTrackingID(), null, paramString);
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    TransferBatch localTransferBatch = null;
    if ((paramObject instanceof TransferBatch)) {
      localTransferBatch = (TransferBatch)paramObject;
    }
    int i = 1;
    if ((paramString.equals("FROMACCOUNTID")) && (getCommonFromAccount() != null) && (localTransferBatch.getCommonFromAccount() != null)) {
      i = localCollator.compare(getCommonFromAccount().getID(), localTransferBatch.getCommonFromAccount().getID());
    } else if ((paramString.equals("TOACCOUNTID")) && (getCommonToAccount() != null) && (localTransferBatch.getCommonToAccount() != null)) {
      i = localCollator.compare(getCommonToAccount().getID(), localTransferBatch.getCommonToAccount().getID());
    } else if ((paramString.equals("TRANSFER_DESTINATION")) && (getCommonDestination() != null) && (localTransferBatch.getCommonDestination() != null)) {
      i = localCollator.compare(getCommonDestination().toLowerCase(), localTransferBatch.getCommonDestination().toLowerCase());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.TransferBatch
 * JD-Core Version:    0.7.0.1
 */