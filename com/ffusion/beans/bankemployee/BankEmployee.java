package com.ffusion.beans.bankemployee;

import com.ffusion.beans.CollectionElement;
import com.ffusion.beans.Person;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.user.UserDefines;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.beans.util.UserUtil;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.efs.adapters.profile.BankEmployeeAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.affiliatebank.AffiliateBankAssignment;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableString;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class BankEmployee
  extends Person
  implements BankEmployeeDefines, UserDefines, Serializable, CollectionElement
{
  public static final String BANKEMPLOYEEINFO = "BANKEMPLOYEEINFO";
  public static final String ID = "ID";
  public static final String ADMINACCESS = "ADMINACCESS";
  public static final String STATUS = "STATUS";
  public static final String USERNAME = "USERNAME";
  public static final String PASSWORD = "PASSWORD";
  public static final String EMPLOYEEID = "EMPLOYEEID";
  public static final String SUPERVISOR = "SUPERVISOR";
  public static final String EMPLOYEE_NOTIFY = "EMPLOYEE_NOTIFY";
  public static final String CURRENT_ACCESS = "CURRENT_ACCESS";
  public static final String TRACKING_ID = "TRACKING_ID";
  public static final String REVIEW_REQUIRED = "REVIEW_REQUIRED";
  public static final String OBO_ENABLED = "OBO_ENABLED";
  public static final String APPROVAL_PROVIDER = "APPROVAL_PROVIDER";
  public static final String MSG_APPROVAL_PROVIDER = "MSG_APPROVAL_PROVIDER";
  public static final String AFFILIATE_BANK = "AFFILIATE_BANK";
  public static final String ON_BEHALF_OF = "ON_BEHALF_OF";
  public static final String DEFAULT_BANK = "DEFAULT_BANK";
  public static final String ASSIGNED_BANK = "ASSIGNED_BANK";
  public static final String JOB_TYPE = "JOB_TYPE";
  public static final String SORTABLE_FULL_NAME = "SORTABLE_FULL_NAME";
  public static final String SORTABLE_FULL_NAME_WITH_LOGIN_ID = "SORTABLE_FULL_NAME_WITH_LOGIN_ID";
  public static final String BANK_ADDED = "BANK_ADDED";
  public static final String BANK_REMOVED = "BANK_REMOVED";
  public static final String BEAN_NAME = BankEmployee.class.getName();
  protected String id;
  protected ArrayList adminAccess;
  protected String currentAccess = "";
  protected String status;
  protected String userName;
  protected String password;
  protected String employeeId;
  protected String supervisor;
  protected String notify;
  protected String trackingID;
  protected String reviewRequired;
  protected String oboEnabled;
  protected String approvalProvider;
  protected String msgApprovalProvider;
  protected String defaultAffiliateBankId;
  protected ArrayList affiliateBankIds;
  protected int jobTypeId;
  public static final String BIZ_RESOURCE_BUNDLE = "com.ffusion.beans.business.resources";
  public static final String BE_RESOURCE_BUNDLE = "com.ffusion.beans.bankemployee.resources";
  public static final String HISTORY_VALUE_KEY_ADMIN_ACCESS_PREFIX = "ADMINACCESS";
  public static final String EMPLOYEELOOKUPFAIL = "EmployeeLookupFail";
  public static final String BANKNAMELOOKUPFAIL = "BankNameLookupFail";
  
  protected BankEmployee()
  {
    this.adminAccess = new ArrayList(3);
    this.affiliateBankIds = new ArrayList();
  }
  
  public BankEmployee(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.locale = paramLocale;
    this.adminAccess = new ArrayList(3);
    this.affiliateBankIds = new ArrayList();
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.employeeId = paramString;
  }
  
  public String getEmployeeId()
  {
    return this.employeeId;
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setAdminAccess(ArrayList paramArrayList)
  {
    this.adminAccess = paramArrayList;
  }
  
  public void setAdminAccess(String paramString)
  {
    if (this.adminAccess == null) {
      this.adminAccess = new ArrayList();
    }
    if (!this.adminAccess.contains(paramString)) {
      this.adminAccess.add(paramString);
    }
    if (paramString.equals("ACCESS_ADMINISTRATOR"))
    {
      this.adminAccess.remove("ACCESS_SUPERVISOR");
      this.adminAccess.remove("ACCESS_CSR");
    }
    if (paramString.equals("ACCESS_SUPERVISOR"))
    {
      this.adminAccess.remove("ACCESS_ADMINISTRATOR");
      this.adminAccess.remove("ACCESS_CSR");
    }
    if (paramString.equals("ACCESS_CSR"))
    {
      this.adminAccess.remove("ACCESS_ADMINISTRATOR");
      this.adminAccess.remove("ACCESS_SUPERVISOR");
    }
  }
  
  public void setAllowAdminAccess(String paramString)
  {
    if ((this.currentAccess != null) && (Boolean.valueOf(paramString).booleanValue() == true))
    {
      if (!this.adminAccess.contains(this.currentAccess)) {
        this.adminAccess.add(this.currentAccess);
      }
    }
    else if ((this.currentAccess != null) && (!Boolean.valueOf(paramString).booleanValue()))
    {
      int i = this.adminAccess.indexOf(this.currentAccess);
      if (i != -1) {
        this.adminAccess.remove(i);
      }
    }
  }
  
  public void setCurrentAccess(String paramString)
  {
    this.currentAccess = paramString;
  }
  
  public String hasAccess(String paramString)
  {
    return jdMethod_long(paramString);
  }
  
  public String getHasAdminAccess()
  {
    return jdMethod_long(this.currentAccess);
  }
  
  private String jdMethod_long(String paramString)
  {
    String str1 = "false";
    if ((paramString != null) && (!paramString.equals("")))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str2 = localStringTokenizer.nextToken();
        str1 = String.valueOf(this.adminAccess.contains(str2));
        if (str1.equals("true")) {
          break;
        }
      }
    }
    return str1;
  }
  
  public ArrayList getAdminAccess()
  {
    return this.adminAccess;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public int getStatusInt()
  {
    if (this.status != null) {
      return Integer.parseInt(this.status);
    }
    return 0;
  }
  
  public void setSupervisor(String paramString)
  {
    this.supervisor = paramString;
  }
  
  public String getSupervisor()
  {
    return this.supervisor;
  }
  
  public void setNotify(String paramString)
  {
    this.notify = paramString;
  }
  
  public String getNotify()
  {
    return this.notify;
  }
  
  public void setReviewRequired(String paramString)
  {
    this.reviewRequired = paramString;
  }
  
  public String getReviewRequired()
  {
    return this.reviewRequired;
  }
  
  public void setOboEnabled(String paramString)
  {
    this.oboEnabled = paramString;
  }
  
  public String getOboEnabled()
  {
    return this.oboEnabled;
  }
  
  public void setApprovalProvider(String paramString)
  {
    this.approvalProvider = paramString;
  }
  
  public String getApprovalProvider()
  {
    return this.approvalProvider;
  }
  
  public void setMsgApprovalProvider(String paramString)
  {
    this.msgApprovalProvider = paramString;
  }
  
  public String getMsgApprovalProvider()
  {
    return this.msgApprovalProvider;
  }
  
  public void setDefaultAffiliateBankId(String paramString)
  {
    this.defaultAffiliateBankId = paramString;
  }
  
  public String getDefaultAffiliateBankId()
  {
    return this.defaultAffiliateBankId;
  }
  
  public void setAffiliateBankIds(ArrayList paramArrayList)
  {
    this.affiliateBankIds = ((ArrayList)paramArrayList.clone());
  }
  
  public void setAffiliateBankIds(AffiliateBanks paramAffiliateBanks)
  {
    if (paramAffiliateBanks != null) {
      for (int i = 0; i < paramAffiliateBanks.size(); i++)
      {
        AffiliateBank localAffiliateBank = (AffiliateBank)paramAffiliateBanks.get(i);
        addAffiliateBankId(localAffiliateBank.getAffiliateBankID());
      }
    }
  }
  
  public ArrayList getAffiliateBankIds()
  {
    return this.affiliateBankIds;
  }
  
  public void addAffiliateBankId(int paramInt)
  {
    if (this.affiliateBankIds == null) {
      this.affiliateBankIds = new ArrayList();
    }
    this.affiliateBankIds.add(String.valueOf(paramInt));
  }
  
  public void setJobTypeId(String paramString)
  {
    try
    {
      this.jobTypeId = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setJobTypeId(int paramInt)
  {
    this.jobTypeId = paramInt;
  }
  
  public int getJobTypeId()
  {
    return this.jobTypeId;
  }
  
  public String getFullName()
  {
    return UserUtil.getFullName(getFirstName(), getLastName(), this.locale);
  }
  
  public String getFullNameWithLoginId()
  {
    return UserUtil.getFullNameWithLoginId(getFirstName(), getLastName(), this.userName, this.employeeId, this.locale);
  }
  
  public String getSortableFullName()
  {
    return UserUtil.getSortableFullName(getFirstName(), getLastName(), this.locale);
  }
  
  public String getSortableFullNameWithLoginId()
  {
    return UserUtil.getSortableFullNameWithLoginId(getFirstName(), getLastName(), this.userName, this.employeeId, this.locale);
  }
  
  public SecureUser getSecureUser()
  {
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setId(getEmployeeId());
    localSecureUser.setUserName(getUserName());
    localSecureUser.setPassword(getPassword());
    localSecureUser.setBankID(getBankId());
    localSecureUser.setProfileID(getId());
    localSecureUser.setEntitlementID(getJobTypeId());
    String str = getDefaultAffiliateBankId();
    if (((str == null) || (str.equals("0")) || (str.equals(""))) && (getAffiliateBankIds().size() > 0)) {
      str = (String)getAffiliateBankIds().get(0);
    }
    localSecureUser.setAffiliateID(str);
    localSecureUser.setUserType(2);
    return localSecureUser;
  }
  
  public EntitlementGroupMember getEntitlementGroupMember()
  {
    return com.ffusion.csil.core.EntitlementsUtil.getEntitlementGroupMember(getSecureUser());
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BANKEMPLOYEEINFO");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    ArrayList localArrayList = getAdminAccess();
    if ((localArrayList != null) && (localArrayList.size() != 0))
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext()) {
        XMLHandler.appendTag(localStringBuffer, "ADMINACCESS", (String)localIterator.next());
      }
    }
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    XMLHandler.appendTag(localStringBuffer, "USERNAME", this.userName);
    XMLHandler.appendTag(localStringBuffer, "PASSWORD", this.password);
    XMLHandler.appendTag(localStringBuffer, "EMPLOYEEID", this.employeeId);
    XMLHandler.appendTag(localStringBuffer, "SUPERVISOR", this.supervisor);
    XMLHandler.appendTag(localStringBuffer, "EMPLOYEE_NOTIFY", this.notify);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BANKEMPLOYEEINFO");
    return localStringBuffer.toString();
  }
  
  public void set(BankEmployee paramBankEmployee)
  {
    super.set(paramBankEmployee);
    ArrayList localArrayList1 = paramBankEmployee.getAdminAccess();
    if (this.adminAccess == null) {
      this.adminAccess = new ArrayList(3);
    }
    if ((localArrayList1 != null) && (localArrayList1.size() != 0))
    {
      Iterator localIterator = localArrayList1.iterator();
      ArrayList localArrayList2 = new ArrayList(localArrayList1.size());
      while (localIterator.hasNext()) {
        localArrayList2.add((String)localIterator.next());
      }
      setAdminAccess(localArrayList2);
    }
    setId(paramBankEmployee.getId());
    setPassword(paramBankEmployee.getPassword());
    setStatus(paramBankEmployee.getStatus());
    setUserName(paramBankEmployee.getUserName());
    setEmployeeId(paramBankEmployee.getEmployeeId());
    setSupervisor(paramBankEmployee.getSupervisor());
    setNotify(paramBankEmployee.getNotify());
    setReviewRequired(paramBankEmployee.getReviewRequired());
    setOboEnabled(paramBankEmployee.getOboEnabled());
    setApprovalProvider(paramBankEmployee.getApprovalProvider());
    setMsgApprovalProvider(paramBankEmployee.getMsgApprovalProvider());
    setJobTypeId(paramBankEmployee.getJobTypeId());
    setDefaultAffiliateBankId(paramBankEmployee.getDefaultAffiliateBankId());
    setAffiliateBankIds((ArrayList)paramBankEmployee.getAffiliateBankIds().clone());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID")) {
      this.id = paramString2;
    } else if (paramString1.equals("ADMINACCESS")) {
      setAdminAccess(paramString2);
    } else if (paramString1.equals("STATUS")) {
      this.status = paramString2;
    } else if (paramString1.equals("USERNAME")) {
      this.userName = paramString2;
    } else if (paramString1.equals("PASSWORD")) {
      this.password = paramString2;
    } else if (paramString1.equals("EMPLOYEEID")) {
      this.employeeId = paramString2;
    } else if (paramString1.equals("SUPERVISOR")) {
      this.supervisor = paramString2;
    } else if (paramString1.equals("EMPLOYEE_NOTIFY")) {
      this.notify = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if (paramString1.equalsIgnoreCase("ADMINACCESS"))
    {
      this.currentAccess = paramString3;
      if ((paramString2.equals("=")) || (paramString2.equals("=="))) {
        return getHasAdminAccess().equals("true");
      }
      if ((paramString2.equals("!")) || (paramString2.equals("!!"))) {
        return getHasAdminAccess().equals("false");
      }
    }
    else
    {
      if ((paramString1.equalsIgnoreCase("STATUS")) && (getStatus() != null)) {
        return isFilterable(getStatus(), paramString2, paramString3);
      }
      if ((paramString1.equalsIgnoreCase("ID")) && (getId() != null)) {
        return isFilterable(getId(), paramString2, paramString3);
      }
      if ((paramString1.equalsIgnoreCase("SUPERVISOR")) && (getSupervisor() != null)) {
        return isFilterable(getSupervisor(), paramString2, paramString3);
      }
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    if (paramObject == null) {
      return 1;
    }
    BankEmployee localBankEmployee = (BankEmployee)paramObject;
    int i;
    if (this == localBankEmployee) {
      i = 0;
    } else if ((getLastName() == null) && (localBankEmployee.getLastName() == null)) {
      i = 0;
    } else if ((getLastName() == null) && (localBankEmployee.getLastName() != null)) {
      i = -1;
    } else if ((getLastName() != null) && (localBankEmployee.getLastName() == null)) {
      i = 1;
    } else {
      i = getLastName().compareToIgnoreCase(localBankEmployee.getLastName());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
    throws ClassCastException
  {
    if (paramObject == null) {
      return 1;
    }
    Collator localCollator = doGetCollator();
    BankEmployee localBankEmployee = (BankEmployee)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (getId() != null) && (localBankEmployee.getId() != null))
    {
      i = localCollator.compare(getId(), localBankEmployee.getId());
    }
    else if ((paramString.equals("USERNAME")) && (getUserName() != null) && (localBankEmployee.getUserName() != null))
    {
      i = localCollator.compare(getUserName().toLowerCase(this.locale), localBankEmployee.getUserName().toLowerCase(this.locale));
    }
    else if ((paramString.equals("EMPLOYEEID")) && (getEmployeeId() != null) && (localBankEmployee.getEmployeeId() != null))
    {
      i = localCollator.compare(getEmployeeId().toLowerCase(this.locale), localBankEmployee.getEmployeeId().toLowerCase(this.locale));
    }
    else if ((paramString.equals("STATUS")) && (getStatus() != null) && (localBankEmployee.getStatus() != null))
    {
      i = localCollator.compare(getStatus(), localBankEmployee.getStatus());
    }
    else
    {
      if (paramString.equals("SORTABLE_FULL_NAME"))
      {
        i = localCollator.compare(getLastName(), localBankEmployee.getLastName());
        if (i == 0) {
          i = localCollator.compare(getFirstName(), localBankEmployee.getFirstName());
        }
        return i;
      }
      if (paramString.equals("SORTABLE_FULL_NAME_WITH_LOGIN_ID"))
      {
        i = localCollator.compare(getLastName(), localBankEmployee.getLastName());
        if (i == 0) {
          i = localCollator.compare(getFirstName(), localBankEmployee.getFirstName());
        }
        if (i == 0) {
          i = localCollator.compare(getUserName(), localBankEmployee.getUserName());
        }
        if (i == 0) {
          i = localCollator.compare(getEmployeeId(), localBankEmployee.getEmployeeId());
        }
        return i;
      }
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getBankId()
  {
    return (String)get("BANK_ID");
  }
  
  public int getBankIdIntValue()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(getBankId());
    }
    catch (Exception localException) {}
    return i;
  }
  
  public void setBankId(String paramString)
  {
    set("BANK_ID", paramString);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, BankEmployee paramBankEmployee, String paramString)
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    String str1 = "";
    String str2 = "";
    Integer localInteger1 = null;
    Integer localInteger2 = null;
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramBankEmployee.id, this.id, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", ResourceUtil.getString("STATUS." + paramBankEmployee.status, "com.ffusion.beans.bankemployee.resources", this.locale), ResourceUtil.getString("STATUS." + this.status, "com.ffusion.beans.bankemployee.resources", this.locale), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "USERNAME", paramBankEmployee.userName, this.userName, paramString);
    if ((paramBankEmployee.password != this.password) && ((this.password == null) || (!this.password.equals(paramBankEmployee.password)))) {
      paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BEAN_NAME, "PASSWORD"), (String)null, (String)null, paramString);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "EMPLOYEEID", paramBankEmployee.employeeId, this.employeeId, paramString);
    try
    {
      if ((paramBankEmployee.supervisor == null) || (paramBankEmployee.supervisor.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBankEmployee.supervisor);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.supervisor == null) || (this.supervisor.equals("0")) || (this.supervisor.equals("None")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.supervisor);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "SUPERVISOR", str1, str2, paramString);
    }
    catch (ProfileException localProfileException1)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "SUPERVISOR", paramBankEmployee.supervisor, this.supervisor, ResourceUtil.getString("EmployeeLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "EMPLOYEE_NOTIFY", ResourceUtil.getString("EMPLOYEE_NOTIFY." + paramBankEmployee.notify, "com.ffusion.beans.bankemployee.resources", this.locale), ResourceUtil.getString("EMPLOYEE_NOTIFY." + this.notify, "com.ffusion.beans.bankemployee.resources", this.locale), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CURRENT_ACCESS", paramBankEmployee.currentAccess, this.currentAccess, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKING_ID", paramBankEmployee.trackingID, this.trackingID, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "REVIEW_REQUIRED", ResourceUtil.getString("REVIEW_REQUIRED." + paramBankEmployee.reviewRequired, "com.ffusion.beans.bankemployee.resources", this.locale), ResourceUtil.getString("REVIEW_REQUIRED." + this.reviewRequired, "com.ffusion.beans.bankemployee.resources", this.locale), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "OBO_ENABLED", ResourceUtil.getString("ON_BEHALF_OF." + paramBankEmployee.oboEnabled, "com.ffusion.beans.bankemployee.resources", this.locale), ResourceUtil.getString("ON_BEHALF_OF." + this.oboEnabled, "com.ffusion.beans.bankemployee.resources", this.locale), paramString);
    try
    {
      if ((paramBankEmployee.approvalProvider == null) || (paramBankEmployee.approvalProvider.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBankEmployee.approvalProvider);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.approvalProvider == null) || (this.approvalProvider.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.approvalProvider);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "APPROVAL_PROVIDER", str1, str2, paramString);
    }
    catch (ProfileException localProfileException2)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "APPROVAL_PROVIDER", paramBankEmployee.approvalProvider, this.approvalProvider, ResourceUtil.getString("EmployeeLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    try
    {
      if ((paramBankEmployee.msgApprovalProvider == null) || (paramBankEmployee.msgApprovalProvider.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBankEmployee.msgApprovalProvider);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.msgApprovalProvider == null) || (this.msgApprovalProvider.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.msgApprovalProvider);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "MSG_APPROVAL_PROVIDER", str1, str2, paramString);
    }
    catch (ProfileException localProfileException3)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "MSG_APPROVAL_PROVIDER", paramBankEmployee.msgApprovalProvider, this.msgApprovalProvider, ResourceUtil.getString("EmployeeLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    localInteger1 = new Integer(paramBankEmployee.defaultAffiliateBankId);
    localInteger2 = new Integer(this.defaultAffiliateBankId);
    try
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "AFFILIATE_BANK", AffiliateBankAdapter.getAffiliateBankByID(null, localInteger1.intValue()).getAffiliateBankName(), AffiliateBankAdapter.getAffiliateBankByID(null, localInteger2.intValue()).getAffiliateBankName(), paramString);
    }
    catch (ProfileException localProfileException4)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "AFFILIATE_BANK", paramBankEmployee.defaultAffiliateBankId, this.defaultAffiliateBankId, ResourceUtil.getString("BankNameLookupFail", "com.ffusion.beans.business.resources", this.locale));
    }
    super.logChanges(paramHistoryTracker, paramBankEmployee, paramString);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "USERNAME", this.userName, paramString);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, BankEmployee paramBankEmployee, ILocalizable paramILocalizable)
  {
    BankEmployee localBankEmployee = new BankEmployee(this.locale);
    String str1 = "";
    String str2 = "";
    paramHistoryTracker.detectChange(BEAN_NAME, "ID", paramBankEmployee.id, this.id, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", paramBankEmployee.status == null ? null : new LocalizableString("com.ffusion.beans.bankemployee.resources", "STATUS." + paramBankEmployee.status, null), this.status == null ? null : new LocalizableString("com.ffusion.beans.bankemployee.resources", "STATUS." + this.status, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "USERNAME", paramBankEmployee.userName, this.userName, paramILocalizable);
    if ((paramBankEmployee.password != this.password) && ((this.password == null) || (!this.password.equals(paramBankEmployee.password)))) {
      paramHistoryTracker.logChange(BEAN_NAME, "PASSWORD", (String)null, (String)null, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "EMPLOYEEID", paramBankEmployee.employeeId, this.employeeId, paramILocalizable);
    try
    {
      if ((paramBankEmployee.supervisor == null) || (paramBankEmployee.supervisor.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBankEmployee.supervisor);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.supervisor == null) || (this.supervisor.equals("0")) || (this.supervisor.equals("None")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.supervisor);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "SUPERVISOR", str1, str2, paramILocalizable);
    }
    catch (ProfileException localProfileException1)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "SUPERVISOR", paramBankEmployee.supervisor, this.supervisor, paramILocalizable);
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "EMPLOYEE_NOTIFY", paramBankEmployee.notify == null ? null : new LocalizableString("com.ffusion.beans.bankemployee.resources", "EMPLOYEE_NOTIFY." + paramBankEmployee.notify, null), this.notify == null ? null : new LocalizableString("com.ffusion.beans.bankemployee.resources", "EMPLOYEE_NOTIFY." + this.notify, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CURRENT_ACCESS", paramBankEmployee.currentAccess, this.currentAccess, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TRACKING_ID", paramBankEmployee.trackingID, this.trackingID, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "REVIEW_REQUIRED", paramBankEmployee.reviewRequired == null ? null : new LocalizableString("com.ffusion.beans.bankemployee.resources", "REVIEW_REQUIRED." + paramBankEmployee.reviewRequired, null), this.reviewRequired == null ? null : new LocalizableString("com.ffusion.beans.bankemployee.resources", "REVIEW_REQUIRED." + this.reviewRequired, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "OBO_ENABLED", paramBankEmployee.oboEnabled == null ? null : new LocalizableString("com.ffusion.beans.bankemployee.resources", "ON_BEHALF_OF." + paramBankEmployee.oboEnabled, null), this.oboEnabled == null ? null : new LocalizableString("com.ffusion.beans.bankemployee.resources", "ON_BEHALF_OF." + this.oboEnabled, null), paramILocalizable);
    Object localObject1;
    Object localObject2;
    String str4;
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
      localObject1 = BankEmployeeAdmin.getJobTypes(getSecureUser(), localHashMap);
      localObject2 = "";
      str4 = "";
      for (int i = 0; i < ((EntitlementGroups)localObject1).size(); i++)
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)((EntitlementGroups)localObject1).get(i);
        EntitlementGroupProperties localEntitlementGroupProperties;
        if (localEntitlementGroup.getGroupId() == paramBankEmployee.getJobTypeId())
        {
          localEntitlementGroupProperties = localEntitlementGroup.getProperties();
          localObject2 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementGroupProperties, "display name", this.locale);
          if (localObject2 == null) {
            localObject2 = localEntitlementGroup.getGroupName();
          }
        }
        if (localEntitlementGroup.getGroupId() == getJobTypeId())
        {
          localEntitlementGroupProperties = localEntitlementGroup.getProperties();
          str4 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementGroupProperties, "display name", this.locale);
          if (str4 == null) {
            str4 = localEntitlementGroup.getGroupName();
          }
        }
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "JOB_TYPE", (String)localObject2, str4, paramILocalizable);
    }
    catch (Exception localException1)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "JOB_TYPE", paramBankEmployee.getJobTypeId(), getJobTypeId(), paramILocalizable);
    }
    try
    {
      if ((paramBankEmployee.approvalProvider == null) || (paramBankEmployee.approvalProvider.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBankEmployee.approvalProvider);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.approvalProvider == null) || (this.approvalProvider.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.approvalProvider);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "APPROVAL_PROVIDER", str1, str2, paramILocalizable);
    }
    catch (ProfileException localProfileException2)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "APPROVAL_PROVIDER", paramBankEmployee.approvalProvider, this.approvalProvider, paramILocalizable);
    }
    try
    {
      if ((paramBankEmployee.msgApprovalProvider == null) || (paramBankEmployee.msgApprovalProvider.equals("0")))
      {
        str1 = "";
      }
      else
      {
        localBankEmployee.setId(paramBankEmployee.msgApprovalProvider);
        str1 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      if ((this.msgApprovalProvider == null) || (this.msgApprovalProvider.equals("0")))
      {
        str2 = "";
      }
      else
      {
        localBankEmployee.setId(this.msgApprovalProvider);
        str2 = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee).getUserName();
      }
      paramHistoryTracker.detectChange(BEAN_NAME, "MSG_APPROVAL_PROVIDER", str1, str2, paramILocalizable);
    }
    catch (ProfileException localProfileException3)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "MSG_APPROVAL_PROVIDER", paramBankEmployee.msgApprovalProvider, this.msgApprovalProvider, paramILocalizable);
    }
    try
    {
      String str3 = (paramBankEmployee.defaultAffiliateBankId == null) || (paramBankEmployee.defaultAffiliateBankId.equals("0")) ? null : AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(paramBankEmployee.defaultAffiliateBankId)).getAffiliateBankName();
      localObject1 = (this.defaultAffiliateBankId == null) || (this.defaultAffiliateBankId.equals("0")) ? null : AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(this.defaultAffiliateBankId)).getAffiliateBankName();
      paramHistoryTracker.detectChange(BEAN_NAME, "DEFAULT_BANK", str3, (String)localObject1, paramILocalizable);
    }
    catch (Exception localException2)
    {
      paramHistoryTracker.detectChange(BEAN_NAME, "DEFAULT_BANK", paramBankEmployee.defaultAffiliateBankId, this.defaultAffiliateBankId, paramILocalizable);
    }
    ArrayList localArrayList = paramBankEmployee.getAffiliateBankIds();
    if ((this.affiliateBankIds != null) && (localArrayList != null))
    {
      localObject1 = new LocalizableString("com.ffusion.beans.bankemployee.resources", "BANK_ADDED", null);
      localObject2 = this.affiliateBankIds.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        str1 = (String)((Iterator)localObject2).next();
        if (!localArrayList.contains(str1)) {
          try
          {
            str4 = AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(str1)).getAffiliateBankName();
            paramHistoryTracker.logChange(BEAN_NAME, "ASSIGNED_BANK", null, str4, (ILocalizable)localObject1);
          }
          catch (ProfileException localProfileException4)
          {
            paramHistoryTracker.logChange(BEAN_NAME, "ASSIGNED_BANK", null, str1, (ILocalizable)localObject1);
          }
        }
      }
      localObject1 = new LocalizableString("com.ffusion.beans.bankemployee.resources", "BANK_REMOVED", null);
      localObject2 = localArrayList.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        str1 = (String)((Iterator)localObject2).next();
        if (!this.affiliateBankIds.contains(str1)) {
          try
          {
            String str5 = AffiliateBankAdapter.getAffiliateBankByID(null, Integer.parseInt(str1)).getAffiliateBankName();
            paramHistoryTracker.logChange(BEAN_NAME, "ASSIGNED_BANK", str5, null, (ILocalizable)localObject1);
          }
          catch (ProfileException localProfileException5)
          {
            paramHistoryTracker.logChange(BEAN_NAME, "ASSIGNED_BANK", str1, null, (ILocalizable)localObject1);
          }
        }
      }
    }
    super.logChanges(paramHistoryTracker, paramBankEmployee, paramILocalizable);
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "USERNAME", this.userName, paramILocalizable);
  }
  
  public boolean affiliateBankComparisonAcceptable(BankEmployee paramBankEmployee)
  {
    boolean bool = false;
    if (!AffiliateBankAssignment.isMultiBank(paramBankEmployee.getDefaultAffiliateBankId()))
    {
      ArrayList localArrayList1 = new ArrayList(paramBankEmployee.getAffiliateBankIds());
      localArrayList1.add(paramBankEmployee.getDefaultAffiliateBankId());
      ArrayList localArrayList2 = new ArrayList(getAffiliateBankIds());
      localArrayList2.add(getDefaultAffiliateBankId());
      String str = "";
      for (int i = 0; i < localArrayList2.size(); i++)
      {
        str = (String)localArrayList2.get(i);
        if ((str != null) && (str.trim().length() > 0))
        {
          bool = localArrayList1.contains(str);
          if (!bool) {
            break;
          }
        }
      }
    }
    else
    {
      bool = true;
    }
    return bool;
  }
  
  public boolean isSameEmployee(BankEmployee paramBankEmployee)
  {
    boolean bool = false;
    if ((getId() != null) && (paramBankEmployee.getId() != null) && (getId().trim().equalsIgnoreCase(paramBankEmployee.getId().trim()))) {
      bool = true;
    }
    return bool;
  }
  
  public boolean isAffiliatedTo(BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2)
  {
    boolean bool = false;
    if ((paramBankEmployee1 == null) || (paramBankEmployee2 == null) || (paramBankEmployee2.getAffiliateBankIds() == null) || (getAffiliateBankIds() == null)) {
      return bool;
    }
    if (isSameEmployee(paramBankEmployee2)) {
      return bool;
    }
    bool = affiliateBankComparisonAcceptable(paramBankEmployee2);
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankemployee.BankEmployee
 * JD-Core Version:    0.7.0.1
 */