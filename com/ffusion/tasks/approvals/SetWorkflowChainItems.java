package com.ffusion.tasks.approvals;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsChainItems;
import com.ffusion.beans.approvals.ApprovalsLevel;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetWorkflowChainItems
  extends BaseTask
{
  private String aOq = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ApprovalsLevel localApprovalsLevel = (ApprovalsLevel)localHttpSession.getAttribute("ApprovalsLevel");
    ApprovalsChainItems localApprovalsChainItems = (ApprovalsChainItems)localHttpSession.getAttribute("ApprovalsChainItems");
    localHttpSession.removeAttribute("ApprovalsStallConditionsStallNumber");
    localHttpSession.removeAttribute("ApprovalsStallConditionsGroupID");
    localHttpSession.removeAttribute("ApprovalsStallConditionsApprovalGroupIDs");
    localHttpSession.removeAttribute("ApprovalsStallConditionsMemberList");
    try
    {
      if ((localHttpSession.getAttribute("BusinessEmployees") != null) || (localHttpSession.getAttribute("ApprovalsEntitledBankGroups") != null) || (localHttpSession.getAttribute("ApprovalsEntitledBankEmployees") != null))
      {
        try
        {
          Approvals.setWorkflowChainItems(localSecureUser, localApprovalsLevel, localApprovalsChainItems, localHashMap);
        }
        catch (CSILException localCSILException1)
        {
          Integer localInteger = (Integer)localHashMap.get("ApprovalsStallConditionsStallCount");
          if ((localInteger != null) && (localInteger.intValue() > 0))
          {
            ArrayList localArrayList1 = (ArrayList)localHashMap.get("ApprovalsStallConditionsUserIDs");
            ArrayList localArrayList2 = (ArrayList)localHashMap.get("ApprovalsStallConditionsEntitlementGroupIDs");
            ArrayList localArrayList3 = (ArrayList)localHashMap.get("ApprovalsStallConditionsApprovalGroupIDs");
            int i = 1;
            if (localSecureUser.getUserType() != 1) {
              i = 0;
            }
            ArrayList localArrayList4 = new ArrayList();
            Object localObject;
            Iterator localIterator;
            int j;
            int k;
            String str2;
            if (i != 0)
            {
              localObject = (BusinessEmployees)localHttpSession.getAttribute("BusinessEmployees");
              if ((localArrayList1 != null) && (!localArrayList1.isEmpty()))
              {
                localIterator = localArrayList1.iterator();
                while (localIterator.hasNext())
                {
                  j = ((Integer)localIterator.next()).intValue();
                  k = jdMethod_for(j, (BusinessEmployees)localObject).intValue();
                  str2 = jdMethod_for(j, k, (BusinessEmployees)localObject);
                  if (str2 != null) {
                    localArrayList4.add(str2);
                  }
                }
              }
            }
            else
            {
              localObject = (BankEmployees)localHttpSession.getAttribute("ApprovalsEntitledBankEmployees");
              if ((localArrayList1 != null) && (!localArrayList1.isEmpty()))
              {
                localIterator = localArrayList1.iterator();
                while (localIterator.hasNext())
                {
                  j = ((Integer)localIterator.next()).intValue();
                  k = jdMethod_for(j, (BankEmployees)localObject).intValue();
                  str2 = jdMethod_for(j, k, (BankEmployees)localObject);
                  if (str2 != null) {
                    localArrayList4.add(str2);
                  }
                }
              }
            }
            localHttpSession.setAttribute("ApprovalsStallConditionsStallNumber", localInteger);
            if (localArrayList2 != null) {
              localHttpSession.setAttribute("ApprovalsStallConditionsGroupID", localArrayList2);
            }
            if (localArrayList3 != null) {
              localHttpSession.setAttribute("ApprovalsStallConditionsApprovalGroupIDs", localArrayList3);
            }
            if (!localArrayList4.isEmpty()) {
              localHttpSession.setAttribute("ApprovalsStallConditionsMemberList", localArrayList4);
            }
            localHttpSession.setAttribute("ApprovalsStallConditions", "true");
          }
          else
          {
            throw localCSILException1;
          }
        }
      }
      else
      {
        str1 = this.taskErrorURL;
        this.error = 31003;
      }
    }
    catch (CSILException localCSILException2)
    {
      str1 = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException2);
    }
    return str1;
  }
  
  private Integer jdMethod_for(int paramInt, BusinessEmployees paramBusinessEmployees)
    throws CSILException
  {
    int i = 0;
    Integer localInteger = new Integer(-1);
    i = paramBusinessEmployees.size();
    for (int j = 0; j < i; j++)
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramBusinessEmployees.get(j);
      if (localBusinessEmployee.getIdValue() == paramInt)
      {
        localInteger = new Integer(localBusinessEmployee.getEntitlementGroupId());
        return localInteger;
      }
    }
    return localInteger;
  }
  
  private Integer jdMethod_for(int paramInt, BankEmployees paramBankEmployees)
    throws CSILException
  {
    int i = 0;
    Integer localInteger = new Integer(-1);
    i = paramBankEmployees.size();
    for (int j = 0; j < i; j++)
    {
      BankEmployee localBankEmployee = (BankEmployee)paramBankEmployees.get(j);
      if (Integer.parseInt(localBankEmployee.getEntitlementGroupMember().getId()) == paramInt)
      {
        localInteger = new Integer(localBankEmployee.getEntitlementGroupMember().getEntitlementGroupId());
        return localInteger;
      }
    }
    return localInteger;
  }
  
  private String jdMethod_for(int paramInt1, int paramInt2, BusinessEmployees paramBusinessEmployees)
    throws CSILException
  {
    int i = 0;
    String str = null;
    i = paramBusinessEmployees.size();
    for (int j = 0; j < i; j++)
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramBusinessEmployees.get(j);
      if ((localBusinessEmployee.getIdValue() == paramInt1) && (localBusinessEmployee.getEntitlementGroupId() == paramInt2))
      {
        str = localBusinessEmployee.getName();
        return str;
      }
    }
    return str;
  }
  
  private String jdMethod_for(int paramInt1, int paramInt2, BankEmployees paramBankEmployees)
    throws CSILException
  {
    int i = 0;
    String str = null;
    i = paramBankEmployees.size();
    for (int j = 0; j < i; j++)
    {
      BankEmployee localBankEmployee = (BankEmployee)paramBankEmployees.get(j);
      if ((Integer.parseInt(localBankEmployee.getEntitlementGroupMember().getId()) == paramInt1) && (localBankEmployee.getEntitlementGroupMember().getEntitlementGroupId() == paramInt2))
      {
        str = localBankEmployee.getName();
        return str;
      }
    }
    return str;
  }
  
  public void setAffiliateID(String paramString)
  {
    this.aOq = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.SetWorkflowChainItems
 * JD-Core Version:    0.7.0.1
 */