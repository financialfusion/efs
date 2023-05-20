package com.ffusion.services.authentication;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credential;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.user.User;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.efs.adapters.profile.BankEmployeeAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.IAuthentication;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLog;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

public class AuthenticationService
  implements IAuthentication
{
  private static final String jdField_int = "com.ffusion.beans.user.resources";
  private static final String jdField_case = "PWD_QUESTION_LIST";
  private static final String jdField_do = "PWD_QUESTION";
  private static final String jdField_byte = "com.ffusion.util.logging.audit.authentication";
  private static final String jdField_if = "AuditMessage_1.";
  private static final String jdField_for = "AuditMessage_2";
  private static final String jdField_new = "AuditMessage_3";
  private static final String a = "AuditMessage_4";
  private Properties jdField_try;
  
  public void initialize(HashMap paramHashMap)
    throws AuthException
  {
    String str1 = "AuthenticationServer.initialize";
    InputStream localInputStream = null;
    String str2 = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(this, "authentication.xml");
      str2 = Strings.streamToString(localInputStream, "UTF-8");
    }
    catch (Exception localException1)
    {
      throw new AuthException(str1, 1);
    }
    XMLTag localXMLTag1 = null;
    try
    {
      localXMLTag1 = new XMLTag(true);
      localXMLTag1.build(str2);
    }
    catch (Exception localException2)
    {
      throw new AuthException(str1, 1);
    }
    XMLTag localXMLTag2 = localXMLTag1.getContainedTag("AUTH_SETTINGS");
    if (localXMLTag2 == null) {
      throw new AuthException(str1, 1);
    }
    this.jdField_try = new Properties();
    ArrayList localArrayList = localXMLTag2.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      XMLTag localXMLTag3 = (XMLTag)localIterator.next();
      XMLTag localXMLTag4 = localXMLTag3.getContainedTag("NAME");
      XMLTag localXMLTag5 = localXMLTag3.getContainedTag("VALUE");
      if ((localXMLTag4 == null) || (localXMLTag5 == null)) {
        throw new AuthException(str1, 1);
      }
      this.jdField_try.setProperty(localXMLTag4.getTagContent(), localXMLTag5.getTagContent());
    }
  }
  
  public ArrayList getSecrets(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws AuthException
  {
    String str1 = "AuthenticationService.getSecrets";
    String str2 = (String)paramHashMap2.get("UserType");
    if (str2 == null) {
      throw new AuthException(str1, 5);
    }
    ArrayList localArrayList = new ArrayList(1);
    if (str2.equals("Consumer")) {
      localArrayList.add("efs/multilang/jsp/secretgrafx/sample1.jpg");
    } else if (str2.equals("Business")) {
      localArrayList.add("cb/multilang/jsp/secretgrafx/sample1.jpg");
    } else if (str2.equals("BankEmployee")) {
      localArrayList.add("bc/multilang/jsp/secretgrafx/sample1.jpg");
    }
    return localArrayList;
  }
  
  public Credentials getCredentialRequests(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws AuthException
  {
    String str = "AuthenticationService.getCredentialRequests";
    if (this.jdField_try == null) {
      throw new AuthException(str, 2);
    }
    int i = 1;
    if (paramHashMap1 != null)
    {
      localObject1 = (Integer)paramHashMap1.get("Operation");
      if (localObject1 != null) {
        i = ((Integer)localObject1).intValue();
      }
    }
    Object localObject1 = new Credentials();
    boolean bool1 = false;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    if (paramSecureUser.getUserType() == 2)
    {
      try
      {
        BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
        localBankEmployee.setUserName(paramSecureUser.getUserName());
        if (paramSecureUser.getProfileID() == 0) {
          bool1 = BankEmployeeAdapter.getInfoForAuditing(localBankEmployee, paramHashMap2);
        } else {
          bool1 = true;
        }
        if (bool1) {
          paramSecureUser.setProfileID(localBankEmployee.getId());
        }
      }
      catch (ProfileException localProfileException1)
      {
        bool1 = false;
      }
    }
    else if ((paramSecureUser.getUserType() == 1) && (paramSecureUser.getAgent() == null))
    {
      localObject2 = (String)paramHashMap2.get("UserType");
      if (localObject2 == null) {
        throw new AuthException(str, 5);
      }
      EntitlementCachedAdapter localEntitlementCachedAdapter = (EntitlementCachedAdapter)paramHashMap2.get("EntCachedAdapter");
      if (localEntitlementCachedAdapter == null) {
        throw new AuthException(str, 5);
      }
      localObject3 = null;
      try
      {
        if (paramSecureUser.getProfileID() == 0) {
          bool1 = CustomerAdapter.getInfoForAuditing(paramSecureUser, paramHashMap2);
        } else {
          bool1 = true;
        }
        if (bool1) {
          try
          {
            localObject3 = CustomerAdapter.getUser(paramSecureUser);
          }
          catch (ProfileException localProfileException2)
          {
            bool1 = false;
          }
        }
      }
      catch (ProfileException localProfileException3)
      {
        bool1 = false;
      }
      localObject4 = null;
      if (!bool1)
      {
        EntitlementGroups localEntitlementGroups = null;
        try
        {
          if (((String)localObject2).equals("Consumer")) {
            localEntitlementGroups = a(paramSecureUser, "Consumers", paramHashMap2);
          } else {
            localEntitlementGroups = a(paramSecureUser, "Business", paramHashMap2);
          }
        }
        catch (EntitlementException localEntitlementException2)
        {
          throw new AuthException(str, 10);
        }
        Random localRandom = new Random(paramSecureUser.getUserName().hashCode());
        localObject4 = (EntitlementGroup)localEntitlementGroups.get(localRandom.nextInt(localEntitlementGroups.size()));
      }
      else
      {
        try
        {
          a(paramSecureUser, (User)localObject3, paramHashMap2);
          localObject4 = localEntitlementCachedAdapter.getEntitlementGroup(((User)localObject3).getServicesPackageIdValue());
        }
        catch (EntitlementException localEntitlementException1)
        {
          throw new AuthException(str, 10);
        }
      }
      boolean bool2 = false;
      boolean bool3 = false;
      boolean bool4 = false;
      ServicesPackage localServicesPackage = new ServicesPackage();
      localServicesPackage.setEntitlementGroup((EntitlementGroup)localObject4);
      if (i == 1)
      {
        bool2 = localServicesPackage.getAuthLoginToken();
        bool3 = localServicesPackage.getAuthLoginScratch();
        bool4 = localServicesPackage.getAuthLoginChallenge();
      }
      else if (i == 2)
      {
        bool2 = localServicesPackage.getAuthTransToken();
        bool3 = localServicesPackage.getAuthTransScratch();
        bool4 = localServicesPackage.getAuthTransChallenge();
      }
      else if (i == 3)
      {
        bool2 = localServicesPackage.getAuthPrivToken();
        bool3 = localServicesPackage.getAuthPrivScratch();
        bool4 = localServicesPackage.getAuthPrivChallenge();
      }
      Credential localCredential;
      if (bool2)
      {
        localCredential = null;
        try
        {
          localCredential = getTokenCardCredential(paramSecureUser, paramHashMap2);
        }
        catch (AuthException localAuthException1) {}
        if (localCredential != null) {
          ((Credentials)localObject1).add(localCredential);
        }
      }
      if (bool3)
      {
        localCredential = null;
        try
        {
          localCredential = getScratchCardCredential(paramSecureUser, paramHashMap2);
        }
        catch (AuthException localAuthException2) {}
        if (localCredential != null) {
          ((Credentials)localObject1).add(localCredential);
        }
      }
      if (bool4)
      {
        localCredential = null;
        try
        {
          localCredential = getChallengeCredential(paramSecureUser, paramHashMap2);
        }
        catch (AuthException localAuthException3) {}
        if (localCredential != null) {
          ((Credentials)localObject1).add(localCredential);
        }
      }
    }
    if ((localObject1 != null) && (((Credentials)localObject1).size() != 0))
    {
      localObject2 = new LocalizableList();
      for (int j = 0; j < ((Credentials)localObject1).size(); j++)
      {
        localObject3 = (Credential)((Credentials)localObject1).get(j);
        localObject4 = new LocalizableString("com.ffusion.util.logging.audit.authentication", "AuditMessage_1." + ((Credential)localObject3).getType(), null);
        ((LocalizableList)localObject2).add(localObject4);
      }
      LocalizableString localLocalizableString = null;
      if (paramSecureUser.getUserType() == 2)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.authentication", "AuditMessage_3", new Object[] { paramSecureUser.getUserName(), localObject2 });
        a(paramSecureUser, 1811, localLocalizableString);
      }
      else
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.authentication", "AuditMessage_2", new Object[] { paramSecureUser.getUserName(), localObject2 });
        a(paramSecureUser, 3207, localLocalizableString);
      }
    }
    return localObject1;
  }
  
  public void validateCredentials(SecureUser paramSecureUser, Credentials paramCredentials, HashMap paramHashMap)
    throws AuthException
  {
    String str = "AuthenticationService.validateCredentials";
    try
    {
      boolean bool1 = false;
      boolean bool2 = true;
      if (paramSecureUser.getUserType() == 2)
      {
        localObject1 = new BankEmployee(paramSecureUser.getLocale());
        ((BankEmployee)localObject1).setUserName(paramSecureUser.getUserName());
        bool1 = BankEmployeeAdapter.getInfoForAuditing((BankEmployee)localObject1, paramHashMap);
        paramSecureUser.setProfileID(((BankEmployee)localObject1).getId());
        if (bool1)
        {
          localObject1 = BankEmployeeAdapter.getBankEmployeeById((BankEmployee)localObject1);
          bool2 = ((BankEmployee)localObject1).getStatus().equals("0");
        }
      }
      else
      {
        bool1 = CustomerAdapter.getInfoForAuditing(paramSecureUser, paramHashMap);
        if (bool1)
        {
          localObject1 = new User();
          try
          {
            localObject1 = CustomerAdapter.getUserById(paramSecureUser.getProfileID());
          }
          catch (ProfileException localProfileException2)
          {
            bool1 = false;
          }
          if (bool1) {
            bool2 = ((User)localObject1).getAccountStatus().equals(String.valueOf(1));
          }
        }
      }
      if ((!bool1) || (!bool2)) {
        throw new AuthException(str, 8);
      }
    }
    catch (ProfileException localProfileException1)
    {
      throw new AuthException(str, 8, localProfileException1);
    }
    int i = 0;
    LocalizableList localLocalizableList = new LocalizableList();
    if (paramCredentials == null) {
      return;
    }
    Object localObject1 = paramCredentials.iterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Credential)((Iterator)localObject1).next();
      try
      {
        if (((Credential)localObject2).getType() == 3) {
          validateChallengeCredential(paramSecureUser, (Credential)localObject2, paramHashMap);
        } else if (((Credential)localObject2).getType() == 2) {
          validateScratchCardCredential(paramSecureUser, (Credential)localObject2, paramHashMap);
        } else if (((Credential)localObject2).getType() == 1) {
          validateTokenCardCredential(paramSecureUser, (Credential)localObject2, paramHashMap);
        }
      }
      catch (AuthException localAuthException)
      {
        i = 1;
        localLocalizableList.add(new LocalizableString("com.ffusion.util.logging.audit.authentication", "AuditMessage_1." + ((Credential)localObject2).getType(), null));
      }
    }
    if (i != 0)
    {
      localObject2 = new LocalizableString("com.ffusion.util.logging.audit.authentication", "AuditMessage_4", new Object[] { localLocalizableList });
      if (paramSecureUser.getUserType() == 2) {
        a(paramSecureUser, 1808, (ILocalizable)localObject2);
      } else {
        a(paramSecureUser, 3202, (ILocalizable)localObject2);
      }
      throw new AuthException(str, 7);
    }
  }
  
  public Credential getScratchCardCredential(SecureUser paramSecureUser, HashMap paramHashMap)
    throws AuthException
  {
    String str1 = "AuthenticationService.getScratchCardCredential";
    if (this.jdField_try == null) {
      throw new AuthException(str1, 2);
    }
    String str2 = this.jdField_try.getProperty("Scratch Card Rows");
    String str3 = this.jdField_try.getProperty("Scratch Card Columns");
    if ((str2 == null) || (str3 == null)) {
      throw new AuthException(str1, 3);
    }
    Credential localCredential = new Credential(2);
    int i;
    int j;
    try
    {
      i = Integer.parseInt(str2);
      j = Integer.parseInt(str3);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new AuthException(str1, 4);
    }
    Random localRandom = new Random(System.currentTimeMillis());
    int k = localRandom.nextInt(i);
    k++;
    int m = localRandom.nextInt(j);
    char c = (char)(m + 65);
    String str4 = "" + m + "" + c;
    localCredential.setRequest(str4);
    String str5 = ResourceUtil.getString("SCRATCH_QUESTION", "com.ffusion.beans.authentication.resources", paramSecureUser.getLocale());
    if ((str5 == null) || (str5.length() == 0)) {
      throw new AuthException(str1, 9);
    }
    MessageFormat localMessageFormat = new MessageFormat(str5, paramSecureUser.getLocale());
    str5 = localMessageFormat.format(new String[] { "" + k, "" + c });
    localCredential.setLocalizedRequest(str5);
    return localCredential;
  }
  
  public Credential getTokenCardCredential(SecureUser paramSecureUser, HashMap paramHashMap)
    throws AuthException
  {
    String str = "AuthenticationService.getTokenCardCredential";
    if (this.jdField_try == null) {
      throw new AuthException(str, 2);
    }
    Credential localCredential = new Credential(1);
    localCredential.setRequest(null);
    localCredential.setLocalizedRequest(null);
    return localCredential;
  }
  
  public Credential getChallengeCredential(SecureUser paramSecureUser, HashMap paramHashMap)
    throws AuthException
  {
    String str1 = "AuthenticationService.getChallengeCredential";
    if (this.jdField_try == null) {
      throw new AuthException(str1, 2);
    }
    Credential localCredential = null;
    if (paramSecureUser.getUserType() == 1)
    {
      User localUser = null;
      boolean bool = false;
      try
      {
        bool = CustomerAdapter.getInfoForAuditing(paramSecureUser, paramHashMap);
        if (bool) {
          try
          {
            localUser = CustomerAdapter.getUser(paramSecureUser);
          }
          catch (ProfileException localProfileException1)
          {
            bool = false;
          }
        }
      }
      catch (ProfileException localProfileException2)
      {
        bool = false;
      }
      if (bool)
      {
        localCredential = new Credential(3);
        Random localRandom = new Random(System.currentTimeMillis());
        int i = localRandom.nextInt(2);
        i++;
        localCredential.setRequest("" + i);
        String str2 = null;
        if (i == 1) {
          str2 = localUser.getPasswordClue();
        } else if (i == 2) {
          str2 = localUser.getPasswordClue2();
        }
        if ((str2 == null) || (str2.trim().length() == 0)) {
          return null;
        }
        localCredential.setLocalizedRequest(str2);
      }
      else
      {
        localCredential = a(paramSecureUser);
      }
    }
    else
    {
      localCredential = null;
    }
    return localCredential;
  }
  
  public void validateScratchCardCredential(SecureUser paramSecureUser, Credential paramCredential, HashMap paramHashMap)
    throws AuthException
  {
    String str = "AuthenticationService.validateScratchCardCredential";
    if (paramCredential.getType() != 2) {
      throw new AuthException(str, 6);
    }
    if (paramSecureUser.getUserType() == 1)
    {
      User localUser = null;
      boolean bool2 = false;
      try
      {
        bool2 = CustomerAdapter.getInfoForAuditing(paramSecureUser, paramHashMap);
        if (bool2) {
          try
          {
            localUser = CustomerAdapter.getUser(paramSecureUser);
          }
          catch (ProfileException localProfileException2)
          {
            bool2 = false;
          }
        }
      }
      catch (ProfileException localProfileException3)
      {
        bool2 = false;
      }
      if (bool2)
      {
        if ((paramCredential.getResponse() == null) || (paramCredential.getResponse().trim().length() == 0)) {
          throw new AuthException(str, 7);
        }
        return;
      }
      throw new AuthException(str, 8);
    }
    if (paramSecureUser.getUserType() == 2)
    {
      boolean bool1 = false;
      try
      {
        BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
        localBankEmployee.setUserName(paramSecureUser.getUserName());
        if (paramSecureUser.getProfileID() == 0) {
          bool1 = BankEmployeeAdapter.getInfoForAuditing(localBankEmployee, paramHashMap);
        } else {
          bool1 = true;
        }
        if (bool1) {
          paramSecureUser.setProfileID(localBankEmployee.getId());
        }
      }
      catch (ProfileException localProfileException1)
      {
        bool1 = false;
      }
      if (bool1)
      {
        if ((paramCredential.getResponse() == null) || (paramCredential.getResponse().trim().length() == 0)) {
          throw new AuthException(str, 7);
        }
        return;
      }
      throw new AuthException(str, 8);
    }
    throw new AuthException(str, 8);
  }
  
  public void validateTokenCardCredential(SecureUser paramSecureUser, Credential paramCredential, HashMap paramHashMap)
    throws AuthException
  {
    String str = "AuthenticationService.validateTokenCardCredential";
    if (paramCredential.getType() != 1) {
      throw new AuthException(str, 6);
    }
    if (paramSecureUser.getUserType() == 1)
    {
      User localUser = null;
      boolean bool2 = false;
      try
      {
        bool2 = CustomerAdapter.getInfoForAuditing(paramSecureUser, paramHashMap);
        if (bool2) {
          try
          {
            localUser = CustomerAdapter.getUser(paramSecureUser);
          }
          catch (ProfileException localProfileException2)
          {
            bool2 = false;
          }
        }
      }
      catch (ProfileException localProfileException3)
      {
        bool2 = false;
      }
      if (bool2)
      {
        if ((paramCredential.getResponse() == null) || (paramCredential.getResponse().trim().length() == 0)) {
          throw new AuthException(str, 7);
        }
        return;
      }
      throw new AuthException(str, 8);
    }
    if (paramSecureUser.getUserType() == 2)
    {
      boolean bool1 = false;
      try
      {
        BankEmployee localBankEmployee = new BankEmployee(paramSecureUser.getLocale());
        localBankEmployee.setUserName(paramSecureUser.getUserName());
        if (paramSecureUser.getProfileID() == 0) {
          bool1 = BankEmployeeAdapter.getInfoForAuditing(localBankEmployee, paramHashMap);
        } else {
          bool1 = true;
        }
        if (bool1) {
          paramSecureUser.setProfileID(localBankEmployee.getId());
        }
      }
      catch (ProfileException localProfileException1)
      {
        bool1 = false;
      }
      if (bool1)
      {
        if ((paramCredential.getResponse() == null) || (paramCredential.getResponse().trim().length() == 0)) {
          throw new AuthException(str, 7);
        }
        return;
      }
      throw new AuthException(str, 8);
    }
    throw new AuthException(str, 8);
  }
  
  public void validateChallengeCredential(SecureUser paramSecureUser, Credential paramCredential, HashMap paramHashMap)
    throws AuthException
  {
    String str1 = "AuthenticationService.validateChallengeCredential";
    if (paramCredential.getType() != 3) {
      throw new AuthException(str1, 6);
    }
    if (paramSecureUser.getUserType() == 1)
    {
      User localUser = null;
      boolean bool = false;
      try
      {
        bool = CustomerAdapter.getInfoForAuditing(paramSecureUser, paramHashMap);
        if (bool) {
          try
          {
            localUser = CustomerAdapter.getUser(paramSecureUser);
          }
          catch (ProfileException localProfileException1)
          {
            bool = false;
          }
        }
      }
      catch (ProfileException localProfileException2)
      {
        bool = false;
      }
      if (bool)
      {
        if ((paramCredential.getResponse() == null) || (paramCredential.getResponse().trim().length() == 0)) {
          throw new AuthException(str1, 7);
        }
        String str2 = paramCredential.getResponse();
        String str3 = null;
        if (paramCredential.getRequest().equals("1")) {
          str3 = localUser.getPasswordReminder();
        } else if (paramCredential.getRequest().equals("2")) {
          str3 = localUser.getPasswordReminder2();
        } else {
          throw new AuthException(str1, 7);
        }
        if ((str3 == null) || (str3.length() == 0)) {
          throw new AuthException(str1, 7);
        }
        str2 = str2.trim();
        str3 = str3.trim();
        if (!str2.equalsIgnoreCase(str3)) {
          throw new AuthException(str1, 7);
        }
      }
      else
      {
        throw new AuthException(str1, 8);
      }
    }
    else
    {
      throw new AuthException(str1, 8);
    }
  }
  
  private void a(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws EntitlementException
  {
    EntitlementCachedAdapter localEntitlementCachedAdapter = (EntitlementCachedAdapter)paramHashMap.get("EntCachedAdapter");
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    localEntitlementGroupMember = localEntitlementCachedAdapter.getMember(localEntitlementGroupMember);
    int i = localEntitlementGroupMember.getEntitlementGroupId();
    paramSecureUser.setEntitlementID(i);
    while (i != 0)
    {
      EntitlementGroup localEntitlementGroup = localEntitlementCachedAdapter.getEntitlementGroup(i);
      if (localEntitlementGroup == null) {
        break;
      }
      if (localEntitlementGroup.getEntGroupType().equals("ServicesPackage"))
      {
        paramUser.setServicesPackageId(i);
        break;
      }
      i = localEntitlementGroup.getParentId();
    }
  }
  
  private EntitlementGroups a(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws EntitlementException
  {
    EntitlementCachedAdapter localEntitlementCachedAdapter = (EntitlementCachedAdapter)paramHashMap.get("EntCachedAdapter");
    EntitlementGroups localEntitlementGroups1 = new EntitlementGroups();
    EntitlementGroup localEntitlementGroup1 = localEntitlementCachedAdapter.getEntitlementGroupByNameAndSvcBureau(paramString, "UserType", paramSecureUser.getBankIDValue());
    EntitlementGroups localEntitlementGroups2 = localEntitlementCachedAdapter.getChildrenByGroupType(localEntitlementGroup1.getGroupId(), "MarketSegment");
    Iterator localIterator = localEntitlementGroups2.iterator();
    while (localIterator.hasNext())
    {
      EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator.next();
      EntitlementGroups localEntitlementGroups3 = localEntitlementCachedAdapter.getChildrenByGroupType(localEntitlementGroup2.getGroupId(), "ServicesPackage");
      localEntitlementGroups1.addAll(localEntitlementGroups3);
    }
    return localEntitlementGroups1;
  }
  
  private void a(SecureUser paramSecureUser, int paramInt, ILocalizable paramILocalizable)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = paramSecureUser.getPrimaryUserID();
    if (paramSecureUser.getUserType() == 2)
    {
      str1 = "";
      str2 = String.valueOf(paramSecureUser.getProfileID());
      str3 = String.valueOf(paramSecureUser.getUserType());
    }
    else
    {
      str1 = String.valueOf(paramSecureUser.getProfileID());
      if (paramSecureUser.getAgent() != null) {
        if (paramSecureUser.getAgent().getProfileID() > 0)
        {
          str2 = String.valueOf(paramSecureUser.getAgent().getProfileID());
          str3 = String.valueOf(paramSecureUser.getAgent().getUserType());
        }
        else
        {
          str2 = paramSecureUser.getAgent().getUserName();
        }
      }
    }
    String str4 = TrackingIDGenerator.GetNextID();
    AuditLog.log(str1, i, str2, str3, paramILocalizable, str4, paramInt, paramSecureUser.getBusinessID());
  }
  
  private Credential a(SecureUser paramSecureUser)
    throws AuthException
  {
    String str1 = ResourceUtil.getString("PWD_QUESTION_LIST", "com.ffusion.beans.user.resources", paramSecureUser.getLocale());
    int i = -1;
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
    while (localStringTokenizer.hasMoreTokens()) {
      try
      {
        int j = Integer.parseInt(localStringTokenizer.nextToken());
        if (j > i) {
          i = j;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new AuthException(9);
      }
    }
    int k = i + 1;
    Random localRandom = new Random(paramSecureUser.getUserName().hashCode());
    int m = localRandom.nextInt(k);
    int n = localRandom.nextInt(k);
    localRandom = new Random(System.currentTimeMillis());
    int i1 = localRandom.nextInt() % 2 == 0 ? m : n;
    String str2 = ResourceUtil.getString("PWD_QUESTION." + i1, "com.ffusion.beans.user.resources", paramSecureUser.getLocale());
    Credential localCredential = new Credential(3);
    localCredential.setLocalizedRequest(str2);
    localCredential.setRequest("" + i1);
    return localCredential;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.authentication.AuthenticationService
 * JD-Core Version:    0.7.0.1
 */