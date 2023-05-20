package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditGlobalPayee
  extends AddGlobalPayee
{
  private boolean GH = false;
  boolean GG = false;
  boolean GF = true;
  Payee GI = new Payee();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.GG)
    {
      this.GI = ((Payee)localHttpSession.getAttribute("Payee"));
      set(this.GI);
      this.error = 100408;
      if (this.GI == null) {
        str = this.taskErrorURL;
      }
    }
    else
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        if (validateInput(localHttpSession))
        {
          if (getFiID().equals("NONE")) {
            this.fiID = null;
          }
          if (get("Group") != null) {
            set("IDScope", get("Group").toString());
          }
          PaymentsAdmin.modifyGlobalPayee(localSecureUser, this, localHashMap);
          if (this.error == 0) {
            str = this.successURL;
          }
        }
        else
        {
          str = this.taskErrorURL;
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setInitialize(String paramString)
  {
    boolean bool = Boolean.valueOf(paramString).booleanValue();
    this.GG = bool;
    this.GF = (!bool);
  }
  
  public void setProcess(String paramString)
  {
    boolean bool = Boolean.valueOf(paramString).booleanValue();
    this.GF = bool;
    this.GG = (!bool);
  }
  
  public void setInit(String paramString)
  {
    this.GH = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.EditGlobalPayee
 * JD-Core Version:    0.7.0.1
 */