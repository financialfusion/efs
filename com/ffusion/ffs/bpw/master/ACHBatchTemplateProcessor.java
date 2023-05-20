package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.ACHBatchTemplate;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.ACHBatchTemplateInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class ACHBatchTemplateProcessor
  implements DBConsts, FFSConst, OFXConsts, ACHConsts
{
  public ACHBatchTemplateInfo addACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("ACHBatchTemplateProcessor.addACHBatchTemplate start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str1;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      paramACHBatchTemplateInfo = ACHBatchTemplate.create(localFFSConnectionHolder, paramACHBatchTemplateInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "*** ACHBatchTemplateProcessor.addACHBatchTemplate failed:";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("ACHBatchTemplateProcessor.addACHBatchTemplate: done", 6);
    return paramACHBatchTemplateInfo;
  }
  
  public ACHBatchTemplateInfo modACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("ACHBatchTemplateProcessor.modACHBatchTemplate start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str1;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      throw new FFSException(localThrowable, "Could not get connection");
    }
    try
    {
      paramACHBatchTemplateInfo = ACHBatchTemplate.modify(localFFSConnectionHolder, paramACHBatchTemplateInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "*** ACHBatchTemplateProcessor.modACHBatchTemplate failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("ACHBatchTemplateProcessor.modACHBatchTemplate: done", 6);
    return paramACHBatchTemplateInfo;
  }
  
  public ACHBatchTemplateInfo deleteACHBatchTemplate(ACHBatchTemplateInfo paramACHBatchTemplateInfo)
    throws FFSException
  {
    FFSDebug.log("ACHBatchTemplateProcessor.deleteACHBatchTemplate start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
    }
    catch (Throwable localThrowable)
    {
      str = "Could not get connection";
      FFSDebug.stackTrace(localThrowable);
      throw new FFSException(str);
    }
    try
    {
      paramACHBatchTemplateInfo = ACHBatchTemplate.delete(localFFSConnectionHolder, paramACHBatchTemplateInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "*** ACHBatchTemplateProcessor.deleteACHBatchTemplate failed: ";
      FFSDebug.log(str, 0);
      throw new FFSException(str + localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("ACHBatchTemplateProcessor.deleteACHBatchTemplate: done", 6);
    return paramACHBatchTemplateInfo;
  }
  
  public ACHBatchTemplateInfo getACHBatchTemplate(String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHBatchTemplateProcessor.getACHBatchTemplate start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    ACHBatchTemplateInfo localACHBatchTemplateInfo1 = null;
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***ACHBatchTemplateProcessor.getACHBatchTemplate failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      localACHBatchTemplateInfo1 = ACHBatchTemplate.getACHBatchTemplate(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      ACHBatchTemplateInfo localACHBatchTemplateInfo2 = localACHBatchTemplateInfo1;
      return localACHBatchTemplateInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***ACHBatchTemplateProcessor.getACHBatchTemplate failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplateByCompany(String paramString)
    throws FFSException
  {
    FFSDebug.log("ACHBatchTemplateProcessor.getACHBatchTemplateByCompany start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***ACHBatchTemplateProcessor.getACHBatchTemplateByCompany failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo = ACHBatchTemplate.getACHBatchTemplateByCompany(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      localObject1 = arrayOfACHBatchTemplateInfo;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***ACHBatchTemplateProcessor.getACHBatchTemplateByCompany failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplateByGroup(String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("ACHBatchTemplateProcessor.getACHBatchTemplateByCompany start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    Object localObject1;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      localObject1 = "***ACHBatchTemplateProcessor.getACHBatchTemplateByGroup failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    try
    {
      ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo = ACHBatchTemplate.getACHBatchTemplateByGroup(localFFSConnectionHolder, paramString1, paramString2);
      localFFSConnectionHolder.conn.commit();
      localObject1 = arrayOfACHBatchTemplateInfo;
      return localObject1;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject1 = "***ACHBatchTemplateProcessor.getACHBatchTemplateByGroup failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException(localThrowable2, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ACHBatchTemplateInfo[] getACHBatchTemplate(String[] paramArrayOfString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo1 = null;
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    }
    catch (Throwable localThrowable1)
    {
      str = "***ACHBatchTemplateProcessor.getACHBatchTemplate (Multiple) failed:Error Retrieving Database Connection" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable1, str);
    }
    try
    {
      arrayOfACHBatchTemplateInfo1 = ACHBatchTemplate.getACHBatchTemplate(localFFSConnectionHolder, paramArrayOfString);
      localFFSConnectionHolder.conn.commit();
      ACHBatchTemplateInfo[] arrayOfACHBatchTemplateInfo2 = arrayOfACHBatchTemplateInfo1;
      return arrayOfACHBatchTemplateInfo2;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      str = "***ACHBatchTemplateProcessor.getACHBatchTemplate (Multiple) failed:" + localThrowable2.toString() + FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log(str, 0);
      throw new FFSException(localThrowable2, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHBatchTemplateProcessor
 * JD-Core Version:    0.7.0.1
 */