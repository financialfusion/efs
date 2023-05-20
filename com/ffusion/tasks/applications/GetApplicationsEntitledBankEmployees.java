package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApplicationsEntitledBankEmployees
  extends BaseTask
  implements Task
{
  private String uO = "BankEmployees";
  private String uM = "ApplicationsEntitledBankEmployees";
  private static Entitlement uL = new Entitlement("ApplicationCrud", null, null);
  private static Entitlement uN = new Entitlement("BC Manage Multiple Banks Simultaneously", null, null);
  private static Entitlement uK = new Entitlement("Manage Consumer Banking", null, null);
  private static Entitlement uJ = new Entitlement("Manage Corporate Banking", null, null);
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Application localApplication = (Application)localHttpSession.getAttribute("Application");
    if (localApplication == null)
    {
      this.error = 7201;
      return this.taskErrorURL;
    }
    String str = String.valueOf(localApplication.getAffiliateBankID());
    localHttpSession.removeAttribute(this.uM);
    BankEmployees localBankEmployees1 = (BankEmployees)localHttpSession.getAttribute(this.uO);
    if (localBankEmployees1 == null)
    {
      this.error = 7392;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    MessageQueues localMessageQueues = null;
    MessageQueue localMessageQueue = null;
    try
    {
      localMessageQueue = new MessageQueue();
      localMessageQueue.setQueueType(String.valueOf(1));
      HashMap localHashMap = new HashMap();
      localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      localMessageQueues = MessageAdmin.getMessageQueues(localSecureUser, localMessageQueue, localHashMap);
      localMessageQueue = localMessageQueues.getByStatProdID(localApplication.getStatusID(), localApplication.getProductID());
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      return this.serviceErrorURL;
    }
    BankEmployees localBankEmployees2 = new BankEmployees(localBankEmployees1.getLocale());
    Iterator localIterator = localBankEmployees1.iterator();
    BankEmployee localBankEmployee = null;
    EntitlementGroupMember localEntitlementGroupMember = null;
    while (localIterator.hasNext())
    {
      localBankEmployee = (BankEmployee)localIterator.next();
      try
      {
        localEntitlementGroupMember = localBankEmployee.getEntitlementGroupMember();
        if (Entitlements.checkEntitlement(localEntitlementGroupMember, uL))
        {
          ArrayList localArrayList = localBankEmployee.getAffiliateBankIds();
          int i = 0;
          if ((localArrayList != null) && (localArrayList.contains(str))) {
            i = 1;
          } else if ((localBankEmployee.getDefaultAffiliateBankId() != null) && (localBankEmployee.getDefaultAffiliateBankId().trim().equals(str))) {
            i = 1;
          } else if (Entitlements.checkEntitlement(localEntitlementGroupMember, uN)) {
            i = 1;
          }
          int j = 0;
          if (Entitlements.checkEntitlement(localEntitlementGroupMember, uK))
          {
            if (localMessageQueue.getIsConsumer()) {
              j = 1;
            }
          }
          else if ((Entitlements.checkEntitlement(localEntitlementGroupMember, uJ)) && (localMessageQueue.getIsCorporate())) {
            j = 1;
          }
          if ((i != 0) && (j != 0)) {
            localBankEmployees2.add(localBankEmployee);
          }
        }
      }
      catch (CSILException localCSILException2) {}
    }
    localHttpSession.setAttribute(this.uM, localBankEmployees2);
    return this.successURL;
  }
  
  public void setBankEmployeesCollectionName(String paramString)
  {
    this.uO = paramString;
  }
  
  public void setApplicationsEntitledBankEmployeesCollectionName(String paramString)
  {
    this.uM = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetApplicationsEntitledBankEmployees
 * JD-Core Version:    0.7.0.1
 */