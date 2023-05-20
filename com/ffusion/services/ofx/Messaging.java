package com.ffusion.services.ofx;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;
import com.ffusion.services.Messaging2;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class Messaging
  extends BillPayBase
  implements BankingDefines, BillPayDefines, Messaging2
{
  protected Messages messages;
  protected Message message;
  protected String mailToken;
  protected String bankMailToken;
  protected String paymentMailToken;
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "MESSAGINGSERVICE");
    localStringBuffer.append(super.getSettings());
    XMLHandler.appendEndTag(localStringBuffer, "MESSAGINGSERVICE");
    return localStringBuffer.toString();
  }
  
  public void setUserName(String paramString)
  {
    setUserID(paramString);
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public int markMessageAsRead(Object paramObject)
  {
    return 2;
  }
  
  public int getNumberOfUnreadMessages(Object paramObject)
  {
    return 2;
  }
  
  public int getMessageList(Object paramObject, Messages paramMessages)
  {
    return 2;
  }
  
  public int deleteMessage(Object paramObject)
  {
    return 2;
  }
  
  protected void formatMAIL(StringBuffer paramStringBuffer)
  {
    if ((this.message.getSubject() == null) || (this.message.getSubject().length() == 0)) {
      this.message.setSubject("Mail Subject");
    }
    appendBeginTag(paramStringBuffer, "MAIL");
    appendTag(paramStringBuffer, "USERID", this.userID);
    appendTag(paramStringBuffer, "DTCREATED", this.message.getDateValue(), "yyyyMMdd");
    appendTag(paramStringBuffer, "FROM", this.message.getFrom());
    appendTag(paramStringBuffer, "TO", this.message.getTo());
    appendTag(paramStringBuffer, "SUBJECT", this.message.getSubject());
    formatMailMemo(paramStringBuffer);
    appendTag(paramStringBuffer, "INCIMAGES", "N");
    appendTag(paramStringBuffer, "USEHTML", "N");
    appendEndTag(paramStringBuffer, "MAIL");
  }
  
  protected void formatMailMemo(StringBuffer paramStringBuffer)
  {
    String str = this.message.getMemo();
    if ((str == null) || (str.length() == 0)) {
      str = this.message.getSubject();
    }
    int i = str.length();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < i; j++)
    {
      char c = str.charAt(j);
      switch (c)
      {
      case '\n': 
        localStringBuffer.append(' ');
        break;
      default: 
        localStringBuffer.append(c);
      }
    }
    appendTag(paramStringBuffer, "MSGBODY", localStringBuffer.toString());
    appendEndTag(paramStringBuffer, "MSGBODY");
  }
  
  public void setMessage()
  {
    if ((this.message != null) || (this.messages == null)) {
      return;
    }
    this.message = this.messages.create();
    this.message.setID(String.valueOf(this.message.hashCode()));
    Iterator localIterator = this.savedValues.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      setMessage(str, (String)this.savedValues.get(str));
    }
    this.savedValues.clear();
  }
  
  public void setMessage(String paramString1, String paramString2)
  {
    if (this.message != null)
    {
      if (paramString1.equals("TO")) {
        this.message.setTo(paramString2);
      } else if (paramString1.equals("FROM")) {
        this.message.setFrom(paramString2);
      } else if (paramString1.equals("DTCREATED")) {
        this.message.setDate(getDate(paramString2));
      } else if (paramString1.equals("SUBJECT")) {
        this.message.setSubject(paramString2);
      } else if (paramString1.equals("MSGBODY")) {
        this.message.setMemo(paramString2);
      } else if (this.extendedInfo) {
        saveTagInExtendABean(paramString1, paramString2, this.message);
      }
    }
    else {
      this.savedValues.put(paramString1, paramString2);
    }
  }
  
  protected void parseMAIL()
  {
    this.message = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals("MAIL"))) {
      if (str.equals("MSGBODY")) {
        parseMSGBODY();
      } else if ((str.equals("USERID")) || (str.equals("DTCREATED")) || (str.equals("FROM")) || (str.equals("TO")) || (str.equals("SUBJECT")) || (str.equals("INCIMAGES")) || (str.equals("USEHTML"))) {
        setMessage(str, getField());
      } else if (this.extendedInfo) {
        this.savedValues.put(str, getField());
      } else {
        throw new IllegalArgumentException("parseMAIL failed on tag=" + str);
      }
    }
    setMessage();
  }
  
  protected void parseMSGBODY()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str;
    while (((str = getToken()) != null) && (!str.equals("MSGBODY")))
    {
      localStringBuffer.append(str);
      localStringBuffer.append("\n");
    }
    setMessage("MSGBODY", localStringBuffer.toString());
  }
  
  protected boolean handleResponse(char[] paramArrayOfChar)
  {
    boolean bool = false;
    this.objStatus.clear();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".handleResponse:");
    }
    if ((paramArrayOfChar != null) && (startResponse(paramArrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNONMSGSRSV1")) {
          parseSIGNONMSGSRSV1();
        } else if (str.equals("BANKMSGSRSV1")) {
          parseBANKMSGSRSV1();
        } else if (str.equals("CREDITCARDMSGSRSV1")) {
          parseCREDITCARDMSGSRSV1();
        } else if (str.equals("BILLPAYMSGSRSV1")) {
          parseBILLPAYMSGSRSV1();
        } else if (!handleResponseNotHandled(str)) {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        bool = true;
      }
    }
    return bool;
  }
  
  protected boolean handleEResponse(char[] paramArrayOfChar)
  {
    boolean bool = false;
    this.objStatus.clear();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".handleResponse:");
    }
    if ((paramArrayOfChar != null) && (startResponse(paramArrayOfChar)))
    {
      String str;
      while (((str = getToken()) != null) && (!str.equals("OFX"))) {
        if (str.equals("SIGNONMSGSRSV1")) {
          parseSIGNONMSGSRSV1();
        } else if (str.equals("EMAILMSGSRSV1")) {
          parseEMAILMSGSRSV1();
        } else if (!handleEResponseNotHandled(str)) {
          logError(3);
        }
      }
      if ((str != null) && (str.equals("OFX"))) {
        bool = true;
      }
    }
    return bool;
  }
  
  protected boolean cleanupRequest()
  {
    this.messages = null;
    this.message = null;
    this.accounts = null;
    this.account = null;
    this.payees = null;
    this.payee = null;
    this.payments = null;
    this.payment = null;
    return checkStatus();
  }
  
  public int getMessages(Messages paramMessages)
  {
    int i = getGeneralMail(paramMessages);
    return handleGetMessagesError(i);
  }
  
  public int getMessages(Object paramObject, Messages paramMessages)
  {
    int i;
    if ((paramObject instanceof Accounts))
    {
      i = getBankMail(paramMessages, this.accounts);
      i = handleGetMessagesError(i);
    }
    else if (paramObject == null)
    {
      i = getPaymentMail(paramMessages);
      i = handleGetMessagesError(i);
    }
    else
    {
      i = 2;
    }
    return i;
  }
  
  protected int handleGetMessagesError(int paramInt)
  {
    if (paramInt == 0)
    {
      if (this.status != 0)
      {
        logError(mapError(this.status));
        paramInt = this.lastError;
      }
    }
    else {
      paramInt = this.lastError;
    }
    return paramInt;
  }
  
  public int sendMessage(Message paramMessage)
  {
    boolean bool = false;
    this.message = paramMessage;
    logStart(getClass().getName() + ".sendMail:");
    char[] arrayOfChar = sendRequest(formatSendMailRequest());
    bool = handleEResponse(arrayOfChar);
    if (!cleanupRequest()) {
      bool = false;
    }
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int sendMessage(Message paramMessage, Object paramObject)
  {
    int i = 2;
    if ((paramObject instanceof Account)) {
      i = sendBankMail(paramMessage, (Account)paramObject);
    } else if ((paramObject instanceof Payment)) {
      i = sendPaymentMail(paramMessage, (Payment)paramObject);
    }
    return i;
  }
  
  public int sendBankMail(Message paramMessage, Account paramAccount)
  {
    boolean bool = false;
    this.message = paramMessage;
    this.account = paramAccount;
    logStart(getClass().getName() + ".sendBankMail:");
    char[] arrayOfChar = sendRequest(formatSendBankMailRequest());
    bool = handleResponse(arrayOfChar);
    if (!cleanupRequest()) {
      bool = false;
    }
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  protected int getGeneralMail(Messages paramMessages)
  {
    boolean bool = false;
    this.messages = paramMessages;
    logStart(getClass().getName() + ".getGeneralMail:");
    char[] arrayOfChar = sendRequest(formatGetGeneralMailRequest());
    bool = handleEResponse(arrayOfChar);
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  protected int getBankMail(Messages paramMessages, Accounts paramAccounts)
  {
    boolean bool = false;
    this.accounts = paramAccounts;
    this.messages = paramMessages;
    logStart(getClass().getName() + ".getBankMail:");
    char[] arrayOfChar = sendRequest(formatGetBankMailRequest());
    bool = handleResponse(arrayOfChar);
    cleanupRequest();
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  protected char[] formatGetGeneralMailRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetGeneralMailRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "EMAILMSGSRQV1");
    appendBeginTag(localStringBuffer, "MAILSYNCRQ");
    if ((this.mailToken != null) && (this.mailToken.length() > 0)) {
      appendTag(localStringBuffer, "TOKEN", this.mailToken);
    } else {
      appendTag(localStringBuffer, "REFRESH", "Y");
    }
    appendTag(localStringBuffer, "REJECTIFMISSING", "N");
    appendTag(localStringBuffer, "INCIMAGES", "N");
    appendTag(localStringBuffer, "USEHTML", "N");
    appendEndTag(localStringBuffer, "MAILSYNCRQ");
    appendEndTag(localStringBuffer, "EMAILMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatGetBankMailRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetBankMailRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    if (this.accounts != null)
    {
      appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
      int i = this.accounts.size();
      for (int j = 0; j < i; j++)
      {
        Account localAccount = (Account)this.accounts.get(j);
        if (localAccount != null)
        {
          appendBeginTag(localStringBuffer, "BANKMAILSYNCRQ");
          if ((this.bankMailToken != null) && (this.bankMailToken.length() > 0)) {
            appendTag(localStringBuffer, "TOKEN", this.bankMailToken);
          } else {
            appendTag(localStringBuffer, "REFRESH", "Y");
          }
          appendTag(localStringBuffer, "REJECTIFMISSING", "N");
          appendTag(localStringBuffer, "INCIMAGES", "N");
          appendTag(localStringBuffer, "USEHTML", "N");
          formatFromAccount(localStringBuffer, localAccount);
          appendEndTag(localStringBuffer, "BANKMAILSYNCRQ");
        }
      }
      appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    }
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatSendMailRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendMailRequest:");
    }
    this.message.setID(getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "EMAILMSGSRQV1");
    appendBeginTag(localStringBuffer, "MAILTRNRQ");
    appendTag(localStringBuffer, "TRNUID", this.message.getID());
    appendBeginTag(localStringBuffer, "MAILRQ");
    formatMAIL(localStringBuffer);
    appendEndTag(localStringBuffer, "MAILRQ");
    appendEndTag(localStringBuffer, "MAILTRNRQ");
    appendEndTag(localStringBuffer, "EMAILMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatSendBankMailRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendMailRequest:");
    }
    this.message.setID(getUniqueSeqNum());
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BANKMSGSRQV1");
    appendBeginTag(localStringBuffer, "BANKMAILTRNRQ");
    appendTag(localStringBuffer, "TRNUID", this.message.getID());
    appendBeginTag(localStringBuffer, "BANKMAILRQ");
    formatFromAccount(localStringBuffer, this.account);
    formatMAIL(localStringBuffer);
    appendEndTag(localStringBuffer, "BANKMAILRQ");
    appendEndTag(localStringBuffer, "BANKMAILTRNRQ");
    appendEndTag(localStringBuffer, "BANKMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected void parseBILLPAYMSGSRSV1()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("BILLPAYMSGSRSV1"))) {
      if (str.equals("PMTMAILSYNCRS")) {
        parsePMTMAILSYNCRS();
      } else if (str.equals("PMTMAILTRNRS")) {
        parsePMTMAILTRNRS();
      } else if ((str.equals("PAYEETRNRS")) || (str.equals("PAYEESYNCRS")) || (str.equals("PMTTRNRS")) || (str.equals("RECPMTTRNRS")) || (str.equals("PMTINQTRNRS")) || (str.equals("PMTSYNCRS")) || (str.equals("RECPMTSYNCRS"))) {
        parseNotSupported(str);
      } else {
        throw new IllegalArgumentException("parseBILLPAYMSGSRSV1 failed on tag=" + str);
      }
    }
  }
  
  protected void parsePMTMAILSYNCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTMAILSYNCRS"))) {
      if (str.equals("PMTMAILTRNRS")) {
        parsePMTMAILTRNRS();
      } else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC"))) {
        setMessage(str, getField());
      } else {
        throw new IllegalArgumentException("parsePMTMAILTRNRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseBANKMAILSYNCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("BANKMAILSYNCRS"))) {
      if (str.equals("BANKMAILTRNRS")) {
        parseBANKMAILTRNRS();
      } else if (str.equals("BANKACCTFROM")) {
        parseBANKACCTFROM();
      } else if (str.equals("CCACCTFROM")) {
        parseCCACCTFROM();
      } else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC"))) {
        setMessage(str, getField());
      } else {
        throw new IllegalArgumentException("parseBANKMAILSYNCRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseBANKMAILTRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("BANKMAILTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("BANKMAILRS")) {
        parseBANKMAILRS();
      } else if (str.equals("CHKMAILRS")) {
        parseNotSupported(str);
      } else if (str.equals("DEPMAILRS")) {
        parseNotSupported(str);
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setMessage(str, getField());
      } else {
        throw new IllegalArgumentException("parseBANKMAILTRNRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseBANKMAILRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("BANKMAILRS"))) {
      if (str.equals("MAIL")) {
        parseMAIL();
      } else if (str.equals("BANKACCTFROM")) {
        parseBANKACCTFROM();
      } else if (str.equals("CCACCTFROM")) {
        parseCCACCTFROM();
      } else {
        throw new IllegalArgumentException("parseBANKMAILRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseEMAILMSGSRSV1()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("EMAILMSGSRSV1"))) {
      if (str.equals("MAILTRNRS")) {
        parseMAILTRNRS();
      } else if (str.equals("MAILSYNCRS")) {
        parseMAILSYNCRS();
      } else if (str.equals("GETMIMETRNRS")) {
        parseNotSupported(str);
      } else {
        throw new IllegalArgumentException("parseEMAILMSGSRSV1 failed on tag=" + str);
      }
    }
  }
  
  protected void parseMAILSYNCRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("MAILSYNCRS"))) {
      if (str.equals("MAILTRNRS")) {
        parseMAILTRNRS();
      } else if ((str.equals("TOKEN")) || (str.equals("LOSTSYNC"))) {
        setMessage(str, getField());
      } else {
        throw new IllegalArgumentException("parseMAILSYNCRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseMAILTRNRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("MAILTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("MAILRS")) {
        parseMAILRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setMessage(str, getField());
      } else {
        throw new IllegalArgumentException("parseMAILTRNRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseMAILRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("MAILRS"))) {
      if (str.equals("MAIL")) {
        parseMAIL();
      } else {
        throw new IllegalArgumentException("parseMAILRS failed on tag=" + str);
      }
    }
  }
  
  protected void parseSTATUS()
  {
    super.parseSTATUS();
    setMessage("STATUS", String.valueOf(this.status));
  }
  
  protected int mapError(int paramInt)
  {
    int i = 1;
    switch (paramInt)
    {
    case 16500: 
      i = 2;
      break;
    case 16501: 
      i = 3;
      break;
    case 16502: 
      i = 3;
      break;
    case 16503: 
      i = 2;
      break;
    default: 
      i = super.mapError(paramInt);
    }
    return i;
  }
  
  public int getPaymentMail(Messages paramMessages)
  {
    boolean bool = false;
    this.messages = paramMessages;
    logStart(getClass().getName() + ".getPaymentMail:");
    char[] arrayOfChar = sendRequest(formatGetPaymentMailRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    cleanupRequest();
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  public int sendPaymentMail(Message paramMessage, Payment paramPayment)
  {
    boolean bool = false;
    this.account = paramPayment.getAccount();
    this.payment = paramPayment;
    this.message = paramMessage;
    logStart(getClass().getName() + ".sendPaymentMail:");
    char[] arrayOfChar = sendRequest(formatSendMessageRequest());
    bool = handleResponse(arrayOfChar);
    if ((bool) && (this.status != 0))
    {
      bool = false;
      logError(mapError(this.status));
    }
    if (!cleanupRequest()) {
      bool = false;
    }
    if (bool) {
      return 0;
    }
    return this.lastError;
  }
  
  protected char[] formatGetPaymentMailRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatGetMailRequest:");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendBeginTag(localStringBuffer, "PMTMAILSYNCRQ");
    if ((this.paymentMailToken != null) && (this.paymentMailToken.length() > 0)) {
      appendTag(localStringBuffer, "TOKEN", this.paymentMailToken);
    } else {
      appendTag(localStringBuffer, "REFRESH", "Y");
    }
    appendTag(localStringBuffer, "REJECTIFMISSING", "N");
    appendTag(localStringBuffer, "INCIMAGES", "N");
    appendTag(localStringBuffer, "USEHTML", "N");
    appendEndTag(localStringBuffer, "PMTMAILSYNCRQ");
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected char[] formatSendMessageRequest()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatSendMessageRequest:");
    }
    this.message.setID(getUniqueSeqNum());
    if ((this.message.getSubject() == null) || (this.message.getSubject().length() == 0)) {
      this.message.setSubject("Payment Inquiry");
    }
    appendBeginTag(localStringBuffer, "OFX");
    formatSIGNONMSGSRQV1(localStringBuffer);
    appendBeginTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendBeginTag(localStringBuffer, "PMTMAILTRNRQ");
    appendTag(localStringBuffer, "TRNUID", this.message.getID());
    appendBeginTag(localStringBuffer, "PMTMAILRQ");
    formatMAIL(localStringBuffer);
    if (this.payment != null)
    {
      appendTag(localStringBuffer, "SRVRTID", this.payment.getID());
      formatPaymentRequest(localStringBuffer, this.account, this.payment, -1);
    }
    appendEndTag(localStringBuffer, "PMTMAILRQ");
    appendEndTag(localStringBuffer, "PMTMAILTRNRQ");
    appendEndTag(localStringBuffer, "BILLPAYMSGSRQV1");
    appendEndTag(localStringBuffer, "OFX");
    return localStringBuffer.toString().toCharArray();
  }
  
  protected void parsePMTMAILTRNRS()
  {
    this.payment = null;
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTMAILTRNRS"))) {
      if (str.equals("STATUS")) {
        parseSTATUS();
      } else if (str.equals("PMTMAILRS")) {
        parsePMTMAILRS();
      } else if ((str.equals("TRNUID")) || (str.equals("CLTCOOKIE"))) {
        setMessage(str, getField());
      } else {
        throw new IllegalArgumentException("parsePMTMAILTRNRS failed on tag=" + str);
      }
    }
    setPayment();
  }
  
  protected void parsePMTMAILRS()
  {
    String str;
    while (((str = getToken()) != null) && (!str.equals("PMTMAILRS"))) {
      if (str.equals("PMTINFO")) {
        parsePMTINFO();
      } else if (str.equals("MAIL")) {
        parseMAIL();
      } else if (str.equals("SRVRTID")) {
        setMessage(str, getField());
      } else {
        throw new IllegalArgumentException("parsePMTMAILRS failed on tag=" + str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ofx.Messaging
 * JD-Core Version:    0.7.0.1
 */