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

public class DeleteImageResults
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected int error;
  protected String collection = "ImageResults";
  protected boolean deleteOnlyAvailableImageFlag = false;
  protected Locale locale;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = null;
    HashMap localHashMap = null;
    Iterator localIterator = null;
    ImageResults localImageResults1 = null;
    ImageResults localImageResults2 = null;
    Object localObject = null;
    ImageResult localImageResult = null;
    SecureUser localSecureUser = null;
    String str1 = null;
    String str2 = null;
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
    localImageResults2 = (ImageResults)localHttpSession.getAttribute(this.collection);
    if ((localImageResults2 == null) || (localImageResults2.size() < 1)) {
      return this.successURL;
    }
    str1 = localImageResults2.getPackageId();
    if ((str1 == null) || (str1.equals(""))) {
      localIterator = localImageResults2.iterator();
    }
    while (localIterator.hasNext())
    {
      localImageResult = (ImageResult)localIterator.next();
      localImageResults1 = new ImageResults();
      localImageResults1.add(localImageResult);
      localImageResults1.setPackageId(localImageResult.getPackageId());
      if (this.deleteOnlyAvailableImageFlag)
      {
        str2 = localImageResult.getStatus();
        if ((str2 != null) && (!str2.equals("")) && (str2.equals("AVAILABLE"))) {
          this.nextURL = jdMethod_for(localSecureUser, localImageResults1, localHashMap);
        }
      }
      else
      {
        this.nextURL = jdMethod_for(localSecureUser, localImageResults1, localHashMap);
        continue;
        this.nextURL = jdMethod_for(localSecureUser, localImageResults2, localHashMap);
      }
    }
    localHttpSession.removeAttribute(this.collection);
    return this.nextURL;
  }
  
  private String jdMethod_for(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
  {
    try
    {
      ImageResults localImageResults = CheckImaging.deleteImageResults(paramSecureUser, paramImageResults, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public void setDeleteOnlyAvailableImageFlag(String paramString)
  {
    this.deleteOnlyAvailableImageFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getDeleteOnlyAvailableImageFlag()
  {
    return this.deleteOnlyAvailableImageFlag;
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
 * Qualified Name:     com.ffusion.tasks.checkimaging.DeleteImageResults
 * JD-Core Version:    0.7.0.1
 */