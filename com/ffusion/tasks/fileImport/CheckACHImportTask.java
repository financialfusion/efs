package com.ffusion.tasks.fileImport;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.XMLStrings;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHClassCode;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.fileimporter.fileadapters.custom.Mapper;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckACHImportTask
  extends BaseTask
  implements ACHClassCode, XMLStrings
{
  private String Qa;
  private boolean Qb;
  private boolean Qc;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str1 = super.getSuccessURL();
    ErrorMessages localErrorMessages = (ErrorMessages)localHttpSession.getAttribute("ImportErrors");
    if (localErrorMessages == null)
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
    ACHBatch localACHBatch = (ACHBatch)localHttpSession.getAttribute("AddEditACHBatch");
    if (localACHBatch == null)
    {
      this.error = 16002;
      return super.getTaskErrorURL();
    }
    if (localErrorMessages.operationCanContinue())
    {
      int i = localACHBatch.getStandardEntryClassCodeValue();
      Integer localInteger = (Integer)localHashMap.get("AchClassCode");
      if (localInteger == null)
      {
        this.error = 23004;
        return super.getTaskErrorURL();
      }
      int j = localInteger.intValue();
      if ((i != j) && (!this.Qc))
      {
        ACHEntries localACHEntries = localACHBatch.getACHEntries();
        if (localACHEntries.size() > 0)
        {
          String str2 = ResourceUtil.getString("ACHClassCode" + ACHClassCode.ACHClassCodes[i], "com.ffusion.beans.ach.resources", ExtendABean.DEFAULT_LOCALE);
          String str3 = ResourceUtil.getString("ACHClassCode" + ACHClassCode.ACHClassCodes[j], "com.ffusion.beans.ach.resources", ExtendABean.DEFAULT_LOCALE);
          SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
          Object[] arrayOfObject = { str3, str2 };
          localErrorMessages.add(new ImportError(4, Mapper.getImportError("SECCodesDoNotMatch", localSecureUser.getLocale()), Mapper.getImportError("SECCodeWarningText", arrayOfObject, localSecureUser.getLocale()), null, null, null));
        }
        this.Qb = true;
      }
      else
      {
        this.Qb = false;
      }
    }
    if (!localErrorMessages.isEmpty())
    {
      localErrorMessages.sort();
      str1 = getImportErrorsURL();
    }
    return str1;
  }
  
  public String getImportErrorsURL()
  {
    return this.Qa;
  }
  
  public void setImportErrorsURL(String paramString)
  {
    this.Qa = paramString;
  }
  
  public boolean isClearACHEntries()
  {
    return this.Qb;
  }
  
  public boolean getClearACHEntries()
  {
    return this.Qb;
  }
  
  public void setClearACHEntries(boolean paramBoolean)
  {
    this.Qb = paramBoolean;
  }
  
  public void setPartialImport(String paramString)
  {
    this.Qc = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getPartialImport()
  {
    return this.Qc;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.CheckACHImportTask
 * JD-Core Version:    0.7.0.1
 */