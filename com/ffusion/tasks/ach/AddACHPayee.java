package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.MapError;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

public class AddACHPayee
  extends EditACHPayee
{
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    setSubmittedBy(Integer.toString(localSecureUser.getProfileID()));
    setID(null);
  }
  
  protected int doProcess(HttpSession paramHttpSession, ACHPayees paramACHPayees)
  {
    this.error = 0;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      ACHPayee localACHPayee1 = ACH.addACHPayee(localSecureUser, this, localHashMap);
      set(localACHPayee1);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    if (this.error == 0)
    {
      ACHPayee localACHPayee2 = (ACHPayee)paramACHPayees.create();
      localACHPayee2.set(this);
      if ((this.payeeSessionName != null) && (this.payeeSessionName.length() > 0)) {
        paramHttpSession.setAttribute(this.payeeSessionName, localACHPayee2);
      }
      paramACHPayees.setSortedBy(paramACHPayees.getSortedBy());
    }
    return this.error;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.AddACHPayee
 * JD-Core Version:    0.7.0.1
 */