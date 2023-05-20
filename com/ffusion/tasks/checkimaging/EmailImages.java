package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.Email;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CheckImaging;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EmailImages
  extends BaseTask
  implements ImageTask
{
  protected String verifyFormat;
  protected String validate;
  protected String emailAddress;
  protected String fromAddress;
  protected String subject;
  protected String message;
  protected String imageId;
  protected boolean initFlag = false;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected int error;
  protected Locale locale;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = null;
    HashMap localHashMap = null;
    User localUser = null;
    SecureUser localSecureUser = null;
    ImageResult localImageResult1 = null;
    ImageResult localImageResult2 = null;
    ImageResults localImageResults = null;
    this.nextURL = this.successURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.initFlag)
    {
      localUser = (User)localHttpSession.getAttribute("User");
      if (localUser == null) {
        localUser = (User)localHttpSession.getAttribute("AdminUser");
      }
      if (localUser != null) {
        this.fromAddress = localUser.getEmail();
      }
      this.nextURL = this.successURL;
      this.initFlag = false;
    }
    else
    {
      localImageResults = (ImageResults)localHttpSession.getAttribute("AvailableImages");
      localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (localSecureUser == null)
      {
        this.error = 38;
        return this.taskErrorURL;
      }
      if (localImageResults != null)
      {
        if (validateInput(localHttpSession))
        {
          localImageResult1 = localImageResults.getByHandle(this.imageId);
          if (localImageResult1 != null)
          {
            try
            {
              localHashMap = new HashMap();
              localHashMap.put("FROMADDRESS", this.fromAddress);
              localHashMap.put("TOADDRESS", this.emailAddress);
              localHashMap.put("SUBJECT", this.subject);
              localHashMap.put("MESSAGE", this.message);
              localImageResult2 = CheckImaging.emailImage(localSecureUser, localImageResult1, localHashMap);
              this.nextURL = this.successURL;
            }
            catch (CSILException localCSILException)
            {
              this.error = MapError.mapError(localCSILException);
              this.nextURL = this.serviceErrorURL;
            }
          }
          else
          {
            this.error = 400030;
            this.nextURL = this.taskErrorURL;
          }
        }
        else
        {
          this.nextURL = this.taskErrorURL;
        }
      }
      else
      {
        this.error = 400030;
        this.nextURL = this.taskErrorURL;
      }
    }
    if ((this.nextURL.equals(this.taskErrorURL)) || (this.nextURL.equals(this.serviceErrorURL))) {
      localHttpSession.setAttribute("ImageError", "TRUE");
    } else {
      localHttpSession.setAttribute("ImageError", "FALSE");
    }
    return this.nextURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null) {
      bool = validationCheck(paramHttpSession);
    }
    if (bool)
    {
      if (this.verifyFormat != null) {
        bool = verifyFormatCheck(paramHttpSession);
      }
    }
    else {
      this.verifyFormat = null;
    }
    return bool;
  }
  
  protected boolean validationCheck(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((this.validate.indexOf("TOADDRESS") != -1) && ((this.emailAddress == null) || (this.emailAddress.trim().equals("")))) {
      bool = setError(400031, paramHttpSession);
    }
    if ((this.validate.indexOf("FROMADDRESS") != -1) && ((this.fromAddress == null) || (this.fromAddress.trim().equals("")))) {
      bool = setError(400032, paramHttpSession);
    }
    return bool;
  }
  
  protected boolean verifyFormatCheck(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((this.verifyFormat.indexOf("TOADDRESS") != -1) && (this.emailAddress != null) && (!this.emailAddress.trim().equals("")) && (!validateEmail())) {
      bool = false;
    }
    if ((this.verifyFormat.indexOf("FROMADDRESS") != -1) && (this.fromAddress != null) && (!this.fromAddress.trim().equals("")) && (!validateFromAddress())) {
      bool = false;
    }
    return bool;
  }
  
  public boolean validateEmail()
  {
    boolean bool = true;
    if ((this.emailAddress != null) && (this.emailAddress != ""))
    {
      Email localEmail = new Email(this.emailAddress);
      if (!localEmail.isValid())
      {
        this.error = 400028;
        bool = false;
      }
    }
    return bool;
  }
  
  public boolean validateFromAddress()
  {
    boolean bool = true;
    if ((this.fromAddress != null) && (this.fromAddress != ""))
    {
      Email localEmail = new Email(this.fromAddress);
      if (!localEmail.isValid())
      {
        this.error = 400029;
        bool = false;
      }
    }
    return bool;
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setVerifyFormat(String paramString)
  {
    this.verifyFormat = null;
    if (!paramString.equalsIgnoreCase("")) {
      this.verifyFormat = paramString.toUpperCase();
    }
  }
  
  public void setToAddress(String paramString)
  {
    this.emailAddress = paramString;
  }
  
  public String getToAddress()
  {
    return this.emailAddress;
  }
  
  public void setFromAddress(String paramString)
  {
    this.fromAddress = paramString;
  }
  
  public String getFromAddress()
  {
    return this.fromAddress;
  }
  
  public void setSubject(String paramString)
  {
    this.subject = paramString;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setImageId(String paramString)
  {
    this.imageId = paramString;
  }
  
  public void setInit(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
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
 * Qualified Name:     com.ffusion.tasks.checkimaging.EmailImages
 * JD-Core Version:    0.7.0.1
 */