package com.ffusion.fx.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXCurrency;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.beans.fx.FXRates;
import com.ffusion.fx.FXException;
import com.ffusion.util.ReadWriteLock;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class FXAdapter
  implements IFXAdapter
{
  private String q;
  private Locale p;
  private a jdField_long = new a();
  private static final int d = 86400000;
  private static final String e = "DB_PROPERTIES";
  public static final String NO_CONNECTION_POOL = "NO_CONNECTION_POOL";
  private static final String jdField_byte = "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, ( SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.baseCurrencyCode = ? AND fb.targetCurrencyCode = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode) MostRecentDate WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND MostRecentDate.max_date = fa.asOfDate";
  private static final String jdField_null = "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, ( SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType,  fb.objectID, max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.baseCurrencyCode = ?  AND fb.targetCurrencyCode = ? AND fb.objectType = ?  AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID )  MostRecentDate  WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID";
  private static final String jdField_char = "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ?";
  private static final String jdField_new = "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?";
  private static final String m = "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa WHERE fa.baseCurrencyCode = ?  AND fa.targetCurrencyCode= ?  AND fa.objectType= ? AND fa.objectID = ? AND  fa.asOfDate = (SELECT MAX(fb.asOfDate) FROM FX_Rate fb WHERE  fb.baseCurrencyCode = ?  AND fb.targetCurrencyCode= ?  AND fb.objectType= ? AND fb.objectID = ? AND  fb.asOfDate <= ? ) ";
  private static final String s = "SELECT fa.targetCurrencyCode,  fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, (SELECT fb.baseCurrencyCode, fb.targetCurrencyCode,  max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.baseCurrencyCode = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode) MostRecentDate WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND MostRecentDate.max_date = fa.asOfDate";
  private static final String jdField_case = "SELECT fa .baseCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, ( SELECT fb.baseCurrencyCode,  fb.targetCurrencyCode, fb.objectType, fb.objectID, max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.targetCurrencyCode = ? AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID)  MostRecentDate WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ";
  private static final String f = "SELECT fa.targetCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, (SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID,  max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.baseCurrencyCode = ? AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID)  MostRecentDate WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ";
  private static final String jdField_if = "SELECT fa.targetCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, (SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.asOfDate, fb.objectType,  fb.objectID FROM FX_Rate fb WHERE  fb.asOfDate=? and fb.baseCurrencyCode = ?  AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.asOfDate,  fb.objectType, fb.objectID ) SheetView WHERE SheetView.baseCurrencyCode = fa.baseCurrencyCode AND SheetView.targetCurrencyCode = fa.targetCurrencyCode AND  SheetView.asOfDate = fa.asOfDate AND SheetView.objectType=fa.objectType AND SheetView.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ";
  private static final String jdField_int = "select currencyCode, description from FX_Currency";
  private static final String n = "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.baseCurrencyCode = cur.currencyCode";
  private static final String jdField_do = "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.targetCurrencyCode = ? AND rate.buyPrice IS NOT NULL AND rate.sellPrice IS NOT NULL AND rate.baseCurrencyCode = cur.currencyCode";
  private static final String c = "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.targetCurrencyCode = cur.currencyCode";
  private static final String b = "insert into FX_Currency( currencyCode, description ) values ( ?, ? )";
  private static final String r = "update FX_Currency set description = ? where currencyCode = ?";
  private static final String jdField_goto = "select count(*) from FX_Rate where baseCurrencyCode = ? OR targetCurrencyCode = ?";
  private static final String a = "delete from FX_Currency where currencyCode = ?";
  private static final String i = "insert into FX_Rate( baseCurrencyCode, targetCurrencyCode, buyPrice, sellPrice, asOfDate ) values ( ?, ?, ?, ?, ? )";
  private static final String j = "insert into FX_Rate( baseCurrencyCode, targetCurrencyCode, buyPrice, sellPrice, asOfDate, objectType, objectID ) values ( ?, ?, ?, ?, ?, ?, ? )";
  private static final String k = "update FX_Rate set buyPrice = ?, sellPrice = ? where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ?";
  private static final String l = "update FX_Rate set buyPrice = ?, sellPrice = ? where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?";
  private static final String g = "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ?";
  private static final String o = "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND objectType=? AND objectID=?";
  private static final String jdField_for = "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ?";
  private static final String v = "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?";
  private static final String jdField_void = "select distinct targetCurrencyCode from FX_Rate where baseCurrencyCode = ? order by targetCurrencyCode";
  private static final String jdField_else = "select distinct targetCurrencyCode from FX_Rate where baseCurrencyCode = ?  and objectType = ? and objectID = ? order by targetCurrencyCode";
  private static final String t = "delete from FX_Rate where asOfDate < ?";
  private static final String u = "delete from FX_Rate where asOfDate < ? and objectType=? AND objectID=?";
  private static final String w = "select b.affiliate_bank_id from business b,  entitlement_group eg where eg.name=b.business_name and eg.ent_group_id= ? ";
  private static final String jdField_try = "select c.affiliate_bank_id from customer c where directory_id =?";
  private static final String h = "select PRIMARY_USER_ID from CUSTOMER_REL where DIRECTORY_ID = ? ";
  
  public void initialize(HashMap paramHashMap)
    throws FXException
  {
    try
    {
      Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      this.p = Locale.getDefault();
      if (paramHashMap.get("NO_CONNECTION_POOL") == null) {
        try
        {
          this.q = ConnectionPool.init(localProperties);
        }
        catch (PoolException localPoolException)
        {
          throw new FXException(34007, "Unable to create a DB Connection pool during initialization.", localPoolException);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not initialize the foreign exchange module.", localThrowable);
      }
      throw new FXException("Could not initialize the foreign exchange module.", localThrowable);
    }
  }
  
  public FXRate getFXRate(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    return getFXRate(paramInt, paramString1, paramString2, 0, "0", paramHashMap);
  }
  
  public FXRate getFXRate(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform getFXRate() because the specified baseCurrencyCode string was null.");
    }
    if (paramString2 == null) {
      throw new FXException(34002, "Could not perform getFXRate() because the specified targetCurrencyCode string was null.");
    }
    if (paramInt2 < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString3 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    FXRate localFXRate = this.jdField_long.a(paramString1, paramString2, paramInt2, paramString3);
    if (localFXRate == null)
    {
      Connection localConnection = null;
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      try
      {
        localConnection = DBUtil.getConnection(this.q, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, ( SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType,  fb.objectID, max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.baseCurrencyCode = ?  AND fb.targetCurrencyCode = ? AND fb.objectType = ?  AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID )  MostRecentDate  WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localPreparedStatement.setInt(3, paramInt2);
        localPreparedStatement.setString(4, paramString3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, ( SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType,  fb.objectID, max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.baseCurrencyCode = ?  AND fb.targetCurrencyCode = ? AND fb.objectType = ?  AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID )  MostRecentDate  WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID");
        if (!localResultSet.next())
        {
          localObject1 = null;
          return localObject1;
        }
        localFXRate = new FXRate(this.p);
        localFXRate.setTargetCurrencyCode(paramString2);
        Object localObject1 = localResultSet.getString(1);
        BigDecimal localBigDecimal1 = localObject1 != null ? new BigDecimal((String)localObject1) : null;
        String str = localResultSet.getString(2);
        BigDecimal localBigDecimal2 = str != null ? new BigDecimal(str) : null;
        if (localBigDecimal1 != null) {
          localFXRate.setBuyPrice(new Currency(localBigDecimal1, paramString1, this.p));
        }
        if (localBigDecimal2 != null) {
          localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
        }
        DateTime localDateTime = new DateTime();
        localDateTime.setTime(localResultSet.getTimestamp(3));
        localFXRate.setAsOfDate(localDateTime);
        localFXRate.setObjectType(paramInt2);
        localFXRate.setObjectID(paramString3);
        this.jdField_long.jdField_do(paramString1, localFXRate);
      }
      catch (Throwable localThrowable)
      {
        if ((localThrowable instanceof FXException)) {
          throw ((FXException)localThrowable);
        }
        if ((localThrowable instanceof SQLException)) {
          throw new FXException(34006, "Could not get the foreign exchange rate.", localThrowable);
        }
        throw new FXException("Could not get the foreign exchange rate.", localThrowable);
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet);
        DBUtil.closeStatement(localPreparedStatement);
        DBUtil.returnConnection(this.q, localConnection);
      }
    }
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt, String paramString1, String paramString2, DateTime paramDateTime, HashMap paramHashMap)
    throws FXException
  {
    return getFXRate(paramInt, paramString1, paramString2, paramDateTime, 0, "0", paramHashMap);
  }
  
  public FXRate getFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform getFXRate() because the specified baseCurrencyCode string was null.");
    }
    if (paramString2 == null) {
      throw new FXException(34002, "Could not perform getFXRate() because the specified targetCurrencyCode string was null.");
    }
    if (paramDateTime == null) {
      throw new FXException(34019, "Could not perform getFXRate() because the specified DateTime object was null.");
    }
    if (paramInt2 < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString3 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    FXRate localFXRate = this.jdField_long.a(paramString1, paramString2, paramDateTime, paramInt2, paramString3);
    if (localFXRate == null)
    {
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      try
      {
        Date localDate = a((Calendar)paramDateTime.clone()).getTime();
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localPreparedStatement.setTimestamp(3, new Timestamp(localDate.getTime()));
        localPreparedStatement.setInt(4, paramInt2);
        localPreparedStatement.setString(5, paramString3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        if (!localResultSet.next())
        {
          localObject1 = null;
          return localObject1;
        }
        localFXRate = new FXRate(this.p);
        localFXRate.setTargetCurrencyCode(paramString2);
        Object localObject1 = localResultSet.getString(1);
        BigDecimal localBigDecimal1 = localObject1 != null ? new BigDecimal((String)localObject1) : null;
        String str = localResultSet.getString(2);
        BigDecimal localBigDecimal2 = str != null ? new BigDecimal(str) : null;
        if (localBigDecimal1 != null) {
          localFXRate.setBuyPrice(new Currency(localBigDecimal1, paramString1, this.p));
        }
        if (localBigDecimal2 != null) {
          localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
        }
        DateTime localDateTime = new DateTime();
        localDateTime.setTime(localDate);
        localFXRate.setAsOfDate(localDateTime);
        localFXRate.setObjectType(paramInt2);
        localFXRate.setObjectID(paramString3);
        this.jdField_long.jdField_do(paramString1, localFXRate);
      }
      catch (Throwable localThrowable)
      {
        if ((localThrowable instanceof FXException)) {
          throw ((FXException)localThrowable);
        }
        if ((localThrowable instanceof SQLException)) {
          throw new FXException(34006, "Could not get the foreign exchange rate for the specified date.", localThrowable);
        }
        throw new FXException("Could not get the foreign exchange rate for the specified date.", localThrowable);
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet);
        DBUtil.closeStatement(localPreparedStatement);
      }
    }
    return localFXRate;
  }
  
  public FXRate getFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform getFXRate() because the specified baseCurrencyCode string was null.");
    }
    if (paramString2 == null) {
      throw new FXException(34002, "Could not perform getFXRate() because the specified targetCurrencyCode string was null.");
    }
    if (paramDateTime == null) {
      throw new FXException(34019, "Could not perform getFXRate() because the specified DateTime object was null.");
    }
    if (paramInt2 < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString3 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    FXRate localFXRate = this.jdField_long.a(paramString1, paramString2, paramDateTime, paramInt2, paramString3);
    if (localFXRate == null)
    {
      Connection localConnection = null;
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      try
      {
        Date localDate = a((Calendar)paramDateTime.clone()).getTime();
        localConnection = DBUtil.getConnection(this.q, true, 2);
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, paramString2);
        localPreparedStatement.setTimestamp(3, new Timestamp(localDate.getTime()));
        localPreparedStatement.setInt(4, paramInt2);
        localPreparedStatement.setString(5, paramString3);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        if (!localResultSet.next())
        {
          localObject1 = null;
          return localObject1;
        }
        localFXRate = new FXRate(this.p);
        localFXRate.setTargetCurrencyCode(paramString2);
        Object localObject1 = localResultSet.getString(1);
        BigDecimal localBigDecimal1 = localObject1 != null ? new BigDecimal((String)localObject1) : null;
        String str = localResultSet.getString(2);
        BigDecimal localBigDecimal2 = str != null ? new BigDecimal(str) : null;
        if (localBigDecimal1 != null) {
          localFXRate.setBuyPrice(new Currency(localBigDecimal1, paramString1, this.p));
        }
        if (localBigDecimal2 != null) {
          localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
        }
        DateTime localDateTime = new DateTime();
        localDateTime.setTime(localDate);
        localFXRate.setAsOfDate(localDateTime);
        localFXRate.setObjectType(paramInt2);
        localFXRate.setObjectID(paramString3);
        this.jdField_long.jdField_do(paramString1, localFXRate);
      }
      catch (Throwable localThrowable)
      {
        if ((localThrowable instanceof FXException)) {
          throw ((FXException)localThrowable);
        }
        if ((localThrowable instanceof SQLException)) {
          throw new FXException(34006, "Could not get the foreign exchange rate for the specified date.", localThrowable);
        }
        throw new FXException("Could not get the foreign exchange rate for the specified date.", localThrowable);
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet);
        DBUtil.closeStatement(localPreparedStatement);
        DBUtil.returnConnection(this.q, localConnection);
      }
    }
    return localFXRate;
  }
  
  public FXRate getClosestFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform getFXRate() because the specified baseCurrencyCode string was null.");
    }
    if (paramString2 == null) {
      throw new FXException(34002, "Could not perform getFXRate() because the specified targetCurrencyCode string was null.");
    }
    if (paramDateTime == null) {
      throw new FXException(34019, "Could not perform getFXRate() because the specified DateTime object was null.");
    }
    if (paramInt2 < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString3 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    FXRate localFXRate = this.jdField_long.a(paramString1, paramString2, paramDateTime, paramInt2, paramString3);
    if (localFXRate == null)
    {
      Connection localConnection = null;
      PreparedStatement localPreparedStatement1 = null;
      ResultSet localResultSet1 = null;
      PreparedStatement localPreparedStatement2 = null;
      ResultSet localResultSet2 = null;
      try
      {
        Date localDate = a((Calendar)paramDateTime.clone()).getTime();
        localConnection = DBUtil.getConnection(this.q, true, 2);
        localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        localPreparedStatement1.setString(1, paramString1);
        localPreparedStatement1.setString(2, paramString2);
        localPreparedStatement1.setTimestamp(3, new Timestamp(localDate.getTime()));
        localPreparedStatement1.setInt(4, paramInt2);
        localPreparedStatement1.setString(5, paramString3);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        Object localObject1;
        BigDecimal localBigDecimal1;
        String str;
        BigDecimal localBigDecimal2;
        DateTime localDateTime;
        if (!localResultSet1.next())
        {
          localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa WHERE fa.baseCurrencyCode = ?  AND fa.targetCurrencyCode= ?  AND fa.objectType= ? AND fa.objectID = ? AND  fa.asOfDate = (SELECT MAX(fb.asOfDate) FROM FX_Rate fb WHERE  fb.baseCurrencyCode = ?  AND fb.targetCurrencyCode= ?  AND fb.objectType= ? AND fb.objectID = ? AND  fb.asOfDate <= ? ) ");
          localPreparedStatement2.setString(1, paramString1);
          localPreparedStatement2.setString(2, paramString2);
          localPreparedStatement2.setInt(3, paramInt2);
          localPreparedStatement2.setString(4, paramString3);
          localPreparedStatement2.setString(5, paramString1);
          localPreparedStatement2.setString(6, paramString2);
          localPreparedStatement2.setInt(7, paramInt2);
          localPreparedStatement2.setString(8, paramString3);
          localPreparedStatement2.setTimestamp(9, new Timestamp(localDate.getTime()));
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa WHERE fa.baseCurrencyCode = ?  AND fa.targetCurrencyCode= ?  AND fa.objectType= ? AND fa.objectID = ? AND  fa.asOfDate = (SELECT MAX(fb.asOfDate) FROM FX_Rate fb WHERE  fb.baseCurrencyCode = ?  AND fb.targetCurrencyCode= ?  AND fb.objectType= ? AND fb.objectID = ? AND  fb.asOfDate <= ? ) ");
          if (!localResultSet2.next())
          {
            localObject1 = null;
            return localObject1;
          }
          localFXRate = new FXRate(this.p);
          localFXRate.setTargetCurrencyCode(paramString2);
          localObject1 = localResultSet2.getString(1);
          localBigDecimal1 = localObject1 != null ? new BigDecimal((String)localObject1) : null;
          str = localResultSet2.getString(2);
          localBigDecimal2 = str != null ? new BigDecimal(str) : null;
          if (localBigDecimal1 != null) {
            localFXRate.setBuyPrice(new Currency(localBigDecimal1, paramString1, this.p));
          }
          if (localBigDecimal2 != null) {
            localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
          }
          localDateTime = new DateTime();
          localDateTime.setTime(localResultSet2.getTimestamp(3));
          localFXRate.setAsOfDate(localDateTime);
          localFXRate.setObjectType(paramInt2);
          localFXRate.setObjectID(paramString3);
        }
        else
        {
          localFXRate = new FXRate(this.p);
          localFXRate.setTargetCurrencyCode(paramString2);
          localObject1 = localResultSet1.getString(1);
          localBigDecimal1 = localObject1 != null ? new BigDecimal((String)localObject1) : null;
          str = localResultSet1.getString(2);
          localBigDecimal2 = str != null ? new BigDecimal(str) : null;
          if (localBigDecimal1 != null) {
            localFXRate.setBuyPrice(new Currency(localBigDecimal1, paramString1, this.p));
          }
          if (localBigDecimal2 != null) {
            localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
          }
          localDateTime = new DateTime();
          localDateTime.setTime(localDate);
          localFXRate.setAsOfDate(localDateTime);
          localFXRate.setObjectType(paramInt2);
          localFXRate.setObjectID(paramString3);
          this.jdField_long.jdField_do(paramString1, localFXRate);
        }
      }
      catch (Throwable localThrowable)
      {
        if ((localThrowable instanceof FXException)) {
          throw ((FXException)localThrowable);
        }
        if ((localThrowable instanceof SQLException)) {
          throw new FXException(34006, "Could not get the foreign exchange rate for the specified date.", localThrowable);
        }
        throw new FXException("Could not get the foreign exchange rate for the specified date.", localThrowable);
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeStatement(localPreparedStatement1);
        DBUtil.closeResultSet(localResultSet2);
        DBUtil.closeStatement(localPreparedStatement2);
        DBUtil.returnConnection(this.q, localConnection);
      }
    }
    return localFXRate;
  }
  
  public FXRate getClosestFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform getFXRate() because the specified baseCurrencyCode string was null.");
    }
    if (paramString2 == null) {
      throw new FXException(34002, "Could not perform getFXRate() because the specified targetCurrencyCode string was null.");
    }
    if (paramDateTime == null) {
      throw new FXException(34019, "Could not perform getFXRate() because the specified DateTime object was null.");
    }
    if (paramInt2 < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString3 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    FXRate localFXRate = this.jdField_long.a(paramString1, paramString2, paramDateTime, paramInt2, paramString3);
    if (localFXRate == null)
    {
      PreparedStatement localPreparedStatement1 = null;
      ResultSet localResultSet1 = null;
      PreparedStatement localPreparedStatement2 = null;
      ResultSet localResultSet2 = null;
      try
      {
        Date localDate = a((Calendar)paramDateTime.clone()).getTime();
        localPreparedStatement1 = DBUtil.prepareStatement(paramConnection, "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        localPreparedStatement1.setString(1, paramString1);
        localPreparedStatement1.setString(2, paramString2);
        localPreparedStatement1.setTimestamp(3, new Timestamp(localDate.getTime()));
        localPreparedStatement1.setInt(4, paramInt2);
        localPreparedStatement1.setString(5, paramString3);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select buyPrice, sellPrice from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        Object localObject1;
        BigDecimal localBigDecimal1;
        String str;
        BigDecimal localBigDecimal2;
        DateTime localDateTime;
        if (!localResultSet1.next())
        {
          localPreparedStatement2 = DBUtil.prepareStatement(paramConnection, "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa WHERE fa.baseCurrencyCode = ?  AND fa.targetCurrencyCode= ?  AND fa.objectType= ? AND fa.objectID = ? AND  fa.asOfDate = (SELECT MAX(fb.asOfDate) FROM FX_Rate fb WHERE  fb.baseCurrencyCode = ?  AND fb.targetCurrencyCode= ?  AND fb.objectType= ? AND fb.objectID = ? AND  fb.asOfDate <= ? ) ");
          localPreparedStatement2.setString(1, paramString1);
          localPreparedStatement2.setString(2, paramString2);
          localPreparedStatement2.setInt(3, paramInt2);
          localPreparedStatement2.setString(4, paramString3);
          localPreparedStatement2.setString(5, paramString1);
          localPreparedStatement2.setString(6, paramString2);
          localPreparedStatement2.setInt(7, paramInt2);
          localPreparedStatement2.setString(8, paramString3);
          localPreparedStatement2.setTimestamp(9, new Timestamp(localDate.getTime()));
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "SELECT fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa WHERE fa.baseCurrencyCode = ?  AND fa.targetCurrencyCode= ?  AND fa.objectType= ? AND fa.objectID = ? AND  fa.asOfDate = (SELECT MAX(fb.asOfDate) FROM FX_Rate fb WHERE  fb.baseCurrencyCode = ?  AND fb.targetCurrencyCode= ?  AND fb.objectType= ? AND fb.objectID = ? AND  fb.asOfDate <= ? ) ");
          if (!localResultSet2.next())
          {
            localObject1 = null;
            return localObject1;
          }
          localFXRate = new FXRate(this.p);
          localFXRate.setTargetCurrencyCode(paramString2);
          localObject1 = localResultSet2.getString(1);
          localBigDecimal1 = localObject1 != null ? new BigDecimal((String)localObject1) : null;
          str = localResultSet2.getString(2);
          localBigDecimal2 = str != null ? new BigDecimal(str) : null;
          if (localBigDecimal1 != null) {
            localFXRate.setBuyPrice(new Currency(localBigDecimal1, paramString1, this.p));
          }
          if (localBigDecimal2 != null) {
            localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
          }
          localDateTime = new DateTime();
          localDateTime.setTime(localResultSet2.getTimestamp(3));
          localFXRate.setAsOfDate(localDateTime);
          localFXRate.setObjectType(paramInt2);
          localFXRate.setObjectID(paramString3);
        }
        else
        {
          localFXRate = new FXRate(this.p);
          localFXRate.setTargetCurrencyCode(paramString2);
          localObject1 = localResultSet1.getString(1);
          localBigDecimal1 = localObject1 != null ? new BigDecimal((String)localObject1) : null;
          str = localResultSet1.getString(2);
          localBigDecimal2 = str != null ? new BigDecimal(str) : null;
          if (localBigDecimal1 != null) {
            localFXRate.setBuyPrice(new Currency(localBigDecimal1, this.p));
          }
          if (localBigDecimal2 != null) {
            localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
          }
          localDateTime = new DateTime();
          localDateTime.setTime(localDate);
          localFXRate.setAsOfDate(localDateTime);
          localFXRate.setObjectType(paramInt2);
          localFXRate.setObjectID(paramString3);
          this.jdField_long.jdField_do(paramString1, localFXRate);
        }
      }
      catch (Throwable localThrowable)
      {
        if ((localThrowable instanceof FXException)) {
          throw ((FXException)localThrowable);
        }
        if ((localThrowable instanceof SQLException)) {
          throw new FXException(34006, "Could not get the foreign exchange rate for the specified date.", localThrowable);
        }
        throw new FXException("Could not get the foreign exchange rate for the specified date.", localThrowable);
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeStatement(localPreparedStatement1);
        DBUtil.closeResultSet(localResultSet2);
        DBUtil.closeStatement(localPreparedStatement2);
      }
    }
    return localFXRate;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException
  {
    return getFXRateSheet(paramInt, paramString, 0, "0", paramHashMap);
  }
  
  public FXRateSheet getFXRateSheet(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform getFXRateSheet() because the specified baseCurrencyCode string was null.");
    }
    if (paramInt2 < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    FXRateSheet localFXRateSheet = new FXRateSheet(this.p);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT fa.targetCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, (SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID,  max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.baseCurrencyCode = ? AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID)  MostRecentDate WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setString(3, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT fa.targetCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, (SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID,  max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.baseCurrencyCode = ? AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID)  MostRecentDate WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ");
      FXRates localFXRates = null;
      while (localResultSet.next())
      {
        if (localFXRates == null) {
          localFXRates = new FXRates(this.p);
        }
        FXRate localFXRate = localFXRates.add();
        localFXRate.setTargetCurrencyCode(localResultSet.getString(1));
        String str1 = localResultSet.getString(2);
        BigDecimal localBigDecimal1 = str1 != null ? new BigDecimal(str1) : null;
        String str2 = localResultSet.getString(3);
        BigDecimal localBigDecimal2 = str2 != null ? new BigDecimal(str2) : null;
        if (localBigDecimal1 != null) {
          localFXRate.setBuyPrice(new Currency(localBigDecimal1, paramString1, this.p));
        }
        if (localBigDecimal2 != null) {
          localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
        }
        DateTime localDateTime = new DateTime();
        localDateTime.setTime(localResultSet.getTimestamp(4));
        localFXRate.setAsOfDate(localDateTime);
      }
      localFXRateSheet.setBaseCurrencyCode(paramString1);
      localFXRateSheet.setRates(localFXRates);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange rate sheet.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange rate sheet.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheetForTarget(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34002, "Could not perform getFXRateSheetForTarget() because the specified targetCurrencyCode string was null.");
    }
    if (paramInt2 < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    FXRateSheet localFXRateSheet = new FXRateSheet(this.p);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT fa .baseCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, ( SELECT fb.baseCurrencyCode,  fb.targetCurrencyCode, fb.objectType, fb.objectID, max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.targetCurrencyCode = ? AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID)  MostRecentDate WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setString(3, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT fa .baseCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, ( SELECT fb.baseCurrencyCode,  fb.targetCurrencyCode, fb.objectType, fb.objectID, max(fb.asOfDate) AS max_date FROM FX_Rate fb WHERE fb.targetCurrencyCode = ? AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.objectType, fb.objectID)  MostRecentDate WHERE MostRecentDate.baseCurrencyCode = fa.baseCurrencyCode AND MostRecentDate.targetCurrencyCode = fa.targetCurrencyCode AND  MostRecentDate.max_date = fa.asOfDate AND MostRecentDate.objectType=fa.objectType AND MostRecentDate.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ");
      FXRates localFXRates = null;
      while (localResultSet.next())
      {
        if (localFXRates == null) {
          localFXRates = new FXRates(this.p);
        }
        FXRate localFXRate = localFXRates.add();
        localFXRate.setTargetCurrencyCode(paramString1);
        String str1 = localResultSet.getString(1);
        String str2 = localResultSet.getString(2);
        BigDecimal localBigDecimal1 = str2 != null ? new BigDecimal(str2) : null;
        String str3 = localResultSet.getString(3);
        BigDecimal localBigDecimal2 = str3 != null ? new BigDecimal(str3) : null;
        if (localBigDecimal1 != null) {
          localFXRate.setBuyPrice(new Currency(localBigDecimal1, str1, this.p));
        }
        if (localBigDecimal2 != null) {
          localFXRate.setSellPrice(new Currency(localBigDecimal2, str1, this.p));
        }
        DateTime localDateTime = new DateTime();
        localDateTime.setTime(localResultSet.getTimestamp(4));
        localFXRate.setAsOfDate(localDateTime);
      }
      localFXRateSheet.setRates(localFXRates);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange rate sheet.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange rate sheet.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt1, String paramString1, DateTime paramDateTime, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform getFXRateSheet() because the specified baseCurrencyCode string was null.");
    }
    if (paramDateTime == null) {
      throw new FXException(34019, "Could not perform getFXRate() because the specified DateTime object was null.");
    }
    if (paramInt2 < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    FXRateSheet localFXRateSheet = new FXRateSheet(this.p);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      Date localDate = a((Calendar)paramDateTime.clone()).getTime();
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "SELECT fa.targetCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, (SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.asOfDate, fb.objectType,  fb.objectID FROM FX_Rate fb WHERE  fb.asOfDate=? and fb.baseCurrencyCode = ?  AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.asOfDate,  fb.objectType, fb.objectID ) SheetView WHERE SheetView.baseCurrencyCode = fa.baseCurrencyCode AND SheetView.targetCurrencyCode = fa.targetCurrencyCode AND  SheetView.asOfDate = fa.asOfDate AND SheetView.objectType=fa.objectType AND SheetView.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ");
      localPreparedStatement.setTimestamp(1, new Timestamp(localDate.getTime()));
      localPreparedStatement.setString(2, paramString1);
      localPreparedStatement.setInt(3, paramInt2);
      localPreparedStatement.setString(4, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT fa.targetCurrencyCode, fa.buyPrice, fa.sellPrice, fa.asOfDate FROM FX_Rate fa, (SELECT fb.baseCurrencyCode, fb.targetCurrencyCode, fb.asOfDate, fb.objectType,  fb.objectID FROM FX_Rate fb WHERE  fb.asOfDate=? and fb.baseCurrencyCode = ?  AND fb.objectType= ? AND fb.objectID = ? GROUP BY fb.baseCurrencyCode, fb.targetCurrencyCode, fb.asOfDate,  fb.objectType, fb.objectID ) SheetView WHERE SheetView.baseCurrencyCode = fa.baseCurrencyCode AND SheetView.targetCurrencyCode = fa.targetCurrencyCode AND  SheetView.asOfDate = fa.asOfDate AND SheetView.objectType=fa.objectType AND SheetView.objectID=fa.objectID AND  fa.baseCurrencyCode != fa.targetCurrencyCode ");
      FXRates localFXRates = null;
      while (localResultSet.next())
      {
        if (localFXRates == null) {
          localFXRates = new FXRates(this.p);
        }
        FXRate localFXRate = localFXRates.add();
        localFXRate.setTargetCurrencyCode(localResultSet.getString(1));
        String str1 = localResultSet.getString(2);
        BigDecimal localBigDecimal1 = str1 != null ? new BigDecimal(str1) : null;
        String str2 = localResultSet.getString(3);
        BigDecimal localBigDecimal2 = str2 != null ? new BigDecimal(str2) : null;
        if (localBigDecimal1 != null) {
          localFXRate.setBuyPrice(new Currency(localBigDecimal1, paramString1, this.p));
        }
        if (localBigDecimal2 != null) {
          localFXRate.setSellPrice(new Currency(localBigDecimal2, paramString1, this.p));
        }
        DateTime localDateTime = new DateTime();
        localDateTime.setTime(localResultSet.getTimestamp(4));
        localFXRate.setAsOfDate(localDateTime);
      }
      localFXRateSheet.setBaseCurrencyCode(paramString1);
      localFXRateSheet.setRates(localFXRates);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange rate sheet.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange rate sheet.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return localFXRateSheet;
  }
  
  public FXCurrencies getCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    FXCurrencies localFXCurrencies = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select currencyCode, description from FX_Currency");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select currencyCode, description from FX_Currency");
      while (localResultSet.next())
      {
        if (localFXCurrencies == null) {
          localFXCurrencies = new FXCurrencies(this.p);
        }
        FXCurrency localFXCurrency = localFXCurrencies.add();
        localFXCurrency.setCurrencyCode(localResultSet.getString(1));
        localFXCurrency.setDescription(localResultSet.getString(2));
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange currencies.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange currencies.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return localFXCurrencies;
  }
  
  public FXCurrencies getBaseCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    FXCurrencies localFXCurrencies = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.baseCurrencyCode = cur.currencyCode");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.baseCurrencyCode = cur.currencyCode");
      while (localResultSet.next())
      {
        if (localFXCurrencies == null) {
          localFXCurrencies = new FXCurrencies(this.p);
        }
        FXCurrency localFXCurrency = localFXCurrencies.add();
        localFXCurrency.setCurrencyCode(localResultSet.getString(1));
        localFXCurrency.setDescription(localResultSet.getString(2));
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange base currencies.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange base currencies.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return localFXCurrencies;
  }
  
  public FXCurrencies getBaseCurrenciesGivenTarget(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString == null) {
      throw new FXException(34002, "Could not perform getBaseCurrenciesGivenTarget() because the specified targetCurrencyCode string was null.");
    }
    FXCurrencies localFXCurrencies = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.targetCurrencyCode = ? AND rate.buyPrice IS NOT NULL AND rate.sellPrice IS NOT NULL AND rate.baseCurrencyCode = cur.currencyCode");
      localPreparedStatement.setString(1, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.targetCurrencyCode = ? AND rate.buyPrice IS NOT NULL AND rate.sellPrice IS NOT NULL AND rate.baseCurrencyCode = cur.currencyCode");
      while (localResultSet.next())
      {
        if (localFXCurrencies == null) {
          localFXCurrencies = new FXCurrencies(this.p);
        }
        FXCurrency localFXCurrency = localFXCurrencies.add();
        localFXCurrency.setCurrencyCode(localResultSet.getString(1));
        localFXCurrency.setDescription(localResultSet.getString(2));
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange base currencies given a target currency.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange base currencies given a target currency.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return localFXCurrencies;
  }
  
  public FXCurrencies getTargetCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    FXCurrencies localFXCurrencies = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.targetCurrencyCode = cur.currencyCode");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select distinct cur.currencyCode, cur.description from FX_Currency cur, FX_Rate rate where rate.targetCurrencyCode = cur.currencyCode");
      while (localResultSet.next())
      {
        if (localFXCurrencies == null) {
          localFXCurrencies = new FXCurrencies(this.p);
        }
        FXCurrency localFXCurrency = localFXCurrencies.add();
        localFXCurrency.setCurrencyCode(localResultSet.getString(1));
        localFXCurrency.setDescription(localResultSet.getString(2));
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange target currencies.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange target currencies.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return localFXCurrencies;
  }
  
  public void addCurrency(FXCurrency paramFXCurrency, HashMap paramHashMap)
    throws FXException
  {
    if (paramFXCurrency == null) {
      throw new FXException(34003, "Could not perform addCurrency() because the specified FXCurrency object was null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into FX_Currency( currencyCode, description ) values ( ?, ? )");
      localPreparedStatement.setString(1, paramFXCurrency.getCurrencyCode());
      localPreparedStatement.setString(2, paramFXCurrency.getDescription());
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "insert into FX_Currency( currencyCode, description ) values ( ?, ? )");
      if (i1 == 0) {
        throw new FXException(34008, "Could not add that currency to the foreign exchange repository.");
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not add that currency to the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not add that currency to the foreign exchange repository.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void modifyCurrency(FXCurrency paramFXCurrency, HashMap paramHashMap)
    throws FXException
  {
    if (paramFXCurrency == null) {
      throw new FXException(34003, "Could not perform modifyCurrency() because the specified FXCurrency object was null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update FX_Currency set description = ? where currencyCode = ?");
      localPreparedStatement.setString(1, paramFXCurrency.getDescription());
      localPreparedStatement.setString(2, paramFXCurrency.getCurrencyCode());
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "update FX_Currency set description = ? where currencyCode = ?");
      if (i1 == 0) {
        throw new FXException(34009, "Could not modify that currency in the foreign exchange repository.");
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not modify that currency in the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not modify that currency in the foreign exchange repository.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void removeCurrency(FXCurrency paramFXCurrency, HashMap paramHashMap)
    throws FXException
  {
    if (paramFXCurrency == null) {
      throw new FXException(34003, "Could not perform removeCurrency() because the specified FXCurrency object was null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      String str = paramFXCurrency.getCurrencyCode();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select count(*) from FX_Rate where baseCurrencyCode = ? OR targetCurrencyCode = ?");
      localPreparedStatement.setString(1, str);
      localPreparedStatement.setString(2, str);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select count(*) from FX_Rate where baseCurrencyCode = ? OR targetCurrencyCode = ?");
      if ((localResultSet.next()) && (localResultSet.getInt(1) > 0)) {
        throw new FXException(34010, "Could not remove that currency from the foreign exchange repository because it is in use as a base or target currency.");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from FX_Currency where currencyCode = ?");
      localPreparedStatement.setString(1, str);
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "delete from FX_Currency where currencyCode = ?");
      if (i1 == 0) {
        throw new FXException(34010, "Could not remove that currency from the foreign exchange repository.");
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not remove that currency from the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not remove that currency from the foreign exchange repository.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void addFXRate(String paramString, FXRate paramFXRate, HashMap paramHashMap)
    throws FXException
  {
    addFXRate(paramString, paramFXRate, 0, "0", paramHashMap);
  }
  
  public void addFXRate(String paramString1, FXRate paramFXRate, int paramInt, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform addFXRate() because the specified baseCurrencyCode string was null.");
    }
    if (paramFXRate == null) {
      throw new FXException(34004, "Could not perform addFXRate() because the specified FXRate object was null.");
    }
    if (paramInt < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into FX_Rate( baseCurrencyCode, targetCurrencyCode, buyPrice, sellPrice, asOfDate, objectType, objectID ) values ( ?, ?, ?, ?, ?, ?, ? )");
      BigDecimal localBigDecimal1 = null;
      BigDecimal localBigDecimal2 = null;
      if (paramFXRate.getBuyPrice() != null) {
        localBigDecimal1 = paramFXRate.getBuyPrice().getAmountValue();
      }
      if (paramFXRate.getSellPrice() != null) {
        localBigDecimal2 = paramFXRate.getSellPrice().getAmountValue();
      }
      Calendar localCalendar = a((Calendar)paramFXRate.getAsOfDate().clone());
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramFXRate.getTargetCurrencyCode());
      localPreparedStatement.setString(3, localBigDecimal1 != null ? localBigDecimal1.toString() : null);
      localPreparedStatement.setString(4, localBigDecimal2 != null ? localBigDecimal2.toString() : null);
      localPreparedStatement.setTimestamp(5, new Timestamp(localCalendar.getTime().getTime()));
      localPreparedStatement.setInt(6, paramInt);
      localPreparedStatement.setString(7, paramString2);
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "insert into FX_Rate( baseCurrencyCode, targetCurrencyCode, buyPrice, sellPrice, asOfDate, objectType, objectID ) values ( ?, ?, ?, ?, ?, ?, ? )");
      if (i1 == 0) {
        throw new FXException(34011, "Could not add a rate to the foreign exchange repository.");
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not add a rate to the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not add a rate to the foreign exchange repository.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void modifyFXRate(String paramString, FXRate paramFXRate, HashMap paramHashMap)
    throws FXException
  {
    modifyFXRate(paramString, paramFXRate, 0, "0", paramHashMap);
  }
  
  public void modifyFXRate(String paramString1, FXRate paramFXRate, int paramInt, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform modifyFXRate() because the specified baseCurrencyCode string was null.");
    }
    if (paramFXRate == null) {
      throw new FXException(34004, "Could not perform modifyFXRate() because the specified FXRate object was null.");
    }
    if (paramInt < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update FX_Rate set buyPrice = ?, sellPrice = ? where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
      BigDecimal localBigDecimal1 = null;
      BigDecimal localBigDecimal2 = null;
      if (paramFXRate.getBuyPrice() != null) {
        localBigDecimal1 = paramFXRate.getBuyPrice().getAmountValue();
      }
      if (paramFXRate.getSellPrice() != null) {
        localBigDecimal2 = paramFXRate.getSellPrice().getAmountValue();
      }
      Calendar localCalendar = a((Calendar)paramFXRate.getAsOfDate().clone());
      localPreparedStatement.setString(1, localBigDecimal1 != null ? localBigDecimal1.toString() : null);
      localPreparedStatement.setString(2, localBigDecimal2 != null ? localBigDecimal2.toString() : null);
      localPreparedStatement.setString(3, paramString1);
      localPreparedStatement.setString(4, paramFXRate.getTargetCurrencyCode());
      localPreparedStatement.setTimestamp(5, new Timestamp(localCalendar.getTime().getTime()));
      localPreparedStatement.setInt(6, paramInt);
      localPreparedStatement.setString(7, paramString2);
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "update FX_Rate set buyPrice = ?, sellPrice = ? where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
      if (i1 == 0) {
        throw new FXException(34012, "Could not modify a rate in the foreign exchange repository.");
      }
      paramFXRate.setObjectType(paramInt);
      paramFXRate.setObjectID(paramString2);
      this.jdField_long.a(paramString1, paramFXRate);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not modify a rate in the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not modify a rate in the foreign exchange repository.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void removeFXRate(String paramString, FXRate paramFXRate, HashMap paramHashMap)
    throws FXException
  {
    removeFXRate(paramString, paramFXRate, 0, "0", paramHashMap);
  }
  
  public void removeFXRate(String paramString1, FXRate paramFXRate, int paramInt, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform removeFXRate() because the specified baseCurrencyCode string was null.");
    }
    if (paramFXRate == null) {
      throw new FXException(34004, "Could not perform removeFXRate() because the specified FXRate object was null.");
    }
    if (paramInt < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      Calendar localCalendar = a((Calendar)paramFXRate.getAsOfDate().clone());
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramFXRate.getTargetCurrencyCode());
      localPreparedStatement.setTimestamp(3, new Timestamp(localCalendar.getTime().getTime()));
      localPreparedStatement.setInt(4, paramInt);
      localPreparedStatement.setString(5, paramString2);
      int i1 = DBUtil.executeUpdate(localPreparedStatement, "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
      if (i1 == 0) {
        throw new FXException(34013, "Could not remove a rate from the foreign exchange repository.");
      }
      paramFXRate.setObjectType(paramInt);
      paramFXRate.setObjectID(paramString2);
      this.jdField_long.jdField_if(paramString1, paramFXRate);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not remove a rate from the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not remove a rate from the foreign exchange repository.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void addFXRateSheet(String paramString1, FXRateSheet paramFXRateSheet, int paramInt, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform addFXRateSheet() because the specified baseCurrencyCode string was null.");
    }
    if (paramFXRateSheet == null) {
      throw new FXException(34005, "Could not perform addFXRateSheet() because the specified FXRateSheet object was null.");
    }
    if (paramInt < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    a(paramFXRateSheet);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into FX_Rate( baseCurrencyCode, targetCurrencyCode, buyPrice, sellPrice, asOfDate, objectType, objectID ) values ( ?, ?, ?, ?, ?, ?, ? )");
      FXRates localFXRates = paramFXRateSheet.getRates();
      Iterator localIterator = localFXRates.iterator();
      while (localIterator.hasNext())
      {
        FXRate localFXRate = (FXRate)localIterator.next();
        BigDecimal localBigDecimal1 = null;
        BigDecimal localBigDecimal2 = null;
        if (localFXRate.getBuyPrice() != null) {
          localBigDecimal1 = localFXRate.getBuyPrice().getAmountValue();
        }
        if (localFXRate.getSellPrice() != null) {
          localBigDecimal2 = localFXRate.getSellPrice().getAmountValue();
        }
        Calendar localCalendar = a((Calendar)localFXRate.getAsOfDate().clone());
        localPreparedStatement.clearParameters();
        localPreparedStatement.setString(1, paramString1);
        localPreparedStatement.setString(2, localFXRate.getTargetCurrencyCode());
        localPreparedStatement.setString(3, localBigDecimal1 != null ? localBigDecimal1.toString() : null);
        localPreparedStatement.setString(4, localBigDecimal2 != null ? localBigDecimal2.toString() : null);
        localPreparedStatement.setTimestamp(5, new Timestamp(localCalendar.getTime().getTime()));
        localPreparedStatement.setInt(6, paramInt);
        localPreparedStatement.setString(7, paramString2);
        int i1 = DBUtil.executeUpdate(localPreparedStatement, "insert into FX_Rate( baseCurrencyCode, targetCurrencyCode, buyPrice, sellPrice, asOfDate, objectType, objectID ) values ( ?, ?, ?, ?, ?, ?, ? )");
        if (i1 == 0) {
          throw new FXException(34014, "Could not add that rate sheet to the foreign exchange repository.");
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not add that rate sheet to the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not add that rate sheet to the foreign exchange repository.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void addFXRateSheet(String paramString, FXRateSheet paramFXRateSheet, HashMap paramHashMap)
    throws FXException
  {
    addFXRateSheet(paramString, paramFXRateSheet, 0, "0", paramHashMap);
  }
  
  public void modifyFXRateSheet(String paramString, FXRateSheet paramFXRateSheet, HashMap paramHashMap)
    throws FXException
  {
    modifyFXRateSheet(paramString, paramFXRateSheet, 0, "0", paramHashMap);
  }
  
  public void modifyFXRateSheet(String paramString1, FXRateSheet paramFXRateSheet, int paramInt, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform modifyFXRateSheet() because the specified baseCurrencyCode string was null.");
    }
    if (paramFXRateSheet == null) {
      throw new FXException(34005, "Could not perform modifyFXRateSheet() because the specified FXRateSheet object was null.");
    }
    if (paramInt < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    a(paramFXRateSheet);
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update FX_Rate set buyPrice = ?, sellPrice = ? where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
      FXRates localFXRates = paramFXRateSheet.getRates();
      Iterator localIterator = localFXRates.iterator();
      while (localIterator.hasNext())
      {
        FXRate localFXRate = (FXRate)localIterator.next();
        localFXRate.setObjectType(paramInt);
        localFXRate.setObjectID(paramString2);
        BigDecimal localBigDecimal1 = null;
        BigDecimal localBigDecimal2 = null;
        if (localFXRate.getBuyPrice() != null) {
          localBigDecimal1 = localFXRate.getBuyPrice().getAmountValue();
        }
        if (localFXRate.getSellPrice() != null) {
          localBigDecimal2 = localFXRate.getSellPrice().getAmountValue();
        }
        Calendar localCalendar = a((Calendar)localFXRate.getAsOfDate().clone());
        localPreparedStatement.clearParameters();
        localPreparedStatement.setString(1, localBigDecimal1 != null ? localBigDecimal1.toString() : null);
        localPreparedStatement.setString(2, localBigDecimal2 != null ? localBigDecimal2.toString() : null);
        localPreparedStatement.setString(3, paramString1);
        localPreparedStatement.setString(4, localFXRate.getTargetCurrencyCode());
        localPreparedStatement.setTimestamp(5, new Timestamp(localCalendar.getTime().getTime()));
        localPreparedStatement.setInt(6, paramInt);
        localPreparedStatement.setString(7, paramString2);
        int i1 = DBUtil.executeUpdate(localPreparedStatement, "update FX_Rate set buyPrice = ?, sellPrice = ? where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        if (i1 == 0) {
          throw new FXException(34015, "Could not modify that rate sheet in the foreign exchange repository.");
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not modify that rate sheet in the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not modify that rate sheet in the foreign exchange repository.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    this.jdField_long.a(paramString1, paramFXRateSheet.getRates());
  }
  
  public void removeFXRateSheet(String paramString1, int paramInt, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform removeFXRateSheet() because the specified baseCurrencyCode string was null.");
    }
    if (paramInt < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select distinct targetCurrencyCode from FX_Rate where baseCurrencyCode = ?  and objectType = ? and objectID = ? order by targetCurrencyCode");
      localPreparedStatement1.setString(1, paramString1);
      localPreparedStatement1.setInt(2, paramInt);
      localPreparedStatement1.setString(3, paramString2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, "select distinct targetCurrencyCode from FX_Rate where baseCurrencyCode = ?  and objectType = ? and objectID = ? order by targetCurrencyCode");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND objectType=? AND objectID=?");
      int i1 = 0;
      while (localResultSet.next())
      {
        String str = localResultSet.getString(1);
        localPreparedStatement2.clearParameters();
        localPreparedStatement2.setString(1, paramString1);
        localPreparedStatement2.setString(2, str);
        localPreparedStatement2.setInt(3, paramInt);
        localPreparedStatement2.setString(4, paramString2);
        i1 = DBUtil.executeUpdate(localPreparedStatement2, "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND objectType=? AND objectID=?");
        if (i1 < 1) {
          throw new FXException(34016, "Could not remove that rate sheet from the foreign exchange repository.");
        }
      }
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement2);
      localPreparedStatement2 = null;
      DBUtil.closeAll(localPreparedStatement1, localResultSet);
      localResultSet = null;
      localPreparedStatement1 = null;
      this.jdField_long.a(paramString1, paramInt, paramString2);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not remove that rate sheet from the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not remove that rate sheet from the foreign exchange repository.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void removeFXRateSheet(String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws FXException
  {
    removeFXRateSheet(paramString, paramDateTime, 0, "0", paramHashMap);
  }
  
  public void removeFXRateSheet(String paramString, HashMap paramHashMap)
    throws FXException
  {
    removeFXRateSheet(paramString, 0, "0", paramHashMap);
  }
  
  public void removeFXRateSheet(String paramString1, DateTime paramDateTime, int paramInt, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    if (paramString1 == null) {
      throw new FXException(34001, "Could not perform removeFXRateSheet() because the specified baseCurrencyCode string was null.");
    }
    if (paramDateTime == null) {
      throw new FXException(34019, "Could not perform removeFXRateSheet() because the specified DateTime object was null.");
    }
    if (paramInt < 0) {
      throw new FXException(34024, "Could not perform getFXRate() because the specified object type was invalid.");
    }
    if (paramString2 == null) {
      throw new FXException(34023, "Could not perform getFXRate() because the specified object id was null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    try
    {
      Timestamp localTimestamp = new Timestamp(a((Calendar)paramDateTime.clone()).getTime().getTime());
      localConnection = DBUtil.getConnection(this.q, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select distinct targetCurrencyCode from FX_Rate where baseCurrencyCode = ? order by targetCurrencyCode");
      localPreparedStatement1.setString(1, paramString1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, "select distinct targetCurrencyCode from FX_Rate where baseCurrencyCode = ? order by targetCurrencyCode");
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
      int i1 = 0;
      while (localResultSet.next())
      {
        String str = localResultSet.getString(1);
        localPreparedStatement2.clearParameters();
        localPreparedStatement2.setString(1, paramString1);
        localPreparedStatement2.setString(2, str);
        localPreparedStatement2.setTimestamp(3, localTimestamp);
        localPreparedStatement2.setInt(4, paramInt);
        localPreparedStatement2.setString(5, paramString2);
        i1 = DBUtil.executeUpdate(localPreparedStatement2, "delete from FX_Rate where baseCurrencyCode = ? AND targetCurrencyCode = ? AND asOfDate = ? AND objectType=? AND objectID=?");
        if (i1 != 1) {
          throw new FXException(34016, "Could not remove that rate sheet from the foreign exchange repository.");
        }
      }
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement2);
      localPreparedStatement2 = null;
      DBUtil.closeAll(localPreparedStatement1, localResultSet);
      localResultSet = null;
      localPreparedStatement1 = null;
      this.jdField_long.a(paramString1, paramDateTime);
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not remove the rates for the specified base currency and date from the foreign exchange repository.", localThrowable);
      }
      throw new FXException("Could not remove the rates for the specified base currency and date from the foreign exchange repository.", localThrowable);
    }
    finally
    {
      try
      {
        DBUtil.endTransaction(localConnection);
      }
      catch (Exception localException) {}
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  private static void a(FXRateSheet paramFXRateSheet)
  {
    FXRates localFXRates1 = paramFXRateSheet.getRates();
    int i1 = localFXRates1.size();
    FXRate[] arrayOfFXRate = new FXRate[i1];
    arrayOfFXRate = (FXRate[])paramFXRateSheet.getRates().toArray(arrayOfFXRate);
    Arrays.sort(arrayOfFXRate, new Comparator()
    {
      public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if ((!(paramAnonymousObject1 instanceof FXRate)) || (!(paramAnonymousObject2 instanceof FXRate))) {
          throw new ClassCastException("The objects are not of type com.ffusion.beans.fx.FXRate");
        }
        FXRate localFXRate1 = (FXRate)paramAnonymousObject1;
        FXRate localFXRate2 = (FXRate)paramAnonymousObject2;
        if (localFXRate1.equals(localFXRate2)) {
          return 0;
        }
        return localFXRate1.compare(localFXRate2, "TARGET_CURRENCY_CODE");
      }
      
      public boolean equals(Object paramAnonymousObject)
      {
        return super.equals(paramAnonymousObject);
      }
    });
    FXRates localFXRates2 = new FXRates(paramFXRateSheet.getLocale());
    for (int i2 = 0; i2 < i1; i2++) {
      localFXRates2.add(arrayOfFXRate[i2]);
    }
    paramFXRateSheet.setRates(localFXRates2);
  }
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramInt < 0) {
      throw new FXException(34020, "Could not perform cleanup() because the specified age in days is a negetive value.");
    }
    String str = (String)paramHashMap.get("OBJECT_ID");
    if ((str != null) && (str.trim().length() > 0))
    {
      int i1 = Integer.parseInt((String)paramHashMap.get("OBJECT_TYPE"));
      a(paramInt, i1, str.trim(), paramHashMap);
      return;
    }
    Object localObject1 = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from FX_Rate where asOfDate < ?");
      Calendar localCalendar = a(Calendar.getInstance());
      localCalendar.add(5, -1 * paramInt);
      Timestamp localTimestamp = new Timestamp(localCalendar.getTime().getTime());
      localPreparedStatement.setTimestamp(1, localTimestamp);
      DBUtil.executeUpdate(localPreparedStatement, "delete from FX_Rate where asOfDate < ?");
      this.jdField_long.a(paramInt);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not cleanup the foreign exchange rates.", localThrowable);
      }
      throw new FXException("Could not cleanup the foreign exchange rates.", localThrowable);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  private void a(int paramInt1, int paramInt2, String paramString, HashMap paramHashMap)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    if (paramInt1 < 0) {
      throw new FXException(34020, "Could not perform cleanup() because the specified age in days is a negetive value.");
    }
    Object localObject1 = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from FX_Rate where asOfDate < ? and objectType=? AND objectID=?");
      Calendar localCalendar = a(Calendar.getInstance());
      localCalendar.add(5, -1 * paramInt1);
      Timestamp localTimestamp = new Timestamp(localCalendar.getTime().getTime());
      localPreparedStatement.setTimestamp(1, localTimestamp);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setString(3, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "delete from FX_Rate where asOfDate < ? and objectType=? AND objectID=?");
      this.jdField_long.a(paramInt1, paramInt2, paramString);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not cleanup the foreign exchange rates.", localThrowable);
      }
      throw new FXException("Could not cleanup the foreign exchange rates.", localThrowable);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
  }
  
  public void shutdown()
    throws FXException
  {
    try
    {
      ConnectionPool.releasePool(this.q);
    }
    catch (Exception localException)
    {
      throw ((FXException)localException);
    }
  }
  
  public int getPrimaryID(int paramInt)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select PRIMARY_USER_ID from CUSTOMER_REL where DIRECTORY_ID = ? ");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select PRIMARY_USER_ID from CUSTOMER_REL where DIRECTORY_ID = ? ");
      if (!localResultSet.next())
      {
        int i2 = 0;
        return i2;
      }
      i1 = localResultSet.getInt(1);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange rate.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange rate.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return i1;
  }
  
  public int getBankForBusiness(int paramInt)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select b.affiliate_bank_id from business b,  entitlement_group eg where eg.name=b.business_name and eg.ent_group_id= ? ");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select b.affiliate_bank_id from business b,  entitlement_group eg where eg.name=b.business_name and eg.ent_group_id= ? ");
      if (!localResultSet.next())
      {
        int i2 = 0;
        return i2;
      }
      i1 = localResultSet.getInt(1);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the affiliate bank id.", localThrowable);
      }
      throw new FXException("Could not get the affiliate bank id.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return i1;
  }
  
  public int getBankForCustomer(int paramInt)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1;
    try
    {
      localConnection = DBUtil.getConnection(this.q, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select c.affiliate_bank_id from customer c where directory_id =?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.affiliate_bank_id from customer c where directory_id =?");
      if (!localResultSet.next())
      {
        int i2 = 0;
        return i2;
      }
      i1 = localResultSet.getInt(1);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the affiliate bank id.", localThrowable);
      }
      throw new FXException("Could not get the affiliate bank id.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(this.q, localConnection);
    }
    return i1;
  }
  
  public int getPrimaryID(int paramInt, Connection paramConnection)
    throws FXException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select PRIMARY_USER_ID from CUSTOMER_REL where DIRECTORY_ID = ? ");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select PRIMARY_USER_ID from CUSTOMER_REL where DIRECTORY_ID = ? ");
      if (!localResultSet.next())
      {
        int i2 = 0;
        return i2;
      }
      i1 = localResultSet.getInt(1);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the foreign exchange rate.", localThrowable);
      }
      throw new FXException("Could not get the foreign exchange rate.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i1;
  }
  
  public int getBankForBusiness(int paramInt, Connection paramConnection)
    throws FXException
  {
    if (this.q == null) {
      throw new FXException(34025, "A DB Connection pool expected but it is null.");
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select b.affiliate_bank_id from business b,  entitlement_group eg where eg.name=b.business_name and eg.ent_group_id= ? ");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select b.affiliate_bank_id from business b,  entitlement_group eg where eg.name=b.business_name and eg.ent_group_id= ? ");
      if (!localResultSet.next())
      {
        int i2 = 0;
        return i2;
      }
      i1 = localResultSet.getInt(1);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the affiliate bank id.", localThrowable);
      }
      throw new FXException("Could not get the affiliate bank id.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i1;
  }
  
  public int getBankForCustomer(int paramInt, Connection paramConnection)
    throws FXException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i1;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select c.affiliate_bank_id from customer c where directory_id =?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select c.affiliate_bank_id from customer c where directory_id =?");
      if (!localResultSet.next())
      {
        int i2 = 0;
        return i2;
      }
      i1 = localResultSet.getInt(1);
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof FXException)) {
        throw ((FXException)localThrowable);
      }
      if ((localThrowable instanceof SQLException)) {
        throw new FXException(34006, "Could not get the affiliate bank id.", localThrowable);
      }
      throw new FXException("Could not get the affiliate bank id.", localThrowable);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    return i1;
  }
  
  private Calendar a(Calendar paramCalendar)
  {
    paramCalendar.clear(10);
    paramCalendar.clear(11);
    paramCalendar.clear(12);
    paramCalendar.clear(13);
    paramCalendar.clear(14);
    return paramCalendar;
  }
  
  private FXRate a(FXRate paramFXRate)
  {
    if (paramFXRate == null) {
      return null;
    }
    FXRate localFXRate = new FXRate(paramFXRate.getLocale());
    localFXRate.setTargetCurrencyCode(paramFXRate.getTargetCurrencyCode());
    localFXRate.setBuyPrice(paramFXRate.getBuyPrice());
    localFXRate.setSellPrice(paramFXRate.getSellPrice());
    DateTime localDateTime = new DateTime(paramFXRate.getAsOfDate(), paramFXRate.getLocale());
    localFXRate.setAsOfDate(localDateTime);
    return localFXRate;
  }
  
  private class a
  {
    private HashMap jdField_for = new HashMap(5);
    private HashMap jdField_do = new HashMap();
    private HashMap jdField_int = new HashMap();
    private long a = 0L;
    private ReadWriteLock jdField_if = new ReadWriteLock();
    
    a()
    {
      jdField_if();
    }
    
    private void jdField_if()
    {
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_for.clear();
          this.jdField_do.clear();
          this.jdField_int.clear();
          Calendar localCalendar = Calendar.getInstance();
          FXAdapter.this.a(localCalendar);
          this.jdField_for.put(localCalendar.clone(), this.jdField_do);
          localCalendar.add(5, -1);
          this.jdField_for.put(localCalendar.clone(), this.jdField_int);
          localCalendar.add(5, 2);
          this.a = localCalendar.getTime().getTime();
        }
      }
      finally
      {
        this.jdField_if.releaseLock();
      }
    }
    
    private boolean a()
    {
      return System.currentTimeMillis() >= this.a;
    }
    
    FXRate a(String paramString1, String paramString2, DateTime paramDateTime)
    {
      return a(paramString1, paramString2, paramDateTime, 0, "0");
    }
    
    FXRate a(String paramString1, String paramString2, DateTime paramDateTime, int paramInt, String paramString3)
    {
      int i = 1;
      this.jdField_if.getReadLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          localObject1 = null;
          return localObject1;
        }
        Object localObject1 = (HashMap)this.jdField_for.get(FXAdapter.this.a((Calendar)paramDateTime.clone()));
        if (localObject1 == null)
        {
          localObject2 = null;
          return localObject2;
        }
        Object localObject2 = paramString1 + "|" + paramInt + "|" + paramString3;
        HashMap localHashMap = (HashMap)((HashMap)localObject1).get(localObject2);
        if (localHashMap == null)
        {
          localFXRate = null;
          return localFXRate;
        }
        FXRate localFXRate = FXAdapter.this.a((FXRate)localHashMap.get(paramString2));
        return localFXRate;
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    FXRate a(String paramString1, String paramString2)
    {
      return a(paramString1, paramString2, 0, "0");
    }
    
    FXRate a(String paramString1, String paramString2, int paramInt, String paramString3)
    {
      int i = 1;
      this.jdField_if.getReadLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          localObject1 = null;
          return localObject1;
        }
        Object localObject1 = (HashMap)this.jdField_for.get(FXAdapter.this.a(Calendar.getInstance()));
        if (localObject1 == null)
        {
          localObject2 = null;
          return localObject2;
        }
        Object localObject2 = paramString1 + "|" + paramInt + "|" + paramString3;
        HashMap localHashMap = (HashMap)((HashMap)localObject1).get(localObject2);
        if (localHashMap == null)
        {
          localFXRate = null;
          return localFXRate;
        }
        FXRate localFXRate = FXAdapter.this.a((FXRate)localHashMap.get(paramString2));
        return localFXRate;
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    void jdField_do(String paramString, FXRate paramFXRate)
    {
      int i = 1;
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          return;
        }
        DateTime localDateTime = paramFXRate.getAsOfDate();
        HashMap localHashMap1 = (HashMap)this.jdField_for.get(FXAdapter.this.a((Calendar)localDateTime.clone()));
        if (localHashMap1 == null) {
          return;
        }
        String str1 = paramString + "|" + paramFXRate.getObjectType() + "|" + paramFXRate.getObjectID();
        HashMap localHashMap2 = (HashMap)localHashMap1.get(str1);
        if (localHashMap2 == null)
        {
          localHashMap2 = new HashMap();
          localHashMap1.put(str1, localHashMap2);
        }
        String str2 = paramFXRate.getTargetCurrencyCode();
        if (!localHashMap2.containsKey(str2)) {
          localHashMap2.put(str2, FXAdapter.this.a(paramFXRate));
        }
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    void jdField_if(String paramString, FXRate paramFXRate)
    {
      int i = 1;
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          return;
        }
        DateTime localDateTime = paramFXRate.getAsOfDate();
        HashMap localHashMap1 = (HashMap)this.jdField_for.get(FXAdapter.this.a((Calendar)localDateTime.clone()));
        if (localHashMap1 == null) {
          return;
        }
        String str = paramString + "|" + paramFXRate.getObjectType() + "|" + paramFXRate.getObjectID();
        HashMap localHashMap2 = (HashMap)localHashMap1.get(str);
        if (localHashMap2 == null) {
          return;
        }
        localHashMap2.remove(paramFXRate.getTargetCurrencyCode());
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    void a(String paramString)
    {
      a(paramString, 0, "0");
    }
    
    void a(String paramString1, int paramInt, String paramString2)
    {
      int i = 1;
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          return;
        }
        String str = paramString1 + "|" + paramInt + "|" + paramString2;
        this.jdField_do.remove(str);
        this.jdField_int.remove(str);
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    void a(String paramString, DateTime paramDateTime)
    {
      a(paramString, paramDateTime, 0, "0");
    }
    
    void a(String paramString1, DateTime paramDateTime, int paramInt, String paramString2)
    {
      int i = 1;
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          return;
        }
        HashMap localHashMap = (HashMap)this.jdField_for.get(FXAdapter.this.a((Calendar)paramDateTime.clone()));
        String str = paramString1 + "|" + paramInt + "|" + paramString2;
        if (localHashMap != null) {
          localHashMap.remove(str);
        }
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    void a(String paramString, FXRate paramFXRate)
    {
      int i = 1;
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          return;
        }
        jdField_for(paramString, paramFXRate);
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    void a(String paramString, FXRates paramFXRates)
    {
      int i = 1;
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          return;
        }
        Iterator localIterator = paramFXRates.iterator();
        while (localIterator.hasNext())
        {
          FXRate localFXRate = (FXRate)localIterator.next();
          jdField_for(paramString, localFXRate);
        }
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    private void jdField_for(String paramString, FXRate paramFXRate)
    {
      DateTime localDateTime = paramFXRate.getAsOfDate();
      HashMap localHashMap1 = (HashMap)this.jdField_for.get(FXAdapter.this.a((Calendar)localDateTime.clone()));
      if (localHashMap1 == null) {
        return;
      }
      String str = paramString + "|" + paramFXRate.getObjectType() + "|" + paramFXRate.getObjectID();
      HashMap localHashMap2 = (HashMap)localHashMap1.get(str);
      if (localHashMap2 == null) {
        return;
      }
      FXRate localFXRate = (FXRate)localHashMap2.get(paramFXRate.getTargetCurrencyCode());
      if (localFXRate != null)
      {
        localFXRate.setBuyPrice(paramFXRate.getBuyPrice());
        localFXRate.setSellPrice(paramFXRate.getSellPrice());
      }
    }
    
    void a(int paramInt)
    {
      int i = 1;
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          return;
        }
        if (paramInt == 0) {
          this.jdField_int.clear();
        }
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
    
    void a(int paramInt1, int paramInt2, String paramString)
    {
      int i = 1;
      this.jdField_if.getWriteLock();
      try
      {
        if (a())
        {
          this.jdField_if.releaseLock();
          i = 0;
          jdField_if();
          return;
        }
        if (paramInt1 == 0) {
          this.jdField_int.clear();
        }
      }
      finally
      {
        if (i != 0) {
          this.jdField_if.releaseLock();
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.fx.adapter.FXAdapter
 * JD-Core Version:    0.7.0.1
 */