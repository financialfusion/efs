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

public class SetPendingImages
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected int error;
  protected String pendingImageId;
  protected String operation = "ADD";
  protected Locale locale;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = null;
    SecureUser localSecureUser = null;
    this.nextURL = this.successURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (this.operation.equalsIgnoreCase("ADD")) {
      this.nextURL = jdMethod_for(localHttpSession, localSecureUser);
    } else {
      this.nextURL = jdMethod_int(localHttpSession, localSecureUser);
    }
    return this.nextURL;
  }
  
  private String jdMethod_for(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    HashMap localHashMap = null;
    Iterator localIterator = null;
    ImageResults localImageResults1 = null;
    ImageResults localImageResults2 = null;
    ImageResults localImageResults3 = null;
    ImageResult localImageResult1 = null;
    ImageResult localImageResult2 = null;
    localImageResults1 = (ImageResults)paramHttpSession.getAttribute("RequestedCheckImages");
    localImageResults3 = (ImageResults)paramHttpSession.getAttribute("AvailableImages");
    if (localImageResults3 != null)
    {
      ImageResult localImageResult3 = null;
      localIterator = localImageResults3.iterator();
      while (localIterator.hasNext())
      {
        localImageResult3 = (ImageResult)localIterator.next();
        if (localImageResult3.getImageHandle().equals(this.pendingImageId)) {
          localImageResult2 = (ImageResult)localImageResult3.clone();
        }
      }
    }
    localImageResults2 = new ImageResults();
    if (localImageResults1 != null)
    {
      int i = 0;
      localIterator = localImageResults1.iterator();
      while (localIterator.hasNext())
      {
        localImageResult1 = (ImageResult)localIterator.next();
        if (localImageResult1.getImageHandle().equals(localImageResult2.getImageHandle())) {
          i = 1;
        }
      }
      if (i == 0) {
        localImageResults2.add(localImageResult2);
      }
    }
    else
    {
      localImageResults2.add(localImageResult2);
    }
    if ((localImageResults2 != null) && (localImageResults2.size() > 0))
    {
      try
      {
        localHashMap = new HashMap();
        localHashMap.put("operation", "ADD");
        localImageResults2 = CheckImaging.setPendingImages(paramSecureUser, localImageResults2, localHashMap);
        paramHttpSession.setAttribute("EmailImageResults", localImageResults2);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
      if (localImageResults1 == null) {
        localImageResults1 = new ImageResults();
      }
      localIterator = localImageResults2.iterator();
      while (localIterator.hasNext())
      {
        localImageResult1 = (ImageResult)localIterator.next();
        localImageResults1.add(localImageResult1);
      }
      paramHttpSession.setAttribute("RequestedCheckImages", localImageResults1);
    }
    return this.successURL;
  }
  
  private String jdMethod_int(HttpSession paramHttpSession, SecureUser paramSecureUser)
  {
    HashMap localHashMap = null;
    Iterator localIterator = null;
    ImageResults localImageResults1 = null;
    ImageResults localImageResults2 = null;
    ImageResults localImageResults3 = null;
    ImageResult localImageResult1 = null;
    ImageResult localImageResult2 = null;
    localImageResults1 = (ImageResults)paramHttpSession.getAttribute("RequestedCheckImages");
    if ((localImageResults1 != null) && (localImageResults1.size() > 0))
    {
      try
      {
        localImageResults2 = CheckImaging.getPendingImages(paramSecureUser, localHashMap);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        return this.serviceErrorURL;
      }
      localIterator = localImageResults2.iterator();
      while (localIterator.hasNext())
      {
        localImageResult2 = (ImageResult)localIterator.next();
        localImageResult1 = localImageResults1.getByHandle(localImageResult2.getImageHandle());
        if ((localImageResult2.getImageHandle().equals(localImageResult1.getImageHandle())) && (!localImageResult2.getStatus().equals(localImageResult1.getStatus())))
        {
          if (localImageResults3 == null) {
            localImageResults3 = new ImageResults();
          }
          localImageResults3.add(localImageResult1);
        }
      }
      if ((localImageResults3 == null) || (localImageResults3.size() <= 0)) {
        return this.successURL;
      }
      try
      {
        localHashMap = new HashMap();
        localHashMap.put("operation", "UPDATE");
        localImageResults3 = CheckImaging.setPendingImages(paramSecureUser, localImageResults3, localHashMap);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
        return this.serviceErrorURL;
      }
    }
    return this.successURL;
  }
  
  public void setPendingImageId(String paramString)
  {
    this.pendingImageId = paramString;
  }
  
  public String getPendingImageId()
  {
    return this.pendingImageId;
  }
  
  public void setOperation(String paramString)
  {
    this.operation = paramString;
  }
  
  public String getOperation()
  {
    return this.operation;
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
 * Qualified Name:     com.ffusion.tasks.checkimaging.SetPendingImages
 * JD-Core Version:    0.7.0.1
 */