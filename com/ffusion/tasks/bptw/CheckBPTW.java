package com.ffusion.tasks.bptw;

import com.ffusion.tasks.InitService;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckBPTW
  extends InitService
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    InputStream localInputStream = ResourceUtil.getResourceAsStream(this, this.initURL);
    if (localInputStream == null) {
      localHttpSession.setAttribute("BptwFlag", "false");
    } else {
      localHttpSession.setAttribute("BptwFlag", "true");
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.CheckBPTW
 * JD-Core Version:    0.7.0.1
 */