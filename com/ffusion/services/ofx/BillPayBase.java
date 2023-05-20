package com.ffusion.services.ofx;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.util.logging.DebugLog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public abstract class BillPayBase
  extends Base
  implements BillPayDefines
{
  protected Payees payees;
  protected Payee payee;
  protected Payment payment;
  protected Payments payments;
  protected String m_PayeeToken;
  protected String m_PaymentToken;
  protected String m_RecPaymentToken;
  protected int defaultDaysToPay = 4;
  protected SimpleDateFormat pmtProcessDateFormatter = new SimpleDateFormat("MM/dd/yyyy");
  
  protected void formatPaymentRequest(StringBuffer paramStringBuffer, Account paramAccount, Payment paramPayment, int paramInt)
  {
    Payee localPayee = paramPayment.getPayee();
    String str2 = "";
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatPaymentRequest:");
    }
    String str1;
    if ((localPayee != null) && (localPayee.getUserAccountNumber() != null) && (localPayee.getUserAccountNumber().length() > 0)) {
      str1 = localPayee.getUserAccountNumber();
    } else {
      str1 = "NA";
    }
    if (localPayee == null) {
      str2 = paramPayment.getID();
    } else if (localPayee.getID().length() > 0) {
      str2 = localPayee.getID();
    }
    appendBeginTag(paramStringBuffer, "PMTINFO");
    formatFromAccount(paramStringBuffer, paramAccount);
    appendTag(paramStringBuffer, "TRNAMT", paramPayment.getAmountValue());
    if (localPayee != null)
    {
      if ((localPayee.getHostID() == null) || (localPayee.getHostID().length() == 0)) {
        formatPAYEE(paramStringBuffer, localPayee);
      } else {
        appendTag(paramStringBuffer, "PAYEEID", localPayee.getHostID());
      }
    }
    else {
      appendTag(paramStringBuffer, "PAYEEID", str2);
    }
    appendTag(paramStringBuffer, "PAYEELSTID", str2);
    appendTag(paramStringBuffer, "PAYACCT", str1);
    appendTag(paramStringBuffer, "DTDUE", fixPaymentDate(paramPayment, paramInt), "yyyyMMdd");
    if ((paramPayment.getMemo() != null) && (paramPayment.getMemo().length() > 0)) {
      appendTag(paramStringBuffer, "MEMO", paramPayment.getMemo());
    }
    appendEndTag(paramStringBuffer, "PMTINFO");
  }
  
  protected void formatPAYEE(StringBuffer paramStringBuffer, Payee paramPayee)
  {
    appendBeginTag(paramStringBuffer, "PAYEE");
    appendTag(paramStringBuffer, "NAME", paramPayee.getName());
    appendTag(paramStringBuffer, "ADDR1", paramPayee.getStreet());
    if ((paramPayee.getStreet2() != null) && (paramPayee.getStreet2().length() > 0)) {
      appendTag(paramStringBuffer, "ADDR2", paramPayee.getStreet2());
    }
    appendTag(paramStringBuffer, "CITY", paramPayee.getCity());
    appendTag(paramStringBuffer, "STATE", paramPayee.getState());
    appendTag(paramStringBuffer, "POSTALCODE", paramPayee.getZipCode());
    appendTag(paramStringBuffer, "PHONE", paramPayee.getPhone());
    appendEndTag(paramStringBuffer, "PAYEE");
  }
  
  protected Calendar fixPaymentDate(Payment paramPayment, int paramInt)
  {
    DateTime localDateTime = paramPayment.getPayDateValue();
    if (paramInt != -1) {
      if (paramPayment.getStatus() != 8)
      {
        int i = 0;
        Payee localPayee = paramPayment.getPayee();
        if (localPayee != null) {
          i = localPayee.getDaysToPayValue();
        }
        if (i == 0) {
          i = this.defaultDaysToPay;
        }
        Calendar localCalendar = jdMethod_if(i);
        if (localDateTime.before(localCalendar)) {
          localDateTime.setTime(localCalendar.getTime());
        }
      }
      else
      {
        localDateTime.add(5, 1);
      }
    }
    return localDateTime;
  }
  
  private Calendar jdMethod_if(int paramInt)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    if (paramInt > 0) {
      localGregorianCalendar.add(5, paramInt);
    }
    return localGregorianCalendar;
  }
  
  protected void setPayment()
  {
    String str1 = (String)this.savedValues.get("TRNUID");
    String str2 = (String)this.savedValues.get("SRVRTID");
    if ((str1 != null) && (this.payments != null) && (!str1.equals("0"))) {
      this.payment = ((Payment)this.payments.getFirstByFilter("TRNUID=" + str1));
    }
    if ((this.payment == null) && (this.payments != null) && (str2 != null)) {
      this.payment = ((Payment)this.payments.getFirstByFilter("ID=" + str2));
    }
    Object localObject;
    if ((this.payment == null) && (this.payments != null))
    {
      localObject = (String)this.savedValues.get("TRNAMT");
      if ((localObject != null) && (((String)localObject).length() != 0)) {
        this.payment = ((Payment)this.payments.create());
      }
    }
    if (this.payment != null)
    {
      localObject = this.savedValues.keySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str3 = (String)((Iterator)localObject).next();
        setPayment(str3, (String)this.savedValues.get(str3));
      }
      if (this.payment.getErrorValue() == 2014) {
        this.payment.setStatus(8);
      }
    }
    this.savedValues.clear();
  }
  
  protected void setPayment(String paramString1, String paramString2)
  {
    if (this.payment != null)
    {
      if ((paramString1.equals("RECSRVRTID")) || (paramString1.equals("TRNAMT")) || (paramString1.equals("NAME")) || (paramString1.equals("DTDUE")) || (paramString1.equals("MEMO")) || (paramString1.equals("TRNUID")) || (paramString1.equals("STATUS")) || (paramString1.equals("PAYEELSTID")) || (paramString1.equals("ACCTTYPE")))
      {
        setPaymentCommon(this.payment, paramString1, paramString2);
      }
      else if (paramString1.equals("SRVRTID"))
      {
        this.payment.setID(paramString2);
      }
      else if (paramString1.equals("TOKEN"))
      {
        this.m_PaymentToken = paramString2;
      }
      else if (paramString1.equals("DTPMTPRC"))
      {
        this.payment.set("PayProcessDate", this.pmtProcessDateFormatter.format(getDate(paramString2).getTime()));
      }
      else if (paramString1.equals("STATUS"))
      {
        if (Integer.parseInt(paramString2) == 2014) {
          this.payment.setStatus(8);
        }
      }
      else if (paramString1.equals("PMTPRCCODE"))
      {
        this.payment.set("PayProcessCode", paramString2);
        if (paramString2.equals("WILLPROCESSON")) {
          this.payment.setStatus(2);
        } else if (paramString2.equals("PROCESSEDON")) {
          this.payment.setStatus(5);
        } else if (paramString2.equals("NOFUNDSON")) {
          this.payment.setStatus(6);
        } else if (paramString2.equals("FAILEDON")) {
          this.payment.setStatus(6);
        } else if (paramString2.equals("CANCELEDON")) {
          this.payment.setStatus(3);
        }
      }
      else if (this.extendedInfo)
      {
        saveTagInExtendABean(paramString1, paramString2, this.payment);
      }
    }
    else {
      this.savedValues.put(paramString1, paramString2);
    }
  }
  
  protected void parsePMTINFO()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTINFO"))) {
      if (str.equals("BANKACCTFROM")) {
        parseBANKACCTFROM();
      } else if (str.equals("PAYEE")) {
        parsePAYEE();
      } else if (str.equals("BANKACCTTO")) {
        parseBANKACCTTO();
      } else if (str.equals("EXTDPMT")) {
        parseNotSupported(str);
      } else if ((str.equals("TRNAMT")) || (str.equals("PAYEEID")) || (str.equals("PAYEELSTID")) || (str.equals("PAYACCT")) || (str.equals("DTDUE")) || (str.equals("MEMO")) || (str.equals("BILLREFINFO"))) {
        setPayment(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parsePMTINFO failed on tag=" + str);
      }
    }
  }
  
  protected void parsePAYEE()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PAYEE"))) {
      if ((str.equals("NAME")) || (str.equals("ADDR1")) || (str.equals("ADDR2")) || (str.equals("ADDR3")) || (str.equals("CITY")) || (str.equals("STATE")) || (str.equals("POSTALCODE")) || (str.equals("COUNTRY")) || (str.equals("PHONE"))) {
        setPayee(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parsePAYEE failed on tag=" + str);
      }
    }
  }
  
  protected void setPaymentCommon(Payment paramPayment, String paramString1, String paramString2)
  {
    if (paramString1.equals("RECSRVRTID"))
    {
      paramPayment.setRecPaymentID(paramString2);
    }
    else if (paramString1.equals("TRNAMT"))
    {
      paramPayment.setAmount(paramString2);
    }
    else if (paramString1.equals("NAME"))
    {
      paramPayment.setPayeeName(paramString2);
    }
    else if (paramString1.equals("DTDUE"))
    {
      paramPayment.setPayDate(getDate(paramString2));
    }
    else if (paramString1.equals("MEMO"))
    {
      paramPayment.setMemo(paramString2);
    }
    else if (paramString1.equals("TRNUID"))
    {
      paramPayment.set("TRNUID", paramString2);
    }
    else
    {
      String str;
      if (paramString1.equals("STATUS"))
      {
        str = paramPayment.getID();
        if (str == null) {
          if ((paramPayment instanceof RecPayment)) {
            str = (String)this.savedValues.get("RECSRVRTID");
          } else {
            str = (String)this.savedValues.get("SRVRTID");
          }
        }
        paramPayment.setError(mapError(Integer.parseInt(paramString2)));
        this.objStatus.put(str, paramString2);
        if ((paramString2.equals("0")) && (paramPayment.getStatus() == 1)) {
          paramPayment.setStatus(2);
        }
      }
      else if (paramString1.equals("PAYEELSTID"))
      {
        if (this.payees != null)
        {
          this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + paramString2));
          paramPayment.setPayee(this.payee);
          this.payee = null;
        }
      }
      else if (paramString1.equals("ACCTTYPE"))
      {
        str = (String)this.savedValues.get("ACCTID");
        if ((str != null) && (this.accounts != null))
        {
          this.account = this.accounts.getByAccountNumberAndType(str, getAccountType(paramString2));
          if (this.account != null) {
            paramPayment.setAccount(this.account);
          } else {
            paramPayment.setAccountID(str);
          }
          this.account = null;
        }
      }
    }
  }
  
  protected void setPayee()
  {
    String str1 = (String)this.savedValues.get("PAYEELSTID");
    if (str1 == null) {
      throw new IllegalArgumentException("setPayee: PAYEELSTID=" + str1);
    }
    String str2 = (String)this.savedValues.get("TRNUID");
    if ((str2 != null) && (this.payees != null) && (!str2.equals("0"))) {
      this.payee = ((Payee)this.payees.getFirstByFilter("TRNUID=" + str2));
    }
    if ((this.payee == null) && (this.payees != null)) {
      this.payee = ((Payee)this.payees.getFirstByFilter("ID=" + str1));
    }
    if ((this.payee == null) && (this.payees != null)) {
      this.payee = this.payees.create();
    }
    this.payee.setStatus(2);
    Iterator localIterator = this.savedValues.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str3 = (String)localIterator.next();
      setPayee(str3, (String)this.savedValues.get(str3));
    }
    this.savedValues.clear();
  }
  
  protected void setPayee(String paramString1, String paramString2)
  {
    if (this.payee != null)
    {
      if (paramString1.equals("PAYEELSTID")) {
        this.payee.setID(paramString2);
      } else if (paramString1.equals("PAYACCT")) {
        this.payee.setUserAccountNumber(paramString2);
      } else if (paramString1.equals("NAME")) {
        this.payee.setName(paramString2);
      } else if (paramString1.equals("ADDR1")) {
        this.payee.setStreet(paramString2);
      } else if (paramString1.equals("ADDR2")) {
        this.payee.setStreet2(paramString2);
      } else if (paramString1.equals("CITY")) {
        this.payee.setCity(paramString2);
      } else if (paramString1.equals("STATE")) {
        this.payee.setState(paramString2);
      } else if (paramString1.equals("COUNTRY")) {
        this.payee.setCountry(paramString2);
      } else if (paramString1.equals("POSTALCODE")) {
        this.payee.setZipCode(paramString2);
      } else if (paramString1.equals("PHONE")) {
        this.payee.setPhone(paramString2);
      } else if (paramString1.equals("PAYEEID")) {
        this.payee.setHostID(paramString2);
      } else if (paramString1.equals("DAYSTOPAY")) {
        this.payee.setDaysToPay(Integer.parseInt(paramString2));
      } else if (paramString1.equals("TRNUID")) {
        this.payee.set("TRNUID", paramString2);
      } else if (paramString1.equals("TOKEN")) {
        this.m_PayeeToken = paramString2;
      } else if (paramString1.equals("STATUS")) {
        this.objStatus.put(this.payee.getID(), paramString2);
      } else if (this.extendedInfo) {
        saveTagInExtendABean(paramString1, paramString2, this.payee);
      }
    }
    else {
      this.savedValues.put(paramString1, paramString2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.BillPayBase
 * JD-Core Version:    0.7.0.1
 */