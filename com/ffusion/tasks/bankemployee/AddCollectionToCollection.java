package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.CollectionElement;
import com.ffusion.beans.IdCollection;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.user.Users;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCollectionToCollection
  extends BaseTask
  implements BankEmployeeTask
{
  private String tK = "";
  private String tJ = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    Object localObject = (IdCollection)localHttpSession.getAttribute(this.tK);
    IdCollection localIdCollection = (IdCollection)localHttpSession.getAttribute(this.tJ);
    if (localIdCollection != null)
    {
      if (localObject == null) {
        if (this.tK.indexOf("Emp") != -1) {
          localObject = new BankEmployees((Locale)localHttpSession.getAttribute("java.util.Locale"));
        } else {
          localObject = new Users((Locale)localHttpSession.getAttribute("java.util.Locale"));
        }
      }
      Iterator localIterator = localIdCollection.iterator();
      while (localIterator.hasNext())
      {
        CollectionElement localCollectionElement = (CollectionElement)localIterator.next();
        if (((IdCollection)localObject).getElementByID(localCollectionElement.getId()) == null) {
          ((IdCollection)localObject).add(localCollectionElement);
        }
      }
      localHttpSession.setAttribute(this.tK, localObject);
    }
    return str;
  }
  
  public void setCollectionToUpdate(String paramString)
  {
    this.tK = paramString;
  }
  
  public void setCollectionToAdd(String paramString)
  {
    this.tJ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.AddCollectionToCollection
 * JD-Core Version:    0.7.0.1
 */