package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.ChangePassword;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class ChangeBankingPassword
  extends ChangePassword
  implements Task
{
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  protected String changePassword(HttpSession paramHttpSession)
  {
    String str = null;
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHttpSession.getAttribute(this.bankingServiceName);
    HashMap localHashMap = null;
    if (localBanking != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBanking);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      localSecureUser = com.ffusion.csil.core.Banking.changePIN(localSecureUser, this.currentPassword, this.newPassword, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.ChangeBankingPassword
 * JD-Core Version:    0.7.0.1
 */