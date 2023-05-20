package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.csil.core.Messages;
import com.ffusion.services.Messaging;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.bankemployee.BankEmployeeTask;
import com.ffusion.tasks.messages.SendMessage;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NotifyEmployees
  extends SendMessage
  implements Task, BankEmployeeTask
{
  protected String successURL;
  protected int error = 0;
  protected String taskErrorURL;
  protected String serviceErrorURL;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
    if (localBankEmployees == null)
    {
      this.error = 5503;
      DebugLog.log("NotifyEmployees Task Error: " + this.error);
      return this.taskErrorURL;
    }
    localBankEmployees.setFilter("All");
    Application localApplication = (Application)localHttpSession.getAttribute("Application");
    if (localApplication == null)
    {
      this.error = 7201;
      DebugLog.log("NotifyEmployees Task Error: " + this.error);
      return this.taskErrorURL;
    }
    Products localProducts = (Products)localHttpSession.getAttribute("Products");
    Product localProduct = null;
    Object localObject;
    if (localProducts == null)
    {
      localProducts = new Products();
      localProduct = new Product();
      localHashMap.put("PRODUCTS", localProducts);
      localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localProducts = Applications.getProducts((SecureUser)localObject, localProduct, localHashMap);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        str1 = this.serviceErrorURL;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("Products", localProducts);
      }
    }
    if (this.error == 0)
    {
      localObject = (Locale)localHttpSession.getAttribute("java.util.Locale");
      ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.applications.resources", (Locale)localObject);
      if (localResourceBundle == null)
      {
        this.error = 7350;
        DebugLog.log("NotifyEmployees Task Error: " + this.error);
        return this.taskErrorURL;
      }
      String str2 = localResourceBundle.getString("EMPLOYEE_EMAIL_SUBJECT");
      String str3 = localResourceBundle.getString("EMPLOYEE_EMAIL_BODY");
      localProduct = localProducts.getByID(localApplication.getProductID());
      str3 = str3 + "\r\n\r\n" + localProduct.getTitle();
      String str4 = localApplication.getOwner();
      if ((str4 == null) || (str4.equals("")))
      {
        this.error = 7216;
        DebugLog.log("NotifyEmployees Task Error: " + this.error);
        return this.taskErrorURL;
      }
      BankEmployee localBankEmployee = localBankEmployees.getByID(str4);
      if (localBankEmployee == null)
      {
        this.error = 5502;
        DebugLog.log("NotifyEmployees Task Error: " + this.error);
        return this.taskErrorURL;
      }
      String str5 = localBankEmployee.getNotify();
      if ((str5 != null) && (str5.equals("1")))
      {
        localHashMap = null;
        Messaging localMessaging = (Messaging)localHttpSession.getAttribute("com.ffusion.tasks.messages.EmailMessages");
        if (localMessaging != null)
        {
          localHashMap = new HashMap();
          localHashMap.put("SERVICE", localMessaging);
        }
        setTo(localBankEmployee.getEmail());
        setFrom(localBankEmployee.getEmail());
        setSubject(str2);
        setMemo(str3);
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          Messages.sendMessage(localSecureUser, this, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
          str1 = this.serviceErrorURL;
        }
      }
    }
    return str1;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.NotifyEmployees
 * JD-Core Version:    0.7.0.1
 */