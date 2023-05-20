package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.disbursement.DisbursementAccounts;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckPerAccountReportingEntitlements
  extends BaseTask
  implements Task
{
  protected String accountsName = "Accounts";
  protected boolean entitledToDetailIntra = false;
  protected boolean entitledToDetailPrev = false;
  protected boolean entitledToSummaryIntra = false;
  protected boolean entitledToSummaryPrev = false;
  protected boolean entitledToDetailSummaryIntra = false;
  protected boolean entitledToDetailSummaryPrev = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FilteredList localFilteredList1 = (FilteredList)localHttpSession.getAttribute(this.accountsName);
    if (localFilteredList1 == null)
    {
      this.error = 39;
      str = this.taskErrorURL;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      FilteredList localFilteredList2 = getAccountsNotRestricted(localSecureUser, localFilteredList1, "Information Reporting - Intra Day Detail");
      if (localFilteredList2.size() != 0) {
        this.entitledToDetailIntra = true;
      }
      FilteredList localFilteredList3 = getAccountsNotRestricted(localSecureUser, localFilteredList1, "Information Reporting - Intra Day Summary");
      if (localFilteredList3.size() != 0) {
        this.entitledToSummaryIntra = true;
      }
      FilteredList localFilteredList4 = getAccountsNotRestricted(localSecureUser, localFilteredList1, "Information Reporting - Previous Day Detail");
      if (localFilteredList4.size() != 0) {
        this.entitledToDetailPrev = true;
      }
      FilteredList localFilteredList5 = getAccountsNotRestricted(localSecureUser, localFilteredList1, "Information Reporting - Previous Day Summary");
      if (localFilteredList5.size() != 0) {
        this.entitledToSummaryPrev = true;
      }
      FilteredList localFilteredList6 = getAccountsNotRestricted(localSecureUser, localFilteredList2, "Information Reporting - Intra Day Summary");
      if (localFilteredList6.size() != 0) {
        this.entitledToDetailSummaryIntra = true;
      }
      FilteredList localFilteredList7 = getAccountsNotRestricted(localSecureUser, localFilteredList4, "Information Reporting - Previous Day Summary");
      if (localFilteredList7.size() != 0) {
        this.entitledToDetailSummaryPrev = true;
      }
    }
    return str;
  }
  
  public FilteredList getAccountsNotRestricted(SecureUser paramSecureUser, FilteredList paramFilteredList, String paramString)
  {
    Locale localLocale = null;
    Object localObject1 = null;
    if ((paramFilteredList instanceof Accounts))
    {
      localLocale = ((Accounts)paramFilteredList).getLocale();
      localObject1 = new Accounts(localLocale);
    }
    else if ((paramFilteredList instanceof LockboxAccounts))
    {
      localLocale = ((LockboxAccounts)paramFilteredList).getLocale();
      localObject1 = new LockboxAccounts(localLocale);
    }
    else if ((paramFilteredList instanceof DisbursementAccounts))
    {
      localLocale = ((DisbursementAccounts)paramFilteredList).getLocale();
      localObject1 = new DisbursementAccounts(localLocale);
    }
    if ((paramFilteredList == null) || (paramFilteredList.size() <= 0)) {
      return localObject1;
    }
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { paramString });
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    Iterator localIterator = paramFilteredList.iterator();
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      Object localObject3;
      if ((paramFilteredList instanceof Accounts))
      {
        localObject3 = (Account)localObject2;
        arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((Account)localObject3);
        localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      }
      else if ((paramFilteredList instanceof LockboxAccounts))
      {
        localObject3 = (LockboxAccount)localObject2;
        arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((LockboxAccount)localObject3);
        localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      }
      else if ((paramFilteredList instanceof DisbursementAccounts))
      {
        localObject3 = (DisbursementAccount)localObject2;
        arrayOfString2[0] = EntitlementsUtil.getEntitlementObjectId((DisbursementAccount)localObject3);
        localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
      }
      try
      {
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID()) == null) {
          ((FilteredList)localObject1).add(localObject2);
        }
      }
      catch (Exception localException) {}
    }
    return localObject1;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public String getEntitledToDetailIntra()
  {
    return String.valueOf(this.entitledToDetailIntra);
  }
  
  public String getEntitledToDetailPrev()
  {
    return String.valueOf(this.entitledToDetailPrev);
  }
  
  public String getEntitledToSummaryIntra()
  {
    return String.valueOf(this.entitledToSummaryIntra);
  }
  
  public String getEntitledToSummaryPrev()
  {
    return String.valueOf(this.entitledToSummaryPrev);
  }
  
  public String getEntitledToDetailSummaryIntra()
  {
    return String.valueOf(this.entitledToDetailSummaryIntra);
  }
  
  public String getEntitledToDetailSummaryPrev()
  {
    return String.valueOf(this.entitledToDetailSummaryPrev);
  }
  
  public String getEntitledToDetailOrSummaryAnyDataClassification()
  {
    return String.valueOf((this.entitledToDetailIntra) || (this.entitledToDetailPrev) || (this.entitledToSummaryIntra) || (this.entitledToSummaryPrev));
  }
  
  public String getEntitledToDetailOrSummaryIntra()
  {
    return String.valueOf((this.entitledToDetailIntra) || (this.entitledToSummaryIntra));
  }
  
  public String getEntitledToDetailOrSummaryPrev()
  {
    return String.valueOf((this.entitledToDetailPrev) || (this.entitledToSummaryPrev));
  }
  
  public String getEntitledToDetailAnyDataClassification()
  {
    return String.valueOf((this.entitledToDetailIntra) || (this.entitledToDetailPrev));
  }
  
  public String getEntitledToSummaryAnyDataClassification()
  {
    return String.valueOf((this.entitledToSummaryIntra) || (this.entitledToSummaryPrev));
  }
  
  public String getEntitledToSummaryAndDetailPrev()
  {
    return String.valueOf((this.entitledToSummaryPrev) && (this.entitledToDetailPrev));
  }
  
  public String getEntitledToSummaryAndDetailIntra()
  {
    return String.valueOf((this.entitledToSummaryIntra) && (this.entitledToDetailIntra));
  }
  
  public String getEntitledToSummaryAndDetailAnyDataClassification()
  {
    return String.valueOf(((this.entitledToSummaryIntra) && (this.entitledToDetailIntra)) || ((this.entitledToSummaryPrev) && (this.entitledToDetailPrev)));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.CheckPerAccountReportingEntitlements
 * JD-Core Version:    0.7.0.1
 */