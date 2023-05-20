package com.ffusion.tasks.disbursement;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.disbursement.DisbursementPresentmentSummaries;
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

public class GetPresentmentSummaries
  extends BaseTask
  implements Task
{
  protected String bankingServiceName = "com.ffusion.services.Banking";
  String DI = "P";
  Calendar DH = null;
  Calendar DJ = null;
  String DK = "MM/dd/yyyy";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    DisbursementPresentmentSummaries localDisbursementPresentmentSummaries = null;
    this.error = 0;
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    if (localDisbursementPresentmentSummaries == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("BANKING_SERVICE", localBanking3);
      localHashMap.put("DATA_CLASSIFICATION", this.DI);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        if ((this.DH == null) && (this.DJ == null))
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
            localDisbursementPresentmentSummaries = Disbursements.getPresentmentSummaries(localSecureUser, localCalendar1, localCalendar2, localHashMap);
            if ((localDisbursementPresentmentSummaries == null) || (localDisbursementPresentmentSummaries.size() == 0))
            {
              localCalendar1.add(6, -1);
              localCalendar2.add(6, -1);
            }
            else
            {
              this.DH = localCalendar1;
              this.DJ = localCalendar2;
            }
          }
        }
        else
        {
          if (this.DH != null)
          {
            this.DH.set(11, 0);
            this.DH.set(12, 0);
            this.DH.set(13, 0);
            this.DH.set(14, 0);
          }
          if (this.DJ != null)
          {
            this.DJ.set(11, 0);
            this.DJ.set(12, 0);
            this.DJ.set(13, 0);
            this.DJ.set(14, 0);
            this.DJ.add(6, 1);
            this.DJ.add(14, -1);
          }
          localDisbursementPresentmentSummaries = Disbursements.getPresentmentSummaries(localSecureUser, this.DH, this.DJ, localHashMap);
        }
        localHttpSession.setAttribute("DisbursementPresentmentSummaries", localDisbursementPresentmentSummaries);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setDataClassification(String paramString)
  {
    this.DI = paramString;
  }
  
  public String getDataClassification()
  {
    return this.DI;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.DK)) {
      this.DK = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.DK;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.DK))) {
      this.DH = null;
    } else {
      try
      {
        this.DH = Calendar.getInstance();
        this.DH.setTime(DateFormatUtil.getFormatter(this.DK).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.DH = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.DK + ")");
      }
    }
  }
  
  public String getStartDate()
  {
    String str = null;
    if (this.DH != null) {
      str = DateFormatUtil.getFormatter(this.DK).format(this.DH.getTime());
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.DK))) {
      this.DJ = null;
    } else {
      try
      {
        this.DJ = Calendar.getInstance();
        this.DJ.setTime(DateFormatUtil.getFormatter(this.DK).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.DJ = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.DK + ")");
      }
    }
  }
  
  public String getEndDate()
  {
    String str = null;
    if (this.DJ != null) {
      str = DateFormatUtil.getFormatter(this.DK).format(this.DJ.getTime());
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.disbursement.GetPresentmentSummaries
 * JD-Core Version:    0.7.0.1
 */