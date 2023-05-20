package com.ffusion.services.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.affiliatebank.CutOffDefinition;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.alerts.Alert;
import com.ffusion.beans.alerts.DeliveryInfo;
import com.ffusion.beans.alerts.DeliveryInfos;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.efs.adapters.profile.ObjectPropertyAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.AffiliateBankAdmin3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class AffiliateBankService
  implements AffiliateBankAdmin3
{
  private static final String a = "Restricted Calculations";
  
  public AffiliateBanks getAffiliateBankNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return AffiliateBankAdapter.getAffiliateBankNames(paramSecureUser, paramHashMap);
  }
  
  public AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return AffiliateBankAdapter.getAffiliateBanks(paramSecureUser, paramHashMap);
  }
  
  public AffiliateBank getAffiliateBankByID(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    return AffiliateBankAdapter.getAffiliateBankByID(paramSecureUser, paramInt, paramHashMap);
  }
  
  public AffiliateBank addAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws ProfileException
  {
    return AffiliateBankAdapter.addAffiliateBank(paramAffiliateBank);
  }
  
  public void deleteAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws ProfileException
  {
    AffiliateBankAdapter.deleteAffiliateBank(paramAffiliateBank);
    int i = 1;
    String str = String.valueOf(paramAffiliateBank.getAffiliateBankID());
    ObjectPropertyAdapter.deleteObjectProperty(i, str, "Restricted Calculations");
  }
  
  public void modifyAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws ProfileException
  {
    AffiliateBankAdapter.modifyAffiliateBank(paramAffiliateBank);
  }
  
  public CutOffDefinition getCutOffDefinition(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException
  {
    return AffiliateBankAdapter.getCutOffDefinition(paramInt1, paramInt2);
  }
  
  public void setCutOffDefinition(SecureUser paramSecureUser, int paramInt1, int paramInt2, CutOffDefinition paramCutOffDefinition, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "AffiliateBankService.setCutOffDefinition";
    if (paramInt2 == 1) {
      try
      {
        com.ffusion.services.Alerts localAlerts = (com.ffusion.services.Alerts)paramHashMap.get("SERVICE");
        if (localAlerts == null) {
          throw new ProfileException(str, 3850, "Alert service not found");
        }
        localAlerts.setUserName("PPAY_DEFAULT_DECISIONS");
        com.ffusion.beans.alerts.Alerts localAlerts1 = new com.ffusion.beans.alerts.Alerts();
        localAlerts.getAlerts(localAlerts1);
        Object localObject1 = null;
        Iterator localIterator = localAlerts1.iterator();
        Object localObject3;
        Object localObject4;
        while (localIterator.hasNext())
        {
          localObject2 = (Alert)localIterator.next();
          localObject3 = (DeliveryInfo)((Alert)localObject2).getDeliveryInfosValue().get(0);
          ((DeliveryInfo)localObject3).setPropertyKey("PPAY_AFFILIATE_BANK_ID");
          localObject4 = ((DeliveryInfo)localObject3).getProperty();
          try
          {
            if ((localObject4 != null) && (Integer.parseInt((String)localObject4) == paramInt1))
            {
              localObject1 = localObject2;
              break;
            }
          }
          catch (NumberFormatException localNumberFormatException) {}
        }
        Object localObject2 = null;
        if (paramCutOffDefinition != null)
        {
          localObject3 = paramCutOffDefinition.getCutOffs();
          if ((localObject3 != null) && (((CutOffTimes)localObject3).size() > 0))
          {
            localObject4 = (CutOffTime)((CutOffTimes)localObject3).get(0);
            if (((CutOffTime)localObject4).getTimeOfDay().length() > 0) {
              localObject2 = ((CutOffTime)localObject4).getTimeOfDay();
            }
          }
        }
        int i = 0;
        if (localObject2 != null)
        {
          int j;
          int k;
          if (localObject1 != null)
          {
            localObject4 = new DateTime();
            j = Integer.parseInt(((String)localObject2).substring(0, ((String)localObject2).indexOf(':')));
            k = Integer.parseInt(((String)localObject2).substring(((String)localObject2).indexOf(':') + 1));
            if ((((DateTime)localObject4).get(10) > j) || ((((DateTime)localObject4).get(10) == j) && (((DateTime)localObject4).get(12) > k))) {
              ((DateTime)localObject4).add(5, 1);
            }
            ((DateTime)localObject4).set(11, j);
            ((DateTime)localObject4).set(12, k);
            ((DateTime)localObject4).set(13, 0);
            ((DateTime)localObject4).set(14, 0);
            ((Alert)localObject1).setStartDate((DateTime)localObject4);
            i = localAlerts.modifyAlert((Alert)localObject1);
          }
          else
          {
            localObject1 = new Alert(Locale.getDefault());
            localObject4 = new DateTime();
            j = Integer.parseInt(((String)localObject2).substring(0, ((String)localObject2).indexOf(':')));
            k = Integer.parseInt(((String)localObject2).substring(((String)localObject2).indexOf(':') + 1));
            if ((((DateTime)localObject4).get(10) > j) || ((((DateTime)localObject4).get(10) == j) && (((DateTime)localObject4).get(12) > k))) {
              ((DateTime)localObject4).add(5, 1);
            }
            ((DateTime)localObject4).set(11, j);
            ((DateTime)localObject4).set(12, k);
            ((DateTime)localObject4).set(13, 0);
            ((DateTime)localObject4).set(14, 0);
            ((Alert)localObject1).setStartDate((DateTime)localObject4);
            ((Alert)localObject1).setMessage("This positive pay decision is made by the banking system");
            ((Alert)localObject1).setTimeZone(TimeZone.getDefault());
            ((Alert)localObject1).setType(2);
            ((Alert)localObject1).setEndDate("01/01/2100");
            ((Alert)localObject1).setInterval("86400000");
            DeliveryInfo localDeliveryInfo = (DeliveryInfo)((Alert)localObject1).getDeliveryInfosValue().create();
            localDeliveryInfo.setChannelName("PositivePayDefaultDecisions");
            localDeliveryInfo.setPropertyKey("PPAY_AFFILIATE_BANK_ID");
            localDeliveryInfo.setPropertyValue(String.valueOf(paramInt1));
            i = localAlerts.addAlert((Alert)localObject1);
          }
        }
        else if (localObject1 != null)
        {
          i = localAlerts.deleteAlert((Alert)localObject1);
        }
        if (i != 0) {
          throw new ProfileException(str, 3850, "Failure updating Alerts");
        }
      }
      catch (ClassCastException localClassCastException)
      {
        throw new ProfileException(str, 3851, "Failed to set values for cut-off defintion");
      }
    }
    AffiliateBankAdapter.setCutOffDefinition(paramInt1, paramInt2, paramCutOffDefinition);
  }
  
  public void initialize(String paramString)
    throws ProfileException
  {}
  
  public ArrayList getRestrictedCalculations(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    int i = 1;
    String str1 = String.valueOf(paramInt);
    String str2 = ObjectPropertyAdapter.getObjectPropertyValue(i, str1, "Restricted Calculations");
    ArrayList localArrayList = new ArrayList();
    if (str2 != null)
    {
      String str3 = ",";
      StringTokenizer localStringTokenizer = new StringTokenizer(str2, str3);
      while (localStringTokenizer.hasMoreTokens()) {
        localArrayList.add(localStringTokenizer.nextToken());
      }
    }
    return localArrayList;
  }
  
  public void saveRestrictedCalculations(SecureUser paramSecureUser, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException
  {
    int i = 1;
    String str1 = String.valueOf(paramInt);
    Iterator localIterator = paramArrayList.iterator();
    StringBuffer localStringBuffer = new StringBuffer();
    String str2 = ",";
    int j = 1;
    while (localIterator.hasNext()) {
      if (j != 0)
      {
        localStringBuffer.append((String)localIterator.next());
        j = 0;
      }
      else
      {
        String str3 = (String)localIterator.next();
        localStringBuffer.append(str2);
        localStringBuffer.append(str3);
      }
    }
    ObjectPropertyAdapter.modifyObjectProperty(i, str1, "Restricted Calculations", localStringBuffer.toString());
  }
  
  public AffiliateBank getAffiliateBankByBPWID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException
  {
    return AffiliateBankAdapter.getAffiliateBankByBPWID(paramSecureUser, paramString, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.AffiliateBankService
 * JD-Core Version:    0.7.0.1
 */