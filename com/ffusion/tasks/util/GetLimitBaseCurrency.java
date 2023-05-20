package com.ffusion.tasks.util;

import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetLimitBaseCurrency
  extends BaseTask
{
  public static final String BASE_CURRENCY = "BaseCurrency";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = Util.getLimitBaseCurrency();
    localHttpSession.setAttribute("BaseCurrency", str);
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetLimitBaseCurrency
 * JD-Core Version:    0.7.0.1
 */