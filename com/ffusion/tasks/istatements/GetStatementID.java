package com.ffusion.tasks.istatements;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatementID
  extends BaseTask
{
  private SecureUser O9;
  private DateTime Pd;
  private Locale Pa;
  private String Pc;
  private String O7;
  private String Pb;
  private String O8;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.O9 = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.Pa = BaseTask.getLocale(localHttpSession, this.O9);
    if (validateInput()) {
      try
      {
        this.O8 = StatementData.getStatementID(this.O9, this.Pc, this.Pd, new HashMap());
      }
      catch (CSILException localCSILException)
      {
        this.error = localCSILException.code;
        str = this.serviceErrorURL;
      }
    } else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setAccountNumber(String paramString)
  {
    this.Pc = paramString;
  }
  
  public String getAccountNumber()
  {
    return this.Pc;
  }
  
  public void setStatementDate(String paramString)
  {
    this.O7 = paramString;
  }
  
  public String getStatementDate()
  {
    return this.O7;
  }
  
  public void setDateFormat(String paramString)
  {
    this.Pb = paramString;
  }
  
  public String getStatementID()
  {
    return this.O8;
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.O9 == null))
    {
      this.error = 38;
      bool = false;
    }
    if ((bool) && ((this.Pc == null) || (this.Pc.length() == 0)))
    {
      this.error = 36213;
      bool = false;
    }
    if ((bool) && ((this.O7 == null) || (this.O7.length() == 0)))
    {
      this.error = 36214;
      bool = false;
    }
    if ((bool) && ((this.Pb == null) || (this.Pb.length() == 0)))
    {
      this.error = 36215;
      bool = false;
    }
    if (bool)
    {
      this.Pd = new DateTime(this.Pa);
      this.Pd.setFormat(this.Pb);
      this.Pd.setDate(this.O7);
      if (!this.O7.equals(this.Pd.toString()))
      {
        this.error = 36216;
        bool = false;
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetStatementID
 * JD-Core Version:    0.7.0.1
 */