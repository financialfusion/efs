package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterReport;
import com.ffusion.beans.register.RegisterReportTransactions;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Register;
import com.ffusion.csil.core.Reporting;
import com.ffusion.reporting.RptException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class RegisterReportUtil
{
  public static ReportResult getReportResult(SecureUser paramSecureUser, RegisterCategories paramRegisterCategories, Report paramReport, RegisterReport paramRegisterReport)
    throws CSILException
  {
    String str1 = "RegisterReportUtil.getReportResult";
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    Reporting.prepareForReport(paramSecureUser, localReportCriteria, new HashMap());
    Properties localProperties = localReportCriteria.getReportOptions();
    if (localProperties == null)
    {
      str2 = "The report criteria used in a call to getReportData did not contain a valid report options object.  This object is required for report retrieval.";
      DebugLog.log(str2);
      throw new CSILException(str1, 21007, str2);
    }
    String str2 = localProperties.getProperty("REPORTTYPE");
    if (str2 == null)
    {
      String str3 = "The report options contained within the Report Criteria used in a call to getReportData does not contain a report type.";
      DebugLog.log(str3);
      throw new CSILException(str1, 21007, str3);
    }
    return (ReportResult)Register.getReportData(paramSecureUser, localReportCriteria, new HashMap());
  }
  
  private static ReportResult jdMethod_do(Report paramReport, RegisterReport paramRegisterReport, Locale paramLocale)
    throws CSILException
  {
    String str1 = "RegisterReportUtil.getCashFlowReportResult";
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    Properties localProperties = localReportCriteria.getReportOptions();
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    try
    {
      localReportResult1.init(localReportCriteria);
      String str2 = localProperties.getProperty("TITLE");
      if (str2 == null) {
        str2 = "Cash Flow Report";
      }
      jdMethod_do(localReportResult1, str2, paramLocale);
      ReportResult localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2300, paramLocale), paramLocale);
      jdMethod_if(localReportResult2, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      Currency localCurrency1 = new Currency(new BigDecimal(0.0D), paramLocale);
      Currency localCurrency2 = new Currency(new BigDecimal(0.0D), paramLocale);
      paramRegisterReport.setFilter("TYPE=INCOME");
      paramRegisterReport.setFilterSortedBy("CATEGORY_NAME");
      Object localObject3;
      Object localObject4;
      Object localObject6;
      Object localObject7;
      Object localObject8;
      StringBuffer localStringBuffer;
      Object localObject5;
      for (int i = 0; i < paramRegisterReport.size(); i++)
      {
        localObject1 = (RegisterReportTransactions)paramRegisterReport.get(i);
        localObject2 = ((RegisterReportTransactions)localObject1).getParentName();
        String str3 = ((RegisterReportTransactions)localObject1).getName();
        localObject3 = ((RegisterReportTransactions)localObject1).getFullName();
        localObject4 = new Currency(new BigDecimal(0.0D), paramLocale);
        for (int k = 0; k < ((RegisterReportTransactions)localObject1).size(); k++)
        {
          localObject6 = (RegisterTransaction)((RegisterReportTransactions)localObject1).get(k);
          localObject7 = new ReportRow(paramLocale);
          ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
          ((ReportRow)localObject7).setDataItems(localReportDataItems);
          localObject8 = new ReportDataItem(paramLocale);
          localStringBuffer = new StringBuffer();
          if (localObject2 != null) {
            localStringBuffer.append((String)localObject2);
          }
          localStringBuffer.append(ReportConsts.getText(2305, paramLocale));
          if (str3 != null) {
            localStringBuffer.append(str3);
          }
          ((ReportDataItem)localObject8).setData(localStringBuffer.toString());
          localReportDataItems.add(localObject8);
          localObject8 = new ReportDataItem(paramLocale);
          ((ReportDataItem)localObject8).setData(((RegisterTransaction)localObject6).getAmountValue());
          localReportDataItems.add(localObject8);
          localObject8 = new ReportDataItem(paramLocale);
          localReportDataItems.add(localObject8);
          localReportResult2.addRow((ReportRow)localObject7);
          if (((RegisterTransaction)localObject6).getAmountValue() != null) {
            ((Currency)localObject4).addAmount(((RegisterTransaction)localObject6).getAmountValue());
          }
        }
        localReportResult2 = new ReportResult(paramLocale);
        jdMethod_if(localReportResult2, (String)localObject3 + " " + ReportConsts.getText(2302, paramLocale), paramLocale);
        localReportResult1.addSubReport(localReportResult2);
        localObject5 = new ReportRow(paramLocale);
        localObject6 = new ReportDataItems(paramLocale);
        ((ReportRow)localObject5).setDataItems((ReportDataItems)localObject6);
        localObject7 = new ReportDataItem(paramLocale);
        ((ReportDataItem)localObject7).setData(null);
        ((ReportDataItems)localObject6).add(localObject7);
        localObject7 = new ReportDataItem(paramLocale);
        ((ReportDataItem)localObject7).setData(localObject4);
        ((ReportDataItems)localObject6).add(localObject7);
        localReportResult2.addRow((ReportRow)localObject5);
        localCurrency1.addAmount((Currency)localObject4);
      }
      localReportResult2 = new ReportResult(paramLocale);
      jdMethod_if(localReportResult2, ReportConsts.getText(2303, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      ReportRow localReportRow = new ReportRow(paramLocale);
      Object localObject1 = new ReportDataItems(paramLocale);
      localReportRow.setDataItems((ReportDataItems)localObject1);
      Object localObject2 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject2).setData(null);
      ((ReportDataItems)localObject1).add(localObject2);
      localObject2 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject2).setData(localCurrency1);
      ((ReportDataItems)localObject1).add(localObject2);
      localReportResult2.addRow(localReportRow);
      paramRegisterReport.setFilter("TYPE=EXPENSE");
      paramRegisterReport.setFilterSortedBy("CATEGORY_NAME");
      localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2301, paramLocale), paramLocale);
      jdMethod_if(localReportResult2, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      for (int j = 0; j < paramRegisterReport.size(); j++)
      {
        localObject3 = (RegisterReportTransactions)paramRegisterReport.get(j);
        localObject4 = ((RegisterReportTransactions)localObject3).getParentName();
        localObject5 = ((RegisterReportTransactions)localObject3).getName();
        localObject6 = ((RegisterReportTransactions)localObject3).getFullName();
        localObject7 = new Currency(new BigDecimal(0.0D), paramLocale);
        for (int m = 0; m < ((RegisterReportTransactions)localObject3).size(); m++)
        {
          localObject8 = (RegisterTransaction)((RegisterReportTransactions)localObject3).get(m);
          localReportRow = new ReportRow(paramLocale);
          localObject1 = new ReportDataItems(paramLocale);
          localReportRow.setDataItems((ReportDataItems)localObject1);
          localObject2 = new ReportDataItem(paramLocale);
          localStringBuffer = new StringBuffer();
          if (localObject4 != null) {
            localStringBuffer.append((String)localObject4);
          }
          localStringBuffer.append(ReportConsts.getText(2305, paramLocale));
          if (localObject5 != null) {
            localStringBuffer.append((String)localObject5);
          }
          ((ReportDataItem)localObject2).setData(localStringBuffer.toString());
          ((ReportDataItems)localObject1).add(localObject2);
          localObject2 = new ReportDataItem(paramLocale);
          ((ReportDataItem)localObject2).setData(((RegisterTransaction)localObject8).getAmountValue());
          ((ReportDataItems)localObject1).add(localObject2);
          localObject2 = new ReportDataItem(paramLocale);
          ((ReportDataItems)localObject1).add(localObject2);
          localReportResult2.addRow(localReportRow);
          if (((RegisterTransaction)localObject8).getAmountValue() != null) {
            ((Currency)localObject7).addAmount(((RegisterTransaction)localObject8).getAmountValue());
          }
          localReportResult2.addRow(localReportRow);
          if (((RegisterTransaction)localObject8).getAmountValue() != null) {
            ((Currency)localObject7).addAmount(((RegisterTransaction)localObject8).getAmountValue());
          }
        }
        localReportResult2 = new ReportResult(paramLocale);
        jdMethod_if(localReportResult2, (String)localObject6 + " " + ReportConsts.getText(2302, paramLocale), paramLocale);
        localReportResult1.addSubReport(localReportResult2);
        localReportRow = new ReportRow(paramLocale);
        localObject1 = new ReportDataItems(paramLocale);
        localReportRow.setDataItems((ReportDataItems)localObject1);
        localObject2 = new ReportDataItem(paramLocale);
        ((ReportDataItem)localObject2).setData(null);
        ((ReportDataItems)localObject1).add(localObject2);
        localObject2 = new ReportDataItem(paramLocale);
        ((ReportDataItem)localObject2).setData(localObject7);
        ((ReportDataItems)localObject1).add(localObject2);
        localReportResult2.addRow(localReportRow);
        localCurrency2.addAmount((Currency)localObject7);
      }
      localReportResult2 = new ReportResult(paramLocale);
      jdMethod_if(localReportResult2, ReportConsts.getText(2304, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      localReportRow = new ReportRow(paramLocale);
      localObject1 = new ReportDataItems(paramLocale);
      localReportRow.setDataItems((ReportDataItems)localObject1);
      localObject2 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject2).setData(null);
      ((ReportDataItems)localObject1).add(localObject2);
      localObject2 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject2).setData(localCurrency1);
      ((ReportDataItems)localObject1).add(localObject2);
      localReportResult2.addRow(localReportRow);
    }
    catch (RptException localRptException1)
    {
      localReportResult1.setError(localRptException1);
      DebugLog.throwing(str1, localRptException1);
      throw new CSILException(str1, 21007, localRptException1);
    }
    finally
    {
      try
      {
        localReportResult1.fini();
      }
      catch (RptException localRptException2)
      {
        throw new CSILException(str1, 21007, localRptException2);
      }
    }
    return localReportResult1;
  }
  
  private static ReportResult jdMethod_if(Report paramReport, RegisterReport paramRegisterReport, Locale paramLocale)
    throws CSILException
  {
    String str1 = "RegisterReportUtil.getReconciliationReportResult";
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    Properties localProperties = localReportCriteria.getReportOptions();
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    try
    {
      localReportResult1.init(localReportCriteria);
      String str2 = localProperties.getProperty("TITLE");
      if (str2 == null) {
        str2 = "Reconciliation Status Report";
      }
      jdMethod_do(localReportResult1, str2, paramLocale);
      Currency localCurrency1 = new Currency(new BigDecimal(0.0D), paramLocale);
      Currency localCurrency2 = new Currency(new BigDecimal(0.0D), paramLocale);
      ReportResult localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2300, paramLocale), paramLocale);
      jdMethod_do(localReportResult2, true, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      paramRegisterReport.setFilter("TYPE=INCOME");
      paramRegisterReport.setFilterSortedBy("CATEGORY_NAME");
      jdMethod_if(paramRegisterReport, localReportResult2, localCurrency1, true, paramLocale);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2303, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      ReportRow localReportRow = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency1);
      localReportDataItems.add(localReportDataItem);
      localReportResult2.addRow(localReportRow);
      paramRegisterReport.setFilter("TYPE=EXPENSE");
      paramRegisterReport.setFilterSortedBy("CATEGORY_NAME");
      localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2301, paramLocale), paramLocale);
      jdMethod_do(localReportResult2, false, paramLocale);
      jdMethod_if(paramRegisterReport, localReportResult2, localCurrency2, false, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2304, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      localReportRow = new ReportRow(paramLocale);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency2);
      localReportDataItems.add(localReportDataItem);
      localReportResult2.addRow(localReportRow);
    }
    catch (RptException localRptException1)
    {
      localReportResult1.setError(localRptException1);
      DebugLog.throwing(str1, localRptException1);
      throw new CSILException(str1, 21007, localRptException1);
    }
    finally
    {
      try
      {
        localReportResult1.fini();
      }
      catch (RptException localRptException2)
      {
        throw new CSILException(str1, 21007, localRptException2);
      }
    }
    return localReportResult1;
  }
  
  private static void jdMethod_if(RegisterReport paramRegisterReport, ReportResult paramReportResult, Currency paramCurrency, boolean paramBoolean, Locale paramLocale)
    throws RptException
  {
    for (int i = 0; i < paramRegisterReport.size(); i++)
    {
      RegisterReportTransactions localRegisterReportTransactions = (RegisterReportTransactions)paramRegisterReport.get(i);
      String str1 = localRegisterReportTransactions.getParentName();
      String str2 = localRegisterReportTransactions.getName();
      String str3 = localRegisterReportTransactions.getFullName();
      for (int j = 0; j < localRegisterReportTransactions.size(); j++)
      {
        localObject1 = (RegisterTransaction)localRegisterReportTransactions.get(j);
        localObject2 = new ReportRow(paramLocale);
        localObject3 = new ReportDataItems(paramLocale);
        ((ReportRow)localObject2).setDataItems((ReportDataItems)localObject3);
        paramReportResult.addRow((ReportRow)localObject2);
        ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDateIssued());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getRegisterTypeName());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getReferenceNumber());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getPayeeName());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        StringBuffer localStringBuffer = new StringBuffer();
        if (str1 != null) {
          localStringBuffer.append(str1);
        }
        localStringBuffer.append(ReportConsts.getText(2305, paramLocale));
        if (str2 != null) {
          localStringBuffer.append(str2);
        }
        localReportDataItem.setData(localStringBuffer.toString());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getAmountValue());
        ((ReportDataItems)localObject3).add(localReportDataItem);
      }
      ReportResult localReportResult = new ReportResult(paramLocale);
      a(localReportResult, str3 + " " + ReportConsts.getText(2302, paramLocale), paramLocale);
      paramReportResult.addSubReport(localReportResult);
      Object localObject1 = new ReportRow(paramLocale);
      Object localObject2 = new ReportDataItems(paramLocale);
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      Object localObject3 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject3).setData(null);
      ((ReportDataItems)localObject2).add(localObject3);
      localObject3 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject3).setData(localRegisterReportTransactions.getAmountValue());
      ((ReportDataItems)localObject2).add(localObject3);
      localReportResult.addRow((ReportRow)localObject1);
      paramCurrency.addAmount(localRegisterReportTransactions.getAmountValue());
    }
  }
  
  private static ReportResult a(Report paramReport, RegisterReport paramRegisterReport, Locale paramLocale)
    throws CSILException
  {
    String str1 = "RegisterReportUtil.getTaxStatusReportResult";
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    Properties localProperties = localReportCriteria.getReportOptions();
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), paramLocale);
    Currency localCurrency2 = new Currency(new BigDecimal(0.0D), paramLocale);
    try
    {
      localReportResult1.init(localReportCriteria);
      String str2 = localProperties.getProperty("TITLE");
      if (str2 == null) {
        str2 = "Tax Status Report";
      }
      jdMethod_do(localReportResult1, str2, paramLocale);
      ReportResult localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2300, paramLocale), paramLocale);
      jdMethod_if(localReportResult2, true, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      paramRegisterReport.setFilter("TYPE=INCOME,TAX=true,and");
      paramRegisterReport.setFilterSortedBy("CATEGORY_NAME");
      a(paramRegisterReport, localReportResult2, localCurrency1, true, paramLocale);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2303, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      ReportRow localReportRow = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult2.addRow(localReportRow);
      ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency1);
      localReportDataItems.add(localReportDataItem);
      localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2301, paramLocale), paramLocale);
      jdMethod_if(localReportResult2, true, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      paramRegisterReport.setFilter("TYPE=EXPENSE,TAX=true,and");
      paramRegisterReport.setFilterSortedBy("CATEGORY_NAME");
      a(paramRegisterReport, localReportResult2, localCurrency2, false, paramLocale);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2304, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      localReportRow = new ReportRow(paramLocale);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult2.addRow(localReportRow);
      localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency2);
      localReportDataItems.add(localReportDataItem);
    }
    catch (RptException localRptException1)
    {
      localReportResult1.setError(localRptException1);
      DebugLog.throwing(str1, localRptException1);
      throw new CSILException(str1, 21007, localRptException1);
    }
    finally
    {
      try
      {
        localReportResult1.fini();
      }
      catch (RptException localRptException2)
      {
        throw new CSILException(str1, 21007, localRptException2);
      }
    }
    return localReportResult1;
  }
  
  private static ReportResult jdMethod_if(Report paramReport, RegisterReport paramRegisterReport, RegisterCategories paramRegisterCategories, Locale paramLocale)
    throws CSILException
  {
    String str1 = "RegisterReportUtil.getTransactionTypeReportResult";
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    Properties localProperties = localReportCriteria.getReportOptions();
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), paramLocale);
    Currency localCurrency2 = new Currency(new BigDecimal(0.0D), paramLocale);
    try
    {
      localReportResult1.init(localReportCriteria);
      String str2 = localProperties.getProperty("TITLE");
      if (str2 == null) {
        str2 = "Payee Total Report";
      }
      jdMethod_do(localReportResult1, str2, paramLocale);
      ReportResult localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2300, paramLocale), paramLocale);
      jdMethod_for(localReportResult2, true, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      paramRegisterReport.setFilter("TYPE=INCOME");
      paramRegisterReport.setFilterSortedBy("REGISTER_TYPE");
      a(paramRegisterReport, localReportResult2, localCurrency1, true, paramRegisterCategories, paramLocale);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2303, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      ReportRow localReportRow = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult2.addRow(localReportRow);
      ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency1);
      localReportDataItems.add(localReportDataItem);
      localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2301, paramLocale), paramLocale);
      jdMethod_for(localReportResult2, false, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      paramRegisterReport.setFilter("TYPE=EXPENSE");
      paramRegisterReport.setFilterSortedBy("REGISTER_TYPE");
      a(paramRegisterReport, localReportResult2, localCurrency2, false, paramRegisterCategories, paramLocale);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2304, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      localReportRow = new ReportRow(paramLocale);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult2.addRow(localReportRow);
      localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency2);
      localReportDataItems.add(localReportDataItem);
    }
    catch (RptException localRptException1)
    {
      localReportResult1.setError(localRptException1);
      DebugLog.throwing(str1, localRptException1);
      throw new CSILException(str1, 21007, localRptException1);
    }
    finally
    {
      try
      {
        localReportResult1.fini();
      }
      catch (RptException localRptException2)
      {
        throw new CSILException(str1, 21007, localRptException2);
      }
    }
    return localReportResult1;
  }
  
  private static ReportResult jdMethod_do(Report paramReport, RegisterReport paramRegisterReport, RegisterCategories paramRegisterCategories, Locale paramLocale)
    throws CSILException
  {
    String str1 = "RegisterReportUtil.getTransactionTypeReportResult";
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    Properties localProperties = localReportCriteria.getReportOptions();
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), paramLocale);
    Currency localCurrency2 = new Currency(new BigDecimal(0.0D), paramLocale);
    try
    {
      localReportResult1.init(localReportCriteria);
      String str2 = localProperties.getProperty("TITLE");
      if (str2 == null) {
        str2 = "Payee Total Report";
      }
      jdMethod_do(localReportResult1, str2, paramLocale);
      ReportResult localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2300, paramLocale), paramLocale);
      a(localReportResult2, true, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      paramRegisterReport.setFilter("TYPE=INCOME");
      paramRegisterReport.setFilterSortedBy("CATEGORY_NAME");
      jdMethod_if(paramRegisterReport, localReportResult2, localCurrency1, true, paramRegisterCategories, paramLocale);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2303, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      ReportRow localReportRow = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult2.addRow(localReportRow);
      ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency1);
      localReportDataItems.add(localReportDataItem);
      localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult2, ReportConsts.getText(2301, paramLocale), paramLocale);
      a(localReportResult2, false, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      paramRegisterReport.setFilter("TYPE=EXPENSE");
      paramRegisterReport.setFilterSortedBy("CATEGORY_NAME");
      jdMethod_if(paramRegisterReport, localReportResult2, localCurrency2, false, paramRegisterCategories, paramLocale);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2304, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      localReportRow = new ReportRow(paramLocale);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult2.addRow(localReportRow);
      localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency2);
      localReportDataItems.add(localReportDataItem);
    }
    catch (RptException localRptException1)
    {
      localReportResult1.setError(localRptException1);
      DebugLog.throwing(str1, localRptException1);
      throw new CSILException(str1, 21007, localRptException1);
    }
    finally
    {
      try
      {
        localReportResult1.fini();
      }
      catch (RptException localRptException2)
      {
        throw new CSILException(str1, 21007, localRptException2);
      }
    }
    return localReportResult1;
  }
  
  private static void jdMethod_if(ReportResult paramReportResult, boolean paramBoolean, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(7);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    String str = ReportConsts.getColumnName(2251, paramLocale) + ReportConsts.getText(2305, paramLocale) + ReportConsts.getColumnName(2252, paramLocale);
    localArrayList.add(str);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(15);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2256, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    if (paramBoolean) {
      localArrayList.add(ReportConsts.getColumnName(2258, paramLocale));
    } else {
      localArrayList.add(ReportConsts.getColumnName(2257, paramLocale));
    }
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2260, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2259, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    if (paramBoolean) {
      localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
    } else {
      localArrayList.add(ReportConsts.getColumnName(2261, paramLocale));
    }
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(15);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(RegisterReport paramRegisterReport, ReportResult paramReportResult, Currency paramCurrency, boolean paramBoolean, Locale paramLocale)
    throws RptException
  {
    for (int i = 0; i < paramRegisterReport.size(); i++)
    {
      RegisterReportTransactions localRegisterReportTransactions = (RegisterReportTransactions)paramRegisterReport.get(i);
      String str1 = localRegisterReportTransactions.getParentName();
      String str2 = localRegisterReportTransactions.getName();
      String str3 = localRegisterReportTransactions.getFullName();
      Currency localCurrency = new Currency(new BigDecimal(0.0D), paramLocale);
      for (int j = 0; j < localRegisterReportTransactions.size(); j++)
      {
        localObject1 = (RegisterTransaction)localRegisterReportTransactions.get(j);
        localObject2 = new ReportRow(paramLocale);
        localObject3 = new ReportDataItems(paramLocale);
        ((ReportRow)localObject2).setDataItems((ReportDataItems)localObject3);
        paramReportResult.addRow((ReportRow)localObject2);
        ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
        StringBuffer localStringBuffer = new StringBuffer();
        if (str1 != null) {
          localStringBuffer.append(str1);
        }
        localStringBuffer.append(ReportConsts.getText(2305, paramLocale));
        if (str2 != null) {
          localStringBuffer.append(str2);
        }
        localReportDataItem.setData(localStringBuffer.toString());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDateIssued());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDate());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getPayeeName());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getRegisterTypeName());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getReferenceNumber());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getAmountValue());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        if (((RegisterTransaction)localObject1).getAmountValue() != null) {
          localCurrency.addAmount(((RegisterTransaction)localObject1).getAmountValue());
        }
      }
      ReportResult localReportResult = new ReportResult(paramLocale);
      a(localReportResult, str3 + " " + ReportConsts.getText(2302, paramLocale), paramLocale);
      paramReportResult.addSubReport(localReportResult);
      Object localObject1 = new ReportRow(paramLocale);
      Object localObject2 = new ReportDataItems(paramLocale);
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      Object localObject3 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject3).setData(null);
      ((ReportDataItems)localObject2).add(localObject3);
      localObject3 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject3).setData(localCurrency);
      ((ReportDataItems)localObject2).add(localObject3);
      localReportResult.addRow((ReportRow)localObject1);
      paramCurrency.addAmount(localCurrency);
    }
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, boolean paramBoolean, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(8);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2260, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(13);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2259, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2256, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    if (paramBoolean) {
      localArrayList.add(ReportConsts.getColumnName(2258, paramLocale));
    } else {
      localArrayList.add(ReportConsts.getColumnName(2257, paramLocale));
    }
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    String str = ReportConsts.getColumnName(2251, paramLocale) + ReportConsts.getText(2305, paramLocale) + ReportConsts.getColumnName(2252, paramLocale);
    localArrayList.add(str);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2262, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    if (paramBoolean) {
      localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
    } else {
      localArrayList.add(ReportConsts.getColumnName(2261, paramLocale));
    }
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(13);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(ReportResult paramReportResult, boolean paramBoolean, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(8);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    String str = ReportConsts.getColumnName(2251, paramLocale) + ReportConsts.getText(2305, paramLocale) + ReportConsts.getColumnName(2252, paramLocale);
    localArrayList.add(str);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2260, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(13);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2259, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2256, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    if (paramBoolean) {
      localArrayList.add(ReportConsts.getColumnName(2258, paramLocale));
    } else {
      localArrayList.add(ReportConsts.getColumnName(2257, paramLocale));
    }
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2262, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    if (paramBoolean) {
      localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
    } else {
      localArrayList.add(ReportConsts.getColumnName(2261, paramLocale));
    }
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(13);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(RegisterReport paramRegisterReport, ReportResult paramReportResult, Currency paramCurrency, boolean paramBoolean, RegisterCategories paramRegisterCategories, Locale paramLocale)
    throws RptException
  {
    for (int i = 0; i < paramRegisterReport.size(); i++)
    {
      RegisterReportTransactions localRegisterReportTransactions = (RegisterReportTransactions)paramRegisterReport.get(i);
      String str1 = localRegisterReportTransactions.getParentName();
      String str2 = localRegisterReportTransactions.getName();
      String str3 = localRegisterReportTransactions.getFullName();
      for (int j = 0; j < localRegisterReportTransactions.size(); j++)
      {
        localObject1 = (RegisterTransaction)localRegisterReportTransactions.get(j);
        paramRegisterCategories.setCurrent(((RegisterTransaction)localObject1).getRegisterCategoryId());
        localObject2 = new ReportRow(paramLocale);
        ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
        ((ReportRow)localObject2).setDataItems(localReportDataItems);
        paramReportResult.addRow((ReportRow)localObject2);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getRegisterTypeName());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getReferenceNumber());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDateIssued());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDate());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getPayeeName());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        StringBuffer localStringBuffer = new StringBuffer();
        if (str1 != null) {
          localStringBuffer.append(str1);
        }
        localStringBuffer.append(ReportConsts.getText(2305, paramLocale));
        if (str2 != null) {
          localStringBuffer.append(str2);
        }
        localReportDataItem.setData(localStringBuffer.toString());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(paramRegisterCategories.getTaxRelatedValue() ? ReportConsts.getText(2308, paramLocale) : ReportConsts.getText(2309, paramLocale));
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getAmountValue());
        localReportDataItems.add(localReportDataItem);
      }
      ReportResult localReportResult = new ReportResult(paramLocale);
      a(localReportResult, str3 + " " + ReportConsts.getText(2302, paramLocale), paramLocale);
      paramReportResult.addSubReport(localReportResult);
      Object localObject1 = new ReportRow(paramLocale);
      Object localObject2 = new ReportDataItems(paramLocale);
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(null);
      ((ReportDataItems)localObject2).add(localReportDataItem);
      localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localRegisterReportTransactions.getAmountValue());
      ((ReportDataItems)localObject2).add(localReportDataItem);
      localReportResult.addRow((ReportRow)localObject1);
      paramCurrency.addAmount(localRegisterReportTransactions.getAmountValue());
    }
  }
  
  private static void jdMethod_if(RegisterReport paramRegisterReport, ReportResult paramReportResult, Currency paramCurrency, boolean paramBoolean, RegisterCategories paramRegisterCategories, Locale paramLocale)
    throws RptException
  {
    for (int i = 0; i < paramRegisterReport.size(); i++)
    {
      RegisterReportTransactions localRegisterReportTransactions = (RegisterReportTransactions)paramRegisterReport.get(i);
      String str1 = localRegisterReportTransactions.getParentName();
      String str2 = localRegisterReportTransactions.getName();
      String str3 = localRegisterReportTransactions.getFullName();
      for (int j = 0; j < localRegisterReportTransactions.size(); j++)
      {
        localObject1 = (RegisterTransaction)localRegisterReportTransactions.get(j);
        paramRegisterCategories.setCurrent(((RegisterTransaction)localObject1).getRegisterCategoryId());
        localObject2 = new ReportRow(paramLocale);
        ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
        ((ReportRow)localObject2).setDataItems(localReportDataItems);
        paramReportResult.addRow((ReportRow)localObject2);
        localReportDataItem = new ReportDataItem(paramLocale);
        StringBuffer localStringBuffer = new StringBuffer();
        if (str1 != null) {
          localStringBuffer.append(str1);
        }
        localStringBuffer.append(ReportConsts.getText(2305, paramLocale));
        if (str2 != null) {
          localStringBuffer.append(str2);
        }
        localReportDataItem.setData(localStringBuffer.toString());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getRegisterTypeName());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getReferenceNumber());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDateIssued());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDate());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getPayeeName());
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(paramRegisterCategories.getTaxRelatedValue() ? ReportConsts.getText(2308, paramLocale) : ReportConsts.getText(2309, paramLocale));
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getAmountValue());
        localReportDataItems.add(localReportDataItem);
      }
      ReportResult localReportResult = new ReportResult(paramLocale);
      a(localReportResult, str3 + " " + ReportConsts.getText(2302, paramLocale), paramLocale);
      paramReportResult.addSubReport(localReportResult);
      Object localObject1 = new ReportRow(paramLocale);
      Object localObject2 = new ReportDataItems(paramLocale);
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(null);
      ((ReportDataItems)localObject2).add(localReportDataItem);
      localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localRegisterReportTransactions.getAmountValue());
      ((ReportDataItems)localObject2).add(localReportDataItem);
      localReportResult.addRow((ReportRow)localObject1);
      paramCurrency.addAmount(localRegisterReportTransactions.getAmountValue());
    }
  }
  
  private static void a(RegisterReport paramRegisterReport, ReportResult paramReportResult, Currency paramCurrency, RegisterCategories paramRegisterCategories, Locale paramLocale)
    throws RptException
  {
    for (int i = 0; i < paramRegisterReport.size(); i++)
    {
      RegisterReportTransactions localRegisterReportTransactions = (RegisterReportTransactions)paramRegisterReport.get(i);
      String str1 = localRegisterReportTransactions.getParentName();
      String str2 = localRegisterReportTransactions.getName();
      String str3 = localRegisterReportTransactions.getFullName();
      for (int j = 0; j < localRegisterReportTransactions.size(); j++)
      {
        localObject1 = (RegisterTransaction)localRegisterReportTransactions.get(j);
        paramRegisterCategories.setCurrent(((RegisterTransaction)localObject1).getRegisterCategoryId());
        localObject2 = new ReportRow(paramLocale);
        localObject3 = new ReportDataItems(paramLocale);
        ((ReportRow)localObject2).setDataItems((ReportDataItems)localObject3);
        paramReportResult.addRow((ReportRow)localObject2);
        ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
        StringBuffer localStringBuffer = new StringBuffer();
        if (str1 != null) {
          localStringBuffer.append(str1);
        }
        localStringBuffer.append(ReportConsts.getText(2305, paramLocale));
        if (str2 != null) {
          localStringBuffer.append(str2);
        }
        localReportDataItem.setData(localStringBuffer.toString());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDateIssued());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getDate());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getRegisterTypeName());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getReferenceNumber());
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(paramRegisterCategories.getTaxRelatedValue() ? ReportConsts.getText(2308, paramLocale) : ReportConsts.getText(2309, paramLocale));
        ((ReportDataItems)localObject3).add(localReportDataItem);
        localReportDataItem = new ReportDataItem(paramLocale);
        localReportDataItem.setData(((RegisterTransaction)localObject1).getAmountValue());
        ((ReportDataItems)localObject3).add(localReportDataItem);
      }
      ReportResult localReportResult = new ReportResult(paramLocale);
      a(localReportResult, str3 + " " + ReportConsts.getText(2302, paramLocale), paramLocale);
      paramReportResult.addSubReport(localReportResult);
      Object localObject1 = new ReportRow(paramLocale);
      Object localObject2 = new ReportDataItems(paramLocale);
      ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
      Object localObject3 = new ReportDataItem(paramLocale);
      ((ReportDataItem)localObject3).setData(localRegisterReportTransactions.getAmountValue());
      ((ReportDataItems)localObject2).add(localObject3);
      localReportResult.addRow((ReportRow)localObject1);
      paramCurrency.addAmount(localRegisterReportTransactions.getAmountValue());
    }
  }
  
  private static ReportResult a(Report paramReport, RegisterReport paramRegisterReport, RegisterCategories paramRegisterCategories, Locale paramLocale)
    throws CSILException
  {
    String str1 = "RegisterReportUtil.getPayeeReportResult";
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    Properties localProperties = localReportCriteria.getReportOptions();
    ReportResult localReportResult1 = new ReportResult(paramLocale);
    Currency localCurrency = new Currency(new BigDecimal(0.0D), paramLocale);
    try
    {
      localReportResult1.init(localReportCriteria);
      String str2 = localProperties.getProperty("TITLE");
      if (str2 == null) {
        str2 = "Payee Total Report";
      }
      jdMethod_do(localReportResult1, str2, paramLocale);
      ReportResult localReportResult2 = new ReportResult(paramLocale);
      jdMethod_do(localReportResult1, ReportConsts.getText(2307, paramLocale), paramLocale);
      a(localReportResult2, paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      paramRegisterReport.setFilterSortedBy("PAYEE_NAME");
      a(paramRegisterReport, localReportResult2, localCurrency, paramRegisterCategories, paramLocale);
      localReportResult2 = new ReportResult(paramLocale);
      a(localReportResult2, ReportConsts.getText(2302, paramLocale), paramLocale);
      localReportResult1.addSubReport(localReportResult2);
      ReportRow localReportRow = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult2.addRow(localReportRow);
      ReportDataItem localReportDataItem = new ReportDataItem(paramLocale);
      localReportDataItem.setData(localCurrency);
      localReportDataItems.add(localReportDataItem);
    }
    catch (RptException localRptException1)
    {
      localReportResult1.setError(localRptException1);
      DebugLog.throwing(str1, localRptException1);
      throw new CSILException(str1, 21007, localRptException1);
    }
    finally
    {
      try
      {
        localReportResult1.fini();
      }
      catch (RptException localRptException2)
      {
        throw new CSILException(str1, 21007, localRptException2);
      }
    }
    return localReportResult1;
  }
  
  private static void jdMethod_do(ReportResult paramReportResult, String paramString, Locale paramLocale)
    throws RptException
  {
    ReportHeading localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel(paramString);
    localReportHeading.setJustification("LEFT");
    paramReportResult.setHeading(localReportHeading);
  }
  
  private static void jdMethod_if(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(3);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    String str = ReportConsts.getColumnName(2251, paramLocale) + ReportConsts.getText(2305, paramLocale) + ReportConsts.getColumnName(2252, paramLocale);
    localArrayList.add(str);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(50);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(25);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getText(2302, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(25);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void jdMethod_if(ReportResult paramReportResult, String paramString, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(1);
    localReportDataDimensions.setNumColumns(2);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add("");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(75);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(paramString);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(25);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void jdMethod_do(ReportResult paramReportResult, boolean paramBoolean, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(6);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(16);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2260, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(17);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2259, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(16);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    if (paramBoolean) {
      localArrayList.add(ReportConsts.getColumnName(2258, paramLocale));
    } else {
      localArrayList.add(ReportConsts.getColumnName(2257, paramLocale));
    }
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(18);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    String str = ReportConsts.getColumnName(2251, paramLocale) + ReportConsts.getText(2305, paramLocale) + ReportConsts.getColumnName(2252, paramLocale);
    localArrayList.add(str);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(17);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(16);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(ReportResult paramReportResult, String paramString, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(1);
    localReportDataDimensions.setNumColumns(1);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(paramString);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(100);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(7);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    String str = ReportConsts.getColumnName(2251, paramLocale) + ReportConsts.getText(2305, paramLocale) + ReportConsts.getColumnName(2252, paramLocale);
    localArrayList.add(str);
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(15);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2256, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2260, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2259, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2262, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(14);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(15);
    paramReportResult.addColumn(localReportColumn);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.RegisterReportUtil
 * JD-Core Version:    0.7.0.1
 */