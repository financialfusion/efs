package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetEffectiveDate
  extends ExtendedBaseTask
  implements Task
{
  private Transfer zv;
  private DateTime zw = new DateTime();
  protected String datetype = "SHORT";
  
  public GetEffectiveDate()
  {
    this.beanSessionName = "Transfer";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 1037;
      str = this.taskErrorURL;
      return str;
    }
    HashMap localHashMap = null;
    int i = 1;
    this.zv = ((Transfer)localHttpSession.getAttribute(this.beanSessionName));
    if (this.zv == null)
    {
      i = 0;
      this.zv = new Transfer();
      this.zv.setDate(new DateTime());
      this.zv.setCustomerID(localSecureUser.getBusinessID());
      this.zv.setBankID(localSecureUser.getBPWFIID());
    }
    try
    {
      localHashMap = new HashMap();
      if (this.zv != null)
      {
        this.zv.setCustomerID(localSecureUser.getBusinessID());
        this.zv.setBankID(localSecureUser.getBPWFIID());
      }
      Date localDate = Banking.getEffectiveDate(localSecureUser, this.zv, localHashMap);
      if (localDate == null)
      {
        localDateTime = new DateTime();
        localDate = new DateTime(localDateTime, localSecureUser.getLocale()).getTime();
      }
      DateTime localDateTime = new DateTime(localDate, localSecureUser.getLocale());
      if (this.dateFormat != null) {
        localDateTime.setFormat(this.dateFormat);
      }
      if (i != 0) {
        this.zv.setDate(localDateTime);
      } else {
        setEffectiveDate(localDateTime);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public String getEffectiveDate()
  {
    return this.zw.toString();
  }
  
  public void setEffectiveDate(DateTime paramDateTime)
  {
    this.zw = paramDateTime;
  }
  
  public void setEffectiveDate(String paramString)
  {
    try
    {
      if (this.zw == null) {
        this.zw = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.zw.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.zw != null) {
      this.zw.setFormat(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetEffectiveDate
 * JD-Core Version:    0.7.0.1
 */