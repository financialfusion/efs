package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessEnrollComments
  extends BaseTask
  implements BusinessTask
{
  private String gR = "";
  private String gQ = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    this.gQ = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    History localHistory = new History((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localHistory.setId(this.gR);
    localHistory.setIdType(2);
    localHistory.setDataChanged("EnrollmentComments");
    Histories localHistories = new Histories((Locale)localHttpSession.getAttribute("java.util.Locale"));
    try
    {
      localHistories = Business.getHistory(localSecureUser, localHistory, null, null, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.gQ = this.serviceErrorURL;
    }
    if ((this.error == 0) || (this.error == 12)) {
      localHttpSession.setAttribute("BusinesEnrollComments", localHistories);
    }
    return this.gQ;
  }
  
  public void setBusinessId(String paramString)
  {
    this.gR = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetBusinessEnrollComments
 * JD-Core Version:    0.7.0.1
 */