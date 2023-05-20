package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.PayeeRoute;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchGlobalPayees
  extends BaseTask
  implements GlobalPayeeTask
{
  String GL;
  String GN;
  String GJ;
  String GM;
  private String GK = "PayeesSearchList";
  
  public String getPayeeGroup()
  {
    return this.GJ;
  }
  
  public String getPayeeName()
  {
    return this.GL;
  }
  
  public String getRouteId()
  {
    return this.GM;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.GN = paramString;
  }
  
  public void setPayeeGroup(String paramString)
  {
    this.GJ = paramString;
  }
  
  public void setPayeeName(String paramString)
  {
    this.GL = paramString;
  }
  
  public void setRouteId(String paramString)
  {
    this.GM = paramString;
  }
  
  public void setSearchListName(String paramString)
  {
    this.GK = paramString;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Payees localPayees = new Payees();
    Payee localPayee1 = new Payee((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localPayee1.setName(this.GL);
    localPayee1.set("IDScope", this.GJ);
    Object localObject;
    if (localPayee1.getPayeeRoute() == null)
    {
      localObject = new PayeeRoute();
      ((PayeeRoute)localObject).setRouteID(this.GM);
      localPayee1.setPayeeRoute((PayeeRoute)localObject);
    }
    else
    {
      localPayee1.getPayeeRoute().setRouteID(this.GM);
    }
    try
    {
      localObject = new HashMap();
      localPayee1.setFiID(this.GN);
      localPayees = PaymentsAdmin.searchGlobalPayees(localSecureUser, localPayee1, (HashMap)localObject);
      String str2 = null;
      if (localPayees != null)
      {
        Iterator localIterator = localPayees.iterator();
        while (localIterator.hasNext())
        {
          Payee localPayee2 = (Payee)localIterator.next();
          str2 = localPayee2.getSearchLanguage();
          if (localPayee2.getSearchLanguage() != null) {
            localPayee2.set("Language", str2);
          } else {
            localPayee2.set("Language", "en_US");
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.GK, localPayees);
    str1 = this.successURL;
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.SearchGlobalPayees
 * JD-Core Version:    0.7.0.1
 */