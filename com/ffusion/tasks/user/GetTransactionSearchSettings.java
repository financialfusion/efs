package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransactionSearchSettings
  extends SetTransactionSearchSettings
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    String str2 = null;
    HashMap localHashMap = null;
    String str3 = null;
    str3 = (String)localHttpSession.getAttribute(this.sessionName);
    if ((str3 == null) || (str3.length() == 0))
    {
      try
      {
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        str2 = UserAdmin.getAdditionalData(localSecureUser, this.nameSpace, localHashMap);
        if ((str2 != null) && (str2.length() != 0))
        {
          XMLTag localXMLTag = new XMLTag();
          localXMLTag.build(str2);
          if (localXMLTag.getTagName().equals(this.nameSpace)) {
            localXMLTag = localXMLTag.getContainedTag(this.nameSpace);
          }
          str3 = localXMLTag.getContainedTag(this.tag).getTagContent();
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
      catch (Throwable localThrowable)
      {
        DebugLog.throwing("GetAdditionalSettings Task Exception: ", localThrowable);
        this.error = 3002;
        str1 = this.taskErrorURL;
      }
      if ((str3 == null) || (str3.length() == 0)) {
        str3 = i();
      }
      localHttpSession.setAttribute(this.sessionName, str3);
    }
    return str1;
  }
  
  private String i()
  {
    String str = null;
    try
    {
      HashMap localHashMap1 = Initialize.getSettings();
      if (localHashMap1 != null)
      {
        HashMap localHashMap2 = (HashMap)localHashMap1.get("GLOBAL_SETTINGS");
        if (localHashMap2 != null) {
          str = (String)localHashMap2.get("TransactionSearchMaxDisplay");
        }
      }
    }
    catch (Exception localException) {}
    if ((str == null) || (str.length() == 0)) {
      str = "250";
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetTransactionSearchSettings
 * JD-Core Version:    0.7.0.1
 */