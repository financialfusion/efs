package com.ffusion.services.profile;

import com.ffusion.banksim.interfaces.BSException;
import com.ffusion.banksim.proxy.BankSim;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.beans.autoentitle.AutoEntitle;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.ContactPoint;
import com.ffusion.beans.user.ContactPoints;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTagValuesList;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.AutoEntitleAdmin;
import com.ffusion.csil.core.AutoEntitleUtil;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.handlers.Authentication;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.DirectoryDataAdapter;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.IMSAdapter;
import com.ffusion.efs.adapters.profile.ObjectPropertyAdapter;
import com.ffusion.efs.adapters.profile.Profile;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.services.UserAdmin10;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.DateTime;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class UserAdminService
  implements UserAdmin10
{
  private static final String jdField_int = "FIELD";
  private static HashMap jdField_for = null;
  
  public void initialize(String paramString)
    throws ProfileException
  {
    InputStream localInputStream = null;
    if ((paramString != null) && (paramString.length() != 0)) {
      try
      {
        localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
        if (localInputStream == null) {
          throw new ProfileException(3507);
        }
        String str = Strings.streamToString(localInputStream);
        XMLTag localXMLTag = new XMLTag();
        localXMLTag.build(str);
        jdField_for = localXMLTag.getTagHashMap();
        return;
      }
      catch (ProfileException localProfileException)
      {
        throw localProfileException;
      }
      catch (Exception localException2)
      {
        throw new ProfileException(1, localException2);
      }
      finally
      {
        try
        {
          localInputStream.close();
        }
        catch (Exception localException3) {}
      }
    }
  }
  
  public SecureUser authenticate(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    if ((paramHashMap != null) && (paramHashMap.containsKey("Credentials")))
    {
      localObject = (Credentials)paramHashMap.get("Credentials");
      try
      {
        Authentication.validateCredentials(paramSecureUser, (Credentials)localObject, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        try
        {
          if (paramSecureUser.getProfileID() != 0) {
            CustomerAdapter.logFailedLogin(paramSecureUser, paramHashMap);
          }
        }
        catch (ProfileException localProfileException) {}
        throw new ProfileException(22, localCSILException);
      }
    }
    Object localObject = CustomerAdapter.authenticateUser(paramSecureUser, paramHashMap);
    return localObject;
  }
  
  public boolean userExists(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    if (SignonSettings.allowDuplicateUserNames())
    {
      String str = (String)paramHashMap.get("BUSINESS_CUST_ID");
      return CustomerAdapter.userExists(paramString1, str, paramString2);
    }
    return CustomerAdapter.userExists(paramString1, paramString2);
  }
  
  public User getUserInfoFromBackend(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BankSim.signOn(paramString1, paramString2);
    }
    catch (BSException localBSException)
    {
      int i = 0;
      switch (localBSException.getErrorCode())
      {
      case 1006: 
        i = 8;
        break;
      case 1008: 
        i = 6;
        break;
      default: 
        i = 2;
      }
      throw new ProfileException(i);
    }
  }
  
  public User addUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.addUser(paramUser);
  }
  
  public void addSecondaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    paramUser.setAccountStatus(User.STATUS_INACTIVE);
    CustomerAdapter.addSecondaryUser(paramSecureUser, paramUser, paramHashMap);
    Accounts localAccounts = AccountAdapter.getAccounts(paramSecureUser);
    Users localUsers = new Users(paramUser.getLocale());
    localUsers.add(paramUser);
    try
    {
      EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setId(Integer.toString(paramUser.getIdValue()));
      localEntitlementGroupMember.setMemberType("USER");
      localEntitlementGroupMember.setMemberSubType(Integer.toString(1));
      localEntitlementGroupMember.setEntitlementGroupId(paramSecureUser.getEntitlementID());
      com.ffusion.csil.core.Entitlements.addMember(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlementGroupMember);
      paramUser.setEntitlementGroupId(paramSecureUser.getEntitlementID());
      EntitlementsUtil.addSecondaryUserAccountRestrictions(paramSecureUser, localUsers, true, localAccounts);
    }
    catch (Exception localException)
    {
      throw new ProfileException(3503, localException);
    }
  }
  
  public Users getSecondaryUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getSecondaryUsers(paramSecureUser, paramUser, paramHashMap);
  }
  
  public User getPrimaryUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getPrimaryUser(paramSecureUser, paramUser, paramHashMap);
  }
  
  public User getUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    User localUser = CustomerAdapter.getUser(paramSecureUser);
    paramUser.set(localUser);
    paramUser.setLocale(paramSecureUser.getLocale());
    return paramUser;
  }
  
  public User getUserById(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    User localUser = CustomerAdapter.getUserById(paramUser.getIdValue());
    paramUser.set(localUser);
    paramUser.setLocale(paramSecureUser.getLocale());
    return paramUser;
  }
  
  public Users getUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    Users localUsers = CustomerAdapter.getUsers(paramUser, Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap), paramHashMap);
    for (int i = 0; i < localUsers.size(); i++) {
      ((User)localUsers.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localUsers;
  }
  
  public ArrayList getUsersIDs(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getUsersIDs(paramUser);
  }
  
  public ArrayList getUserIDsByUserNames(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getUserIDsByUserNames(paramArrayList);
  }
  
  public String getUsersCount(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getUsersCount(paramUser);
  }
  
  public String getConsumersCount(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getConsumersCount(paramUser);
  }
  
  public Users getUserList(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    Users localUsers = CustomerAdapter.getUserList(paramUser, Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
    for (int i = 0; i < localUsers.size(); i++) {
      ((User)localUsers.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localUsers;
  }
  
  public Users getFilteredUsers(SecureUser paramSecureUser, User paramUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    Users localUsers;
    if (paramAccount != null) {
      localUsers = CustomerAdapter.getFilteredUsers(paramUser, paramAccount.getID(), Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
    } else {
      localUsers = CustomerAdapter.getFilteredUsers(paramUser, null, Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
    }
    for (int i = 0; i < localUsers.size(); i++) {
      ((User)localUsers.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localUsers;
  }
  
  public User modifyPasswordStatus(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "UserAdminService.modifyPasswordStatus";
    int i = 0;
    try
    {
      i = Integer.parseInt((String)paramUser.get("PASSWORD_STATUS"));
    }
    catch (Exception localException)
    {
      throw new ProfileException(str, 3, "Invalid password status");
    }
    CustomerAdapter.modifyPasswordStatus(paramUser.getIdValue(), i);
    return paramUser;
  }
  
  public User modifyUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    boolean bool1 = false;
    if ((paramSecureUser.getUserType() == 1) && (paramSecureUser.getProfileID() == Integer.parseInt(paramUser.getId())))
    {
      Integer localInteger = (Integer)paramSecureUser.get("AUTHENTICATE");
      boolean bool2 = true;
      if ((localInteger != null) && ((localInteger.intValue() == 3007) || (localInteger.intValue() == 3024))) {
        bool2 = false;
      }
      int i = CustomerAdapter.checkPasswordHistory(1, Integer.parseInt(paramUser.getId()), paramUser.getPassword(), bool2);
      switch (i)
      {
      case 2: 
        throw new ProfileException(14);
      case 3: 
        throw new ProfileException(16);
      case 4: 
        throw new ProfileException(17);
      }
      bool1 = CustomerAdapter.modifyUser(paramUser);
      if ((bool1) && (i == 1)) {
        CustomerAdapter.addToPasswordHistory(1, Integer.parseInt(paramUser.getId()), paramUser.getPassword());
      }
      if (localInteger != null) {
        paramSecureUser.remove("AUTHENTICATE");
      }
    }
    else
    {
      bool1 = CustomerAdapter.modifyUser(paramUser);
    }
    if (!bool1) {
      throw new ProfileException(3503);
    }
    return paramUser;
  }
  
  public User modifyUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    if ((paramUser == null) || (!paramUser.getPassword().equals(paramString2))) {
      throw new ProfileException(8);
    }
    if ((paramSecureUser.getUserType() == 1) && (paramSecureUser.getProfileID() == Integer.parseInt(paramUser.getId())))
    {
      Integer localInteger = (Integer)paramSecureUser.get("AUTHENTICATE");
      boolean bool = true;
      if ((localInteger != null) && ((localInteger.intValue() == 3007) || (localInteger.intValue() == 3024))) {
        bool = false;
      }
      int i = CustomerAdapter.checkPasswordHistory(1, Integer.parseInt(paramUser.getId()), paramString1, bool);
      switch (i)
      {
      case 2: 
        throw new ProfileException(14);
      case 3: 
        throw new ProfileException(16);
      case 4: 
        throw new ProfileException(17);
      }
      CustomerAdapter.modifyUserPassword(paramUser.getIdValue(), paramString2, paramString1, paramUser.getPasswordClue(), paramUser.getPasswordReminder());
      if (i == 1) {
        CustomerAdapter.addToPasswordHistory(1, Integer.parseInt(paramUser.getId()), paramString1);
      }
      if (localInteger != null) {
        paramSecureUser.remove("AUTHENTICATE");
      }
    }
    else
    {
      CustomerAdapter.modifyUserPassword(paramUser.getIdValue(), paramString2, paramString1, paramUser.getPasswordClue(), paramUser.getPasswordReminder());
    }
    paramUser.setPassword(paramString1);
    paramUser.set("PASSWORD_STATUS", String.valueOf(0));
    return paramUser;
  }
  
  public Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    AccountAdapter.addAccount(paramAccount, paramSecureUser.getProfileID());
    return paramAccount;
  }
  
  public Accounts getAccounts(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    return AccountAdapter.getAccounts(paramAccount, paramSecureUser.getProfileID());
  }
  
  public Account modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    boolean bool = AccountAdapter.modifyAccount(paramAccount, paramSecureUser.getProfileID());
    if (!bool) {
      throw new ProfileException(3503);
    }
    return paramAccount;
  }
  
  public Account deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    AccountAdapter.deleteAccount(paramAccount, paramSecureUser.getProfileID());
    return paramAccount;
  }
  
  public void addAdditionalData(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    if ((paramString1 == null) || (paramString1.equals("DIRECTORY_ID"))) {
      throw new ProfileException(3750);
    }
    try
    {
      DirectoryDataAdapter.addAdditionalData(paramSecureUser.getProfileID(), paramString1, "<" + paramString1 + ">" + paramString2 + "</" + paramString1 + ">");
    }
    catch (Exception localException)
    {
      throw new ProfileException(3503, localException);
    }
  }
  
  public String getAdditionalData(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    if ((paramString != null) && (paramString.equals("DIRECTORY_ID"))) {
      throw new ProfileException(3750);
    }
    try
    {
      return DirectoryDataAdapter.getAdditionalData(paramSecureUser.getProfileID(), paramString);
    }
    catch (Exception localException)
    {
      throw new ProfileException(3503, localException);
    }
  }
  
  public Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws ProfileException
  {
    HistoryAdapter.addHistory(paramHistories);
    return paramHistories;
  }
  
  public Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws ProfileException
  {
    return HistoryAdapter.getHistory(paramHistory, paramCalendar1, paramCalendar2);
  }
  
  public Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException
  {
    return getHistory(paramSecureUser, paramHistory, paramCalendar1, paramCalendar2, paramHashMap);
  }
  
  public CustomTags addCustomTags(SecureUser paramSecureUser, CustomTags paramCustomTags, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    boolean bool = true;
    int i = -1;
    try
    {
      i = Integer.parseInt(paramUser.getId());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new ProfileException(3505, localNumberFormatException);
    }
    bool = IMSAdapter.addImsTag(i, paramCustomTags);
    if (!bool) {
      throw new ProfileException(3503);
    }
    return paramCustomTags;
  }
  
  public CustomTags getCustomTags(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    int i = -1;
    try
    {
      i = Integer.parseInt(paramUser.getId());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new ProfileException(3505, localNumberFormatException);
    }
    return IMSAdapter.getImsTag(i, null);
  }
  
  public CustomTag modifyCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    boolean bool = true;
    int i = -1;
    try
    {
      i = Integer.parseInt(paramUser.getId());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new ProfileException(3505, localNumberFormatException);
    }
    bool = IMSAdapter.modifyImsTag(i, paramCustomTag);
    if (!bool) {
      throw new ProfileException(3503);
    }
    return paramCustomTag;
  }
  
  public CustomTag deleteCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    boolean bool = true;
    int i = -1;
    try
    {
      i = Integer.parseInt(paramUser.getId());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new ProfileException(3505, localNumberFormatException);
    }
    bool = IMSAdapter.deleteImsTag(i, paramCustomTag);
    if (!bool) {
      throw new ProfileException(3503);
    }
    return paramCustomTag;
  }
  
  public CustomTagValuesList getCustomTagValuesList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    throw new ProfileException(13);
  }
  
  public ArrayList getExtraFields(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    if (jdField_for == null) {
      throw new ProfileException(1);
    }
    int i = 1;
    String str = (String)jdField_for.get("FIELD" + i);
    ArrayList localArrayList = new ArrayList();
    while (str != null)
    {
      localArrayList.add(str);
      i++;
      str = (String)jdField_for.get("FIELD" + i);
    }
    return localArrayList;
  }
  
  public User deleteUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    CustomerAdapter.deleteUser(paramUser);
    return paramUser;
  }
  
  private static void a(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = paramBusinessEmployee.getEntitlementGroupMember();
    com.ffusion.csil.core.Entitlements.addMember(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlementGroupMember);
  }
  
  public BusinessEmployee addBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    BusinessEmployee localBusinessEmployee = CustomerAdapter.addBusinessEmployee(paramBusinessEmployee);
    try
    {
      a(paramSecureUser, localBusinessEmployee, paramHashMap);
    }
    catch (CSILException localCSILException1)
    {
      deleteBusinessEmployee(paramSecureUser, paramBusinessEmployee, paramHashMap);
      throw new ProfileException(21, localCSILException1);
    }
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    if (paramHashMap != null) {
      localEntitlements = (com.ffusion.csil.beans.entitlements.Entitlements)paramHashMap.get("AUTOENTITLE_RESTRICTED_ENTS_KEY");
    }
    if (localEntitlements != null) {
      try
      {
        AutoEntitleAdmin.restrictFeatures(paramSecureUser, paramBusinessEmployee.getEntitlementGroupMember(), localEntitlements, paramHashMap);
      }
      catch (CSILException localCSILException2)
      {
        throw new ProfileException(20, localCSILException2);
      }
    }
    return localBusinessEmployee;
  }
  
  public BusinessEmployee modifyBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    Object localObject1;
    boolean bool1;
    if ((paramSecureUser.getUserType() == 1) && (paramSecureUser.getProfileID() == Integer.parseInt(paramBusinessEmployee.getId())))
    {
      localObject1 = (Integer)paramSecureUser.get("AUTHENTICATE");
      boolean bool2 = true;
      if ((localObject1 != null) && ((((Integer)localObject1).intValue() == 3007) || (((Integer)localObject1).intValue() == 3024))) {
        bool2 = false;
      }
      int i = CustomerAdapter.checkPasswordHistory(1, Integer.parseInt(paramBusinessEmployee.getId()), paramBusinessEmployee.getPassword(), bool2);
      switch (i)
      {
      case 2: 
        throw new ProfileException(14);
      case 3: 
        throw new ProfileException(16);
      case 4: 
        throw new ProfileException(17);
      }
      bool1 = CustomerAdapter.modifyBusinessEmployee(paramBusinessEmployee);
      if ((bool1) && (i == 1)) {
        CustomerAdapter.addToPasswordHistory(1, Integer.parseInt(paramBusinessEmployee.getId()), paramBusinessEmployee.getPassword());
      }
      if (localObject1 != null) {
        paramSecureUser.remove("AUTHENTICATE");
      }
    }
    else
    {
      bool1 = CustomerAdapter.modifyBusinessEmployee(paramBusinessEmployee);
      localObject1 = (Boolean)paramHashMap.get("ENT_GROUP_CHANGED_KEY");
      Boolean localBoolean = (Boolean)paramHashMap.get("AUTOENTITLE_FLAG_KEY");
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
      if (paramHashMap != null) {
        localEntitlements = (com.ffusion.csil.beans.entitlements.Entitlements)paramHashMap.get("AUTOENTITLE_RESTRICTED_ENTS_KEY");
      }
      if ((localObject1 != null) && (((Boolean)localObject1).booleanValue())) {
        try
        {
          EntitlementGroupMember localEntitlementGroupMember = paramBusinessEmployee.getEntitlementGroupMember();
          if ((localBoolean != null) && (localBoolean.booleanValue()))
          {
            localObject2 = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
            localObject3 = AutoEntitleUtil.buildAdminEntitlementsList(com.ffusion.csil.core.Entitlements.getRestrictedEntitlements((EntitlementGroupMember)localObject2, localEntitlementGroupMember));
            com.ffusion.csil.core.Entitlements.setRestrictedEntitlements((EntitlementGroupMember)localObject2, localEntitlementGroupMember, (com.ffusion.csil.beans.entitlements.Entitlements)localObject3);
          }
          if (localEntitlements != null) {
            AutoEntitleAdmin.restrictFeatures(paramSecureUser, localEntitlementGroupMember, localEntitlements, paramHashMap);
          }
          Object localObject2 = new AutoEntitle();
          ((AutoEntitle)localObject2).setEntitlementGroupMember(localEntitlementGroupMember);
          Object localObject3 = AutoEntitleAdmin.getSettings(paramSecureUser, localEntitlementGroupMember, paramHashMap);
          AutoEntitleAdmin.setSettings(paramSecureUser, (AutoEntitle)localObject2, (AutoEntitle)localObject3, paramHashMap);
        }
        catch (CSILException localCSILException)
        {
          throw new ProfileException(20, localCSILException);
        }
      }
    }
    if (bool1) {
      maintainAlertsOnBusinessEmployeeModification(paramSecureUser, paramBusinessEmployee, paramHashMap);
    } else {
      throw new ProfileException(3503);
    }
    return paramBusinessEmployee;
  }
  
  public BusinessEmployees getBusinessEmployees(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    BusinessEmployees localBusinessEmployees = CustomerAdapter.getBusinessEmployees(paramBusinessEmployee, Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
    for (int i = 0; i < localBusinessEmployees.size(); i++) {
      ((BusinessEmployee)localBusinessEmployees.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localBusinessEmployees;
  }
  
  public ArrayList getBusinessEmployeesIDs(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getBusinessEmployeesIDs(paramBusinessEmployee);
  }
  
  public ArrayList getBusinessEmployeesIDs(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getBusinessEmployeesIDs(paramArrayList);
  }
  
  public Users getConsumers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    Users localUsers = CustomerAdapter.getConsumers(paramUser, Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
    for (int i = 0; i < localUsers.size(); i++) {
      ((User)localUsers.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localUsers;
  }
  
  public BusinessEmployee deleteBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    CustomerAdapter.deleteBusinessEmployee(paramBusinessEmployee);
    maintainAlertsOnBusinessEmployeeModification(paramSecureUser, paramBusinessEmployee, paramHashMap);
    return paramBusinessEmployee;
  }
  
  protected void maintainAlertsOnBusinessEmployeeModification(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    int i = 1;
    int j = 2;
    int k = 3;
    Object localObject1 = paramHashMap.get("SERVICE");
    com.ffusion.services.Alerts localAlerts;
    if ((localObject1 != null) && ((localObject1 instanceof com.ffusion.services.Alerts))) {
      localAlerts = (com.ffusion.services.Alerts)localObject1;
    } else {
      return;
    }
    int m = Integer.parseInt(paramBusinessEmployee.getAccountStatus());
    int n;
    if (m == 8) {
      n = 3;
    } else if ((m == 2) || (m == 64)) {
      n = 2;
    } else {
      n = 1;
    }
    try
    {
      String str1 = paramBusinessEmployee.getUserName() + ":" + paramBusinessEmployee.getId();
      localAlerts.setUserName(str1);
      com.ffusion.beans.alerts.Alerts localAlerts1 = new com.ffusion.beans.alerts.Alerts();
      int i1 = localAlerts.getAlerts(localAlerts1);
      if (i1 == 0) {
        for (int i2 = 0; i2 < localAlerts1.size(); i2++)
        {
          Alert localAlert = (Alert)localAlerts1.get(i2);
          if (n == 3)
          {
            localAlerts.deleteAlert(localAlert);
          }
          else
          {
            DeliveryInfos localDeliveryInfos = localAlert.getDeliveryInfosValue();
            boolean bool;
            if (n == 1) {
              bool = false;
            } else {
              bool = true;
            }
            for (int i3 = 0; i3 < localDeliveryInfos.size(); i3++)
            {
              DeliveryInfo localDeliveryInfo = (DeliveryInfo)localDeliveryInfos.get(i3);
              localDeliveryInfo.setSuspended(bool);
            }
            localAlerts.modifyAlert(localAlert);
          }
        }
      }
    }
    finally
    {
      String str2 = paramSecureUser.getUserName() + ":" + paramSecureUser.getId();
      localAlerts.setUserName(str2);
    }
  }
  
  public boolean businessIdExists(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return CustomerAdapter.isValidBusinessId(paramInt);
    }
    catch (Exception localException)
    {
      throw new ProfileException(3503, localException);
    }
  }
  
  public BusinessEmployees getFilteredBusinessEmployees(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    BusinessEmployees localBusinessEmployees = CustomerAdapter.getFilteredBusinessEmployees(paramBusiness, paramBusinessEmployee, Profile.useMaxRows(paramHashMap), Profile.getMaxRowCount(paramHashMap));
    for (int i = 0; i < localBusinessEmployees.size(); i++) {
      ((BusinessEmployee)localBusinessEmployees.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localBusinessEmployees;
  }
  
  public ArrayList getFilteredBusinessEmployeesIDs(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getFilteredBusinessEmployeesIDs(paramBusiness, paramBusinessEmployee);
  }
  
  public String getFilteredBusinessEmployeesCount(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getFilteredBusinessEmployeesCount(paramBusiness, paramBusinessEmployee);
  }
  
  public BusinessEmployees getBusinessEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    BusinessEmployees localBusinessEmployees = CustomerAdapter.getBusinessEmployeesByIds(paramArrayList);
    for (int i = 0; i < localBusinessEmployees.size(); i++) {
      ((BusinessEmployee)localBusinessEmployees.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localBusinessEmployees;
  }
  
  public User resetUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    CustomerAdapter.resetUserPassword(paramUser, paramString1, paramString2);
    paramUser.setPassword(paramString2);
    return paramUser;
  }
  
  public ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getBankIdsByServicesPackage(paramSecureUser, paramInt, paramHashMap);
  }
  
  public BusinessEmployees getFilteredBusinessEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    BusinessEmployees localBusinessEmployees = CustomerAdapter.getFilteredBusinessEmployeesByIds(paramArrayList, paramBusinessEmployee);
    for (int i = 0; i < localBusinessEmployees.size(); i++) {
      ((BusinessEmployee)localBusinessEmployees.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localBusinessEmployees;
  }
  
  public Users getUsersByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    Users localUsers = CustomerAdapter.getUsersByIds(paramArrayList);
    for (int i = 0; i < localUsers.size(); i++) {
      ((User)localUsers.get(i)).setLocale(paramSecureUser.getLocale());
    }
    return localUsers;
  }
  
  public boolean getInfoForAuditing(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getInfoForAuditing(paramSecureUser, paramHashMap);
  }
  
  public DateTime getLastExportedDateTime(SecureUser paramSecureUser, Account paramAccount, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    DateTime localDateTime = null;
    try
    {
      String str1 = a(paramString, paramAccount);
      String str2 = ObjectPropertyAdapter.getObjectPropertyValue(3, str1, "REPORT_EXPORT");
      if (str2 == null) {
        return null;
      }
      DateFormat localDateFormat = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss");
      localDateTime = new DateTime(localDateFormat.parse(str2), Locale.getDefault());
    }
    catch (Exception localException)
    {
      throw new ProfileException(5000);
    }
    return localDateTime;
  }
  
  public void setLastExportedDateTime(SecureUser paramSecureUser, Account paramAccount, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      String str1 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").format(paramDateTime.getTime());
      String str2 = a(paramString, paramAccount);
      ObjectPropertyAdapter.modifyObjectProperty(3, str2, "REPORT_EXPORT", str1);
    }
    catch (Exception localException)
    {
      throw new ProfileException(5001);
    }
  }
  
  private String a(String paramString, Account paramAccount)
  {
    if ("ConsumerAccountHistory".equals(paramString)) {
      return "LASTEXP_AH_" + paramAccount.getRoutingNum() + ":" + paramAccount.getID();
    }
    if ("ConsumerAccountRegister".equals(paramString)) {
      return "LASTEXP_AR_" + paramAccount.getRoutingNum() + ":" + paramAccount.getID();
    }
    return null;
  }
  
  public void addContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException
  {
    CustomerAdapter.addContactPoint(paramSecureUser, paramContactPoint, paramHashMap);
  }
  
  public void addContactPoints(SecureUser paramSecureUser, ContactPoints paramContactPoints, HashMap paramHashMap)
    throws ProfileException
  {
    CustomerAdapter.addContactPoints(paramSecureUser, paramContactPoints, paramHashMap);
  }
  
  public void modifyContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException
  {
    CustomerAdapter.modifyContactPoint(paramSecureUser, paramContactPoint, paramHashMap);
  }
  
  public void deleteContactPoint(SecureUser paramSecureUser, ContactPoint paramContactPoint, HashMap paramHashMap)
    throws ProfileException
  {
    CustomerAdapter.deleteContactPoint(paramSecureUser, paramContactPoint, paramHashMap);
  }
  
  public ContactPoints getContactPoints(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getContactPoints(paramSecureUser, paramInt, paramHashMap);
  }
  
  public ContactPoint getContactPoint(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getContactPoint(paramSecureUser, paramInt, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.UserAdminService
 * JD-Core Version:    0.7.0.1
 */