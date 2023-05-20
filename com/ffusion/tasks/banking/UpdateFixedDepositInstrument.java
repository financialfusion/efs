package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateFixedDepositInstrument
  extends BaseTask
  implements Task
{
  String za = "FixedDepositInstrument";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setInstrumentName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.za = "FixedDepositInstrument";
    } else {
      this.za = paramString;
    }
  }
  
  public String getInstrumentName()
  {
    return this.za;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    FixedDepositInstrument localFixedDepositInstrument = (FixedDepositInstrument)localHttpSession.getAttribute(this.za);
    if (localFixedDepositInstrument == null) {
      this.error = 1040;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else {
      try
      {
        this.error = 0;
        HashMap localHashMap = new HashMap();
        localHashMap.put("SERVICE", localBanking3);
        Banking.updateFixedDepositInstrument(localSecureUser, localFixedDepositInstrument, localHashMap);
        str = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
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
 * Qualified Name:     com.ffusion.tasks.banking.UpdateFixedDepositInstrument
 * JD-Core Version:    0.7.0.1
 */