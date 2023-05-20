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

public class GetSummariesForPresentment
  extends BaseTask
  implements Task
{
  protected String presentment = "";
  String DE = "P";
  Calendar DD = null;
  Calendar DF = null;
  String DG = "MM/dd/yyyy";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.presentment == null)
    {
      this.error = 21002;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = 0;
      Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
      HashMap localHashMap = new HashMap();
      localHashMap.put("BANKING_SERVICE", localBanking3);
      localHashMap.put("DATA_CLASSIFICATION", this.DE);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        DisbursementSummaries localDisbursementSummaries;
        if (((this.DD == null ? 1 : 0) & (this.DF == null ? 1 : 0)) != 0)
        {
          localDisbursementSummaries = Disbursements.getSummariesForPresentment(localSecureUser, this.presentment, localHashMap);
        }
        else
        {
          if (this.DD != null)
          {
            this.DD.set(11, 0);
            this.DD.set(12, 0);
            this.DD.set(13, 0);
            this.DD.set(14, 0);
          }
          if (this.DF != null)
          {
            this.DF.set(11, 0);
            this.DF.set(12, 0);
            this.DF.set(13, 0);
            this.DF.set(14, 0);
            this.DF.add(6, 1);
            this.DF.add(14, -1);
          }
          localDisbursementSummaries = Disbursements.getSummariesForPresentment(localSecureUser, this.presentment, this.DD, this.DF, localHashMap);
        }
        localHttpSession.setAttribute("DisbursementSummariesForPresentment", localDisbursementSummaries);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setPresentmentName(String paramString)
  {
    this.presentment = paramString;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setDataClassification(String paramString)
  {
    this.DE = paramString;
  }
  
  public String getDataClassification()
  {
    return this.DE;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.DG)) {
      this.DG = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.DG;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.DG))) {
      this.DD = null;
    } else {
      try
      {
        this.DD = Calendar.getInstance();
        this.DD.setTime(DateFormatUtil.getFormatter(this.DG).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.DD = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.DG + ")");
      }
    }
  }
  
  public String getStartDate()
  {
    String str = null;
    if (this.DD != null) {
      str = DateFormatUtil.getFormatter(this.DG).format(this.DD.getTime());
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.DG))) {
      this.DF = null;
    } else {
      try
      {
        this.DF = Calendar.getInstance();
        this.DF.setTime(DateFormatUtil.getFormatter(this.DG).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.DF = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.DG + ")");
      }
    }
  }
  
  public String getEndDate()
  {
    String str = null;
    if (this.DF != null) {
      str = DateFormatUtil.getFormatter(this.DG).format(this.DF.getTime());
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.disbursement.GetSummariesForPresentment
 * JD-Core Version:    0.7.0.1
 */