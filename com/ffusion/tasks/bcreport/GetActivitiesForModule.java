package com.ffusion.tasks.bcreport;

import com.ffusion.beans.TransactionTypes;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.handlers.AdminHandler;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetActivitiesForModule
  extends BaseTask
  implements Task
{
  private String aPd = "AllModules";
  private String aPc = null;
  private String aPe = "";
  public static final String BANK_APPLICATION = "BANK";
  public static final String BUSINESS_APPLICATION = "BUSINESS";
  public static final String CONSUMER_APPLICATION = "CONSUMER";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = null;
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    if (localUserLocale != null) {
      localLocale = localUserLocale.getLocale();
    }
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    TransactionTypes localTransactionTypes = null;
    if (this.aPd.equals("AllModules"))
    {
      if (this.aPe.equals("BANK")) {
        localTransactionTypes = AdminHandler.getBankTransactionTypes(localLocale);
      } else if (this.aPe.equals("BUSINESS")) {
        localTransactionTypes = AdminHandler.getBusinessTransactionTypes(localLocale);
      } else if (this.aPe.equals("CONSUMER")) {
        localTransactionTypes = AdminHandler.getConsumerTransactionTypes(localLocale);
      } else {
        localTransactionTypes = AdminHandler.getTransactionTypes(localLocale);
      }
    }
    else if (this.aPe.equals("BANK")) {
      localTransactionTypes = AdminHandler.getBankTransactionTypesForModule(localLocale, this.aPd);
    } else if (this.aPe.equals("BUSINESS")) {
      localTransactionTypes = AdminHandler.getBusinessTransactionTypesForModule(localLocale, this.aPd);
    } else if (this.aPe.equals("CONSUMER")) {
      localTransactionTypes = AdminHandler.getConsumerTransactionTypesForModule(localLocale, this.aPd);
    } else {
      localTransactionTypes = AdminHandler.getTransactionTypesForModule(localLocale, this.aPd);
    }
    if (localTransactionTypes == null) {
      localTransactionTypes = new TransactionTypes();
    } else {
      localTransactionTypes.sortByName();
    }
    localHttpSession.setAttribute(this.aPc, localTransactionTypes);
    return str;
  }
  
  public void setModule(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.aPd = "AllModules";
    } else {
      this.aPd = paramString;
    }
  }
  
  public void setActivityListName(String paramString)
  {
    this.aPc = paramString;
  }
  
  public void setApplicationName(String paramString)
  {
    this.aPe = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bcreport.GetActivitiesForModule
 * JD-Core Version:    0.7.0.1
 */