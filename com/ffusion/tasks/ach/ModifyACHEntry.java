package com.ffusion.tasks.ach;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHEntry
  extends ACHEntry
  implements Task
{
  public static final String SERVICE_ERROR_SESSION_NAME = "com.ffusion.tasks.BaseTask.taskErrorURL";
  public static final String TASK_ERROR_SESSION_NAME = "com.ffusion.tasks.BaseTask.serviceErrorURL";
  public static final String SERVICE_ERROR = "SE";
  public static final String TASK_ERROR = "TE";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  public static final String TASKTIMEOUTSECS = "TaskTimeoutSecs";
  protected String payeesName = "ACHPayees";
  protected String payeeName = "ACHPayee";
  protected String entryID;
  protected String batchName = "ACHBatch";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected ACHPayee origPayee = null;
  protected HashMap parameters = new HashMap();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag) {
      str = initProcess(localHttpSession);
    } else {
      str = doProcess(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.initFlag = false;
    this.error = 0;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    ACHBatch localACHBatch = (ACHBatch)paramHttpSession.getAttribute(this.batchName);
    if (localACHBatch == null)
    {
      this.error = 16002;
      str = this.taskErrorURL;
    }
    else
    {
      ACHPayee localACHPayee = new ACHPayee();
      ACHEntry localACHEntry;
      if ((this.entryID != null) && (this.entryID.trim().length() > 0))
      {
        localACHEntry = localACHBatch.getACHEntries().getByID(this.entryID);
        if (localACHEntry != null)
        {
          localACHPayee = localACHEntry.getAchPayee();
          localACHEntry.setAction("MOD");
        }
      }
      else
      {
        localACHEntry = (ACHEntry)localACHBatch.create();
        if (localACHEntry != null)
        {
          this.entryID = localACHEntry.getID();
          localACHEntry.setAmount("0.00");
          localACHPayee.setScope("ACHBatch");
          localACHPayee.setCompanyID(localACHBatch.getCoID());
          localACHEntry.setAchPayee(localACHPayee);
          localACHEntry.setAction("ADD");
          localACHEntry.setAmountIsDebit((Boolean)null);
        }
      }
      paramHttpSession.setAttribute("ACHPayee", localACHPayee);
      this.origPayee = ((ACHPayee)localACHPayee.clone());
      set(localACHEntry);
    }
    return str;
  }
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = null;
    if (!this.parameters.isEmpty())
    {
      if (processParameters(paramHttpSession)) {
        return this.successURL;
      }
      if (this.error != 0) {
        return this.taskErrorURL;
      }
    }
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = modifyACHEntry(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean processParameters(HttpSession paramHttpSession)
  {
    this.error = 0;
    boolean bool = true;
    Iterator localIterator = this.parameters.keySet().iterator();
    resetValidationErrors();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)this.parameters.get(str1);
      bool &= processParameter(paramHttpSession, str1, str2);
    }
    if (this.error != 0) {
      bool = false;
    }
    this.parameters.clear();
    return bool;
  }
  
  protected boolean processParameter(HttpSession paramHttpSession, String paramString1, String paramString2)
  {
    return false;
  }
  
  public String modifyACHEntry(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.error = 0;
    boolean bool = false;
    if ((this.batchName == null) || (paramHttpSession.getAttribute(this.batchName) == null))
    {
      this.error = 16002;
      str = this.taskErrorURL;
    }
    else
    {
      ACHBatch localACHBatch = null;
      Object localObject1 = paramHttpSession.getAttribute(this.batchName);
      if ((localObject1 instanceof ACHBatch))
      {
        localACHBatch = (ACHBatch)localObject1;
      }
      else if ((localObject1 instanceof FundsTransactionTemplate))
      {
        localObject2 = (FundsTransactionTemplate)localObject1;
        localACHBatch = (ACHBatch)((FundsTransactionTemplate)localObject2).getFundsTransaction();
      }
      if ((localACHBatch instanceof ModifyACHBatch)) {
        bool = ((ModifyACHBatch)localACHBatch).getIsTemplateValue();
      }
      Object localObject2 = localACHBatch.getACHEntries().getByID(this.entryID);
      ACHPayee localACHPayee = (ACHPayee)paramHttpSession.getAttribute(this.payeeName);
      Object localObject3;
      if ((localACHBatch.getBatchTypeValue() == 3) && (getOffsetAccountID() != null) && (getOffsetAccountID().length() > 0))
      {
        localObject3 = (ACHOffsetAccounts)paramHttpSession.getAttribute("ACHOffsetAccounts");
        ACHOffsetAccount localACHOffsetAccount = ((ACHOffsetAccounts)localObject3).getByID(getOffsetAccountID());
        if (localACHOffsetAccount != null)
        {
          setOffsetAccountBankID(localACHOffsetAccount.getRoutingNum());
          setOffsetAccountName(localACHOffsetAccount.getNickName());
          setOffsetAccountNumber(localACHOffsetAccount.getNumber());
          setOffsetAccountType(localACHOffsetAccount.getTypeValue());
        }
      }
      if (localObject2 != null)
      {
        ((ACHEntry)localObject2).set(this);
        if (localACHPayee != null)
        {
          if ((bool) && ("ACHTemplate".equals(this.origPayee.getScope())) && (this.origPayee.getScope().equals(localACHPayee.getScope())) && (!localACHPayee.equals(this.origPayee))) {
            localACHPayee.setScope("ACHBatch");
          }
          localObject3 = (ACHPayee)localACHPayee.clone();
          if ((localACHBatch.getStandardEntryClassCodeValue() == 2) && (((ACHPayee)localObject3).getName() != null) && (((ACHPayee)localObject3).getName().length() > 15)) {
            ((ACHPayee)localObject3).setName(((ACHPayee)localObject3).getName().substring(0, 15));
          }
          ((ACHEntry)localObject2).setAchPayee((ACHPayee)localObject3);
          int i = 0;
          if ("MOD".equals(((ACHEntry)localObject2).getAction()))
          {
            if ((!"ACHCompany".equals(localACHPayee.getScope())) && (("PRENOTE_REQUESTED".equalsIgnoreCase(localACHPayee.getPrenoteStatus())) || ("PRENOTE_PENDING".equalsIgnoreCase(localACHPayee.getPrenoteStatus()))))
            {
              if ((localACHPayee.getName() != null) && (!localACHPayee.getName().equalsIgnoreCase(this.origPayee.getName()))) {
                i = 1;
              }
              if ((localACHPayee.getRoutingNumber() != null) && (!localACHPayee.getRoutingNumber().equalsIgnoreCase(this.origPayee.getRoutingNumber()))) {
                i = 1;
              }
              if ((localACHPayee.getAccountNumberNoSpaces() != null) && (!localACHPayee.getAccountNumberNoSpaces().equalsIgnoreCase(this.origPayee.getAccountNumberNoSpaces()))) {
                i = 1;
              }
              if (localACHPayee.getAccountTypeValue() != this.origPayee.getAccountTypeValue()) {
                i = 1;
              }
              if ("PRENOTE_REQUESTED".equalsIgnoreCase(localACHPayee.getPrenoteStatus())) {
                i = 1;
              }
            }
          }
          else if (("ADD".equals(((ACHEntry)localObject2).getAction())) && (!localACHPayee.getScope().equals("ACHCompany")) && ("PRENOTE_REQUESTED".equalsIgnoreCase(localACHPayee.getPrenoteStatus()))) {
            i = 1;
          }
          if ((!bool) && ("PRENOTE_PENDING".equalsIgnoreCase(localACHPayee.getPrenoteStatus())))
          {
            i = 1;
            if (localACHPayee.getPrenoteSubmitDateValue() != null)
            {
              DateTime localDateTime = (DateTime)localACHPayee.getPrenoteSubmitDateValue().clone();
              int j = 6;
              while (j > 0)
              {
                localDateTime.add(5, 1);
                int k = localDateTime.get(7);
                if ((k != 7) && (k != 1))
                {
                  Date localDate = null;
                  try
                  {
                    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
                    localDate = ACH.getSmartDate(localSecureUser, localDateTime);
                  }
                  catch (CSILException localCSILException) {}
                  j--;
                  localDateTime.setTime(localDate);
                }
              }
              if (localDateTime.before(localACHBatch.getDateValue())) {
                i = 0;
              }
            }
          }
          if (i != 0)
          {
            ((ACHEntry)localObject2).setActive("false");
            ((ACHEntry)localObject2).setAction("PRENOTE");
          }
        }
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    int i = 0;
    boolean bool = false;
    ACHBatch localACHBatch = null;
    if ((this.validate != null) && (this.validate.length() > 0))
    {
      Object localObject2;
      if ((this.batchName == null) || (paramHttpSession.getAttribute(this.batchName) == null))
      {
        this.error = 16002;
      }
      else
      {
        localObject1 = paramHttpSession.getAttribute(this.batchName);
        if ((localObject1 instanceof ACHBatch))
        {
          localACHBatch = (ACHBatch)localObject1;
        }
        else if ((localObject1 instanceof FundsTransactionTemplate))
        {
          localObject2 = (FundsTransactionTemplate)localObject1;
          localACHBatch = (ACHBatch)((FundsTransactionTemplate)localObject2).getFundsTransaction();
        }
        if (localACHBatch != null)
        {
          i = localACHBatch.getStandardEntryClassCodeValue();
          if ((localACHBatch instanceof ModifyACHBatch)) {
            bool = ((ModifyACHBatch)localACHBatch).getIsTemplateValue();
          }
        }
      }
      Object localObject1 = (ACHPayee)paramHttpSession.getAttribute(this.payeeName);
      if ((this.error == 0) && (this.validate.indexOf("ACHENTRY") != -1) && (localACHBatch != null))
      {
        localObject2 = getAchPayee();
        setAchPayee((ACHPayee)localObject1);
        validate(i, true, localACHBatch.getBatchTypeValue() == 3, bool, false);
        setAchPayee((ACHPayee)localObject2);
      }
      Object localObject3;
      if ((this.error == 0) && (this.validate.indexOf("ROUTINGNUM") != -1))
      {
        localObject2 = new FinancialInstitution();
        localObject3 = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        FinancialInstitutions localFinancialInstitutions = null;
        try
        {
          ((FinancialInstitution)localObject2).setAchRoutingNumber(((ACHPayee)localObject1).getRoutingNumber());
          localFinancialInstitutions = ACH.getFinancialInstitutions((SecureUser)localObject3, (FinancialInstitution)localObject2, 5, new HashMap());
        }
        catch (Exception localException) {}
        if ((localFinancialInstitutions != null) && (localFinancialInstitutions.size() > 0))
        {
          if (((((ACHPayee)localObject1).getBankName() == null) || (((ACHPayee)localObject1).getBankName().trim().length() == 0)) && (localFinancialInstitutions.size() == 1)) {
            ((ACHPayee)localObject1).setBankName(((FinancialInstitution)localFinancialInstitutions.get(0)).getInstitutionName());
          }
        }
        else {
          this.error = 16183;
        }
      }
      if ((this.error == 0) && (this.validate.indexOf("NAME") != -1) && (localObject1 != null) && (((ACHPayee)localObject1).getAddToListValue() == true))
      {
        localObject2 = (ACHPayees)paramHttpSession.getAttribute(this.payeesName);
        localObject3 = ((ACHPayees)localObject2).isPayeeUnique((ACHPayee)localObject1);
        if (localObject3 != null)
        {
          this.error = 16113;
          ((ACHPayee)localObject1).set((ACHPayee)localObject3);
        }
      }
      if ((this.error == 0) && (getValidationErrors() != null)) {
        this.error = 16159;
      }
    }
    return this.error == 0;
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public void setPayeesName(String paramString)
  {
    this.payeesName = paramString;
  }
  
  public void setEntryID(String paramString)
  {
    this.entryID = paramString;
  }
  
  public void setBatchName(String paramString)
  {
    this.batchName = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public String getSuccessURL()
  {
    return this.successURL;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getTaskErrorURL()
  {
    return this.taskErrorURL;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getServiceErrorURL()
  {
    return this.serviceErrorURL;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public static long getTaskTimeoutValue(ServletContext paramServletContext)
  {
    long l = 120L;
    try
    {
      String str = (String)paramServletContext.getAttribute("TaskTimeoutSecs");
      if (str != null) {
        l = Long.parseLong(str);
      }
      if (l == 0L) {
        l = 120L;
      }
    }
    catch (Throwable localThrowable) {}
    return l * 1000L;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    if (!"".equals(paramString)) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHEntry
 * JD-Core Version:    0.7.0.1
 */