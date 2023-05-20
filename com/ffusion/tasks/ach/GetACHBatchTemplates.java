package com.ffusion.tasks.ach;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHBatchTemplates
  extends BaseTask
  implements Task
{
  protected String sessionName = "ACHBatchTemplates";
  protected String achType = "ACHBatch";
  protected boolean reload = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.sessionName == null)
    {
      str = this.taskErrorURL;
      return str;
    }
    FundsTransactionTemplates localFundsTransactionTemplates1 = (FundsTransactionTemplates)localHttpSession.getAttribute(this.sessionName);
    if (this.reload)
    {
      localFundsTransactionTemplates1 = null;
      localHttpSession.removeAttribute(this.sessionName);
      this.reload = false;
    }
    if (localFundsTransactionTemplates1 == null)
    {
      localFundsTransactionTemplates1 = new FundsTransactionTemplates();
      this.error = 0;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      FundsTransactionTemplates localFundsTransactionTemplates2 = jdMethod_for(localSecureUser, false);
      if (this.error != 0)
      {
        str = this.serviceErrorURL;
        return str;
      }
      FundsTransactionTemplates localFundsTransactionTemplates3 = jdMethod_for(localSecureUser, true);
      if (this.error != 0)
      {
        str = this.serviceErrorURL;
        return str;
      }
      localFundsTransactionTemplates1.addAll(localFundsTransactionTemplates2);
      localFundsTransactionTemplates1.addAll(localFundsTransactionTemplates3);
      localHttpSession.setAttribute(this.sessionName, localFundsTransactionTemplates1);
    }
    return str;
  }
  
  private FundsTransactionTemplates jdMethod_for(SecureUser paramSecureUser, boolean paramBoolean)
  {
    FundsTransactionTemplates localFundsTransactionTemplates1 = new FundsTransactionTemplates();
    HashMap localHashMap = new HashMap();
    try
    {
      int i = 1007;
      if (this.achType.compareTo("ACHBatch") == 0) {
        i = 1007;
      } else if (this.achType.compareTo("TaxPayment") == 0) {
        i = 1007;
      } else if (this.achType.compareTo("ChildSupportPayment") == 0) {
        i = 1007;
      }
      if (paramBoolean == true) {
        localHashMap.put("TemplateType", "Business");
      }
      FundsTransactionTemplates localFundsTransactionTemplates2 = Banking.getFundsTransactionTemplates(paramSecureUser, i, localHashMap);
      if (localFundsTransactionTemplates2 != null)
      {
        Iterator localIterator = localFundsTransactionTemplates2.iterator();
        while (localIterator.hasNext())
        {
          FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localIterator.next();
          FundsTransaction localFundsTransaction = localFundsTransactionTemplate.getFundsTransaction();
          if ((localFundsTransaction instanceof ACHBatch))
          {
            ACHBatch localACHBatch = (ACHBatch)localFundsTransaction;
            if (!paramBoolean) {
              localACHBatch.setBatchScope("USER");
            } else {
              localACHBatch.setBatchScope("BUSINESS");
            }
            localFundsTransactionTemplates1.add(localFundsTransactionTemplate);
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    return localFundsTransactionTemplates1;
  }
  
  public void setSessionName(String paramString)
  {
    this.sessionName = paramString;
  }
  
  public void setAchType(String paramString)
  {
    this.achType = paramString;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetACHBatchTemplates
 * JD-Core Version:    0.7.0.1
 */