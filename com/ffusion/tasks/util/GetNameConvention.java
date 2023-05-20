package com.ffusion.tasks.util;

import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetNameConvention
  extends BaseTask
{
  public static final String NAME_CONVENTION = "NameConvention";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = Util.getNameConvention();
    localHttpSession.setAttribute("NameConvention", str);
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.GetNameConvention
 * JD-Core Version:    0.7.0.1
 */