package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
import com.ffusion.beans.register.RegisterPayee;
import com.ffusion.beans.register.RegisterPayees;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.ServerTransaction;
import com.ffusion.beans.register.TransactionCategories;
import com.ffusion.beans.register.TransactionCategory;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.handlers.UtilHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.IntMap;
import com.ffusion.util.MapUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class RegisterAdapter
  implements ProfileDefines
{
  private static int ah2 = -1;
  private static String ahX = "MM/dd/yyyy HH:mm:ss";
  private static String ah0 = "T";
  private static String ahY = "String";
  private static int ah3 = 20;
  protected static final int TRANSACTION_NOT_FOUND = -1;
  protected static final String REG_CATEGORY_EXPORT_SQL = "select ruc.reg_user_cat_id, ruc.directory_id, ruc.name, ruc2.name, ruc.description, ruc.type, ruc.tax_related, ruc.parent_category_id from reg_user_category ruc left outer join reg_user_category ruc2 on ( ruc.parent_category_id = ruc2.reg_user_cat_id ) where ruc.directory_id = ? order by ruc2.name, ruc.name ";
  protected static final String REG_TRAN_RPT_EXPORT_SQL = "select rt.reg_transaction_id, rt.directory_id, rt.account_id, rt.fi_transaction_id, rt.reference_number, rt.payee_name, rt.type, rt.status, rt.memo, rt.date_issued, rt.date_cleared, rt.rec_server_tran_id, rt.server_tran_id, rtc.reg_user_cat_id, rtc.amount, ruc2.name, ruc.name from reg_transaction rt, reg_tran_category rtc, reg_user_category ruc left outer join reg_user_category ruc2 on ( ruc2.reg_user_cat_id = ruc.parent_category_id and ruc2.directory_id=?) where rt.reg_transaction_id = rtc.reg_transaction_id and ruc.reg_user_cat_id = rtc.reg_user_cat_id and rt.directory_id=? and rt.account_id=? and rtc.directory_id=ruc.directory_id";
  protected static final String REG_TRAN_RPT_EXPORT_ORDER_SQL = "order by rt.date_issued, rt.reg_transaction_id, ruc2.name, ruc.name ";
  protected static final String REG_TRANTYPEDESC_FLUSH_SQL = "delete from reg_transtypedesc ";
  protected static final String REG_TRANTYPEDESC_ADD_SQL = "insert into reg_transtypedesc ( trans_type_id, language, trans_type_desc ) values ( ?, ?, ?)";
  protected static final String SQL_GET_PAGED_TRANSACTIONS = "select rt.* from reg_transaction rt ";
  protected static final String SQL_GET_PAGED_TRANSACTIONS_CATEGORIES = "select rtc.reg_user_cat_id, rtc.amount, ruc2.name, ruc.name from reg_tran_category rtc, (select * from reg_user_category where directory_id = ?) ruc left outer join (select * from reg_user_category where directory_id = ?) ruc2 on ( ruc2.reg_user_cat_id = ruc.parent_category_id ) where rtc.reg_transaction_id=? and ruc.reg_user_cat_id=rtc.reg_user_cat_id and ruc.directory_id=rtc.directory_id ";
  protected static int PAGESIZE = 10;
  protected static String KEY_REGISTER_DISPLAY_TEXT_CAT_SUBCAT = "RegisterDisplayTextCatSubcat";
  protected static final String REG_REPORT_SQL_1 = "SELECT a.reg_transaction_id, a.directory_id, a.account_id, a.fi_transaction_id, a.reference_number, a.payee_name, a.type, a.status, a.memo, a.date_issued, a.date_cleared, a.rec_server_tran_id, a.server_tran_id, a.amount, d.name as parent_cat_name, c.name as cat_name, c.tax_related, e.trans_type_desc, b.amount as category_amount FROM reg_transaction a, reg_tran_category b, reg_user_category c LEFT OUTER JOIN reg_user_category d ON (d.reg_user_cat_id= c.parent_category_id and d.directory_id=?), reg_transtypedesc e WHERE a.reg_transaction_id= b.reg_transaction_id AND b.reg_user_cat_id= c.reg_user_cat_id AND a.type= e.trans_type_id AND e.language= ? AND a.directory_id= ? AND a.account_id= ? AND b.directory_id=c.directory_id ";
  protected static final String REG_REPORT_SQL_REC_STATUS_UNMATCHED = "AND (a.status = 0) ";
  protected static final String REG_REPORT_SQL_REC_STATUS_MATCHED = "AND (a.status = 2 OR a.status = 3 OR a.status = 4) ";
  protected static final String REG_REPORT_SQL_TAX_STATUS = "AND c.tax_related= '" + ah0 + "' ";
  protected static final String REG_REPORT_SQL_INCOME = "AND b.amount>= 0 ";
  protected static final String REG_REPORT_SQL_EXPENSE = "AND b.amount< 0 ";
  protected static final String REG_REPORT_SQL_ADV_TRAN = "AND a.type= ? ";
  protected static final String REG_REPORT_SQL_ADV_REF = "AND a.reference_number= ? ";
  protected static final String REG_REPORT_SQL_ADV_PAYEE = " AND a.payee_name= ? ";
  protected static final String REG_REPORT_SQL_ADV_CAT = "AND b.reg_user_cat_id= ? ";
  protected static final String REG_REPORT_SQL_ADV_MEMO = "AND a.memo LIKE ? ";
  protected static final String REG_REPORT_SQL_ADV_TAX = "AND c.tax_related= '" + ah0 + "' ";
  protected static final String REG_REPORT_SQL_ORDER_CATEGORY_TOTALS = "ORDER BY d.name, c.name, a.date_issued, a.reg_transaction_id ";
  protected static final String REG_REPORT_SQL_ORDER_TRANSACTION_TYPE_TOTALS = "ORDER BY e.trans_type_desc, a.date_issued, a.reg_transaction_id ";
  protected static final String REG_REPORT_SQL_ORDER_PAYEE_TOTALS = "ORDER BY a.payee_name, a.date_issued, a.reg_transaction_id ";
  protected static final String REG_REPORT_SQL_ORDER_RECONCILIATION_STATUS = "ORDER BY a.date_issued, a.reg_transaction_id ";
  protected static final int REG_REPORT_SQL_ACCOUNT_PARAM_SEQ = 4;
  protected static int matchRange = 5;
  protected static final String REG_MOD_TRAN_BY_SVRTID = "select reg_transaction.reg_transaction_id, reg_transaction.amount from reg_transaction, reg_tran_category where reg_transaction.reg_transaction_id = reg_tran_category.reg_transaction_id and server_tran_id=? ";
  private static final String ahZ = "UPDATE reg_transaction SET status= 2 WHERE status = 1 AND date_cleared IS NOT NULL AND date_cleared < ? AND directory_id = ?";
  private static int ah1 = 30;
  private static final String ah4 = "categoryAmount";
  
  public static void initialize(HashMap paramHashMap)
  {
    String str1 = "RegisterAdapter.initialize()";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from reg_transtypedesc ");
      DBUtil.execute(localPreparedStatement, "delete from reg_transtypedesc ");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into reg_transtypedesc ( trans_type_id, language, trans_type_desc ) values ( ?, ?, ?)");
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(null, null);
      Iterator localIterator = localLanguageDefns.iterator();
      while (localIterator.hasNext()) {
        try
        {
          LanguageDefn localLanguageDefn = (LanguageDefn)localIterator.next();
          Locale localLocale = new Locale(localLanguageDefn.getLanguage().substring(0, 2), localLanguageDefn.getLanguage().substring(3, 5));
          ResourceBundle localResourceBundle1 = ResourceBundle.getBundle("com.ffusion.beans.register.resources", localLocale);
          ResourceBundle localResourceBundle2 = ResourceBundle.getBundle(localResourceBundle1.getString("TransactionTypeResourceFile"), localLocale);
          String str2 = localResourceBundle1.getString("RegTypes");
          StringTokenizer localStringTokenizer = new StringTokenizer(str2, ",");
          while (localStringTokenizer.hasMoreTokens())
          {
            String str3 = localStringTokenizer.nextToken();
            localPreparedStatement.setInt(1, Integer.parseInt(str3));
            localPreparedStatement.setString(2, localLanguageDefn.getLanguage());
            localPreparedStatement.setString(3, localResourceBundle2.getString("TransactionType" + str3));
            DBUtil.executeUpdate(localPreparedStatement, "insert into reg_transtypedesc ( trans_type_id, language, trans_type_desc ) values ( ?, ?, ?)");
          }
        }
        catch (Exception localException2)
        {
          DebugLog.log(Level.FINER, str1 + ": Exception caught while processing resource files needed for Register Transactions");
        }
      }
      ahY = MapUtil.getStringValue(paramHashMap, "REF_NUM_TYPE", ahY);
      ah3 = O();
      ah1 = MapUtil.getIntValue(paramHashMap, "NUMBER_OF_DAYS_BEFORE_AUTO_RECONCILE", 30);
    }
    catch (Exception localException1)
    {
      try
      {
        Profile.handleError(localException1, str1);
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.log(Level.FINER, str1 + ": Exception caught before able to process resource files needed for Register Transactions");
      }
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public static void addBankTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addBankTransactions()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterTransactions)) {
      logInvalidRequest(str1, "Invalid register transactions.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      Object localObject1 = null;
      String str2 = null;
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      synchronized (paramSecureUser)
      {
        Iterator localIterator = paramRegisterTransactions.iterator();
        while (localIterator.hasNext())
        {
          RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
          String str3 = localRegisterTransaction.getID();
          DateTime localDateTime = localRegisterTransaction.getDateValue();
          if (str2 == null)
          {
            str2 = (String)localRegisterTransaction.get("ACCOUNT");
            if (!Profile.hasValue(str2))
            {
              DBUtil.rollback(localConnection);
              logInvalidRequest(str1, "Invalid account ID.");
            }
          }
          if (!Profile.hasValue(str3))
          {
            DBUtil.rollback(localConnection);
            logInvalidRequest(str1, "Invalid financial institution transaction ID.");
          }
          if (!Profile.hasValue(localDateTime))
          {
            DBUtil.rollback(localConnection);
            logInvalidRequest(str1, "Invalid transaction clear date.");
          }
          if ((localObject1 == null) || (localDateTime.after(localObject1))) {
            localObject1 = localDateTime;
          }
          if (!transactionExists(localConnection, str3, str2, i, true)) {
            localRegisterTransactions.add(localRegisterTransaction);
          }
        }
        if (localRegisterTransactions.size() > 0)
        {
          addTransactions(localConnection, localRegisterTransactions, paramSecureUser, false);
          addTransactionCategories(localConnection, localRegisterTransactions, i);
          modifyRetrievalDate(localConnection, i, str2, localObject1);
          try
          {
            jdMethod_for(localConnection, paramSecureUser, ah1, new HashMap());
          }
          catch (ProfileException localProfileException)
          {
            DebugLog.throwing("The system was unable to automatically reconcile bank transactions older than " + ah1 + "days due to an error.", localProfileException);
          }
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  protected static boolean updateExistingRecurringTransactions(Connection paramConnection, String paramString1, String paramString2)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("update ").append("reg_transaction").append(" set ").append("fi_transaction_id");
    localStringBuffer.append(" = ? where ").append("rec_server_tran_id").append(" = ? and ");
    localStringBuffer.append("fi_transaction_id").append(" is null");
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramString2);
      localPreparedStatement.setString(2, paramString1);
      int i = localPreparedStatement.executeUpdate();
      if (i == 0)
      {
        boolean bool = false;
        return bool;
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    return true;
  }
  
  public static void addRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addRegisterCategory()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterCategory)) {
      logInvalidRequest(str1, "Invalid register category.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    String str2 = paramRegisterCategory.getName();
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.hasValue(str2)) {
      logInvalidRequest(str1, "Invalid category name.");
    }
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int j = DBUtil.getNextId(localConnection, Profile.dbType, "reg_user_cat_id");
      paramRegisterCategory.setId(j);
      String str3 = buildAddRegisterCategorySql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str3);
      prepareAddRegisterCategory(localPreparedStatement, paramRegisterCategory, i);
      DBUtil.executeUpdate(localPreparedStatement, str3);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void addRegisterPayee(RegisterPayee paramRegisterPayee, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addRegisterPayee()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterPayee)) {
      logInvalidRequest(str1, "Invalid register payee.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    String str2 = paramRegisterPayee.getName();
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.hasValue(str2)) {
      logInvalidRequest(str1, "Invalid payee name.");
    }
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int j = DBUtil.getNextId(localConnection, Profile.dbType, "reg_payee_id");
      paramRegisterPayee.setId(j);
      String str3 = buildAddRegisterPayeeSql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str3);
      prepareAddRegisterPayee(localPreparedStatement, paramRegisterPayee, i);
      DBUtil.executeUpdate(localPreparedStatement, str3.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void addRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addRegisterTransaction()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    String str2 = (String)paramRegisterTransaction.get("ACCOUNT");
    if (!Profile.hasValue(paramRegisterTransaction)) {
      logInvalidRequest(str1, "Invalid register transaction.");
    }
    if (!Profile.hasValue(str2)) {
      logInvalidRequest(str1, "Invalid account ID.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    if (!validTransactionCategories(paramRegisterTransaction)) {
      logInvalidRequest(str1, "Invalid transaction categories.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      int j = paramRegisterTransaction.getStatusValue();
      boolean bool = jdMethod_new(localConnection, paramRegisterTransaction, paramSecureUser);
      try
      {
        jdMethod_for(localConnection, paramSecureUser, ah1, new HashMap());
      }
      catch (ProfileException localProfileException)
      {
        DebugLog.throwing("The system was unable to automatically reconcile bank transactions older than " + ah1 + "days due to an error.", localProfileException);
      }
      if (((bool) && (j == 0)) || (!bool))
      {
        int k = DBUtil.getNextId(localConnection, Profile.dbType, "reg_transaction_id");
        paramRegisterTransaction.setRegisterId(k);
        String str3 = buildAddRegisterTransactionSql();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, str3);
        prepareAddRegisterTransaction(localPreparedStatement, paramRegisterTransaction, i, str2);
        DBUtil.executeUpdate(localPreparedStatement, str3);
        addTransactionCategories(localConnection, paramRegisterTransaction, i);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void addRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "RegisterAdapter.addRegisterTransactions()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterTransactions)) {
      logInvalidRequest(str, "Invalid register transactions.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      addTransactions(localConnection, paramRegisterTransactions, paramSecureUser, true);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void addUpdateRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addUpdateRegisterTransactions()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterTransactions)) {
      logInvalidRequest(str1, "Invalid register transactions.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      RegisterTransactions localRegisterTransactions1 = new RegisterTransactions();
      RegisterTransactions localRegisterTransactions2 = new RegisterTransactions();
      Object localObject2;
      synchronized (paramSecureUser)
      {
        localObject1 = paramRegisterTransactions.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (RegisterTransaction)((Iterator)localObject1).next();
          String str2 = (String)((RegisterTransaction)localObject2).get("SERVER_TID");
          String str3 = (String)((RegisterTransaction)localObject2).get("ACCOUNT");
          if (!validTransactionCategories((RegisterTransaction)localObject2))
          {
            logInvalidRequest(str1, "Invalid transaction categories for transaction: " + str2);
          }
          else
          {
            int j = getTransactionStatus(localConnection, str2, str3, i, false);
            if (j == -1) {
              localRegisterTransactions2.add(localObject2);
            } else if (j == 0) {
              localRegisterTransactions1.add(localObject2);
            }
          }
        }
        addTransactions(localConnection, localRegisterTransactions2, paramSecureUser, true);
        modifyTransactionsByServerTID(localConnection, localRegisterTransactions1, paramSecureUser);
      }
      ??? = new ArrayList();
      Object localObject1 = localRegisterTransactions2.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (RegisterTransaction)((Iterator)localObject1).next();
        if ((((RegisterTransaction)localObject2).getRegisterTypeValue() == 25) && (!Profile.hasValue(((RegisterTransaction)localObject2).get("RECSRVRTID")))) {
          ((ArrayList)???).add(((RegisterTransaction)localObject2).getID());
        }
      }
      if (((ArrayList)???).size() > 0)
      {
        localObject1 = new StringBuffer();
        ((StringBuffer)localObject1).append("delete from ").append("reg_srvr_tran_cat");
        ((StringBuffer)localObject1).append(" where ").append("server_tran_id").append("=?");
        localPreparedStatement = DBUtil.prepareStatement(localConnection, ((StringBuffer)localObject1).toString());
        localObject2 = ((ArrayList)???).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localPreparedStatement.setString(1, (String)((Iterator)localObject2).next());
          localPreparedStatement.executeUpdate();
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void addSrvrTranRegisterCategory(ArrayList paramArrayList)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addSrvrTranRegisterCategory()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramArrayList)) {
      logInvalidRequest(str1, "Invalid server transactions collection.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      String str2 = buildAddSrvrTranRegisterCategorySql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        ServerTransaction localServerTransaction = (ServerTransaction)localIterator.next();
        if ((!Profile.hasValue(localServerTransaction.getServerTID())) && (!Profile.hasValue(localServerTransaction.getRecServerTID())))
        {
          DBUtil.rollback(localConnection);
          logInvalidRequest(str1, "Server transaction requires a serverTID or recServerTID.");
        }
        int i = localServerTransaction.getRegCategoryID();
        if (i < 0)
        {
          DBUtil.rollback(localConnection);
          logInvalidRequest(str1, "Invalid register category ID.");
        }
        prepareAddSrvrTranRegisterCategory(localPreparedStatement, localServerTransaction);
        DBUtil.executeUpdate(localPreparedStatement, str2);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void modifySrvrTranRegisterCategory(ServerTransaction paramServerTransaction)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.modifySrvrTranRegisterCategory()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramServerTransaction)) {
      logInvalidRequest(str1, "Invalid server transaction.");
    }
    if ((!Profile.hasValue(paramServerTransaction.getServerTID())) && (!Profile.hasValue(paramServerTransaction.getRecServerTID()))) {
      logInvalidRequest(str1, "Server transaction requires a serverTID or recServerTID.");
    }
    if (paramServerTransaction.getRegCategoryID() < 0) {
      logInvalidRequest(str1, "Invalid register category ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    int i = 0;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = buildModifySrvrTranRegisterCategorySql(paramServerTransaction.getRecServerTID() != null);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareModifySrvrTranRegisterCategory(localPreparedStatement, paramServerTransaction);
      i = DBUtil.executeUpdate(localPreparedStatement, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    if (i == 0)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(paramServerTransaction);
      addSrvrTranRegisterCategory(localArrayList);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void deleteSrvrTranRegisterCategory(String paramString, boolean paramBoolean)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.deleteSrvrTranRegisterCategory()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramString)) {
      logInvalidRequest(str1, "Invalid recurring payment ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = buildDeleteSrvrTranRegisterCategorySql(paramBoolean);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareDeleteSrvrTranRegisterCategory(localPreparedStatement, paramString);
      DBUtil.executeUpdate(localPreparedStatement, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void deleteRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.deleteRegisterCategory()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterCategory)) {
      logInvalidRequest(str1, "Invalid register category.");
    }
    int i = Profile.convertToInt(paramRegisterCategory.getId(), -1);
    int j = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid category ID.");
    }
    if (!Profile.isValidId(j)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = buildDeleteRegisterCategorySql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareDeleteRegisterCategory(localPreparedStatement, i, j);
      DBUtil.executeUpdate(localPreparedStatement, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void deleteRegisterPayee(RegisterPayee paramRegisterPayee)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.deleteRegisterPayee()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterPayee)) {
      logInvalidRequest(str1, "Invalid register payee.");
    }
    int i = Profile.convertToInt(paramRegisterPayee.getId(), -1);
    if (i == -1) {
      logInvalidRequest(str1, "Invalid payee ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = buildDeleteRegisterPayeeSql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareDeleteRegisterPayee(localPreparedStatement, i);
      DBUtil.executeUpdate(localPreparedStatement, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void deleteRegisterTransaction(RegisterTransaction paramRegisterTransaction)
    throws ProfileException
  {
    String str = "RegisterAdapter.deleteRegisterTransaction()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      deleteTransaction(localConnection, paramRegisterTransaction);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void deleteRegisterTransactions(RegisterTransactions paramRegisterTransactions)
    throws ProfileException
  {
    String str = "RegisterAdapter.deleteRegisterTransactions()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterTransactions)) {
      logInvalidRequest(str, "Invalid register transactions.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      deleteTransactions(localConnection, paramRegisterTransactions);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void deleteOldUnreconciledRegisterTransactions(SecureUser paramSecureUser, int paramInt)
    throws ProfileException
  {
    String str = "RegisterAdapter.deleteOldUnreconciledTransactions()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directoryID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      DateTime localDateTime = new DateTime();
      localDateTime.add(6, -1 * paramInt);
      StringBuffer localStringBuffer = new StringBuffer(50);
      localStringBuffer.append("select ").append("reg_transaction_id").append(" from ");
      localStringBuffer.append("reg_transaction").append(" where ");
      localStringBuffer.append("directory_id").append(" = ? and ");
      localStringBuffer.append("status").append(" = ").append(0).append(" and ");
      localStringBuffer.append("date_issued").append(" < ? and (").append("type").append(" = ");
      localStringBuffer.append(25).append(" or ").append("type");
      localStringBuffer.append(" = ").append(16).append(")");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, i);
      localPreparedStatement.setDate(2, new java.sql.Date(localDateTime.getTime().getTime()));
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        int j = localResultSet.getInt(1);
        localStringBuffer.setLength(0);
        localStringBuffer.append("delete from ").append("reg_tran_category").append(" where ");
        localStringBuffer.append("reg_transaction_id").append(" = ?");
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setInt(1, j);
        DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
        localStringBuffer.setLength(0);
        localStringBuffer.append("delete from ").append("reg_transaction").append(" where ");
        localStringBuffer.append("reg_transaction_id").append(" = ?");
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setInt(1, j);
        DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void deleteRegisterTransactionsByServerTID(ArrayList paramArrayList)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.deleteRegisterTransactionsByServerTID(ArrayList)";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramArrayList)) {
      logInvalidRequest(str1, "Invalid server transaction IDs.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      String[] arrayOfString1 = { "reg_transaction_id" };
      String[] arrayOfString2 = { "server_tran_id", "status" };
      String str2 = ProfileUtil.buildSelectSql("reg_transaction", arrayOfString1, arrayOfString2, null, 50);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext()) {
        try
        {
          String str3 = (String)localIterator.next();
          localPreparedStatement.clearParameters();
          localPreparedStatement.setString(1, str3);
          localPreparedStatement.setInt(2, 0);
          localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
          while (localResultSet.next())
          {
            RegisterTransaction localRegisterTransaction = new RegisterTransaction();
            localRegisterTransaction.setRegisterId(localResultSet.getString(1));
            localRegisterTransactions.add(localRegisterTransaction);
          }
          DBUtil.closeResultSet(localResultSet);
        }
        finally
        {
          DBUtil.closeResultSet(localResultSet);
        }
      }
      deleteTransactions(localConnection, localRegisterTransactions);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  protected static ArrayList getRegisterIDsFromRecSrvrTID(Connection paramConnection, String paramString)
    throws ProfileException
  {
    String str = "RegisterAdapter.deleteRecurringTransactions()";
    logModerateActivity("Entered " + str);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("select ").append("reg_transaction_id");
    localStringBuffer.append(" from ").append("reg_transaction");
    localStringBuffer.append(" where ").append("rec_server_tran_id").append("=?");
    localStringBuffer.append(" and ").append("status").append("=?");
    ArrayList localArrayList = new ArrayList();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramString);
      localPreparedStatement.setInt(2, 0);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next()) {
        localArrayList.add(localResultSet.getString("reg_transaction_id"));
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(paramConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    logModerateActivity("Exited " + str);
    return localArrayList;
  }
  
  public static void getRegisterCategories(RegisterCategories paramRegisterCategories, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.getRegisterCategories()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (i == -1) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = buildGetRegisterCategoriesSql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareGetRegisterCategories(localPreparedStatement, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      while (localResultSet.next())
      {
        RegisterCategory localRegisterCategory = new RegisterCategory();
        fillRegisterCategoryBean(localRegisterCategory, localResultSet, false);
        paramRegisterCategories.add(localRegisterCategory);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void getRegisterDefaultCategories(RegisterCategories paramRegisterCategories)
    throws ProfileException
  {
    String str = "RegisterAdapter.getRegisterCategories()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    StringBuffer localStringBuffer = new StringBuffer(45);
    localStringBuffer.append("select * from ").append("reg_def_category");
    localStringBuffer.append(" order by ").append("name");
    Connection localConnection = null;
    Statement localStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localStatement = DBUtil.createStatement(localConnection);
      localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        RegisterCategory localRegisterCategory = new RegisterCategory();
        localRegisterCategory.setLocale(paramRegisterCategories.getLocale());
        fillRegisterCategoryBean(localRegisterCategory, localResultSet, true);
        paramRegisterCategories.add(localRegisterCategory);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void getRegisterPayees(RegisterPayees paramRegisterPayees, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.getRegisterPayees()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = buildGetRegisterPayeesSql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareGetRegisterPayees(localPreparedStatement, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      while (localResultSet.next())
      {
        RegisterPayee localRegisterPayee = new RegisterPayee();
        fillRegisterPayeeBean(localRegisterPayee, localResultSet);
        paramRegisterPayees.add(localRegisterPayee);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void getRegisterCategoriesForPayments(Payments paramPayments, RecPayments paramRecPayments, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.getRegisterCategoriesForPayments()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramPayments)) {
      logInvalidRequest(str1, "Invalid payments collection.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    Object localObject1 = paramPayments.listIterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Payment)((Iterator)localObject1).next();
      if (((Payment)localObject2).getRecPaymentID() == null) {
        if (RegisterUtil.isRegisterEnabled(((Payment)localObject2).getAccount())) {
          localArrayList1.add(((Payment)localObject2).getID());
        }
      }
    }
    localObject1 = paramRecPayments.listIterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (RecPayment)((Iterator)localObject1).next();
      if (RegisterUtil.isRegisterEnabled(((RecPayment)localObject2).getAccount()))
      {
        localArrayList2.add(((RecPayment)localObject2).getRecPaymentID());
        if (((RecPayment)localObject2).getNumberPaymentsValue() == 1) {
          localArrayList3.add(((RecPayment)localObject2).getRecPaymentID());
        }
      }
    }
    localObject1 = null;
    Object localObject2 = null;
    ResultSet localResultSet = null;
    try
    {
      localObject1 = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer;
      Iterator localIterator;
      Object localObject3;
      if (localArrayList1.size() > 0) {
        try
        {
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select ").append("server_tran_id").append(",").append("reg_user_cat_id");
          localStringBuffer.append(" from ").append("reg_srvr_tran_cat").append(" where ");
          localStringBuffer.append("server_tran_id").append("=?");
          localObject2 = DBUtil.prepareStatement((Connection)localObject1, localStringBuffer.toString());
          localIterator = localArrayList1.iterator();
          while (localIterator.hasNext()) {
            try
            {
              ((PreparedStatement)localObject2).setString(1, (String)localIterator.next());
              localResultSet = DBUtil.executeQuery((PreparedStatement)localObject2, localStringBuffer.toString());
              if (localResultSet.next())
              {
                localObject3 = paramPayments.getByID(localResultSet.getString("server_tran_id"));
                if (localObject3 != null) {
                  ((Payment)localObject3).set("REGISTER_CATEGORY_ID", localResultSet.getString("reg_user_cat_id"));
                }
              }
              DBUtil.closeResultSet(localResultSet);
            }
            finally
            {
              DBUtil.closeResultSet(localResultSet);
            }
          }
        }
        finally
        {
          DBUtil.closeStatement((PreparedStatement)localObject2);
        }
      }
      if (localArrayList2.size() > 0) {
        try
        {
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("select ").append("rec_server_tran_id").append(",").append("reg_user_cat_id");
          localStringBuffer.append(" from ").append("reg_srvr_tran_cat").append(" where ");
          localStringBuffer.append("rec_server_tran_id").append("=?");
          localObject2 = DBUtil.prepareStatement((Connection)localObject1, localStringBuffer.toString());
          localIterator = localArrayList2.iterator();
          while (localIterator.hasNext()) {
            try
            {
              ((PreparedStatement)localObject2).setString(1, (String)localIterator.next());
              localResultSet = DBUtil.executeQuery((PreparedStatement)localObject2, localStringBuffer.toString());
              while (localResultSet.next())
              {
                localObject3 = localResultSet.getString("rec_server_tran_id");
                String str2 = localResultSet.getString("reg_user_cat_id");
                RecPayment localRecPayment = paramRecPayments.getByRecID((String)localObject3);
                if (localRecPayment != null) {
                  localRecPayment.set("REGISTER_CATEGORY_ID", str2);
                }
                Payments localPayments = paramPayments.getByRecPaymentID((String)localObject3);
                if (localPayments != null)
                {
                  ListIterator localListIterator = localPayments.listIterator();
                  while (localListIterator.hasNext())
                  {
                    Payment localPayment = (Payment)localListIterator.next();
                    localPayment.set("REGISTER_CATEGORY_ID", str2);
                  }
                }
              }
              DBUtil.closeResultSet(localResultSet);
            }
            finally
            {
              DBUtil.closeResultSet(localResultSet);
            }
          }
        }
        finally
        {
          DBUtil.closeStatement((PreparedStatement)localObject2);
        }
      }
      if (localArrayList3.size() > 0) {
        try
        {
          localStringBuffer = new StringBuffer();
          localStringBuffer.append("delete from ").append("reg_srvr_tran_cat");
          localStringBuffer.append(" where ").append("rec_server_tran_id").append("=?");
          localObject2 = DBUtil.prepareStatement((Connection)localObject1, localStringBuffer.toString());
          localIterator = localArrayList3.iterator();
          while (localIterator.hasNext())
          {
            ((PreparedStatement)localObject2).setString(1, (String)localIterator.next());
            DBUtil.executeUpdate((PreparedStatement)localObject2, localStringBuffer.toString());
          }
        }
        finally
        {
          DBUtil.closeStatement((PreparedStatement)localObject2);
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, (Connection)localObject1);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void getRegisterCategoriesForTransfers(Transfers paramTransfers, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.getRegisterCategoriesForTransfers()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramTransfers)) {
      logInvalidRequest(str1, "Invalid transfers collection.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    Object localObject1 = paramTransfers.listIterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Transfer)((Iterator)localObject1).next();
      if ((RegisterUtil.isRegisterEnabled(((Transfer)localObject2).getFromAccount())) || (RegisterUtil.isRegisterEnabled(((Transfer)localObject2).getToAccount()))) {
        if ((localObject2 instanceof RecTransfer))
        {
          localArrayList2.add(localObject2);
          localObject3 = (RecTransfer)localObject2;
          if (((RecTransfer)localObject3).getNumberTransfersValue() <= 1) {
            localArrayList3.add(((RecTransfer)localObject3).getRecTransferID());
          }
        }
        else if (((Transfer)localObject2).getRecTransferID() != null)
        {
          localArrayList2.add(localObject2);
        }
        else
        {
          localArrayList1.add(localObject2);
        }
      }
    }
    localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    try
    {
      localObject1 = DBUtil.getConnection(Profile.poolName, true, 2);
      Object localObject4;
      Object localObject5;
      Object localObject6;
      if (localArrayList1.size() > 0)
      {
        localObject4 = new StringBuffer();
        ((StringBuffer)localObject4).append("select ").append("server_tran_id").append(",").append("reg_user_cat_id");
        ((StringBuffer)localObject4).append(" from ").append("reg_srvr_tran_cat").append(" where ");
        ((StringBuffer)localObject4).append("server_tran_id").append("=?");
        localObject2 = DBUtil.prepareStatement((Connection)localObject1, ((StringBuffer)localObject4).toString());
        localObject5 = localArrayList1.iterator();
        while (((Iterator)localObject5).hasNext())
        {
          localObject6 = (Transfer)((Iterator)localObject5).next();
          ((PreparedStatement)localObject2).setString(1, "tx_" + ((Transfer)localObject6).getID());
          localObject3 = DBUtil.executeQuery((PreparedStatement)localObject2, ((StringBuffer)localObject4).toString());
          if (((ResultSet)localObject3).next()) {
            ((Transfer)localObject6).set("REGISTER_CATEGORY_ID", ((ResultSet)localObject3).getString("reg_user_cat_id"));
          }
          DBUtil.closeResultSet((ResultSet)localObject3);
          localObject3 = null;
        }
        DBUtil.closeStatement((PreparedStatement)localObject2);
        localObject2 = null;
      }
      if (localArrayList2.size() > 0)
      {
        localObject4 = new HashMap();
        localObject5 = new StringBuffer();
        ((StringBuffer)localObject5).append("select ").append("rec_server_tran_id").append(",").append("reg_user_cat_id");
        ((StringBuffer)localObject5).append(" from ").append("reg_srvr_tran_cat").append(" where ");
        ((StringBuffer)localObject5).append("rec_server_tran_id").append("=?");
        localObject2 = DBUtil.prepareStatement((Connection)localObject1, ((StringBuffer)localObject5).toString());
        localObject6 = localArrayList2.iterator();
        while (((Iterator)localObject6).hasNext())
        {
          Transfer localTransfer = (Transfer)((Iterator)localObject6).next();
          String str2 = (String)((HashMap)localObject4).get(localTransfer.getRecTransferID());
          if (str2 != null)
          {
            localTransfer.set("REGISTER_CATEGORY_ID", str2);
          }
          else
          {
            ((PreparedStatement)localObject2).setString(1, "tx_" + localTransfer.getRecTransferID());
            localObject3 = DBUtil.executeQuery((PreparedStatement)localObject2, ((StringBuffer)localObject5).toString());
            if (((ResultSet)localObject3).next())
            {
              str2 = ((ResultSet)localObject3).getString("reg_user_cat_id");
              localTransfer.set("REGISTER_CATEGORY_ID", str2);
              ((HashMap)localObject4).put(localTransfer.getRecTransferID(), str2);
            }
            DBUtil.closeResultSet((ResultSet)localObject3);
            localObject3 = null;
          }
        }
        DBUtil.closeStatement((PreparedStatement)localObject2);
        localObject2 = null;
      }
      if (localArrayList3.size() > 0)
      {
        localObject4 = new StringBuffer();
        ((StringBuffer)localObject4).append("delete from ").append("reg_srvr_tran_cat");
        ((StringBuffer)localObject4).append(" where ").append("rec_server_tran_id").append("=?");
        localObject2 = DBUtil.prepareStatement((Connection)localObject1, ((StringBuffer)localObject4).toString());
        localObject5 = localArrayList3.iterator();
        while (((Iterator)localObject5).hasNext())
        {
          ((PreparedStatement)localObject2).setString(1, "tx_" + (String)((Iterator)localObject5).next());
          DBUtil.executeUpdate((PreparedStatement)localObject2, ((StringBuffer)localObject4).toString());
        }
        DBUtil.closeStatement((PreparedStatement)localObject2);
        localObject2 = null;
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll((PreparedStatement)localObject2, (ResultSet)localObject3);
      DBUtil.returnConnection(Profile.poolName, (Connection)localObject1);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void getRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.getRegisterTransaction()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    String str2 = paramRegisterTransaction.getReferenceNumber();
    String str3 = (String)paramRegisterTransaction.get("ACCOUNT");
    if (!Profile.hasValue(str2)) {
      logInvalidRequest(str1, "Invalid reference number.");
    }
    if (!Profile.hasValue(str3)) {
      logInvalidRequest(str1, "Invalid account ID.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str4 = buildGetRegisterTransactionSql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str4);
      prepareGetRegisterTransaction(localPreparedStatement, i, str2, str3);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str4);
      if (localResultSet.next())
      {
        paramRegisterTransaction.setLocale(paramSecureUser.getLocale());
        fillRegisterTransactionBean(paramRegisterTransaction, localResultSet);
        getTransactionCategories(localConnection, paramRegisterTransaction);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void getRegisterTransactions(RegisterTransactions paramRegisterTransactions, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "RegisterAdapter.getRegisterTransactions()";
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      jdMethod_for(localConnection, paramRegisterTransactions, paramDateTime1, paramDateTime2, paramBoolean, paramSecureUser);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, RegisterTransactions paramRegisterTransactions, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.getRegisterTransactions()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    RegisterTransaction localRegisterTransaction1 = (RegisterTransaction)paramRegisterTransactions.get(0);
    String str2 = (String)localRegisterTransaction1.get("ACCOUNT");
    String str3 = (String)localRegisterTransaction1.get("CURRENCY_CODE");
    String str4 = localRegisterTransaction1.getReferenceNumber();
    paramRegisterTransactions.clear();
    if (!Profile.hasValue(str2)) {
      logInvalidRequest(str1, "Invalid account ID.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      String str5 = buildGetRegisterTransactionsSql(str4, paramDateTime1, paramDateTime2, paramBoolean);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str5);
      prepareGetRegisterTransactions(localPreparedStatement, i, str2, str4, paramDateTime1, paramDateTime2);
      int j = 0;
      int k = 0;
      int m = 0;
      RegisterTransaction localRegisterTransaction2 = null;
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str5);
      while (localResultSet.next())
      {
        k = localResultSet.getInt("reg_transaction_id");
        if (j != k)
        {
          m = 1;
          localRegisterTransaction2 = new RegisterTransaction(paramSecureUser.getLocale());
          fillRegisterTransactionBean(localRegisterTransaction2, localResultSet);
          localRegisterTransaction2.setTransactionCategories(new TransactionCategories());
        }
        else
        {
          m = 0;
        }
        TransactionCategory localTransactionCategory = new TransactionCategory(paramSecureUser.getLocale());
        localTransactionCategory.setRegisterCategoryId(localResultSet.getString("reg_user_cat_id"));
        BigDecimal localBigDecimal = localResultSet.getBigDecimal("categoryAmount");
        Currency localCurrency = new Currency(localBigDecimal, str3, paramSecureUser.getLocale());
        localTransactionCategory.setAmount(localCurrency);
        localRegisterTransaction2.getTransactionCategories().add(localTransactionCategory);
        j = k;
        if (m != 0)
        {
          if (Profile.hasValue(str4))
          {
            if (str4.equals(localRegisterTransaction2.getReferenceNumber())) {
              localRegisterTransaction2.put("DATE_RANGE", "in");
            } else {
              localRegisterTransaction2.put("DATE_RANGE", "unknown");
            }
          }
          else
          {
            DateTime localDateTime = localRegisterTransaction2.getDateIssuedValue();
            if (Profile.hasValue(localDateTime))
            {
              if ((Profile.hasValue(paramDateTime1)) && (localDateTime.before(paramDateTime1))) {
                localRegisterTransaction2.put("DATE_RANGE", "before");
              } else if ((Profile.hasValue(paramDateTime2)) && (localDateTime.after(paramDateTime2))) {
                localRegisterTransaction2.put("DATE_RANGE", "after");
              } else {
                localRegisterTransaction2.put("DATE_RANGE", "in");
              }
            }
            else {
              localRegisterTransaction2.put("DATE_RANGE", "unknown");
            }
          }
          paramRegisterTransactions.add(localRegisterTransaction2);
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    logModerateActivity("Exited " + str1);
  }
  
  private static RegisterTransactions jdMethod_for(Connection paramConnection, RegisterTransaction paramRegisterTransaction, DateTime paramDateTime1, DateTime paramDateTime2, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    String str1 = "RegisterAdapter.getRegisterTransactionsForReconcilation()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    String str2 = (String)paramRegisterTransaction.get("ACCOUNT");
    if (!Profile.hasValue(str2)) {
      logInvalidRequest(str1, "Invalid account ID.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    try
    {
      localPreparedStatement2 = DBUtil.prepareStatement(paramConnection, "select rtc.reg_user_cat_id, rtc.amount, ruc2.name, ruc.name from reg_tran_category rtc, (select * from reg_user_category where directory_id = ?) ruc left outer join (select * from reg_user_category where directory_id = ?) ruc2 on ( ruc2.reg_user_cat_id = ruc.parent_category_id ) where rtc.reg_transaction_id=? and ruc.reg_user_cat_id=rtc.reg_user_cat_id and ruc.directory_id=rtc.directory_id ");
      String str3 = buildGetRegisterTransactionsSqlForReconcilation(paramRegisterTransaction, paramDateTime1, paramDateTime2);
      localPreparedStatement1 = DBUtil.prepareStatement(paramConnection, str3);
      prepareGetRegisterTransactionsForReconcilation(localPreparedStatement1, i, str2, paramRegisterTransaction, paramDateTime1, paramDateTime2);
      RegisterTransaction localRegisterTransaction = null;
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, str3.toString());
      Locale localLocale = paramSecureUser.getLocale();
      while (localResultSet1.next())
      {
        localRegisterTransaction = new RegisterTransaction(localLocale);
        localRegisterTransaction.setRegisterId(localResultSet1.getInt("reg_transaction_id"));
        localRegisterTransaction.set("ACCOUNT", localResultSet1.getString("ACCOUNT_ID"));
        localRegisterTransaction.setID(localResultSet1.getString("fi_transaction_id"));
        localRegisterTransaction.setReferenceNumber(unPadRefNum(localResultSet1.getString("reference_number")));
        localRegisterTransaction.setPayeeName(localResultSet1.getString("payee_name"));
        localRegisterTransaction.setMemo(localResultSet1.getString("memo"));
        localRegisterTransaction.setType(localResultSet1.getInt("type"));
        localRegisterTransaction.setStatus(localResultSet1.getInt("STATUS"));
        localRegisterTransaction.setDateIssued(ProfileUtil.getDateTime(localResultSet1.getTimestamp("date_issued")));
        localRegisterTransaction.setDate(ProfileUtil.getDateTime(localResultSet1.getTimestamp("date_cleared")));
        localRegisterTransaction.set("SERVER_TID", localResultSet1.getString("server_tran_id"));
        localRegisterTransaction.set("RECSRVRTID", localResultSet1.getString("rec_server_tran_id"));
        localRegisterTransaction.setAmount(localResultSet1.getBigDecimal("amount").toString());
        try
        {
          localRegisterTransaction.setTransactionCategories(new TransactionCategories());
          localPreparedStatement2.setInt(1, i);
          localPreparedStatement2.setInt(2, i);
          localPreparedStatement2.setInt(3, localRegisterTransaction.getRegisterIdValue());
          localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select rtc.reg_user_cat_id, rtc.amount, ruc2.name, ruc.name from reg_tran_category rtc, (select * from reg_user_category where directory_id = ?) ruc left outer join (select * from reg_user_category where directory_id = ?) ruc2 on ( ruc2.reg_user_cat_id = ruc.parent_category_id ) where rtc.reg_transaction_id=? and ruc.reg_user_cat_id=rtc.reg_user_cat_id and ruc.directory_id=rtc.directory_id ");
          while (localResultSet2.next())
          {
            TransactionCategory localTransactionCategory = new TransactionCategory(localLocale);
            localTransactionCategory.setRegisterCategoryId(localResultSet2.getString("reg_user_cat_id"));
            BigDecimal localBigDecimal = localResultSet2.getBigDecimal("amount");
            Currency localCurrency = new Currency(localBigDecimal, paramSecureUser.getLocale());
            localTransactionCategory.setAmount(localCurrency);
            localRegisterTransaction.getTransactionCategories().add(localTransactionCategory);
          }
          localRegisterTransactions.add(localRegisterTransaction);
          DBUtil.closeResultSet(localResultSet2);
        }
        finally
        {
          DBUtil.closeResultSet(localResultSet2);
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
    }
    logModerateActivity("Exited " + str1);
    return localRegisterTransactions;
  }
  
  public static void modifyRegisterAccountData(Account paramAccount, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.modifyRegisterAccountData()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramAccount)) {
      logInvalidRequest(str1, ": Invalid account.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, ": Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    String str2 = paramAccount.getID();
    if (i == -1) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    if (!Profile.hasValue(str2)) {
      logInvalidRequest(str1, "Invalid account ID.");
    }
    boolean bool1 = Boolean.valueOf((String)paramAccount.get("REG_DEFAULT".toUpperCase())).booleanValue();
    boolean bool2 = Boolean.valueOf((String)paramAccount.get("REG_ENABLED".toUpperCase())).booleanValue();
    DateTime localDateTime = (DateTime)paramAccount.get("REG_RETRIEVAL_DATE".toUpperCase());
    if ((!bool2) && (bool1))
    {
      paramAccount.put("REG_DEFAULT".toUpperCase(), "false");
      bool1 = false;
    }
    StringBuffer localStringBuffer1 = new StringBuffer(40);
    localStringBuffer1.append(" where ");
    localStringBuffer1.append("directory_id").append("=?");
    localStringBuffer1.append(" and ");
    localStringBuffer1.append("account_id").append("=?");
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      if (bool2)
      {
        localStringBuffer2 = new StringBuffer(100);
        localStringBuffer2.append("update ").append("accounts").append(" set ");
        localStringBuffer2.append("reg_enabled").append("=?").append(",");
        localStringBuffer2.append("reg_retrieval_date").append("=?");
        localStringBuffer2.append(localStringBuffer1.toString());
        localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer2.toString());
        localPreparedStatement1.setString(1, "T");
        localPreparedStatement1.setTimestamp(2, ProfileUtil.getTimestamp(localDateTime));
        localPreparedStatement1.setInt(3, i);
        localPreparedStatement1.setString(4, str2);
        DBUtil.executeUpdate(localPreparedStatement1, localStringBuffer2.toString());
        if (bool1) {
          setDefaultAccount(localConnection, i, str2);
        }
      }
      else
      {
        localStringBuffer2 = new StringBuffer(110);
        localStringBuffer2.append("update ").append("accounts").append(" set ");
        localStringBuffer2.append("reg_enabled").append("=?").append(",");
        localStringBuffer2.append("reg_default").append("=?").append(",");
        localStringBuffer2.append("reg_retrieval_date").append("=?");
        localStringBuffer2.append(localStringBuffer1.toString());
        localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer2.toString());
        localPreparedStatement1.setString(1, "F");
        localPreparedStatement1.setString(2, "F");
        localPreparedStatement1.setTimestamp(3, ProfileUtil.getTimestamp(localDateTime));
        localPreparedStatement1.setInt(4, i);
        localPreparedStatement1.setString(5, str2);
        DBUtil.executeUpdate(localPreparedStatement1, localStringBuffer2.toString());
      }
      StringBuffer localStringBuffer2 = new StringBuffer(20);
      if (enabledAccountNoDefault(localConnection, i, localStringBuffer2))
      {
        String str3 = localStringBuffer2.toString();
        if (bool2)
        {
          paramAccount.put("REG_DEFAULT".toUpperCase(), "true");
          str3 = paramAccount.getID();
        }
        localStringBuffer2 = new StringBuffer(75);
        localStringBuffer2.append("update ").append("accounts").append(" set ");
        localStringBuffer2.append("reg_default").append("=?");
        localStringBuffer2.append(" where ");
        localStringBuffer2.append("directory_id").append("=?");
        localStringBuffer2.append(" and ");
        localStringBuffer2.append("account_id").append("=?");
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, localStringBuffer2.toString());
        localPreparedStatement2.setString(1, "T");
        localPreparedStatement2.setInt(2, i);
        localPreparedStatement2.setString(3, str3);
        DBUtil.executeUpdate(localPreparedStatement2, localStringBuffer2.toString());
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void modifyRegisterAccountsData(Accounts paramAccounts, SecureUser paramSecureUser)
    throws ProfileException
  {
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext()) {
      modifyRegisterAccountData((Account)localIterator.next(), paramSecureUser);
    }
  }
  
  public static void modifyRegisterCategory(RegisterCategory paramRegisterCategory, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.modifyRegisterCategory()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterCategory)) {
      logInvalidRequest(str1, "Invalid register category.");
    }
    int i = Profile.convertToInt(paramRegisterCategory.getId(), -1);
    int j = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid category ID.");
    }
    if (!Profile.isValidId(j)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Statement localStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      String str2 = buildModifyRegisterCategorySql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareModifyRegisterCategory(localPreparedStatement, paramRegisterCategory, j);
      DBUtil.executeUpdate(localPreparedStatement, str2);
      if (paramRegisterCategory.getParentCategoryValue() == -1)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("update ").append("reg_user_category");
        localStringBuffer.append(" set ").append("type").append("=").append(paramRegisterCategory.getType());
        localStringBuffer.append(" where ").append("parent_category_id").append("=").append(paramRegisterCategory.getId());
        localStatement = DBUtil.createStatement(localConnection);
        DBUtil.executeUpdate(localStatement, localStringBuffer.toString());
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.closeStatement(localStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void modifyRegisterPayee(RegisterPayee paramRegisterPayee)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.modifyRegisterPayee()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterPayee)) {
      logInvalidRequest(str1, "Invalid register payee.");
    }
    int i = Profile.convertToInt(paramRegisterPayee.getId(), -1);
    if (i == -1) {
      logInvalidRequest(str1, "Invalid payee ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = buildModifyRegisterPayeeSql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareModifyRegisterPayee(localPreparedStatement, paramRegisterPayee);
      DBUtil.executeUpdate(localPreparedStatement, str2);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void modifyRegisterTransaction(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "RegisterAdapter.modifyRegisterTransaction()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    if (paramRegisterTransaction.getRegisterIdValue() == 0)
    {
      logModerateActivity(str + ": Register transaction not in database.");
    }
    else
    {
      int i = getDirectoryID(paramSecureUser);
      if (!Profile.isValidId(i)) {
        logInvalidRequest(str, "Invalid directory ID.");
      }
      Connection localConnection = null;
      try
      {
        localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
        modifyTransaction(localConnection, paramRegisterTransaction, paramSecureUser);
        DBUtil.commit(localConnection);
      }
      catch (Exception localException)
      {
        DBUtil.rollback(localConnection);
        Profile.handleError(localException, str);
      }
      finally
      {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void modifyRegisterTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "RegisterAdapter.modifyRegisterTransactions()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    if (!Profile.hasValue(paramRegisterTransactions)) {
      logInvalidRequest(str, "Invalid register transactions.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      modifyTransactions(localConnection, paramRegisterTransactions, paramSecureUser);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void reassignTransactionsCategory(String paramString1, String paramString2, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "RegisterAdapter.reassignTransactionsCategory()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    int i = Profile.convertToInt(paramString1, -1);
    int j = Profile.convertToInt(paramString2, -1);
    int k = getDirectoryID(paramSecureUser);
    if (i < 0) {
      logInvalidRequest(str, "Invalid old category ID.");
    }
    if (j < 0) {
      logInvalidRequest(str, "Invalid new category ID.");
    }
    if (!Profile.isValidId(k)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    StringBuffer localStringBuffer1 = new StringBuffer(95);
    localStringBuffer1.append("update ").append("reg_tran_category");
    localStringBuffer1.append(" set ").append("reg_user_cat_id").append("=?");
    localStringBuffer1.append(" where ");
    localStringBuffer1.append("reg_user_cat_id").append("=?");
    localStringBuffer1.append(" and ");
    localStringBuffer1.append("directory_id").append("=?");
    StringBuffer localStringBuffer2 = new StringBuffer(85);
    localStringBuffer2.append("update ").append("reg_payee");
    localStringBuffer2.append(" set ").append("reg_user_cat_id").append("=?");
    localStringBuffer2.append(" where ");
    localStringBuffer2.append("reg_user_cat_id").append("=?");
    localStringBuffer2.append(" and ");
    localStringBuffer2.append("directory_id").append("=?");
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer1.toString());
      localPreparedStatement1.setInt(1, j);
      localPreparedStatement1.setInt(2, i);
      localPreparedStatement1.setInt(3, k);
      DBUtil.executeUpdate(localPreparedStatement1, localStringBuffer1.toString());
      localPreparedStatement2 = DBUtil.prepareStatement(localConnection, localStringBuffer2.toString());
      localPreparedStatement2.setInt(1, j);
      localPreparedStatement2.setInt(2, i);
      localPreparedStatement2.setInt(3, k);
      DBUtil.executeUpdate(localPreparedStatement2, localStringBuffer2.toString());
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void reconcileRegisterTransactions(RegisterTransactions paramRegisterTransactions1, RegisterTransactions paramRegisterTransactions2, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "RegisterAdapter.reconcileRegisterTransactions()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    if (!Profile.hasValue(paramRegisterTransactions1)) {
      logInvalidRequest(str, "Invalid modification list of register transactions.");
    }
    if (!Profile.hasValue(paramRegisterTransactions2)) {
      logInvalidRequest(str, "Invalid delete list of register transactions.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      modifyTransactions(localConnection, paramRegisterTransactions1, paramSecureUser);
      deleteTransactions(localConnection, paramRegisterTransactions2);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void setDefaultRegisterAccount(String paramString, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "RegisterAdapter.setDefaultRegisterAccount()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    if (!Profile.hasValue(paramString)) {
      logInvalidRequest(str, "Invalid account ID.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      setDefaultAccount(localConnection, i, paramString);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static void transferDefaultCategories(SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.transferDefaultCategories()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    Statement localStatement = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      StringBuffer localStringBuffer = new StringBuffer(45);
      localStringBuffer.append("select * from ").append("reg_user_category");
      localStringBuffer.append(" where ").append("directory_id").append("=?");
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement1.setInt(1, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, localStringBuffer.toString());
      if (!localResultSet.next())
      {
        DBUtil.closeResultSet(localResultSet);
        localStringBuffer.setLength(0);
        localStringBuffer.append("select * from ").append("reg_def_category").append(" where ");
        localStringBuffer.append("reg_def_cat_id").append(" = 0 or ");
        if (isBusinessUser(paramSecureUser))
        {
          localStringBuffer.append("type").append("=").append(2);
          localStringBuffer.append(" or ");
          localStringBuffer.append("type").append("=").append(3);
        }
        else
        {
          localStringBuffer.append("type").append("=").append(0);
          localStringBuffer.append(" or ");
          localStringBuffer.append("type").append("=").append(1);
        }
        localStatement = DBUtil.createStatement(localConnection);
        localResultSet = DBUtil.executeQuery(localStatement, localStringBuffer.toString());
        String str2 = buildAddRegisterCategorySql();
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, str2);
        while (localResultSet.next())
        {
          RegisterCategory localRegisterCategory = new RegisterCategory();
          localRegisterCategory.setLocale(paramSecureUser.getLocale());
          fillRegisterCategoryBean(localRegisterCategory, localResultSet, true);
          prepareAddRegisterCategory(localPreparedStatement2, localRegisterCategory, i);
          if (Profile.batchMode) {
            localPreparedStatement2.addBatch();
          } else {
            DBUtil.executeUpdate(localPreparedStatement2, str2);
          }
        }
        if (Profile.batchMode)
        {
          logSQL(str1, "[Batched Statement] " + str2);
          localPreparedStatement2.executeBatch();
        }
        DBUtil.commit(localConnection);
      }
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeAll(localStatement, localResultSet);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void addRecurringTransactions(RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addRecurringTransactions()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramRegisterTransactions)) {
      logInvalidRequest(str1, "Invalid register transactions.");
    }
    if (!Profile.hasValue(paramSecureUser)) {
      logInvalidRequest(str1, "Invalid user.");
    }
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      synchronized (paramSecureUser)
      {
        Iterator localIterator = paramRegisterTransactions.iterator();
        while (localIterator.hasNext())
        {
          RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
          String str2 = (String)localRegisterTransaction.get("SERVER_TID");
          String str3 = (String)localRegisterTransaction.get("RECSRVRTID");
          String str4 = (String)localRegisterTransaction.get("ACCOUNT");
          if (((str3 == null) || (!updateExistingRecurringTransactions(localConnection, str3, str2))) && (!transactionExists(localConnection, str2, str4, i, false))) {
            if (!validTransactionCategories(localRegisterTransaction)) {
              logInvalidRequest(str1, "Invalid transaction categories for transaction: " + str2);
            } else {
              localRegisterTransactions.add(localRegisterTransaction);
            }
          }
        }
        if (localRegisterTransactions.size() > 0) {
          addTransactions(localConnection, localRegisterTransactions, paramSecureUser, true);
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void deleteRegisterTransactionsByTranId(String paramString)
    throws ProfileException
  {
    String str = "RegisterAdapter.deleteRegisterTransactionsByServerTID(String)";
    logModerateActivity("Entered " + str);
    if (!Profile.hasValue(paramString)) {
      logInvalidRequest(str, "Invalid register transaction.");
    }
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(paramString);
    deleteRegisterTransactionsByServerTID(localArrayList);
    logModerateActivity("Exited " + str);
  }
  
  public static void getRegisterTransactionsByTranId(RegisterTransactions paramRegisterTransactions, String paramString, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.getRegisterTransactionsByTranId()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    if (!Profile.hasValue(paramString)) {
      logInvalidRequest(str1, "Invalid transaction ID.");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      String str2 = buildGetRegisterTransactionsByTranIdSql();
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str2);
      prepareGetRegisterTransactionsByTranId(localPreparedStatement, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str2);
      while (localResultSet.next())
      {
        RegisterTransaction localRegisterTransaction = new RegisterTransaction();
        localRegisterTransaction.setLocale(paramSecureUser.getLocale());
        fillRegisterTransactionBean(localRegisterTransaction, localResultSet);
        getTransactionCategories(localConnection, localRegisterTransaction);
        paramRegisterTransactions.add(localRegisterTransaction);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str1);
  }
  
  public static void modifyRegisterTransactionsByTranId(RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str = "RegisterAdapter.modifyRegisterTransactionsByTranId()";
    logModerateActivity("Entered " + str);
    Profile.isInitialized();
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    if (!Profile.hasValue(paramRegisterTransaction)) {
      logInvalidRequest(str, "Invalid register transaction.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      localRegisterTransactions.add(paramRegisterTransaction);
      modifyTransactionsByTranIds(localConnection, localRegisterTransactions, paramSecureUser);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
    logModerateActivity("Exited " + str);
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.getReportData()";
    logModerateActivity("Entered " + str1);
    ReportResult localReportResult = null;
    Profile.isInitialized();
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("REPORTTYPE");
    String str3 = localProperties.getProperty("FORMAT");
    boolean bool = false;
    if ((str3.equals("HTML")) || (str3.equals("COMMA")) || (str3.equals("TAB")) || (str3.equals("PDF")) || (str3.equals("TEXT"))) {
      bool = true;
    }
    Accounts localAccounts = (Accounts)paramHashMap.get("Accounts");
    ArrayList localArrayList = jdMethod_try(paramReportCriteria);
    jdMethod_for(paramSecureUser, paramReportCriteria, localAccounts, localArrayList);
    if ((paramSecureUser != null) && (paramSecureUser.getLocale() != null)) {
      localReportResult = new ReportResult(paramSecureUser.getLocale());
    } else {
      localReportResult = new ReportResult(Locale.getDefault());
    }
    try
    {
      localReportResult.init(paramReportCriteria);
      if (str2.equals("Account Register Export Transactions")) {
        jdMethod_for(paramSecureUser, localReportResult, paramReportCriteria, bool);
      } else if (str2.equals("Account Register Category Totals")) {
        jdMethod_try(paramSecureUser, localReportResult, paramReportCriteria, localAccounts);
      } else if (str2.equals("Account Register Tax Status")) {
        jdMethod_for(paramSecureUser, localReportResult, paramReportCriteria, localAccounts);
      } else if (str2.equals("Account Register Cash Flow")) {
        jdMethod_int(paramSecureUser, localReportResult, paramReportCriteria, localAccounts);
      } else if (str2.equals("Account Register Transaction Type Totals")) {
        jdMethod_new(paramSecureUser, localReportResult, paramReportCriteria, localAccounts);
      } else if (str2.equals("Account Register Payee Totals")) {
        jdMethod_byte(paramSecureUser, localReportResult, paramReportCriteria, localAccounts);
      } else if (str2.equals("Account Register Reconciliation Status")) {
        jdMethod_int(paramSecureUser, localReportResult, paramReportCriteria, localAccounts, paramHashMap);
      } else if (str2.equals("Custom Register Report")) {
        jdMethod_for(paramSecureUser, localReportResult, paramReportCriteria, localAccounts, paramHashMap);
      }
    }
    catch (Exception localException)
    {
      if (localReportResult != null) {
        localReportResult.setError(localException);
      }
      Profile.handleError(localException, str1);
    }
    logModerateActivity("Exited " + str1);
    return localReportResult;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, Accounts paramAccounts, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.reportCustomRegister";
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str2 = jdMethod_for(paramReportCriteria, true);
    String str3 = jdMethod_for(paramReportCriteria, false);
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement1 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str2, true);
      localPreparedStatement2 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str3, false);
      ArrayList localArrayList = jdMethod_try(paramReportCriteria);
      String str4 = paramReportCriteria.getSearchCriteria().getProperty("Tax");
      boolean bool = (str4 != null) && (str4.equalsIgnoreCase("T"));
      for (int i = 0; i < localArrayList.size(); i++)
      {
        String str5 = (String)localArrayList.get(i);
        Account localAccount = null;
        if (paramAccounts != null) {
          localAccount = paramAccounts.getByID(str5);
        }
        if (paramAccounts.size() > 1) {
          jdMethod_for(paramReportResult, jdMethod_for(localAccount, paramReportCriteria), true);
        }
        localPreparedStatement1.setString(4, str5);
        localPreparedStatement2.setString(4, str5);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, str2.toString());
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, str3.toString());
        jdMethod_for(paramReportResult, ReportConsts.getText(2300, paramReportResult.getLocale()), false);
        ReportResult localReportResult1 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult1);
        jdMethod_for(localReportResult1, localResultSet1, bool, true, localAccount);
        jdMethod_for(paramReportResult, ReportConsts.getText(2301, paramReportResult.getLocale()), false);
        ReportResult localReportResult2 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult2);
        jdMethod_for(localReportResult2, localResultSet2, bool, false, localAccount);
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeResultSet(localResultSet2);
      }
    }
    catch (Exception localException)
    {
      if (paramReportResult != null) {
        paramReportResult.setError(localException);
      }
      Profile.handleError(localException, str1);
    }
    finally
    {
      if (paramReportResult != null) {
        try
        {
          paramReportResult.fini();
        }
        catch (RptException localRptException) {}
      }
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  protected static void modifyTransactionsByTranIds(Connection paramConnection, RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.modifyTransactionsByTranIds()";
    logModerateActivity("Entered " + str1);
    Profile.isInitialized();
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      int j = 0;
      ArrayList localArrayList = new ArrayList();
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      Object localObject1 = paramRegisterTransactions.iterator();
      Object localObject4;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (RegisterTransaction)((Iterator)localObject1).next();
        localObject3 = (Account)((RegisterTransaction)localObject2).get("FROMACCOUNT");
        Account localAccount = (Account)((RegisterTransaction)localObject2).get("TOACCOUNT");
        localObject4 = (String)((RegisterTransaction)localObject2).get("SERVER_TID");
        if (!Profile.hasValue(localObject4)) {
          logInvalidRequest(str1, "Invalid server transaction ID.");
        }
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("select ").append("reg_transaction").append(".").append("reg_transaction_id").append(",").append("reg_tran_category").append(".").append("amount");
        localStringBuffer.append(" from ").append("reg_transaction").append(",").append("reg_tran_category");
        localStringBuffer.append(" where ").append("reg_transaction").append(".").append("reg_transaction_id");
        localStringBuffer.append(" = ").append("reg_tran_category").append(".").append("reg_transaction_id");
        localStringBuffer.append(" and ").append("server_tran_id").append("=?");
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
        localPreparedStatement.setString(1, (String)localObject4);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        while (localResultSet.next())
        {
          String str2 = localResultSet.getString(1);
          BigDecimal localBigDecimal = localResultSet.getBigDecimal(2);
          RegisterTransaction localRegisterTransaction = new RegisterTransaction();
          localRegisterTransaction.set((RegisterTransaction)localObject2);
          localRegisterTransaction.setRegisterId(str2);
          localRegisterTransactions.add(localRegisterTransaction);
          if (localRegisterTransaction.getRegisterTypeValue() == 16)
          {
            String str3;
            if (localBigDecimal.compareTo(new BigDecimal("0.0")) == ah2)
            {
              localRegisterTransaction.set("ACCOUNT_ID", ((Account)localObject3).getID());
              str3 = RegisterUtil.getTransferPayeeName("TransferFromText", localAccount.getNickName(), ((RegisterTransaction)localObject2).getLocale());
              localRegisterTransaction.setPayeeName(str3);
            }
            else
            {
              localRegisterTransaction.getTransactionCategories().negateAmounts();
              localRegisterTransaction.set("ACCOUNT_ID", localAccount.getID());
              str3 = RegisterUtil.getTransferPayeeName("TransferToText", ((Account)localObject3).getNickName(), ((RegisterTransaction)localObject2).getLocale());
              localRegisterTransaction.setPayeeName(str3);
            }
          }
          localArrayList.add(str2);
        }
      }
      j = 0;
      localObject1 = new int[localArrayList.size()];
      Object localObject2 = localArrayList.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (String)((Iterator)localObject2).next();
        localObject1[(j++)] = Integer.parseInt((String)localObject3);
      }
      jdMethod_for(paramConnection, localRegisterTransactions, paramSecureUser);
      deleteTransactionCategories(paramConnection, (int[])localObject1);
      addTransactionCategories(paramConnection, localRegisterTransactions, i);
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      localObject2 = new StringBuffer(180);
      ((StringBuffer)localObject2).append("update ").append("reg_transaction").append(" set ");
      ((StringBuffer)localObject2).append("account_id").append("=?,");
      ((StringBuffer)localObject2).append("fi_transaction_id").append("=?,");
      ((StringBuffer)localObject2).append("reference_number").append("=?,");
      ((StringBuffer)localObject2).append("type").append("=?,");
      ((StringBuffer)localObject2).append("status").append("=?,");
      ((StringBuffer)localObject2).append("memo").append("=?,");
      ((StringBuffer)localObject2).append("date_issued").append("=?,");
      ((StringBuffer)localObject2).append("date_cleared").append("=?,");
      ((StringBuffer)localObject2).append("payee_name").append("=?");
      ((StringBuffer)localObject2).append("reg_transaction").append(".").append("amount").append("=?");
      ((StringBuffer)localObject2).append(" where ");
      ((StringBuffer)localObject2).append("directory_id").append("=?").append(" and ");
      ((StringBuffer)localObject2).append("reg_transaction_id").append("=?");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, ((StringBuffer)localObject2).toString());
      Object localObject3 = localRegisterTransactions.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        int k = 1;
        localObject4 = (RegisterTransaction)((Iterator)localObject3).next();
        localPreparedStatement.setString(k++, (String)((RegisterTransaction)localObject4).get("ACCOUNT_ID"));
        localPreparedStatement.setString(k++, ((RegisterTransaction)localObject4).getID());
        localPreparedStatement.setString(k++, padRefNum(((RegisterTransaction)localObject4).getReferenceNumber()));
        localPreparedStatement.setInt(k++, ((RegisterTransaction)localObject4).getTypeValue());
        localPreparedStatement.setInt(k++, ((RegisterTransaction)localObject4).getStatusValue());
        localPreparedStatement.setString(k++, ((RegisterTransaction)localObject4).getMemo());
        localPreparedStatement.setTimestamp(k++, Profile.convertToTimestamp(((RegisterTransaction)localObject4).getDateIssuedValue()));
        localPreparedStatement.setTimestamp(k++, Profile.convertToTimestamp(((RegisterTransaction)localObject4).getDateValue()));
        localPreparedStatement.setString(k++, ((RegisterTransaction)localObject4).getPayeeName());
        localPreparedStatement.setBigDecimal(k++, ((RegisterTransaction)localObject4).getAmountValue().getAmountValue().setScale(3, 5));
        localPreparedStatement.setInt(k++, i);
        localPreparedStatement.setInt(k++, ((RegisterTransaction)localObject4).getRegisterIdValue());
        if (Profile.batchMode) {
          localPreparedStatement.addBatch();
        } else {
          DBUtil.executeUpdate(localPreparedStatement, ((StringBuffer)localObject2).toString());
        }
      }
      if (Profile.batchMode)
      {
        logSQL(str1, "[Batched Statement] " + localObject2);
        localPreparedStatement.executeBatch();
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    logModerateActivity("Exited " + str1);
  }
  
  protected static void addTransactionCategories(Connection paramConnection, RegisterTransaction paramRegisterTransaction, int paramInt)
    throws ProfileException
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    localRegisterTransactions.add(paramRegisterTransaction);
    addTransactionCategories(paramConnection, localRegisterTransactions, paramInt);
  }
  
  protected static void addTransactionCategories(Connection paramConnection, RegisterTransactions paramRegisterTransactions, int paramInt)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addTransactionCategories()";
    logModerateActivity("Entered " + str1);
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str2 = buildAddTransactionCategorySql();
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      Iterator localIterator1 = paramRegisterTransactions.iterator();
      while (localIterator1.hasNext())
      {
        RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator1.next();
        TransactionCategories localTransactionCategories = localRegisterTransaction.getTransactionCategories();
        Iterator localIterator2 = localTransactionCategories.iterator();
        while (localIterator2.hasNext())
        {
          TransactionCategory localTransactionCategory = (TransactionCategory)localIterator2.next();
          prepareAddTransactionCategory(localPreparedStatement, localTransactionCategory, localRegisterTransaction.getRegisterIdValue(), paramInt);
          if (Profile.batchMode) {
            localPreparedStatement.addBatch();
          } else {
            DBUtil.executeUpdate(localPreparedStatement, str2);
          }
        }
      }
      if (Profile.batchMode)
      {
        logSQL(str1, "[Batched Statement] " + str2);
        localPreparedStatement.executeBatch();
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    logModerateActivity("Exited " + str1);
  }
  
  protected static void addTransactions(Connection paramConnection, RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser, boolean paramBoolean)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.addTransactions()";
    logModerateActivity("Entered " + str1);
    int i = getDirectoryIDForConsumer(paramSecureUser);
    PreparedStatement localPreparedStatement = null;
    try
    {
      jdMethod_for(paramConnection, paramRegisterTransactions, paramSecureUser);
      String str2 = buildAddRegisterTransactionSql();
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      Iterator localIterator = paramRegisterTransactions.iterator();
      while (localIterator.hasNext())
      {
        int j = DBUtil.getNextId(paramConnection, Profile.dbType, "reg_transaction_id");
        RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
        localRegisterTransaction.setRegisterId(j);
        String str3 = (String)localRegisterTransaction.get("ACCOUNT");
        prepareAddRegisterTransaction(localPreparedStatement, localRegisterTransaction, i, str3);
        if (Profile.batchMode) {
          localPreparedStatement.addBatch();
        } else {
          DBUtil.executeUpdate(localPreparedStatement, str2);
        }
      }
      if (Profile.batchMode)
      {
        logSQL(str1, "[Batched Statement] " + str2);
        localPreparedStatement.executeBatch();
      }
      if (paramBoolean) {
        addTransactionCategories(paramConnection, paramRegisterTransactions, i);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    logModerateActivity("Exited " + str1);
  }
  
  protected static void deleteTransaction(Connection paramConnection, RegisterTransaction paramRegisterTransaction)
    throws ProfileException
  {
    String str = "RegisterAdapter.deleteTransaction()";
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    localRegisterTransactions.add(paramRegisterTransaction);
    deleteTransactions(paramConnection, localRegisterTransactions);
    try
    {
      DBUtil.commit(paramConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(paramConnection);
      Profile.handleError(localException, str);
    }
  }
  
  protected static void deleteTransactions(Connection paramConnection, RegisterTransactions paramRegisterTransactions)
    throws ProfileException
  {
    String str = "RegisterAdapter.deleteTransactions()";
    logModerateActivity("Entered " + str);
    if (paramRegisterTransactions.size() == 0) {
      return;
    }
    StringBuffer localStringBuffer = new StringBuffer(60);
    localStringBuffer.append("delete from ").append("reg_transaction");
    localStringBuffer.append(" where ").append("reg_transaction_id").append("=?");
    int[] arrayOfInt = new int[paramRegisterTransactions.size()];
    Iterator localIterator = paramRegisterTransactions.iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      localObject1 = (RegisterTransaction)localIterator.next();
      int j = Profile.convertToInt(((RegisterTransaction)localObject1).getRegisterId(), -1);
      if (j == -1) {
        logInvalidRequest(str, "Invalid register transaction ID.");
      }
      arrayOfInt[(i++)] = j;
    }
    Object localObject1 = null;
    try
    {
      deleteTransactionCategories(paramConnection, arrayOfInt);
      localObject1 = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localIterator = paramRegisterTransactions.iterator();
      while (localIterator.hasNext())
      {
        RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
        ((PreparedStatement)localObject1).setInt(1, localRegisterTransaction.getRegisterIdValue());
        if (Profile.batchMode) {
          ((PreparedStatement)localObject1).addBatch();
        } else {
          DBUtil.executeUpdate((PreparedStatement)localObject1, localStringBuffer.toString());
        }
      }
      if (Profile.batchMode)
      {
        logSQL(str, "[Batched Statement] " + localStringBuffer);
        ((PreparedStatement)localObject1).executeBatch();
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement((PreparedStatement)localObject1);
    }
    logModerateActivity("Exited " + str);
  }
  
  protected static void deleteTransactionCategories(Connection paramConnection, int[] paramArrayOfInt)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.deleteTransactionCategories()";
    logModerateActivity("Entered " + str1);
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str2 = buildDeleteTransactionCategoriesSql();
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str2);
      for (int i = 0; i < paramArrayOfInt.length; i++)
      {
        prepareDeleteTransactionCategories(localPreparedStatement, paramArrayOfInt[i]);
        if (Profile.batchMode) {
          localPreparedStatement.addBatch();
        } else {
          DBUtil.executeUpdate(localPreparedStatement, str2);
        }
      }
      if (Profile.batchMode)
      {
        logSQL(str1, "[Batched Statement] " + str2);
        localPreparedStatement.executeBatch();
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    logModerateActivity("Exited " + str1);
  }
  
  protected static boolean enabledAccountNoDefault(Connection paramConnection, int paramInt, StringBuffer paramStringBuffer)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.enabledAccountNoDefault()";
    logModerateActivity("Entered " + str1);
    StringBuffer localStringBuffer = new StringBuffer(85);
    localStringBuffer.append("select ").append("account_id").append(",").append("reg_default");
    localStringBuffer.append(" from ").append("accounts");
    localStringBuffer.append(" where ");
    localStringBuffer.append("directory_id").append("=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("reg_enabled").append("=?");
    boolean bool = true;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, "T");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (!localResultSet.next()) {
        bool = false;
      } else {
        do
        {
          String str2 = localResultSet.getString("reg_default");
          if ("T".equals(str2))
          {
            bool = false;
            break;
          }
          paramStringBuffer.setLength(0);
          paramStringBuffer.append(localResultSet.getString("account_id"));
        } while (localResultSet.next());
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    logModerateActivity("Exited " + str1);
    return bool;
  }
  
  protected static DateTime getRetrievalDate(Connection paramConnection, int paramInt, String paramString)
    throws ProfileException
  {
    String str = "RegisterAdapter.getAccountRetrievalDate()";
    logModerateActivity("Entered " + str);
    StringBuffer localStringBuffer = new StringBuffer(80);
    localStringBuffer.append("select ").append("reg_retrieval_date");
    localStringBuffer.append(" from ").append("accounts");
    localStringBuffer.append(" where ");
    localStringBuffer.append("directory_id").append("=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("account_id").append("=?");
    DateTime localDateTime = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        java.sql.Date localDate = localResultSet.getDate("reg_retrieval_date");
        if (localDate != null) {
          localDateTime = new DateTime(localDate, Locale.getDefault());
        }
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    logModerateActivity("Exited " + str);
    return localDateTime;
  }
  
  protected static void getTransactionCategories(Connection paramConnection, RegisterTransaction paramRegisterTransaction)
    throws ProfileException
  {
    String str = "RegisterAdapter.getTransactionCategories()";
    logModerateActivity("Entered " + str);
    StringBuffer localStringBuffer = new StringBuffer(65);
    localStringBuffer.append("select * from ");
    localStringBuffer.append("reg_tran_category");
    localStringBuffer.append(" where ");
    localStringBuffer.append("reg_transaction_id").append("=?");
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, Profile.convertToInt(paramRegisterTransaction.getRegisterId(), -1));
      TransactionCategories localTransactionCategories = new TransactionCategories();
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        TransactionCategory localTransactionCategory = new TransactionCategory(paramRegisterTransaction.getLocale());
        fillTransactionCategoryBean(localTransactionCategory, localResultSet);
        localTransactionCategories.add(localTransactionCategory);
      }
      paramRegisterTransaction.setTransactionCategories(localTransactionCategories);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    logModerateActivity("Exited " + str);
  }
  
  protected static void logInvalidRequest(String paramString1, String paramString2)
    throws ProfileException
  {
    Profile.handleError(paramString1, paramString2, 3811);
  }
  
  protected static void logModerateActivity(String paramString)
  {
    DebugLog.log(Level.FINE, paramString);
  }
  
  protected static void logSQL(String paramString1, String paramString2)
  {
    DebugLog.log(Level.FINER, paramString1 + " executing: " + paramString2);
  }
  
  protected static void modifyRetrievalDate(Connection paramConnection, int paramInt, String paramString, DateTime paramDateTime)
    throws ProfileException
  {
    String str = "RegisterAdapter.modifyRetrievalDate()";
    logModerateActivity("Entered " + str);
    StringBuffer localStringBuffer = new StringBuffer(85);
    localStringBuffer.append("update ").append("accounts");
    localStringBuffer.append(" set ").append("reg_retrieval_date").append("=?");
    localStringBuffer.append(" where ");
    localStringBuffer.append("directory_id").append("=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("account_id").append("=?");
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setTimestamp(1, ProfileUtil.getTimestamp(paramDateTime));
      localPreparedStatement.setInt(2, paramInt);
      localPreparedStatement.setString(3, paramString);
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
    logModerateActivity("Exited " + str);
  }
  
  protected static void modifyTransaction(Connection paramConnection, RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
    throws ProfileException
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    localRegisterTransactions.add(paramRegisterTransaction);
    modifyTransactions(paramConnection, localRegisterTransactions, paramSecureUser);
  }
  
  protected static void modifyTransactions(Connection paramConnection, RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.modifyTransaction()";
    logModerateActivity("Entered " + str1);
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (paramRegisterTransactions.size() == 0) {
      return;
    }
    int[] arrayOfInt = new int[paramRegisterTransactions.size()];
    int j = 0;
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (RegisterTransaction)localIterator.next();
      int k = ((RegisterTransaction)localObject1).getRegisterIdValue();
      if (k == -1) {
        logInvalidRequest(str1, "Invalid register transaction ID.");
      }
      if (!validTransactionCategories((RegisterTransaction)localObject1)) {
        logInvalidRequest(str1, "Invalid transaction categories.");
      }
      arrayOfInt[(j++)] = k;
    }
    Object localObject1 = null;
    try
    {
      jdMethod_for(paramConnection, paramRegisterTransactions, paramSecureUser);
      deleteTransactionCategories(paramConnection, arrayOfInt);
      addTransactionCategories(paramConnection, paramRegisterTransactions, i);
      String str2 = buildModifyRegisterTransactionSql();
      localObject1 = DBUtil.prepareStatement(paramConnection, str2);
      localIterator = paramRegisterTransactions.iterator();
      while (localIterator.hasNext())
      {
        RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
        prepareModifyRegisterTransaction((PreparedStatement)localObject1, localRegisterTransaction);
        if (Profile.batchMode) {
          ((PreparedStatement)localObject1).addBatch();
        } else {
          DBUtil.executeUpdate((PreparedStatement)localObject1, str2);
        }
      }
      if (Profile.batchMode)
      {
        logSQL(str1, "[Batched Statement] " + str2);
        ((PreparedStatement)localObject1).executeBatch();
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeStatement((PreparedStatement)localObject1);
    }
    logModerateActivity("Exited " + str1);
  }
  
  protected static boolean paymentTransferMatchExists(Connection paramConnection, RegisterTransaction paramRegisterTransaction, String paramString)
    throws ProfileException
  {
    String str = "RegisterAdapter.paymentTransferMatchExists()";
    logModerateActivity("Entered " + str);
    StringBuffer localStringBuffer1 = new StringBuffer(95);
    localStringBuffer1.append("select ").append("reg_transaction_id");
    localStringBuffer1.append(" from ").append("reg_transaction");
    localStringBuffer1.append(" where ");
    localStringBuffer1.append("reference_number").append("=?");
    localStringBuffer1.append(" and ");
    localStringBuffer1.append("account_id").append("=?");
    StringBuffer localStringBuffer2 = new StringBuffer(70);
    localStringBuffer2.append("update ").append("reg_transaction");
    localStringBuffer2.append(" set ");
    localStringBuffer2.append("status").append("=?");
    localStringBuffer2.append(" where ");
    localStringBuffer2.append("reg_transaction_id").append("=?");
    boolean bool = false;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement1 = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      localPreparedStatement1.setString(1, paramRegisterTransaction.getID());
      localPreparedStatement1.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement1, localStringBuffer1.toString());
      if (localResultSet.next())
      {
        localPreparedStatement2 = DBUtil.prepareStatement(paramConnection, localStringBuffer2.toString());
        localPreparedStatement2.setInt(1, 3);
        localPreparedStatement2.setInt(2, localResultSet.getInt("reg_transaction_id"));
        DBUtil.executeUpdate(localPreparedStatement2, localStringBuffer2.toString());
        bool = true;
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement1, localResultSet);
      DBUtil.closeStatement(localPreparedStatement2);
    }
    logModerateActivity("Exited " + str);
    return bool;
  }
  
  protected static void modifyTransactionsByServerTID(Connection paramConnection, RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.modifyTransactionsByServerTID()";
    logModerateActivity("Entered " + str1);
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select reg_transaction.reg_transaction_id, reg_transaction.amount from reg_transaction, reg_tran_category where reg_transaction.reg_transaction_id = reg_tran_category.reg_transaction_id and server_tran_id=? ");
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      Iterator localIterator = paramRegisterTransactions.iterator();
      while (localIterator.hasNext())
      {
        RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
        String str2 = (String)localRegisterTransaction.get("SERVER_TID");
        if (!Profile.hasValue(str2)) {
          logInvalidRequest(str1, "Invalid server transaction ID.");
        }
        localPreparedStatement.setString(1, str2);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select reg_transaction.reg_transaction_id, reg_transaction.amount from reg_transaction, reg_tran_category where reg_transaction.reg_transaction_id = reg_tran_category.reg_transaction_id and server_tran_id=? ");
        while (localResultSet.next())
        {
          String str3 = localResultSet.getString(1);
          BigDecimal localBigDecimal = localResultSet.getBigDecimal(2);
          if (localRegisterTransaction.getRegisterTypeValue() == 16)
          {
            if (RegisterUtil.sameSign(localRegisterTransaction.getAmountValue().getAmountValue(), localBigDecimal))
            {
              localRegisterTransaction.setRegisterId(str3);
              break;
            }
          }
          else
          {
            localRegisterTransaction.setRegisterId(str3);
            break;
          }
        }
        DBUtil.closeResultSet(localResultSet);
        localResultSet = null;
        if (localRegisterTransaction.getRegisterIdValue() != 0) {
          localRegisterTransactions.add(localRegisterTransaction);
        }
      }
      modifyTransactions(paramConnection, localRegisterTransactions, paramSecureUser);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    logModerateActivity("Exited " + str1);
  }
  
  protected static void setDefaultAccount(Connection paramConnection, int paramInt, String paramString)
    throws ProfileException
  {
    String str = "RegisterAdapter.setDefaultAccount()";
    logModerateActivity("Entered " + str);
    StringBuffer localStringBuffer1 = new StringBuffer(60);
    localStringBuffer1.append("update ").append("accounts");
    localStringBuffer1.append(" set ");
    localStringBuffer1.append("reg_default").append("=?");
    localStringBuffer1.append(" where ");
    localStringBuffer1.append("directory_id").append("=?");
    StringBuffer localStringBuffer2 = new StringBuffer(75);
    localStringBuffer2.append("update ").append("accounts");
    localStringBuffer2.append(" set ");
    localStringBuffer2.append("reg_default").append("=?");
    localStringBuffer2.append(" where ");
    localStringBuffer2.append("directory_id").append("=?");
    localStringBuffer2.append(" and ");
    localStringBuffer2.append("account_id").append("=?");
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    try
    {
      localPreparedStatement1 = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      localPreparedStatement1.setString(1, "F");
      localPreparedStatement1.setInt(2, paramInt);
      DBUtil.executeUpdate(localPreparedStatement1, localStringBuffer1.toString());
      localPreparedStatement2 = DBUtil.prepareStatement(paramConnection, localStringBuffer2.toString());
      localPreparedStatement2.setString(1, "T");
      localPreparedStatement2.setInt(2, paramInt);
      localPreparedStatement2.setString(3, paramString);
      DBUtil.executeUpdate(localPreparedStatement2, localStringBuffer2.toString());
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
    }
    logModerateActivity("Exited " + str);
  }
  
  protected static boolean transactionExists(Connection paramConnection, String paramString1, String paramString2, int paramInt, boolean paramBoolean)
    throws ProfileException
  {
    String str = "RegisterAdapter.transactionExists()";
    logModerateActivity("Entered " + str);
    StringBuffer localStringBuffer = new StringBuffer(115);
    localStringBuffer.append("select ").append("reg_transaction_id");
    localStringBuffer.append(" from ").append("reg_transaction");
    localStringBuffer.append(" where ");
    if (paramBoolean) {
      localStringBuffer.append("fi_transaction_id=?");
    } else {
      localStringBuffer.append("server_tran_id=?");
    }
    localStringBuffer.append(" and ");
    localStringBuffer.append("account_id=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("directory_id=?");
    boolean bool = false;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setInt(3, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        bool = true;
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    logModerateActivity("Exited " + str);
    return bool;
  }
  
  protected static int getTransactionStatus(Connection paramConnection, String paramString1, String paramString2, int paramInt, boolean paramBoolean)
    throws ProfileException
  {
    String str = "RegisterAdapter.getTransactionStatus()";
    logModerateActivity("Entered " + str);
    StringBuffer localStringBuffer = new StringBuffer(115);
    localStringBuffer.append("select ").append("status");
    localStringBuffer.append(" from ").append("reg_transaction");
    localStringBuffer.append(" where ");
    if (paramBoolean) {
      localStringBuffer.append("fi_transaction_id=?");
    } else {
      localStringBuffer.append("server_tran_id=?");
    }
    localStringBuffer.append(" and ");
    localStringBuffer.append("account_id=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("directory_id=?");
    int i = -1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramString1);
      localPreparedStatement.setString(2, paramString2);
      localPreparedStatement.setInt(3, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next()) {
        i = localResultSet.getInt(1);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
    }
    logModerateActivity("Exited " + str);
    return i;
  }
  
  protected static boolean validTransactionCategories(RegisterTransaction paramRegisterTransaction)
  {
    TransactionCategories localTransactionCategories = paramRegisterTransaction.getTransactionCategories();
    if (!Profile.hasValue(localTransactionCategories)) {
      return false;
    }
    Iterator localIterator = localTransactionCategories.iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      int i = Profile.convertToInt(localTransactionCategory.getRegisterCategoryId(), -1);
      String str = localTransactionCategory.getAmount();
      if ((i < 0) || (!Profile.hasValue(str))) {
        return false;
      }
    }
    return true;
  }
  
  protected static String buildAddSrvrTranRegisterCategorySql()
  {
    return ProfileUtil.buildInsertSql("reg_srvr_tran_cat", ProfileDefines.REG_SRVR_TRAN_CAT_COLUMNS, 100);
  }
  
  protected static String buildAddRegisterCategorySql()
  {
    return ProfileUtil.buildInsertSql("reg_user_category", ProfileDefines.REG_USER_CATEGORY_COLUMNS, 150);
  }
  
  protected static String buildAddRegisterPayeeSql()
  {
    return ProfileUtil.buildInsertSql("reg_payee", ProfileDefines.REG_PAYEE_COLUMNS, 100);
  }
  
  protected static String buildAddRegisterTransactionSql()
  {
    return ProfileUtil.buildInsertSql("reg_transaction", ProfileDefines.REG_TRANSACTION_COLUMNS, 200);
  }
  
  protected static String buildAddTransactionCategorySql()
  {
    return ProfileUtil.buildInsertSql("reg_tran_category", ProfileDefines.REG_TRAN_CATEGORY_COLUMNS, 110);
  }
  
  protected static String buildDeleteRegisterCategorySql()
  {
    String[] arrayOfString = { "reg_user_cat_id", "directory_id" };
    return ProfileUtil.buildDeleteSql("reg_user_category", arrayOfString, 80);
  }
  
  protected static String buildDeleteRegisterPayeeSql()
  {
    String[] arrayOfString = { "reg_payee_id" };
    return ProfileUtil.buildDeleteSql("reg_payee", arrayOfString, 50);
  }
  
  protected static String buildDeleteRegisterTransactionSql()
  {
    String[] arrayOfString = { "reg_transaction_id" };
    return ProfileUtil.buildDeleteSql("reg_transaction", arrayOfString, 60);
  }
  
  protected static String buildDeleteTransactionCategoriesSql()
  {
    String[] arrayOfString = { "reg_transaction_id" };
    return ProfileUtil.buildDeleteSql("reg_tran_category", arrayOfString, 60);
  }
  
  protected static String buildDeleteSrvrTranRegisterCategorySql(boolean paramBoolean)
  {
    String[] arrayOfString = new String[1];
    if (paramBoolean) {
      arrayOfString[0] = "rec_server_tran_id";
    } else {
      arrayOfString[0] = "server_tran_id";
    }
    return ProfileUtil.buildDeleteSql("reg_srvr_tran_cat", arrayOfString, 100);
  }
  
  protected static String buildGetRegisterAccountsDataSql()
  {
    String[] arrayOfString1 = { "account_id", "reg_enabled", "reg_default", "reg_retrieval_date" };
    String[] arrayOfString2 = { "directory_id" };
    return ProfileUtil.buildSelectSql("accounts", arrayOfString1, arrayOfString2, null, 100);
  }
  
  protected static String buildGetRegisterCategoriesSql()
  {
    String[] arrayOfString = { "directory_id" };
    return ProfileUtil.buildSelectSql("reg_user_category", null, arrayOfString, "name", 70);
  }
  
  protected static String buildGetRegisterPayeesSql()
  {
    String[] arrayOfString = { "directory_id" };
    return ProfileUtil.buildSelectSql("reg_payee", null, arrayOfString, "name", 60);
  }
  
  protected static String buildGetRegisterTransactionSql()
  {
    StringBuffer localStringBuffer = new StringBuffer(250);
    localStringBuffer.append("select ").append("reg_transaction").append(".*,").append("reg_user_cat_id");
    localStringBuffer.append(",").append("reg_tran_category").append(".").append("amount");
    localStringBuffer.append(" from ").append("reg_transaction").append(",").append("reg_tran_category");
    localStringBuffer.append(" where ");
    localStringBuffer.append("reg_transaction").append(".").append("reg_transaction_id").append("=");
    localStringBuffer.append("reg_tran_category").append(".").append("reg_transaction_id");
    localStringBuffer.append(" and ");
    localStringBuffer.append("reg_transaction").append(".").append("directory_id").append("=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("account_id").append("=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("reference_number").append("=?");
    return localStringBuffer.toString();
  }
  
  protected static String buildGetRegisterTransactionsSql(String paramString, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(400);
    localStringBuffer.append("select ").append("reg_transaction").append(".*,").append("reg_user_cat_id").append(",").append("reg_tran_category").append(".").append("amount").append(" ").append("categoryAmount");
    localStringBuffer.append(" from ").append("reg_transaction").append(",").append("reg_tran_category");
    localStringBuffer.append(" where ");
    localStringBuffer.append("reg_transaction").append(".").append("reg_transaction_id").append("=");
    localStringBuffer.append("reg_tran_category").append(".").append("reg_transaction_id");
    localStringBuffer.append(" and ");
    localStringBuffer.append("reg_transaction").append(".").append("directory_id").append("=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("account_id").append("=?");
    setAdditionalCriteria(localStringBuffer, paramString, paramDateTime1, paramDateTime2, paramBoolean);
    localStringBuffer.append(" order by ").append("reg_transaction").append(".").append("reg_transaction_id");
    localStringBuffer.append(" , ").append("reg_tran_category").append(".").append("reg_user_cat_id");
    localStringBuffer.append(" , ").append("reg_transaction").append(".").append("date_issued");
    return localStringBuffer.toString();
  }
  
  protected static String buildGetRegisterTransactionsSqlForReconcilation(RegisterTransaction paramRegisterTransaction, DateTime paramDateTime1, DateTime paramDateTime2)
  {
    String str = paramRegisterTransaction.getReferenceNumber();
    int i = paramRegisterTransaction.getStatusValue();
    int j = paramRegisterTransaction.getTypeValue();
    StringBuffer localStringBuffer = new StringBuffer(400);
    localStringBuffer.append("select ").append("* from ");
    localStringBuffer.append("reg_transaction");
    localStringBuffer.append(" where ");
    localStringBuffer.append("directory_id").append("=?");
    localStringBuffer.append(" and ");
    localStringBuffer.append("account_id").append("=?");
    if (Profile.hasValue(str)) {
      localStringBuffer.append(" and ").append("reference_number=?");
    }
    if (Profile.hasValue(paramDateTime1))
    {
      paramDateTime1.set(11, 0);
      paramDateTime1.set(12, 0);
      paramDateTime1.set(13, 0);
      paramDateTime1.set(14, 0);
      localStringBuffer.append(" and ").append("date_issued").append(" >= ?");
    }
    if (Profile.hasValue(paramDateTime2))
    {
      paramDateTime2.set(11, 23);
      paramDateTime2.set(12, 59);
      paramDateTime2.set(13, 59);
      paramDateTime2.set(14, 999);
      localStringBuffer.append(" and ").append("date_issued").append(" <= ?");
    }
    if (i >= 0) {
      localStringBuffer.append(" and ").append("status").append("=?");
    }
    if (j > 0) {
      localStringBuffer.append(" and ").append("type").append("=?");
    }
    localStringBuffer.append(" order by ");
    localStringBuffer.append("date_issued");
    return localStringBuffer.toString();
  }
  
  protected static String buildGetRegisterTransactionsByTranIdSql()
  {
    StringBuffer localStringBuffer = new StringBuffer(250);
    localStringBuffer.append("select ").append("reg_transaction").append(".*,").append("reg_user_cat_id").append(",").append("reg_tran_category").append(".").append("amount");
    localStringBuffer.append(" from ").append("reg_transaction").append(",").append("reg_tran_category");
    localStringBuffer.append(" where ");
    localStringBuffer.append("reg_transaction").append(".").append("reg_transaction_id").append("=");
    localStringBuffer.append("reg_tran_category").append(".").append("reg_transaction_id");
    localStringBuffer.append(" and ");
    localStringBuffer.append("reg_transaction").append(".").append("status").append("=").append(0);
    localStringBuffer.append(" and ");
    localStringBuffer.append("reg_transaction").append(".").append("fi_transaction_id").append("=?");
    return localStringBuffer.toString();
  }
  
  protected static String buildModifyRegisterCategorySql()
  {
    String[] arrayOfString1 = { "name", "description", "type", "parent_category_id", "tax_related" };
    String[] arrayOfString2 = { "reg_user_cat_id", "directory_id" };
    return ProfileUtil.buildUpdateSql("reg_user_category", arrayOfString1, arrayOfString2, 150);
  }
  
  protected static String buildModifyRegisterPayeeSql()
  {
    String[] arrayOfString1 = { "reg_user_cat_id", "name" };
    String[] arrayOfString2 = { "reg_payee_id" };
    return ProfileUtil.buildUpdateSql("reg_payee", arrayOfString1, arrayOfString2, 75);
  }
  
  protected static String buildModifyRegisterTransactionSql()
  {
    String[] arrayOfString1 = { "account_id", "fi_transaction_id", "reference_number", "payee_name", "type", "status", "memo", "date_issued", "date_cleared", "server_tran_id", "rec_server_tran_id", "amount" };
    String[] arrayOfString2 = { "reg_transaction_id" };
    return ProfileUtil.buildUpdateSql("reg_transaction", arrayOfString1, arrayOfString2, 165);
  }
  
  protected static String buildModifySrvrTranRegisterCategorySql(boolean paramBoolean)
  {
    String[] arrayOfString1 = { "reg_user_cat_id" };
    String[] arrayOfString2 = new String[1];
    if (paramBoolean) {
      arrayOfString2[0] = "rec_server_tran_id";
    } else {
      arrayOfString2[0] = "server_tran_id";
    }
    return ProfileUtil.buildUpdateSql("reg_srvr_tran_cat", arrayOfString1, arrayOfString2, 100);
  }
  
  protected static void prepareAddRegisterCategory(PreparedStatement paramPreparedStatement, RegisterCategory paramRegisterCategory, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramRegisterCategory.getIdValue());
    paramPreparedStatement.setInt(2, paramInt);
    paramPreparedStatement.setString(3, paramRegisterCategory.getName());
    paramPreparedStatement.setString(4, paramRegisterCategory.getDescription());
    paramPreparedStatement.setInt(5, paramRegisterCategory.getTypeValue());
    paramPreparedStatement.setInt(6, paramRegisterCategory.getParentCategoryValue());
    paramPreparedStatement.setString(7, RegisterUtil.getTFChar(paramRegisterCategory.getTaxRelated()));
  }
  
  protected static void prepareAddSrvrTranRegisterCategory(PreparedStatement paramPreparedStatement, ServerTransaction paramServerTransaction)
    throws SQLException
  {
    paramPreparedStatement.setString(1, paramServerTransaction.getServerTID());
    paramPreparedStatement.setString(2, paramServerTransaction.getRecServerTID());
    paramPreparedStatement.setInt(3, paramServerTransaction.getRegCategoryID());
  }
  
  protected static void prepareAddRegisterPayee(PreparedStatement paramPreparedStatement, RegisterPayee paramRegisterPayee, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramRegisterPayee.getIdValue());
    paramPreparedStatement.setInt(2, paramRegisterPayee.getDefaultCategoryValue());
    paramPreparedStatement.setInt(3, paramInt);
    paramPreparedStatement.setString(4, paramRegisterPayee.getName());
  }
  
  protected static void prepareAddRegisterTransaction(PreparedStatement paramPreparedStatement, RegisterTransaction paramRegisterTransaction, int paramInt, String paramString)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramRegisterTransaction.getRegisterIdValue());
    paramPreparedStatement.setInt(2, paramInt);
    paramPreparedStatement.setString(3, paramString);
    paramPreparedStatement.setString(4, paramRegisterTransaction.getID());
    paramPreparedStatement.setString(5, padRefNum(paramRegisterTransaction.getReferenceNumber()));
    paramPreparedStatement.setString(6, paramRegisterTransaction.getPayeeName());
    paramPreparedStatement.setInt(7, paramRegisterTransaction.getRegisterTypeValue());
    paramPreparedStatement.setInt(8, paramRegisterTransaction.getStatusValue());
    paramPreparedStatement.setString(9, paramRegisterTransaction.getMemo());
    paramPreparedStatement.setTimestamp(10, ProfileUtil.getTimestamp(paramRegisterTransaction.getDateIssuedValue()));
    paramPreparedStatement.setTimestamp(11, ProfileUtil.getTimestamp(paramRegisterTransaction.getDateValue()));
    paramPreparedStatement.setString(12, (String)paramRegisterTransaction.get("SERVER_TID"));
    paramPreparedStatement.setString(13, (String)paramRegisterTransaction.get("RECSRVRTID"));
    paramPreparedStatement.setBigDecimal(14, paramRegisterTransaction.getAmountValue().getAmountValue().setScale(3, 5));
  }
  
  protected static void prepareAddTransactionCategory(PreparedStatement paramPreparedStatement, TransactionCategory paramTransactionCategory, int paramInt1, int paramInt2)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt1);
    paramPreparedStatement.setInt(2, paramTransactionCategory.getRegisterCategoryIdValue());
    paramPreparedStatement.setInt(3, paramInt2);
    paramPreparedStatement.setBigDecimal(4, paramTransactionCategory.getAmountValue().getAmountValue().setScale(3, 5));
  }
  
  protected static void prepareDeleteRegisterCategory(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt1);
    paramPreparedStatement.setInt(2, paramInt2);
  }
  
  protected static void prepareDeleteRegisterPayee(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt);
  }
  
  protected static void prepareDeleteRegisterTransaction(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt);
  }
  
  protected static void prepareDeleteTransactionCategories(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt);
  }
  
  protected static void prepareDeleteSrvrTranRegisterCategory(PreparedStatement paramPreparedStatement, String paramString)
    throws SQLException
  {
    paramPreparedStatement.setString(1, paramString);
  }
  
  protected static void prepareGetRegisterAccountsData(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt);
  }
  
  protected static void prepareGetRegisterCategories(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt);
  }
  
  protected static void prepareGetRegisterPayees(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt);
  }
  
  protected static void prepareGetRegisterTransaction(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt);
    paramPreparedStatement.setString(2, paramString1);
    paramPreparedStatement.setString(3, paramString2);
  }
  
  protected static void prepareGetRegisterTransactions(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2, DateTime paramDateTime1, DateTime paramDateTime2)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramInt);
    paramPreparedStatement.setString(2, paramString1);
    int i = 3;
    if (Profile.hasValue(paramString2)) {
      paramPreparedStatement.setString(i++, padRefNum(paramString2));
    }
    if (Profile.hasValue(paramDateTime1)) {
      paramPreparedStatement.setTimestamp(i++, ProfileUtil.getTimestamp(paramDateTime1));
    }
    if (Profile.hasValue(paramDateTime2)) {
      paramPreparedStatement.setTimestamp(i, ProfileUtil.getTimestamp(paramDateTime2));
    }
  }
  
  protected static void prepareGetRegisterTransactionsForReconcilation(PreparedStatement paramPreparedStatement, int paramInt, String paramString, RegisterTransaction paramRegisterTransaction, DateTime paramDateTime1, DateTime paramDateTime2)
    throws SQLException
  {
    String str = paramRegisterTransaction.getReferenceNumber();
    int i = paramRegisterTransaction.getStatusValue();
    int j = paramRegisterTransaction.getTypeValue();
    paramPreparedStatement.setInt(1, paramInt);
    paramPreparedStatement.setString(2, paramString);
    int k = 3;
    if (Profile.hasValue(str)) {
      paramPreparedStatement.setString(k++, padRefNum(str));
    }
    if (Profile.hasValue(paramDateTime1)) {
      paramPreparedStatement.setTimestamp(k++, ProfileUtil.getTimestamp(paramDateTime1));
    }
    if (Profile.hasValue(paramDateTime2)) {
      paramPreparedStatement.setTimestamp(k++, ProfileUtil.getTimestamp(paramDateTime2));
    }
    if (i >= 0) {
      paramPreparedStatement.setInt(k++, i);
    }
    if (j > 0) {
      paramPreparedStatement.setInt(k, j);
    }
  }
  
  protected static void prepareGetRegisterTransactionsByTranId(PreparedStatement paramPreparedStatement, String paramString)
    throws SQLException
  {
    paramPreparedStatement.setString(1, paramString);
  }
  
  protected static void prepareModifyRegisterCategory(PreparedStatement paramPreparedStatement, RegisterCategory paramRegisterCategory, int paramInt)
    throws SQLException
  {
    paramPreparedStatement.setString(1, paramRegisterCategory.getName());
    paramPreparedStatement.setString(2, paramRegisterCategory.getDescription());
    paramPreparedStatement.setInt(3, paramRegisterCategory.getTypeValue());
    paramPreparedStatement.setInt(4, paramRegisterCategory.getParentCategoryValue());
    paramPreparedStatement.setString(5, RegisterUtil.getTFChar(paramRegisterCategory.getTaxRelatedValue()));
    paramPreparedStatement.setInt(6, paramRegisterCategory.getIdValue());
    paramPreparedStatement.setInt(7, paramInt);
  }
  
  protected static void prepareModifyRegisterPayee(PreparedStatement paramPreparedStatement, RegisterPayee paramRegisterPayee)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramRegisterPayee.getDefaultCategoryValue());
    paramPreparedStatement.setString(2, paramRegisterPayee.getName());
    paramPreparedStatement.setInt(3, paramRegisterPayee.getIdValue());
  }
  
  protected static void prepareModifyRegisterTransaction(PreparedStatement paramPreparedStatement, RegisterTransaction paramRegisterTransaction)
    throws SQLException
  {
    paramPreparedStatement.setString(1, (String)paramRegisterTransaction.get("ACCOUNT"));
    paramPreparedStatement.setString(2, paramRegisterTransaction.getID());
    paramPreparedStatement.setString(3, padRefNum(paramRegisterTransaction.getReferenceNumber()));
    paramPreparedStatement.setString(4, paramRegisterTransaction.getPayeeName());
    paramPreparedStatement.setInt(5, paramRegisterTransaction.getTypeValue());
    paramPreparedStatement.setInt(6, paramRegisterTransaction.getStatusValue());
    paramPreparedStatement.setString(7, paramRegisterTransaction.getMemo());
    paramPreparedStatement.setTimestamp(8, ProfileUtil.getTimestamp(paramRegisterTransaction.getDateIssuedValue()));
    paramPreparedStatement.setTimestamp(9, ProfileUtil.getTimestamp(paramRegisterTransaction.getDateValue()));
    paramPreparedStatement.setString(10, (String)paramRegisterTransaction.get("SERVER_TID"));
    paramPreparedStatement.setString(11, (String)paramRegisterTransaction.get("RECSRVRTID"));
    paramPreparedStatement.setBigDecimal(12, paramRegisterTransaction.getAmountValue().getAmountValue().setScale(3, 5));
    paramPreparedStatement.setInt(13, paramRegisterTransaction.getRegisterIdValue());
  }
  
  protected static void prepareModifySrvrTranRegisterCategory(PreparedStatement paramPreparedStatement, ServerTransaction paramServerTransaction)
    throws SQLException
  {
    paramPreparedStatement.setInt(1, paramServerTransaction.getRegCategoryID());
    if (paramServerTransaction.getRecServerTID() != null) {
      paramPreparedStatement.setString(2, paramServerTransaction.getRecServerTID());
    } else {
      paramPreparedStatement.setString(2, paramServerTransaction.getServerTID());
    }
  }
  
  protected static void fillRegisterCategoryBean(RegisterCategory paramRegisterCategory, ResultSet paramResultSet, boolean paramBoolean)
    throws SQLException
  {
    if (paramBoolean) {
      paramRegisterCategory.setId(paramResultSet.getInt("reg_def_cat_id"));
    } else {
      paramRegisterCategory.setId(paramResultSet.getInt("reg_user_cat_id"));
    }
    paramRegisterCategory.setName(paramResultSet.getString("name"));
    paramRegisterCategory.setDescription(paramResultSet.getString("description"));
    paramRegisterCategory.setType(paramResultSet.getInt("type"));
    paramRegisterCategory.setTaxRelated(RegisterUtil.getBooleanString(paramResultSet.getString("tax_related")));
    paramRegisterCategory.setParentCategory(paramResultSet.getInt("parent_category_id"));
    paramRegisterCategory.setIsDefaultCategory(paramBoolean);
  }
  
  protected static void fillRegisterPayeeBean(RegisterPayee paramRegisterPayee, ResultSet paramResultSet)
    throws SQLException
  {
    paramRegisterPayee.setId(paramResultSet.getInt("reg_payee_id"));
    paramRegisterPayee.setName(paramResultSet.getString("name"));
    paramRegisterPayee.setDefaultCategory(paramResultSet.getInt("reg_user_cat_id"));
  }
  
  protected static void fillRegisterTransactionBean(RegisterTransaction paramRegisterTransaction, ResultSet paramResultSet)
    throws SQLException
  {
    paramRegisterTransaction.setRegisterId(paramResultSet.getInt("reg_transaction_id"));
    paramRegisterTransaction.set("ACCOUNT", paramResultSet.getString("account_id"));
    paramRegisterTransaction.setID(paramResultSet.getString("fi_transaction_id"));
    paramRegisterTransaction.setReferenceNumber(unPadRefNum(paramResultSet.getString("reference_number")));
    paramRegisterTransaction.setPayeeName(paramResultSet.getString("payee_name"));
    paramRegisterTransaction.setMemo(paramResultSet.getString("memo"));
    paramRegisterTransaction.setType(paramResultSet.getInt("type"));
    paramRegisterTransaction.setStatus(paramResultSet.getInt("status"));
    paramRegisterTransaction.setDateIssued(ProfileUtil.getDateTime(paramResultSet.getTimestamp("date_issued")));
    paramRegisterTransaction.setDate(ProfileUtil.getDateTime(paramResultSet.getTimestamp("date_cleared")));
    paramRegisterTransaction.set("SERVER_TID", paramResultSet.getString("server_tran_id"));
    paramRegisterTransaction.set("RECSRVRTID", paramResultSet.getString("rec_server_tran_id"));
    paramRegisterTransaction.setAmount(paramResultSet.getBigDecimal("amount").toString());
  }
  
  protected static void fillTransactionCategoryBean(TransactionCategory paramTransactionCategory, ResultSet paramResultSet)
    throws SQLException
  {
    BigDecimal localBigDecimal = paramResultSet.getBigDecimal("amount");
    Currency localCurrency = new Currency(localBigDecimal, paramTransactionCategory.getLocale());
    paramTransactionCategory.setAmount(localCurrency);
    paramTransactionCategory.setRegisterCategoryId(paramResultSet.getInt("reg_user_cat_id"));
  }
  
  protected static void setAdditionalCriteria(StringBuffer paramStringBuffer, String paramString, DateTime paramDateTime1, DateTime paramDateTime2, boolean paramBoolean)
  {
    boolean bool = false;
    if (Profile.hasValue(paramString))
    {
      bool = sqlJoinCriteria(paramStringBuffer, bool);
      paramStringBuffer.append("reference_number=?");
    }
    if (Profile.hasValue(paramDateTime1))
    {
      bool = sqlJoinCriteria(paramStringBuffer, bool);
      paramDateTime1.set(11, 0);
      paramDateTime1.set(12, 0);
      paramDateTime1.set(13, 0);
      paramDateTime1.set(14, 0);
      paramStringBuffer.append("date_issued").append(" >= ?");
    }
    if (Profile.hasValue(paramDateTime2))
    {
      bool = sqlJoinCriteria(paramStringBuffer, bool);
      paramDateTime2.set(11, 23);
      paramDateTime2.set(12, 59);
      paramDateTime2.set(13, 59);
      paramDateTime2.set(14, 999);
      paramStringBuffer.append("date_issued").append(" <= ?");
    }
    if ((bool) && (paramBoolean))
    {
      paramStringBuffer.append(" or ");
      paramStringBuffer.append("status").append("=").append(0);
      paramStringBuffer.append(" or ");
      paramStringBuffer.append("status").append("=").append(1);
    }
    if (bool) {
      paramStringBuffer.append(")");
    }
  }
  
  protected static boolean sqlJoinCriteria(StringBuffer paramStringBuffer, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramStringBuffer.append(" and ");
    } else {
      paramStringBuffer.append(" and (");
    }
    return true;
  }
  
  protected static int getDirectoryID(SecureUser paramSecureUser)
  {
    if (paramSecureUser.getBusinessID() != 0) {
      return paramSecureUser.getBusinessID();
    }
    return paramSecureUser.getProfileID();
  }
  
  protected static int getDirectoryIDForConsumer(SecureUser paramSecureUser)
  {
    if (paramSecureUser.getBusinessID() != 0) {
      return paramSecureUser.getBusinessID();
    }
    if (paramSecureUser.getPrimaryUserID() != 0) {
      return paramSecureUser.getPrimaryUserID();
    }
    return paramSecureUser.getProfileID();
  }
  
  protected static boolean isBusinessUser(SecureUser paramSecureUser)
  {
    return paramSecureUser.getBusinessID() != 0;
  }
  
  protected static String getProfilePoolName()
  {
    return Profile.poolName;
  }
  
  protected static void exportCategoriesReport(ReportResult paramReportResult, ResultSet paramResultSet)
    throws SQLException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("reg_user_cat_id");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("directory_id");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("name");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("parent_name");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("description");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("type");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("tax_related");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Boolean");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("parent_cat_id");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    while (paramResultSet.next())
    {
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = null;
      int i = paramResultSet.getInt(1);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Integer(i));
      int j = paramResultSet.getInt(2);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Integer(j));
      String str1 = paramResultSet.getString(3);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str1);
      String str2 = paramResultSet.getString(4);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str2);
      String str3 = paramResultSet.getString(5);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str3);
      int k = paramResultSet.getInt(6);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Integer(k));
      String str4 = paramResultSet.getString(7);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Boolean(RegisterUtil.getBoolean(str4)));
      int m = paramResultSet.getInt(8);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(paramResultSet.wasNull() ? null : new Integer(m));
      paramReportResult.addRow(localReportRow);
    }
  }
  
  protected static void exportRegisterTransactions(ReportResult paramReportResult, ResultSet paramResultSet)
    throws SQLException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("reg_transaction_id");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("DIRECTORY_ID");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("ACCOUNT_ID");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("fi_transaction_id");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Reference Number");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("payee_name");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Type");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("STATUS");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Memo");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("date_issued");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("date_cleared");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("rec_server_tran_id");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("server_tran_id");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("CATEGORY_ID");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Amount");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("parent_category_name");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("category_name");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(1);
    paramReportResult.addColumn(localReportColumn);
    while (paramResultSet.next())
    {
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = null;
      int i = paramResultSet.getInt(1);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Integer(i));
      int j = paramResultSet.getInt(2);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Integer(j));
      String str1 = paramResultSet.getString(3);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str1);
      String str2 = paramResultSet.getString(4);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str2);
      String str3 = paramResultSet.getString(5);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str3);
      String str4 = paramResultSet.getString(6);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str4);
      int k = paramResultSet.getInt(7);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Integer(k));
      int m = paramResultSet.getInt(8);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Integer(m));
      String str5 = paramResultSet.getString(9);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str5);
      DateTime localDateTime1 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(10));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime1);
      DateTime localDateTime2 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(11));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime2);
      String str6 = paramResultSet.getString(12);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str6);
      String str7 = paramResultSet.getString(13);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str7);
      int n = paramResultSet.getInt(14);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(new Integer(n));
      Currency localCurrency = new Currency(paramResultSet.getBigDecimal(15), localLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localCurrency);
      String str8 = paramResultSet.getString(16);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str8);
      String str9 = paramResultSet.getString(17);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str9);
      paramReportResult.addRow(localReportRow);
    }
  }
  
  protected static void exportRegisterTransactionsForDisplay(ReportResult paramReportResult, ResultSet paramResultSet)
    throws SQLException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    ArrayList localArrayList = new ArrayList();
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Type");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Ref. Number");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Date Issued");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Date Cleared");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Payee");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(20);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Category");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Sub Category");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Status");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localArrayList = new ArrayList();
    localArrayList.add("Amount");
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    while (paramResultSet.next())
    {
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = null;
      int i = paramResultSet.getInt(7);
      localReportDataItem = localReportDataItems.add();
      try
      {
        String str1 = ResourceUtil.getString("TransactionTypeResourceFile", "com.ffusion.beans.register.resources", localLocale);
        localReportDataItem.setData(ResourceUtil.getString("TransactionType" + Integer.toString(i), str1, localLocale));
      }
      catch (Exception localException)
      {
        localReportDataItem.setData(ResourceUtil.getString("TranType." + String.valueOf(c(i)), "com.ffusion.beans.register.resources", localLocale));
      }
      String str2 = paramResultSet.getString(5);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str2);
      DateTime localDateTime1 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(10));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime1);
      DateTime localDateTime2 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(11));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime2);
      String str3 = paramResultSet.getString(6);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str3);
      String str4 = paramResultSet.getString(16);
      localReportDataItem = localReportDataItems.add();
      if (str4 == null) {
        localReportDataItem.setData("");
      } else {
        localReportDataItem.setData(str4);
      }
      String str5 = paramResultSet.getString(17);
      localReportDataItem = localReportDataItems.add();
      if (str5 == null) {
        localReportDataItem.setData("");
      } else {
        localReportDataItem.setData(str5);
      }
      int j = paramResultSet.getInt(8);
      localReportDataItem = localReportDataItems.add();
      if (RegisterUtil.isReconciled(j)) {
        localReportDataItem.setData("Reconciled");
      } else {
        localReportDataItem.setData("Unreconciled");
      }
      Currency localCurrency = new Currency(paramResultSet.getBigDecimal(15), localLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localCurrency);
      paramReportResult.addRow(localReportRow);
    }
  }
  
  private static int c(int paramInt)
  {
    int i = 30;
    switch (paramInt)
    {
    case 6: 
    case 7: 
    case 28: 
      i = 0;
      break;
    case 13: 
    case 25: 
      i = 5;
      break;
    case 3: 
      i = 1;
      break;
    case 5: 
    case 12: 
      i = 2;
      break;
    case 1: 
    case 29: 
      i = 3;
      break;
    case 16: 
      i = 6;
    }
    return i;
  }
  
  public static RegisterTransactions getPagedTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    ResultSet localResultSet = null;
    Statement localStatement = null;
    Connection localConnection = null;
    String str = "RegisterAdapter.getPagedTransactions()";
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    try
    {
      RegisterTransactions localRegisterTransactions1 = new RegisterTransactions();
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      jdMethod_int(localConnection, paramPagingContext, i, paramHashMap);
      jdMethod_for(localConnection, paramPagingContext, i, paramHashMap);
      if (!paramPagingContext.getMap().containsKey("UPPER_BOUND_TransactionIndex"))
      {
        paramPagingContext.getMap().put("TOTAL_TRANS", "0");
        paramPagingContext.getMap().put("PAGE_SIZE", MapUtil.getIntValue(paramHashMap, "PAGESIZE", PAGESIZE) + "");
        RegisterTransactions localRegisterTransactions2 = localRegisterTransactions1;
        return localRegisterTransactions2;
      }
      paramPagingContext.getMap().remove("CurrentPage");
      paramPagingContext.getMap().remove("PageSettings");
      int j = jdMethod_new(localConnection, paramPagingContext, i, paramHashMap);
      paramPagingContext.getMap().put("NumPages", new Integer(j));
      int k = 1;
      paramPagingContext.getMap().put("CurrentPage", new Integer(k));
      jdMethod_char(paramPagingContext.getMap());
      localResultSet = jdMethod_for(paramPagingContext, i, true, paramHashMap, localConnection);
      localStatement = localResultSet.getStatement();
      jdMethod_for(paramSecureUser, localRegisterTransactions1, localResultSet);
      jdMethod_for(paramPagingContext, localRegisterTransactions1);
      RegisterTransactions localRegisterTransactions3 = localRegisterTransactions1;
      return localRegisterTransactions3;
    }
    catch (ProfileException localProfileException1)
    {
      throw localProfileException1;
    }
    catch (Exception localException)
    {
      ProfileException localProfileException2 = new ProfileException(0);
      throw localProfileException2;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localStatement);
      if (localConnection != null) {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
  }
  
  public static RegisterTransactions getNextTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    ResultSet localResultSet = null;
    Statement localStatement = null;
    String str = "RegisterAdapter.getNextTransactions()";
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    HashMap localHashMap = paramPagingContext.getMap();
    try
    {
      RegisterTransactions localRegisterTransactions1 = new RegisterTransactions();
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int j = MapUtil.getIntValue(localHashMap, "CurrentPage", -1);
      if (j != -1)
      {
        j++;
        localHashMap.put("CurrentPage", new Integer(j));
        jdMethod_char(localHashMap);
      }
      localResultSet = jdMethod_for(paramPagingContext, i, true, paramHashMap, localConnection);
      localStatement = localResultSet.getStatement();
      jdMethod_for(paramSecureUser, localRegisterTransactions1, localResultSet);
      jdMethod_for(paramPagingContext, localRegisterTransactions1);
      RegisterTransactions localRegisterTransactions2 = localRegisterTransactions1;
      return localRegisterTransactions2;
    }
    catch (ProfileException localProfileException1)
    {
      throw localProfileException1;
    }
    catch (Exception localException)
    {
      ProfileException localProfileException2 = new ProfileException(0);
      throw localProfileException2;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localStatement);
      if (localConnection != null) {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
  }
  
  public static RegisterTransactions getPreviousTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    ResultSet localResultSet = null;
    Statement localStatement = null;
    String str = "RegisterAdapter.getPreviousTransactions()";
    int i = getDirectoryIDForConsumer(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str, "Invalid directory ID.");
    }
    HashMap localHashMap1 = paramPagingContext.getMap();
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap1.get("ReportCriteria");
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      int j = MapUtil.getIntValue(localHashMap1, "CurrentPage", -1);
      if (j != -1)
      {
        j--;
        localHashMap1.put("CurrentPage", new Integer(j));
        localObject1 = (IntMap)localHashMap1.get("PageSettings");
        HashMap localHashMap2 = (HashMap)((IntMap)localObject1).get(j);
        if (localReportCriteria != null) {
          for (int k = 0; k < localReportCriteria.getSortCriteria().size(); k++)
          {
            ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(k);
            localHashMap1.put("SORT_VALUE_MIN_" + localReportSortCriterion.getName(), localHashMap2.get("SORT_VALUE_MIN_" + localReportSortCriterion.getName()));
            localHashMap1.put("SORT_VALUE_MAX_" + localReportSortCriterion.getName(), localHashMap2.get("SORT_VALUE_MAX_" + localReportSortCriterion.getName()));
          }
        }
        localHashMap1.put("SORT_VALUE_MIN_TransactionIndex", localHashMap2.get("SORT_VALUE_MIN_TransactionIndex"));
        localHashMap1.put("SORT_VALUE_MAX_TransactionIndex", localHashMap2.get("SORT_VALUE_MAX_TransactionIndex"));
      }
      localResultSet = jdMethod_for(paramPagingContext, i, true, paramHashMap, localConnection);
      localStatement = localResultSet.getStatement();
      jdMethod_for(paramSecureUser, localRegisterTransactions, localResultSet);
      jdMethod_for(paramPagingContext, localRegisterTransactions);
      Object localObject1 = localRegisterTransactions;
      return localObject1;
    }
    catch (ProfileException localProfileException1)
    {
      throw localProfileException1;
    }
    catch (Exception localException)
    {
      ProfileException localProfileException2 = new ProfileException(0);
      throw localProfileException2;
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localStatement);
      if (localConnection != null) {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
  }
  
  public static RegisterTransactions getLastTransactions(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    HashMap localHashMap = paramPagingContext.getMap();
    try
    {
      RegisterTransactions localRegisterTransactions1 = new RegisterTransactions();
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      int i = MapUtil.getIntValue(localHashMap, "CurrentPage", 0);
      int j = MapUtil.getIntValue(localHashMap, "NumPages", 0);
      while (i < j)
      {
        localRegisterTransactions1 = getNextTransactions(paramSecureUser, paramPagingContext, paramHashMap);
        i = MapUtil.getIntValue(localHashMap, "CurrentPage", 0);
      }
      jdMethod_for(paramPagingContext, localRegisterTransactions1);
      RegisterTransactions localRegisterTransactions2 = localRegisterTransactions1;
      return localRegisterTransactions2;
    }
    catch (ProfileException localProfileException1)
    {
      throw localProfileException1;
    }
    catch (Exception localException)
    {
      ProfileException localProfileException2 = new ProfileException(0);
      throw localProfileException2;
    }
    finally
    {
      if (localConnection != null) {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
  }
  
  public static RegisterTransaction getLastMatchingTransaction(SecureUser paramSecureUser, RegisterTransaction paramRegisterTransaction, HashMap paramHashMap)
    throws ProfileException
  {
    Connection localConnection = null;
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      RegisterTransactions localRegisterTransactions = new RegisterTransactions();
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localObject1 = new StringBuffer("select rt.* from reg_transaction rt ");
      ((StringBuffer)localObject1).append("where rt.directory_id=? ");
      Object localObject2 = null;
      Object localObject3 = null;
      Object localObject4 = null;
      Object localObject5 = null;
      Object localObject6 = null;
      Object localObject7 = null;
      int i = -1;
      int j = -1;
      Object localObject8 = null;
      String str = (String)paramRegisterTransaction.get("ACCOUNT");
      if ((str != null) && (str.length() > 0))
      {
        ((StringBuffer)localObject1).append(" and rt.account_id= ? ");
        localObject2 = str;
      }
      str = paramRegisterTransaction.getPayeeName();
      if ((str != null) && (str.length() > 0))
      {
        ((StringBuffer)localObject1).append(" and rt.payee_name= ? ");
        localObject3 = str;
      }
      str = paramRegisterTransaction.getDateIssued();
      if ((str != null) && (str.length() > 0))
      {
        ((StringBuffer)localObject1).append(" and date(rt.date_issued) = ? ");
        localObject4 = str;
      }
      str = paramRegisterTransaction.getDate();
      if ((str != null) && (str.length() > 0))
      {
        ((StringBuffer)localObject1).append(" and date(rt.date_cleared) = ? ");
        localObject5 = str;
      }
      str = paramRegisterTransaction.getID();
      if ((str != null) && (str.length() != 0))
      {
        ((StringBuffer)localObject1).append(" and rt.fi_transaction_id= ? ");
        localObject6 = str;
      }
      str = paramRegisterTransaction.getReferenceNumber();
      if ((str != null) && (str.length() != 0))
      {
        ((StringBuffer)localObject1).append(" and rt.reference_number= ? ");
        localObject7 = str;
      }
      int k = paramRegisterTransaction.getRegisterTypeValue();
      if (k >= 0)
      {
        ((StringBuffer)localObject1).append(" and rt.type= ? ");
        i = k;
      }
      k = paramRegisterTransaction.getStatusValue();
      if (k >= 0)
      {
        ((StringBuffer)localObject1).append(" and rt.status= ? ");
        j = k;
      }
      str = paramRegisterTransaction.getMemo();
      if ((str != null) && (str.length() > 0))
      {
        ((StringBuffer)localObject1).append(" and rt.memo like ? ");
        localObject8 = str;
      }
      ((StringBuffer)localObject1).append(" ORDER BY rt.reg_transaction_id DESC");
      localPreparedStatement = jdMethod_for(localConnection, ((StringBuffer)localObject1).toString());
      int m = 1;
      localPreparedStatement.setInt(m++, paramSecureUser.getProfileID());
      if (localObject2 != null) {
        localPreparedStatement.setString(m++, localObject2);
      }
      if (localObject3 != null) {
        localPreparedStatement.setString(m++, localObject3);
      }
      if (localObject4 != null) {
        jdMethod_for(localPreparedStatement, m++, localObject4, ahX);
      }
      if (localObject5 != null) {
        jdMethod_for(localPreparedStatement, m++, localObject5, ahX);
      }
      if (localObject6 != null) {
        localPreparedStatement.setString(m++, localObject6);
      }
      if (localObject7 != null) {
        localPreparedStatement.setString(m++, padRefNum(localObject7));
      }
      if (i >= 0) {
        localPreparedStatement.setInt(m++, i);
      }
      if (j >= 0) {
        localPreparedStatement.setInt(m++, j);
      }
      if (localObject8 != null) {
        localPreparedStatement.setString(m++, "%" + localObject8 + "%");
      }
      localPreparedStatement.setMaxRows(1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, ((StringBuffer)localObject1).toString());
      jdMethod_for(paramSecureUser, localRegisterTransactions, localResultSet);
      if ((localRegisterTransactions != null) && (localRegisterTransactions.size() > 0))
      {
        localRegisterTransaction1 = (RegisterTransaction)localRegisterTransactions.get(0);
        RegisterTransaction localRegisterTransaction2 = localRegisterTransaction1;
        return localRegisterTransaction2;
      }
      RegisterTransaction localRegisterTransaction1 = null;
      return localRegisterTransaction1;
    }
    catch (ProfileException localProfileException)
    {
      throw localProfileException;
    }
    catch (Exception localException)
    {
      Object localObject1 = new ProfileException(0, localException);
      throw ((Throwable)localObject1);
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      if (localConnection != null) {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
  }
  
  private static void jdMethod_int(StringBuffer paramStringBuffer, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    for (int i = 0; i < localReportSortCriteria.size(); i++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i);
      paramStringBuffer.append(M(localReportSortCriterion.getName())).append(", ");
    }
    paramStringBuffer.append(" rt.reg_transaction_id ");
  }
  
  private static void jdMethod_int(Connection paramConnection, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      localObject1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", localObject1);
    }
    Object localObject1 = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      Locale localLocale = (Locale)paramHashMap.get("Locale");
      if (localLocale == null) {
        localLocale = Locale.getDefault();
      }
      StringBuffer localStringBuffer = jdMethod_for(paramPagingContext, paramHashMap, localLocale);
      localStringBuffer.append(" where rt.directory_id=? ");
      jdMethod_for(localStringBuffer, paramPagingContext, paramHashMap);
      jdMethod_for(localStringBuffer, paramPagingContext, true, Boolean.FALSE, paramHashMap);
      localPreparedStatement = jdMethod_for(paramConnection, localStringBuffer.toString());
      int i = 1;
      localPreparedStatement.setString(i++, localLocale.toString());
      localPreparedStatement.setInt(i++, paramInt);
      i = jdMethod_for(localPreparedStatement, i, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int j = 0; j < localReportSortCriteria.size(); j++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
          if (localReportSortCriterion.getName().equals("Reference Number")) {
            paramPagingContext.getMap().put("UPPER_BOUND_" + localReportSortCriterion.getName(), unPadRefNum((String)jdMethod_for((ResultSet)localObject1, j + 1, localReportCriteria.getLocale())));
          } else {
            paramPagingContext.getMap().put("UPPER_BOUND_" + localReportSortCriterion.getName(), jdMethod_for((ResultSet)localObject1, j + 1, localReportCriteria.getLocale()));
          }
        }
        paramPagingContext.getMap().put("UPPER_BOUND_TransactionIndex", jdMethod_for((ResultSet)localObject1, localReportSortCriteria.size() + 1, localReportCriteria.getLocale()));
      }
    }
    catch (SQLException localSQLException)
    {
      throw new ProfileException(0, localSQLException);
    }
    catch (Exception localException)
    {
      throw new ProfileException(0, localException);
    }
    finally
    {
      DBUtil.closeResultSet((ResultSet)localObject1);
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      localObject1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", localObject1);
    }
    Object localObject1 = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      Locale localLocale = (Locale)paramHashMap.get("Locale");
      if (localLocale == null) {
        localLocale = Locale.getDefault();
      }
      StringBuffer localStringBuffer = jdMethod_for(paramPagingContext, paramHashMap, localLocale);
      localStringBuffer.append(" where rt.directory_id=? ");
      jdMethod_for(localStringBuffer, paramPagingContext, paramHashMap);
      jdMethod_for(localStringBuffer, paramPagingContext, true, Boolean.TRUE, paramHashMap);
      localPreparedStatement = jdMethod_for(paramConnection, localStringBuffer.toString());
      int i = 1;
      localPreparedStatement.setString(i++, localLocale.toString());
      localPreparedStatement.setInt(i++, paramInt);
      i = jdMethod_for(localPreparedStatement, i, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (((ResultSet)localObject1).next())
      {
        for (int j = 0; j < localReportSortCriteria.size(); j++)
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
          if (localReportSortCriterion.getName().equals("Reference Number")) {
            paramPagingContext.getMap().put("LOWER_BOUND_" + localReportSortCriterion.getName(), unPadRefNum((String)jdMethod_for((ResultSet)localObject1, j + 1, localReportCriteria.getLocale())));
          } else {
            paramPagingContext.getMap().put("LOWER_BOUND_" + localReportSortCriterion.getName(), jdMethod_for((ResultSet)localObject1, j + 1, localReportCriteria.getLocale()));
          }
        }
        paramPagingContext.getMap().put("LOWER_BOUND_TransactionIndex", jdMethod_for((ResultSet)localObject1, localReportSortCriteria.size() + 1, localReportCriteria.getLocale()));
      }
    }
    catch (Exception localException)
    {
      throw new ProfileException(0, localException);
    }
    finally
    {
      DBUtil.closeResultSet((ResultSet)localObject1);
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static int jdMethod_new(Connection paramConnection, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
    throws Exception
  {
    int i = 0;
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      localObject1 = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", localObject1);
    }
    Object localObject1 = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("SELECT count(*)");
      localStringBuffer.append(" from reg_transaction rt ");
      localStringBuffer.append(" where rt.directory_id=? ");
      jdMethod_for(localStringBuffer, paramPagingContext, paramHashMap);
      localPreparedStatement = jdMethod_for(paramConnection, localStringBuffer.toString());
      int j = 1;
      localPreparedStatement.setInt(j++, paramInt);
      j = jdMethod_for(localPreparedStatement, j, paramPagingContext, paramHashMap);
      localPreparedStatement.setMaxRows(1);
      localObject1 = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      int k = 0;
      int m = MapUtil.getIntValue(paramHashMap, "PAGESIZE", PAGESIZE);
      if (((ResultSet)localObject1).next())
      {
        k = ((ResultSet)localObject1).getInt(1);
        i = k / m;
        if (k % m > 0) {
          i++;
        }
      }
      paramPagingContext.getMap().put("TOTAL_TRANS", k + "");
      paramPagingContext.getMap().put("PAGE_SIZE", m + "");
      int n = i;
      return n;
    }
    catch (Exception localException)
    {
      throw new ProfileException(0, localException);
    }
    finally
    {
      DBUtil.closeResultSet((ResultSet)localObject1);
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_char(HashMap paramHashMap)
  {
    IntMap localIntMap = (IntMap)paramHashMap.get("PageSettings");
    if (localIntMap == null)
    {
      localIntMap = new IntMap();
      paramHashMap.put("PageSettings", localIntMap);
    }
    int i = MapUtil.getIntValue(paramHashMap, "CurrentPage", 1);
    if (localIntMap.get(i) == null)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.putAll(paramHashMap);
      localIntMap.put(i, localHashMap);
    }
  }
  
  private static ResultSet jdMethod_for(PagingContext paramPagingContext, int paramInt, boolean paramBoolean, HashMap paramHashMap, Connection paramConnection)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    if (!paramPagingContext.getMap().containsKey("DataClassification"))
    {
      String str = MapUtil.getStringValue(paramHashMap, "DATA_CLASSIFICATION", "P");
      localReportCriteria.getSearchCriteria().put("DataClassification", str);
    }
    int i = MapUtil.getIntValue(paramHashMap, "PAGESIZE", PAGESIZE);
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      Locale localLocale = (Locale)paramHashMap.get("Locale");
      if (localLocale == null) {
        localLocale = Locale.getDefault();
      }
      StringBuffer localStringBuffer = jdMethod_for(paramPagingContext, paramHashMap, localLocale);
      localStringBuffer.append(" where rt.directory_id=? ");
      jdMethod_for(localStringBuffer, paramPagingContext, paramHashMap);
      ArrayList localArrayList = jdMethod_for(localStringBuffer, paramPagingContext, paramBoolean, paramHashMap);
      jdMethod_for(localStringBuffer, paramPagingContext, paramBoolean, null, paramHashMap);
      localPreparedStatement = jdMethod_for(paramConnection, localStringBuffer.toString());
      int j = 1;
      localPreparedStatement.setString(j++, localLocale.toString());
      localPreparedStatement.setInt(j++, paramInt);
      j = jdMethod_for(localPreparedStatement, j, paramPagingContext, paramHashMap);
      jdMethod_for(localPreparedStatement, j, localArrayList, paramHashMap);
      if (i != -1) {
        localPreparedStatement.setMaxRows(i);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
    }
    finally {}
    return localResultSet;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, RegisterTransactions paramRegisterTransactions, ResultSet paramResultSet)
    throws Exception
  {
    String str = "RegisterAdapter.loadTransactions()";
    paramRegisterTransactions.clear();
    RegisterTransaction localRegisterTransaction = null;
    Connection localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(localConnection, "select rtc.reg_user_cat_id, rtc.amount, ruc2.name, ruc.name from reg_tran_category rtc, (select * from reg_user_category where directory_id = ?) ruc left outer join (select * from reg_user_category where directory_id = ?) ruc2 on ( ruc2.reg_user_cat_id = ruc.parent_category_id ) where rtc.reg_transaction_id=? and ruc.reg_user_cat_id=rtc.reg_user_cat_id and ruc.directory_id=rtc.directory_id ");
    int i = getDirectoryIDForConsumer(paramSecureUser);
    while (paramResultSet.next())
    {
      localRegisterTransaction = new RegisterTransaction(paramSecureUser.getLocale());
      localRegisterTransaction.setRegisterId(paramResultSet.getInt("reg_transaction_id"));
      localRegisterTransaction.set("ACCOUNT", paramResultSet.getString("ACCOUNT_ID"));
      localRegisterTransaction.setID(paramResultSet.getString("fi_transaction_id"));
      localRegisterTransaction.setReferenceNumber(unPadRefNum(paramResultSet.getString("reference_number")));
      localRegisterTransaction.setPayeeName(paramResultSet.getString("payee_name"));
      localRegisterTransaction.setMemo(paramResultSet.getString("memo"));
      localRegisterTransaction.setType(paramResultSet.getInt("type"));
      localRegisterTransaction.setStatus(paramResultSet.getInt("STATUS"));
      localRegisterTransaction.setDateIssued(ProfileUtil.getDateTime(paramResultSet.getTimestamp("date_issued")));
      localRegisterTransaction.setDate(ProfileUtil.getDateTime(paramResultSet.getTimestamp("date_cleared")));
      localRegisterTransaction.set("SERVER_TID", paramResultSet.getString("server_tran_id"));
      localRegisterTransaction.set("RECSRVRTID", paramResultSet.getString("rec_server_tran_id"));
      localRegisterTransaction.setAmount(paramResultSet.getBigDecimal("amount").toString());
      localRegisterTransaction.setTransactionCategories(new TransactionCategories());
      localPreparedStatement.setInt(1, i);
      localPreparedStatement.setInt(2, i);
      localPreparedStatement.setInt(3, localRegisterTransaction.getRegisterIdValue());
      ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, "select rtc.reg_user_cat_id, rtc.amount, ruc2.name, ruc.name from reg_tran_category rtc, (select * from reg_user_category where directory_id = ?) ruc left outer join (select * from reg_user_category where directory_id = ?) ruc2 on ( ruc2.reg_user_cat_id = ruc.parent_category_id ) where rtc.reg_transaction_id=? and ruc.reg_user_cat_id=rtc.reg_user_cat_id and ruc.directory_id=rtc.directory_id ");
      while (localResultSet.next())
      {
        TransactionCategory localTransactionCategory = new TransactionCategory(paramSecureUser.getLocale());
        localTransactionCategory.setRegisterCategoryId(localResultSet.getString("reg_user_cat_id"));
        BigDecimal localBigDecimal = localResultSet.getBigDecimal("amount");
        Currency localCurrency = new Currency(localBigDecimal, paramSecureUser.getLocale());
        localTransactionCategory.setAmount(localCurrency);
        localRegisterTransaction.getTransactionCategories().add(localTransactionCategory);
      }
      DBUtil.closeResultSet(localResultSet);
      paramRegisterTransactions.add(localRegisterTransaction);
    }
    DBUtil.closeStatement(localPreparedStatement);
    DBUtil.returnConnection(Profile.poolName, localConnection);
    logModerateActivity("Exited " + str);
  }
  
  private static void jdMethod_for(PagingContext paramPagingContext, RegisterTransactions paramRegisterTransactions)
  {
    if ((paramRegisterTransactions == null) || (paramRegisterTransactions.size() == 0)) {
      return;
    }
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null)
    {
      localHashMap = new HashMap();
      paramPagingContext.setMap(localHashMap);
    }
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    if (localReportCriteria == null) {
      return;
    }
    boolean bool = false;
    for (int i = 0; i < localReportCriteria.getSortCriteria().size(); i++)
    {
      localObject2 = (ReportSortCriterion)localReportCriteria.getSortCriteria().get(i);
      Object localObject3 = jdMethod_for((ReportSortCriterion)localObject2, paramRegisterTransactions.get(0));
      Object localObject4 = jdMethod_for((ReportSortCriterion)localObject2, paramRegisterTransactions.get(paramRegisterTransactions.size() - 1));
      if (((ReportSortCriterion)localObject2).getAsc())
      {
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
      }
      else
      {
        localHashMap.put("SORT_VALUE_MIN_" + ((ReportSortCriterion)localObject2).getName(), localObject4);
        localHashMap.put("SORT_VALUE_MAX_" + ((ReportSortCriterion)localObject2).getName(), localObject3);
      }
      bool = ((ReportSortCriterion)localObject2).getAsc();
    }
    Object localObject1 = jdMethod_for(new ReportSortCriterion(1, "TransactionIndex", true), paramRegisterTransactions.get(0));
    Object localObject2 = jdMethod_for(new ReportSortCriterion(1, "TransactionIndex", true), paramRegisterTransactions.get(paramRegisterTransactions.size() - 1));
    if (bool)
    {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", localObject1);
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", localObject2);
    }
    else
    {
      localHashMap.put("SORT_VALUE_MIN_TransactionIndex", localObject2);
      localHashMap.put("SORT_VALUE_MAX_TransactionIndex", localObject1);
    }
  }
  
  private static String M(String paramString)
    throws Exception
  {
    if (paramString.equals("Date Issued")) {
      return "rt.date_issued";
    }
    if (paramString.equals("Date Cleared")) {
      return "rt.date_cleared";
    }
    if (paramString.equals("Payee Name")) {
      return "rt.payee_name";
    }
    if (paramString.equals("Type")) {
      return "reg_transtypedesc.trans_type_desc";
    }
    if (paramString.equals("Reference Number")) {
      return "rt.reference_number";
    }
    if (paramString.equals("Amount")) {
      return "rt.amount";
    }
    if (paramString.equals("Memo")) {
      return "rt.memo";
    }
    throw new ProfileException(0);
  }
  
  private static void jdMethod_for(StringBuffer paramStringBuffer, PagingContext paramPagingContext, HashMap paramHashMap)
  {
    if (paramPagingContext == null) {
      return;
    }
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null) {
      return;
    }
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    if ((localReportCriteria == null) || (localReportCriteria.getSearchCriteria() == null)) {
      return;
    }
    Properties localProperties = localReportCriteria.getSearchCriteria();
    jdMethod_for(paramStringBuffer, localProperties, paramHashMap);
  }
  
  private static void jdMethod_for(StringBuffer paramStringBuffer, Properties paramProperties, HashMap paramHashMap)
  {
    if (paramProperties == null) {
      return;
    }
    String str = paramProperties.getProperty("AccountID");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and rt.account_id= ? ");
    }
    str = paramProperties.getProperty("StartDate");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and rt.date_issued>= ? ");
    }
    str = paramProperties.getProperty("EndDate");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and rt.date_issued<= ? ");
    }
    str = paramProperties.getProperty("TransactionID");
    if ((str != null) && (str.length() != 0)) {
      paramStringBuffer.append(" and rt.reg_transaction_id= ? ");
    }
    str = paramProperties.getProperty("ReferenceNumber");
    if ((str != null) && (str.length() != 0)) {
      paramStringBuffer.append(" and rt.reference_number= ? ");
    }
    str = paramProperties.getProperty("PayeeName");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and rt.payee_name= ? ");
    }
    str = paramProperties.getProperty("Type");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and rt.type= ? ");
    }
    str = paramProperties.getProperty("Memo");
    if ((str != null) && (str.length() > 0)) {
      paramStringBuffer.append(" and rt.memo like ? ");
    }
    str = paramProperties.getProperty("Status");
    if ((str != null) && (str.length() > 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(str, ",");
      if (localStringTokenizer.countTokens() == 1)
      {
        paramStringBuffer.append(" and rt.status= ? ");
      }
      else if (localStringTokenizer.countTokens() > 1)
      {
        paramStringBuffer.append(" and ( ");
        for (int i = 0; i < localStringTokenizer.countTokens(); i++) {
          if (i == 0) {
            paramStringBuffer.append(" rt.status= ? ");
          } else {
            paramStringBuffer.append(" or rt.status= ? ");
          }
        }
        paramStringBuffer.append(" ) ");
      }
    }
  }
  
  private static void jdMethod_for(StringBuffer paramStringBuffer, PagingContext paramPagingContext, boolean paramBoolean, Boolean paramBoolean1, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    paramStringBuffer.append(" ORDER BY ");
    boolean bool = paramBoolean ? true : paramBoolean1 != null ? paramBoolean1.booleanValue() : false;
    for (int i = 0; i < localReportSortCriteria.size(); i++)
    {
      if (i != 0) {
        paramStringBuffer.append(", ");
      }
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(i);
      paramStringBuffer.append(M(localReportSortCriterion.getName()));
      bool = paramBoolean1 == null ? false : !localReportSortCriterion.getAsc() ? true : paramBoolean ? localReportSortCriterion.getAsc() : paramBoolean1.booleanValue();
      paramStringBuffer.append(bool ? " ASC" : " DESC");
    }
    if (localReportSortCriteria.size() != 0) {
      paramStringBuffer.append(",");
    }
    paramStringBuffer.append(" rt.").append("reg_transaction_id").append(bool ? " ASC" : " DESC");
  }
  
  private static PreparedStatement jdMethod_for(Connection paramConnection, String paramString)
    throws ProfileException
  {
    try
    {
      PreparedStatement localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramString);
      return localPreparedStatement;
    }
    catch (Exception localException)
    {
      throw new ProfileException(0, localException);
    }
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    if (paramPagingContext == null) {
      return paramInt;
    }
    HashMap localHashMap = paramPagingContext.getMap();
    if (localHashMap == null) {
      return paramInt;
    }
    ReportCriteria localReportCriteria = (ReportCriteria)localHashMap.get("ReportCriteria");
    if ((localReportCriteria == null) || (localReportCriteria.getSearchCriteria() == null)) {
      return paramInt;
    }
    Properties localProperties = localReportCriteria.getSearchCriteria();
    return jdMethod_for(paramPreparedStatement, paramInt, localProperties, paramHashMap);
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, Properties paramProperties, HashMap paramHashMap)
    throws Exception
  {
    if (paramProperties == null) {
      return paramInt;
    }
    String str1 = paramProperties.getProperty("AccountID");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("StartDate");
    if ((str1 != null) && (str1.length() > 0)) {
      jdMethod_for(paramPreparedStatement, paramInt++, str1, ahX);
    }
    str1 = paramProperties.getProperty("EndDate");
    if ((str1 != null) && (str1.length() > 0)) {
      jdMethod_for(paramPreparedStatement, paramInt++, str1, ahX, true);
    }
    str1 = paramProperties.getProperty("TransactionID");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("ReferenceNumber");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, padRefNum(str1));
    }
    str1 = paramProperties.getProperty("PayeeName");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, str1);
    }
    str1 = paramProperties.getProperty("Type");
    try
    {
      int i = Integer.parseInt(str1);
      paramPreparedStatement.setInt(paramInt++, i);
    }
    catch (NumberFormatException localNumberFormatException1) {}
    str1 = paramProperties.getProperty("Memo");
    if ((str1 != null) && (str1.length() > 0)) {
      paramPreparedStatement.setString(paramInt++, "%" + str1 + "%");
    }
    str1 = paramProperties.getProperty("Status");
    if ((str1 != null) && (str1.length() > 0))
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
      while (localStringTokenizer.hasMoreTokens())
      {
        String str2 = localStringTokenizer.nextToken();
        try
        {
          int j = Integer.parseInt(str2);
          paramPreparedStatement.setInt(paramInt++, j);
        }
        catch (NumberFormatException localNumberFormatException2) {}
      }
    }
    return paramInt;
  }
  
  private static ArrayList jdMethod_for(StringBuffer paramStringBuffer, PagingContext paramPagingContext, boolean paramBoolean, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramPagingContext.getMap().get("ReportCriteria");
    ReportSortCriteria localReportSortCriteria = localReportCriteria.getSortCriteria();
    String str1 = Profile.getDBType();
    ArrayList localArrayList1 = new ArrayList();
    int i = MapUtil.getIntValue(paramPagingContext.getMap(), "CurrentPage", -1);
    if (i == 1) {
      return localArrayList1;
    }
    boolean bool = true;
    ArrayList localArrayList2 = new ArrayList();
    StringBuffer localStringBuffer1 = null;
    ArrayList localArrayList3 = new ArrayList();
    String str2 = null;
    if (!localReportSortCriteria.isEmpty()) {
      bool = ((ReportSortCriterion)localReportSortCriteria.get(localReportSortCriteria.size() - 1)).getAsc();
    } else {
      bool = true;
    }
    if (paramBoolean)
    {
      if (bool) {
        str2 = "SORT_VALUE_MAX_TransactionIndex";
      } else {
        str2 = "SORT_VALUE_MIN_TransactionIndex";
      }
    }
    else if (bool) {
      str2 = "SORT_VALUE_MIN_TransactionIndex";
    } else {
      str2 = "SORT_VALUE_MAX_TransactionIndex";
    }
    if (paramPagingContext.getMap().containsKey(str2)) {
      localReportSortCriteria.create(localReportSortCriteria.size() + 1, "TransactionIndex", bool);
    }
    for (int j = 0; j < localReportSortCriteria.size(); j++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(j);
      int k = (str1 != null) && (str1.indexOf("ASE") != -1) ? 1 : 0;
      int m = ((k != 0) && (localReportSortCriterion.getAsc())) || ((k == 0) && (!localReportSortCriterion.getAsc())) ? 1 : 0;
      String str3 = null;
      String str4 = null;
      bool = localReportSortCriterion.getAsc();
      if (paramBoolean)
      {
        if (bool)
        {
          str3 = "SORT_VALUE_MAX_" + localReportSortCriterion.getName();
          str4 = " > ";
        }
        else
        {
          str3 = "SORT_VALUE_MIN_" + localReportSortCriterion.getName();
          str4 = " < ";
        }
      }
      else if (bool)
      {
        str3 = "SORT_VALUE_MIN_" + localReportSortCriterion.getName();
        str4 = " < ";
      }
      else
      {
        str3 = "SORT_VALUE_MAX_" + localReportSortCriterion.getName();
        str4 = " > ";
      }
      if (paramPagingContext.getMap().containsKey(str3))
      {
        String str5;
        if ("TransactionIndex".equals(localReportSortCriterion.getName())) {
          str5 = "rt.reg_transaction_id";
        } else {
          str5 = M(localReportSortCriterion.getName());
        }
        Object localObject = paramPagingContext.getMap().get(str3);
        StringBuffer localStringBuffer2;
        if (localObject != null)
        {
          if (localStringBuffer1 == null) {
            localStringBuffer1 = new StringBuffer();
          }
          localStringBuffer2 = new StringBuffer(localStringBuffer1.toString());
          if (localStringBuffer2.length() != 0) {
            localStringBuffer2.append(" AND ");
          }
          if (m == 0)
          {
            localStringBuffer2.append("( ").append(str5).append(str4).append("? ");
            localStringBuffer2.append("OR ").append(str5).append(" is NULL ) ");
          }
          else
          {
            localStringBuffer2.append(str5).append(str4).append("? ");
          }
          localArrayList1.addAll(localArrayList3);
          if (localReportSortCriterion.getName().equals("Reference Number")) {
            localArrayList1.add(padRefNum((String)localObject));
          } else {
            localArrayList1.add(localObject);
          }
          if (localStringBuffer1.length() != 0) {
            localStringBuffer1.append(" AND ");
          }
          if (m == 0)
          {
            localStringBuffer1.append("( ").append(str5).append(" = ? ");
            localStringBuffer1.append("OR ").append(str5).append(" is NULL ) ");
          }
          else
          {
            localStringBuffer1.append(str5).append(" = ? ");
          }
          if (localReportSortCriterion.getName().equals("Reference Number")) {
            localArrayList3.add(padRefNum((String)localObject));
          } else {
            localArrayList3.add(localObject);
          }
          localArrayList2.add(localStringBuffer2);
        }
        else
        {
          if (localStringBuffer1 == null) {
            localStringBuffer1 = new StringBuffer();
          } else if (localStringBuffer1.length() != 0) {
            localStringBuffer1.append(" AND ");
          }
          if (m == 0)
          {
            localStringBuffer1.append(str5).append(" is NULL ");
          }
          else
          {
            localStringBuffer2 = new StringBuffer(localStringBuffer1.toString());
            localStringBuffer1.append(str5).append(" is NULL ");
            localStringBuffer2.append(str5).append(" is NOT NULL");
            localArrayList2.add(localStringBuffer2);
            localArrayList1.addAll(localArrayList3);
          }
        }
      }
    }
    if (paramPagingContext.getMap().containsKey(str2))
    {
      paramStringBuffer.append(" AND (");
      paramStringBuffer.append((StringBuffer)localArrayList2.get(0));
      for (j = 1; j < localArrayList2.size(); j++) {
        paramStringBuffer.append(" OR (").append((StringBuffer)localArrayList2.get(j)).append(" ) ");
      }
      paramStringBuffer.append(" ) ");
      localReportSortCriteria.remove(localReportSortCriteria.size() - 1);
    }
    return localArrayList1;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws Exception
  {
    for (int i = 0; i < paramArrayList.size(); i++) {
      jdMethod_for(paramPreparedStatement, paramInt++, paramArrayList.get(i));
    }
    return paramInt;
  }
  
  private static Object jdMethod_for(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    RegisterTransaction localRegisterTransaction = (RegisterTransaction)paramObject;
    if (paramReportSortCriterion.getName().equals("Date Issued")) {
      return localRegisterTransaction.getDateIssuedValue();
    }
    if (paramReportSortCriterion.getName().equals("Date Cleared")) {
      return localRegisterTransaction.getDateValue();
    }
    if (paramReportSortCriterion.getName().equals("Payee Name")) {
      return localRegisterTransaction.getPayeeName();
    }
    if (paramReportSortCriterion.getName().equals("Type")) {
      return localRegisterTransaction.getType();
    }
    if (paramReportSortCriterion.getName().equals("Reference Number")) {
      return unPadRefNum(localRegisterTransaction.getReferenceNumber());
    }
    if (paramReportSortCriterion.getName().equals("Amount")) {
      return localRegisterTransaction.getAmountValue();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Integer(localRegisterTransaction.getRegisterIdValue());
    }
    if (paramReportSortCriterion.getName().equals("Memo")) {
      return localRegisterTransaction.getMemo();
    }
    return null;
  }
  
  private static void jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, long paramLong)
    throws Exception
  {
    if (paramLong > 0L) {
      paramPreparedStatement.setTimestamp(paramInt, new Timestamp(paramLong));
    } else {
      paramPreparedStatement.setTimestamp(paramInt, null);
    }
  }
  
  private static void jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, Calendar paramCalendar)
    throws Exception
  {
    if (paramCalendar != null) {
      paramPreparedStatement.setTimestamp(paramInt, new Timestamp(paramCalendar.getTime().getTime()));
    } else {
      paramPreparedStatement.setTimestamp(paramInt, null);
    }
  }
  
  private static void jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2)
    throws Exception
  {
    jdMethod_for(paramPreparedStatement, paramInt, paramString1, paramString2, false);
  }
  
  private static void jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, String paramString1, String paramString2, boolean paramBoolean)
    throws Exception
  {
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter(paramString2);
      java.util.Date localDate = localDateFormat.parse(paramString1);
      if (paramBoolean) {
        localDate.setTime(jdMethod_byte(localDate));
      }
      jdMethod_for(paramPreparedStatement, paramInt, new DateTime(localDate, Locale.getDefault(), paramString2));
    }
  }
  
  private static void jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, Object paramObject)
    throws Exception
  {
    if ((paramObject instanceof Integer)) {
      paramPreparedStatement.setInt(paramInt, ((Integer)paramObject).intValue());
    } else if ((paramObject instanceof Long)) {
      paramPreparedStatement.setLong(paramInt, ((Long)paramObject).longValue());
    } else if ((paramObject instanceof String)) {
      paramPreparedStatement.setString(paramInt, (String)paramObject);
    } else if ((paramObject instanceof DateTime)) {
      jdMethod_for(paramPreparedStatement, paramInt, (DateTime)paramObject);
    } else if ((paramObject instanceof Calendar)) {
      jdMethod_for(paramPreparedStatement, paramInt, (Calendar)paramObject);
    } else if ((paramObject instanceof Currency)) {
      jdMethod_for(paramPreparedStatement, paramInt, (Currency)paramObject);
    }
  }
  
  private static void jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, Currency paramCurrency)
    throws Exception
  {
    if (paramCurrency != null)
    {
      BigDecimal localBigDecimal = paramCurrency.getAmountValue();
      try
      {
        localBigDecimal = localBigDecimal.setScale(3, 1);
      }
      catch (ArithmeticException localArithmeticException)
      {
        throw new ProfileException(0, localArithmeticException);
      }
      paramPreparedStatement.setBigDecimal(paramInt, localBigDecimal);
    }
    else
    {
      paramPreparedStatement.setNull(paramInt, 2);
    }
  }
  
  private static Object jdMethod_for(ResultSet paramResultSet, int paramInt, Locale paramLocale)
    throws Exception
  {
    ResultSetMetaData localResultSetMetaData = paramResultSet.getMetaData();
    switch (localResultSetMetaData.getColumnType(paramInt))
    {
    case -6: 
    case -5: 
    case 2: 
    case 5: 
      try
      {
        int i = paramResultSet.findColumn("AMOUNT");
        if (i == paramInt) {
          return jdMethod_for(paramResultSet.getBigDecimal(paramInt), paramLocale);
        }
        return new Integer(paramResultSet.getInt(paramInt));
      }
      catch (SQLException localSQLException)
      {
        return new Integer(paramResultSet.getInt(paramInt));
      }
    case 4: 
      return new Integer(paramResultSet.getInt(paramInt));
    case 93: 
      return jdMethod_for(paramResultSet.getTimestamp(paramInt), paramLocale);
    case 3: 
      return jdMethod_for(paramResultSet.getBigDecimal(paramInt), paramLocale);
    case 12: 
      return paramResultSet.getString(paramInt);
    }
    return null;
  }
  
  private static Currency jdMethod_for(BigDecimal paramBigDecimal, Locale paramLocale)
  {
    if (paramBigDecimal == null) {
      return null;
    }
    return new Currency(paramBigDecimal, paramLocale);
  }
  
  private static DateTime jdMethod_for(Timestamp paramTimestamp, Locale paramLocale)
  {
    if (paramTimestamp == null) {
      return null;
    }
    return new DateTime(paramTimestamp, paramLocale);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, boolean paramBoolean)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.reportExportTransactions";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      String str2 = localProperties.getProperty("StartDate");
      String str3 = localProperties.getProperty("EndDate");
      DateFormat localDateFormat = DateFormatUtil.getFormatter(ahX);
      Timestamp localTimestamp1 = null;
      Timestamp localTimestamp2 = null;
      try
      {
        if ((str2 != null) && (str2.length() != 0)) {
          localTimestamp1 = new Timestamp(localDateFormat.parse(str2).getTime());
        }
      }
      catch (ParseException localParseException1) {}
      try
      {
        if ((str3 != null) && (str3.length() != 0)) {
          localTimestamp2 = new Timestamp(jdMethod_byte(localDateFormat.parse(str3)));
        }
      }
      catch (ParseException localParseException2) {}
      String str4 = localProperties.getProperty("Account");
      String str5 = localProperties.getProperty("RefNumber");
      String str6 = localProperties.getProperty("Status", "All");
      String str7 = localProperties.getProperty("MaximumAmount");
      String str8 = localProperties.getProperty("MinimumAmount");
      ReportResult localReportResult = new ReportResult(paramReportResult.getLocale());
      paramReportResult.addSubReport(localReportResult);
      if (!paramBoolean)
      {
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ruc.reg_user_cat_id, ruc.directory_id, ruc.name, ruc2.name, ruc.description, ruc.type, ruc.tax_related, ruc.parent_category_id from reg_user_category ruc left outer join reg_user_category ruc2 on ( ruc.parent_category_id = ruc2.reg_user_cat_id ) where ruc.directory_id = ? order by ruc2.name, ruc.name ");
        localPreparedStatement.setInt(1, i);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ruc.reg_user_cat_id, ruc.directory_id, ruc.name, ruc2.name, ruc.description, ruc.type, ruc.tax_related, ruc.parent_category_id from reg_user_category ruc left outer join reg_user_category ruc2 on ( ruc.parent_category_id = ruc2.reg_user_cat_id ) where ruc.directory_id = ? order by ruc2.name, ruc.name ".toString());
        exportCategoriesReport(localReportResult, localResultSet);
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localResultSet = null;
        localPreparedStatement = null;
      }
      StringBuffer localStringBuffer = new StringBuffer("select rt.reg_transaction_id, rt.directory_id, rt.account_id, rt.fi_transaction_id, rt.reference_number, rt.payee_name, rt.type, rt.status, rt.memo, rt.date_issued, rt.date_cleared, rt.rec_server_tran_id, rt.server_tran_id, rtc.reg_user_cat_id, rtc.amount, ruc2.name, ruc.name from reg_transaction rt, reg_tran_category rtc, reg_user_category ruc left outer join reg_user_category ruc2 on ( ruc2.reg_user_cat_id = ruc.parent_category_id and ruc2.directory_id=?) where rt.reg_transaction_id = rtc.reg_transaction_id and ruc.reg_user_cat_id = rtc.reg_user_cat_id and rt.directory_id=? and rt.account_id=? and rtc.directory_id=ruc.directory_id");
      if (localTimestamp1 != null) {
        localStringBuffer.append(" and rt.date_issued>= ? ");
      }
      if (localTimestamp2 != null) {
        localStringBuffer.append(" and rt.date_issued<= ? ");
      }
      if ((str5 != null) && (str5.length() > 0)) {
        localStringBuffer.append(" and rt.reference_number=? ");
      }
      if (str6.equals("Reconciled")) {
        localStringBuffer.append(" and (rt.status=2 or rt.status=3 ) ");
      } else if (str6.equals("Unreconciled")) {
        localStringBuffer.append(" and rt.status=0 ");
      }
      if (str7 != null) {
        try
        {
          BigDecimal localBigDecimal1 = new BigDecimal(str7);
          localStringBuffer.append(" and rtc.amount < " + localBigDecimal1.toString() + " ");
        }
        catch (NumberFormatException localNumberFormatException1) {}
      }
      if (str8 != null) {
        try
        {
          BigDecimal localBigDecimal2 = new BigDecimal(str8);
          localStringBuffer.append(" and rtc.amount > " + localBigDecimal2.toString() + " ");
        }
        catch (NumberFormatException localNumberFormatException2) {}
      }
      localStringBuffer.append("order by rt.date_issued, rt.reg_transaction_id, ruc2.name, ruc.name ");
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int j = 1;
      localPreparedStatement.setInt(j++, i);
      localPreparedStatement.setInt(j++, i);
      localPreparedStatement.setString(j++, str4);
      if (localTimestamp1 != null) {
        localPreparedStatement.setTimestamp(j++, localTimestamp1);
      }
      if (localTimestamp2 != null) {
        localPreparedStatement.setTimestamp(j++, localTimestamp2);
      }
      if ((str5 != null) && (str5.length() != 0)) {
        localPreparedStatement.setString(j++, str5);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (paramBoolean) {
        exportRegisterTransactionsForDisplay(paramReportResult, localResultSet);
      } else {
        exportRegisterTransactions(paramReportResult, localResultSet);
      }
    }
    catch (Exception localException)
    {
      if (paramReportResult != null) {
        paramReportResult.setError(localException);
      }
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      if (paramReportResult != null) {
        try
        {
          paramReportResult.fini();
        }
        catch (RptException localRptException) {}
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_try(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, Accounts paramAccounts)
    throws ProfileException
  {
    jdMethod_for(paramSecureUser, paramReportResult, paramReportCriteria, false, paramAccounts);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, Accounts paramAccounts)
    throws ProfileException
  {
    jdMethod_for(paramSecureUser, paramReportResult, paramReportCriteria, true, paramAccounts);
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, Accounts paramAccounts)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.reportCashFlow";
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str2 = jdMethod_for(paramReportCriteria, true);
    String str3 = jdMethod_for(paramReportCriteria, false);
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement1 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str2, true);
      localPreparedStatement2 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str3, false);
      ArrayList localArrayList = jdMethod_try(paramReportCriteria);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        Account localAccount = null;
        BigDecimal localBigDecimal = new BigDecimal("0.0");
        if (paramAccounts != null) {
          localAccount = paramAccounts.getByID(str4);
        }
        if (localArrayList.size() > 1) {
          jdMethod_for(paramReportResult, jdMethod_for(localAccount, paramReportCriteria), true);
        }
        localPreparedStatement1.setString(4, str4);
        localPreparedStatement2.setString(4, str4);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, str2.toString());
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, str3.toString());
        jdMethod_for(paramReportResult, ReportConsts.getText(2300, paramReportResult.getLocale()), false);
        ReportResult localReportResult1 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult1);
        localBigDecimal = localBigDecimal.add(jdMethod_new(localReportResult1, localResultSet1, true, localAccount));
        jdMethod_for(paramReportResult, ReportConsts.getText(2301, paramReportResult.getLocale()), false);
        ReportResult localReportResult2 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult2);
        localBigDecimal = localBigDecimal.add(jdMethod_new(localReportResult2, localResultSet2, false, localAccount));
        jdMethod_for(ReportConsts.getText(10304, paramReportResult.getLocale()), localBigDecimal, paramReportResult.getLocale(), localAccount, paramReportResult);
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeResultSet(localResultSet2);
      }
    }
    catch (Exception localException)
    {
      if (paramReportResult != null) {
        paramReportResult.setError(localException);
      }
      Profile.handleError(localException, str1);
    }
    finally
    {
      if (paramReportResult != null) {
        try
        {
          paramReportResult.fini();
        }
        catch (RptException localRptException) {}
      }
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_new(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, Accounts paramAccounts)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.reportTransactionTypeTotals";
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str2 = jdMethod_for(paramReportCriteria, true);
    String str3 = jdMethod_for(paramReportCriteria, false);
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement1 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str2, true);
      localPreparedStatement2 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str3, false);
      ArrayList localArrayList = jdMethod_try(paramReportCriteria);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        Account localAccount = null;
        if (paramAccounts != null) {
          localAccount = paramAccounts.getByID(str4);
        }
        if (localArrayList.size() > 1) {
          jdMethod_for(paramReportResult, jdMethod_for(localAccount, paramReportCriteria), true);
        }
        localPreparedStatement1.setString(4, str4);
        localPreparedStatement2.setString(4, str4);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, str2.toString());
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, str3.toString());
        jdMethod_for(paramReportResult, ReportConsts.getText(2300, paramReportResult.getLocale()), false);
        ReportResult localReportResult1 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult1);
        jdMethod_for(localReportResult1, localResultSet1, true, localAccount);
        jdMethod_for(paramReportResult, ReportConsts.getText(2301, paramReportResult.getLocale()), false);
        ReportResult localReportResult2 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult2);
        jdMethod_for(localReportResult2, localResultSet2, false, localAccount);
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeResultSet(localResultSet2);
      }
    }
    catch (Exception localException)
    {
      if (paramReportResult != null) {
        paramReportResult.setError(localException);
      }
      Profile.handleError(localException, str1);
    }
    finally
    {
      if (paramReportResult != null) {
        try
        {
          paramReportResult.fini();
        }
        catch (RptException localRptException) {}
      }
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_byte(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, Accounts paramAccounts)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.reportPayeeTotals";
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str2 = jdMethod_for(paramReportCriteria, true);
    String str3 = jdMethod_for(paramReportCriteria, false);
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement1 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str2, true);
      localPreparedStatement2 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str3, false);
      ArrayList localArrayList = jdMethod_try(paramReportCriteria);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        Account localAccount = null;
        if (paramAccounts != null) {
          localAccount = paramAccounts.getByID(str4);
        }
        if (localArrayList.size() > 1) {
          jdMethod_for(paramReportResult, jdMethod_for(localAccount, paramReportCriteria), true);
        }
        localPreparedStatement1.setString(4, str4);
        localPreparedStatement2.setString(4, str4);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, str2.toString());
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, str3.toString());
        jdMethod_for(paramReportResult, ReportConsts.getText(2300, paramReportResult.getLocale()), false);
        ReportResult localReportResult1 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult1);
        jdMethod_try(localReportResult1, localResultSet1, true, localAccount);
        jdMethod_for(paramReportResult, ReportConsts.getText(2301, paramReportResult.getLocale()), false);
        ReportResult localReportResult2 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult2);
        jdMethod_try(localReportResult2, localResultSet2, false, localAccount);
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeResultSet(localResultSet2);
      }
    }
    catch (Exception localException)
    {
      if (paramReportResult != null) {
        paramReportResult.setError(localException);
      }
      Profile.handleError(localException, str1);
    }
    finally
    {
      if (paramReportResult != null) {
        try
        {
          paramReportResult.fini();
        }
        catch (RptException localRptException) {}
      }
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_int(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, Accounts paramAccounts, HashMap paramHashMap)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.reportReconciliationStatus";
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str2 = jdMethod_for(paramReportCriteria, true);
    String str3 = jdMethod_for(paramReportCriteria, false);
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement1 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str2, true);
      localPreparedStatement2 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str3, false);
      ArrayList localArrayList = jdMethod_try(paramReportCriteria);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        Account localAccount = null;
        if (paramAccounts != null) {
          localAccount = paramAccounts.getByID(str4);
        }
        if (localArrayList.size() > 1) {
          jdMethod_for(paramReportResult, jdMethod_for(localAccount, paramReportCriteria), true);
        }
        localPreparedStatement1.setString(4, str4);
        localPreparedStatement2.setString(4, str4);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, str2.toString());
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, str3.toString());
        ReportResult localReportResult1 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult1);
        jdMethod_for(localReportResult1);
        jdMethod_for(localReportResult1, ReportConsts.getText(10308, paramReportResult.getLocale()), localAccount.getCurrentBalance(), paramReportResult.getLocale());
        jdMethod_for(localReportResult1, ReportConsts.getText(10309, paramReportResult.getLocale()), localAccount.getAvailableBalance(), paramReportResult.getLocale());
        Balance localBalance = jdMethod_for(paramSecureUser, localAccount, paramReportCriteria, paramHashMap);
        jdMethod_for(localReportResult1, ReportConsts.getText(10313, paramReportResult.getLocale()), localBalance, paramReportResult.getLocale());
        ReportResult localReportResult2 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult2);
        jdMethod_for(localReportResult2, localAccount.getCurrentBalance());
        ReportResult localReportResult3 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult3);
        jdMethod_for(localReportResult3, ReportConsts.getText(10306, paramReportResult.getLocale()));
        jdMethod_for(paramReportResult, ReportConsts.getText(2300, paramReportResult.getLocale()), false);
        ReportResult localReportResult4 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult4);
        jdMethod_int(localReportResult4, localResultSet1, true, localAccount);
        ReportResult localReportResult5 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult5);
        jdMethod_for(localReportResult5, ReportConsts.getText(10307, paramReportResult.getLocale()));
        jdMethod_for(paramReportResult, ReportConsts.getText(2301, paramReportResult.getLocale()), false);
        ReportResult localReportResult6 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult6);
        jdMethod_int(localReportResult6, localResultSet2, false, localAccount);
        if ((localBalance != null) && (localBalance.getAmountValue() != null)) {
          jdMethod_for(ReportConsts.getText(10313, paramReportResult.getLocale()), localBalance.getAmountValue().getAmountValue(), paramReportResult.getLocale(), localAccount, paramReportResult);
        }
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeResultSet(localResultSet2);
      }
    }
    catch (Exception localException)
    {
      if (paramReportResult != null) {
        paramReportResult.setError(localException);
      }
      Profile.handleError(localException, str1);
    }
    finally
    {
      if (paramReportResult != null) {
        try
        {
          paramReportResult.fini();
        }
        catch (RptException localRptException) {}
      }
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, String paramString)
    throws RptException
  {
    int i = 1;
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    localReportColumn = new ReportColumn(localLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(100);
    localReportResult.addColumn(localReportColumn);
    ReportRow localReportRow = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = null;
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(paramString);
    localReportResult.addRow(localReportRow);
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, Balance paramBalance)
    throws RptException
  {
    int i = 3;
    Locale localLocale = paramReportResult.getLocale();
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(3);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    localReportColumn = new ReportColumn(localLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(60);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(30);
    paramReportResult.addColumn(localReportColumn);
    if ((paramBalance != null) && (paramBalance.getAmountValue() != null))
    {
      localReportColumn = new ReportColumn(localLocale);
      localReportColumn.setDataType("com.ffusion.beans.Currency");
      localReportColumn.setWidthAsPercent(10);
      paramReportResult.addColumn(localReportColumn);
    }
    else
    {
      localReportColumn = new ReportColumn(localLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(10);
      paramReportResult.addColumn(localReportColumn);
    }
    ReportRow localReportRow = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = null;
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10305, paramReportResult.getLocale()));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10308, paramReportResult.getLocale()));
    localReportDataItem = localReportDataItems.add();
    if ((paramBalance != null) && (paramBalance.getAmountValue() != null)) {
      localReportDataItem.setData(paramBalance.getAmountValue());
    } else {
      localReportDataItem.setData(ReportConsts.getText(10314, paramReportResult.getLocale()));
    }
    paramReportResult.addRow(localReportRow);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ReportResult paramReportResult, ReportCriteria paramReportCriteria, boolean paramBoolean, Accounts paramAccounts)
    throws ProfileException
  {
    String str1 = "RegisterAdapter.reportCategoryTotalsAndTaxStatus";
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    String str2 = jdMethod_for(paramReportCriteria, true);
    String str3 = jdMethod_for(paramReportCriteria, false);
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement1 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str2, true);
      localPreparedStatement2 = jdMethod_for(paramSecureUser, paramReportCriteria, localConnection, str3, false);
      ArrayList localArrayList = jdMethod_try(paramReportCriteria);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str4 = (String)localIterator.next();
        Account localAccount = null;
        if (paramAccounts != null) {
          localAccount = paramAccounts.getByID(str4);
        }
        if (localArrayList.size() > 1) {
          jdMethod_for(paramReportResult, jdMethod_for(localAccount, paramReportCriteria), true);
        }
        localPreparedStatement1.setString(4, str4);
        localPreparedStatement2.setString(4, str4);
        localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, str2.toString());
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, str3.toString());
        jdMethod_for(paramReportResult, ReportConsts.getText(2300, paramReportResult.getLocale()), false);
        ReportResult localReportResult1 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult1);
        jdMethod_for(localReportResult1, localResultSet1, paramBoolean, true, localAccount);
        jdMethod_for(paramReportResult, ReportConsts.getText(2301, paramReportResult.getLocale()), false);
        ReportResult localReportResult2 = new ReportResult(paramReportResult.getLocale());
        paramReportResult.addSubReport(localReportResult2);
        jdMethod_for(localReportResult2, localResultSet2, paramBoolean, false, localAccount);
        DBUtil.closeResultSet(localResultSet1);
        DBUtil.closeResultSet(localResultSet2);
      }
    }
    catch (Exception localException)
    {
      if (paramReportResult != null) {
        paramReportResult.setError(localException);
      }
      Profile.handleError(localException, str1);
    }
    finally
    {
      if (paramReportResult != null) {
        try
        {
          paramReportResult.fini();
        }
        catch (RptException localRptException) {}
      }
      DBUtil.closeStatement(localPreparedStatement1);
      DBUtil.closeStatement(localPreparedStatement2);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, ResultSet paramResultSet, boolean paramBoolean1, boolean paramBoolean2, Account paramAccount)
    throws SQLException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult1 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult1);
    Object localObject1 = "";
    Object localObject2 = "";
    BigDecimal localBigDecimal1 = new BigDecimal("0.0");
    BigDecimal localBigDecimal2 = new BigDecimal("0.0");
    String str1 = ResourceUtil.getString(KEY_REGISTER_DISPLAY_TEXT_CAT_SUBCAT, "com.ffusion.beans.register.resources", localLocale);
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = "{0}:{1}";
    }
    int i = 1;
    ReportResult localReportResult2 = null;
    int j = 0;
    String str2 = "category_amount";
    while (paramResultSet.next())
    {
      if (j == 0)
      {
        j = 1;
        localReportResult2 = jdMethod_for(true, localLocale, paramBoolean2, paramBoolean1, localReportResult1);
      }
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = null;
      String str3 = paramResultSet.getString(15);
      String str4 = paramResultSet.getString(16);
      if (str3 == null) {
        str3 = "";
      }
      localObject2 = "";
      if (str4 == null) {
        localObject2 = str3;
      } else if (str3.length() == 0) {
        localObject2 = str4;
      } else {
        localObject2 = MessageFormat.format(str1, new Object[] { str3, str4 });
      }
      if (((String)localObject2).equals(localObject1))
      {
        localObject2 = null;
      }
      else
      {
        if ((localObject1 != null) && (!((String)localObject1).trim().equals("")))
        {
          jdMethod_int((String)localObject1, localBigDecimal1, localLocale, paramAccount, localReportResult1);
          localReportResult2 = jdMethod_for(false, localLocale, paramBoolean2, paramBoolean1, localReportResult1);
        }
        localBigDecimal1 = new BigDecimal("0.0");
        localObject1 = localObject2;
      }
      localBigDecimal1 = localBigDecimal1.add(paramResultSet.getBigDecimal("category_amount"));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localObject2);
      DateTime localDateTime1 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(10));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime1);
      DateTime localDateTime2 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(11));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime2);
      String str5 = paramResultSet.getString(6);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str5);
      String str6 = paramResultSet.getString(9);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str6);
      String str7 = paramResultSet.getString(18);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str7);
      String str8 = paramResultSet.getString(5);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str8);
      if (!paramBoolean1)
      {
        localObject3 = paramResultSet.getString(17);
        localReportDataItem = localReportDataItems.add();
        if (((String)localObject3).equals(ah0)) {
          localReportDataItem.setData(ReportConsts.getText(2308, localLocale));
        } else {
          localReportDataItem.setData(ReportConsts.getText(2309, localLocale));
        }
      }
      Object localObject3 = new Currency(paramResultSet.getBigDecimal("category_amount"), localLocale);
      localBigDecimal2 = localBigDecimal2.add(paramResultSet.getBigDecimal("category_amount"));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localObject3);
      if (i % 2 == 0) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      i++;
      localReportResult2.addRow(localReportRow);
    }
    if (j == 0) {
      jdMethod_int(localReportResult1);
    }
    if (!localBigDecimal2.equals(localBigDecimal1)) {
      jdMethod_int((String)localObject1, localBigDecimal1, localLocale, paramAccount, localReportResult1);
    }
    jdMethod_for(paramBoolean2, localBigDecimal2, localLocale, paramAccount, localReportResult1);
  }
  
  private static ReportResult jdMethod_for(boolean paramBoolean1, Locale paramLocale, boolean paramBoolean2, boolean paramBoolean3, ReportResult paramReportResult)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    if (paramBoolean3) {
      localReportDataDimensions.setNumColumns(8);
    } else {
      localReportDataDimensions.setNumColumns(9);
    }
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2251, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2256, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      if (paramBoolean2) {
        localArrayList.add(ReportConsts.getColumnName(2258, paramLocale));
      } else {
        localArrayList.add(ReportConsts.getColumnName(2257, paramLocale));
      }
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2265, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    if (paramBoolean3) {
      localReportColumn.setWidthAsPercent(30);
    } else {
      localReportColumn.setWidthAsPercent(20);
    }
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2260, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2259, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    if (!paramBoolean3)
    {
      localReportColumn = new ReportColumn(paramLocale);
      if (paramBoolean1)
      {
        localArrayList = new ArrayList();
        localArrayList.add(ReportConsts.getColumnName(2262, paramLocale));
        localReportColumn.setLabels(localArrayList);
      }
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(10);
      localReportResult.addColumn(localReportColumn);
    }
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    return localReportResult;
  }
  
  private static void jdMethod_for(ReportResult paramReportResult)
    throws RptException
  {
    int i = 3;
    Locale localLocale = paramReportResult.getLocale();
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(3);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    localReportColumn = new ReportColumn(localLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(30);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setWidthAsPercent(30);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(localLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(40);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, String paramString, Balance paramBalance, Locale paramLocale)
    throws RptException
  {
    ReportRow localReportRow = new ReportRow(paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = null;
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(paramString);
    localReportDataItem = localReportDataItems.add();
    if ((paramBalance != null) && (paramBalance.getAmountValue() != null)) {
      localReportDataItem.setData(paramBalance.getAmountValue());
    } else {
      localReportDataItem.setData(null);
    }
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(null);
    paramReportResult.addRow(localReportRow);
  }
  
  private static BigDecimal jdMethod_new(ReportResult paramReportResult, ResultSet paramResultSet, boolean paramBoolean, Account paramAccount)
    throws SQLException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult1 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult1);
    Object localObject = "";
    BigDecimal localBigDecimal1 = new BigDecimal("0.0");
    BigDecimal localBigDecimal2 = new BigDecimal("0.0");
    int i = 1;
    String str1 = ResourceUtil.getString(KEY_REGISTER_DISPLAY_TEXT_CAT_SUBCAT, "com.ffusion.beans.register.resources", localLocale);
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = "{0}:{1}";
    }
    int j = 1;
    ReportResult localReportResult2 = null;
    int k = 0;
    String str2 = "category_amount";
    while (paramResultSet.next())
    {
      if (k == 0)
      {
        k = 1;
        localReportResult2 = jdMethod_for(true, localLocale, localReportResult1);
      }
      String str3 = paramResultSet.getString(15);
      String str4 = paramResultSet.getString(16);
      if (str3 == null) {
        str3 = "";
      }
      String str5 = "";
      if (str4 == null) {
        str5 = str3;
      } else if (str3.length() == 0) {
        str5 = str4;
      } else {
        str5 = MessageFormat.format(str1, new Object[] { str3, str4 });
      }
      if ((!str5.equals(localObject)) && (i == 0))
      {
        jdMethod_int((String)localObject, localBigDecimal1, localLocale, paramAccount, localReportResult1);
        localReportResult2 = jdMethod_for(false, localLocale, localReportResult1);
        localBigDecimal2 = localBigDecimal2.add(localBigDecimal1);
        localBigDecimal1 = new BigDecimal("0.0");
      }
      BigDecimal localBigDecimal3 = paramResultSet.getBigDecimal("category_amount");
      Currency localCurrency = new Currency(localBigDecimal3, localLocale);
      localBigDecimal1 = localBigDecimal1.add(localBigDecimal3);
      if (str5.equals(localObject)) {
        str5 = null;
      } else {
        localObject = str5;
      }
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = null;
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str5);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localCurrency);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(null);
      if (j % 2 == 0) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      j++;
      i = 0;
      localReportResult2.addRow(localReportRow);
    }
    if (k == 0) {
      jdMethod_int(localReportResult1);
    }
    if (!localBigDecimal2.equals(localBigDecimal1)) {
      jdMethod_int((String)localObject, localBigDecimal1, localLocale, paramAccount, localReportResult1);
    }
    localBigDecimal2 = localBigDecimal2.add(localBigDecimal1);
    jdMethod_for(paramBoolean, localBigDecimal2, localLocale, paramAccount, localReportResult1);
    return localBigDecimal2;
  }
  
  private static ReportResult jdMethod_for(boolean paramBoolean, Locale paramLocale, ReportResult paramReportResult)
    throws RptException
  {
    int i = 3;
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(3);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2251, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(40);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setWidthAsPercent(30);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2264, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setWidthAsPercent(30);
    localReportResult.addColumn(localReportColumn);
    return localReportResult;
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, ResultSet paramResultSet, boolean paramBoolean, Account paramAccount)
    throws SQLException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult1 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult1);
    BigDecimal localBigDecimal1 = new BigDecimal("0.0");
    String str1 = ResourceUtil.getString(KEY_REGISTER_DISPLAY_TEXT_CAT_SUBCAT, "com.ffusion.beans.register.resources", localLocale);
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = "{0}:{1}";
    }
    int i = 1;
    ReportResult localReportResult2 = null;
    int j = 0;
    Object localObject = "";
    BigDecimal localBigDecimal2 = new BigDecimal("0.0");
    String str2 = "category_amount";
    while (paramResultSet.next())
    {
      if (j == 0)
      {
        j = 1;
        localReportResult2 = jdMethod_new(true, localLocale, paramBoolean, localReportResult1);
      }
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = null;
      String str3 = paramResultSet.getString(18);
      localReportDataItem = localReportDataItems.add();
      if (str3.equals(localObject))
      {
        str3 = null;
      }
      else
      {
        if ((localObject != null) && (!((String)localObject).equals(str3)) && (i > 1))
        {
          jdMethod_int((String)localObject, localBigDecimal2, localLocale, paramAccount, localReportResult1);
          localReportResult2 = jdMethod_new(false, localLocale, paramBoolean, localReportResult1);
        }
        localBigDecimal2 = new BigDecimal("0.0");
        localObject = str3;
      }
      localBigDecimal2 = localBigDecimal2.add(paramResultSet.getBigDecimal("category_amount"));
      localReportDataItem.setData(str3);
      String str4 = paramResultSet.getString(5);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str4);
      DateTime localDateTime1 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(10));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime1);
      DateTime localDateTime2 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(11));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime2);
      String str5 = paramResultSet.getString(6);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str5);
      String str6 = paramResultSet.getString(9);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str6);
      String str7 = paramResultSet.getString(15);
      String str8 = paramResultSet.getString(16);
      if (str7 == null) {
        str7 = "";
      }
      String str9 = "";
      if (str8 == null) {
        str9 = str7;
      } else if (str7.length() == 0) {
        str9 = str8;
      } else {
        str9 = MessageFormat.format(str1, new Object[] { str7, str8 });
      }
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str9);
      String str10 = paramResultSet.getString(17);
      localReportDataItem = localReportDataItems.add();
      if (str10.equals(ah0)) {
        localReportDataItem.setData(ReportConsts.getText(2308, localLocale));
      } else {
        localReportDataItem.setData(ReportConsts.getText(2309, localLocale));
      }
      BigDecimal localBigDecimal3 = paramResultSet.getBigDecimal("category_amount");
      Currency localCurrency = new Currency(localBigDecimal3, localLocale);
      localBigDecimal1 = localBigDecimal1.add(localBigDecimal3);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localCurrency);
      if (i % 2 == 0) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      i++;
      localReportResult2.addRow(localReportRow);
    }
    if (j == 0) {
      jdMethod_int(localReportResult1);
    }
    if (!localBigDecimal2.equals(localBigDecimal1)) {
      jdMethod_int((String)localObject, localBigDecimal2, localLocale, paramAccount, localReportResult1);
    }
    jdMethod_for(paramBoolean, localBigDecimal1, localLocale, paramAccount, localReportResult1);
  }
  
  private static ReportResult jdMethod_new(boolean paramBoolean1, Locale paramLocale, boolean paramBoolean2, ReportResult paramReportResult)
    throws RptException
  {
    int i = 9;
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(9);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2260, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2259, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2256, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      if (paramBoolean2) {
        localArrayList.add(ReportConsts.getColumnName(2258, paramLocale));
      } else {
        localArrayList.add(ReportConsts.getColumnName(2257, paramLocale));
      }
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2265, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2251, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2262, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    return localReportResult;
  }
  
  private static void jdMethod_try(ReportResult paramReportResult, ResultSet paramResultSet, boolean paramBoolean, Account paramAccount)
    throws SQLException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult1 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult1);
    BigDecimal localBigDecimal1 = new BigDecimal("0.0");
    String str1 = ResourceUtil.getString(KEY_REGISTER_DISPLAY_TEXT_CAT_SUBCAT, "com.ffusion.beans.register.resources", localLocale);
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = "{0}:{1}";
    }
    Object localObject = "";
    int i = 1;
    BigDecimal localBigDecimal2 = new BigDecimal("0.0");
    ReportResult localReportResult2 = null;
    int j = 0;
    String str2 = "category_amount";
    while (paramResultSet.next())
    {
      if (j == 0)
      {
        j = 1;
        localReportResult2 = jdMethod_int(true, localLocale, paramBoolean, localReportResult1);
      }
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = null;
      String str3 = paramResultSet.getString(6);
      localReportDataItem = localReportDataItems.add();
      if (((str3 != null) && (str3.equals(localObject))) || ((str3 == null) && (localObject == null)))
      {
        localReportDataItem.setData(null);
      }
      else
      {
        if ((localObject != null) && (!((String)localObject).equals(str3)) && (i > 1))
        {
          jdMethod_int((String)localObject, localBigDecimal2, localLocale, paramAccount, localReportResult1);
          localReportResult2 = jdMethod_int(false, localLocale, paramBoolean, localReportResult1);
        }
        localBigDecimal2 = new BigDecimal("0.0");
        localReportDataItem.setData(str3);
        localObject = str3;
      }
      localBigDecimal2 = localBigDecimal2.add(paramResultSet.getBigDecimal("category_amount"));
      String str4 = paramResultSet.getString(5);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str4);
      DateTime localDateTime1 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(10));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime1);
      DateTime localDateTime2 = ProfileUtil.getDateTime(paramResultSet.getTimestamp(11));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime2);
      String str5 = paramResultSet.getString(9);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str5);
      String str6 = paramResultSet.getString(15);
      String str7 = paramResultSet.getString(16);
      if (str6 == null) {
        str6 = "";
      }
      String str8 = "";
      if (str7 == null) {
        str8 = str6;
      } else if (str6.length() == 0) {
        str8 = str7;
      } else {
        str8 = MessageFormat.format(str1, new Object[] { str6, str7 });
      }
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str8);
      String str9 = paramResultSet.getString(17);
      localReportDataItem = localReportDataItems.add();
      if (str9.equals(ah0)) {
        localReportDataItem.setData(ReportConsts.getText(2308, localLocale));
      } else {
        localReportDataItem.setData(ReportConsts.getText(2309, localLocale));
      }
      BigDecimal localBigDecimal3 = paramResultSet.getBigDecimal("category_amount");
      Currency localCurrency = new Currency(localBigDecimal3, localLocale);
      localBigDecimal1 = localBigDecimal1.add(localBigDecimal3);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localCurrency);
      if (i % 2 == 0) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      i++;
      localReportResult2.addRow(localReportRow);
    }
    if (j == 0) {
      jdMethod_int(localReportResult1);
    }
    if (!localBigDecimal2.equals(localBigDecimal1)) {
      jdMethod_int((String)localObject, localBigDecimal2, localLocale, paramAccount, localReportResult1);
    }
    jdMethod_for(paramBoolean, localBigDecimal1, localLocale, paramAccount, localReportResult1);
  }
  
  private static ReportResult jdMethod_int(boolean paramBoolean1, Locale paramLocale, boolean paramBoolean2, ReportResult paramReportResult)
    throws RptException
  {
    int i = 8;
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(8);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      if (paramBoolean2) {
        localArrayList.add(ReportConsts.getColumnName(2258, paramLocale));
      } else {
        localArrayList.add(ReportConsts.getColumnName(2257, paramLocale));
      }
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2259, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2256, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2265, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2251, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2262, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    return localReportResult;
  }
  
  private static void jdMethod_int(ReportResult paramReportResult, ResultSet paramResultSet, boolean paramBoolean, Account paramAccount)
    throws SQLException, RptException
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult1 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult1);
    BigDecimal localBigDecimal1 = new BigDecimal("0.0");
    String str1 = ResourceUtil.getString(KEY_REGISTER_DISPLAY_TEXT_CAT_SUBCAT, "com.ffusion.beans.register.resources", localLocale);
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = "{0}:{1}";
    }
    int i = 1;
    int j = 0;
    ReportResult localReportResult2 = null;
    String str2 = "category_amount";
    while (paramResultSet.next())
    {
      if (j == 0)
      {
        localReportResult2 = jdMethod_for(true, localLocale, paramBoolean, localReportResult1);
        j = 1;
      }
      ReportRow localReportRow = new ReportRow(localLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      ReportDataItem localReportDataItem = null;
      DateTime localDateTime = ProfileUtil.getDateTime(paramResultSet.getTimestamp(10));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localDateTime);
      String str3 = paramResultSet.getString(18);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str3);
      String str4 = paramResultSet.getString(6);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str4);
      String str5 = paramResultSet.getString(15);
      String str6 = paramResultSet.getString(16);
      if (str5 == null) {
        str5 = "";
      }
      String str7 = "";
      if (str6 == null) {
        str7 = str5;
      } else if (str5.length() == 0) {
        str7 = str6;
      } else {
        str7 = MessageFormat.format(str1, new Object[] { str5, str6 });
      }
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(str7);
      BigDecimal localBigDecimal2 = paramResultSet.getBigDecimal("category_amount");
      Currency localCurrency = new Currency(localBigDecimal2, localLocale);
      localBigDecimal1 = localBigDecimal1.add(localBigDecimal2);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localCurrency);
      if (i % 2 == 0) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      i++;
      localReportResult2.addRow(localReportRow);
    }
    if (j == 0) {
      jdMethod_int(localReportResult1);
    }
    jdMethod_for(paramBoolean, localBigDecimal1, localLocale, paramAccount, localReportResult1);
  }
  
  private static void jdMethod_int(ReportResult paramReportResult)
    throws RptException
  {
    jdMethod_for(paramReportResult, ReportConsts.getText(10054, paramReportResult.getLocale()));
  }
  
  private static ReportResult jdMethod_for(boolean paramBoolean1, Locale paramLocale, boolean paramBoolean2, ReportResult paramReportResult)
    throws RptException
  {
    int i = 5;
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(5);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    ArrayList localArrayList = null;
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setWidthAsPercent(20);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2255, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setWidthAsPercent(20);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2260, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setWidthAsPercent(20);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      if (paramBoolean2) {
        localArrayList.add(ReportConsts.getColumnName(2258, paramLocale));
      } else {
        localArrayList.add(ReportConsts.getColumnName(2257, paramLocale));
      }
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setWidthAsPercent(20);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2251, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("java.lang.String");
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setWidthAsPercent(20);
    if (paramBoolean1)
    {
      localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(2263, paramLocale));
      localReportColumn.setLabels(localArrayList);
    }
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportResult.addColumn(localReportColumn);
    return localReportResult;
  }
  
  private static void jdMethod_for(Connection paramConnection, RegisterTransactions paramRegisterTransactions, SecureUser paramSecureUser)
  {
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if ((localRegisterTransaction.getStatusValue() == 0) || (localRegisterTransaction.getStatusValue() == 1))
      {
        int i = localRegisterTransaction.getStatusValue();
        if ((jdMethod_new(paramConnection, localRegisterTransaction, paramSecureUser)) && (i == 1)) {
          localIterator.remove();
        }
      }
    }
    try
    {
      jdMethod_for(paramConnection, paramSecureUser, ah1, new HashMap());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.throwing("The system was unable to automatically reconcile bank transactions older than " + ah1 + "days due to an error.", localProfileException);
    }
  }
  
  private static boolean jdMethod_new(Connection paramConnection, RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
  {
    boolean bool = false;
    RegisterTransaction localRegisterTransaction = jdMethod_for(paramConnection, paramRegisterTransaction, paramSecureUser);
    if (localRegisterTransaction != null)
    {
      jdMethod_for(paramConnection, paramRegisterTransaction, localRegisterTransaction, paramSecureUser);
      bool = true;
    }
    return bool;
  }
  
  private static RegisterTransaction jdMethod_for(Connection paramConnection, RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
  {
    Object localObject = null;
    RegisterTransaction localRegisterTransaction1 = null;
    RegisterTransaction localRegisterTransaction2 = null;
    logModerateActivity("findMatchingTransaction");
    RegisterTransactions localRegisterTransactions;
    Iterator localIterator;
    if (paramRegisterTransaction.getStatusValue() == 0)
    {
      localRegisterTransactions = jdMethod_int(paramConnection, paramRegisterTransaction, paramSecureUser);
      if (localRegisterTransactions != null)
      {
        localRegisterTransaction2 = paramRegisterTransaction;
        localIterator = localRegisterTransactions.iterator();
        while ((localIterator.hasNext()) && (localObject == null))
        {
          localRegisterTransaction1 = (RegisterTransaction)localIterator.next();
          if (jdMethod_for(localRegisterTransaction1, localRegisterTransaction2)) {
            localObject = localRegisterTransaction1;
          }
        }
      }
    }
    else if (paramRegisterTransaction.getTypeValue() == 3)
    {
      localRegisterTransactions = jdMethod_for(paramConnection, paramRegisterTransaction, paramSecureUser, true);
      localRegisterTransaction1 = paramRegisterTransaction;
      if (localRegisterTransactions != null)
      {
        localIterator = localRegisterTransactions.iterator();
        while ((localIterator.hasNext()) && (localObject == null))
        {
          localRegisterTransaction2 = (RegisterTransaction)localIterator.next();
          if (jdMethod_for(localRegisterTransaction1, localRegisterTransaction2)) {
            localObject = localRegisterTransaction2;
          }
        }
      }
      if (localObject == null)
      {
        localRegisterTransactions = jdMethod_int(paramConnection, paramRegisterTransaction, paramSecureUser);
        if (localRegisterTransactions != null)
        {
          localIterator = localRegisterTransactions.iterator();
          while ((localIterator.hasNext()) && (localObject == null))
          {
            localRegisterTransaction2 = (RegisterTransaction)localIterator.next();
            if (jdMethod_for(localRegisterTransaction1, localRegisterTransaction2)) {
              localObject = localRegisterTransaction2;
            }
          }
        }
      }
    }
    else
    {
      localRegisterTransactions = jdMethod_int(paramConnection, paramRegisterTransaction, paramSecureUser);
      if (localRegisterTransactions != null)
      {
        localRegisterTransaction1 = paramRegisterTransaction;
        localIterator = localRegisterTransactions.iterator();
        while ((localIterator.hasNext()) && (localObject == null))
        {
          localRegisterTransaction2 = (RegisterTransaction)localIterator.next();
          if (jdMethod_for(localRegisterTransaction1, localRegisterTransaction2)) {
            localObject = localRegisterTransaction2;
          }
        }
      }
    }
    logModerateActivity("findMatchingTransaction: end");
    return localObject;
  }
  
  private static void jdMethod_for(Connection paramConnection, RegisterTransaction paramRegisterTransaction1, RegisterTransaction paramRegisterTransaction2, SecureUser paramSecureUser)
  {
    RegisterTransaction localRegisterTransaction1 = null;
    RegisterTransaction localRegisterTransaction2 = null;
    if (paramRegisterTransaction1.getStatusValue() == 0)
    {
      localRegisterTransaction1 = paramRegisterTransaction1;
      localRegisterTransaction2 = paramRegisterTransaction2;
    }
    else
    {
      localRegisterTransaction1 = paramRegisterTransaction2;
      localRegisterTransaction2 = paramRegisterTransaction1;
    }
    try
    {
      logModerateActivity("mergeTransactions: mergeBankAndManualTransactions");
      int i = paramRegisterTransaction1.getStatusValue();
      jdMethod_for(localRegisterTransaction2, localRegisterTransaction1, paramSecureUser);
      logModerateActivity("mergeTransactions: deleteTransaction");
      if (i == 0) {
        deleteTransaction(paramConnection, localRegisterTransaction2);
      } else {
        modifyTransaction(paramConnection, localRegisterTransaction1, paramSecureUser);
      }
    }
    catch (ProfileException localProfileException) {}
    logModerateActivity("mergeTransactions: end");
  }
  
  private static boolean jdMethod_for(RegisterTransaction paramRegisterTransaction1, RegisterTransaction paramRegisterTransaction2)
  {
    if (paramRegisterTransaction1.getRegisterTypeValue() == paramRegisterTransaction2.getRegisterTypeValue()) {
      if ((paramRegisterTransaction2.getRegisterTypeValue() == 3) && (RegisterUtil.hasValue(paramRegisterTransaction2.getReferenceNumber())))
      {
        if (jdMethod_int(paramRegisterTransaction1, paramRegisterTransaction2))
        {
          paramRegisterTransaction1.setReconcileMatch(paramRegisterTransaction2.getRegisterId());
          paramRegisterTransaction2.setReconcileMatch(paramRegisterTransaction1.getRegisterId());
          if (!paramRegisterTransaction1.getAmountValue().equals(paramRegisterTransaction2.getAmountValue()))
          {
            paramRegisterTransaction1.setDiscrepancy(true);
            paramRegisterTransaction2.setDiscrepancy(true);
          }
          return true;
        }
      }
      else if (paramRegisterTransaction1.getAmountValue().equals(paramRegisterTransaction2.getAmountValue()))
      {
        DateTime localDateTime1 = paramRegisterTransaction2.getDateIssuedValue();
        DateTime localDateTime2 = (DateTime)paramRegisterTransaction1.getDateValue().clone();
        DateTime localDateTime3 = (DateTime)localDateTime2.clone();
        localDateTime2.add(6, 1);
        localDateTime3.add(6, matchRange * -1);
        if ((localDateTime1.after(localDateTime3)) && (localDateTime1.before(localDateTime2)))
        {
          paramRegisterTransaction1.setReconcileMatch(paramRegisterTransaction2.getRegisterId());
          paramRegisterTransaction2.setReconcileMatch(paramRegisterTransaction1.getRegisterId());
          return true;
        }
      }
    }
    return false;
  }
  
  private static boolean jdMethod_int(RegisterTransaction paramRegisterTransaction1, RegisterTransaction paramRegisterTransaction2)
  {
    try
    {
      String str1 = paramRegisterTransaction1.getReferenceNumber();
      String str2 = paramRegisterTransaction2.getReferenceNumber();
      if ((RegisterUtil.hasValue(str1)) && (RegisterUtil.hasValue(str2)))
      {
        long l1 = Long.parseLong(str1);
        long l2 = Long.parseLong(str2);
        if (l1 == l2) {
          return true;
        }
      }
    }
    catch (NumberFormatException localNumberFormatException) {}
    return false;
  }
  
  private static RegisterTransactions jdMethod_int(RegisterTransactions paramRegisterTransactions, int paramInt)
  {
    RegisterTransactions localRegisterTransactions = new RegisterTransactions();
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      if (localRegisterTransaction.getStatusValue() == paramInt) {
        localRegisterTransactions.add(localRegisterTransaction);
      }
    }
    return localRegisterTransactions;
  }
  
  private static RegisterTransactions jdMethod_for(Connection paramConnection, RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser, boolean paramBoolean)
  {
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = null;
    RegisterTransaction localRegisterTransaction = new RegisterTransaction();
    int i = paramRegisterTransaction.getTypeValue();
    int j = paramRegisterTransaction.getStatusValue();
    localRegisterTransaction.set("ACCOUNT", (String)paramRegisterTransaction.get("ACCOUNT"));
    localRegisterTransaction.set("CURRENCY_CODE", (String)paramRegisterTransaction.get("CURRENCY_CODE"));
    if ((j == 0) && (i == 3) && (RegisterUtil.hasValue(paramRegisterTransaction.getReferenceNumber())))
    {
      localRegisterTransaction.setReferenceNumber(unPadRefNum(paramRegisterTransaction.getReferenceNumber()));
    }
    else
    {
      if (paramBoolean) {
        localRegisterTransaction.setReferenceNumber(unPadRefNum(paramRegisterTransaction.getReferenceNumber()));
      }
      localDateTime2 = (DateTime)paramRegisterTransaction.getDateIssuedValue().clone();
      localDateTime1 = (DateTime)paramRegisterTransaction.getDateIssuedValue().clone();
      localDateTime1.add(6, 1);
      localDateTime2.add(6, matchRange * -1);
    }
    localRegisterTransaction.setType(i);
    if (j == 0) {
      localRegisterTransaction.setStatus(1);
    } else {
      localRegisterTransaction.setStatus(0);
    }
    RegisterTransactions localRegisterTransactions = null;
    try
    {
      localRegisterTransactions = jdMethod_for(paramConnection, localRegisterTransaction, localDateTime2, localDateTime1, paramSecureUser);
    }
    catch (ProfileException localProfileException)
    {
      return null;
    }
    return localRegisterTransactions;
  }
  
  private static RegisterTransactions jdMethod_int(Connection paramConnection, RegisterTransaction paramRegisterTransaction, SecureUser paramSecureUser)
  {
    return jdMethod_for(paramConnection, paramRegisterTransaction, paramSecureUser, false);
  }
  
  private static void jdMethod_for(RegisterTransaction paramRegisterTransaction1, RegisterTransaction paramRegisterTransaction2, SecureUser paramSecureUser)
  {
    paramRegisterTransaction2.setID(paramRegisterTransaction1.getID());
    paramRegisterTransaction2.setType(paramRegisterTransaction1.getTypeValue());
    paramRegisterTransaction2.setDate(paramRegisterTransaction1.getDateValue());
    paramRegisterTransaction2.setStatus(3);
    paramRegisterTransaction1.setStatus(3);
    if (RegisterUtil.hasValue(paramRegisterTransaction1.getReferenceNumber())) {
      paramRegisterTransaction2.setReferenceNumber(unPadRefNum(paramRegisterTransaction1.getReferenceNumber()));
    }
    if (!RegisterUtil.hasValue(paramRegisterTransaction2.getMemo())) {
      paramRegisterTransaction2.setMemo(paramRegisterTransaction1.getMemo());
    }
    if (!paramRegisterTransaction2.getAmountValue().equals(paramRegisterTransaction1.getAmountValue()))
    {
      if (paramRegisterTransaction2.getMultipleCategories()) {
        paramRegisterTransaction2.setRegisterCategoryId(0);
      }
      String[] arrayOfString = new String[2];
      arrayOfString[0] = paramRegisterTransaction2.getAmount();
      arrayOfString[1] = paramRegisterTransaction2.getMemo();
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.register.resources", "DiscrepancyText", arrayOfString);
      String str = (String)localLocalizableString.localize(paramSecureUser.getLocale());
      paramRegisterTransaction2.setMemo(str);
      paramRegisterTransaction2.setAmount(paramRegisterTransaction1.getAmountValue());
      paramRegisterTransaction2.setDiscrepancy(true);
      paramRegisterTransaction1.setDiscrepancy(true);
      paramRegisterTransaction2.setStatus(4);
      paramRegisterTransaction1.setStatus(4);
    }
  }
  
  private static String jdMethod_for(ReportCriteria paramReportCriteria, boolean paramBoolean)
    throws ProfileException
  {
    String str1 = "buildReportSQL";
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      String str2 = jdMethod_new(paramReportCriteria);
      localStringBuffer.append("SELECT a.reg_transaction_id, a.directory_id, a.account_id, a.fi_transaction_id, a.reference_number, a.payee_name, a.type, a.status, a.memo, a.date_issued, a.date_cleared, a.rec_server_tran_id, a.server_tran_id, a.amount, d.name as parent_cat_name, c.name as cat_name, c.tax_related, e.trans_type_desc, b.amount as category_amount FROM reg_transaction a, reg_tran_category b, reg_user_category c LEFT OUTER JOIN reg_user_category d ON (d.reg_user_cat_id= c.parent_category_id and d.directory_id=?), reg_transtypedesc e WHERE a.reg_transaction_id= b.reg_transaction_id AND b.reg_user_cat_id= c.reg_user_cat_id AND a.type= e.trans_type_id AND e.language= ? AND a.directory_id= ? AND a.account_id= ? AND b.directory_id=c.directory_id ");
      String str3 = paramReportCriteria.getSearchCriteria().getProperty("StartDate");
      String str4 = paramReportCriteria.getSearchCriteria().getProperty("EndDate");
      if ((str3 != null) && (str3.length() > 0))
      {
        localStringBuffer.append(" AND a.");
        localStringBuffer.append("date_issued");
        localStringBuffer.append(">=? ");
      }
      if ((str4 != null) && (str4.length() > 0))
      {
        localStringBuffer.append(" AND a.");
        localStringBuffer.append("date_issued");
        localStringBuffer.append("<=? ");
      }
      if (str2.equals("Account Register Reconciliation Status")) {
        localStringBuffer.append("AND (a.status = 0) ");
      } else {
        localStringBuffer.append("AND (a.status = 2 OR a.status = 3 OR a.status = 4) ");
      }
      if (str2.equals("Account Register Tax Status")) {
        localStringBuffer.append(REG_REPORT_SQL_TAX_STATUS);
      }
      if (paramBoolean) {
        localStringBuffer.append("AND b.amount>= 0 ");
      } else {
        localStringBuffer.append("AND b.amount< 0 ");
      }
      Properties localProperties = paramReportCriteria.getSearchCriteria();
      String str5 = localProperties.getProperty("TranType");
      if ((str5 != null) && (str5.length() > 0)) {
        localStringBuffer.append("AND a.type= ? ");
      }
      String str6 = localProperties.getProperty("RefNumber");
      if ((str6 != null) && (str6.length() > 0)) {
        localStringBuffer.append("AND a.reference_number= ? ");
      }
      String str7 = localProperties.getProperty("MinimumAmount");
      if ((str7 != null) && (str7.length() > 0))
      {
        localStringBuffer.append(" AND a.");
        localStringBuffer.append("amount");
        if (paramBoolean) {
          localStringBuffer.append(">=? ");
        } else {
          localStringBuffer.append("<=? ");
        }
      }
      String str8 = localProperties.getProperty("MaximumAmount");
      if ((str8 != null) && (str8.length() > 0))
      {
        localStringBuffer.append(" AND a.");
        localStringBuffer.append("amount");
        if (paramBoolean) {
          localStringBuffer.append("<=? ");
        } else {
          localStringBuffer.append(">=? ");
        }
      }
      String str9 = localProperties.getProperty("Payee");
      if ((str9 != null) && (str9.length() > 0)) {
        localStringBuffer.append(" AND a.payee_name= ? ");
      }
      String str10 = localProperties.getProperty("Category");
      if ((str10 != null) && (str10.length() > 0)) {
        localStringBuffer.append("AND b.reg_user_cat_id= ? ");
      }
      String str11 = localProperties.getProperty("Memo");
      if ((str11 != null) && (str11.length() > 0)) {
        localStringBuffer.append("AND a.memo LIKE ? ");
      }
      String str12 = localProperties.getProperty("Tax");
      int i = (str12 != null) && (str12.equalsIgnoreCase("T")) ? 1 : 0;
      if (i != 0) {
        localStringBuffer.append(REG_REPORT_SQL_ADV_TAX);
      }
      if (str2.equals("Account Register Reconciliation Status")) {
        localStringBuffer.append("ORDER BY a.date_issued, a.reg_transaction_id ");
      } else if (str2.equals("Account Register Transaction Type Totals")) {
        localStringBuffer.append("ORDER BY e.trans_type_desc, a.date_issued, a.reg_transaction_id ");
      } else if (str2.equals("Account Register Payee Totals")) {
        localStringBuffer.append("ORDER BY a.payee_name, a.date_issued, a.reg_transaction_id ");
      } else {
        localStringBuffer.append("ORDER BY d.name, c.name, a.date_issued, a.reg_transaction_id ");
      }
    }
    catch (Exception localException)
    {
      throw new ProfileException(localException, str1, 3504, "unable to build SQL from report criteria");
    }
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_new(ReportCriteria paramReportCriteria)
    throws ProfileException
  {
    String str1 = "getReportType";
    Properties localProperties = paramReportCriteria.getReportOptions();
    if (localProperties == null) {
      throw new ProfileException(str1, 3504, "getReportOptions() returned null");
    }
    String str2 = localProperties.getProperty("REPORTTYPE");
    if (localProperties == null) {
      throw new ProfileException(str1, 3504, "report type not found");
    }
    return str2;
  }
  
  private static PreparedStatement jdMethod_for(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, Connection paramConnection, String paramString, boolean paramBoolean)
    throws ProfileException
  {
    String str1 = "populateReportStatement";
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str2 = jdMethod_for(paramSecureUser.getLocale());
    int i = getDirectoryID(paramSecureUser);
    if (!Profile.isValidId(i)) {
      logInvalidRequest(str1, "Invalid directory ID.");
    }
    try
    {
      String str3 = localProperties.getProperty("StartDate");
      String str4 = localProperties.getProperty("EndDate");
      DateFormat localDateFormat = DateFormatUtil.getFormatter(ahX);
      Timestamp localTimestamp1 = null;
      Timestamp localTimestamp2 = null;
      try
      {
        if ((str3 != null) && (str3.length() != 0)) {
          localTimestamp1 = new Timestamp(localDateFormat.parse(str3).getTime());
        }
      }
      catch (ParseException localParseException1)
      {
        throw new ProfileException(str1, 3504, "unable to find or parse report start date");
      }
      try
      {
        if ((str4 != null) && (str4.length() != 0)) {
          localTimestamp2 = new Timestamp(jdMethod_byte(localDateFormat.parse(str4)));
        }
      }
      catch (ParseException localParseException2)
      {
        throw new ProfileException(str1, 3504, "unable to find or parse report end date");
      }
      PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramString);
      localPreparedStatement.setInt(1, i);
      localPreparedStatement.setString(2, str2);
      localPreparedStatement.setInt(3, i);
      int j = 5;
      if (localTimestamp1 != null) {
        localPreparedStatement.setTimestamp(j++, localTimestamp1);
      }
      if (localTimestamp2 != null) {
        localPreparedStatement.setTimestamp(j++, localTimestamp2);
      }
      String str5 = localProperties.getProperty("TranType");
      if ((str5 != null) && (str5.length() > 0))
      {
        int k = Integer.parseInt(str5);
        localPreparedStatement.setInt(j++, k);
      }
      String str6 = localProperties.getProperty("RefNumber");
      if ((str6 != null) && (str6.length() > 0)) {
        localPreparedStatement.setString(j++, str6);
      }
      String str7 = localProperties.getProperty("MinimumAmount");
      if ((str7 != null) && (str7.length() > 0))
      {
        localObject1 = new BigDecimal(str7);
        if (!paramBoolean) {
          localObject1 = ((BigDecimal)localObject1).negate();
        }
        localPreparedStatement.setBigDecimal(j++, (BigDecimal)localObject1);
      }
      Object localObject1 = localProperties.getProperty("MaximumAmount");
      if ((localObject1 != null) && (((String)localObject1).length() > 0))
      {
        localObject2 = new BigDecimal((String)localObject1);
        if (!paramBoolean) {
          localObject2 = ((BigDecimal)localObject2).negate();
        }
        localPreparedStatement.setBigDecimal(j++, (BigDecimal)localObject2);
      }
      Object localObject2 = localProperties.getProperty("Payee");
      if ((localObject2 != null) && (((String)localObject2).length() > 0)) {
        localPreparedStatement.setString(j++, (String)localObject2);
      }
      String str8 = localProperties.getProperty("Category");
      if ((str8 != null) && (str8.length() > 0))
      {
        int m = Integer.parseInt(str8);
        localPreparedStatement.setInt(j++, m);
      }
      String str9 = localProperties.getProperty("Memo");
      if ((str9 != null) && (str9.length() > 0)) {
        localPreparedStatement.setString(j++, "%" + str9 + "%");
      }
      return localPreparedStatement;
    }
    catch (ProfileException localProfileException)
    {
      throw localProfileException;
    }
    catch (Exception localException)
    {
      throw new ProfileException(localException, str1, 3504, "error preparing statement for report");
    }
  }
  
  private static ArrayList jdMethod_try(ReportCriteria paramReportCriteria)
    throws ProfileException
  {
    String str1 = "getReportAccountIDs";
    ArrayList localArrayList = new ArrayList();
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str2 = localProperties.getProperty("Account");
    if (str2 != null)
    {
      localArrayList.add(str2);
    }
    else
    {
      String str3 = localProperties.getProperty("Accounts");
      if (str3 != null)
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(str3, ",");
        while (localStringTokenizer.hasMoreTokens()) {
          localArrayList.add(localStringTokenizer.nextToken());
        }
      }
      if (localArrayList.size() == 0) {
        throw new ProfileException(str1, 3504, "no accounts id(s) found in report criteria");
      }
    }
    return localArrayList;
  }
  
  private static void jdMethod_for(ReportResult paramReportResult, String paramString, boolean paramBoolean)
    throws RptException
  {
    ReportResult localReportResult = new ReportResult(Locale.getDefault());
    paramReportResult.addSubReport(localReportResult);
    Locale localLocale = paramReportResult.getLocale();
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    localReportHeading.setLabel(paramString);
    if (paramBoolean) {
      localReportResult.setHeading(localReportHeading);
    } else {
      localReportResult.setSectionHeading(localReportHeading);
    }
  }
  
  private static String jdMethod_for(Locale paramLocale)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramLocale.getLanguage());
    localStringBuffer.append("_");
    localStringBuffer.append(paramLocale.getCountry());
    return localStringBuffer.toString();
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, Accounts paramAccounts, ArrayList paramArrayList)
  {
    String str1 = (String)paramReportCriteria.getSearchCriteria().get("TranType");
    Object localObject1;
    if ((str1 != null) && (str1.length() > 0)) {
      try
      {
        ResourceBundle localResourceBundle = ResourceBundle.getBundle("com.ffusion.beans.register.resources", paramSecureUser.getLocale());
        localObject1 = ResourceBundle.getBundle(localResourceBundle.getString("TransactionTypeResourceFile"), paramSecureUser.getLocale());
        paramReportCriteria.setDisplayValue("TranType", ((ResourceBundle)localObject1).getString("TransactionType" + str1));
      }
      catch (Exception localException1) {}
    }
    String str2 = (String)paramReportCriteria.getSearchCriteria().get("Category");
    Object localObject2;
    if ((str2 != null) && (str2.length() > 0)) {
      try
      {
        localObject1 = new RegisterCategories();
        getRegisterCategories((RegisterCategories)localObject1, paramSecureUser);
        localObject2 = ((RegisterCategories)localObject1).getById(str2);
        paramReportCriteria.setDisplayValue("Category", ((RegisterCategory)localObject2).getName());
      }
      catch (Exception localException2) {}
    }
    String str3 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    if (str3.equals("Custom Register Report"))
    {
      localObject2 = (String)paramReportCriteria.getSearchCriteria().get("Tax");
      try
      {
        if ((localObject2 != null) && (((String)localObject2).equals(ah0))) {
          paramReportCriteria.setDisplayValue("Tax", ReportConsts.getText(2308, paramSecureUser.getLocale()));
        } else {
          paramReportCriteria.setDisplayValue("Tax", ReportConsts.getText(2309, paramSecureUser.getLocale()));
        }
      }
      catch (Exception localException3) {}
    }
    if (paramAccounts != null)
    {
      localObject2 = paramArrayList.iterator();
      int i = 0;
      LocalizableList localLocalizableList = new LocalizableList();
      if (paramArrayList.size() > 1) {
        i = 1;
      }
      while (((Iterator)localObject2).hasNext())
      {
        str4 = (String)((Iterator)localObject2).next();
        Account localAccount = paramAccounts.getByID(str4);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = jdMethod_for(localAccount, paramReportCriteria);
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10319", arrayOfObject);
        localLocalizableList.add(localLocalizableString);
      }
      String str4 = null;
      if (i != 0)
      {
        str4 = ReportConsts.getText(10318, paramAccounts.getLocale());
        paramReportCriteria.getSearchCriteria().remove(ReportConsts.getText(10317, paramAccounts.getLocale()));
      }
      else
      {
        str4 = ReportConsts.getText(10317, paramAccounts.getLocale());
        paramReportCriteria.getSearchCriteria().remove(ReportConsts.getText(10318, paramAccounts.getLocale()));
      }
      paramReportCriteria.setCurrentSearchCriterion(str4);
      paramReportCriteria.setCurrentSearchCriterionValue((String)localLocalizableList.localize(paramAccounts.getLocale()));
    }
    paramReportCriteria.setHiddenSearchCriterion("Accounts", true);
    paramReportCriteria.setHiddenSearchCriterion("Account", true);
  }
  
  private static long jdMethod_byte(java.util.Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    localCalendar.set(14, 999);
    return localCalendar.getTimeInMillis();
  }
  
  public static String padRefNum(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (ahY.equals("Integer"))
    {
      if (paramString.trim().equals("")) {
        return "";
      }
      int i = ah3;
      StringBuffer localStringBuffer = new StringBuffer();
      for (int j = i - paramString.length(); j > 0; j--) {
        localStringBuffer.append(" ");
      }
      paramString = localStringBuffer.toString() + paramString;
    }
    return paramString;
  }
  
  public static String unPadRefNum(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (ahY.equals("Integer")) {
      return paramString.trim();
    }
    return paramString;
  }
  
  private static int O()
  {
    int i = 20;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localResultSet = localConnection.getMetaData().getColumns(null, null, "reg_transaction", "reference_number");
      int j = 3;
      int k = 4;
      int m = 7;
      while (localResultSet.next())
      {
        String str1 = localResultSet.getString(j);
        if (str1.equalsIgnoreCase("reg_transaction"))
        {
          String str2 = localResultSet.getString(k);
          if (str2.equalsIgnoreCase("reference_number"))
          {
            i = localResultSet.getInt(m);
            break;
          }
        }
      }
    }
    catch (Exception localException)
    {
      i = 20;
    }
    finally
    {
      if (localConnection != null) {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
    if (i <= 0) {
      i = 20;
    }
    return i;
  }
  
  private static String jdMethod_byte(String paramString, Locale paramLocale)
  {
    String str1 = "com.ffusion.beans.reporting.reports";
    String str2 = "Text10315";
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10315", arrayOfObject);
    return (String)localLocalizableString.localize(paramLocale);
  }
  
  private static void jdMethod_int(String paramString, BigDecimal paramBigDecimal, Locale paramLocale, Account paramAccount, ReportResult paramReportResult)
    throws RptException
  {
    String str1 = "com.ffusion.beans.reporting.reports";
    String str2 = "Text10316";
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString;
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10316", arrayOfObject);
    jdMethod_for((String)localLocalizableString.localize(paramLocale), paramBigDecimal, paramLocale, paramAccount, paramReportResult);
  }
  
  private static void jdMethod_for(boolean paramBoolean, BigDecimal paramBigDecimal, Locale paramLocale, Account paramAccount, ReportResult paramReportResult)
    throws RptException
  {
    String str = null;
    if (paramBoolean) {
      str = ReportConsts.getText(2303, paramLocale);
    } else {
      str = ReportConsts.getText(2304, paramLocale);
    }
    jdMethod_for(str, paramBigDecimal, paramLocale, paramAccount, paramReportResult);
  }
  
  private static void jdMethod_for(String paramString, BigDecimal paramBigDecimal, Locale paramLocale, Account paramAccount, ReportResult paramReportResult)
    throws RptException
  {
    int i = 4;
    ReportResult localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(4);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumn localReportColumn = null;
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setReportColumnProperty("DATASTYLE", "reportDataSubtotal");
    localReportColumn.setWidthAsPercent(60);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setReportColumnProperty("DATASTYLE", "reportDataSubtotal");
    localReportColumn.setWidthAsPercent(20);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setReportColumnProperty("DATASTYLE", "reportDataSubtotal");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setReportColumnProperty("DATASTYLE", "reportDataSubtotal");
    localReportColumn.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn);
    ReportRow localReportRow = new ReportRow(paramLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = null;
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(null);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(paramString);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(jdMethod_byte(paramAccount.getCurrencyCode(), paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(jdMethod_for(paramBigDecimal, paramLocale));
    localReportResult.addRow(localReportRow);
  }
  
  private static Balance jdMethod_for(SecureUser paramSecureUser, Account paramAccount, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    Balance localBalance1 = null;
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      String str1 = paramReportCriteria.getSearchCriteria().getProperty("Category");
      int i = 0;
      if ((str1 != null) && (str1.length() > 0)) {
        i = 1;
      }
      String str2 = paramReportCriteria.getSearchCriteria().getProperty("Tax");
      int j = 0;
      if ((str2 != null) && (str2.length() > 0)) {
        j = 1;
      }
      StringBuffer localStringBuffer = new StringBuffer("select sum( rtc.amount) from ");
      localStringBuffer.append("reg_user_category").append(" ruc, ").append("reg_transaction").append(" rt left outer join ").append("reg_tran_category").append(" rtc on ( rt.").append("reg_transaction_id").append(" = rtc.").append("reg_transaction_id").append(" ) where rt.").append("status").append(" = ").append(0).append(" and rt.").append("directory_id").append("=? ").append(" and ruc.").append("reg_user_cat_id").append(" = rtc.").append("reg_user_cat_id").append(" and ruc.").append("directory_id").append("= rtc.").append("directory_id");
      if (i != 0) {
        localStringBuffer.append(" and rt.reg_tran_category.reg_user_cat_id =? ");
      }
      if (j != 0) {
        localStringBuffer.append(" and ruc.").append("tax_related").append(" =? ");
      }
      String str3 = paramReportCriteria.getSearchCriteria().getProperty("AccountID");
      int k = 0;
      if ((str3 == null) || (str3.length() == 0))
      {
        k = 1;
        paramReportCriteria.getSearchCriteria().setProperty("AccountID", paramAccount.getID());
      }
      jdMethod_for(localStringBuffer, paramReportCriteria.getSearchCriteria(), paramHashMap);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int m = 1;
      localPreparedStatement.setInt(m++, getDirectoryIDForConsumer(paramSecureUser));
      if ((str1 != null) && (str1.length() > 0)) {
        localPreparedStatement.setInt(m++, Integer.parseInt(str1));
      }
      if ((str2 != null) && (str2.length() > 0)) {
        localPreparedStatement.setString(m++, ah0);
      }
      jdMethod_for(localPreparedStatement, m, paramReportCriteria.getSearchCriteria(), paramHashMap);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if ((paramAccount != null) && (paramAccount.getCurrentBalance() != null)) {
        localBalance1 = (Balance)paramAccount.getCurrentBalance().clone();
      }
      Currency localCurrency = localBalance1.getAmountValue();
      BigDecimal localBigDecimal = null;
      if (localResultSet.next())
      {
        localBigDecimal = localResultSet.getBigDecimal(1);
      }
      else
      {
        Balance localBalance2 = null;
        return localBalance2;
      }
      if ((localBigDecimal != null) && (localCurrency != null)) {
        localCurrency.addAmount(localBigDecimal);
      }
      localBalance1.setAmount(localCurrency);
      if (k != 0) {
        paramReportCriteria.getSearchCriteria().remove("AccountID");
      }
    }
    finally
    {
      DBUtil.closeResultSet(localResultSet);
      DBUtil.closeStatement(localPreparedStatement);
      if (localConnection != null) {
        DBUtil.returnConnection(Profile.poolName, localConnection);
      }
    }
    return localBalance1;
  }
  
  private static String jdMethod_for(Account paramAccount, ReportCriteria paramReportCriteria)
  {
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str = localProperties.getProperty("DESTINATION");
    if (str == null)
    {
      str = "OBJECT";
      localProperties.setProperty("DESTINATION", str);
    }
    if (str.equals("USER_FILE_SYSTEM")) {
      return paramAccount.getConsumerExportDisplayText();
    }
    return paramAccount.getConsumerDisplayText();
  }
  
  private static StringBuffer jdMethod_for(PagingContext paramPagingContext, HashMap paramHashMap, Locale paramLocale)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer("select ");
    jdMethod_int(localStringBuffer, paramPagingContext, paramHashMap);
    localStringBuffer.append(", rt.* from reg_transaction rt");
    localStringBuffer.append(" left outer join reg_transtypedesc on ( reg_transtypedesc.trans_type_id = rt.type and reg_transtypedesc.language=? ) ");
    return localStringBuffer;
  }
  
  public static void autoreconcileOldTransactions(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "RegisterAdapter.autoreconcileOldTransactions";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      jdMethod_for(localConnection, paramSecureUser, paramInt, paramHashMap);
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
      DBUtil.returnConnection(Profile.poolName, localConnection);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "RegisterAdapter.autoreconcileOldTransactions";
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "UPDATE reg_transaction SET status= 2 WHERE status = 1 AND date_cleared IS NOT NULL AND date_cleared < ? AND directory_id = ?");
      DateTime localDateTime = new DateTime();
      localDateTime.set(11, 0);
      localDateTime.set(12, 0);
      localDateTime.set(13, 0);
      localDateTime.set(14, 0);
      localDateTime.add(6, -ah1);
      jdMethod_for(localPreparedStatement, 1, localDateTime);
      localPreparedStatement.setInt(2, getDirectoryIDForConsumer(paramSecureUser));
      DBUtil.executeUpdate(localPreparedStatement, "UPDATE reg_transaction SET status= 2 WHERE status = 1 AND date_cleared IS NOT NULL AND date_cleared < ? AND directory_id = ?");
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.RegisterAdapter
 * JD-Core Version:    0.7.0.1
 */