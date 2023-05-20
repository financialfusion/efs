package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageRequest;
import com.ffusion.beans.checkimaging.ImageResults;
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

public class GetImageIndex
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected int error;
  protected Locale locale;
  protected String collection = "ImageResults";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = null;
    HttpSession localHttpSession = null;
    ImageRequest localImageRequest = null;
    ImageResults localImageResults = null;
    SecureUser localSecureUser = null;
    this.nextURL = this.taskErrorURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localImageRequest = (ImageRequest)localHttpSession.getAttribute("ImageRequest");
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (localImageRequest != null)
    {
      try
      {
        localImageResults = CheckImaging.getImageIndex(localSecureUser, localImageRequest, localHashMap);
        localHttpSession.setAttribute(this.collection, localImageResults);
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
      this.error = 400027;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  public void setCollection(String paramString)
  {
    this.collection = paramString;
  }
  
  public String getCollection()
  {
    return this.collection;
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
 * Qualified Name:     com.ffusion.tasks.checkimaging.GetImageIndex
 * JD-Core Version:    0.7.0.1
 */