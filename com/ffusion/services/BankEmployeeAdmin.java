package com.ffusion.services;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import java.io.Serializable;
import java.util.Calendar;

public abstract interface BankEmployeeAdmin
  extends Serializable
{
  public abstract int addBankEmployee(BankEmployee paramBankEmployee);
  
  public abstract int getBankEmployee(BankEmployee paramBankEmployee);
  
  public abstract int getBankEmployees(BankEmployees paramBankEmployees, BankEmployee paramBankEmployee);
  
  public abstract int getFilteredBankEmployees(BankEmployees paramBankEmployees, BankEmployee paramBankEmployee);
  
  public abstract int getBankEmployeeList(BankEmployees paramBankEmployees, BankEmployee paramBankEmployee);
  
  public abstract int modifyBankEmployee(BankEmployee paramBankEmployee);
  
  public abstract int deleteBankEmployee(BankEmployee paramBankEmployee);
  
  public abstract int addHistory(Histories paramHistories);
  
  public abstract int getHistory(History paramHistory, Histories paramHistories, Calendar paramCalendar1, Calendar paramCalendar2);
  
  public abstract int getUser(User paramUser);
  
  public abstract int modifyUser(User paramUser);
  
  public abstract int getFilteredUsers(Users paramUsers, User paramUser, Account paramAccount);
  
  public abstract int getUsers(Users paramUsers, User paramUser);
  
  public abstract int getUserList(Users paramUsers, User paramUser);
  
  public abstract int deleteUser(User paramUser);
  
  public abstract int modifyPasswordStatus(User paramUser);
  
  public abstract int getLastError();
  
  public abstract String getLastMessage();
  
  public abstract void setInitURL(String paramString);
  
  public abstract int getBusinesses(Businesses paramBusinesses, Business paramBusiness);
  
  public abstract int deleteBusiness(Business paramBusiness);
  
  public abstract int getFilteredBusinesses(Businesses paramBusinesses, Business paramBusiness, Account paramAccount);
  
  public abstract int getBusinessList(Businesses paramBusinesses, Business paramBusiness);
  
  public abstract int getHistoryUserType();
  
  public abstract int getHistoryEmployeeType();
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BankEmployeeAdmin
 * JD-Core Version:    0.7.0.1
 */