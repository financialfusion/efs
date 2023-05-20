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

public class IsObjectInUse
  extends BaseTask
{
  private String aOr = null;
  private String aOs = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    try
    {
      localHttpSession.removeAttribute("IsObjectInUse");
      int i = -1;
      if (this.aOr != null)
      {
        try
        {
          i = Integer.parseInt(this.aOr);
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          throw new CSILException(30224);
        }
        if (i == -1) {
          throw new CSILException(30224);
        }
      }
      else
      {
        throw new CSILException(30224);
      }
      int j = -1;
      if (this.aOs != null)
      {
        try
        {
          j = Integer.parseInt(this.aOs);
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          throw new CSILException(30225);
        }
        if (j == -1) {
          throw new CSILException(30225);
        }
      }
      else
      {
        throw new CSILException(30225);
      }
      Boolean localBoolean = Approvals.isObjectInUse(i, j, localHashMap) ? Boolean.TRUE : Boolean.FALSE;
      localHttpSession.setAttribute("IsObjectInUse", localBoolean);
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
      this.error = MapError.mapError(localCSILException);
    }
    return str;
  }
  
  public void setObjectID(String paramString)
  {
    this.aOr = paramString;
  }
  
  public void setObjectType(String paramString)
  {
    this.aOs = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.approvals.IsObjectInUse
 * JD-Core Version:    0.7.0.1
 */