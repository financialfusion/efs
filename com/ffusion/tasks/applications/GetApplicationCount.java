package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FieldValidation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetApplicationCount
  extends Application
  implements Task
{
  protected String successURL = null;
  protected String taskErrorURL = null;
  protected String serviceErrorURL = null;
  protected int error = 0;
  protected StringList statusIDs = new StringList();
  protected StringList productIDs = new StringList();
  protected StringList bankIDs = new StringList();
  protected String fromDate = "";
  protected String toDate = "";
  protected String startRange = "";
  protected String endRange = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Integer localInteger = null;
    boolean bool;
    if ((getSsn() != null) && (!getSsn().equals("")))
    {
      bool = FieldValidation.ssn(getSsn());
      if (!bool)
      {
        if (this.endRange.equals("-1")) {
          this.endRange = "";
        }
        this.error = 7387;
        return this.taskErrorURL;
      }
    }
    if ((getFromDate() != null) && (!getFromDate().equals("")))
    {
      bool = FieldValidation.date(getFromDate());
      if (!bool)
      {
        if (this.endRange.equals("-1")) {
          this.endRange = "";
        }
        this.error = 7388;
        return this.taskErrorURL;
      }
    }
    if ((getToDate() != null) && (!getToDate().equals("")))
    {
      bool = FieldValidation.date(getToDate());
      if (!bool)
      {
        if (this.endRange.equals("-1")) {
          this.endRange = "";
        }
        this.error = 7389;
        return this.taskErrorURL;
      }
    }
    if (getAffiliateBankID() == -1)
    {
      this.error = 7217;
      return this.taskErrorURL;
    }
    SetProductListString();
    SetStatusListString();
    SetBankListString();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    setLocale(localLocale);
    com.ffusion.beans.applications.Applications localApplications = new com.ffusion.beans.applications.Applications(localLocale);
    if (this.startRange.equals("-1")) {
      this.startRange = "";
    }
    if (this.endRange.equals("-1")) {
      this.endRange = "";
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localHashMap.put("APP_COUNT", localStringBuffer);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localInteger = com.ffusion.csil.core.Applications.getApplicationCount(localSecureUser, this, this.fromDate, this.toDate, this.startRange, this.endRange, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("ApplicationCount", localInteger.toString());
    }
    return str;
  }
  
  protected void SetProductListString()
  {
    int i = 0;
    int j = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = this.productIDs.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.equals("-1"))
      {
        i = 1;
        break;
      }
      if (j > 0) {
        localStringBuffer.append(",");
      }
      localStringBuffer.append(str);
      j++;
    }
    if (i != 0) {
      setProductID("");
    } else {
      setProductID(localStringBuffer.toString());
    }
  }
  
  protected void SetStatusListString()
  {
    int i = 0;
    int j = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = this.statusIDs.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.equals("-1"))
      {
        i = 1;
        break;
      }
      if (j > 0) {
        localStringBuffer.append(",");
      }
      localStringBuffer.append(str);
      j++;
    }
    if (i != 0) {
      setStatusID("");
    } else if ((localStringBuffer != null) && (localStringBuffer.toString() != null) && (localStringBuffer.toString().length() > 0)) {
      setStatusID(localStringBuffer.toString());
    }
  }
  
  protected void SetBankListString()
  {
    int i = 0;
    int j = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = this.bankIDs.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.equals("-1"))
      {
        i = 1;
        break;
      }
      if (j > 0) {
        localStringBuffer.append(",");
      }
      localStringBuffer.append(str);
      j++;
    }
    if (i != 0) {
      setBankID("");
    } else {
      setBankID(localStringBuffer.toString());
    }
  }
  
  public void setStartRange(String paramString)
  {
    this.startRange = paramString;
  }
  
  public String getStartRange()
  {
    return this.startRange;
  }
  
  public void setEndRange(String paramString)
  {
    this.endRange = paramString;
  }
  
  public String getEndRange()
  {
    return this.endRange;
  }
  
  public void setProductIDs(String paramString)
  {
    this.productIDs.add(paramString);
  }
  
  public StringList getProductIDs()
  {
    return this.productIDs;
  }
  
  public void setStatusIDs(String paramString)
  {
    this.statusIDs.add(paramString);
  }
  
  public StringList getStatusIDs()
  {
    return this.statusIDs;
  }
  
  public void setBankIDs(String paramString)
  {
    this.bankIDs.add(paramString);
  }
  
  public StringList getBankIDs()
  {
    return this.bankIDs;
  }
  
  public void setFromDate(String paramString)
  {
    this.fromDate = paramString;
  }
  
  public String getFromDate()
  {
    return this.fromDate;
  }
  
  public void setToDate(String paramString)
  {
    this.toDate = paramString;
  }
  
  public String getToDate()
  {
    return this.toDate;
  }
  
  public void setSearchEmpty(String paramString)
  {
    this.productIDs.clear();
    this.bankIDs.clear();
    this.statusIDs.clear();
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetApplicationCount
 * JD-Core Version:    0.7.0.1
 */