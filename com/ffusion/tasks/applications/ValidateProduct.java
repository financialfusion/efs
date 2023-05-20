package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateProduct
  implements Task
{
  protected String _modifyProductTaskSessionName = "ModifyProduct";
  protected String _modifyStatusTaskSessionName = "ModifyProductStatuses";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  protected String validateStatus = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ModifyProduct localModifyProduct = (ModifyProduct)localHttpSession.getAttribute(this._modifyProductTaskSessionName);
    if (localModifyProduct == null)
    {
      this.error = 7221;
      str = this.taskErrorURL;
      return str;
    }
    ModifyProductStatuses localModifyProductStatuses = (ModifyProductStatuses)localHttpSession.getAttribute(this._modifyStatusTaskSessionName);
    if (localModifyProductStatuses == null)
    {
      this.error = 7222;
      str = this.taskErrorURL;
      return str;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    LanguageDefns localLanguageDefns = null;
    try
    {
      localLanguageDefns = Util.getLanguageList(localSecureUser, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
      return str;
    }
    int i = 0;
    for (int j = 0; j < localLanguageDefns.size(); j++)
    {
      LanguageDefn localLanguageDefn = (LanguageDefn)localLanguageDefns.get(j);
      if ((localModifyProduct.isAllRequiredValueSpecified(localLanguageDefn.getLanguage())) && (localModifyProductStatuses.isAllRequiredValueSpecified(localLanguageDefn.getLanguage()))) {
        i = 1;
      }
      if ((localModifyProduct.isAnyValueSpecified(localLanguageDefn.getLanguage())) || (localModifyProductStatuses.isAnyValueSpecified(localLanguageDefn.getLanguage())))
      {
        if (!localModifyProduct.isAllRequiredValueSpecified(localLanguageDefn.getLanguage()))
        {
          this.error = 7223;
          str = this.taskErrorURL;
          return str;
        }
        if (!localModifyProductStatuses.isAllRequiredValueSpecified(localLanguageDefn.getLanguage()))
        {
          this.error = 7224;
          str = this.taskErrorURL;
          return str;
        }
      }
    }
    if (i == 0)
    {
      this.error = 7225;
      str = this.taskErrorURL;
      return str;
    }
    j = localModifyProduct.validateProduct(localHttpSession);
    this.validateStatus = "Success";
    if (j != 0)
    {
      try
      {
        this.error = Integer.parseInt(localModifyProduct.getError());
      }
      catch (Exception localException1) {}
      if (j == 1)
      {
        this.validateStatus = "Warning";
        return str;
      }
      if (j == 2)
      {
        this.validateStatus = "TaskError";
        return this.taskErrorURL;
      }
      if (j == 3)
      {
        this.validateStatus = "ServiceError";
        return this.serviceErrorURL;
      }
    }
    j = localModifyProductStatuses.validateStatuses(localHttpSession);
    if (j != 0)
    {
      try
      {
        this.error = Integer.parseInt(localModifyProduct.getError());
      }
      catch (Exception localException2) {}
      if (j == 1)
      {
        this.validateStatus = "Warning";
        return str;
      }
      if (j == 2)
      {
        this.validateStatus = "TaskError";
        return this.taskErrorURL;
      }
      if (j == 3)
      {
        this.validateStatus = "ServiceError";
        return this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setModifyProductTaskSessionName(String paramString)
  {
    this._modifyProductTaskSessionName = paramString;
  }
  
  public void setModifyStatusTaskSessionName(String paramString)
  {
    this._modifyStatusTaskSessionName = paramString;
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
  
  public String getValidateStatus()
  {
    return this.validateStatus;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.ValidateProduct
 * JD-Core Version:    0.7.0.1
 */