package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.FileImporter;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetOutputFormatTask
  extends BaseTask
{
  private String Pw;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      HashMap localHashMap = new HashMap();
      if ((this.Pw != null) && (this.Pw.trim().length() > 0))
      {
        OutputFormat localOutputFormat = FileImporter.getOutputFormat(localSecureUser, this.Pw, localHashMap);
        if (localOutputFormat == null) {
          localHttpSession.removeAttribute("OutputFormat");
        } else {
          localHttpSession.setAttribute("OutputFormat", localOutputFormat);
        }
      }
      str = super.getSuccessURL();
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = super.getServiceErrorURL();
    }
    return str;
  }
  
  public String getName()
  {
    return this.Pw;
  }
  
  public void setName(String paramString)
  {
    this.Pw = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.GetOutputFormatTask
 * JD-Core Version:    0.7.0.1
 */