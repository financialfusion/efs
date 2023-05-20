package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.Locations;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetLocationsByAccount
  extends BaseTask
  implements Task
{
  protected String _fiId;
  protected String _customerId;
  protected String _cashConCompId;
  protected String _acctId;
  protected String _isDisb;
  protected String _sessionName = "Locations";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      Boolean localBoolean = Boolean.valueOf(this._isDisb);
      Locations localLocations = CashCon.getLocationsByAccount(localSecureUser, this._fiId, this._customerId, this._cashConCompId, this._acctId, localBoolean.booleanValue(), localHashMap);
      localHttpSession.setAttribute(this._sessionName, localLocations);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setFiId(String paramString)
  {
    this._fiId = paramString;
  }
  
  public void setCashConCustomerID(String paramString)
  {
    this._customerId = paramString;
  }
  
  public void setCashConCompanyID(String paramString)
  {
    this._cashConCompId = paramString;
  }
  
  public void setAccountID(String paramString)
  {
    this._acctId = paramString;
  }
  
  public void setIsDisbAccount(String paramString)
  {
    this._isDisb = paramString;
  }
  
  public void setSessionName(String paramString)
  {
    this._sessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetLocationsByAccount
 * JD-Core Version:    0.7.0.1
 */