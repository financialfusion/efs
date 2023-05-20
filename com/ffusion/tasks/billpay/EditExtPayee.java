package com.ffusion.tasks.billpay;

import com.ffusion.beans.billpay.Payees;
import com.ffusion.csil.CSILException;
import javax.servlet.http.HttpSession;

public class EditExtPayee
  extends EditPayee
{
  protected int validateInput(HttpSession paramHttpSession, Payees paramPayees)
    throws CSILException
  {
    int i = 0;
    if (this.validate != null)
    {
      String str = getNickName();
      if ((this.validate.indexOf("NICK_NAME") != -1) && (str.length() == 0)) {
        i = 34;
      } else {
        i = super.validateInput(paramHttpSession, paramPayees);
      }
      this.validate = null;
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.EditExtPayee
 * JD-Core Version:    0.7.0.1
 */