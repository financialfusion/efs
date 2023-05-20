package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
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

class DCAccountSummary
{
  private static final String L = "INSERT INTO DC_AccountSummary( DCAccountID, DataDate, DataSource, TotalCredits, TotalCreditsMTD, CreditsNotDetailed,  DepositsSubjFloat, TotalAdjCreditsYTD, TotalLockboxDeposits, TotalDebits, TotalDebitsMTD, TodayTotalDebits, TotalDebitLessWire, TotalAdjDebitsYTD, TotalDebitsExRetn, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, AvailableOverdraft, RestrictedCash, AccruedInterest, AccruedDividend, TotalOverdraftAmt, NxtOvrDrftPmtDate, InterestRate,  OpeningLedger, ClosingLedger, CurrAvailBal, LedgerBal, OneDayFloat, TwoDayFloat, TotalFloat, Total_Num_Debits, Total_Num_Credits, NUMCREDITSMTD, NUMCRNOTDETAIL, NUMDEPSUBJFLOAT, NUMADJCREDITYTD, NUMCURDAYLBDEP, NUMDEBITSMTD, NUMTODAYTOTALDEBIT, NUMDEBITLESSWIRES, NUMADJDEBITYTD, NUMDEBITEXRETN, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String X = "INSERT INTO DC_AccountSummary( DCAccountID, DataDate, DataSource, BookValue, MarketValue, ValueDateTime, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
  private static final String h = "INSERT INTO DC_CCAcctSummary( DCAccountID, DataDate, DataSource, AvailableCredit, AmountDue, InterestRate, DueDate, CardHolderName, CardExpDate, CreditLimit, LastPaymentAmt, NextPaymentMinAmt, NextPaymentDue, LastPaymentDate, ValueDate, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
  private static final String f = "INSERT INTO DC_LoanAcctSummary( DCAccountID, DataDate, DataSource, AvailableCredit, AmountDue, InterestRate, DueDate, MaturityDate, AccruedInterest, OpeningBalance, CollateralDesc, PrinciplePastDue, InterestPastDue, LateFees, NextPrincipleAmt, NextInterestAmt, ValueDate, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ? )";
  private static final String o = "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, DataSource=?, BookValue=?, MarketValue=?, ValueDateTime=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static final String A = "UPDATE DC_CCAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, CardHolderName=?, CardExpDate=?, CreditLimit=?, LastPaymentAmt=?, NextPaymentMinAmt=?, NextPaymentDue=?, LastPaymentDate=?, ValueDate=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static final String g = "UPDATE DC_LoanAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, MaturityDate=?, AccruedInterest=?, OpeningBalance=?, CollateralDesc=?, PrinciplePastDue=?, InterestPastDue=?, LateFees=?, NextPrincipleAmt=?, NextInterestAmt=?, ValueDate=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static final String W = "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalCreditsMTD=?, CreditsNotDetailed=?,  DepositsSubjFloat=?, TotalAdjCreditsYTD=?, TotalLockboxDeposits=?, TotalDebits=?, TotalDebitsMTD=?, TodayTotalDebits=?, TotalDebitLessWire=?, TotalAdjDebitsYTD=?, TotalDebitsExRetn=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, AvailableOverdraft=?, RestrictedCash=?, AccruedInterest=?, AccruedDividend=?, TotalOverdraftAmt=?, NxtOvrDrftPmtDate=?, InterestRate=?, OpeningLedger=?, ClosingLedger=?, CurrAvailBal=?, LedgerBal=?, OneDayFloat=?, TwoDayFloat=?, TotalFloat=?, Total_Num_Debits = ?, Total_Num_Credits = ?, NUMCREDITSMTD=?, NUMCRNOTDETAIL=?, NUMDEPSUBJFLOAT=?, NUMADJCREDITYTD=?, NUMCURDAYLBDEP=?, NUMDEBITSMTD=?, NUMTODAYTOTALDEBIT=?, NUMDEBITLESSWIRES=?, NUMADJDEBITYTD=?, NUMDEBITEXRETN=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static final String e = "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, BookValue=?, MarketValue=?, ValueDateTime=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static final String aa = "UPDATE DC_CCAcctSummary SET DCAccountID=?, DataDate=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, CardHolderName=?, CardExpDate=?, CreditLimit=?, LastPaymentAmt=?, NextPaymentMinAmt=?, NextPaymentDue=?, LastPaymentDate=?, ValueDate=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static final String Z = "UPDATE DC_LoanAcctSummary SET DCAccountID=?, DataDate=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, MaturityDate=?, AccruedInterest=?, OpeningBalance=?, CollateralDesc=?, PrinciplePastDue=?, InterestPastDue=?, LateFees=?, NextPrincipleAmt=?, NextInterestAmt=?, ValueDate=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static final String D = "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, TotalCredits=?, TotalCreditsMTD=?, CreditsNotDetailed=?, DepositsSubjFloat=?, TotalAdjCreditsYTD=?, TotalLockboxDeposits=?, TotalDebits=?, TotalDebitsMTD=?, TodayTotalDebits=?, TotalDebitLessWire=?, TotalAdjDebitsYTD=?, TotalDebitsExRetn=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, AvailableOverdraft=?, RestrictedCash=?, AccruedInterest=?, AccruedDividend=?, TotalOverdraftAmt=?, NxtOvrDrftPmtDate=?, InterestRate=?, OpeningLedger=?, ClosingLedger=?, CurrAvailBal=?, LedgerBal=?, OneDayFloat=?, TwoDayFloat=?, TotalFloat=?, Total_Num_Debits = ?, Total_Num_Credits = ?, NUMCREDITSMTD=?, NUMCRNOTDETAIL=?, NUMDEPSUBJFLOAT=?, NUMADJCREDITYTD=?, NUMCURDAYLBDEP=?, NUMDEBITSMTD=?, NUMTODAYTOTALDEBIT=?, NUMDEBITLESSWIRES=?, NUMADJDEBITYTD=?, NUMDEBITEXRETN=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static String jdField_long = "SELECT ExtendABeanXMLID FROM DC_CCAcctSummary WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static String E = "SELECT ExtendABeanXMLID FROM DC_LoanAcctSummary WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static String jdField_case = "SELECT ExtendABeanXMLID FROM DC_AccountSummary WHERE DCAccountID=? AND DataDate=? AND DataClassification=?";
  private static String v = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static String jdField_for = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static String t = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static String I = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static String r = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static String jdField_goto = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static String p = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ";
  private static String O = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ";
  private static String jdField_null = "SELECT b.CurrentLedger FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate DESC";
  private static String k = "SELECT b.CurrentLedger FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate DESC";
  private static String jdField_byte = "SELECT b.CurrentLedger FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate DESC";
  private static String S = "SELECT b.CurrentLedger FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate DESC";
  private static String jdField_new = "SELECT b.CurrentLedger FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate DESC";
  private static String m = "SELECT b.CurrentLedger FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate DESC";
  private static String jdField_int = "SELECT b.CurrentLedger FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate DESC";
  private static String T = "SELECT b.CurrentLedger FROM DC_Account a, DC_AccountHistory b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate DESC";
  private static String n = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ";
  private static String c = "SELECT b.DataDate, b.TotalCredits, b.TotalCreditsMTD, b.CreditsNotDetailed, b.DepositsSubjFloat, b.TotalAdjCreditsYTD, b.TotalLockboxDeposits, b.TotalDebits, b.TotalDebitsMTD, b.TodayTotalDebits, b.TotalDebitLessWire, b.TotalAdjDebitsYTD, b.TotalDebitsExRetn, b.ImmedAvailAmount, b.OneDayAvailAmount, b.MoreOneDayAvailAm, b.ValueDateTime, b.AvailableOverdraft, b.RestrictedCash, b.AccruedInterest, b.AccruedDividend, b.TotalOverdraftAmt, b.NxtOvrDrftPmtDate, b.InterestRate, b.ExtendABeanXMLID, b.OpeningLedger, b.ClosingLedger, b.CurrAvailBal, b.LedgerBal, b.OneDayFloat, b.TwoDayFloat, b.TotalFloat, b.Total_Num_Debits, b.Total_Num_Credits, b.NUMCREDITSMTD, b.NUMCRNOTDETAIL, b.NUMDEPSUBJFLOAT, b.NUMADJCREDITYTD, b.NUMCURDAYLBDEP, b.NUMDEBITSMTD, b.NUMTODAYTOTALDEBIT, b.NUMDEBITLESSWIRES, b.NUMADJDEBITYTD, b.NUMDEBITEXRETN, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=? AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ";
  private static final String d = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ";
  private static final String B = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ";
  private static final String b = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ";
  private static final String jdField_do = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ";
  private static final String jdField_void = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String H = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String jdField_else = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ";
  private static final String jdField_char = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ";
  private static final String jdField_try = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ";
  private static final String N = "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ";
  private static final String M = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ";
  private static final String P = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ";
  private static final String K = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String i = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String J = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String Q = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String G = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String j = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String C = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ";
  private static final String R = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ";
  private static final String x = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=?  AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String Y = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=?  AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String w = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String y = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String u = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String ab = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String s = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String z = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate";
  private static final String q = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ";
  private static final String ac = "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ";
  private static String V = "SELECT max(b.DataDate) FROM DC_Account a, ";
  private static String U = " b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID=? AND a.BankID=?";
  private static String l = "DC_AccountSummary";
  private static String F = "DC_CCAcctSummary";
  private static String jdField_if = "DC_LoanAcctSummary";
  private static String a = "DC_AccountSummary";
  
  protected static void addSummary(Account paramAccount, AccountSummary paramAccountSummary, int paramInt, Connection paramConnection, HashMap paramHashMap)
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
      if (!DCAccount.accountExists(paramAccount, paramConnection, paramHashMap)) {
        DCAccount.addAccount(paramAccount, paramConnection, paramHashMap);
      }
      int i2 = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
      if (i2 == -1) {
        throw new DCException(414, "Account not found.");
      }
      if (paramAccountSummary.getSummaryDate() == null) {
        throw new DCException(436, "The account summary record does not have date information.");
      }
      AccountSummary localAccountSummary = jdField_do(paramAccount, paramAccountSummary.getSummaryDate(), paramConnection, str3);
      Object localObject1;
      Currency localCurrency;
      DateTime localDateTime;
      if ((paramAccountSummary instanceof AssetAcctSummary))
      {
        localObject1 = null;
        if (localAccountSummary == null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_AccountSummary( DCAccountID, DataDate, DataSource, BookValue, MarketValue, ValueDateTime, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
          localObject1 = (AssetAcctSummary)paramAccountSummary;
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, DataSource=?, BookValue=?, MarketValue=?, ValueDateTime=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
          AssetAcctSummary localAssetAcctSummary = (AssetAcctSummary)paramAccountSummary;
          localObject1 = (AssetAcctSummary)localAccountSummary;
          localCurrency = null;
          localDateTime = null;
          localCurrency = localAssetAcctSummary.getBookValue();
          if (localCurrency != null) {
            ((AssetAcctSummary)localObject1).setBookValue(localCurrency);
          }
          localCurrency = localAssetAcctSummary.getMarketValue();
          if (localCurrency != null) {
            ((AssetAcctSummary)localObject1).setMarketValue(localCurrency);
          }
          localDateTime = localAssetAcctSummary.getValueDate();
          if (localDateTime != null) {
            ((AssetAcctSummary)localObject1).setValueDate(localDateTime);
          }
        }
        localPreparedStatement.setInt(1, i2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 2, ((AssetAcctSummary)localObject1).getSummaryDate());
        localPreparedStatement.setInt(3, paramInt);
        DCUtil.fillCurrencyColumn(localPreparedStatement, 4, ((AssetAcctSummary)localObject1).getBookValue());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 5, ((AssetAcctSummary)localObject1).getMarketValue());
        DCUtil.fillTimestampColumn(localPreparedStatement, 6, ((AssetAcctSummary)localObject1).getValueDate());
        if (localAccountSummary != null)
        {
          long l1 = a(paramConnection, i2, (AccountSummary)localObject1, str3);
          DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
          a((AccountSummary)localObject1, paramAccountSummary);
        }
        localPreparedStatement.setLong(7, DCExtendABeanXML.addExtendABeanXML(paramConnection, ((AssetAcctSummary)localObject1).getExtendABeanXML(), paramHashMap));
        localPreparedStatement.setString(8, null);
        localPreparedStatement.setString(9, str1);
        if (localAccountSummary == null)
        {
          localPreparedStatement.setString(10, str3);
          localPreparedStatement.setString(11, str2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 12, localCalendar);
          DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_AccountSummary( DCAccountID, DataDate, DataSource, BookValue, MarketValue, ValueDateTime, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
        }
        else
        {
          localPreparedStatement.setString(10, str2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 11, localCalendar);
          localPreparedStatement.setInt(12, i2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 13, ((AssetAcctSummary)localObject1).getSummaryDate());
          localPreparedStatement.setString(14, str3);
          DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, DataSource=?, BookValue=?, MarketValue=?, ValueDateTime=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
        }
      }
      else if ((paramAccountSummary instanceof DepositAcctSummary))
      {
        localObject1 = null;
        if (localAccountSummary == null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_AccountSummary( DCAccountID, DataDate, DataSource, TotalCredits, TotalCreditsMTD, CreditsNotDetailed,  DepositsSubjFloat, TotalAdjCreditsYTD, TotalLockboxDeposits, TotalDebits, TotalDebitsMTD, TodayTotalDebits, TotalDebitLessWire, TotalAdjDebitsYTD, TotalDebitsExRetn, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, AvailableOverdraft, RestrictedCash, AccruedInterest, AccruedDividend, TotalOverdraftAmt, NxtOvrDrftPmtDate, InterestRate,  OpeningLedger, ClosingLedger, CurrAvailBal, LedgerBal, OneDayFloat, TwoDayFloat, TotalFloat, Total_Num_Debits, Total_Num_Credits, NUMCREDITSMTD, NUMCRNOTDETAIL, NUMDEPSUBJFLOAT, NUMADJCREDITYTD, NUMCURDAYLBDEP, NUMDEBITSMTD, NUMTODAYTOTALDEBIT, NUMDEBITLESSWIRES, NUMADJDEBITYTD, NUMDEBITEXRETN, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
          localObject1 = (DepositAcctSummary)paramAccountSummary;
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalCreditsMTD=?, CreditsNotDetailed=?,  DepositsSubjFloat=?, TotalAdjCreditsYTD=?, TotalLockboxDeposits=?, TotalDebits=?, TotalDebitsMTD=?, TodayTotalDebits=?, TotalDebitLessWire=?, TotalAdjDebitsYTD=?, TotalDebitsExRetn=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, AvailableOverdraft=?, RestrictedCash=?, AccruedInterest=?, AccruedDividend=?, TotalOverdraftAmt=?, NxtOvrDrftPmtDate=?, InterestRate=?, OpeningLedger=?, ClosingLedger=?, CurrAvailBal=?, LedgerBal=?, OneDayFloat=?, TwoDayFloat=?, TotalFloat=?, Total_Num_Debits = ?, Total_Num_Credits = ?, NUMCREDITSMTD=?, NUMCRNOTDETAIL=?, NUMDEPSUBJFLOAT=?, NUMADJCREDITYTD=?, NUMCURDAYLBDEP=?, NUMDEBITSMTD=?, NUMTODAYTOTALDEBIT=?, NUMDEBITLESSWIRES=?, NUMADJDEBITYTD=?, NUMDEBITEXRETN=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
          DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)paramAccountSummary;
          localObject1 = (DepositAcctSummary)localAccountSummary;
          localCurrency = null;
          localDateTime = null;
          float f1 = -1.0F;
          localCurrency = localDepositAcctSummary.getTotalCredits();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalCredits(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalCreditAmtMTD();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalCreditAmtMTD(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getCreditsNotDetailed();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setCreditsNotDetailed(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getDepositsSubjectToFloat();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setDepositsSubjectToFloat(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalAdjCreditsYTD();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalAdjCreditsYTD(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalLockboxDeposits();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalLockboxDeposits(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalDebits();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalDebits(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalDebitAmtMTD();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalDebitAmtMTD(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTodaysTotalDebits();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTodaysTotalDebits(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalDebitsLessWireAndCharge();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalDebitsLessWireAndCharge(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalAdjDebitsYTD();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalAdjDebitsYTD(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalDebitsExcludeReturns();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalDebitsExcludeReturns(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getImmedAvailAmt();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setImmedAvailAmt(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getOneDayAvailAmt();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setOneDayAvailAmt(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getMoreThanOneDayAvailAmt();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setMoreThanOneDayAvailAmt(localCurrency);
          }
          localDateTime = localDepositAcctSummary.getValueDate();
          if (localDateTime != null) {
            ((DepositAcctSummary)localObject1).setValueDate(localDateTime);
          }
          localCurrency = localDepositAcctSummary.getAvailOverdraft();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setAvailOverdraft(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getRestrictedCash();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setRestrictedCash(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getAccruedInterest();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setAccruedInterest(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getAccruedDividend();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setAccruedDividend(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalOverdraftAmt();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalOverdraftAmt(localCurrency);
          }
          localDateTime = localDepositAcctSummary.getNextOverdraftPmtDate();
          if (localDateTime != null) {
            ((DepositAcctSummary)localObject1).setNextOverdraftPmtDate(localDateTime);
          }
          f1 = localDepositAcctSummary.getInterestRate();
          if (f1 != -1.0F) {
            ((DepositAcctSummary)localObject1).setInterestRate(f1);
          }
          localCurrency = localDepositAcctSummary.getOpeningLedger();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setOpeningLedger(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getClosingLedger();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setClosingLedger(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getLedgerBal();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setLedgerBal(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getCurrentAvailBal();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setCurrentAvailBal(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getOneDayFloat();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setOneDayFloat(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTwoDayFloat();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTwoDayFloat(localCurrency);
          }
          localCurrency = localDepositAcctSummary.getTotalFloat();
          if (localCurrency != null) {
            ((DepositAcctSummary)localObject1).setTotalFloat(localCurrency);
          }
          int i3 = localDepositAcctSummary.getTotalNumberOfDebits();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setTotalNumberOfDebits(i3);
          }
          i3 = localDepositAcctSummary.getTotalNumberOfCredits();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setTotalNumberOfCredits(i3);
          }
          i3 = localDepositAcctSummary.getNumCreditMTD();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumCreditMTD(i3);
          }
          i3 = localDepositAcctSummary.getNumCreditsNotDetailed();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumCreditsNotDetailed(i3);
          }
          i3 = localDepositAcctSummary.getNumDepositsSubjectToFloat();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumDepositsSubjectToFloat(i3);
          }
          i3 = localDepositAcctSummary.getNumAdjCreditsYTD();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumAdjCreditsYTD(i3);
          }
          i3 = localDepositAcctSummary.getNumLockboxDeposits();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumLockboxDeposits(i3);
          }
          i3 = localDepositAcctSummary.getNumDebitMTD();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumDebitMTD(i3);
          }
          i3 = localDepositAcctSummary.getNumTodaysTotalDebits();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumTodaysTotalDebits(i3);
          }
          i3 = localDepositAcctSummary.getNumDebitsLessWireAndCharge();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumDebitsLessWireAndCharge(i3);
          }
          i3 = localDepositAcctSummary.getNumAdjDebitsYTD();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumAdjDebitsYTD(i3);
          }
          i3 = localDepositAcctSummary.getNumDebitsExcludeReturns();
          if (i3 != -1) {
            ((DepositAcctSummary)localObject1).setNumDebitsExcludeReturns(i3);
          }
        }
        localPreparedStatement.setInt(1, i2);
        DCUtil.fillTimestampColumn(localPreparedStatement, 2, ((DepositAcctSummary)localObject1).getSummaryDate());
        localPreparedStatement.setInt(3, paramInt);
        DCUtil.fillCurrencyColumn(localPreparedStatement, 4, ((DepositAcctSummary)localObject1).getTotalCredits());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 5, ((DepositAcctSummary)localObject1).getTotalCreditAmtMTD());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 6, ((DepositAcctSummary)localObject1).getCreditsNotDetailed());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 7, ((DepositAcctSummary)localObject1).getDepositsSubjectToFloat());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 8, ((DepositAcctSummary)localObject1).getTotalAdjCreditsYTD());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 9, ((DepositAcctSummary)localObject1).getTotalLockboxDeposits());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 10, ((DepositAcctSummary)localObject1).getTotalDebits());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 11, ((DepositAcctSummary)localObject1).getTotalDebitAmtMTD());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 12, ((DepositAcctSummary)localObject1).getTodaysTotalDebits());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 13, ((DepositAcctSummary)localObject1).getTotalDebitsLessWireAndCharge());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 14, ((DepositAcctSummary)localObject1).getTotalAdjDebitsYTD());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 15, ((DepositAcctSummary)localObject1).getTotalDebitsExcludeReturns());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 16, ((DepositAcctSummary)localObject1).getImmedAvailAmt());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 17, ((DepositAcctSummary)localObject1).getOneDayAvailAmt());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 18, ((DepositAcctSummary)localObject1).getMoreThanOneDayAvailAmt());
        DCUtil.fillTimestampColumn(localPreparedStatement, 19, ((DepositAcctSummary)localObject1).getValueDate());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 20, ((DepositAcctSummary)localObject1).getAvailOverdraft());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 21, ((DepositAcctSummary)localObject1).getRestrictedCash());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 22, ((DepositAcctSummary)localObject1).getAccruedInterest());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 23, ((DepositAcctSummary)localObject1).getAccruedDividend());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 24, ((DepositAcctSummary)localObject1).getTotalOverdraftAmt());
        DCUtil.fillTimestampColumn(localPreparedStatement, 25, ((DepositAcctSummary)localObject1).getNextOverdraftPmtDate());
        localPreparedStatement.setFloat(26, ((DepositAcctSummary)localObject1).getInterestRate());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 27, ((DepositAcctSummary)localObject1).getOpeningLedger());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 28, ((DepositAcctSummary)localObject1).getClosingLedger());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 29, ((DepositAcctSummary)localObject1).getCurrentAvailBal());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 30, ((DepositAcctSummary)localObject1).getLedgerBal());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 31, ((DepositAcctSummary)localObject1).getOneDayFloat());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 32, ((DepositAcctSummary)localObject1).getTwoDayFloat());
        DCUtil.fillCurrencyColumn(localPreparedStatement, 33, ((DepositAcctSummary)localObject1).getTotalFloat());
        localPreparedStatement.setInt(34, ((DepositAcctSummary)localObject1).getTotalNumberOfDebits());
        localPreparedStatement.setInt(35, ((DepositAcctSummary)localObject1).getTotalNumberOfCredits());
        localPreparedStatement.setInt(36, ((DepositAcctSummary)localObject1).getNumCreditMTD());
        localPreparedStatement.setInt(37, ((DepositAcctSummary)localObject1).getNumCreditsNotDetailed());
        localPreparedStatement.setInt(38, ((DepositAcctSummary)localObject1).getNumDepositsSubjectToFloat());
        localPreparedStatement.setInt(39, ((DepositAcctSummary)localObject1).getNumAdjCreditsYTD());
        localPreparedStatement.setInt(40, ((DepositAcctSummary)localObject1).getNumLockboxDeposits());
        localPreparedStatement.setInt(41, ((DepositAcctSummary)localObject1).getNumDebitMTD());
        localPreparedStatement.setInt(42, ((DepositAcctSummary)localObject1).getNumTodaysTotalDebits());
        localPreparedStatement.setInt(43, ((DepositAcctSummary)localObject1).getNumDebitsLessWireAndCharge());
        localPreparedStatement.setInt(44, ((DepositAcctSummary)localObject1).getNumAdjDebitsYTD());
        localPreparedStatement.setInt(45, ((DepositAcctSummary)localObject1).getNumDebitsExcludeReturns());
        if (localAccountSummary != null)
        {
          long l2 = a(paramConnection, i2, (AccountSummary)localObject1, str3);
          DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l2);
          a((AccountSummary)localObject1, paramAccountSummary);
        }
        localPreparedStatement.setLong(46, DCExtendABeanXML.addExtendABeanXML(paramConnection, ((DepositAcctSummary)localObject1).getExtendABeanXML(), paramHashMap));
        localPreparedStatement.setString(47, null);
        localPreparedStatement.setString(48, str1);
        if (localAccountSummary == null)
        {
          localPreparedStatement.setString(49, str3);
          localPreparedStatement.setString(50, str2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 51, localCalendar);
          DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_AccountSummary( DCAccountID, DataDate, DataSource, TotalCredits, TotalCreditsMTD, CreditsNotDetailed,  DepositsSubjFloat, TotalAdjCreditsYTD, TotalLockboxDeposits, TotalDebits, TotalDebitsMTD, TodayTotalDebits, TotalDebitLessWire, TotalAdjDebitsYTD, TotalDebitsExRetn, ImmedAvailAmount, OneDayAvailAmount, MoreOneDayAvailAm, ValueDateTime, AvailableOverdraft, RestrictedCash, AccruedInterest, AccruedDividend, TotalOverdraftAmt, NxtOvrDrftPmtDate, InterestRate,  OpeningLedger, ClosingLedger, CurrAvailBal, LedgerBal, OneDayFloat, TwoDayFloat, TotalFloat, Total_Num_Debits, Total_Num_Credits, NUMCREDITSMTD, NUMCRNOTDETAIL, NUMDEPSUBJFLOAT, NUMADJCREDITYTD, NUMCURDAYLBDEP, NUMDEBITSMTD, NUMTODAYTOTALDEBIT, NUMDEBITLESSWIRES, NUMADJDEBITYTD, NUMDEBITEXRETN, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        else
        {
          localPreparedStatement.setString(49, str2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 50, localCalendar);
          localPreparedStatement.setInt(51, i2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 52, ((DepositAcctSummary)localObject1).getSummaryDate());
          localPreparedStatement.setString(53, str3);
          DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, DataSource=?, TotalCredits=?, TotalCreditsMTD=?, CreditsNotDetailed=?,  DepositsSubjFloat=?, TotalAdjCreditsYTD=?, TotalLockboxDeposits=?, TotalDebits=?, TotalDebitsMTD=?, TodayTotalDebits=?, TotalDebitLessWire=?, TotalAdjDebitsYTD=?, TotalDebitsExRetn=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, AvailableOverdraft=?, RestrictedCash=?, AccruedInterest=?, AccruedDividend=?, TotalOverdraftAmt=?, NxtOvrDrftPmtDate=?, InterestRate=?, OpeningLedger=?, ClosingLedger=?, CurrAvailBal=?, LedgerBal=?, OneDayFloat=?, TwoDayFloat=?, TotalFloat=?, Total_Num_Debits = ?, Total_Num_Credits = ?, NUMCREDITSMTD=?, NUMCRNOTDETAIL=?, NUMDEPSUBJFLOAT=?, NUMADJCREDITYTD=?, NUMCURDAYLBDEP=?, NUMDEBITSMTD=?, NUMTODAYTOTALDEBIT=?, NUMDEBITLESSWIRES=?, NUMADJDEBITYTD=?, NUMDEBITEXRETN=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
        }
      }
      else
      {
        String str4;
        float f2;
        if ((paramAccountSummary instanceof CreditCardAcctSummary))
        {
          localObject1 = null;
          if (localAccountSummary == null)
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_CCAcctSummary( DCAccountID, DataDate, DataSource, AvailableCredit, AmountDue, InterestRate, DueDate, CardHolderName, CardExpDate, CreditLimit, LastPaymentAmt, NextPaymentMinAmt, NextPaymentDue, LastPaymentDate, ValueDate, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            localObject1 = (CreditCardAcctSummary)paramAccountSummary;
          }
          else
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_CCAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, CardHolderName=?, CardExpDate=?, CreditLimit=?, LastPaymentAmt=?, NextPaymentMinAmt=?, NextPaymentDue=?, LastPaymentDate=?, ValueDate=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
            localObject1 = (CreditCardAcctSummary)localAccountSummary;
            CreditCardAcctSummary localCreditCardAcctSummary = (CreditCardAcctSummary)paramAccountSummary;
            localCurrency = null;
            localDateTime = null;
            str4 = null;
            f2 = -1.0F;
            localCurrency = localCreditCardAcctSummary.getAvailCredit();
            if (localCurrency != null) {
              ((CreditCardAcctSummary)localObject1).setAvailCredit(localCurrency);
            }
            localCurrency = localCreditCardAcctSummary.getAmtDue();
            if (localCurrency != null) {
              ((CreditCardAcctSummary)localObject1).setAmtDue(localCurrency);
            }
            f2 = localCreditCardAcctSummary.getInterestRate();
            if (f2 != -1.0F) {
              ((CreditCardAcctSummary)localObject1).setInterestRate(f2);
            }
            localDateTime = localCreditCardAcctSummary.getDueDate();
            if (localDateTime != null) {
              ((CreditCardAcctSummary)localObject1).setDueDate(localDateTime);
            }
            str4 = localCreditCardAcctSummary.getCardHolderName();
            if (str4 != null) {
              ((CreditCardAcctSummary)localObject1).setCardHolderName(str4);
            }
            localDateTime = localCreditCardAcctSummary.getCardExpDate();
            if (localDateTime != null) {
              ((CreditCardAcctSummary)localObject1).setCardExpDate(localDateTime);
            }
            localCurrency = localCreditCardAcctSummary.getCreditLimit();
            if (localCurrency != null) {
              ((CreditCardAcctSummary)localObject1).setCreditLimit(localCurrency);
            }
            localCurrency = localCreditCardAcctSummary.getLastPaymentAmt();
            if (localCurrency != null) {
              ((CreditCardAcctSummary)localObject1).setLastPaymentAmt(localCurrency);
            }
            localCurrency = localCreditCardAcctSummary.getNextPaymentMinAmt();
            if (localCurrency != null) {
              ((CreditCardAcctSummary)localObject1).setNextPaymentMinAmt(localCurrency);
            }
            localDateTime = localCreditCardAcctSummary.getNextPaymentDue();
            if (localDateTime != null) {
              ((CreditCardAcctSummary)localObject1).setNextPaymentDue(localDateTime);
            }
            localDateTime = localCreditCardAcctSummary.getLastPaymentDate();
            if (localDateTime != null) {
              ((CreditCardAcctSummary)localObject1).setLastPaymentDate(localDateTime);
            }
            localDateTime = localCreditCardAcctSummary.getLastPaymentDate();
            if (localDateTime != null) {
              ((CreditCardAcctSummary)localObject1).setLastPaymentDate(localDateTime);
            }
            localDateTime = localCreditCardAcctSummary.getValueDate();
            if (localDateTime != null) {
              ((CreditCardAcctSummary)localObject1).setValueDate(localDateTime);
            }
          }
          localPreparedStatement.setInt(1, i2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 2, ((CreditCardAcctSummary)localObject1).getSummaryDate());
          localPreparedStatement.setInt(3, paramInt);
          DCUtil.fillCurrencyColumn(localPreparedStatement, 4, ((CreditCardAcctSummary)localObject1).getAvailCredit());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 5, ((CreditCardAcctSummary)localObject1).getAmtDue());
          localPreparedStatement.setFloat(6, ((CreditCardAcctSummary)localObject1).getInterestRate());
          DCUtil.fillTimestampColumn(localPreparedStatement, 7, ((CreditCardAcctSummary)localObject1).getDueDate());
          localPreparedStatement.setString(8, ((CreditCardAcctSummary)localObject1).getCardHolderName());
          DCUtil.fillTimestampColumn(localPreparedStatement, 9, ((CreditCardAcctSummary)localObject1).getCardExpDate());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 10, ((CreditCardAcctSummary)localObject1).getCreditLimit());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 11, ((CreditCardAcctSummary)localObject1).getLastPaymentAmt());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 12, ((CreditCardAcctSummary)localObject1).getNextPaymentMinAmt());
          DCUtil.fillTimestampColumn(localPreparedStatement, 13, ((CreditCardAcctSummary)localObject1).getNextPaymentDue());
          DCUtil.fillTimestampColumn(localPreparedStatement, 14, ((CreditCardAcctSummary)localObject1).getLastPaymentDate());
          DCUtil.fillTimestampColumn(localPreparedStatement, 15, ((CreditCardAcctSummary)localObject1).getValueDate());
          if (localAccountSummary != null)
          {
            long l3 = a(paramConnection, i2, (AccountSummary)localObject1, str3);
            DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l3);
            a((AccountSummary)localObject1, paramAccountSummary);
          }
          localPreparedStatement.setLong(16, DCExtendABeanXML.addExtendABeanXML(paramConnection, ((CreditCardAcctSummary)localObject1).getExtendABeanXML(), paramHashMap));
          localPreparedStatement.setString(17, null);
          localPreparedStatement.setString(18, str1);
          if (localAccountSummary == null)
          {
            localPreparedStatement.setString(19, str3);
            localPreparedStatement.setString(20, str2);
            DCUtil.fillTimestampColumn(localPreparedStatement, 21, localCalendar);
            DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_CCAcctSummary( DCAccountID, DataDate, DataSource, AvailableCredit, AmountDue, InterestRate, DueDate, CardHolderName, CardExpDate, CreditLimit, LastPaymentAmt, NextPaymentMinAmt, NextPaymentDue, LastPaymentDate, ValueDate, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
          }
          else
          {
            localPreparedStatement.setString(19, str2);
            DCUtil.fillTimestampColumn(localPreparedStatement, 20, localCalendar);
            localPreparedStatement.setInt(21, i2);
            DCUtil.fillTimestampColumn(localPreparedStatement, 22, ((CreditCardAcctSummary)localObject1).getSummaryDate());
            localPreparedStatement.setString(23, str3);
            DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_CCAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, CardHolderName=?, CardExpDate=?, CreditLimit=?, LastPaymentAmt=?, NextPaymentMinAmt=?, NextPaymentDue=?, LastPaymentDate=?, ValueDate=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
          }
        }
        else if ((paramAccountSummary instanceof LoanAcctSummary))
        {
          localObject1 = null;
          if (localAccountSummary == null)
          {
            localObject1 = (LoanAcctSummary)paramAccountSummary;
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_LoanAcctSummary( DCAccountID, DataDate, DataSource, AvailableCredit, AmountDue, InterestRate, DueDate, MaturityDate, AccruedInterest, OpeningBalance, CollateralDesc, PrinciplePastDue, InterestPastDue, LateFees, NextPrincipleAmt, NextInterestAmt, ValueDate, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ? )");
          }
          else
          {
            localObject1 = (LoanAcctSummary)localAccountSummary;
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_LoanAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, MaturityDate=?, AccruedInterest=?, OpeningBalance=?, CollateralDesc=?, PrinciplePastDue=?, InterestPastDue=?, LateFees=?, NextPrincipleAmt=?, NextInterestAmt=?, ValueDate=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
            LoanAcctSummary localLoanAcctSummary = (LoanAcctSummary)paramAccountSummary;
            localCurrency = null;
            localDateTime = null;
            str4 = null;
            f2 = -1.0F;
            localCurrency = localLoanAcctSummary.getAvailCredit();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setAvailCredit(localCurrency);
            }
            localCurrency = localLoanAcctSummary.getAmtDue();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setAmtDue(localCurrency);
            }
            f2 = localLoanAcctSummary.getInterestRate();
            if (f2 != -1.0F) {
              ((LoanAcctSummary)localObject1).setInterestRate(f2);
            }
            localDateTime = localLoanAcctSummary.getDueDate();
            if (localDateTime != null) {
              ((LoanAcctSummary)localObject1).setDueDate(localDateTime);
            }
            localDateTime = localLoanAcctSummary.getMaturityDate();
            if (localDateTime != null) {
              ((LoanAcctSummary)localObject1).setMaturityDate(localDateTime);
            }
            localCurrency = localLoanAcctSummary.getAccruedInterest();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setAccruedInterest(localCurrency);
            }
            localCurrency = localLoanAcctSummary.getOpeningBal();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setOpeningBal(localCurrency);
            }
            str4 = localLoanAcctSummary.getCollateralDescription();
            if (str4 != null) {
              ((LoanAcctSummary)localObject1).setCollateralDescription(str4);
            }
            localCurrency = localLoanAcctSummary.getPrincipalPastDue();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setPrincipalPastDue(localCurrency);
            }
            localCurrency = localLoanAcctSummary.getInterestPastDue();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setInterestPastDue(localCurrency);
            }
            localCurrency = localLoanAcctSummary.getLateFees();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setLateFees(localCurrency);
            }
            localCurrency = localLoanAcctSummary.getNextPrincipalAmt();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setNextPrincipalAmt(localCurrency);
            }
            localCurrency = localLoanAcctSummary.getNextInterestAmt();
            if (localCurrency != null) {
              ((LoanAcctSummary)localObject1).setNextInterestAmt(localCurrency);
            }
            localDateTime = localLoanAcctSummary.getValueDate();
            if (localDateTime != null) {
              ((LoanAcctSummary)localObject1).setValueDate(localDateTime);
            }
          }
          localPreparedStatement.setInt(1, i2);
          DCUtil.fillTimestampColumn(localPreparedStatement, 2, ((LoanAcctSummary)localObject1).getSummaryDate());
          localPreparedStatement.setInt(3, paramInt);
          DCUtil.fillCurrencyColumn(localPreparedStatement, 4, ((LoanAcctSummary)localObject1).getAvailCredit());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 5, ((LoanAcctSummary)localObject1).getAmtDue());
          localPreparedStatement.setFloat(6, ((LoanAcctSummary)localObject1).getInterestRate());
          DCUtil.fillTimestampColumn(localPreparedStatement, 7, ((LoanAcctSummary)localObject1).getDueDate());
          DCUtil.fillTimestampColumn(localPreparedStatement, 8, ((LoanAcctSummary)localObject1).getMaturityDate());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 9, ((LoanAcctSummary)localObject1).getAccruedInterest());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 10, ((LoanAcctSummary)localObject1).getOpeningBal());
          localPreparedStatement.setString(11, ((LoanAcctSummary)localObject1).getCollateralDescription());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 12, ((LoanAcctSummary)localObject1).getPrincipalPastDue());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 13, ((LoanAcctSummary)localObject1).getInterestPastDue());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 14, ((LoanAcctSummary)localObject1).getLateFees());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 15, ((LoanAcctSummary)localObject1).getNextPrincipalAmt());
          DCUtil.fillCurrencyColumn(localPreparedStatement, 16, ((LoanAcctSummary)localObject1).getNextInterestAmt());
          DCUtil.fillTimestampColumn(localPreparedStatement, 17, ((LoanAcctSummary)localObject1).getValueDate());
          if (localAccountSummary != null)
          {
            long l4 = a(paramConnection, i2, (AccountSummary)localObject1, str3);
            DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l4);
            a((AccountSummary)localObject1, paramAccountSummary);
          }
          localPreparedStatement.setLong(18, DCExtendABeanXML.addExtendABeanXML(paramConnection, ((LoanAcctSummary)localObject1).getExtendABeanXML(), paramHashMap));
          localPreparedStatement.setString(19, null);
          localPreparedStatement.setString(20, str1);
          if (localAccountSummary == null)
          {
            localPreparedStatement.setString(21, str3);
            localPreparedStatement.setString(22, str2);
            DCUtil.fillTimestampColumn(localPreparedStatement, 23, localCalendar);
            DBUtil.executeUpdate(localPreparedStatement, "INSERT INTO DC_LoanAcctSummary( DCAccountID, DataDate, DataSource, AvailableCredit, AmountDue, InterestRate, DueDate, MaturityDate, AccruedInterest, OpeningBalance, CollateralDesc, PrinciplePastDue, InterestPastDue, LateFees, NextPrincipleAmt, NextInterestAmt, ValueDate, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification, DataSourceFileName, DataSourceFileDate ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ? )");
          }
          else
          {
            localPreparedStatement.setString(21, str2);
            DCUtil.fillTimestampColumn(localPreparedStatement, 22, localCalendar);
            localPreparedStatement.setInt(23, i2);
            DCUtil.fillTimestampColumn(localPreparedStatement, 24, ((LoanAcctSummary)localObject1).getSummaryDate());
            localPreparedStatement.setString(25, str3);
            DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_LoanAcctSummary SET DCAccountID=?, DataDate=?, DataSource=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, MaturityDate=?, AccruedInterest=?, OpeningBalance=?, CollateralDesc=?, PrinciplePastDue=?, InterestPastDue=?, LateFees=?, NextPrincipleAmt=?, NextInterestAmt=?, ValueDate=?, ExtendABeanXMLID=?, Extra=?, BAIFileIdentifier=?, DataSourceFileName = ?, DataSourceFileDate = ? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
          }
        }
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 437;
      throw new DCException(i1, "Failed to add account summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static void a(Connection paramConnection, Account paramAccount, LoanAcctSummary paramLoanAcctSummary, int paramInt, String paramString, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_LoanAcctSummary SET DCAccountID=?, DataDate=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, MaturityDate=?, AccruedInterest=?, OpeningBalance=?, CollateralDesc=?, PrinciplePastDue=?, InterestPastDue=?, LateFees=?, NextPrincipleAmt=?, NextInterestAmt=?, ValueDate=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramLoanAcctSummary.getSummaryDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 3, paramLoanAcctSummary.getAvailCredit());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, paramLoanAcctSummary.getAmtDue());
      localPreparedStatement.setFloat(5, paramLoanAcctSummary.getInterestRate());
      DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramLoanAcctSummary.getDueDate());
      DCUtil.fillTimestampColumn(localPreparedStatement, 7, paramLoanAcctSummary.getMaturityDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, paramLoanAcctSummary.getAccruedInterest());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, paramLoanAcctSummary.getOpeningBal());
      localPreparedStatement.setString(10, paramLoanAcctSummary.getCollateralDescription());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 11, paramLoanAcctSummary.getPrincipalPastDue());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 12, paramLoanAcctSummary.getInterestPastDue());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 13, paramLoanAcctSummary.getLateFees());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 14, paramLoanAcctSummary.getNextPrincipalAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 15, paramLoanAcctSummary.getNextInterestAmt());
      DCUtil.fillTimestampColumn(localPreparedStatement, 16, paramLoanAcctSummary.getValueDate());
      long l1 = a(paramConnection, paramInt, paramLoanAcctSummary, paramString);
      DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
      localPreparedStatement.setLong(17, DCExtendABeanXML.addExtendABeanXML(paramConnection, paramLoanAcctSummary.getExtendABeanXML(), paramHashMap));
      localPreparedStatement.setString(18, null);
      localPreparedStatement.setInt(19, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 20, paramLoanAcctSummary.getSummaryDate());
      localPreparedStatement.setString(21, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_LoanAcctSummary SET DCAccountID=?, DataDate=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, MaturityDate=?, AccruedInterest=?, OpeningBalance=?, CollateralDesc=?, PrinciplePastDue=?, InterestPastDue=?, LateFees=?, NextPrincipleAmt=?, NextInterestAmt=?, ValueDate=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static void a(Connection paramConnection, Account paramAccount, AssetAcctSummary paramAssetAcctSummary, int paramInt, String paramString, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, BookValue=?, MarketValue=?, ValueDateTime=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramAssetAcctSummary.getSummaryDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 3, paramAssetAcctSummary.getBookValue());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, paramAssetAcctSummary.getMarketValue());
      DCUtil.fillTimestampColumn(localPreparedStatement, 5, paramAssetAcctSummary.getValueDate());
      long l1 = a(paramConnection, paramInt, paramAssetAcctSummary, paramString);
      DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
      localPreparedStatement.setLong(6, DCExtendABeanXML.addExtendABeanXML(paramConnection, paramAssetAcctSummary.getExtendABeanXML(), paramHashMap));
      localPreparedStatement.setString(7, null);
      localPreparedStatement.setInt(8, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 9, paramAssetAcctSummary.getSummaryDate());
      localPreparedStatement.setString(10, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, BookValue=?, MarketValue=?, ValueDateTime=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static void a(Connection paramConnection, Account paramAccount, CreditCardAcctSummary paramCreditCardAcctSummary, int paramInt, String paramString, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_CCAcctSummary SET DCAccountID=?, DataDate=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, CardHolderName=?, CardExpDate=?, CreditLimit=?, LastPaymentAmt=?, NextPaymentMinAmt=?, NextPaymentDue=?, LastPaymentDate=?, ValueDate=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramCreditCardAcctSummary.getSummaryDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 3, paramCreditCardAcctSummary.getAvailCredit());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, paramCreditCardAcctSummary.getAmtDue());
      localPreparedStatement.setFloat(5, paramCreditCardAcctSummary.getInterestRate());
      DCUtil.fillTimestampColumn(localPreparedStatement, 6, paramCreditCardAcctSummary.getDueDate());
      localPreparedStatement.setString(7, paramCreditCardAcctSummary.getCardHolderName());
      DCUtil.fillTimestampColumn(localPreparedStatement, 8, paramCreditCardAcctSummary.getCardExpDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, paramCreditCardAcctSummary.getCreditLimit());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 10, paramCreditCardAcctSummary.getLastPaymentAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 11, paramCreditCardAcctSummary.getNextPaymentMinAmt());
      DCUtil.fillTimestampColumn(localPreparedStatement, 12, paramCreditCardAcctSummary.getNextPaymentDue());
      DCUtil.fillTimestampColumn(localPreparedStatement, 13, paramCreditCardAcctSummary.getLastPaymentDate());
      DCUtil.fillTimestampColumn(localPreparedStatement, 14, paramCreditCardAcctSummary.getValueDate());
      long l1 = a(paramConnection, paramInt, paramCreditCardAcctSummary, paramString);
      DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
      localPreparedStatement.setLong(15, DCExtendABeanXML.addExtendABeanXML(paramConnection, paramCreditCardAcctSummary.getExtendABeanXML(), paramHashMap));
      localPreparedStatement.setString(16, null);
      localPreparedStatement.setInt(17, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 18, paramCreditCardAcctSummary.getSummaryDate());
      localPreparedStatement.setString(19, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_CCAcctSummary SET DCAccountID=?, DataDate=?, AvailableCredit=?, AmountDue=?, InterestRate=?, DueDate=?, CardHolderName=?, CardExpDate=?, CreditLimit=?, LastPaymentAmt=?, NextPaymentMinAmt=?, NextPaymentDue=?, LastPaymentDate=?, ValueDate=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static void a(Connection paramConnection, Account paramAccount, DepositAcctSummary paramDepositAcctSummary, int paramInt, String paramString, HashMap paramHashMap)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DCAdapter.getStatement(paramConnection, "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, TotalCredits=?, TotalCreditsMTD=?, CreditsNotDetailed=?, DepositsSubjFloat=?, TotalAdjCreditsYTD=?, TotalLockboxDeposits=?, TotalDebits=?, TotalDebitsMTD=?, TodayTotalDebits=?, TotalDebitLessWire=?, TotalAdjDebitsYTD=?, TotalDebitsExRetn=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, AvailableOverdraft=?, RestrictedCash=?, AccruedInterest=?, AccruedDividend=?, TotalOverdraftAmt=?, NxtOvrDrftPmtDate=?, InterestRate=?, OpeningLedger=?, ClosingLedger=?, CurrAvailBal=?, LedgerBal=?, OneDayFloat=?, TwoDayFloat=?, TotalFloat=?, Total_Num_Debits = ?, Total_Num_Credits = ?, NUMCREDITSMTD=?, NUMCRNOTDETAIL=?, NUMDEPSUBJFLOAT=?, NUMADJCREDITYTD=?, NUMCURDAYLBDEP=?, NUMDEBITSMTD=?, NUMTODAYTOTALDEBIT=?, NUMDEBITLESSWIRES=?, NUMADJDEBITYTD=?, NUMDEBITEXRETN=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, paramDepositAcctSummary.getSummaryDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 3, paramDepositAcctSummary.getTotalCredits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 4, paramDepositAcctSummary.getTotalCreditAmtMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 5, paramDepositAcctSummary.getCreditsNotDetailed());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 6, paramDepositAcctSummary.getDepositsSubjectToFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 7, paramDepositAcctSummary.getTotalAdjCreditsYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 8, paramDepositAcctSummary.getTotalLockboxDeposits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 9, paramDepositAcctSummary.getTotalDebits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 10, paramDepositAcctSummary.getTotalDebitAmtMTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 11, paramDepositAcctSummary.getTodaysTotalDebits());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 12, paramDepositAcctSummary.getTotalDebitsLessWireAndCharge());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 13, paramDepositAcctSummary.getTotalAdjDebitsYTD());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 14, paramDepositAcctSummary.getTotalDebitsExcludeReturns());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 15, paramDepositAcctSummary.getImmedAvailAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 16, paramDepositAcctSummary.getOneDayAvailAmt());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 17, paramDepositAcctSummary.getMoreThanOneDayAvailAmt());
      DCUtil.fillTimestampColumn(localPreparedStatement, 18, paramDepositAcctSummary.getValueDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 19, paramDepositAcctSummary.getAvailOverdraft());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 20, paramDepositAcctSummary.getRestrictedCash());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 21, paramDepositAcctSummary.getAccruedInterest());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 22, paramDepositAcctSummary.getAccruedDividend());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 23, paramDepositAcctSummary.getTotalOverdraftAmt());
      DCUtil.fillTimestampColumn(localPreparedStatement, 24, paramDepositAcctSummary.getNextOverdraftPmtDate());
      localPreparedStatement.setFloat(25, paramDepositAcctSummary.getInterestRate());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 26, paramDepositAcctSummary.getOpeningLedger());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 27, paramDepositAcctSummary.getClosingLedger());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 28, paramDepositAcctSummary.getCurrentAvailBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 29, paramDepositAcctSummary.getLedgerBal());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 30, paramDepositAcctSummary.getOneDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 31, paramDepositAcctSummary.getTwoDayFloat());
      DCUtil.fillCurrencyColumn(localPreparedStatement, 32, paramDepositAcctSummary.getTotalFloat());
      localPreparedStatement.setInt(33, paramDepositAcctSummary.getTotalNumberOfDebits());
      localPreparedStatement.setInt(34, paramDepositAcctSummary.getTotalNumberOfCredits());
      localPreparedStatement.setInt(35, paramDepositAcctSummary.getNumCreditMTD());
      localPreparedStatement.setInt(36, paramDepositAcctSummary.getNumCreditsNotDetailed());
      localPreparedStatement.setInt(37, paramDepositAcctSummary.getNumDepositsSubjectToFloat());
      localPreparedStatement.setInt(38, paramDepositAcctSummary.getNumAdjCreditsYTD());
      localPreparedStatement.setInt(39, paramDepositAcctSummary.getNumLockboxDeposits());
      localPreparedStatement.setInt(40, paramDepositAcctSummary.getNumDebitMTD());
      localPreparedStatement.setInt(41, paramDepositAcctSummary.getNumTodaysTotalDebits());
      localPreparedStatement.setInt(42, paramDepositAcctSummary.getNumDebitsLessWireAndCharge());
      localPreparedStatement.setInt(43, paramDepositAcctSummary.getNumAdjDebitsYTD());
      localPreparedStatement.setInt(44, paramDepositAcctSummary.getNumDebitsExcludeReturns());
      long l1 = a(paramConnection, paramInt, paramDepositAcctSummary, paramString);
      DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
      localPreparedStatement.setLong(45, DCExtendABeanXML.addExtendABeanXML(paramConnection, paramDepositAcctSummary.getExtendABeanXML(), paramHashMap));
      localPreparedStatement.setString(46, null);
      localPreparedStatement.setInt(47, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 48, paramDepositAcctSummary.getSummaryDate());
      localPreparedStatement.setString(49, paramString);
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE DC_AccountSummary SET DCAccountID=?, DataDate=?, TotalCredits=?, TotalCreditsMTD=?, CreditsNotDetailed=?, DepositsSubjFloat=?, TotalAdjCreditsYTD=?, TotalLockboxDeposits=?, TotalDebits=?, TotalDebitsMTD=?, TodayTotalDebits=?, TotalDebitLessWire=?, TotalAdjDebitsYTD=?, TotalDebitsExRetn=?, ImmedAvailAmount=?, OneDayAvailAmount=?, MoreOneDayAvailAm=?, ValueDateTime=?, AvailableOverdraft=?, RestrictedCash=?, AccruedInterest=?, AccruedDividend=?, TotalOverdraftAmt=?, NxtOvrDrftPmtDate=?, InterestRate=?, OpeningLedger=?, ClosingLedger=?, CurrAvailBal=?, LedgerBal=?, OneDayFloat=?, TwoDayFloat=?, TotalFloat=?, Total_Num_Debits = ?, Total_Num_Credits = ?, NUMCREDITSMTD=?, NUMCRNOTDETAIL=?, NUMDEPSUBJFLOAT=?, NUMADJCREDITYTD=?, NUMCURDAYLBDEP=?, NUMDEBITSMTD=?, NUMTODAYTOTALDEBIT=?, NUMDEBITLESSWIRES=?, NUMADJDEBITYTD=?, NUMDEBITEXRETN=?, ExtendABeanXMLID=?, Extra=? WHERE DCAccountID=? AND DataDate=? AND DataClassification=?");
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  protected static void updateSummary(Connection paramConnection, Account paramAccount, AccountSummary paramAccountSummary, HashMap paramHashMap)
    throws DCException
  {
    DCUtil.isAccountInfoSufficient(paramAccount);
    try
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      i1 = DCAdapter.getDCAccountID(paramConnection, paramAccount.getID(), paramAccount.getBankID(), paramAccount.getRoutingNum());
      if (i1 == -1) {
        throw new DCException(414, "Account not found.");
      }
      AccountSummary localAccountSummary = jdField_do(paramAccount, paramAccountSummary.getSummaryDate(), paramConnection, str);
      if (localAccountSummary == null) {
        throw new DCException(456, "No account summary was found to update for the date " + paramAccountSummary.getSummaryDate().getTime().toString() + " , and account with accoutid=" + paramAccount.getID() + ", bankid= " + paramAccount.getBankID() + ", routing number=" + paramAccount.getRoutingNum() + ", and data classification '" + str + "'.");
      }
      if ((paramAccountSummary instanceof AssetAcctSummary)) {
        a(paramConnection, paramAccount, (AssetAcctSummary)paramAccountSummary, i1, str, paramHashMap);
      } else if ((paramAccountSummary instanceof DepositAcctSummary)) {
        a(paramConnection, paramAccount, (DepositAcctSummary)paramAccountSummary, i1, str, paramHashMap);
      } else if ((paramAccountSummary instanceof CreditCardAcctSummary)) {
        a(paramConnection, paramAccount, (CreditCardAcctSummary)paramAccountSummary, i1, str, paramHashMap);
      } else if ((paramAccountSummary instanceof LoanAcctSummary)) {
        a(paramConnection, paramAccount, (LoanAcctSummary)paramAccountSummary, i1, str, paramHashMap);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 456;
      throw new DCException(i1, "Failed to update account summary.", localException);
    }
  }
  
  private static long a(Connection paramConnection, int paramInt, AccountSummary paramAccountSummary, String paramString)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    DateTime localDateTime = null;
    String str = null;
    long l1;
    try
    {
      localDateTime = paramAccountSummary.getSummaryDate();
      if ((paramAccountSummary instanceof CreditCardAcctSummary)) {
        str = jdField_long;
      } else if ((paramAccountSummary instanceof LoanAcctSummary)) {
        str = E;
      } else {
        str = jdField_case;
      }
      localPreparedStatement = DCAdapter.getStatement(paramConnection, str);
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, localDateTime);
      localPreparedStatement.setString(3, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str);
      if (localResultSet.next())
      {
        l1 = localResultSet.getLong(1);
      }
      else
      {
        if ((paramAccountSummary instanceof CreditCardAcctSummary)) {
          throw new DCException(434, "Error retrieving EXTENDABEANXMLID: No records in DC_CCACCTSUMMARY table with DCACCOUNTID " + paramInt + " and DATADATE " + localDateTime);
        }
        if ((paramAccountSummary instanceof LoanAcctSummary)) {
          throw new DCException(434, "Error retrieving EXTENDABEANXMLID: No records in DC_LOANACCTSUMMARY table with DCACCOUNTID " + paramInt + " and DATADATE " + localDateTime);
        }
        throw new DCException(434, "Error retrieving EXTENDABEANXMLID: No records in DC_ACCOUNTSUMMARY table with DCACCOUNTID " + paramInt + " and DATADATE " + localDateTime);
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 434;
      throw new DCException(i1, "Failed to get bean xml ID.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
    return l1;
  }
  
  private static void a(AccountSummary paramAccountSummary1, AccountSummary paramAccountSummary2)
  {
    if ((paramAccountSummary1 != null) && (paramAccountSummary2 != null)) {
      paramAccountSummary1.putAll(paramAccountSummary2);
    }
  }
  
  private static AccountSummaries jdField_goto(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    AccountSummaries localAccountSummaries = new AccountSummaries();
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_case(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      while (localResultSet.next())
      {
        localObject1 = a(paramAccount, localResultSet, paramConnection);
        localAccountSummaries.add(localObject1);
      }
      Object localObject1 = localAccountSummaries;
      return localObject1;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "SQLException occured when reading deposit summary result set", localSQLException);
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
  
  private static AccountSummary jdField_int(Account paramAccount, Calendar paramCalendar, Connection paramConnection, String paramString)
    throws DCException
  {
    DepositAcctSummary localDepositAcctSummary1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = paramAccount.getRoutingNum();
    try
    {
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, n);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramAccount.getRoutingNum());
        localPreparedStatement.setString(5, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, n);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, c);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, c);
      }
      if (localResultSet.next()) {
        localDepositAcctSummary1 = a(paramAccount, localResultSet, paramConnection);
      }
      DepositAcctSummary localDepositAcctSummary2 = localDepositAcctSummary1;
      return localDepositAcctSummary2;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 438;
      throw new DCException(i1, "Failed to get deposit summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static DepositAcctSummary a(Account paramAccount, ResultSet paramResultSet, Connection paramConnection)
    throws DCException
  {
    try
    {
      DepositAcctSummary localDepositAcctSummary = new DepositAcctSummary();
      localDepositAcctSummary.setAccountID(paramAccount.getID());
      localDepositAcctSummary.setAccountNumber(paramAccount.getNumber());
      localDepositAcctSummary.setBankID(paramAccount.getBankID());
      localDepositAcctSummary.setRoutingNumber(paramAccount.getRoutingNum());
      localDepositAcctSummary.setSummaryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalCredits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalCreditAmtMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setCreditsNotDetailed(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setDepositsSubjectToFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalAdjCreditsYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalLockboxDeposits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalDebitAmtMTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTodaysTotalDebits(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalDebitsLessWireAndCharge(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalAdjDebitsYTD(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalDebitsExcludeReturns(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setImmedAvailAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setOneDayAvailAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(15), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setMoreThanOneDayAvailAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(16), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setValueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(17), paramAccount.getLocale()));
      localDepositAcctSummary.setAvailOverdraft(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(18), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setRestrictedCash(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(19), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setAccruedInterest(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(20), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setAccruedDividend(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(21), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalOverdraftAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(22), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setNextOverdraftPmtDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(23), paramAccount.getLocale()));
      float f1 = paramResultSet.getFloat(24);
      if ((!paramResultSet.wasNull()) && (f1 >= 0.0D)) {
        localDepositAcctSummary.setInterestRate(f1);
      }
      DCUtil.fillExtendABean(paramConnection, localDepositAcctSummary, paramResultSet, 25);
      localDepositAcctSummary.setOpeningLedger(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(26), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setClosingLedger(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(27), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setCurrentAvailBal(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(28), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setLedgerBal(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(29), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setOneDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(30), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTwoDayFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(31), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalFloat(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(32), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localDepositAcctSummary.setTotalNumberOfDebits(paramResultSet.getInt(33));
      localDepositAcctSummary.setTotalNumberOfCredits(paramResultSet.getInt(34));
      localDepositAcctSummary.setNumCreditMTD(paramResultSet.getInt(35));
      localDepositAcctSummary.setNumCreditsNotDetailed(paramResultSet.getInt(36));
      localDepositAcctSummary.setNumDepositsSubjectToFloat(paramResultSet.getInt(37));
      localDepositAcctSummary.setNumAdjCreditsYTD(paramResultSet.getInt(38));
      localDepositAcctSummary.setNumLockboxDeposits(paramResultSet.getInt(39));
      localDepositAcctSummary.setNumDebitMTD(paramResultSet.getInt(40));
      localDepositAcctSummary.setNumTodaysTotalDebits(paramResultSet.getInt(41));
      localDepositAcctSummary.setNumDebitsLessWireAndCharge(paramResultSet.getInt(42));
      localDepositAcctSummary.setNumAdjDebitsYTD(paramResultSet.getInt(43));
      localDepositAcctSummary.setNumDebitsExcludeReturns(paramResultSet.getInt(44));
      return localDepositAcctSummary;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 438;
      throw new DCException(i1, "Failed to get deposit summary.", localException);
    }
  }
  
  private static AccountSummaries a(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    AccountSummaries localAccountSummaries = new AccountSummaries();
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_try(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      while (localResultSet.next())
      {
        localObject1 = jdField_do(paramAccount, localResultSet, paramConnection);
        localAccountSummaries.add(localObject1);
      }
      Object localObject1 = localAccountSummaries;
      return localObject1;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "SQLException occured when reading asset summary result set", localSQLException);
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
  
  private static AccountSummary jdField_for(Account paramAccount, Calendar paramCalendar, Connection paramConnection, String paramString)
    throws DCException
  {
    AssetAcctSummary localAssetAcctSummary1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = paramAccount.getRoutingNum();
    try
    {
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, str);
        localPreparedStatement.setString(5, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ");
      }
      if (localResultSet.next()) {
        localAssetAcctSummary1 = jdField_do(paramAccount, localResultSet, paramConnection);
      }
      AssetAcctSummary localAssetAcctSummary2 = localAssetAcctSummary1;
      return localAssetAcctSummary2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 439;
      throw new DCException(i1, "Failed to get asset summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static AssetAcctSummary jdField_do(Account paramAccount, ResultSet paramResultSet, Connection paramConnection)
    throws DCException
  {
    try
    {
      AssetAcctSummary localAssetAcctSummary = new AssetAcctSummary();
      localAssetAcctSummary.setAccountID(paramAccount.getID());
      localAssetAcctSummary.setAccountNumber(paramAccount.getNumber());
      localAssetAcctSummary.setBankID(paramAccount.getBankID());
      localAssetAcctSummary.setRoutingNumber(paramAccount.getRoutingNum());
      localAssetAcctSummary.setSummaryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale()));
      localAssetAcctSummary.setBookValue(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localAssetAcctSummary.setMarketValue(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      DCUtil.fillExtendABean(paramConnection, localAssetAcctSummary, paramResultSet, 4);
      localAssetAcctSummary.setValueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(5), paramAccount.getLocale()));
      return localAssetAcctSummary;
    }
    catch (SQLException localSQLException)
    {
      i1 = 302;
      throw new DCException(i1, "Failed to read asset summary database record.", localSQLException);
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 439;
      throw new DCException(i1, "Failed to read asset summary database record.", localException);
    }
  }
  
  private static AccountSummaries jdField_for(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    AccountSummaries localAccountSummaries = new AccountSummaries();
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_new(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      while (localResultSet.next())
      {
        localObject1 = jdField_if(paramAccount, localResultSet, paramConnection);
        localAccountSummaries.add(localObject1);
      }
      Object localObject1 = localAccountSummaries;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 440;
      throw new DCException(i1, "Failed to get creditcard summary.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static AccountSummary a(Account paramAccount, Calendar paramCalendar, Connection paramConnection, String paramString)
    throws DCException
  {
    CreditCardAcctSummary localCreditCardAcctSummary1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = paramAccount.getRoutingNum();
    try
    {
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, str);
        localPreparedStatement.setString(5, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ");
      }
      if (localResultSet.next()) {
        localCreditCardAcctSummary1 = jdField_if(paramAccount, localResultSet, paramConnection);
      }
      CreditCardAcctSummary localCreditCardAcctSummary2 = localCreditCardAcctSummary1;
      return localCreditCardAcctSummary2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 440;
      throw new DCException(i1, "Failed to get creditcard summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static CreditCardAcctSummary jdField_if(Account paramAccount, ResultSet paramResultSet, Connection paramConnection)
    throws DCException
  {
    try
    {
      CreditCardAcctSummary localCreditCardAcctSummary = new CreditCardAcctSummary();
      localCreditCardAcctSummary.setAccountID(paramAccount.getID());
      localCreditCardAcctSummary.setAccountNumber(paramAccount.getNumber());
      localCreditCardAcctSummary.setBankID(paramAccount.getBankID());
      localCreditCardAcctSummary.setRoutingNumber(paramAccount.getRoutingNum());
      localCreditCardAcctSummary.setSummaryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale()));
      localCreditCardAcctSummary.setAvailCredit(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localCreditCardAcctSummary.setAmtDue(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      float f1 = paramResultSet.getFloat(4);
      if ((!paramResultSet.wasNull()) && (f1 >= 0.0D)) {
        localCreditCardAcctSummary.setInterestRate(f1);
      }
      localCreditCardAcctSummary.setDueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(5), paramAccount.getLocale()));
      localCreditCardAcctSummary.setCardHolderName(paramResultSet.getString(6));
      localCreditCardAcctSummary.setCardExpDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(7), paramAccount.getLocale()));
      localCreditCardAcctSummary.setCreditLimit(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localCreditCardAcctSummary.setLastPaymentAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localCreditCardAcctSummary.setNextPaymentMinAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localCreditCardAcctSummary.setNextPaymentDue(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(11), paramAccount.getLocale()));
      localCreditCardAcctSummary.setLastPaymentDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(12), paramAccount.getLocale()));
      DCUtil.fillExtendABean(paramConnection, localCreditCardAcctSummary, paramResultSet, 13);
      localCreditCardAcctSummary.setValueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(14), paramAccount.getLocale()));
      return localCreditCardAcctSummary;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 440;
      throw new DCException(i1, "Failed to get creditcard summary.", localException);
    }
  }
  
  private static AccountSummaries jdField_null(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    AccountSummaries localAccountSummaries = new AccountSummaries();
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_byte(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      while (localResultSet.next())
      {
        localObject1 = jdField_for(paramAccount, localResultSet, paramConnection);
        localAccountSummaries.add(localObject1);
      }
      Object localObject1 = localAccountSummaries;
      return localObject1;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 441;
      throw new DCException(i1, "Failed to get loan account summary.", localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static AccountSummary jdField_if(Account paramAccount, Calendar paramCalendar, Connection paramConnection, String paramString)
    throws DCException
  {
    LoanAcctSummary localLoanAcctSummary1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = paramAccount.getRoutingNum();
    try
    {
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, str);
        localPreparedStatement.setString(5, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate=? AND a.RoutingNumber=? AND b.DataClassification=? ");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar);
        localPreparedStatement.setString(4, paramString);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ");
      }
      if (localResultSet.next()) {
        localLoanAcctSummary1 = jdField_for(paramAccount, localResultSet, paramConnection);
      }
      LoanAcctSummary localLoanAcctSummary2 = localLoanAcctSummary1;
      return localLoanAcctSummary2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 441;
      throw new DCException(i1, "Failed to get loan account summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static LoanAcctSummary jdField_for(Account paramAccount, ResultSet paramResultSet, Connection paramConnection)
    throws Exception
  {
    try
    {
      LoanAcctSummary localLoanAcctSummary = new LoanAcctSummary();
      localLoanAcctSummary.setAccountID(paramAccount.getID());
      localLoanAcctSummary.setAccountNumber(paramAccount.getNumber());
      localLoanAcctSummary.setBankID(paramAccount.getBankID());
      localLoanAcctSummary.setRoutingNumber(paramAccount.getRoutingNum());
      localLoanAcctSummary.setSummaryDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale()));
      localLoanAcctSummary.setAvailCredit(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localLoanAcctSummary.setAmtDue(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      float f1 = paramResultSet.getFloat(4);
      if ((!paramResultSet.wasNull()) && (f1 >= 0.0D)) {
        localLoanAcctSummary.setInterestRate(f1);
      }
      localLoanAcctSummary.setDueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(5), paramAccount.getLocale()));
      localLoanAcctSummary.setMaturityDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(6), paramAccount.getLocale()));
      localLoanAcctSummary.setAccruedInterest(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localLoanAcctSummary.setOpeningBal(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localLoanAcctSummary.setCollateralDescription(paramResultSet.getString(9));
      localLoanAcctSummary.setPrincipalPastDue(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localLoanAcctSummary.setInterestPastDue(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localLoanAcctSummary.setLateFees(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localLoanAcctSummary.setNextPrincipalAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localLoanAcctSummary.setNextInterestAmt(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      DCUtil.fillExtendABean(paramConnection, localLoanAcctSummary, paramResultSet, 15);
      localLoanAcctSummary.setValueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(16), paramAccount.getLocale()));
      return localLoanAcctSummary;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 441;
      throw new DCException(i1, "Failed to get loan account summary.", localException);
    }
  }
  
  protected static AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    try
    {
      int i1 = paramAccount.getAccountGroup();
      int i2 = Account.getAccountSystemTypeFromGroup(i1);
      AccountSummaries localAccountSummaries = null;
      switch (i2)
      {
      case 2: 
        localAccountSummaries = a(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
        break;
      case 4: 
        localAccountSummaries = jdField_for(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
        break;
      case 3: 
        localAccountSummaries = jdField_null(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
        break;
      case 1: 
      default: 
        localAccountSummaries = jdField_goto(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      }
      ResultSet localResultSet = null;
      Currency localCurrency = null;
      try
      {
        localResultSet = jdField_do(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
        while (localResultSet.next()) {
          localCurrency = DCUtil.getCurrencyColumn(localResultSet.getBigDecimal(1), paramAccount.getCurrencyCode(), paramAccount.getLocale());
        }
      }
      catch (SQLException localSQLException)
      {
        throw new DCException(302, "SQLException occured when reading deposit summary result set", localSQLException);
      }
      finally
      {
        DBUtil.closeResultSet(localResultSet);
      }
      Iterator localIterator = localAccountSummaries.iterator();
      AccountSummary localAccountSummary = null;
      while (localIterator.hasNext())
      {
        localAccountSummary = (AccountSummary)localIterator.next();
        switch (i2)
        {
        case 2: 
          break;
        case 4: 
          ((CreditCardAcctSummary)localAccountSummary).setCurrentBalance(localCurrency);
          break;
        case 3: 
          ((LoanAcctSummary)localAccountSummary).setCurrentBalance(localCurrency);
          break;
        case 1: 
        default: 
          ((DepositAcctSummary)localAccountSummary).setCurrentLedger(localCurrency);
        }
      }
      return localAccountSummaries;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      DCException localDCException2 = new DCException(localException);
      localDCException2.setCode(421);
      throw localDCException2;
    }
  }
  
  private static AccountSummary jdField_do(Account paramAccount, Calendar paramCalendar, Connection paramConnection, String paramString)
    throws DCException
  {
    try
    {
      int i1 = paramAccount.getAccountGroup();
      int i2 = Account.getAccountSystemTypeFromGroup(i1);
      switch (i2)
      {
      case 2: 
        return jdField_for(paramAccount, paramCalendar, paramConnection, paramString);
      case 4: 
        return a(paramAccount, paramCalendar, paramConnection, paramString);
      case 3: 
        return jdField_if(paramAccount, paramCalendar, paramConnection, paramString);
      }
      return jdField_int(paramAccount, paramCalendar, paramConnection, paramString);
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      DCException localDCException2 = new DCException(localException);
      localDCException2.setCode(421);
      throw localDCException2;
    }
  }
  
  protected static ArrayList getSummaryMapList(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    int i1 = paramAccount.getAccountGroup();
    int i2 = Account.getAccountSystemTypeFromGroup(i1);
    switch (i2)
    {
    case 2: 
      return jdField_if(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    case 4: 
      return jdField_int(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    case 3: 
      return jdField_long(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
    }
    return jdField_else(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
  }
  
  private static ResultSet jdField_case(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    try
    {
      int i1 = paramAccount.getTypeValue();
      String str2 = paramAccount.getRoutingNum();
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          if (str2 != null)
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, v);
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str2);
            localPreparedStatement.setString(6, str1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, v);
          }
          else
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_for);
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, jdField_for);
          }
        }
        else if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, t);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, t);
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, I);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, I);
        }
      }
      else if (paramCalendar2 != null)
      {
        if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, r);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, r);
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_goto);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, jdField_goto);
        }
      }
      else if (str2 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, p);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str2);
        localPreparedStatement.setString(4, str1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, p);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, O);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, O);
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (Exception localException)
    {
      int i2 = (localException instanceof SQLException) ? 302 : 438;
      throw new DCException(i2, "Failed to get deposit summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static ResultSet jdField_do(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    try
    {
      int i1 = paramAccount.getTypeValue();
      String str2 = paramAccount.getRoutingNum();
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          if (str2 != null)
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_null);
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str2);
            localPreparedStatement.setString(6, str1);
            localPreparedStatement.setMaxRows(1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, jdField_null);
          }
          else
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, k);
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str1);
            localPreparedStatement.setMaxRows(1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, k);
          }
        }
        else if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_byte);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localPreparedStatement.setMaxRows(1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, jdField_byte);
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, S);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str1);
          localPreparedStatement.setMaxRows(1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, S);
        }
      }
      else if (paramCalendar2 != null)
      {
        if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_new);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localPreparedStatement.setMaxRows(1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, jdField_new);
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, m);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str1);
          localPreparedStatement.setMaxRows(1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, m);
        }
      }
      else if (str2 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_int);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str2);
        localPreparedStatement.setString(4, str1);
        localPreparedStatement.setMaxRows(1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, jdField_int);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, T);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str1);
        localPreparedStatement.setMaxRows(1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, T);
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (Exception localException)
    {
      int i2 = (localException instanceof SQLException) ? 302 : 421;
      throw new DCException(i2, "Failed to get deposit summary.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static ArrayList jdField_else(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_case(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      ArrayList localArrayList = jdField_for(localResultSet, paramAccount, paramConnection);
      return localArrayList;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ArrayList jdField_for(ResultSet paramResultSet, Account paramAccount, Connection paramConnection)
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
        String str = paramResultSet.getString(45);
        a(localHashMap1, "DATADATE", localDateTime);
        int i2 = paramResultSet.getInt(34);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALCREDITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(35);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALCREDITSMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(36);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "CREDITSNOTDETAILED", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(4), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(37);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "DEPOSITSSUBJFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(38);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALADJCREDITSYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(39);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALLOCKBOXDEPOSITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(33);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALDEBITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(40);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALDEBITSMTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(41);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TODAYTOTALDEBITS", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(42);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALDEBITLESSWIRE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(43);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALADJDEBITSYTD", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), str, paramAccount.getLocale()), i2));
        i2 = paramResultSet.getInt(44);
        if (paramResultSet.wasNull()) {
          i2 = -1;
        }
        a(localHashMap1, "TOTALDEBITSEXRETN", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), str, paramAccount.getLocale()), i2));
        a(localHashMap1, "IMMEDAVAILAMOUNT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), str, paramAccount.getLocale())));
        a(localHashMap1, "ONEDAYAVAILAMOUNT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(15), str, paramAccount.getLocale())));
        a(localHashMap1, "MOREONEDAYAVAILAM", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(16), str, paramAccount.getLocale())));
        a(localHashMap1, "VALUEDATETIME", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(17), paramAccount.getLocale()));
        a(localHashMap1, "AVAILABLEOVERDRAFT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(18), str, paramAccount.getLocale())));
        a(localHashMap1, "RESTRICTEDCASH", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(19), str, paramAccount.getLocale())));
        a(localHashMap1, "ACCRUEDINTEREST", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(20), str, paramAccount.getLocale())));
        a(localHashMap1, "ACCRUEDDIVIDEND", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(21), str, paramAccount.getLocale())));
        a(localHashMap1, "TOTALOVERDRAFTAMT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(22), str, paramAccount.getLocale())));
        a(localHashMap1, "NXTOVRDRFTPMTDATE", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(23), paramAccount.getLocale()));
        float f1 = paramResultSet.getFloat(24);
        Float localFloat = null;
        if ((!paramResultSet.wasNull()) && (f1 >= 0.0D)) {
          localFloat = new Float(f1);
        }
        a(localHashMap1, "INTERESTRATE", localFloat);
        a(localHashMap1, "OPENINGLEDGER", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(26), str, paramAccount.getLocale())));
        a(localHashMap1, "CLOSINGLEDGER", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(27), str, paramAccount.getLocale())));
        a(localHashMap1, "CURRAVAILBAL", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(28), str, paramAccount.getLocale())));
        a(localHashMap1, "LEDGERBAL", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(29), str, paramAccount.getLocale())));
        a(localHashMap1, "ONEDAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(30), str, paramAccount.getLocale())));
        a(localHashMap1, "TWODAYFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(31), str, paramAccount.getLocale())));
        a(localHashMap1, "TOTALFLOAT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(32), str, paramAccount.getLocale())));
        AccountSummary localAccountSummary = new AccountSummary();
        DCUtil.fillExtendABean(paramConnection, localAccountSummary, paramResultSet, 25);
        HashMap localHashMap2 = localAccountSummary.getHash();
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
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 438;
      throw new DCException(i1, "Failed to get deposit summary maps.", localException);
    }
  }
  
  private static ResultSet jdField_try(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    try
    {
      String str2 = paramAccount.getRoutingNum();
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          if (str2 != null)
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ");
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str2);
            localPreparedStatement.setString(6, str1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ");
          }
          else
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ");
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ");
          }
        }
        else if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ");
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ");
        }
      }
      else if (paramCalendar2 != null)
      {
        if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
        }
      }
      else if (str2 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str2);
        localPreparedStatement.setString(4, str1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.BookValue, b.MarketValue, b.ExtendABeanXMLID, b.ValueDateTime, a.CurrencyCode FROM DC_Account a, DC_AccountSummary b WHERE a.AccountID=? AND a.BankID=? AND a.DCAccountID=b.DCAccountID AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ");
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 439;
      throw new DCException(i1, "Failed to get asset summary maps.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static ArrayList jdField_if(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_try(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      ArrayList localArrayList = jdField_if(localResultSet, paramAccount, paramConnection);
      return localArrayList;
    }
    catch (Exception localException)
    {
      throw new DCException(localException);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ArrayList jdField_if(ResultSet paramResultSet, Account paramAccount, Connection paramConnection)
    throws Exception
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
      String str = paramResultSet.getString(6);
      a(localHashMap1, "DATADATE", localDateTime);
      a(localHashMap1, "BOOKVALUE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), str, paramAccount.getLocale())));
      a(localHashMap1, "MARKETVALUE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), str, paramAccount.getLocale())));
      a(localHashMap1, "VALUEDATETIME", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(5), paramAccount.getLocale()));
      AccountSummary localAccountSummary = new AccountSummary();
      DCUtil.fillExtendABean(paramConnection, localAccountSummary, paramResultSet, 4);
      HashMap localHashMap2 = localAccountSummary.getHash();
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
  
  private static ResultSet jdField_new(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    String str2 = paramAccount.getRoutingNum();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          if (str2 != null)
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ");
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str2);
            localPreparedStatement.setString(6, str1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate ");
          }
          else
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ");
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate ");
          }
        }
        else if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
        }
      }
      else if (paramCalendar2 != null)
      {
        if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
        }
      }
      else if (str2 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str2);
        localPreparedStatement.setString(4, str1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.CardHolderName, b.CardExpDate, b.CreditLimit, b.LastPaymentAmt, b.NextPaymentMinAmt, b.NextPaymentDue, b.LastPaymentDate, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM DC_Account a, DC_CCAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID = ? AND a.BankID = ?  AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 440;
      throw new DCException(i1, "Failed to get asset summary maps.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static ArrayList jdField_int(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_new(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      ArrayList localArrayList = jdField_do(localResultSet, paramAccount, paramConnection);
      return localArrayList;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ArrayList jdField_do(ResultSet paramResultSet, Account paramAccount, Connection paramConnection)
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
        String str = paramResultSet.getString(15);
        a(localHashMap1, "DATADATE", localDateTime);
        a(localHashMap1, "AVAILABLECREDIT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), str, paramAccount.getLocale())));
        a(localHashMap1, "AMOUNTDUE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), str, paramAccount.getLocale())));
        float f1 = paramResultSet.getFloat(4);
        Float localFloat = null;
        if ((!paramResultSet.wasNull()) && (f1 >= 0.0D)) {
          localFloat = new Float(f1);
        }
        a(localHashMap1, "INTERESTRATE", localFloat);
        a(localHashMap1, "DUEDATE", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(5), paramAccount.getLocale()));
        a(localHashMap1, "CARDHOLDERNAME", paramResultSet.getString(6));
        a(localHashMap1, "CARDEXPDATE", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(7), paramAccount.getLocale()));
        a(localHashMap1, "CREDITLIMIT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), str, paramAccount.getLocale())));
        a(localHashMap1, "LASTPAYMENTAMT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), str, paramAccount.getLocale())));
        a(localHashMap1, "NEXTPAYMENTMINAMT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), str, paramAccount.getLocale())));
        a(localHashMap1, "NEXTPAYMENTDUE", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(11), paramAccount.getLocale()));
        a(localHashMap1, "LASTPAYMENTDATE", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(12), paramAccount.getLocale()));
        a(localHashMap1, "VALUEDATETIME", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(14), paramAccount.getLocale()));
        AccountSummary localAccountSummary = new AccountSummary();
        DCUtil.fillExtendABean(paramConnection, localAccountSummary, paramResultSet, 13);
        HashMap localHashMap2 = localAccountSummary.getHash();
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
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 440;
      throw new DCException(i1, "Failed to get asset summary maps.", localException);
    }
  }
  
  private static ResultSet jdField_byte(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    String str1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    String str2 = paramAccount.getRoutingNum();
    try
    {
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          if (str2 != null)
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=?  AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str2);
            localPreparedStatement.setString(6, str1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=?  AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
          }
          else
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=?  AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str1);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND b.DataDate<=?  AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
          }
        }
        else if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate>=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
        }
      }
      else if (paramCalendar2 != null)
      {
        if (str2 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str2);
          localPreparedStatement.setString(5, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str1);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND b.DataDate<=? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
        }
      }
      else if (str2 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str2);
        localPreparedStatement.setString(4, str1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND a.RoutingNumber=? AND b.DataClassification=? ORDER BY b.DataDate");
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str1);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, "SELECT b.DataDate, b.AvailableCredit, b.AmountDue, b.InterestRate, b.DueDate, b.MaturityDate, b.AccruedInterest, b.OpeningBalance, b.CollateralDesc, b.PrinciplePastDue, b.InterestPastDue, b.LateFees,b.NextPrincipleAmt, b.NextInterestAmt, b.ExtendABeanXMLID, b.ValueDate, a.CurrencyCode FROM  DC_Account a, DC_LoanAcctSummary b WHERE a.DCAccountID=b.DCAccountID AND a.AccountID =? AND a.BankID = ? AND a.RoutingNumber IS NULL AND b.DataClassification=? ORDER BY b.DataDate");
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 441;
      throw new DCException(i1, "Failed to get asset summary maps.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
  
  private static ArrayList jdField_long(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_byte(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      ArrayList localArrayList = a(localResultSet, paramAccount, paramConnection);
      return localArrayList;
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
        String str = paramResultSet.getString(17);
        a(localHashMap1, "DATADATE", localDateTime);
        a(localHashMap1, "AVAILABLECREDIT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(2), str, paramAccount.getLocale())));
        a(localHashMap1, "AMOUNTDUE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(3), str, paramAccount.getLocale())));
        float f1 = paramResultSet.getFloat(4);
        Float localFloat = null;
        if ((!paramResultSet.wasNull()) && (f1 >= 0.0D)) {
          localFloat = new Float(f1);
        }
        a(localHashMap1, "INTERESTRATE", localFloat);
        a(localHashMap1, "DUEDATE", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(5), paramAccount.getLocale()));
        a(localHashMap1, "MATURITYDATE", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(6), paramAccount.getLocale()));
        a(localHashMap1, "ACCRUEDINTEREST", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), str, paramAccount.getLocale())));
        a(localHashMap1, "OPENINGBALANCE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), str, paramAccount.getLocale())));
        a(localHashMap1, "COLLATERALDESC", paramResultSet.getString(9));
        a(localHashMap1, "PRINCIPLEPASTDUE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), str, paramAccount.getLocale())));
        a(localHashMap1, "INTERESTPASTDUE", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), str, paramAccount.getLocale())));
        a(localHashMap1, "LATEFEES", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), str, paramAccount.getLocale())));
        a(localHashMap1, "NEXTPRINCIPLEAMT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), str, paramAccount.getLocale())));
        a(localHashMap1, "NEXTINTERESTAMT", SummaryValue.getInstance(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), str, paramAccount.getLocale())));
        a(localHashMap1, "VALUEDATETIME", DCUtil.getTimestampColumn(paramResultSet.getTimestamp(16), paramAccount.getLocale()));
        AccountSummary localAccountSummary = new AccountSummary();
        DCUtil.fillExtendABean(paramConnection, localAccountSummary, paramResultSet, 15);
        HashMap localHashMap2 = localAccountSummary.getHash();
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
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i1 = (localException instanceof SQLException) ? 302 : 441;
      throw new DCException(i1, "Failed to get asset summary maps.", localException);
    }
  }
  
  private static void a(HashMap paramHashMap, Object paramObject1, Object paramObject2)
  {
    if (paramObject2 != null) {
      paramHashMap.put(paramObject1, paramObject2);
    }
  }
  
  protected static DateTime getSummaryMaxDate(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    ResultSet localResultSet = null;
    try
    {
      localResultSet = jdField_char(paramAccount, paramCalendar1, paramCalendar2, paramConnection, paramHashMap);
      if (localResultSet.next())
      {
        localDateTime = DCUtil.getTimestampColumn(localResultSet.getTimestamp(1), paramAccount.getLocale());
        return localDateTime;
      }
      DateTime localDateTime = null;
      return localDateTime;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "SQLException occured when reading deposit summary result set", localSQLException);
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      DCException localDCException2 = new DCException(localException);
      localDCException2.setCode(421);
      throw localDCException2;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static ResultSet jdField_char(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    int i1 = paramAccount.getAccountGroup();
    int i2 = Account.getAccountSystemTypeFromGroup(i1);
    switch (i2)
    {
    case 2: 
      str1 = l;
    case 4: 
      str1 = F;
    case 3: 
      str1 = jdField_if;
    }
    String str1 = a;
    String str2 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet1 = null;
    try
    {
      String str3 = paramAccount.getRoutingNum();
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(V);
      localStringBuffer.append(str1);
      localStringBuffer.append(U);
      if (paramCalendar1 != null) {
        localStringBuffer.append(" and b.DataDate>=? ");
      }
      if (paramCalendar2 != null) {
        localStringBuffer.append(" and b.DataDate<=? ");
      }
      if (str3 == null) {
        localStringBuffer.append(" AND a.RoutingNumber IS NULL ");
      } else {
        localStringBuffer.append(" AND a.RoutingNumber=? ");
      }
      localStringBuffer.append(" AND b.DataClassification=? ");
      str5 = localStringBuffer.toString();
      localPreparedStatement = DCAdapter.getStatement(paramConnection, str5);
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          if (str3 != null)
          {
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str3);
            localPreparedStatement.setString(6, str2);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, str5);
          }
          else
          {
            localPreparedStatement = DCAdapter.getStatement(paramConnection, str5);
            localPreparedStatement.setString(1, paramAccount.getID());
            localPreparedStatement.setString(2, paramAccount.getBankID());
            DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
            DCUtil.fillTimestampColumn(localPreparedStatement, 4, paramCalendar2);
            localPreparedStatement.setString(5, str2);
            localResultSet1 = DBUtil.executeQuery(localPreparedStatement, str5);
          }
        }
        else if (str3 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, str5);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str3);
          localPreparedStatement.setString(5, str2);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, str5);
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, str5);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str2);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, str5);
        }
      }
      else if (paramCalendar2 != null)
      {
        if (str3 != null)
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, str5);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str3);
          localPreparedStatement.setString(5, str2);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, str5);
        }
        else
        {
          localPreparedStatement = DCAdapter.getStatement(paramConnection, str5);
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar2);
          localPreparedStatement.setString(4, str2);
          localResultSet1 = DBUtil.executeQuery(localPreparedStatement, str5);
        }
      }
      else if (str3 != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, str5);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str3);
        localPreparedStatement.setString(4, str2);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, str5);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, str5);
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str2);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement, str5);
      }
      ResultSet localResultSet2 = localResultSet1;
      return localResultSet2;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      switch (i2)
      {
      case 2: 
        str4 = 460;
      case 4: 
        str4 = 461;
      case 3: 
        str4 = 462;
      }
      String str4 = 459;
      String str5 = (localException instanceof SQLException) ? 302 : str4;
      throw new DCException(str5, "Failed to get max date for summary data.", localException);
    }
    finally
    {
      localPreparedStatement = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCAccountSummary
 * JD-Core Version:    0.7.0.1
 */