package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerProductAccessInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.util.ArrayList;

public class CustomerProductAccess
  implements FFSConst, DBConsts, BPWResource
{
  private static final String kz = "SELECT ConsumerID, ProductType, AccessType, StatusType, Submitdate FROM BPW_CustomerProductAccess WHERE ConsumerID = ?";
  private static final String kv = "SELECT AccessType FROM BPW_CustomerProductAccess WHERE ConsumerID = ? AND StatusType = ? ";
  private static final String kE = "INSERT INTO BPW_CustomerProductAccess( ConsumerID, ProductType, AccessType, StatusType, Submitdate ) VALUES(?,?,?,?,?)";
  private static final String kx = "UPDATE BPW_CustomerProductAccess SET ProductType = ?, AccessType = ?, StatusType = ? WHERE ConsumerID = ?";
  private static final String kB = "UPDATE BPW_CustomerProductAccess SET ProductType = ?, AccessType = ?, StatusType = ? WHERE ConsumerID = ? AND StatusType = ? ";
  private static final String kA = "DELETE FROM BPW_CustomerProductAccess WHERE ConsumerID = ?";
  private static final String kC = "DELETE FROM BPW_CustomerProductAccess WHERE ConsumerID = ?  AND StatusType = ?";
  private static final String ky = "DELETE FROM BPW_CustomerProductAccess WHERE ConsumerID = ?  AND StatusType != 'CANC'";
  private static final String kw = "UPDATE BPW_CustomerProductAccess SET StatusType=?  WHERE ConsumerID = ? AND StatusType = ?";
  private static final String kD = "SELECT ConsumerID FROM BPW_CustomerProductAccess WHERE ConsumerID = ? AND StatusType = ? ";
  
  public static int add(CustomerProductAccessInfo paramCustomerProductAccessInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.add start, consumerID=" + paramCustomerProductAccessInfo.consumerID, 6);
    String str1;
    if (!paramCustomerProductAccessInfo.productType.equals("BILLPAY"))
    {
      str1 = "Your ProductType of " + paramCustomerProductAccessInfo.productType + " is not supported, change ProductType to BILLPAY";
      FFSDebug.log("*** CustomerProductAccess.add failed:" + str1);
      throw new Exception(str1);
    }
    if ((!paramCustomerProductAccessInfo.accessType.equals("INTERNET")) && (!paramCustomerProductAccessInfo.accessType.equals("PC")) && (!paramCustomerProductAccessInfo.accessType.equals("PHONE")))
    {
      str1 = "Your AccessType of " + paramCustomerProductAccessInfo.accessType + " is not supported, change AccessType to INTERNET, PC or PHONE";
      FFSDebug.log("*** CustomerProductAccess.add failed:" + str1);
      throw new Exception(str1);
    }
    int i = 0;
    int j = 1;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID, ProductType, AccessType, StatusType, Submitdate FROM BPW_CustomerProductAccess WHERE ConsumerID = ?", new Object[] { paramCustomerProductAccessInfo.consumerID });
      Object localObject1;
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = localFFSResultSet.getColumnString(2);
        String str2 = localFFSResultSet.getColumnString(3);
        String str3 = localFFSResultSet.getColumnString(4);
        if ((!((String)localObject1).equals(paramCustomerProductAccessInfo.productType)) || (!str2.equals(paramCustomerProductAccessInfo.accessType)))
        {
          if (str3.equals("CANC"))
          {
            int k = 0;
            return k;
          }
          Object localObject2;
          if (str3.equals("NEW"))
          {
            j = 0;
            localObject2 = new Object[] { paramCustomerProductAccessInfo.productType, paramCustomerProductAccessInfo.accessType, str3, paramCustomerProductAccessInfo.consumerID, str3 };
            i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerProductAccess SET ProductType = ?, AccessType = ?, StatusType = ? WHERE ConsumerID = ? AND StatusType = ? ", (Object[])localObject2);
          }
          else if (str3.equals("ACTIVE"))
          {
            j = 0;
            updateStatus(paramCustomerProductAccessInfo.consumerID, "ACTIVE", "CANC", paramFFSConnectionHolder);
            localObject2 = new Object[] { paramCustomerProductAccessInfo.consumerID, paramCustomerProductAccessInfo.productType, paramCustomerProductAccessInfo.accessType, "NEW", FFSUtil.getDateString() };
            i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustomerProductAccess( ConsumerID, ProductType, AccessType, StatusType, Submitdate ) VALUES(?,?,?,?,?)", (Object[])localObject2);
          }
          else
          {
            localObject2 = "Unknown status " + str3;
            FFSDebug.log("*** CustomerProductAccess.add failed:" + (String)localObject2);
            throw new Exception((String)localObject2);
          }
        }
        else
        {
          j = 0;
          if (str3.equals("CANC"))
          {
            deleteNotCanc(paramCustomerProductAccessInfo, paramFFSConnectionHolder);
            i = updateStatus(paramCustomerProductAccessInfo.consumerID, "CANC", "ACTIVE", paramFFSConnectionHolder);
          }
          else
          {
            if ((str3.equals("ACTIVE")) || (str3.equals("NEW"))) {
              break;
            }
          }
        }
      }
      if (j != 0)
      {
        localObject1 = new Object[] { paramCustomerProductAccessInfo.consumerID, paramCustomerProductAccessInfo.productType, paramCustomerProductAccessInfo.accessType, "NEW", FFSUtil.getDateString() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustomerProductAccess( ConsumerID, ProductType, AccessType, StatusType, Submitdate ) VALUES(?,?,?,?,?)", (Object[])localObject1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerProductAccess.add failed:" + localException1.toString());
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
    FFSDebug.log("CustomerProductAccess.add done, rows=" + i, 6);
    return i;
  }
  
  public static int update(CustomerProductAccessInfo paramCustomerProductAccessInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.update start, consumerID=" + paramCustomerProductAccessInfo.consumerID, 6);
    String str1;
    if (!paramCustomerProductAccessInfo.productType.equals("BILLPAY"))
    {
      str1 = "Your ProductType of " + paramCustomerProductAccessInfo.productType + " is not supported, change ProductType to BILLPAY";
      FFSDebug.log("*** CustomerProductAccess.update failed:" + str1);
      throw new Exception(str1);
    }
    if ((!paramCustomerProductAccessInfo.accessType.equals("INTERNET")) && (!paramCustomerProductAccessInfo.accessType.equals("PC")) && (!paramCustomerProductAccessInfo.accessType.equals("PHONE")))
    {
      str1 = "Your AccessType of " + paramCustomerProductAccessInfo.accessType + " is not supported, change AccessType to INTERNET, PC or PHONE";
      FFSDebug.log("*** CustomerProductAccess.update failed:" + str1);
      throw new Exception(str1);
    }
    if ((!paramCustomerProductAccessInfo.statusType.equals("ACTIVE")) && (!paramCustomerProductAccessInfo.statusType.equals("CLOSED")))
    {
      str1 = "Your status of " + paramCustomerProductAccessInfo.statusType + " is not supported, change Status to ACTIVE or CLOSED";
      FFSDebug.log("*** CustomerProductAccess.update failed:" + str1);
      throw new Exception(str1);
    }
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID, ProductType, AccessType, StatusType, Submitdate FROM BPW_CustomerProductAccess WHERE ConsumerID = ?", new Object[] { paramCustomerProductAccessInfo.consumerID });
      Object localObject1;
      if (localFFSResultSet.getNextRow())
      {
        localObject1 = localFFSResultSet.getColumnString(2);
        String str2 = localFFSResultSet.getColumnString(3);
        String str3 = localFFSResultSet.getColumnString(4);
        if ((!((String)localObject1).equals(paramCustomerProductAccessInfo.productType)) || (!str2.equals(paramCustomerProductAccessInfo.accessType)))
        {
          Object[] arrayOfObject = { paramCustomerProductAccessInfo.productType, paramCustomerProductAccessInfo.accessType, paramCustomerProductAccessInfo.statusType, paramCustomerProductAccessInfo.consumerID };
          i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerProductAccess SET ProductType = ?, AccessType = ?, StatusType = ? WHERE ConsumerID = ?", arrayOfObject);
        }
      }
      else
      {
        localObject1 = new Object[] { paramCustomerProductAccessInfo.consumerID, paramCustomerProductAccessInfo.productType, paramCustomerProductAccessInfo.accessType, paramCustomerProductAccessInfo.statusType, FFSUtil.getDateString() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustomerProductAccess( ConsumerID, ProductType, AccessType, StatusType, Submitdate ) VALUES(?,?,?,?,?)", (Object[])localObject1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerProductAccess.update failed:" + localException1.toString());
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
    FFSDebug.log("CustomerProductAccess.update done, rows=" + i, 6);
    return i;
  }
  
  public static int delete(CustomerProductAccessInfo paramCustomerProductAccessInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.delete start, consumerID=" + paramCustomerProductAccessInfo.consumerID, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject = { paramCustomerProductAccessInfo.consumerID };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerProductAccess WHERE ConsumerID = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerProductAccess.delete failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    FFSDebug.log("CustomerProductAccess.delete done, rows=" + i, 6);
    return i;
  }
  
  public static int deleteNotCanc(CustomerProductAccessInfo paramCustomerProductAccessInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.deleteNotCanc start, consumerID=" + paramCustomerProductAccessInfo.consumerID, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject = { paramCustomerProductAccessInfo.consumerID };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerProductAccess WHERE ConsumerID = ?  AND StatusType != 'CANC'", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerProductAccess.deleteNotCanc failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    FFSDebug.log("CustomerProductAccess.deleteNotCanc done, rows=" + i, 6);
    return i;
  }
  
  public static int deleteByStatus(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.deleteByStatus start, consumerID=" + paramString1, 6);
    Object[] arrayOfObject = { paramString1, paramString2 };
    int i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerProductAccess WHERE ConsumerID = ?  AND StatusType = ?", arrayOfObject);
    FFSDebug.log("CustomerProductAccess.deleteByStatus done, rows=" + i, 6);
    return i;
  }
  
  public static int cancel(CustomerProductAccessInfo paramCustomerProductAccessInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.cancel start, consumerID=" + paramCustomerProductAccessInfo.consumerID, 6);
    int i = deleteByStatus(paramCustomerProductAccessInfo.consumerID, "NEW", paramFFSConnectionHolder);
    i += updateStatus(paramCustomerProductAccessInfo.consumerID, "ACTIVE", "CANC", paramFFSConnectionHolder);
    FFSDebug.log("CustomerProductAccess.cancel done, rows=" + i, 6);
    return i;
  }
  
  public static CustomerProductAccessInfo[] get(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.get start, consumerID=" + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID, ProductType, AccessType, StatusType, Submitdate FROM BPW_CustomerProductAccess WHERE ConsumerID = ?", new Object[] { paramString });
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = new CustomerProductAccessInfo();
        ((CustomerProductAccessInfo)localObject1).consumerID = localFFSResultSet.getColumnString(1);
        ((CustomerProductAccessInfo)localObject1).productType = localFFSResultSet.getColumnString(2);
        ((CustomerProductAccessInfo)localObject1).accessType = localFFSResultSet.getColumnString(3);
        ((CustomerProductAccessInfo)localObject1).statusType = localFFSResultSet.getColumnString(4);
        ((CustomerProductAccessInfo)localObject1).submitDate = localFFSResultSet.getColumnString(5);
        localArrayList.add(localObject1);
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException) {}
    }
    Object localObject1 = (CustomerProductAccessInfo[])localArrayList.toArray(new CustomerProductAccessInfo[0]);
    return localObject1;
  }
  
  public static String getAccessTypeByStatus(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.getAccessTypeByStatus start, consumerID=" + paramString1 + ",status=" + paramString2, 6);
    String str = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT AccessType FROM BPW_CustomerProductAccess WHERE ConsumerID = ? AND StatusType = ? ", new Object[] { paramString1, paramString2 });
      if (localFFSResultSet.getNextRow())
      {
        CustomerProductAccessInfo localCustomerProductAccessInfo = new CustomerProductAccessInfo();
        str = localFFSResultSet.getColumnString(1);
      }
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
      }
      catch (Exception localException) {}
    }
    return str;
  }
  
  public static int find(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.find, consumerID=" + paramString1 + ",status=" + paramString2, 6);
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramString1, paramString2 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID FROM BPW_CustomerProductAccess WHERE ConsumerID = ? AND StatusType = ? ", arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        i++;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** CustomerProductAccess.find failed:" + localException1.toString());
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
    FFSDebug.log("CustomerProductAccess.find done, rows=" + i, 6);
    return i;
  }
  
  public static int updateStatus(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomerProductAccess.updateStatus start, consumerID=" + paramString1 + ",oldstatus=" + paramString2 + ",newstatus=" + paramString3, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject = { paramString3, paramString1, paramString2 };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerProductAccess SET StatusType=?  WHERE ConsumerID = ? AND StatusType = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustomerProductAccess.updateStatus failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    FFSDebug.log("CustomerProductAccess.updateStatus done, rows=" + i, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.CustomerProductAccess
 * JD-Core Version:    0.7.0.1
 */