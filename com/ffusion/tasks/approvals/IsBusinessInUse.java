package com.ffusion.tasks.approvals;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Approvals;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsBusinessInUse
  extends BaseTask
{
  private String aOt = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    try
    {
      localHttpSession.removeAttribute("IsBusinessInUse");
      if (this.aOt != null)
      {
        int i = -1;
        try
        {
          i = Integer.parseInt(this.aOt);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new CSILException(30214);
        }
        if (i != -1)
        {
          Boolean localBoolean = Approvals.isBusinessInUse(i, localHashMap) ? Boolean.TRUE : Boolean.FALSE;
          localHttpSession.setAttribute("IsBusinessInUse", localBoolean);
        }
      }
      else
      {
        throw new CSILException(30214);
      }
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str;
  }
  
  public void setBusinessID(String paramString)
  {
    this.aOt = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.IsBusinessInUse
 * JD-Core Version:    0.7.0.1
 */