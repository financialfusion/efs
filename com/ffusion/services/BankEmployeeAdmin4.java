package com.ffusion.services;

import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployeeAssignment;
import com.ffusion.beans.bankemployee.BankEmployeeAssignments;

public abstract interface BankEmployeeAdmin4
  extends BankEmployeeAdmin3
{
  public abstract int signonBankEmployee(BankEmployee paramBankEmployee);
  
  public abstract int getBankEmployeeAssignments(BankEmployeeAssignments paramBankEmployeeAssignments, BankEmployeeAssignment paramBankEmployeeAssignment);
  
  public abstract int addBankEmployeeAssignment(BankEmployeeAssignment paramBankEmployeeAssignment);
  
  public abstract int deleteBankEmployeeAssignment(BankEmployeeAssignment paramBankEmployeeAssignment);
  
  public abstract int getBankEmployeeById(BankEmployee paramBankEmployee);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BankEmployeeAdmin4
 * JD-Core Version:    0.7.0.1
 */