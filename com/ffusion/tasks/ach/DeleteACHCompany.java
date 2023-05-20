package com.ffusion.tasks.ach;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.handlers.Banking;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteACHCompany
  extends BaseTask
  implements Task
{
  protected String achCompaniesName = "ACHCOMPANIES";
  protected String achCompanyName = "ACHCOMPANY";
  protected String fiID;
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  private int zD = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    ACHCompany localACHCompany = (ACHCompany)localHttpSession.getAttribute(this.achCompanyName);
    if (localACHCompany == null)
    {
      this.error = 16506;
      str = this.taskErrorURL;
    }
    else if (validateInput(localHttpSession, localACHCompany))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = deleteACHCompany(localHttpSession, localACHCompany);
      }
      else
      {
        str = this.successURL;
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession, ACHCompany paramACHCompany)
  {
    boolean bool = true;
    if (this.validate != null) {
      this.validate = null;
    }
    return bool;
  }
  
  protected String deleteACHCompany(HttpSession paramHttpSession, ACHCompany paramACHCompany)
  {
    String str = this.successURL;
    this.error = 0;
    ACHCompanies localACHCompanies = (ACHCompanies)paramHttpSession.getAttribute(this.achCompaniesName);
    if (localACHCompanies == null)
    {
      this.error = 16505;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        ACH.deleteACHCompany(localSecureUser, paramACHCompany, this.fiID, localHashMap);
        boolean bool = EntitlementsUtil.removeEntitlementsAndLimitsForObjectUnsafe(getEntitlementGroupId(), "ACHCompany", paramACHCompany.getCompanyID());
        int i = 1007;
        localHashMap.put("TemplateType", "All");
        FundsTransactionTemplates localFundsTransactionTemplates = Banking.getFundTransactionTemplates(localSecureUser, i, localHashMap);
        if (localFundsTransactionTemplates != null)
        {
          Iterator localIterator = localFundsTransactionTemplates.iterator();
          while (localIterator.hasNext())
          {
            FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localIterator.next();
            FundsTransaction localFundsTransaction = localFundsTransactionTemplate.getFundsTransaction();
            if ((localFundsTransaction instanceof ACHBatch))
            {
              ACHBatch localACHBatch = (ACHBatch)localFundsTransaction;
              if ((paramACHCompany.getCompanyID().equals(localACHBatch.getCompanyID())) && (paramACHCompany.getID().equals(localACHBatch.getCoID()))) {
                try
                {
                  Banking.deleteFundsTransactionTemplate(localSecureUser, localFundsTransactionTemplate, localHashMap);
                }
                catch (Exception localException) {}
              }
            }
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException, paramHttpSession);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        localACHCompanies.removeByCompanyID(paramACHCompany.getCompanyID());
        paramHttpSession.removeAttribute(this.achCompanyName);
      }
    }
    return str;
  }
  
  public final void setACHCompaniesInSessionName(String paramString)
  {
    this.achCompaniesName = paramString;
  }
  
  public final String getACHCompaniesInSessionName()
  {
    return this.achCompaniesName;
  }
  
  public final void setACHCompanyInSessionName(String paramString)
  {
    this.achCompanyName = paramString;
  }
  
  public final String getACHCompanyInSessionName()
  {
    return this.achCompanyName;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (paramString.trim().length() > 0) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setFIID(String paramString)
  {
    this.fiID = paramString;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this.zD = Integer.parseInt(paramString);
  }
  
  public int getEntitlementGroupId()
  {
    return this.zD;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.DeleteACHCompany
 * JD-Core Version:    0.7.0.1
 */