package com.ffusion.tasks.applications;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FieldValidation;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApplications
  extends GetApplicationCount
  implements Task
{
  private static String uy = "MM/DD/YYYY";
  private UserLocale uz = null;
  private String taskErrorURL = "TE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.uz = ((UserLocale)localHttpSession.getAttribute("UserLocale"));
    uy = this.uz.getDateFormat();
    if ((getSsn() != null) && (!getSsn().equals("")))
    {
      boolean bool = FieldValidation.ssn(getSsn());
      if (!bool)
      {
        if (this.endRange.equals("-1")) {
          this.endRange = "";
        }
        this.error = 7387;
        return this.taskErrorURL;
      }
    }
    DateTime localDateTime1 = null;
    if ((this.fromDate != null) && (this.fromDate.trim().length() > 0) && (!this.fromDate.equals(uy))) {
      try
      {
        localDateTime1 = new DateTime(this.fromDate, this.uz.getLocale(), this.uz.getDateFormat());
      }
      catch (InvalidDateTimeException localInvalidDateTimeException1)
      {
        if (this.endRange.equals("-1")) {
          this.endRange = "";
        }
        this.error = 44;
        return this.taskErrorURL;
      }
    }
    DateTime localDateTime2 = null;
    if ((this.toDate != null) && (this.toDate.trim().length() > 0) && (!this.toDate.equals(uy))) {
      try
      {
        localDateTime2 = new DateTime(this.toDate, this.uz.getLocale(), this.uz.getDateFormat());
      }
      catch (InvalidDateTimeException localInvalidDateTimeException2)
      {
        if (this.endRange.equals("-1")) {
          this.endRange = "";
        }
        this.error = 44;
        return this.taskErrorURL;
      }
    }
    if ((localDateTime1 != null) && (localDateTime2 != null))
    {
      long l = localDateTime1.getTime().getTime() - localDateTime2.getTime().getTime();
      if (l > 0L)
      {
        this.error = 130;
        return this.taskErrorURL;
      }
    }
    SetProductListString();
    SetStatusListString();
    SetBankListString();
    BankEmployee localBankEmployee = (BankEmployee)localHttpSession.getAttribute("BankEmployee");
    Locale localLocale = this.uz.getLocale();
    setLocale(localLocale);
    if (this.startRange.equals("-1")) {
      this.startRange = "";
    }
    if (this.endRange.equals("-1")) {
      this.endRange = "";
    }
    com.ffusion.beans.applications.Applications localApplications = new com.ffusion.beans.applications.Applications(localLocale);
    localHashMap.put("APPLICATIONS", localApplications);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localApplications = com.ffusion.csil.core.Applications.getApplications(localSecureUser, this, getFromDate(localDateTime1), getToDate(localDateTime2), this.startRange, this.endRange, localHashMap);
      if (localApplications != null)
      {
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.applications.resources", "PUBLIC_WEBSITE_USER_NAME", null);
        String str2 = (String)localLocalizableString.localize(localLocale);
        Iterator localIterator = localApplications.iterator();
        Application localApplication = null;
        while (localIterator.hasNext())
        {
          localApplication = (Application)localIterator.next();
          if ((localApplication.getCustomerID() != null) && (localApplication.getCustomerID().equals("0"))) {
            localApplication.setUserName(str2);
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("Applications", localApplications);
      localHttpSession.setAttribute("BankIDs", this.bankIDs);
      localHttpSession.setAttribute("ProductIDs", this.productIDs);
      localHttpSession.setAttribute("StatusIDs", this.statusIDs);
    }
    return str1;
  }
  
  public void setFromDate(String paramString)
  {
    this.fromDate = paramString;
  }
  
  public String getFromDate(DateTime paramDateTime)
  {
    if (paramDateTime != null) {
      return paramDateTime.toString();
    }
    return "";
  }
  
  public void setToDate(String paramString)
  {
    this.toDate = paramString;
  }
  
  public String getToDate(DateTime paramDateTime)
  {
    if (paramDateTime != null) {
      return paramDateTime.toString();
    }
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetApplications
 * JD-Core Version:    0.7.0.1
 */