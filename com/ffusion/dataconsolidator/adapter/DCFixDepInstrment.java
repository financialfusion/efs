package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Contact;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.FixedDepositInstrument;
import com.ffusion.beans.accounts.FixedDepositInstruments;
import com.ffusion.util.MapUtil;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

class DCFixDepInstrment
{
  private static final String jdField_try = "INSERT INTO DC_FixDepInstrment( DCAccountID, DataDate, DataSource, InstNumber, InstBankName, Currency, PrincipalAmount, AccruedInterest, InterestAtMaturity, ProceedsAtMaturity, ValueDate, MaturityDate, RestrictedAmount, NumberOfRollovers, DaysInTerm, InterestRate, StmtMail1Street1, StmtMail1Street2, StmtMail1City, StmtMail1State,StmtMail1Country, StmtMail1Zip, StmtMail2Street1, StmtMail2Street2, StmtMail2City, StmtMail2State, StmtMail2Country, StmtMail2Zip, StmtMail3Street1, StmtMail3Street2, StmtMail3City, StmtMail3State, StmtMail3Country, StmtMail3Zip, SettleInstrType, TargetAcctNumber, TargetRoutNumber, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String jdField_char = "UPDATE DC_FixDepInstrment SET DCAccountID=?, DataDate=?, DataSource=?, InstNumber=?, InstBankName=?, Currency=?, PrincipalAmount=?, AccruedInterest=?, InterestAtMaturity=?, ProceedsAtMaturity=?, ValueDate=?, MaturityDate=?, RestrictedAmount=?, NumberOfRollovers=?, DaysInTerm=?, InterestRate=?, StmtMail1Street1=?, StmtMail1Street2=?, StmtMail1City=?, StmtMail1State=?,StmtMail1Country=?, StmtMail1Zip=?, StmtMail2Street1=?, StmtMail2Street2=?, StmtMail2City=?, StmtMail2State=?, StmtMail2Country=?, StmtMail2Zip=?, StmtMail3Street1=?, StmtMail3Street2=?, StmtMail3City=?, StmtMail3State=?, StmtMail3Country=?, StmtMail3Zip=?, SettleInstrType=?, TargetAcctNumber=?, TargetRoutNumber=?, BAIFileIdentifier=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND InstNumber=? AND DataClassification=? ";
  private static String jdField_byte = "SELECT ExtendABeanXMLID FROM DC_FixDepInstrment WHERE DCAccountID=? AND DataDate=? AND InstNumber=?";
  private static String jdField_for = "SELECT c.DataDate, c.InstNumber, c.InstBankName, c.Currency, c.PrincipalAmount, c.AccruedInterest, c.InterestAtMaturity, c.ProceedsAtMaturity, c.ValueDate, c.MaturityDate, c.RestrictedAmount, c.NumberOfRollovers, c.DaysInTerm, c.InterestRate, c.StmtMail1Street1, c.StmtMail1Street2, c.StmtMail1City, c.StmtMail1State,c.StmtMail1Country, c.StmtMail1Zip, c.StmtMail2Street1, c.StmtMail2Street2, c.StmtMail2City, c.StmtMail2State, c.StmtMail2Country, c.StmtMail2Zip, c.StmtMail3Street1, c.StmtMail3Street2, c.StmtMail3City, c.StmtMail3State, c.StmtMail3Country, c.StmtMail3Zip,  c.ExtendABeanXMLID, c.SettleInstrType, c.TargetAcctNumber, c.TargetRoutNumber FROM DC_Account a,  DC_FixDepInstrment c WHERE  a.DCAccountID=c.DCAccountID  AND a.AccountID=? AND a.BankID=? AND c.DataDate>=? AND c.DataDate<=? AND c.DataClassification=? ";
  private static String jdField_do = "SELECT c.DataDate, c.InstNumber, c.InstBankName, c.Currency, c.PrincipalAmount, c.AccruedInterest, c.InterestAtMaturity, c.ProceedsAtMaturity, c.ValueDate, c.MaturityDate, c.RestrictedAmount, c.NumberOfRollovers, c.DaysInTerm, c.InterestRate, c.StmtMail1Street1, c.StmtMail1Street2, c.StmtMail1City, c.StmtMail1State,c.StmtMail1Country, c.StmtMail1Zip, c.StmtMail2Street1, c.StmtMail2Street2, c.StmtMail2City, c.StmtMail2State, c.StmtMail2Country, c.StmtMail2Zip, c.StmtMail3Street1, c.StmtMail3Street2, c.StmtMail3City, c.StmtMail3State, c.StmtMail3Country, c.StmtMail3Zip,  c.ExtendABeanXMLID, c.SettleInstrType, c.TargetAcctNumber, c.TargetRoutNumber FROM DC_Account a,  DC_FixDepInstrment c WHERE  a.DCAccountID=c.DCAccountID  AND a.AccountID=? AND a.BankID=? AND c.DataDate>=? AND c.DataClassification=? ";
  private static String a = "SELECT c.DataDate, c.InstNumber, c.InstBankName, c.Currency, c.PrincipalAmount, c.AccruedInterest, c.InterestAtMaturity, c.ProceedsAtMaturity, c.ValueDate, c.MaturityDate, c.RestrictedAmount, c.NumberOfRollovers, c.DaysInTerm, c.InterestRate, c.StmtMail1Street1, c.StmtMail1Street2, c.StmtMail1City, c.StmtMail1State,c.StmtMail1Country, c.StmtMail1Zip, c.StmtMail2Street1, c.StmtMail2Street2, c.StmtMail2City, c.StmtMail2State, c.StmtMail2Country, c.StmtMail2Zip, c.StmtMail3Street1, c.StmtMail3Street2, c.StmtMail3City, c.StmtMail3State, c.StmtMail3Country, c.StmtMail3Zip,  c.ExtendABeanXMLID, c.SettleInstrType, c.TargetAcctNumber, c.TargetRoutNumber FROM DC_Account a, DC_FixDepInstrment c WHERE  a.DCAccountID=c.DCAccountID  AND a.AccountID=? AND a.BankID=? AND c.DataDate<=? AND c.DataClassification=? ";
  private static String jdField_goto = "SELECT c.DataDate, c.InstNumber, c.InstBankName, c.Currency, c.PrincipalAmount, c.AccruedInterest, c.InterestAtMaturity, c.ProceedsAtMaturity, c.ValueDate, c.MaturityDate, c.RestrictedAmount, c.NumberOfRollovers, c.DaysInTerm, c.InterestRate, c.StmtMail1Street1, c.StmtMail1Street2, c.StmtMail1City, c.StmtMail1State,c.StmtMail1Country, c.StmtMail1Zip, c.StmtMail2Street1, c.StmtMail2Street2, c.StmtMail2City, c.StmtMail2State, c.StmtMail2Country, c.StmtMail2Zip, c.StmtMail3Street1, c.StmtMail3Street2, c.StmtMail3City, c.StmtMail3State, c.StmtMail3Country, c.StmtMail3Zip,  c.ExtendABeanXMLID, c.SettleInstrType, c.TargetAcctNumber, c.TargetRoutNumber FROM DC_Account a,  DC_FixDepInstrment c WHERE  a.DCAccountID=c.DCAccountID  AND a.AccountID=? AND a.BankID=? AND c.DataClassification=? ";
  private static String jdField_else = "SELECT  c.DataDate, c.InstNumber, c.InstBankName, c.Currency, c.PrincipalAmount, c.AccruedInterest, c.InterestAtMaturity, c.ProceedsAtMaturity, c.ValueDate, c.MaturityDate, c.RestrictedAmount, c.NumberOfRollovers, c.DaysInTerm, c.InterestRate, c.StmtMail1Street1, c.StmtMail1Street2, c.StmtMail1City, c.StmtMail1State,c.StmtMail1Country, c.StmtMail1Zip, c.StmtMail2Street1, c.StmtMail2Street2, c.StmtMail2City, c.StmtMail2State, c.StmtMail2Country, c.StmtMail2Zip, c.StmtMail3Street1, c.StmtMail3Street2, c.StmtMail3City, c.StmtMail3State, c.StmtMail3Country, c.StmtMail3Zip,  c.ExtendABeanXMLID, c.SettleInstrType, c.TargetAcctNumber, c.TargetRoutNumber, c.dataSource FROM DC_Account a,  DC_FixDepInstrment c WHERE  a.DCAccountID=c.DCAccountID  AND a.AccountID=? AND a.BankID=? AND c.DataDate=? AND c.InstNumber=? AND c.InstBankName=? AND a.RoutingNumber=? ";
  private static String jdField_int = "SELECT  c.DataDate, c.InstNumber, c.InstBankName, c.Currency, c.PrincipalAmount, c.AccruedInterest, c.InterestAtMaturity, c.ProceedsAtMaturity, c.ValueDate, c.MaturityDate, c.RestrictedAmount, c.NumberOfRollovers, c.DaysInTerm, c.InterestRate, c.StmtMail1Street1, c.StmtMail1Street2, c.StmtMail1City, c.StmtMail1State,c.StmtMail1Country, c.StmtMail1Zip, c.StmtMail2Street1, c.StmtMail2Street2, c.StmtMail2City, c.StmtMail2State, c.StmtMail2Country, c.StmtMail2Zip, c.StmtMail3Street1, c.StmtMail3Street2, c.StmtMail3City, c.StmtMail3State, c.StmtMail3Country, c.StmtMail3Zip,  c.ExtendABeanXMLID, c.SettleInstrType, c.TargetAcctNumber, c.TargetRoutNumber, c.dataSource FROM DC_Account a,  DC_FixDepInstrment c WHERE  a.DCAccountID=c.DCAccountID  AND a.AccountID=? AND a.BankID=? AND c.DataDate=? AND c.InstNumber=? AND c.InstBankName=? AND a.RoutingNumber IS NULL ";
  private static String jdField_if = "SELECT SettleInstrType, TargetAcctNumber, TargetRoutNumber FROM DC_FixDepInstrment WHERE NOT SettleInstrType=? ORDER BY DataDate DESC";
  private static String jdField_new = " a.RoutingNumber";
  private static String jdField_case = " ORDER BY c.DataDate";
  
