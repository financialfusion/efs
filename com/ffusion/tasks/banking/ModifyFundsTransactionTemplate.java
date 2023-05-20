package com.ffusion.tasks.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyFundsTransactionTemplate
  extends FundsTransactionTemplate
  implements Task
{
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected String validate;
  protected String originalName;
  protected String sessionName = null;
  protected String accountsCollection = null;
  protected int type = 1;
  protected String payeeID = null;
  protected boolean businessTemplate = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processModifyFundsTransactionTemplate(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    if (paramHttpSession.getAttribute("FundsTransactionTemplate") == null)
    {
      this.error = 1033;
    }
    else
    {
      this.error = 0;
      set((FundsTransactionTemplate)paramHttpSession.getAttribute("FundsTransactionTemplate"));
      this.originalName = getTemplateName();
    }
    return this.error == 0;
  }
  
  protected String processModifyFundsTransactionTemplate(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = ModifyFundsTransactionTemplate(paramHttpSession);
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
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if (this.sessionName == null)
      {
        bool = false;
        this.error = 1034;
      }
      if (bool) {
        bool = validateTemplateName(paramHttpSession);
      }
      Object localObject;
      if (this.type == 1)
      {
        localObject = (RecTransfer)getFundsTransaction();
        if ((bool) && (this.validate.indexOf("AMOUNT") != -1)) {
          bool = ((RecTransfer)localObject).getAmountValue() != null;
        }
        if ((bool) && (this.validate.indexOf("FREQUENCY") != -1) && ((((RecTransfer)localObject).getFrequencyValue() < 0) || (((RecTransfer)localObject).getFrequencyValue() > 10)))
        {
          this.error = 1011;
          bool = false;
        }
        if ((bool) && (this.validate.indexOf("NUMBERTRANSFERS") != -1)) {
          if (((RecTransfer)localObject).getNumberTransfersValue() > 1)
          {
            this.error = 0;
          }
          else
          {
            this.error = 1012;
            bool = false;
          }
        }
        if ((bool) && (this.validate.indexOf("FROMACCOUNTID") != -1))
        {
          ((RecTransfer)localObject).setFromAccount(null);
          bool = validateAccount(paramHttpSession, ((RecTransfer)localObject).getFromAccountID(), 1007);
        }
        if ((bool) && (this.validate.indexOf("TOACCOUNTID") != -1))
        {
          ((RecTransfer)localObject).setToAccount(null);
          bool = validateAccount(paramHttpSession, ((RecTransfer)localObject).getToAccountID(), 1008);
        }
        if ((bool) && ((this.validate.indexOf("FROMACCOUNTID") != -1) || (this.validate.indexOf("TOACCOUNTID") != -1)) && (((RecTransfer)localObject).getFromAccountID().equals(((RecTransfer)localObject).getToAccountID())))
        {
          this.error = 1016;
          bool = false;
        }
      }
      else if (this.type == 2)
      {
        localObject = (RecPayment)getFundsTransaction();
        if ((bool) && (this.validate.indexOf("ACCOUNTID") != -1)) {
          bool = validateAccount(paramHttpSession, ((RecPayment)localObject).getAccountID(), 2007);
        }
        if ((bool) && (this.validate.indexOf("PAYEE") != -1) && (this.payeeID == null) && (((RecPayment)localObject).getPayee() == null))
        {
          this.error = 2008;
          bool = false;
        }
        if ((bool) && (this.validate.indexOf("NUMBERPAYMENTS") != -1)) {
          if (((RecPayment)localObject).getNumberPaymentsValue() > 1)
          {
            this.error = 0;
          }
          else
          {
            this.error = 2022;
            bool = false;
          }
        }
        if ((bool) && (this.validate.indexOf("FREQUENCY") != -1) && ((((RecPayment)localObject).getFrequencyValue() < 0) || (((RecPayment)localObject).getFrequencyValue() > 10)))
        {
          this.error = 1011;
          bool = false;
        }
      }
      else if (this.type == 3)
      {
        localObject = (ACHBatch)getFundsTransaction();
        if ((bool) && (this.validate.indexOf("NUMBERTRANSFERS") != -1)) {
          if (((ACHBatch)localObject).getNumberPaymentsValue() > 1)
          {
            this.error = 0;
          }
          else
          {
            this.error = 16120;
            bool = false;
          }
        }
        if ((bool) && (this.validate.indexOf("FREQUENCY") != -1) && ((((ACHBatch)localObject).getFrequencyValue() < 0) || (((ACHBatch)localObject).getFrequencyValue() > 10)))
        {
          this.error = 1011;
          bool = false;
        }
      }
      else if (this.type == 4)
      {
        localObject = (WireTransfer)getFundsTransaction();
        if ((bool) && (this.validate.indexOf("AMOUNT") != -1))
        {
          Currency localCurrency = ((WireTransfer)localObject).getAmountValue();
          if ((localCurrency == null) || (localCurrency.doubleValue() == 0.0D))
          {
            this.error = 1009;
            bool = false;
          }
        }
        if ((bool) && (this.validate.indexOf("FREQUENCY") != -1) && ((((WireTransfer)localObject).getFrequencyValue() < 0) || (((WireTransfer)localObject).getFrequencyValue() > 10)))
        {
          this.error = 1011;
          bool = false;
        }
        if ((bool) && (this.validate.indexOf("NUMBERTRANSFERS") != -1) && (((WireTransfer)localObject).getType() == 6) && (((WireTransfer)localObject).getNumberTransfersValue() < 2))
        {
          this.error = 1012;
          bool = false;
        }
        if ((bool) && (this.validate.indexOf("FROMACCOUNTID") != -1)) {
          bool = validateAccount(paramHttpSession, ((WireTransfer)localObject).getFromAccountID(), 1007);
        }
        if ((bool) && (this.validate.indexOf("WIRE_PAYEE") != -1) && ((((WireTransfer)localObject).getWirePayee() == null) || (((WireTransfer)localObject).getWirePayee().getPayeeName().trim().length() == 0)))
        {
          this.error = 12016;
          bool = false;
        }
      }
      this.validate = null;
    }
    return bool;
  }
  
  protected boolean validateAccount(HttpSession paramHttpSession, String paramString, int paramInt)
  {
    this.error = 0;
    if ((paramString == null) || (paramString.length() == 0))
    {
      this.error = paramInt;
    }
    else
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.accountsCollection);
      if (localAccounts == null)
      {
        this.error = 1001;
      }
      else
      {
        localAccounts.setFilter("All");
        Account localAccount = localAccounts.getByID(paramString);
        if (localAccount == null) {
          this.error = paramInt;
        }
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateTemplateName(HttpSession paramHttpSession)
  {
    String str = getTemplateName();
    if ((str == null) || (str.length() == 0))
    {
      this.error = 1036;
    }
    else
    {
      FundsTransactionTemplates localFundsTransactionTemplates = (FundsTransactionTemplates)paramHttpSession.getAttribute(this.sessionName);
      if ((getTemplateGroup() == null) && (localFundsTransactionTemplates != null) && (localFundsTransactionTemplates.getByTemplateName(getTemplateName()) != null))
      {
        if ((this.originalName == null) || (!this.originalName.equalsIgnoreCase(str))) {
          this.error = 1038;
        }
      }
      else if ((getTemplateGroup() != null) && (("CHILD".equalsIgnoreCase(getTemplateGroup())) || ("TAX".equalsIgnoreCase(getTemplateGroup()))) && (localFundsTransactionTemplates != null) && (localFundsTransactionTemplates.getByTemplateName_Group(getTemplateName(), getTemplateGroup()) != null) && ((this.originalName == null) || (!this.originalName.equalsIgnoreCase(str)))) {
        this.error = 1038;
      }
    }
    return this.error == 0;
  }
  
  protected String ModifyFundsTransactionTemplate(HttpSession paramHttpSession)
  {
    String str = null;
    if (paramHttpSession.getAttribute("FundsTransactionTemplate") == null)
    {
      this.error = 1033;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = 0;
      Object localObject = paramHttpSession.getAttribute("FundsTransactionTemplate");
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        templateFixupByType(paramHttpSession);
        if (this.businessTemplate) {
          localHashMap.put("TemplateType", "Business");
        }
        FundsTransactionTemplate localFundsTransactionTemplate = Banking.modifyFundsTransactionTemplate(localSecureUser, this, localHashMap);
        set(localFundsTransactionTemplate);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0)
      {
        ((FundsTransactionTemplate)localObject).set(this);
        str = this.successURL;
      }
      else
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
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
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
  
  public String getSessionName()
  {
    return this.sessionName;
  }
  
  public void setType(String paramString)
  {
    try
    {
      this.type = new Integer(paramString).intValue();
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setAccountsCollection(String paramString)
  {
    this.accountsCollection = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  protected void templateFixupByType(HttpSession paramHttpSession)
  {
    l(paramHttpSession);
    k(paramHttpSession);
    if (this.type == 3)
    {
      ACHEntries localACHEntries = ((ACHBatch)getFundsTransaction()).getACHEntries();
      int i = 0;
      for (int j = 0; j < localACHEntries.size(); j++)
      {
        ACHEntry localACHEntry = (ACHEntry)localACHEntries.get(j);
        String str = localACHEntry.getAchPayeeID();
        i = 0;
        if ((localACHEntry.getAchPayee() != null) && (localACHEntry.getAchPayeeScope() != null) && ((localACHEntry.getAchPayeeScope().equalsIgnoreCase("ACHBatch")) || (localACHEntry.getAchPayeeScope().equalsIgnoreCase("ACHTemplate")))) {
          i = 1;
        }
        if (i == 0)
        {
          localACHEntry.setAchPayee(null);
          localACHEntry.setAchPayeeID(str);
        }
      }
    }
  }
  
  private void k(HttpSession paramHttpSession)
  {
    if (this.accountsCollection != null)
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.accountsCollection);
      FundsTransaction localFundsTransaction = getFundsTransaction();
      Object localObject;
      if (this.type == 1)
      {
        localObject = (RecTransfer)localFundsTransaction;
        ((RecTransfer)localObject).setFromAccount(null);
        ((RecTransfer)localObject).setToAccount(null);
        ((RecTransfer)localObject).setFromAccount(localAccounts.getByID(((RecTransfer)localObject).getFromAccountID()));
        ((RecTransfer)localObject).setToAccount(localAccounts.getByID(((RecTransfer)localObject).getToAccountID()));
      }
      else if (this.type == 2)
      {
        localObject = (RecPayment)localFundsTransaction;
        ((RecPayment)localObject).setAccount(null);
        ((RecPayment)localObject).setAccount(localAccounts.getByID(((RecPayment)localObject).getAccountID()));
      }
      else if (this.type == 3)
      {
        localObject = (ACHBatch)localFundsTransaction;
      }
      else if (this.type != 4) {}
    }
  }
  
  private void l(HttpSession paramHttpSession)
  {
    FundsTransaction localFundsTransaction = getFundsTransaction();
    if ((this.type == 2) && (this.payeeID != null))
    {
      RecPayment localRecPayment = (RecPayment)localFundsTransaction;
      Payees localPayees = (Payees)paramHttpSession.getAttribute("Payees");
      if (localPayees != null) {
        localRecPayment.setPayee(localPayees.getByID(this.payeeID));
      }
    }
  }
  
  public String getPayeeID()
  {
    return this.payeeID;
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeID = paramString;
  }
  
  public void setBusinessTemplate(String paramString)
  {
    this.businessTemplate = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.ModifyFundsTransactionTemplate
 * JD-Core Version:    0.7.0.1
 */