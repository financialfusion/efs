package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.core.WireUtil;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSupportRelease
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean supportRelease = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.supportRelease = WireUtil.supportRelease(localSecureUser, localHashMap);
    return str;
  }
  
  public String getSupportRelease()
  {
    return String.valueOf(this.supportRelease);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetSupportRelease
 * JD-Core Version:    0.7.0.1
 */