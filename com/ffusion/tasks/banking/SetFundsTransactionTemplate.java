package com.ffusion.tasks.banking;

import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.beans.FundsTransactionTemplates;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetFundsTransactionTemplate
  extends BaseTask
  implements Task
{
  protected String sessionName = null;
  protected int ID = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FundsTransactionTemplates localFundsTransactionTemplates = null;
    if (this.sessionName == null)
    {
      this.error = 1034;
      str = this.taskErrorURL;
    }
    else
    {
      localFundsTransactionTemplates = (FundsTransactionTemplates)localHttpSession.getAttribute(this.sessionName);
      if (localFundsTransactionTemplates == null)
      {
        this.error = 1034;
        str = this.taskErrorURL;
      }
      else if (this.ID == 0)
      {
        this.error = 1039;
        str = this.taskErrorURL;
      }
      else
      {
        FundsTransactionTemplate localFundsTransactionTemplate = localFundsTransactionTemplates.getByTemplateID(this.ID);
        if (localFundsTransactionTemplate == null)
        {
          this.error = 1033;
          str = this.taskErrorURL;
        }
        else
        {
          localHttpSession.setAttribute("FundsTransactionTemplate", localFundsTransactionTemplate);
          str = this.successURL;
        }
      }
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    try
    {
      this.ID = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.ID = 0;
    }
  }
  
  public void setID(int paramInt)
  {
    this.ID = paramInt;
  }
  
  public String getID()
  {
    return String.valueOf(this.ID);
  }
  
  public int getIDValue()
  {
    return this.ID;
  }
  
  public void setName(String paramString)
  {
    this.sessionName = paramString;
  }
  
  public String getName()
  {
    return this.sessionName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SetFundsTransactionTemplate
 * JD-Core Version:    0.7.0.1
 */