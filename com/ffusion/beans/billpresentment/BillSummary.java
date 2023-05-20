package com.ffusion.beans.billpresentment;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;

public class BillSummary
  extends ExtendABean
  implements Comparable, Serializable
{
  public static final String STATEMENTURL = "STATEMENTURL";
  public static final String CREATEDDATE = "CREATEDDATE";
  public static final String VIEWDATE = "VIEWDATE";
  public static final String BILLSUMMARY = "BILLSUMMARY";
  public static final String BILLSUMMARYID = "BILLSUMMARYID";
  public static final String ACCOUNTID = "ACCOUNTID";
  public static final String CONSUMERID = "CONSUMERID";
  public static final String BILLID = "BILLID";
  public static final String STATUSCODE = "STATUSCODE";
  public static final String STATEMENTBEGINDATE = "STATEMENTBEGINDATE";
  public static final String STATEMENTENDDATE = "STATEMENTENDDATE";
  public static final String BILLDATE = "BILLDATE";
  public static final String PAYMENTDUEDATE = "PAYMENTDUEDATE";
  public static final String AMOUNTDUE = "AMOUNTDUE";
  public static final String BALANCE = "BALANCE";
  public static final String PREVIOUSBALANCE = "PREVIOUSBALANCE";
  public static final String MINIMUMAMOUNTDUE = "MINIMUMAMOUNTDUE";
  public static final String PAYMENTACCOUNTID = "PAYMENTACCOUNTID";
  public static final String PAYMENTDATE = "PAYMENTDATE";
  public static final String AMOUNTPAID = "AMOUNTPAID";
  public static final String PAYMENTINFOS = "PAYMENTINFOS";
  public static final String BILLREFINFO = "BILLREFINFO";
  public static final String ACTIVITY = "ACTIVITY";
  public static final String NEW = "NEW";
  public static final String UNVIEWED = "UNVIEW";
  public static final String VIEWED = "VIEWED";
  public static final String FILED = "FILED";
  public static final String AUTOFILED = "AFILED";
  public static final String DELETED = "DELETE";
  public static final String MARKED = "MARKED";
  public static final String ERROR = "ERROR";
  public static final String PENDING = "PEND";
  private String JV;
  private DateTime JS;
  private DateTime J0;
  private String JW;
  private long JZ;
  private long JI;
  private String JM;
  private String JJ;
  private DateTime JX;
  private DateTime JY;
  private DateTime J2;
  private DateTime JK;
  private Currency J3;
  private Currency JT;
  private Currency JN;
  private Currency JU;
  private String JR;
  private DateTime JO;
  private Currency J1;
  private String JQ;
  private Currency JL;
  private PaymentInfos JP;
  protected String trackingID;
  
  public BillSummary()
  {
    this.datetype = "SHORT";
    this.JP = new PaymentInfos();
  }
  
  public BillSummary(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
    this.JP = new PaymentInfos(this.locale);
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "BILLSUMMARYID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    BillSummary localBillSummary = (BillSummary)paramObject;
    int i = 1;
    if ((paramString.equals("BILLSUMMARYID")) && (this.JW != null) && (localBillSummary.getBillSummaryID() != null)) {
      i = this.JW.compareTo(localBillSummary.getBillSummaryID());
    } else if (paramString.equals("ACCOUNTID")) {
      i = (int)(this.JZ - localBillSummary.getAccountIDValue());
    } else if (paramString.equals("CONSUMERID")) {
      i = (int)(this.JI - localBillSummary.getAccountIDValue());
    } else if ((paramString.equals("BILLID")) && (this.JM != null) && (localBillSummary.getBillID() != null)) {
      i = this.JM.compareTo(localBillSummary.getBillID());
    } else if ((paramString.equals("STATUSCODE")) && (this.JJ != null) && (localBillSummary.getStatusCode() != null)) {
      i = this.JJ.compareTo(localBillSummary.getStatusCode());
    } else if ((paramString.equals("BILLREFINFO")) && (this.JQ != null) && (localBillSummary.getBillRefInfo() != null)) {
      i = this.JQ.compareTo(localBillSummary.getBillRefInfo());
    } else if ((paramString.equals("STATEMENTBEGINDATE")) && (this.JX != null) && (localBillSummary.getStatementBeginDateValue() != null)) {
      i = this.JX.equals(localBillSummary.getStatementBeginDateValue()) ? 0 : this.JX.before(localBillSummary.getStatementBeginDateValue()) ? -1 : 1;
    } else if ((paramString.equals("STATEMENTENDDATE")) && (this.JY != null) && (localBillSummary.getStatementEndDateValue() != null)) {
      i = this.JY.equals(localBillSummary.getStatementEndDateValue()) ? 0 : this.JY.before(localBillSummary.getStatementEndDateValue()) ? -1 : 1;
    } else if ((paramString.equals("BILLDATE")) && (this.J2 != null) && (localBillSummary.getBillDateValue() != null)) {
      i = this.J2.equals(localBillSummary.getBillDateValue()) ? 0 : this.J2.before(localBillSummary.getBillDateValue()) ? -1 : 1;
    } else if ((paramString.equals("PAYMENTDUEDATE")) && (this.JK != null) && (localBillSummary.getPaymentDueDateValue() != null)) {
      i = this.JK.equals(localBillSummary.getPaymentDueDateValue()) ? 0 : this.JK.before(localBillSummary.getPaymentDueDateValue()) ? -1 : 1;
    } else if ((paramString.equals("AMOUNTDUE")) && (this.J3 != null) && (localBillSummary.getAmountDueValue() != null)) {
      i = this.J3.compareTo(localBillSummary.getAmountDueValue());
    } else if ((paramString.equals("BALANCE")) && (this.JT != null) && (localBillSummary.getBalanceValue() != null)) {
      i = this.JT.compareTo(localBillSummary.getBalanceValue());
    } else if ((paramString.equals("PREVIOUSBALANCE")) && (this.JN != null) && (localBillSummary.getPreviousBalanceValue() != null)) {
      i = this.JN.compareTo(localBillSummary.getPreviousBalanceValue());
    } else if ((paramString.equals("MINIMUMAMOUNTDUE")) && (this.JU != null) && (localBillSummary.getMinimumAmountDueValue() != null)) {
      i = this.JU.compareTo(localBillSummary.getMinimumAmountDueValue());
    } else if ((paramString.equals("ACTIVITY")) && (this.JL != null) && (localBillSummary.getActivityValue() != null)) {
      i = this.JL.compareTo(localBillSummary.getActivityValue());
    } else if ((paramString.equals("PAYMENTACCOUNTID")) && (getPaymentAccountID() != null) && (localBillSummary.getPaymentAccountID() != null)) {
      i = getPaymentAccountID().compareTo(localBillSummary.getPaymentAccountID());
    } else if ((paramString.equals("PAYMENTDATE")) && (this.JO != null) && (localBillSummary.getPaymentDateValue() != null)) {
      i = this.JO.equals(localBillSummary.getPaymentDateValue()) ? 0 : this.JO.before(localBillSummary.getPaymentDateValue()) ? -1 : 1;
    } else if (paramString.equals("AMOUNTPAID")) {
      i = this.J1.compareTo(localBillSummary.getAmountPaidValue());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void setActivity(String paramString)
  {
    if (this.JL == null) {
      this.JL = new Currency(paramString, this.locale);
    } else {
      this.JL.fromString(paramString);
    }
  }
  
  public final void setActivity(Currency paramCurrency)
  {
    this.JL = paramCurrency;
  }
  
  public final Currency getActivityValue()
  {
    return this.JL;
  }
  
  public final String getActivity()
  {
    if (this.JL != null) {
      return this.JL.toString();
    }
    return "";
  }
  
  public final void setBillRefInfo(String paramString)
  {
    this.JQ = paramString;
  }
  
  public final String getBillRefInfo()
  {
    return this.JQ;
  }
  
  public final void setPaymentAccountID(String paramString)
  {
    this.JR = paramString;
  }
  
  public final String getPaymentAccountID()
  {
    return this.JR;
  }
  
  public void setPaymentDate(String paramString)
  {
    try
    {
      if (this.JO == null) {
        this.JO = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.JO.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.JO = null;
    }
  }
  
  public final void setPaymentDate(DateTime paramDateTime)
  {
    this.JO = paramDateTime;
  }
  
  public final DateTime getPaymentDateValue()
  {
    return this.JO;
  }
  
  public final String getPaymentDate()
  {
    if (this.JO != null) {
      return this.JO.toString();
    }
    return "";
  }
  
  public void setAmountPaid(String paramString)
  {
    if (this.J1 == null) {
      this.J1 = new Currency(paramString, this.locale);
    } else {
      this.J1.fromString(paramString);
    }
  }
  
  public final void setAmountPaid(Currency paramCurrency)
  {
    this.J1 = paramCurrency;
  }
  
  public final Currency getAmountPaidValue()
  {
    return this.J1;
  }
  
  public final String getAmountPaid()
  {
    if (this.J1 != null) {
      return this.J1.toString();
    }
    return "";
  }
  
  public final void setStatementURL(String paramString)
  {
    this.JV = paramString;
  }
  
  public final String getStatementURL()
  {
    return this.JV;
  }
  
  public final void setCreatedDate(DateTime paramDateTime)
  {
    this.JS = paramDateTime;
  }
  
  public void setCreatedDate(String paramString)
  {
    try
    {
      if (this.JS == null) {
        this.JS = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.JS.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.JS = null;
    }
  }
  
  public final DateTime getCreatedDateValue()
  {
    return this.JS;
  }
  
  public final String getCreatedDate()
  {
    if (this.JS != null) {
      return this.JS.toString();
    }
    return "";
  }
  
  public final void setViewDate(DateTime paramDateTime)
  {
    this.J0 = paramDateTime;
  }
  
  public void setViewDate(String paramString)
  {
    try
    {
      if (this.J0 == null) {
        this.J0 = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.J0.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.J0 = null;
    }
  }
  
  public final DateTime getViewDateValue()
  {
    return this.J0;
  }
  
  public final String getViewDate()
  {
    if (this.J0 != null) {
      return this.J0.toString();
    }
    return "";
  }
  
  public final void setBillSummaryID(String paramString)
  {
    this.JW = paramString;
  }
  
  public final String getBillSummaryID()
  {
    return this.JW;
  }
  
  public final void setAccountID(String paramString)
  {
    try
    {
      this.JZ = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting AccountID:", localException);
    }
  }
  
  public final void setAccountID(long paramLong)
  {
    this.JZ = paramLong;
  }
  
  public final long getAccountIDValue()
  {
    return this.JZ;
  }
  
  public final String getAccountID()
  {
    return String.valueOf(this.JZ);
  }
  
  public final void setConsumerID(String paramString)
  {
    try
    {
      this.JI = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting ConsumerID:", localException);
    }
  }
  
  public final void setConsumerID(long paramLong)
  {
    this.JI = paramLong;
  }
  
  public final long getConsumerIDValue()
  {
    return this.JI;
  }
  
  public final String getConsumerID()
  {
    return String.valueOf(this.JI);
  }
  
  public final void setBillID(String paramString)
  {
    this.JM = paramString;
  }
  
  public final String getBillID()
  {
    return this.JM;
  }
  
  public final void setStatusCode(String paramString)
  {
    this.JJ = paramString;
  }
  
  public final String getStatusCode()
  {
    return this.JJ;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.J3 != null) {
      this.J3.setLocale(paramLocale);
    }
    if (this.JT != null) {
      this.JT.setLocale(paramLocale);
    }
    if (this.JN != null) {
      this.JN.setLocale(paramLocale);
    }
    if (this.JU != null) {
      this.JU.setLocale(paramLocale);
    }
    if (this.J1 != null) {
      this.J1.setLocale(paramLocale);
    }
    if (this.JL != null) {
      this.JL.setLocale(paramLocale);
    }
    if (this.JS != null) {
      this.JS.setLocale(paramLocale);
    }
    if (this.J0 != null) {
      this.J0.setLocale(paramLocale);
    }
    if (this.JX != null) {
      this.JX.setLocale(paramLocale);
    }
    if (this.JY != null) {
      this.JY.setLocale(paramLocale);
    }
    if (this.J2 != null) {
      this.J2.setLocale(paramLocale);
    }
    if (this.JK != null) {
      this.JK.setLocale(paramLocale);
    }
    if (this.JO != null) {
      this.JO.setLocale(paramLocale);
    }
    if (this.JP != null) {
      this.JP.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.JP != null) {
      this.JP.setDateFormat(paramString);
    }
    if (this.JS != null) {
      this.JS.setFormat(paramString);
    }
    if (this.J0 != null) {
      this.J0.setFormat(paramString);
    }
    if (this.JX != null) {
      this.JX.setFormat(paramString);
    }
    if (this.JY != null) {
      this.JY.setFormat(paramString);
    }
    if (this.J2 != null) {
      this.J2.setFormat(paramString);
    }
    if (this.JK != null) {
      this.JK.setFormat(paramString);
    }
    if (this.JO != null) {
      this.JO.setFormat(paramString);
    }
  }
  
  public final void setBillDate(DateTime paramDateTime)
  {
    this.J2 = paramDateTime;
  }
  
  public void setBillDate(String paramString)
  {
    try
    {
      if (this.J2 == null) {
        this.J2 = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.J2.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.J2 = null;
    }
  }
  
  public final DateTime getBillDateValue()
  {
    return this.J2;
  }
  
  public final String getBillDate()
  {
    if (this.J2 != null) {
      return this.J2.toString();
    }
    return "";
  }
  
  public void setPaymentDueDate(DateTime paramDateTime)
  {
    this.JK = paramDateTime;
  }
  
  public void setPaymentDueDate(String paramString)
  {
    try
    {
      if (this.JK == null) {
        this.JK = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.JK.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.JK = null;
    }
  }
  
  public final DateTime getPaymentDueDateValue()
  {
    return this.JK;
  }
  
  public final String getPaymentDueDate()
  {
    if (this.JK != null) {
      return this.JK.toString();
    }
    return "";
  }
  
  public final void setAmountDue(Currency paramCurrency)
  {
    this.J3 = paramCurrency;
  }
  
  public void setAmountDue(String paramString)
  {
    if (this.J3 == null) {
      this.J3 = new Currency(paramString, this.locale);
    } else {
      this.J3.fromString(paramString);
    }
  }
  
  public final Currency getAmountDueValue()
  {
    return this.J3;
  }
  
  public final String getAmountDue()
  {
    if (this.J3 != null) {
      return this.J3.toString();
    }
    return "";
  }
  
  public final void setBalance(Currency paramCurrency)
  {
    this.JT = paramCurrency;
  }
  
  public final void setBalance(String paramString)
  {
    if (this.JT == null) {
      this.JT = new Currency(paramString, this.locale);
    } else {
      this.JT.fromString(paramString);
    }
  }
  
  public final Currency getBalanceValue()
  {
    return this.JT;
  }
  
  public final String getBalance()
  {
    if (this.JT != null) {
      return this.JT.toString();
    }
    return "";
  }
  
  public final void setPreviousBalance(Currency paramCurrency)
  {
    this.JN = paramCurrency;
  }
  
  public final void setPreviousBalance(String paramString)
  {
    if (this.JN == null) {
      this.JN = new Currency(paramString, this.locale);
    } else {
      this.JN.fromString(paramString);
    }
  }
  
  public final Currency getPreviousBalanceValue()
  {
    return this.JN;
  }
  
  public final String getPreviousBalance()
  {
    if (this.JN != null) {
      return this.JN.toString();
    }
    return "";
  }
  
  public final void setMinimumAmountDue(Currency paramCurrency)
  {
    this.JU = paramCurrency;
  }
  
  public final void setMinimumAmountDue(String paramString)
  {
    if (this.JU == null) {
      this.JU = new Currency(paramString, this.locale);
    } else {
      this.JU.fromString(paramString);
    }
  }
  
  public final Currency getMinimumAmountDueValue()
  {
    return this.JU;
  }
  
  public final String getMinimumAmountDue()
  {
    if (this.JU != null) {
      return this.JU.toString();
    }
    return "";
  }
  
  public final void setStatementBeginDate(DateTime paramDateTime)
  {
    this.JX = paramDateTime;
  }
  
  public void setStatementBeginDate(String paramString)
  {
    try
    {
      if (this.JX == null) {
        this.JX = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.JX.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.JX = null;
    }
  }
  
  public final DateTime getStatementBeginDateValue()
  {
    return this.JX;
  }
  
  public final String getStatementBeginDate()
  {
    if (this.JX != null) {
      return this.JX.toString();
    }
    return "";
  }
  
  public final void setStatementEndDate(DateTime paramDateTime)
  {
    this.JY = paramDateTime;
  }
  
  public void setStatementEndDate(String paramString)
  {
    try
    {
      if (this.JY == null) {
        this.JY = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.JY.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.JY = null;
    }
  }
  
  public final DateTime getStatementEndDateValue()
  {
    return this.JY;
  }
  
  public final String getStatementEndDate()
  {
    if (this.JY != null) {
      return this.JY.toString();
    }
    return "";
  }
  
  public final void setPaymentInfos(PaymentInfos paramPaymentInfos)
  {
    this.JP = paramPaymentInfos;
  }
  
  public final PaymentInfos getPaymentInfos()
  {
    return this.JP;
  }
  
  public void setClearPaymentFields(String paramString)
  {
    setClearPaymentFields(Boolean.valueOf(paramString).booleanValue());
  }
  
  public void setClearPaymentFields(boolean paramBoolean)
  {
    if (paramBoolean == true)
    {
      this.JR = null;
      this.JO = null;
      this.J1 = null;
    }
  }
  
  public void set(BillSummary paramBillSummary)
  {
    setStatementURL(paramBillSummary.getStatementURL());
    if (paramBillSummary.getCreatedDateValue() != null) {
      setCreatedDate((DateTime)paramBillSummary.getCreatedDateValue().clone());
    } else {
      setCreatedDate((DateTime)null);
    }
    if (paramBillSummary.getViewDateValue() != null) {
      setViewDate((DateTime)paramBillSummary.getViewDateValue().clone());
    } else {
      setViewDate((DateTime)null);
    }
    setBillSummaryID(paramBillSummary.getBillSummaryID());
    setAccountID(paramBillSummary.getAccountIDValue());
    setConsumerID(paramBillSummary.getConsumerIDValue());
    setBillID(paramBillSummary.getBillID());
    setStatusCode(paramBillSummary.getStatusCode());
    setLocale(paramBillSummary.locale);
    setDateFormat(paramBillSummary.getDateFormat());
    if (paramBillSummary.getBillDateValue() != null) {
      setBillDate((DateTime)paramBillSummary.getBillDateValue().clone());
    } else {
      setBillDate((DateTime)null);
    }
    if (paramBillSummary.getPaymentDueDateValue() != null) {
      setPaymentDueDate((DateTime)paramBillSummary.getPaymentDueDateValue().clone());
    } else {
      setPaymentDueDate((DateTime)null);
    }
    if (paramBillSummary.getAmountDueValue() != null) {
      setAmountDue((Currency)paramBillSummary.getAmountDueValue().clone());
    } else {
      setAmountDue((Currency)null);
    }
    if (paramBillSummary.getBalanceValue() != null) {
      setBalance((Currency)paramBillSummary.getBalanceValue().clone());
    } else {
      setBalance((Currency)null);
    }
    if (paramBillSummary.getPreviousBalanceValue() != null) {
      setPreviousBalance((Currency)paramBillSummary.getPreviousBalanceValue().clone());
    } else {
      setPreviousBalance((Currency)null);
    }
    if (paramBillSummary.getMinimumAmountDueValue() != null) {
      setMinimumAmountDue(paramBillSummary.getMinimumAmountDueValue());
    } else {
      setMinimumAmountDue((Currency)null);
    }
    if (paramBillSummary.getStatementBeginDateValue() != null) {
      setStatementBeginDate((DateTime)paramBillSummary.getStatementBeginDateValue().clone());
    } else {
      setStatementBeginDate((DateTime)null);
    }
    if (paramBillSummary.getStatementEndDateValue() != null) {
      setStatementEndDate((DateTime)paramBillSummary.getStatementEndDateValue().clone());
    } else {
      setStatementEndDate((DateTime)null);
    }
    setPaymentAccountID(paramBillSummary.getPaymentAccountID());
    if (paramBillSummary.getPaymentDateValue() != null) {
      setPaymentDate((DateTime)paramBillSummary.getPaymentDateValue().clone());
    } else {
      setPaymentDate((DateTime)null);
    }
    if (paramBillSummary.getAmountPaidValue() != null) {
      setAmountPaid((Currency)paramBillSummary.getAmountPaidValue().clone());
    } else {
      setAmountPaid((Currency)null);
    }
    setBillRefInfo(paramBillSummary.getBillRefInfo());
    if (paramBillSummary.getActivityValue() != null) {
      setActivity((Currency)paramBillSummary.getActivityValue().clone());
    } else {
      setActivity((Currency)null);
    }
    super.set(paramBillSummary);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("STATEMENTURL"))
      {
        this.JV = paramString2;
      }
      else if (paramString1.equals("CREATEDDATE"))
      {
        if (this.JS == null)
        {
          this.JS = new DateTime(this.locale);
          this.JS.setFormat(this.datetype);
        }
        this.JS.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("VIEWDATE"))
      {
        if (this.J0 == null)
        {
          this.J0 = new DateTime(this.locale);
          this.J0.setFormat(this.datetype);
        }
        this.J0.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("BILLSUMMARYID"))
      {
        this.JW = paramString2;
      }
      else if (paramString1.equals("ACCOUNTID"))
      {
        this.JZ = Long.parseLong(paramString2);
      }
      else if (paramString1.equals("CONSUMERID"))
      {
        this.JI = Long.parseLong(paramString2);
      }
      else if (paramString1.equals("BILLID"))
      {
        this.JM = paramString2;
      }
      else if (paramString1.equals("STATUSCODE"))
      {
        this.JJ = paramString2;
      }
      else if (paramString1.equals("BILLREFINFO"))
      {
        this.JQ = paramString2;
      }
      else if (paramString1.equals("STATEMENTBEGINDATE"))
      {
        if (this.JX == null)
        {
          this.JX = new DateTime(this.locale);
          this.JX.setFormat(this.datetype);
        }
        this.JX.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("STATEMENTENDDATE"))
      {
        if (this.JY == null)
        {
          this.JY = new DateTime(this.locale);
          this.JY.setFormat(this.datetype);
        }
        this.JY.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("BILLDATE"))
      {
        if (this.J2 == null)
        {
          this.J2 = new DateTime(this.locale);
          this.J2.setFormat(this.datetype);
        }
        this.J2.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("PAYMENTDUEDATE"))
      {
        if (this.JK == null)
        {
          this.JK = new DateTime(this.locale);
          this.JK.setFormat(this.datetype);
        }
        this.JK.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("ACTIVITY"))
      {
        if (this.JL == null) {
          this.JL = new Currency(paramString2, this.locale);
        } else {
          this.JL.fromString(paramString2);
        }
      }
      else if (paramString1.equals("AMOUNTDUE"))
      {
        if (this.J3 == null) {
          this.J3 = new Currency(paramString2, this.locale);
        } else {
          this.J3.fromString(paramString2);
        }
      }
      else if (paramString1.equals("BALANCE"))
      {
        if (this.JT == null) {
          this.JT = new Currency(paramString2, this.locale);
        } else {
          this.JT.fromString(paramString2);
        }
      }
      else if (paramString1.equals("PREVIOUSBALANCE"))
      {
        if (this.JN == null) {
          this.JN = new Currency(paramString2, this.locale);
        } else {
          this.JN.fromString(paramString2);
        }
      }
      else if (paramString1.equals("MINIMUMAMOUNTDUE"))
      {
        if (this.JU == null) {
          this.JU = new Currency(paramString2, this.locale);
        } else {
          this.JU.fromString(paramString2);
        }
      }
      else if (paramString1.equals("PAYMENTACCOUNTID"))
      {
        this.JR = paramString2;
      }
      else if (paramString1.equals("PAYMENTDATE"))
      {
        if (this.JO == null)
        {
          this.JO = new DateTime(this.locale);
          this.JO.setFormat(this.datetype);
        }
        this.JO.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("AMOUNTPAID"))
      {
        if (this.J1 == null) {
          this.J1 = new Currency(paramString2, this.locale);
        } else {
          this.J1.fromString(paramString2);
        }
      }
      else
      {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString, EBillAccounts paramEBillAccounts, Consumers paramConsumers)
  {
    setXML(paramString, paramEBillAccounts, paramConsumers);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BILLSUMMARY");
    XMLHandler.appendTag(localStringBuffer, "STATEMENTURL", this.JV);
    XMLHandler.appendTag(localStringBuffer, "CREATEDDATE", this.JS);
    XMLHandler.appendTag(localStringBuffer, "VIEWDATE", this.J0);
    XMLHandler.appendTag(localStringBuffer, "BILLSUMMARYID", this.JW);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", this.JZ);
    XMLHandler.appendTag(localStringBuffer, "CONSUMERID", this.JI);
    XMLHandler.appendTag(localStringBuffer, "BILLID", this.JM);
    XMLHandler.appendTag(localStringBuffer, "STATUSCODE", this.JJ);
    XMLHandler.appendTag(localStringBuffer, "BILLREFINFO", this.JQ);
    XMLHandler.appendTag(localStringBuffer, "STATEMENTBEGINDATE", this.JX);
    XMLHandler.appendTag(localStringBuffer, "STATEMENTENDDATE", this.JY);
    XMLHandler.appendTag(localStringBuffer, "BILLDATE", this.J2);
    XMLHandler.appendTag(localStringBuffer, "PAYMENTDUEDATE", this.JK);
    if (this.JL != null) {
      XMLHandler.appendTag(localStringBuffer, "ACTIVITY", this.JL.doubleValue());
    }
    if (this.J3 != null) {
      XMLHandler.appendTag(localStringBuffer, "AMOUNTDUE", this.J3.doubleValue());
    }
    if (this.JT != null) {
      XMLHandler.appendTag(localStringBuffer, "BALANCE", this.JT.doubleValue());
    }
    if (this.JN != null) {
      XMLHandler.appendTag(localStringBuffer, "PREVIOUSBALANCE", this.JN.doubleValue());
    }
    if (this.JU != null) {
      XMLHandler.appendTag(localStringBuffer, "MINIMUMAMOUNTDUE", this.JU.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "PAYMENTACCOUNTID", this.JR);
    XMLHandler.appendTag(localStringBuffer, "PAYMENTDATE", this.JO);
    if (this.J1 != null) {
      XMLHandler.appendTag(localStringBuffer, "AMOUNTPAID", this.J1.doubleValue());
    }
    if (this.JP != null) {
      localStringBuffer.append(this.JP.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BILLSUMMARY");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString, EBillAccounts paramEBillAccounts, Consumers paramConsumers)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramEBillAccounts, paramConsumers), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, EBillAccounts paramEBillAccounts, Consumers paramConsumers)
  {
    paramXMLHandler.continueWith(new a(paramEBillAccounts, paramConsumers));
  }
  
  class a
    extends XMLHandler
  {
    EBillAccounts jdField_new;
    Consumers jdField_int;
    
    public a(EBillAccounts paramEBillAccounts, Consumers paramConsumers)
    {
      this.jdField_new = paramEBillAccounts;
      this.jdField_int = paramConsumers;
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      BillSummary.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.BillSummary
 * JD-Core Version:    0.7.0.1
 */