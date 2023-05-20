package com.ffusion.tasks.disbursement;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.disbursement.DisbursementSummaries;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Disbursements;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSummaries
  extends BaseTask
  implements Task
{
  protected String bankingServiceName = "com.ffusion.services.Banking";
  String DA = "P";
  Calendar Dz = null;
  Calendar DB = null;
  String DC = "MM/dd/yyyy";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    DisbursementSummaries localDisbursementSummaries = null;
    this.error = 0;
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    HashMap localHashMap = new HashMap();
    localHashMap.put("BANKING_SERVICE", localBanking3);
    localHashMap.put("DATA_CLASSIFICATION", this.DA);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      if ((this.Dz == null) && (this.DB == null))
      {
        Calendar localCalendar1 = Calendar.getInstance();
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar1.set(11, 0);
        localCalendar1.set(12, 0);
        localCalendar1.set(13, 0);
        localCalendar1.set(14, 0);
        localCalendar2.set(11, 0);
        localCalendar2.set(12, 0);
        localCalendar2.set(13, 0);
        localCalendar2.set(14, 0);
        localCalendar2.add(6, 1);
        localCalendar2.add(14, -1);
        int i = 0;
        while (i++ < 7)
        {
          localDisbursementSummaries = Disbursements.getSummaries(localSecureUser, localCalendar1, localCalendar2, localHashMap);
          if ((localDisbursementSummaries == null) || (localDisbursementSummaries.size() == 0))
          {
            localCalendar1.add(6, -1);
            localCalendar2.add(6, -1);
          }
          else
          {
            this.Dz = localCalendar1;
            this.DB = localCalendar2;
          }
        }
      }
      else
      {
        if (this.Dz != null)
        {
          this.Dz.set(11, 0);
          this.Dz.set(12, 0);
          this.Dz.set(13, 0);
          this.Dz.set(14, 0);
        }
        if (this.DB != null)
        {
          this.DB.set(11, 0);
          this.DB.set(12, 0);
          this.DB.set(13, 0);
          this.DB.set(14, 0);
          this.DB.add(6, 1);
          this.DB.add(14, -1);
        }
        localDisbursementSummaries = Disbursements.getSummaries(localSecureUser, this.Dz, this.DB, localHashMap);
      }
      localHttpSession.setAttribute("DisbursementSummaries", localDisbursementSummaries);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setDataClassification(String paramString)
  {
    this.DA = paramString;
  }
  
  public String getDataClassification()
  {
    return this.DA;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.DC)) {
      this.DC = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.DC;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.DC))) {
      this.Dz = null;
    } else {
      try
      {
        this.Dz = Calendar.getInstance();
        this.Dz.setTime(DateFormatUtil.getFormatter(this.DC).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.Dz = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.DC + ")");
      }
    }
  }
  
  public String getStartDate()
  {
    String str = null;
    if (this.Dz != null) {
      str = DateFormatUtil.getFormatter(this.DC).format(this.Dz.getTime());
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.DC))) {
      this.DB = null;
    } else {
      try
      {
        this.DB = Calendar.getInstance();
        this.DB.setTime(DateFormatUtil.getFormatter(this.DC).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.DB = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.DC + ")");
      }
    }
  }
  
  public String getEndDate()
  {
    String str = null;
    if (this.DB != null) {
      str = DateFormatUtil.getFormatter(this.DC).format(this.DB.getTime());
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.disbursement.GetSummaries
 * JD-Core Version:    0.7.0.1
 */