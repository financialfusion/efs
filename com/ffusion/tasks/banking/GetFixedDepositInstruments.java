package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFixedDepositInstruments
  extends BaseTask
  implements Task
{
  String y9 = "Account";
  String y8 = "FixedDepositInstruments";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  String y7 = "P";
  
  public void setDataClassification(String paramString)
  {
    this.y7 = paramString;
  }
  
  public String getDataClassification()
  {
    return this.y7;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.y9 = "Account";
    } else {
      this.y9 = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this.y9;
  }
  
  public void setInstrumentsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.y8 = "FixedDepositInstruments";
    } else {
      this.y8 = paramString;
    }
  }
  
  public String getInstrumentsName()
  {
    return this.y8;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.y9);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localAccount == null) {
      this.error = 1002;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else {
      try
      {
        this.error = 0;
        HashMap localHashMap = new HashMap();
        localHashMap.put("SERVICE", localBanking3);
        localHashMap.put("DATA_CLASSIFICATION", this.y7);
        GregorianCalendar localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.set(11, 0);
        localGregorianCalendar.set(12, 0);
        localGregorianCalendar.set(13, 0);
        localGregorianCalendar.set(14, 0);
        FixedDepositInstruments localFixedDepositInstruments = Banking.getFixedDepositInstruments(localSecureUser, localAccount, localGregorianCalendar, localGregorianCalendar, localHashMap);
        localHttpSession.setAttribute(this.y8, localFixedDepositInstruments);
        str = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetFixedDepositInstruments
 * JD-Core Version:    0.7.0.1
 */