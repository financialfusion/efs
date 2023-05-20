package com.ffusion.services.terms;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.user.User;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ObjectPropertyAdapter;
import com.ffusion.services.ITerms;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

public class TermsService
  implements ITerms
{
  private static String a = null;
  
  public void initialize(HashMap paramHashMap)
    throws TermsException
  {
    String str = "com.ffusion.services.terms.TermsService.initialize";
    try
    {
      if (a == null)
      {
        Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
        if (localProperties == null)
        {
          DebugLog.log(Level.SEVERE, str + ": Missing database connection pool configuration.");
          throw new TermsException(str, 3, "Missing database connection pool configuration.");
        }
        a = ConnectionPool.init(localProperties);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str + ": " + localException.getMessage());
      throw new TermsException(localException, str, 1, localException.getMessage());
    }
  }
  
  public synchronized void resetTermsAcceptance(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks, HashMap paramHashMap)
    throws TermsException
  {
    String str1 = "com.ffusion.services.terms.TermsService.resetTermsAcceptance";
    Connection localConnection = null;
    String str3 = null;
    try
    {
      String str2;
      if ((paramSecureUser == null) || (paramAffiliateBanks == null))
      {
        str2 = "One or more illegal arguments have been specified.";
        DebugLog.log(Level.FINE, str1 + ": " + str2);
        throw new TermsException(str1, 4, str2);
      }
      Date localDate = new Date(System.currentTimeMillis());
      str3 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").format(localDate);
      localConnection = DBUtil.getConnection(a, false, 2);
      for (int j = 0; j < paramAffiliateBanks.size(); j++)
      {
        AffiliateBank localAffiliateBank = (AffiliateBank)paramAffiliateBanks.get(j);
        String str4 = String.valueOf(localAffiliateBank.getAffiliateBankID());
        if ((str4 == null) || (str4.equals("")) || (str4.equals("0")))
        {
          str2 = "An invalid affiliate bank id has been specified.";
          DebugLog.log(Level.FINE, str1 + ": " + str2);
          throw new TermsException(str1, 5, str2);
        }
        String str5 = ObjectPropertyAdapter.getObjectPropertyValue(1, String.valueOf(str4), "TERMS_VERSION");
        int i;
        if (str5 != null) {
          i = Integer.valueOf(str5).intValue();
        } else {
          i = 1;
        }
        ObjectPropertyAdapter.modifyObjectProperty(localConnection, 1, String.valueOf(str4), "TERMS_VERSION", String.valueOf(i + 1));
        ObjectPropertyAdapter.modifyObjectProperty(localConnection, 1, String.valueOf(str4), "TERMS_DATE", str3);
      }
      DBUtil.commit(localConnection);
    }
    catch (TermsException localTermsException)
    {
      DBUtil.rollback(localConnection);
      throw localTermsException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str1 + ": " + localException.getMessage());
      DBUtil.rollback(localConnection);
      throw new TermsException(localException, str1, 2, localException.getMessage());
    }
    finally
    {
      DBUtil.returnConnection(a, localConnection);
    }
  }
  
  public boolean checkTermsAcceptance(SecureUser paramSecureUser, HashMap paramHashMap)
    throws TermsException
  {
    String str1 = "com.ffusion.services.terms.TermsService.checkTermsAcceptance";
    boolean bool = false;
    try
    {
      String str3;
      if (paramSecureUser == null)
      {
        str3 = "One or more illegal arguments have been specified.";
        DebugLog.log(Level.FINE, str1 + ": " + str3);
        throw new TermsException(str1, 4, str3);
      }
      String str2 = paramSecureUser.getAffiliateID();
      if ((str2 == null) || (str2.equals("")) || (str2.equals("0")))
      {
        str3 = "An invalid affiliate bank id has been specified.";
        DebugLog.log(Level.FINE, str1 + ": " + str3);
        throw new TermsException(str1, 5, str3);
      }
      String str4 = ObjectPropertyAdapter.getObjectPropertyValue(1, str2, "TERMS_VERSION");
      int i;
      if (str4 != null) {
        i = Integer.valueOf(str4).intValue();
      } else {
        i = 1;
      }
      User localUser = CustomerAdapter.getUser(paramSecureUser);
      if (i > localUser.getTermsAccepted()) {
        bool = true;
      } else {
        bool = false;
      }
    }
    catch (TermsException localTermsException)
    {
      throw localTermsException;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, str1 + ": " + localException.getMessage());
      throw new TermsException(localException, str1, 2, localException.getMessage());
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.terms.TermsService
 * JD-Core Version:    0.7.0.1
 */