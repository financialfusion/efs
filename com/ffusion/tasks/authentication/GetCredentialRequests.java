package com.ffusion.tasks.authentication;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.core.Authentication;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCredentialRequests
  extends BaseTask
{
  private String aKN = "Credentials";
  private int aKM = -1;
  private String aKO = null;
  private String aKL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.aKM == -1)
    {
      this.error = 90003;
      return this.taskErrorURL;
    }
    if ((this.aKM != 1) && (this.aKL == null))
    {
      this.error = 90002;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String str = localSecureUser.getUserName();
    if ((str == null) || (str.trim().length() == 0))
    {
      this.error = 1;
      return this.taskErrorURL;
    }
    if (this.aKO == null)
    {
      this.error = 90005;
      return this.taskErrorURL;
    }
    HashMap localHashMap1 = new HashMap();
    try
    {
      boolean bool1 = false;
      boolean bool2 = true;
      Object localObject1;
      Object localObject2;
      if (localSecureUser.getUserType() == 2)
      {
        localObject1 = new BankEmployee(localSecureUser.getLocale());
        ((BankEmployee)localObject1).setUserName(localSecureUser.getUserName());
        bool1 = BankEmployeeAdmin.getInfoForAuditing((BankEmployee)localObject1, localHashMap1);
        localSecureUser.setProfileID(((BankEmployee)localObject1).getId());
        if (bool1)
        {
          localObject2 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          localObject2 = Entitlements.getMember((EntitlementGroupMember)localObject2);
          localSecureUser.setEntitlementID(((EntitlementGroupMember)localObject2).getEntitlementGroupId());
          localSecureUser.setBusinessID(((EntitlementGroupMember)localObject2).getBusinessID());
          localObject1 = BankEmployeeAdmin.getBankEmployeeById(localSecureUser, (BankEmployee)localObject1, localHashMap1);
          bool2 = ((BankEmployee)localObject1).getStatus().equals("0");
        }
      }
      else
      {
        bool1 = UserAdmin.getInfoForAuditing(localSecureUser, localHashMap1);
        if (bool1)
        {
          localObject1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
          localObject1 = Entitlements.getMember((EntitlementGroupMember)localObject1);
          localSecureUser.setEntitlementID(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
          localSecureUser.setBusinessID(((EntitlementGroupMember)localObject1).getBusinessID());
          localObject2 = new User();
          ((User)localObject2).setId(String.valueOf(localSecureUser.getProfileID()));
          localObject2 = UserAdmin.getUserById(localSecureUser, (User)localObject2, localHashMap1);
          bool2 = ((User)localObject2).getAccountStatus().equals(String.valueOf(1));
        }
      }
      if ((this.aKM != 1) && ((!bool1) || (!bool2))) {
        return this.aKL;
      }
    }
    catch (CSILException localCSILException1)
    {
      this.error = localCSILException1.getCode();
      return this.serviceErrorURL;
    }
    HashMap localHashMap2 = new HashMap();
    localHashMap2.put("Operation", new Integer(this.aKM));
    localHashMap1.put("UserType", this.aKO);
    Credentials localCredentials = null;
    try
    {
      localCredentials = Authentication.getCredentialRequests(localSecureUser, localHashMap2, localHashMap1);
    }
    catch (CSILException localCSILException2)
    {
      this.error = localCSILException2.getCode();
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.aKN, localCredentials);
    return this.successURL;
  }
  
  public void setOperation(String paramString)
  {
    try
    {
      this.aKM = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aKM = -1;
    }
  }
  
  public String getOperation()
  {
    return String.valueOf(this.aKM);
  }
  
  public void setCredentialsCollection(String paramString)
  {
    this.aKN = paramString;
  }
  
  public String getCredentialsCollection()
  {
    return this.aKN;
  }
  
  public void setUserType(String paramString)
  {
    this.aKO = paramString;
  }
  
  public String getUserType()
  {
    return this.aKO;
  }
  
  public void setLogoutURL(String paramString)
  {
    this.aKL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.authentication.GetCredentialRequests
 * JD-Core Version:    0.7.0.1
 */