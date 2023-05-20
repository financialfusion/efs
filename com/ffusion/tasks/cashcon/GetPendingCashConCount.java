package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPendingCashConCount
  extends BaseTask
  implements Task
{
  private String zT = null;
  private String zU = null;
  private String zV = "NumPendingCashConTransactions";
  private String[] zW = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      HashMap localHashMap = new HashMap();
      int i = CashCon.getPendingCashConCount(localSecureUser, this.zU, this.zT, this.zW, localHashMap);
      localHttpSession.setAttribute(this.zV, Integer.toString(i));
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public String getCompID()
  {
    String str = "";
    if (this.zT != null) {
      str = this.zT;
    }
    return str;
  }
  
  public void setCompID(String paramString)
  {
    this.zT = paramString;
  }
  
  public String getFIID()
  {
    String str = "";
    if (this.zU != null) {
      str = this.zU;
    }
    return str;
  }
  
  public void setFIID(String paramString)
  {
    this.zU = paramString;
  }
  
  public String getSessionName()
  {
    return this.zV;
  }
  
  public void setSessionName(String paramString)
  {
    if (paramString == null) {
      this.zV = "NumPendingCashConTransactions";
    } else {
      this.zV = paramString;
    }
  }
  
  public String getTransactionTypeList()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "";
    if (this.zW != null) {
      for (int i = 0; i < this.zW.length; i++)
      {
        localStringBuffer.append(str);
        localStringBuffer.append(this.zW[i]);
        str = ",";
      }
    }
    return localStringBuffer.toString();
  }
  
  public void setTransactionTypeList(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      this.zW = null;
    }
    else
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
      this.zW = new String[localStringTokenizer.countTokens()];
      for (int i = 0; localStringTokenizer.hasMoreTokens(); i++) {
        this.zW[i] = localStringTokenizer.nextToken().trim();
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetPendingCashConCount
 * JD-Core Version:    0.7.0.1
 */