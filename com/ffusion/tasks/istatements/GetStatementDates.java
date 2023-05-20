package com.ffusion.tasks.istatements;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.StatementData;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetStatementDates
  extends BaseTask
{
  private SecureUser O1;
  private Locale O2;
  private String O6 = "StatementDates";
  private String O5 = null;
  private String O3 = null;
  private boolean O4 = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.O1 = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.O2 = BaseTask.getLocale(localHttpSession, this.O1);
    if (validateInput()) {
      try
      {
        FilteredList localFilteredList = null;
        DateTime localDateTime = null;
        StringList localStringList = new StringList();
        localFilteredList = StatementData.getStatementDates(this.O1, this.O5, new HashMap());
        if (this.O4) {
          localFilteredList.setSortedBy("DATE");
        } else {
          localFilteredList.setSortedBy("DATE,REVERSE");
        }
        int i = 0;
        int j = localFilteredList.size();
        while (i < j)
        {
          localDateTime = (DateTime)localFilteredList.get(i);
          localDateTime.setFormat(this.O3);
          localStringList.add(localDateTime.toString());
          i++;
        }
        localHttpSession.setAttribute(this.O6, localStringList);
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
    this.O5 = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    this.O3 = paramString;
  }
  
  public void setDatesCollectionName(String paramString)
  {
    if (paramString == null) {
      paramString = "StatementDates";
    } else {
      this.O6 = paramString;
    }
  }
  
  public String getDatesCollectionName()
  {
    return this.O6;
  }
  
  public void setAscendingOrder(String paramString)
  {
    this.O4 = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.O1 == null))
    {
      this.error = 38;
      bool = false;
    }
    if ((bool) && ((this.O5 == null) || (this.O5.length() == 0)))
    {
      this.error = 36213;
      bool = false;
    }
    if ((bool) && ((this.O3 == null) || (this.O3.length() == 0)))
    {
      this.error = 36215;
      bool = false;
    }
    if ((bool) && ((this.O6 == null) || (this.O6.length() == 0)))
    {
      this.error = 36218;
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.GetStatementDates
 * JD-Core Version:    0.7.0.1
 */