package com.ffusion.tasks.accountgroups;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountGroupIds
  extends BaseTask
  implements AccountGroupsTask
{
  private int aM1 = -1;
  private String aMZ = "AccountGroupsIds";
  private String aM0 = "Accounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ArrayList localArrayList1 = (ArrayList)localHttpSession.getAttribute(this.aM0);
    ArrayList localArrayList2 = null;
    if (this.aM1 == -1)
    {
      this.error = 70000;
      str = this.taskErrorURL;
    }
    else if (localArrayList1 == null)
    {
      this.error = 70004;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        int i = this.aM1;
        localArrayList2 = AccountGroup.getAccountGroupIds(localSecureUser, i, localArrayList1, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.aMZ, localArrayList2);
      str = this.successURL;
    }
    return str;
  }
  
  public void setBusDirectoryId(String paramString)
  {
    try
    {
      this.aM1 = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aM1 = -1;
    }
  }
  
  public void setAccountsList(String paramString)
  {
    this.aM0 = paramString;
  }
  
  public void setAccountGroupsIds(String paramString)
  {
    this.aMZ = paramString;
  }
  
  public String getBusDirectoryId()
  {
    return String.valueOf(this.aM1);
  }
  
  public String getAccountsList()
  {
    return this.aM0;
  }
  
  public String getAccountGroupsIds()
  {
    return this.aMZ;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accountgroups.GetAccountGroupIds
 * JD-Core Version:    0.7.0.1
 */