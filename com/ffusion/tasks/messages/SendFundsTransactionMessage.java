package com.ffusion.tasks.messages;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeRoute;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.cashcon.CashCon;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.wiretransfers.WireHistory;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.Messages;
import com.ffusion.efs.adapters.profile.MessageQueueAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.enums.UserAssignedAmount;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendFundsTransactionMessage
  extends SendMessage
{
  protected String fundsID;
  protected String collectionName;
  private FundsTransaction qY;
  protected boolean _isConsumer = false;
  protected int _transactionType = 0;
  protected String _amount;
  protected String _payAccountID;
  protected String _payeeID;
  protected String _payDate;
  protected String _transferDate;
  protected String _toAccountID;
  protected String _fromAccountID;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    setLocale((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.initFlag) {
      initProcess(paramHttpServlet.getServletContext(), localHttpSession);
    }
    if ((this.error == 0) && (validateInput(localHttpSession)))
    {
      if (this.processFlag)
      {
        if (getMemo().length() > this.maxSize)
        {
          this.error = 8077;
          str = this.taskErrorURL;
          return str;
        }
        str = sendMessage(localHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String sendMessage(HttpSession paramHttpSession)
  {
    String str1 = this.taskErrorURL;
    this.error = 0;
    if (this.processFlag)
    {
      this.processFlag = false;
      HashMap localHashMap = new HashMap();
      localHashMap.put("SERVICE", paramHttpSession.getAttribute("com.ffusion.services.Messaging3"));
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      String str2 = localSecureUser.getLocaleLanguage();
      try
      {
        MessageQueue localMessageQueue = jdMethod_else(getQueueID(this.qY), localSecureUser.getBankID());
        if (localMessageQueue != null)
        {
          setToName(localMessageQueue.getQueueName(str2));
          setTo(localMessageQueue.getQueueID());
          setToType("QUEUE");
          this.qY.setLocale(getLocale());
          setComment(this.qY.getInquireComment());
        }
        Messages.sendMessage(localSecureUser, this, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      if (this.error == 0) {
        str1 = this.successURL;
      }
    }
    return str1;
  }
  
  protected void initProcess(ServletContext paramServletContext, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    Object localObject1;
    if (this.fundsID == null)
    {
      localObject1 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      if (validate((SecureUser)localObject1))
      {
        if (this._transactionType == 0)
        {
          this.error = 8014;
        }
        else
        {
          Accounts localAccounts;
          Object localObject2;
          if (this._transactionType == 1)
          {
            localAccounts = (Accounts)paramHttpSession.getValue("BankingAccounts");
            this.qY = new Transfer(this.locale);
            localObject2 = (Transfer)this.qY;
            if (localAccounts != null) {
              ((Transfer)localObject2).setToAccount(localAccounts.getByID(this._toAccountID));
            }
            if (localAccounts != null) {
              ((Transfer)localObject2).setFromAccount(localAccounts.getByID(this._fromAccountID));
            }
            ((Transfer)localObject2).setDateToPost(this._transferDate);
            ((Transfer)localObject2).setMemo(this.memo);
          }
          else if (this._transactionType == 3)
          {
            localAccounts = (Accounts)paramHttpSession.getValue("BankingAccounts");
            localObject2 = new Payments(this.locale);
            this.qY = ((FundsTransaction)((Payments)localObject2).createNoAdd());
            Payees localPayees = (Payees)paramHttpSession.getValue("Payees");
            Payment localPayment = (Payment)this.qY;
            if (localAccounts != null) {
              localPayment.setAccount(localAccounts.getByID(this._payAccountID));
            }
            if (localPayees != null) {
              localPayment.setPayee(localPayees.getByID(this._payeeID));
            }
            localPayment.setPayDate(this._payDate);
            localPayment.setMemo(this.memo);
          }
        }
        setFrom(((SecureUser)localObject1).getProfileID());
        setFromName(((SecureUser)localObject1).getUserName());
        this.qY.setAmount(this._amount);
      }
    }
    else
    {
      localObject1 = (FilteredList)paramHttpSession.getAttribute(this.collectionName);
      if (localObject1 == null) {
        this.error = 8015;
      } else {
        this.qY = ((FundsTransaction)((FilteredList)localObject1).getFirstByFilter("ID=" + this.fundsID));
      }
    }
    if ((this.qY == null) && (this.error == 0)) {
      this.error = 8016;
    }
  }
  
  public void setFundsID(String paramString)
  {
    this.fundsID = paramString;
  }
  
  public void setCollectionName(String paramString)
  {
    this.collectionName = paramString;
  }
  
  public String getTransactionAmount()
  {
    String str1 = "";
    if (this.qY != null) {
      if ((this.qY instanceof Payment))
      {
        Payment localPayment = (Payment)this.qY;
        Currency localCurrency = null;
        String str2 = localPayment.getPayee().getPayeeRoute().getCurrencyCode();
        if ((str2 == null) || (str2.trim().length() == 0))
        {
          try
          {
            localCurrency = Billpay.getDefaultCurrency();
          }
          catch (Exception localException) {}
          str2 = localCurrency.getCurrencyCode();
        }
        str1 = localPayment.getAmountForBPW() + " " + str2;
      }
      else
      {
        str1 = this.qY.getAmount();
      }
    }
    return str1;
  }
  
  public Currency getTransactionAmountValue()
  {
    Currency localCurrency = null;
    if (this.qY != null) {
      localCurrency = this.qY.getAmountValue();
    }
    return localCurrency;
  }
  
  public Currency getTransactionToAmountValue()
  {
    Currency localCurrency = null;
    if ((this.qY != null) && ((this.qY instanceof Transfer)))
    {
      Transfer localTransfer = (Transfer)this.qY;
      return localTransfer.getToAmountValue();
    }
    return localCurrency;
  }
  
  public String getTransactionUserAssignedAmountFlagName()
  {
    String str = UserAssignedAmount.SINGLE.getName();
    if ((this.qY != null) && ((this.qY instanceof Transfer)))
    {
      Transfer localTransfer = (Transfer)this.qY;
      str = localTransfer.getUserAssignedAmountFlagName();
    }
    return str;
  }
  
  public boolean getIsAmountEstimated()
  {
    boolean bool = false;
    if ((this.qY != null) && ((this.qY instanceof Transfer)))
    {
      Transfer localTransfer = (Transfer)this.qY;
      bool = localTransfer.getIsAmountEstimated();
    }
    return bool;
  }
  
  public boolean getIsToAmountEstimated()
  {
    boolean bool = false;
    if ((this.qY != null) && ((this.qY instanceof Transfer)))
    {
      Transfer localTransfer = (Transfer)this.qY;
      bool = localTransfer.getIsToAmountEstimated();
    }
    return bool;
  }
  
  public String getAmount()
  {
    return this._amount == null ? "" : this._amount;
  }
  
  public String getTransactionType()
  {
    String str = "";
    if (this.qY != null) {
      str = this.qY.getTypeString();
    }
    return str;
  }
  
  public String getTransactionTypeValue()
  {
    String str = "";
    if (this.qY != null) {
      str = Integer.toString(this.qY.getType());
    }
    return str;
  }
  
  public String getTransactionDate()
  {
    String str = "";
    if ((this.qY instanceof ACHBatch)) {
      str = ((ACHBatch)this.qY).getDate();
    } else if ((this.qY instanceof Transfer)) {
      str = ((Transfer)this.qY).getDate();
    } else if ((this.qY instanceof Payment)) {
      str = ((Payment)this.qY).getPayDate();
    } else if ((this.qY instanceof CashCon)) {
      str = ((CashCon)this.qY).getSubmitDate();
    } else if ((this.qY instanceof WireTransfer)) {
      str = ((WireTransfer)this.qY).getDateToPost();
    } else if ((this.qY instanceof WireHistory)) {
      str = ((WireHistory)this.qY).getDate();
    }
    return str;
  }
  
  public String getTransactionRefNum()
  {
    String str = "";
    if (this.qY != null) {
      str = this.qY.getReferenceNumber();
    }
    return str;
  }
  
  protected String getQueueID(FundsTransaction paramFundsTransaction)
  {
    String str = "1";
    if ((paramFundsTransaction instanceof Transfer))
    {
      if (((Transfer)paramFundsTransaction).getRecTransferID() == null) {
        str = "3";
      } else {
        str = "4";
      }
    }
    else if ((paramFundsTransaction instanceof Payment))
    {
      if (((Payment)paramFundsTransaction).getRecPaymentID() == null) {
        str = "5";
      } else {
        str = "6";
      }
    }
    else if ((paramFundsTransaction instanceof CashCon))
    {
      if (((CashCon)paramFundsTransaction).getType() == 16) {
        str = "18";
      } else {
        str = "17";
      }
    }
    else if ((paramFundsTransaction instanceof WireTransfer))
    {
      if (((WireTransfer)paramFundsTransaction).getRecurringID() == null) {
        str = "15";
      } else {
        str = "16";
      }
    }
    else
    {
      Object localObject;
      if ((paramFundsTransaction instanceof WireHistory))
      {
        localObject = (WireHistory)paramFundsTransaction;
        if (localObject != null) {
          if ("SINGLE".equals(((WireHistory)localObject).getTransType())) {
            str = "15";
          } else if ("RECURRING".equals(((WireHistory)localObject).getTransType())) {
            str = "16";
          } else if ("BATCH".equals(((WireHistory)localObject).getTransType())) {
            str = "21";
          }
        }
      }
      else if ((paramFundsTransaction instanceof ACHBatch))
      {
        localObject = (ACHBatch)paramFundsTransaction;
        if ((((ACHBatch)localObject).getACHType() == null) || (((ACHBatch)localObject).getACHType().equals("ACHBatch")))
        {
          if ((((ACHBatch)localObject).getRecID() == null) || (((ACHBatch)localObject).getRecID().length() <= 0)) {
            str = "9";
          } else {
            str = "10";
          }
        }
        else if ((((ACHBatch)localObject).getTaxForm() != null) && (((ACHBatch)localObject).getTaxForm().getAddendaFormat().equals("TXP")))
        {
          if ((((ACHBatch)localObject).getRecID() == null) || (((ACHBatch)localObject).getRecID().length() <= 0)) {
            str = "12";
          } else {
            str = "13";
          }
        }
        else if ((((ACHBatch)localObject).getTaxForm() != null) && (((ACHBatch)localObject).getTaxForm().getAddendaFormat().equals("DED"))) {
          if ((((ACHBatch)localObject).getRecID() == null) || (((ACHBatch)localObject).getRecID().length() <= 0)) {
            str = "19";
          } else {
            str = "20";
          }
        }
      }
    }
    return str;
  }
  
  public String getPayDate()
  {
    return this._payDate == null ? "" : this._payDate;
  }
  
  public String getTransferDate()
  {
    return this._transferDate == null ? "" : this._transferDate;
  }
  
  private static MessageQueues jdMethod_char(String paramString1, String paramString2)
  {
    MessageQueue localMessageQueue = new MessageQueue();
    localMessageQueue.setBankId(paramString2);
    localMessageQueue.setQueueID(paramString1);
    localMessageQueue.setQueueType("0");
    MessageQueues localMessageQueues;
    try
    {
      localMessageQueues = MessageQueueAdapter.getQueues(localMessageQueue);
    }
    catch (ProfileException localProfileException)
    {
      return null;
    }
    return localMessageQueues;
  }
  
  private MessageQueue jdMethod_else(String paramString1, String paramString2)
  {
    MessageQueue localMessageQueue = null;
    MessageQueues localMessageQueues = jdMethod_char(paramString1, paramString2);
    if (localMessageQueues == null)
    {
      this.error = 8020;
    }
    else
    {
      localMessageQueue = localMessageQueues.getByID(paramString1);
      if (localMessageQueue == null) {
        this.error = 8021;
      }
    }
    return localMessageQueue;
  }
  
  public void setIsConsumer(String paramString)
  {
    this._isConsumer = new Boolean(paramString).booleanValue();
  }
  
  public void setTransactionType(String paramString)
  {
    try
    {
      this._transactionType = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._transactionType = 0;
    }
  }
  
  public void setPayDate(String paramString)
  {
    if ((this._transactionType == 3) && (paramString != null)) {
      this._payDate = paramString;
    }
  }
  
  public void setTransferDate(String paramString)
  {
    if ((this._transactionType == 1) && (paramString != null)) {
      this._transferDate = paramString;
    }
  }
  
  public void setPayeeID(String paramString)
  {
    if ((this._transactionType == 3) && (paramString != null)) {
      this._payeeID = paramString;
    }
  }
  
  public void setAmount(String paramString)
  {
    this._amount = paramString;
  }
  
  public void setPaymentAccountID(String paramString)
  {
    if ((this._transactionType == 3) && (paramString != null)) {
      this._payAccountID = paramString;
    }
  }
  
  public void setToAccountID(String paramString)
  {
    if ((this._transactionType == 1) && (paramString != null)) {
      this._toAccountID = paramString;
    }
  }
  
  public String getToAccountID()
  {
    String str = null;
    if (this._transactionType == 1) {
      str = this._toAccountID;
    }
    return str;
  }
  
  public void setFromAccountID(String paramString)
  {
    if ((this._transactionType == 1) && (paramString != null)) {
      this._fromAccountID = paramString;
    }
  }
  
  public String getFromAccountID()
  {
    String str = null;
    if (this._transactionType == 1) {
      str = this._fromAccountID;
    }
    return str;
  }
  
  public String getPayeeName()
  {
    if ((this.qY instanceof Payment)) {
      return ((Payment)this.qY).getPayeeName();
    }
    return null;
  }
  
  public String getAccountDisplayText()
  {
    String str = null;
    if ((this.qY instanceof Payment))
    {
      Payment localPayment = (Payment)this.qY;
      if (this._isConsumer) {
        str = localPayment.getConsumerAccountDisplayText();
      } else {
        str = localPayment.getAccountDisplayText();
      }
    }
    return str;
  }
  
  public String getFromAccountDisplayText()
  {
    String str = null;
    if ((this.qY instanceof Transfer))
    {
      Transfer localTransfer = (Transfer)this.qY;
      if (this._isConsumer) {
        str = localTransfer.getConsumerFromAccountDisplayText();
      } else {
        str = localTransfer.getFromAccountDisplayText();
      }
    }
    return str;
  }
  
  public String getToAccountDisplayText()
  {
    String str = null;
    if ((this.qY instanceof Transfer))
    {
      Transfer localTransfer = (Transfer)this.qY;
      if (this._isConsumer) {
        str = localTransfer.getConsumerToAccountDisplayText();
      } else {
        str = localTransfer.getToAccountDisplayText();
      }
    }
    return str;
  }
  
  protected boolean validate(SecureUser paramSecureUser)
  {
    Locale localLocale = null;
    this.error = 0;
    if (paramSecureUser != null)
    {
      localLocale = paramSecureUser.getLocale();
      try
      {
        if ((this._amount == null) || (this._amount.length() == 0))
        {
          this.error = 100100;
          return false;
        }
        Object localObject = new Currency(this._amount, paramSecureUser.getBaseCurrency(), localLocale);
        if (!((Currency)localObject).validateCurrency(this._amount))
        {
          this.error = 100101;
          return false;
        }
        if ((((Currency)localObject).isNegative()) || (((Currency)localObject).getIsZero())) {
          this.error = 100101;
        }
        if ((this.memo == null) || (this.memo.length() == 0))
        {
          this.error = 8007;
          return false;
        }
        if (this._transactionType == 3)
        {
          if ((this._payDate == null) || (this._payDate.length() == 0))
          {
            this.error = 100102;
            return false;
          }
          localObject = new DateTime(this._payDate, localLocale, getDateFormat());
          if ((this._payAccountID == null) || (this._payAccountID.length() == 0))
          {
            this.error = 100104;
            return false;
          }
          if ((this._payeeID == null) || (this._payeeID.length() == 0))
          {
            this.error = 100121;
            return false;
          }
        }
        else if (this._transactionType == 1)
        {
          if ((this._toAccountID == null) || (this._toAccountID.length() == 0))
          {
            this.error = 100122;
            return false;
          }
          if ((this._fromAccountID == null) || (this._fromAccountID.length() == 0))
          {
            this.error = 100123;
            return false;
          }
          if ((this._transferDate == null) || (this._transferDate.length() == 0))
          {
            this.error = 100102;
            return false;
          }
          localObject = new DateTime(this._transferDate, localLocale, getDateFormat());
        }
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        this.error = 100103;
        return false;
      }
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendFundsTransactionMessage
 * JD-Core Version:    0.7.0.1
 */