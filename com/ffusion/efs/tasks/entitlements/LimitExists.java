package com.ffusion.efs.tasks.entitlements;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LimitExists
  extends BaseTask
  implements Task
{
  private String LK;
  private static final int LO = -1;
  private String LN;
  private int LM = -1;
  private int LQ = -1;
  private boolean LJ = true;
  private String LI = null;
  private static final String LP = "FALSE";
  private static final String LL = "TRUE";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (!u()) {
      return this.taskErrorURL;
    }
    Limit localLimit = new Limit();
    localLimit.setGroupId(this.LM);
    localLimit.setLimitName(this.LN);
    localLimit.setPeriod(this.LQ);
    localLimit.setCrossCurrency(this.LJ);
    if (this.LI == null) {
      this.LI = Util.getLimitBaseCurrency();
    }
    localLimit.setCurrencyCode(this.LI);
    boolean bool = false;
    try
    {
      bool = Entitlements.limitExists(localLimit);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      if (bool) {
        str = this.successURL;
      } else {
        str = this.LK;
      }
    }
    else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private boolean u()
  {
    boolean bool = true;
    if ((this.LK == null) || (this.LK.length() == 0))
    {
      this.error = 35015;
      bool = false;
    }
    if ((this.LN != null) && (this.LN.length() == 0))
    {
      this.error = 35018;
      bool = false;
    }
    if (this.LM == -1)
    {
      this.error = 35003;
      bool = false;
    }
    if (this.LQ == -1)
    {
      this.error = 35020;
      bool = false;
    }
    return bool;
  }
  
  public void setNotExistsURL(String paramString)
  {
    this.LK = paramString;
  }
  
  public String getNotExistsURL()
  {
    return this.LK;
  }
  
  public void setLimitName(String paramString)
  {
    this.LN = paramString;
  }
  
  public String getLimitName()
  {
    return this.LN;
  }
  
  public void setGroupId(String paramString)
  {
    this.LM = Integer.parseInt(paramString);
  }
  
  public String getGroupId()
  {
    return Integer.toString(this.LM);
  }
  
  public void setPeriod(String paramString)
  {
    this.LQ = Integer.parseInt(paramString);
  }
  
  public String getPeriod()
  {
    return Integer.toString(this.LQ);
  }
  
  public void setCrossCurrency(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.LJ = true;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.LJ = false;
    } else {
      this.error = 35046;
    }
  }
  
  public String getCrossCurrency()
  {
    if (this.LJ) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.LI = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.LI;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.LimitExists
 * JD-Core Version:    0.7.0.1
 */