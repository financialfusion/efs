package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReleaseWiresCount
  extends ExtendedBaseTask
  implements WireTaskDefines
{
  protected boolean canReleaseOwnWires = true;
  
  public GetReleaseWiresCount()
  {
    this.beanSessionName = "WiresReleaseCount";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHashMap.put("ReleaseOwnWires", "" + this.canReleaseOwnWires);
    try
    {
      i = Wire.getReleaseWiresCount(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      i = -1;
    }
    localHttpSession.setAttribute(this.beanSessionName, Integer.toString(i));
    return str;
  }
  
  public void setCanReleaseOwnWires(String paramString)
  {
    this.canReleaseOwnWires = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCanReleaseOwnWires()
  {
    return "" + this.canReleaseOwnWires;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.GetReleaseWiresCount
 * JD-Core Version:    0.7.0.1
 */