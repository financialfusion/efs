package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRestrictedAccountsForGroup
  extends BaseTask
  implements AdminTask, Task
{
  private String Z2 = "GroupAccounts";
  private int Z3 = -1;
  private String Z1 = "";
  private int Z5 = -1;
  private int Z4 = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = super.getSuccessURL();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.accounts.Accounts localAccounts = null;
    try
    {
      if (this.Z4 != -1) {
        localAccounts = com.ffusion.csil.core.Accounts.getAccountsById(localSecureUser, this.Z4, null);
      } else {
        localAccounts = com.ffusion.csil.core.Accounts.getAccountsByBusinessEmployee(localSecureUser, null);
      }
      int i = -1;
      if (localSecureUser.getUserType() == 1) {
        i = localSecureUser.getBusinessID();
      } else {
        i = this.Z3;
      }
      Object localObject1;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      if ((this.Z1 == null) || (this.Z1.length() == 0))
      {
        localObject1 = new MultiEntitlement();
        localObject2 = new String[1];
        localObject3 = new String[] { "Account" };
        localObject4 = localAccounts.iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject5 = (Account)((Iterator)localObject4).next();
          localObject2[0] = EntitlementsUtil.getEntitlementObjectId((Account)localObject5);
          ((MultiEntitlement)localObject1).setObjects((String[])localObject3, (String[])localObject2);
          localObject6 = EntitlementsUtil.checkAccountEntitlement(this.Z3, (MultiEntitlement)localObject1, i);
          if (localObject6 != null) {
            ((Iterator)localObject4).remove();
          }
        }
      }
      else
      {
        localObject1 = null;
        localObject2 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject2).setEntitlementGroupId(this.Z3);
        ((EntitlementGroupMember)localObject2).setId(this.Z1);
        ((EntitlementGroupMember)localObject2).setMemberType("USER");
        ((EntitlementGroupMember)localObject2).setMemberSubType(Integer.toString(this.Z5));
        localObject1 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements((EntitlementGroupMember)localObject2, this.Z3);
        localObject3 = new HashMap();
        localObject4 = new HashMap();
        localObject5 = ((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).iterator();
        localObject6 = null;
        while (((Iterator)localObject5).hasNext())
        {
          localObject7 = (Entitlement)((Iterator)localObject5).next();
          localObject6 = ((Entitlement)localObject7).getObjectType();
          if (localObject6 != null) {
            if ((((String)localObject6).equals("Account")) && ((((Entitlement)localObject7).getOperationName() == null) || (((Entitlement)localObject7).getOperationName().equals("Access")))) {
              ((HashMap)localObject3).put(((Entitlement)localObject7).getObjectId(), localObject7);
            } else if ((((String)localObject6).equals("AccountGroup")) && ((((Entitlement)localObject7).getOperationName() == null) || (((Entitlement)localObject7).getOperationName().equals("Access")))) {
              ((HashMap)localObject4).put(((Entitlement)localObject7).getObjectId(), localObject7);
            }
          }
        }
        if (((HashMap)localObject3).size() == 0)
        {
          localHttpSession.setAttribute(this.Z2, localAccounts);
          localObject7 = str1;
          return localObject7;
        }
        Object localObject7 = localAccounts.iterator();
        while (((Iterator)localObject7).hasNext())
        {
          Account localAccount = (Account)((Iterator)localObject7).next();
          String str2 = EntitlementsUtil.getEntitlementObjectId(localAccount);
          if (((HashMap)localObject3).containsKey(str2))
          {
            ArrayList localArrayList1 = new ArrayList();
            ArrayList localArrayList2 = null;
            localArrayList1.add(EntitlementsUtil.getEntitlementObjectId(localAccount));
            int j = 0;
            localArrayList2 = AccountGroup.getAccountGroupIds(localSecureUser, i, localArrayList1, null);
            Iterator localIterator = localArrayList2.iterator();
            while (localIterator.hasNext()) {
              if (!((HashMap)localObject4).containsKey(localIterator.next())) {
                j = 1;
              }
            }
            if (j == 0) {
              ((Iterator)localObject7).remove();
            }
          }
        }
      }
      localHttpSession.setAttribute(this.Z2, localAccounts);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 4512;
      str1 = this.taskErrorURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    finally
    {
      this.Z3 = -1;
      this.Z1 = "";
      this.Z5 = -1;
      this.Z4 = -1;
    }
    return str1;
  }
  
  public void setGroupID(String paramString)
  {
    this.Z3 = Integer.parseInt(paramString);
  }
  
  public void setMemberID(String paramString)
  {
    this.Z1 = paramString;
  }
  
  public void setUserType(String paramString)
  {
    this.Z5 = Integer.parseInt(paramString);
  }
  
  public void setGroupAccountsName(String paramString)
  {
    this.Z2 = paramString;
  }
  
  public void setOwnerID(String paramString)
  {
    this.Z4 = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetRestrictedAccountsForGroup
 * JD-Core Version:    0.7.0.1
 */