package com.ffusion.tasks.bptw;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BPTW;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.services.bptw.BptwService;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCommonPayees
  extends BaseTask
  implements Task
{
  private String kq = "CommonPayees";
  private String kp = "CommonPayees";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BptwService localBptwService = (BptwService)localHttpSession.getAttribute("BptwService");
    PayeeInfo[] arrayOfPayeeInfo = null;
    HashMap localHashMap = null;
    if (localBptwService != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBptwService);
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      arrayOfPayeeInfo = BPTW.getPreferredPayees(localSecureUser, this.kp, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (arrayOfPayeeInfo == null)
    {
      this.error = 30000;
      return this.serviceErrorURL;
    }
    Payees localPayees = new Payees();
    for (int i = 0; i < arrayOfPayeeInfo.length; i++)
    {
      PayeeInfo localPayeeInfo = arrayOfPayeeInfo[i];
      Payee localPayee = new Payee();
      localPayee.setID(localPayeeInfo.PayeeID);
      localPayee.setHostID(String.valueOf(localPayeeInfo.RouteID));
      localPayee.setName(localPayeeInfo.PayeeName);
      localPayee.setNickName(localPayeeInfo.NickName);
      localPayee.setStreet(localPayeeInfo.Addr1);
      localPayee.setStreet2(localPayeeInfo.Addr2);
      localPayee.setCity(localPayeeInfo.City);
      localPayee.setState(localPayeeInfo.State);
      localPayee.setCountry(localPayeeInfo.Country);
      localPayee.setZipCode(localPayeeInfo.Zipcode);
      localPayee.setDaysToPay(localPayeeInfo.DaysToPay);
      try
      {
        localPayee.setStatus(Integer.parseInt(localPayeeInfo.Status));
      }
      catch (NumberFormatException localNumberFormatException) {}
      localPayees.add(localPayee);
    }
    localHttpSession.setAttribute(this.kq, localPayees);
    return this.successURL;
  }
  
  public void setCollectionName(String paramString)
  {
    this.kq = paramString;
  }
  
  public String getCollectionName()
  {
    return this.kq;
  }
  
  public void setPayeeLevelType(String paramString)
  {
    this.kp = paramString;
  }
  
  public String getPayeeLevelType()
  {
    return this.kp;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bptw.GetCommonPayees
 * JD-Core Version:    0.7.0.1
 */