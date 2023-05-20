package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CheckImaging;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogFee
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected String chargeFlag = "0";
  protected String imageId;
  protected String chargeAccount;
  protected int error;
  protected Locale locale;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    HashMap localHashMap = null;
    HttpSession localHttpSession = null;
    Object localObject = null;
    ImageResult localImageResult = null;
    DateTime localDateTime = null;
    User localUser = null;
    SecureUser localSecureUser = null;
    this.nextURL = this.taskErrorURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    ImageResults localImageResults = (ImageResults)localHttpSession.getAttribute("AvailableImages");
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (localImageResults != null)
    {
      localImageResult = localImageResults.getByHandle(this.imageId);
      if (localImageResult == null)
      {
        this.error = 400030;
        return this.taskErrorURL;
      }
      localHashMap = new HashMap();
      localUser = (User)localHttpSession.getAttribute("User");
      if (localUser == null) {
        localUser = (User)localHttpSession.getAttribute("AdminUser");
      }
      if (localUser == null) {
        str3 = localSecureUser.getUserName();
      } else {
        str3 = localUser.getUserName();
      }
      str1 = localImageResult.getStatus();
      if ((this.chargeAccount == null) || (this.chargeAccount.trim().equals(""))) {
        this.chargeAccount = localImageResult.getChargeAccount();
      }
      if (str1.equalsIgnoreCase("AVAILABLE"))
      {
        localDateTime = new DateTime(Calendar.getInstance(), this.locale);
        localDateTime.setFormat("MM-dd-yyyy HH:mm:ss");
        str2 = localDateTime.toString() + ", " + localImageResult.getChargeAccount() + ", " + this.chargeFlag + ", " + str3 + ", " + localImageResult.getBankId();
        localHashMap.put("ImageLogEntry", str2);
        try
        {
          localImageResult = CheckImaging.logFee(localSecureUser, localImageResult, localHashMap);
          this.nextURL = this.successURL;
        }
        catch (CSILException localCSILException)
        {
          localCSILException.printStackTrace(System.out);
          this.error = MapError.mapError(localCSILException);
          this.nextURL = this.serviceErrorURL;
        }
      }
    }
    else
    {
      this.error = 400030;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  public void setLocale(Locale paramLocale)
  {
    if (paramLocale == null) {
      this.locale = Locale.getDefault();
    } else {
      this.locale = paramLocale;
    }
  }
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public void setChargeFlag(String paramString)
  {
    this.chargeFlag = paramString;
  }
  
  public String getChargeFlag()
  {
    return this.chargeFlag;
  }
  
  public void setImageID(String paramString)
  {
    this.imageId = paramString;
  }
  
  public String getImageID()
  {
    return this.imageId;
  }
  
  public void setChargeAccount(String paramString)
  {
    this.chargeAccount = paramString;
  }
  
  public String getChargeAccount()
  {
    return this.chargeAccount;
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
  
  public boolean setError(int paramInt, HttpSession paramHttpSession)
  {
    this.error = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.checkimaging.LogFee
 * JD-Core Version:    0.7.0.1
 */