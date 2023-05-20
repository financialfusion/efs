package com.ffusion.tasks;

import com.ffusion.csil.core.CurrencyUtil;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCurrencyCode
  extends BaseTask
{
  private String aQv;
  private String aQw;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str2 = CurrencyUtil.getCurrencyCodeByBusinessId(Integer.parseInt(this.aQw));
    if (str2 != null) {
      setCurrencyCode(str2);
    }
    return str1;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.aQv = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.aQv;
  }
  
  public void setBusinessID(String paramString)
  {
    this.aQw = paramString;
  }
  
  public String getBusinessID()
  {
    return this.aQw;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.GetCurrencyCode
 * JD-Core Version:    0.7.0.1
 */