package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.XMLStrings;
import com.ffusion.beans.ach.ACHClassCode;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PositivePay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckPPayImportTask
  extends BaseTask
  implements ACHClassCode, XMLStrings
{
  private String Py;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str1 = super.getSuccessURL();
    ErrorMessages localErrorMessages1 = (ErrorMessages)localHttpSession.getAttribute("ImportErrors");
    if (localErrorMessages1 == null)
    {
      this.error = 23007;
      return super.getTaskErrorURL();
    }
    HashMap localHashMap = (HashMap)localHttpSession.getAttribute("UploadResults");
    if (localHashMap == null)
    {
      this.error = 23002;
      return super.getTaskErrorURL();
    }
    String str2 = (String)localHashMap.get("FILETYPE");
    if (str2 == null)
    {
      this.error = 23008;
      return super.getTaskErrorURL();
    }
    if (localErrorMessages1.isEmpty())
    {
      if (str2.equals("Custom"))
      {
        PPayCheckRecords localPPayCheckRecords = (PPayCheckRecords)localHashMap.get("PPayCheckRecords");
        if (localPPayCheckRecords == null)
        {
          this.error = 23006;
          return super.getTaskErrorURL();
        }
        if (localPPayCheckRecords.isEmpty())
        {
          this.error = 23020;
          return super.getTaskErrorURL();
        }
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        try
        {
          PositivePay.submitCheckRecords(localSecureUser, localPPayCheckRecords, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          ErrorMessages localErrorMessages2 = (ErrorMessages)localHashMap.get("ERRORS_ENCOUNTERED");
          if ((localErrorMessages2 == null) || (localErrorMessages2.isEmpty()))
          {
            str1 = this.serviceErrorURL;
          }
          else
          {
            localHttpSession.setAttribute("ImportErrors", localErrorMessages2);
            str1 = getImportErrorsURL();
          }
        }
      }
    }
    else
    {
      localErrorMessages1.sort();
      str1 = getImportErrorsURL();
    }
    return str1;
  }
  
  public String getImportErrorsURL()
  {
    return this.Py;
  }
  
  public void setImportErrorsURL(String paramString)
  {
    this.Py = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.CheckPPayImportTask
 * JD-Core Version:    0.7.0.1
 */