package com.ffusion.tasks.ach;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.handlers.ACH;
import com.ffusion.tasks.banking.AddFundsTransactionTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddACHTemplate
  extends AddFundsTransactionTemplate
  implements Task
{
  protected ACHEntry currentEntry;
  protected ACHPayees achPayees = null;
  protected String payeesCollection = "ACHPayees";
  protected String achBatchName = null;
  protected String fromBatchName = null;
  protected boolean saveAsTemplate = false;
  protected String fundsType = "ACH";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else if (validateInput(localHttpSession))
    {
      if (this.processFlag)
      {
        ((ACHBatch)getFundsTransaction()).setName(getTemplateName());
        if (this.achBatchName != null)
        {
          ACHBatch localACHBatch1 = (ACHBatch)localHttpSession.getAttribute(this.achBatchName);
          ACHBatch localACHBatch2 = null;
          if (this.fromBatchName != null) {
            localACHBatch2 = (ACHBatch)localHttpSession.getAttribute(this.fromBatchName);
          }
          SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
          ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute(this.payeesCollection);
          this.error = processACHTemplatePayees(localSecureUser, localACHBatch1, localACHBatch2, localACHPayees);
          if (this.error != 0)
          {
            str = this.taskErrorURL;
            return str;
          }
          if (localACHBatch1 != null) {
            setFundsTransaction(localACHBatch1);
          }
        }
        str = addFundsTransactionTemplate(localHttpSession);
      }
    }
    else if (this.error != 0) {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public static int processACHTemplatePayees(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, ACHPayees paramACHPayees)
  {
    int i = 0;
    if ((paramACHBatch1 == null) || ((paramACHBatch1 instanceof ModifyACHTaxPayment))) {
      return i;
    }
    ACHPayees localACHPayees = new ACHPayees();
    ACHEntries localACHEntries = paramACHBatch1.getACHEntries();
    Iterator localIterator = localACHEntries.iterator();
    ACHEntry localACHEntry;
    ACHPayee localACHPayee;
    while (localIterator.hasNext())
    {
      localACHEntry = (ACHEntry)localIterator.next();
      if ((paramACHBatch1.getStandardEntryClassCodeValue() == 1) || (paramACHBatch1.getStandardEntryClassCodeValue() == 5)) {
        localACHEntry.setCheckSerialNumber(null);
      }
      localACHPayee = localACHEntry.getAchPayee();
      if ((localACHPayee != null) && (localACHPayee.getScope().equals("ACHBatch")))
      {
        if ((paramACHBatch1.getStandardEntryClassCodeValue() == 18) && ((localACHPayee.getName() == null) || (localACHPayee.getName().length() == 0))) {
          localACHPayee.setName("XCK Freeform Payee");
        }
        localACHPayee.setScope("ACHTemplate");
        if (localACHEntry.isAmountIsDebit()) {
          localACHPayee.setPrenoteDemand("Debit");
        } else {
          localACHPayee.setPrenoteDemand("Credit");
        }
        localACHPayee.setCompanyID(paramACHBatch1.getCoID());
        localACHPayee.set("Entry_ID", localACHEntry.getID());
        if (localACHPayee.getAddToListValue() == true)
        {
          localACHPayee.setAddToList(false);
          localACHPayee.setScope("ACHCompany");
        }
        localACHPayees.add(localACHPayee);
      }
      if ((localACHPayee != null) && ("BUSINESS".equalsIgnoreCase(paramACHBatch1.getBatchScope())) && (localACHPayee.getPayeeGroupValue() == 1))
      {
        i = 16522;
        localACHEntry.addValidationError(localACHEntry.getImportError("UserScopeInBusinessTemplate"), null);
      }
      else
      {
        localACHEntry.resetValidationErrors();
      }
    }
    if ((localACHPayees.size() > 0) && (i == 0))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("ACHPayees", localACHPayees);
      localHashMap.put("ACHSECCode", paramACHBatch1.getStandardEntryClassCode());
      try
      {
        ACH.addACHPayee(paramSecureUser, null, localHashMap);
        localIterator = localACHPayees.iterator();
        String str = null;
        while (localIterator.hasNext())
        {
          localACHPayee = (ACHPayee)localIterator.next();
          if ("ACHCompany".equals(localACHPayee.getScope())) {
            paramACHPayees.add(localACHPayee);
          }
          str = (String)localACHPayee.get("Entry_ID");
          if ((str != null) && (paramACHBatch2 != null))
          {
            localACHEntry = paramACHBatch2.getByID(str);
            if (localACHEntry != null) {
              localACHEntry.setAchPayee(localACHPayee);
            }
          }
        }
      }
      catch (CSILException localCSILException)
      {
        i = 16519;
        localIterator = localACHPayees.iterator();
        while (localIterator.hasNext())
        {
          localACHPayee = (ACHPayee)localIterator.next();
          if ("PRENOTE_PENDING".equals(localACHPayee.getPrenoteStatus())) {
            localACHPayee.setPrenoteStatus("PRENOTE_REQUESTED");
          }
          if ("ACHCompany".equals(localACHPayee.getScope())) {
            localACHPayee.setAddToList(true);
          }
          localACHPayee.setScope("ACHBatch");
        }
        localCSILException.printStackTrace();
      }
    }
    return i;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.type = 3;
    super.initProcess(paramHttpServletRequest, paramHttpSession);
    Object localObject = null;
    if (this.fundsType.toUpperCase().indexOf("TAX") >= 0)
    {
      localObject = new ModifyACHTaxPayment();
      ((ModifyACHBatch)localObject).set((ACHBatch)getFundsTransaction());
      ((ModifyACHBatch)localObject).setType(12);
      ((ModifyACHBatch)localObject).setACHType("TaxPayment");
      set((FundsTransaction)localObject);
      localObject = (ModifyACHTaxPayment)getFundsTransaction();
    }
    else if (this.fundsType.toUpperCase().indexOf("CHILD") >= 0)
    {
      localObject = new ModifyChildSupportPayment();
      ((ModifyACHBatch)localObject).set((ACHBatch)getFundsTransaction());
      ((ModifyACHBatch)localObject).setType(17);
      ((ModifyACHBatch)localObject).setACHType("ChildSupportPayment");
      set((FundsTransaction)localObject);
      localObject = (ModifyChildSupportPayment)getFundsTransaction();
    }
    else
    {
      localObject = new ModifyACHBatch();
      ((ModifyACHBatch)localObject).set((ACHBatch)getFundsTransaction());
      ((ModifyACHBatch)localObject).setType(9);
      ((ModifyACHBatch)localObject).setACHType("ACHBatch");
      set((FundsTransaction)localObject);
      localObject = (ModifyACHBatch)getFundsTransaction();
    }
    ((ModifyACHBatch)localObject).setIsTemplate("true");
    if (this.fromBatchName != null) {
      ((ModifyACHBatch)localObject).set((ACHBatch)paramHttpSession.getAttribute(this.fromBatchName));
    }
    int i = 1;
    Business localBusiness = (Business)paramHttpSession.getAttribute("Business");
    if ((localBusiness != null) && (localBusiness.getACHBatchTypeValue() != 0)) {
      i = localBusiness.getACHBatchTypeValue();
    }
    ACHCompany localACHCompany = (ACHCompany)paramHttpSession.getAttribute("ACHCOMPANY");
    if ((localACHCompany != null) && (localACHCompany.getACHBatchTypeValue() != 0)) {
      i = localACHCompany.getACHBatchTypeValue();
    }
    ((ModifyACHBatch)localObject).setBatchType(i);
    if (!this.saveAsTemplate) {
      paramHttpSession.setAttribute("ACHEntries", ((ModifyACHBatch)localObject).getACHEntries());
    }
    this.achPayees = ((ACHPayees)paramHttpSession.getAttribute(this.payeesCollection));
    if (this.achBatchName != null) {
      paramHttpSession.setAttribute(this.achBatchName, localObject);
    }
    return this.successURL;
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
  
  public void setFromBatchName(String paramString)
  {
    this.fromBatchName = paramString;
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
  
  public void setSaveAsTemplate(String paramString)
  {
    this.saveAsTemplate = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setFundsType(String paramString)
  {
    this.fundsType = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddACHTemplate
 * JD-Core Version:    0.7.0.1
 */