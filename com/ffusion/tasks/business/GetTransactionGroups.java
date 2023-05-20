package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransactionGroups
  extends BaseTask
  implements BusinessTask
{
  private ArrayList fI = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      com.ffusion.beans.business.Business localBusiness = (com.ffusion.beans.business.Business)localHttpSession.getAttribute("Business");
      int i = localBusiness.getIdValue();
      HashMap localHashMap = new HashMap();
      this.fI = com.ffusion.csil.core.Business.getTransactionGroups(localSecureUser, i, localHashMap);
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
  
  public ArrayList getTransactionGroups()
  {
    if (this.fI == null) {
      return new ArrayList();
    }
    return this.fI;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetTransactionGroups
 * JD-Core Version:    0.7.0.1
 */