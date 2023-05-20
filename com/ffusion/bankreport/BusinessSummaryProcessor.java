package com.ffusion.bankreport;

import com.ffusion.bankreport.adapter.IBankReportAdapter;
import com.ffusion.beans.bankreport.BankReport;
import com.ffusion.beans.bankreport.BankReportDefinition;
import com.ffusion.beans.bankreport.ReportLineItem;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.Businesses;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.db.DBUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class BusinessSummaryProcessor
  implements IBankReportProcessor
{
  private static final String jdField_try = "H ";
  private static final String jdField_case = "D ";
  private static final String jdField_do = "DP";
  private static final String jdField_new = "Business: ";
  private static final String jdField_char = "User: ";
  private static final String a = "Generated: ";
  private static final String jdField_if = "Updateable";
  private static final String jdField_int = "1000";
  private static final String jdField_byte = "MM-dd-yyyy HH:mm:ss";
  private IBankReportAdapter jdField_for;
  
  public void processReport(BankReportDefinition paramBankReportDefinition, InputStream paramInputStream, HashMap paramHashMap)
    throws BRException
  {
    Connection localConnection = null;
    try
    {
      localConnection = this.jdField_for.getConnection();
      DBUtil.beginTransaction(localConnection);
      String str1 = (String)paramBankReportDefinition.getOptions().get("Updateable");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
      HashMap localHashMap = new HashMap();
      BankReport localBankReport = new BankReport(Locale.getDefault());
      DateTime localDateTime = new DateTime(new Date(), Locale.getDefault());
      localBankReport.setType(paramBankReportDefinition.getReportType());
      localBankReport.setImportTime(localDateTime);
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
              this.jdField_for.removeReportFile(localConnection, paramBankReportDefinition.getReportType(), (DateTime)localObject1, new HashMap());
            }
          }
          else
          {
            Object localObject3;
            int k;
            boolean bool2;
            Integer localInteger;
            if (str4.startsWith("Business: "))
            {
              i = 0;
              localObject1 = str4.substring("Business: ".length());
              localObject2 = new Business();
              ((Business)localObject2).setCustId((String)localObject1);
              ((Business)localObject2).setBankId("1000");
              localObject3 = BusinessAdapter.getBusinesses((Business)localObject2);
              if (((Businesses)localObject3).size() != 1) {
                throw new BRException(60301, "Failed to process Bank Report file - Customer ID not unique/not found.");
              }
              localObject2 = (Business)((Businesses)localObject3).get(0);
              k = ((Business)localObject2).getIdValue();
              bool2 = false;
              bool2 = BankReportUtil.checkEntitlementForBusiness(k, paramBankReportDefinition.getReportType(), null);
              if (bool2)
              {
                localInteger = (Integer)localHashMap.get(new Integer(k));
                if (localInteger == null)
                {
                  localBankReport.setDirectoryID(k);
                  this.jdField_for.insertBankReport(localConnection, localBankReport, new HashMap());
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
            else if (str4.startsWith("User: "))
            {
              i = 0;
              localObject1 = str4.substring("User: ".length());
              localObject2 = new User();
              ((User)localObject2).setCustId((String)localObject1);
              ((User)localObject2).setBankId("1000");
              localObject3 = CustomerAdapter.getFilteredUsers((User)localObject2, null);
              ((Users)localObject3).setFilter("CUSTID=" + (String)localObject1);
              if (((Users)localObject3).size() != 1) {
                throw new BRException(60301, "Failed to process Bank Report file - Customer ID not unique/not found.");
              }
              localObject2 = (User)((Users)localObject3).get(0);
              k = ((User)localObject2).getIdValue();
              bool2 = false;
              bool2 = BankReportUtil.checkEntitlementForUser(k, paramBankReportDefinition.getReportType(), null);
              if (bool2)
              {
                localInteger = (Integer)localHashMap.get(new Integer(k));
                if (localInteger == null)
                {
                  localBankReport.setDirectoryID(k);
                  this.jdField_for.insertBankReport(localConnection, localBankReport, new HashMap());
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
          boolean bool1 = false;
          if (str3.equals("DP")) {
            bool1 = true;
          }
          ((ReportLineItem)localObject2).setForcePageBreak(bool1);
          this.jdField_for.insertLineItem(localConnection, (ReportLineItem)localObject2, new HashMap());
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
        this.jdField_for.releaseConnection(localConnection);
      }
      catch (Exception localException2) {}
    }
  }
  
  public IBankReportAdapter getBankReportAdapter()
  {
    return this.jdField_for;
  }
  
  public void setBankReportAdapter(IBankReportAdapter paramIBankReportAdapter)
  {
    this.jdField_for = paramIBankReportAdapter;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.bankreport.BusinessSummaryProcessor
 * JD-Core Version:    0.7.0.1
 */