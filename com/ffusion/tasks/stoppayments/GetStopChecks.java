package com.ffusion.tasks.stoppayments;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.services.Stops2;
import com.ffusion.services.Stops3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.PagingContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStopChecks
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  private String kH;
  private String kG;
  private String kJ;
  private String kF;
  private String kK;
  private String kE;
  private String kI;
  private StopChecks kL = new StopChecks();
  protected Locale locale = Locale.getDefault();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.Stops localStops = (com.ffusion.services.Stops)localHttpSession.getAttribute("com.ffusion.services.Stops");
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.kH);
    if ((this.kF != null) && (this.kK != null) && (jdMethod_char(this.kF).after(jdMethod_char(this.kK))))
    {
      this.error = 79;
      localHttpSession.setAttribute("EndDate", this.kF);
      return str;
    }
    if (localStops == null)
    {
      if (processGetStopChecks(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    else if (((localStops instanceof Stops2)) && (localAccounts == null))
    {
      this.error = 13011;
    }
    else if (this.kH == null)
    {
      this.error = 13002;
      str = this.taskErrorURL;
    }
    else if (processGetStopChecks(localStops, localHttpSession))
    {
      str = this.successURL;
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean processGetStopChecks(HttpSession paramHttpSession)
  {
    StopChecks localStopChecks = (StopChecks)paramHttpSession.getAttribute("StopChecks");
    if ((localStopChecks == null) || (this.reload == true))
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      localStopChecks = new StopChecks(localSecureUser.getLocale());
      PagingContext localPagingContext = new PagingContext(null, null);
      HashMap localHashMap1 = new HashMap();
      localPagingContext.setMap(localHashMap1);
      HashMap localHashMap2 = new HashMap();
      localHashMap1.put("SEARCH_CRITERIA", localHashMap2);
      localHashMap2.put("StartDate", jdMethod_char(this.kF));
      localHashMap2.put("EndDate", jdMethod_char(this.kK));
      if ((this.kH != null) && (this.kH.compareTo("BankingAccounts") != 0)) {
        localHashMap2.put("AccountId", this.kH);
      }
      if ((this.kG != null) && (this.kG.trim().length() > 0)) {
        localHashMap2.put("PayeeName", this.kG.trim());
      }
      if ((this.kJ != null) && (this.kJ.trim().length() > 0)) {
        localHashMap2.put("Amount", this.kJ.trim());
      }
      if ((this.kI != null) && (this.kI.trim().length() > 0)) {
        localHashMap2.put("StopPaymentStatus", this.kI.trim());
      }
      try
      {
        localStopChecks = com.ffusion.csil.core.Stops.getStopPayments(localSecureUser, localPagingContext, new HashMap());
        String str = (String)paramHttpSession.getAttribute("SortedBy");
        if ((str != null) && (str.length() > 0)) {
          localStopChecks.setSortedBy(str);
        }
        if (this.kI == null) {
          paramHttpSession.setAttribute("StopChecks", localStopChecks);
        } else {
          paramHttpSession.setAttribute("StopChecks" + this.kI, localStopChecks);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        return false;
      }
    }
    return true;
  }
  
  private Calendar jdMethod_char(String paramString)
  {
    Calendar localCalendar = Calendar.getInstance();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(getDateFormat());
    try
    {
      Date localDate = localSimpleDateFormat.parse(paramString);
      localCalendar.setTime(localDate);
    }
    catch (Exception localException) {}
    return localCalendar;
  }
  
  protected boolean processGetStopChecks(com.ffusion.services.Stops paramStops, HttpSession paramHttpSession)
  {
    boolean bool = true;
    StopChecks localStopChecks = (StopChecks)paramHttpSession.getAttribute("StopChecks");
    if ((localStopChecks == null) || (this.reload == true))
    {
      localStopChecks = new StopChecks(this.locale);
      SecureUser localSecureUser;
      Accounts localAccounts;
      if ((paramStops instanceof Stops3)) {
        try
        {
          HashMap localHashMap1 = new HashMap(2);
          localHashMap1.put("SERVICE", paramStops);
          localHashMap1.put("STOPCHECKS", localStopChecks);
          localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          localAccounts = (Accounts)paramHttpSession.getAttribute(this.kH);
          String str1 = (String)paramHttpSession.getAttribute("StartDate");
          String str2 = (String)paramHttpSession.getAttribute("EndDate");
          DateTime localDateTime1 = new DateTime();
          UserLocale localUserLocale = (UserLocale)paramHttpSession.getAttribute("UserLocale");
          localDateTime1.setFormat(localUserLocale.getDateFormat());
          if ((str1 != null) && (str1.length() > 1)) {
            localDateTime1.setDate(str1);
          } else {
            localDateTime1 = null;
          }
          DateTime localDateTime2 = new DateTime();
          localDateTime2.setFormat(localUserLocale.getDateFormat());
          if ((str1 != null) && (str1.length() > 0)) {
            localDateTime2.setDate(str2);
          } else {
            localDateTime2 = null;
          }
          localStopChecks = com.ffusion.csil.core.Stops.getStopPayments(localSecureUser, localAccounts, localDateTime1, localDateTime2, localHashMap1);
        }
        catch (CSILException localCSILException1)
        {
          this.error = MapError.mapError(localCSILException1);
          bool = false;
        }
      } else if ((paramStops instanceof Stops2)) {
        try
        {
          HashMap localHashMap2 = new HashMap(2);
          localHashMap2.put("SERVICE", paramStops);
          localHashMap2.put("STOPCHECKS", localStopChecks);
          localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          localAccounts = (Accounts)paramHttpSession.getAttribute(this.kH);
          localStopChecks = com.ffusion.csil.core.Stops.getStopPayments(localSecureUser, localAccounts, localHashMap2);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
          bool = false;
        }
      } else {
        try
        {
          HashMap localHashMap3 = null;
          if (paramStops != null)
          {
            localHashMap3 = new HashMap(2);
            localHashMap3.put("SERVICE", paramStops);
            localHashMap3.put("STOPCHECKS", localStopChecks);
          }
          localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          localStopChecks = com.ffusion.csil.core.Stops.getStopPayments(localSecureUser, localHashMap3);
        }
        catch (CSILException localCSILException3)
        {
          this.error = MapError.mapError(localCSILException3);
          bool = false;
        }
      }
      if (this.error == 0)
      {
        if (this.kI == null) {
          paramHttpSession.setAttribute("StopChecks", localStopChecks);
        } else {
          paramHttpSession.setAttribute("StopChecks" + this.kI, localStopChecks);
        }
        this.kL = localStopChecks;
        this.reload = false;
      }
    }
    return bool;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAccountsName(String paramString)
  {
    this.kH = paramString;
  }
  
  public String getAccountsName()
  {
    return this.kH;
  }
  
  public String getPayeeName()
  {
    return this.kG;
  }
  
  public void setPayeeName(String paramString)
  {
    this.kG = paramString;
  }
  
  public String getAmount()
  {
    return this.kJ;
  }
  
  public void setAmount(String paramString)
  {
    this.kJ = paramString;
  }
  
  public void setStartDate(String paramString)
  {
    this.kF = paramString;
  }
  
  public String getStartDate()
  {
    return this.kF;
  }
  
  public void setEndDate(String paramString)
  {
    this.kK = paramString;
  }
  
  public String getEndDate()
  {
    return this.kK;
  }
  
  public void setDateFormat(String paramString)
  {
    this.kE = paramString;
  }
  
  public String getDateFormat()
  {
    return this.kE;
  }
  
  public void setStatus(String paramString)
  {
    this.kI = paramString;
  }
  
  public String getStatus()
  {
    return this.kI;
  }
  
  public StopChecks getStopChecks()
  {
    return this.kL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.GetStopChecks
 * JD-Core Version:    0.7.0.1
 */