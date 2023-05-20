package com.ffusion.services.fx;

import com.ffusion.beans.Currency;
import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.beans.fx.FXRates;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.fx.FXException;
import com.ffusion.fx.adapter.FXAdapter;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

public class FXCommonStoredService
  implements IForeignExchangeService
{
  protected FXAdapter _adapter = new FXAdapter();
  protected EntitlementCachedAdapter eadapter = null;
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    FXException localFXException = new FXException(34029, "This API is not implemented.");
    DebugLog.throwing("FXCommonStoredService.cleanup", localFXException);
    throw localFXException;
  }
  
  public FXCurrencies getBaseCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencies localFXCurrencies = null;
    localFXCurrencies = this._adapter.getBaseCurrencies(paramInt, paramHashMap);
    return localFXCurrencies;
  }
  
  public FXCurrencies getBaseCurrenciesGivenTarget(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencies localFXCurrencies = null;
    localFXCurrencies = this._adapter.getBaseCurrenciesGivenTarget(paramInt, paramString, paramHashMap);
    return localFXCurrencies;
  }
  
  public FXRate getClosestFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    FXRate localFXRate = null;
    try
    {
      Object localObject1;
      if (paramInt2 == 4)
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setMemberType("USER");
        ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(1));
        ((EntitlementGroupMember)localObject1).setId(paramString3);
        localObject1 = this.eadapter.getMember((EntitlementGroupMember)localObject1);
        localObject2 = this.eadapter.getEntitlementGroup(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        if ("User".equalsIgnoreCase(((EntitlementGroup)localObject2).getEntGroupType()))
        {
          EntitlementGroup localEntitlementGroup1 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          paramHashMap.put("CONSUMER_RATE", paramString3);
          localFXRate = getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, 2, Integer.toString(localEntitlementGroup1.getParentId()), paramHashMap);
        }
        else
        {
          int i = 0;
          if ("Division".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getParentId();
          }
          else if ("Group".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            EntitlementGroup localEntitlementGroup2 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
            i = localEntitlementGroup2.getParentId();
          }
          else if ("Business".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getGroupId();
          }
          localFXRate = getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, 3, Integer.toString(i), paramHashMap);
        }
      }
      else
      {
        Object localObject3;
        Object localObject4;
        if (paramInt2 == 3)
        {
          localObject1 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
          localObject2 = this._adapter.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, 3, Integer.toString(((EntitlementGroup)localObject1).getGroupId()), paramHashMap);
          paramHashMap.put("BUSINESS_RATE", Integer.toString(((EntitlementGroup)localObject1).getGroupId()));
          localObject3 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject1).getParentId());
          int j = ((EntitlementGroup)localObject3).getParentId();
          localObject4 = getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, 2, Integer.toString(j), paramHashMap);
          localFXRate = a((FXRate)localObject2, (FXRate)localObject4);
        }
        else if (paramInt2 == 2)
        {
          localObject1 = this._adapter.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
          localObject2 = (String)paramHashMap.get("AFFILIATE_BANK_ID");
          if (localObject2 == null)
          {
            localObject3 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
            ((EntitlementGroup)localObject3).setGroupPropertiesKey("AffiliateBankId");
            localObject2 = ((EntitlementGroup)localObject3).getGroupPropertiesValue();
            if (localObject2 == null)
            {
              String str = (String)paramHashMap.get("BUSINESS_RATE");
              if (str != null)
              {
                localObject2 = Integer.toString(this._adapter.getBankForBusiness(Integer.parseInt(str)));
              }
              else
              {
                localObject4 = (String)paramHashMap.get("CONSUMER_RATE");
                if (localObject4 != null) {
                  localObject2 = Integer.toString(this._adapter.getBankForCustomer(Integer.parseInt((String)localObject4)));
                }
              }
            }
          }
          localObject3 = getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, 1, (String)localObject2, paramHashMap);
          localFXRate = a((FXRate)localObject1, (FXRate)localObject3);
        }
        else if (paramInt2 == 1)
        {
          localObject1 = this._adapter.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
          localObject2 = getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
          localFXRate = a((FXRate)localObject1, (FXRate)localObject2);
        }
        else if (paramInt2 == 0)
        {
          localFXRate = this._adapter.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
        }
      }
    }
    catch (FXException localFXException)
    {
      throw localFXException;
    }
    catch (Exception localException)
    {
      Object localObject2 = new FXException(34027, localException);
      DebugLog.throwing("FXService.getClosestFXRate", localException);
      throw ((Throwable)localObject2);
    }
    return localFXRate;
  }
  
  public FXRate getClosestFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    FXRate localFXRate = null;
    try
    {
      Object localObject1;
      if (paramInt2 == 4)
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setMemberType("USER");
        ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(1));
        ((EntitlementGroupMember)localObject1).setId(paramString3);
        localObject1 = this.eadapter.getMember((EntitlementGroupMember)localObject1);
        localObject2 = this.eadapter.getEntitlementGroup(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        if ("User".equalsIgnoreCase(((EntitlementGroup)localObject2).getEntGroupType()))
        {
          EntitlementGroup localEntitlementGroup1 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          paramHashMap.put("CONSUMER_RATE", paramString3);
          localFXRate = getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 2, Integer.toString(localEntitlementGroup1.getParentId()), paramHashMap);
        }
        else
        {
          int i = 0;
          if ("Division".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getParentId();
          }
          else if ("Group".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            EntitlementGroup localEntitlementGroup2 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
            i = localEntitlementGroup2.getParentId();
          }
          else if ("Business".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getGroupId();
          }
          localFXRate = getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 3, Integer.toString(i), paramHashMap);
        }
      }
      else
      {
        Object localObject3;
        Object localObject4;
        if (paramInt2 == 3)
        {
          localObject1 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
          localObject2 = this._adapter.getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 3, Integer.toString(((EntitlementGroup)localObject1).getGroupId()), paramHashMap);
          paramHashMap.put("BUSINESS_RATE", Integer.toString(((EntitlementGroup)localObject1).getGroupId()));
          localObject3 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject1).getParentId());
          int j = ((EntitlementGroup)localObject3).getParentId();
          localObject4 = getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 2, Integer.toString(j), paramHashMap);
          localFXRate = a((FXRate)localObject2, (FXRate)localObject4);
        }
        else if (paramInt2 == 2)
        {
          localObject1 = this._adapter.getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
          localObject2 = (String)paramHashMap.get("AFFILIATE_BANK_ID");
          if (localObject2 == null)
          {
            localObject3 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
            ((EntitlementGroup)localObject3).setGroupPropertiesKey("AffiliateBankId");
            localObject2 = ((EntitlementGroup)localObject3).getGroupPropertiesValue();
            if (localObject2 == null)
            {
              String str = (String)paramHashMap.get("BUSINESS_RATE");
              if (str != null)
              {
                localObject2 = Integer.toString(this._adapter.getBankForBusiness(Integer.parseInt(str)));
              }
              else
              {
                localObject4 = (String)paramHashMap.get("CONSUMER_RATE");
                if (localObject4 != null) {
                  localObject2 = Integer.toString(this._adapter.getBankForCustomer(Integer.parseInt((String)localObject4)));
                }
              }
            }
          }
          localObject3 = getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 1, (String)localObject2, paramHashMap);
          localFXRate = a((FXRate)localObject1, (FXRate)localObject3);
        }
        else if (paramInt2 == 1)
        {
          localObject1 = this._adapter.getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
          localObject2 = getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
          localFXRate = a((FXRate)localObject1, (FXRate)localObject2);
        }
        else if (paramInt2 == 0)
        {
          localFXRate = this._adapter.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
        }
      }
    }
    catch (FXException localFXException)
    {
      throw localFXException;
    }
    catch (Exception localException)
    {
      Object localObject2 = new FXException(34027, localException);
      DebugLog.throwing("FXService.getClosestFXRate", localException);
      throw ((Throwable)localObject2);
    }
    return localFXRate;
  }
  
  public FXCurrencies getCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencies localFXCurrencies = null;
    localFXCurrencies = this._adapter.getCurrencies(paramInt, paramHashMap);
    return localFXCurrencies;
  }
  
  public FXRate getFXRate(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXRate localFXRate = null;
    localFXRate = this._adapter.getFXRate(paramInt, paramString1, paramString2, paramHashMap);
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt, String paramString1, String paramString2, DateTime paramDateTime, HashMap paramHashMap)
    throws FXException
  {
    FXRate localFXRate = null;
    localFXRate = this._adapter.getFXRate(paramInt, paramString1, paramString2, paramDateTime, paramHashMap);
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    Object localObject1 = null;
    try
    {
      Object localObject2;
      if (paramInt2 == 4)
      {
        localObject2 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject2).setMemberType("USER");
        ((EntitlementGroupMember)localObject2).setMemberSubType(Integer.toString(1));
        ((EntitlementGroupMember)localObject2).setId(paramString3);
        localObject2 = this.eadapter.getMember((EntitlementGroupMember)localObject2);
        localObject3 = this.eadapter.getEntitlementGroup(((EntitlementGroupMember)localObject2).getEntitlementGroupId());
        if ("User".equalsIgnoreCase(((EntitlementGroup)localObject3).getEntGroupType()))
        {
          EntitlementGroup localEntitlementGroup1 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject3).getParentId());
          paramHashMap.put("CONSUMER_RATE", paramString3);
          localObject1 = getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 2, Integer.toString(localEntitlementGroup1.getParentId()), paramHashMap);
        }
        else
        {
          int i = 0;
          if ("Division".equals(((EntitlementGroup)localObject3).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject3).getParentId();
          }
          else if ("Group".equals(((EntitlementGroup)localObject3).getEntGroupType()))
          {
            EntitlementGroup localEntitlementGroup2 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject3).getParentId());
            i = localEntitlementGroup2.getParentId();
          }
          else if ("Business".equals(((EntitlementGroup)localObject3).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject3).getGroupId();
          }
          localObject1 = getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 3, Integer.toString(i), paramHashMap);
        }
      }
      else
      {
        Object localObject4;
        Object localObject5;
        if (paramInt2 == 3)
        {
          localObject2 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
          localObject3 = this._adapter.getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 3, Integer.toString(((EntitlementGroup)localObject2).getGroupId()), paramHashMap);
          paramHashMap.put("BUSINESS_RATE", Integer.toString(((EntitlementGroup)localObject2).getGroupId()));
          localObject4 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          int j = ((EntitlementGroup)localObject4).getParentId();
          localObject5 = getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 2, Integer.toString(j), paramHashMap);
          if (localObject3 != null)
          {
            ((FXRate)localObject3).set((FXRate)localObject5);
            localObject1 = localObject3;
          }
          else
          {
            localObject1 = localObject5;
          }
        }
        else if (paramInt2 == 2)
        {
          localObject2 = this._adapter.getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
          if (localObject2 != null) {
            return localObject2;
          }
          localObject3 = (String)paramHashMap.get("AFFILIATE_BANK_ID");
          if (localObject3 == null)
          {
            localObject4 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
            ((EntitlementGroup)localObject4).setGroupPropertiesKey("AffiliateBankId");
            localObject3 = ((EntitlementGroup)localObject4).getGroupPropertiesValue();
            if (localObject3 == null)
            {
              String str = (String)paramHashMap.get("BUSINESS_RATE");
              if (str != null)
              {
                localObject3 = Integer.toString(this._adapter.getBankForBusiness(Integer.parseInt(str)));
              }
              else
              {
                localObject5 = (String)paramHashMap.get("CONSUMER_RATE");
                if (localObject5 != null) {
                  localObject3 = Integer.toString(this._adapter.getBankForCustomer(Integer.parseInt((String)localObject5)));
                }
              }
            }
          }
          if (localObject3 != null)
          {
            localObject4 = getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 1, (String)localObject3, paramHashMap);
            localObject1 = a((FXRate)localObject2, (FXRate)localObject4);
          }
          else
          {
            localObject4 = getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
            localObject1 = a((FXRate)localObject2, (FXRate)localObject4);
          }
        }
        else if (paramInt2 == 1)
        {
          localObject2 = this._adapter.getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
          localObject3 = getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
          localObject1 = a((FXRate)localObject2, (FXRate)localObject3);
        }
        else if (paramInt2 == 0)
        {
          localObject1 = this._adapter.getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
        }
      }
    }
    catch (FXException localFXException)
    {
      throw localFXException;
    }
    catch (Exception localException)
    {
      Object localObject3 = new FXException(34031, localException);
      DebugLog.throwing("FXService.geFXRate", localException);
      throw ((Throwable)localObject3);
    }
    return localObject1;
  }
  
  public FXRate getFXRate(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    FXRate localFXRate = null;
    try
    {
      Object localObject1;
      if (paramInt2 == 4)
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setMemberType("USER");
        ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(1));
        ((EntitlementGroupMember)localObject1).setId(paramString3);
        localObject1 = this.eadapter.getMember((EntitlementGroupMember)localObject1);
        localObject2 = this.eadapter.getEntitlementGroup(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        if ("User".equalsIgnoreCase(((EntitlementGroup)localObject2).getEntGroupType()))
        {
          EntitlementGroup localEntitlementGroup1 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          paramHashMap.put("CONSUMER_RATE", paramString3);
          localFXRate = getFXRate(paramInt1, paramString1, paramString2, 2, Integer.toString(localEntitlementGroup1.getParentId()), paramHashMap);
        }
        else
        {
          int i = 0;
          if ("Division".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getParentId();
          }
          else if ("Group".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            EntitlementGroup localEntitlementGroup2 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
            i = localEntitlementGroup2.getParentId();
          }
          else if ("Business".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getGroupId();
          }
          localFXRate = getFXRate(paramInt1, paramString1, paramString2, 3, Integer.toString(i), paramHashMap);
        }
      }
      else
      {
        Object localObject3;
        Object localObject4;
        if (paramInt2 == 3)
        {
          localObject1 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
          localObject2 = this._adapter.getFXRate(paramInt1, paramString1, paramString2, 3, Integer.toString(((EntitlementGroup)localObject1).getGroupId()), paramHashMap);
          paramHashMap.put("BUSINESS_RATE", Integer.toString(((EntitlementGroup)localObject1).getGroupId()));
          localObject3 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject1).getParentId());
          int j = ((EntitlementGroup)localObject3).getParentId();
          localObject4 = getFXRate(paramInt1, paramString1, paramString2, 2, Integer.toString(j), paramHashMap);
          localFXRate = a((FXRate)localObject2, (FXRate)localObject4);
        }
        else if (paramInt2 == 2)
        {
          localObject1 = this._adapter.getFXRate(paramInt1, paramString1, paramString2, paramInt2, paramString3, paramHashMap);
          if (localObject1 != null) {
            return localObject1;
          }
          localObject2 = (String)paramHashMap.get("AFFILIATE_BANK_ID");
          if (localObject2 == null)
          {
            localObject3 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
            ((EntitlementGroup)localObject3).setGroupPropertiesKey("AffiliateBankId");
            localObject2 = ((EntitlementGroup)localObject3).getGroupPropertiesValue();
            if (localObject2 == null)
            {
              String str = (String)paramHashMap.get("BUSINESS_RATE");
              if (str != null)
              {
                localObject2 = Integer.toString(this._adapter.getBankForBusiness(Integer.parseInt(str)));
              }
              else
              {
                localObject4 = (String)paramHashMap.get("CONSUMER_RATE");
                if (localObject4 != null) {
                  localObject2 = Integer.toString(this._adapter.getBankForCustomer(Integer.parseInt((String)localObject4)));
                }
              }
            }
          }
          if (localObject2 != null)
          {
            localObject3 = getFXRate(paramInt1, paramString1, paramString2, 1, (String)localObject2, paramHashMap);
            localFXRate = a((FXRate)localObject1, (FXRate)localObject3);
          }
          else
          {
            localObject3 = getFXRate(paramInt1, paramString1, paramString2, 0, "0", paramHashMap);
            localFXRate = a((FXRate)localObject1, (FXRate)localObject3);
          }
        }
        else if (paramInt2 == 1)
        {
          localObject1 = this._adapter.getFXRate(paramInt1, paramString1, paramString2, paramInt2, paramString3, paramHashMap);
          localObject2 = getFXRate(paramInt1, paramString1, paramString2, 0, "0", paramHashMap);
          localFXRate = a((FXRate)localObject1, (FXRate)localObject2);
        }
        else if (paramInt2 == 0)
        {
          localFXRate = this._adapter.getFXRate(paramInt1, paramString1, paramString2, 0, "0", paramHashMap);
        }
      }
    }
    catch (FXException localFXException)
    {
      throw localFXException;
    }
    catch (Exception localException)
    {
      Object localObject2 = new FXException(34030, localException);
      DebugLog.throwing("FXService.geFXRate", localException);
      throw ((Throwable)localObject2);
    }
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    FXRate localFXRate = null;
    try
    {
      Object localObject1;
      if (paramInt2 == 4)
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setMemberType("USER");
        ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(1));
        ((EntitlementGroupMember)localObject1).setId(paramString3);
        localObject1 = this.eadapter.getMember((EntitlementGroupMember)localObject1);
        localObject2 = this.eadapter.getEntitlementGroup(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        if ("User".equalsIgnoreCase(((EntitlementGroup)localObject2).getEntGroupType()))
        {
          EntitlementGroup localEntitlementGroup1 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          paramHashMap.put("CONSUMER_RATE", paramString3);
          localFXRate = getFXRate(paramInt1, paramString1, paramString2, paramDateTime, 2, Integer.toString(localEntitlementGroup1.getParentId()), paramHashMap);
        }
        else
        {
          int i = 0;
          if ("Division".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getParentId();
          }
          else if ("Group".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            EntitlementGroup localEntitlementGroup2 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
            i = localEntitlementGroup2.getParentId();
          }
          else if ("Business".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getGroupId();
          }
          localFXRate = getFXRate(paramInt1, paramString1, paramString2, paramDateTime, 3, Integer.toString(i), paramHashMap);
        }
      }
      else
      {
        Object localObject3;
        Object localObject4;
        if (paramInt2 == 3)
        {
          localObject1 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
          localObject2 = this._adapter.getFXRate(paramInt1, paramString1, paramString2, paramDateTime, 3, Integer.toString(((EntitlementGroup)localObject1).getGroupId()), paramHashMap);
          paramHashMap.put("BUSINESS_RATE", Integer.toString(((EntitlementGroup)localObject1).getGroupId()));
          localObject3 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject1).getParentId());
          int j = ((EntitlementGroup)localObject3).getParentId();
          localObject4 = getFXRate(paramInt1, paramString1, paramString2, paramDateTime, 2, Integer.toString(j), paramHashMap);
          localFXRate = a((FXRate)localObject2, (FXRate)localObject4);
        }
        else if (paramInt2 == 2)
        {
          localObject1 = this._adapter.getFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
          if (localObject1 != null) {
            return localObject1;
          }
          localObject2 = (String)paramHashMap.get("AFFILIATE_BANK_ID");
          if (localObject2 == null)
          {
            localObject3 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString3));
            ((EntitlementGroup)localObject3).setGroupPropertiesKey("AffiliateBankId");
            localObject2 = ((EntitlementGroup)localObject3).getGroupPropertiesValue();
            if (localObject2 == null)
            {
              String str = (String)paramHashMap.get("BUSINESS_RATE");
              if (str != null)
              {
                localObject2 = Integer.toString(this._adapter.getBankForBusiness(Integer.parseInt(str)));
              }
              else
              {
                localObject4 = (String)paramHashMap.get("CONSUMER_RATE");
                if (localObject4 != null) {
                  localObject2 = Integer.toString(this._adapter.getBankForCustomer(Integer.parseInt((String)localObject4)));
                }
              }
            }
          }
          if (localObject2 != null)
          {
            localObject3 = getFXRate(paramInt1, paramString1, paramString2, paramDateTime, 1, (String)localObject2, paramHashMap);
            localFXRate = a((FXRate)localObject1, (FXRate)localObject3);
          }
          else
          {
            localObject3 = getFXRate(paramInt1, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
            localFXRate = a((FXRate)localObject1, (FXRate)localObject3);
          }
        }
        else if (paramInt2 == 1)
        {
          localObject1 = this._adapter.getFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
          localObject2 = getFXRate(paramInt1, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
          localFXRate = a((FXRate)localObject1, (FXRate)localObject2);
        }
        else if (paramInt2 == 0)
        {
          localFXRate = this._adapter.getFXRate(paramInt1, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
        }
      }
    }
    catch (FXException localFXException)
    {
      throw localFXException;
    }
    catch (Exception localException)
    {
      Object localObject2 = new FXException(34031, localException);
      DebugLog.throwing("FXService.geFXRate", localException);
      throw ((Throwable)localObject2);
    }
    return localFXRate;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = null;
    localFXRateSheet = this._adapter.getFXRateSheet(paramInt, paramString, paramHashMap);
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = null;
    try
    {
      Object localObject1;
      if (paramInt2 == 4)
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setMemberType("USER");
        ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(1));
        ((EntitlementGroupMember)localObject1).setId(paramString2);
        localObject1 = this.eadapter.getMember((EntitlementGroupMember)localObject1);
        localObject2 = this.eadapter.getEntitlementGroup(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        if ("User".equalsIgnoreCase(((EntitlementGroup)localObject2).getEntGroupType()))
        {
          EntitlementGroup localEntitlementGroup1 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          paramHashMap.put("CONSUMER_RATE", paramString2);
          localFXRateSheet = getFXRateSheet(paramInt1, paramString1, 2, Integer.toString(localEntitlementGroup1.getParentId()), paramHashMap);
        }
        else
        {
          int i = 0;
          if ("Division".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getParentId();
          }
          else if ("Group".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            EntitlementGroup localEntitlementGroup2 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
            i = localEntitlementGroup2.getParentId();
          }
          else if ("Business".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getGroupId();
          }
          localFXRateSheet = getFXRateSheet(paramInt1, paramString1, 3, Integer.toString(i), paramHashMap);
        }
      }
      else
      {
        Object localObject3;
        Object localObject4;
        if (paramInt2 == 3)
        {
          localObject1 = this._adapter.getFXRateSheet(paramInt1, paramString1, 3, paramString2, paramHashMap);
          paramHashMap.put("BUSINESS_RATE", paramString2);
          localObject2 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString2));
          localObject3 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          int j = ((EntitlementGroup)localObject3).getParentId();
          localObject4 = getFXRateSheet(paramInt1, paramString1, 2, Integer.toString(j), paramHashMap);
          localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject4, true);
        }
        else if (paramInt2 == 2)
        {
          localObject1 = this._adapter.getFXRateSheet(paramInt1, paramString1, paramInt2, paramString2, paramHashMap);
          localObject2 = (String)paramHashMap.get("AFFILIATE_BANK_ID");
          if (localObject2 == null)
          {
            localObject3 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString2));
            ((EntitlementGroup)localObject3).setGroupPropertiesKey("AffiliateBankId");
            localObject2 = ((EntitlementGroup)localObject3).getGroupPropertiesValue();
            if (localObject2 == null)
            {
              String str = (String)paramHashMap.get("BUSINESS_RATE");
              if (str != null)
              {
                localObject2 = Integer.toString(this._adapter.getBankForBusiness(Integer.parseInt(str)));
              }
              else
              {
                localObject4 = (String)paramHashMap.get("CONSUMER_RATE");
                if (localObject4 != null) {
                  localObject2 = Integer.toString(this._adapter.getBankForCustomer(Integer.parseInt((String)localObject4)));
                }
              }
            }
          }
          if (localObject2 != null)
          {
            localObject3 = getFXRateSheet(paramInt1, paramString1, 1, (String)localObject2, paramHashMap);
            localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject3, true);
          }
          else
          {
            localObject3 = getFXRateSheet(paramInt1, paramString1, 0, "0", paramHashMap);
            localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject3, true);
          }
        }
        else if (paramInt2 == 1)
        {
          localObject1 = this._adapter.getFXRateSheet(paramInt1, paramString1, paramInt2, paramString2, paramHashMap);
          localObject2 = getFXRateSheet(paramInt1, paramString1, 0, "0", paramHashMap);
          localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject2, true);
        }
        else if (paramInt2 == 0)
        {
          localFXRateSheet = this._adapter.getFXRateSheet(paramInt1, paramString1, 0, "0", paramHashMap);
        }
      }
    }
    catch (FXException localFXException)
    {
      throw localFXException;
    }
    catch (Exception localException)
    {
      Object localObject2 = new FXException(34032, localException);
      DebugLog.throwing("FXService.geFXRate", localException);
      throw ((Throwable)localObject2);
    }
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt1, String paramString1, DateTime paramDateTime, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = null;
    try
    {
      Object localObject1;
      if (paramInt2 == 4)
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setMemberType("USER");
        ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(1));
        ((EntitlementGroupMember)localObject1).setId(paramString2);
        localObject1 = this.eadapter.getMember((EntitlementGroupMember)localObject1);
        localObject2 = this.eadapter.getEntitlementGroup(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        if ("User".equalsIgnoreCase(((EntitlementGroup)localObject2).getEntGroupType()))
        {
          EntitlementGroup localEntitlementGroup1 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          paramHashMap.put("CONSUMER_RATE", paramString2);
          localFXRateSheet = getFXRateSheet(paramInt1, paramString1, paramDateTime, 2, Integer.toString(localEntitlementGroup1.getParentId()), paramHashMap);
        }
        else
        {
          int i = 0;
          if ("Division".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getParentId();
          }
          else if ("Group".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            EntitlementGroup localEntitlementGroup2 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
            i = localEntitlementGroup2.getParentId();
          }
          else if ("Business".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getGroupId();
          }
          localFXRateSheet = getFXRateSheet(paramInt1, paramString1, paramDateTime, 3, Integer.toString(i), paramHashMap);
        }
      }
      else
      {
        Object localObject3;
        Object localObject4;
        if (paramInt2 == 3)
        {
          localObject1 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString2));
          localObject2 = this._adapter.getFXRateSheet(paramInt1, paramString1, paramDateTime, 3, Integer.toString(((EntitlementGroup)localObject1).getGroupId()), paramHashMap);
          paramHashMap.put("BUSINESS_RATE", Integer.toString(((EntitlementGroup)localObject1).getGroupId()));
          localObject3 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject1).getParentId());
          int j = ((EntitlementGroup)localObject3).getParentId();
          localObject4 = getFXRateSheet(paramInt1, paramString1, paramDateTime, 2, Integer.toString(j), paramHashMap);
          localFXRateSheet = a((FXRateSheet)localObject2, (FXRateSheet)localObject4, true);
        }
        else if (paramInt2 == 2)
        {
          localObject1 = this._adapter.getFXRateSheet(paramInt1, paramString1, paramDateTime, paramInt2, paramString2, paramHashMap);
          localObject2 = (String)paramHashMap.get("AFFILIATE_BANK_ID");
          if (localObject2 == null)
          {
            localObject3 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString2));
            ((EntitlementGroup)localObject3).setGroupPropertiesKey("AffiliateBankId");
            localObject2 = ((EntitlementGroup)localObject3).getGroupPropertiesValue();
            if (localObject2 == null)
            {
              String str = (String)paramHashMap.get("BUSINESS_RATE");
              if (str != null)
              {
                localObject2 = Integer.toString(this._adapter.getBankForBusiness(Integer.parseInt(str)));
              }
              else
              {
                localObject4 = (String)paramHashMap.get("CONSUMER_RATE");
                if (localObject4 != null) {
                  localObject2 = Integer.toString(this._adapter.getBankForCustomer(Integer.parseInt((String)localObject4)));
                }
              }
            }
          }
          if (localObject2 != null)
          {
            localObject3 = getFXRateSheet(paramInt1, paramString1, paramDateTime, 1, (String)localObject2, paramHashMap);
            localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject3, true);
          }
          else
          {
            localObject3 = getFXRateSheet(paramInt1, paramString1, paramDateTime, 0, "0", paramHashMap);
            localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject3, true);
          }
        }
        else if (paramInt2 == 1)
        {
          localObject1 = this._adapter.getFXRateSheet(paramInt1, paramString1, paramDateTime, paramInt2, paramString2, paramHashMap);
          localObject2 = getFXRateSheet(paramInt1, paramString1, paramDateTime, 0, "0", paramHashMap);
          localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject2, true);
        }
        else if (paramInt2 == 0)
        {
          localFXRateSheet = this._adapter.getFXRateSheet(paramInt1, paramString1, paramDateTime, 0, "0", paramHashMap);
        }
      }
    }
    catch (FXException localFXException)
    {
      throw localFXException;
    }
    catch (Exception localException)
    {
      Object localObject2 = new FXException(34034, localException);
      DebugLog.throwing("FXService.geFXRate", localException);
      throw ((Throwable)localObject2);
    }
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheetForTarget(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = null;
    try
    {
      Object localObject1;
      if (paramInt2 == 4)
      {
        localObject1 = new EntitlementGroupMember();
        ((EntitlementGroupMember)localObject1).setMemberType("USER");
        ((EntitlementGroupMember)localObject1).setMemberSubType(Integer.toString(1));
        ((EntitlementGroupMember)localObject1).setId(paramString2);
        localObject1 = this.eadapter.getMember((EntitlementGroupMember)localObject1);
        localObject2 = this.eadapter.getEntitlementGroup(((EntitlementGroupMember)localObject1).getEntitlementGroupId());
        if ("User".equalsIgnoreCase(((EntitlementGroup)localObject2).getEntGroupType()))
        {
          EntitlementGroup localEntitlementGroup1 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
          paramHashMap.put("CONSUMER_RATE", paramString2);
          localFXRateSheet = getFXRateSheetForTarget(paramInt1, paramString1, 2, Integer.toString(localEntitlementGroup1.getParentId()), paramHashMap);
        }
        else
        {
          int i = 0;
          if ("Division".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getParentId();
          }
          else if ("Group".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            EntitlementGroup localEntitlementGroup2 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject2).getParentId());
            i = localEntitlementGroup2.getParentId();
          }
          else if ("Business".equals(((EntitlementGroup)localObject2).getEntGroupType()))
          {
            i = ((EntitlementGroup)localObject2).getGroupId();
          }
          localFXRateSheet = getFXRateSheetForTarget(paramInt1, paramString1, 3, Integer.toString(i), paramHashMap);
        }
      }
      else
      {
        Object localObject3;
        Object localObject4;
        if (paramInt2 == 3)
        {
          localObject1 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString2));
          localObject2 = this._adapter.getFXRateSheetForTarget(paramInt1, paramString1, 3, Integer.toString(((EntitlementGroup)localObject1).getGroupId()), paramHashMap);
          paramHashMap.put("BUSINESS_RATE", Integer.toString(((EntitlementGroup)localObject1).getGroupId()));
          localObject3 = this.eadapter.getEntitlementGroup(((EntitlementGroup)localObject1).getParentId());
          int j = ((EntitlementGroup)localObject3).getParentId();
          localObject4 = getFXRateSheetForTarget(paramInt1, paramString1, 2, Integer.toString(j), paramHashMap);
          localFXRateSheet = a((FXRateSheet)localObject2, (FXRateSheet)localObject4, false);
        }
        else if (paramInt2 == 2)
        {
          localObject1 = this._adapter.getFXRateSheetForTarget(paramInt1, paramString1, paramInt2, paramString2, paramHashMap);
          localObject2 = (String)paramHashMap.get("AFFILIATE_BANK_ID");
          if (localObject2 == null)
          {
            localObject3 = this.eadapter.getEntitlementGroup(Integer.parseInt(paramString2));
            ((EntitlementGroup)localObject3).setGroupPropertiesKey("AffiliateBankId");
            localObject2 = ((EntitlementGroup)localObject3).getGroupPropertiesValue();
            if (localObject2 == null)
            {
              String str = (String)paramHashMap.get("BUSINESS_RATE");
              if (str != null)
              {
                localObject2 = Integer.toString(this._adapter.getBankForBusiness(Integer.parseInt(str)));
              }
              else
              {
                localObject4 = (String)paramHashMap.get("CONSUMER_RATE");
                if (localObject4 != null) {
                  localObject2 = Integer.toString(this._adapter.getBankForCustomer(Integer.parseInt((String)localObject4)));
                }
              }
            }
          }
          if (localObject2 != null)
          {
            localObject3 = getFXRateSheetForTarget(paramInt1, paramString1, 1, (String)localObject2, paramHashMap);
            localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject3, false);
          }
          else
          {
            localObject3 = getFXRateSheetForTarget(paramInt1, paramString1, 0, "0", paramHashMap);
            localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject3, false);
          }
        }
        else if (paramInt2 == 1)
        {
          localObject1 = this._adapter.getFXRateSheetForTarget(paramInt1, paramString1, paramInt2, paramString2, paramHashMap);
          localObject2 = getFXRateSheetForTarget(paramInt1, paramString1, 0, "0", paramHashMap);
          localFXRateSheet = a((FXRateSheet)localObject1, (FXRateSheet)localObject2, false);
        }
        else if (paramInt2 == 0)
        {
          localFXRateSheet = this._adapter.getFXRateSheetForTarget(paramInt1, paramString1, 0, "0", paramHashMap);
        }
      }
    }
    catch (FXException localFXException)
    {
      throw localFXException;
    }
    catch (Exception localException)
    {
      Object localObject2 = new FXException(34033, localException);
      DebugLog.throwing("FXService.geFXRate", localException);
      throw ((Throwable)localObject2);
    }
    return localFXRateSheet;
  }
  
  public FXCurrencies getTargetCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencies localFXCurrencies = null;
    localFXCurrencies = this._adapter.getTargetCurrencies(paramInt, paramHashMap);
    return localFXCurrencies;
  }
  
  public void initialize(HashMap paramHashMap)
    throws FXException
  {
    try
    {
      this.eadapter = ((EntitlementCachedAdapter)paramHashMap.get("ENTITLEMENT_CACHED_ADAPTER"));
      if (this.eadapter == null) {
        throw new FXException(34028, "Could not initialize fx service because settings for entitlement cached adapter is missing.");
      }
      this._adapter = ((FXAdapter)paramHashMap.get("FX_ADAPTER"));
    }
    catch (Exception localException)
    {
      FXException localFXException = new FXException(34018, localException);
      DebugLog.throwing("FXService.initialize", localException);
      throw localFXException;
    }
  }
  
  public void shutdown()
    throws FXException
  {
    if (this._adapter != null)
    {
      this._adapter.shutdown();
      this._adapter = null;
    }
  }
  
  private FXRateSheet a(FXRateSheet paramFXRateSheet1, FXRateSheet paramFXRateSheet2, boolean paramBoolean)
  {
    if ((paramFXRateSheet1 == null) || (paramFXRateSheet1.getRates() == null)) {
      return paramFXRateSheet2;
    }
    if ((paramFXRateSheet2 == null) || (paramFXRateSheet2.getRates() == null)) {
      return paramFXRateSheet1;
    }
    FXRates localFXRates1 = paramFXRateSheet1.getRates();
    FXRates localFXRates2 = paramFXRateSheet2.getRates();
    Iterator localIterator = localFXRates2.iterator();
    FXRates localFXRates3 = null;
    localFXRates3 = new FXRates(localFXRates2.getLocale());
    FXRate localFXRate1;
    int i;
    int j;
    FXRate localFXRate2;
    Currency localCurrency1;
    Currency localCurrency2;
    Currency localCurrency3;
    Currency localCurrency4;
    if (paramBoolean)
    {
      while (localIterator.hasNext())
      {
        localFXRate1 = (FXRate)localIterator.next();
        i = 0;
        for (j = 0; j < localFXRates1.size(); j++)
        {
          localFXRate2 = (FXRate)localFXRates1.get(j);
          if (localFXRate2.getTargetCurrencyCode().equals(localFXRate1.getTargetCurrencyCode()))
          {
            localCurrency1 = localFXRate2.getBuyPrice();
            localCurrency2 = localFXRate1.getBuyPrice();
            if (localCurrency1 == null) {
              localFXRate2.setBuyPrice(localCurrency2);
            }
            localCurrency3 = localFXRate2.getBuyPrice();
            localCurrency4 = localFXRate1.getBuyPrice();
            if (localCurrency3 == null) {
              localFXRate2.setSellPrice(localCurrency4);
            }
            i = 1;
          }
        }
        if (i == 0) {
          localFXRates3.add(localFXRate1);
        }
      }
      localFXRates1.addAll(localFXRates3);
    }
    else
    {
      while (localIterator.hasNext())
      {
        localFXRate1 = (FXRate)localIterator.next();
        i = 0;
        for (j = 0; j < localFXRates1.size(); j++)
        {
          localFXRate2 = (FXRate)localFXRates1.get(j);
          localCurrency1 = localFXRate2.getBuyPrice();
          localCurrency2 = localFXRate1.getBuyPrice();
          localCurrency3 = localFXRate2.getBuyPrice();
          localCurrency4 = localFXRate1.getBuyPrice();
          if ((localCurrency1 != null) && (localCurrency2 != null) && (localCurrency1.getCurrencyCode().equals(localCurrency2.getCurrencyCode())))
          {
            if (localCurrency3 == null) {
              localFXRate2.setSellPrice(localCurrency4);
            }
            i = 1;
          }
          if ((localCurrency3 != null) && (localCurrency4 != null) && (localCurrency3.getCurrencyCode().equals(localCurrency4.getCurrencyCode())))
          {
            if (localCurrency1 == null) {
              localFXRate2.setBuyPrice(localCurrency2);
            }
            i = 1;
          }
        }
        if (i == 0) {
          localFXRates3.add(localFXRate1);
        }
      }
      if (localFXRates3.size() > 0) {
        localFXRates1.addAll(localFXRates3);
      }
    }
    paramFXRateSheet1.setRates(localFXRates1);
    return paramFXRateSheet1;
  }
  
  private FXRate a(FXRate paramFXRate1, FXRate paramFXRate2)
  {
    if (paramFXRate1 == null) {
      return paramFXRate2;
    }
    if (paramFXRate2 == null) {
      return paramFXRate1;
    }
    Currency localCurrency1 = paramFXRate1.getBuyPrice();
    Currency localCurrency2 = paramFXRate2.getBuyPrice();
    Currency localCurrency3 = paramFXRate1.getBuyPrice();
    Currency localCurrency4 = paramFXRate2.getBuyPrice();
    if ((localCurrency1 != null) && (localCurrency2 != null) && (localCurrency1.getCurrencyCode().equals(localCurrency2.getCurrencyCode())) && (localCurrency3 == null)) {
      paramFXRate1.setSellPrice(localCurrency4);
    }
    if ((localCurrency3 != null) && (localCurrency4 != null) && (localCurrency3.getCurrencyCode().equals(localCurrency4.getCurrencyCode())) && (localCurrency1 == null)) {
      paramFXRate1.setBuyPrice(localCurrency2);
    }
    return paramFXRate1;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.services.fx.FXCommonStoredService
 * JD-Core Version:    0.7.0.1
 */