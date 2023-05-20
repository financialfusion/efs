package com.ffusion.tasks.applications;

import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetProduct
  extends BaseTask
  implements Task
{
  protected String productID = "-1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Products localProducts = (Products)localHttpSession.getAttribute("Products");
    if (localProducts == null)
    {
      this.error = 7250;
      return this.taskErrorURL;
    }
    Product localProduct = localProducts.getByID(this.productID);
    if (localProduct != null)
    {
      localHttpSession.setAttribute("Product", localProduct);
      return this.successURL;
    }
    this.error = 7252;
    return this.taskErrorURL;
  }
  
  public void setProductID(String paramString)
  {
    this.productID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.SetProduct
 * JD-Core Version:    0.7.0.1
 */