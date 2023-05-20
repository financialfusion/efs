package com.ffusion.services.fx;

import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.fx.FXException;
import com.ffusion.fx.adapter.FXAdapter;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;

public class FXService
  implements IForeignExchangeService
{
  public static final int REAL_TIME = 1;
  public static final int REAL_TIME_THEN_STORED = 2;
  public static final String FX_INIT_XML_FILE = "foreignexchange.xml";
  public static final int STORED = 3;
  protected final String RATE_SOURCE = "RATE_SOURCE";
  protected final String RATE_SOURCE_STORED = "STORED";
  protected final String RATE_SOURCE_REAL_TIME = "REALTIME";
  protected final String RATE_SOURCE_REAL_TIME_THEN_STORED = "RealTimeThenStored";
  protected final String JAVA_CLASS = "JAVA_CLASS";
  protected final String INIT_URL = "INIT_URL";
  protected final String FX_INIT_XML = "FX_INIT_XML";
  private int a = 3;
  private IForeignExchangeService jdField_int;
  private IForeignExchangeService jdField_for;
  private FXAdapter jdField_if = null;
  private EntitlementCachedAdapter jdField_do = null;
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    if (this.a == 3)
    {
      this.jdField_for.cleanup(paramInt, paramHashMap);
    }
    else if (this.a == 1)
    {
      this.jdField_int.cleanup(paramInt, paramHashMap);
    }
    else if (this.a == 2)
    {
      int i = 0;
      try
      {
        this.jdField_int.cleanup(paramInt, paramHashMap);
      }
      catch (Exception localException)
      {
        i = 1;
      }
      if (i != 0) {
        this.jdField_for.cleanup(paramInt, paramHashMap);
      }
    }
  }
  
  public FXCurrencies getBaseCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencies localFXCurrencies = null;
    if (this.a == 3)
    {
      localFXCurrencies = this.jdField_for.getBaseCurrencies(paramInt, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXCurrencies = this.jdField_int.getBaseCurrencies(paramInt, paramHashMap);
    }
    else if (this.a == 2)
    {
      int i = 0;
      try
      {
        localFXCurrencies = this.jdField_int.getBaseCurrencies(paramInt, paramHashMap);
      }
      catch (Exception localException)
      {
        i = 1;
      }
      if ((i != 0) || (localFXCurrencies == null)) {
        localFXCurrencies = this.jdField_for.getBaseCurrencies(paramInt, paramHashMap);
      }
    }
    return localFXCurrencies;
  }
  
  public FXCurrencies getBaseCurrenciesGivenTarget(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencies localFXCurrencies = null;
    if (this.a == 3)
    {
      localFXCurrencies = this.jdField_for.getBaseCurrenciesGivenTarget(paramInt, paramString, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXCurrencies = this.jdField_int.getBaseCurrenciesGivenTarget(paramInt, paramString, paramHashMap);
    }
    else if (this.a == 2)
    {
      int i = 0;
      try
      {
        localFXCurrencies = this.jdField_int.getBaseCurrenciesGivenTarget(paramInt, paramString, paramHashMap);
      }
      catch (Exception localException)
      {
        i = 1;
      }
      if ((i != 0) || (localFXCurrencies == null)) {
        localFXCurrencies = this.jdField_for.getBaseCurrenciesGivenTarget(paramInt, paramString, paramHashMap);
      }
    }
    return localFXCurrencies;
  }
  
  public FXRate getClosestFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    String str = paramString3;
    if (paramInt2 == 4)
    {
      int i = this.jdField_if.getPrimaryID(Integer.parseInt(paramString3));
      if (i != 0) {
        str = Integer.toString(i);
      }
    }
    FXRate localFXRate = null;
    if (this.a == 3)
    {
      localFXRate = this.jdField_for.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRate = this.jdField_int.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 2)
    {
      int j = 0;
      try
      {
        localFXRate = this.jdField_int.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
      }
      catch (Exception localException)
      {
        j = 1;
      }
      if ((j != 0) || (localFXRate == null)) {
        localFXRate = this.jdField_for.getClosestFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
      }
    }
    return localFXRate;
  }
  
  public FXRate getClosestFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    String str = paramString3;
    if (paramInt2 == 4)
    {
      int i = this.jdField_if.getPrimaryID(Integer.parseInt(paramString3));
      if (i != 0) {
        str = Integer.toString(i);
      }
    }
    FXRate localFXRate = null;
    if (this.a == 3)
    {
      localFXRate = this.jdField_for.getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRate = this.jdField_int.getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 2)
    {
      int j = 0;
      try
      {
        localFXRate = this.jdField_int.getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
      }
      catch (Exception localException)
      {
        j = 1;
      }
      if ((j != 0) || (localFXRate == null)) {
        localFXRate = this.jdField_for.getClosestFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
      }
    }
    return localFXRate;
  }
  
  public FXCurrencies getCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencies localFXCurrencies = null;
    if (this.a == 3)
    {
      localFXCurrencies = this.jdField_for.getCurrencies(paramInt, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXCurrencies = this.jdField_int.getCurrencies(paramInt, paramHashMap);
    }
    else if (this.a == 2)
    {
      int i = 0;
      try
      {
        localFXCurrencies = this.jdField_int.getCurrencies(paramInt, paramHashMap);
      }
      catch (Exception localException)
      {
        i = 1;
      }
      if ((i != 0) || (localFXCurrencies == null)) {
        localFXCurrencies = this.jdField_for.getCurrencies(paramInt, paramHashMap);
      }
    }
    return localFXCurrencies;
  }
  
  public FXRate getFXRate(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXRate localFXRate = null;
    if (this.a == 3)
    {
      localFXRate = this.jdField_for.getFXRate(paramInt, paramString1, paramString2, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRate = this.jdField_int.getFXRate(paramInt, paramString1, paramString2, paramHashMap);
    }
    else if (this.a == 2)
    {
      int i = 0;
      try
      {
        localFXRate = this.jdField_int.getFXRate(paramInt, paramString1, paramString2, paramHashMap);
      }
      catch (Exception localException)
      {
        i = 1;
      }
      if ((i != 0) || (localFXRate == null)) {
        localFXRate = this.jdField_for.getFXRate(paramInt, paramString1, paramString2, paramHashMap);
      }
    }
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt, String paramString1, String paramString2, DateTime paramDateTime, HashMap paramHashMap)
    throws FXException
  {
    FXRate localFXRate = null;
    if (this.a == 3)
    {
      localFXRate = this.jdField_for.getFXRate(paramInt, paramString1, paramString2, paramDateTime, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRate = this.jdField_int.getFXRate(paramInt, paramString1, paramString2, paramDateTime, paramHashMap);
    }
    else if (this.a == 2)
    {
      int i = 0;
      try
      {
        localFXRate = this.jdField_int.getFXRate(paramInt, paramString1, paramString2, paramDateTime, paramHashMap);
      }
      catch (Exception localException)
      {
        i = 1;
      }
      if ((i != 0) || (localFXRate == null)) {
        localFXRate = this.jdField_for.getFXRate(paramInt, paramString1, paramString2, paramDateTime, paramHashMap);
      }
    }
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    String str = paramString3;
    if (paramInt2 == 4)
    {
      int i = this.jdField_if.getPrimaryID(Integer.parseInt(paramString3));
      if (i != 0) {
        str = Integer.toString(i);
      }
    }
    FXRate localFXRate = null;
    if (this.a == 3)
    {
      localFXRate = this.jdField_for.getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRate = this.jdField_int.getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 2)
    {
      int j = 0;
      try
      {
        localFXRate = this.jdField_int.getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
      }
      catch (Exception localException)
      {
        j = 1;
      }
      if ((j != 0) || (localFXRate == null)) {
        localFXRate = this.jdField_for.getFXRate(paramInt1, paramConnection, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
      }
    }
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    String str = paramString3;
    if (paramInt2 == 4)
    {
      int i = this.jdField_if.getPrimaryID(Integer.parseInt(paramString3));
      if (i != 0) {
        str = Integer.toString(i);
      }
    }
    FXRate localFXRate = null;
    if (this.a == 3)
    {
      localFXRate = this.jdField_for.getFXRate(paramInt1, paramString1, paramString2, paramInt2, str, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRate = this.jdField_int.getFXRate(paramInt1, paramString1, paramString2, paramInt2, str, paramHashMap);
    }
    else if (this.a == 2)
    {
      int j = 0;
      try
      {
        localFXRate = this.jdField_int.getFXRate(paramInt1, paramString1, paramString2, paramInt2, paramString3, paramHashMap);
      }
      catch (Exception localException)
      {
        j = 1;
      }
      if ((j != 0) || (localFXRate == null)) {
        localFXRate = this.jdField_for.getFXRate(paramInt1, paramString1, paramString2, paramInt2, paramString3, paramHashMap);
      }
    }
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    String str = paramString3;
    if (paramInt2 == 4)
    {
      int i = this.jdField_if.getPrimaryID(Integer.parseInt(paramString3));
      if (i != 0) {
        str = Integer.toString(i);
      }
    }
    FXRate localFXRate = null;
    if (this.a == 3)
    {
      localFXRate = this.jdField_for.getFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRate = this.jdField_int.getFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 2)
    {
      int j = 0;
      try
      {
        localFXRate = this.jdField_int.getFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
      }
      catch (Exception localException)
      {
        j = 1;
      }
      if ((j != 0) || (localFXRate == null)) {
        localFXRate = this.jdField_for.getFXRate(paramInt1, paramString1, paramString2, paramDateTime, paramInt2, paramString3, paramHashMap);
      }
    }
    return localFXRate;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = null;
    if (this.a == 3)
    {
      localFXRateSheet = this.jdField_for.getFXRateSheet(paramInt, paramString, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRateSheet = this.jdField_int.getFXRateSheet(paramInt, paramString, paramHashMap);
    }
    else if (this.a == 2)
    {
      int i = 0;
      try
      {
        localFXRateSheet = this.jdField_int.getFXRateSheet(paramInt, paramString, paramHashMap);
      }
      catch (Exception localException)
      {
        i = 1;
      }
      if ((i != 0) || (localFXRateSheet == null)) {
        localFXRateSheet = this.jdField_for.getFXRateSheet(paramInt, paramString, paramHashMap);
      }
    }
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    String str = paramString2;
    if (paramInt2 == 4)
    {
      int i = this.jdField_if.getPrimaryID(Integer.parseInt(paramString2));
      if (i != 0) {
        str = Integer.toString(i);
      }
    }
    FXRateSheet localFXRateSheet = null;
    if (this.a == 3)
    {
      localFXRateSheet = this.jdField_for.getFXRateSheet(paramInt1, paramString1, paramInt2, str, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRateSheet = this.jdField_int.getFXRateSheet(paramInt1, paramString1, paramInt2, str, paramHashMap);
    }
    else if (this.a == 2)
    {
      int j = 0;
      try
      {
        localFXRateSheet = this.jdField_int.getFXRateSheet(paramInt1, paramString1, paramInt2, paramString2, paramHashMap);
      }
      catch (Exception localException)
      {
        j = 1;
      }
      if ((j != 0) || (localFXRateSheet == null)) {
        localFXRateSheet = this.jdField_for.getFXRateSheet(paramInt1, paramString1, paramInt2, paramString2, paramHashMap);
      }
    }
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt1, String paramString1, DateTime paramDateTime, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    String str = paramString2;
    if (paramInt2 == 4)
    {
      int i = this.jdField_if.getPrimaryID(Integer.parseInt(paramString2));
      if (i != 0) {
        str = Integer.toString(i);
      }
    }
    FXRateSheet localFXRateSheet = null;
    if (this.a == 3)
    {
      localFXRateSheet = this.jdField_for.getFXRateSheet(paramInt1, paramString1, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRateSheet = this.jdField_int.getFXRateSheet(paramInt1, paramString1, paramDateTime, paramInt2, str, paramHashMap);
    }
    else if (this.a == 2)
    {
      int j = 0;
      try
      {
        localFXRateSheet = this.jdField_int.getFXRateSheet(paramInt1, paramString1, paramDateTime, paramInt2, paramString2, paramHashMap);
      }
      catch (Exception localException)
      {
        j = 1;
      }
      if ((j != 0) || (localFXRateSheet == null)) {
        localFXRateSheet = this.jdField_for.getFXRateSheet(paramInt1, paramString1, paramDateTime, paramInt2, paramString2, paramHashMap);
      }
    }
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheetForTarget(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    String str = paramString2;
    if (paramInt2 == 4)
    {
      int i = this.jdField_if.getPrimaryID(Integer.parseInt(paramString2));
      if (i != 0) {
        str = Integer.toString(i);
      }
    }
    FXRateSheet localFXRateSheet = null;
    if (this.a == 3)
    {
      localFXRateSheet = this.jdField_for.getFXRateSheetForTarget(paramInt1, paramString1, paramInt2, str, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXRateSheet = this.jdField_int.getFXRateSheetForTarget(paramInt1, paramString1, paramInt2, str, paramHashMap);
    }
    else if (this.a == 2)
    {
      int j = 0;
      try
      {
        localFXRateSheet = this.jdField_int.getFXRateSheetForTarget(paramInt1, paramString1, paramInt2, paramString2, paramHashMap);
      }
      catch (Exception localException)
      {
        j = 1;
      }
      if ((j != 0) || (localFXRateSheet == null)) {
        localFXRateSheet = this.jdField_for.getFXRateSheetForTarget(paramInt1, paramString1, paramInt2, paramString2, paramHashMap);
      }
    }
    return localFXRateSheet;
  }
  
  public FXCurrencies getTargetCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    FXCurrencies localFXCurrencies = null;
    if (this.a == 3)
    {
      localFXCurrencies = this.jdField_for.getTargetCurrencies(paramInt, paramHashMap);
    }
    else if (this.a == 1)
    {
      localFXCurrencies = this.jdField_int.getTargetCurrencies(paramInt, paramHashMap);
    }
    else if (this.a == 2)
    {
      int i = 0;
      try
      {
        localFXCurrencies = this.jdField_int.getTargetCurrencies(paramInt, paramHashMap);
      }
      catch (Exception localException)
      {
        i = 1;
      }
      if ((i != 0) || (localFXCurrencies == null)) {
        localFXCurrencies = this.jdField_for.getTargetCurrencies(paramInt, paramHashMap);
      }
    }
    return localFXCurrencies;
  }
  
  public void initialize(HashMap paramHashMap)
    throws FXException
  {
    try
    {
      this.jdField_do = ((EntitlementCachedAdapter)paramHashMap.get("ENTITLEMENT_CACHED_ADAPTER"));
      if (this.jdField_do == null) {
        throw new FXException(34028, "Could not initialize fx service because settings for entitlement cached adapter is missing.");
      }
      InputStream localInputStream = null;
      localObject1 = null;
      localObject2 = (String)paramHashMap.get("FX_INIT_XML");
      if (localObject2 == null) {
        localObject2 = "foreignexchange.xml";
      }
      localInputStream = ResourceUtil.getResourceAsStream(new FXService(), (String)localObject2);
      localObject1 = Strings.streamToString(localInputStream, "UTF-8");
      XMLTag localXMLTag = new XMLTag(true);
      localXMLTag.build((String)localObject1);
      localObject1 = null;
      this.jdField_if = new FXAdapter();
      this.jdField_if.initialize(paramHashMap);
      paramHashMap.put("FX_ADAPTER", this.jdField_if);
      HashMap localHashMap1 = XMLUtil.tagToHashMap(localXMLTag);
      String str1 = (String)localHashMap1.get("RATE_SOURCE");
      if (str1 != null) {
        if (str1.equalsIgnoreCase("REALTIME")) {
          this.a = 1;
        } else if (str1.equalsIgnoreCase("RealTimeThenStored")) {
          this.a = 2;
        }
      }
      HashMap localHashMap2 = (HashMap)localHashMap1.get("STORED");
      if ((localHashMap2 == null) && ((this.a == 3) || (this.a == 2))) {
        throw new FXException(34021, "Could not initialize fx service because settings for stored service is missing.");
      }
      String str2 = (String)localHashMap2.get("JAVA_CLASS");
      if (str2 != null)
      {
        localObject3 = Class.forName(str2);
        this.jdField_for = ((IForeignExchangeService)((Class)localObject3).newInstance());
        this.jdField_for.initialize(paramHashMap);
      }
      Object localObject3 = (HashMap)localHashMap1.get("REALTIME");
      if ((localObject3 == null) && ((this.a == 1) || (this.a == 2))) {
        throw new FXException(34022, "Could not initialize fx service because settings for real time service is missing.");
      }
      String str3 = (String)((HashMap)localObject3).get("JAVA_CLASS");
      if (((this.a == 1) || (this.a == 2)) && (str3 != null))
      {
        Class localClass = Class.forName(str3);
        String str4 = (String)((HashMap)localObject3).get("INIT_URL");
        if ((str4 != null) && (str4.trim().length() > 0)) {
          paramHashMap.put("INIT_URL", str4);
        }
        this.jdField_int = ((IForeignExchangeService)localClass.newInstance());
        this.jdField_int.initialize(paramHashMap);
      }
    }
    catch (Exception localException)
    {
      Object localObject1 = new StringWriter();
      Object localObject2 = new PrintWriter((Writer)localObject1);
      ((PrintWriter)localObject2).print("****** Error initializing FX Service.  The product will not be functional:");
      localException.printStackTrace((PrintWriter)localObject2);
      DebugLog.log(Level.SEVERE, ((StringWriter)localObject1).toString());
    }
  }
  
  public void shutdown()
    throws FXException
  {
    if (this.jdField_for != null)
    {
      this.jdField_for.shutdown();
      this.jdField_for = null;
    }
    if (this.jdField_int != null)
    {
      this.jdField_int.shutdown();
      this.jdField_int = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.services.fx.FXService
 * JD-Core Version:    0.7.0.1
 */