  protected static void addFixedDepositInstrument(FixedDepositInstrument paramFixedDepositInstrument, int paramInt, Connection paramConnection, HashMap paramHashMap)
    throws DCException
  {
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    int i = 0;
    Object localObject1 = null;
    ResultSet localResultSet = null;
    try
    {
      String str1 = MapUtil.getStringValue(paramHashMap, "BAI_FILE_IDENTIFIER", null);
      String str2 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localObject2 = new Account();
      ((Account)localObject2).setID(paramFixedDepositInstrument.getAccountID());
      ((Account)localObject2).setBankID(paramFixedDepositInstrument.getBankID());
      ((Account)localObject2).setRoutingNum(paramFixedDepositInstrument.getRoutingNumber());
      ((Account)localObject2).setCurrencyCode(paramFixedDepositInstrument.getCurrency());
      if (!DCAccount.accountExists((Account)localObject2, paramConnection, paramHashMap)) {
        DCAccount.addAccount((Account)localObject2, paramConnection, paramHashMap);
      }
      int k = DCAdapter.getDCAccountID(paramConnection, paramFixedDepositInstrument.getAccountID(), paramFixedDepositInstrument.getBankID(), paramFixedDepositInstrument.getRoutingNumber());
      if (k == -1) {
        throw new DCException(414, "Account not found.");
      }
      localPreparedStatement2 = DCAdapter.getStatement(paramConnection, jdField_if);
      Object[] arrayOfObject = a(paramFixedDepositInstrument, paramConnection);
      FixedDepositInstrument localFixedDepositInstrument = (FixedDepositInstrument)arrayOfObject[0];
      if (localFixedDepositInstrument == null)
      {
        localPreparedStatement1 = DCAdapter.getStatement(paramConnection, "INSERT INTO DC_FixDepInstrment( DCAccountID, DataDate, DataSource, InstNumber, InstBankName, Currency, PrincipalAmount, AccruedInterest, InterestAtMaturity, ProceedsAtMaturity, ValueDate, MaturityDate, RestrictedAmount, NumberOfRollovers, DaysInTerm, InterestRate, StmtMail1Street1, StmtMail1Street2, StmtMail1City, StmtMail1State,StmtMail1Country, StmtMail1Zip, StmtMail2Street1, StmtMail2Street2, StmtMail2City, StmtMail2State, StmtMail2Country, StmtMail2Zip, StmtMail3Street1, StmtMail3Street2, StmtMail3City, StmtMail3State, StmtMail3Country, StmtMail3Zip, SettleInstrType, TargetAcctNumber, TargetRoutNumber, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        localObject1 = paramFixedDepositInstrument;
        if (((FixedDepositInstrument)localObject1).getSettlementInstructionType() == 0)
        {
          localPreparedStatement2.setInt(1, 0);
          localResultSet = DBUtil.executeQuery(localPreparedStatement2, jdField_if);
          if (localResultSet.next())
          {
            ((FixedDepositInstrument)localObject1).setSettlementInstructionType(localResultSet.getInt(1));
            ((FixedDepositInstrument)localObject1).setSettlementTargetAccountNumber(localResultSet.getString(2));
            ((FixedDepositInstrument)localObject1).setSettlementTargetRoutingNumber(localResultSet.getString(3));
          }
        }
      }
      else
      {
        localObject3 = (Integer)arrayOfObject[1];
        if (localObject3 != null) {
          i = ((Integer)localObject3).intValue();
        }
        localPreparedStatement1 = DCAdapter.getStatement(paramConnection, "UPDATE DC_FixDepInstrment SET DCAccountID=?, DataDate=?, DataSource=?, InstNumber=?, InstBankName=?, Currency=?, PrincipalAmount=?, AccruedInterest=?, InterestAtMaturity=?, ProceedsAtMaturity=?, ValueDate=?, MaturityDate=?, RestrictedAmount=?, NumberOfRollovers=?, DaysInTerm=?, InterestRate=?, StmtMail1Street1=?, StmtMail1Street2=?, StmtMail1City=?, StmtMail1State=?,StmtMail1Country=?, StmtMail1Zip=?, StmtMail2Street1=?, StmtMail2Street2=?, StmtMail2City=?, StmtMail2State=?, StmtMail2Country=?, StmtMail2Zip=?, StmtMail3Street1=?, StmtMail3Street2=?, StmtMail3City=?, StmtMail3State=?, StmtMail3Country=?, StmtMail3Zip=?, SettleInstrType=?, TargetAcctNumber=?, TargetRoutNumber=?, BAIFileIdentifier=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND InstNumber=? AND DataClassification=? ");
        localObject1 = localFixedDepositInstrument;
        localObject4 = null;
        localObject5 = null;
        String str3 = null;
        Contact localContact = null;
        int m = -1;
        float f = -1.0F;
        str3 = paramFixedDepositInstrument.getSettlementTargetRoutingNumber();
        if (str3 != null) {
          ((FixedDepositInstrument)localObject1).setSettlementTargetRoutingNumber(str3);
        }
        str3 = paramFixedDepositInstrument.getSettlementTargetAccountNumber();
        if (str3 != null) {
          ((FixedDepositInstrument)localObject1).setSettlementTargetAccountNumber(str3);
        }
        str3 = paramFixedDepositInstrument.getCurrency();
        if (str3 != null) {
          ((FixedDepositInstrument)localObject1).setCurrency(str3);
        }
        localObject4 = paramFixedDepositInstrument.getPrincipalAmount();
        if (localObject4 != null) {
          ((FixedDepositInstrument)localObject1).setPrincipalAmount((Currency)localObject4);
        }
        localObject4 = paramFixedDepositInstrument.getAccruedInterest();
        if (localObject4 != null) {
          ((FixedDepositInstrument)localObject1).setAccruedInterest((Currency)localObject4);
        }
        localObject4 = paramFixedDepositInstrument.getInterestAtMaturity();
        if (localObject4 != null) {
          ((FixedDepositInstrument)localObject1).setInterestAtMaturity((Currency)localObject4);
        }
        localObject4 = paramFixedDepositInstrument.getProceedsAtMaturity();
        if (localObject4 != null) {
          ((FixedDepositInstrument)localObject1).setProceedsAtMaturity((Currency)localObject4);
        }
        localObject5 = paramFixedDepositInstrument.getValueDate();
        if (localObject5 != null) {
          ((FixedDepositInstrument)localObject1).setValueDate((DateTime)localObject5);
        }
        localObject5 = paramFixedDepositInstrument.getMaturityDate();
        if (localObject5 != null) {
          ((FixedDepositInstrument)localObject1).setMaturityDate((DateTime)localObject5);
        }
        localObject4 = paramFixedDepositInstrument.getRestrictedAmount();
        if (localObject4 != null) {
          ((FixedDepositInstrument)localObject1).setRestrictedAmount((Currency)localObject4);
        }
        m = paramFixedDepositInstrument.getNumberOfRollovers();
        if (m != -1) {
          ((FixedDepositInstrument)localObject1).setNumberOfRollovers(m);
        }
        m = paramFixedDepositInstrument.getSettlementInstructionType();
        if (m != -1) {
          ((FixedDepositInstrument)localObject1).setSettlementInstructionType(m);
        }
        m = paramFixedDepositInstrument.getDaysInTerm();
        if (m != -1) {
          ((FixedDepositInstrument)localObject1).setDaysInTerm(m);
        }
        f = paramFixedDepositInstrument.getInterestRate();
        if (f != -1.0F) {
          ((FixedDepositInstrument)localObject1).setInterestRate(f);
        }
        localContact = paramFixedDepositInstrument.getStatementMailingAddr1();
        if (localContact != null) {
          ((FixedDepositInstrument)localObject1).setStatementMailingAddr1(localContact);
        }
        localContact = paramFixedDepositInstrument.getStatementMailingAddr2();
        if (localContact != null) {
          ((FixedDepositInstrument)localObject1).setStatementMailingAddr2(localContact);
        }
        localContact = paramFixedDepositInstrument.getStatementMailingAddr3();
        if (localContact != null) {
          ((FixedDepositInstrument)localObject1).setStatementMailingAddr3(localContact);
        }
      }
      localPreparedStatement1.setInt(1, k);
      DCUtil.fillTimestampColumn(localPreparedStatement1, 2, ((FixedDepositInstrument)localObject1).getDataDate());
      if (paramInt == 0) {
        localPreparedStatement1.setInt(3, i);
      } else {
        localPreparedStatement1.setInt(3, paramInt);
      }
      localPreparedStatement1.setString(4, ((FixedDepositInstrument)localObject1).getInstrumentNumber());
      localPreparedStatement1.setString(5, ((FixedDepositInstrument)localObject1).getInstrumentBankName());
      localPreparedStatement1.setString(6, ((FixedDepositInstrument)localObject1).getCurrency());
      DCUtil.fillCurrencyColumn(localPreparedStatement1, 7, ((FixedDepositInstrument)localObject1).getPrincipalAmount());
      DCUtil.fillCurrencyColumn(localPreparedStatement1, 8, ((FixedDepositInstrument)localObject1).getAccruedInterest());
      DCUtil.fillCurrencyColumn(localPreparedStatement1, 9, ((FixedDepositInstrument)localObject1).getInterestAtMaturity());
      DCUtil.fillCurrencyColumn(localPreparedStatement1, 10, ((FixedDepositInstrument)localObject1).getProceedsAtMaturity());
      DCUtil.fillTimestampColumn(localPreparedStatement1, 11, ((FixedDepositInstrument)localObject1).getValueDate());
      DCUtil.fillTimestampColumn(localPreparedStatement1, 12, ((FixedDepositInstrument)localObject1).getMaturityDate());
      DCUtil.fillCurrencyColumn(localPreparedStatement1, 13, ((FixedDepositInstrument)localObject1).getRestrictedAmount());
      localPreparedStatement1.setInt(14, ((FixedDepositInstrument)localObject1).getNumberOfRollovers());
      localPreparedStatement1.setInt(15, ((FixedDepositInstrument)localObject1).getDaysInTerm());
      localPreparedStatement1.setFloat(16, ((FixedDepositInstrument)localObject1).getInterestRate());
      Object localObject3 = ((FixedDepositInstrument)localObject1).getStatementMailingAddr1();
      if (localObject3 != null)
      {
        localPreparedStatement1.setString(17, ((Contact)localObject3).getStreet());
        localPreparedStatement1.setString(18, ((Contact)localObject3).getStreet2());
        localPreparedStatement1.setString(19, ((Contact)localObject3).getCity());
        localPreparedStatement1.setString(20, ((Contact)localObject3).getState());
        localPreparedStatement1.setString(21, ((Contact)localObject3).getCountry());
        localPreparedStatement1.setString(22, ((Contact)localObject3).getZipCode());
      }
      else
      {
        localPreparedStatement1.setString(17, null);
        localPreparedStatement1.setString(18, null);
        localPreparedStatement1.setString(19, null);
        localPreparedStatement1.setString(20, null);
        localPreparedStatement1.setString(21, null);
        localPreparedStatement1.setString(22, null);
      }
      Object localObject4 = ((FixedDepositInstrument)localObject1).getStatementMailingAddr2();
      if (localObject4 != null)
      {
        localPreparedStatement1.setString(23, ((Contact)localObject4).getStreet());
        localPreparedStatement1.setString(24, ((Contact)localObject4).getStreet2());
        localPreparedStatement1.setString(25, ((Contact)localObject4).getCity());
        localPreparedStatement1.setString(26, ((Contact)localObject4).getState());
        localPreparedStatement1.setString(27, ((Contact)localObject4).getCountry());
        localPreparedStatement1.setString(28, ((Contact)localObject4).getZipCode());
      }
      else
      {
        localPreparedStatement1.setString(23, null);
        localPreparedStatement1.setString(24, null);
        localPreparedStatement1.setString(25, null);
        localPreparedStatement1.setString(26, null);
        localPreparedStatement1.setString(27, null);
        localPreparedStatement1.setString(28, null);
      }
      Object localObject5 = ((FixedDepositInstrument)localObject1).getStatementMailingAddr3();
      if (localObject5 != null)
      {
        localPreparedStatement1.setString(29, ((Contact)localObject5).getStreet());
        localPreparedStatement1.setString(30, ((Contact)localObject5).getStreet2());
        localPreparedStatement1.setString(31, ((Contact)localObject5).getCity());
        localPreparedStatement1.setString(32, ((Contact)localObject5).getState());
        localPreparedStatement1.setString(33, ((Contact)localObject5).getCountry());
        localPreparedStatement1.setString(34, ((Contact)localObject5).getZipCode());
      }
      else
      {
        localPreparedStatement1.setString(29, null);
        localPreparedStatement1.setString(30, null);
        localPreparedStatement1.setString(31, null);
        localPreparedStatement1.setString(32, null);
        localPreparedStatement1.setString(33, null);
        localPreparedStatement1.setString(34, null);
      }
      localPreparedStatement1.setInt(35, ((FixedDepositInstrument)localObject1).getSettlementInstructionType());
      localPreparedStatement1.setString(36, ((FixedDepositInstrument)localObject1).getSettlementTargetAccountNumber());
      localPreparedStatement1.setString(37, ((FixedDepositInstrument)localObject1).getSettlementTargetRoutingNumber());
      if (localFixedDepositInstrument == null)
      {
        localPreparedStatement1.setLong(38, DCExtendABeanXML.addExtendABeanXML(paramConnection, ((FixedDepositInstrument)localObject1).getExtendABeanXML(), paramHashMap));
        localPreparedStatement1.setString(39, null);
        localPreparedStatement1.setString(40, str1);
        localPreparedStatement1.setString(41, str2);
        DBUtil.executeUpdate(localPreparedStatement1, "INSERT INTO DC_FixDepInstrment( DCAccountID, DataDate, DataSource, InstNumber, InstBankName, Currency, PrincipalAmount, AccruedInterest, InterestAtMaturity, ProceedsAtMaturity, ValueDate, MaturityDate, RestrictedAmount, NumberOfRollovers, DaysInTerm, InterestRate, StmtMail1Street1, StmtMail1Street2, StmtMail1City, StmtMail1State,StmtMail1Country, StmtMail1Zip, StmtMail2Street1, StmtMail2Street2, StmtMail2City, StmtMail2State, StmtMail2Country, StmtMail2Zip, StmtMail3Street1, StmtMail3Street2, StmtMail3City, StmtMail3State, StmtMail3Country, StmtMail3Zip, SettleInstrType, TargetAcctNumber, TargetRoutNumber, ExtendABeanXMLID, Extra, BAIFileIdentifier, DataClassification ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      }
      else
      {
        long l1 = a(paramConnection, k, (FixedDepositInstrument)localObject1);
        DCExtendABeanXML.deleteExtendABeanXML(paramConnection, l1);
        a((FixedDepositInstrument)localObject1, paramFixedDepositInstrument);
        long l2 = DCExtendABeanXML.addExtendABeanXML(paramConnection, ((FixedDepositInstrument)localObject1).getExtendABeanXML(), paramHashMap);
        localPreparedStatement1.setString(38, str1);
        localPreparedStatement1.setLong(39, l2);
        localPreparedStatement1.setInt(40, k);
        DCUtil.fillTimestampColumn(localPreparedStatement1, 41, ((FixedDepositInstrument)localObject1).getValueDate());
        localPreparedStatement1.setString(42, ((FixedDepositInstrument)localObject1).getInstrumentNumber());
        localPreparedStatement1.setString(43, str2);
        DBUtil.executeUpdate(localPreparedStatement1, "UPDATE DC_FixDepInstrment SET DCAccountID=?, DataDate=?, DataSource=?, InstNumber=?, InstBankName=?, Currency=?, PrincipalAmount=?, AccruedInterest=?, InterestAtMaturity=?, ProceedsAtMaturity=?, ValueDate=?, MaturityDate=?, RestrictedAmount=?, NumberOfRollovers=?, DaysInTerm=?, InterestRate=?, StmtMail1Street1=?, StmtMail1Street2=?, StmtMail1City=?, StmtMail1State=?,StmtMail1Country=?, StmtMail1Zip=?, StmtMail2Street1=?, StmtMail2Street2=?, StmtMail2City=?, StmtMail2State=?, StmtMail2Country=?, StmtMail2Zip=?, StmtMail3Street1=?, StmtMail3Street2=?, StmtMail3City=?, StmtMail3State=?, StmtMail3Country=?, StmtMail3Zip=?, SettleInstrType=?, TargetAcctNumber=?, TargetRoutNumber=?, BAIFileIdentifier=?, ExtendABeanXMLID=? WHERE DCAccountID=? AND DataDate=? AND InstNumber=? AND DataClassification=? ");
      }
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (SQLException localSQLException)
    {
      throw new DCException(302, "Failed to add fixed deposit instrument.", localSQLException);
    }
    catch (Exception localException)
    {
      int j = (localException instanceof SQLException) ? 302 : 452;
      Object localObject2 = new DCException(j, "Failed to add fixed deposit instrument.", localException);
      throw ((Throwable)localObject2);
    }
    finally
    {
      localPreparedStatement1 = null;
      localPreparedStatement2 = null;
      if (localResultSet != null) {
        DBUtil.closeResultSet(localResultSet);
      }
    }
  }
  
  private static long a(Connection paramConnection, int paramInt, FixedDepositInstrument paramFixedDepositInstrument)
    throws DCException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    DateTime localDateTime = null;
    String str = null;
    long l;
    try
    {
      localDateTime = paramFixedDepositInstrument.getDataDate();
      str = paramFixedDepositInstrument.getInstrumentNumber();
      localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_byte);
      localPreparedStatement.setInt(1, paramInt);
      DCUtil.fillTimestampColumn(localPreparedStatement, 2, localDateTime);
      localPreparedStatement.setString(3, str);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_byte);
      if (localResultSet.next()) {
        l = localResultSet.getLong(1);
      } else {
        throw new DCException(434, "Error retrieving EXTENDABEANXMLID: No records in DC_FIXDEPINSTRMENT table with DCACCOUNTID " + paramInt + " and DATADATE " + localDateTime);
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
  
  private static void a(FixedDepositInstrument paramFixedDepositInstrument1, FixedDepositInstrument paramFixedDepositInstrument2)
  {
    if ((paramFixedDepositInstrument1 != null) && (paramFixedDepositInstrument2 != null)) {
      paramFixedDepositInstrument1.putAll(paramFixedDepositInstrument2);
    }
  }
  
  public static FixedDepositInstruments getFixedDepositInstruments(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
    FixedDepositInstruments localFixedDepositInstruments = new FixedDepositInstruments();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      localConnection = DCAdapter.getDBConnection();
      if (paramCalendar1 != null)
      {
        if (paramCalendar2 != null)
        {
          localStringBuffer.append(jdField_for);
          DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
          localStringBuffer.append(jdField_case);
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
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
          localStringBuffer.append(jdField_case);
          localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
          localPreparedStatement.setString(1, paramAccount.getID());
          localPreparedStatement.setString(2, paramAccount.getBankID());
          DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramCalendar1);
          localPreparedStatement.setString(4, str);
          if (paramAccount.getRoutingNum() != null) {
            localPreparedStatement.setString(4, paramAccount.getRoutingNum());
          }
          localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        }
      }
      else if (paramCalendar2 != null)
      {
        localStringBuffer.append(a);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
        localStringBuffer.append(jdField_case);
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
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
        localStringBuffer.append(jdField_goto);
        DCUtil.appendNullWhereClause(localStringBuffer, jdField_new, paramAccount.getRoutingNum());
        localStringBuffer.append(jdField_case);
        localPreparedStatement = DCAdapter.getStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, paramAccount.getID());
        localPreparedStatement.setString(2, paramAccount.getBankID());
        localPreparedStatement.setString(3, str);
        if (paramAccount.getRoutingNum() != null) {
          localPreparedStatement.setString(4, paramAccount.getRoutingNum());
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      }
      while (localResultSet.next())
      {
        localObject1 = a(paramAccount, localResultSet, localConnection);
        localFixedDepositInstruments.add(localObject1);
      }
      Object localObject1 = localFixedDepositInstruments;
      return localObject1;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 415;
      DCException localDCException2 = new DCException(i, "Failed to get fixed deposit instruments.", localException);
      throw localDCException2;
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
      if (localConnection != null) {
        DCAdapter.releaseDBConnection(localConnection);
      }
    }
  }
  
  private static Object[] a(FixedDepositInstrument paramFixedDepositInstrument, Connection paramConnection)
    throws DCException
  {
    FixedDepositInstrument localFixedDepositInstrument = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Object[] arrayOfObject1 = new Object[2];
    try
    {
      String str = paramFixedDepositInstrument.getRoutingNumber();
      if (str != null)
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_else);
        localPreparedStatement.setString(1, paramFixedDepositInstrument.getAccountID());
        localPreparedStatement.setString(2, paramFixedDepositInstrument.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramFixedDepositInstrument.getDataDate());
        localPreparedStatement.setString(4, paramFixedDepositInstrument.getInstrumentNumber());
        localPreparedStatement.setString(5, paramFixedDepositInstrument.getInstrumentBankName());
        localPreparedStatement.setString(6, paramFixedDepositInstrument.getRoutingNumber());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_else);
      }
      else
      {
        localPreparedStatement = DCAdapter.getStatement(paramConnection, jdField_int);
        localPreparedStatement.setString(1, paramFixedDepositInstrument.getAccountID());
        localPreparedStatement.setString(2, paramFixedDepositInstrument.getBankID());
        DCUtil.fillTimestampColumn(localPreparedStatement, 3, paramFixedDepositInstrument.getDataDate());
        localPreparedStatement.setString(4, paramFixedDepositInstrument.getInstrumentNumber());
        localPreparedStatement.setString(5, paramFixedDepositInstrument.getInstrumentBankName());
        localResultSet = DBUtil.executeQuery(localPreparedStatement, jdField_int);
      }
      if (localResultSet.next())
      {
        localFixedDepositInstrument = a(paramFixedDepositInstrument, localResultSet, paramConnection);
        arrayOfObject1[0] = localFixedDepositInstrument;
        arrayOfObject1[1] = new Integer(localResultSet.getInt(37));
      }
      else
      {
        arrayOfObject1[0] = null;
        arrayOfObject1[1] = null;
      }
      Object[] arrayOfObject2 = arrayOfObject1;
      return arrayOfObject2;
    }
    catch (DCException localDCException1)
    {
      throw localDCException1;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 415;
      DCException localDCException2 = new DCException(i, "Failed to get a fixed deposit instrument.", localException);
      throw localDCException2;
    }
    finally
    {
      localPreparedStatement = null;
      DBUtil.closeResultSet(localResultSet);
    }
  }
  
  private static FixedDepositInstrument a(FixedDepositInstrument paramFixedDepositInstrument, ResultSet paramResultSet, Connection paramConnection)
    throws DCException
  {
    try
    {
      FixedDepositInstrument localFixedDepositInstrument = new FixedDepositInstrument();
      localFixedDepositInstrument.setAccountID(paramFixedDepositInstrument.getAccountID());
      localFixedDepositInstrument.setAccountNumber(paramFixedDepositInstrument.getAccountNumber());
      localFixedDepositInstrument.setBankID(paramFixedDepositInstrument.getBankID());
      localFixedDepositInstrument.setRoutingNumber(paramFixedDepositInstrument.getRoutingNumber());
      localFixedDepositInstrument.setDataDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramFixedDepositInstrument.getLocale()));
      localFixedDepositInstrument.setInstrumentNumber(paramResultSet.getString(2));
      localFixedDepositInstrument.setInstrumentBankName(paramResultSet.getString(3));
      localFixedDepositInstrument.setCurrency(paramResultSet.getString(4));
      localFixedDepositInstrument.setPrincipalAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), paramFixedDepositInstrument.getCurrency(), paramFixedDepositInstrument.getLocale()));
      localFixedDepositInstrument.setAccruedInterest(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramFixedDepositInstrument.getCurrency(), paramFixedDepositInstrument.getLocale()));
      localFixedDepositInstrument.setInterestAtMaturity(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramFixedDepositInstrument.getCurrency(), paramFixedDepositInstrument.getLocale()));
      localFixedDepositInstrument.setProceedsAtMaturity(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramFixedDepositInstrument.getCurrency(), paramFixedDepositInstrument.getLocale()));
      localFixedDepositInstrument.setValueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(9), paramFixedDepositInstrument.getLocale()));
      localFixedDepositInstrument.setMaturityDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(10), paramFixedDepositInstrument.getLocale()));
      localFixedDepositInstrument.setRestrictedAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramFixedDepositInstrument.getCurrency(), paramFixedDepositInstrument.getLocale()));
      localFixedDepositInstrument.setNumberOfRollovers(paramResultSet.getInt(12));
      localFixedDepositInstrument.setDaysInTerm(paramResultSet.getInt(13));
      localFixedDepositInstrument.setInterestRate(paramResultSet.getFloat(14));
      Contact localContact1 = new Contact();
      localContact1.setStreet(paramResultSet.getString(15));
      localContact1.setStreet2(paramResultSet.getString(16));
      localContact1.setCity(paramResultSet.getString(17));
      localContact1.setState(paramResultSet.getString(18));
      localContact1.setCountry(paramResultSet.getString(19));
      localContact1.setZipCode(paramResultSet.getString(20));
      localFixedDepositInstrument.setStatementMailingAddr1(localContact1);
      localObject = new Contact();
      ((Contact)localObject).setStreet(paramResultSet.getString(21));
      ((Contact)localObject).setStreet2(paramResultSet.getString(22));
      ((Contact)localObject).setCity(paramResultSet.getString(23));
      ((Contact)localObject).setState(paramResultSet.getString(24));
      ((Contact)localObject).setCountry(paramResultSet.getString(25));
      ((Contact)localObject).setZipCode(paramResultSet.getString(26));
      localFixedDepositInstrument.setStatementMailingAddr2((Contact)localObject);
      Contact localContact2 = new Contact();
      localContact2.setStreet(paramResultSet.getString(27));
      localContact2.setStreet2(paramResultSet.getString(28));
      localContact2.setCity(paramResultSet.getString(29));
      localContact2.setState(paramResultSet.getString(30));
      localContact2.setCountry(paramResultSet.getString(31));
      localContact2.setZipCode(paramResultSet.getString(32));
      localFixedDepositInstrument.setStatementMailingAddr3(localContact2);
      DCUtil.fillExtendABean(paramConnection, localFixedDepositInstrument, paramResultSet, 33);
      localFixedDepositInstrument.setSettlementInstructionType(paramResultSet.getInt(34));
      localFixedDepositInstrument.setSettlementTargetAccountNumber(paramResultSet.getString(35));
      localFixedDepositInstrument.setSettlementTargetRoutingNumber(paramResultSet.getString(36));
      return localFixedDepositInstrument;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 415;
      Object localObject = new DCException(i, "Failed to convert a database record into a fixed deposit instrument java bean.", localException);
      throw ((Throwable)localObject);
    }
  }
  
  private static FixedDepositInstrument a(Account paramAccount, ResultSet paramResultSet, Connection paramConnection)
    throws DCException
  {
    try
    {
      FixedDepositInstrument localFixedDepositInstrument = new FixedDepositInstrument();
      localFixedDepositInstrument.setAccountID(paramAccount.getID());
      localFixedDepositInstrument.setAccountNumber(paramAccount.getNumber());
      localFixedDepositInstrument.setBankID(paramAccount.getBankID());
      localFixedDepositInstrument.setRoutingNumber(paramAccount.getRoutingNum());
      localFixedDepositInstrument.setDataDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(1), paramAccount.getLocale()));
      localFixedDepositInstrument.setInstrumentNumber(paramResultSet.getString(2));
      localFixedDepositInstrument.setInstrumentBankName(paramResultSet.getString(3));
      localFixedDepositInstrument.setCurrency(paramResultSet.getString(4));
      localFixedDepositInstrument.setPrincipalAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(5), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localFixedDepositInstrument.setAccruedInterest(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localFixedDepositInstrument.setInterestAtMaturity(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(7), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localFixedDepositInstrument.setProceedsAtMaturity(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(8), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localFixedDepositInstrument.setValueDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(9), paramAccount.getLocale()));
      localFixedDepositInstrument.setMaturityDate(DCUtil.getTimestampColumn(paramResultSet.getTimestamp(10), paramAccount.getLocale()));
      localFixedDepositInstrument.setRestrictedAmount(DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), paramAccount.getCurrencyCode(), paramAccount.getLocale()));
      localFixedDepositInstrument.setNumberOfRollovers(paramResultSet.getInt(12));
      localFixedDepositInstrument.setDaysInTerm(paramResultSet.getInt(13));
      localFixedDepositInstrument.setInterestRate(paramResultSet.getFloat(14));
      Contact localContact1 = new Contact();
      localContact1.setStreet(paramResultSet.getString(15));
      localContact1.setStreet2(paramResultSet.getString(16));
      localContact1.setCity(paramResultSet.getString(17));
      localContact1.setState(paramResultSet.getString(18));
      localContact1.setCountry(paramResultSet.getString(19));
      localContact1.setZipCode(paramResultSet.getString(20));
      localFixedDepositInstrument.setStatementMailingAddr1(localContact1);
      localObject = new Contact();
      ((Contact)localObject).setStreet(paramResultSet.getString(21));
      ((Contact)localObject).setStreet2(paramResultSet.getString(22));
      ((Contact)localObject).setCity(paramResultSet.getString(23));
      ((Contact)localObject).setState(paramResultSet.getString(24));
      ((Contact)localObject).setCountry(paramResultSet.getString(25));
      ((Contact)localObject).setZipCode(paramResultSet.getString(26));
      localFixedDepositInstrument.setStatementMailingAddr2((Contact)localObject);
      Contact localContact2 = new Contact();
      localContact2.setStreet(paramResultSet.getString(27));
      localContact2.setStreet2(paramResultSet.getString(28));
      localContact2.setCity(paramResultSet.getString(29));
      localContact2.setState(paramResultSet.getString(30));
      localContact2.setCountry(paramResultSet.getString(31));
      localContact2.setZipCode(paramResultSet.getString(32));
      localFixedDepositInstrument.setStatementMailingAddr3(localContact2);
      DCUtil.fillExtendABean(paramConnection, localFixedDepositInstrument, paramResultSet, 33);
      localFixedDepositInstrument.setSettlementInstructionType(paramResultSet.getInt(34));
      localFixedDepositInstrument.setSettlementTargetAccountNumber(paramResultSet.getString(35));
      localFixedDepositInstrument.setSettlementTargetRoutingNumber(paramResultSet.getString(36));
      return localFixedDepositInstrument;
    }
    catch (DCException localDCException)
    {
      throw localDCException;
    }
    catch (Exception localException)
    {
      int i = (localException instanceof SQLException) ? 302 : 415;
      Object localObject = new DCException(i, "Failed to convert a database record into a fixed deposit instrument java bean.", localException);
      throw ((Throwable)localObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCFixDepInstrment
 * JD-Core Version:    0.7.0.1
 */