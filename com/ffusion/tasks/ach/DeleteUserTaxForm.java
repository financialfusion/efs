package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteUserTaxForm
  extends BaseTask
  implements Task
{
  protected String ID;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    TaxForms localTaxForms = (TaxForms)localHttpSession.getAttribute("TaxForms");
    String str = null;
    this.error = 0;
    if (localTaxForms == null)
    {
      this.error = 16160;
      return this.taskErrorURL;
    }
    TaxForm localTaxForm = localTaxForms.getByID(this.ID);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      ACH.deleteUserTaxForm(localSecureUser, localTaxForm, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      localTaxForms.remove(localTaxForm);
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    this.ID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.DeleteUserTaxForm
 * JD-Core Version:    0.7.0.1
 */