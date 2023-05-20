package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CheckImaging;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCheckedImages
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected String collection = "ImageResults";
  protected int error;
  protected Locale locale;
  protected ArrayList checkedImageIdList = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = null;
    HttpSession localHttpSession = null;
    Iterator localIterator = null;
    ImageResult localImageResult = null;
    ImageResults localImageResults1 = null;
    ImageResults localImageResults2 = null;
    SecureUser localSecureUser = null;
    this.nextURL = this.taskErrorURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localImageResults2 = (ImageResults)localHttpSession.getAttribute(this.collection);
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (localImageResults2 != null)
    {
      localImageResults1 = new ImageResults();
      localIterator = this.checkedImageIdList.iterator();
      while (localIterator.hasNext())
      {
        localImageResult = localImageResults2.getByHandle((String)localIterator.next());
        if (localImageResult != null) {
          localImageResults1.add(localImageResult);
        }
      }
      try
      {
        localImageResults1 = CheckImaging.getImagePackageId(localSecureUser, localImageResults1, localHashMap);
        localHttpSession.setAttribute("CheckedImages", localImageResults1);
        localHttpSession.setAttribute("CheckedImagesId", this.checkedImageIdList);
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
  
  public void setCheckedImageIdList(String paramString)
  {
    String str = null;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      str = localStringTokenizer.nextToken();
      this.checkedImageIdList.add(str);
    }
  }
  
  public void setCheckedImageIdList(ArrayList paramArrayList)
  {
    this.checkedImageIdList = paramArrayList;
  }
  
  public ArrayList getCheckedImageIdList()
  {
    return this.checkedImageIdList;
  }
  
  public void setID(String paramString)
  {
    if (this.checkedImageIdList == null) {
      this.checkedImageIdList = new ArrayList();
    }
    this.checkedImageIdList.clear();
    this.checkedImageIdList.add(paramString);
  }
  
  public void setImageID(String paramString)
  {
    if (this.checkedImageIdList == null) {
      this.checkedImageIdList = new ArrayList();
    }
    this.checkedImageIdList.add(paramString);
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
 * Qualified Name:     com.ffusion.tasks.checkimaging.GetCheckedImages
 * JD-Core Version:    0.7.0.1
 */