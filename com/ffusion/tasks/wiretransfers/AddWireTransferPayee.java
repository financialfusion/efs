package com.ffusion.tasks.wiretransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Wire;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class AddWireTransferPayee
  extends EditWireTransferPayee
{
  protected int validateInput(HttpSession paramHttpSession)
    throws CSILException
  {
    int i = 0;
    if (this.validate == null) {
      return i;
    }
    i = super.validateInput(paramHttpSession);
    this.validate = null;
    return i;
  }
  
  protected int doProcess(HttpSession paramHttpSession)
  {
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    setCustomerID(localSecureUser.getBusinessID());
    setSubmittedBy(localSecureUser.getProfileID());
    WireTransferPayee localWireTransferPayee = new WireTransferPayee();
    try
    {
      localWireTransferPayee = Wire.addWireTransferPayee(localSecureUser, this, localHashMap);
      set(localWireTransferPayee);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute(this.payeeSessionName, localWireTransferPayee);
    }
    return this.error;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.wiretransfers.AddWireTransferPayee
 * JD-Core Version:    0.7.0.1
 */