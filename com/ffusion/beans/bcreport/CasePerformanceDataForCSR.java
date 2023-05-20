package com.ffusion.beans.bcreport;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.bankemployee.BankEmployee;
import java.util.ArrayList;

public class CasePerformanceDataForCSR
  extends ExtendABean
{
  private BankEmployee a4C = null;
  private ArrayList a4B = null;
  
  public void setCSR(BankEmployee paramBankEmployee)
  {
    this.a4C = paramBankEmployee;
  }
  
  public BankEmployee getCSR()
  {
    return this.a4C;
  }
  
  public void setCasePerformances(ArrayList paramArrayList)
  {
    this.a4B = paramArrayList;
  }
  
  public ArrayList getCasePerformances()
  {
    return this.a4B;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.CasePerformanceDataForCSR
 * JD-Core Version:    0.7.0.1
 */