package com.ffusion.tasks.authentication;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Authentication;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSecrets
  extends BaseTask
{
  private String aKF = "Secrets";
  private String aKG = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    String str1 = localSecureUser.getUserName();
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      this.error = 1;
      return this.taskErrorURL;
    }
    String str2 = localSecureUser.getBusinessCustId();
    boolean bool1 = SignonSettings.allowDuplicateUserNames();
    boolean bool2 = this.aKG.equals("Business");
    if ((bool2) && (bool1) && ((str2 == null) || (str2.trim().length() == 0)))
    {
      this.error = 13;
      return this.taskErrorURL;
    }
    if (this.aKG == null)
    {
      this.error = 90005;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("UserType", this.aKG);
    ArrayList localArrayList = null;
    try
    {
      localArrayList = Authentication.getSecrets(localSecureUser, new HashMap(), localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, localHttpSession);
      return this.serviceErrorURL;
    }
    if (localArrayList == null) {
      localArrayList = new ArrayList();
    }
    localHttpSession.setAttribute(this.aKF, localArrayList);
    return this.successURL;
  }
  
  public void setSecretsCollection(String paramString)
  {
    this.aKF = paramString;
  }
  
  public String getSecretsCollection()
  {
    return this.aKF;
  }
  
  public void setUserType(String paramString)
  {
    this.aKG = paramString;
  }
  
  public String getUserType()
  {
    return this.aKG;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.authentication.GetSecrets
 * JD-Core Version:    0.7.0.1
 */