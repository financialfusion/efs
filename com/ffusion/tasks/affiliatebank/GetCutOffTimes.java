package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCutOffTimes
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  protected CutOffTime cutOff = new CutOffTime();
  
  public GetCutOffTimes()
  {
    this.collectionSessionName = "CutOffTimes";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    CutOffTimes localCutOffTimes = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      localCutOffTimes = PaymentsAdmin.getCutOffTimes(localSecureUser, this.cutOff, localHashMap);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.collectionSessionName, localCutOffTimes);
    return str;
  }
  
  public void setInstructionType(String paramString)
  {
    this.cutOff.setInstructionType(paramString);
  }
  
  public String getInstructionType()
  {
    return this.cutOff.getInstructionType();
  }
  
  public void setFIId(String paramString)
  {
    this.cutOff.setFIId(paramString);
  }
  
  public String getFIId()
  {
    return this.cutOff.getFIId();
  }
  
  public void setStatus(String paramString)
  {
    this.cutOff.setStatus(paramString);
  }
  
  public String getStatus()
  {
    return this.cutOff.getStatus();
  }
  
  public void setCategory(String paramString)
  {
    this.cutOff.setCategory(paramString);
  }
  
  public String getCategory()
  {
    return this.cutOff.getCategory();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetCutOffTimes
 * JD-Core Version:    0.7.0.1
 */