package com.ffusion.tasks.alerts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.alerts.AccountAlert;
import com.ffusion.beans.user.ContactPoints;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FillSummaryAlert
  extends FillUserAlert
{
  protected String _account;
  protected boolean _init = false;
  protected boolean _updateExisitngAlertBean = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this._init)
    {
      localObject = (AccountAlert)localHttpSession.getAttribute(this._alertSessionKey);
      if (localObject == null)
      {
        this.error = 19001;
        str = this.taskErrorURL;
      }
      else
      {
        if (((AccountAlert)localObject).getAccountId() == null) {
          this._account = "All Accounts";
        } else {
          this._account = (((AccountAlert)localObject).getRoutingNumber() + ";" + ((AccountAlert)localObject).getAccountId() + ";" + ((AccountAlert)localObject).getAccountType());
        }
        initDeliveryInfos(localHttpSession);
      }
      return str;
    }
    Object localObject = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ContactPoints localContactPoints = (ContactPoints)localHttpSession.getAttribute(this._contactPointsCollections);
    if (localContactPoints == null)
    {
      this.error = 19307;
      return this.taskErrorURL;
    }
    if (b(localHttpSession))
    {
      AccountAlert localAccountAlert;
      if (this._updateExisitngAlertBean) {
        localAccountAlert = (AccountAlert)localHttpSession.getAttribute(this._alertSessionKey);
      } else {
        localAccountAlert = new AccountAlert();
      }
      localAccountAlert.setAlertType(2);
      localAccountAlert.setStatus(1);
      localAccountAlert.setDirectoryId(((SecureUser)localObject).getProfileID());
      localAccountAlert.setDeliveryInfos(constructDeliveryInfos(localHttpSession));
      if (!this._account.equals("All Accounts"))
      {
        String[] arrayOfString = this._account.split(";");
        localAccountAlert.setRoutingNumber(arrayOfString[0]);
        localAccountAlert.setAccountId(arrayOfString[1]);
        localAccountAlert.setAccountType(arrayOfString[2]);
      }
      else
      {
        localAccountAlert.setRoutingNumber(null);
        localAccountAlert.setAccountId(null);
        localAccountAlert.setAccountType(-2147483648);
      }
      localHttpSession.setAttribute(this._alertSessionKey, localAccountAlert);
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  private boolean b(HttpSession paramHttpSession)
  {
    return validateDeliveryInfos(paramHttpSession);
  }
  
  public void setAccount(String paramString)
  {
    this._account = paramString;
  }
  
  public String getAccount()
  {
    return this._account;
  }
  
  public void setInit(String paramString)
  {
    try
    {
      this._init = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getInit()
  {
    return Boolean.toString(this._init);
  }
  
  public void setUpdateExisitngAlertBean(String paramString)
  {
    try
    {
      this._updateExisitngAlertBean = Boolean.valueOf(paramString).booleanValue();
    }
    catch (Exception localException) {}
  }
  
  public String getUpdateExisitngAlertBean()
  {
    return Boolean.toString(this._updateExisitngAlertBean);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.alerts.FillSummaryAlert
 * JD-Core Version:    0.7.0.1
 */