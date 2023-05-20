package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CheckImaging;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPendingImageStatus
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected int error;
  protected Locale locale;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = null;
    Iterator localIterator = null;
    HashMap localHashMap = null;
    ImageResult localImageResult1 = null;
    Object localObject = null;
    ImageResults localImageResults = null;
    SecureUser localSecureUser = null;
    this.nextURL = this.successURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localImageResults = (ImageResults)localHttpSession.getAttribute("RequestedCheckImages");
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if ((localImageResults != null) && (localImageResults.size() > 0))
    {
      localIterator = localImageResults.iterator();
      while (localIterator.hasNext()) {
        try
        {
          localImageResult1 = (ImageResult)localIterator.next();
          str = localImageResult1.getStatus();
          if ((str.equalsIgnoreCase("PENDING")) || (str.equalsIgnoreCase("ERROR"))) {
            ImageResult localImageResult2 = CheckImaging.getImageStatus(localSecureUser, localImageResult1, localHashMap);
          }
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          this.nextURL = this.serviceErrorURL;
          return this.nextURL;
        }
      }
      localHttpSession.setAttribute("RequestedCheckImages", localImageResults);
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
 * Qualified Name:     com.ffusion.tasks.checkimaging.GetPendingImageStatus
 * JD-Core Version:    0.7.0.1
 */