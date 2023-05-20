package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetTaxForm
  extends BaseTask
  implements Task
{
  protected String ID;
  protected String sessionName = "TaxForm";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession(false);
    TaxForms localTaxForms = (TaxForms)localHttpSession.getAttribute("TaxForms");
    String str = null;
    this.error = 0;
    if (localTaxForms == null)
    {
      this.error = 16160;
      str = this.taskErrorURL;
    }
    else
    {
      TaxForm localTaxForm = localTaxForms.getByID(this.ID);
      if (localTaxForm == null)
      {
        this.error = 16160;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute(this.sessionName, localTaxForm);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public final void setID(String paramString)
  {
    this.ID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SetTaxForm
 * JD-Core Version:    0.7.0.1
 */