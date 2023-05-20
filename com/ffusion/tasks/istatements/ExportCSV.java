package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportCSV
  extends BaseTask
  implements StatementTask
{
  private String OU;
  private String OV = null;
  private String OW = "Statement";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.OU = this.taskErrorURL;
    this.error = 0;
    SecureUser localSecureUser = null;
    Object localObject1 = null;
    PrintWriter localPrintWriter = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    Statement localStatement = (Statement)localHttpSession.getAttribute(this.OW);
    if (localStatement == null)
    {
      this.error = 36205;
      return this.taskErrorURL;
    }
    Locale localLocale = localSecureUser.getLocale();
    if (localLocale == null)
    {
      this.error = 41;
      return this.taskErrorURL;
    }
    try
    {
      paramHttpServletResponse.setContentType("application/text; charset=UTF-8");
      paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.CSV");
      localPrintWriter = paramHttpServletResponse.getWriter();
      StatementData.getCSV(localSecureUser, localStatement, localPrintWriter, localLocale);
      this.OU = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.OU = this.serviceErrorURL;
    }
    finally
    {
      if (localPrintWriter != null)
      {
        localPrintWriter.flush();
        localPrintWriter.close();
      }
    }
    return this.OU;
  }
  
  public void setStatementInSessionName(String paramString)
  {
    this.OW = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.ExportCSV
 * JD-Core Version:    0.7.0.1
 */