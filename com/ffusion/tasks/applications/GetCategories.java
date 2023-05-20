package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Categories;
import com.ffusion.beans.applications.Category;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.bankemployee.BankEmployeeTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCategories
  extends BaseTask
  implements Task, BankEmployeeTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Category localCategory = new Category();
    Categories localCategories = new Categories();
    localHashMap.put("CATEGORIES", localCategories);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localCategory.setBankID(localSecureUser.getBankID());
    try
    {
      localCategories = Applications.getCategories(localSecureUser, localCategory, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("Categories", localCategories);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetCategories
 * JD-Core Version:    0.7.0.1
 */