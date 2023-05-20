package com.ffusion.tasks.multiuser;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.Billpay;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.handlers.AffiliateBankAdmin;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.Banking2;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditSecondaryUser
  extends AddSecondaryUser
{
  private static final Entitlement pq = new Entitlement("Payments", null, null);
  private static final Entitlement px = new Entitlement("Transfers", null, null);
  protected User oldSecondaryUser = null;
  protected User primaryUser = null;
  protected boolean initialize = false;
  protected Banking2 bankingService = null;
  protected String bankingServiceSessionName = "com.ffusion.services.Banking";
  private Payments pw;
  private RecPayments pv;
  private Transfers ps;
  private RecTransfers pu;
  private Accounts pB;
  private String pA = "PrimaryUser";
  private String py = "SecondaryUser";
  private String pz = "Payments";
  private String pC = "RecPayments";
  private String pr = "Transfers";
  private String po = "RecTransfers";
  private String pn = "Accounts";
  private String pp = "TransferOrCancelTransactions";
  private int pt;
  
  public EditSecondaryUser()
  {
    this.successURL = null;
    this.taskErrorURL = "TE";
    this.serviceErrorURL = "SE";
    this.error = 0;
    this.process = false;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.sUser = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.locale = BaseTask.getLocale(localHttpSession, this.sUser);
    if (this.initialize)
    {
      str1 = initialize(localHttpSession);
    }
    else
    {
      str1 = validateInput();
      if (this.error == 0) {
        str1 = verifyInputFormat();
      }
      if ((this.error == 0) && ((str1 == this.successURL) || ((str1 != null) && (str1.equals(this.successURL)))) && (this.process)) {
        try
        {
          HashMap localHashMap = new HashMap();
          String str2 = this.oldSecondaryUser.getPassword();
          String str3 = getPassword();
          String str4 = this.oldSecondaryUser.getAccountStatus();
          String str5 = getAccountStatus();
          Users localUsers = null;
          if (!str3.equals(str2))
          {
            UserAdmin.resetUserPassword(this.sUser, this, str2, str3, localHashMap);
            put("PASSWORD_STATUS", "2");
            UserAdmin.modifyPasswordStatus(this.sUser, this, localHashMap);
          }
          UserAdmin.modifyUser(this.sUser, this, this.oldSecondaryUser, localHashMap);
          addHistory();
          if ((User.STATUS_INACTIVE.equals(getAccountStatus())) || (User.STATUS_DELETED.equals(getAccountStatus()))) {
            modifyTransactionsOwnership(localHttpSession);
          }
          if (this.error == 0)
          {
            localUsers = new Users(this.locale);
            localUsers.add(this);
            localHttpSession.setAttribute(this.secondaryUsersSessionName, localUsers);
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
        }
      }
    }
    return str1;
  }
  
  public void setInitialize(String paramString)
  {
    this.initialize = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getMaskedPassword()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = getPassword();
    if ((str != null) && (str.length() > 0))
    {
      int i = 0;
      int j = str.length();
      while (i < j)
      {
        localStringBuffer.append('*');
        i++;
      }
    }
    return localStringBuffer.toString();
  }
  
  public User getOriginalSecondaryUser()
  {
    return this.oldSecondaryUser;
  }
  
  public void setProcess(String paramString)
  {
    this.process = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPrimaryUserSessionName(String paramString)
  {
    if (paramString == null) {
      this.pA = "PrimaryUser";
    } else {
      this.pA = paramString;
    }
  }
  
  public void setSecondaryUserSessionName(String paramString)
  {
    if (paramString == null) {
      this.py = "SecondaryUser";
    } else {
      this.py = paramString;
    }
  }
  
  public void setPaymentsSessionName(String paramString)
  {
    if (paramString == null) {
      this.pz = "Payments";
    } else {
      this.pz = paramString;
    }
  }
  
  public void setRecPaymentsSessionName(String paramString)
  {
    if (paramString == null) {
      this.pC = "RecPayments";
    } else {
      this.pC = paramString;
    }
  }
  
  public void setTransfersSessionName(String paramString)
  {
    if (paramString == null) {
      this.pr = "Transfers";
    } else {
      this.pr = paramString;
    }
  }
  
  public void setRecTransfersSessionName(String paramString)
  {
    if (paramString == null) {
      this.po = "RecTransfers";
    } else {
      this.po = paramString;
    }
  }
  
  public void setUserID(String paramString)
  {
    try
    {
      this.pt = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.pt = 0;
    }
  }
  
  public void setPendingTransactionsActionInputName(String paramString)
  {
    if (paramString == null) {
      this.pp = "TransferOrCancelTransactions";
    } else {
      this.pp = paramString;
    }
  }
  
  public void setBankingServiceName(String paramString)
  {
    if (paramString == null) {
      this.bankingServiceSessionName = "com.ffusion.services.Banking";
    } else {
      this.bankingServiceSessionName = paramString;
    }
  }
  
  public void setPrimaryUserAccountsSessionName(String paramString)
  {
    if (paramString == null) {
      this.pn = "Accounts";
    } else {
      this.pn = paramString;
    }
  }
  
  public void setResetPasswordQA(String paramString)
  {
    boolean bool = Boolean.valueOf(paramString).booleanValue();
    if (bool)
    {
      setPasswordReminder("");
      setPasswordReminder2("");
      setPasswordClue(null);
      setPasswordClue2(null);
    }
  }
  
  protected String initialize(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    int i = 1;
    if ((i != 0) && ((this.py == null) || (this.py.length() == 0)))
    {
      this.error = 28001;
      str = this.taskErrorURL;
      i = 0;
    }
    if (i != 0)
    {
      this.oldSecondaryUser = ((User)paramHttpSession.getAttribute(this.py));
      if (this.oldSecondaryUser == null)
      {
        this.error = 28002;
        str = this.taskErrorURL;
        i = 0;
      }
      else
      {
        set(this.oldSecondaryUser);
      }
    }
    if ((i != 0) && (this.sUser.getPrimaryUserID() == 0) && ((this.pA == null) || (this.pA.length() == 0)))
    {
      this.error = 28031;
      str = this.taskErrorURL;
      i = 0;
    }
    if ((i != 0) && (this.sUser.getPrimaryUserID() == 0))
    {
      this.primaryUser = ((User)paramHttpSession.getAttribute(this.pA));
      if (this.primaryUser == null)
      {
        this.error = 28030;
        str = this.taskErrorURL;
        i = 0;
      }
    }
    HashMap localHashMap;
    if (i != 0)
    {
      localHashMap = null;
      try
      {
        localHashMap = new HashMap();
        if (this.primaryUser == null)
        {
          if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), pq)) {
            Billpay.signOn(this.sUser, Integer.toString(this.sUser.getProfileID()), "0", localHashMap);
          }
        }
        else
        {
          SecureUser localSecureUser1 = jdMethod_int(this.primaryUser);
          if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser1), pq)) {
            Billpay.signOn(localSecureUser1, Integer.toString(localSecureUser1.getProfileID()), "0", localHashMap);
          }
        }
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str = this.serviceErrorURL;
        i = 0;
      }
    }
    if ((i != 0) && ((this.bankingServiceSessionName == null) || (this.bankingServiceSessionName.length() == 0)))
    {
      this.error = 28032;
      str = this.taskErrorURL;
      i = 0;
    }
    if (i != 0)
    {
      this.bankingService = ((Banking2)paramHttpSession.getAttribute(this.bankingServiceSessionName));
      if (this.bankingService == null)
      {
        this.error = 28029;
        str = this.taskErrorURL;
        i = 0;
      }
    }
    if (i != 0)
    {
      localHashMap = null;
      try
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", this.bankingService);
        if (this.primaryUser == null)
        {
          if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), px)) {
            Banking.signOn(this.sUser, this.sUser.getId(), "0", localHashMap);
          }
        }
        else
        {
          SecureUser localSecureUser2 = jdMethod_int(this.primaryUser);
          if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser2), px)) {
            Banking.signOn(localSecureUser2, localSecureUser2.getId(), "0", localHashMap);
          }
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        str = this.serviceErrorURL;
        i = 0;
      }
    }
    if ((i != 0) && ((this.pn == null) || (this.pn.length() == 0)))
    {
      this.error = 28033;
      str = this.taskErrorURL;
      i = 0;
    }
    if (i != 0)
    {
      this.pB = ((Accounts)paramHttpSession.getAttribute(this.pn));
      if (this.pB == null)
      {
        this.error = 28034;
        str = this.taskErrorURL;
        i = 0;
      }
    }
    if (i != 0)
    {
      this.pw = ((Payments)paramHttpSession.getAttribute(this.pz));
      this.pv = ((RecPayments)paramHttpSession.getAttribute(this.pC));
      this.ps = ((Transfers)paramHttpSession.getAttribute(this.pr));
      this.pu = ((RecTransfers)paramHttpSession.getAttribute(this.po));
    }
    return str;
  }
  
  protected String validateInput()
  {
    String str1 = this.successURL;
    int i = 1;
    String str2 = this.fieldsToValidate;
    StringBuffer localStringBuffer = null;
    if (!super.validateFieldLength()) {
      return this.taskErrorURL;
    }
    if ((i != 0) && (this.oldSecondaryUser == null))
    {
      this.error = 28002;
      str1 = this.taskErrorURL;
      i = 0;
    }
    if ((i != 0) && (this.bankingService == null))
    {
      this.error = 28029;
      str1 = this.taskErrorURL;
      i = 0;
    }
    if ((i != 0) && (this.fieldsToValidate != null) && (this.fieldsToValidate.length() > 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(this.fieldsToValidate, ",");
      String str3 = null;
      String str4 = "";
      localStringBuffer = new StringBuffer(this.fieldsToValidate.length());
      while ((i != 0) && (localStringTokenizer.hasMoreTokens()))
      {
        str3 = localStringTokenizer.nextToken();
        if ("USER_NAME".equals(str3))
        {
          try
          {
            String str5 = this.oldSecondaryUser.getUserName();
            String str6 = getUserName();
            if ((!str6.equals(str5)) && (UserAdmin.userExists(this.sUser, str6, getBankId(), new HashMap())))
            {
              this.error = 28048;
              str1 = this.taskErrorURL;
              i = 0;
            }
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException);
            str1 = this.serviceErrorURL;
            i = 0;
          }
        }
        else
        {
          localStringBuffer.append(str3);
          localStringBuffer.append(str4);
          str4 = ",";
        }
      }
    }
    if (i != 0)
    {
      if ((localStringBuffer != null) && (localStringBuffer.length() > 0)) {
        this.fieldsToValidate = localStringBuffer.toString();
      }
      str1 = super.validateInput();
      i = this.error == 0 ? 1 : 0;
    }
    this.fieldsToValidate = str2;
    return str1;
  }
  
  protected void modifyTransactionsOwnership(HttpSession paramHttpSession)
    throws CSILException
  {
    String str = null;
    HashMap localHashMap = null;
    str = (String)paramHttpSession.getAttribute(this.pp);
    Object localObject1;
    Object localObject2;
    Object localObject3;
    int j;
    if ((this.pw != null) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), pq)))
    {
      localObject1 = new Payments(this.locale);
      localObject2 = null;
      localObject3 = null;
      Account localAccount = null;
      localHashMap = new HashMap();
      j = 0;
      int k = this.pw.size();
      while (j < k)
      {
        localObject2 = (Payment)this.pw.get(j);
        localObject3 = (Payment)this.pw.createNoAdd();
        ((Payment)localObject3).set((Payment)localObject2);
        if (this.pt > 0) {
          ((Payment)localObject3).setSubmittedBy(this.pt);
        } else {
          ((Payment)localObject3).setSubmittedBy(this.sUser.getProfileID());
        }
        localAccount = this.pB.getByAccountNumberAndType(((Payment)localObject3).getAccount().getNumber(), ((Payment)localObject3).getAccount().getTypeValue());
        ((Payment)localObject3).setAccount(localAccount);
        if ("Transfer".equals(str)) {
          Billpay.modifyPayment(this.sUser, (Payment)localObject3, (Payment)localObject2, localHashMap);
        } else if ("Cancel".equals(str)) {
          ((Payments)localObject1).add(localObject3);
        } else if (this.error == 0) {
          this.error = 28050;
        }
        j++;
      }
      if (!((Payments)localObject1).isEmpty()) {
        Billpay.cancelPayments(this.sUser, (Payments)localObject1, localHashMap);
      }
    }
    int i;
    if ((this.pv != null) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), pq)))
    {
      localObject1 = null;
      localObject2 = null;
      localObject3 = null;
      localHashMap = new HashMap();
      i = 0;
      j = this.pv.size();
      while (i < j)
      {
        localObject1 = (RecPayment)this.pv.get(i);
        localObject2 = (RecPayment)this.pv.createNoAdd();
        ((RecPayment)localObject2).set((RecPayment)localObject1);
        localObject3 = this.pB.getByAccountNumberAndType(((RecPayment)localObject2).getAccount().getNumber(), ((RecPayment)localObject2).getAccount().getTypeValue());
        ((RecPayment)localObject2).setAccount((Account)localObject3);
        if ("Cancel".equals(str))
        {
          Billpay.deleteRecPayment(this.sUser, (RecPayment)localObject2, localHashMap);
        }
        else if ("Transfer".equals(str))
        {
          if (this.pt > 0) {
            ((RecPayment)localObject2).setSubmittedBy(this.pt);
          } else {
            ((RecPayment)localObject2).setSubmittedBy(this.sUser.getProfileID());
          }
          Billpay.modifyRecPayment(this.sUser, (RecPayment)localObject2, (RecPayment)localObject1, localHashMap);
        }
        else if (this.error == 0)
        {
          this.error = 28050;
        }
        i++;
      }
    }
    if ((this.ps != null) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), px)))
    {
      localObject1 = null;
      localObject2 = null;
      localObject3 = null;
      i = 0;
      j = this.ps.size();
      while (i < j)
      {
        localObject1 = (Transfer)this.ps.get(i);
        localObject2 = (Transfer)this.ps.createNoAdd();
        ((Transfer)localObject2).set((Transfer)localObject1);
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", this.bankingService);
        localObject3 = this.pB.getByAccountNumberAndType(((Transfer)localObject2).getFromAccount().getNumber(), ((Transfer)localObject2).getFromAccount().getTypeValue());
        ((Transfer)localObject2).setFromAccount((Account)localObject3);
        localObject3 = this.pB.getByAccountNumberAndType(((Transfer)localObject2).getToAccount().getNumber(), ((Transfer)localObject2).getToAccount().getTypeValue());
        ((Transfer)localObject2).setToAccount((Account)localObject3);
        if ("Cancel".equals(str))
        {
          Banking.cancelTransfer(this.sUser, (Transfer)localObject2, localHashMap);
        }
        else if ("Transfer".equals(str))
        {
          if (this.pt > 0) {
            ((Transfer)localObject2).setSubmittedBy(this.pt);
          } else {
            ((Transfer)localObject2).setSubmittedBy(this.sUser.getProfileID());
          }
          Banking.modifyTransfer(this.sUser, (Transfer)localObject2, (Transfer)localObject1, localHashMap);
        }
        else if (this.error == 0)
        {
          this.error = 28050;
        }
        i++;
      }
    }
    if ((this.pu != null) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(this.sUser), px)))
    {
      localObject1 = null;
      localObject2 = null;
      localObject3 = null;
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", this.bankingService);
      i = 0;
      j = this.pu.size();
      while (i < j)
      {
        localObject1 = (RecTransfer)this.pu.get(i);
        localObject2 = (RecTransfer)this.pu.createNoAdd();
        ((RecTransfer)localObject2).set((RecTransfer)localObject1);
        localObject3 = this.pB.getByAccountNumberAndType(((RecTransfer)localObject2).getFromAccount().getNumber(), ((RecTransfer)localObject2).getFromAccount().getTypeValue());
        ((RecTransfer)localObject2).setFromAccount((Account)localObject3);
        localObject3 = this.pB.getByAccountNumberAndType(((RecTransfer)localObject2).getToAccount().getNumber(), ((RecTransfer)localObject2).getToAccount().getTypeValue());
        ((RecTransfer)localObject2).setToAccount((Account)localObject3);
        if ("Cancel".equals(str))
        {
          Banking.cancelRecTransfer(this.sUser, (RecTransfer)localObject2, localHashMap);
        }
        else if ("Transfer".equals(str))
        {
          if (this.pt > 0) {
            ((RecTransfer)localObject2).setSubmittedBy(this.pt);
          } else {
            ((RecTransfer)localObject2).setSubmittedBy(this.sUser.getProfileID());
          }
          Banking.modifyRecTransfer(this.sUser, (RecTransfer)localObject2, (RecTransfer)localObject1, localHashMap);
        }
        else if (this.error == 0)
        {
          this.error = 28050;
        }
        i++;
      }
    }
  }
  
  private SecureUser jdMethod_int(User paramUser)
    throws CSILException
  {
    SecureUser localSecureUser = paramUser.getSecureUser();
    EntitlementGroupMember localEntitlementGroupMember = null;
    AffiliateBank localAffiliateBank = null;
    localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
    localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
    localSecureUser.setEntitlementID(localEntitlementGroupMember.getEntitlementGroupId());
    localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID(this.sUser, paramUser.getAffiliateBankID(), null);
    localSecureUser.setBPWFIID(localAffiliateBank.getFIBPWID());
    localSecureUser.setAffiliateID(paramUser.getAffiliateBankID());
    if (this.sUser.getUserType() == 2) {
      localSecureUser.setAgent(this.sUser);
    }
    return localSecureUser;
  }
  
  protected void addHistory()
  {
    HistoryTracker localHistoryTracker = new HistoryTracker(this.sUser, 1, getId());
    logChanges(localHistoryTracker, this.oldSecondaryUser, localHistoryTracker.buildLocalizableComment(17));
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.EditSecondaryUser: " + localProfileException.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.EditSecondaryUser
 * JD-Core Version:    0.7.0.1
 */