package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportPDF
  extends BaseTask
  implements StatementTask
{
  private String Oz;
  private boolean OB = false;
  private String OC = "Statement";
  private String OA = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.Oz = this.taskErrorURL;
    this.error = 0;
    SecureUser localSecureUser = null;
    ServletOutputStream localServletOutputStream = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    Statement localStatement = (Statement)localHttpSession.getAttribute(this.OC);
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
      paramHttpServletResponse.setStatus(200);
      paramHttpServletResponse.setContentType("application/pdf");
      paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=STATEMENT.PDF");
      localServletOutputStream = paramHttpServletResponse.getOutputStream();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      StatementData.getPDF(localSecureUser, localStatement, this.OB, localByteArrayOutputStream, localLocale);
      paramHttpServletResponse.setContentLength(localByteArrayOutputStream.size());
      localByteArrayOutputStream.writeTo(localServletOutputStream);
      DebugLog.log("ExportPDF done");
      this.Oz = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.Oz = this.serviceErrorURL;
    }
    finally
    {
      if (localServletOutputStream != null)
      {
        localServletOutputStream.flush();
        localServletOutputStream.close();
      }
    }
    return this.Oz;
  }
  
  public void setImageFlag(String paramString)
  {
    this.OB = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getImageFlag()
  {
    return this.OB;
  }
  
  public void setStatementInSessionName(String paramString)
  {
    this.OC = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.ExportPDF
 * JD-Core Version:    0.7.0.1
 */