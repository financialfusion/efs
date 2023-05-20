package com.ffusion.tasks.admin;

import com.ffusion.beans.TransactionTypes;
import com.ffusion.csil.core.Admin;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransactionTypes
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    TransactionTypes localTransactionTypes = Admin.getTransactionTypes(localLocale);
    if (localTransactionTypes != null)
    {
      localTransactionTypes.sortByName();
      localHttpSession.setAttribute("TransactionTypes", localTransactionTypes);
    }
    return super.getSuccessURL();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetTransactionTypes
 * JD-Core Version:    0.7.0.1
 */