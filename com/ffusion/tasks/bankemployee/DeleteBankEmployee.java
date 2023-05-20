package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteBankEmployee
  extends BaseTask
  implements BankEmployeeTask
{
  protected boolean bProcess = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.bProcess == true)
    {
      BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute("ModifiedBankEmployee");
      if (localBankEmployee == null)
      {
        str = this.taskErrorURL;
        this.error = 5505;
      }
      else
      {
        try
        {
          if ((checkAssignedCaseTypesForDelete(localHttpSession) == true) && (checkAssignedCases(localHttpSession) == true))
          {
            HashMap localHashMap = null;
            SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            BankEmployeeAdmin.deleteBankEmployee(localSecureUser, localBankEmployee, localHashMap);
            if (this.error == 0)
            {
              BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
              if (localBankEmployees != null) {
                localBankEmployees.removeByID(localBankEmployee.getId());
              }
              localHttpSession.setAttribute("BankEmployees", localBankEmployees);
            }
          }
          else
          {
            str = this.taskErrorURL;
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
      }
    }
    return str;
  }
  
  protected boolean checkAssignedCases(HttpSession paramHttpSession)
    throws CSILException
  {
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    BankEmployee localBankEmployee = (BankEmployee)paramHttpSession.getAttribute("ModifiedBankEmployee");
    String str = localBankEmployee.getId();
    int i = Messages.getAssignedMessageCount(localSecureUser, str, localHashMap);
    if (i != 0)
    {
      this.error = 5528;
      return false;
    }
    return true;
  }
  
  protected boolean checkAssignedCaseTypesForDelete(HttpSession paramHttpSession)
  {
    boolean bool = true;
    BankEmployee localBankEmployee1 = (BankEmployee)paramHttpSession.getAttribute("ModifiedBankEmployee");
    String str = localBankEmployee1.getId();
    MessageQueues localMessageQueues = (MessageQueues)paramHttpSession.getAttribute("MessageQueues");
    if (localMessageQueues == null)
    {
      this.error = 8020;
      return false;
    }
    localMessageQueues.setFilter("All");
    Iterator localIterator = localMessageQueues.iterator();
    while (localIterator.hasNext())
    {
      MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
      MessageQueueMembers localMessageQueueMembers = localMessageQueue.getActiveQueueMembers();
      BankEmployee localBankEmployee2 = localMessageQueueMembers.getByID(str);
      if (localBankEmployee2 != null)
      {
        if ((localMessageQueueMembers != null) && (localMessageQueueMembers.size() == 1))
        {
          this.error = 5527;
          return false;
        }
        localMessageQueueMembers.removeByID(str);
      }
    }
    return bool;
  }
  
  public void setProcess(String paramString)
  {
    this.bProcess = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.DeleteBankEmployee
 * JD-Core Version:    0.7.0.1
 */