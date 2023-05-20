package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CheckImaging;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteImageRequests
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected int error;
  protected boolean deleteAllFlag = false;
  protected boolean deleteOldImageRequestsFlag = false;
  protected Locale locale;
  protected ArrayList deleteList = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = null;
    Iterator localIterator = null;
    SecureUser localSecureUser = null;
    ImageResults localImageResults1 = null;
    ImageResults localImageResults2 = null;
    ImageResult localImageResult = null;
    this.nextURL = this.successURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localImageResults1 = (ImageResults)localHttpSession.getAttribute("RequestedCheckImages");
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (localImageResults1 != null) {
      if (this.deleteOldImageRequestsFlag)
      {
        this.nextURL = jdMethod_for(localHttpSession, localSecureUser, localImageResults1);
      }
      else if (this.deleteAllFlag)
      {
        this.nextURL = jdMethod_int(localHttpSession, localSecureUser, localImageResults1);
        this.deleteAllFlag = false;
      }
      else
      {
        localImageResults2 = new ImageResults();
        localIterator = this.deleteList.iterator();
        while (localIterator.hasNext())
        {
          localImageResult = localImageResults1.getByHandle((String)localIterator.next());
          if (localImageResult != null) {
            localImageResults2.add(localImageResult);
          }
        }
        this.nextURL = jdMethod_int(localHttpSession, localSecureUser, localImageResults2);
      }
    }
    return this.nextURL;
  }
  
  private String jdMethod_int(HttpSession paramHttpSession, SecureUser paramSecureUser, ImageResults paramImageResults)
  {
    Iterator localIterator = null;
    HashMap localHashMap = null;
    ImageResults localImageResults1 = null;
    ImageResult localImageResult = null;
    localImageResults1 = (ImageResults)paramHttpSession.getAttribute("RequestedCheckImages");
    try
    {
      ImageResults localImageResults2 = CheckImaging.deleteImageRequests(paramSecureUser, paramImageResults, localHashMap);
      paramHttpSession.setAttribute("DeleteImages", paramImageResults);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localIterator = paramImageResults.iterator();
    while (localIterator.hasNext())
    {
      localImageResult = (ImageResult)localIterator.next();
      localImageResults1.removeByID(localImageResult.getImageHandle());
    }
    paramHttpSession.setAttribute("RequestedCheckImages", localImageResults1);
    return this.successURL;
  }
  
  private String jdMethod_for(HttpSession paramHttpSession, SecureUser paramSecureUser, ImageResults paramImageResults)
  {
    int i = 0;
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = null;
    Iterator localIterator = null;
    DateTime localDateTime = null;
    ImageResult localImageResult = null;
    ImageResults localImageResults = null;
    localDateTime = new DateTime(new GregorianCalendar(), this.locale);
    try
    {
      localHashMap2 = CheckImaging.getConfigProperties(paramSecureUser, localHashMap1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    i = Integer.parseInt((String)localHashMap2.get("IMAGE_MAX_AGE"));
    if (i <= 0) {
      i = 24;
    }
    localImageResults = new ImageResults();
    localIterator = paramImageResults.iterator();
    while (localIterator.hasNext())
    {
      localImageResult = (ImageResult)localIterator.next();
      if (localDateTime.getDiff(localImageResult.getRequestedDateValue(), 2) > i) {
        localImageResults.add(localImageResult);
      }
    }
    if (localImageResults.size() > 0) {
      this.nextURL = jdMethod_int(paramHttpSession, paramSecureUser, localImageResults);
    }
    return this.nextURL;
  }
  
  public void setID(String paramString)
  {
    this.deleteList.add(paramString);
  }
  
  public void setDeleteAll(String paramString)
  {
    this.deleteAllFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDeleteOldRequests(String paramString)
  {
    this.deleteOldImageRequestsFlag = Boolean.valueOf(paramString).booleanValue();
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
 * Qualified Name:     com.ffusion.tasks.checkimaging.DeleteImageRequests
 * JD-Core Version:    0.7.0.1
 */