package com.ffusion.tasks.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindMessageThreads
  extends BaseTask
  implements Task
{
  private String rx = null;
  private String rH = null;
  private String ru = null;
  private String rG = null;
  private String rC = null;
  private String ry = null;
  private DateTime rw = null;
  private DateTime rD = null;
  private String rA = null;
  private static String rE = "MM/DD/YYYY";
  private String rF = null;
  private String rv = null;
  private UserLocale rz = null;
  private boolean rI = false;
  private boolean rB = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MessageThreads localMessageThreads = null;
    this.rz = ((UserLocale)localHttpSession.getAttribute("UserLocale"));
    rE = this.rz.getDateFormat();
    MessageQueues localMessageQueues1 = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
    MessageQueues localMessageQueues2 = (MessageQueues)localHttpSession.getAttribute("MessageQueuesInactive");
    BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
    if (localBankEmployees == null)
    {
      this.error = 5503;
      return this.taskErrorURL;
    }
    if ((localMessageQueues1 == null) && (localMessageQueues2 == null))
    {
      this.error = 8020;
      return this.taskErrorURL;
    }
    int i = 0;
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Business localBusiness = new Business();
    User localUser = new User();
    MessageThread localMessageThread1 = new MessageThread();
    if ((this.rx != null) && (this.rx.length() > 0))
    {
      localMessageThread1.setCaseNum(this.rx.trim());
      i = 1;
    }
    if ((this.rH != null) && (this.rH.length() > 0))
    {
      localUser.setFirstName(this.rH.trim());
      i = 1;
    }
    if ((this.ru != null) && (this.ru.length() > 0))
    {
      localUser.setLastName(this.ru.trim());
      i = 1;
    }
    if ((this.rG != null) && (this.rG.length() > 0))
    {
      localUser.setUserName(this.rG.trim());
      i = 1;
    }
    if ((this.rF != null) && (this.rF.length() > 0))
    {
      localUser.setAffiliateBankID(this.rF.trim());
      i = 1;
    }
    if ((this.rC != null) && (this.rC.length() > 0))
    {
      localBusiness.setBusinessName(this.rC.trim());
      i = 1;
    }
    if ((this.ry != null) && (this.ry.length() > 0))
    {
      localMessageThread1.setThreadStatus(this.ry);
      i = 1;
    }
    if ((this.rA != null) && (this.rA.length() > 0)) {
      localMessageThread1.set("BANK_ID", this.rA);
    }
    if ((this.rv != null) && (this.rv.length() > 0))
    {
      localMessageThread1.setQueueID(this.rv.trim());
      i = 1;
    }
    if ((this.rI) || (this.rB))
    {
      this.error = 44;
      this.rI = false;
      this.rB = false;
      return this.taskErrorURL;
    }
    if (this.rw != null) {
      i = 1;
    }
    if (this.rD != null) {
      i = 1;
    }
    if ((this.rw != null) && (this.rD != null))
    {
      long l = this.rw.getTime().getTime() - this.rD.getTime().getTime();
      if (l > 0L)
      {
        this.error = 130;
        return this.taskErrorURL;
      }
    }
    if (i == 0)
    {
      this.error = 8049;
      return this.taskErrorURL;
    }
    try
    {
      localMessageThreads = Messages.findMessageThreads(localSecureUser, localHashMap, localUser, localBusiness, localMessageThread1, this.rw, this.rD);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      Iterator localIterator = localMessageThreads.iterator();
      while (localIterator.hasNext())
      {
        MessageThread localMessageThread2 = (MessageThread)localIterator.next();
        String str2 = localMessageThread2.getEmployeeID();
        if (str2 != null)
        {
          localObject = localBankEmployees.getByID(str2);
          if (localObject != null) {
            localMessageThread2.setEmployeeName(((BankEmployee)localObject).getFullNameWithLoginId());
          }
        }
        Object localObject = localMessageThread2.getQueueID();
        if (localObject != null)
        {
          MessageQueue localMessageQueue = null;
          if (localMessageQueues1 != null) {
            localMessageQueue = localMessageQueues1.getByID((String)localObject);
          }
          if ((localMessageQueue == null) && (localMessageQueues2 != null)) {
            localMessageQueue = localMessageQueues2.getByID((String)localObject);
          }
          if (localMessageQueue != null) {
            localMessageThread2.setQueueName(localMessageQueue.getQueueName());
          }
        }
      }
      localHttpSession.setAttribute("MessageThreads", localMessageThreads);
    }
    else
    {
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setCaseNum(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.rx = paramString;
    } else {
      this.rx = null;
    }
  }
  
  public String getCaseNum()
  {
    return this.rx;
  }
  
  public void setFirstName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.rH = paramString;
    } else {
      this.rH = null;
    }
  }
  
  public String getFirstName()
  {
    return this.rH;
  }
  
  public void setLastName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.ru = paramString;
    } else {
      this.ru = null;
    }
  }
  
  public String getLastName()
  {
    return this.ru;
  }
  
  public void setUsername(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.rG = paramString;
    } else {
      this.rG = null;
    }
  }
  
  public String getUsername()
  {
    return this.rG;
  }
  
  public void setBusinessName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.rC = paramString;
    } else {
      this.rC = null;
    }
  }
  
  public String getBusinessName()
  {
    return this.rC;
  }
  
  public void setThreadStatus(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      this.ry = null;
    }
    else
    {
      paramString = paramString.trim().toUpperCase();
      if (paramString.equals("MESSAGE_ALL")) {
        this.ry = "0";
      } else if (paramString.equals("MESSAGE_NEW")) {
        this.ry = "1";
      } else if (paramString.equals("MESSAGE_OPEN")) {
        this.ry = "2";
      } else if (paramString.equals("MESSAGE_IQUERY")) {
        this.ry = "3";
      } else if (paramString.equals("MESSAGE_CQUERY")) {
        this.ry = "4";
      } else if (paramString.equals("MESSAGE_CLOSED")) {
        this.ry = "5";
      } else if (paramString.equals("MESSAGE_NOTES")) {
        this.ry = "6";
      } else if (paramString.equals("MESSAGE_AUTOREPLY")) {
        this.ry = "7";
      } else if (paramString.equals("MESSAGE_PENDING")) {
        this.ry = "9";
      } else if (paramString.equals("MESSAGE_DENIED")) {
        this.ry = "10";
      } else {
        this.ry = paramString;
      }
    }
  }
  
  public String getThreadStatus()
  {
    return this.ry;
  }
  
  public void setStartDate(DateTime paramDateTime)
  {
    this.rw = paramDateTime;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0) && (!paramString.equals(rE))) {
      try
      {
        this.rw = new DateTime(paramString, this.rz.getLocale(), this.rz.getDateFormat());
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        this.rI = true;
      }
    } else {
      this.rw = null;
    }
  }
  
  public void setStartDate(Date paramDate)
  {
    if (paramDate != null)
    {
      this.rw = new DateTime();
      this.rw.setTime(paramDate);
    }
    else
    {
      this.rw = null;
    }
  }
  
  public String getStartDate()
  {
    if (this.rw != null)
    {
      this.rw.setFormat(this.rz.getDateFormat());
      return this.rw.getString();
    }
    return null;
  }
  
  public void setEndDate(DateTime paramDateTime)
  {
    this.rD = paramDateTime;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0) && (!paramString.equals(rE))) {
      try
      {
        this.rD = new DateTime(paramString, this.rz.getLocale(), this.rz.getDateFormat());
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        this.rB = true;
      }
    } else {
      this.rD = null;
    }
  }
  
  public void setEndDate(Date paramDate)
  {
    if (paramDate != null)
    {
      this.rD = new DateTime();
      this.rD.setTime(paramDate);
    }
    else
    {
      this.rD = null;
    }
  }
  
  public String getEndDate()
  {
    if (this.rD != null)
    {
      this.rD.setFormat(this.rz.getDateFormat());
      return this.rD.getString();
    }
    return null;
  }
  
  public String getBankId()
  {
    return this.rA;
  }
  
  public void setBankId(String paramString)
  {
    this.rA = paramString;
  }
  
  public String getAffiliateBankId()
  {
    return this.rF;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.rF = paramString;
  }
  
  public String getQueueId()
  {
    return this.rv;
  }
  
  public void setQueueId(String paramString)
  {
    this.rv = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.FindMessageThreads
 * JD-Core Version:    0.7.0.1
 */