package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.Task;
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

public class GetDefaultACHFrequencies
  extends ArrayList
  implements Task
{
  private String aQL = null;
  private String aQM = null;
  private int aQI = 0;
  private SecureUser aQK;
  private String aQH = "com.ffusion.beans.ach.resources";
  private Locale aQG;
  private String aQJ;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.aQI = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.aQK = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.aQG = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (this.aQG == null)
    {
      this.aQI = 41;
      str = this.aQM;
    }
    else if ((this.aQH != null) && (this.aQH.length() > 0))
    {
      ResourceBundle localResourceBundle = ResourceUtil.getBundle(this.aQH, this.aQG);
      if (localResourceBundle == null)
      {
        this.aQI = 43;
        str = this.aQM;
      }
      else
      {
        str = this.aQL;
      }
    }
    else
    {
      str = this.aQL;
    }
    return str;
  }
  
  public String getDefaultACHFrequencies()
    throws CSILException
  {
    if (this.aQG != null)
    {
      clear();
      ArrayList localArrayList = new ArrayList();
      String str1 = ResourceUtil.getString(this.aQJ, this.aQH, this.aQG);
      Object localObject;
      if (str1 != null)
      {
        localObject = new StringTokenizer(str1, ",", false);
        while (((StringTokenizer)localObject).hasMoreTokens()) {
          localArrayList.add(((StringTokenizer)localObject).nextToken());
        }
      }
      if (localArrayList != null)
      {
        localObject = ACH.getDefaultACHFrequencies(this.aQK);
        if (localObject != null) {
          for (int i = 0; i < ((ArrayList)localObject).size(); i++)
          {
            String str2 = ResourceUtil.getString(this.aQJ + ((ArrayList)localObject).get(i), this.aQH, this.aQG);
            if (localArrayList.contains(str2)) {
              add(str2);
            }
          }
        }
      }
    }
    return "";
  }
  
  public void setPrefixFrequencyResource(String paramString)
  {
    this.aQJ = paramString;
  }
  
  public String getPrefixFrequencyResource()
  {
    return this.aQJ;
  }
  
  public void setResourceFileName(String paramString)
  {
    this.aQH = paramString;
  }
  
  public String getResourceFileName()
  {
    return this.aQH;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.aQL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.aQM = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.aQI);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetDefaultACHFrequencies
 * JD-Core Version:    0.7.0.1
 */