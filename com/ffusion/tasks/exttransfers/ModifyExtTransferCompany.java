package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyExtTransferCompany
  extends ExtTransferCompany
  implements Task
{
  protected String fiId;
  protected String affiliateId;
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean modify = true;
  protected ExtTransferCompany currentExtTransferCompany;
  protected ExtTransferCompany originalExtTransferCompany;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processModifyExtTransferCompany(paramHttpServletRequest, localHttpSession);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferCompaniesUpdated", "true");
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    Object localObject = paramHttpSession.getAttribute("ExternalTransferCompany");
    if (localObject == null)
    {
      this.error = 4204;
    }
    else
    {
      this.error = 0;
      this.originalExtTransferCompany = ((ExtTransferCompany)localObject);
      set(this.originalExtTransferCompany);
    }
    return this.error == 0;
  }
  
  protected String processModifyExtTransferCompany(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = modifyExtTransferCompany(paramHttpSession);
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
  
  protected String modifyExtTransferCompany(HttpSession paramHttpSession)
  {
    String str = null;
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      Object localObject = paramHttpSession.getAttribute("ExternalTransferCompany");
      AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(localSecureUser, Integer.parseInt(this.affiliateId), localHashMap);
      this.fiId = localAffiliateBank.getFIBPWID();
      if (localObject == null)
      {
        this.error = 4204;
        str = this.taskErrorURL;
      }
      else if ((this.fiId == null) || (this.fiId.trim().length() == 0))
      {
        this.error = 4208;
        str = this.taskErrorURL;
      }
      else
      {
        this.error = 0;
        ExtTransferCompany localExtTransferCompany = (ExtTransferCompany)localObject;
        ExternalTransferAdmin.modifyExtTransferCompany(localSecureUser, this, this.originalExtTransferCompany, this.fiId, localHashMap);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      paramHttpSession.setAttribute("ExternalTransferCompany", this);
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null) {
      this.validate = null;
    }
    if ((this.companyName == null) || (this.companyName.trim().length() < 1) || (this.companyName.trim().length() > 16)) {
      return setError(4209);
    }
    if ((this.companyID == null) || ((this.companyID != null) && ((this.companyID.trim().length() < 1) || (this.companyID.trim().length() > 10)))) {
      return setError(4210);
    }
    if ((this.batchDescription == null) || ((this.batchDescription != null) && ((this.batchDescription.trim().length() < 1) || (this.batchDescription.trim().length() > 10)))) {
      return setError(4211);
    }
    return bool;
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
  
  public void setCurrentExtTransferCompany(ExtTransferCompany paramExtTransferCompany)
  {
    this.currentExtTransferCompany = paramExtTransferCompany;
  }
  
  public void setFiId(String paramString)
  {
    this.fiId = paramString;
  }
  
  public void setAffiliateId(String paramString)
  {
    this.affiliateId = paramString;
  }
  
  public boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.ModifyExtTransferCompany
 * JD-Core Version:    0.7.0.1
 */