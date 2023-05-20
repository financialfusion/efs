package com.ffusion.tasks.multiuser;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLog;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TermsAccepted
  extends BaseTask
{
  private static final String aML = "com.ffusion.util.logging.audit.useradmin";
  private static final String aMN = "AuditMessage_41";
  private SecureUser aMK;
  private Locale aMM;
  private boolean aMJ;
  private String aMI;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.aMK = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.aMM = BaseTask.getLocale(localHttpSession, this.aMK);
    if (validateInput())
    {
      if (this.aMJ)
      {
        String str2 = TrackingIDGenerator.GetNextID();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = this.aMK.getUserName();
        arrayOfObject[1] = this.aMI;
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.useradmin", "AuditMessage_41", arrayOfObject);
        AuditLog.log(Integer.toString(this.aMK.getProfileID()), localLocalizableString, str2, 3232);
      }
      else
      {
        this.error = 28000;
        str1 = this.taskErrorURL;
      }
    }
    else {
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setAccepted(String paramString)
  {
    this.aMJ = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.aMK == null))
    {
      this.error = 38;
      bool = false;
    }
    if ((bool) && ((this.aMI == null) || (this.aMI.trim().length() == 0)))
    {
      this.error = 28017;
      bool = false;
    }
    return bool;
  }
  
  public void setSecondaryUserUserName(String paramString)
  {
    this.aMI = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.TermsAccepted
 * JD-Core Version:    0.7.0.1
 */