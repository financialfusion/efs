package com.ffusion.beans.bankemployee;

import com.ffusion.beans.ExtendABean;
import java.io.Serializable;
import java.util.Locale;

public class BankEmployeeAssignment
  extends ExtendABean
  implements Serializable
{
  public static final String TYPE_BUSINESS_PERSONAL_BANKER = "BUSINESS_PERSONAL_BANKER";
  public static final String TYPE_BUSINESS_APPROVE_BY = "BUSINESS_APPROVE_BY";
  public static final String TYPE_BUSINESS_ACCOUNT_REP = "BUSINESS_ACCOUNT_REP";
  protected int employeeId;
  protected String assignmentType;
  protected int assignment;
  
  public BankEmployeeAssignment() {}
  
  public BankEmployeeAssignment(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.locale = paramLocale;
  }
  
  public void setEmployeeId(int paramInt)
  {
    this.employeeId = paramInt;
  }
  
  public int getEmployeeId()
  {
    return this.employeeId;
  }
  
  public void setAssignmentType(String paramString)
  {
    this.assignmentType = paramString;
  }
  
  public String getAssignmentType()
  {
    return this.assignmentType;
  }
  
  public void setAssignment(int paramInt)
  {
    this.assignment = paramInt;
  }
  
  public int getAssignment()
  {
    return this.assignment;
  }
  
  public void set(BankEmployeeAssignment paramBankEmployeeAssignment)
  {
    setEmployeeId(paramBankEmployeeAssignment.getEmployeeId());
    setAssignmentType(paramBankEmployeeAssignment.getAssignmentType());
    setAssignment(paramBankEmployeeAssignment.getAssignment());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankemployee.BankEmployeeAssignment
 * JD-Core Version:    0.7.0.1
 */