package com.ffusion.services.banklookup;

import com.ffusion.banklookup.FinancialInstitutionException;
import com.ffusion.banklookup.adapters.FinancialInstitutionAdapter;
import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class BankLookupService
  implements com.ffusion.services.BankLookupService
{
  public static final String SERVICE_INIT_XML = "banklookup.xml";
  public static final String BANKLOOKUP_SETTINGS = "BANKLOOKUP_SETTINGS";
  
  public void initialize()
    throws CSILException
  {
    String str1 = "BankLookupService.initialize";
    HashMap localHashMap = a();
    if (localHashMap == null)
    {
      localObject = "*** BankLookup Error: The Service could not be initialized because failed to load config file banklookup.xml";
      DebugLog.log(Level.INFO, (String)localObject);
      throw new CSILException(str1, -1004, (String)localObject);
    }
    Object localObject = (HashMap)localHashMap.get("BANKLOOKUP_SETTINGS");
    if (localObject == null)
    {
      String str2 = "*** BankLookup Error: The <BANKLOOKUP_SETTINGS> tag was not found in the banklookup.xml configuration file.";
      DebugLog.log(str2);
      throw new CSILException(str1, 22012, str2);
    }
    try
    {
      FinancialInstitutionAdapter.initialize((HashMap)localObject);
    }
    catch (FinancialInstitutionException localFinancialInstitutionException)
    {
      String str3 = "*** BankLooup Error: The Service could not be initialized because FinancialInstitutionAdapter.initialize() failed.";
      DebugLog.log(Level.INFO, str3);
      localFinancialInstitutionException.printStackTrace(System.err);
      throw new CSILException(-1010, a(localFinancialInstitutionException.getErrCode()));
    }
  }
  
  public FinancialInstitutions getFinancialInstitutions(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      boolean bool = true;
      if (paramHashMap != null)
      {
        String str = (String)paramHashMap.get("addingGlobalPayee");
        if ((str != null) && ("true".equalsIgnoreCase(str.trim()))) {
          bool = false;
        }
      }
      return FinancialInstitutionAdapter.get(paramFinancialInstitution, bool, paramInt);
    }
    catch (FinancialInstitutionException localFinancialInstitutionException)
    {
      DebugLog.log(Level.INFO, "*** BankLooup Error: Unable to get FinancialInstitutions Info.");
      throw new CSILException(-1009, a(localFinancialInstitutionException.getErrCode()));
    }
  }
  
  public FinancialInstitution updateFinancialInstitution(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      FinancialInstitutionAdapter.update(paramFinancialInstitution);
      return paramFinancialInstitution;
    }
    catch (FinancialInstitutionException localFinancialInstitutionException)
    {
      DebugLog.log(Level.INFO, "*** BankLooup Error: Unable to get FinancialInstitutions Info.");
      throw new CSILException(-1009, a(localFinancialInstitutionException.getErrCode()));
    }
  }
  
  public ArrayList getCountries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return FinancialInstitutionAdapter.getCountries();
    }
    catch (FinancialInstitutionException localFinancialInstitutionException)
    {
      DebugLog.log(Level.INFO, "*** BankLooup Error: Unable to get Countries.");
      throw new CSILException(-1009, a(localFinancialInstitutionException.getErrCode()));
    }
  }
  
  private HashMap a()
    throws CSILException
  {
    String str1 = "BankLookupService.retrieveConfigInfo";
    InputStream localInputStream = ResourceUtil.getResourceAsStream(this, "banklookup.xml");
    if (localInputStream == null)
    {
      str2 = "*** BankLookup Error: During the initialization of the bank lookup service the XML configuration file banklookup.xml could  not be found.";
      DebugLog.log(str2);
      throw new CSILException(str1, 22011, str2);
    }
    String str2 = null;
    try
    {
      str2 = Strings.streamToString(localInputStream);
    }
    catch (IOException localIOException)
    {
      String str3 = "*** BankLookup Error: Unable to read from the XML configuration file while initializing the positive pay service.";
      DebugLog.throwing(str3, localIOException);
      throw new CSILException(str1, 22011, str3);
    }
    XMLTag localXMLTag = new XMLTag(true);
    try
    {
      localXMLTag.build(str2);
    }
    catch (Exception localException)
    {
      String str4 = "*** BankLookup Error: Unable to read from the XML configuration file while initializing the positive pay service.";
      DebugLog.throwing(str4, localException);
      throw new CSILException(str1, 22011, str4);
    }
    return localXMLTag.getTagHashMap();
  }
  
  private int a(int paramInt)
  {
    int i = -1009;
    switch (paramInt)
    {
    case 1: 
      i = 32000;
      break;
    case 2: 
      i = 32001;
      break;
    case 3: 
      i = 32002;
      break;
    case 4: 
      i = 32003;
      break;
    case 5: 
      i = 32004;
      break;
    case 6: 
      i = 32005;
      break;
    case 7: 
      i = 32006;
      break;
    case 8: 
      i = 32007;
      break;
    case 9: 
      i = 32008;
      break;
    case 10: 
      i = 32009;
      break;
    case 11: 
      i = 32010;
      break;
    case 12: 
      i = 32011;
      break;
    case 14: 
      i = 32012;
      break;
    case 15: 
      i = 32013;
      break;
    case 13: 
    default: 
      i = 1;
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banklookup.BankLookupService
 * JD-Core Version:    0.7.0.1
 */