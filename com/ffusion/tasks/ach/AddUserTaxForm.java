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
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddUserTaxForm
  extends BaseTask
  implements Task
{
  protected String ID = null;
  protected String taxForms = null;
  protected String collectionName = "TaxForms";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    TaxForms localTaxForms1 = (TaxForms)localHttpSession.getAttribute(this.taxForms);
    TaxForms localTaxForms2 = (TaxForms)localHttpSession.getAttribute(this.collectionName);
    if (localTaxForms1 == null)
    {
      this.error = 16160;
      return this.taskErrorURL;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(this.ID, ",");
    String str2 = null;
    while (localStringTokenizer.hasMoreTokens())
    {
      str2 = localStringTokenizer.nextToken();
      if (str2 != null)
      {
        TaxForm localTaxForm = localTaxForms1.getByID(str2);
        if ((localTaxForm != null) && ((localTaxForms2 == null) || (localTaxForms2.getByID(str2) == null)))
        {
          try
          {
            ACH.addUserTaxForm(localSecureUser, localTaxForm, localHashMap);
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException);
            str1 = this.serviceErrorURL;
            break;
          }
          if (this.error == 0) {
            localTaxForms2.add(localTaxForm);
          }
        }
      }
    }
    return str1;
  }
  
  public void setID(String paramString)
  {
    this.ID = paramString;
  }
  
  public void setCollectionName(String paramString)
  {
    this.collectionName = paramString;
  }
  
  public void setTaxForms(String paramString)
  {
    this.taxForms = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddUserTaxForm
 * JD-Core Version:    0.7.0.1
 */