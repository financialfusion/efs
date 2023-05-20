package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatches;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRecACHBatches
  extends GetACHBatches
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str;
    if (getRecACHBatches(localHttpSession)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getRecACHBatches(HttpSession paramHttpSession)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    ACHBatches localACHBatches = new ACHBatches(localLocale);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    if (this.error == 0) {
      paramHttpSession.setAttribute(this.batchesName, localACHBatches);
    }
    return this.error == 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetRecACHBatches
 * JD-Core Version:    0.7.0.1
 */