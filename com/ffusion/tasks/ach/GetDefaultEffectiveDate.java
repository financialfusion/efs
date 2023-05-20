package com.ffusion.tasks.ach;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetDefaultEffectiveDate
  extends BaseTask
  implements Task
{
  protected String dateFormat = "yyyyMMdd";
  protected String defaultEffectiveDate = null;
  protected String compId = null;
  protected String SEC = null;
  protected String batchName = null;
  protected boolean dateBeforeEffectiveDate;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    ACHBatch localACHBatch = null;
    if ((this.batchName != null) && (this.batchName.length() > 0)) {
      localACHBatch = (ACHBatch)localHttpSession.getAttribute(this.batchName);
    }
    this.dateBeforeEffectiveDate = false;
    if ((this.compId == null) || (this.compId.trim().length() == 0)) {
      return this.successURL;
    }
    if ((this.SEC == null) || (this.SEC.trim().length() == 0)) {
      return this.successURL;
    }
    try
    {
      this.defaultEffectiveDate = ACH.getDefaultEffectiveDate(localSecureUser, this.compId.trim(), this.SEC.trim(), localHashMap);
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
      Date localDate = localSimpleDateFormat1.parse(this.defaultEffectiveDate);
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat(this.dateFormat);
      this.defaultEffectiveDate = localSimpleDateFormat2.format(localDate);
      if ((localACHBatch == null) || (localACHBatch.getDateValue() == null) || (localACHBatch.getDateValue().getTime().before(localDate))) {
        this.dateBeforeEffectiveDate = true;
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    catch (Exception localException) {}
    return this.successURL;
  }
  
  public String getDateFormat()
  {
    return this.dateFormat;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateFormat = paramString;
  }
  
  public String getCompanyID()
  {
    return this.compId;
  }
  
  public void setCompanyID(String paramString)
  {
    this.compId = paramString;
  }
  
  public String getStandardEntryClassCode()
  {
    return this.SEC;
  }
  
  public void setStandardEntryClassCode(String paramString)
  {
    this.SEC = paramString;
  }
  
  public String getDefaultEffectiveDate()
  {
    return this.defaultEffectiveDate;
  }
  
  public void setDefaultEffectiveDate(String paramString)
  {
    this.defaultEffectiveDate = paramString;
  }
  
  public String getBatchName()
  {
    return this.batchName;
  }
  
  public void setBatchName(String paramString)
  {
    this.batchName = paramString;
  }
  
  public String getBatchDateBeforeEffectiveDate()
  {
    return "" + this.dateBeforeEffectiveDate;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetDefaultEffectiveDate
 * JD-Core Version:    0.7.0.1
 */