package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class PayeeToRoute
  implements FFSConst, DBConsts
{
  private PayeeRouteInfo xY;
  
  public PayeeToRoute()
  {
    this.xY = new PayeeRouteInfo();
  }
  
  public PayeeToRoute(PayeeRouteInfo paramPayeeRouteInfo)
  {
    this.xY = paramPayeeRouteInfo;
  }
  
  public static PayeeRouteInfo getPayeeRoute(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PayeeToRoute.getPayeeRoute start, payeeID=" + paramString, 6);
    PayeeRouteInfo localPayeeRouteInfo = null;
    String str1 = "SELECT PayeeType, ExtdPayeeID, BankID, AcctID, AcctType, PaymentCost, ExtdInfo, CurrencyCode, CustAcctRequired FROM BPW_PayeeRoute WHERE PayeeID=? AND RouteID=?";
    Object[] arrayOfObject = { paramString, new Integer(paramInt) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localPayeeRouteInfo = new PayeeRouteInfo();
        localPayeeRouteInfo.PayeeID = paramString;
        localPayeeRouteInfo.RouteID = paramInt;
        localPayeeRouteInfo.PayeeType = localFFSResultSet.getColumnInt(1);
        localPayeeRouteInfo.ExtdPayeeID = localFFSResultSet.getColumnString(2);
        localPayeeRouteInfo.BankID = localFFSResultSet.getColumnString(3);
        localPayeeRouteInfo.AcctID = localFFSResultSet.getColumnString(4);
        localPayeeRouteInfo.AcctType = localFFSResultSet.getColumnString(5);
        localPayeeRouteInfo.PaymentCost = localFFSResultSet.getColumnDouble(6);
        localPayeeRouteInfo.ExtdInfo = localFFSResultSet.getColumnString(7);
        localPayeeRouteInfo.CurrencyCode = localFFSResultSet.getColumnString(8);
        String str2 = localFFSResultSet.getColumnString(9);
        if ((str2 != null) && (str2.trim().length() > 0)) {
          localPayeeRouteInfo.CustAcctRequired = (str2.equalsIgnoreCase("Y"));
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PayeeToRoute.getPayeeRoute failed:" + localException1.toString());
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
        FFSDebug.log("*** PayeeToRoute.getPayeeRoute failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PayeeToRoute.getPayeeRoute done, payeeID=" + paramString, 6);
    return localPayeeRouteInfo;
  }
  
  public static int findRouteID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PayeeToRoute.findRouteID start, payeeID=" + paramString, 6);
    int i = -1;
    String str = "SELECT RouteID FROM BPW_PayeeRoute WHERE PayeeID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PayeeToRoute.findRoute failed:" + localException1.toString());
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
        FFSDebug.log("*** PayeeToRoute.findRoute failed:" + localException2.toString());
      }
    }
    FFSDebug.log("PayeeToRoute.findRouteID done, payeeID=" + paramString, 6);
    return i;
  }
  
  public boolean storeToDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = "N";
    if (this.xY.CustAcctRequired == true) {
      str1 = "Y";
    }
    FFSDebug.log("PayeeToRoute.storeToDB start, payeeID=" + this.xY.PayeeID, 6);
    String str2 = (this.xY.ExtdPayeeID == null) || (this.xY.ExtdPayeeID.length() == 0) ? "0" : this.xY.ExtdPayeeID;
    String str3 = "INSERT INTO BPW_PayeeRoute(PayeeID, PayeeType,ExtdPayeeID, RouteID, BankID, AcctID, AcctType, PaymentCost, ExtdInfo, CurrencyCode ,CustAcctRequired ) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { this.xY.PayeeID, new Integer(this.xY.PayeeType), str2, new Integer(this.xY.RouteID), this.xY.BankID, this.xY.AcctID, this.xY.AcctType, new Double(this.xY.PaymentCost), this.xY.ExtdInfo, this.xY.CurrencyCode, str1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PayeeToRoute.storeToDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PayeeToRoute.storeToDB done, payeeID=" + this.xY.PayeeID, 6);
    return true;
  }
  
  public void removeFromDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PayeeToRoute.removeFromDB start, payeeID=" + this.xY.PayeeID, 6);
    String str = "DELETE FROM BPW_PayeeRoute WHERE PayeeID=? AND RouteID=?";
    Object[] arrayOfObject = { this.xY.PayeeID, new Integer(this.xY.RouteID) };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PayeeToRoute.removeFromDB failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PayeeToRoute.removeFromDB done, payeeID=" + this.xY.PayeeID, 6);
  }
  
  public boolean update(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PayeeToRoute.update start, payeeID=" + this.xY.PayeeID, 6);
    try
    {
      String str1 = "N";
      if (this.xY.CustAcctRequired) {
        str1 = "Y";
      }
      String str2 = "UPDATE BPW_PayeeRoute SET PayeeType=?, BankID=?, AcctID=?, AcctType=?,ExtdPayeeID=?,PaymentCost=?,ExtdInfo=? ,RouteID=?, CurrencyCode=?, CustAcctRequired=? WHERE PayeeID=?";
      Object[] arrayOfObject = { new Integer(this.xY.PayeeType), this.xY.BankID, this.xY.AcctID, this.xY.AcctType, this.xY.ExtdPayeeID, new Double(this.xY.PaymentCost), this.xY.ExtdInfo, new Integer(this.xY.RouteID), this.xY.CurrencyCode, str1, this.xY.PayeeID };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PayeeToRoute.update failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PayeeToRoute.update done, payeeID=" + this.xY.PayeeID, 6);
    return true;
  }
  
  public void updateOrInsert(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("PayeeToRoute.updateOrInsert start, payeeID=" + this.xY.PayeeID, 6);
    try
    {
      String str1 = null;
      if (this.xY.CustAcctRequired) {
        str1 = "Y";
      } else {
        str1 = "N";
      }
      String str2 = (this.xY.ExtdPayeeID == null) || (this.xY.ExtdPayeeID.length() == 0) ? "0" : this.xY.ExtdPayeeID;
      String str3 = "UPDATE BPW_PayeeRoute SET PayeeType=?, BankID=?, AcctID=?, AcctType=?,ExtdPayeeID=?,PaymentCost=?,ExtdInfo=? ,RouteID=?, CurrencyCode=?, CustAcctRequired=? WHERE PayeeID=?";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, new Object[] { new Integer(this.xY.PayeeType), this.xY.BankID, this.xY.AcctID, this.xY.AcctType, str2, new Double(this.xY.PaymentCost), this.xY.ExtdInfo, new Integer(this.xY.RouteID), this.xY.CurrencyCode, str1, this.xY.PayeeID });
      if (i > 0) {
        return;
      }
      str3 = "INSERT INTO BPW_PayeeRoute(PayeeID, PayeeType,ExtdPayeeID, RouteID, BankID, AcctID, AcctType, PaymentCost, ExtdInfo, CurrencyCode ,CustAcctRequired ) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
      DBUtil.executeStatement(paramFFSConnectionHolder, str3, new Object[] { this.xY.PayeeID, new Integer(this.xY.PayeeType), str2, new Integer(this.xY.RouteID), this.xY.BankID, this.xY.AcctID, this.xY.AcctType, new Double(this.xY.PaymentCost), this.xY.ExtdInfo, this.xY.CurrencyCode, str1 });
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PayeeToRoute.updateOrInsert failed:" + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PayeeToRoute.updateOrInsert done, payeeID=" + this.xY.PayeeID, 6);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("PayeeToRoute.delete: start, payeeID=" + paramString, 6);
    String str1 = "DELETE FROM BPW_PayeeRoute WHERE PayeeID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** PayeeToRoute.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("PayeeToRoute.delete done, payeeID=" + paramString, 6);
  }
  
  public static PayeeRouteInfo getPayeeRouteById(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    String str1 = "SELECT RouteID, BankID,  AcctID, AcctType, CurrencyCode, CustAcctRequired ,PayeeID  FROM BPW_PayeeRoute  WHERE PayeeID = ?";
    FFSResultSet localFFSResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer(str1);
    PayeeRouteInfo localPayeeRouteInfo = new PayeeRouteInfo();
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), new Object[] { paramString });
      if (localFFSResultSet != null) {
        while (localFFSResultSet.getNextRow())
        {
          localPayeeRouteInfo.RouteID = localFFSResultSet.getColumnInt(1);
          localPayeeRouteInfo.BankID = localFFSResultSet.getColumnString(2);
          localPayeeRouteInfo.AcctID = localFFSResultSet.getColumnString(3);
          localPayeeRouteInfo.AcctType = localFFSResultSet.getColumnString(4);
          localPayeeRouteInfo.CurrencyCode = localFFSResultSet.getColumnString(5);
          String str2 = localFFSResultSet.getColumnString(6);
          localPayeeRouteInfo.PayeeID = localFFSResultSet.getColumnString(7);
          if (str2.equals("Y")) {
            localPayeeRouteInfo.CustAcctRequired = true;
          } else {
            localPayeeRouteInfo.CustAcctRequired = false;
          }
        }
      }
      localPayeeRouteInfo.CurrencyCode = "USD";
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** PayeeToRoute.getPayeeRouteById failed:" + localException1.toString());
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
        FFSDebug.log("*** PayeeToRoute.getPayeeRouteById failed:" + localException2.toString());
      }
    }
    return localPayeeRouteInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.PayeeToRoute
 * JD-Core Version:    0.7.0.1
 */