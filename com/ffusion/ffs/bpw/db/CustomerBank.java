package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerBankInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class CustomerBank
  implements FFSConst, DBConsts, BPWResource
{
  private static final String kG = "SELECT RTN, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ? ";
  private static final String kO = "SELECT ConsumerID, RTN, AcctNumber, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate FROM BPW_CustomerBankInfo WHERE ConsumerID = ?";
  private static final String kP = "SELECT ConsumerID, RTN, AcctNumber, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND Status = ?";
  private static final String kL = "UPDATE BPW_CustomerBankInfo SET RTN = ?, AcctType = ?, SettlementRefNumber = ?, SettlementAcctFlag = ?, Status=?  WHERE ConsumerID = ?  AND AcctNumber = ? ";
  private static final String kI = "UPDATE BPW_CustomerBankInfo SET RTN = ?, AcctType = ?, SettlementRefNumber = ?, SettlementAcctFlag = ?, Status=?  WHERE ConsumerID = ?  AND AcctNumber = ? AND Status = ?";
  private static final String kN = "INSERT INTO BPW_CustomerBankInfo( ConsumerID, RTN, AcctNumber, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate )  VALUES(?,?,?,?,?,?,?,?)";
  private static final String kJ = "DELETE FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ? ";
  private static final String kH = "DELETE FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ?  AND Status = ?";
  private static final String kF = "DELETE FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ?  AND Status != 'CANC'";
  private static final String kM = "UPDATE BPW_CustomerBankInfo SET Status=?  WHERE ConsumerID = ?  AND AcctNumber = ? AND Status = ?";
  private static final String kK = "SELECT ConsumerID FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ? AND Status = ? ";
  
  public static int add(CustomerBankInfo paramCustomerBankInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.add start, consumerID=" + paramCustomerBankInfo.consumerID + ",acctNumber=" + paramCustomerBankInfo.acctNumber, 6);
    String str1;
    if ((!paramCustomerBankInfo.acctType.equals("DDA")) && (!paramCustomerBankInfo.acctType.equals("SV")))
    {
      str1 = "Your AcctType of " + paramCustomerBankInfo.acctType + " is not supported, change AcctType to DDA or SV";
      FFSDebug.log("*** CustomerBank.add failed:" + str1);
      throw new Exception(str1);
    }
    if ((!paramCustomerBankInfo.primaryAcctFlag.equals("Y")) && (!paramCustomerBankInfo.primaryAcctFlag.equals("N")))
    {
      str1 = "Your PrimaryAcctFlag of " + paramCustomerBankInfo.primaryAcctFlag + " is not supported, change PrimaryAcctFlag to Y or N";
      FFSDebug.log("*** CustomerBank.add failed:" + str1);
      throw new Exception(str1);
    }
    int i = 0;
    int j = 1;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RTN, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ? ", new Object[] { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber });
      Object localObject1;
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = localFFSResultSet.getColumnString(1);
        String str2 = localFFSResultSet.getColumnString(2);
        String str3 = localFFSResultSet.getColumnString(3);
        String str4 = localFFSResultSet.getColumnString(4);
        String str5 = localFFSResultSet.getColumnString(5);
        if ((!((String)localObject1).equals(paramCustomerBankInfo.routingAndTransitNumber)) || (!str2.equals(paramCustomerBankInfo.acctType)) || (!str3.equals(paramCustomerBankInfo.settlementRefNumber)) || (!str4.equals(paramCustomerBankInfo.primaryAcctFlag)))
        {
          if (!str5.equals("CANC"))
          {
            Object localObject2;
            if (str5.equals("NEW"))
            {
              j = 0;
              localObject2 = new Object[] { paramCustomerBankInfo.routingAndTransitNumber, paramCustomerBankInfo.acctType, paramCustomerBankInfo.settlementRefNumber, paramCustomerBankInfo.primaryAcctFlag, str5, paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber, str5 };
              i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerBankInfo SET RTN = ?, AcctType = ?, SettlementRefNumber = ?, SettlementAcctFlag = ?, Status=?  WHERE ConsumerID = ?  AND AcctNumber = ? AND Status = ?", (Object[])localObject2);
            }
            else if (str5.equals("ACTIVE"))
            {
              j = 0;
              updateStatus(paramCustomerBankInfo, "ACTIVE", "CANC", paramFFSConnectionHolder);
              localObject2 = new Object[] { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.routingAndTransitNumber, paramCustomerBankInfo.acctNumber, paramCustomerBankInfo.acctType, paramCustomerBankInfo.settlementRefNumber, paramCustomerBankInfo.primaryAcctFlag, "NEW", FFSUtil.getDateString() };
              i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustomerBankInfo( ConsumerID, RTN, AcctNumber, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate )  VALUES(?,?,?,?,?,?,?,?)", (Object[])localObject2);
            }
            else
            {
              localObject2 = "Unknown status " + str5;
              FFSDebug.log("*** CustomerBank.add failed:" + (String)localObject2);
              throw new Exception((String)localObject2);
            }
          }
        }
        else
        {
          j = 0;
          if (str5.equals("CANC"))
          {
            deleteNotCanc(paramCustomerBankInfo, paramFFSConnectionHolder);
            i = updateStatus(paramCustomerBankInfo, "CANC", "ACTIVE", paramFFSConnectionHolder);
          }
          else
          {
            if ((str5.equals("ACTIVE")) || (str5.equals("NEW"))) {
              break;
            }
          }
        }
      }
      if (j != 0)
      {
        localObject1 = new Object[] { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.routingAndTransitNumber, paramCustomerBankInfo.acctNumber, paramCustomerBankInfo.acctType, paramCustomerBankInfo.settlementRefNumber, paramCustomerBankInfo.primaryAcctFlag, "NEW", FFSUtil.getDateString() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustomerBankInfo( ConsumerID, RTN, AcctNumber, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate )  VALUES(?,?,?,?,?,?,?,?)", (Object[])localObject1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerBank.add failed:" + localException1.toString());
      throw new Exception(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException2) {}
    }
    FFSDebug.log("CustomerBank.add done, rows=" + i, 6);
    return i;
  }
  
  public static int update(CustomerBankInfo paramCustomerBankInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.update start, consumerID=" + paramCustomerBankInfo.consumerID + ",acctNumber=" + paramCustomerBankInfo.acctNumber, 6);
    String str1;
    if ((!paramCustomerBankInfo.acctType.equals("DDA")) && (!paramCustomerBankInfo.acctType.equals("SV")))
    {
      str1 = "Your AcctType of " + paramCustomerBankInfo.acctType + " is not supported, change AcctType to DDA or SV";
      FFSDebug.log("*** CustomerBank.add failed:" + str1);
      throw new Exception(str1);
    }
    if ((!paramCustomerBankInfo.primaryAcctFlag.equals("Y")) && (!paramCustomerBankInfo.primaryAcctFlag.equals("N")))
    {
      str1 = "Your PrimaryAcctFlag of " + paramCustomerBankInfo.primaryAcctFlag + " is not supported, change PrimaryAcctFlag to Y or N";
      FFSDebug.log("*** CustomerBank.add failed:" + str1);
      throw new Exception(str1);
    }
    if ((!paramCustomerBankInfo.status.equals("ACTIVE")) && (!paramCustomerBankInfo.status.equals("CLOSED")))
    {
      str1 = "Your status of " + paramCustomerBankInfo.status + " is not supported, change status to ACTIVE or CLOSED";
      FFSDebug.log("*** CustomerBank.add failed:" + str1);
      throw new Exception(str1);
    }
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT RTN, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ? ", new Object[] { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber });
      Object localObject1;
      if (localFFSResultSet.getNextRow())
      {
        localObject1 = localFFSResultSet.getColumnString(1);
        String str2 = localFFSResultSet.getColumnString(2);
        String str3 = localFFSResultSet.getColumnString(3);
        String str4 = localFFSResultSet.getColumnString(4);
        String str5 = localFFSResultSet.getColumnString(5);
        if ((!((String)localObject1).equals(paramCustomerBankInfo.routingAndTransitNumber)) || (!str2.equals(paramCustomerBankInfo.acctType)) || (!str3.equals(paramCustomerBankInfo.settlementRefNumber)) || (!str4.equals(paramCustomerBankInfo.primaryAcctFlag)))
        {
          Object[] arrayOfObject = { paramCustomerBankInfo.routingAndTransitNumber, paramCustomerBankInfo.acctType, paramCustomerBankInfo.settlementRefNumber, paramCustomerBankInfo.primaryAcctFlag, paramCustomerBankInfo.status, paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber };
          i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerBankInfo SET RTN = ?, AcctType = ?, SettlementRefNumber = ?, SettlementAcctFlag = ?, Status=?  WHERE ConsumerID = ?  AND AcctNumber = ? ", arrayOfObject);
        }
      }
      else
      {
        localObject1 = new Object[] { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.routingAndTransitNumber, paramCustomerBankInfo.acctNumber, paramCustomerBankInfo.acctType, paramCustomerBankInfo.settlementRefNumber, paramCustomerBankInfo.primaryAcctFlag, paramCustomerBankInfo.status, FFSUtil.getDateString() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustomerBankInfo( ConsumerID, RTN, AcctNumber, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate )  VALUES(?,?,?,?,?,?,?,?)", (Object[])localObject1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerBank.update failed:" + localException1.toString());
      throw new Exception(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException2) {}
    }
    FFSDebug.log("CustomerBank.add update, rows=" + i, 6);
    return i;
  }
  
  public static int delete(CustomerBankInfo paramCustomerBankInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.delete start, consumerID=" + paramCustomerBankInfo.consumerID + ",acctNumber=" + paramCustomerBankInfo.acctNumber, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject = { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ? ", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerBank.delete failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    FFSDebug.log("CustomerBank.delete done, rows=" + i, 6);
    return i;
  }
  
  public static int deleteNotCanc(CustomerBankInfo paramCustomerBankInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.deleteNotCanc start, consumerID=" + paramCustomerBankInfo.consumerID + ",acctNumber=" + paramCustomerBankInfo.acctNumber, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject = { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ?  AND Status != 'CANC'", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerBank.deleteNotCanc failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    FFSDebug.log("CustomerBank.deleteNotCanc done, rows=" + i, 6);
    return i;
  }
  
  public static int deleteByStatus(CustomerBankInfo paramCustomerBankInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.deleteByStatus start, consumerID=" + paramCustomerBankInfo.consumerID + ",acctNumber=" + paramCustomerBankInfo.acctNumber, 6);
    Object[] arrayOfObject = { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber, paramString };
    int i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ?  AND Status = ?", arrayOfObject);
    FFSDebug.log("CustomerBank.deleteByStatus done, rows=" + i, 6);
    return i;
  }
  
  public static int cancel(CustomerBankInfo paramCustomerBankInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.cancel start, consumerID=" + paramCustomerBankInfo.consumerID + ",acctNumber=" + paramCustomerBankInfo.acctNumber, 6);
    int i = deleteByStatus(paramCustomerBankInfo, "NEW", paramFFSConnectionHolder);
    i += updateStatus(paramCustomerBankInfo, "ACTIVE", "CANC", paramFFSConnectionHolder);
    FFSDebug.log("CustomerBank.cancel done, rows=" + i, 6);
    return i;
  }
  
  public static CustomerBankInfo[] getByConsumerID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.getByConsumerID start, consumerID=" + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID, RTN, AcctNumber, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate FROM BPW_CustomerBankInfo WHERE ConsumerID = ?", new Object[] { paramString });
      while (localFFSResultSet.getNextRow())
      {
        CustomerBankInfo localCustomerBankInfo = new CustomerBankInfo();
        localCustomerBankInfo.consumerID = localFFSResultSet.getColumnString(1);
        localCustomerBankInfo.routingAndTransitNumber = localFFSResultSet.getColumnString(2);
        localCustomerBankInfo.acctNumber = localFFSResultSet.getColumnString(3);
        localCustomerBankInfo.acctType = localFFSResultSet.getColumnString(4);
        localCustomerBankInfo.settlementRefNumber = localFFSResultSet.getColumnString(5);
        localCustomerBankInfo.primaryAcctFlag = localFFSResultSet.getColumnString(6);
        localCustomerBankInfo.status = localFFSResultSet.getColumnString(7);
        localCustomerBankInfo.submitDate = localFFSResultSet.getColumnString(8);
        localArrayList.add(localCustomerBankInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerBank.getByConsumerID failed:" + localException1.toString());
      throw new Exception(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException2) {}
    }
    CustomerBankInfo[] arrayOfCustomerBankInfo = (CustomerBankInfo[])localArrayList.toArray(new CustomerBankInfo[0]);
    return arrayOfCustomerBankInfo;
  }
  
  public static CustomerBankInfo[] getByConsumerIDAndStatus(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.getByConsumerIDAndStatus start, consumerID=" + paramString1, 6);
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID, RTN, AcctNumber, AcctType, SettlementRefNumber, SettlementAcctFlag, Status, Submitdate FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND Status = ?", new Object[] { paramString1, paramString2 });
      while (localFFSResultSet.getNextRow())
      {
        CustomerBankInfo localCustomerBankInfo = new CustomerBankInfo();
        localCustomerBankInfo.consumerID = localFFSResultSet.getColumnString(1);
        localCustomerBankInfo.routingAndTransitNumber = localFFSResultSet.getColumnString(2);
        localCustomerBankInfo.acctNumber = localFFSResultSet.getColumnString(3);
        localCustomerBankInfo.acctType = localFFSResultSet.getColumnString(4);
        localCustomerBankInfo.settlementRefNumber = localFFSResultSet.getColumnString(5);
        localCustomerBankInfo.primaryAcctFlag = localFFSResultSet.getColumnString(6);
        localCustomerBankInfo.status = localFFSResultSet.getColumnString(7);
        localCustomerBankInfo.submitDate = localFFSResultSet.getColumnString(8);
        localArrayList.add(localCustomerBankInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerBank.getByConsumerIDAndStatus failed:" + localException1.toString());
      throw new Exception(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException2) {}
    }
    CustomerBankInfo[] arrayOfCustomerBankInfo = (CustomerBankInfo[])localArrayList.toArray(new CustomerBankInfo[0]);
    return arrayOfCustomerBankInfo;
  }
  
  public static int find(CustomerBankInfo paramCustomerBankInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.find start, consumerID=" + paramCustomerBankInfo.consumerID + ",acctNumber=" + paramCustomerBankInfo.acctNumber + ",status=" + paramString, 6);
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber, paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID FROM BPW_CustomerBankInfo WHERE ConsumerID = ? AND AcctNumber = ? AND Status = ? ", arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        i++;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerBank.find failed:" + localException1.toString());
      throw new Exception(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException2) {}
    }
    FFSDebug.log("CustomerBank.find done, rows=" + i, 6);
    return i;
  }
  
  public static int updateStatus(CustomerBankInfo paramCustomerBankInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerBank.updateStatus start, consumerID=" + paramCustomerBankInfo.consumerID + ",acctNumber=" + paramCustomerBankInfo.acctNumber + ",oldstatus=" + paramString1 + ",newstatus=" + paramString2, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject = { paramString2, paramCustomerBankInfo.consumerID, paramCustomerBankInfo.acctNumber, paramString1 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerBankInfo SET Status=?  WHERE ConsumerID = ?  AND AcctNumber = ? AND Status = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerBank.updateStatus failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    FFSDebug.log("CustomerBank.updateStatus done, rows=" + i, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.CustomerBank
 * JD-Core Version:    0.7.0.1
 */