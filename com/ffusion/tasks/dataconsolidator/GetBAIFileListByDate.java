package com.ffusion.tasks.dataconsolidator;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBAIFileListByDate
  extends BaseTask
  implements Task
{
  protected DateTime startDate = null;
  protected DateTime endDate = null;
  protected Locale locale;
  protected String datetype = "SHORT";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ImportedFiles localImportedFiles = null;
    this.locale = ((Locale)localHttpSession.getAttribute("Locale"));
    if (this.locale == null) {
      this.locale = Locale.getDefault();
    }
    if ((this.startDate == null) && (this.endDate == null))
    {
      this.endDate = new DateTime(Calendar.getInstance(), this.locale);
      this.endDate.setFormat(this.datetype);
      this.endDate.set(11, 23);
      this.endDate.set(12, 59);
      this.endDate.set(13, 59);
      this.startDate = new DateTime(Calendar.getInstance(), this.locale);
      this.startDate.setFormat(this.datetype);
      this.startDate.add(5, -7);
      this.startDate.set(11, 0);
      this.startDate.set(12, 0);
      this.startDate.set(13, 0);
      this.startDate.set(14, 0);
    }
    else
    {
      validateInput(localHttpSession);
    }
    if (this.error == 0)
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        HashMap localHashMap = new HashMap();
        localImportedFiles = DataConsolidator.getImportedFileList(localSecureUser, 2, this.startDate, this.endDate, localHashMap);
        localImportedFiles.setDateFormat(this.datetype);
        localHttpSession.setAttribute("ImportedFileList", localImportedFiles);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.startDate == null) {
      this.error = 10041;
    } else if (this.endDate == null) {
      this.error = 10042;
    } else if (this.startDate.after(this.endDate)) {
      this.error = 10043;
    }
    return this.error == 0;
  }
  
  public void setStartDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.startDate = new DateTime(paramString, this.locale, this.datetype);
        if (this.startDate != null)
        {
          this.startDate.set(11, 0);
          this.startDate.set(12, 0);
          this.startDate.set(13, 0);
          this.startDate.set(14, 0);
        }
      }
      else
      {
        this.startDate = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      System.out.println("GetBAIFileListByDate.setEndDate" + localInvalidDateTimeException);
    }
  }
  
  public String getStartDate()
  {
    if (this.startDate != null) {
      return this.startDate.toString();
    }
    return null;
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.endDate = new DateTime(paramString, this.locale, this.datetype);
        if (this.endDate != null)
        {
          this.endDate.set(11, 23);
          this.endDate.set(12, 59);
          this.endDate.set(13, 59);
        }
      }
      else
      {
        this.endDate = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      System.out.println("GetBAIFileListByDate.setEndDate" + localInvalidDateTimeException);
    }
  }
  
  public String getEndDate()
  {
    if (this.endDate != null) {
      return this.endDate.toString();
    }
    return null;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.dataconsolidator.GetBAIFileListByDate
 * JD-Core Version:    0.7.0.1
 */