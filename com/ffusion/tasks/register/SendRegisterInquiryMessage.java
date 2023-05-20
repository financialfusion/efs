package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.efs.adapters.profile.MessageQueueAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.messages.SendMessage;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendRegisterInquiryMessage
  extends SendMessage
{
  private String qQ = "RegisterTransaction";
  private String qU = "Account";
  private String qT = "29";
  private boolean qS = false;
  private SecureUser qR;
  
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
      if (this.processFlag) {
        str = sendMessage(localHttpSession);
      } else {
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
    String str = this.taskErrorURL;
    this.error = 0;
    if (this.processFlag)
    {
      this.processFlag = false;
      if ((getMemo() == null) || (getMemo().trim().equals("")))
      {
        this.error = 100105;
        str = this.taskErrorURL;
        return str;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("SERVICE", paramHttpSession.getAttribute("com.ffusion.services.Messaging3"));
      try
      {
        Messages.sendMessage(this.qR, this, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0) {
        str = this.successURL;
      }
    }
    return str;
  }
  
  protected void initProcess(ServletContext paramServletContext, HttpSession paramHttpSession)
  {
    this.qR = ((SecureUser)paramHttpSession.getAttribute("SecureUser"));
    RegisterTransaction localRegisterTransaction = (RegisterTransaction)paramHttpSession.getAttribute(getRegisterTransactionSessionID());
    Account localAccount = (Account)paramHttpSession.getAttribute(getAccountSessionID());
    MessageQueue localMessageQueue = jdMethod_new(getQueueID(), this.qR.getBankID());
    if (this.qR == null)
    {
      this.error = 20009;
    }
    else if (localRegisterTransaction == null)
    {
      this.error = 20003;
    }
    else if (localAccount == null)
    {
      this.error = 20001;
    }
    else if (localMessageQueue == null)
    {
      this.error = 8021;
    }
    else
    {
      this.initFlag = false;
      String str = this.qR.getLocaleLanguage();
      Locale localLocale = this.qR.getLocale();
      setToName(localMessageQueue.getQueueName(str));
      setTo(localMessageQueue.getQueueID());
      setToType("QUEUE");
      setSubject(localMessageQueue.getQueueName(str));
      setFromType("CUSTOMER");
      setFromName(this.qR.getUserName());
      setFrom(this.qR.getProfileID());
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(jdMethod_for("TransactionProperty0", localAccount.getRoutingNum(), localLocale));
      localStringBuffer.append(jdMethod_for("TransactionProperty1", localAccount.getNumberMasked(), localLocale));
      localStringBuffer.append(jdMethod_for("TransactionProperty2", localRegisterTransaction.getDateIssued(), localLocale));
      localStringBuffer.append(jdMethod_for("TransactionProperty3", localRegisterTransaction.getAmount(), localLocale));
      localStringBuffer.append(jdMethod_for("TransactionProperty4", localRegisterTransaction.getType(), localLocale));
      localStringBuffer.append(jdMethod_for("TransactionProperty5", localRegisterTransaction.getReferenceNumber(), localLocale));
      localStringBuffer.append(jdMethod_for("TransactionProperty6", localRegisterTransaction.getMemo(), localLocale));
      localStringBuffer.append(jdMethod_for("TransactionProperty7", localRegisterTransaction.getPayeeName(), localLocale));
      localStringBuffer.append(jdMethod_for("TransactionProperty8", jdMethod_for(localRegisterTransaction.getStatusValue(), this.qS, localLocale), localLocale));
      setComment(localStringBuffer.toString());
    }
  }
  
  private String jdMethod_for(String paramString1, String paramString2, Locale paramLocale)
  {
    String[] arrayOfString = { paramString2 };
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.register.resources", paramString1, arrayOfString);
    String str = (String)localLocalizableString.localize(paramLocale);
    str = str + ";\n";
    return str;
  }
  
  private String jdMethod_for(int paramInt, boolean paramBoolean, Locale paramLocale)
  {
    String str1 = "";
    String str2 = "ReconciliationStatus0";
    if (!paramBoolean)
    {
      if (paramInt == 0) {
        str2 = "ReconciliationStatus0";
      } else if (paramInt == 2) {
        str2 = "ReconciliationStatus1";
      } else if (paramInt == 3) {
        str2 = "ReconciliationStatus2";
      }
    }
    else if (paramInt == 3) {
      str2 = "ReconciliationStatus3";
    }
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.register.resources", str2, new String[0]);
    str1 = (String)localLocalizableString.localize(paramLocale);
    return str1;
  }
  
  private static MessageQueues jdMethod_try(String paramString1, String paramString2)
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
  
  private MessageQueue jdMethod_new(String paramString1, String paramString2)
  {
    MessageQueue localMessageQueue = null;
    MessageQueues localMessageQueues = jdMethod_try(paramString1, paramString2);
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
  
  public void setAccountSessionID(String paramString)
  {
    this.qU = paramString;
  }
  
  public String getAccountSessionID()
  {
    return this.qU;
  }
  
  public void setRegisterTransactionSessionID(String paramString)
  {
    this.qQ = paramString;
  }
  
  public String getRegisterTransactionSessionID()
  {
    return this.qQ;
  }
  
  public void setQueueID(String paramString)
  {
    this.qT = paramString;
  }
  
  public String getQueueID()
  {
    return this.qT;
  }
  
  public boolean getTransactionHasDiscrepancies()
  {
    return this.qS;
  }
  
  public void setTransactionHasDiscrepancies(String paramString)
  {
    if ("true".equals(paramString)) {
      this.qS = true;
    } else {
      this.qS = false;
    }
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SendRegisterInquiryMessage
 * JD-Core Version:    0.7.0.1
 */