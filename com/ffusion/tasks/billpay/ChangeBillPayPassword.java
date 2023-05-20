package com.ffusion.tasks.billpay;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Billpay;
import com.ffusion.services.BillPay;
import com.ffusion.tasks.ChangePassword;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class ChangeBillPayPassword
  extends ChangePassword
  implements Task
{
  protected String changePassword(HttpSession paramHttpSession)
  {
    String str = null;
    BillPay localBillPay = (BillPay)paramHttpSession.getAttribute("com.ffusion.services.BillPay");
    HashMap localHashMap = null;
    if (localBillPay != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBillPay);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      localSecureUser = Billpay.changePIN(localSecureUser, this.newPassword, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error != 0) {
      str = this.serviceErrorURL;
    } else {
      str = this.successURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.ChangeBillPayPassword
 * JD-Core Version:    0.7.0.1
 */