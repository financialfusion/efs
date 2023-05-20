package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
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

public class GetProducts
  extends BaseTask
  implements Task, BankEmployeeTask
{
  private String fg = "en_US";
  private String fh = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Product localProduct = new Product();
    localProduct.setSearchLanguage(this.fg);
    Products localProducts = new Products();
    localHashMap.put("PRODUCTS", localProducts);
    if (localSecureUser != null)
    {
      localProduct.setLocale(localSecureUser.getLocale());
      this.fh = localSecureUser.getBankID();
    }
    localProduct.setBankID(this.fh);
    try
    {
      localProducts = Applications.getProducts(localSecureUser, localProduct, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if ((this.error == 0) || (this.error == 7034))
    {
      localHttpSession.setAttribute("Products", localProducts);
      return this.successURL;
    }
    return str;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.fg = paramString;
  }
  
  public void setBankID(String paramString)
  {
    this.fh = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetProducts
 * JD-Core Version:    0.7.0.1
 */