package com.ffusion.tasks.user;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.beans.user.OneToOneUser;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Authenticate;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.TTYAdmin;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetOneToOneUser
  extends GetUser
{
  private String ob = "false";
  private String od = "UserService";
  private String oc = "Credentials";
  
  public int signOn(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    if (this.bankID == null) {
      this.bankID = ((String)localHttpSession.getAttribute("BankId"));
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (this.bankID == null)
    {
      DebugLog.log(Level.WARNING, "***** com.ffusion.tasks.user.GetOneToOneUser.BankID not set, assuming 1000 ****** ");
      this.bankID = "1000";
    }
    SecureUser localSecureUser1 = (SecureUser)localHttpSession.getAttribute("SecureUser");
    SecureUser localSecureUser2 = null;
    if (localSecureUser1 != null)
    {
      localSecureUser2 = new SecureUser();
      localSecureUser2.set(localSecureUser1);
      localSecureUser2.updateUserName(this.userName);
      localSecureUser2.updateId(this.custId);
      localSecureUser2.updatePassword(this.password);
      localSecureUser2.updateBankID(this.bankID);
      localSecureUser2.updateBusinessCustId(this.custId);
      if (this.affiliateBankID != null) {
        localSecureUser2.updateAffiliateID(this.affiliateBankID);
      }
      localSecureUser2.setLocale(localLocale);
    }
    else
    {
      try
      {
        localSecureUser2 = Authenticate.authenticate(this.userName, this.password, this.bankID, null);
        if ((this.custId != null) && (this.custId.length() > 0)) {
          localSecureUser2.setBusinessCustId(this.custId);
        }
        localSecureUser2.setLocale(localLocale);
        if (this.affiliateBankID != null) {
          localSecureUser2.setAffiliateID(this.affiliateBankID);
        }
        localHttpSession.setAttribute("SecureUser", localSecureUser2);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
      }
    }
    OneToOneUser localOneToOneUser = new OneToOneUser(localLocale);
    localOneToOneUser.setUserName(this.userName);
    if ((this.error == 0) && (localSecureUser2.getUserType() != 2))
    {
      localOneToOneUser.setPassword(this.password);
      HashMap localHashMap = new HashMap();
      localHashMap.put("USER", localOneToOneUser);
      if (!this.bAuthenticate) {
        localHashMap.put("NO_PASSWORD_CHECK", "1");
      }
      Object localObject2;
      if (this.userType != null)
      {
        String str2 = localSecureUser2.getPassword();
        localSecureUser2.updatePassword(null);
        try
        {
          localOneToOneUser = (OneToOneUser)UserAdmin.getUser(localSecureUser2, localOneToOneUser, null);
        }
        catch (CSILException localCSILException4)
        {
          this.error = MapError.mapError(localCSILException4);
          str1 = this.serviceErrorURL;
        }
        finally
        {
          localSecureUser2.updatePassword(str2);
        }
        localObject2 = (String)localOneToOneUser.get("BUSINESS_ID");
        if ((localObject2 != null) && (((String)localObject2).length() == 0)) {
          localObject2 = null;
        }
        if ((this.userType.equalsIgnoreCase("consumer")) && (localObject2 != null)) {
          this.error = 3005;
        }
        if ((this.userType.equalsIgnoreCase("business")) && (localObject2 == null)) {
          this.error = 3005;
        }
      }
      if (this.error == 0) {
        try
        {
          boolean bool1 = new Boolean(getIsTTY()).booleanValue();
          if (bool1) {
            TTYAdmin.addTTYSupport(localHashMap, localHttpSession.getId());
          }
          localObject2 = (Credentials)localHttpSession.getAttribute(this.oc);
          if (localObject2 != null)
          {
            localHashMap.put("Credentials", localObject2);
            localHttpSession.removeAttribute(this.oc);
          }
          localSecureUser2 = UserAdmin.authenticate(localSecureUser2, localHashMap);
          localHashMap.remove("Credentials");
        }
        catch (CSILException localCSILException3)
        {
          TTYAdmin.removeTTYSupport(localHashMap);
          this.error = MapError.mapError(localCSILException3);
          str1 = this.serviceErrorURL;
        }
      }
      localSecureUser2.setLocale(localLocale);
      boolean bool2;
      if (this.error == 0)
      {
        bool2 = false;
        try
        {
          localObject2 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser2);
          localObject2 = Entitlements.getMember((EntitlementGroupMember)localObject2);
          localSecureUser2.setEntitlementID(((EntitlementGroupMember)localObject2).getEntitlementGroupId());
          if ((localSecureUser2.getBusinessName() != null) && (!localSecureUser2.getBusinessName().equals("")))
          {
            EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroupByNameAndSvcBureau(localSecureUser2.getBusinessName(), "Business", localSecureUser2.getBankIDValue());
            EntitlementAdmin localEntitlementAdmin = new EntitlementAdmin((EntitlementGroupMember)localObject2, localEntitlementGroup.getGroupId());
            if (Entitlements.canAdminister(localEntitlementAdmin)) {
              bool2 = true;
            }
          }
        }
        catch (CSILException localCSILException5)
        {
          this.error = MapError.mapError(localCSILException5);
          str1 = this.serviceErrorURL;
        }
        DateTime localDateTime = (DateTime)localSecureUser2.get("LAST_ACTIVE_DATE");
        long l = -1L;
        if (localDateTime != null) {
          l = System.currentTimeMillis() - localDateTime.getTime().getTime();
        }
        if ((l >= SignonSettings.getInactivityPeriod() * 86400000L) && (!bool2))
        {
          try
          {
            User localUser = new User(localLocale);
            localUser.put("PASSWORD_STATUS", "4");
            localUser.setId(String.valueOf(localSecureUser2.getProfileID()));
            UserAdmin.modifyPasswordStatus(localSecureUser2, localUser, localHashMap);
          }
          catch (CSILException localCSILException7)
          {
            this.error = MapError.mapError(localCSILException7);
            str1 = this.serviceErrorURL;
          }
          localHttpSession.removeAttribute("SecureUser");
          this.error = 3017;
          return this.error;
        }
      }
      if (this.error == 0)
      {
        bool2 = new Boolean(getIsTTY()).booleanValue();
        if (bool2) {
          try
          {
            if (!TTYAdmin.isEntitled(localSecureUser2))
            {
              TTYAdmin.removeTTYSupport(localHashMap);
              this.error = 20001;
              str1 = this.serviceErrorURL;
            }
          }
          catch (CSILException localCSILException6)
          {
            TTYAdmin.removeTTYSupport(localHashMap);
            this.error = MapError.mapError(localCSILException6);
            str1 = this.serviceErrorURL;
          }
        }
      }
      localHttpSession.setAttribute("SecureUser", localSecureUser2);
    }
    if ((this.error == 0) || (this.error == 3007) || (this.error == 3024) || (this.error == 3018) || (this.error == 3023))
    {
      try
      {
        localOneToOneUser = (OneToOneUser)UserAdmin.getUser(localSecureUser2, localOneToOneUser, null);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        str1 = this.serviceErrorURL;
      }
      if (((this.accountStatus == null) || (!this.accountStatus.equals(localOneToOneUser.getAccountStatus()))) && (localOneToOneUser.getAccountStatus() != null) && (localOneToOneUser.getAccountStatus().equals("2"))) {
        this.error = 100;
      }
      if (this.userType != null)
      {
        localObject1 = (String)localOneToOneUser.get("BUSINESS_ID");
        if ((localObject1 != null) && (((String)localObject1).length() == 0)) {
          localObject1 = null;
        }
        if ((this.userType.equalsIgnoreCase("consumer")) && (localObject1 != null)) {
          this.error = 3005;
        }
        if ((this.userType.equalsIgnoreCase("business")) && (localObject1 == null)) {
          this.error = 3005;
        }
      }
    }
    if ((this.error == 0) && (localSecureUser1 != null) && (localSecureUser1.getUserType() == 2) && (localSecureUser1.getAffiliateIDValue() != 0) && (localSecureUser1.getAffiliateIDValue() != localOneToOneUser.getAffiliateBankID())) {
      this.error = 104;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("User", localOneToOneUser);
    }
    Object localObject1 = (Integer)localSecureUser2.get("AUTHENTICATE");
    if (localObject1 != null)
    {
      if (localSecureUser2.getAgent() == null) {
        this.error = ((Integer)localObject1).intValue();
      }
      if ((((Integer)localObject1).intValue() != 3007) && (((Integer)localObject1).intValue() != 3024) && (((Integer)localObject1).intValue() != 3018) && (((Integer)localObject1).intValue() != 3023)) {
        localSecureUser2.remove("AUTHENTICATE");
      }
    }
    return this.error;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.od = paramString;
  }
  
  public String getIsTTY()
  {
    return this.ob;
  }
  
  public void setIsTTY(String paramString)
  {
    this.ob = paramString;
  }
  
  public void setCredentialsCollection(String paramString)
  {
    this.oc = paramString;
  }
  
  public String getCredentialsCollection()
  {
    return this.oc;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetOneToOneUser
 * JD-Core Version:    0.7.0.1
 */