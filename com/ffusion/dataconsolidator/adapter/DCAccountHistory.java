package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.util.MapUtil;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

class DCAccountHistory
{
  private static final String jdField_long = "INSERT INTO DC_AccountHistory( DCAccountID, DataDate, DataSource, OpeningLedger, AvgOpenLedgerMTD, AvgOpenLedgerYTD, ClosingLedger, AvgCloseLedgerMTD, AvgMonth, AggBalanceAdjust, AvCloseLedgYTDPrvM, AvgCloseLedgerYTD, CurrentLedger, ACHNetPosition, OpAvaiSamDayACHDTC, OpeningAvailable, AvgOpenAvailMTD, AvgOpenAvailYTD, AvgAvailPrevMonth, DisbOpenAvailBal, ClosingAvail, AvgCloseAvailMTD, AvCloseAvailPreM,  AvCloseAvailYTDPrM, AvCloseAvailYTD, LoanBalance, TotalInvestPosn, CurrAvailCRSSupr, CurrentAvail, AvgCurrAvailMTD, AvgCurrAvailYTD, TotalFloat, TargetBalance, AdjBalance, AdjBalanceMTD, AdjBalanceYTD, ZeroDayFloat, OneDayFloat, FloatAdj, TwoMoreDayFloat, ThreeMoreDayFloat, AdjToBalances, AvgAdjToBalMTD, AvgAdjToBalYTD, FourDayFloat, FiveDayFloat, SixDayFloat, AvgOneDayFloatMTD, AvgOneDayFloatYTD, AvgTwoDayFloatMTD, AvgTwoDayFloatYTD, TransferCalc, TargBalDeficiency, TotalFundingReq, TotalChecksPaid, NUMCHECKSPAID, GrandTotCredMinDeb, NUMCREDITLESSDEBIT, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
  private static final String jdField_for = "UPDATE DC_AccountHistory SET DCAccountID=?, DataDate=?, DataSource=?, OpeningLedger=?, AvgOpenLedgerMTD=?, AvgOpenLedgerYTD=?, ClosingLedger=?, AvgCloseLedgerMTD=?, AvgMonth=?, AggBalanceAdjust=?, AvCloseLedgYTDPrvM=?, AvgCloseLedgerYTD=?, CurrentLedger=?, ACHNetPosition=?, OpAvaiSamDayACHDTC=?, OpeningAvailable=?, AvgOpenAvailMTD=?, AvgOpenAvailYTD=?, AvgAvailPrevMonth=?, DisbOpenAvailBal=?, ClosingAvail=?, AvgCloseAvailMTD=?, AvCloseAvailPreM=?,  AvCloseAvailYTDPrM=?, AvCloseAvailYTD=?, LoanBalance=?, TotalInvestPosn=?, CurrAvailCRSSupr=?, CurrentAvail=?, AvgCurrAvailMTD=?, AvgCurrAvailYTD=?, TotalFloat=?, TargetBalance=?, AdjBalance=?, AdjBalanceMTD=?, AdjBalanceYTD=?, ZeroDayFloat=?, OneDayFloat=?, FloatAdj=?, TwoMoreDayFloat=?, ThreeMoreDayFloat=?, AdjToBalances=?, AvgAdjToBalMTD=?, AvgAdjToBalYTD=?, FourDayFloat=?, FiveDayFloat=?, SixDayFloat=?, AvgOneDayFloatMTD=?, AvgOneDayFloatYTD=?, AvgTwoDayFloatMTD=?, AvgTwoDayFloatYTD=?, TransferCalc=?, TargBalDeficiency=?, TotalFundingReq=?, TotalChecksPaid=?, NUMCHECKSPAID=?, GrandTotCredMinDeb=?, NUMCREDITLESSDEBIT=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static final String jdField_case = "UPDATE DC_AccountHistory SET DCAccountID=?, DataDate=?, OpeningLedger=?, AvgOpenLedgerMTD=?, AvgOpenLedgerYTD=?, ClosingLedger=?, AvgCloseLedgerMTD=?, AvgMonth=?, AggBalanceAdjust=?, AvCloseLedgYTDPrvM=?, AvgCloseLedgerYTD=?, CurrentLedger=?, ACHNetPosition=?, OpAvaiSamDayACHDTC=?, OpeningAvailable=?, AvgOpenAvailMTD=?, AvgOpenAvailYTD=?, AvgAvailPrevMonth=?, DisbOpenAvailBal=?, ClosingAvail=?, AvgCloseAvailMTD=?, AvCloseAvailPreM=?,  AvCloseAvailYTDPrM=?, AvCloseAvailYTD=?, LoanBalance=?, TotalInvestPosn=?, CurrAvailCRSSupr=?, CurrentAvail=?, AvgCurrAvailMTD=?, AvgCurrAvailYTD=?, TotalFloat=?, TargetBalance=?, AdjBalance=?, AdjBalanceMTD=?, AdjBalanceYTD=?, ZeroDayFloat=?, OneDayFloat=?, FloatAdj=?, TwoMoreDayFloat=?, ThreeMoreDayFloat=?, AdjToBalances=?, AvgAdjToBalMTD=?, AvgAdjToBalYTD=?, FourDayFloat=?, FiveDayFloat=?, SixDayFloat=?, AvgOneDayFloatMTD=?, AvgOneDayFloatYTD=?, AvgTwoDayFloatMTD=?, AvgTwoDayFloatYTD=?, TransferCalc=?, TargBalDeficiency=?, TotalFundingReq=?, TotalChecksPaid=?, NUMCHECKSPAID=?, GrandTotCredMinDeb=?, NUMCREDITLESSDEBIT=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static String b = " SELECT b.DataDate, b.OpeningLedger, b.AvgOpenLedgerMTD, b.AvgOpenLedgerYTD, b.ClosingLedger, b.AvgCloseLedgerMTD, b.AvgMonth, b.AggBalanceAdjust, b.AvCloseLedgYTDPrvM, b.AvgCloseLedgerYTD, b.CurrentLedger, b.ACHNetPosition, b.OpAvaiSamDayACHDTC, b.OpeningAvailable, b.AvgOpenAvailMTD, b.AvgOpenAvailYTD, b.AvgAvailPrevMonth, b.DisbOpenAvailBal, b.ClosingAvail, b.AvgCloseAvailMTD, b.AvCloseAvailPreM, b. AvCloseAvailYTDPrM, b.AvCloseAvailYTD, b.LoanBalance, b.TotalInvestPosn, b.CurrAvailCRSSupr, b.CurrentAvail, b.AvgCurrAvailMTD, b.AvgCurrAvailYTD, b.TotalFloat, b.TargetBalance, b.AdjBalance, b.AdjBalanceMTD, b.AdjBalanceYTD, b.ZeroDayFloat, b.OneDayFloat, b.FloatAdj, b.TwoMoreDayFloat, b.ThreeMoreDayFloat, b.AdjToBalances, b.AvgAdjToBalMTD, b.AvgAdjToBalYTD, b.FourDayFloat, b.FiveDayFloat, b.SixDayFloat, b.AvgOneDayFloatMTD, b.AvgOneDayFloatYTD, b.AvgTwoDayFloatMTD, b.AvgTwoDayFloatYTD, b.TransferCalc, b.TargBalDeficiency, b.TotalFundingReq, b.TotalChecksPaid, b.NUMCHECKSPAID, b.GrandTotCredMinDeb, b.NUMCREDITLESSDEBIT, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_void = " SELECT b.DataDate, b.OpeningLedger, b.AvgOpenLedgerMTD, b.AvgOpenLedgerYTD, b.ClosingLedger, b.AvgCloseLedgerMTD, b.AvgMonth, b.AggBalanceAdjust, b.AvCloseLedgYTDPrvM, b.AvgCloseLedgerYTD, b.CurrentLedger, b.ACHNetPosition, b.OpAvaiSamDayACHDTC, b.OpeningAvailable, b.AvgOpenAvailMTD, b.AvgOpenAvailYTD, b.AvgAvailPrevMonth, b.DisbOpenAvailBal, b.ClosingAvail, b.AvgCloseAvailMTD, b.AvCloseAvailPreM, b. AvCloseAvailYTDPrM, b.AvCloseAvailYTD, b.LoanBalance, b.TotalInvestPosn, b.CurrAvailCRSSupr, b.CurrentAvail, b.AvgCurrAvailMTD, b.AvgCurrAvailYTD, b.TotalFloat, b.TargetBalance, b.AdjBalance, b.AdjBalanceMTD, b.AdjBalanceYTD, b.ZeroDayFloat, b.OneDayFloat, b.FloatAdj, b.TwoMoreDayFloat, b.ThreeMoreDayFloat, b.AdjToBalances, b.AvgAdjToBalMTD, b.AvgAdjToBalYTD, b.FourDayFloat, b.FiveDayFloat, b.SixDayFloat, b.AvgOneDayFloatMTD, b.AvgOneDayFloatYTD, b.AvgTwoDayFloatMTD, b.AvgTwoDayFloatYTD, b.TransferCalc, b.TargBalDeficiency, b.TotalFundingReq, b.TotalChecksPaid, b.NUMCHECKSPAID, b.GrandTotCredMinDeb, b.NUMCREDITLESSDEBIT, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ";
  private static String jdField_goto = " SELECT b.DataDate, b.OpeningLedger, b.AvgOpenLedgerMTD, b.AvgOpenLedgerYTD, b.ClosingLedger, b.AvgCloseLedgerMTD, b.AvgMonth, b.AggBalanceAdjust, b.AvCloseLedgYTDPrvM, b.AvgCloseLedgerYTD, b.CurrentLedger, b.ACHNetPosition, b.OpAvaiSamDayACHDTC, b.OpeningAvailable, b.AvgOpenAvailMTD, b.AvgOpenAvailYTD, b.AvgAvailPrevMonth, b.DisbOpenAvailBal, b.ClosingAvail, b.AvgCloseAvailMTD, b.AvCloseAvailPreM, b. AvCloseAvailYTDPrM, b.AvCloseAvailYTD, b.LoanBalance, b.TotalInvestPosn, b.CurrAvailCRSSupr, b.CurrentAvail, b.AvgCurrAvailMTD, b.AvgCurrAvailYTD, b.TotalFloat, b.TargetBalance, b.AdjBalance, b.AdjBalanceMTD, b.AdjBalanceYTD, b.ZeroDayFloat, b.OneDayFloat, b.FloatAdj, b.TwoMoreDayFloat, b.ThreeMoreDayFloat, b.AdjToBalances, b.AvgAdjToBalMTD, b.AvgAdjToBalYTD, b.FourDayFloat, b.FiveDayFloat, b.SixDayFloat, b.AvgOneDayFloatMTD, b.AvgOneDayFloatYTD, b.AvgTwoDayFloatMTD, b.AvgTwoDayFloatYTD, b.TransferCalc, b.TargBalDeficiency, b.TotalFundingReq, b.TotalChecksPaid, b.NUMCHECKSPAID, b.GrandTotCredMinDeb, b.NUMCREDITLESSDEBIT, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_else = " SELECT b.DataDate, b.OpeningLedger, b.AvgOpenLedgerMTD, b.AvgOpenLedgerYTD, b.ClosingLedger, b.AvgCloseLedgerMTD, b.AvgMonth, b.AggBalanceAdjust, b.AvCloseLedgYTDPrvM, b.AvgCloseLedgerYTD, b.CurrentLedger, b.ACHNetPosition, b.OpAvaiSamDayACHDTC, b.OpeningAvailable, b.AvgOpenAvailMTD, b.AvgOpenAvailYTD, b.AvgAvailPrevMonth, b.DisbOpenAvailBal, b.ClosingAvail, b.AvgCloseAvailMTD, b.AvCloseAvailPreM, b. AvCloseAvailYTDPrM, b.AvCloseAvailYTD, b.LoanBalance, b.TotalInvestPosn, b.CurrAvailCRSSupr, b.CurrentAvail, b.AvgCurrAvailMTD, b.AvgCurrAvailYTD, b.TotalFloat, b.TargetBalance, b.AdjBalance, b.AdjBalanceMTD, b.AdjBalanceYTD, b.ZeroDayFloat, b.OneDayFloat, b.FloatAdj, b.TwoMoreDayFloat, b.ThreeMoreDayFloat, b.AdjToBalances, b.AvgAdjToBalMTD, b.AvgAdjToBalYTD, b.FourDayFloat, b.FiveDayFloat, b.SixDayFloat, b.AvgOneDayFloatMTD, b.AvgOneDayFloatYTD, b.AvgTwoDayFloatMTD, b.AvgTwoDayFloatYTD, b.TransferCalc, b.TargBalDeficiency, b.TotalFundingReq, b.TotalChecksPaid, b.NUMCHECKSPAID, b.GrandTotCredMinDeb, b.NUMCREDITLESSDEBIT, b.ExtendABeanXMLID, a.CurrencyCode FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  private static String jdField_char = " SELECT b.DataDate, b.OpeningLedger, b.AvgOpenLedgerMTD, b.AvgOpenLedgerYTD, b.ClosingLedger, b.AvgCloseLedgerMTD, b.AvgMonth, b.AggBalanceAdjust, b.AvCloseLedgYTDPrvM, b.AvgCloseLedgerYTD, b.CurrentLedger, b.ACHNetPosition, b.OpAvaiSamDayACHDTC, b.OpeningAvailable, b.AvgOpenAvailMTD, b.AvgOpenAvailYTD, b.AvgAvailPrevMonth, b.DisbOpenAvailBal, b.ClosingAvail, b.AvgCloseAvailMTD, b.AvCloseAvailPreM, b. AvCloseAvailYTDPrM, b.AvCloseAvailYTD, b.LoanBalance, b.TotalInvestPosn, b.CurrAvailCRSSupr, b.CurrentAvail, b.AvgCurrAvailMTD, b.AvgCurrAvailYTD, b.TotalFloat, b.TargetBalance, b.AdjBalance, b.AdjBalanceMTD, b.AdjBalanceYTD, b.ZeroDayFloat, b.OneDayFloat, b.FloatAdj, b.TwoMoreDayFloat, b.ThreeMoreDayFloat, b.AdjToBalances, b.AvgAdjToBalMTD, b.AvgAdjToBalYTD, b.FourDayFloat, b.FiveDayFloat, b.SixDayFloat, b.AvgOneDayFloatMTD, b.AvgOneDayFloatYTD, b.AvgTwoDayFloatMTD, b.AvgTwoDayFloatYTD, b.TransferCalc, b.TargBalDeficiency, b.TotalFundingReq, b.TotalChecksPaid, b.NUMCHECKSPAID, b.GrandTotCredMinDeb, b.NUMCREDITLESSDEBIT, b.ExtendABeanXMLID FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? and b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static String jdField_byte = " SELECT b.DataDate, b.OpeningLedger, b.AvgOpenLedgerMTD, b.AvgOpenLedgerYTD, b.ClosingLedger, b.AvgCloseLedgerMTD, b.AvgMonth, b.AggBalanceAdjust, b.AvCloseLedgYTDPrvM, b.AvgCloseLedgerYTD, b.CurrentLedger, b.ACHNetPosition, b.OpAvaiSamDayACHDTC, b.OpeningAvailable, b.AvgOpenAvailMTD, b.AvgOpenAvailYTD, b.AvgAvailPrevMonth, b.DisbOpenAvailBal, b.ClosingAvail, b.AvgCloseAvailMTD, b.AvCloseAvailPreM, b. AvCloseAvailYTDPrM, b.AvCloseAvailYTD, b.LoanBalance, b.TotalInvestPosn, b.CurrAvailCRSSupr, b.CurrentAvail, b.AvgCurrAvailMTD, b.AvgCurrAvailYTD, b.TotalFloat, b.TargetBalance, b.AdjBalance, b.AdjBalanceMTD, b.AdjBalanceYTD, b.ZeroDayFloat, b.OneDayFloat, b.FloatAdj, b.TwoMoreDayFloat, b.ThreeMoreDayFloat, b.AdjToBalances, b.AvgAdjToBalMTD, b.AvgAdjToBalYTD, b.FourDayFloat, b.FiveDayFloat, b.SixDayFloat, b.AvgOneDayFloatMTD, b.AvgOneDayFloatYTD, b.AvgTwoDayFloatMTD, b.AvgTwoDayFloatYTD, b.TransferCalc, b.TargBalDeficiency, b.TotalFundingReq, b.TotalChecksPaid, b.NUMCHECKSPAID, b.GrandTotCredMinDeb, b.NUMCREDITLESSDEBIT, b.ExtendABeanXMLID FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? and b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static String jdField_try = "SELECT ExtendABeanXMLID FROM DC_AccountHistory WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static String jdField_null = " ORDER by b.DataDate";
  private static String jdField_new = "a.RoutingNumber";
  private static String jdField_int = " SELECT max(b.DataDate) FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String jdField_do = " SELECT max(b.DataDate) FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataClassification=? ";
  private static String jdField_if = " SELECT max(b.DataDate)  FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND b.DataClassification=? ";
  private static String a = " SELECT max(b.DataDate) FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataClassification=? ";
  
