package com.ffusion.tasks.istatements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFullStatement
  extends BaseTask
{
  private SecureUser Ps;
  private Locale Pm;
  private Statement Pr;
  private String Pp;
  private String Pk = "Statement";
  private String Po = "StatementTransactions";
  private String Pq = "StatementDailyBalances";
  private String Pj = "StatementMessages";
  private boolean Pn;
  private boolean Pl;
  private boolean Pi;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.Ps = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.Pm = BaseTask.getLocale(localHttpSession, this.Ps);
    if (validateInput()) {
      try
      {
        this.Pr = new Statement(this.Pm);
        this.Pr.setID(this.Pp);
        this.Pr = StatementData.getFullStatement(this.Ps, this.Pr);
        localHttpSession.setAttribute(this.Pk, this.Pr);
        if (this.Pn) {
          localHttpSession.setAttribute(this.Po, this.Pr.getTransactions());
        }
        if ((this.Pl) && (this.Pr.getDailyBalanceSummaries() != null)) {
          localHttpSession.setAttribute(this.Pq, this.Pr.getDailyBalanceSummaries());
        }
        if (this.Pi) {
          localHttpSession.setAttribute(this.Pj, this.Pr.getMessages());
        }
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
  
  public void setStatementID(String paramString)
  {
    this.Pp = paramString;
  }
  
  public void setStatementSessionName(String paramString)
  {
    if (paramString == null) {
      this.Pk = "Statement";
    } else {
      this.Pk = paramString;
    }
  }
  
  public void setTransactionsInSession(String paramString)
  {
    this.Pn = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setTransactionsSessionName(String paramString)
  {
    if (paramString == null) {
      this.Po = "StatementTransactions";
    } else {
      this.Po = paramString;
    }
  }
  
  public void setDailyBalanceSummariesInSession(String paramString)
  {
    this.Pl = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDailyBalanceSummariesSessionName(String paramString)
  {
    if (paramString == null) {
      this.Pq = "StatementDailyBalances";
    } else {
      this.Pq = paramString;
    }
  }
  
  public void setInformationInSession(String paramString)
  {
    this.Pi = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setInformationSessionName(String paramString)
  {
    if (paramString == null) {
      this.Pj = "StatementMessages";
    } else {
      this.Pj = paramString;
    }
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.Ps == null))
    {
      this.error = 38;
      bool = false;
    }
    if ((bool) && ((this.Pp == null) || (this.Pp.length() == 0)))
    {
      this.error = 36220;
      bool = false;
    }
    if ((bool) && ((this.Pk == null) || (this.Pk.length() == 0)))
    {
      this.error = 36221;
      bool = false;
    }
    if ((bool) && (this.Pn) && ((this.Po == null) || (this.Po.length() == 0)))
    {
      this.error = 36222;
      bool = false;
    }
    if ((bool) && (this.Pl) && ((this.Pq == null) || (this.Pq.length() == 0)))
    {
      this.error = 36224;
      bool = false;
    }
    if ((bool) && (this.Pi) && ((this.Pj == null) || (this.Pj.length() == 0)))
    {
      this.error = 36223;
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetFullStatement
 * JD-Core Version:    0.7.0.1
 */