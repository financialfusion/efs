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

public class GetImages
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected int error;
  protected Locale locale = Locale.getDefault();
  protected int items = 2;
  protected int begin = 0;
  protected int end = this.items - 1;
  protected boolean nextPageFlag = false;
  protected boolean previousPageFlag = false;
  protected boolean nextAvailable = false;
  protected boolean previousAvailable = false;
  protected boolean previousFlag = false;
  ArrayList Vk = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    int i = 0;
    String str1 = null;
    HttpSession localHttpSession = null;
    HashMap localHashMap1 = null;
    Iterator localIterator = null;
    ArrayList localArrayList = null;
    ImageResult localImageResult1 = null;
    ImageResults localImageResults1 = null;
    ImageResults localImageResults2 = null;
    SecureUser localSecureUser = null;
    this.nextURL = this.taskErrorURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localImageResults1 = (ImageResults)localHttpSession.getAttribute("CheckedImages");
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.Vk = ((ArrayList)localHttpSession.getAttribute("CheckedImagesId"));
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (localImageResults1 != null)
    {
      localImageResults2 = new ImageResults();
      localArrayList = new ArrayList();
      if (this.error != 0) {
        return this.nextURL;
      }
      L();
      localIterator = this.Vk.iterator();
      while (localIterator.hasNext())
      {
        str1 = (String)localIterator.next();
        if ((i >= this.begin) && (i <= this.end)) {
          localArrayList.add(str1);
        }
        i += 1;
      }
      if (localArrayList != null)
      {
        localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          localImageResult1 = localImageResults1.getByHandle((String)localIterator.next());
          String str2 = localImageResult1.getStatus();
          if (!str2.equalsIgnoreCase("AVAILABLE")) {
            try
            {
              ImageResult localImageResult2 = CheckImaging.getImageStatus(localSecureUser, localImageResult1, localHashMap1);
              str2 = localImageResult1.getStatus();
            }
            catch (CSILException localCSILException1)
            {
              this.error = MapError.mapError(localCSILException1);
              this.nextURL = this.serviceErrorURL;
            }
          }
          if (str2.equalsIgnoreCase("PENDING"))
          {
            jdMethod_for(localHttpSession, localImageResult1);
          }
          else if (str2.equalsIgnoreCase("AVAILABLE"))
          {
            HashMap localHashMap2 = localImageResult1.getImage();
            if ((localHashMap2 == null) || (localHashMap2.isEmpty())) {
              try
              {
                ImageResult localImageResult3 = CheckImaging.getImage(localSecureUser, localImageResult1, localHashMap1);
              }
              catch (CSILException localCSILException2)
              {
                this.error = MapError.mapError(localCSILException2);
                this.nextURL = this.serviceErrorURL;
              }
            }
          }
          else if (str2.equalsIgnoreCase("ERROR"))
          {
            this.error = Integer.valueOf(localImageResult1.getErrorCode()).intValue();
          }
          localImageResults2.add(localImageResult1);
        }
        localHttpSession.setAttribute("AvailableImages", localImageResults2);
        this.nextURL = this.successURL;
      }
    }
    else
    {
      this.error = 400030;
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, ImageResult paramImageResult)
  {
    try
    {
      String str1 = paramImageResult.getStorageTier();
      String str2 = "";
      if ((str1 != null) && (str1.equals("2"))) {
        str2 = "2 Minutes";
      } else if ((str1 != null) && (str1.equals("3"))) {
        str2 = "4 Minutes";
      }
      paramImageResult.setEstimate(str2);
    }
    catch (Exception localException) {}
  }
  
  private void L()
  {
    int i = this.Vk.size();
    if ((this.nextPageFlag) && (this.begin + this.items < i))
    {
      this.begin += this.items;
      this.end += this.items;
      this.nextPageFlag = false;
    }
    if ((this.previousPageFlag) && (this.begin > 0))
    {
      this.begin -= this.items;
      this.end -= this.items;
      this.previousPageFlag = false;
    }
    if (this.begin > 0) {
      this.previousAvailable = true;
    } else {
      this.previousAvailable = false;
    }
    if (this.end < i - 1) {
      this.nextAvailable = true;
    } else {
      this.nextAvailable = false;
    }
  }
  
  public void setCheckedImageIdList(String paramString)
  {
    String str = null;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      str = localStringTokenizer.nextToken();
      this.Vk.add(str);
    }
  }
  
  public void setCheckedImageIdList(ArrayList paramArrayList)
  {
    this.Vk = paramArrayList;
  }
  
  public ArrayList getCheckedImageIdList()
  {
    return this.Vk;
  }
  
  public void setNextPage(String paramString)
  {
    this.nextPageFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPreviousPage(String paramString)
  {
    this.previousPageFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getNextAvailable()
  {
    return String.valueOf(this.nextAvailable);
  }
  
  public String getPreviousAvailable()
  {
    return String.valueOf(this.previousAvailable);
  }
  
  public void setPreviousFlag(String paramString)
  {
    this.previousFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setMaxItems(String paramString)
  {
    this.items = Integer.valueOf(paramString).intValue();
    this.begin = 0;
    this.end = (this.items - 1);
  }
  
  public String getMaxItems()
  {
    return String.valueOf(this.items);
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
 * Qualified Name:     com.ffusion.tasks.checkimaging.GetImages
 * JD-Core Version:    0.7.0.1
 */