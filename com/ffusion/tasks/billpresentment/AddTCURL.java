package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.TCURL;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddTCURL
  extends ModifyTCURL
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    int i = addTCURL(localHttpSession);
    if (i == 0) {
      str = getSuccessURL();
    } else if (i == -2) {
      str = getServiceErrorURL();
    } else if (i == -1) {
      str = getTaskErrorURL();
    }
    return str;
  }
  
  public int addTCURL(HttpSession paramHttpSession)
  {
    int i = 0;
    if (getProcessValue())
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      setError(0);
      try
      {
        TCURL localTCURL = new TCURL();
        localTCURL = BillPresentment.addTCURL(localSecureUser, this, localHashMap);
        set(localTCURL);
      }
      catch (CSILException localCSILException)
      {
        int j = MapError.mapError(localCSILException);
        setError(j);
      }
      if (getErrorValue() == 0) {
        paramHttpSession.setAttribute("TCURL", this);
      } else {
        i = -2;
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.AddTCURL
 * JD-Core Version:    0.7.0.1
 */