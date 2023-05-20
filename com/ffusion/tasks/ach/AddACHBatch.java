package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddACHBatch
  extends ModifyACHBatch
{
  protected Boolean currentlyProcessing = Boolean.FALSE;
  protected static long timeoutValue = 120000L;
  protected String nextURL = null;
  
  public AddACHBatch()
  {
    this.achType = "ACHBatch";
    this.datetype = "SHORT";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag)
    {
      str = initProcess(paramHttpServletRequest, localHttpSession);
      timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
    }
    else
    {
      str = addACHBatch(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    boolean bool = true;
    if ((this.batchID != null) && (this.batchID.length() > 0))
    {
      super.initProcess(paramHttpSession);
      setID(null);
      setTrackingID(null);
    }
    else if ((this.templateID != null) && (this.templateID.length() > 0))
    {
      bool = initFromTemplate(paramHttpSession);
    }
    else
    {
      int i = 1;
      Business localBusiness = (Business)paramHttpSession.getAttribute("Business");
      if ((localBusiness != null) && (localBusiness.getACHBatchTypeValue() != 0)) {
        i = localBusiness.getACHBatchTypeValue();
      }
      ACHCompany localACHCompany = (ACHCompany)paramHttpSession.getAttribute("ACHCOMPANY");
      if ((localACHCompany != null) && (localACHCompany.getACHBatchTypeValue() != 0)) {
        i = localACHCompany.getACHBatchTypeValue();
      }
      setBatchType(i);
      this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    }
    paramHttpSession.setAttribute("ACHEntries", getACHEntries());
    this.currentlyProcessing = Boolean.FALSE;
    if (bool) {
      return this.successURL;
    }
    return this.taskErrorURL;
  }
  
  protected String addACHBatch(HttpSession paramHttpSession)
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
    else if (this.error != 0)
    {
      return this.taskErrorURL;
    }
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        int i = 0;
        synchronized (this)
        {
          if (!this.currentlyProcessing.booleanValue())
          {
            this.currentlyProcessing = Boolean.TRUE;
            i = 1;
          }
        }
        if (i != 0)
        {
          try
          {
            str = doProcess(paramHttpSession);
            this.nextURL = str;
          }
          catch (Exception localException1)
          {
            this.error = 1;
            this.nextURL = this.serviceErrorURL;
          }
          finally
          {
            this.currentlyProcessing = Boolean.FALSE;
          }
        }
        else
        {
          long l = System.currentTimeMillis();
          while (this.currentlyProcessing.booleanValue() == true)
          {
            if (System.currentTimeMillis() - l > timeoutValue)
            {
              if (this.error != 0) {
                break;
              }
              this.error = 1;
              break;
            }
            try
            {
              Thread.sleep(2000L);
            }
            catch (Exception localException2) {}
          }
          str = this.nextURL;
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
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = null;
    ACHBatch localACHBatch = new ACHBatch(this.locale);
    localACHBatch.set(this);
    HashMap localHashMap = new HashMap();
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      if (getFrequencyValue() != 0) {
        localACHBatch = ACH.addRecACHBatch(localSecureUser, localACHBatch, localHashMap);
      } else {
        localACHBatch = ACH.addACHBatch(localSecureUser, localACHBatch, localHashMap);
      }
      set(localACHBatch);
      if (this.anyAddToManaged) {
        paramHttpSession.removeAttribute("ACHPayees");
      }
      if (this.clearEntriesFlag) {
        getACHEntries().clear();
      }
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      ACHBatches localACHBatches = (ACHBatches)paramHttpSession.getAttribute(this.batchesName);
      if (localACHBatches != null) {
        localACHBatches.add(localACHBatch);
      }
      paramHttpSession.setAttribute("ACHBatch", localACHBatch);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddACHBatch
 * JD-Core Version:    0.7.0.1
 */