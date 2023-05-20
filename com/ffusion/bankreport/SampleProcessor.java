package com.ffusion.bankreport;

import com.ffusion.bankreport.adapter.IBankReportAdapter;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.bankreport.BankReport;
import com.ffusion.beans.bankreport.BankReportDefinition;
import com.ffusion.beans.bankreport.ReportLineItem;
import com.ffusion.efs.adapters.profile.AccountAdapter;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.db.DBUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class SampleProcessor
  implements IBankReportProcessor
{
  private static final String c = "H ";
  private static final String e = "D ";
  private static final String jdField_null = "DP";
  private static final String jdField_goto = "Account: ";
  private static final String jdField_else = "Generated: ";
  private static final String jdField_long = "Updateable";
  private static final String b = "1000";
  private static final String d = "MM-dd-yyyy HH:mm:ss";
  private IBankReportAdapter jdField_void;
  
  public void processReport(BankReportDefinition paramBankReportDefinition, InputStream paramInputStream, HashMap paramHashMap)
    throws BRException
  {
    Connection localConnection = null;
    try
    {
      localConnection = this.jdField_void.getConnection();
      DBUtil.beginTransaction(localConnection);
      String str1 = (String)paramBankReportDefinition.getOptions().get("Updateable");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
      HashMap localHashMap = new HashMap();
      BankReport localBankReport = new BankReport(Locale.getDefault());
      DateTime localDateTime = new DateTime(new Date(), Locale.getDefault());
      localBankReport.setType(paramBankReportDefinition.getReportType());
      localBankReport.setImportTime(localDateTime);
      Account localAccount = null;
      int i = 0;
      int j = 0;
      String str2;
      while ((str2 = localBufferedReader.readLine()) != null)
      {
        j++;
        String str3 = str2.substring(0, 2);
        String str4 = str2.substring(2);
        Object localObject1;
        Object localObject2;
        if (str3.equals("H "))
        {
          if (str4.startsWith("Generated: "))
          {
            localObject1 = new DateTime(str4.substring("Generated: ".length()), Locale.getDefault(), "MM-dd-yyyy HH:mm:ss");
            localBankReport.setGeneratedTime((DateTime)localObject1);
            if (str1.equals("TRUE")) {
              this.jdField_void.removeReportFile(localConnection, paramBankReportDefinition.getReportType(), (DateTime)localObject1, new HashMap());
            }
          }
          else if (str4.startsWith("Account: "))
          {
            i = 0;
            localObject1 = str4.substring("Account: ".length());
            localObject2 = new Account();
            ((Account)localObject2).setID((String)localObject1);
            Accounts localAccounts = AccountAdapter.getAccounts((Account)localObject2, 0);
            if (localAccounts.size() != 1) {
              throw new BRException(60301, "Failed to process Bank Report file - Account ID not unique.");
            }
            localAccount = (Account)localAccounts.get(0);
            if (localAccount == null)
            {
              i = 1;
            }
            else
            {
              int k = this.jdField_void.getBusinessIdFromAccount("1000", localAccount.getRoutingNum(), (String)localObject1);
              boolean bool2 = false;
              if (k == -1)
              {
                k = this.jdField_void.getUserIdFromAccount("1000", localAccount.getRoutingNum(), (String)localObject1);
                bool2 = BankReportUtil.checkEntitlementForUser(k, paramBankReportDefinition.getReportType(), localAccount);
              }
              else
              {
                bool2 = BankReportUtil.checkEntitlementForBusiness(k, paramBankReportDefinition.getReportType(), localAccount);
              }
              if (bool2)
              {
                Integer localInteger = (Integer)localHashMap.get(new Integer(k));
                if (localInteger == null)
                {
                  localBankReport.setDirectoryID(k);
                  this.jdField_void.insertBankReport(localConnection, localBankReport, new HashMap());
                  localInteger = new Integer(localBankReport.getReportID());
                  localHashMap.put(new Integer(k), localInteger);
                }
                localBankReport.setReportID(localInteger.intValue());
              }
              else
              {
                i = 1;
              }
            }
          }
        }
        else if (((str3.equals("D ")) || (str3.equals("DP"))) && (i == 0))
        {
          localObject1 = "_" + str4;
          str4 = ((String)localObject1).trim().substring(1);
          localObject2 = new ReportLineItem(Locale.getDefault());
          ((ReportLineItem)localObject2).setReportID(localBankReport.getReportID());
          ((ReportLineItem)localObject2).setLineNumber(j);
          ((ReportLineItem)localObject2).setData(str4);
          ((ReportLineItem)localObject2).setRoutingNum(localAccount.getRoutingNum());
          ((ReportLineItem)localObject2).setAccountID(localAccount.getID());
          boolean bool1 = false;
          if (str3.equals("DP")) {
            bool1 = true;
          }
          ((ReportLineItem)localObject2).setForcePageBreak(bool1);
          this.jdField_void.insertLineItem(localConnection, (ReportLineItem)localObject2, new HashMap());
        }
      }
      DBUtil.commit(localConnection);
      return;
    }
    catch (BRException localBRException)
    {
      DBUtil.rollback(localConnection);
      throw localBRException;
    }
    catch (Throwable localThrowable)
    {
      DBUtil.rollback(localConnection);
      throw new BRException(60301, "Failed to process BankReport file.", localThrowable);
    }
    finally
    {
      try
      {
        this.jdField_void.releaseConnection(localConnection);
      }
      catch (Exception localException2) {}
    }
  }
  
  public IBankReportAdapter getBankReportAdapter()
  {
    return this.jdField_void;
  }
  
  public void setBankReportAdapter(IBankReportAdapter paramIBankReportAdapter)
  {
    this.jdField_void = paramIBankReportAdapter;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.bankreport.SampleProcessor
 * JD-Core Version:    0.7.0.1
 */