  protected static void addHistory(Account paramAccount, AccountHistory paramAccountHistory, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      String str1 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_IDENTIFIER", null);
      Calendar localCalendar = null;
      if (paramHashMap != null) {
        localCalendar = (Calendar)paramHashMap.get("BAI_FILE_DATE");
      }
      String str2 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_NAME", null);
      String str3 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      Object localObject1 = null;
      if (!DCAccount.accountExists(paramAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(paramAccount, paramConnection, paramHashMap);
      }
      int j = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
      if (j == -1) {
        throw new DCException(414, "Account not found.");
      }
      AccountHistory localAccountHistory = a(paramAccount, paramAccountHistory.getHistoryDate(), paramConnection, str3);
      if (localAccountHistory == null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_AccountHistory( DCAccountID, DataDate, DataSource, OpeningLedger, AvgOpenLedgerMTD, AvgOpenLedgerYTD, ClosingLedger, AvgCloseLedgerMTD, AvgMonth, AggBalanceAdjust, AvCloseLedgYTDPrvM, AvgCloseLedgerYTD, CurrentLedger, ACHNetPosition, OpAvaiSamDayACHDTC, OpeningAvailable, AvgOpenAvailMTD, AvgOpenAvailYTD, AvgAvailPrevMonth, DisbOpenAvailBal, ClosingAvail, AvgCloseAvailMTD, AvCloseAvailPreM,  AvCloseAvailYTDPrM, AvCloseAvailYTD, LoanBalance, TotalInvestPosn, CurrAvailCRSSupr, CurrentAvail, AvgCurrAvailMTD, AvgCurrAvailYTD, TotalFloat, TargetBalance, AdjBalance, AdjBalanceMTD, AdjBalanceYTD, ZeroDayFloat, OneDayFloat, FloatAdj, TwoMoreDayFloat, ThreeMoreDayFloat, AdjToBalances, AvgAdjToBalMTD, AvgAdjToBalYTD, FourDayFloat, FiveDayFloat, SixDayFloat, AvgOneDayFloatMTD, AvgOneDayFloatYTD, AvgTwoDayFloatMTD, AvgTwoDayFloatYTD, TransferCalc, TargBalDeficiency, TotalFundingReq, TotalChecksPaid, NUMCHECKSPAID, GrandTotCredMinDeb, NUMCREDITLESSDEBIT, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
        localObject1 = paramAccountHistory;
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_AccountHistory SET DCAccountID=?, DataDate=?, DataSource=?, OpeningLedger=?, AvgOpenLedgerMTD=?, AvgOpenLedgerYTD=?, ClosingLedger=?, AvgCloseLedgerMTD=?, AvgMonth=?, AggBalanceAdjust=?, AvCloseLedgYTDPrvM=?, AvgCloseLedgerYTD=?, CurrentLedger=?, ACHNetPosition=?, OpAvaiSamDayACHDTC=?, OpeningAvailable=?, AvgOpenAvailMTD=?, AvgOpenAvailYTD=?, AvgAvailPrevMonth=?, DisbOpenAvailBal=?, ClosingAvail=?, AvgCloseAvailMTD=?, AvCloseAvailPreM=?,  AvCloseAvailYTDPrM=?, AvCloseAvailYTD=?, LoanBalance=?, TotalInvestPosn=?, CurrAvailCRSSupr=?, CurrentAvail=?, AvgCurrAvailMTD=?, AvgCurrAvailYTD=?, TotalFloat=?, TargetBalance=?, AdjBalance=?, AdjBalanceMTD=?, AdjBalanceYTD=?, ZeroDayFloat=?, OneDayFloat=?, FloatAdj=?, TwoMoreDayFloat=?, ThreeMoreDayFloat=?, AdjToBalances=?, AvgAdjToBalMTD=?, AvgAdjToBalYTD=?, FourDayFloat=?, FiveDayFloat=?, SixDayFloat=?, AvgOneDayFloatMTD=?, AvgOneDayFloatYTD=?, AvgTwoDayFloatMTD=?, AvgTwoDayFloatYTD=?, TransferCalc=?, TargBalDeficiency=?, TotalFundingReq=?, TotalChecksPaid=?, NUMCHECKSPAID=?, GrandTotCredMinDeb=?, NUMCREDITLESSDEBIT=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
        Currency localCurrency = null;
        localObject1 = localAccountHistory;
        localCurrency = paramAccountHistory.getOpeningLedger();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setOpeningLedger(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgOpeningLedgerMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgOpeningLedgerMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgOpeningLedgerYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgOpeningLedgerYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getClosingLedger();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setClosingLedger(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgClosingLedgerMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgClosingLedgerMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgMonth();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgMonth(localCurrency);
        }
        localCurrency = paramAccountHistory.getAggregateBalAdjustment();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAggregateBalAdjustment(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgClosingLedgerYTDPrevMonth();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgClosingLedgerYTDPrevMonth(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgClosingLedgerYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgClosingLedgerYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getCurrentLedger();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setCurrentLedger(localCurrency);
        }
        localCurrency = paramAccountHistory.getNetPositionACH();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setNetPositionACH(localCurrency);
        }
        localCurrency = paramAccountHistory.getOpenAvailSameDayACHDTC();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setOpenAvailSameDayACHDTC(localCurrency);
        }
        localCurrency = paramAccountHistory.getOpeningAvail();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setOpeningAvail(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgOpenAvailMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgOpenAvailMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgOpenAvailYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgOpenAvailYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgAvailPrevMonth();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgAvailPrevMonth(localCurrency);
        }
        localCurrency = paramAccountHistory.getDisbursingOpeningAvailBal();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setDisbursingOpeningAvailBal(localCurrency);
        }
        localCurrency = paramAccountHistory.getClosingAvail();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setClosingAvail(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgClosingAvailMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgClosingAvailMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgClosingAvailPrevMonth();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgClosingAvailPrevMonth(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgClosingAvailYTDPrevMonth();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgClosingAvailYTDPrevMonth(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgClosingAvailYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgClosingAvailYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getLoanBal();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setLoanBal(localCurrency);
        }
        localCurrency = paramAccountHistory.getTotalInvestmentPosition();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setTotalInvestmentPosition(localCurrency);
        }
        localCurrency = paramAccountHistory.getCurrentAvailCRSSurpressed();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setCurrentAvailCRSSurpressed(localCurrency);
        }
        localCurrency = paramAccountHistory.getCurrentAvail();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setCurrentAvail(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgCurrentAvailMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgCurrentAvailMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgCurrentAvailYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgCurrentAvailYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getTotalFloat();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setTotalFloat(localCurrency);
        }
        localCurrency = paramAccountHistory.getTargetBal();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setTargetBal(localCurrency);
        }
        localCurrency = paramAccountHistory.getAdjustedBal();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAdjustedBal(localCurrency);
        }
        localCurrency = paramAccountHistory.getAdjustedBalMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAdjustedBalMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAdjustedBalYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAdjustedBalYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getZeroDayFloat();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setZeroDayFloat(localCurrency);
        }
        localCurrency = paramAccountHistory.getOneDayFloat();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setOneDayFloat(localCurrency);
        }
        localCurrency = paramAccountHistory.getFloatAdjusted();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setFloatAdjusted(localCurrency);
        }
        localCurrency = paramAccountHistory.getTwoOrMoreDayFloat();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setTwoOrMoreDayFloat(localCurrency);
        }
        localCurrency = paramAccountHistory.getThreeOrMoreDayFloat();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setThreeOrMoreDayFloat(localCurrency);
        }
        localCurrency = paramAccountHistory.getAdjustmentToBal();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAdjustmentToBal(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgAdjustmentToBalMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgAdjustmentToBalMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgAdjustmentToBalYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgAdjustmentToBalYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getFourDayFloat();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setFourDayFloat(localCurrency);
        }
        localCurrency = paramAccountHistory.getFiveDayFloat();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setFiveDayFloat(localCurrency);
        }
        localCurrency = paramAccountHistory.getSixDayFloat();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setSixDayFloat(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgOneDayFloatMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgOneDayFloatMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgOneDayFloatYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgOneDayFloatYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgTwoDayFloatMTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgTwoDayFloatMTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getAvgTwoDayFloatYTD();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setAvgTwoDayFloatYTD(localCurrency);
        }
        localCurrency = paramAccountHistory.getTransferCalculation();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setTransferCalculation(localCurrency);
        }
        localCurrency = paramAccountHistory.getTargetBalDeficiency();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setTargetBalDeficiency(localCurrency);
        }
        localCurrency = paramAccountHistory.getTotalFundingRequirement();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setTotalFundingRequirement(localCurrency);
        }
        localCurrency = paramAccountHistory.getTotalChecksPaid();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setTotalChecksPaid(localCurrency);
        }
        int k = paramAccountHistory.getNumChecksPaid();
        if (k != -1) {
          ((AccountHistory)localObject1).setNumChecksPaid(k);
        }
        localCurrency = paramAccountHistory.getGrandTotalCreditMinusDebit();
        if (localCurrency != null) {
          ((AccountHistory)localObject1).setGrandTotalCreditMinusDebit(localCurrency);
        }
        k = paramAccountHistory.getGrandNumCreditMinusDebit();
        if (k != -1) {
          ((AccountHistory)localObject1).setGrandNumCreditMinusDebit(k);
        }
      }
      if (((AccountHistory)localObject1).getHistoryDate() == null) {
        throw new DCException(433, "There is no history date set with account history");
      }
      localPreparedStatement.setInt(1, j);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, ((AccountHistory)localObject1).getHistoryDate());
      localPreparedStatement.setInt(3, paramInt);
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, ((AccountHistory)localObject1).getOpeningLedger());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 5, ((AccountHistory)localObject1).getAvgOpeningLedgerMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 6, ((AccountHistory)localObject1).getAvgOpeningLedgerYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 7, ((AccountHistory)localObject1).getClosingLedger());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, ((AccountHistory)localObject1).getAvgClosingLedgerMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, ((AccountHistory)localObject1).getAvgMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 10, ((AccountHistory)localObject1).getAggregateBalAdjustment());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 11, ((AccountHistory)localObject1).getAvgClosingLedgerYTDPrevMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 12, ((AccountHistory)localObject1).getAvgClosingLedgerYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 13, ((AccountHistory)localObject1).getCurrentLedger());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 14, ((AccountHistory)localObject1).getNetPositionACH());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 15, ((AccountHistory)localObject1).getOpenAvailSameDayACHDTC());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 16, ((AccountHistory)localObject1).getOpeningAvail());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 17, ((AccountHistory)localObject1).getAvgOpenAvailMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 18, ((AccountHistory)localObject1).getAvgOpenAvailYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 19, ((AccountHistory)localObject1).getAvgAvailPrevMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 20, ((AccountHistory)localObject1).getDisbursingOpeningAvailBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 21, ((AccountHistory)localObject1).getClosingAvail());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 22, ((AccountHistory)localObject1).getAvgClosingAvailMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 23, ((AccountHistory)localObject1).getAvgClosingAvailPrevMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 24, ((AccountHistory)localObject1).getAvgClosingAvailYTDPrevMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 25, ((AccountHistory)localObject1).getAvgClosingAvailYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 26, ((AccountHistory)localObject1).getLoanBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 27, ((AccountHistory)localObject1).getTotalInvestmentPosition());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 28, ((AccountHistory)localObject1).getCurrentAvailCRSSurpressed());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 29, ((AccountHistory)localObject1).getCurrentAvail());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 30, ((AccountHistory)localObject1).getAvgCurrentAvailMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 31, ((AccountHistory)localObject1).getAvgCurrentAvailYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 32, ((AccountHistory)localObject1).getTotalFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 33, ((AccountHistory)localObject1).getTargetBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 34, ((AccountHistory)localObject1).getAdjustedBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 35, ((AccountHistory)localObject1).getAdjustedBalMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 36, ((AccountHistory)localObject1).getAdjustedBalYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 37, ((AccountHistory)localObject1).getZeroDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 38, ((AccountHistory)localObject1).getOneDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 39, ((AccountHistory)localObject1).getFloatAdjusted());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 40, ((AccountHistory)localObject1).getTwoOrMoreDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 41, ((AccountHistory)localObject1).getThreeOrMoreDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 42, ((AccountHistory)localObject1).getAdjustmentToBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 43, ((AccountHistory)localObject1).getAvgAdjustmentToBalMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 44, ((AccountHistory)localObject1).getAvgAdjustmentToBalYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 45, ((AccountHistory)localObject1).getFourDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 46, ((AccountHistory)localObject1).getFiveDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 47, ((AccountHistory)localObject1).getSixDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 48, ((AccountHistory)localObject1).getAvgOneDayFloatMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 49, ((AccountHistory)localObject1).getAvgOneDayFloatYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 50, ((AccountHistory)localObject1).getAvgTwoDayFloatMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 51, ((AccountHistory)localObject1).getAvgTwoDayFloatYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 52, ((AccountHistory)localObject1).getTransferCalculation());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 53, ((AccountHistory)localObject1).getTargetBalDeficiency());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 54, ((AccountHistory)localObject1).getTotalFundingRequirement());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 55, ((AccountHistory)localObject1).getTotalChecksPaid());
      localPreparedStatement.setInt(56, ((AccountHistory)localObject1).getNumChecksPaid());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 57, ((AccountHistory)localObject1).getGrandTotalCreditMinusDebit());
      localPreparedStatement.setInt(58, ((AccountHistory)localObject1).getGrandNumCreditMinusDebit());
      long l1;
      if (localAccountHistory == null)
      {
        l1 = DCExtendABeanXML.addExtendABeanXML(paramConnection, ((AccountHistory)localObject1).getExtendABeanXML(), paramHashMap);
        localPreparedStatement.setLong(59, l1);
        localPreparedStatement.setString(60, null);
        localPreparedStatement.setString(61, str1);
        localPreparedStatement.setString(62, str3);
        localPreparedStatement.setString(63, str2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 64, localCalendar);
        DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_AccountHistory( DCAccountID, DataDate, DataSource, OpeningLedger, AvgOpenLedgerMTD, AvgOpenLedgerYTD, ClosingLedger, AvgCloseLedgerMTD, AvgMonth, AggBalanceAdjust, AvCloseLedgYTDPrvM, AvgCloseLedgerYTD, CurrentLedger, ACHNetPosition, OpAvaiSamDayACHDTC, OpeningAvailable, AvgOpenAvailMTD, AvgOpenAvailYTD, AvgAvailPrevMonth, DisbOpenAvailBal, ClosingAvail, AvgCloseAvailMTD, AvCloseAvailPreM,  AvCloseAvailYTDPrM, AvCloseAvailYTD, LoanBalance, TotalInvestPosn, CurrAvailCRSSupr, CurrentAvail, AvgCurrAvailMTD, AvgCurrAvailYTD, TotalFloat, TargetBalance, AdjBalance, AdjBalanceMTD, AdjBalanceYTD, ZeroDayFloat, OneDayFloat, FloatAdj, TwoMoreDayFloat, ThreeMoreDayFloat, AdjToBalances, AvgAdjToBalMTD, AvgAdjToBalYTD, FourDayFloat, FiveDayFloat, SixDayFloat, AvgOneDayFloatMTD, AvgOneDayFloatYTD, AvgTwoDayFloatMTD, AvgTwoDayFloatYTD, TransferCalc, TargBalDeficiency, TotalFundingReq, TotalChecksPaid, NUMCHECKSPAID, GrandTotCredMinDeb, NUMCREDITLESSDEBIT, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
      }
      else
      {
        if (((AccountHistory)localObject1).getHistoryDate() == null) {
          throw new DCException(433, "The account history has no date.");
        }
        l1 = a(paramConnection, j, ((AccountHistory)localObject1).getHistoryDate(), str3);
        DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
        a((AccountHistory)localObject1, paramAccountHistory);
        long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, ((AccountHistory)localObject1).getExtendABeanXML(), paramHashMap);
        localPreparedStatement.setString(59, str1);
        localPreparedStatement.setLong(60, l2);
        localPreparedStatement.setString(61, str2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 62, localCalendar);
        localPreparedStatement.setInt(63, j);
        DCUtil.fillTimestampColumn(localPreparedStatement, 64, ((AccountHistory)localObject1).getHistoryDate());
        localPreparedStatement.setString(65, str3);
        DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_AccountHistory SET DCAccountID=?, DataDate=?, DataSource=?, OpeningLedger=?, AvgOpenLedgerMTD=?, AvgOpenLedgerYTD=?, ClosingLedger=?, AvgCloseLedgerMTD=?, AvgMonth=?, AggBalanceAdjust=?, AvCloseLedgYTDPrvM=?, AvgCloseLedgerYTD=?, CurrentLedger=?, ACHNetPosition=?, OpAvaiSamDayACHDTC=?, OpeningAvailable=?, AvgOpenAvailMTD=?, AvgOpenAvailYTD=?, AvgAvailPrevMonth=?, DisbOpenAvailBal=?, ClosingAvail=?, AvgCloseAvailMTD=?, AvCloseAvailPreM=?,  AvCloseAvailYTDPrM=?, AvCloseAvailYTD=?, LoanBalance=?, TotalInvestPosn=?, CurrAvailCRSSupr=?, CurrentAvail=?, AvgCurrAvailMTD=?, AvgCurrAvailYTD=?, TotalFloat=?, TargetBalance=?, AdjBalance=?, AdjBalanceMTD=?, AdjBalanceYTD=?, ZeroDayFloat=?, OneDayFloat=?, FloatAdj=?, TwoMoreDayFloat=?, ThreeMoreDayFloat=?, AdjToBalances=?, AvgAdjToBalMTD=?, AvgAdjToBalYTD=?, FourDayFloat=?, FiveDayFloat=?, SixDayFloat=?, AvgOneDayFloatMTD=?, AvgOneDayFloatYTD=?, AvgTwoDayFloatMTD=?, AvgTwoDayFloatYTD=?, TransferCalc=?, TargBalDeficiency=?, TotalFundingReq=?, TotalChecksPaid=?, NUMCHECKSPAID=?, GrandTotCredMinDeb=?, NUMCREDITLESSDEBIT=?, BAIFileIdentifier=?, ExtendABeanXMLID=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 430;
      throw new DCException(i, "Unable to update account history.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void updateHistory(Connection paramConnection, Account paramAccount, AccountHistory paramAccountHistory, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      i = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
      if (i == -1) {
        throw new DCException(414, "Account not found.");
      }
      AccountHistory localAccountHistory = a(paramAccount, paramAccountHistory.getHistoryDate(), paramConnection, str);
      if (localAccountHistory == null) {
        throw new DCException(455, "No account history was found to update for the date " + paramAccountHistory.getHistoryDate().getTime().toString() + " , and account with accoutid=" + paramAccount.getID() + ", bankid= " + paramAccount.getBankID() + ", routing number=" + paramAccount.getRoutingNum() + ", and data classification '" + str + "'.");
      }
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_AccountHistory SET DCAccountID=?, DataDate=?, OpeningLedger=?, AvgOpenLedgerMTD=?, AvgOpenLedgerYTD=?, ClosingLedger=?, AvgCloseLedgerMTD=?, AvgMonth=?, AggBalanceAdjust=?, AvCloseLedgYTDPrvM=?, AvgCloseLedgerYTD=?, CurrentLedger=?, ACHNetPosition=?, OpAvaiSamDayACHDTC=?, OpeningAvailable=?, AvgOpenAvailMTD=?, AvgOpenAvailYTD=?, AvgAvailPrevMonth=?, DisbOpenAvailBal=?, ClosingAvail=?, AvgCloseAvailMTD=?, AvCloseAvailPreM=?,  AvCloseAvailYTDPrM=?, AvCloseAvailYTD=?, LoanBalance=?, TotalInvestPosn=?, CurrAvailCRSSupr=?, CurrentAvail=?, AvgCurrAvailMTD=?, AvgCurrAvailYTD=?, TotalFloat=?, TargetBalance=?, AdjBalance=?, AdjBalanceMTD=?, AdjBalanceYTD=?, ZeroDayFloat=?, OneDayFloat=?, FloatAdj=?, TwoMoreDayFloat=?, ThreeMoreDayFloat=?, AdjToBalances=?, AvgAdjToBalMTD=?, AvgAdjToBalYTD=?, FourDayFloat=?, FiveDayFloat=?, SixDayFloat=?, AvgOneDayFloatMTD=?, AvgOneDayFloatYTD=?, AvgTwoDayFloatMTD=?, AvgTwoDayFloatYTD=?, TransferCalc=?, TargBalDeficiency=?, TotalFundingReq=?, TotalChecksPaid=?, NUMCHECKSPAID=?, GrandTotCredMinDeb=?, NUMCREDITLESSDEBIT=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
      localPreparedStatement.setInt(1, i);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramAccountHistory.getHistoryDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 3, paramAccountHistory.getOpeningLedger());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, paramAccountHistory.getAvgOpeningLedgerMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 5, paramAccountHistory.getAvgOpeningLedgerYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 6, paramAccountHistory.getClosingLedger());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 7, paramAccountHistory.getAvgClosingLedgerMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, paramAccountHistory.getAvgMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, paramAccountHistory.getAggregateBalAdjustment());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 10, paramAccountHistory.getAvgClosingLedgerYTDPrevMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 11, paramAccountHistory.getAvgClosingLedgerYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 12, paramAccountHistory.getCurrentLedger());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 13, paramAccountHistory.getNetPositionACH());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 14, paramAccountHistory.getOpenAvailSameDayACHDTC());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 15, paramAccountHistory.getOpeningAvail());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 16, paramAccountHistory.getAvgOpenAvailMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 17, paramAccountHistory.getAvgOpenAvailYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 18, paramAccountHistory.getAvgAvailPrevMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 19, paramAccountHistory.getDisbursingOpeningAvailBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 20, paramAccountHistory.getClosingAvail());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 21, paramAccountHistory.getAvgClosingAvailMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 22, paramAccountHistory.getAvgClosingAvailPrevMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 23, paramAccountHistory.getAvgClosingAvailYTDPrevMonth());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 24, paramAccountHistory.getAvgClosingAvailYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 25, paramAccountHistory.getLoanBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 26, paramAccountHistory.getTotalInvestmentPosition());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 27, paramAccountHistory.getCurrentAvailCRSSurpressed());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 28, paramAccountHistory.getCurrentAvail());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 29, paramAccountHistory.getAvgCurrentAvailMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 30, paramAccountHistory.getAvgCurrentAvailYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 31, paramAccountHistory.getTotalFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 32, paramAccountHistory.getTargetBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 33, paramAccountHistory.getAdjustedBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 34, paramAccountHistory.getAdjustedBalMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 35, paramAccountHistory.getAdjustedBalYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 36, paramAccountHistory.getZeroDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 37, paramAccountHistory.getOneDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 38, paramAccountHistory.getFloatAdjusted());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 39, paramAccountHistory.getTwoOrMoreDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 40, paramAccountHistory.getThreeOrMoreDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 41, paramAccountHistory.getAdjustmentToBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 42, paramAccountHistory.getAvgAdjustmentToBalMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 43, paramAccountHistory.getAvgAdjustmentToBalYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 44, paramAccountHistory.getFourDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 45, paramAccountHistory.getFiveDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 46, paramAccountHistory.getSixDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 47, paramAccountHistory.getAvgOneDayFloatMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 48, paramAccountHistory.getAvgOneDayFloatYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 49, paramAccountHistory.getAvgTwoDayFloatMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 50, paramAccountHistory.getAvgTwoDayFloatYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 51, paramAccountHistory.getTransferCalculation());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 52, paramAccountHistory.getTargetBalDeficiency());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 53, paramAccountHistory.getTotalFundingRequirement());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 54, paramAccountHistory.getTotalChecksPaid());
      localPreparedStatement.setInt(55, paramAccountHistory.getNumChecksPaid());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 56, paramAccountHistory.getGrandTotalCreditMinusDebit());
      localPreparedStatement.setInt(57, paramAccountHistory.getGrandNumCreditMinusDebit());
      long l1 = a(paramConnection, i, paramAccountHistory.getHistoryDate(), str);
      DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
      long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, paramAccountHistory.getExtendABeanXML(), paramHashMap);
      localPreparedStatement.setLong(58, l2);
      localPreparedStatement.setInt(59, i);
      DCUtil.fillTimestampColumn(localPreparedStatement, 60, paramAccountHistory.getHistoryDate());
      localPreparedStatement.setString(61, str);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_AccountHistory SET DCAccountID=?, DataDate=?, OpeningLedger=?, AvgOpenLedgerMTD=?, AvgOpenLedgerYTD=?, ClosingLedger=?, AvgCloseLedgerMTD=?, AvgMonth=?, AggBalanceAdjust=?, AvCloseLedgYTDPrvM=?, AvgCloseLedgerYTD=?, CurrentLedger=?, ACHNetPosition=?, OpAvaiSamDayACHDTC=?, OpeningAvailable=?, AvgOpenAvailMTD=?, AvgOpenAvailYTD=?, AvgAvailPrevMonth=?, DisbOpenAvailBal=?, ClosingAvail=?, AvgCloseAvailMTD=?, AvCloseAvailPreM=?,  AvCloseAvailYTDPrM=?, AvCloseAvailYTD=?, LoanBalance=?, TotalInvestPosn=?, CurrAvailCRSSupr=?, CurrentAvail=?, AvgCurrAvailMTD=?, AvgCurrAvailYTD=?, TotalFloat=?, TargetBalance=?, AdjBalance=?, AdjBalanceMTD=?, AdjBalanceYTD=?, ZeroDayFloat=?, OneDayFloat=?, FloatAdj=?, TwoMoreDayFloat=?, ThreeMoreDayFloat=?, AdjToBalances=?, AvgAdjToBalMTD=?, AvgAdjToBalYTD=?, FourDayFloat=?, FiveDayFloat=?, SixDayFloat=?, AvgOneDayFloatMTD=?, AvgOneDayFloatYTD=?, AvgTwoDayFloatMTD=?, AvgTwoDayFloatYTD=?, TransferCalc=?, TargBalDeficiency=?, TotalFundingReq=?, TotalChecksPaid=?, NUMCHECKSPAID=?, GrandTotCredMinDeb=?, NUMCREDITLESSDEBIT=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 455;
      throw new DCException(i, "Unable to update account history.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static long a(Connection paramConnection, int paramInt, DateTime paramDateTime, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    long l;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_try);
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramDateTime);
      localPreparedStatement.setString(3, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_try);
      if (localResultSet.next()) {
        l = localResultSet.getLong(1);
      } else {
        throw new DCException(434, "Error retrieving EXTENDABEANXMLID: No records in DC_ACCOUNTHISTORY table with DCACCOUNTID " + paramInt + " and DATADATE " + paramDateTime);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 434;
      throw new DCException(i, "Failed to get bean xml ID.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
    return l;
  }
  
  private static void a(AccountHistory paramAccountHistory1, AccountHistory paramAccountHistory2)
  {
    if ((paramAccountHistory1 != null) && (paramAccountHistory2 != null)) {
      paramAccountHistory1.putAll(paramAccountHistory2);
    }
  }
  
  protected static AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
      AccountHistories localAccountHistories = getHistory(paramAccount, paramCalendar1, paramCalendar2, localConnection, paramHashMap);
      return localAccountHistories;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    finally
    {
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  protected static AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    AccountHistories localAccountHistories1 = null;
    ResultSet localResultSet = null;
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      localResultSet = jdField_if(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      localAccountHistories1 = new AccountHistories();
      AccountHistory localAccountHistory = null;
      while (localResultSet.next())
      {
        localAccountHistory = a(paramAccount, localResultSet);
        DCUtil.fillExtendABean(paramConnection, localAccountHistory, localResultSet, 57);
        localAccountHistories1.add(localAccountHistory);
      }
      AccountHistories localAccountHistories2 = localAccountHistories1;
      return localAccountHistories2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 429;
      throw new DCException(i, "Unable to get account history for account " + paramAccount.getID(), localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static AccountHistory a(Account paramAccount, Calendar paramCalendar, Connection paramConnection, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str = paramAccount.getRoutingNum();
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_char);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramAccount.getRoutingNum());
        localPreparedStatement.setString(5, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_char);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_byte);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_byte);
      }
      AccountHistory localAccountHistory1 = null;
      if (localResultSet.next())
      {
        localAccountHistory1 = a(paramAccount, localResultSet);
        DCUtil.fillExtendABean(paramConnection, localAccountHistory1, localResultSet, 57);
      }
      AccountHistory localAccountHistory2 = localAccountHistory1;
      return localAccountHistory2;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 429;
      throw new DCException(i, "Unable to get account history for account " + paramAccount.getID(), localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static AccountHistory a(Account paramAccount, ResultSet paramResultSet)
    throws DCException
  {
    try
    {
      AccountHistory localAccountHistory = new AccountHistory();
      localAccountHistory.setAccountID(paramAccount.getID());
      localAccountHistory.setAccountNumber(paramAccount.getNumber());
      localAccountHistory.setRoutingNumber(paramAccount.getRoutingNum());
      localAccountHistory.setBankID(paramAccount.getBankID());
      localAccountHistory.setHistoryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale()));
      localAccountHistory.setOpeningLedger(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgOpeningLedgerMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgOpeningLedgerYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setClosingLedger(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgClosingLedgerMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgMonth(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAggregateBalAdjustment(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgClosingLedgerYTDPrevMonth(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgClosingLedgerYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setCurrentLedger(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setNetPositionACH(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setOpenAvailSameDayACHDTC(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setOpeningAvail(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgOpenAvailMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(15), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgOpenAvailYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(16), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgAvailPrevMonth(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(17), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setDisbursingOpeningAvailBal(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(18), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setClosingAvail(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(19), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgClosingAvailMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(20), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgClosingAvailPrevMonth(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(21), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgClosingAvailYTDPrevMonth(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(22), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgClosingAvailYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(23), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setLoanBal(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(24), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setTotalInvestmentPosition(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(25), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setCurrentAvailCRSSurpressed(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(26), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setCurrentAvail(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(27), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgCurrentAvailMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(28), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgCurrentAvailYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(29), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setTotalFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(30), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setTargetBal(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(31), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAdjustedBal(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(32), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAdjustedBalMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(33), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAdjustedBalYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(34), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setZeroDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(35), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setOneDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(36), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setFloatAdjusted(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(37), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setTwoOrMoreDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(38), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setThreeOrMoreDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(39), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAdjustmentToBal(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(40), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgAdjustmentToBalMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(41), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgAdjustmentToBalYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(42), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setFourDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(43), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setFiveDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(44), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setSixDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(45), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgOneDayFloatMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(46), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgOneDayFloatYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(47), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgTwoDayFloatMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(48), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setAvgTwoDayFloatYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(49), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setTransferCalculation(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(50), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setTargetBalDeficiency(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(51), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setTotalFundingRequirement(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(52), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setTotalChecksPaid(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(53), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setNumChecksPaid(paramResultSet.getInt(54));
      localAccountHistory.setGrandTotalCreditMinusDebit(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(55), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAccountHistory.setGrandNumCreditMinusDebit(paramResultSet.getInt(56));
      return localAccountHistory;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 432;
      throw new DCException(i, "Unable to create account history for account " + paramAccount.getID(), localException);
    }
  }
  
  protected static ArrayList getHistoryMapList(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    ArrayList localArrayList1 = null;
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_if(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      localArrayList1 = a(localResultSet, paramAccount, paramConnection);
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ArrayList a(ResultSet paramResultSet, Account paramAccount, Connection paramConnection)
    throws DCException
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      HashMap localHashMap1 = null;
      Object localObject = null;
      while (paramResultSet.next())
      {
        DateTime localDateTime = DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale());
        if (localObject == null)
        {
          localObject = localDateTime;
          localHashMap1 = new HashMap();
          localArrayList.add(localHashMap1);
        }
        else if (!localDateTime.isSameDayInYearAs(localObject))
        {
          localObject = localDateTime;
          localHashMap1 = new HashMap();
          localArrayList.add(localHashMap1);
        }
        String str = paramResultSet.getString(58);
        a(localHashMap1, "DATADATE", localDateTime);
        a(localHashMap1, "OPENINGLEDGER", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGOPENLEDGERMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGOPENLEDGERYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), str, paramAccount.getLocale())));
        a(localHashMap1, "CLOSINGLEDGER", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGCLOSELEDGERMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGMONTH", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), str, paramAccount.getLocale())));
        a(localHashMap1, "AGGBALANCEADJUST", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), str, paramAccount.getLocale())));
        a(localHashMap1, "AVCLOSELEDGYTDPRVM", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGCLOSELEDGERYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), str, paramAccount.getLocale())));
        a(localHashMap1, "CURRENTLEDGER", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), str, paramAccount.getLocale())));
        a(localHashMap1, "ACHNETPOSITION", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), str, paramAccount.getLocale())));
        a(localHashMap1, "OPAVAISAMDAYACHDTC", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), str, paramAccount.getLocale())));
        a(localHashMap1, "OPENINGAVAILABLE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGOPENAVAILMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(15), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGOPENAVAILYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(16), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGAVAILPREVMONTH", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(17), str, paramAccount.getLocale())));
        a(localHashMap1, "DISBOPENAVAILBAL", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(18), str, paramAccount.getLocale())));
        a(localHashMap1, "CLOSINGAVAIL", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(19), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGCLOSEAVAILMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(20), str, paramAccount.getLocale())));
        a(localHashMap1, "AVCLOSEAVAILPREM", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(21), str, paramAccount.getLocale())));
        a(localHashMap1, "AVCLOSEAVAILYTDPRM", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(22), str, paramAccount.getLocale())));
        a(localHashMap1, "AVCLOSEAVAILYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(23), str, paramAccount.getLocale())));
        a(localHashMap1, "LOANBALANCE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(24), str, paramAccount.getLocale())));
        a(localHashMap1, "TOTALINVESTPOSN", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(25), str, paramAccount.getLocale())));
        a(localHashMap1, "CURRAVAILCRSSUPR", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(26), str, paramAccount.getLocale())));
        a(localHashMap1, "CURRENTAVAIL", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(27), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGCURRAVAILMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(28), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGCURRAVAILYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(29), str, paramAccount.getLocale())));
        a(localHashMap1, "TOTALFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(30), str, paramAccount.getLocale())));
        a(localHashMap1, "TARGETBALANCE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(31), str, paramAccount.getLocale())));
        a(localHashMap1, "ADJBALANCE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(32), str, paramAccount.getLocale())));
        a(localHashMap1, "ADJBALANCEMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(33), str, paramAccount.getLocale())));
        a(localHashMap1, "ADJBALANCEYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(34), str, paramAccount.getLocale())));
        a(localHashMap1, "ZERODAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(35), str, paramAccount.getLocale())));
        a(localHashMap1, "ONEDAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(36), str, paramAccount.getLocale())));
        a(localHashMap1, "FLOATADJ", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(37), str, paramAccount.getLocale())));
        a(localHashMap1, "TWOMOREDAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(38), str, paramAccount.getLocale())));
        a(localHashMap1, "THREEMOREDAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(39), str, paramAccount.getLocale())));
        a(localHashMap1, "ADJTOBALANCES", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(40), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGADJTOBALMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(41), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGADJTOBALYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(42), str, paramAccount.getLocale())));
        a(localHashMap1, "FOURDAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(43), str, paramAccount.getLocale())));
        a(localHashMap1, "FIVEDAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(44), str, paramAccount.getLocale())));
        a(localHashMap1, "SIXDAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(45), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGONEDAYFLOATMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(46), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGONEDAYFLOATYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(47), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGTWODAYFLOATMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(48), str, paramAccount.getLocale())));
        a(localHashMap1, "AVGTWODAYFLOATYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(49), str, paramAccount.getLocale())));
        a(localHashMap1, "TRANSFERCALC", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(50), str, paramAccount.getLocale())));
        a(localHashMap1, "TARGBALDEFICIENCY", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(51), str, paramAccount.getLocale())));
        a(localHashMap1, "TOTALFUNDINGREQ", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(52), str, paramAccount.getLocale())));
        int j = paramResultSet.getInt(54);
        if (paramResultSet.wasNull()) {
          j = -1;
        }
        a(localHashMap1, "TOTALCHECKSPAID", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(53), str, paramAccount.getLocale()), j));
        j = paramResultSet.getInt(56);
        if (paramResultSet.wasNull()) {
          j = -1;
        }
        a(localHashMap1, "GRANDTOTCREDMINDEB", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(55), str, paramAccount.getLocale()), j));
        AccountHistory localAccountHistory = new AccountHistory();
        DCUtil.fillExtendABean(paramConnection, localAccountHistory, paramResultSet, 57);
        HashMap localHashMap2 = localAccountHistory.getHash();
        if (localHashMap2.size() != 0)
        {
          Iterator localIterator = localHashMap2.entrySet().iterator();
          while (localIterator.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            if (localEntry.getValue() != null) {
              a(localHashMap1, localEntry.getKey(), localEntry.getValue());
            }
          }
        }
      }
      return localArrayList;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 435;
      throw new DCException(i, "Unable to create account history value maps.", localException);
    }
  }
  
  private static void a(HashMap paramHashMap, Object paramObject1, Object paramObject2)
  {
    if (paramObject2 != null) {
      paramHashMap.put(paramObject1, paramObject2);
    }
  }
  
  private static ResultSet jdField_if(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer();
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(b);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
          localStringBuffer.append(jdField_null);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(6, paramAccount.getRoutingNum());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(jdField_void);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
          localStringBuffer.append(jdField_null);
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(5, paramAccount.getRoutingNum());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(jdField_goto);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
        localStringBuffer.append(jdField_null);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(5, paramAccount.getRoutingNum());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(jdField_else);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
        localStringBuffer.append(jdField_null);
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(4, paramAccount.getRoutingNum());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      return localResultSet;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 429;
      throw new DCException(i, "Unable to get account history.", localException);
    }
  }
  
  protected static DateTime getHistoryMaxDate(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    Object localObject1 = null;
    ResultSet localResultSet = null;
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      localResultSet = a(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      if (localResultSet.next())
      {
        localDateTime = DCUtil.getTimestampColumn(localResultSet.getTimestamp(1), paramAccount.getLocale());
        return localDateTime;
      }
      DateTime localDateTime = null;
      return localDateTime;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 463;
      throw new DCException(i, "Unable to get max account history date for account " + paramAccount.getID(), localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ResultSet a(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer();
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(jdField_int);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
          localPreparedStatement.setString(5, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(6, paramAccount.getRoutingNum());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
        else
        {
          localStringBuffer.append(jdField_do);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
          localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(5, paramAccount.getRoutingNum());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(jdField_if);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
        localPreparedStatement.setString(4, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(5, paramAccount.getRoutingNum());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      else
      {
        localStringBuffer.append(a);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
        localPreparedStatement = DCAdapter.getStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(4, paramAccount.getRoutingNum());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      return localResultSet;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 463;
      throw new DCException(i, "Unable to get account history.", localException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCAccountHistory
 * JD-Core Version:    0.7.0.1
 */