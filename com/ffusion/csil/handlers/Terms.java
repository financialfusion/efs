package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.csil.CSILException;
import com.ffusion.services.ITerms;
import com.ffusion.services.terms.TermsException;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class Terms
  extends Initialize
{
  private static final String a6X = "Terms";
  private static ITerms a6W;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Terms.initialize";
    debug(str);
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Terms", str, 91001);
    a6W = (ITerms)HandlerUtil.instantiateService(localHashMap, str, 91001);
    try
    {
      a6W.initialize(paramHashMap);
    }
    catch (Exception localException)
    {
      throw new CSILException(str, jdMethod_for(localException), localException);
    }
  }
  
  public static void resetTermsAcceptance(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Terms.resetTermsAcceptance";
    try
    {
      if (a6W == null) {
        throwing(-1009, 91000);
      }
      a6W.resetTermsAcceptance(paramSecureUser, paramAffiliateBanks, paramHashMap);
    }
    catch (TermsException localTermsException)
    {
      CSILException localCSILException = new CSILException(jdMethod_for(localTermsException), localTermsException.getCode(), localTermsException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean checkTermsAcceptance(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Terms.checkTermsAcceptance";
    try
    {
      if (a6W == null) {
        throwing(-1009, 91000);
      }
      return a6W.checkTermsAcceptance(paramSecureUser, paramHashMap);
    }
    catch (TermsException localTermsException)
    {
      CSILException localCSILException = new CSILException(jdMethod_for(localTermsException), localTermsException.getCode(), localTermsException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static int jdMethod_for(Throwable paramThrowable)
  {
    int i = 1;
    if ((paramThrowable instanceof TermsException))
    {
      TermsException localTermsException = (TermsException)paramThrowable;
      switch (localTermsException.getCode())
      {
      case 1: 
      case 3: 
        i = 91001;
        break;
      case 2: 
        i = 91002;
        break;
      case 4: 
        i = 91003;
        break;
      case 5: 
        i = 91005;
        break;
      default: 
        i = 1;
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Terms
 * JD-Core Version:    0.7.0.1
 */