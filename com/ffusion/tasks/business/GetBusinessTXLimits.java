package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.TransactionLimits;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessTXLimits
  extends BaseTask
  implements BusinessTask
{
  private int fL;
  private boolean fM = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    TransactionLimits localTransactionLimits1 = null;
    TransactionLimits localTransactionLimits2 = null;
    try
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (this.fM)
      {
        Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
        LimitTypePropertyLists localLimitTypePropertyLists = Business.getLimitTypeProps();
        localTransactionLimits2 = new TransactionLimits(localLimitTypePropertyLists, "per business", localLocale);
      }
      localTransactionLimits1 = Business.getTXLimitsByGroupId(localSecureUser, this.fL, "per business", localHashMap);
      if (this.fM)
      {
        localTransactionLimits2.set(localTransactionLimits1);
        this.fM = false;
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      if (localTransactionLimits2 == null) {
        localHttpSession.setAttribute("BusinessTransactionLimits", localTransactionLimits1);
      } else {
        localHttpSession.setAttribute("BusinessTransactionLimits", localTransactionLimits2);
      }
    }
    return str;
  }
  
  public void setBusinessEntGroupId(String paramString)
  {
    try
    {
      this.fL = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setBusinessEntGroupId(int paramInt)
  {
    this.fL = paramInt;
  }
  
  public void setMergeLimits(String paramString)
  {
    if ((paramString != null) && (paramString.compareToIgnoreCase("true") == 0)) {
      this.fM = true;
    } else {
      this.fM = false;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetBusinessTXLimits
 * JD-Core Version:    0.7.0.1
 */