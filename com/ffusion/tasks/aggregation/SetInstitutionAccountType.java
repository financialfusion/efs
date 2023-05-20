package com.ffusion.tasks.aggregation;

import com.ffusion.beans.aggregation.InstitutionAccountType;
import com.ffusion.beans.aggregation.InstitutionAccountTypes;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetInstitutionAccountType
  extends BaseTask
  implements Task
{
  private String nS = "InstitutionAccountTypes";
  private String nR = "InstitutionAccountType";
  private String nT;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    InstitutionAccountTypes localInstitutionAccountTypes = (InstitutionAccountTypes)localHttpSession.getAttribute(this.nS);
    if (localInstitutionAccountTypes != null)
    {
      InstitutionAccountType localInstitutionAccountType = localInstitutionAccountTypes.getByType(this.nT);
      if (localInstitutionAccountType != null)
      {
        localHttpSession.setAttribute(this.nR, localInstitutionAccountType);
        str = this.successURL;
      }
      else
      {
        this.error = 11012;
      }
    }
    else
    {
      this.error = 11013;
    }
    return str;
  }
  
  public void setInstitutionAccountTypesInSessionName(String paramString)
  {
    this.nS = paramString;
  }
  
  public String getInstitutionAccountTypesInSessionName()
  {
    return this.nS;
  }
  
  public void setInstitutionAccountTypeInSessionName(String paramString)
  {
    this.nR = paramString;
  }
  
  public String getInstitutionAccountTypeInSessionName()
  {
    return this.nR;
  }
  
  public void setTypeID(String paramString)
  {
    this.nT = paramString;
  }
  
  public String getTypeID()
  {
    return this.nT;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.SetInstitutionAccountType
 * JD-Core Version:    0.7.0.1
 */