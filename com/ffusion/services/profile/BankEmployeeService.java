package com.ffusion.services.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployeeAssignment;
import com.ffusion.beans.bankemployee.BankEmployeeAssignments;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.handlers.Authentication;
import com.ffusion.efs.adapters.profile.BankEmployeeAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.BankEmployeeAdmin8;
import com.ffusion.util.Strings;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class BankEmployeeService
  implements BankEmployeeAdmin8
{
  public void initialize(String paramString)
    throws ProfileException
  {}
  
  public BankEmployee addBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BankEmployeeAdapter.addBankEmployee(paramBankEmployee);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public BankEmployee getBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      BankEmployee localBankEmployee = BankEmployeeAdapter.getBankEmployee(paramBankEmployee);
      localBankEmployee.setLocale(paramSecureUser.getLocale());
      return localBankEmployee;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public BankEmployee getBankEmployeeById(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      BankEmployee localBankEmployee = BankEmployeeAdapter.getBankEmployeeById(paramBankEmployee);
      localBankEmployee.setLocale(paramSecureUser.getLocale());
      return localBankEmployee;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public BankEmployees getBankEmployees(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return getBankEmployeeList(paramSecureUser, paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public BankEmployees getBankEmployeeList(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      BankEmployees localBankEmployees = BankEmployeeAdapter.getFilteredBankEmployees(paramBankEmployee);
      for (int i = 0; i < localBankEmployees.size(); i++) {
        ((BankEmployee)localBankEmployees.get(i)).setLocale(paramSecureUser.getLocale());
      }
      return localBankEmployees;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public String getBankEmployeeCount(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BankEmployeeAdapter.getFilteredBankEmployeesCount(paramBankEmployee);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public BankEmployee modifyBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "BankEmployeeService.modifyBankEmployee";
    try
    {
      if ((paramHashMap != null) && (paramHashMap.get("SELF_MODIFY") != null))
      {
        int i = 0;
        BankEmployee localBankEmployee = new BankEmployee(paramBankEmployee.getLocale());
        localBankEmployee.setId(paramBankEmployee.getId());
        localBankEmployee = BankEmployeeAdapter.getBankEmployeeById(localBankEmployee);
        if (!localBankEmployee.getStatus().equals(paramBankEmployee.getStatus())) {
          i = 1;
        } else if (Strings.compareTo(localBankEmployee.getSupervisor(), paramBankEmployee.getSupervisor()) != 0) {
          i = 1;
        } else if (Strings.compareTo(localBankEmployee.getNotify(), paramBankEmployee.getNotify()) != 0) {
          i = 1;
        } else if (Strings.compareTo(localBankEmployee.getReviewRequired(), paramBankEmployee.getReviewRequired()) != 0) {
          i = 1;
        } else if (Strings.compareTo(localBankEmployee.getOboEnabled(), paramBankEmployee.getOboEnabled()) != 0) {
          i = 1;
        } else if (Strings.compareTo(localBankEmployee.getApprovalProvider(), paramBankEmployee.getApprovalProvider()) != 0) {
          i = 1;
        } else if (BankEmployeeAdapter.adminAccessToInt(localBankEmployee.getAdminAccess()) != BankEmployeeAdapter.adminAccessToInt(paramBankEmployee.getAdminAccess())) {
          i = 1;
        }
        if (i != 0) {
          throw new ProfileException(str, 15, "Bank Employee attempting to sefl-modify restricted data");
        }
        Integer localInteger = (Integer)paramSecureUser.get("AUTHENTICATE");
        boolean bool = true;
        if ((localInteger != null) && ((localInteger.intValue() == 3007) || (localInteger.intValue() == 3024))) {
          bool = false;
        }
        int j;
        try
        {
          j = Integer.parseInt(paramBankEmployee.getId());
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new ProfileException(14, localNumberFormatException);
        }
        int k = CustomerAdapter.checkPasswordHistory(2, j, paramBankEmployee.getPassword(), bool);
        if (k == 2) {
          throw new ProfileException(14);
        }
        if (k == 3) {
          throw new ProfileException(16);
        }
        if (k == 4) {
          throw new ProfileException(17);
        }
        BankEmployeeAdapter.modifyBankEmployee(paramBankEmployee);
        if (k == 1) {
          CustomerAdapter.addToPasswordHistory(2, j, paramBankEmployee.getPassword());
        }
        if (localInteger != null)
        {
          paramSecureUser.remove("AUTHENTICATE");
          paramBankEmployee.remove("AUTHENTICATE");
        }
      }
      else
      {
        BankEmployeeAdapter.modifyBankEmployee(paramBankEmployee);
      }
      return paramBankEmployee;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public BankEmployee deleteBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      BankEmployeeAdapter.deleteBankEmployee(paramBankEmployee);
      return paramBankEmployee;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public BankEmployees getBankEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      BankEmployees localBankEmployees = BankEmployeeAdapter.getBankEmployeesByIds(paramArrayList);
      for (int i = 0; i < localBankEmployees.size(); i++) {
        ((BankEmployee)localBankEmployees.get(i)).setLocale(paramSecureUser.getLocale());
      }
      return localBankEmployees;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public boolean hasDirectReports(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BankEmployeeAdapter.hasDirectReports(paramInt, 0);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public boolean hasDirectReports(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return BankEmployeeAdapter.hasDirectReports(paramInt1, paramInt2);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public boolean getInfoForAuditing(BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    return BankEmployeeAdapter.getInfoForAuditing(paramBankEmployee, paramHashMap);
  }
  
  public Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      HistoryAdapter.addHistory(paramHistories);
      return paramHistories;
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      return HistoryAdapter.getHistory(paramHistory, paramCalendar1, paramCalendar2);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  protected int mapAdminAccessString(String paramString)
  {
    if (paramString.equalsIgnoreCase("ACCESS_ADMINISTRATOR")) {
      return 1;
    }
    if (paramString.equalsIgnoreCase("ACCESS_SUPERVISOR")) {
      return 2;
    }
    if (paramString.equalsIgnoreCase("ACCESS_CSR")) {
      return 4;
    }
    if (paramString.equalsIgnoreCase("ACCESS_PERSONALBANKER")) {
      return 8;
    }
    return 0;
  }
  
  protected void updateAdminAccess(BankEmployee paramBankEmployee, int paramInt)
  {
    Object localObject = null;
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
  
  protected int mapError(Exception paramException)
  {
    int i = 5002;
    if ((paramException instanceof ProfileException))
    {
      ProfileException localProfileException = (ProfileException)paramException;
      DebugLog.log("Exception from ProfileAdapter: " + localProfileException.where + " : " + localProfileException.why + " : " + localProfileException.code);
      switch (localProfileException.code)
      {
      case 0: 
        i = 0;
        break;
      case 8: 
        i = 8;
        break;
      case 7: 
        i = 7;
        break;
      case 11: 
        i = 11;
        break;
      case 12: 
        i = 12;
        break;
      case 3: 
      case 4: 
        i = 3811;
        break;
      case 15: 
        i = 15;
      case 14: 
        i = 14;
        break;
      case 16: 
        i = 16;
        break;
      case 17: 
        i = 17;
        break;
      case 1: 
      case 2: 
      case 5: 
      case 6: 
      case 9: 
      case 10: 
      case 13: 
      default: 
        i = 2;
      }
    }
    return i;
  }
  
  public void redistributeWorkloads(SecureUser paramSecureUser, BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2, HashMap paramHashMap)
    throws ProfileException
  {}
  
  public int getLastError()
  {
    return 5002;
  }
  
  public String getLastMessage()
  {
    return null;
  }
  
  public BankEmployee signonBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    if ((paramHashMap != null) && (paramHashMap.containsKey("Credentials")))
    {
      Credentials localCredentials = (Credentials)paramHashMap.get("Credentials");
      try
      {
        Authentication.validateCredentials(paramSecureUser, localCredentials, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        try
        {
          if (paramSecureUser.getProfileID() != 0) {
            BankEmployeeAdapter.logFailedLogin(paramSecureUser, paramHashMap);
          }
        }
        catch (ProfileException localProfileException2) {}
        throw new ProfileException(22, localCSILException);
      }
    }
    try
    {
      return BankEmployeeAdapter.signonBankEmployee(paramBankEmployee);
    }
    catch (ProfileException localProfileException1)
    {
      throw new ProfileException(mapError(localProfileException1), localProfileException1);
    }
  }
  
  public void resetBankEmployeePassword(SecureUser paramSecureUser, BankEmployee paramBankEmployee, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      BankEmployeeAdapter.resetBankEmployeePassword(paramBankEmployee, paramString, paramHashMap);
      paramBankEmployee.setPassword(paramString);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public void modifyBankEmployeePasswordStatus(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      BankEmployeeAdapter.modifyBankEmployeePasswordStatus(paramBankEmployee, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public void modifyBankEmployeePassword(SecureUser paramSecureUser, BankEmployee paramBankEmployee, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException
  {
    try
    {
      Integer localInteger = (Integer)paramSecureUser.get("AUTHENTICATE");
      boolean bool = true;
      if ((localInteger != null) && ((localInteger.intValue() == 3007) || (localInteger.intValue() == 3024))) {
        bool = false;
      }
      int i;
      try
      {
        i = Integer.parseInt(paramBankEmployee.getId());
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new ProfileException(14, localNumberFormatException);
      }
      int j = CustomerAdapter.checkPasswordHistory(2, i, paramString2, bool);
      if (j == 2) {
        throw new ProfileException(14);
      }
      if (j == 3) {
        throw new ProfileException(16);
      }
      if (j == 4) {
        throw new ProfileException(17);
      }
      if (j == 1) {
        CustomerAdapter.addToPasswordHistory(2, i, paramString2);
      }
      if (localInteger != null)
      {
        paramSecureUser.remove("AUTHENTICATE");
        paramBankEmployee.remove("AUTHENTICATE");
      }
      BankEmployeeAdapter.modifyBankEmployeePassword(paramBankEmployee, paramString2, paramHashMap);
      paramBankEmployee.setPassword(paramString2);
    }
    catch (ProfileException localProfileException)
    {
      throw new ProfileException(mapError(localProfileException), localProfileException);
    }
  }
  
  public BankEmployeeAssignments getBankEmployeeAssignments(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException
  {
    throw new ProfileException(3800);
  }
  
  public BankEmployeeAssignment addBankEmployeeAssignment(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException
  {
    throw new ProfileException(3800);
  }
  
  public BankEmployeeAssignment deleteBankEmployeeAssignment(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException
  {
    throw new ProfileException(3800);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.BankEmployeeService
 * JD-Core Version:    0.7.0.1
 */