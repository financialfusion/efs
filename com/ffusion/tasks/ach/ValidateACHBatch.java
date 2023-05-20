package com.ffusion.tasks.ach;

import com.ffusion.beans.XMLStrings;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHClassCode;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateACHBatch
  extends BaseTask
  implements ACHClassCode, XMLStrings
{
  private String UL;
  protected String batchName = "AddEditACHBatch";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str = super.getSuccessURL();
    ACHBatch localACHBatch = (ACHBatch)localHttpSession.getAttribute(this.batchName);
    if (localACHBatch == null)
    {
      this.error = 16002;
      return super.getTaskErrorURL();
    }
    int i = localACHBatch.getStandardEntryClassCodeValue();
    boolean bool1 = false;
    boolean bool2 = false;
    if ((localACHBatch instanceof ModifyACHBatch)) {
      bool1 = ((ModifyACHBatch)localACHBatch).getIsTemplateValue();
    }
    ACHEntries localACHEntries = localACHBatch.getACHEntries();
    bool2 = "REVERSAL".equals(localACHBatch.getCoEntryDesc());
    int j = localACHEntries.validate(i, true, localACHBatch.getBatchTypeValue() == 3, bool1, bool2);
    localHttpSession.setAttribute("ValidationErrorsCount", String.valueOf(j));
    if (j > 0) {
      str = this.UL;
    }
    return str;
  }
  
  public String getValidationErrorsURL()
  {
    return this.UL;
  }
  
  public void setValidationErrorsURL(String paramString)
  {
    this.UL = paramString;
  }
  
  public final void setBatchName(String paramString)
  {
    this.batchName = paramString;
  }
  
  public final String getBatchName()
  {
    return this.batchName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ValidateACHBatch
 * JD-Core Version:    0.7.0.1
 */