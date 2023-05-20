package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.services.BankEmployeeAdmin2;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.bankemployee.BankEmployeeTask;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAppAssignmentsBySupervisor
  extends BaseTask
  implements Task, BankEmployeeTask
{
  private static final String QUEUE_APP_NAME = "application";
  private String ts;
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    MessageQueues localMessageQueues1 = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
    if (localMessageQueues1 == null)
    {
      this.error = 8020;
      str = this.taskErrorURL;
    }
    else
    {
      BankEmployeeAdmin2 localBankEmployeeAdmin2 = (BankEmployeeAdmin2)localHttpSession.getAttribute("BankEmployeeService");
      if (localBankEmployeeAdmin2 != null) {
        localHashMap.put("SERVICE", localBankEmployeeAdmin2);
      }
      if (this._entBypass) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      BankEmployee localBankEmployee1 = new BankEmployee(localLocale);
      localBankEmployee1.setSupervisor(this.ts);
      localBankEmployee1.setStatus("0");
      BankEmployees localBankEmployees = new BankEmployees(localLocale);
      localHashMap.put("BANKEMPLOYEES", localBankEmployees);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localBankEmployees = BankEmployeeAdmin.getBankEmployees(localSecureUser, localBankEmployee1, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      finally
      {
        this._entBypass = false;
      }
      if ((this.error == 0) || (this.error == 5004))
      {
        localMessageQueues1.setFilter("All");
        MessageQueues localMessageQueues2 = new MessageQueues();
        Iterator localIterator = localBankEmployees.iterator();
        Object localObject3;
        Object localObject4;
        Object localObject5;
        while (localIterator.hasNext())
        {
          localObject2 = (BankEmployee)localIterator.next();
          localObject3 = ((BankEmployee)localObject2).getId();
          localObject4 = localMessageQueues1.iterator();
          while (((Iterator)localObject4).hasNext())
          {
            localObject5 = (MessageQueue)((Iterator)localObject4).next();
            if (((MessageQueue)localObject5).getQueueTypeName().equals("application"))
            {
              MessageQueueMembers localMessageQueueMembers = ((MessageQueue)localObject5).getActiveQueueMembers();
              BankEmployee localBankEmployee2 = localMessageQueueMembers.getByID((String)localObject3);
              if (localBankEmployee2 != null) {
                localMessageQueues2.addMessageQueue((MessageQueue)localObject5);
              }
            }
          }
        }
        Object localObject2 = localMessageQueues1.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = (MessageQueue)((Iterator)localObject2).next();
          if (((MessageQueue)localObject3).getQueueTypeName().equals("application"))
          {
            localObject4 = ((MessageQueue)localObject3).getActiveQueueMembers();
            localObject5 = ((MessageQueueMembers)localObject4).getByID(this.ts);
            if (localObject5 != null)
            {
              localMessageQueues2.addMessageQueue((MessageQueue)localObject3);
              break;
            }
          }
        }
        localHttpSession.setAttribute("AppQueueAssignmentsActive", localMessageQueues2);
      }
    }
    return str;
  }
  
  public void setSupervisorID(String paramString)
  {
    this.ts = paramString;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this._entBypass = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this._entBypass);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetAppAssignmentsBySupervisor
 * JD-Core Version:    0.7.0.1
 */