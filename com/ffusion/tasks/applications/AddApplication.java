package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Product;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddApplication
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    Application localApplication = (Application)localHttpSession.getAttribute("Application");
    if (localApplication == null)
    {
      this.error = 7201;
      str1 = this.taskErrorURL;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (localSecureUser != null)
      {
        localApplication.setCustomerID(String.valueOf(localSecureUser.getProfileID()));
        localApplication.setAffiliateBankID(localSecureUser.getAffiliateID());
      }
      else
      {
        localApplication.setCustomerID("0");
      }
      if (localApplication.getAffiliateBankID() < 0)
      {
        this.error = 7217;
        str1 = this.taskErrorURL;
      }
      else
      {
        Locale localLocale = null;
        if (localSecureUser != null) {
          localLocale = localSecureUser.getLocale();
        } else {
          localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
        }
        if (localLocale == null) {
          localLocale = Locale.getDefault();
        }
        ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", localLocale);
        if (localResourceBundle == null)
        {
          this.error = 7350;
          return this.taskErrorURL;
        }
        try
        {
          Product localProduct = new Product();
          localProduct.setProductID(localApplication.getProductID());
          localProduct.setBankID(localApplication.getBankID());
          localProduct = Applications.getProduct(localSecureUser, localProduct, localHashMap);
          LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.beans.applications.resources", "EMPLOYEE_EMAIL_SUBJECT", null);
          String str2 = localResourceBundle.getString("EMPLOYEE_EMAIL_ADDRESS");
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localProduct.getTitle();
          LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.beans.applications.resources", "EMPLOYEE_EMAIL_BODY", arrayOfObject);
          localHashMap.put("EMPLOYEE_EMAIL_SUBJECT", (String)localLocalizableString1.localize(localLocale));
          localHashMap.put("EMPLOYEE_EMAIL_BODY", (String)localLocalizableString2.localize(localLocale));
          localHashMap.put("EMPLOYEE_EMAIL_ADDRESS", str2);
          LocalizableString localLocalizableString3 = new LocalizableString("com.ffusion.beans.applications.resources", "HISTORY_COMMENT_ADD_APPLICATION", null);
          localApplication = Applications.addApplication(localSecureUser, localApplication, (String)localLocalizableString3.localize(localLocale), localHashMap);
          localHashMap.remove("EMPLOYEE_EMAIL_SUBJECT");
          localHashMap.remove("EMPLOYEE_EMAIL_BODY");
          localHashMap.remove("EMPLOYEE_EMAIL_ADDRESS");
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
        }
        localHttpSession.setAttribute("Application", localApplication);
      }
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.AddApplication
 * JD-Core Version:    0.7.0.1
 */