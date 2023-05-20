package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransferPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetWireTransferPayeesAllScopes
  extends BaseTask
  implements WireTaskDefines
{
  protected static boolean reload = false;
  protected String payeeName = "WireTransferPayees";
  protected String businessID = null;
  private String Dp = "REGULAR";
  private String Dq;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    WireTransferPayees localWireTransferPayees1 = (WireTransferPayees)localHttpSession.getAttribute(this.payeeName);
    if (reload)
    {
      localWireTransferPayees1 = null;
      localHttpSession.removeAttribute(this.payeeName);
      reload = false;
    }
    if (localWireTransferPayees1 == null)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        WireTransferPayee localWireTransferPayee1 = new WireTransferPayee();
        localWireTransferPayee1.setCustomerID(this.businessID);
        localWireTransferPayee1.setPayeeDestination(this.Dp);
        localWireTransferPayee1.setSubmittedBy(null);
        localWireTransferPayees1 = Wire.getWireTransferPayees(localSecureUser, localWireTransferPayee1, localHashMap);
        com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business();
        localBusiness.setId(this.businessID);
        localBusiness = com.ffusion.csil.core.Business.getBusiness(localSecureUser, localBusiness, localHashMap);
        BusinessEmployee localBusinessEmployee1 = new BusinessEmployee();
        localBusinessEmployee1.setBusinessId(this.businessID);
        localBusinessEmployee1.setBankId(localBusiness.getBankId());
        BusinessEmployees localBusinessEmployees = UserAdmin.getBusinessEmployees(localSecureUser, localBusinessEmployee1, localHashMap);
        Iterator localIterator1 = localBusinessEmployees.iterator();
        while (localIterator1.hasNext())
        {
          BusinessEmployee localBusinessEmployee2 = (BusinessEmployee)localIterator1.next();
          WireTransferPayee localWireTransferPayee2 = new WireTransferPayee();
          localWireTransferPayee2.setCustomerID(this.businessID);
          localWireTransferPayee2.setPayeeDestination(this.Dp);
          localWireTransferPayee2.setSubmittedBy(localBusinessEmployee2.getId());
          WireTransferPayees localWireTransferPayees2 = Wire.getWireTransferPayees(localSecureUser, localWireTransferPayee2, localHashMap);
          Iterator localIterator2 = localWireTransferPayees2.iterator();
          while (localIterator2.hasNext())
          {
            WireTransferPayee localWireTransferPayee3 = (WireTransferPayee)localIterator2.next();
            if (localWireTransferPayee3.getPayeeScope().equals("USER")) {
              localWireTransferPayees1.add(localWireTransferPayee3);
            }
          }
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        Collections.sort(localWireTransferPayees1);
        localHttpSession.setAttribute(this.payeeName, localWireTransferPayees1);
      }
    }
    return str;
  }
  
  public void setReload(String paramString)
  {
    reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public void setBusinessID(String paramString)
  {
    this.businessID = paramString;
  }
  
  public String getBusinessID()
  {
    return this.businessID;
  }
  
  public void setPayeeDestination(String paramString)
  {
    this.Dp = paramString;
  }
  
  public String getPayeeDestination()
  {
    return this.Dp;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetWireTransferPayeesAllScopes
 * JD-Core Version:    0.7.0.1
 */