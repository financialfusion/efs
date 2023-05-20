package com.ffusion.services.demo;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.CustomTag;
import com.ffusion.beans.user.CustomTagValuesList;
import com.ffusion.beans.user.CustomTags;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.UserAdmin6;
import com.ffusion.util.XMLTag;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class UserAdmin
  implements UserAdmin6
{
  private Users jdField_do;
  private Histories jdField_if;
  private Accounts a;
  
  public void initialize(String paramString)
    throws ProfileException
  {
    int i = 0;
    try
    {
      Locale localLocale = new Locale("en", "US");
      String str = new String(Service.getXMLString(this, paramString));
      if (str.length() == 0) {
        throw new ProfileException(3507);
      }
      XMLTag localXMLTag1 = new XMLTag();
      localXMLTag1.build(str);
      if (localXMLTag1 != null)
      {
        this.jdField_do = new Users(localLocale);
        XMLTag localXMLTag2;
        if ((localXMLTag2 = localXMLTag1.getContainedTag("USER_GROUP")) != null) {
          a(localXMLTag2, this.jdField_do);
        }
        this.jdField_if = new Histories(localLocale);
        if ((localXMLTag2 = localXMLTag1.getContainedTag("HISTORY_GROUP")) != null) {
          a(localXMLTag2, this.jdField_if);
        }
        this.a = new Accounts(localLocale);
        if ((localXMLTag2 = localXMLTag1.getContainedTag("ACCOUNT_GROUP")) != null) {
          jdField_if(localXMLTag2, this.a);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      throw new ProfileException(3506);
    }
  }
  
  public SecureUser authenticate(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    if (this.jdField_do == null) {
      throw new ProfileException(3502);
    }
    User localUser = this.jdField_do.getByUserName(paramSecureUser.getUserName());
    if (localUser == null) {
      throw new ProfileException(3502);
    }
    paramSecureUser.setProfileID(localUser.getId());
    return paramSecureUser;
  }
  
  public boolean userExists(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    User localUser = this.jdField_do.getByUserName(paramSecureUser.getUserName());
    return localUser != null;
  }
  
  public User addUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    if (this.jdField_do != null)
    {
      paramUser.setId(String.valueOf((int)(Math.random() * 100000.0D)));
      this.jdField_do.add(paramUser);
    }
    return paramUser;
  }
  
  public User getUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    if (this.jdField_do == null) {
      throw new ProfileException(3502);
    }
    User localUser = this.jdField_do.getByUserName(paramUser.getUserName());
    if (localUser == null) {
      throw new ProfileException(3502);
    }
    return localUser;
  }
  
  public User getUserById(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    if (this.jdField_do == null) {
      throw new ProfileException(3502);
    }
    User localUser = this.jdField_do.getByID(String.valueOf(paramUser.getIdValue()));
    if (localUser == null) {
      throw new ProfileException(3502);
    }
    return localUser;
  }
  
  public Users getUsers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    Users localUsers = new Users(Locale.getDefault());
    if (localUsers == null) {
      throw new ProfileException(3502);
    }
    Iterator localIterator = localUsers.iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      localUsers.add(localUser);
    }
    return localUsers;
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
  
  public Users getFilteredUsers(SecureUser paramSecureUser, User paramUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public User modifyPasswordStatus(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    User localUser = null;
    if (this.jdField_do != null)
    {
      localUser = this.jdField_do.getByID(paramUser.getId());
      if (localUser != null) {
        localUser.put("PASSWORD_STATUS", paramUser.get("PASSWORD_STATUS"));
      }
    }
    return localUser;
  }
  
  public Users getUserList(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public User modifyUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    if ((this.jdField_do != null) && (paramUser != null))
    {
      this.jdField_do.removeByID(paramUser.getId());
      this.jdField_do.add(paramUser);
    }
    return paramUser;
  }
  
  public User modifyUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    if (paramUser != null) {
      paramUser.setPassword(paramString1);
    }
    return paramUser;
  }
  
  public User resetUserPassword(SecureUser paramSecureUser, User paramUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    if (paramUser != null) {
      paramUser.setPassword(paramString2);
    }
    return paramUser;
  }
  
  public Account addAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    if (this.a != null) {
      this.a.add(paramAccount);
    }
    return paramAccount;
  }
  
  public Accounts getAccounts(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    Accounts localAccounts = new Accounts();
    Object localObject;
    if ((paramAccount != null) && (this.a != null))
    {
      localObject = this.a.getByID(paramAccount.getID());
      localAccounts.add(localObject);
    }
    else if ((paramAccount == null) && (this.a != null))
    {
      localObject = this.a.iterator();
      while (((Iterator)localObject).hasNext())
      {
        Account localAccount = (Account)((Iterator)localObject).next();
        localAccounts.add(localAccount);
      }
    }
    return localAccounts;
  }
  
  public Account modifyAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    if (this.a != null)
    {
      this.a.removeByID(paramAccount.getID());
      this.a.add(paramAccount);
    }
    return paramAccount;
  }
  
  public Account deleteAccount(SecureUser paramSecureUser, Account paramAccount, HashMap paramHashMap)
    throws ProfileException
  {
    if (this.a != null) {
      this.a.removeByID(paramAccount.getID());
    }
    return paramAccount;
  }
  
  public void addAdditionalData(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {}
  
  public String getAdditionalData(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws ProfileException
  {
    if (this.jdField_if != null)
    {
      Iterator localIterator = paramHistories.iterator();
      if (localIterator != null) {
        while (localIterator.hasNext())
        {
          History localHistory = (History)localIterator.next();
          this.jdField_if.add(localHistory);
        }
      }
    }
    return paramHistories;
  }
  
  public Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws ProfileException
  {
    Histories localHistories = new Histories(Locale.getDefault());
    Iterator localIterator = this.jdField_if.iterator();
    if (localIterator != null) {
      while (localIterator.hasNext())
      {
        History localHistory = (History)localIterator.next();
        localHistories.add(localHistory);
      }
    }
    return localHistories;
  }
  
  public Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public CustomTags addCustomTags(SecureUser paramSecureUser, CustomTags paramCustomTags, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public CustomTags getCustomTags(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public CustomTag modifyCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public CustomTag deleteCustomTag(SecureUser paramSecureUser, CustomTag paramCustomTag, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public CustomTagValuesList getCustomTagValuesList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public ArrayList getExtraFields(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public User deleteUser(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    if ((this.jdField_do != null) && (paramUser != null)) {
      this.jdField_do.removeByID(paramUser.getId());
    }
    return paramUser;
  }
  
  public BusinessEmployee addBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BusinessEmployee modifyBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BusinessEmployees getBusinessEmployees(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Users getConsumers(SecureUser paramSecureUser, User paramUser, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BusinessEmployee deleteBusinessEmployee(SecureUser paramSecureUser, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public boolean businessIdExists(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    return false;
  }
  
  public BusinessEmployees getFilteredBusinessEmployees(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public String getFilteredBusinessEmployeesCount(SecureUser paramSecureUser, Business paramBusiness, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BusinessEmployees getBusinessEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  private void a(XMLTag paramXMLTag, Users paramUsers)
  {
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        if (localXMLTag.getTagName().equalsIgnoreCase("USER"))
        {
          User localUser = paramUsers.create();
          if (a(localXMLTag, localUser)) {
            paramUsers.add(localUser);
          }
        }
      }
    }
  }
  
  private boolean a(XMLTag paramXMLTag, User paramUser)
  {
    ArrayList localArrayList = null;
    boolean bool = false;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    bool = true;
    while (localIterator.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("DIRECTORY_ID"))
      {
        paramUser.setId(str2);
      }
      else if (str1.equals("CUST_ID"))
      {
        paramUser.setCustId(str2);
      }
      else if (str1.equals("FIRST_NAME"))
      {
        paramUser.setFirstName(str2);
      }
      else if (str1.equals("MIDDLE_NAME"))
      {
        paramUser.setMiddleName(str2);
      }
      else if (str1.equals("LAST_NAME"))
      {
        paramUser.setLastName(str2);
      }
      else if (str1.equals("USER_NAME"))
      {
        paramUser.setUserName(str2);
      }
      else if (str1.equals("ADDRESS1"))
      {
        paramUser.setStreet(str2);
      }
      else if (str1.equals("ADDRESS2"))
      {
        paramUser.setStreet2(str2);
      }
      else if (str1.equals("CITY"))
      {
        paramUser.setCity(str2);
      }
      else if (str1.equals("STATE"))
      {
        paramUser.setState(str2);
      }
      else if (str1.equals("ZIP"))
      {
        paramUser.setZipCode(str2);
      }
      else if (str1.equals("COUNTRY"))
      {
        paramUser.setCountry(str2);
      }
      else if (str1.equals("PASSWORD"))
      {
        paramUser.setPassword(str2);
      }
      else if (str1.equals("PWD_REMINDER"))
      {
        paramUser.setPasswordReminder(str2);
      }
      else if (str1.equals("HOME_PHONE"))
      {
        paramUser.setPhone(str2);
      }
      else if (str1.equals("WORK_PHONE"))
      {
        paramUser.setPhone2(str2);
      }
      else if (str1.equals("EMAIL_ADDRESS"))
      {
        paramUser.setEmail(str2);
      }
      else if (str1.equals("GENDER"))
      {
        paramUser.setGender(str2);
      }
      else if (str1.equals("PERSONAL_BANKER"))
      {
        paramUser.setPersonalBanker(str2);
      }
      else if (str1.equals("GREETING_TYPE"))
      {
        paramUser.setGreetingType(str2);
      }
      else if (str1.equals("GREETING"))
      {
        paramUser.setGreeting(str2);
      }
      else if (str1.equals("TITLE"))
      {
        paramUser.setTitle(str2);
      }
      else if (str1.equals("SSN"))
      {
        paramUser.setSSN(str2);
      }
      else if (str1.equals("ACCOUNT_STATUS"))
      {
        paramUser.setAccountStatus(str2);
      }
      else if (str1.equals("ACCESS_MODE"))
      {
        paramUser.setAccessMode(str2);
      }
      else if (str1.equals("SERVICE_LEVEL"))
      {
        paramUser.setServiceLevel(str2);
      }
      else if (str1.equals("TIMEOUT"))
      {
        paramUser.setTimeout(str2);
      }
      else if ((str1.equals("LAST_ACTIVE_DATE")) || (str1.equals("DIRECTORY_DATE")) || (str1.equals("CREATE_DATE")) || (str1.equals("BIRTH_DATE")))
      {
        HashMap localHashMap = paramUser.getHash();
        localHashMap.put(str1, XMLUtil.parseNewDateTime(str2));
      }
      else
      {
        paramUser.setHashValue(str1, str2);
      }
    }
    return bool;
  }
  
  private void jdField_if(XMLTag paramXMLTag, Accounts paramAccounts)
  {
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        if (localXMLTag.getTagName().equalsIgnoreCase("ACCOUNT")) {
          a(localXMLTag, paramAccounts);
        }
      }
    }
  }
  
  private void a(XMLTag paramXMLTag, Accounts paramAccounts)
  {
    if (paramXMLTag.getTagName().equalsIgnoreCase("ACCOUNT"))
    {
      ArrayList localArrayList = null;
      XMLTag localXMLTag = paramXMLTag.getContainedTag("ACCOUNT_ID");
      if (localXMLTag != null)
      {
        Account localAccount = paramAccounts.create(localXMLTag.getTagContent());
        if ((localArrayList = paramXMLTag.getContainedTagList()) != null)
        {
          Iterator localIterator = localArrayList.iterator();
          while (localIterator.hasNext())
          {
            paramXMLTag = (XMLTag)localIterator.next();
            String str1 = paramXMLTag.getTagName();
            String str2 = paramXMLTag.getTagContent();
            if (str1.equals("NICKNAME")) {
              localAccount.setNickName(str2);
            } else {
              localAccount.setHashValue(str1, str2);
            }
          }
        }
      }
    }
  }
  
  private void a(XMLTag paramXMLTag, Histories paramHistories)
  {
    if (paramXMLTag.getContainedTagList().size() > 0)
    {
      Iterator localIterator1 = paramXMLTag.getContainedTagList().iterator();
      while (localIterator1.hasNext())
      {
        paramXMLTag = (XMLTag)localIterator1.next();
        if ((paramXMLTag.getTagName().equalsIgnoreCase("HISTORY")) && (paramXMLTag.getContainedTagList().size() != 0))
        {
          Iterator localIterator2 = paramXMLTag.getContainedTagList().iterator();
          while (localIterator2.hasNext())
          {
            paramXMLTag = (XMLTag)localIterator2.next();
            String str1 = paramXMLTag.getTagName();
            String str2 = paramXMLTag.getTagContent();
            History localHistory = this.jdField_if.add();
            if (str1.equals("DIRECTORY_ID")) {
              localHistory.setId(str2);
            } else if (str1.equals("MODIFIER_NAME")) {
              localHistory.setModifierName(str2);
            } else if (str1.equals("MODIFIER_ID")) {
              localHistory.setModifierId(str2);
            } else if (str1.equals("MODIFIER_TYPE")) {
              localHistory.setModifierType(Integer.valueOf(str2).intValue());
            } else if (str1.equals("COLUMN_CHANGED")) {
              localHistory.setDataChanged(str2);
            } else if (str1.equals("OLD_VALUE")) {
              localHistory.setOldValue(str2);
            } else if (str1.equals("NEW_VALUE")) {
              localHistory.setNewValue(str2);
            } else if (str1.equals("HISTORY_COMMENT")) {
              localHistory.setComment(str2);
            } else if (str1.equals("DATE_CHANGED")) {
              localHistory.setDateChanged(XMLUtil.parseNewDateTime(str2));
            }
          }
        }
      }
    }
  }
  
  public BusinessEmployees getFilteredBusinessEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, BusinessEmployee paramBusinessEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return CustomerAdapter.getFilteredBusinessEmployeesByIds(paramArrayList, paramBusinessEmployee);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.UserAdmin
 * JD-Core Version:    0.7.0.1
 */