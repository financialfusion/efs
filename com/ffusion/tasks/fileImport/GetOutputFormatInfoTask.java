package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetOutputFormatInfoTask
  extends BaseTask
{
  private String PA = "";
  private String PD = "";
  private ArrayList PB = new ArrayList();
  private ArrayList PC = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    OutputFormat localOutputFormat = (OutputFormat)localHttpSession.getAttribute(this.PA);
    if (localOutputFormat != null)
    {
      String str = BaseTask.getLocale(localHttpSession, localSecureUser).toString();
      this.PD = localOutputFormat.getName(str);
      this.PB = localOutputFormat.getFieldList(str);
      this.PC = localOutputFormat.getMemoFieldList(str);
    }
    else
    {
      this.PD = "";
      this.PB = new ArrayList();
      this.PC = new ArrayList();
    }
    return super.getSuccessURL();
  }
  
  public String getName()
  {
    return this.PA;
  }
  
  public void setName(String paramString)
  {
    this.PA = paramString;
  }
  
  public String getLocaleSpecificName()
  {
    return this.PD;
  }
  
  public ArrayList getLocaleSpecificFieldList()
  {
    return this.PB;
  }
  
  public ArrayList getLocaleSpecificMemoList()
  {
    return this.PC;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.GetOutputFormatInfoTask
 * JD-Core Version:    0.7.0.1
 */