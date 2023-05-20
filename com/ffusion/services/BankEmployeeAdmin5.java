package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployeeAssignment;
import com.ffusion.beans.bankemployee.BankEmployeeAssignments;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface BankEmployeeAdmin5
{
  public static final int SERVICE_ERROR_INVALID_SIGNON = 5000;
  public static final int SERVICE_ERROR_NO_DATABASE_CONNECTION = 5001;
  public static final int SERVICE_ERROR_UNABLE_TO_COMPLETE_REQUEST = 5002;
  public static final int SERVICE_ERROR_INVALID_REQUEST = 5003;
  public static final int SERVICE_ERROR_NO_DATA_FOUND = 5004;
  public static final int SERVICE_ERROR_INIT_FILE_NOT_FOUND = 5005;
  public static final int SERVICE_ERROR_INVALID_INIT_FILE = 5006;
  public static final int SERVICE_ERROR_EXCESSIVE_DATA = 5007;
  
  public abstract BankEmployee addBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployee getBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployees getBankEmployees(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployees getBankEmployeeList(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployee modifyBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployee deleteBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Histories addHistory(SecureUser paramSecureUser, Histories paramHistories, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Histories getHistory(SecureUser paramSecureUser, History paramHistory, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void initialize(String paramString)
    throws ProfileException;
  
  public abstract BankEmployee signonBankEmployee(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployeeAssignments getBankEmployeeAssignments(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployeeAssignment addBankEmployeeAssignment(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployeeAssignment deleteBankEmployeeAssignment(SecureUser paramSecureUser, BankEmployeeAssignment paramBankEmployeeAssignment, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployee getBankEmployeeById(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void redistributeWorkloads(SecureUser paramSecureUser, BankEmployee paramBankEmployee1, BankEmployee paramBankEmployee2, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract BankEmployees getBankEmployeesByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BankEmployeeAdmin5
 * JD-Core Version:    0.7.0.1
 */