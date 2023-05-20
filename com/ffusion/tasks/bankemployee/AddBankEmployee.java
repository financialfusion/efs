package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.Email;
import com.ffusion.beans.Phone;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ZipCode;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.PasswordUtil;
import com.ffusion.tasks.ValidatingTask;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddBankEmployee
  extends BankEmployee
  implements ValidatingTask, BankEmployeeTask
{
  private static final String oQ = "MANAGEMENT";
  public static final String PASSWORD_MASK = "****";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected String taskErrorURL = "TE";
  protected String validInputURL;
  protected int error = 0;
  protected String validate;
  protected String verifyFormat;
  protected int minUserNameLength = 3;
  protected boolean processFlag = false;
  private String oM = null;
  private String oP;
  private String oU;
  private String oS = "1";
  private String oN = "1";
  private String oO = "0";
  private int oT = 2;
  private int oV;
  private static final String oR = "EMPLOYEE_ADDED";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.bankemployee.resources";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    this.oM = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (getBankId() == null)
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      setBankId(localSecureUser.getBankID());
    }
    try
    {
      if (validateInput(localHttpSession))
      {
        if (this.processFlag)
        {
          this.processFlag = false;
          h(localHttpSession);
        }
        else
        {
          this.oM = this.validInputURL;
        }
      }
      else {
        this.oM = this.taskErrorURL;
      }
    }
    catch (CSILException localCSILException)
    {
      MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.oM;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    boolean bool = true;
    if (this.validate != null) {
      bool = validationCheck(paramHttpSession);
    }
    if (bool)
    {
      if (this.verifyFormat != null) {
        bool = verifyFormatCheck(paramHttpSession);
      }
    }
    else {
      this.verifyFormat = null;
    }
    return bool;
  }
  
  protected boolean validationCheck(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((this.validate.indexOf("EMPLOYEEID") != -1) && ((getEmployeeId() == null) || (getEmployeeId().length() == 0))) {
      bool = setError(5501);
    }
    if ((this.validate.indexOf("FIRST") != -1) && ((getFirstName() == null) || (getFirstName().length() == 0) || (getFirstName().length() > 35))) {
      bool = setError(28);
    }
    if ((this.validate.indexOf("MIDDLE") != -1) && ((getMiddleName() == null) || (getMiddleName().length() == 0) || (getMiddleName().length() > 35))) {
      bool = setError(29);
    }
    if ((this.validate.indexOf("LAST") != -1) && ((getLastName() == null) || (getLastName().length() == 0) || (getLastName().length() > 35))) {
      bool = setError(30);
    }
    if ((this.validate.indexOf("STREET") != -1) && ((this.street == null) || (this.street.length() == 0) || (this.street.length() > 80))) {
      bool = setError(23);
    }
    if ((this.validate.indexOf("CITY") != -1) && ((this.city == null) || (this.city.length() == 0) || (this.city.length() > 20))) {
      bool = setError(24);
    }
    if ((this.validate.indexOf("ZIPCODE") != -1) && ((this.zipCode == null) || (this.zipCode.getString().length() == 0) || (this.zipCode.getString().length() > 11))) {
      bool = setError(26);
    }
    if ((this.validate.indexOf("PHONE") != -1) && ((this.phone == null) || (this.phone.getString().length() == 0) || (this.phone.getString().length() > 14))) {
      bool = setError(27);
    }
    if ((this.validate.indexOf("PHONE2") != -1) && ((this.phone2 == null) || (this.phone2.getString().length() == 0) || (this.phone2.getString().length() > 14))) {
      bool = setError(27);
    }
    if ((this.validate.indexOf("STATE") != -1) && ((this.state == null) || (this.state.length() == 0))) {
      bool = setError(25);
    }
    if ((this.validate.indexOf("EMAIL") != -1) && ((this.email == null) || (this.email.getString().length() == 0) || (this.email.getString().length() > 40) || (!this.email.isValid()))) {
      bool = setError(31);
    }
    if ((this.validate.indexOf("EMAIL") != -1) && ((this.oU == null) || (!this.email.getString().equals(this.oU)))) {
      bool = setError(131);
    }
    if (this.validate.indexOf("USERNAME") != -1)
    {
      if ((this.userName == null) || (this.userName.length() == 0)) {
        return setError(1);
      }
      if (Strings.verifyStringWithNonAscii(this.userName)) {
        return setError(199);
      }
      if (this.userName.length() < this.minUserNameLength) {
        return setError(66);
      }
      if (this.userName.length() > 18) {
        return setError(1);
      }
      if (duplicateUserName(paramHttpSession) == true) {
        return setError(5514);
      }
    }
    if ((this.validate.indexOf("PASSWORD") != -1) && (validatePassword() != true)) {
      return setError(this.oV);
    }
    if ((this.validate.indexOf("AFFILIATE_BANK") != -1) && (this.oO.equals("0")) && ((getDefaultAffiliateBankId().equals("0")) || (getDefaultAffiliateBankId().equals("")))) {
      bool = setError(5531);
    }
    if ((this.validate.indexOf("MANAGEMENT") != -1) && ((this.oS == null) || (this.oS.equals("0")) || (this.oS.trim().length() == 0)) && ((this.oN == null) || (this.oN.equals("0")) || (this.oN.trim().length() == 0))) {
      return setError(5535);
    }
    if ((this.validate.indexOf("ADMINACCESS") != -1) && ((this.adminAccess == null) || (this.adminAccess.size() == 0))) {
      bool = setError(42);
    }
    this.validate = null;
    return bool;
  }
  
  protected boolean verifyFormatCheck(HttpSession paramHttpSession)
    throws CSILException
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    boolean bool = true;
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
    if (localResourceBundle == null)
    {
      this.error = 43;
      return false;
    }
    String str1 = localResourceBundle.getString("StateAbbr").toUpperCase();
    String str2 = localResourceBundle.getString("StateNames").toUpperCase();
    if ((this.verifyFormat.indexOf("ZIPCODE") != -1) && (this.zipCode != null) && (this.zipCode.getString().length() > 0) && (!jdMethod_new(localSecureUser))) {
      bool = setError(26);
    } else if ((this.verifyFormat.indexOf("PHONE") != -1) && (this.phone != null) && (this.phone.getString().length() > 0) && (!jdMethod_new(localSecureUser, this.phone))) {
      bool = setError(27);
    } else if ((this.verifyFormat.indexOf("PHONE2") != -1) && (this.phone2 != null) && (this.phone2.getString().length() > 0) && (!jdMethod_new(localSecureUser, this.phone2))) {
      bool = setError(27);
    } else if ((this.verifyFormat.indexOf("EMAIL") != -1) && (this.email != null) && (this.email.getString().length() > 0) && (!this.email.isValid())) {
      bool = setError(31);
    } else if ((this.verifyFormat.indexOf("STATE") != -1) && (this.state.length() < 2)) {
      bool = setError(25);
    } else if ((this.verifyFormat.indexOf("STATE") != -1) && (this.state.length() == 2) && (str1.indexOf(this.state.toUpperCase()) == -1)) {
      bool = setError(25);
    } else if ((this.verifyFormat.indexOf("STATE") != -1) && (this.state.length() > 2) && (str2.indexOf(this.state.toUpperCase()) == -1)) {
      bool = setError(25);
    } else if ((this.verifyFormat.indexOf("USERNAME") != -1) && (this.userName.length() < this.minUserNameLength)) {
      bool = setError(1);
    }
    this.verifyFormat = null;
    return bool;
  }
  
  private boolean jdMethod_new(SecureUser paramSecureUser)
    throws CSILException
  {
    String str = Util.validateZipCodeFormat(paramSecureUser, null, this.zipCode.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    this.zipCode.setFormat(str);
    return true;
  }
  
  private boolean jdMethod_new(SecureUser paramSecureUser, Phone paramPhone)
    throws CSILException
  {
    String str = Util.validatePhoneFormat(paramSecureUser, null, paramPhone.getString(), new HashMap());
    if (str == null) {
      return false;
    }
    paramPhone.setFormat(str);
    return true;
  }
  
  protected boolean duplicateUserName(HttpSession paramHttpSession)
  {
    BankEmployee localBankEmployee1 = (BankEmployee)paramHttpSession.getAttribute("OldBankEmployee");
    int i = 1;
    boolean bool = false;
    if ((localBankEmployee1 != null) && (localBankEmployee1.getUserName().equals(getUserName()))) {
      i = 0;
    }
    if (i != 0)
    {
      BankEmployee localBankEmployee2 = new BankEmployee((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      localBankEmployee2.setUserName(this.userName);
      BankEmployees localBankEmployees = new BankEmployees((Locale)paramHttpSession.getAttribute("java.util.Locale"));
      try
      {
        HashMap localHashMap = null;
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        localBankEmployees = BankEmployeeAdmin.getBankEmployeeList(localSecureUser, localBankEmployee2, localHashMap);
        if (localBankEmployees.getByUserName(this.userName) != null) {
          bool = true;
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        if (this.error == 12) {
          bool = false;
        } else {
          bool = true;
        }
      }
    }
    return bool;
  }
  
  private void h(HttpSession paramHttpSession)
  {
    HashMap localHashMap = null;
    this.error = 0;
    BankEmployee localBankEmployee1 = null;
    HistoryTracker localHistoryTracker = null;
    Object localObject;
    try
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      localBankEmployee1 = BankEmployeeAdmin.addBankEmployee(localSecureUser, this, localHashMap);
      localObject = localBankEmployee1.getSecureUser();
      localHistoryTracker = new HistoryTracker(localSecureUser, 18, localBankEmployee1.getId());
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
      EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      EntitlementGroupMember localEntitlementGroupMember2 = EntitlementsUtil.getEntitlementGroupMember((SecureUser)localObject);
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
      Entitlement localEntitlement;
      if (getManageCorporateBanking().equals("0"))
      {
        localEntitlement = new Entitlement("Manage Corporate Banking", null, null);
        localEntitlements.add(localEntitlement);
        localHistoryTracker.logEntitlementAdd(localEntitlement, localEntitlementTypePropertyLists);
      }
      if (getManageConsumerBanking().equals("0"))
      {
        localEntitlement = new Entitlement("Manage Consumer Banking", null, null);
        localEntitlements.add(localEntitlement);
        localHistoryTracker.logEntitlementAdd(localEntitlement, localEntitlementTypePropertyLists);
      }
      if (getManageMultipleBanks().equals("0"))
      {
        localEntitlement = new Entitlement("BC Manage Multiple Banks Simultaneously", null, null);
        localEntitlements.add(localEntitlement);
        localHistoryTracker.logEntitlementAdd(localEntitlement, localEntitlementTypePropertyLists);
      }
      com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.oM = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      paramHttpSession.setAttribute("ModifiedBankEmployee", localBankEmployee1);
      addBankEmployeeHistory(paramHttpSession, "Add", localBankEmployee1, null, localHistoryTracker);
      BankEmployee localBankEmployee2 = (BankEmployee)paramHttpSession.getAttribute("BankEmployee");
      if (localBankEmployee2 != null)
      {
        put("BankId", localBankEmployee2.get("BankId"));
        localObject = (BankEmployees)paramHttpSession.getAttribute("BankEmployees");
        if (localObject != null)
        {
          ((BankEmployees)localObject).add(localBankEmployee1);
        }
        else
        {
          this.error = 5503;
          this.oM = this.taskErrorURL;
        }
      }
      else
      {
        this.error = 5502;
        this.oM = this.taskErrorURL;
      }
    }
  }
  
  protected boolean addBankEmployeeHistory(HttpSession paramHttpSession, String paramString, BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2, HistoryTracker paramHistoryTracker)
  {
    boolean bool = true;
    if (paramString.equals("Add"))
    {
      paramBankEmployee1.logCreation(paramHistoryTracker, paramHistoryTracker.buildLocalizableComment(1));
      jdMethod_for(paramBankEmployee1, null, paramHistoryTracker, paramHistoryTracker.buildLocalizableComment(1));
      jdMethod_int(paramHttpSession, paramHistoryTracker.getHistories());
      if (this.error == 0) {
        paramHttpSession.setAttribute("EmployeeHistories", paramHistoryTracker.getHistories());
      } else {
        bool = false;
      }
    }
    else
    {
      paramBankEmployee1.logChanges(paramHistoryTracker, paramBankEmployee2, paramHistoryTracker.buildLocalizableComment(17));
      jdMethod_for(paramBankEmployee1, paramBankEmployee2, paramHistoryTracker, paramHistoryTracker.buildLocalizableComment(17));
      Histories localHistories1 = paramHistoryTracker.getHistories();
      if (localHistories1.size() > 0)
      {
        jdMethod_int(paramHttpSession, localHistories1);
        if (this.error == 0)
        {
          Histories localHistories2 = (Histories)paramHttpSession.getAttribute("EmployeeHistories");
          if (localHistories2 != null) {
            for (int i = 0; i < localHistories1.size(); i++) {
              localHistories2.add((History)localHistories1.get(i));
            }
          }
        }
        else
        {
          bool = false;
        }
      }
    }
    return bool;
  }
  
  private void jdMethod_int(HttpSession paramHttpSession, Histories paramHistories)
  {
    try
    {
      HistoryAdapter.addHistory(paramHistories);
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for AddBankEmployee: " + localProfileException.toString());
    }
  }
  
  private void jdMethod_for(BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2, HistoryTracker paramHistoryTracker, String paramString)
  {
    String str1 = "";
    String str2 = "";
    ArrayList localArrayList1 = paramBankEmployee1.getAdminAccess();
    ArrayList localArrayList2 = paramBankEmployee2 == null ? null : paramBankEmployee2.getAdminAccess();
    if (localArrayList1 != null)
    {
      if (localArrayList1.contains("ACCESS_ADMINISTRATOR")) {
        str1 = "Administrator";
      } else if (localArrayList1.contains("ACCESS_SUPERVISOR")) {
        str1 = "SUPERVISOR";
      } else if (localArrayList1.contains("ACCESS_CSR")) {
        str1 = "CSR";
      }
      if (localArrayList1.contains("ACCESS_PERSONALBANKER")) {
        str1 = str1 + ", Personal Banker";
      }
    }
    if (localArrayList2 != null)
    {
      if (localArrayList2.contains("ACCESS_ADMINISTRATOR")) {
        str2 = "Administrator";
      } else if (localArrayList2.contains("ACCESS_SUPERVISOR")) {
        str2 = "SUPERVISOR";
      } else if (localArrayList2.contains("ACCESS_CSR")) {
        str2 = "CSR";
      }
      if (localArrayList2.contains("ACCESS_PERSONALBANKER")) {
        str2 = str2 + ", Personal Banker";
      }
    }
    if (!localArrayList1.equals(localArrayList2)) {
      paramHistoryTracker.logChange(paramHistoryTracker.lookupField(BankEmployee.BEAN_NAME, "ADMINACCESS"), str2, str1, paramString);
    }
  }
  
  private void jdMethod_for(BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2, HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = paramBankEmployee1.getAdminAccess();
    ArrayList localArrayList4 = paramBankEmployee2 == null ? null : paramBankEmployee2.getAdminAccess();
    if (localArrayList3 != null)
    {
      if (localArrayList3.contains("ACCESS_ADMINISTRATOR")) {
        localArrayList1.add(new LocalizableString("com.ffusion.beans.bankemployee.resources", "ACCESS_ADMINISTRATOR", null));
      } else if (localArrayList3.contains("ACCESS_SUPERVISOR")) {
        localArrayList1.add(new LocalizableString("com.ffusion.beans.bankemployee.resources", "ACCESS_SUPERVISOR", null));
      } else if (localArrayList3.contains("ACCESS_CSR")) {
        localArrayList1.add(new LocalizableString("com.ffusion.beans.bankemployee.resources", "ACCESS_CSR", null));
      }
      if (localArrayList3.contains("ACCESS_PERSONALBANKER")) {
        localArrayList1.add(new LocalizableString("com.ffusion.beans.bankemployee.resources", "ACCESS_PERSONALBANKER", null));
      }
    }
    if (localArrayList4 != null)
    {
      if (localArrayList4.contains("ACCESS_ADMINISTRATOR")) {
        localArrayList2.add(new LocalizableString("com.ffusion.beans.bankemployee.resources", "ACCESS_ADMINISTRATOR", null));
      } else if (localArrayList4.contains("ACCESS_SUPERVISOR")) {
        localArrayList2.add(new LocalizableString("com.ffusion.beans.bankemployee.resources", "ACCESS_SUPERVISOR", null));
      } else if (localArrayList4.contains("ACCESS_CSR")) {
        localArrayList2.add(new LocalizableString("com.ffusion.beans.bankemployee.resources", "ACCESS_CSR", null));
      }
      if (localArrayList4.contains("ACCESS_PERSONALBANKER")) {
        localArrayList2.add(new LocalizableString("com.ffusion.beans.bankemployee.resources", "ACCESS_PERSONALBANKER", null));
      }
    }
    if (!localArrayList3.equals(localArrayList4))
    {
      LocalizableString localLocalizableString = null;
      if (localArrayList1.size() > 0)
      {
        localObject = new StringBuffer("ADMINACCESS").append(localArrayList1.size());
        localLocalizableString = new LocalizableString("com.ffusion.beans.bankemployee.resources", ((StringBuffer)localObject).toString(), localArrayList1.toArray());
      }
      Object localObject = null;
      if (localArrayList2.size() > 0)
      {
        StringBuffer localStringBuffer = new StringBuffer("ADMINACCESS").append(localArrayList2.size());
        localObject = new LocalizableString("com.ffusion.beans.bankemployee.resources", localStringBuffer.toString(), localArrayList2.toArray());
      }
      paramHistoryTracker.logChange(BEAN_NAME, "ADMINACCESS", (ILocalizable)localObject, localLocalizableString, paramILocalizable);
    }
  }
  
  public static History buildHistory(HttpSession paramHttpSession, BankEmployee paramBankEmployee, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    DateTime localDateTime = new DateTime(localLocale);
    History localHistory = new History(localLocale);
    localHistory.setId(paramBankEmployee.getId());
    localHistory.setIdType(18);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    String str1 = "" + localSecureUser.getProfileID();
    String str2 = localSecureUser.getUserName();
    int i = 1;
    BankEmployee localBankEmployee = (BankEmployee)paramHttpSession.getAttribute("BankEmployee");
    if (localBankEmployee != null)
    {
      str1 = localBankEmployee.getId();
      i = 0;
      str2 = localBankEmployee.getFirstName() + " " + localBankEmployee.getLastName();
    }
    localHistory.setModifierId(str1);
    localHistory.setModifierName(str2);
    localHistory.setModifierType(i);
    localHistory.setDateChanged(localDateTime);
    String str3 = null;
    try
    {
      str3 = ResourceUtil.getString(paramString1, "com.ffusion.beans.bankemployee.resources", localLocale);
    }
    catch (Exception localException1) {}
    if (str3 == null) {
      localHistory.setDataChanged(paramString1);
    } else {
      localHistory.setDataChanged(str3);
    }
    String str4 = null;
    try
    {
      str4 = ResourceUtil.getString(paramString1 + "." + paramString2, "com.ffusion.beans.bankemployee.resources", localLocale);
    }
    catch (Exception localException2) {}
    String str5 = null;
    try
    {
      str5 = ResourceUtil.getString(paramString1 + "." + paramString3, "com.ffusion.beans.bankemployee.resources", localLocale);
    }
    catch (Exception localException3) {}
    if (str4 != null) {
      localHistory.setOldValue(str4);
    } else {
      localHistory.setOldValue(paramString2);
    }
    if (str5 != null) {
      localHistory.setNewValue(str5);
    } else {
      localHistory.setNewValue(paramString3);
    }
    if ((paramString4 != null) && (paramString4.length() > 0)) {
      localHistory.setComment(paramString4);
    }
    return localHistory;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setVerifyFormat(String paramString)
  {
    this.verifyFormat = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.verifyFormat = paramString.toUpperCase();
    }
  }
  
  public void setMinUserNameLength(String paramString)
  {
    int i;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 0;
    }
    this.minUserNameLength = i;
  }
  
  public void setMinUserNameLength(int paramInt)
  {
    this.minUserNameLength = paramInt;
  }
  
  public void setValidInputURL(String paramString)
  {
    this.validInputURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  protected boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setManageConsumerBanking(String paramString)
  {
    this.oS = paramString;
  }
  
  public String getManageConsumerBanking()
  {
    return this.oS;
  }
  
  public void setManageCorporateBanking(String paramString)
  {
    this.oN = paramString;
  }
  
  public String getManageCorporateBanking()
  {
    return this.oN;
  }
  
  public void setManageMultipleBanks(String paramString)
  {
    this.oO = paramString;
  }
  
  public String getManageMultipleBanks()
  {
    return this.oO;
  }
  
  public void setPassword(String paramString)
  {
    if (!"****".equals(paramString)) {
      this.password = paramString;
    }
  }
  
  public String getPasswordForDisplay()
  {
    return (this.password == null) || (this.password.length() == 0) ? "" : "****";
  }
  
  public void setPassword2(String paramString)
  {
    if (!"****".equals(paramString)) {
      this.oP = paramString;
    }
  }
  
  public String getPassword2()
  {
    return this.oP;
  }
  
  public String getPassword2ForDisplay()
  {
    return (this.oP == null) || (this.oP.length() == 0) ? "" : "****";
  }
  
  public void setEmail2(String paramString)
  {
    this.oU = paramString;
  }
  
  public String getEmail2()
  {
    return this.oU;
  }
  
  public void setUserType(String paramString)
  {
    try
    {
      this.oT = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.oT = 1;
    }
  }
  
  protected boolean validatePassword()
  {
    this.oV = PasswordUtil.validatePassword(this.password, this.oT);
    if (!this.password.equals(this.oP)) {
      this.oV = 5;
    }
    return this.oV == 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.AddBankEmployee
 * JD-Core Version:    0.7.0.1
 */