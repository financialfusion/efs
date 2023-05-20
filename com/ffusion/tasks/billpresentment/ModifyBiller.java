package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.Biller;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyBiller
  extends Biller
  implements Task
{
  private String dp;
  private String dk;
  private String dn;
  private int dl;
  private boolean dm = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.dm)
    {
      if (!init(localHttpSession)) {
        str = this.dp;
      } else {
        str = this.dn;
      }
    }
    else
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.dl = 0;
      try
      {
        Biller localBiller1 = BillPresentment.modifyBiller(localSecureUser, this, localHashMap);
        set(localBiller1);
      }
      catch (CSILException localCSILException)
      {
        this.dl = MapError.mapError(localCSILException);
      }
      if (this.dl == 0)
      {
        str = this.dn;
        Biller localBiller2 = new Biller();
        localBiller2.set(this);
        localBiller2.setLocale(getLocale());
        localBiller2.setDateFormat(getDateFormat());
        localHttpSession.setAttribute("Biller", localBiller2);
      }
      else
      {
        str = this.dk;
      }
    }
    return str;
  }
  
  public boolean init(HttpSession paramHttpSession)
  {
    this.dm = false;
    Biller localBiller = (Biller)paramHttpSession.getAttribute("Biller");
    if (localBiller == null)
    {
      this.dl = 6502;
      return false;
    }
    set(localBiller);
    setLocale(localBiller.getLocale());
    setDateFormat(localBiller.getDateFormat());
    return true;
  }
  
  public final void setInitFlag(String paramString)
  {
    if (paramString.trim().toLowerCase().equals("true")) {
      this.dm = true;
    } else {
      this.dm = false;
    }
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.dn = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.dk = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.dp = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.dl);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.ModifyBiller
 * JD-Core Version:    0.7.0.1
 */