package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.ConsumerCrossRefInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;

public class ConsumerCrossRef
  implements FFSConst, DBConsts, BPWResource
{
  private static final String xq = "SELECT ConsumerID, FederalTaxID, ConsumerSSN, SponsorID, SponsorConsumerID, ConsumerType, Submitdate FROM BPW_ConsumerCrossReference WHERE ConsumerID = ?";
  private static final String xs = "SELECT ConsumerID, FederalTaxID, ConsumerSSN, SponsorID, SponsorConsumerID, ConsumerType, Submitdate FROM BPW_ConsumerCrossReference WHERE SponsorConsumerID = ?";
  private static final String xv = "SELECT SponsorConsumerID FROM BPW_ConsumerCrossReference WHERE ConsumerID = ?";
  private static final String xt = "SELECT ConsumerID FROM BPW_ConsumerCrossReference WHERE SponsorConsumerID = ?";
  private static final String xx = "SELECT ConsumerID, FederalTaxID, ConsumerSSN, SponsorID, SponsorConsumerID, ConsumerType, Submitdate FROM BPW_ConsumerCrossReference ";
  private static final String xw = "INSERT INTO BPW_ConsumerCrossReference( ConsumerID, FederalTaxID, ConsumerSSN, SponsorID, SponsorConsumerID, ConsumerType, Submitdate ) VALUES(?,?,?,?,?,?,?)";
  private static final String xr = "UPDATE BPW_ConsumerCrossReference SET FederalTaxID = ?, ConsumerSSN = ?, SponsorID = ?, SponsorConsumerID = ?, ConsumerType=? WHERE ConsumerID = ?";
  private static final String xu = "DELETE FROM BPW_ConsumerCrossReference WHERE ConsumerID = ?";
  
  public static int add(ConsumerCrossRefInfo paramConsumerCrossRefInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ConsumerCrossRef.add start, consumerID=" + paramConsumerCrossRefInfo.consumerID, 6);
    if ((paramConsumerCrossRefInfo.consumerType != null) && (!paramConsumerCrossRefInfo.consumerType.equals("INDIVIDUAL")) && (!paramConsumerCrossRefInfo.consumerType.equals("SMALLBS")))
    {
      String str1 = "Your ConsumerType of " + paramConsumerCrossRefInfo.consumerType + " is not supported, change AccessType to INDIVIDUAL or SMALLBS";
      FFSDebug.log("*** ConsumerCrossRef.add failed:" + str1);
      throw new Exception(str1);
    }
    int i = 0;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID, FederalTaxID, ConsumerSSN, SponsorID, SponsorConsumerID, ConsumerType, Submitdate FROM BPW_ConsumerCrossReference WHERE ConsumerID = ?", new Object[] { paramConsumerCrossRefInfo.consumerID });
      Object localObject1;
      if (localFFSResultSet.getNextRow())
      {
        localObject1 = localFFSResultSet.getColumnString(1);
        String str2 = localFFSResultSet.getColumnString(2);
        String str3 = localFFSResultSet.getColumnString(3);
        String str4 = localFFSResultSet.getColumnString(4);
        String str5 = localFFSResultSet.getColumnString(5);
        String str6 = localFFSResultSet.getColumnString(6);
        if (((str2 != null) && (!str2.equals(paramConsumerCrossRefInfo.federalTaxID))) || ((str3 != null) && (!str3.equals(paramConsumerCrossRefInfo.consumerSSN))) || ((str4 != null) && (!str4.equals(paramConsumerCrossRefInfo.sponsorID))) || (!str5.equals(paramConsumerCrossRefInfo.customerID)) || ((paramConsumerCrossRefInfo.consumerType != null) && (str6 != null) && (!str6.equals(paramConsumerCrossRefInfo.consumerType))))
        {
          Object[] arrayOfObject = { paramConsumerCrossRefInfo.federalTaxID, paramConsumerCrossRefInfo.consumerSSN, paramConsumerCrossRefInfo.sponsorID, paramConsumerCrossRefInfo.customerID, paramConsumerCrossRefInfo.consumerType != null ? paramConsumerCrossRefInfo.consumerType : str6, paramConsumerCrossRefInfo.consumerID };
          i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_ConsumerCrossReference SET FederalTaxID = ?, ConsumerSSN = ?, SponsorID = ?, SponsorConsumerID = ?, ConsumerType=? WHERE ConsumerID = ?", arrayOfObject);
        }
      }
      else
      {
        localObject1 = new Object[] { paramConsumerCrossRefInfo.consumerID, paramConsumerCrossRefInfo.federalTaxID, paramConsumerCrossRefInfo.consumerSSN, paramConsumerCrossRefInfo.sponsorID, paramConsumerCrossRefInfo.customerID, paramConsumerCrossRefInfo.consumerType, FFSUtil.getDateString() };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_ConsumerCrossReference( ConsumerID, FederalTaxID, ConsumerSSN, SponsorID, SponsorConsumerID, ConsumerType, Submitdate ) VALUES(?,?,?,?,?,?,?)", (Object[])localObject1);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ConsumerCrossRef.add failed:" + localException1.toString());
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
    FFSDebug.log("ConsumerCrossRef.add done, rows=" + i, 6);
    return i;
  }
  
  public static int delete(ConsumerCrossRefInfo paramConsumerCrossRefInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ConsumerCrossRef.delete start, consumerID=" + paramConsumerCrossRefInfo.consumerID, 6);
    int i = 0;
    try
    {
      Object[] arrayOfObject = { paramConsumerCrossRefInfo.consumerID };
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_ConsumerCrossReference WHERE ConsumerID = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** ConsumerCrossRef.delete failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    FFSDebug.log("ConsumerCrossRef.delete done, rows=" + i, 6);
    return i;
  }
  
  public static ConsumerCrossRefInfo getByConsumerID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ConsumerCrossRef.getByConsumerID start, consumerID=" + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID, FederalTaxID, ConsumerSSN, SponsorID, SponsorConsumerID, ConsumerType, Submitdate FROM BPW_ConsumerCrossReference WHERE ConsumerID = ?", new Object[] { paramString });
      if (localFFSResultSet.getNextRow())
      {
        ConsumerCrossRefInfo localConsumerCrossRefInfo1 = new ConsumerCrossRefInfo();
        localConsumerCrossRefInfo1.consumerID = localFFSResultSet.getColumnString(1);
        localConsumerCrossRefInfo1.federalTaxID = localFFSResultSet.getColumnString(2);
        localConsumerCrossRefInfo1.consumerSSN = localFFSResultSet.getColumnString(3);
        localConsumerCrossRefInfo1.sponsorID = localFFSResultSet.getColumnString(4);
        localConsumerCrossRefInfo1.customerID = localFFSResultSet.getColumnString(5);
        localConsumerCrossRefInfo1.consumerType = localFFSResultSet.getColumnString(6);
        localConsumerCrossRefInfo1.submitDate = localFFSResultSet.getColumnString(7);
        ConsumerCrossRefInfo localConsumerCrossRefInfo2 = localConsumerCrossRefInfo1;
        return localConsumerCrossRefInfo2;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ConsumerCrossRef.getByConsumerID failed:" + localException1.toString());
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
    return null;
  }
  
  public static ConsumerCrossRefInfo getByCustomerID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ConsumerCrossRef.getByCustomerID start, customerID=" + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID, FederalTaxID, ConsumerSSN, SponsorID, SponsorConsumerID, ConsumerType, Submitdate FROM BPW_ConsumerCrossReference WHERE SponsorConsumerID = ?", new Object[] { paramString });
      if (localFFSResultSet.getNextRow())
      {
        ConsumerCrossRefInfo localConsumerCrossRefInfo1 = new ConsumerCrossRefInfo();
        localConsumerCrossRefInfo1.consumerID = localFFSResultSet.getColumnString(1);
        localConsumerCrossRefInfo1.federalTaxID = localFFSResultSet.getColumnString(2);
        localConsumerCrossRefInfo1.consumerSSN = localFFSResultSet.getColumnString(3);
        localConsumerCrossRefInfo1.sponsorID = localFFSResultSet.getColumnString(4);
        localConsumerCrossRefInfo1.customerID = localFFSResultSet.getColumnString(5);
        localConsumerCrossRefInfo1.consumerType = localFFSResultSet.getColumnString(6);
        localConsumerCrossRefInfo1.submitDate = localFFSResultSet.getColumnString(7);
        ConsumerCrossRefInfo localConsumerCrossRefInfo2 = localConsumerCrossRefInfo1;
        return localConsumerCrossRefInfo2;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ConsumerCrossRef.getByCustomerID failed:" + localException1.toString());
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
    return null;
  }
  
  public static String lookupCustomerID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ConsumerCrossRef.lookupCustomerID start, consumerID=" + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT SponsorConsumerID FROM BPW_ConsumerCrossReference WHERE ConsumerID = ?", new Object[] { paramString });
      if (localFFSResultSet.getNextRow())
      {
        String str1 = localFFSResultSet.getColumnString(1);
        String str2 = str1;
        return str2;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ConsumerCrossRef.lookupCustomerID failed:" + localException1.toString());
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
    return null;
  }
  
  public static String lookupConsumerID(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ConsumerCrossRef.lookupConsumerID start, customerID=" + paramString, 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT ConsumerID FROM BPW_ConsumerCrossReference WHERE SponsorConsumerID = ?", new Object[] { paramString });
      if (localFFSResultSet.getNextRow())
      {
        String str1 = localFFSResultSet.getColumnString(1);
        String str2 = str1;
        return str2;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** ConsumerCrossRef.lookupConsumerID failed:" + localException1.toString());
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
    return null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ConsumerCrossRef
 * JD-Core Version:    0.7.0.1
 */