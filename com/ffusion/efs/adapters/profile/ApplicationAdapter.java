package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.applications.Application;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.applications.Applications;
import com.ffusion.beans.applications.Bank;
import com.ffusion.beans.applications.Banks;
import com.ffusion.beans.applications.Categories;
import com.ffusion.beans.applications.Category;
import com.ffusion.beans.applications.Form;
import com.ffusion.beans.applications.FormField;
import com.ffusion.beans.applications.FormFields;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.applications.Status;
import com.ffusion.beans.applications.Statuses;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.user.User;
import com.ffusion.csil.handlers.Messages;
import com.ffusion.services.javax.JavaMail;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;

public class ApplicationAdapter
  implements ProfileDefines
{
  protected static MessageAdapter messageAdapter = new MessageAdapter();
  private static final String ajy = "select * from bank";
  private static final String aiY = "select * from product where bank_id=?";
  private static final String ai9 = "select * from product_i18n where bank_id = ? and product_id=?";
  private static final String ai8 = " and language=?";
  private static final String ajc = "insert into product (bank_id,product_id,category_id,form_id,description,title) values(?,?,?,?,?,?)";
  private static final String ajs = "insert into product_i18n( bank_id, product_id, language, title, description ) values (?,?,?,?,?)";
  private static final String ajE = "update product set category_id=?,form_id=?,description=?,title=? where product_id=?";
  private static final String ajB = "delete from product where product_id=?";
  private static final String ajj = "delete from product_i18n where bank_id=? and product_id=?";
  private static final String ajz = "select s.name,s.status_id,p.product_id from status s, product_status p where s.status_id = p.status_id and s.bank_id = p.bank_id and s.bank_id = ? order by p.product_id, s.status_id";
  private static final String ajA = "select * from status where bank_id=?";
  private static final String ajD = "select * from status_i18n where bank_id = ? and status_id=?";
  private static final String ajC = " and language=?";
  private static final String ajF = "insert into status (status_id,bank_id,name) values (?,?,?)";
  private static final String ai5 = "insert into status_i18n (bank_id,status_id,language,name) values (?,?,?,?)";
  private static final String aiZ = "insert into product_status (product_id,status_id,bank_id) values (?,?,?)";
  private static final String ajm = "update status set name=? where status_id=?";
  private static final String aiT = "delete from status where status_id=?";
  private static final String aiV = "delete from status_i18n where bank_id=? and status_id=?";
  private static final String ajg = "delete from product_status where bank_id=? and product_id=? and status_id=?";
  private static final String ajJ = "delete from product_access";
  private static final String ajf = "select * from category where bank_id=?";
  private static final String ajw = "select * from status where bank_id=? and status_id=?";
  private static final String ai3 = "select * from application where app_id=?";
  private static final String ai6 = "select * from application";
  private static final String ai0 = "((employee_id=?) or (employee_id in (select employee_id from bank_employee where supervisor=?)))";
  private static final String ai4 = "insert into application (app_id,bank_id,form_id,product_id,category_id,status_id,tracking_no,first_name,last_name,email_address,ssn,create_date,customer_id,employee_id, affiliate_bank_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String aiU = "select * from form where form_id=?";
  private static final String ajh = "select * from ";
  private static final String ajG = "delete from ";
  private static final String ajx = " where app_id=?";
  private static final String ajt = "insert into ";
  private static final String aiX = "select * from form_columns_i18n where form_id=? and field_id=?";
  private static final String aiW = " and language=?";
  private static final String ai1 = "select * from form_columns where form_id=?";
  private static final String ajb = "select form_name from form where form_id = (select form_id from application where app_id=?)";
  private static final String ajq = "select field_name from form_columns where form_id=?";
  private static final String ajH = "delete from application where app_id=?";
  private static final String aju = "select qm.employee_id from queue q, queue_members qm, bank_employee b where q.queue_id = qm.queue_id and qm.employee_id = b.employee_id and qm.status=0 and q.bank_id = ? and q.product_id = ? and q.status_id = ?";
  private static final String ajr = " and b.employee_status = 0";
  private static final String ajp = " and b.employee_status = 2";
  private static final String aje = " and ( (b.affiliate_bank_id = ? or exists  ( select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?))  or ( b.affiliate_bank_id = 0 and not exists (select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?)) )";
  private static final String ajl = "select q.queue_id,qi.subject,qi.autoreply_text,q.from_id,q.email_address from queue q, queue_i18n qi where q.queue_id=qi.queue_id and q.bank_id=? and q.product_id=? and q.status_id=? and qi.language=? and q.queue_type=1";
  private static final String ajo = "select queue_id,subject,autoreply_text,from_id,email_address from queue where bank_id=? and product_id=? and status_id=? and queue_type=1";
  private static final String ajd = "select count(app_id) from application where employee_id=?";
  private static final String aja = "insert into application_history (app_id,owner_id,modifier_id,status_id,product_id,history_comment,modified_date) values (?,?,?,?,?,?,?)";
  private static final String ai2 = "select count(app_id) from application where app_id != 0 and ((employee_id=?) or (employee_id in (select employee_id from bank_employee where supervisor=?))) ";
  private static final String aji = "select count(app_id) from application a where a.app_id != 0 and ((a.employee_id=?) or (a.employee_id in (select employee_id from bank_employee where supervisor=?))) and a.affiliate_bank_id=?";
  private static final String ajk = "select employee_id,status_id,product_id,bank_id from application where app_id=?";
  private static final String ajI = "update application set status_id=?, employee_id=? where app_id=?";
  private static final String ai7 = "select * from application_history ";
  private static final String ajn = "((owner_id=?) or (owner_id in (select employee_id from bank_employee where supervisor=?)))";
  private static final int ajv = 29;
  
  public static Banks getBanks()
    throws ProfileException
  {
    String str = "ApplicationAdapter.getBanks";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Banks localBanks = new Banks();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from bank");
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from bank");
      while (localResultSet.next())
      {
        Bank localBank = new Bank();
        localBank.setBankID(localResultSet.getString("bank_id"));
        localBanks.add(localBank);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localBanks;
  }
  
  public static Products getProducts(Product paramProduct)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.getProducts";
    Profile.isInitialized();
    if (paramProduct == null) {
      throw new ProfileException(str1, 7030, "The specified product is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = paramProduct.getBankID();
    if ((str2 == null) || (str2.length() == 0)) {
      throw new ProfileException(str1, 3534, "BankID required");
    }
    Products localProducts = new Products();
    localProducts.setLocale(paramProduct.getLocale());
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("select * from product where bank_id=?");
      Profile.appendSQLSelectInt(localStringBuffer, "product_id", paramProduct.getProductID(), true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      Profile.setStatementString(localPreparedStatement, 1, str2, "bank_id", false);
      Profile.setStatementInt(localPreparedStatement, 2, paramProduct.getProductID(), true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        Product localProduct = new Product();
        localProduct.setBankID(Profile.getRSString(localResultSet, "bank_id"));
        localProduct.setProductID(String.valueOf(localResultSet.getInt("product_id")));
        localProduct.setCategoryID(String.valueOf(localResultSet.getInt("category_id")));
        localProduct.setFormID(String.valueOf(localResultSet.getInt("form_id")));
        localProduct.setDescription(Profile.getRSString(localResultSet, "description"));
        localProduct.setTitle(Profile.getRSString(localResultSet, "title"));
        localProducts.add(localProduct);
        jdMethod_for(localConnection, null, localProduct);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localProducts;
  }
  
  private static void jdMethod_for(Connection paramConnection, String paramString, Product paramProduct)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.getProductI18N";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select * from product_i18n where bank_id = ? and product_id=?");
      if ((paramString != null) && (paramString.length() != 0)) {
        localStringBuffer.append(" and language=?");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getBankID(), "bank_id", true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramProduct.getProductID(), true);
      if ((paramString != null) && (paramString.length() != 0)) {
        i = Profile.setStatementString(localPreparedStatement, i, paramString, "language", true);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        String str2 = Profile.getRSString(localResultSet, "language");
        paramProduct.setTitle(str2, Profile.getRSString(localResultSet, "title"));
        paramProduct.setDescription(str2, Profile.getRSString(localResultSet, "description"));
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
  }
  
  public static Product addProduct(Product paramProduct)
    throws ProfileException
  {
    String str = "ApplicationAdapter.addProduct";
    Profile.isInitialized();
    if (paramProduct == null) {
      throw new ProfileException(str, 7034, "The specified product is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into product (bank_id,product_id,category_id,form_id,description,title) values(?,?,?,?,?,?)");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getBankID(), "bank_id", false);
      int j = DBUtil.getNextId(localConnection, Profile.dbType, "product_id");
      paramProduct.setProductID(String.valueOf(j));
      localPreparedStatement.setInt(i++, j);
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramProduct.getCategoryID()));
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramProduct.getFormID()));
      i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getDescription(), "description", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getTitle(), "title", false);
      DBUtil.executeUpdate(localPreparedStatement, "insert into product (bank_id,product_id,category_id,form_id,description,title) values(?,?,?,?,?,?)");
      jdMethod_for(localConnection, paramProduct);
      localConnection.commit();
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return paramProduct;
  }
  
  private static void jdMethod_for(Connection paramConnection, Product paramProduct)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.addProductI18N";
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into product_i18n( bank_id, product_id, language, title, description ) values (?,?,?,?,?)");
      Iterator localIterator = paramProduct.getLanguages();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        int i = 1;
        i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getBankID(), "bank_id", false);
        localPreparedStatement.setInt(i++, Profile.convertToInt(paramProduct.getProductID()));
        i = Profile.setStatementString(localPreparedStatement, i, str2, "language", false);
        i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getTitle(str2), "title", false);
        i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getDescription(str2), "description", false);
        DBUtil.executeUpdate(localPreparedStatement, "insert into product_i18n( bank_id, product_id, language, title, description ) values (?,?,?,?,?)");
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
  }
  
  public static void modifyProduct(Product paramProduct)
    throws ProfileException
  {
    String str = "ApplicationAdapter.modifyProduct";
    Profile.isInitialized();
    if (paramProduct == null) {
      throw new ProfileException(str, 7032, "The specified product is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update product set category_id=?,form_id=?,description=?,title=? where product_id=?");
      int i = 1;
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramProduct.getCategoryID()));
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramProduct.getFormID()));
      i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getDescription(), "description", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getTitle(), "title", false);
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramProduct.getProductID()));
      DBUtil.executeUpdate(localPreparedStatement, "update product set category_id=?,form_id=?,description=?,title=? where product_id=?");
      jdMethod_int(localConnection, paramProduct);
      jdMethod_for(localConnection, paramProduct);
      localConnection.commit();
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void deleteProduct(Product paramProduct)
    throws ProfileException
  {
    String str = "ApplicationAdapter.deleteProduct";
    Profile.isInitialized();
    if (paramProduct == null) {
      throw new ProfileException(str, 7033, "The specified product is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      int i = Profile.convertToInt(paramProduct.getProductID());
      MessageQueueAdapter.deleteQueueTX(localConnection, i, 0);
      jdMethod_int(localConnection, paramProduct);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from product where product_id=?");
      localPreparedStatement.setInt(1, i);
      DBUtil.executeUpdate(localPreparedStatement, "delete from product where product_id=?");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  private static void jdMethod_int(Connection paramConnection, Product paramProduct)
    throws ProfileException
  {
    String str = "ApplicationAdapter.deleteProductI18N";
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from product_i18n where bank_id=? and product_id=?");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramProduct.getBankID(), "bank_id", false);
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramProduct.getProductID()));
      DBUtil.executeUpdate(localPreparedStatement, "delete from product_i18n where bank_id=? and product_id=?");
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
  
  public static Statuses getProductStatuses(String paramString)
    throws ProfileException
  {
    String str = "ApplicationAdapter.getProductStatuses";
    Profile.isInitialized();
    if (paramString == null) {
      throw new ProfileException(str, 7034, "The specfied bank ID is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Statuses localStatuses = new Statuses();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select s.name,s.status_id,p.product_id from status s, product_status p where s.status_id = p.status_id and s.bank_id = p.bank_id and s.bank_id = ? order by p.product_id, s.status_id");
      Profile.setStatementString(localPreparedStatement, 1, paramString, "bank_id", false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select s.name,s.status_id,p.product_id from status s, product_status p where s.status_id = p.status_id and s.bank_id = p.bank_id and s.bank_id = ? order by p.product_id, s.status_id");
      while (localResultSet.next())
      {
        Status localStatus = new Status();
        localStatus.setID(String.valueOf(localResultSet.getInt("status_id")));
        localStatus.setProductID(localResultSet.getString("product_id"));
        localStatus.setBankID(paramString);
        localStatus.setName(Profile.getRSString(localResultSet, "name"));
        localStatuses.add(localStatus);
        jdMethod_for(localConnection, null, localStatus);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localStatuses;
  }
  
  public static Status addProductStatus(Status paramStatus)
    throws ProfileException
  {
    String str = "ApplicationAdapter.addProductStatus";
    Profile.isInitialized();
    if (paramStatus == null) {
      throw new ProfileException(str, 7052, "The specified status is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into status (status_id,bank_id,name) values (?,?,?)");
      int i = 1;
      int j = DBUtil.getNextId(localConnection, Profile.dbType, "status_id");
      paramStatus.setID(String.valueOf(j));
      localPreparedStatement.setInt(i++, j);
      i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getBankID(), "bank_id", false);
      i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getName(), "name", false);
      DBUtil.executeUpdate(localPreparedStatement, "insert into status (status_id,bank_id,name) values (?,?,?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      jdMethod_for(localConnection, paramStatus);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into product_status (product_id,status_id,bank_id) values (?,?,?)");
      i = 1;
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramStatus.getProductID()));
      localPreparedStatement.setInt(i++, j);
      i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getBankID(), "bank_id", false);
      DBUtil.executeUpdate(localPreparedStatement, "insert into product_status (product_id,status_id,bank_id) values (?,?,?)");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    return paramStatus;
  }
  
  private static void jdMethod_for(Connection paramConnection, Status paramStatus)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.addStatusI18N";
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into status_i18n (bank_id,status_id,language,name) values (?,?,?,?)");
      Iterator localIterator = paramStatus.getLanguages();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        int i = 1;
        i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getBankID(), "bank_id", false);
        localPreparedStatement.setInt(i++, Profile.convertToInt(paramStatus.getID()));
        i = Profile.setStatementString(localPreparedStatement, i, str2, "language", false);
        i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getName(str2), "name", false);
        DBUtil.executeUpdate(localPreparedStatement, "insert into status_i18n (bank_id,status_id,language,name) values (?,?,?,?)");
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
  }
  
  public static Statuses getStatus(Status paramStatus)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.getStatus";
    Profile.isInitialized();
    if (paramStatus == null) {
      throw new ProfileException(str1, 7050, "The specified status is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str2 = paramStatus.getBankID();
    if ((str2 == null) || (str2.length() == 0)) {
      throw new ProfileException(str1, 3534, "BankID required");
    }
    Statuses localStatuses = new Statuses();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer = new StringBuffer("select * from status where bank_id=?");
      Profile.appendSQLSelectInt(localStringBuffer, "status_id", paramStatus.getID(), true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      Profile.setStatementString(localPreparedStatement, 1, str2, "bank_id", false);
      Profile.setStatementInt(localPreparedStatement, 2, paramStatus.getID(), true);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        Status localStatus = new Status();
        localStatus.setBankID(Profile.getRSString(localResultSet, "bank_id"));
        localStatus.setID(String.valueOf(localResultSet.getInt("status_id")));
        localStatus.setName(Profile.getRSString(localResultSet, "name"));
        localStatuses.add(localStatus);
        jdMethod_for(localConnection, paramStatus.getSearchLanguage(), localStatus);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localStatuses;
  }
  
  private static void jdMethod_for(Connection paramConnection, String paramString, Status paramStatus)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.getStatusI18N";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select * from status_i18n where bank_id = ? and status_id=?");
      if ((paramString != null) && (paramString.length() != 0)) {
        localStringBuffer.append(" and language=?");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getBankID(), "bank_id", false);
      i = Profile.setStatementInt(localPreparedStatement, i, paramStatus.getID(), true);
      if ((paramString != null) && (paramString.length() != 0)) {
        i = Profile.setStatementString(localPreparedStatement, i, paramString, "language", true);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        String str2 = Profile.getRSString(localResultSet, "language");
        paramStatus.setName(str2, Profile.getRSString(localResultSet, "name"));
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
  }
  
  public static void modifyStatus(Status paramStatus)
    throws ProfileException
  {
    String str = "ApplicationAdapter.modifyStatus";
    Profile.isInitialized();
    if (paramStatus == null) {
      throw new ProfileException(str, 7054, "The specified status is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update status set name=? where status_id=?");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getName(), "name", false);
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramStatus.getID()));
      DBUtil.executeUpdate(localPreparedStatement, "update status set name=? where status_id=?");
      jdMethod_int(localConnection, paramStatus);
      jdMethod_for(localConnection, paramStatus);
      localConnection.commit();
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public static void deleteStatus(Status paramStatus)
    throws ProfileException
  {
    String str = "ApplicationAdapter.deleteStatus";
    Profile.isInitialized();
    if (paramStatus == null) {
      throw new ProfileException(str, 7051, "The specified status is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from product_status where bank_id=? and product_id=? and status_id=?");
      int i = 1;
      int j = Profile.convertToInt(paramStatus.getID());
      i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getBankID(), "bank_id", false);
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramStatus.getProductID()));
      localPreparedStatement.setInt(i++, j);
      DBUtil.executeUpdate(localPreparedStatement, "delete from product_status where bank_id=? and product_id=? and status_id=?");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      MessageQueueAdapter.deleteQueueTX(localConnection, 0, j);
      jdMethod_int(localConnection, paramStatus);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from status where status_id=?");
      localPreparedStatement.setInt(1, Profile.convertToInt(paramStatus.getID()));
      DBUtil.executeUpdate(localPreparedStatement, "delete from status where status_id=?");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
  }
  
  private static void jdMethod_int(Connection paramConnection, Status paramStatus)
    throws ProfileException
  {
    String str = "ApplicationAdapter.deleteStatusI18N";
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from status_i18n where bank_id=? and status_id=?");
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramStatus.getBankID(), "bank_id", false);
      localPreparedStatement.setInt(i++, Profile.convertToInt(paramStatus.getID()));
      DBUtil.executeUpdate(localPreparedStatement, "delete from status_i18n where bank_id=? and status_id=?");
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
  
  public static Categories getCategories(String paramString)
    throws ProfileException
  {
    String str = "ApplicationAdapter.getCategories";
    Profile.isInitialized();
    if (paramString == null) {
      throw new ProfileException(str, 7022, "The specified bank Id is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Categories localCategories = new Categories();
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from category where bank_id=?");
      Profile.setStatementString(localPreparedStatement, 1, paramString, "bank_id", false);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from category where bank_id=?");
      while (localResultSet.next())
      {
        Category localCategory = new Category();
        localCategory.setID(String.valueOf(localResultSet.getInt("category_id")));
        localCategory.setBankID(Profile.getRSString(localResultSet, "bank_id"));
        localCategory.setName(Profile.getRSString(localResultSet, "name"));
        localCategories.add(localCategory);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localCategories;
  }
  
  public static Form getFormById(Form paramForm)
    throws ProfileException
  {
    String str = "ApplicationAdapter.getFormById";
    Profile.isInitialized();
    if (paramForm == null) {
      throw new ProfileException(str, 7060, "The specified form is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from form where form_id=?");
      localPreparedStatement.setInt(1, Profile.convertToInt(paramForm.getID()));
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from form where form_id=?");
      if (localResultSet.next())
      {
        paramForm.setName(localResultSet.getString("form_name"));
        jdMethod_for(localConnection, paramForm, paramForm.getSearchLanguage());
      }
      else
      {
        paramForm = null;
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return paramForm;
  }
  
  public static Application getApplicationById(int paramInt)
    throws ProfileException
  {
    return getApplicationById(paramInt, "en_US", Locale.getDefault(), new HashMap());
  }
  
  public static Application getApplicationById(int paramInt, String paramString, Locale paramLocale, HashMap paramHashMap)
    throws ProfileException
  {
    String str = "ApplicationAdapter.getApplicationById";
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Application localApplication = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from application where app_id=?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from application where app_id=?");
      if (localResultSet.next())
      {
        localApplication = jdMethod_int(localResultSet, paramLocale);
        jdMethod_for(localConnection, localApplication.getForm(), paramInt, localResultSet.getInt("form_id"), paramString);
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localApplication;
  }
  
  public static Applications getApplicationList(Application paramApplication, String paramString1, String paramString2, String paramString3, String paramString4)
    throws ProfileException
  {
    String str = "ApplicationAdapter.getApplicationList";
    Profile.isInitialized();
    if (paramApplication == null) {
      throw new ProfileException(str, 7006, "The specified application is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    StringBuffer localStringBuffer = new StringBuffer("select * from application");
    Applications localApplications = new Applications(paramApplication.getLocale());
    boolean bool = false;
    try
    {
      bool = Profile.appendSQLSelectString(localStringBuffer, "tracking_no", paramApplication.getTrackingNumber(), bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "first_name", paramApplication.getFirstName(), true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "last_name", paramApplication.getLastName(), true, bool);
      bool = Profile.appendSQLSelectString(localStringBuffer, "ssn", paramApplication.getSsn(), true, bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "product_id", paramApplication.getProductID(), bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "status_id", paramApplication.getStatusID(), bool);
      if (paramApplication.getAffiliateBankID() > 0) {
        bool = Profile.appendSQLSelectInt(localStringBuffer, "affiliate_bank_id", paramApplication.getAffiliateBankID(), bool);
      }
      Date localDate1 = Profile.convertToDate(paramString1);
      Date localDate2 = Profile.getNextDay(Profile.convertToDate(paramString2));
      bool = Profile.appendSQLSelectDate(localStringBuffer, "create_date", localDate1, ">=", bool);
      bool = Profile.appendSQLSelectDate(localStringBuffer, "create_date", localDate2, "<=", bool);
      int i = Profile.convertToInt(paramApplication.getOwner());
      if (Profile.isValidId(i))
      {
        if (!bool) {
          localStringBuffer.append(" where ");
        } else {
          localStringBuffer.append(" and ");
        }
        localStringBuffer.append("((employee_id=?) or (employee_id in (select employee_id from bank_employee where supervisor=?)))");
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int j = 1;
      j = Profile.setStatementString(localPreparedStatement, j, paramApplication.getTrackingNumber(), "tracking_no", true);
      j = Profile.setStatementString(localPreparedStatement, j, paramApplication.getFirstName(), "first_name", true);
      j = Profile.setStatementString(localPreparedStatement, j, paramApplication.getLastName(), "last_name", true);
      j = Profile.setStatementString(localPreparedStatement, j, paramApplication.getSsn(), "ssn", true);
      j = Profile.setStatementInt(localPreparedStatement, j, paramApplication.getProductID(), true);
      j = Profile.setStatementInt(localPreparedStatement, j, paramApplication.getStatusID(), true);
      if (paramApplication.getAffiliateBankID() > 0) {
        j = Profile.setStatementInt(localPreparedStatement, j, paramApplication.getAffiliateBankID(), true);
      }
      j = Profile.setStatementDate(localPreparedStatement, j, localDate1, true);
      j = Profile.setStatementDate(localPreparedStatement, j, localDate2, true);
      if (Profile.isValidId(i))
      {
        j = Profile.setStatementInt(localPreparedStatement, j, i, true);
        j = Profile.setStatementInt(localPreparedStatement, j, i, true);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      jdMethod_for(localResultSet, Profile.convertToInt(paramString3));
      int k = Profile.convertToInt(paramString4);
      int m = 0;
      while ((localResultSet.next()) && ((k == 0) || (m++ <= k))) {
        localApplications.add(jdMethod_int(localResultSet, paramApplication.getLocale()));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localApplications;
  }
  
  private static Application jdMethod_int(ResultSet paramResultSet, Locale paramLocale)
    throws Exception
  {
    Application localApplication = new Application(paramLocale);
    localApplication.setAppID(String.valueOf(paramResultSet.getInt("app_id")));
    localApplication.setBankID(Profile.getRSString(paramResultSet, "bank_id"));
    localApplication.setProductID(String.valueOf(paramResultSet.getInt("product_id")));
    localApplication.setCategoryID(String.valueOf(paramResultSet.getInt("category_id")));
    localApplication.setStatusID(String.valueOf(paramResultSet.getInt("status_id")));
    localApplication.setTrackingNumber(Profile.getRSString(paramResultSet, "tracking_no"));
    localApplication.setFirstName(Profile.getRSString(paramResultSet, "first_name"));
    localApplication.setLastName(Profile.getRSString(paramResultSet, "last_name"));
    localApplication.setEmailAddress(Profile.getRSString(paramResultSet, "email_address"));
    localApplication.setSsn(Profile.getRSString(paramResultSet, "ssn"));
    localApplication.setCreateDate(ProfileUtil.getDateTime(paramResultSet.getTimestamp("create_date")));
    localApplication.setCustomerID(String.valueOf(paramResultSet.getInt("customer_id")));
    localApplication.setOwner(String.valueOf(paramResultSet.getInt("employee_id")));
    localApplication.setAffiliateBankID(paramResultSet.getInt("affiliate_bank_id"));
    if ((localApplication.getCustomerID() != null) && (localApplication.getCustomerID().length() > 0)) {
      try
      {
        User localUser = CustomerAdapter.getUserById(Integer.parseInt(localApplication.getCustomerID()));
        localApplication.setUserName(localUser.getUserName());
      }
      catch (Exception localException) {}
    }
    return localApplication;
  }
  
  public static Application addApplication(Application paramApplication, String paramString)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.addApplication";
    Profile.isInitialized();
    if (paramApplication == null) {
      Profile.handleError(str1, "invalid application", 5);
    }
    int i = Profile.convertToInt(paramApplication.getProductID());
    int j = Profile.convertToInt(paramApplication.getForm().getID());
    int k = Profile.convertToInt(paramApplication.getCategoryID());
    String str2 = paramApplication.getBankID();
    if ((!Profile.isValidId(i)) || (!Profile.isValidId(j)) || (!Profile.isValidId(k)) || (str2 == null) || (str2.length() == 0)) {
      Profile.handleError(str1, "invalid application", 4);
    }
    int m = Profile.convertToInt(paramApplication.getStatusID());
    if (m <= 0)
    {
      m = 1;
      paramApplication.setStatusID(String.valueOf(m));
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into application (app_id,bank_id,form_id,product_id,category_id,status_id,tracking_no,first_name,last_name,email_address,ssn,create_date,customer_id,employee_id, affiliate_bank_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      int n = DBUtil.getNextId(localConnection, Profile.dbType, "app_id");
      paramApplication.setAppID(String.valueOf(n));
      String str3 = jdMethod_int(paramApplication);
      paramApplication.setTrackingNumber(str3);
      int i1 = jdMethod_for(localConnection, str2, i, m, paramApplication.getAffiliateBankID());
      paramApplication.setOwner(String.valueOf(i1));
      int i2 = 1;
      localPreparedStatement.setInt(i2++, n);
      i2 = Profile.setStatementString(localPreparedStatement, i2, str2, "bank_id", false);
      localPreparedStatement.setInt(i2++, j);
      localPreparedStatement.setInt(i2++, i);
      localPreparedStatement.setInt(i2++, k);
      localPreparedStatement.setInt(i2++, m);
      i2 = Profile.setStatementString(localPreparedStatement, i2, str3, "tracking_no", false);
      i2 = Profile.setStatementString(localPreparedStatement, i2, paramApplication.getFirstName(), "first_name", false);
      i2 = Profile.setStatementString(localPreparedStatement, i2, paramApplication.getLastName(), "last_name", false);
      i2 = Profile.setStatementString(localPreparedStatement, i2, paramApplication.getEmailAddress(), "email_address", false);
      i2 = Profile.setStatementString(localPreparedStatement, i2, paramApplication.getSsn(), "ssn", false);
      localPreparedStatement.setTimestamp(i2++, DBUtil.getCurrentTimestamp());
      localPreparedStatement.setInt(i2++, Profile.convertToInt(paramApplication.getCustomerID()));
      localPreparedStatement.setInt(i2++, i1);
      localPreparedStatement.setInt(i2++, paramApplication.getAffiliateBankID());
      DBUtil.executeUpdate(localPreparedStatement, "insert into application (app_id,bank_id,form_id,product_id,category_id,status_id,tracking_no,first_name,last_name,email_address,ssn,create_date,customer_id,employee_id, affiliate_bank_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      jdMethod_for(localConnection, paramApplication);
      jdMethod_for(localConnection, n, i1, 1, i, m, paramString);
      DBUtil.commit(localConnection);
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException1, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement);
    }
    try
    {
      jdMethod_for(paramApplication);
    }
    catch (Exception localException2)
    {
      DebugLog.log(Level.INFO, "An application submission notification could not be sent. " + localException2.getMessage());
    }
    return paramApplication;
  }
  
  public static void deleteApplication(int paramInt)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.deleteApplication";
    if (!Profile.isValidId(paramInt)) {
      Profile.handleError(str1, "Invalid application ID", 4);
    }
    Profile.isInitialized();
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select form_name from form where form_id = (select form_id from application where app_id=?)");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select form_name from form where form_id = (select form_id from application where app_id=?)");
      if (localResultSet.next())
      {
        String str2 = localResultSet.getString("form_name");
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localResultSet = null;
        localPreparedStatement = null;
        StringBuffer localStringBuffer = new StringBuffer("delete from ").append(str2).append(" where app_id=?");
        localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
        localPreparedStatement.setInt(1, paramInt);
        DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from application where app_id=?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from application where app_id=?");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      Profile.handleError(localException, str1);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public static int getApplicationCount(Application paramApplication, String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "ApplicationAdapter.getApplicationCount";
    int i = 0;
    Profile.isInitialized();
    if (paramApplication == null) {
      throw new ProfileException(str, 7006, "The specified application is null");
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    if (!Profile.isValidId(paramApplication.getOwner())) {
      Profile.handleError(str, "Invalid BankEmployee ID", 4);
    }
    int j = Profile.convertToInt(paramApplication.getOwner());
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      StringBuffer localStringBuffer;
      if (paramApplication.getAffiliateBankID() > 0) {
        localStringBuffer = new StringBuffer("select count(app_id) from application a where a.app_id != 0 and ((a.employee_id=?) or (a.employee_id in (select employee_id from bank_employee where supervisor=?))) and a.affiliate_bank_id=?");
      } else {
        localStringBuffer = new StringBuffer("select count(app_id) from application where app_id != 0 and ((employee_id=?) or (employee_id in (select employee_id from bank_employee where supervisor=?))) ");
      }
      Date localDate1 = Profile.convertToDate(paramString1);
      Date localDate2 = Profile.getNextDay(Profile.convertToDate(paramString2));
      Profile.appendSQLSelectDate(localStringBuffer, "create_date", localDate1, ">=", true);
      Profile.appendSQLSelectDate(localStringBuffer, "create_date", localDate2, "<=", true);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int k = 1;
      localPreparedStatement.setInt(k++, j);
      localPreparedStatement.setInt(k++, j);
      if (paramApplication.getAffiliateBankID() > 0) {
        localPreparedStatement.setInt(k++, paramApplication.getAffiliateBankID());
      }
      k = Profile.setStatementDate(localPreparedStatement, k, localDate1, true);
      k = Profile.setStatementDate(localPreparedStatement, k, localDate2, true);
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
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  public static Applications modifyApplications(ArrayList paramArrayList, int paramInt1, int paramInt2, String paramString, int paramInt3)
    throws ProfileException
  {
    String str = "ApplicationAdapter.modifyApplications";
    Profile.isInitialized();
    if (paramArrayList == null) {
      throw new ProfileException(str, 7014, "The list containing the application Ids is null");
    }
    Applications localApplications = new Applications(Locale.getDefault());
    Application localApplication = new Application(Locale.getDefault());
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      int i = paramArrayList.size();
      for (int j = 0; j < i; j++)
      {
        localApplication.setOwner(String.valueOf(paramInt1));
        localApplication.setAppID((String)paramArrayList.get(j));
        localApplication.setStatusID(String.valueOf(paramInt2));
        localApplications.add(jdMethod_for(localConnection, localApplication, paramString, paramInt3));
      }
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
    return localApplications;
  }
  
  public static Application modifyApplication(Application paramApplication, String paramString, int paramInt)
    throws ProfileException
  {
    String str = "ApplicationAdapter.modifyApplication";
    Profile.isInitialized();
    if (paramApplication == null) {
      throw new ProfileException(str, 7004, "The specified application is null");
    }
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      jdMethod_for(localConnection, paramApplication, paramString, paramInt);
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
    return paramApplication;
  }
  
  private static Application jdMethod_for(Connection paramConnection, Application paramApplication, String paramString, int paramInt)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i;
    int j;
    try
    {
      int k = Profile.convertToInt(paramApplication.getAppID());
      int m = Profile.convertToInt(paramApplication.getOwner());
      i = Profile.convertToInt(paramApplication.getStatusID());
      if (!Profile.isValidId(k)) {
        throw new ProfileException(7094);
      }
      if (!Profile.isValidId(i)) {
        throw new ProfileException(7095);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select employee_id,status_id,product_id,bank_id from application where app_id=?");
      localPreparedStatement.setInt(1, k);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select employee_id,status_id,product_id,bank_id from application where app_id=?");
      int n = 0;
      int i1 = 0;
      j = 0;
      String str = null;
      if (localResultSet.next())
      {
        n = localResultSet.getInt("employee_id");
        j = localResultSet.getInt("status_id");
        i1 = localResultSet.getInt("product_id");
        str = localResultSet.getString("bank_id");
      }
      else
      {
        throw new ProfileException(7094);
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from status where bank_id=? and status_id=?");
      localPreparedStatement.setString(1, str);
      localPreparedStatement.setInt(2, i);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from status where bank_id=? and status_id=?");
      if (!localResultSet.next()) {
        throw new ProfileException(7095);
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      if ((i != j) && (n == m)) {
        m = jdMethod_for(paramConnection, str, i1, i, paramApplication.getAffiliateBankID());
      }
      paramApplication.setOwner(String.valueOf(m));
      paramApplication.setStatusID(String.valueOf(i));
      jdMethod_for(paramConnection, k, m, paramInt, i1, i, paramString);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "update application set status_id=?, employee_id=? where app_id=?");
      localPreparedStatement.setInt(1, i);
      localPreparedStatement.setInt(2, m);
      localPreparedStatement.setInt(3, k);
      DBUtil.executeUpdate(localPreparedStatement, "update application set status_id=?, employee_id=? where app_id=?");
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    if (i != j) {
      try
      {
        jdMethod_for(paramApplication);
      }
      catch (Exception localException)
      {
        DebugLog.log(Level.INFO, "An application submission notification could not be sent. " + localException.getMessage());
      }
    }
    return paramApplication;
  }
  
  public static ApplicationHistories getApplicationHistories(ApplicationHistory paramApplicationHistory, String paramString1, String paramString2)
    throws ProfileException
  {
    String str = "ApplicationAdapter.getApplicationHistories";
    Profile.isInitialized();
    if (paramApplicationHistory == null) {
      throw new ProfileException(str, 7090, "The specified application history is null");
    }
    if (!Profile.isValidId(paramApplicationHistory.getAppID())) {
      Profile.handleError(str, "Invalid application ID", 4);
    }
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    boolean bool = false;
    StringBuffer localStringBuffer = new StringBuffer("select * from application_history ");
    ApplicationHistories localApplicationHistories = new ApplicationHistories(paramApplicationHistory.getLocale());
    try
    {
      int i = Profile.convertToInt(paramApplicationHistory.getAppID());
      int j = Profile.convertToInt(paramApplicationHistory.getStatusID());
      int k = Profile.convertToInt(paramApplicationHistory.getProductID());
      int m = Profile.convertToInt(paramApplicationHistory.getModifierID());
      int n = Profile.convertToInt(paramApplicationHistory.getOwnerID());
      bool = Profile.appendSQLSelectInt(localStringBuffer, "app_id", i, bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "status_id", j, bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "product_id", k, bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "modifier_id", m, bool);
      Date localDate1 = Profile.convertToDate(paramString1);
      Date localDate2 = Profile.getNextDay(Profile.convertToDate(paramString2));
      bool = Profile.appendSQLSelectDate(localStringBuffer, "modified_date", localDate1, ">=", bool);
      bool = Profile.appendSQLSelectDate(localStringBuffer, "modified_date", localDate2, "<=", bool);
      if (Profile.isValidId(n))
      {
        if (!bool) {
          localStringBuffer.append(" where ");
        } else {
          localStringBuffer.append(" and ");
        }
        localStringBuffer.append("((owner_id=?) or (owner_id in (select employee_id from bank_employee where supervisor=?)))");
      }
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      int i1 = 1;
      i1 = Profile.setStatementInt(localPreparedStatement, i1, i, true);
      i1 = Profile.setStatementInt(localPreparedStatement, i1, j, true);
      i1 = Profile.setStatementInt(localPreparedStatement, i1, k, true);
      i1 = Profile.setStatementInt(localPreparedStatement, i1, m, true);
      i1 = Profile.setStatementDate(localPreparedStatement, i1, localDate1, true);
      i1 = Profile.setStatementDate(localPreparedStatement, i1, localDate2, true);
      if (Profile.isValidId(n))
      {
        i1 = Profile.setStatementInt(localPreparedStatement, i1, n, true);
        i1 = Profile.setStatementInt(localPreparedStatement, i1, n, true);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        ApplicationHistory localApplicationHistory = localApplicationHistories.add();
        localApplicationHistory.setAppID(localResultSet.getString("app_id"));
        localApplicationHistory.setProductID(localResultSet.getString("product_id"));
        localApplicationHistory.setStatusID(localResultSet.getString("status_id"));
        localApplicationHistory.setOwnerID(localResultSet.getString("owner_id"));
        localApplicationHistory.setModifierID(localResultSet.getString("modifier_id"));
        localApplicationHistory.setHistoryComment(localResultSet.getString("history_comment"));
        localApplicationHistory.setModifiedDate(new DateTime(localResultSet.getTimestamp("modified_date"), paramApplicationHistory.getLocale()));
      }
    }
    catch (Exception localException)
    {
      Profile.handleError(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localApplicationHistories;
  }
  
  private static String jdMethod_int(Application paramApplication)
  {
    int i = Profile.convertToInt(paramApplication.getProductID());
    int j = Profile.convertToInt(paramApplication.getCategoryID());
    int k = Profile.convertToInt(paramApplication.getForm().getID());
    int m = Profile.convertToInt(paramApplication.getAppID());
    int n = (m + j + i + k) * 29;
    String str1 = paramApplication.getFirstName();
    String str2 = paramApplication.getLastName();
    StringBuffer localStringBuffer = new StringBuffer();
    if ((str2 != null) && (str2.length() > 2)) {
      localStringBuffer.append(str2.substring(0, 2));
    }
    localStringBuffer.append(n);
    if ((str1 != null) && (str1.length() > 2)) {
      localStringBuffer.append(str1.substring(0, 2));
    }
    return localStringBuffer.toString();
  }
  
  private static int jdMethod_for(Connection paramConnection, String paramString, int paramInt1, int paramInt2, int paramInt3)
    throws Exception
  {
    String str = "ApplicationAdapter.routeApplication";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    int i;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select qm.employee_id from queue q, queue_members qm, bank_employee b where q.queue_id = qm.queue_id and qm.employee_id = b.employee_id and qm.status=0 and q.bank_id = ? and q.product_id = ? and q.status_id = ? and b.employee_status = 0");
      if (paramInt3 > 0) {
        localStringBuffer.append(" and ( (b.affiliate_bank_id = ? or exists  ( select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?))  or ( b.affiliate_bank_id = 0 and not exists (select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?)) )");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      Profile.setStatementString(localPreparedStatement, 1, paramString, "bank_id", false);
      localPreparedStatement.setInt(2, paramInt1);
      localPreparedStatement.setInt(3, paramInt2);
      if (paramInt3 > 0)
      {
        localPreparedStatement.setInt(4, paramInt3);
        localPreparedStatement.setInt(5, paramInt3);
        localPreparedStatement.setInt(6, paramInt3);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      i = messageAdapter.getAvailableEmployeeId(paramConnection, localResultSet, "select count(app_id) from application where employee_id=?");
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      if (i == 0)
      {
        localStringBuffer = new StringBuffer("select qm.employee_id from queue q, queue_members qm, bank_employee b where q.queue_id = qm.queue_id and qm.employee_id = b.employee_id and qm.status=0 and q.bank_id = ? and q.product_id = ? and q.status_id = ? and b.employee_status = 2");
        if (paramInt3 > 0) {
          localStringBuffer.append(" and ( (b.affiliate_bank_id = ? or exists  ( select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?))  or ( b.affiliate_bank_id = 0 and not exists (select afas.AFFILIATE_BANK_ID from AFFILIATE_ASSIGN afas where afas.EMPLOYEE_ID=b.employee_id and afas.AFFILIATE_BANK_ID=?)) )");
        }
        localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
        Profile.setStatementString(localPreparedStatement, 1, paramString, "bank_id", false);
        localPreparedStatement.setInt(2, paramInt1);
        localPreparedStatement.setInt(3, paramInt2);
        if (paramInt3 > 0)
        {
          localPreparedStatement.setInt(4, paramInt3);
          localPreparedStatement.setInt(5, paramInt3);
          localPreparedStatement.setInt(6, paramInt3);
        }
        localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
        i = messageAdapter.getAvailableEmployeeId(paramConnection, localResultSet, "select count(app_id) from application where employee_id=?");
      }
      if (i == 0) {
        throw new ProfileException(str, 7092, "No bank employees have been assigned to process this application type");
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  private static void jdMethod_for(Connection paramConnection, Application paramApplication)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str1 = null;
    int i = Profile.convertToInt(paramApplication.getAppID());
    int j = Profile.convertToInt(paramApplication.getForm().getID());
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from form where form_id=?");
      localPreparedStatement.setInt(1, j);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from form where form_id=?");
      if (localResultSet.next()) {
        str1 = localResultSet.getString("form_name");
      } else {
        throw new Exception("addDynamicAppData: Invalid form Id in application");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      ArrayList localArrayList = new ArrayList();
      StringBuffer localStringBuffer = new StringBuffer("insert into ").append(str1).append(" (bank_id,app_id");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select field_name from form_columns where form_id=?");
      localPreparedStatement.setInt(1, j);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select field_name from form_columns where form_id=?");
      while (localResultSet.next())
      {
        String str2 = localResultSet.getString("field_name");
        localStringBuffer.append("," + str2);
        localArrayList.add(str2);
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localPreparedStatement = null;
      localResultSet = null;
      localStringBuffer.append(")values(?,?");
      for (int k = 0; k < localArrayList.size(); k++) {
        localStringBuffer.append(",?");
      }
      localStringBuffer.append(")");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      k = 1;
      localPreparedStatement.setString(k++, paramApplication.getBankID());
      localPreparedStatement.setInt(k++, i);
      for (int m = 0; m < localArrayList.size(); m++)
      {
        String str3 = (String)paramApplication.getFieldValues().get(((String)localArrayList.get(m)).toUpperCase());
        if ((str3 == null) || (str3.length() == 0)) {
          str3 = (String)paramApplication.getFieldValues().get(localArrayList.get(m));
        }
        localPreparedStatement.setString(k++, str3);
      }
      DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, Form paramForm, int paramInt1, int paramInt2, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    String str = null;
    try
    {
      paramForm.setID(String.valueOf(paramInt2));
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from form where form_id=?");
      localPreparedStatement.setInt(1, paramInt2);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from form where form_id=?");
      if (localResultSet.next()) {
        str = localResultSet.getString("form_name");
      } else {
        throw new Exception("getDynamicAppData: Invalid form Id in application");
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      localResultSet = null;
      localPreparedStatement = null;
      StringBuffer localStringBuffer = new StringBuffer("select * from ").append(str).append(" where app_id=?");
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, paramInt1);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      if (localResultSet.next())
      {
        jdMethod_for(paramConnection, paramForm, paramString);
        jdMethod_for(paramForm.getFormFields(), localResultSet);
      }
      else
      {
        throw new Exception("getDynamicAppData: No dynamic application table found");
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, Form paramForm, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    FormFields localFormFields = new FormFields();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select * from form_columns where form_id=?");
      localPreparedStatement.setInt(1, Profile.convertToInt(paramForm.getID()));
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from form_columns where form_id=?");
      while (localResultSet.next())
      {
        FormField localFormField = new FormField();
        localFormField.setFormID(String.valueOf(localResultSet.getInt("form_id")));
        localFormField.setFieldID(String.valueOf(localResultSet.getInt("field_id")));
        localFormField.setDisplayName(Profile.getRSString(localResultSet, "display_name"));
        localFormField.setFieldName(Profile.getRSString(localResultSet, "field_name"));
        localFormField.setFieldType(Profile.getRSString(localResultSet, "field_type"));
        localFormField.setMinValue(Profile.getRSString(localResultSet, "min_value"));
        localFormField.setMaxValue(Profile.getRSString(localResultSet, "max_value"));
        localFormField.setRequired(Profile.getRSString(localResultSet, "required"));
        localFormField.setFieldNumber(Profile.getRSString(localResultSet, "field_number"));
        localFormField.setErrorString(Profile.getRSString(localResultSet, "error_string"));
        localFormField.setDependField(Profile.getRSString(localResultSet, "depend_field"));
        localFormField.setDependValue(Profile.getRSString(localResultSet, "depend_value"));
        localFormField.setExactValue(Profile.getRSString(localResultSet, "exact_value"));
        localFormField.setControlType(Profile.getRSString(localResultSet, "control_type"));
        localFormFields.add(localFormField);
        jdMethod_for(paramConnection, paramString, localFormField);
      }
      paramForm.setFormFields(localFormFields);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, String paramString, FormField paramFormField)
    throws ProfileException
  {
    String str1 = "ApplicationAdapter.getFormFieldI18N";
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer("select * from form_columns_i18n where form_id=? and field_id=?");
      if ((paramString != null) && (paramString.length() != 0)) {
        localStringBuffer.append(" and language=?");
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
      localPreparedStatement.setInt(1, Profile.convertToInt(paramFormField.getFormID()));
      localPreparedStatement.setInt(2, Profile.convertToInt(paramFormField.getFieldID()));
      if ((paramString != null) && (paramString.length() != 0)) {
        localPreparedStatement.setString(3, paramString);
      }
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      while (localResultSet.next())
      {
        String str2 = Profile.getRSString(localResultSet, "language");
        paramFormField.setErrorString(str2, Profile.getRSString(localResultSet, "error_string"));
        paramFormField.setDisplayName(str2, Profile.getRSString(localResultSet, "display_name"));
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
  }
  
  private static void jdMethod_for(FormFields paramFormFields, ResultSet paramResultSet)
    throws Exception
  {
    ResultSetMetaData localResultSetMetaData = paramResultSet.getMetaData();
    int i = localResultSetMetaData.getColumnCount();
    for (int j = 1; j <= i; j++)
    {
      String str1 = localResultSetMetaData.getColumnName(j);
      String str2 = paramResultSet.getString(j);
      FormField localFormField = paramFormFields.getByFieldName(str1);
      if (localFormField != null) {
        localFormField.setFieldValue(str2);
      }
    }
  }
  
  private static void jdMethod_for(Application paramApplication)
    throws Exception
  {
    String str1 = paramApplication.getBankID();
    int i = Profile.convertToInt(paramApplication.getProductID());
    int j = Profile.convertToInt(paramApplication.getStatusID());
    String str2 = null;
    User localUser = null;
    int k = 1;
    String str3 = paramApplication.getCustomerID();
    if ((str3 == null) || (str3.trim().length() == 0)) {
      k = 0;
    }
    int m = Profile.convertToInt(str3);
    if (m == 0) {
      k = 0;
    }
    if (k != 0) {
      localUser = CustomerAdapter.getUserById(m);
    }
    if (localUser != null) {
      str2 = localUser.getPreferredLanguage();
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, false, 2);
      String str4 = null;
      if ((str2 != null) && (!str2.equals("en_US"))) {
        str4 = "select q.queue_id,qi.subject,qi.autoreply_text,q.from_id,q.email_address from queue q, queue_i18n qi where q.queue_id=qi.queue_id and q.bank_id=? and q.product_id=? and q.status_id=? and qi.language=? and q.queue_type=1";
      } else {
        str4 = "select queue_id,subject,autoreply_text,from_id,email_address from queue where bank_id=? and product_id=? and status_id=? and queue_type=1";
      }
      localPreparedStatement = DBUtil.prepareStatement(localConnection, str4);
      Profile.setStatementString(localPreparedStatement, 1, str1, "bank_id", false);
      localPreparedStatement.setInt(2, i);
      localPreparedStatement.setInt(3, j);
      if ((str2 != null) && (!str2.equals("en_US"))) {
        localPreparedStatement.setString(4, str2);
      }
      String str5 = null;
      String str6 = null;
      String str7 = null;
      localResultSet = DBUtil.executeQuery(localPreparedStatement, str4);
      if (localResultSet.next())
      {
        int n = localResultSet.getInt(1);
        str6 = localResultSet.getString(2);
        if ((str6 == null) || (str6.length() == 0)) {
          str6 = "application";
        }
        str5 = localResultSet.getString(3);
        int i1 = localResultSet.getInt(4);
        str7 = localResultSet.getString(5);
        if ((k != 0) && (i1 > 0) && (str5 != null) && (str5.trim().length() > 0))
        {
          int i2 = messageAdapter.addMessageBody(localConnection, str5);
          MessageThread localMessageThread = messageAdapter.addNewMessage(localConnection, str1, n, i1, m, str6, 5, null);
          messageAdapter.addMessageItem(localConnection, Profile.convertToInt(localMessageThread.getThreadID()), 7, i2, 0, m, 1, i1, 0);
        }
      }
      DBUtil.commit(localConnection);
      try
      {
        String str8 = paramApplication.getEmailAddress();
        if ((str8 != null) && (str8.trim().length() > 0) && (str5 != null) && (str5.trim().length() > 0) && (str7 != null) && (str7.trim().length() > 0)) {
          Messages._mailService.sendMessage(str8, str7, str6, str5);
        }
      }
      catch (Exception localException2)
      {
        DebugLog.log(Level.INFO, "An application submission email notification could not be sent. " + localException2.getMessage());
      }
    }
    catch (Exception localException1)
    {
      DBUtil.rollback(localConnection);
      DebugLog.log(Level.INFO, "An application submission secure message notification could not be sent. " + localException1.getMessage());
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  private static void jdMethod_for(Connection paramConnection, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into application_history (app_id,owner_id,modifier_id,status_id,product_id,history_comment,modified_date) values (?,?,?,?,?,?,?)");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setInt(3, paramInt3);
      localPreparedStatement.setInt(4, paramInt5);
      localPreparedStatement.setInt(5, paramInt4);
      Profile.setStatementString(localPreparedStatement, 6, paramString != null ? paramString : "", "history_comment", false);
      localPreparedStatement.setTimestamp(7, DBUtil.getCurrentTimestamp());
      DBUtil.executeUpdate(localPreparedStatement, "insert into application_history (app_id,owner_id,modifier_id,status_id,product_id,history_comment,modified_date) values (?,?,?,?,?,?,?)");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  protected static void deleteProductAccessTX(Connection paramConnection, String paramString, int paramInt1, int paramInt2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer("delete from product_access");
    try
    {
      boolean bool = false;
      bool = Profile.appendSQLSelectString(localStringBuffer, "bank_id", paramString, bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "employee_id", paramInt1, bool);
      bool = Profile.appendSQLSelectInt(localStringBuffer, "product_id", paramInt2, bool);
      String str = localStringBuffer.toString();
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, str);
      int i = 1;
      i = Profile.setStatementString(localPreparedStatement, i, paramString, "bank_id", true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt1, true);
      i = Profile.setStatementInt(localPreparedStatement, i, paramInt2, true);
      DBUtil.executeUpdate(localPreparedStatement, str);
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private static void jdMethod_for(ResultSet paramResultSet, int paramInt)
    throws Exception
  {
    if (paramInt > 1) {
      for (int i = 1; (i < paramInt) && (paramResultSet.next()); i++) {}
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.ApplicationAdapter
 * JD-Core Version:    0.7.0.1
 */