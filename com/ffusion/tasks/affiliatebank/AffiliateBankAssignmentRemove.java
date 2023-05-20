package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.business.GetFilteredBusinesses;
import com.ffusion.tasks.messages.GetAssignedMsgsByEmployee;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AffiliateBankAssignmentRemove
  extends BaseTask
  implements AffiliateBankTask
{
  private boolean aPM = false;
  private String aPQ = "TransferAffiliateBankIds";
  private String aPN = "TransferAssignedAffiliateBankIds";
  private String aPO = null;
  private int aPP = 0;
  public static final String REQUEST_ERROR_CODE_KEY = "ErrorCode";
  public static final int TASK_ERROR_CANNOT_REMOVE_WITH_BUSINESS_ASSOCIATIONS = 4143;
  public static final int TASK_ERROR_CANNOT_REMOVE_WITH_CASES_ASSOCIATIONS = 8500;
  private ArrayList aPL = new ArrayList();
  
  private ArrayList jdMethod_for(String[] paramArrayOfString)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramArrayOfString.length; i++) {
      localArrayList.add(paramArrayOfString[i]);
    }
    return localArrayList;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    String str2 = "Edit";
    if (isAdding()) {
      str2 = "Add";
    }
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute(str2 + "BankEmployee");
    int i = 1;
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("AssignedBanks");
    if (arrayOfString != null)
    {
      getAssignedBankIds().clear();
      for (int j = 0; j < arrayOfString.length; j++)
      {
        GetAffiliateBankRelationshipToEmployee localGetAffiliateBankRelationshipToEmployee = new GetAffiliateBankRelationshipToEmployee();
        localGetAffiliateBankRelationshipToEmployee.setAffiliateBankId(arrayOfString[j]);
        localGetAffiliateBankRelationshipToEmployee.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
        if (localGetAffiliateBankRelationshipToEmployee.getErrorCode() > 0)
        {
          i = 0;
          this.error = localGetAffiliateBankRelationshipToEmployee.getErrorCode();
          setErrorCode(this.error);
          str1 = "TE";
          break;
        }
        if ((localBankEmployee != null) && (arrayOfString[j].equals(localBankEmployee.getDefaultAffiliateBankId()))) {
          localBankEmployee.setDefaultAffiliateBankId("");
        }
        getAssignedBankIds().add(arrayOfString[j]);
      }
    }
    if ((i != 0) && (getAssignedBankIds() != null))
    {
      localObject = null;
      for (int k = 0; k < getAssignedBankIds().size(); k++)
      {
        localObject = (String)getAssignedBankIds().get(k);
        if ((GetFilteredBusinesses.isAssignedToAffiliate(paramHttpServletRequest, (String)localObject, 0)) || (GetFilteredBusinesses.isAssignedToAffiliate(paramHttpServletRequest, (String)localObject, 1)))
        {
          i = 0;
          this.error = 4143;
          setErrorCode(this.error);
          str1 = "TE";
          break;
        }
        GetAssignedMsgsByEmployee localGetAssignedMsgsByEmployee = new GetAssignedMsgsByEmployee();
        localGetAssignedMsgsByEmployee.setEmployeeId(getEmployeeId());
        localGetAssignedMsgsByEmployee.setAffiliateBankId((String)localObject);
        localGetAssignedMsgsByEmployee.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
        if (localGetAssignedMsgsByEmployee.getCount() > 0)
        {
          i = 0;
          this.error = 8500;
          setErrorCode(this.error);
          str1 = "TE";
          break;
        }
      }
    }
    Object localObject = null;
    if ((arrayOfString != null) && (i != 0)) {
      localObject = jdMethod_for(arrayOfString);
    }
    if (this.error == 0)
    {
      AffiliateBankAssignment localAffiliateBankAssignment = new AffiliateBankAssignment();
      localAffiliateBankAssignment.synchLists(localHttpSession, (ArrayList)localObject);
    }
    return str1;
  }
  
  public boolean isAdding()
  {
    return this.aPM;
  }
  
  public void setAdding(boolean paramBoolean)
  {
    this.aPM = paramBoolean;
  }
  
  public void setAdding(String paramString)
  {
    this.aPM = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getTransferAffiliateBankIdsKey()
  {
    return this.aPQ;
  }
  
  public void setTransferAffiliateBankIdsKey(String paramString)
  {
    this.aPQ = paramString;
  }
  
  public String getTransferAssignedAffiliateBankIds()
  {
    return this.aPN;
  }
  
  public void setTransferAssignedAffiliateBankIds(String paramString)
  {
    this.aPN = paramString;
  }
  
  public String getEmployeeId()
  {
    return this.aPO;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.aPO = paramString;
  }
  
  public int getErrorCode()
  {
    return this.aPP;
  }
  
  public void setErrorCode(int paramInt)
  {
    this.aPP = paramInt;
  }
  
  public ArrayList getAssignedBankIds()
  {
    return this.aPL;
  }
  
  public void setAssignedBankIds(ArrayList paramArrayList)
  {
    this.aPL = paramArrayList;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.AffiliateBankAssignmentRemove
 * JD-Core Version:    0.7.0.1
 */