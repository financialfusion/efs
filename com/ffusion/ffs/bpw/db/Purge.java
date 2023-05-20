package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.CleanupRequestInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class Purge
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  private static int kk = ACHAdapterUtil.getPropertyInt("BatchSize", 1000);
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "Purge.cleanup:";
    FFSDebug.log(str1, " start...", 6);
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    int i = 0;
    Object localObject1 = null;
    Object localObject2 = null;
    int j = 0;
    int k = 0;
    try
    {
      for (CleanupRequestInfo[] arrayOfCleanupRequestInfo = CleanupRequest.getCleanupRequest(paramFFSConnectionHolder, kk); arrayOfCleanupRequestInfo != null; arrayOfCleanupRequestInfo = CleanupRequest.getCleanupRequest(paramFFSConnectionHolder, kk))
      {
        int m = arrayOfCleanupRequestInfo.length;
        if (m == 0) {
          break;
        }
        for (int n = 0; n < m; n++)
        {
          k++;
          localObject1 = arrayOfCleanupRequestInfo[n];
          arrayOfString1 = localObject1.getPaymentTypeList();
          arrayOfString2 = localObject1.getAgeInDaysList();
          for (int i1 = 0; i1 < arrayOfString1.length; i1++)
          {
            String str2 = arrayOfString1[i1].trim();
            i = Integer.parseInt(arrayOfString2[i1].trim());
            if ((str2.equalsIgnoreCase("WIRETRN")) || (str2.equalsIgnoreCase("RECWIRETRN")))
            {
              Wire.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, false);
              Wire.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, true);
            }
            else if ((str2.equalsIgnoreCase("ACHBATCHTRN")) || (str2.equalsIgnoreCase("RECACHBATCHTRN")))
            {
              ACHBatch.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, false, "ACH_BATCH_PAYMENT");
              ACHBatch.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, true, "ACH_BATCH_PAYMENT");
              ACHFile.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i);
            }
            else if (str2.equalsIgnoreCase("TAXTRN"))
            {
              ACHBatch.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, false, "ACH_BATCH_TAX");
            }
            else if (str2.equalsIgnoreCase("CHILDSUPPORTTRN"))
            {
              ACHBatch.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, false, "ACH_BATCH_CHILD_SUPPORT");
              ACHBatch.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, true, "ACH_BATCH_CHILD_SUPPORT");
            }
            else if ((str2.equalsIgnoreCase("ETFTRN")) || (str2.equalsIgnoreCase("RECETFTRN")))
            {
              Transfer.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, 0);
              Transfer.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, 1);
              Transfer.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, 2);
              TransferBatch.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i);
            }
            else if ((str2.equalsIgnoreCase("INTRATRN")) || (str2.equalsIgnoreCase("RECINTRATRN")))
            {
              XferInstruction.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i);
              RecXferInstruction.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i);
            }
            else if ((str2.equalsIgnoreCase("PMTTRN")) || (str2.equalsIgnoreCase("RECPMTTRN")))
            {
              PmtInstruction.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i);
              RecPmtInstruction.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i);
              PaymentBatch.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId());
            }
            else if (str2.equalsIgnoreCase("CASHCONTRN"))
            {
              CashCon.cleanup(paramFFSConnectionHolder, localObject1.getCustomerId(), i, false);
            }
            if (i > j) {
              j = i;
            }
            paramFFSConnectionHolder.conn.commit();
          }
        }
        if (j != 0)
        {
          Trans.deleteTransID(paramFFSConnectionHolder, j);
          paramFFSConnectionHolder.conn.commit();
        }
        n = CleanupRequest.delete(paramFFSConnectionHolder, arrayOfCleanupRequestInfo);
        paramFFSConnectionHolder.conn.commit();
        if (m < kk) {
          break;
        }
      }
    }
    catch (FFSException localFFSException)
    {
      paramFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** Purge.cleanup(): failed:" + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      paramFFSConnectionHolder.conn.rollback();
      FFSDebug.log("*** Purge.cleanup(): failed:" + localException1.toString());
      throw new FFSException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localObject2 != null)
        {
          localObject2.close();
          localObject2 = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** Purge.cleanup(): failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Purge.cleanup(): end.", 6);
    return k;
  }
  
  public static int getPageSize()
  {
    return kk;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Purge
 * JD-Core Version:    0.7.0.1
 */