package com.ffusion.services.demo;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployeeAssignment;
import com.ffusion.beans.bankemployee.BankEmployeeAssignments;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.BankEmployeeAdmin5;
import com.ffusion.util.XMLTag;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;

public class BankEmployeeAdmin
  implements BankEmployeeAdmin5
{
  private int jdField_if;
  protected String m_BankId;
  private BankEmployees a;
  private Users jdField_for;
  private Histories jdField_do;
  
  public int addBankEmployee(BankEmployee paramBankEmployee)
  {
    if (this.a != null)
    {
      Random localRandom = new Random();
      paramBankEmployee.setId(String.valueOf(localRandom.nextInt()));
      this.a.add(paramBankEmployee);
    }
    return 0;
  }
  
  public int getBankEmployee(BankEmployee paramBankEmployee)
  {
    if (this.a != null)
    {
      BankEmployee localBankEmployee = this.a.getByUserName(paramBankEmployee.getUserName());
      paramBankEmployee.set(localBankEmployee);
    }
    else
    {
      return 5004;
    }
    return 0;
  }
  
  public int getBankEmployees(BankEmployees paramBankEmployees, BankEmployee paramBankEmployee)
  {
    if (this.a != null)
    {
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
      {
        BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
        paramBankEmployees.add(localBankEmployee);
      }
    }
    else
    {
      return 5004;
    }
    return 0;
  }
  
  public int getFilteredBankEmployees(BankEmployees paramBankEmployees, BankEmployee paramBankEmployee)
  {
    Iterator localIterator = this.a.iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
      paramBankEmployees.add(localBankEmployee);
    }
    return 0;
  }
  
  public int getBankEmployeeList(BankEmployees paramBankEmployees, BankEmployee paramBankEmployee)
  {
    if (this.a != null)
    {
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
      {
        BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
        paramBankEmployees.add(localBankEmployee);
      }
    }
    else
    {
      return 5004;
    }
    return 0;
  }
  
  public int modifyBankEmployee(BankEmployee paramBankEmployee)
  {
    if (this.a != null)
    {
      BankEmployee localBankEmployee = this.a.getByID(paramBankEmployee.getId());
      if (localBankEmployee != null)
      {
        this.a.removeByID(paramBankEmployee.getId());
        this.a.add(paramBankEmployee);
      }
    }
    return 0;
  }
  
  public int deleteBankEmployee(BankEmployee paramBankEmployee)
  {
    if (this.a != null) {
      this.a.removeByID(paramBankEmployee.getId());
    }
    return 0;
  }
  
  public int getUserList(Users paramUsers, User paramUser)
  {
    if (this.jdField_for != null)
    {
      Iterator localIterator = this.jdField_for.iterator();
      while (localIterator.hasNext())
      {
        User localUser = (User)localIterator.next();
        paramUsers.add(localUser);
      }
    }
    else
    {
      return 5004;
    }
    return 0;
  }
  
  public int getUsers(Users paramUsers, User paramUser)
  {
    if (this.jdField_for != null)
    {
      Iterator localIterator = this.jdField_for.iterator();
      while (localIterator.hasNext())
      {
        User localUser = (User)localIterator.next();
        paramUsers.add(localUser);
      }
    }
    else
    {
      return 5004;
    }
    return 0;
  }
  
  public int getFilteredUsers(Users paramUsers, User paramUser, Account paramAccount)
  {
    String str1 = paramUser.getFirstName();
    String str2 = paramUser.getLastName();
    String str3 = paramUser.getUserName();
    String str4 = paramUser.getSSN();
    String str5 = paramUser.getCustId();
    String str6 = paramUser.getPhone();
    if (this.jdField_for != null)
    {
      Iterator localIterator = this.jdField_for.iterator();
      while (localIterator.hasNext())
      {
        User localUser = (User)localIterator.next();
        if (((str1 != null) && (str1.length() > 0) && (localUser.getFirstName().indexOf(str1) != -1)) || ((str2 != null) && (str2.length() > 0) && (localUser.getLastName().indexOf(str2) != -1)) || ((str3 != null) && (str3.length() > 0) && (localUser.getUserName().indexOf(str3) != -1)) || ((str4 != null) && (str4.length() > 0) && (localUser.getSSN().indexOf(str4) != -1)) || ((str5 != null) && (str5.length() > 0) && (localUser.getCustId().indexOf(str5) != -1)) || ((str6 != null) && (str6.length() > 0) && (localUser.getPhone().indexOf(str6) != -1))) {
          paramUsers.add(localUser);
        }
      }
    }
    return 0;
  }
  
  protected void updateHistoryListFromXML(XMLTag paramXMLTag, Histories paramHistories)
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
            History localHistory = this.jdField_do.add();
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
  
  public int getBusinesses(Businesses paramBusinesses, Business paramBusiness)
  {
    return 0;
  }
  
  public int getFilteredBusinesses(Businesses paramBusinesses, Business paramBusiness, Account paramAccount)
  {
    return 0;
  }
  
  public int getHistory(History paramHistory, Histories paramHistories, Calendar paramCalendar1, Calendar paramCalendar2)
  {
    if (this.jdField_do != null)
    {
      Iterator localIterator = this.jdField_do.iterator();
      while (localIterator.hasNext())
      {
        History localHistory = (History)localIterator.next();
        paramHistories.add(localHistory);
      }
    }
    return 0;
  }
  
  public int getBusinessList(Businesses paramBusinesses, Business paramBusiness)
  {
    return 0;
  }
  
  public int deleteBusiness(Business paramBusiness)
  {
    return 0;
  }
  
  public BankEmployee addBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployee getBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployees getBankEmployees(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployees getBankEmployeeList(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployee modifyBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployee deleteBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public void initialize(String paramString)
    throws ProfileException
  {
    int i = 0;
    try
    {
      if (paramString == null)
      {
        i = 5005;
      }
      else
      {
        Locale localLocale = new Locale("en", "US");
        String str = new String(Service.getXMLString(this, paramString));
        XMLTag localXMLTag1 = new XMLTag();
        localXMLTag1.build(str);
        if (localXMLTag1 != null)
        {
          this.jdField_for = new Users(localLocale);
          XMLTag localXMLTag2;
          if ((localXMLTag2 = localXMLTag1.getContainedTag("USER_GROUP")) != null) {
            updateUserListFromXML(localXMLTag2, this.jdField_for);
          }
          this.jdField_do = new Histories(localLocale);
          if ((localXMLTag2 = localXMLTag1.getContainedTag("HISTORY_GROUP")) != null) {
            updateHistoryListFromXML(localXMLTag2, this.jdField_do);
          }
          this.a = new BankEmployees(localLocale);
          if ((localXMLTag2 = localXMLTag1.getContainedTag("EMPLOYEE_GROUP")) != null) {
            updateEmployeeListFromXML(localXMLTag2, this.a);
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      System.out.println("Error initializing file" + localThrowable);
      i = 5006;
    }
  }
  
  protected void updateUserListFromXML(XMLTag paramXMLTag, Users paramUsers)
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
          if (updateUserFromXML(localXMLTag, localUser)) {
            paramUsers.add(localUser);
          }
        }
      }
    }
  }
  
  public void setDirectoryId(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  protected boolean updateUserFromXML(XMLTag paramXMLTag, User paramUser)
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
        setDirectoryId(Integer.valueOf(str2).intValue());
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
  
  public void setBankId(String paramString)
  {
    this.m_BankId = paramString;
  }
  
  public String getBankId()
  {
    return this.m_BankId;
  }
  
  protected void updateEmployeeListFromXML(XMLTag paramXMLTag, BankEmployees paramBankEmployees)
  {
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        if (localXMLTag.getTagName().equalsIgnoreCase("EMPLOYEE"))
        {
          BankEmployee localBankEmployee = paramBankEmployees.create();
          if (updateEmployeeFromXML(localXMLTag, localBankEmployee)) {
            paramBankEmployees.add(localBankEmployee);
          }
        }
      }
    }
  }
  
  protected boolean updateEmployeeFromXML(XMLTag paramXMLTag, BankEmployee paramBankEmployee)
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
      if (str1.equals("EMPLOYEE_ID"))
      {
        paramBankEmployee.setId(str2);
      }
      else if (str1.equals("BANK_EMPLOYEE_ID"))
      {
        paramBankEmployee.setEmployeeId(str2);
      }
      else if (str1.equals("FIRST_NAME"))
      {
        paramBankEmployee.setFirstName(str2);
      }
      else if (str1.equals("LAST_NAME"))
      {
        paramBankEmployee.setLastName(str2);
      }
      else if (str1.equals("USER_NAME"))
      {
        paramBankEmployee.setUserName(str2);
      }
      else if (str1.equals("USER_PASSWORD"))
      {
        paramBankEmployee.setPassword(str2);
      }
      else if (str1.equals("ADDRESS1"))
      {
        paramBankEmployee.setStreet(str2);
      }
      else if (str1.equals("ADDRESS2"))
      {
        paramBankEmployee.setStreet2(str2);
      }
      else if (str1.equals("CITY"))
      {
        paramBankEmployee.setCity(str2);
      }
      else if (str1.equals("STATE"))
      {
        paramBankEmployee.setState(str2);
      }
      else if (str1.equals("ZIP"))
      {
        paramBankEmployee.setZipCode(str2);
      }
      else if (str1.equals("HOME_PHONE"))
      {
        paramBankEmployee.setPhone(str2);
      }
      else if (str1.equals("EMAIL_ADDRESS"))
      {
        paramBankEmployee.setEmail(str2);
      }
      else if (str1.equals("EMPLOYEE_ADMIN_ACCESS"))
      {
        updateAdminAccess(paramBankEmployee, Integer.valueOf(str2).intValue());
      }
      else if (str1.equals("EMPLOYEE_STATUS"))
      {
        paramBankEmployee.setStatus(str2);
      }
      else if (str1.equals("SUPERVISOR"))
      {
        paramBankEmployee.setSupervisor(str2);
      }
      else if (str1.equals("EMPLOYEE_NOTIFY"))
      {
        paramBankEmployee.setNotify(str2);
      }
      else
      {
        paramBankEmployee.setHashValue(str1, str2);
        if ((str1.equals("BANK_ID")) && (getBankId() == null)) {
          setBankId(str2);
        }
      }
    }
    return bool;
  }
  
  protected void updateAdminAccess(BankEmployee paramBankEmployee, int paramInt)
  {
    ArrayList localArrayList = new ArrayList(5);
    if ((paramInt & 0x1) != 0) {
      localArrayList.add("ACCESS_ADMINISTRATOR");
    }
    if ((paramInt & 0x2) != 0) {
      localArrayList.add("ACCESS_SUPERVISOR");
    }
    if ((paramInt & 0x4) != 0) {
      localArrayList.add("ACCESS_CSR");
    }
    if ((paramInt & 0x8) != 0) {
      localArrayList.add("ACCESS_PERSONALBANKER");
    }
    paramBankEmployee.setAdminAccess(localArrayList);
  }
  
  public BankEmployee signonBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployeeAssignments getBankEmployeeAssignments(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployeeAssignment addBankEmployeeAssignment(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployeeAssignment deleteBankEmployeeAssignment(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public BankEmployee getBankEmployeeById(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
  
  public void redistributeWorkloads(SecureUser paramSecureUser, BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2, HashMap paramHashMap)
    throws ProfileException
  {}
  
  public BankEmployees getBankEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.BankEmployeeAdmin
 * JD-Core Version:    0.7.0.1
 */