package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyACHBatchInfo
  implements BusinessTask
{
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String successURL;
  protected int error;
  protected String groupId;
  protected String achCompanyName = null;
  protected String achCompanyId = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HashMap localHashMap = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(Integer.parseInt(this.groupId));
      EntitlementGroupProperties localEntitlementGroupProperties = localEntitlementGroup.getProperties();
      localEntitlementGroupProperties.setCurrentProperty("ACHCompanyID");
      String str2 = localEntitlementGroupProperties.getValueOfCurrentProperty();
      localEntitlementGroupProperties.setCurrentProperty("ACHCompanyName");
      String str3 = localEntitlementGroupProperties.getValueOfCurrentProperty();
      localEntitlementGroupProperties.setCurrentProperty("BillPayWarehouseID");
      String str4 = localEntitlementGroupProperties.getValueOfCurrentProperty();
      if ((this.achCompanyName != null) && (this.achCompanyId != null))
      {
        this.achCompanyName = this.achCompanyName.trim();
        this.achCompanyId = this.achCompanyId.trim();
      }
      if ((str4 == null) || (str4.trim().length() == 0))
      {
        if ((this.achCompanyName != null) && (this.achCompanyName.length() != 0) && (this.achCompanyName.length() <= 16) && (this.achCompanyId != null) && (this.achCompanyId.length() != 0) && (this.achCompanyId.length() <= 10)) {
          try
          {
            ACHCompany localACHCompany1 = new ACHCompany();
            localACHCompany1.setCompanyID(this.achCompanyId);
            localACHCompany1.setCompanyName(this.achCompanyName);
            localACHCompany1 = ACH.addACHCompany(localSecureUser, localACHCompany1, localSecureUser.getBankID(), localHashMap);
            localEntitlementGroupProperties.setCurrentProperty("ACHCompanyID");
            localEntitlementGroupProperties.setValueOfCurrentProperty(this.achCompanyId);
            localEntitlementGroupProperties.setCurrentProperty("ACHCompanyName");
            localEntitlementGroupProperties.setValueOfCurrentProperty(this.achCompanyName);
            localEntitlementGroupProperties.setCurrentProperty("BillPayWarehouseID");
            localEntitlementGroupProperties.setValueOfCurrentProperty(localACHCompany1.getID());
            Entitlements.modifyEntitlementGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup);
          }
          catch (CSILException localCSILException2)
          {
            this.error = MapError.mapError(localCSILException2);
            str1 = this.serviceErrorURL;
          }
        }
      }
      else
      {
        if ((this.achCompanyName == null) || (this.achCompanyName.length() == 0) || (this.achCompanyName.length() > 16) || (this.achCompanyId == null) || (this.achCompanyId.length() == 0) || (this.achCompanyId.length() > 10))
        {
          this.error = 4137;
          str1 = this.taskErrorURL;
          return str1;
        }
        if ((!this.achCompanyName.equals(str3)) || (!this.achCompanyId.equals(str2)))
        {
          localEntitlementGroupProperties.setCurrentProperty("BillPayWarehouseID");
          String str5 = localEntitlementGroupProperties.getValueOfCurrentProperty();
          try
          {
            ACHCompany localACHCompany2 = new ACHCompany(str5, Integer.toString(localSecureUser.getBusinessID()), this.achCompanyId, this.achCompanyName);
            ACHCompany localACHCompany3 = new ACHCompany(str5, Integer.toString(localSecureUser.getBusinessID()), str2, str3);
            ACH.modifyACHCompany(localSecureUser, localACHCompany2, localACHCompany3, localSecureUser.getBankID(), localHashMap);
            localEntitlementGroupProperties.setCurrentProperty("ACHCompanyID");
            localEntitlementGroupProperties.setValueOfCurrentProperty(this.achCompanyId);
            localEntitlementGroupProperties.setCurrentProperty("ACHCompanyName");
            localEntitlementGroupProperties.setValueOfCurrentProperty(this.achCompanyName);
            Entitlements.modifyEntitlementGroup(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlementGroup);
          }
          catch (CSILException localCSILException3)
          {
            this.error = MapError.mapError(localCSILException3);
            str1 = this.serviceErrorURL;
          }
        }
      }
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public int getErrorValue()
  {
    return this.error;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.groupId = paramString;
  }
  
  public void setAchCompanyID(String paramString)
  {
    this.achCompanyId = paramString;
  }
  
  public String getAchCompanyID()
  {
    return this.achCompanyId;
  }
  
  public void setAchCompanyName(String paramString)
  {
    this.achCompanyName = paramString;
  }
  
  public String getAchCompanyName()
  {
    return this.achCompanyName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.ModifyACHBatchInfo
 * JD-Core Version:    0.7.0.1
 */