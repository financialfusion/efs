package com.ffusion.tasks.messages;

import com.ffusion.beans.messages.GlobalMessages;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetGlobalLoginMessages
  extends BaseTask
  implements Task
{
  private String qI = "GlobalMessages";
  private String qK;
  private String qM;
  private String qL;
  private String qJ = "en_US";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    GlobalMessages localGlobalMessages = null;
    try
    {
      int i = Integer.parseInt(this.qL);
      int j = Integer.parseInt(this.qM);
      localHashMap.put("LANGUAGE", this.qJ);
      localGlobalMessages = Messages.getGlobalLoginMessages(this.qK, j, i, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.qI, localGlobalMessages);
    }
    return str;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.qI = paramString;
    }
  }
  
  public void setBankId(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.qK = paramString;
    }
  }
  
  public void setAffiliateBankId(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.qM = paramString;
    }
  }
  
  public void setSuiteType(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.qL = paramString;
    }
  }
  
  public void setSearchLanguage(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.qJ = null;
    } else {
      this.qJ = paramString;
    }
  }
  
  public String getSearchLanguage()
  {
    return this.qJ;
  }
  
  public String getDestinationSessionKey()
  {
    return this.qI;
  }
  
  public String getBankId()
  {
    return this.qK;
  }
  
  public String getAffiliateBankId()
  {
    return this.qM;
  }
  
  public String getSuiteType()
  {
    return this.qL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetGlobalLoginMessages
 * JD-Core Version:    0.7.0.1
 */