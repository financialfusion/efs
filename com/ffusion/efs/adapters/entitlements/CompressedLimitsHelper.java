package com.ffusion.efs.adapters.entitlements;

import com.ffusion.beans.Currency;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.util.beans.DateTime;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

class CompressedLimitsHelper
{
  public HashMap _allOff;
  public HashMap[] _opOnly;
  public HashMap[] _objIds;
  private int a;
  private int jdField_for;
  private HashMap jdField_do;
  private HashMap jdField_new;
  public static final int IGNORE_LIMIT = 1;
  public static final int FIRST_LIMIT = 2;
  public static final int REPLACE_EXISTING_LIMIT = 3;
  private IForeignExchangeService jdField_int;
  private EntitlementCachedAdapter jdField_if;
  
  public CompressedLimitsHelper(IForeignExchangeService paramIForeignExchangeService, HashMap paramHashMap1, HashMap paramHashMap2, EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws EntitlementException
  {
    this.jdField_int = paramIForeignExchangeService;
    if (this.jdField_int == null) {
      throw new EntitlementException(36);
    }
    this._allOff = new HashMap();
    this.jdField_do = new HashMap(paramHashMap1);
    this.jdField_new = new HashMap(paramHashMap2);
    this.a = this.jdField_do.size();
    this.jdField_for = this.jdField_new.size();
    this.jdField_if = paramEntitlementCachedAdapter;
    this._opOnly = new HashMap[this.a];
    this._objIds = new HashMap[this.jdField_for + this.a * this.jdField_for];
  }
  
  public int getWhatToDo(Limit paramLimit, int paramInt, String paramString)
    throws EntitlementException
  {
    String str1 = paramLimit.getEntitlement().getOperationName();
    String str2 = paramLimit.getEntitlement().getObjectType();
    String str3 = paramLimit.getEntitlement().getObjectId();
    int i = paramLimit.getPeriod();
    char c = paramLimit.getRunningTotalType();
    boolean bool = paramLimit.isAllowedApproval();
    BigDecimal localBigDecimal = paramLimit.getAmount();
    String str4 = paramLimit.getCurrencyCode();
    CLHelperCacheKey localCLHelperCacheKey = new CLHelperCacheKey(str3, i, c, bool, str4, paramLimit.isCrossCurrency());
    CLAmount localCLAmount2 = new CLAmount(localBigDecimal, str4);
    int j;
    if ((str1 == null) && (str2 == null))
    {
      localCLAmount1 = (CLAmount)this._allOff.get(localCLHelperCacheKey);
      if (localCLAmount1 != null)
      {
        j = a(localCLAmount2, localCLAmount1, paramInt, paramString);
        if ((j == 3) || (j == 2)) {
          this._allOff.put(localCLHelperCacheKey, localCLAmount2);
        }
        return j;
      }
      this._allOff.put(localCLHelperCacheKey, localCLAmount2);
      return 2;
    }
    if (str2 == null)
    {
      if (this._opOnly[a(str1)] == null)
      {
        this._opOnly[a(str1)] = new HashMap();
        this._opOnly[a(str1)].put(localCLHelperCacheKey, localCLAmount2);
        return 2;
      }
      localCLAmount1 = (CLAmount)this._opOnly[a(str1)].get(localCLHelperCacheKey);
      if (localCLAmount1 != null)
      {
        j = a(localCLAmount2, localCLAmount1, paramInt, paramString);
        if ((j == 3) || (j == 2)) {
          this._opOnly[a(str1)].put(localCLHelperCacheKey, localCLAmount2);
        }
        return j;
      }
      this._opOnly[a(str1)].put(localCLHelperCacheKey, localCLAmount2);
      return 2;
    }
    if (str1 == null) {
      j = jdField_if(str2);
    } else {
      j = this.jdField_for + jdField_if(str2) * this.a + a(str1);
    }
    if (this._objIds[j] == null)
    {
      this._objIds[j] = new HashMap();
      this._objIds[j].put(localCLHelperCacheKey, localCLAmount2);
      return 2;
    }
    CLAmount localCLAmount1 = (CLAmount)this._objIds[j].get(localCLHelperCacheKey);
    if (localCLAmount1 != null)
    {
      int k = a(localCLAmount2, localCLAmount1, paramInt, paramString);
      if ((k == 3) || (k == 2)) {
        this._objIds[j].put(localCLHelperCacheKey, localCLAmount2);
      }
      return k;
    }
    this._objIds[j].put(localCLHelperCacheKey, localCLAmount2);
    return 2;
  }
  
  private int a(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 >= paramFloat2 ? 1 : 3;
  }
  
  private int a(CLAmount paramCLAmount1, CLAmount paramCLAmount2, int paramInt, String paramString)
    throws EntitlementException
  {
    if (paramCLAmount1.getCurrencyCode().equals(paramCLAmount2.getCurrencyCode())) {
      return paramCLAmount1.getAmount().compareTo(paramCLAmount2.getAmount()) >= 0 ? 1 : 3;
    }
    try
    {
      FXRate localFXRate = this.jdField_int.getClosestFXRate(Integer.parseInt(paramString), paramCLAmount2.getCurrencyCode(), paramCLAmount1.getCurrencyCode(), new DateTime(new Date(System.currentTimeMillis()), Locale.getDefault()), paramInt, paramString, new HashMap());
      BigDecimal localBigDecimal1 = localFXRate.getBuyPrice().getAmountValue();
      BigDecimal localBigDecimal2 = paramCLAmount2.getAmount().multiply(localBigDecimal1);
      return paramCLAmount1.getAmount().compareTo(localBigDecimal2) >= 0 ? 1 : 3;
    }
    catch (Exception localException) {}
    return 2;
  }
  
  private int a(String paramString)
  {
    Integer localInteger = (Integer)this.jdField_do.get(paramString);
    return localInteger == null ? -1 : localInteger.intValue();
  }
  
  private int jdField_if(String paramString)
  {
    Integer localInteger = (Integer)this.jdField_new.get(paramString);
    return localInteger == null ? -1 : localInteger.intValue();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.CompressedLimitsHelper
 * JD-Core Version:    0.7.0.1
 */