package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class CustPayee
  implements FFSConst, DBConsts, BPWResource
{
  private CustomerPayeeInfo k2;
  private static int k3;
  private static Hashtable k1 = new Hashtable();
  private static final int k0 = 1000;
  
  public CustPayee()
  {
    this.k2 = new CustomerPayeeInfo();
  }
  
  public CustPayee(CustomerPayeeInfo paramCustomerPayeeInfo)
  {
    this.k2 = paramCustomerPayeeInfo;
  }
  
  public CustomerPayeeInfo getCustPayeeInfo()
  {
    return this.k2;
  }
  
  public static void setBatchSize(int paramInt)
  {
    if (paramInt <= 0) {
      k3 = 1000;
    } else {
      k3 = paramInt;
    }
  }
  
  public static void clearCache()
  {
    k1.clear();
  }
  
  public void setCustomerID(String paramString)
  {
    this.k2.CustomerID = paramString;
  }
  
  public String getCustomerID()
  {
    return this.k2.CustomerID;
  }
  
  public void setPayeeID(String paramString)
  {
    this.k2.PayeeID = paramString;
  }
  
  public String getPayeeID()
  {
    return this.k2.PayeeID;
  }
  
  public void setPayeeListID(int paramInt)
  {
    this.k2.PayeeListID = paramInt;
  }
  
  public int getPayeeListID()
  {
    return this.k2.PayeeListID;
  }
  
  public void setExtdInfo(String paramString)
  {
    this.k2.ExtdInfo = paramString;
  }
  
  public String getExtdInfo()
  {
    return this.k2.ExtdInfo;
  }
  
  public void setLinkID(int paramInt)
  {
    this.k2.LinkID = paramInt;
  }
  
  public void setLinkGoDate(int paramInt)
  {
    this.k2.LinkGoDate = paramInt;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.k2.SubmitDate = paramString;
  }
  
  public int createPayeeListID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.createPayeeListID start, customerID=" + paramString, 6);
    int i = 0;
    String str = "SELECT MAX(PayeeListID) FROM BPW_CustomerPayee WHERE CustomerID = ?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      if (i == 0)
      {
        PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        i = localPropertyConfig.StartPayeeListID;
        if (i == 0) {
          i = 1;
        }
      }
      else
      {
        i += 1;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustPayee.createPayeeListID failed: " + localException1.toString());
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
        FFSDebug.log("*** CustPayee.createPayeeListID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustPayee.createPayeeListID done, payeelistID=" + i, 6);
    return i;
  }
  
  public void setPayAcct(String paramString)
  {
    this.k2.PayAcct = paramString;
  }
  
  public String getPayAcct()
  {
    return this.k2.PayAcct;
  }
  
  public void setNameOnAcct(String paramString)
  {
    this.k2.NameOnAcct = paramString;
  }
  
  public String getNameOnAcct()
  {
    return this.k2.NameOnAcct;
  }
  
  public void setStatus(String paramString)
  {
    this.k2.Status = paramString;
  }
  
  public String getStatus()
  {
    return this.k2.Status;
  }
  
  public static CustomerPayeeInfo[] getCustPayeeByUID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.getCustPayeeByUID start, customerID=" + paramString, 6);
    String str = "SELECT PayeeID, PayeeListID, PayAcct, NameOnAcct, Status, ErrCode, ErrMsg, LinkID, LinkGoDate, Submitdate  FROM BPW_CustomerPayee WHERE CustomerID = ? ";
    Object[] arrayOfObject = { paramString };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
        localCustomerPayeeInfo.CustomerID = paramString;
        localCustomerPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localCustomerPayeeInfo.PayeeListID = localFFSResultSet.getColumnInt(2);
        localCustomerPayeeInfo.PayAcct = localFFSResultSet.getColumnString(3);
        localCustomerPayeeInfo.NameOnAcct = localFFSResultSet.getColumnString(4);
        localCustomerPayeeInfo.Status = localFFSResultSet.getColumnString(5);
        localCustomerPayeeInfo.ErrCode = localFFSResultSet.getColumnInt(6);
        localCustomerPayeeInfo.ErrMsg = localFFSResultSet.getColumnString(7);
        localCustomerPayeeInfo.LinkID = localFFSResultSet.getColumnInt(8);
        if (localFFSResultSet.wasNull()) {
          localCustomerPayeeInfo.LinkID = -1;
        }
        localCustomerPayeeInfo.LinkGoDate = localFFSResultSet.getColumnInt(9);
        if (localFFSResultSet.wasNull()) {
          localCustomerPayeeInfo.LinkGoDate = -1;
        }
        localCustomerPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(10);
        if ((!localCustomerPayeeInfo.Status.equals("CANC")) && (!localCustomerPayeeInfo.Status.equals("PENDING"))) {
          localVector.addElement(localCustomerPayeeInfo);
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustPayee.getCustPayeeByUID failed:" + localException1.toString());
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
        FFSDebug.log("*** CustPayee.getCustPayeeByUID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustPayee.getCustPayeeByUID done, customerID=" + paramString, 6);
    return (CustomerPayeeInfo[])localVector.toArray(new CustomerPayeeInfo[0]);
  }
  
  public static CustomerPayeeInfo[] getCustPayeeByUIDAndRID(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.getCustPayeeByUIDAndRID start, customerID=" + paramString, 6);
    String str = "SELECT cp.PayeeID, cp.PayeeListID, cp.PayAcct, cp.NameOnAcct, cp.Status, cp.ErrCode, cp.ErrMsg, cp.LinkID, cp.LinkGoDate, cp.Submitdate  FROM BPW_CustomerPayee cp, BPW_Payee p WHERE cp.CustomerID = ? AND p.RouteID =? AND cp.Status != ? and cp.PayeeID = p.PayeeID ";
    Object[] arrayOfObject = { paramString, new Integer(paramInt), "CLOSED" };
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
        localCustomerPayeeInfo.CustomerID = paramString;
        localCustomerPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localCustomerPayeeInfo.PayeeListID = localFFSResultSet.getColumnInt(2);
        localCustomerPayeeInfo.PayAcct = localFFSResultSet.getColumnString(3);
        localCustomerPayeeInfo.NameOnAcct = localFFSResultSet.getColumnString(4);
        localCustomerPayeeInfo.Status = localFFSResultSet.getColumnString(5);
        localCustomerPayeeInfo.ErrCode = localFFSResultSet.getColumnInt(6);
        localCustomerPayeeInfo.ErrMsg = localFFSResultSet.getColumnString(7);
        localCustomerPayeeInfo.LinkID = localFFSResultSet.getColumnInt(8);
        if (localFFSResultSet.wasNull()) {
          localCustomerPayeeInfo.LinkID = -1;
        }
        localCustomerPayeeInfo.LinkGoDate = localFFSResultSet.getColumnInt(9);
        if (localFFSResultSet.wasNull()) {
          localCustomerPayeeInfo.LinkGoDate = -1;
        }
        localCustomerPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(10);
        if ((!localCustomerPayeeInfo.Status.equals("CANC")) && (!localCustomerPayeeInfo.Status.equals("PENDING"))) {
          localArrayList.add(localCustomerPayeeInfo);
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustPayee.getCustPayeeByUIDAndRID failed:" + localException1.toString());
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
        FFSDebug.log("*** CustPayee.getCustPayeeByUIDAndRID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustPayee.getCustPayeeByUIDAndRID done, customerID=" + paramString, 6);
    return (CustomerPayeeInfo[])localArrayList.toArray(new CustomerPayeeInfo[0]);
  }
  
  private boolean f(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.storeToDB start.", 6);
    String str = "INSERT INTO BPW_CustomerPayee(CustomerID, PayAcct, NameOnAcct, PayeeID, PayeeListID, Status, LinkID, LinkGoDate, Submitdate )  VALUES(?,?,?,?,?,?,?,?,?)";
    Integer localInteger1 = null;
    if (this.k2.LinkID != -1) {
      localInteger1 = new Integer(this.k2.LinkID);
    }
    Integer localInteger2 = null;
    if (this.k2.LinkGoDate != -1) {
      localInteger2 = new Integer(this.k2.LinkGoDate);
    }
    try
    {
      Object[] arrayOfObject = { this.k2.CustomerID, this.k2.PayAcct, this.k2.NameOnAcct, this.k2.PayeeID, new Integer(this.k2.PayeeListID), this.k2.Status, localInteger1, localInteger2, FFSUtil.getDateString() };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustPayee.storeToDB failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustPayee.storeToDB done", 6);
    return true;
  }
  
  public static int findPayeeListID(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.findPayeeListID start, customerID=" + paramString2 + ",payeeID=" + paramString1 + ",payAcct=" + paramString3, 6);
    int i = -1;
    String str1 = "SELECT PayeeListID, Status FROM BPW_CustomerPayee WHERE PayeeID=? AND CustomerID=? AND PayAcct=?";
    Object[] arrayOfObject = { paramString1, paramString2, paramString3 };
    String str2 = "";
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        i = localFFSResultSet.getColumnInt(1);
        str2 = localFFSResultSet.getColumnString(2);
        if ((str2.equals("CANC")) || (str2.equals("PENDING"))) {
          i = -1;
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustPayee.findPayeeLstID failed: " + localException1.toString());
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
        FFSDebug.log("*** CustPayee.findPayeeLstID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustPayee.findPayeeListID done, customerID=" + paramString2, 6);
    return i;
  }
  
  public void findCustPayeeByPayeeListID(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.findCustPayeeByPayeeListID start, customerID=" + paramString + ",payeeListID=" + paramInt, 6);
    String str = "SELECT PayeeID, PayeeListID, PayAcct, NameOnAcct, CustomerID, Status, ExtdInfo, LinkID, LinkGoDate, Submitdate  FROM BPW_CustomerPayee WHERE PayeeListID=? AND CustomerID=?";
    Object[] arrayOfObject = { new Integer(paramInt), paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        setPayeeID(localFFSResultSet.getColumnString(1));
        setPayeeListID(localFFSResultSet.getColumnInt(2));
        setPayAcct(localFFSResultSet.getColumnString(3));
        setNameOnAcct(localFFSResultSet.getColumnString(4));
        setCustomerID(localFFSResultSet.getColumnString(5));
        setStatus(localFFSResultSet.getColumnString(6));
        setExtdInfo(localFFSResultSet.getColumnString(7));
        int i = localFFSResultSet.getColumnInt(8);
        if (localFFSResultSet.wasNull()) {
          i = -1;
        }
        setLinkID(i);
        int j = localFFSResultSet.getColumnInt(9);
        if (localFFSResultSet.wasNull()) {
          j = -1;
        }
        setLinkGoDate(j);
        setSubmitDate(localFFSResultSet.getColumnString(10));
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustPayee.findCustPayeeByPayeeLstID failed: " + localException1.toString());
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
        FFSDebug.log("*** CustPayee.findCustPayeeByPayeeLstID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustPayee.findCustPayeeByPayeeListID done, customerID=" + paramString, 6);
  }
  
  public static boolean isBatchDone(String paramString, int paramInt)
  {
    FFSDebug.log("CustPayee.isBatchDone status=" + paramString + ",routeID=" + paramInt + ",_rsetCache.get=" + k1.get(new StringBuffer().append(paramString).append(paramInt).toString()), 6);
    return k1.get(paramString + paramInt) == null;
  }
  
  public static void clearBatch(String paramString, int paramInt)
  {
    try
    {
      if (k1.get(paramString + paramInt) != null)
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)k1.get(paramString + paramInt);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        k1.remove(paramString + paramInt);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustPayee.clearBatch failed:" + localException.toString());
    }
  }
  
  public static CustomerPayeeInfo[] findCustPayeesWithPaymentByStatus(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustPayee.findCustPayeesWithPaymentByStatus start, status=" + paramString + ",routeID=" + paramInt, 6);
    Vector localVector = new Vector();
    String str1 = "SELECT BPW_CustomerPayee.PayeeID,BPW_CustomerPayee.PayeeListID, BPW_CustomerPayee.PayAcct,BPW_CustomerPayee.NameOnAcct, BPW_CustomerPayee.CustomerID, BPW_CustPayeeRoute.Status, BPW_CustomerPayee.ExtdInfo, BPW_CustomerPayee.LinkID, BPW_CustomerPayee.LinkGoDate, BPW_CustomerPayee.Submitdate  FROM BPW_CustomerPayee, BPW_CustPayeeRoute, BPW_CustomerRoute WHERE BPW_CustomerPayee.CustomerID = BPW_CustPayeeRoute.CustomerID AND BPW_CustomerPayee.PayeeListID = BPW_CustPayeeRoute.PayeeListID AND BPW_CustomerPayee.CustomerID = BPW_CustomerRoute.CustomerID AND BPW_CustPayeeRoute.RouteID = BPW_CustomerRoute.RouteID AND BPW_CustomerRoute.RouteID = ? AND BPW_CustPayeeRoute.Status = ? AND BPW_CustomerPayee.Status='ACTIVE'";
    Object[] arrayOfObject1 = { new Integer(paramInt), paramString };
    Object[] arrayOfObject2 = { new Integer(paramInt) };
    Object[] arrayOfObject3 = { new Integer(paramInt), paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      String str2 = paramString + paramInt;
      if (k1.get(str2) == null)
      {
        if (paramString.equals("MOD"))
        {
          str1 = "SELECT BPW_CustomerPayee.PayeeID,BPW_CustomerPayee.PayeeListID, BPW_CustomerPayee.PayAcct,BPW_CustomerPayee.NameOnAcct, BPW_CustomerPayee.CustomerID, BPW_CustPayeeRoute.Status, BPW_CustomerPayee.ExtdInfo, BPW_CustomerPayee.LinkID, BPW_CustomerPayee.LinkGoDate, BPW_CustomerPayee.Submitdate  FROM BPW_CustomerPayee, BPW_CustPayeeRoute, BPW_CustomerRoute WHERE BPW_CustomerPayee.CustomerID = BPW_CustPayeeRoute.CustomerID AND BPW_CustomerPayee.PayeeListID = BPW_CustPayeeRoute.PayeeListID AND BPW_CustomerPayee.CustomerID = BPW_CustomerRoute.CustomerID AND BPW_CustPayeeRoute.RouteID = BPW_CustomerRoute.RouteID AND BPW_CustomerRoute.RouteID = ? AND BPW_CustPayeeRoute.Status LIKE 'MOD%' AND BPW_CustomerPayee.Status='ACTIVE'";
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject2);
        }
        else if (paramString.equals("NEW"))
        {
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject1);
        }
        else
        {
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject3);
        }
        k1.put(str2, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)k1.get(str2);
      }
      while (localFFSResultSet.getNextRow())
      {
        CustomerPayeeInfo localCustomerPayeeInfo = new CustomerPayeeInfo();
        localCustomerPayeeInfo.PayeeID = localFFSResultSet.getColumnString(1);
        localCustomerPayeeInfo.PayeeListID = localFFSResultSet.getColumnInt(2);
        localCustomerPayeeInfo.PayAcct = localFFSResultSet.getColumnString(3);
        localCustomerPayeeInfo.NameOnAcct = localFFSResultSet.getColumnString(4);
        localCustomerPayeeInfo.CustomerID = localFFSResultSet.getColumnString(5);
        localCustomerPayeeInfo.Status = localFFSResultSet.getColumnString(6);
        localCustomerPayeeInfo.ExtdInfo = localFFSResultSet.getColumnString(7);
        localCustomerPayeeInfo.LinkID = localFFSResultSet.getColumnInt(8);
        if (localFFSResultSet.wasNull()) {
          localCustomerPayeeInfo.LinkID = -1;
        }
        localCustomerPayeeInfo.LinkGoDate = localFFSResultSet.getColumnInt(9);
        if (localFFSResultSet.wasNull()) {
          localCustomerPayeeInfo.LinkGoDate = -1;
        }
        localCustomerPayeeInfo.SubmitDate = localFFSResultSet.getColumnString(10);
        localVector.addElement(localCustomerPayeeInfo);
        i++;
        if (i == k3)
        {
          FFSDebug.log("CustPayee.findCustPayeesWithPaymentByStatus done, status=" + paramString + ",number=" + localVector.size(), 6);
          CustomerPayeeInfo[] arrayOfCustomerPayeeInfo2 = (CustomerPayeeInfo[])localVector.toArray(new CustomerPayeeInfo[0]);
          return arrayOfCustomerPayeeInfo2;
        }
      }
      localFFSResultSet.close();
      k1.remove(paramString + paramInt);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      if (k1 != null) {
        k1.remove(paramString + paramInt);
      }
      FFSDebug.log("*** CustPayee.findCustPayeesWithPaymentByStatus failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    finally {}
    CustomerPayeeInfo[] arrayOfCustomerPayeeInfo1 = (CustomerPayeeInfo[])localVector.toArray(new CustomerPayeeInfo[0]);
    FFSDebug.log("CustPayee.findCustPayeesWithPaymentByStatus done, status=" + paramString + ",number=" + localVector.size(), 6);
    return arrayOfCustomerPayeeInfo1;
  }
  
  public static void updateStatus(String paramString1, int paramInt, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.updateStatus start, customerID=" + paramString1 + ",payeeListID=" + paramInt + ",status=" + paramString2, 6);
    if (paramString2.equals("CLOSED"))
    {
      boolean bool = isReadyToClose(paramString1, paramInt, paramString2, paramFFSConnectionHolder);
      if (bool != true)
      {
        FFSDebug.log("CustPayee.updateStatus done.  Status not ready to be updated to CLOSE for customerID=" + paramString1 + ", payeeListID=" + paramInt + ".  Entries" + " with this customerID and payeeListID still" + " exist in the CustPayeeRoute table.", 6);
        return;
      }
    }
    String str = "UPDATE BPW_CustomerPayee SET Status = ? WHERE PayeeListID = ? AND CustomerID = ?";
    Object[] arrayOfObject = { paramString2, new Integer(paramInt), paramString1 };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerPayee.updateStatus failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustPayee.updateStatus done, customerID=" + paramString1 + ",payeeListID=" + paramInt, 6);
  }
  
  public static void updateStatus(String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.updateStatus start, customerID=" + paramString1 + ",payeeListID=" + paramInt1 + ",status=" + paramString2, 6);
    if (paramString2.equals("CLOSED"))
    {
      boolean bool = isReadyToClose(paramString1, paramInt1, paramString2, paramFFSConnectionHolder);
      if (bool != true)
      {
        FFSDebug.log("CustPayee.updateStatus done.  Status not ready to be updated to CLOSE for customerID=" + paramString1 + ", payeeListID=" + paramInt1 + ".  Entries" + " with this customerID and payeeListID still" + " exist in the CustPayeeRoute table.", 6);
        return;
      }
    }
    String str = "UPDATE BPW_CustomerPayee SET Status = ? , ErrCode=?, ErrMsg =? WHERE PayeeListID = ? AND CustomerID = ?";
    Object[] arrayOfObject = { paramString2, new Integer(paramInt2), paramString3, new Integer(paramInt1), paramString1 };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustPayee.updateStatus failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustPayee.updateStatus done, customerID=" + paramString1 + ",payeeListID=" + paramInt1, 6);
  }
  
  public void update(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.update start, customerID=" + this.k2.CustomerID + ",PayeeListID=" + this.k2.PayeeListID + ",PayeeID=" + this.k2.PayeeID, 6);
    try
    {
      String str = "UPDATE BPW_CustomerPayee SET PayeeID=?, PayAcct=?, NameOnAcct=?, Status = ?, ExtdInfo = ? WHERE PayeeListID = ? AND CustomerID = ?";
      Object[] arrayOfObject = { this.k2.PayeeID, this.k2.PayAcct, this.k2.NameOnAcct, this.k2.Status, this.k2.ExtdInfo, new Integer(this.k2.PayeeListID), this.k2.CustomerID };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerPayee.update failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustPayee.update done, customerID=" + this.k2.CustomerID, 6);
  }
  
  public CustomerPayeeInfo matchCustPayees(String paramString1, String[] paramArrayOfString, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.matchCustPayees start, customerID=" + paramString1 + ",payeeIDs.length=" + paramArrayOfString.length + ",payAcct=" + paramString2, 6);
    String str1 = "SELECT PayeeListID,Status FROM BPW_CustomerPayee WHERE CustomerID=? AND PayeeID=? AND PayAcct=?";
    CustomerPayeeInfo localCustomerPayeeInfo = null;
    int i = 0;
    for (int j = 0; j < paramArrayOfString.length; j++)
    {
      Object[] arrayOfObject = { paramString1, paramArrayOfString[j], paramString2 };
      FFSResultSet localFFSResultSet = null;
      try
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
        while (localFFSResultSet.getNextRow())
        {
          String str2 = localFFSResultSet.getColumnString(2);
          if (str2.equals("ACTIVE"))
          {
            i = localFFSResultSet.getColumnInt(1);
            localCustomerPayeeInfo = new CustomerPayeeInfo();
            localCustomerPayeeInfo.PayeeID = paramArrayOfString[j];
            localCustomerPayeeInfo.PayeeListID = i;
            localCustomerPayeeInfo.PayAcct = paramString2;
            localCustomerPayeeInfo.CustomerID = paramString1;
          }
        }
      }
      catch (Exception localException1)
      {
        FFSDebug.log("*** CustPayee.matchCustPayees failed: " + localException1.toString());
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
      if (i != 0) {
        break;
      }
    }
    FFSDebug.log("CustPayee.matchCustPayees end, PayeeListID=" + i, 6);
    return localCustomerPayeeInfo;
  }
  
  public int addCustPayee(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2)
    throws BPWException, OFXException
  {
    FFSDebug.log("CustPayee.addCustPayee start, customerID=" + paramString1 + ",payeeID=" + paramString2 + ",payAcct=" + paramString3, 6);
    String str1 = "SELECT PayeeListID, Status FROM BPW_CustomerPayee WHERE PayeeID=? AND CustomerID=? AND PayAcct=?";
    String str2 = "INSERT INTO BPW_UidLock (CustomerID) VALUES( ? )";
    Object[] arrayOfObject1 = { paramString2, paramString1, paramString3 };
    Object[] arrayOfObject2 = { paramString1 };
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder1, str1, arrayOfObject1);
      while (localFFSResultSet.getNextRow())
      {
        i = localFFSResultSet.getColumnInt(1);
        String str3 = localFFSResultSet.getColumnString(2);
        if (str3.equals("ACTIVE"))
        {
          CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(paramString1, i, paramFFSConnectionHolder1);
          if ((localCustPayeeRoute != null) && ((localCustPayeeRoute.Status.equals("NEW")) || (localCustPayeeRoute.Status.equals("ACTIVE")) || (localCustPayeeRoute.Status.equals("INPROCESS")) || (localCustPayeeRoute.Status.indexOf("MOD") != -1)))
          {
            int k = i;
            return k;
          }
        }
      }
      int j = DBUtil.executeStatement(paramFFSConnectionHolder2, str2, arrayOfObject2);
    }
    catch (OFXException localOFXException)
    {
      throw localOFXException;
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("*** CustPayee.addCustPayee failed: " + FFSDebug.stackTrace(localThrowable));
      throw new BPWException(FFSDebug.stackTrace(localThrowable));
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
      catch (Exception localException)
      {
        FFSDebug.log("*** CustPayee.addCustPayee failed:" + localException.toString());
      }
    }
    this.k2.CustomerID = paramString1;
    this.k2.Status = "ACTIVE";
    this.k2.PayeeID = paramString2;
    this.k2.PayAcct = paramString3;
    this.k2.NameOnAcct = paramString4;
    i = createPayeeListID(paramString1, paramFFSConnectionHolder1);
    this.k2.PayeeListID = i;
    f(paramFFSConnectionHolder1);
    CustPayeeRoute.addCustPayeeRoute(paramString1, i, paramInt, paramFFSConnectionHolder1);
    FFSDebug.log("CustPayee.addCustPayee done, customerID=" + paramString1 + ", payeelistID=" + i, 6);
    return i;
  }
  
  public void modCustPayee(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = getExtdInfo();
    CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(getCustomerID(), getPayeeListID(), paramFFSConnectionHolder);
    String str2 = null;
    if (localCustPayeeRoute != null) {
      str2 = localCustPayeeRoute.Status;
    } else {
      str2 = getStatus();
    }
    if ((str2.equals("MODACCT")) || (str2.equals("MODBOTH"))) {
      setExtdInfo(str1);
    } else {
      setExtdInfo(paramString3);
    }
    setPayAcct(paramString1);
    setNameOnAcct(paramString2);
    setPayeeID(paramString4);
    update(paramFFSConnectionHolder);
  }
  
  public static void updateLinkID(String paramString, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.updateLinkID start, customerID=" + paramString + ",payeeListID=" + paramInt1 + ", LinkID=" + paramInt2, 6);
    String str = "UPDATE BPW_CustomerPayee SET LinkID = ? WHERE PayeeListID = ? AND CustomerID = ?";
    Integer localInteger = null;
    if (paramInt2 != -1) {
      localInteger = new Integer(paramInt2);
    }
    Object[] arrayOfObject = { localInteger, new Integer(paramInt1), paramString };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustPayee.updateLinkID failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustPayee.updateLinkID done, customerID=" + paramString, 6);
  }
  
  public static void updateLinkGoDate(String paramString, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.updateLinkGoDate start, customerID=" + paramString + ",payeeListID=" + paramInt1 + ",LinkGoDate=" + paramInt2, 6);
    String str = "UPDATE BPW_CustomerPayee SET LinkGoDate = ? WHERE PayeeListID = ? AND CustomerID = ?";
    Integer localInteger = null;
    if (paramInt2 != -1) {
      localInteger = new Integer(paramInt2);
    }
    Object[] arrayOfObject = { localInteger, new Integer(paramInt1), paramString };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustPayee.updateLinkGoDate failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustPayee.updateLinkGoDate done, customerID=" + paramString, 6);
  }
  
  public static boolean isReadyToClose(String paramString1, int paramInt, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayee.isReadyToClose start, customerID=" + paramString1 + ", payeeListID=" + paramInt, 6);
    boolean bool = false;
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString1, new Integer(paramInt), paramString2 };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT Status from BPW_CustPayeeRoute  WHERE CustomerID = ? AND PayeeListID = ? AND Status NOT IN (?)", arrayOfObject);
      if (!localFFSResultSet.getNextRow()) {
        bool = true;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerPayee.isReadyToClose failed: " + localException1.toString());
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
        FFSDebug.log("*** CustPayee.isReadyToClose failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log("CustPayee.isReadyToClose done, customerID=" + paramString1, 6);
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.CustPayee
 * JD-Core Version:    0.7.0.1
 */