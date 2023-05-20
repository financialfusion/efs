package com.ffusion.tasks.multiuser;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.StatementData;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetupAccounts
  extends BaseTask
{
  private static final Entitlement aLy = new Entitlement("Account Register", null, null);
  private static final Entitlement aLz = new Entitlement("OnlineStatement", null, null);
  private static final String aLn = "1";
  private SecureUser aLD;
  private Locale aLr;
  private Accounts aLG = null;
  private Accounts aLp = null;
  private Accounts aLH = null;
  private String aLv = "Accounts";
  private String aLA = "ExternalTransferAccounts";
  private String aLq = "CandidateStatementAccounts";
  private String aLE = "SecondaryUserEntitledCoreAccounts";
  private String aLC = "SecondaryUserEntitledExternalAccounts";
  private String aLL = "SecondaryUserEntitledRegisterAccounts";
  private String aLx = "SecondaryUserEntitledStatementAccounts";
  private String aLt = "SecondaryUserRestrictedCoreAccounts";
  private String aLB = "SecondaryUserRestrictedExternalAccounts";
  private String aLI = "SecondaryUserRestrictedRegisterAccounts";
  private String aLJ = "SecondaryUserRestrictedStatementAccounts";
  private String aLF = "NICKNAME,ID";
  private String aLw = "ConsumerDisplayText,ID";
  private String aLo = null;
  private String aLu = null;
  private String aLs = null;
  private boolean aLK = false;
  private boolean aLm = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.aLD = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.aLr = BaseTask.getLocale(localHttpSession, this.aLD);
    if (this.aLK)
    {
      if (!initialize(localHttpSession)) {
        str1 = this.taskErrorURL;
      }
    }
    else if (validateInput())
    {
      Accounts localAccounts1 = new Accounts(this.aLr);
      Accounts localAccounts2 = new Accounts(this.aLr);
      Accounts localAccounts3 = new Accounts(this.aLr);
      Accounts localAccounts4 = new Accounts(this.aLr);
      Accounts localAccounts5 = new Accounts(this.aLr);
      Accounts localAccounts6 = new Accounts(this.aLr);
      Accounts localAccounts7 = new Accounts(this.aLr);
      Accounts localAccounts8 = new Accounts(this.aLr);
      Object localObject1;
      Object localObject2;
      Object localObject3;
      Object localObject4;
      Object localObject5;
      Object localObject6;
      Object localObject7;
      Object localObject8;
      Object localObject9;
      Object localObject10;
      if (this.aLm)
      {
        localAccounts1 = (Accounts)localHttpSession.getAttribute(this.aLE);
        localAccounts2 = (Accounts)localHttpSession.getAttribute(this.aLC);
        localAccounts5 = (Accounts)localHttpSession.getAttribute(this.aLL);
        localAccounts7 = (Accounts)localHttpSession.getAttribute(this.aLx);
        localAccounts3 = (Accounts)localHttpSession.getAttribute(this.aLt);
        localAccounts4 = (Accounts)localHttpSession.getAttribute(this.aLB);
        localAccounts6 = (Accounts)localHttpSession.getAttribute(this.aLI);
        localAccounts8 = (Accounts)localHttpSession.getAttribute(this.aLJ);
        try
        {
          EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(this.aLD);
          localObject1 = X();
          localObject2 = null;
          localObject3 = null;
          localObject4 = null;
          localObject5 = null;
          localObject6 = null;
          localObject7 = null;
          localObject8 = null;
          localObject9 = null;
          localObject10 = new HistoryTracker(this.aLD, 1, this.aLo);
          EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
          localObject3 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1);
          localObject4 = new com.ffusion.csil.beans.entitlements.Entitlements();
          localObject5 = new com.ffusion.csil.beans.entitlements.Entitlements();
          localObject2 = new MultiEntitlement();
          ((MultiEntitlement)localObject2).setOperations(new String[] { "Access" });
          localObject8 = new String[] { "Account" };
          localObject9 = new String[1];
          int j = 0;
          int k = localAccounts1.size();
          while (j < k)
          {
            localObject7 = (Account)localAccounts1.get(j);
            localObject9[0] = EntitlementsUtil.getEntitlementObjectId((Account)localObject7);
            ((MultiEntitlement)localObject2).setObjects((String[])localObject8, (String[])localObject9);
            if (EntitlementsUtil.checkAccountEntitlement((EntitlementGroupMember)localObject1, (MultiEntitlement)localObject2, ((EntitlementGroupMember)localObject1).getBusinessID()) != null)
            {
              localObject6 = new Entitlement("Access", "Account", localObject9[0]);
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject4).add(localObject6);
              ((HistoryTracker)localObject10).logEntitlementDelete((Entitlement)localObject6, localEntitlementTypePropertyLists);
            }
            j++;
          }
          j = 0;
          k = localAccounts2.size();
          while (j < k)
          {
            localObject7 = (Account)localAccounts2.get(j);
            localObject9[0] = EntitlementsUtil.getEntitlementObjectId((Account)localObject7);
            ((MultiEntitlement)localObject2).setObjects((String[])localObject8, (String[])localObject9);
            if (EntitlementsUtil.checkAccountEntitlement((EntitlementGroupMember)localObject1, (MultiEntitlement)localObject2, ((EntitlementGroupMember)localObject1).getBusinessID()) != null)
            {
              localObject6 = new Entitlement("Access", "Account", localObject9[0]);
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject4).add(localObject6);
              ((HistoryTracker)localObject10).logEntitlementDelete((Entitlement)localObject6, localEntitlementTypePropertyLists);
            }
            j++;
          }
          j = 0;
          k = localAccounts3.size();
          while (j < k)
          {
            localObject7 = (Account)localAccounts3.get(j);
            localObject9[0] = EntitlementsUtil.getEntitlementObjectId((Account)localObject7);
            ((MultiEntitlement)localObject2).setObjects((String[])localObject8, (String[])localObject9);
            if (EntitlementsUtil.checkAccountEntitlement((EntitlementGroupMember)localObject1, (MultiEntitlement)localObject2, ((EntitlementGroupMember)localObject1).getBusinessID()) == null)
            {
              localObject6 = new Entitlement("Access", "Account", localObject9[0]);
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).add(localObject6);
              ((HistoryTracker)localObject10).logEntitlementAdd((Entitlement)localObject6, localEntitlementTypePropertyLists);
            }
            j++;
          }
          j = 0;
          k = localAccounts4.size();
          while (j < k)
          {
            localObject7 = (Account)localAccounts4.get(j);
            localObject9[0] = EntitlementsUtil.getEntitlementObjectId((Account)localObject7);
            ((MultiEntitlement)localObject2).setObjects((String[])localObject8, (String[])localObject9);
            if (EntitlementsUtil.checkAccountEntitlement((EntitlementGroupMember)localObject1, (MultiEntitlement)localObject2, ((EntitlementGroupMember)localObject1).getBusinessID()) == null)
            {
              localObject6 = new Entitlement("Access", "Account", localObject9[0]);
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).add(localObject6);
              ((HistoryTracker)localObject10).logEntitlementAdd((Entitlement)localObject6, localEntitlementTypePropertyLists);
            }
            j++;
          }
          com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject5);
          com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject4);
          localObject4 = new com.ffusion.csil.beans.entitlements.Entitlements();
          localObject5 = new com.ffusion.csil.beans.entitlements.Entitlements();
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember, aLy))
          {
            j = !localAccounts5.isEmpty() ? 1 : 0;
            localObject4 = new com.ffusion.csil.beans.entitlements.Entitlements();
            localObject5 = new com.ffusion.csil.beans.entitlements.Entitlements();
            localObject6 = new Entitlement();
            ((Entitlement)localObject6).setOperationName("Account Register");
            ((Entitlement)localObject6).setObjectType("Account");
            k = 0;
            int n = localAccounts5.size();
            while (k < n)
            {
              localObject7 = (Account)localAccounts5.get(k);
              ((Entitlement)localObject6).setObjectId(EntitlementsUtil.getEntitlementObjectId((Account)localObject7));
              if (((com.ffusion.csil.beans.entitlements.Entitlements)localObject3).contains(localObject6))
              {
                ((com.ffusion.csil.beans.entitlements.Entitlements)localObject4).add(((Entitlement)localObject6).clone());
                ((HistoryTracker)localObject10).logEntitlementDelete((Entitlement)localObject6, localEntitlementTypePropertyLists);
              }
              k++;
            }
            k = 0;
            n = localAccounts6.size();
            while (k < n)
            {
              localObject7 = (Account)localAccounts6.get(k);
              ((Entitlement)localObject6).setObjectId(EntitlementsUtil.getEntitlementObjectId((Account)localObject7));
              if (!((com.ffusion.csil.beans.entitlements.Entitlements)localObject3).contains(localObject6))
              {
                ((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).add(((Entitlement)localObject6).clone());
                ((HistoryTracker)localObject10).logEntitlementAdd((Entitlement)localObject6, localEntitlementTypePropertyLists);
              }
              k++;
            }
            com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject5);
            com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject4);
            if ((j != 0) && (!com.ffusion.csil.core.Entitlements.checkEntitlement((EntitlementGroupMember)localObject1, aLy)))
            {
              localObject4 = new com.ffusion.csil.beans.entitlements.Entitlements();
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject4).add(aLy);
              com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject4);
              ((HistoryTracker)localObject10).logEntitlementDelete(aLy, localEntitlementTypePropertyLists);
            }
            else if ((j == 0) && (com.ffusion.csil.core.Entitlements.checkEntitlement((EntitlementGroupMember)localObject1, aLy)))
            {
              localObject5 = new com.ffusion.csil.beans.entitlements.Entitlements();
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).add(aLy);
              com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject5);
              ((HistoryTracker)localObject10).logEntitlementAdd(aLy, localEntitlementTypePropertyLists);
            }
          }
          if (com.ffusion.csil.core.Entitlements.checkEntitlement(localEntitlementGroupMember, aLz))
          {
            Accounts localAccounts9 = StatementData.getAccountsForIStatement(this.aLD, this.aLu, new HashMap());
            Account localAccount2 = null;
            String str2 = null;
            int i2 = 0;
            int i3 = !localAccounts7.isEmpty() ? 1 : 0;
            Object localObject11 = localAccounts8.iterator();
            while (((Iterator)localObject11).hasNext())
            {
              localObject7 = (Account)((Iterator)localObject11).next();
              str2 = ((Account)localObject7).getNumber();
              i2 = ((Account)localObject7).getTypeValue();
              localAccount2 = localAccounts9.getByAccountNumberAndType(str2, i2);
              if (localAccount2 == null) {
                ((Iterator)localObject11).remove();
              }
            }
            localObject11 = localAccounts7.iterator();
            while (((Iterator)localObject11).hasNext())
            {
              localObject7 = (Account)((Iterator)localObject11).next();
              str2 = ((Account)localObject7).getNumber();
              i2 = ((Account)localObject7).getTypeValue();
              localAccount2 = localAccounts9.getByAccountNumberAndType(str2, i2);
              if (localAccount2 != null) {
                ((Iterator)localObject11).remove();
              }
            }
            localObject11 = new HashMap();
            ((HashMap)localObject11).put("SECONDARY_USER_USER_NAME", this.aLs);
            StatementData.removeAccountsForIStatement(this.aLD, this.aLu, localAccounts8, (HashMap)localObject11);
            HashMap localHashMap = new HashMap();
            localHashMap.put("SECONDARY_USER_USER_NAME", this.aLs);
            StatementData.addAccountsForIStatement(this.aLD, this.aLu, localAccounts7, localHashMap);
            if ((i3 != 0) && (!com.ffusion.csil.core.Entitlements.checkEntitlement((EntitlementGroupMember)localObject1, aLz)))
            {
              localObject4 = new com.ffusion.csil.beans.entitlements.Entitlements();
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject4).add(aLz);
              com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject4);
              ((HistoryTracker)localObject10).logEntitlementDelete(aLz, localEntitlementTypePropertyLists);
            }
            else if ((i3 == 0) && (com.ffusion.csil.core.Entitlements.checkEntitlement((EntitlementGroupMember)localObject1, aLz)))
            {
              localObject5 = new com.ffusion.csil.beans.entitlements.Entitlements();
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject5).add(aLz);
              com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember, (EntitlementGroupMember)localObject1, (com.ffusion.csil.beans.entitlements.Entitlements)localObject5);
              ((HistoryTracker)localObject10).logEntitlementAdd(aLz, localEntitlementTypePropertyLists);
            }
            try
            {
              HistoryAdapter.addHistory(((HistoryTracker)localObject10).getHistories());
            }
            catch (ProfileException localProfileException)
            {
              DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.SetupAccounts: " + localProfileException.toString());
            }
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
        }
      }
      else
      {
        Account localAccount1 = null;
        localObject1 = null;
        localObject2 = null;
        localObject3 = null;
        localObject4 = null;
        localObject5 = null;
        localObject6 = null;
        localObject7 = null;
        localObject8 = null;
        localObject9 = null;
        localObject10 = null;
        int i = 0;
        boolean bool = false;
        localObject4 = this.aLG.getFilter();
        this.aLG.setFilter("COREACCOUNT=1,HIDE!1,AND");
        localObject5 = this.aLG.getSortedBy();
        this.aLG.setFilterSortedBy(this.aLF);
        int m = 0;
        int i1 = this.aLG.size();
        while (m < i1)
        {
          localAccount1 = (Account)this.aLG.get(m);
          localObject7 = localAccount1.getBankID();
          localObject8 = localAccount1.getRoutingNum();
          localObject9 = localAccount1.getID();
          localObject10 = localAccount1.getNumber();
          i = localAccount1.getTypeValue();
          bool = Boolean.valueOf((String)localAccount1.get("REG_ENABLED")).booleanValue();
          localObject2 = this.aLH.getByIDAndBankIDAndRoutingNum((String)localObject9, (String)localObject7, (String)localObject8);
          localObject6 = (String)localHttpSession.getAttribute("EnableCoreAccount_" + m);
          if (localObject6 != null)
          {
            localObject3 = localAccounts1.create((String)localObject7, (String)localObject9, (String)localObject10, i);
            ((Account)localObject3).set(localAccount1);
            if (bool)
            {
              localObject6 = (String)localHttpSession.getAttribute("EnableRegisterAccount_" + m);
              if (localObject6 != null) {
                localObject3 = localAccounts5.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              } else {
                localObject3 = localAccounts6.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              }
              ((Account)localObject3).set(localAccount1);
            }
            if (localObject2 != null)
            {
              localObject6 = (String)localHttpSession.getAttribute("EnableStatementAccount_" + m);
              if (localObject6 != null) {
                localObject3 = localAccounts7.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              } else {
                localObject3 = localAccounts8.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              }
              ((Account)localObject3).set(localAccount1);
            }
          }
          else
          {
            localObject3 = localAccounts3.create((String)localObject7, (String)localObject9, (String)localObject10, i);
            ((Account)localObject3).set(localAccount1);
            if (bool)
            {
              localObject3 = localAccounts6.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              ((Account)localObject3).set(localAccount1);
            }
            if (localObject2 != null)
            {
              localObject3 = localAccounts8.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              ((Account)localObject3).set(localAccount1);
            }
          }
          m++;
        }
        this.aLG.setFilter((String)localObject4);
        this.aLG.setFilterSortedBy((String)localObject5);
        localObject4 = this.aLp.getFilter();
        this.aLp.setFilter("COREACCOUNT=0,HIDE!1,AND");
        localObject5 = this.aLp.getSortedBy();
        this.aLp.setFilterSortedBy(this.aLw);
        m = 0;
        i1 = this.aLp.size();
        while (m < i1)
        {
          localObject1 = (Account)this.aLp.get(m);
          localObject7 = ((Account)localObject1).getBankID();
          localObject8 = ((Account)localObject1).getRoutingNum();
          localObject9 = ((Account)localObject1).getID();
          localObject10 = ((Account)localObject1).getNumber();
          i = ((Account)localObject1).getTypeValue();
          bool = Boolean.valueOf((String)((Account)localObject1).get("REG_ENABLED")).booleanValue();
          localObject6 = (String)localHttpSession.getAttribute("EnableExternalAccount_" + m);
          if (localObject6 != null)
          {
            localObject3 = localAccounts2.create(((Account)localObject1).getBankID(), ((Account)localObject1).getID(), ((Account)localObject1).getNumber(), ((Account)localObject1).getTypeValue());
            ((Account)localObject3).set((Account)localObject1);
            if (bool)
            {
              localObject6 = (String)localHttpSession.getAttribute("EnableExternalRegisterAccount_" + m);
              if (localObject6 != null) {
                localObject3 = localAccounts5.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              } else {
                localObject3 = localAccounts6.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              }
              ((Account)localObject3).set((Account)localObject1);
            }
          }
          else
          {
            localObject3 = localAccounts4.create((String)localObject7, (String)localObject9, (String)localObject10, i);
            ((Account)localObject3).set((Account)localObject1);
            if (bool)
            {
              localObject3 = localAccounts6.create((String)localObject7, (String)localObject9, (String)localObject10, i);
              ((Account)localObject3).set((Account)localObject1);
            }
          }
          m++;
        }
        this.aLp.setFilter((String)localObject4);
        this.aLp.setFilterSortedBy((String)localObject5);
        localHttpSession.setAttribute(this.aLE, localAccounts1);
        localHttpSession.setAttribute(this.aLC, localAccounts2);
        localHttpSession.setAttribute(this.aLL, localAccounts5);
        localHttpSession.setAttribute(this.aLx, localAccounts7);
        localHttpSession.setAttribute(this.aLt, localAccounts3);
        localHttpSession.setAttribute(this.aLB, localAccounts4);
        localHttpSession.setAttribute(this.aLI, localAccounts6);
        localHttpSession.setAttribute(this.aLJ, localAccounts8);
      }
    }
    else
    {
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setInitialize(String paramString)
  {
    this.aLK = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.aLm = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSecondaryUserID(String paramString)
  {
    this.aLo = paramString;
  }
  
  public void setSecondaryUserCustID(String paramString)
  {
    this.aLu = paramString;
  }
  
  public void setSecondaryUserUserName(String paramString)
  {
    this.aLs = paramString;
  }
  
  public void setCandidateCoreAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLv = "Accounts";
    } else {
      this.aLv = paramString;
    }
  }
  
  public void setCandidateExternalAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLA = "ExternalAccounts";
    } else {
      this.aLA = paramString;
    }
  }
  
  public void setCandidateStatementAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLq = "CandidateStatementAccounts";
    } else {
      this.aLq = paramString;
    }
  }
  
  public void setSecondaryUserEntitledCoreAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLE = "SecondaryUserEntitledCoreAccounts";
    } else {
      this.aLE = paramString;
    }
  }
  
  public void setSecondaryUserRestrictedCoreAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLt = "SecondaryUserRestrictedCoreAccounts";
    } else {
      this.aLt = paramString;
    }
  }
  
  public void setSecondaryUserEntitledExternalAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLC = "SecondaryUserEntitledExternalAccounts";
    } else {
      this.aLC = paramString;
    }
  }
  
  public void setSecondaryUserRestrictedExternalAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLB = "SecondaryUserRestrictedExternalAccounts";
    } else {
      this.aLB = paramString;
    }
  }
  
  public void setSecondaryUserEntitledRegisterAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLL = "SecondaryUserEntitledRegisterAccounts";
    } else {
      this.aLL = paramString;
    }
  }
  
  public void setSecondaryUserRestrictedRegisterAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLI = "SecondaryUserRestrictedRegisterAccounts";
    } else {
      this.aLI = paramString;
    }
  }
  
  public void setSecondaryUserEntitledStatementAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLx = "SecondaryUserEntitledStatementAccounts";
    } else {
      this.aLx = paramString;
    }
  }
  
  public void setSecondaryUserRestrictedStatementAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.aLJ = "SecondaryUserRestrictedStatementAccounts";
    } else {
      this.aLJ = paramString;
    }
  }
  
  public void setCoreAccountsSortFields(String paramString)
  {
    this.aLF = paramString;
  }
  
  public void setExternalAccountsSortFields(String paramString)
  {
    this.aLw = paramString;
  }
  
  protected boolean initialize(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((bool) && ((this.aLv == null) || (this.aLv.length() == 0)))
    {
      this.error = 28004;
      bool = false;
    }
    if (bool)
    {
      this.aLG = ((Accounts)paramHttpSession.getAttribute(this.aLv));
      if (this.aLG == null)
      {
        this.error = 28005;
        bool = false;
      }
    }
    if ((bool) && ((this.aLA == null) || (this.aLA.length() == 0)))
    {
      this.error = 28043;
      bool = false;
    }
    if (bool)
    {
      this.aLp = ((Accounts)paramHttpSession.getAttribute(this.aLA));
      if (this.aLp == null)
      {
        this.error = 28042;
        bool = false;
      }
    }
    if ((bool) && ((this.aLq == null) || (this.aLq.length() == 0)))
    {
      this.error = 28006;
      bool = false;
    }
    if (bool)
    {
      this.aLH = ((Accounts)paramHttpSession.getAttribute(this.aLq));
      if (this.aLH == null)
      {
        this.error = 28007;
        bool = false;
      }
    }
    return bool;
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.aLD == null))
    {
      this.error = 38;
      bool = false;
    }
    if ((bool) && ((this.aLE == null) || (this.aLE.length() == 0)))
    {
      this.error = 28003;
      bool = false;
    }
    if ((bool) && ((this.aLC == null) || (this.aLC.length() == 0)))
    {
      this.error = 28035;
      bool = false;
    }
    if ((bool) && ((this.aLL == null) || (this.aLL.length() == 0)))
    {
      this.error = 28036;
      bool = false;
    }
    if ((bool) && ((this.aLx == null) || (this.aLx.length() == 0)))
    {
      this.error = 28037;
      bool = false;
    }
    if ((bool) && ((this.aLt == null) || (this.aLt.length() == 0)))
    {
      this.error = 28038;
      bool = false;
    }
    if ((bool) && ((this.aLB == null) || (this.aLB.length() == 0)))
    {
      this.error = 28039;
      bool = false;
    }
    if ((bool) && ((this.aLI == null) || (this.aLI.length() == 0)))
    {
      this.error = 28040;
      bool = false;
    }
    if ((bool) && ((this.aLJ == null) || (this.aLJ.length() == 0)))
    {
      this.error = 28041;
      bool = false;
    }
    if ((bool) && (this.aLG == null))
    {
      this.error = 28005;
      bool = false;
    }
    if ((bool) && (this.aLp == null))
    {
      this.error = 28042;
      bool = false;
    }
    if ((bool) && (this.aLH == null))
    {
      this.error = 28007;
      bool = false;
    }
    if ((bool) && (this.aLm) && ((this.aLo == null) || (this.aLo.trim().length() == 0)))
    {
      this.error = 28008;
      bool = false;
    }
    if ((bool) && (this.aLm) && ((this.aLu == null) || (this.aLu.trim().length() == 0)))
    {
      this.error = 28049;
      bool = false;
    }
    if ((bool) && (this.aLm) && ((this.aLs == null) || (this.aLs.trim().length() == 0)))
    {
      this.error = 28017;
      bool = false;
    }
    return bool;
  }
  
  private EntitlementGroupMember X()
  {
    EntitlementGroupMember localEntitlementGroupMember = null;
    localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(this.aLo);
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType(Integer.toString(this.aLD.getUserType()));
    localEntitlementGroupMember.setEntitlementGroupId(this.aLD.getEntitlementID());
    return localEntitlementGroupMember;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.SetupAccounts
 * JD-Core Version:    0.7.0.1
 */