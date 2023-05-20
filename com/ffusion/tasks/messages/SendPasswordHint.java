package com.ffusion.tasks.messages;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendPasswordHint
  extends SendMessage
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    setLocale((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if (getMemo().length() > this.maxSize)
    {
      this.error = 8077;
      str = this.taskErrorURL;
      return str;
    }
    if (validateInput(localHttpSession))
    {
      if (this.processFlag) {
        str = sendMessage(localHttpSession);
      } else {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate != null) {
      if ((this.validate.indexOf(TO) != -1) && ((getTo() == null) || (getTo().length() == 0))) {
        this.error = 8017;
      } else if ((this.validate.indexOf(MEMO) != -1) && ((getMemo() == null) || (getMemo().length() == 0))) {
        this.error = 8018;
      } else if (this.error == 0) {
        super.validateInput(paramHttpSession);
      }
    }
    return this.error == 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendPasswordHint
 * JD-Core Version:    0.7.0.1
 */