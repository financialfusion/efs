package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.bankemployee.GetFilteredBankEmployees;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAffiliateBankRelationshipToEmployee
  extends BaseTask
  implements AffiliateBankTask
{
  private BankEmployee aPq = null;
  private String aPr = null;
  private int aPs = 0;
  public static final int TASK_ERROR_CANNOT_REMOVE_AFFILIATE_BANK_FOR_SUPERVISOR = 4145;
  public static final int TASK_ERROR_CANNOT_REMOVE_AFFILIATE_BANK_FOR_APPROVER = 4146;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    GetFilteredBankEmployees localGetFilteredBankEmployees = new GetFilteredBankEmployees();
    localGetFilteredBankEmployees.setAffiliateBankId(getAffiliateBankId());
    BankEmployee localBankEmployee1 = (BankEmployee)localHttpSession.getAttribute("AddBankEmployee");
    if (localBankEmployee1 == null) {
      localBankEmployee1 = (BankEmployee)localHttpSession.getAttribute("EditBankEmployee");
    }
    setBankEmployee(localBankEmployee1);
    localGetFilteredBankEmployees.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    AffiliateBanks localAffiliateBanks = (AffiliateBanks)localHttpSession.getAttribute("TempAssignedBanks");
    BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
    if ((localBankEmployees != null) && (localAffiliateBanks != null))
    {
      Iterator localIterator = localBankEmployees.iterator();
      BankEmployee localBankEmployee2 = null;
      while (localIterator.hasNext())
      {
        localBankEmployee2 = (BankEmployee)localIterator.next();
        if ((getBankEmployee().getId() != null) && (localBankEmployee2.getSupervisor() != null) && (localBankEmployee2.getSupervisor().trim().length() > 0) && (getBankEmployee().getId().trim().equals(localBankEmployee2.getSupervisor().trim()))) {
          setErrorCode(4145);
        } else if ((getBankEmployee().getId() != null) && (localBankEmployee2.getApprovalProvider() != null) && (localBankEmployee2.getApprovalProvider().trim().length() > 0) && (getBankEmployee().getId().trim().equals(localBankEmployee2.getApprovalProvider().trim()))) {
          setErrorCode(4146);
        } else if ((getBankEmployee().getId() != null) && (localBankEmployee2.getMsgApprovalProvider() != null) && (localBankEmployee2.getMsgApprovalProvider().trim().length() > 0) && (getBankEmployee().getId().trim().equals(localBankEmployee2.getMsgApprovalProvider().trim()))) {
          setErrorCode(4146);
        }
      }
    }
    return null;
  }
  
  public String getAffiliateBankId()
  {
    return this.aPr;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.aPr = paramString;
  }
  
  public BankEmployee getBankEmployee()
  {
    return this.aPq;
  }
  
  public void setBankEmployee(BankEmployee paramBankEmployee)
  {
    this.aPq = paramBankEmployee;
  }
  
  public int getErrorCode()
  {
    return this.aPs;
  }
  
  public void setErrorCode(int paramInt)
  {
    this.aPs = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetAffiliateBankRelationshipToEmployee
 * JD-Core Version:    0.7.0.1
 */