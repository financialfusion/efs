package com.ffusion.beans.business;

import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public class TransactionLimits
  extends FilteredList
  implements BusinessDefines, Serializable
{
  public static final String LIMIT_OBJECT_TYPE_ACCOUNT = "Account";
  public static final boolean LIMIT_ALLOW_APPROVAL_TRUE = true;
  public static final int[] periods = { 1, 2, 3, 4 };
  LimitTypePropertyLists ij = null;
  
  public TransactionLimits(LimitTypePropertyLists paramLimitTypePropertyLists, String paramString, Locale paramLocale)
  {
    this.ij = paramLimitTypePropertyLists;
    TransactionLimit localTransactionLimit = null;
    for (int i = 0; i < paramLimitTypePropertyLists.size(); i++)
    {
      LimitTypePropertyList localLimitTypePropertyList = (LimitTypePropertyList)paramLimitTypePropertyLists.get(i);
      if (localLimitTypePropertyList.isPropertySet("category", paramString)) {
        for (int j = 0; j < periods.length; j++)
        {
          localTransactionLimit = new TransactionLimit(paramLocale);
          localTransactionLimit.setLimitName(localLimitTypePropertyList.getOperationName());
          localTransactionLimit.setPeriod(periods[j]);
          String str1 = EntitlementsUtil.getPropertyValue(localLimitTypePropertyList, "display name", paramLocale);
          if (str1 != null) {
            localTransactionLimit.setDisplayName(str1);
          } else {
            localTransactionLimit.setDisplayName(localLimitTypePropertyList.getOperationName());
          }
          localTransactionLimit.setLimitCategory(paramString);
          if (localLimitTypePropertyList.isPropertySet("hide"))
          {
            String str2 = localLimitTypePropertyList.getPropertyValue("hide", 0);
            if ((str2 != null) && (str2.equalsIgnoreCase("yes"))) {
              localTransactionLimit.setHide(true);
            } else {
              localTransactionLimit.setHide(false);
            }
          }
          add(localTransactionLimit);
        }
      }
    }
  }
  
  public TransactionLimits() {}
  
  public void set(TransactionLimits paramTransactionLimits)
  {
    TransactionLimit localTransactionLimit1 = null;
    TransactionLimit localTransactionLimit2 = null;
    for (int i = 0; i < paramTransactionLimits.size(); i++)
    {
      localTransactionLimit1 = (TransactionLimit)paramTransactionLimits.get(i);
      localTransactionLimit2 = getLimitByNameAndPeriod(localTransactionLimit1.getLimitName(), localTransactionLimit1.getPeriod());
      if (localTransactionLimit2 != null) {
        localTransactionLimit2.set(localTransactionLimit1);
      }
    }
  }
  
  public void setLimits(Limits paramLimits)
  {
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext())
    {
      Limit localLimit = (Limit)localIterator.next();
      TransactionLimit localTransactionLimit = getLimitByNameAndPeriod(localLimit.getLimitName(), localLimit.getPeriod());
      if (localTransactionLimit != null) {
        localTransactionLimit.setLimit(localLimit);
      }
    }
  }
  
  public Limits getLimits(EntitlementGroup paramEntitlementGroup, LimitTypePropertyLists paramLimitTypePropertyLists)
  {
    Limits localLimits = new Limits();
    TransactionLimit localTransactionLimit = null;
    for (int i = 0; i < size(); i++)
    {
      Limit localLimit = new Limit();
      localTransactionLimit = (TransactionLimit)get(i);
      if (localTransactionLimit != null)
      {
        localLimit.setLimitId(localTransactionLimit.getLimitId());
        localLimit.setData(localTransactionLimit.getData());
        localLimit.setLimitName(localTransactionLimit.getLimitName());
        if (localTransactionLimit.getPeriod() == 0) {
          localLimit.setPeriod(1);
        } else {
          localLimit.setPeriod(localTransactionLimit.getPeriod());
        }
        localLimit.setGroupId(paramEntitlementGroup.getGroupId());
        localLimit.setParentId(paramEntitlementGroup.getParentId());
        localLimit.setAllowApproval(Boolean.valueOf(localTransactionLimit.getAllowApproval()).booleanValue());
        localLimit.setRunningTotalType('G');
        localLimit.setCurrencyCode(localTransactionLimit.getCurrencyCode());
        localLimit.setCrossCurrency(localTransactionLimit.isCrossCurrency());
        localLimits.add(localLimit);
      }
    }
    return localLimits;
  }
  
  public LimitTypePropertyLists getLimitTypeProps()
  {
    return this.ij;
  }
  
  public void setLimitTypeProps(LimitTypePropertyLists paramLimitTypePropertyLists)
  {
    this.ij = paramLimitTypePropertyLists;
  }
  
  public TransactionLimit getTXLimitByName(String paramString)
  {
    Object localObject = null;
    TransactionLimit localTransactionLimit = null;
    for (int i = 0; i < size(); i++)
    {
      localTransactionLimit = (TransactionLimit)get(i);
      if ((localTransactionLimit.getLimitName().equals(paramString)) && ((localTransactionLimit.getPeriod() == 1) || (localTransactionLimit.getPeriod() == 0)))
      {
        localObject = localTransactionLimit;
        break;
      }
    }
    if (localObject == null) {
      DebugLog.log("TransactionLimit with name [ " + paramString + " ] not found");
    }
    return localObject;
  }
  
  public TransactionLimit getLimitByNameAndPeriod(String paramString, int paramInt)
  {
    Object localObject = null;
    TransactionLimit localTransactionLimit = null;
    if (paramInt == 0) {
      paramInt = 1;
    }
    for (int i = 0; i < size(); i++)
    {
      localTransactionLimit = (TransactionLimit)get(i);
      int j = localTransactionLimit.getPeriod();
      if (j == 0) {
        j = 1;
      }
      if ((localTransactionLimit.getLimitName().equals(paramString)) && (j == paramInt))
      {
        localObject = localTransactionLimit;
        break;
      }
    }
    if (localObject == null) {
      DebugLog.log("TransactionLimit with name [ " + paramString + " ] and Period [ " + paramInt + " ] not found");
    }
    return localObject;
  }
  
  public void setTXLimitData(String paramString1, String paramString2)
  {
    TransactionLimit localTransactionLimit = getTXLimitByName(paramString1);
    if (localTransactionLimit != null) {
      localTransactionLimit.setCurrencyString(paramString2);
    }
  }
  
  public void setLimitData(String paramString1, String paramString2, int paramInt)
  {
    TransactionLimit localTransactionLimit = getLimitByNameAndPeriod(paramString1, paramInt);
    if (localTransactionLimit != null) {
      localTransactionLimit.setCurrencyString(paramString2);
    }
  }
  
  public String getTXLimitData(String paramString)
  {
    TransactionLimit localTransactionLimit = getTXLimitByName(paramString);
    if (localTransactionLimit != null) {
      return localTransactionLimit.getCurrencyString();
    }
    return null;
  }
  
  public BigDecimal getTXLimitAmount(String paramString)
  {
    TransactionLimit localTransactionLimit = getTXLimitByName(paramString);
    BigDecimal localBigDecimal = null;
    if (localTransactionLimit != null) {
      localBigDecimal = localTransactionLimit.getAmount();
    } else {
      localBigDecimal = new BigDecimal(0.0D);
    }
    return localBigDecimal;
  }
  
  public ArrayList getLimitCategories()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localObject = (TransactionLimit)localIterator.next();
      String str = ((TransactionLimit)localObject).getLimitCategory();
      if (!localArrayList.contains(str)) {
        localArrayList.add(str);
      }
    }
    Object localObject = new String[localArrayList.size()];
    localArrayList.toArray((Object[])localObject);
    Arrays.sort((Object[])localObject);
    localArrayList.clear();
    localArrayList.addAll(Arrays.asList((Object[])localObject));
    return localArrayList;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.TransactionLimits
 * JD-Core Version:    0.7.0.1
 */