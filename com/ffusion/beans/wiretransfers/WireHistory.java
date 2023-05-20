package com.ffusion.beans.wiretransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.accounts.Account;
import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
import com.ffusion.ffs.bpw.interfaces.WireBatchInfo;
import com.ffusion.ffs.bpw.interfaces.WireHistoryInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Locale;

public class WireHistory
  extends FundsTransaction
  implements WireDefines, Comparable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.wiretransfers.resources";
  public static final String RESOURCE_BUNDLE_2 = "com.ffusion.beans.reporting.wire_status";
  protected DateTime date;
  protected String transType;
  protected int status;
  protected String payeeID;
  protected WireTransferPayee payee;
  protected String destination;
  protected String source;
  protected String customerID;
  protected String userID;
  protected String recurringID;
  protected String templateID;
  protected String fromAccountID;
  protected String fromAccountNumber;
  protected int fromAccountType;
  private boolean Bh = false;
  private boolean Bi = false;
  private FundsTransaction Bj;
  private String Bg;
  
  public WireHistory() {}
  
  public WireHistory(String paramString)
  {
    super(paramString);
  }
  
  public WireHistory(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException("Locale cannot be null");
    }
    this.locale = paramLocale;
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.date = paramDateTime;
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
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.date != null) {
      this.date.setFormat(paramString);
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
    return "";
  }
  
  public String getTransType()
  {
    return this.transType;
  }
  
  public void setTransType(String paramString)
  {
    this.transType = paramString;
  }
  
  public String getStatusName()
  {
    return ResourceUtil.getString("WireStatus" + this.status, "com.ffusion.beans.wiretransfers.resources", this.locale);
  }
  
  public int getStatus()
  {
    return this.status;
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
    catch (Exception localException) {}
  }
  
  public String getPayeeID()
  {
    return this.payeeID;
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeID = paramString;
  }
  
  public WireTransferPayee getPayee()
  {
    return this.payee;
  }
  
  public void setPayee(WireTransferPayee paramWireTransferPayee)
  {
    this.payee = paramWireTransferPayee;
  }
  
  public void setDestination(String paramString)
  {
    this.destination = paramString;
  }
  
  public String getDestination()
  {
    return this.destination;
  }
  
  public void setSource(String paramString)
  {
    this.source = paramString;
  }
  
  public String getSource()
  {
    return this.source;
  }
  
  public String getCustomerID()
  {
    return this.customerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.customerID = paramString;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String paramString)
  {
    this.userID = paramString;
  }
  
  public void setUserID(int paramInt)
  {
    this.userID = String.valueOf(paramInt);
  }
  
  public String getRecurringID()
  {
    return this.recurringID;
  }
  
  public void setRecurringID(String paramString)
  {
    this.recurringID = paramString;
  }
  
  public String getTemplateID()
  {
    return this.templateID;
  }
  
  public void setTemplateID(String paramString)
  {
    this.templateID = paramString;
  }
  
  public String getFromAccountID()
  {
    return this.fromAccountID;
  }
  
  public void setFromAccountID(String paramString)
  {
    this.fromAccountID = paramString;
  }
  
  public String getFromAccountNum()
  {
    return this.fromAccountNumber;
  }
  
  public void setFromAccountNum(String paramString)
  {
    this.fromAccountNumber = paramString;
  }
  
  public int getFromAccountType()
  {
    return this.fromAccountType;
  }
  
  public void setFromAccountType(int paramInt)
  {
    this.fromAccountType = paramInt;
  }
  
  public void setCanEdit(String paramString)
  {
    this.Bh = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanEdit(boolean paramBoolean)
  {
    this.Bh = paramBoolean;
  }
  
  public String getCanEdit()
  {
    return String.valueOf(this.Bh);
  }
  
  public boolean getCanEditValue()
  {
    return this.Bh;
  }
  
  public void setCanDelete(String paramString)
  {
    this.Bi = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setCanDelete(boolean paramBoolean)
  {
    this.Bi = paramBoolean;
  }
  
  public String getCanDelete()
  {
    return String.valueOf(this.Bi);
  }
  
  public boolean getCanDeleteValue()
  {
    return this.Bi;
  }
  
  public void setTransaction(FundsTransaction paramFundsTransaction)
  {
    this.Bj = paramFundsTransaction;
  }
  
  public FundsTransaction getTransaction()
  {
    return this.Bj;
  }
  
  public String getAmountForBPW()
  {
    if (getAmountValue() == null) {
      return "0.00";
    }
    BigDecimal localBigDecimal = getAmountValue().getAmountValue().setScale(2, 5);
    return localBigDecimal.toString();
  }
  
  public String getComment()
  {
    return this.Bg;
  }
  
  public void setComment(String paramString)
  {
    this.Bg = paramString;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equals("PAYEE")) {
      return isFilterable(getPayee().getPayeeName(), paramString2, paramString3);
    }
    if ((paramString1.equals("PAYEEID")) && (getPayee() != null)) {
      return isFilterable(getPayee().getID(), paramString2, paramString3);
    }
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if ((paramString1.equals("DATE")) && (this.date != null)) {
      return this.date.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("FROM_ACCOUNT_ID")) && (getFromAccountID() != null)) {
      return isFilterable(getFromAccountID(), paramString2, paramString3);
    }
    if ((paramString1.equals("WIRE_DESTINATION")) && (getDestination() != null)) {
      return isFilterable(getDestination(), paramString2, paramString3);
    }
    if ((paramString1.equals("WIRE_SOURCE")) && (getSource() != null)) {
      return isFilterable(getSource(), paramString2, paramString3);
    }
    if ((paramString1.equals("TRANS_TYPE")) && (getTransType() != null)) {
      return isFilterable(getTransType(), paramString2, paramString3);
    }
    if ((paramString1.equals("TEMPLATE_ID")) && (getTemplateID() != null)) {
      return isFilterable(getTemplateID(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public Object clone()
  {
    try
    {
      WireHistory localWireHistory = (WireHistory)super.clone();
      if (this.payee != null) {
        localWireHistory.setPayee((WireTransferPayee)this.payee.clone());
      }
      if (this.date != null) {
        localWireHistory.setDate((DateTime)this.date.clone());
      }
      return localWireHistory;
    }
    catch (Exception localException) {}
    return super.clone();
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "DATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    WireHistory localWireHistory = (WireHistory)paramObject;
    int i = 1;
    if (paramString.equals("PAYEE"))
    {
      String str1;
      if (getPayee() != null) {
        str1 = getPayee().getPayeeName();
      } else if (getTransType().equals("BATCH")) {
        str1 = "BATCH";
      } else {
        str1 = "HOST";
      }
      String str2;
      if (localWireHistory.getPayee() != null) {
        str2 = localWireHistory.getPayee().getPayeeName();
      } else if (localWireHistory.getTransType().equals("BATCH")) {
        str2 = "BATCH";
      } else {
        str2 = "HOST";
      }
      i = str1.compareToIgnoreCase(str2);
    }
    else if ((paramString.equals("AMOUNT")) && (this.amount != null) && (localWireHistory.getAmountValue() != null))
    {
      i = this.amount.compareTo(localWireHistory.getAmountValue());
    }
    else if ((paramString.equals("PAYEEID")) && (getPayeeID() != null) && (localWireHistory.getPayeeID() != null))
    {
      i = numStringCompare(getPayeeID(), localWireHistory.getPayeeID());
    }
    else if ((paramString.equals("CUSTOMER_ID")) && (getCustomerID() != null) && (localWireHistory.getCustomerID() != null))
    {
      i = numStringCompare(getCustomerID(), localWireHistory.getCustomerID());
    }
    else if ((paramString.equals("USERID")) && (getUserID() != null) && (localWireHistory.getUserID() != null))
    {
      i = getUserID().compareTo(localWireHistory.getUserID());
    }
    else if ((paramString.equals("RECURRING_ID")) && (getRecurringID() != null) && (localWireHistory.getRecurringID() != null))
    {
      i = numStringCompare(getRecurringID(), localWireHistory.getRecurringID());
    }
    else if ((paramString.equals("DATE")) && (getDateValue() != null) && (localWireHistory.getDateValue() != null))
    {
      i = this.date.equals(localWireHistory.getDateValue()) ? 0 : this.date.before(localWireHistory.getDateValue()) ? -1 : 1;
    }
    else if ((paramString.equals("FROM_ACCOUNT_ID")) && (getFromAccountID() != null) && (localWireHistory.getFromAccountID() != null))
    {
      i = getFromAccountID().compareTo(localWireHistory.getFromAccountID());
    }
    else if (paramString.equals("STATUS"))
    {
      i = getStatus() - localWireHistory.getStatus();
    }
    else if ((paramString.equals("WIRE_DESTINATION")) && (getDestination() != null) && (localWireHistory.getDestination() != null))
    {
      i = getDestination().compareToIgnoreCase(localWireHistory.getDestination());
    }
    else if ((paramString.equals("WIRE_SOURCE")) && (getSource() != null) && (localWireHistory.getSource() != null))
    {
      i = getSource().compareToIgnoreCase(localWireHistory.getSource());
    }
    else if ((paramString.equals("TRANS_TYPE")) && (getTransType() != null) && (localWireHistory.getTransType() != null))
    {
      i = getTransType().compareToIgnoreCase(localWireHistory.getTransType());
    }
    else if ((paramString.equals("TEMPLATE_ID")) && (getTemplateID() != null) && (localWireHistory.getTemplateID() != null))
    {
      i = getTemplateID().compareTo(localWireHistory.getTemplateID());
    }
    else
    {
      if (paramString.equals("TRANSACTION_INDEX"))
      {
        if (getTransactionIndex() < localWireHistory.getTransactionIndex()) {
          return -1;
        }
        if (getTransactionIndex() == localWireHistory.getTransactionIndex()) {
          return 0;
        }
        return 1;
      }
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "WIRE_HISTORY");
    XMLHandler.appendTag(localStringBuffer, "PAYEEID", getPayeeID());
    if (getPayee() != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "PAYEE");
      localStringBuffer.append(getPayee().getXML());
      XMLHandler.appendEndTag(localStringBuffer, "PAYEE");
    }
    XMLHandler.appendTag(localStringBuffer, "CUSTOMER_ID", getCustomerID());
    XMLHandler.appendTag(localStringBuffer, "USERID", getUserID());
    XMLHandler.appendTag(localStringBuffer, "RECURRING_ID", getRecurringID());
    XMLHandler.appendTag(localStringBuffer, "FROM_ACCOUNT_ID", getFromAccountID());
    XMLHandler.appendTag(localStringBuffer, "DATE", getDate());
    XMLHandler.appendTag(localStringBuffer, "STATUS", getStatus());
    XMLHandler.appendTag(localStringBuffer, "TRANS_TYPE", getTransType());
    XMLHandler.appendTag(localStringBuffer, "WIRE_DESTINATION", getDestination());
    XMLHandler.appendTag(localStringBuffer, "WIRE_SOURCE", getSource());
    XMLHandler.appendTag(localStringBuffer, "STATUS", getStatus());
    XMLHandler.appendTag(localStringBuffer, "COMMENT", getComment());
    XMLHandler.appendTag(localStringBuffer, "TEMPLATE_ID", getTemplateID());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "WIRE_HISTORY");
    return localStringBuffer.toString();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("TRANS_TYPE")) {
      setTransType(paramString2);
    } else if (paramString1.equalsIgnoreCase("RECURRING_ID")) {
      setRecurringID(paramString2);
    } else if (paramString1.equalsIgnoreCase("PAYEEID")) {
      setPayeeID(paramString2);
    } else if ((paramString1.equalsIgnoreCase("PAYEE")) && (this.payee != null)) {
      this.payee.setPayeeName(paramString2);
    } else if (paramString1.equals("CUSTOMER_ID")) {
      setCustomerID(paramString2);
    } else if (paramString1.equals("USERID")) {
      setUserID(paramString2);
    } else if (paramString1.equalsIgnoreCase("FROM_ACCOUNT_ID")) {
      setFromAccountID(paramString2);
    } else if (paramString1.equalsIgnoreCase("TEMPLATE_ID")) {
      setTemplateID(paramString2);
    } else if (paramString1.equalsIgnoreCase("FROM_ACCOUNT_NUMBER")) {
      setFromAccountNum(paramString2);
    } else if (paramString1.equalsIgnoreCase("FROM_ACCOUNT_TYPE")) {
      try
      {
        setFromAccountType(Integer.parseInt(paramString2));
      }
      catch (Exception localException)
      {
        setFromAccountType(0);
      }
    } else if (paramString1.equalsIgnoreCase("DATE")) {
      setDate(paramString2);
    } else if (paramString1.equalsIgnoreCase("STATUS")) {
      setStatus(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_DESTINATION")) {
      setDestination(paramString2);
    } else if (paramString1.equalsIgnoreCase("WIRE_SOURCE")) {
      setSource(paramString2);
    } else if (paramString1.equalsIgnoreCase("COMMENT")) {
      setComment(paramString2);
    } else {
      return super.set(paramString1, paramString2);
    }
    return true;
  }
  
  public void set(WireHistory paramWireHistory)
  {
    if (paramWireHistory == null) {
      return;
    }
    super.set(paramWireHistory);
    if (paramWireHistory.getDateValue() != null) {
      setDate((DateTime)paramWireHistory.getDateValue().clone());
    } else {
      setDate((DateTime)null);
    }
    setTransType(paramWireHistory.getTransType());
    setRecurringID(paramWireHistory.getRecurringID());
    setCustomerID(paramWireHistory.getCustomerID());
    setUserID(paramWireHistory.getUserID());
    setPayeeID(paramWireHistory.getPayeeID());
    if (paramWireHistory.getPayee() != null) {
      setPayee((WireTransferPayee)paramWireHistory.getPayee().clone());
    }
    setFromAccountID(paramWireHistory.getFromAccountID());
    setFromAccountNum(paramWireHistory.getFromAccountNum());
    setFromAccountType(paramWireHistory.getFromAccountType());
    setStatus(paramWireHistory.getStatus());
    setDestination(paramWireHistory.getDestination());
    setSource(paramWireHistory.getSource());
    setCanEdit(paramWireHistory.getCanEditValue());
    setCanDelete(paramWireHistory.getCanDeleteValue());
    setComment(paramWireHistory.getComment());
    setTemplateID(paramWireHistory.getTemplateID());
    setVersion(paramWireHistory.getVersion());
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public WireHistoryInfo getWireHistoryInfo()
  {
    WireHistoryInfo localWireHistoryInfo = new WireHistoryInfo();
    localWireHistoryInfo.setCursorId(String.valueOf(getTransactionIndex()));
    localWireHistoryInfo.setTransType(this.transType);
    localWireHistoryInfo.setStatus(WireStatusMap.mapToStatusToStr(this.status));
    localWireHistoryInfo.setId(this.id);
    localWireHistoryInfo.setExtId(this.trackingID);
    localWireHistoryInfo.setPayeeId(this.payeeID);
    localWireHistoryInfo.setFromAcctId(this.fromAccountID);
    localWireHistoryInfo.setAmount(getAmountForBPW());
    localWireHistoryInfo.setDestination(this.destination);
    localWireHistoryInfo.setSource(this.source);
    localWireHistoryInfo.setBusinessId(this.customerID);
    localWireHistoryInfo.setUserId(this.userID);
    localWireHistoryInfo.setTemplateId(this.templateID);
    localWireHistoryInfo.setVersion(getVersion());
    if (this.payee != null) {
      localWireHistoryInfo.setPayeeInfo(this.payee.getWirePayeeInfo());
    }
    return localWireHistoryInfo;
  }
  
  public void setWireHistoryInfo(WireHistoryInfo paramWireHistoryInfo)
  {
    if (paramWireHistoryInfo == null) {
      return;
    }
    setDateFormat("yyyyMMdd");
    String str = paramWireHistoryInfo.getDate();
    if ((str != null) && (str.length() > 8)) {
      str = str.substring(0, 8);
    }
    setDate(str);
    setDateFormat("SHORT");
    setTransType(paramWireHistoryInfo.getTransType());
    setTransactionIndex(paramWireHistoryInfo.getCursorId());
    if ("SINGLE".equals(paramWireHistoryInfo.getTransType())) {
      setType(5);
    } else if ("RECURRING".equals(paramWireHistoryInfo.getTransType())) {
      setType(6);
    } else if ("BATCH".equals(paramWireHistoryInfo.getTransType())) {
      setType(14);
    }
    Object localObject1 = paramWireHistoryInfo.getWireObject();
    if (localObject1 != null)
    {
      Object localObject2;
      if ((localObject1 instanceof WireInfo))
      {
        localObject2 = new WireTransfer();
        ((WireTransfer)localObject2).setWireInfo((WireInfo)localObject1);
        setTransaction((FundsTransaction)localObject2);
      }
      else if ((localObject1 instanceof RecWireInfo))
      {
        localObject2 = new WireTransfer();
        ((WireTransfer)localObject2).setRecWireInfo((RecWireInfo)localObject1);
        setTransaction((FundsTransaction)localObject2);
      }
      else if ((localObject1 instanceof WireBatchInfo))
      {
        localObject2 = new WireBatch();
        ((WireBatch)localObject2).setBatchInfo((WireBatchInfo)localObject1);
        setTransaction((FundsTransaction)localObject2);
      }
      else
      {
        DebugLog.log("WireHistory.setWireHistoryInfo: Unknown object in WireHistoryInfo");
      }
    }
    setStatus(WireStatusMap.mapStatusToInt(paramWireHistoryInfo.getStatus()));
    setID(paramWireHistoryInfo.getId());
    setRecurringID(paramWireHistoryInfo.getRecId());
    setTrackingID(paramWireHistoryInfo.getExtId());
    setPayeeID(paramWireHistoryInfo.getPayeeId());
    if (paramWireHistoryInfo.getPayeeInfo() != null)
    {
      this.payee = new WireTransferPayee();
      this.payee.setWirePayeeInfo(paramWireHistoryInfo.getPayeeInfo());
    }
    setFromAccountID(paramWireHistoryInfo.getFromAcctId());
    setFromAccountNum(paramWireHistoryInfo.getFromAcctNum());
    setFromAccountType(WireAccountMap.mapAccountTypeToInt(paramWireHistoryInfo.getFromAcctType()));
    setAmount(paramWireHistoryInfo.getAmount());
    setDestination(paramWireHistoryInfo.getDestination());
    setSource(paramWireHistoryInfo.getSource());
    setCustomerID(paramWireHistoryInfo.getBusinessId());
    setUserID(paramWireHistoryInfo.getUserId());
    setSubmittedBy(paramWireHistoryInfo.getUserId());
    setTemplateID(paramWireHistoryInfo.getTemplateId());
    setVersion(paramWireHistoryInfo.getVersion());
  }
  
  public void setFromWireTransfer(WireTransfer paramWireTransfer)
  {
    if (paramWireTransfer == null) {
      return;
    }
    setDate(paramWireTransfer.getDateToPostValue());
    setID(paramWireTransfer.getID());
    setAmount(paramWireTransfer.getAmountValue());
    setTrackingID(paramWireTransfer.getTrackingID());
    if (paramWireTransfer.getType() == 6)
    {
      setTransType("RECURRING");
      setRecurringID(paramWireTransfer.getRecurringID());
    }
    else
    {
      setTransType("SINGLE");
    }
    setType(paramWireTransfer.getType());
    setStatus(paramWireTransfer.getStatus());
    setPayeeID(paramWireTransfer.getWirePayeeID());
    setPayee(paramWireTransfer.getWirePayee());
    setFromAccountID(paramWireTransfer.getFromAccountID());
    setFromAccountNum(paramWireTransfer.getFromAccountNum());
    setFromAccountType(paramWireTransfer.getFromAccountType());
    setDestination(paramWireTransfer.getWireDestination());
    setSource(paramWireTransfer.getWireSource());
    setCustomerID(paramWireTransfer.getCustomerID());
    setUserID(paramWireTransfer.getUserID());
    setSubmittedBy(paramWireTransfer.getSubmittedBy());
    setCanEdit(paramWireTransfer.getCanEditValue());
    setCanDelete(paramWireTransfer.getCanDeleteValue());
    setApproverIsGroup(paramWireTransfer.getApproverIsGroup());
    setApproverName(paramWireTransfer.getApproverName());
    setRejectReason(paramWireTransfer.getRejectReason());
    setUserName(paramWireTransfer.getUserName());
    setTransaction(paramWireTransfer);
    setTemplateID(paramWireTransfer.getTemplateID());
    setVersion(paramWireTransfer.getVersion());
    if (paramWireTransfer.getApprovalID() != null) {
      setApprovalID(paramWireTransfer.getApprovalID());
    }
  }
  
  public void setFromWireBatch(WireBatch paramWireBatch)
  {
    if (paramWireBatch == null) {
      return;
    }
    setDate(paramWireBatch.getDateToPostValue());
    setID(paramWireBatch.getID());
    setAmount(paramWireBatch.getAmountValue());
    setTrackingID(paramWireBatch.getTrackingID());
    setTransType("BATCH");
    setType(paramWireBatch.getType());
    setStatus(paramWireBatch.getStatus());
    setPayeeID("BATCH");
    setPayee(new WireTransferPayee());
    this.payee.setPayeeName("BATCH");
    setFromAccountID("BATCH");
    setFromAccountNum("BATCH");
    setFromAccountType(0);
    setDestination(paramWireBatch.getBatchDestination());
    setSource(paramWireBatch.getBatchType());
    setCustomerID(paramWireBatch.getCustomerID());
    setUserID(paramWireBatch.getUserID());
    setSubmittedBy(paramWireBatch.getSubmittedBy());
    setCanEdit(paramWireBatch.getCanEditValue());
    setCanDelete(paramWireBatch.getCanDeleteValue());
    setApproverIsGroup(paramWireBatch.getApproverIsGroup());
    setApproverName(paramWireBatch.getApproverName());
    setRejectReason(paramWireBatch.getRejectReason());
    setUserName(paramWireBatch.getUserName());
    setTransaction(paramWireBatch);
    setVersion(paramWireBatch.getVersion());
  }
  
  public void setWireHistoryFromWireInfo(WireInfo paramWireInfo)
  {
    setDateFormat("yyyyMMdd");
    String str1 = paramWireInfo.getDateToPost();
    if ((str1 != null) && (str1.length() > 8)) {
      str1 = str1.substring(0, 8);
    }
    setDate(str1);
    setDateFormat("SHORT");
    setTransType(paramWireInfo.getWireType());
    setID(paramWireInfo.getSrvrTid());
    setAmount(paramWireInfo.getAmount());
    setTrackingID(paramWireInfo.getExtId());
    setStatus(WireStatusMap.mapStatusToInt(paramWireInfo.getPrcStatus()));
    setPayeeID(paramWireInfo.getWirePayeeId());
    int i = WireAccountMap.mapAccountTypeToInt(paramWireInfo.getFromAcctType());
    String str2 = paramWireInfo.getFromAcctId();
    Account localAccount = new Account();
    localAccount.setID(str2, Integer.toString(i));
    setFromAccountID(localAccount.getID());
    setFromAccountNum(localAccount.getNumber());
    setFromAccountType(localAccount.getTypeValue());
    setDestination(paramWireInfo.getWireDest());
    setSource(paramWireInfo.getWireSource());
    setCustomerID(paramWireInfo.getCustomerID());
    setSubmittedBy(paramWireInfo.getUserId());
    setTemplateID(paramWireInfo.getTemplateId());
    setVersion(paramWireInfo.getVersion());
    Hashtable localHashtable = paramWireInfo.getExtInfo();
    if (localHashtable != null)
    {
      String str3 = (String)localHashtable.get("WIRE_OLDSTATUS");
      str3 = ResourceUtil.getString(str3, "com.ffusion.beans.reporting.wire_status", this.locale);
      put("WIRE_OLDSTATUS", str3);
    }
    int j = paramWireInfo.getStatusCode();
    if (j != 0)
    {
      setComment(jdMethod_int(j));
    }
    else
    {
      String str4 = ResourceUtil.getString("Msg5", "com.ffusion.beans.wiretransfers.resources", this.locale);
      setComment(str4);
    }
  }
  
  private String jdMethod_int(int paramInt)
  {
    int i = 0;
    switch (paramInt)
    {
    case 17521: 
      i = 1;
      break;
    case 17523: 
      i = 2;
      break;
    case 17524: 
      i = 3;
      break;
    case 17522: 
    default: 
      i = 4;
    }
    String str = ResourceUtil.getString("Msg" + i, "com.ffusion.beans.wiretransfers.resources", this.locale);
    return str;
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equalsIgnoreCase("PAYEE") == true)
      {
        WireTransferPayee localWireTransferPayee = new WireTransferPayee();
        WireHistory.this.setPayee(localWireTransferPayee);
        localWireTransferPayee.continueXMLParsing(getHandler());
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
      WireHistory.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.wiretransfers.WireHistory
 * JD-Core Version:    0.7.0.1
 */