package com.ffusion.tasks.banking;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.RecTransfers;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddFundsTransactionTemplate
  extends ModifyFundsTransactionTemplate
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else {
      str = addFundsTransactionTemplate(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    Object localObject1 = null;
    Object localObject2;
    if (this.type == 1)
    {
      localObject2 = new RecTransfers();
      localObject1 = (RecTransfer)((RecTransfers)localObject2).createNoAdd();
    }
    else if (this.type == 2)
    {
      localObject2 = new RecPayments();
      localObject1 = (RecPayment)((RecPayments)localObject2).createNoAdd();
    }
    else if (this.type == 3)
    {
      localObject2 = new ACHBatches();
      localObject1 = (ACHBatch)((ACHBatches)localObject2).createNoAdd();
      ((FundsTransaction)localObject1).setAmount("0");
    }
    else if (this.type == 4)
    {
      localObject2 = new WireTransfers();
      localObject1 = (WireTransfer)((WireTransfers)localObject2).createNoAdd();
    }
    set((FundsTransaction)localObject1);
    return this.successURL;
  }
  
  protected String addFundsTransactionTemplate(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        try
        {
          processAddFundsTransactionTemplate(paramHttpSession);
          if (this.error != 0) {
            str = this.serviceErrorURL;
          }
        }
        catch (Exception localException)
        {
          this.error = 1;
          str = this.serviceErrorURL;
        }
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected void processAddFundsTransactionTemplate(HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      templateFixupByType(paramHttpSession);
      if (this.businessTemplate) {
        localHashMap.put("TemplateType", "Business");
      }
      FundsTransactionTemplate localFundsTransactionTemplate1 = Banking.addFundsTransactionTemplate(localSecureUser, this, localHashMap);
      set(localFundsTransactionTemplate1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      FundsTransactionTemplates localFundsTransactionTemplates = (FundsTransactionTemplates)paramHttpSession.getAttribute(this.sessionName);
      if (localFundsTransactionTemplates != null)
      {
        FundsTransactionTemplate localFundsTransactionTemplate2 = localFundsTransactionTemplates.create();
        localFundsTransactionTemplate2.set(this);
        paramHttpSession.setAttribute("FundsTransactionTemplate", localFundsTransactionTemplate2);
      }
    }
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if (this.validate.indexOf("TEMPLATENAME") != -1) {
        bool = validateTemplateName(paramHttpSession);
      }
      if (this.error == 0) {
        bool = super.validateInput(paramHttpSession);
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.AddFundsTransactionTemplate
 * JD-Core Version:    0.7.0.1
 */