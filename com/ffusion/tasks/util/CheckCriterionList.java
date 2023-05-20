package com.ffusion.tasks.util;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckCriterionList
  extends BaseTask
{
  private String Rx = null;
  private String Rw = null;
  private HashSet Rv = null;
  private String Ru = null;
  
  public void setCriterionListFromSession(String paramString)
  {
    this.Rx = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.Rx == null)
    {
      this.error = 63;
      return this.taskErrorURL;
    }
    this.Rw = ((String)localHttpSession.getAttribute(this.Rx));
    if (this.Rw == null) {
      return this.successURL;
    }
    this.Rv = new HashSet();
    if ((this.Rw.equals("")) || (this.Rw.startsWith(","))) {
      this.Rv.add("");
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(this.Rw, ",", false);
    while (localStringTokenizer.hasMoreTokens()) {
      this.Rv.add(localStringTokenizer.nextToken());
    }
    return this.successURL;
  }
  
  public void setValueToCheck(String paramString)
  {
    this.Ru = paramString;
  }
  
  public boolean getFoundInList()
  {
    if ((this.Rw == null) || (this.Ru == null)) {
      return false;
    }
    return this.Rv.contains(this.Ru);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.CheckCriterionList
 * JD-Core Version:    0.7.0.1
 */