package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddTransactionGroup
  extends BaseTask
  implements BusinessTask
{
  private String fK = null;
  private String fJ = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this.fK == null) || (this.fK.length() <= 0) || (this.fK.length() > 1010))
    {
      this.error = 4140;
      return this.taskErrorURL;
    }
    if ((this.fJ == null) || (this.fJ.length() <= 0))
    {
      this.error = 4141;
      return this.taskErrorURL;
    }
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      com.ffusion.beans.business.Business localBusiness = (com.ffusion.beans.business.Business)localHttpSession.getAttribute("Business");
      int i = localBusiness.getIdValue();
      HashMap localHashMap = new HashMap();
      com.ffusion.csil.core.Business.addTransactionGroup(localSecureUser, i, this.fK, this.fJ, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      str = this.successURL;
    }
    return str;
  }
  
  public void setTransactionGroup(String paramString)
  {
    this.fK = paramString;
  }
  
  public void setTypecodes(String paramString)
  {
    this.fJ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.AddTransactionGroup
 * JD-Core Version:    0.7.0.1
 */