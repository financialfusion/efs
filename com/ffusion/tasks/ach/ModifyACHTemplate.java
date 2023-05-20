package com.ffusion.tasks.ach;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.banking.ModifyFundsTransactionTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHTemplate
  extends ModifyFundsTransactionTemplate
  implements Task
{
  protected ACHEntry currentEntry;
  protected String payeesCollection = "ACHPayees";
  protected String achBatchName = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    }
    else if ((validateInput(localHttpSession)) && (this.processFlag))
    {
      if (this.achBatchName != null)
      {
        ACHBatch localACHBatch = (ACHBatch)localHttpSession.getAttribute(this.achBatchName);
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute(this.payeesCollection);
        this.error = AddACHTemplate.processACHTemplatePayees(localSecureUser, localACHBatch, null, localACHPayees);
        if (this.error != 0)
        {
          str = this.taskErrorURL;
          return str;
        }
        if (localACHBatch != null) {
          setFundsTransaction(localACHBatch);
        }
      }
      ((ACHBatch)getFundsTransaction()).setName(getTemplateName());
      ((ACHBatch)getFundsTransaction()).setTemplateName(getTemplateName());
      str = processModifyFundsTransactionTemplate(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.error = 0;
    super.initProcess(paramHttpSession);
    Object localObject = null;
    String str1 = "ACH";
    if ((getFundsTransaction().getType() == 12) || (getFundsTransaction().getType() == 13)) {
      str1 = "TAX";
    }
    if ((getFundsTransaction().getType() == 17) || (getFundsTransaction().getType() == 18)) {
      str1 = "CHILD";
    }
    if (str1.toUpperCase().indexOf("TAX") >= 0)
    {
      localObject = new ModifyACHTaxPayment();
      ((ModifyACHBatch)localObject).set((ACHBatch)getFundsTransaction());
      set((FundsTransaction)localObject);
      localObject = (ModifyACHTaxPayment)getFundsTransaction();
    }
    else if (str1.toUpperCase().indexOf("CHILD") >= 0)
    {
      localObject = new ModifyChildSupportPayment();
      ((ModifyACHBatch)localObject).set((ACHBatch)getFundsTransaction());
      set((FundsTransaction)localObject);
      localObject = (ModifyChildSupportPayment)getFundsTransaction();
    }
    else
    {
      localObject = new ModifyACHBatch();
      ((ModifyACHBatch)localObject).set((ACHBatch)getFundsTransaction());
      set((FundsTransaction)localObject);
      localObject = (ModifyACHBatch)getFundsTransaction();
    }
    ((ModifyACHBatch)localObject).setIsTemplate("true");
    ((ModifyACHBatch)localObject).setName(getTemplateName());
    ((ModifyACHBatch)localObject).fixupEntriesAmount();
    paramHttpSession.setAttribute("ACHEntries", ((ModifyACHBatch)localObject).getACHEntries());
    ACHPayees localACHPayees = (ACHPayees)paramHttpSession.getAttribute(this.payeesCollection);
    ((ModifyACHBatch)localObject).addACHPayees(localACHPayees);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    getFreeFormTemplatePayees(localSecureUser, (ACHBatch)localObject);
    for (int i = 0; i < ((ModifyACHBatch)localObject).getACHEntries().size(); i++)
    {
      ACHEntry localACHEntry = (ACHEntry)((ModifyACHBatch)localObject).getACHEntries().get(i);
      String str2 = localACHEntry.getAchPayeeID();
      ACHPayee localACHPayee = localACHEntry.getAchPayee();
      if ((str2 != null) && (str2.length() > 0) && (localACHPayee == null))
      {
        this.error = 16523;
        break;
      }
    }
    if (this.achBatchName != null) {
      paramHttpSession.setAttribute(this.achBatchName, localObject);
    }
    return this.error == 0 ? this.successURL : this.taskErrorURL;
  }
  
  public static void getFreeFormTemplatePayees(SecureUser paramSecureUser, ACHBatch paramACHBatch)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject;
    for (int i = 0; i < paramACHBatch.getACHEntries().size(); i++)
    {
      localObject = (ACHEntry)paramACHBatch.getACHEntries().get(i);
      if ((paramACHBatch.getStandardEntryClassCodeValue() == 1) || (paramACHBatch.getStandardEntryClassCodeValue() == 5)) {
        ((ACHEntry)localObject).setCheckSerialNumber(null);
      }
      String str = ((ACHEntry)localObject).getAchPayeeID();
      ACHPayee localACHPayee = ((ACHEntry)localObject).getAchPayee();
      if ((str != null) && (str.length() > 0) && (localACHPayee != null) && ("ACHTemplate".equals(localACHPayee.getScope()))) {
        localArrayList.add(str);
      }
    }
    if (localArrayList.size() > 0)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("PayeeIDs", localArrayList);
      try
      {
        localObject = ACH.getACHPayees(paramSecureUser, null, null, localHashMap);
        paramACHBatch.addACHPayees((ACHPayees)localObject);
      }
      catch (CSILException localCSILException) {}
    }
  }
  
  public void setCurrentEntry(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.currentEntry = ((ACHBatch)getFundsTransaction()).getACHEntries().getByID(paramString);
    } else {
      this.currentEntry = null;
    }
  }
  
  public void setPayeesCollection(String paramString)
  {
    this.payeesCollection = paramString;
  }
  
  public void setAchBatchName(String paramString)
  {
    this.achBatchName = paramString;
  }
  
  public String getAchBatchName()
  {
    return this.achBatchName;
  }
  
  public void setEntryOffsetAccountNumber(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setOffsetAccountNumber(paramString);
    }
  }
  
  public void setEntryOffsetAccountType(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setOffsetAccountType(paramString);
    }
  }
  
  public void setEntryOffsetAccountBankID(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setOffsetAccountBankID(paramString);
    }
  }
  
  public void setEntryAmount(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setAmount(paramString);
    }
  }
  
  public void setEntryActive(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setActive(paramString);
    }
  }
  
  public void setEntryCheckSerialNumber(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setCheckSerialNumber(paramString);
    }
  }
  
  public void setEntryTerminalState(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setTerminalState(paramString);
    }
  }
  
  public void setEntryTerminalCity(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setTerminalCity(paramString);
    }
  }
  
  public void setEntryProcessControlField(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setProcessControlField(paramString);
    }
  }
  
  public void setEntryItemResearchNumber(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setItemResearchNumber(paramString);
    }
  }
  
  public void setEntryPaymentTypeCode(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setPaymentTypeCode(paramString);
    }
  }
  
  public void setEntryIdentificationNumber(String paramString)
  {
    if (this.currentEntry != null) {
      this.currentEntry.setIdentificationNumber(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ModifyACHTemplate
 * JD-Core Version:    0.7.0.1
 */