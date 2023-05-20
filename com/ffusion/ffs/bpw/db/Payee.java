package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class Payee
  implements DBConsts, FFSConst, OFXConsts, BPWResource
{
  private PayeeInfo aH;
  private static int aG;
  private static Hashtable aE = new Hashtable();
  private static final int DEFAULT_BATCH_SIZE = 1000;
  private static final int aF = 500;
  
  public Payee()
  {
    this.aH = new PayeeInfo();
  }
  
  public Payee(PayeeInfo paramPayeeInfo)
  {
    this.aH = paramPayeeInfo;
  }
  
  public void setPayeeInfo(PayeeInfo paramPayeeInfo)
  {
    this.aH = paramPayeeInfo;
  }
  
  public PayeeInfo getPayeeInfo()
  {
    return this.aH;
  }
  
  public void setPayeeID()
    throws BPWException
  {
    this.aH.PayeeID = DBUtil.getNextIndexString("PayeeID");
  }
  
  public void setPayeeID(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    try
    {
      this.aH.PayeeID = DBUtil.getNextIndexString(paramFFSConnectionHolder, "PayeeID");
    }
    catch (FFSException localFFSException)
    {
      throw new BPWException(FFSDebug.stackTrace(localFFSException));
    }
  }
  
  public void setPayeeID(String paramString)
  {
    this.aH.PayeeID = paramString;
  }
  
  public String getPayeeID()
  {
    return this.aH.PayeeID;
  }
  
  public void setExtdPayeeID(String paramString)
  {
    this.aH.ExtdPayeeID = paramString;
  }
  
  public String getExtdPayeeID()
  {
    return this.aH.ExtdPayeeID;
  }
  
  public void setPayeeLevelType(String paramString)
  {
    this.aH.PayeeLevelType = paramString;
  }
  
  public void setPayeeType(int paramInt)
  {
    this.aH.PayeeType = paramInt;
  }
  
  public int getPayeeType()
  {
    return this.aH.PayeeType;
  }
  
  public void setLinkPayeeID(String paramString)
  {
    this.aH.LinkPayeeID = paramString;
  }
  
  public String getLinkPayeeID()
  {
    return this.aH.LinkPayeeID;
  }
  
  public void setRouteID(int paramInt)
  {
    this.aH.setRouteID(paramInt);
  }
  
  public int getRouteID()
  {
    return this.aH.getRouteID();
  }
  
  public void setStatus(String paramString)
  {
    this.aH.Status = paramString;
  }
  
  public String getStatus()
  {
    return this.aH.Status;
  }
  
  public void setTranID(String paramString)
  {
    this.aH.TranID = paramString;
  }
  
  public String getTranID()
  {
    return this.aH.TranID;
  }
  
  public static void setBatchSize(int paramInt)
  {
    if (paramInt <= 0) {
      aG = 1000;
    } else {
      aG = paramInt;
    }
  }
  
  public static void clearCache()
  {
    aE.clear();
  }
  
  public void mapPayeeInfo(TypePayeeV1Aggregate paramTypePayeeV1Aggregate)
  {
    this.aH.PayeeName = paramTypePayeeV1Aggregate.Name;
    this.aH.Addr1 = paramTypePayeeV1Aggregate.AddressCm.Addr1;
    if (paramTypePayeeV1Aggregate.AddressCm.SubAddressCmExists)
    {
      this.aH.Addr2 = paramTypePayeeV1Aggregate.AddressCm.SubAddressCm.Addr2;
      if (paramTypePayeeV1Aggregate.AddressCm.SubAddressCm.Addr3Exists) {
        this.aH.Addr3 = paramTypePayeeV1Aggregate.AddressCm.SubAddressCm.Addr3;
      }
    }
    this.aH.City = paramTypePayeeV1Aggregate.City;
    this.aH.State = paramTypePayeeV1Aggregate.State;
    this.aH.Phone = paramTypePayeeV1Aggregate.Phone;
    this.aH.Zipcode = paramTypePayeeV1Aggregate.PostalCode;
    if (paramTypePayeeV1Aggregate.CountryExists) {
      this.aH.Country = paramTypePayeeV1Aggregate.Country;
    }
  }
  
  public void mapPayeeInfo(TypePayeeAggregate paramTypePayeeAggregate)
  {
    this.aH.PayeeName = paramTypePayeeAggregate.Name;
    this.aH.Addr1 = paramTypePayeeAggregate.AddressCm.Addr1;
    if (paramTypePayeeAggregate.AddressCm.SubAddressCmExists)
    {
      this.aH.Addr2 = paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr2;
      if (paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3Exists) {
        this.aH.Addr3 = paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3;
      }
    }
    this.aH.City = paramTypePayeeAggregate.City;
    this.aH.State = paramTypePayeeAggregate.State;
    this.aH.Phone = paramTypePayeeAggregate.Phone;
    this.aH.Zipcode = paramTypePayeeAggregate.PostalCode;
    if (paramTypePayeeAggregate.CountryExists) {
      this.aH.Country = paramTypePayeeAggregate.Country;
    }
  }
  
  private static int jdMethod_try()
  {
    int i = 500;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      FFSProperties localFFSProperties = localPropertyConfig.otherProperties;
      String str = localFFSProperties.getProperty("PayeeReturnSize", String.valueOf(500));
      i = Integer.parseInt(str.trim());
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to get payee return size.  Only the first 500 results will be returned.", 3);
      FFSDebug.log("Error: " + FFSDebug.stackTrace(localException), 6);
    }
    return i;
  }
  
  public static int findRoute(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findRoute start, payeeID=" + paramString, 6);
    int i = -1;
    String str = "SELECT ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeID = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(12);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findRoute failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findRoute failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.findRoute done, payeeID=" + paramString, 6);
    return i;
  }
  
  public static String findLinkPayeeID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findLinkPayeeID start, payeeID=" + paramString, 6);
    String str1 = null;
    String str2 = "SELECT ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeID = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str1 = localFFSResultSet.getColumnString(13);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findLinkPayeeID failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findLinkPayeeID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.findLinkPayeeID done, payeeID=" + paramString, 6);
    return str1;
  }
  
  public static boolean hasPendingLink(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.hasPendingLink start, payeeID=" + paramString, 6);
    String str1 = "SELECT CustomerID,Status,PayeeListID FROM BPW_CustomerPayee WHERE PayeeID=?";
    String str2 = "SELECT PayeeID, Status FROM BPW_Payee WHERE LinkPayeeID=?";
    Object[] arrayOfObject = { paramString };
    int i = 0;
    int j = 0;
    FFSResultSet localFFSResultSet1 = null;
    FFSResultSet localFFSResultSet2 = null;
    try
    {
      localFFSResultSet1 = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet1.getNextRow())
      {
        String str3 = localFFSResultSet1.getColumnString(2);
        if ((!str3.equals("CANC")) && (!str3.equals("PENDING")) && (!str3.equals("CLOSED"))) {
          i = 1;
        }
      }
      localFFSResultSet2 = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet2.getNextRow()) {
        j = 1;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.hasPendingLink failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet1 != null)
        {
          localFFSResultSet1.close();
          localFFSResultSet1 = null;
        }
        if (localFFSResultSet2 != null)
        {
          localFFSResultSet2.close();
          localFFSResultSet2 = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.hasPendingLink failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.hasPendingLink done, payeeID=" + paramString, 6);
    return (i != 0) || (j != 0);
  }
  
  public boolean storeToDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = "_NULL_";
    String str2 = "en_US";
    Integer localInteger = new Integer(0);
    FFSDebug.log("Payee.storeToDB start, payeeID=" + this.aH.PayeeID, 6);
    if (this.aH.DaysToPay < -1) {
      throw new BPWException("Invalid value of DaysToPay", 1001000);
    }
    String str4 = paramFFSConnectionHolder.conn.getDatabaseType();
    String str3;
    if (str4.startsWith("ASE")) {
      str3 = "INSERT INTO BPW_Payee( PayeeID, ExtdPayeeID, PayeeType, PayeeName, Encoding, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID, FIId )VALUES(?,?,?,?,SOUNDEX(CONVERT(CHAR(50),?)),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    } else {
      str3 = "INSERT INTO BPW_Payee(  PayeeID, ExtdPayeeID, PayeeType, PayeeName, Encoding,  Addr1, Addr2, Addr3, City, State,  Zipcode, Country, Phone, RouteID, LinkPayeeID,  Status, DisbursementType, PayeeLevelType, Nickname, ContactName,  DaysToPay, Submitdate, TranID, FIId ) VALUES(?,?,?,?,SOUNDEX(CAST(? AS CHAR(50))),?,?,?,?,?, ?,?,?,?,?,                          ?,?,?,?,?, ?,?,?,?)";
    }
    String str5 = (this.aH.ExtdPayeeID == null) || (this.aH.ExtdPayeeID.length() == 0) ? "0" : this.aH.ExtdPayeeID;
    if ((this.aH.PayeeName != null) && (!this.aH.PayeeName.trim().equals(""))) {
      str1 = this.aH.PayeeName;
    }
    if (this.aH.PayeeRouteInfo != null) {
      localInteger = new Integer(getRouteID());
    }
    Object[] arrayOfObject = { this.aH.PayeeID, str5, new Integer(this.aH.PayeeType), str1.toUpperCase(), str1, this.aH.Addr1, this.aH.Addr2, this.aH.Addr3, this.aH.City, this.aH.State, this.aH.Zipcode, this.aH.Country, this.aH.Phone, localInteger, this.aH.LinkPayeeID, this.aH.Status, this.aH.DisbursementType, this.aH.PayeeLevelType, null, this.aH.ContactName, new Integer(this.aH.DaysToPay), FFSUtil.getDateString(), this.aH.TranID, this.aH.FIID };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.storeToDB failed: " + localException.toString());
      localException.printStackTrace();
      throw new BPWException(localException.toString());
    }
    if ((this.aH.PayeeName != null) && (!this.aH.PayeeName.trim().equals(""))) {
      storePayeeToSmartPayee(paramFFSConnectionHolder, this.aH.PayeeID, str1, str2);
    }
    if ((this.aH.PayeeNamesI18N != null) && (!this.aH.PayeeNamesI18N.isEmpty()))
    {
      Set localSet = this.aH.PayeeNamesI18N.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        str2 = (String)localIterator.next();
        str1 = (String)this.aH.PayeeNamesI18N.get(str2);
        if ((str1 != null) && (!str1.trim().equals(""))) {
          storePayeeToSmartPayee(paramFFSConnectionHolder, this.aH.PayeeID, str1, str2);
        }
      }
    }
    FFSDebug.log("Payee.storeToDB done, payeeID=" + this.aH.PayeeID, 6);
    return true;
  }
  
  public static void storePayeeToSmartPayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("Payee.storePayeeToSmartPayee start, payeeID=" + paramString1, 6);
    String str1 = "INSERT INTO BPW_SmartSearch(  PayeeID, NoSpaceName, NoPuctName, NoPunctSpaceName, Language, PayeeName) VALUES(?,?,?,?,?,?)";
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str2 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : localPropertyConfig.otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    String str3 = removePunct(paramString2.toUpperCase(), str2);
    String str4 = removeSpaces(paramString2.toUpperCase());
    String str5 = removeSpaces(str3);
    Object[] arrayOfObject = { paramString1, str4, str3, str5 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.storeToDB failed to add info to smartPayee table: " + FFSDebug.stackTrace(localException));
      localException.printStackTrace();
      throw new BPWException(FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("Payee.storePayeeToSmartPayee done, payeeID=" + paramString1, 6);
  }
  
  public static void storePayeeToSmartPayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws BPWException
  {
    FFSDebug.log("Payee.storePayeeToSmartPayee start, payeeID=" + paramString1, 6);
    String str1 = "INSERT INTO BPW_SmartSearch(  PayeeID, NoSpaceName, NoPuctName, NoPunctSpaceName, Language, PayeeName) VALUES(?,?,?,?,?,?)";
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str2 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : localPropertyConfig.otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    String str3 = removePunct(paramString2.toUpperCase(), str2);
    String str4 = removeSpaces(paramString2.toUpperCase());
    String str5 = removeSpaces(str3);
    Object[] arrayOfObject = { paramString1, str4, str3, str5, paramString3, paramString2 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.storeToDB failed to add info to smartPayee table: " + FFSDebug.stackTrace(localException));
      localException.printStackTrace();
      throw new BPWException(FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("Payee.storePayeeToSmartPayee done, payeeID=" + paramString1, 6);
  }
  
  public static void updateSmartPayeeDB(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("Payee.updateSmartPayeeDB start, payeeID=" + paramString1, 6);
    String str1 = "UPDATE BPW_SmartSearch SET  NoSpaceName = ?, NoPuctName = ?, NoPunctSpaceName = ?  WHERE PayeeID = ?";
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str2 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : localPropertyConfig.otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    String str3 = removePunct(paramString2.toUpperCase(), str2);
    String str4 = removeSpaces(paramString2.toUpperCase());
    String str5 = removeSpaces(str3);
    Object[] arrayOfObject = { str4, str3, str5, paramString1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.updateSmartPayeeDB failed to add info to smartPayee table: " + FFSDebug.stackTrace(localException));
      localException.printStackTrace();
      throw new BPWException(FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("Payee.updateSmartPayeeDB done, payeeID=" + paramString1, 6);
  }
  
  public static void updateSmartPayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("Payee.updateSmartPayee start, payeeID=" + paramString1, 6);
    String str1 = "SELECT  NoPunctSpaceName FROM BPW_SmartSearch WHERE PayeeID = ?";
    FFSResultSet localFFSResultSet = null;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str2 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : localPropertyConfig.otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    String str3 = removePunct(paramString2.toUpperCase(), str2);
    String str4 = removeSpaces(str3);
    Object[] arrayOfObject = { paramString1 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      String str5 = null;
      if (localFFSResultSet.getNextRow()) {
        str5 = localFFSResultSet.getColumnString(1);
      }
      if (str5 == null) {
        storePayeeToSmartPayee(paramFFSConnectionHolder, paramString1, paramString2);
      } else if (!str5.equals(str4)) {
        updateSmartPayeeDB(paramFFSConnectionHolder, paramString1, paramString2);
      } else {
        FFSDebug.log("Payee.PayeeId: " + paramString1 + ", name is not changed no need to update smart table", 6);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.updateSmartPayee failed to add info to smartPayee table: " + FFSDebug.stackTrace(localException1));
      localException1.printStackTrace();
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.updateSmartPayee failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.updateSmartPayee done, payeeID=" + paramString1, 6);
  }
  
  public static void deleteSmartPayeeFromDB(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("Payee.deleteSmartPayeeFromDB start, payeeID=" + paramString, 6);
    String str = "DELETE FROM BPW_SmartSearch WHERE PayeeID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.deleteSmartPayeeFromDB failed:" + FFSDebug.stackTrace(localException));
      throw new BPWException(FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("Payee.deleteSmartPayeeFromDB done, payeeID=" + paramString, 6);
  }
  
  public void removeFromDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.removeFromDB start, payeeID=" + this.aH.PayeeID, 6);
    String str = "DELETE FROM BPW_Payee WHERE PayeeID=?";
    Object[] arrayOfObject = { this.aH.PayeeID };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.removeFromDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    deleteSmartPayeeFromDB(paramFFSConnectionHolder, this.aH.PayeeID);
    PayeeEditMask.deletePayeeEditMask(this.aH.PayeeID, paramFFSConnectionHolder);
    FFSDebug.log("Payee.removeFromDB done, payeeID=" + this.aH.PayeeID, 6);
  }
  
  public static PayeeInfo findPayeeByID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findPayeeByID start, payeeID=" + paramString, 6);
    PayeeInfo localPayeeInfo = null;
    String str = "SELECT ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeID = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = paramString;
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(2);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(3);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.City = localFFSResultSet.getColumnString(7);
        localPayeeInfo.State = localFFSResultSet.getColumnString(8);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(11);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(12));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(13);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(14);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(15);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(17);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(18);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(19));
        if (localFFSResultSet.wasNull()) {
          localPayeeInfo.DaysToPay = -1;
        }
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(20);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(21);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findPayeeByID failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.findPayeeByID done, payeeID=" + (localPayeeInfo == null ? "null" : localPayeeInfo.PayeeID), 6);
    return localPayeeInfo;
  }
  
  public static PayeeInfo findGlobalPayeeByID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findGlobalPayeeByID start, payeeID=" + paramString, 6);
    PayeeInfo localPayeeInfo = null;
    String str = "SELECT ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeID = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = paramString;
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(2);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(3);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.City = localFFSResultSet.getColumnString(7);
        localPayeeInfo.State = localFFSResultSet.getColumnString(8);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(11);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(12));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(13);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(14);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(15);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(17);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(18);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(19));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(20);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(21);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findGlobalPayeeByID failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findGlobalPayeeByID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.findGlobalPayeeByID done, payeeID=" + (localPayeeInfo == null ? "null" : localPayeeInfo.PayeeID), 6);
    return localPayeeInfo;
  }
  
  public static PayeeInfo findPayeeByExtendedID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findPayeeByExtendedID start, extdPayeeID=" + paramString, 6);
    String str = "SELECT PayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE ExtdPayeeID = ?";
    Object[] arrayOfObject = { paramString };
    PayeeInfo localPayeeInfo = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = paramString;
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(2);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(3);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.City = localFFSResultSet.getColumnString(7);
        localPayeeInfo.State = localFFSResultSet.getColumnString(8);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(11);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(12));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(13);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(14);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(15);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(17);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(18);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(19));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(20);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(21);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findPayeeByExtendedID failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByExtendedID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.findPayeeByExtendedID done, PayeeID=" + (localPayeeInfo == null ? "null" : localPayeeInfo.PayeeID), 6);
    return localPayeeInfo;
  }
  
  public static boolean isBatchDone(String paramString)
  {
    return aE.get(paramString) == null;
  }
  
  public static void clearBatch(String paramString)
  {
    try
    {
      if (aE.get(paramString) != null)
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)aE.get(paramString);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        aE.remove(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.clearBatch failed:" + localException.toString());
    }
  }
  
  public static PayeeInfo[] findPayeesByStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("Payee.findPayeesByStatus start, status=" + paramString, 6);
    Vector localVector = new Vector();
    String str1 = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE Status=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      String str2 = paramString;
      if (aE.get(str2) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
        aE.put(str2, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)aE.get(str2);
      }
      while (localFFSResultSet.getNextRow())
      {
        PayeeInfo localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(3);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(7);
        localPayeeInfo.City = localFFSResultSet.getColumnString(8);
        localPayeeInfo.State = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(11);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(12);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(13));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(14);
        localPayeeInfo.Status = paramString;
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(15);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(17);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(18);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(19));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(20);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(21);
        localVector.addElement(localPayeeInfo);
        i++;
        if (i == aG)
        {
          FFSDebug.log("Payee.findPayeesByStatus done, status=" + paramString + ",number=" + localVector.size(), 6);
          PayeeInfo[] arrayOfPayeeInfo2 = (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
          return arrayOfPayeeInfo2;
        }
      }
      localFFSResultSet.close();
      aE.remove(paramString);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      if (aE != null) {
        aE.remove(paramString);
      }
      FFSDebug.log("*** Payee.findPayeesByStatus failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    finally {}
    PayeeInfo[] arrayOfPayeeInfo1 = (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
    FFSDebug.log("Payee.findPayeesByStatus done, status=" + paramString + ",number=" + localVector.size(), 6);
    return arrayOfPayeeInfo1;
  }
  
  public static PayeeInfo[] findPayeeByStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findPayeeByStatus start, status=" + paramString, 6);
    if (paramString == null) {
      return null;
    }
    Vector localVector = new Vector();
    String str = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE Status=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        PayeeInfo localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(3);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(7);
        localPayeeInfo.City = localFFSResultSet.getColumnString(8);
        localPayeeInfo.State = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(11);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(12);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(13));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(14);
        localPayeeInfo.Status = paramString;
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(15);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(17);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(18);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(19));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(20);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(21);
        localVector.addElement(localPayeeInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findPayeeByStatus failed: " + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByStatus failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.findPayeeByStatus done, status=" + paramString + ", found payees:" + localVector.size(), 6);
    return (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
  }
  
  public void updateRouteID(int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.updateRouteID start, routeID=" + paramInt, 6);
    String str = "UPDATE BPW_Payee SET RouteID = ? WHERE PayeeID = ?";
    Object[] arrayOfObject = { new Integer(paramInt), this.aH.PayeeID };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.updateRouteID failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("Payee.updateRouteID done, routeID=" + paramInt, 6);
  }
  
  public static void updateStatus(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.updateStatus start, payeeID=" + paramString1, 6);
    String str = "UPDATE BPW_Payee SET Status = ? WHERE PayeeID = ?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.updateStats failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("Payee.updateStatus done, payeeID=" + paramString1, 6);
  }
  
  public boolean update(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.update start, payeeID=" + this.aH.PayeeID, 6);
    String str1 = "_NULL_";
    String str2 = "en_US";
    Integer localInteger = new Integer(0);
    if ((this.aH.PayeeName != null) && (!this.aH.PayeeName.trim().equals(""))) {
      str1 = this.aH.PayeeName;
    }
    if (this.aH.DaysToPay < -1) {
      throw new BPWException("Invalid value of DaysToPay", 1001000);
    }
    String str4 = paramFFSConnectionHolder.conn.getDatabaseType();
    String str3;
    if (str4.startsWith("ASE")) {
      str3 = "UPDATE BPW_Payee SET PayeeID=?, ExtdPayeeID=?, PayeeType=?, PayeeName=?, Encoding=SOUNDEX(CONVERT(CHAR(50),?)), Addr1=?, Addr2=?, Addr3=?, City=?, State=?, Zipcode=?, Country=?, Phone=?, RouteID=?, LinkPayeeID=?, Status=?, DisbursementType=?, PayeeLevelType=?, Nickname=?, ContactName=?, DaysToPay =?, FIId=? WHERE PayeeID=?";
    } else {
      str3 = "UPDATE BPW_Payee SET PayeeID=?, ExtdPayeeID=?, PayeeType=?, PayeeName=?, Encoding=SOUNDEX(CAST(? AS CHAR(50))), Addr1=?, Addr2=?, Addr3=?, City=?, State=?, Zipcode=?, Country=?, Phone=?, RouteID=?, LinkPayeeID=?, Status=?, DisbursementType=?, PayeeLevelType=?, Nickname=?, ContactName=?, DaysToPay =?, FIId=? WHERE PayeeID=?";
    }
    String str5 = (this.aH.ExtdPayeeID == null) || (this.aH.ExtdPayeeID.length() == 0) ? "0" : this.aH.ExtdPayeeID;
    if (this.aH.PayeeRouteInfo != null) {
      localInteger = new Integer(this.aH.PayeeRouteInfo.RouteID);
    }
    Object[] arrayOfObject = { this.aH.PayeeID, str5, new Integer(this.aH.PayeeType), this.aH.PayeeName.toUpperCase(), this.aH.PayeeName, this.aH.Addr1, this.aH.Addr2, this.aH.Addr3, this.aH.City, this.aH.State, this.aH.Zipcode, this.aH.Country, this.aH.Phone, localInteger, this.aH.LinkPayeeID, this.aH.Status, this.aH.DisbursementType, this.aH.PayeeLevelType, null, this.aH.ContactName, new Integer(this.aH.DaysToPay), this.aH.FIID, this.aH.PayeeID };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Payee.update failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    deleteSmartPayeeFromDB(paramFFSConnectionHolder, this.aH.PayeeID);
    if ((this.aH.PayeeName != null) && (this.aH.PayeeName.length() != 0)) {
      storePayeeToSmartPayee(paramFFSConnectionHolder, this.aH.PayeeID, str1, str2);
    }
    if ((this.aH.PayeeNamesI18N != null) && (!this.aH.PayeeNamesI18N.isEmpty()))
    {
      Set localSet = this.aH.PayeeNamesI18N.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        str2 = (String)localIterator.next();
        str1 = (String)this.aH.PayeeNamesI18N.get(str2);
        if ((str1 != null) && (str1.length() != 0)) {
          storePayeeToSmartPayee(paramFFSConnectionHolder, this.aH.PayeeID, str1, str2);
        }
      }
    }
    FFSDebug.log("Payee.update done, payeeID=" + this.aH.PayeeID, 6);
    return true;
  }
  
  public String matchGlobalPayee(TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    mapPayeeInfo(paramTypePayeeV1Aggregate);
    PayeeInfo[] arrayOfPayeeInfo = findGlobalPayeeByName(this.aH.PayeeName, paramFFSConnectionHolder);
    String str = matchPayee(arrayOfPayeeInfo);
    return str;
  }
  
  public String matchGlobalPayee(TypePayeeAggregate paramTypePayeeAggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    mapPayeeInfo(paramTypePayeeAggregate);
    PayeeInfo[] arrayOfPayeeInfo = findGlobalPayeeByName(this.aH.PayeeName, paramFFSConnectionHolder);
    String str = matchPayee(arrayOfPayeeInfo);
    return str;
  }
  
  public String matchGlobalPayee(PayeeInfo paramPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.aH = paramPayeeInfo;
    PayeeInfo[] arrayOfPayeeInfo = findGlobalPayeeByName(this.aH.PayeeName, paramFFSConnectionHolder);
    String str = matchPayee(arrayOfPayeeInfo);
    return str;
  }
  
  public String[] matchPayees(TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    mapPayeeInfo(paramTypePayeeV1Aggregate);
    return a(paramFFSConnectionHolder);
  }
  
  public String[] matchPayees(PayeeInfo paramPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    this.aH = paramPayeeInfo;
    return a(paramFFSConnectionHolder);
  }
  
  public String[] matchPayees(TypePayeeAggregate paramTypePayeeAggregate, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    mapPayeeInfo(paramTypePayeeAggregate);
    return a(paramFFSConnectionHolder);
  }
  
  private String[] a(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    PayeeInfo[] arrayOfPayeeInfo = a(this.aH.PayeeName, "begin", this.aH.Addr1, this.aH.City, this.aH.State, paramFFSConnectionHolder);
    String[] arrayOfString = a(arrayOfPayeeInfo);
    return arrayOfString;
  }
  
  private String jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    setPayeeID();
    setStatus("NEW");
    setPayeeType(3);
    setPayeeLevelType("PERSONAL");
    setTranID(paramString);
    storeToDB(paramFFSConnectionHolder);
    return this.aH.PayeeID;
  }
  
  public String addPayeeNoMatch(PayeeInfo paramPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    setPayeeInfo(paramPayeeInfo);
    return jdMethod_do(paramFFSConnectionHolder, paramString);
  }
  
  public String addPayeeNoMatch(TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    mapPayeeInfo(paramTypePayeeV1Aggregate);
    return jdMethod_do(paramFFSConnectionHolder, paramString);
  }
  
  public String addPayeeNoMatch(TypePayeeAggregate paramTypePayeeAggregate, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    mapPayeeInfo(paramTypePayeeAggregate);
    return jdMethod_do(paramFFSConnectionHolder, paramString);
  }
  
  public String addPayee(TypePayeeV1Aggregate paramTypePayeeV1Aggregate, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    mapPayeeInfo(paramTypePayeeV1Aggregate);
    return addPayee(paramFFSConnectionHolder, paramString);
  }
  
  public String addPayee(PayeeInfo paramPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    this.aH = paramPayeeInfo;
    return addPayee(paramFFSConnectionHolder, paramString);
  }
  
  public String addPayee(TypePayeeAggregate paramTypePayeeAggregate, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    mapPayeeInfo(paramTypePayeeAggregate);
    return addPayee(paramFFSConnectionHolder, paramString);
  }
  
  public String addPayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    PayeeInfo[] arrayOfPayeeInfo = a(this.aH.PayeeName, "begin", this.aH.Addr1, this.aH.City, this.aH.State, paramFFSConnectionHolder);
    String str = matchPayee(arrayOfPayeeInfo);
    if (str == null)
    {
      setPayeeID();
      setStatus("NEW");
      setPayeeType(3);
      setPayeeLevelType("PERSONAL");
      setTranID(paramString);
      storeToDB(paramFFSConnectionHolder);
      return this.aH.PayeeID;
    }
    setPayeeID(str);
    return str;
  }
  
  public String matchPayee(PayeeInfo[] paramArrayOfPayeeInfo)
  {
    if ((paramArrayOfPayeeInfo == null) || (paramArrayOfPayeeInfo.length == 0)) {
      return null;
    }
    for (int i = 0; i < paramArrayOfPayeeInfo.length; i++) {
      if (a(paramArrayOfPayeeInfo[i]) == true) {
        return paramArrayOfPayeeInfo[i].PayeeID;
      }
    }
    return null;
  }
  
  private String[] a(PayeeInfo[] paramArrayOfPayeeInfo)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramArrayOfPayeeInfo != null) && (paramArrayOfPayeeInfo.length > 0)) {
      for (int i = 0; i < paramArrayOfPayeeInfo.length; i++) {
        if (a(paramArrayOfPayeeInfo[i]) == true) {
          localArrayList.add(paramArrayOfPayeeInfo[i].PayeeID);
        }
      }
    }
    String[] arrayOfString = (String[])localArrayList.toArray(new String[0]);
    return arrayOfString;
  }
  
  private boolean a(PayeeInfo paramPayeeInfo)
  {
    boolean bool = false;
    if ((paramPayeeInfo.Addr1 != null) && (paramPayeeInfo.Addr1.equalsIgnoreCase(this.aH.Addr1)) && (((paramPayeeInfo.Addr2 == null) && (this.aH.Addr2 == null)) || ((paramPayeeInfo.Addr2 != null) && (paramPayeeInfo.Addr2.equalsIgnoreCase(this.aH.Addr2)) && (((paramPayeeInfo.Addr3 == null) && (this.aH.Addr3 == null)) || ((paramPayeeInfo.Addr3 != null) && (paramPayeeInfo.Addr3.equalsIgnoreCase(this.aH.Addr3)) && (paramPayeeInfo.City != null) && (paramPayeeInfo.City.equalsIgnoreCase(this.aH.City)) && (paramPayeeInfo.State != null) && (paramPayeeInfo.State.equalsIgnoreCase(this.aH.State)) && (((paramPayeeInfo.Zipcode == null) && (this.aH.Zipcode == null)) || ((paramPayeeInfo.Zipcode != null) && (paramPayeeInfo.Zipcode.equalsIgnoreCase(this.aH.Zipcode)) && (paramPayeeInfo.Phone != null) && (paramPayeeInfo.Phone.equalsIgnoreCase(this.aH.Phone))))))))) {
      bool = true;
    }
    return bool;
  }
  
  public static PayeeInfo[] findPayeeByName(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findPayeeByName start, payeeName=" + paramString1, 6);
    if (paramString1 == null) {
      throw new BPWException("Payee.findPayeeByName: payeeName is null!");
    }
    int i = jdMethod_try();
    Vector localVector = new Vector();
    String str1 = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeName like ? ORDER BY PayeeName";
    String str2 = paramString1.toUpperCase().trim();
    String str3 = str2.trim();
    if (paramString2.equals("begin") == true) {
      str3 = str2 + '%';
    } else if (paramString2.equals("any") == true) {
      str3 = '%' + str2 + '%';
    } else if (paramString2.equals("end") == true) {
      str3 = '%' + str2;
    } else {
      str1 = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeName=? ORDER BY PayeeType ASC ";
    }
    Object[] arrayOfObject = { str3 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow() == true)
      {
        PayeeInfo localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString("ExtdPayeeID");
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt("PayeeType");
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString("PayeeName");
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString("Addr1");
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString("Addr2");
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString("Addr3");
        localPayeeInfo.City = localFFSResultSet.getColumnString("City");
        localPayeeInfo.State = localFFSResultSet.getColumnString("State");
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString("Zipcode");
        localPayeeInfo.Country = localFFSResultSet.getColumnString("Country");
        localPayeeInfo.Phone = localFFSResultSet.getColumnString("Phone");
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt("RouteID"));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString("LinkPayeeID");
        localPayeeInfo.Status = localFFSResultSet.getColumnString("Status");
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString("DisbursementType");
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString("PayeeLevelType");
        localPayeeInfo.NickName = localFFSResultSet.getColumnString("Nickname");
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString("ContactName");
        a(localPayeeInfo, localFFSResultSet.getColumnInt("DaysToPay"));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString("Submitdate");
        localPayeeInfo.TranID = localFFSResultSet.getColumnString("TranID");
        localVector.addElement(localPayeeInfo);
        if (localVector.size() >= i)
        {
          FFSDebug.log("Payee.findPayeeByName: payeeName=" + paramString1, 1);
          FFSDebug.log("Payee.findPayeeByName: The number of results retrieved exceeds the current limit on the number of items to be returned (" + i + ").", 1);
          FFSDebug.log("Payee.findPayeeByName: Please increase the limit using the 'PayeeReturnSize' configuration property or provide more specific search criteria.", 1);
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findPayeeByName failed: " + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByName failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.findPayeeByName done, payeeName=" + paramString1 + ", found payees:" + localVector.size(), 6);
    return (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
  }
  
  private PayeeInfo[] a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findPayeeByNameAndAddr start, payeeName=" + paramString1 + " address=" + paramString3 + " city=" + paramString4 + " state=" + paramString5, 6);
    if ((paramString1 == null) || (paramString1.trim().length() == 0)) {
      throw new BPWException("Payee.findPayeeByNameAndAddr: payeeName is null or empty!");
    }
    Vector localVector = new Vector();
    if ((paramString3 == null) || (paramString4 == null) || (paramString5 == null)) {
      return new PayeeInfo[0];
    }
    String str1 = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeName like ? AND UPPER(Addr1) = ? AND UPPER(City) = ? AND UPPER(State) = ? ORDER BY PayeeName";
    String str2 = paramString1.toUpperCase().trim();
    if (paramString2.equals("begin") == true) {
      str2 = str2 + '%';
    } else if (paramString2.equals("any") == true) {
      str2 = '%' + str2 + '%';
    } else if (paramString2.equals("end") == true) {
      str2 = '%' + str2;
    } else {
      str1 = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeName=? AND UPPER(Addr1) = ? AND UPPER(City) = ? AND UPPER(State) = ? ORDER BY PayeeType ASC";
    }
    Object[] arrayOfObject = { str2, paramString3.toUpperCase(), paramString4.toUpperCase(), paramString5.toUpperCase() };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow() == true)
      {
        PayeeInfo localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString("ExtdPayeeID");
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt("PayeeType");
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString("PayeeName");
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString("Addr1");
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString("Addr2");
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString("Addr3");
        localPayeeInfo.City = localFFSResultSet.getColumnString("City");
        localPayeeInfo.State = localFFSResultSet.getColumnString("State");
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString("Zipcode");
        localPayeeInfo.Country = localFFSResultSet.getColumnString("Country");
        localPayeeInfo.Phone = localFFSResultSet.getColumnString("Phone");
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt("RouteID"));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString("LinkPayeeID");
        localPayeeInfo.Status = localFFSResultSet.getColumnString("Status");
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString("DisbursementType");
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString("PayeeLevelType");
        localPayeeInfo.NickName = localFFSResultSet.getColumnString("Nickname");
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString("ContactName");
        a(localPayeeInfo, localFFSResultSet.getColumnInt("DaysToPay"));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString("Submitdate");
        localPayeeInfo.TranID = localFFSResultSet.getColumnString("TranID");
        localVector.addElement(localPayeeInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findPayeeByNameAndAddr failed: " + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByNameAndAddr failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.findPayeeByNameAndAddr done, payeeName=" + paramString1 + ", found payees:" + localVector.size(), 6);
    return (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
  }
  
  public static PayeeInfo[] findGlobalPayeeByName(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    return findGlobalPayeeByName(paramString, "exact", paramFFSConnectionHolder);
  }
  
  public static PayeeInfo[] findGlobalPayeeByName(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.findGlobalPayeeByName start, payeeName=" + paramString1, 6);
    if (paramString1 == null) {
      return null;
    }
    Vector localVector = new Vector();
    String str1 = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeName like ? AND PayeeType!=? ";
    String str2 = paramString1.toUpperCase().trim();
    if (paramString2.equals("begin") == true) {
      str2 = str2 + '%';
    } else if (paramString2.equals("any") == true) {
      str2 = '%' + str2 + '%';
    } else if (paramString2.equals("end") == true) {
      str2 = '%' + str2;
    } else {
      str1 = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeName=? AND PayeeType!=? ";
    }
    Object[] arrayOfObject = { str2, new Integer(3) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        PayeeInfo localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString("ExtdPayeeID");
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt("PayeeType");
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString("PayeeName");
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString("Addr1");
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString("Addr2");
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString("Addr3");
        localPayeeInfo.City = localFFSResultSet.getColumnString("City");
        localPayeeInfo.State = localFFSResultSet.getColumnString("State");
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString("Zipcode");
        localPayeeInfo.Country = localFFSResultSet.getColumnString("Country");
        localPayeeInfo.Phone = localFFSResultSet.getColumnString("Phone");
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt("RouteID"));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString("LinkPayeeID");
        localPayeeInfo.Status = localFFSResultSet.getColumnString("Status");
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString("DisbursementType");
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString("PayeeLevelType");
        localPayeeInfo.NickName = localFFSResultSet.getColumnString("Nickname");
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString("ContactName");
        a(localPayeeInfo, localFFSResultSet.getColumnInt("DaysToPay"));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString("Submitdate");
        localPayeeInfo.TranID = localFFSResultSet.getColumnString("TranID");
        localVector.addElement(localPayeeInfo);
      }
      localFFSResultSet.close();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.findGlobalPayeeByName failed: " + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
        }
        catch (Exception localException2) {}
      }
    }
    FFSDebug.log("Payee.findGlobalPayeeByName done, payeeName=" + paramString1 + ", found payees:" + localVector.size(), 6);
    return (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
  }
  
  public void closePayee(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    findPayeeByID(paramString, paramFFSConnectionHolder);
    if (getPayeeType() == 3) {
      updateStatus(paramString, "CLOSED", paramFFSConnectionHolder);
    }
  }
  
  public static PayeeInfo[] searchGlobalPayees(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws OutOfMemoryError, FFSException
  {
    FFSDebug.log("Payee.searchGlobalPayees start, payeeName=" + paramString, 6);
    if (paramString == null) {
      return null;
    }
    int i = jdMethod_try();
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str1 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "500" : localPropertyConfig.otherProperties.getProperty("PayeeSearchCriteria", "true");
    int j = 1;
    if ("any".equalsIgnoreCase(str1)) {
      j = 0;
    }
    Vector localVector = new Vector();
    PayeeInfo localPayeeInfo = null;
    String str2 = paramString.toUpperCase().trim();
    String str3 = str2.trim();
    if (j != 0) {
      str3 = str3 + "%";
    } else {
      str3 = "%" + str3 + "%";
    }
    String str4 = "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeType=? AND PayeeName like ? ORDER BY PayeeName";
    Object[] arrayOfObject = { new Integer(0), str3 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str4, arrayOfObject);
      while ((localFFSResultSet.getNextRow()) && (i-- > 0))
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(3);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(7);
        localPayeeInfo.City = localFFSResultSet.getColumnString(8);
        localPayeeInfo.State = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(11);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(12);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(13));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(14);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(15);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(17);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(18);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(19);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(20));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(21);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(22);
        localVector.addElement(localPayeeInfo);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localVector = null;
      throw localOutOfMemoryError;
    }
    catch (Exception localException1)
    {
      FFSDebug.log("***Payee.searchGlobalPayees failed: " + localException1.toString());
      throw new FFSException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.searchGlobalPayees failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.searchGlobalPayees done: vec.size()= " + localVector.size(), 6);
    return (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
  }
  
  public static PayeeInfo[] searchGlobalPayees(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws OutOfMemoryError, FFSException
  {
    FFSDebug.log("Payee.searchGlobalPayees start, payeeName=" + paramString1, 6);
    if (paramString1 == null) {
      return null;
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str1 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : localPropertyConfig.otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    Vector localVector = new Vector();
    PayeeInfo localPayeeInfo = null;
    String str2 = paramString1.toUpperCase().trim();
    String str3 = str2.trim();
    paramString2 = paramString2.trim();
    str3 = removeSpaces(removePunct(str3, str1));
    String str4 = "SELECT p.PayeeID, p.ExtdPayeeID, p.PayeeType, p.PayeeName,p.Addr1, p.Addr2, p.Addr3, p.City, p.State, p.Zipcode, p.Country, p.Phone, p.RouteID, p.LinkPayeeID, p.Status, p.DisbursementType, p.PayeeLevelType, p.Nickname, p.ContactName, p.DaysToPay, p.Submitdate, p.TranID FROM BPW_Payee p,BPW_SmartSearch s WHERE p.PayeeID=s.PayeeID  AND s.NoPunctSpaceName like ? AND s.Language = ? AND p.PayeeType =0";
    Object[] arrayOfObject = { str3, paramString2 };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str4, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(3);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(7);
        localPayeeInfo.City = localFFSResultSet.getColumnString(8);
        localPayeeInfo.State = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(11);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(12);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(13));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(14);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(15);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(17);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(18);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(19);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(20));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(21);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(22);
        localVector.addElement(localPayeeInfo);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localVector = null;
      throw localOutOfMemoryError;
    }
    catch (Exception localException1)
    {
      FFSDebug.log("***Payee.searchGlobalPayees failed: " + localException1.toString());
      throw new FFSException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.searchGlobalPayees failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.searchGlobalPayees done: vec.size()= " + localVector.size(), 6);
    return (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
  }
  
  public static PayeeInfo[] getLinkedPayees(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramFFSConnectionHolder == null) {
      return null;
    }
    LinkedList localLinkedList = new LinkedList();
    PayeeInfo localPayeeInfo = null;
    String str1 = "SELECT DISTINCT BPW_Payee.PayeeID, BPW_Payee.ExtdPayeeID, BPW_Payee.PayeeType, BPW_Payee.PayeeName, BPW_Payee.Addr1, BPW_Payee.Addr2, BPW_Payee.Addr3, BPW_Payee.City, BPW_Payee.State, BPW_Payee.Zipcode, BPW_Payee.Country, BPW_Payee.Phone, BPW_Payee.RouteID, BPW_Payee.LinkPayeeID, BPW_Payee.Status, BPW_Payee.DisbursementType, BPW_Payee.PayeeLevelType, BPW_Payee.Nickname, BPW_Payee.ContactName, BPW_Payee.DaysToPay, BPW_Payee.Submitdate, BPW_Payee.TranID FROM BPW_Payee, BPW_CustomerPayee WHERE BPW_Payee.PayeeID=BPW_CustomerPayee.PayeeID ORDER BY PayeeName";
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, null);
      int i = 1;
      while (localFFSResultSet.getNextRow())
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(3);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(7);
        localPayeeInfo.City = localFFSResultSet.getColumnString(8);
        localPayeeInfo.State = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(11);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(12);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(13));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(14);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(15);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(17);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(18);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(19);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(20));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(21);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(22);
        localLinkedList.add(localPayeeInfo);
        FFSDebug.log("******Added payee #: " + i++, 6);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      str2 = "Failed to get linked payee list. Error: " + FFSDebug.stackTrace(localOutOfMemoryError);
      FFSDebug.log(str2);
      throw new FFSException(localOutOfMemoryError, str2);
    }
    catch (Exception localException1)
    {
      String str2 = "Failed to get linked payee list. Error: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2);
      throw new FFSException(localException1, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getLinkedPaee failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getLinkedPayees done: vec.size()= " + localLinkedList.size(), 6);
    return (PayeeInfo[])localLinkedList.toArray(new PayeeInfo[0]);
  }
  
  public static PayeeInfo[] getMostUsedPayees(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    if ((paramFFSConnectionHolder == null) || (paramInt == 0)) {
      return null;
    }
    LinkedList localLinkedList = new LinkedList();
    PayeeInfo localPayeeInfo = null;
    String str1 = "SELECT DISTINCT BPW_Payee.PayeeID, BPW_Payee.ExtdPayeeID, BPW_Payee.PayeeType, BPW_Payee.PayeeName, BPW_Payee.Addr1, BPW_Payee.Addr2, BPW_Payee.Addr3, BPW_Payee.City, BPW_Payee.State, BPW_Payee.Zipcode, BPW_Payee.Country, BPW_Payee.Phone, BPW_Payee.RouteID, BPW_Payee.LinkPayeeID, BPW_Payee.Status, BPW_Payee.DisbursementType, BPW_Payee.PayeeLevelType, BPW_Payee.Nickname, BPW_Payee.ContactName, BPW_Payee.DaysToPay, BPW_Payee.Submitdate, BPW_Payee.TranID, (SELECT COUNT(*) FROM BPW_CustomerPayee WHERE BPW_Payee.PayeeID=BPW_CustomerPayee.PayeeID GROUP BY BPW_CustomerPayee.PayeeID) AS freq FROM BPW_Payee, BPW_CustomerPayee ORDER BY freq DESC";
    String str2 = "SELECT DISTINCT BPW_Payee.PayeeID, BPW_Payee.ExtdPayeeID, BPW_Payee.PayeeType, BPW_Payee.PayeeName, BPW_Payee.Addr1, BPW_Payee.Addr2, BPW_Payee.Addr3, BPW_Payee.City, BPW_Payee.State, BPW_Payee.Zipcode, BPW_Payee.Country, BPW_Payee.Phone, BPW_Payee.RouteID, BPW_Payee.LinkPayeeID, BPW_Payee.Status, BPW_Payee.DisbursementType, BPW_Payee.PayeeLevelType, BPW_Payee.Nickname, BPW_Payee.ContactName, BPW_Payee.DaysToPay, BPW_Payee.Submitdate, BPW_Payee.TranID, (SELECT COUNT(*) AS freq FROM BPW_CustomerPayee WHERE BPW_Payee.PayeeID=BPW_CustomerPayee.PayeeID GROUP BY BPW_CustomerPayee.PayeeID) FROM BPW_Payee, BPW_CustomerPayee ORDER BY freq DESC";
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    try
    {
      String str3 = paramFFSConnectionHolder.conn.getDatabaseType();
      if (str3.startsWith("DB2")) {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, null);
      } else {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, null);
      }
      while ((localFFSResultSet.getNextRow()) && (i < paramInt))
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(3);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(7);
        localPayeeInfo.City = localFFSResultSet.getColumnString(8);
        localPayeeInfo.State = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(11);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(12);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(13));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(14);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(15);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(17);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(18);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(19);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(20));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(21);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(22);
        localLinkedList.add(localPayeeInfo);
        i++;
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      str4 = "Failed to get most used payee list. Error: " + FFSDebug.stackTrace(localOutOfMemoryError);
      FFSDebug.log(str4);
      throw new FFSException(str4);
    }
    catch (Exception localException1)
    {
      String str4 = "Failed to get most used payee list. Error: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4);
      throw new FFSException(str4);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getMostUsedPayees failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getMostUsedPayees done: vec.size()= " + localLinkedList.size(), 6);
    return (PayeeInfo[])localLinkedList.toArray(new PayeeInfo[0]);
  }
  
  public static PayeeInfo[] getPreferedPayees(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    if ((paramFFSConnectionHolder == null) || (paramString == null)) {
      return null;
    }
    LinkedList localLinkedList = new LinkedList();
    PayeeInfo localPayeeInfo = null;
    String str1 = "SELECT DISTINCT BPW_Payee.PayeeID, BPW_Payee.ExtdPayeeID, BPW_Payee.PayeeType, BPW_Payee.PayeeName, BPW_Payee.Addr1, BPW_Payee.Addr2, BPW_Payee.Addr3, BPW_Payee.City, BPW_Payee.State, BPW_Payee.Zipcode, BPW_Payee.Country, BPW_Payee.Phone, BPW_Payee.RouteID, BPW_Payee.LinkPayeeID, BPW_Payee.Status, BPW_Payee.DisbursementType, BPW_Payee.PayeeLevelType, BPW_Payee.Nickname, BPW_Payee.ContactName, BPW_Payee.DaysToPay, BPW_Payee.Submitdate, BPW_Payee.TranID FROM BPW_Payee WHERE PayeeLevelType = ? ORDER BY BPW_Payee.PayeeName DESC";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(3);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(7);
        localPayeeInfo.City = localFFSResultSet.getColumnString(8);
        localPayeeInfo.State = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(11);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(12);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(13));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(14);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(15);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(17);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(18);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(19);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(20));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(21);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(22);
        localLinkedList.add(localPayeeInfo);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      str2 = "Failed to get prefered  payee list. Error: " + FFSDebug.stackTrace(localOutOfMemoryError);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    catch (Exception localException1)
    {
      String str2 = "Failed to get prefered payee list. Error: " + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getMostUsedPayees failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getMostUsedPayees done: vec.size()= " + localLinkedList.size(), 6);
    return (PayeeInfo[])localLinkedList.toArray(new PayeeInfo[0]);
  }
  
  public static String getPayeeNamesByPayeeId(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    FFSDebug.log("Payee.getPayeeNamesByPayeeId start, payeeId=" + paramString, 6);
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    String str = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT PayeeName FROM BPW_Payee WHERE PayeeID = ? ORDER BY PayeeName", arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str = localFFSResultSet.getColumnString(1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getPayeeNamesByPayeeId failed:" + FFSDebug.stackTrace(localException1));
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getPayeeNamesByPayeeId failed:" + FFSDebug.stackTrace(localException2));
      }
    }
    FFSDebug.log("Payee.getPayeeNamesByPayeeId done, payeeId=" + paramString, 6);
    return str;
  }
  
  public static String[] getPayeeNames(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception, OutOfMemoryError
  {
    FFSDebug.log("Payee.getPayeeNames start, substring=" + paramString, 6);
    int i = jdMethod_try();
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str1 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "500" : localPropertyConfig.otherProperties.getProperty("PayeeSearchCriteria", "true");
    int j = 1;
    if ("any".equalsIgnoreCase(str1)) {
      j = 0;
    }
    paramString = paramString.toUpperCase().trim();
    if (j != 0) {
      paramString = paramString + "%";
    } else {
      paramString = "%" + paramString + "%";
    }
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT PayeeName FROM BPW_Payee WHERE PayeeName like ? ORDER BY PayeeName", arrayOfObject);
      while ((localFFSResultSet.getNextRow()) && (i-- > 0))
      {
        String str2 = localFFSResultSet.getColumnString(1);
        if ((str2 != null) && (str2.length() > 0)) {
          localArrayList.add(str2);
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getPayeeNames failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getPayeeName failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getPayeeNames done, substring=" + paramString, 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static String[] getPayeeIDs(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception, OutOfMemoryError
  {
    FFSDebug.log("Payee.getPayeeIDs start, substring=" + paramString, 6);
    int i = jdMethod_try();
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str1 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "500" : localPropertyConfig.otherProperties.getProperty("PayeeSearchCriteria", "true");
    int j = 1;
    if ("any".equalsIgnoreCase(str1)) {
      j = 0;
    }
    paramString = paramString.toUpperCase().trim();
    if (j != 0) {
      paramString = paramString + "%";
    } else {
      paramString = "%" + paramString + "%";
    }
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT PayeeID FROM BPW_Payee WHERE PayeeName like ? ORDER BY PayeeID", arrayOfObject);
      while ((localFFSResultSet.getNextRow()) && (i-- > 0))
      {
        String str2 = localFFSResultSet.getColumnString(1);
        if ((str2 != null) && (str2.length() > 0)) {
          localArrayList.add(str2);
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getPayeeIDs failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getPayeeID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getPayeeIDs done, substring=" + paramString, 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static PayeeInfo[] getPayees(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception, OutOfMemoryError
  {
    FFSDebug.log("Payee.getPayees start, substring=" + paramString, 6);
    if (paramString == null) {
      return null;
    }
    int i = jdMethod_try();
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "500" : localPropertyConfig.otherProperties.getProperty("PayeeSearchCriteria", "true");
    int j = 1;
    if ("any".equalsIgnoreCase(str)) {
      j = 0;
    }
    paramString = paramString.toUpperCase().trim();
    if (j != 0) {
      paramString = paramString + "%";
    } else {
      paramString = "%" + paramString + "%";
    }
    Object[] arrayOfObject = { paramString };
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT PayeeID, ExtdPayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, TranID FROM BPW_Payee WHERE PayeeName like ? ORDER BY PayeeName", arrayOfObject);
      while ((localFFSResultSet.getNextRow()) && (i-- > 0))
      {
        PayeeInfo localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt(3);
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString(4);
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString(5);
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString(6);
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString(7);
        localPayeeInfo.City = localFFSResultSet.getColumnString(8);
        localPayeeInfo.State = localFFSResultSet.getColumnString(9);
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString(10);
        localPayeeInfo.Country = localFFSResultSet.getColumnString(11);
        localPayeeInfo.Phone = localFFSResultSet.getColumnString(12);
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt(13));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString(14);
        localPayeeInfo.Status = localFFSResultSet.getColumnString(15);
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString(16);
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString(17);
        localPayeeInfo.NickName = localFFSResultSet.getColumnString(18);
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString(19);
        a(localPayeeInfo, localFFSResultSet.getColumnInt(20));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(21);
        localPayeeInfo.TranID = localFFSResultSet.getColumnString(22);
        localArrayList.add(localPayeeInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getPayees failed: " + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getPayees failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getPayees done, substring=" + paramString + ", found payees:" + localArrayList.size(), 6);
    return (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
  }
  
  public static void updatePayee(FFSConnectionHolder paramFFSConnectionHolder, PayeeInfo paramPayeeInfo)
    throws Exception
  {
    try
    {
      Payee localPayee = new Payee(paramPayeeInfo);
      localPayee.setStatus("NEW");
      localPayee.update(paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      localException = localException;
      throw new FFSException(localException.toString());
    }
    finally {}
  }
  
  public static PayeeInfo findPayeeByID(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    return findPayeeByID(paramString, paramFFSConnectionHolder);
  }
  
  public static String removePunct(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      return paramString1;
    }
    if ((paramString2 == null) || (paramString2.length() == 0))
    {
      localObject = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      paramString2 = (localObject == null) || (((PropertyConfig)localObject).otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : ((PropertyConfig)localObject).otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    }
    Object localObject = new StringTokenizer(paramString1, paramString2);
    StringBuffer localStringBuffer = new StringBuffer(paramString1.length());
    try
    {
      while (((StringTokenizer)localObject).hasMoreTokens())
      {
        String str = ((StringTokenizer)localObject).nextToken();
        if ((str != null) && (str.length() >= 1)) {
          localStringBuffer.append(str);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localStringBuffer.toString();
  }
  
  public static String removeSpaces(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return paramString;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
    StringBuffer localStringBuffer = new StringBuffer(paramString.length());
    try
    {
      while (localStringTokenizer.hasMoreTokens())
      {
        String str = localStringTokenizer.nextToken().trim();
        if ((str != null) && (str.length() >= 1)) {
          localStringBuffer.append(str);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localStringBuffer.toString();
  }
  
  public static PayeeInfo[] getPayees(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws Exception
  {
    FFSDebug.log("Payee.getPayees start, payeeName=" + paramString, 6);
    if (paramString == null) {
      return null;
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : localPropertyConfig.otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    paramString = paramString.toUpperCase().trim();
    ArrayList localArrayList = new ArrayList();
    PayeeInfo[] arrayOfPayeeInfo = null;
    String[] arrayOfString = null;
    int i;
    PayeeInfo localPayeeInfo;
    switch (paramInt)
    {
    case 1: 
      paramString = removeSpaces(paramString);
      arrayOfString = getSmartPayeeIdNoSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 2: 
      paramString = removePunct(paramString, str);
      arrayOfString = getSmartPayeeIdNoPunct(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 3: 
      arrayOfPayeeInfo = findPayeeByName(paramString, "begin", paramFFSConnectionHolder);
      break;
    case 4: 
      paramString = removeSpaces(removePunct(paramString, str));
      arrayOfString = getSmartPayeeIdNoPunctSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 5: 
      paramString = removeSpaces(paramString);
      paramString = paramString + "%";
      arrayOfString = getSmartPayeeIdNoSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 6: 
      paramString = removePunct(paramString, str);
      paramString = paramString + "%";
      arrayOfString = getSmartPayeeIdNoPunct(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 7: 
      paramString = removeSpaces(removePunct(paramString, str));
      paramString = paramString + "%";
      arrayOfString = getSmartPayeeIdNoPunctSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 0: 
    default: 
      arrayOfPayeeInfo = findPayeeByName(paramString, "exact", paramFFSConnectionHolder);
    }
    return arrayOfPayeeInfo;
  }
  
  public static String[] getSmartPayeeIdNoPunct(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("Payee.getSmartPayeeIdNoPunct start, payeeName=" + paramString, 6);
    String str = null;
    if ((paramString != null) && (paramString.endsWith("%"))) {
      str = "SELECT PayeeID FROM BPW_SmartSearch WHERE NoPuctName like ? ";
    } else {
      str = "SELECT PayeeID FROM BPW_SmartSearch WHERE NoPuctName = ? ";
    }
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getSmartPayeeIdNoPunct failed:" + FFSDebug.stackTrace(localException1));
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getSmartPayeeIdNoPunct failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getSmartPayeeIdNoPunct done, payeeName=" + paramString, 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static String[] getSmartPayeeIdNoPunctSpace(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("Payee.getSmartPayeeIdNoPunctSpace start, payeeName=" + paramString, 6);
    String str = null;
    if ((paramString != null) && (paramString.endsWith("%"))) {
      str = "SELECT PayeeID FROM BPW_SmartSearch WHERE NoPunctSpaceName like ? ";
    } else {
      str = "SELECT PayeeID FROM BPW_SmartSearch WHERE NoPunctSpaceName = ? ";
    }
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getSmartPayeeIdNoPunctSpace failed:" + FFSDebug.stackTrace(localException1));
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getSmartPayeeIdNoPunctSpace failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getSmartPayeeIdNoPunctSpace done, payeeName=" + paramString, 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static String[] getSmartPayeeIdNoSpace(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("Payee.getSmartPayeeIdNoSpace start, payeeName=" + paramString, 6);
    String str = null;
    if ((paramString != null) && (paramString.endsWith("%"))) {
      str = "SELECT PayeeID FROM BPW_SmartSearch WHERE NoSpaceName like ? ";
    } else {
      str = "SELECT PayeeID FROM BPW_SmartSearch WHERE NoSpaceName = ? ";
    }
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString(1));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getSmartPayeeIdNoSpace failed:" + FFSDebug.stackTrace(localException1));
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.getSmartPayeeIdNoSpace failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getSmartPayeeIdNoSpace done, payeeName=" + paramString, 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static PayeeInfo[] getGlobalPayees(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws Exception
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : localPropertyConfig.otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    paramString = paramString.toUpperCase().trim();
    ArrayList localArrayList = new ArrayList();
    PayeeInfo[] arrayOfPayeeInfo = null;
    String[] arrayOfString = null;
    int i;
    PayeeInfo localPayeeInfo;
    switch (paramInt)
    {
    case 1: 
      paramString = removeSpaces(paramString);
      arrayOfString = getSmartPayeeIdNoSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findGlobalPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 2: 
      paramString = removePunct(paramString, str);
      arrayOfString = getSmartPayeeIdNoPunct(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findGlobalPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 3: 
      arrayOfPayeeInfo = findGlobalPayeeByName(paramString, "begin", paramFFSConnectionHolder);
      break;
    case 4: 
      paramString = removeSpaces(removePunct(paramString, str));
      arrayOfString = getSmartPayeeIdNoPunctSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findGlobalPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 5: 
      paramString = removeSpaces(paramString);
      paramString = paramString + "%";
      arrayOfString = getSmartPayeeIdNoSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findGlobalPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 6: 
      paramString = removePunct(paramString, str);
      paramString = paramString + "%";
      arrayOfString = getSmartPayeeIdNoPunct(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findGlobalPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 7: 
      paramString = removeSpaces(removePunct(paramString, str));
      paramString = paramString + "%";
      arrayOfString = getSmartPayeeIdNoPunctSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString != null) {
        for (i = 0; i < arrayOfString.length; i++)
        {
          localPayeeInfo = findGlobalPayeeByID(arrayOfString[i], paramFFSConnectionHolder);
          localArrayList.add(localPayeeInfo);
        }
      }
      arrayOfPayeeInfo = (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
      break;
    case 0: 
    default: 
      arrayOfPayeeInfo = findGlobalPayeeByName(paramString, "exact", paramFFSConnectionHolder);
    }
    return arrayOfPayeeInfo;
  }
  
  public static String[] getPayeeNames(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws Exception
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str1 = (localPropertyConfig == null) || (localPropertyConfig.otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : localPropertyConfig.otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
    paramString = paramString.toUpperCase().trim();
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    PayeeInfo[] arrayOfPayeeInfo = null;
    int i;
    String str2;
    switch (paramInt)
    {
    case 1: 
      paramString = removeSpaces(paramString);
      arrayOfString2 = getSmartPayeeIdNoSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString2 != null) {
        for (i = 0; i < arrayOfString2.length; i++)
        {
          str2 = getPayeeNamesByPayeeId(paramFFSConnectionHolder, arrayOfString2[i]);
          localArrayList.add(str2);
        }
      }
      arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      break;
    case 2: 
      paramString = removePunct(paramString, str1);
      arrayOfString2 = getSmartPayeeIdNoPunct(paramFFSConnectionHolder, paramString);
      if (arrayOfString2 != null) {
        for (i = 0; i < arrayOfString2.length; i++)
        {
          str2 = getPayeeNamesByPayeeId(paramFFSConnectionHolder, arrayOfString2[i]);
          localArrayList.add(str2);
        }
      }
      arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      break;
    case 3: 
      arrayOfPayeeInfo = findPayeeByName(paramString, "begin", paramFFSConnectionHolder);
      if (arrayOfPayeeInfo != null)
      {
        for (i = 0; i < arrayOfPayeeInfo.length; i++) {
          localArrayList.add(arrayOfPayeeInfo[i].PayeeName);
        }
        arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      }
      break;
    case 4: 
      paramString = removeSpaces(removePunct(paramString, str1));
      arrayOfString2 = getSmartPayeeIdNoPunctSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString2 != null) {
        for (i = 0; i < arrayOfString2.length; i++)
        {
          str2 = getPayeeNamesByPayeeId(paramFFSConnectionHolder, arrayOfString2[i]);
          localArrayList.add(str2);
        }
      }
      arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      break;
    case 5: 
      paramString = removeSpaces(paramString);
      paramString = paramString + "%";
      arrayOfString2 = getSmartPayeeIdNoSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString2 != null) {
        for (i = 0; i < arrayOfString2.length; i++)
        {
          str2 = getPayeeNamesByPayeeId(paramFFSConnectionHolder, arrayOfString2[i]);
          localArrayList.add(str2);
        }
      }
      arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      break;
    case 6: 
      paramString = removePunct(paramString, str1);
      paramString = paramString + "%";
      arrayOfString2 = getSmartPayeeIdNoPunct(paramFFSConnectionHolder, paramString);
      if (arrayOfString2 != null) {
        for (i = 0; i < arrayOfString2.length; i++)
        {
          str2 = getPayeeNamesByPayeeId(paramFFSConnectionHolder, arrayOfString2[i]);
          localArrayList.add(str2);
        }
      }
      arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      break;
    case 7: 
      paramString = removeSpaces(removePunct(paramString, str1));
      paramString = paramString + "%";
      arrayOfString2 = getSmartPayeeIdNoPunctSpace(paramFFSConnectionHolder, paramString);
      if (arrayOfString2 != null) {
        for (i = 0; i < arrayOfString2.length; i++)
        {
          str2 = getPayeeNamesByPayeeId(paramFFSConnectionHolder, arrayOfString2[i]);
          localArrayList.add(str2);
        }
      }
      arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      break;
    case 0: 
    default: 
      arrayOfPayeeInfo = findPayeeByName(paramString, "exact", paramFFSConnectionHolder);
      if (arrayOfPayeeInfo != null)
      {
        for (i = 0; i < arrayOfPayeeInfo.length; i++) {
          localArrayList.add(arrayOfPayeeInfo[i].PayeeName);
        }
        arrayOfString1 = (String[])localArrayList.toArray(new String[0]);
      }
      break;
    }
    return arrayOfString1;
  }
  
  public static void addPayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    try
    {
      PayeeInfo localPayeeInfo = findPayeeByExtendedID(paramPayeeInfo.ExtdPayeeID, paramFFSConnectionHolder);
      Payee localPayee = new Payee(paramPayeeInfo);
      if (localPayeeInfo == null)
      {
        localPayee.setPayeeID();
        localPayee.storeToDB(paramFFSConnectionHolder);
        paramPayeeRouteInfo.PayeeID = localPayee.getPayeeID();
        PayeeToRoute localPayeeToRoute = new PayeeToRoute(paramPayeeRouteInfo);
        localPayeeToRoute.storeToDB(paramFFSConnectionHolder);
      }
      else
      {
        paramPayeeInfo.PayeeID = localPayeeInfo.PayeeID;
        paramPayeeRouteInfo.PayeeID = localPayeeInfo.PayeeID;
        updatePayee(paramPayeeInfo, localPayeeInfo, paramPayeeRouteInfo, paramFFSConnectionHolder);
      }
    }
    catch (BPWException localBPWException)
    {
      throw new FFSException(localBPWException.getErrorCode(), localBPWException.getMessage());
    }
    catch (Exception localException)
    {
      throw new FFSException(localException, "Failed to add a payee: " + localException.toString());
    }
  }
  
  public static void addPayeeByRouteIdExtIdNameAddress(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    try
    {
      PayeeInfo localPayeeInfo = findPayeeByRouteIdExtIdNameAddress(paramPayeeInfo, paramFFSConnectionHolder);
      Payee localPayee = new Payee(paramPayeeInfo);
      if (localPayeeInfo == null)
      {
        localPayee.setPayeeID();
        localPayee.storeToDB(paramFFSConnectionHolder);
        paramPayeeRouteInfo.PayeeID = localPayee.getPayeeID();
        PayeeToRoute localPayeeToRoute = new PayeeToRoute(paramPayeeRouteInfo);
        localPayeeToRoute.storeToDB(paramFFSConnectionHolder);
      }
      else
      {
        paramPayeeInfo.PayeeID = localPayeeInfo.PayeeID;
        paramPayeeRouteInfo.PayeeID = localPayeeInfo.PayeeID;
        updatePayee(paramPayeeInfo, localPayeeInfo, paramPayeeRouteInfo, paramFFSConnectionHolder);
      }
    }
    catch (BPWException localBPWException)
    {
      throw new FFSException(localBPWException.getErrorCode(), localBPWException.getMessage());
    }
    catch (Exception localException)
    {
      throw new FFSException(localException, "Failed to add a payee: " + localException.toString());
    }
  }
  
  public static void updatePayee(PayeeInfo paramPayeeInfo1, PayeeInfo paramPayeeInfo2, PayeeRouteInfo paramPayeeRouteInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    try
    {
      Payee localPayee = new Payee(paramPayeeInfo1);
      int i = (PmtInstruction.hasPendingPmt(paramPayeeInfo1.PayeeID, paramFFSConnectionHolder)) || (hasPendingLink(paramPayeeInfo1.PayeeID, paramFFSConnectionHolder)) ? 1 : 0;
      if (i != 0)
      {
        localObject = new PayeeInfo[] { paramPayeeInfo2 };
        if (localPayee.matchPayee((PayeeInfo[])localObject) == null)
        {
          FFSDebug.log("Cannot modify payee information because this payee is being refered.", 1);
          if ((paramPayeeInfo2.Status.equals("INPROCESS")) && (paramPayeeInfo1.Status.equals("ACTIVE")))
          {
            localPayee = new Payee(paramPayeeInfo2);
            localPayee.setStatus("ACTIVE");
          }
          else
          {
            return;
          }
        }
      }
      localPayee.update(paramFFSConnectionHolder);
      Object localObject = new PayeeToRoute(paramPayeeRouteInfo);
      ((PayeeToRoute)localObject).updateOrInsert(paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new FFSException(localException, "Failed to add modify payee information: " + localException.toString());
    }
  }
  
  public static void deletePayeesByRouteIdExtId(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString)
    throws FFSException
  {
    PayeeInfo[] arrayOfPayeeInfo = findPayeeByRouteIdExtId(paramFFSConnectionHolder, paramInt, paramString);
    for (int i = 0; i < arrayOfPayeeInfo.length; i++) {
      deletePayee(paramFFSConnectionHolder, arrayOfPayeeInfo[i].PayeeID);
    }
  }
  
  public static final void deletePayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str = "Payee.deletePayee: ";
    try
    {
      boolean bool1 = PmtInstruction.hasPendingPmt(paramString, paramFFSConnectionHolder);
      boolean bool2 = hasPendingLink(paramString, paramFFSConnectionHolder);
      if ((bool1) || (bool2)) {
        throw new FFSException(str + "Can not delete this payee since it's being refered.");
      }
      PayeeInfo localPayeeInfo = findPayeeByID(paramString, paramFFSConnectionHolder);
      Payee localPayee = new Payee(localPayeeInfo);
      PayeeRouteInfo localPayeeRouteInfo = PayeeToRoute.getPayeeRoute(paramString, localPayeeInfo.getRouteID(), paramFFSConnectionHolder);
      PayeeToRoute localPayeeToRoute = new PayeeToRoute(localPayeeRouteInfo);
      localPayeeToRoute.removeFromDB(paramFFSConnectionHolder);
      int i = PayeeToRoute.findRouteID(paramString, paramFFSConnectionHolder);
      if (i == -1)
      {
        localPayee.removeFromDB(paramFFSConnectionHolder);
      }
      else
      {
        localPayee.setRouteID(i);
        localPayee.update(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("***" + str + " failed. " + localException.toString(), 0);
      throw new FFSException(localException, localException.toString());
    }
  }
  
  public static PayeeInfo findPayeeByRouteIdExtIdNameAddress(PayeeInfo paramPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str = "Payee.findPayeeByRouteIdExtendedIDNameAddress: ";
    FFSDebug.log(str + "routeId=" + paramPayeeInfo.getRouteID(), 6);
    FFSDebug.log(str + "extdPayeeID=" + paramPayeeInfo.ExtdPayeeID, 6);
    FFSDebug.log(str + "payeeName=" + paramPayeeInfo.PayeeName, 6);
    PayeeInfo[] arrayOfPayeeInfo = findPayeeByRouteIdExtId(paramFFSConnectionHolder, paramPayeeInfo.getRouteID(), paramPayeeInfo.ExtdPayeeID);
    PayeeInfo localPayeeInfo = null;
    for (int i = 0; i < arrayOfPayeeInfo.length; i++) {
      if (matchPayeeNameAddress(arrayOfPayeeInfo[i], paramPayeeInfo) == true)
      {
        localPayeeInfo = arrayOfPayeeInfo[i];
        break;
      }
    }
    FFSDebug.log(str + "done, PayeeID=" + (localPayeeInfo == null ? "null" : localPayeeInfo.PayeeID), 6);
    return localPayeeInfo;
  }
  
  public static PayeeInfo[] findPayeeByRouteIdExtId(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString)
    throws BPWException
  {
    String str1 = "Payee.findPayeeByRouteIdExtId: ";
    FFSDebug.log(str1 + "extdPayeeID=" + paramString, 6);
    String str2 = "SELECT PayeeID, PayeeType, PayeeName, Addr1, Addr2, Addr3, City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status, DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay, Submitdate, ExtdPayeeID, TranID FROM BPW_Payee WHERE RouteID =? and ExtdPayeeID = ? ";
    ArrayList localArrayList = new ArrayList();
    Object[] arrayOfObject = { new Integer(paramInt), paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        PayeeInfo localPayeeInfo = setupPayeeInfoFromResultset(localFFSResultSet);
        localArrayList.add(localPayeeInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** " + str1 + "failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("***" + str1 + "failed:" + localException2.toString());
      }
    }
    FFSDebug.log(str1 + "done. Number of payees:" + localArrayList.size(), 6);
    return (PayeeInfo[])localArrayList.toArray(new PayeeInfo[0]);
  }
  
  public static PayeeInfo setupPayeeInfoFromResultset(FFSResultSet paramFFSResultSet)
    throws Exception
  {
    PayeeInfo localPayeeInfo = new PayeeInfo();
    localPayeeInfo.PayeeID = paramFFSResultSet.getColumnString(1);
    localPayeeInfo.PayeeType = paramFFSResultSet.getColumnInt(2);
    localPayeeInfo.PayeeName = paramFFSResultSet.getColumnString(3);
    localPayeeInfo.Addr1 = paramFFSResultSet.getColumnString(4);
    localPayeeInfo.Addr2 = paramFFSResultSet.getColumnString(5);
    localPayeeInfo.Addr3 = paramFFSResultSet.getColumnString(6);
    localPayeeInfo.City = paramFFSResultSet.getColumnString(7);
    localPayeeInfo.State = paramFFSResultSet.getColumnString(8);
    localPayeeInfo.Zipcode = paramFFSResultSet.getColumnString(9);
    localPayeeInfo.Country = paramFFSResultSet.getColumnString(10);
    localPayeeInfo.Phone = paramFFSResultSet.getColumnString(11);
    localPayeeInfo.setRouteID(paramFFSResultSet.getColumnInt(12));
    localPayeeInfo.LinkPayeeID = paramFFSResultSet.getColumnString(13);
    localPayeeInfo.Status = paramFFSResultSet.getColumnString(14);
    localPayeeInfo.DisbursementType = paramFFSResultSet.getColumnString(15);
    localPayeeInfo.PayeeLevelType = paramFFSResultSet.getColumnString(16);
    localPayeeInfo.NickName = paramFFSResultSet.getColumnString(17);
    localPayeeInfo.ContactName = paramFFSResultSet.getColumnString(18);
    a(localPayeeInfo, paramFFSResultSet.getColumnInt(19));
    localPayeeInfo.SubmitDate = paramFFSResultSet.getColumnString(20);
    localPayeeInfo.ExtdPayeeID = paramFFSResultSet.getColumnString("ExtdPayeeID");
    localPayeeInfo.TranID = paramFFSResultSet.getColumnString("TranID");
    return localPayeeInfo;
  }
  
  public static boolean matchPayeeNameAddress(PayeeInfo paramPayeeInfo1, PayeeInfo paramPayeeInfo2)
  {
    return (paramPayeeInfo1.PayeeName != null) && (paramPayeeInfo1.PayeeName.equalsIgnoreCase(paramPayeeInfo2.PayeeName)) && (paramPayeeInfo1.Addr1 != null) && (paramPayeeInfo1.Addr1.equalsIgnoreCase(paramPayeeInfo2.Addr1)) && (((paramPayeeInfo1.Addr2 == null) && (paramPayeeInfo2.Addr2 == null)) || ((paramPayeeInfo1.Addr2 != null) && (paramPayeeInfo1.Addr2.equalsIgnoreCase(paramPayeeInfo2.Addr2)) && (((paramPayeeInfo1.Addr3 == null) && (paramPayeeInfo2.Addr3 == null)) || ((paramPayeeInfo1.Addr3 != null) && (paramPayeeInfo1.Addr3.equalsIgnoreCase(paramPayeeInfo2.Addr3)) && (paramPayeeInfo1.City != null) && (paramPayeeInfo1.City.equalsIgnoreCase(paramPayeeInfo2.City)) && (paramPayeeInfo1.State != null) && (paramPayeeInfo1.State.equalsIgnoreCase(paramPayeeInfo2.State)) && (((paramPayeeInfo1.Zipcode == null) && (paramPayeeInfo2.Zipcode == null)) || ((paramPayeeInfo1.Zipcode != null) && (paramPayeeInfo1.Zipcode.equalsIgnoreCase(paramPayeeInfo2.Zipcode))))))));
  }
  
  public static PayeeInfo getPayeeByListId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("Payee.getPayeeByListId start, customerID=" + paramString1 + ", payeeListID=" + paramString2, 6);
    FFSResultSet localFFSResultSet = null;
    PayeeInfo localPayeeInfo = null;
    try
    {
      Object[] arrayOfObject = { paramString1, new Integer(paramString2) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT BPW_Payee.PayeeID, BPW_Payee.ExtdPayeeID, BPW_Payee.PayeeType, BPW_Payee.PayeeName, BPW_Payee.Addr1, BPW_Payee.Addr2, BPW_Payee.Addr3, BPW_Payee.City, BPW_Payee.State, BPW_Payee.Zipcode, BPW_Payee.Country, BPW_Payee.Phone, BPW_Payee.RouteID, BPW_Payee.LinkPayeeID, BPW_Payee.Status, BPW_Payee.DisbursementType, BPW_Payee.PayeeLevelType, BPW_Payee.Nickname, BPW_Payee.ContactName, BPW_Payee.DaysToPay, BPW_Payee.Submitdate, BPW_Payee.TranID FROM BPW_Payee, BPW_CustomerPayee WHERE BPW_Payee.PayeeID=BPW_CustomerPayee.PayeeID AND BPW_CustomerPayee.CustomerID=? AND BPW_CustomerPayee.PayeeListID=?", arrayOfObject);
      if (localFFSResultSet.getNextRow() == true)
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString("ExtdPayeeID");
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt("PayeeType");
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString("PayeeName");
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString("Addr1");
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString("Addr2");
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString("Addr3");
        localPayeeInfo.City = localFFSResultSet.getColumnString("City");
        localPayeeInfo.State = localFFSResultSet.getColumnString("State");
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString("Zipcode");
        localPayeeInfo.Country = localFFSResultSet.getColumnString("Country");
        localPayeeInfo.Phone = localFFSResultSet.getColumnString("Phone");
        localPayeeInfo.setRouteID(localFFSResultSet.getColumnInt("RouteID"));
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString("LinkPayeeID");
        localPayeeInfo.Status = localFFSResultSet.getColumnString("Status");
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString("DisbursementType");
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString("PayeeLevelType");
        localPayeeInfo.NickName = localFFSResultSet.getColumnString("Nickname");
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString("ContactName");
        a(localPayeeInfo, localFFSResultSet.getColumnInt("DaysToPay"));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString("Submitdate");
        localPayeeInfo.TranID = localFFSResultSet.getColumnString("TranID");
      }
    }
    catch (Exception localException1)
    {
      String str = "Payee.getPayeeByListId failed";
      FFSDebug.log(localException1, str, 6);
      throw new Exception(str);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2) {}
    }
    FFSDebug.log("Payee.getPayeeByListId end, customerID=" + paramString1 + ", payeeListID=" + paramString2, 6);
    return localPayeeInfo;
  }
  
  private static void a(PayeeInfo paramPayeeInfo, int paramInt)
  {
    paramPayeeInfo.DaysToPay = paramInt;
    if (paramInt == -1)
    {
      String str = "5";
      FFSProperties localFFSProperties = (FFSProperties)FFSRegistry.lookup("FFSPROPS");
      int i = paramPayeeInfo.PayeeType;
      if (i == 3) {
        str = localFFSProperties.getProperty("bpw.billpay.payee.personal.default.daystopay", "5");
      }
      paramPayeeInfo.DaysToPay = Integer.parseInt(str);
    }
  }
  
  public static String[] getGlobalPayeeGroups(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Payee.getGlobalPayeeGroups start", 6);
    String str = "SELECT DISTINCT PayeeLevelType FROM BPW_Payee";
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, null);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString("PayeeLevelType"));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getGlobalPayeeGroups failed:" + FFSDebug.stackTrace(localException1));
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getGlobalPayeeGroups done", 6);
    String[] arrayOfString = (String[])localArrayList.toArray(new String[0]);
    return arrayOfString;
  }
  
  public static PayeeInfo[] searchGlobalPayees(FFSConnectionHolder paramFFSConnectionHolder, PayeeInfo paramPayeeInfo, int paramInt)
    throws BPWException
  {
    FFSDebug.log("Payee.searchGlobalPayees start", 6);
    String str1 = "SELECT DISTINCT p.PayeeID, ExtdPayeeID, PayeeType, Addr1, Addr2,Addr3,City, State, Zipcode, Country, Phone, RouteID, LinkPayeeID, Status,DisbursementType, PayeeLevelType, Nickname, ContactName, DaysToPay,Submitdate, TranID, FIId,Language,CASE WHEN s.PayeeName='_NULL_' THEN p.PayeeName ELSE s.PayeeName END AS Name FROM BPW_Payee p, BPW_SmartSearch s WHERE p.PayeeID = s.PayeeID AND PayeeType =0";
    StringBuffer localStringBuffer = new StringBuffer(str1);
    FFSResultSet localFFSResultSet = null;
    PayeeInfo localPayeeInfo = null;
    Vector localVector = new Vector();
    PayeeRouteInfo localPayeeRouteInfo1 = new PayeeRouteInfo();
    String str2 = paramPayeeInfo.PayeeName;
    Object[] arrayOfObject = new Object[20];
    int i = 0;
    int j = jdMethod_try();
    try
    {
      if (str2 != null)
      {
        str2 = str2.toUpperCase(Locale.getDefault());
        str2 = str2.trim();
        localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        localObject2 = (localObject1 == null) || (((PropertyConfig)localObject1).otherProperties == null) ? "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`" : ((PropertyConfig)localObject1).otherProperties.getProperty("Payee.Punctuation", "'{}[]:-.!-,()?\";/\\*%$#@<>~&+=^_|{}`");
        localObject3 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        String str3 = (localObject3 == null) || (((PropertyConfig)localObject3).otherProperties == null) ? "500" : ((PropertyConfig)localObject3).otherProperties.getProperty("PayeeSearchCriteria", "true");
        int m = 1;
        if ("any".equalsIgnoreCase(str3)) {
          m = 0;
        }
        if (m != 0) {
          str2 = str2 + "%";
        } else {
          str2 = "%" + str2 + "%";
        }
        switch (paramInt)
        {
        case 1: 
          str2 = removeSpaces(str2);
          if ((str2 != null) && (str2.endsWith("%"))) {
            localStringBuffer = localStringBuffer.append(" AND NoSpaceName like ? ");
          } else {
            localStringBuffer = localStringBuffer.append(" AND NoSpaceName = ? ");
          }
          arrayOfObject[(i++)] = str2;
          break;
        case 2: 
          str2 = removeSpaces(str2);
          if ((str2 != null) && (str2.endsWith("%"))) {
            localStringBuffer = localStringBuffer.append(" AND NoPuctName like ? ");
          } else {
            localStringBuffer = localStringBuffer.append(" AND NoPuctName = ? ");
          }
          arrayOfObject[(i++)] = str2;
          break;
        case 3: 
          localStringBuffer = localStringBuffer.append(" AND (p.PayeeName LIKE ? OR s.PayeeName LIKE ? ) ");
          arrayOfObject[i] = (str2 + '%');
          break;
        case 4: 
          str2 = removeSpaces(str2);
          if ((str2 != null) && (str2.endsWith("%"))) {
            localStringBuffer = localStringBuffer.append(" AND NoPunctSpaceName like ? ");
          } else {
            localStringBuffer = localStringBuffer.append(" AND NoPunctSpaceName = ? ");
          }
          arrayOfObject[(i++)] = str2;
          arrayOfObject[(i++)] = str2;
          break;
        case 5: 
          str2 = removeSpaces(str2);
          str2 = str2 + "%";
          if ((str2 != null) && (str2.endsWith("%"))) {
            localStringBuffer = localStringBuffer.append(" AND NoPuctName like ? ");
          } else {
            localStringBuffer = localStringBuffer.append(" AND NoPuctName = ? ");
          }
          arrayOfObject[(i++)] = str2;
          break;
        case 6: 
          str2 = removePunct(str2, (String)localObject2);
          str2 = str2 + "%";
          if ((str2 != null) && (str2.endsWith("%"))) {
            localStringBuffer = localStringBuffer.append(" AND NoPuctName like ? ");
          } else {
            localStringBuffer = localStringBuffer.append(" AND NoPuctName = ? ");
          }
          arrayOfObject[(i++)] = str2;
          break;
        case 7: 
          str2 = removeSpaces(removePunct(str2, (String)localObject2));
          str2 = str2 + "%";
          if ((str2 != null) && (str2.endsWith("%"))) {
            localStringBuffer = localStringBuffer.append(" AND NoPunctSpaceName like ? ");
          } else {
            localStringBuffer = localStringBuffer.append("  AND NoPunctSpaceName = ? ");
          }
          arrayOfObject[(i++)] = str2;
          break;
        case 0: 
        default: 
          localStringBuffer = localStringBuffer.append(" AND (p.PayeeName = ? OR s.PayeeName = ?) ");
          arrayOfObject[(i++)] = (str2 + '%');
          arrayOfObject[(i++)] = (str2 + '%');
        }
      }
      if ("".equals(paramPayeeInfo.PayeeLevelType)) {
        paramPayeeInfo.PayeeLevelType = null;
      }
      Object localObject1 = { paramPayeeInfo.FIID, new Integer(paramPayeeInfo.PayeeRouteInfo.RouteID), paramPayeeInfo.PayeeLevelType };
      Object localObject2 = { "FIId", "RouteID", "PayeeLevelType" };
      Object localObject3 = new ArrayList();
      for (int k = 0; k < localObject2.length; k++) {
        ((ArrayList)localObject3).add(localObject2);
      }
      for (k = 0; k < localObject1.length; k++) {
        if ((localObject1[k] != null) && (localObject1[k] != "")) {
          if ((localObject1[k] instanceof Integer))
          {
            if (((Integer)localObject1[k]).intValue() != 0)
            {
              DBUtil.appendIntToCondition(localStringBuffer, (ArrayList)localObject3, localObject2[k], String.valueOf(localObject1[k]));
              arrayOfObject[(i++)] = localObject1[k];
            }
          }
          else if ((localObject1[k] instanceof String))
          {
            if (((((String)localObject1[k]).indexOf("-1") < 0 ? 1 : 0) & (((String)localObject1[k]).indexOf("NONE") < 0 ? 1 : 0)) != 0)
            {
              DBUtil.appendStringToCondition(localStringBuffer, (ArrayList)localObject3, localObject2[k], (String)localObject1[k]);
              arrayOfObject[(i++)] = localObject1[k];
            }
            if (((String)localObject1[k]).indexOf("NONE") >= 0) {
              localStringBuffer.append(" AND " + localObject2[k] + " IS NULL");
            }
          }
        }
      }
      if (i == 0)
      {
        arrayOfObject = null;
        localObject1 = null;
      }
      else
      {
        localObject1 = new Object[i];
        for (k = 0; k < i; k++) {
          localObject1[k] = arrayOfObject[k];
        }
      }
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), (Object[])localObject1);
      while ((localFFSResultSet.getNextRow()) && (j-- > 0))
      {
        localPayeeInfo = new PayeeInfo();
        localPayeeInfo.PayeeNamesI18N = new HashMap();
        localPayeeRouteInfo1 = new PayeeRouteInfo();
        localPayeeInfo.setPayeeRouteInfo(localPayeeRouteInfo1);
        localPayeeInfo.PayeeRouteInfo = localPayeeInfo.getPayeeRouteInfo();
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString("ExtdPayeeID");
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt("PayeeType");
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString("Addr1");
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString("Addr2");
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString("Addr3");
        localPayeeInfo.City = localFFSResultSet.getColumnString("City");
        localPayeeInfo.State = localFFSResultSet.getColumnString("State");
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString("Zipcode");
        localPayeeInfo.Country = localFFSResultSet.getColumnString("Country");
        localPayeeInfo.Phone = localFFSResultSet.getColumnString("Phone");
        localPayeeInfo.PayeeRouteInfo.RouteID = localFFSResultSet.getColumnInt("RouteID");
        PayeeRouteInfo localPayeeRouteInfo2 = PayeeToRoute.getPayeeRoute(localPayeeInfo.PayeeID, localPayeeInfo.PayeeRouteInfo.RouteID, paramFFSConnectionHolder);
        if (localPayeeRouteInfo2 != null) {
          localPayeeRouteInfo1.CurrencyCode = localPayeeRouteInfo2.CurrencyCode;
        }
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString("LinkPayeeID");
        localPayeeInfo.Status = localFFSResultSet.getColumnString("Status");
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString("DisbursementType");
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString("PayeeLevelType");
        localPayeeInfo.NickName = localFFSResultSet.getColumnString("Nickname");
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString("ContactName");
        a(localPayeeInfo, localFFSResultSet.getColumnInt("DaysToPay"));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString("Submitdate");
        localPayeeInfo.TranID = localFFSResultSet.getColumnString("TranID");
        localPayeeInfo.FIID = localFFSResultSet.getColumnString("FIId");
        String str4 = localFFSResultSet.getColumnString("Language");
        localPayeeInfo.PayeeName = localFFSResultSet.getColumnString("Name");
        localPayeeInfo.PayeeNamesI18N.put(str4, localPayeeInfo.PayeeName);
        localVector.addElement(localPayeeInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.searchGlobalPayees failed:" + FFSDebug.stackTrace(localException1));
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.searchGlobalPayeeGroups done", 6);
    PayeeInfo[] arrayOfPayeeInfo = (PayeeInfo[])localVector.toArray(new PayeeInfo[0]);
    return arrayOfPayeeInfo;
  }
  
  public static PayeeInfo getGlobalPayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("Payee.getGlobalPayee start", 6);
    String str1 = "SELECT p.PayeeID, p.ExtdPayeeID, p.PayeeType, Addr1, Addr2, Addr3,  City, State, Zipcode, Country, Phone, LinkPayeeID,  Status, DisbursementType, PayeeLevelType, Nickname, ContactName,  DaysToPay, Submitdate, TranID, FIId, Language, CASE  WHEN s.PayeeName = '_NULL_' THEN p.PayeeName  ELSE s.PayeeName  END AS Name  FROM BPW_Payee p, BPW_SmartSearch s WHERE p.PayeeID = s.PayeeID AND  p.PayeeID = ? AND p.PayeeType =0";
    FFSResultSet localFFSResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer(str1);
    PayeeInfo localPayeeInfo = new PayeeInfo();
    localPayeeInfo.PayeeNamesI18N = new HashMap();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), new Object[] { paramString });
      while (localFFSResultSet.getNextRow())
      {
        localPayeeInfo.PayeeID = localFFSResultSet.getColumnString("PayeeID");
        localPayeeInfo.ExtdPayeeID = localFFSResultSet.getColumnString("ExtdPayeeID");
        localPayeeInfo.PayeeType = localFFSResultSet.getColumnInt("PayeeType");
        localPayeeInfo.Addr1 = localFFSResultSet.getColumnString("Addr1");
        localPayeeInfo.Addr2 = localFFSResultSet.getColumnString("Addr2");
        localPayeeInfo.Addr3 = localFFSResultSet.getColumnString("Addr3");
        localPayeeInfo.City = localFFSResultSet.getColumnString("City");
        localPayeeInfo.State = localFFSResultSet.getColumnString("State");
        localPayeeInfo.Zipcode = localFFSResultSet.getColumnString("Zipcode");
        localPayeeInfo.Country = localFFSResultSet.getColumnString("Country");
        localPayeeInfo.Phone = localFFSResultSet.getColumnString("Phone");
        localPayeeInfo.LinkPayeeID = localFFSResultSet.getColumnString("LinkPayeeID");
        localPayeeInfo.Status = localFFSResultSet.getColumnString("Status");
        localPayeeInfo.DisbursementType = localFFSResultSet.getColumnString("DisbursementType");
        localPayeeInfo.PayeeLevelType = localFFSResultSet.getColumnString("PayeeLevelType");
        localPayeeInfo.NickName = localFFSResultSet.getColumnString("Nickname");
        localPayeeInfo.ContactName = localFFSResultSet.getColumnString("ContactName");
        a(localPayeeInfo, localFFSResultSet.getColumnInt("DaysToPay"));
        localPayeeInfo.SubmitDate = localFFSResultSet.getColumnString("Submitdate");
        localPayeeInfo.TranID = localFFSResultSet.getColumnString("TranID");
        localPayeeInfo.FIID = localFFSResultSet.getColumnString("FIId");
        String str2 = localFFSResultSet.getColumnString("Language");
        String str3 = localFFSResultSet.getColumnString("Name");
        if (!str2.equals("en_US")) {
          localPayeeInfo.PayeeNamesI18N.put(str2, str3);
        } else {
          localPayeeInfo.PayeeName = str3;
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getGlobalPayee failed:" + FFSDebug.stackTrace(localException1));
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getGlobalPayee done", 6);
    return localPayeeInfo;
  }
  
  public static String getGlobalPayeeStatusByID(FFSConnectionHolder paramFFSConnectionHolder, PayeeInfo paramPayeeInfo)
    throws BPWException
  {
    FFSDebug.log("Payee.getGlobalPayeeStatusByID start", 6);
    String str1 = "SELECT Status FROM BPW_Payee WHERE PayeeID = ?";
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    StringBuffer localStringBuffer = new StringBuffer(str1);
    PayeeInfo localPayeeInfo = new PayeeInfo();
    localPayeeInfo.PayeeNamesI18N = new HashMap();
    Object[] arrayOfObject = { paramPayeeInfo.PayeeID };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        str2 = localFFSResultSet.getColumnString("Status");
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Payee.getGlobalPayeeStatusByID failed:" + FFSDebug.stackTrace(localException1));
      throw new BPWException(FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Payee.findPayeeByID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Payee.getGlobalPayeeStatusByID done", 6);
    return str2;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Payee
 * JD-Core Version:    0.7.0.1
 */