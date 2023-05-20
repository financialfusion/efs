package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.affiliatebank.CutOffDefinition;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.AffiliateBankAdmin3;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;

public class AffiliateBankAdmin
{
  private static final String jdField_if = "AffiliateBank";
  private static AffiliateBankAdmin3 a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "AffiliateBank", str, 20107);
    a = (AffiliateBankAdmin3)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a.initialize("");
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(20107, localProfileException);
      DebugLog.throwing(str, localProfileException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a;
  }
  
  public static AffiliateBanks getAffiliateBankNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.getAffiliateBankNames";
    try
    {
      return a.getAffiliateBankNames(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25000, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.getAffiliateBanks";
    try
    {
      return a.getAffiliateBanks(paramSecureUser, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25000, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static AffiliateBank getAffiliateBankByID(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.getAffiliateBankByID";
    try
    {
      return a.getAffiliateBankByID(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25000, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getRestrictedCalculations(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.getRestrictedCalculations";
    try
    {
      return a.getRestrictedCalculations(paramSecureUser, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25000, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void saveRestrictedCalculations(SecureUser paramSecureUser, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.saveRestrictedCalculations";
    try
    {
      a.saveRestrictedCalculations(paramSecureUser, paramInt, paramArrayList, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25000, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static AffiliateBank getAffiliateBankByBPWID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.getAffiliateBankByBPWID";
    try
    {
      return a.getAffiliateBankByBPWID(paramSecureUser, paramString, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25000, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static AffiliateBank getAffiliateBankByRoutingNumber(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBank.getAffiliateBankByRoutingNumber";
    try
    {
      AffiliateBank localAffiliateBank1 = new AffiliateBank();
      localAffiliateBank1.setAffiliateRoutingNum(paramString);
      localAffiliateBank1 = ACH.getAffiliateBank(paramSecureUser, localAffiliateBank1, paramHashMap);
      localObject = localAffiliateBank1.getFIBPWID();
      AffiliateBank localAffiliateBank2 = a.getAffiliateBankByBPWID(paramSecureUser, (String)localObject, paramHashMap);
      localAffiliateBank2.setDescription(localAffiliateBank1.getDescription());
      localAffiliateBank2.setAffiliateRoutingNum(localAffiliateBank1.getAffiliateRoutingNum());
      localAffiliateBank2.setCurrencyCode(localAffiliateBank1.getCurrencyCode());
      localAffiliateBank2.setStreet(localAffiliateBank1.getStreet());
      localAffiliateBank2.setStreet2(localAffiliateBank1.getStreet2());
      localAffiliateBank2.setStreet3(localAffiliateBank1.getStreet3());
      localAffiliateBank2.setCity(localAffiliateBank1.getCity());
      localAffiliateBank2.setState(localAffiliateBank1.getState());
      localAffiliateBank2.setCountry(localAffiliateBank1.getCountry());
      localAffiliateBank2.setZipCode(localAffiliateBank1.getZipCode());
      localAffiliateBank2.setACHImmediateOrigin(localAffiliateBank1.getACHImmediateOrigin());
      localAffiliateBank2.setACHImmediateOriginName(localAffiliateBank1.getACHImmediateOriginName());
      localAffiliateBank2.setACHDestination(localAffiliateBank1.getACHDestination());
      localAffiliateBank2.setACHDestinationName(localAffiliateBank1.getACHDestinationName());
      return localAffiliateBank2;
    }
    catch (ProfileException localProfileException)
    {
      Object localObject = new CSILException(25000, localProfileException);
      DebugLog.throwing(str, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
  }
  
  public static AffiliateBank addAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.addAffiliateBank";
    try
    {
      return a.addAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25001, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static AffiliateBank deleteAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.deleteAffiliateBank";
    try
    {
      a.deleteAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25002, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramAffiliateBank;
  }
  
  public static AffiliateBank modifyAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.modifyAffiliateBank";
    try
    {
      a.modifyAffiliateBank(paramSecureUser, paramAffiliateBank, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25003, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    return paramAffiliateBank;
  }
  
  public static CutOffDefinition getCutOffDefinition(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.getCutOffDefinition";
    try
    {
      return a.getCutOffDefinition(paramSecureUser, paramInt1, paramInt2, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(25021, localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void setCutOffDefinition(SecureUser paramSecureUser, int paramInt1, int paramInt2, CutOffDefinition paramCutOffDefinition, HashMap paramHashMap)
    throws CSILException
  {
    String str = "AffiliateBankAdmin.setCutOffDefinition";
    try
    {
      a.setCutOffDefinition(paramSecureUser, paramInt1, paramInt2, paramCutOffDefinition, paramHashMap);
      return;
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException;
      if (localProfileException.code == 3850) {
        localCSILException = new CSILException(25024, localProfileException);
      } else {
        localCSILException = new CSILException(25022, localProfileException);
      }
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.AffiliateBankAdmin
 * JD-Core Version:    0.7.0.1
 */