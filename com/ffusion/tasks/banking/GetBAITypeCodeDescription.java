package com.ffusion.tasks.banking;

import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBAITypeCodeDescription
  extends BaseTask
{
  private String aQa = "";
  private String aQb = "java.util.Locale";
  private String aP9 = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    BAITypeCodeInfo localBAITypeCodeInfo = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    localBAITypeCodeInfo = (BAITypeCodeInfo)localHttpSession.getAttribute(this.aQa);
    if (localBAITypeCodeInfo == null)
    {
      localBAITypeCodeInfo = (BAITypeCodeInfo)paramHttpServletRequest.getAttribute(this.aQa);
      if (localBAITypeCodeInfo == null)
      {
        this.error = 63;
        str = this.taskErrorURL;
      }
    }
    if (this.error == 0)
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute(this.aQb);
      if (localLocale == null)
      {
        this.error = 41;
        str = this.taskErrorURL;
      }
      else
      {
        this.aP9 = localBAITypeCodeInfo.getDescription(localLocale);
      }
    }
    return str;
  }
  
  public String getDescription()
  {
    return this.aP9;
  }
  
  public void setBAITypeCodeInfoItemName(String paramString)
  {
    this.aQa = paramString;
  }
  
  public void setLocaleName(String paramString)
  {
    this.aQb = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetBAITypeCodeDescription
 * JD-Core Version:    0.7.0.1
 */