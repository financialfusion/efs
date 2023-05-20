package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
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

public class GetStates
  extends BaseTask
{
  private String Rn = new String("StatesAbbr_List");
  private String Ro = new String("StatesFull_List");
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
    if (localResourceBundle == null)
    {
      this.error = 43;
      return this.taskErrorURL;
    }
    String str1 = localResourceBundle.getString("StateAbbr").toUpperCase();
    localHttpSession.setAttribute(this.Rn, t(str1));
    String str2 = localResourceBundle.getString("StateNames");
    localHttpSession.setAttribute(this.Ro, t(str2));
    return this.successURL;
  }
  
  private ArrayList t(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    while (localStringTokenizer.hasMoreElements()) {
      localArrayList.add(localStringTokenizer.nextElement());
    }
    return localArrayList;
  }
  
  public void setStatesAbbrName(String paramString)
  {
    this.Rn = paramString;
  }
  
  public void setStatesFullName(String paramString)
  {
    this.Ro = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetStates
 * JD-Core Version:    0.7.0.1
 */