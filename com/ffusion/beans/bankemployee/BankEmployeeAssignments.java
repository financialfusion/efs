package com.ffusion.beans.bankemployee;

import com.ffusion.util.FilteredList;
import java.util.Iterator;
import java.util.Locale;

public class BankEmployeeAssignments
  extends FilteredList
{
  public BankEmployeeAssignments() {}
  
  public BankEmployeeAssignments(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public BankEmployeeAssignment add()
  {
    BankEmployeeAssignment localBankEmployeeAssignment = new BankEmployeeAssignment(this.locale);
    add(localBankEmployeeAssignment);
    return localBankEmployeeAssignment;
  }
  
  public BankEmployeeAssignment create()
  {
    BankEmployeeAssignment localBankEmployeeAssignment = new BankEmployeeAssignment(this.locale);
    return localBankEmployeeAssignment;
  }
  
  public void set(BankEmployeeAssignments paramBankEmployeeAssignments)
  {
    Iterator localIterator = paramBankEmployeeAssignments.iterator();
    while (localIterator.hasNext())
    {
      BankEmployeeAssignment localBankEmployeeAssignment = (BankEmployeeAssignment)localIterator.next();
      if (localBankEmployeeAssignment != null) {
        add(localBankEmployeeAssignment);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankemployee.BankEmployeeAssignments
 * JD-Core Version:    0.7.0.1
 */