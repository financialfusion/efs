package com.ffusion.tasks.bcreport;

import com.ffusion.beans.Modules;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.handlers.AdminHandler;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetModules
  extends BaseTask
  implements Task
{
  private String aO7;
  private String aO8 = "";
  public static final String BANK_APPLICATION = "BANK";
  public static final String BUSINESS_APPLICATION = "BUSINESS";
  public static final String CONSUMER_APPLICATION = "CONSUMER";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = localSecureUser.getLocale();
    Modules localModules = null;
    if (this.aO8.equals("BANK")) {
      localModules = AdminHandler.getBankModules(localLocale);
    } else if (this.aO8.equals("BUSINESS")) {
      localModules = AdminHandler.getBusinessModules(localLocale);
    } else if (this.aO8.equals("CONSUMER")) {
      localModules = AdminHandler.getConsumerModules(localLocale);
    } else {
      localModules = AdminHandler.getModules(localLocale);
    }
    if (localModules == null) {
      localModules = new Modules();
    } else {
      localModules.sortByName();
    }
    localHttpSession.setAttribute(this.aO7, localModules);
    return str;
  }
  
  public void setModuleListName(String paramString)
  {
    this.aO7 = paramString;
  }
  
  public void setApplicationName(String paramString)
  {
    this.aO8 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bcreport.GetModules
 * JD-Core Version:    0.7.0.1
 */