package com.ffusion.tasks.admin;

import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.PasswordUtil;
import com.ffusion.tasks.util.ValidateString;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddBusinessUser
  extends AutoEntitleBaseTask
  implements AdminTask
{
  private static final String X6 = "X@Y.Z";
  private static final char[] Yb = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ' ', '(', ')', '-' };
  private static final char XZ = '@';
  private static final char X7 = '.';
  private boolean XY = false;
  private HttpServletRequest X2 = null;
  private String X8 = "ACCOUNTS";
  private String XX = "AccountID";
  private String Ye = "Entitlement_EntitlementGroup";
  private String X3 = "GroupEntitlements";
  private String Ya;
  private static Entitlement Yf = new Entitlement("Division Management", null, null);
  private static Entitlement X5 = new Entitlement("Group Management", null, null);
  private int X4 = 3;
  private int X0 = 3;
  private int X9 = 1;
  private int Yd = 1;
  private boolean X1 = false;
  private int Yc = 1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    this.X2 = paramHttpServletRequest;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessEmployee localBusinessEmployee = (BusinessEmployee)localHttpSession.getAttribute("BusinessEmployee");
    if (localBusinessEmployee == null)
    {
      this.error = 4501;
      return super.getTaskErrorURL();
    }
    if (localBusinessEmployee.getEntitlementGroupId() == -1)
    {
      this.error = 72;
      return super.getTaskErrorURL();
    }
    if (this._initAutoEntitle) {
      try
      {
        this._empGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(localBusinessEmployee.getEntitlementGroupId());
        initialize(localSecureUser);
        this._initAutoEntitle = false;
        return null;
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        return super.getTaskErrorURL();
      }
    }
    EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    if (localEntitlementGroupMember == null)
    {
      localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      try
      {
        localEntitlementGroupMember = com.ffusion.csil.core.Entitlements.getMember(localEntitlementGroupMember);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        return super.getServiceErrorURL();
      }
    }
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)localHttpSession.getAttribute(this.Ye);
    if (localEntitlementGroup == null)
    {
      this.error = 4508;
      return super.getTaskErrorURL();
    }
    int i = localEntitlementGroup.getGroupId();
    if (localBusinessEmployee.getUserName() != null)
    {
      if (Strings.verifyStringWithNonAscii(localBusinessEmployee.getUserName()))
      {
        this.error = 199;
        return super.getTaskErrorURL();
      }
      if ((localBusinessEmployee.getUserName().length() < this.X4) || (localBusinessEmployee.getUserName().length() > 20))
      {
        this.error = 1;
        return super.getTaskErrorURL();
      }
    }
    else
    {
      this.error = 1;
      return super.getTaskErrorURL();
    }
    if (!localBusinessEmployee.getUserName().toLowerCase().equals(localBusinessEmployee.getUserName()))
    {
      this.error = 1;
      return super.getTaskErrorURL();
    }
    String str1 = (String)localHttpSession.getAttribute("NameConvention");
    if ((str1.equalsIgnoreCase("dual")) && ((localBusinessEmployee.getFirstName() == null) || (localBusinessEmployee.getFirstName().length() == 0)))
    {
      this.error = 28;
      return super.getTaskErrorURL();
    }
    if ((localBusinessEmployee.getLastName() == null) || (localBusinessEmployee.getLastName().length() == 0))
    {
      this.error = 30;
      return super.getTaskErrorURL();
    }
    if (!ValidateString.validateName(localBusinessEmployee.getUserName()))
    {
      this.error = 4544;
      return super.getTaskErrorURL();
    }
    if ((str1.equalsIgnoreCase("dual")) && ((!ValidateString.validateName(localBusinessEmployee.getFirstName(), "-'")) || (localBusinessEmployee.getFirstName().length() > 35)))
    {
      this.error = 4544;
      return super.getTaskErrorURL();
    }
    if ((!ValidateString.validateName(localBusinessEmployee.getLastName(), "-'")) || (localBusinessEmployee.getLastName().length() > 35))
    {
      this.error = 4544;
      return super.getTaskErrorURL();
    }
    this.Ya = localBusinessEmployee.getPassword();
    String str2 = localBusinessEmployee.getConfirmPassword();
    if ((this.Ya == null) || (this.Ya.length() == 0) || (str2 == null) || (str2.length() == 0))
    {
      this.error = 3;
      return super.getTaskErrorURL();
    }
    if ((str2 != null) && (this.Ya != null) && (!str2.equals(this.Ya)))
    {
      this.error = 5;
      return super.getTaskErrorURL();
    }
    if (validatePassword() != true) {
      return super.getTaskErrorURL();
    }
    try
    {
      if (!jdMethod_new(localSecureUser, localBusinessEmployee.getZipCode(), localBusinessEmployee.getCountry()))
      {
        this.error = 26;
        return super.getTaskErrorURL();
      }
      if (!jdMethod_try(localSecureUser, localBusinessEmployee.getPhone(), localBusinessEmployee.getCountry()))
      {
        this.error = 27;
        return super.getTaskErrorURL();
      }
      if (!jdMethod_try(localSecureUser, localBusinessEmployee.getFaxPhone(), localBusinessEmployee.getCountry()))
      {
        this.error = 119;
        return super.getTaskErrorURL();
      }
      if (!jdMethod_try(localSecureUser, localBusinessEmployee.getDataPhone(), localBusinessEmployee.getCountry()))
      {
        this.error = 120;
        return super.getTaskErrorURL();
      }
      if (!F(localBusinessEmployee.getEmail()))
      {
        this.error = 31;
        return super.getTaskErrorURL();
      }
      localHashMap.put("BUSINESS_CUST_ID", localSecureUser.getBusinessCustId());
      if (UserAdmin.userExists(localSecureUser, localBusinessEmployee.getUserName(), localBusinessEmployee.getBankId(), localHashMap))
      {
        this.error = 3014;
        return super.getServiceErrorURL();
      }
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = buildRestrictedEntitlementsList(localSecureUser);
      if (com.ffusion.csil.core.Entitlements.getAdminInfoFor(localBusinessEmployee.getEntitlementGroupId()).size() != 0)
      {
        localHashMap.put("AUTOENTITLE_FLAG_KEY", new Boolean(this._autoEntitle));
        localHashMap.put("AUTOENTITLE_SETTINGS_KEY", this._autoEntitleSettings);
        localObject = AutoEntitleUtil.buildRestrictedAdminEntitlementsList(localSecureUser, this._empGroup, localHashMap);
        localEntitlements.addAll((Collection)localObject);
      }
      localHashMap.put("AUTOENTITLE_RESTRICTED_ENTS_KEY", localEntitlements);
      localBusinessEmployee = UserAdmin.addBusinessEmployee(localSecureUser, localBusinessEmployee, localHashMap);
      Object localObject = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
      ((User)localObject).setId(localBusinessEmployee.getId());
      ((User)localObject).put("PASSWORD_STATUS", "2");
      UserAdmin.modifyPasswordStatus(localSecureUser, (User)localObject, localHashMap);
      HistoryTracker localHistoryTracker = new HistoryTracker(localSecureUser, 1, localBusinessEmployee.getId());
      localBusinessEmployee.logCreation(localHistoryTracker, localHistoryTracker.buildLocalizableComment(1));
      try
      {
        HistoryAdapter.addHistory(localHistoryTracker.getHistories());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.SEVERE, "Add History failed for AddBusinessUser: " + localProfileException.toString());
      }
    }
    catch (CSILException localCSILException3)
    {
      this.error = MapError.mapError(localCSILException3);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setProcess(String paramString)
  {
    this.XY = (paramString.equalsIgnoreCase("true"));
  }
  
  public void setCheckGroupAccountsName(String paramString)
  {
    this.X8 = paramString;
  }
  
  public void setCheckAccountID(String paramString)
  {
    this.XX = paramString;
  }
  
  public void setEntitlementsName(String paramString)
  {
    this.X3 = paramString;
  }
  
  public void setGroupName(String paramString)
  {
    this.Ye = paramString;
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      this.Yc = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.Yc = 1;
    }
  }
  
  public void setMinimumUserNameLength(int paramInt)
  {
    this.X4 = paramInt;
  }
  
  public void setMinimumPasswordLength(int paramInt)
  {
    this.X0 = paramInt;
  }
  
  public void setMinimumPasswordLength(String paramString)
  {
    this.X0 = Integer.parseInt(paramString);
  }
  
  public void setMinimumNumbersInPassword(int paramInt)
  {
    this.X9 = paramInt;
  }
  
  public void setMinimumNumbersInPassword(String paramString)
  {
    this.X9 = Integer.parseInt(paramString);
  }
  
  public void setMinimumLettersInPassword(int paramInt)
  {
    this.Yd = paramInt;
  }
  
  public void setMinimumLettersInPassword(String paramString)
  {
    this.Yd = Integer.parseInt(paramString);
  }
  
  public void setAllowSpecialChars(boolean paramBoolean)
  {
    this.X1 = paramBoolean;
  }
  
  public void setAllowSpecialChars(String paramString)
  {
    this.X1 = Boolean.valueOf(paramString).booleanValue();
  }
  
  private boolean jdMethod_try(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      return true;
    }
    if (paramString1.length() > 14) {
      return false;
    }
    Phone localPhone = new Phone(paramString1.trim(), true);
    String str = Util.validatePhoneFormat(paramSecureUser, paramString2, localPhone.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    if (!str.equals(""))
    {
      localPhone.setFormat(str);
      return true;
    }
    return true;
  }
  
  private boolean jdMethod_new(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException
  {
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      return true;
    }
    if (paramString1.length() > 11) {
      return false;
    }
    ZipCode localZipCode = new ZipCode(paramString1.trim(), true);
    String str = Util.validateZipCodeFormat(paramSecureUser, paramString2, localZipCode.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    if (!str.equals(""))
    {
      localZipCode.setFormat(str);
      return true;
    }
    return true;
  }
  
  private boolean F(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return true;
    }
    if ((paramString.length() < "X@Y.Z".length()) || (paramString.length() > 40)) {
      return false;
    }
    if ((paramString.charAt(0) == '@') || (paramString.charAt(0) == '.') || (paramString.charAt(paramString.length() - 1) == '.') || (paramString.substring(paramString.length() - 3).indexOf('@') >= 0)) {
      return false;
    }
    int i = 0;
    for (int j = 1; j < paramString.length() - 3; j++) {
      if (paramString.charAt(j) == '@') {
        i++;
      }
    }
    return i == 1;
  }
  
  protected boolean validatePassword()
  {
    int i = PasswordUtil.validatePassword(this.Ya, this.Yc);
    if (i != 0)
    {
      this.error = i;
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.AddBusinessUser
 * JD-Core Version:    0.7.0.1
 */