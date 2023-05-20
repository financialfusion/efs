package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccountEntitlementFilterTask
  extends BaseTask
  implements Task
{
  protected String accountsName = "Accounts";
  protected ArrayList entitlementFilters = new ArrayList();
  protected String filteredAccountsName = null;
  protected int _groupId;
  protected String _memberId;
  protected String _memberType;
  protected String _memberSubType;
  private FilteredList kf = null;
  protected String _operation = "";
  protected boolean _checkAccessEntitlement = true;
  private boolean kg = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    FilteredList localFilteredList = (FilteredList)localHttpSession.getAttribute(this.accountsName);
    if ((localFilteredList instanceof Accounts))
    {
      this.kf = new Accounts(localSecureUser.getLocale());
      ((Accounts)this.kf).setSecureUser(localSecureUser);
    }
    else if ((localFilteredList instanceof LockboxAccounts))
    {
      this.kf = new LockboxAccounts(localSecureUser.getLocale());
    }
    else if ((localFilteredList instanceof DisbursementAccounts))
    {
      this.kf = new DisbursementAccounts(localSecureUser.getLocale());
    }
    if (localFilteredList == null)
    {
      this.error = 39;
      str = this.taskErrorURL;
    }
    else
    {
      EntitlementGroupMember localEntitlementGroupMember = null;
      if (this._groupId == 0)
      {
        localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      }
      else if (this._memberId != null)
      {
        localEntitlementGroupMember = new EntitlementGroupMember();
        localEntitlementGroupMember.setMemberType(this._memberType);
        localEntitlementGroupMember.setMemberSubType(this._memberSubType);
        localEntitlementGroupMember.setId(this._memberId);
        localEntitlementGroupMember.setEntitlementGroupId(this._groupId);
      }
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      Iterator localIterator = localFilteredList.iterator();
      String[] arrayOfString1 = { "Account" };
      String[] arrayOfString2 = new String[1];
      String[] arrayOfString3;
      if (this._checkAccessEntitlement)
      {
        arrayOfString3 = new String[2];
        arrayOfString3[1] = "Access";
      }
      else
      {
        arrayOfString3 = new String[1];
      }
      while (localIterator.hasNext())
      {
        Object localObject1 = localIterator.next();
        Object localObject2;
        if ((localFilteredList instanceof Accounts))
        {
          localObject2 = (Account)localObject1;
          arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((Account)localObject2);
          localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
        }
        else if ((localFilteredList instanceof LockboxAccounts))
        {
          localObject2 = (LockboxAccount)localObject1;
          arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((LockboxAccount)localObject2);
          localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
        }
        else if ((localFilteredList instanceof DisbursementAccounts))
        {
          localObject2 = (DisbursementAccount)localObject1;
          arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((DisbursementAccount)localObject2);
          localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
        }
        int i = 1;
        for (int j = 0; j < this.entitlementFilters.size(); j++)
        {
          arrayOfString3[0] = ((String)this.entitlementFilters.get(j));
          localMultiEntitlement.setOperations(arrayOfString3);
          try
          {
            int k;
            if (localEntitlementGroupMember == null)
            {
              if (this.kg) {
                k = EntitlementsUtil.checkAccountAndAccountGroupEntitlement(this._groupId, localMultiEntitlement, localSecureUser.getBusinessID()) == null ? 1 : 0;
              } else {
                k = Entitlements.checkEntitlement(this._groupId, localMultiEntitlement) == null ? 1 : 0;
              }
            }
            else if (this.kg) {
              k = EntitlementsUtil.checkAccountAndAccountGroupEntitlement(localEntitlementGroupMember, localMultiEntitlement, localSecureUser.getBusinessID()) == null ? 1 : 0;
            } else {
              k = Entitlements.checkEntitlement(localEntitlementGroupMember, localMultiEntitlement) == null ? 1 : 0;
            }
            if (k != 0)
            {
              if (this._operation.equals("OR"))
              {
                i = 1;
                break;
              }
            }
            else
            {
              i = 0;
              if (!this._operation.equals("OR")) {
                break;
              }
            }
          }
          catch (CSILException localCSILException)
          {
            i = 0;
            break;
          }
        }
        if (i != 0) {
          this.kf.add(localObject1);
        }
      }
    }
    if (this.filteredAccountsName != null) {
      localHttpSession.setAttribute(this.filteredAccountsName, this.kf);
    }
    return str;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public void setEntitlementFilter(String paramString)
  {
    if (!this.entitlementFilters.contains(paramString)) {
      this.entitlementFilters.add(paramString);
    }
  }
  
  public void setClearFilter(String paramString)
  {
    this.entitlementFilters = new ArrayList();
    this.kf = null;
  }
  
  public ArrayList getFilteredAccounts()
  {
    return this.kf;
  }
  
  public void setFilteredAccountsName(String paramString)
  {
    this.filteredAccountsName = paramString;
  }
  
  public void setOperation(String paramString)
  {
    this._operation = paramString;
  }
  
  public void setMemberId(String paramString)
  {
    this._memberId = paramString;
  }
  
  public void setMemberType(String paramString)
  {
    this._memberType = paramString;
  }
  
  public void setMemberSubType(String paramString)
  {
    this._memberSubType = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this._groupId = Integer.parseInt(paramString);
  }
  
  public void setCheckAccessEntitlement(String paramString)
  {
    this._checkAccessEntitlement = new Boolean(paramString).booleanValue();
  }
  
  public boolean getCheckAccessEntitlement()
  {
    return this._checkAccessEntitlement;
  }
  
  public void setCheckAccountGroupEntitlement(String paramString)
  {
    this.kg = new Boolean(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.AccountEntitlementFilterTask
 * JD-Core Version:    0.7.0.1
 */