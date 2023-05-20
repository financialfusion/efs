package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchACHPayees
  extends BaseTask
  implements Task
{
  protected String payeesName = "ACHPayees";
  private String ec;
  private String d8;
  private String eb;
  private String d9;
  private String ed;
  private String ea;
  private String ef;
  private String ee;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute(this.payeesName);
    if (localACHPayees != null)
    {
      searchACHPayees(localHttpSession, localACHPayees);
      str = this.successURL;
    }
    else
    {
      this.error = 16150;
    }
    return str;
  }
  
  protected void searchACHPayees(HttpSession paramHttpSession, ACHPayees paramACHPayees)
  {
    ACHPayees localACHPayees = new ACHPayees();
    localACHPayees.setSortedBy(paramACHPayees.getSortedBy());
    Iterator localIterator = paramACHPayees.iterator();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    while (localIterator.hasNext())
    {
      ACHPayee localACHPayee = (ACHPayee)localIterator.next();
      if (localACHPayee != null)
      {
        int i = 0;
        if ((localSecureUser != null) && (localACHPayee.getSecurePayeeValue() == true) && (localACHPayee.getSubmittedBy() != null) && (!localACHPayee.getSubmittedBy().equals("" + localSecureUser.getProfileID()))) {
          i = 1;
        }
        if (((i != 0) || (((this.ec == null) || (this.ec.length() <= 0) || (localACHPayee.getNickName() == null) || (localACHPayee.getNickName().toUpperCase().startsWith(this.ec.toUpperCase()))) && ((this.d8 == null) || (this.d8.length() <= 0) || (localACHPayee.getName() == null) || (localACHPayee.getName().toUpperCase().startsWith(this.d8.toUpperCase()))) && ((this.d9 == null) || (this.d9.length() <= 0) || (localACHPayee.getAccountNumber() == null) || (localACHPayee.getAccountNumber().startsWith(this.d9))) && ((this.ed == null) || (this.ed.length() <= 0) || (localACHPayee.getRoutingNumber() == null) || (localACHPayee.getRoutingNumber().startsWith(this.ed))) && ((this.ef == null) || (this.ef.length() <= 0) || (localACHPayee.getBankName() == null) || (localACHPayee.getBankName().toUpperCase().startsWith(this.ef.toUpperCase()))))) && ((this.eb == null) || (this.eb.length() <= 0) || (localACHPayee.getUserAccountNumber() == null) || (localACHPayee.getUserAccountNumber().startsWith(this.eb))) && ((this.ea == null) || (this.ea.length() <= 0) || (localACHPayee.getPayeeGroup() == null) || (localACHPayee.getPayeeGroup().startsWith(this.ea)) || (this.ea.equals(String.valueOf(localACHPayee.getPayeeGroupValue())))) && ((this.ee == null) || (this.ee.length() <= 0) || (localACHPayee.getCompanyID() == null) || (localACHPayee.getPayeeGroupValue() != 2) || (localACHPayee.getCompanyID().startsWith(this.ee)))) {
          localACHPayees.add(localACHPayee);
        }
      }
    }
    paramHttpSession.setAttribute("FILTERED_ACHPAYEES", localACHPayees);
  }
  
  public String getNickName()
  {
    return this.ec;
  }
  
  public void setNickName(String paramString)
  {
    this.ec = paramString;
  }
  
  public String getName()
  {
    return this.d8;
  }
  
  public void setName(String paramString)
  {
    this.d8 = paramString;
  }
  
  public String getUserAccountNumber()
  {
    return this.eb;
  }
  
  public void setUserAccountNumber(String paramString)
  {
    this.eb = paramString;
  }
  
  public String getAccountNumber()
  {
    return this.d9;
  }
  
  public void setAccountNumber(String paramString)
  {
    this.d9 = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.ed;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.ed = paramString;
  }
  
  public String getACHCompanyID()
  {
    return this.ee;
  }
  
  public void setACHCompanyID(String paramString)
  {
    this.ee = paramString;
  }
  
  public String getPayeeGroup()
  {
    return this.ea;
  }
  
  public void setPayeeGroup(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0) || (paramString.equals("-1"))) {
      this.ea = null;
    } else {
      this.ea = paramString;
    }
  }
  
  public String getBankName()
  {
    return this.ef;
  }
  
  public void setBankName(String paramString)
  {
    this.ef = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SearchACHPayees
 * JD-Core Version:    0.7.0.1
 */