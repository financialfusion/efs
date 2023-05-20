package com.ffusion.tasks.ach;

import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteACHPayee
  extends BaseTask
  implements Task
{
  protected String payeeID;
  protected String validate = null;
  protected boolean processFlag = false;
  protected boolean checkForOutstandingPayments = true;
  protected String templatesSessionName = "ACHBatchTemplates";
  protected String businessTemplatesSessionName = "ACHBusinessTemplates";
  
  public void setCheckForOutstandingPayments(String paramString)
  {
    if (paramString.equalsIgnoreCase("false")) {
      this.checkForOutstandingPayments = false;
    } else {
      this.checkForOutstandingPayments = true;
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute("ACHPayees");
    String str = null;
    this.error = 0;
    if (localACHPayees == null)
    {
      this.error = 16150;
      return this.taskErrorURL;
    }
    ACHPayee localACHPayee = localACHPayees.getByID(this.payeeID);
    if (localACHPayee == null)
    {
      this.error = 16152;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if ((localACHPayee.getSecurePayeeValue() == true) && (localACHPayee.getSubmittedBy() != null) && (!localACHPayee.getSubmittedBy().equals("" + localSecureUser.getProfileID())))
    {
      this.error = 16180;
      return this.taskErrorURL;
    }
    this.error = validateInput(localHttpSession, localACHPayee);
    if ((this.error == 0) && ("ACHCompany".equals(localACHPayee.getScope()))) {
      validatePayeeNotUsed(localHttpSession, localACHPayee);
    }
    if (this.error == 0)
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        this.error = doProcess(localHttpSession, localACHPayee);
        if (this.error == 0) {
          str = this.successURL;
        } else {
          str = this.serviceErrorURL;
        }
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
  
  protected int validateInput(HttpSession paramHttpSession, ACHPayee paramACHPayee)
  {
    int i = 0;
    if (this.validate != null)
    {
      if (paramACHPayee == null) {
        i = 16152;
      }
      this.validate = null;
    }
    return i;
  }
  
  protected void validatePayeeNotUsed(HttpSession paramHttpSession, ACHPayee paramACHPayee)
  {
    int i = 0;
    FundsTransactionTemplates localFundsTransactionTemplates = new FundsTransactionTemplates();
    Object localObject;
    if (paramHttpSession.getAttribute(this.templatesSessionName) != null)
    {
      localObject = (FundsTransactionTemplates)paramHttpSession.getAttribute(this.templatesSessionName);
      localFundsTransactionTemplates.addAll((Collection)localObject);
    }
    if (paramHttpSession.getAttribute(this.businessTemplatesSessionName) != null)
    {
      localObject = (FundsTransactionTemplates)paramHttpSession.getAttribute(this.businessTemplatesSessionName);
      localFundsTransactionTemplates.addAll((Collection)localObject);
    }
    if (localFundsTransactionTemplates.size() > 0)
    {
      localFundsTransactionTemplates.setFilter("All");
      localObject = localFundsTransactionTemplates.iterator();
      while ((((Iterator)localObject).hasNext()) && (i == 0))
      {
        FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)((Iterator)localObject).next();
        if ((localFundsTransactionTemplate.getFundsTransaction() instanceof ACHBatch))
        {
          ACHBatch localACHBatch = (ACHBatch)localFundsTransactionTemplate.getFundsTransaction();
          if (localACHBatch.getTaxFormID() == null)
          {
            ACHEntries localACHEntries = localACHBatch.getACHEntries();
            Iterator localIterator = localACHEntries.iterator();
            while ((localIterator.hasNext()) && (i == 0))
            {
              ACHEntry localACHEntry = (ACHEntry)localIterator.next();
              if ((localACHEntry != null) && (localACHEntry.getAchPayeeID() != null) && (localACHEntry.getAchPayeeID().equals(paramACHPayee.getID()))) {
                i = 1;
              }
            }
          }
        }
      }
      if (i != 0) {
        this.error = 16521;
      }
    }
  }
  
  public int doProcess(HttpSession paramHttpSession, ACHPayee paramACHPayee)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      ACH.deleteACHPayee(localSecureUser, paramACHPayee, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    if (this.error == 0)
    {
      ACHPayees localACHPayees = (ACHPayees)paramHttpSession.getAttribute("ACHPayees");
      paramACHPayee = localACHPayees.getByID(paramACHPayee.getID());
      localACHPayees.remove(paramACHPayee);
      paramHttpSession.removeAttribute("ACHPayee");
    }
    return this.error;
  }
  
  public void setPayeeID(String paramString)
  {
    this.payeeID = paramString;
  }
  
  public void setTemplatesSessionName(String paramString)
  {
    this.templatesSessionName = paramString;
  }
  
  public void setBusinessTemplatesSessionName(String paramString)
  {
    this.businessTemplatesSessionName = paramString;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.DeleteACHPayee
 * JD-Core Version:    0.7.0.1
 */