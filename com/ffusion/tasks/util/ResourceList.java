package com.ffusion.tasks.util;

import com.ffusion.tasks.Task;
import com.ffusion.util.Localeable;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResourceList
  extends ArrayList
  implements Task, Localeable
{
  private String aQC = null;
  private String aQF = null;
  private int aQB = 0;
  private Locale aQA;
  private String aQE;
  private String aQD;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.aQA == null) {
      this.aQA = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    }
    if (this.aQA == null)
    {
      this.aQB = 41;
      str = this.aQF;
    }
    else if ((this.aQD != null) && (this.aQD.length() > 0))
    {
      ResourceBundle localResourceBundle = ResourceUtil.getBundle(this.aQD, this.aQA);
      if (localResourceBundle == null)
      {
        this.aQB = 43;
        str = this.aQF;
      }
      else
      {
        str = this.aQC;
      }
    }
    else
    {
      str = this.aQC;
    }
    return str;
  }
  
  public void setResourceFilename(String paramString)
  {
    this.aQD = paramString;
  }
  
  public String getResourceFilename()
  {
    return this.aQD;
  }
  
  public void setResourceID(String paramString)
  {
    this.aQE = paramString;
  }
  
  public String getResourceID()
  {
    return this.aQE;
  }
  
  public String getSize()
  {
    return "" + size();
  }
  
  public String getResource()
  {
    synchronized (this)
    {
      if (this.aQA != null)
      {
        clear();
        String str = ResourceUtil.getString(this.aQE, this.aQD, this.aQA);
        if (str != null)
        {
          StringTokenizer localStringTokenizer = new StringTokenizer(str, ",", false);
          while (localStringTokenizer.hasMoreTokens()) {
            add(localStringTokenizer.nextToken());
          }
        }
      }
    }
    return "";
  }
  
  public void setSuccessURL(String paramString)
  {
    this.aQC = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.aQF = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.aQB);
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.aQA = paramLocale;
  }
  
  public Locale getLocale()
  {
    return this.aQA;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.ResourceList
 * JD-Core Version:    0.7.0.1
 */