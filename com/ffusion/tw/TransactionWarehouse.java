package com.ffusion.tw;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.tw.TWTransactions;
import com.ffusion.tw.db.Transaction;
import com.ffusion.tw.interfaces.ITransactionWarehousePlugin;
import com.ffusion.tw.interfaces.TWException;
import com.ffusion.util.IntMap;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;

public class TransactionWarehouse
{
  private static final String jdField_try = "transactionwarehouse.xml";
  private static final String jdField_if = "TW_PLUGIN_LIST";
  private static final String jdField_byte = "TW_PLUGIN";
  private static final String jdField_do = "TRANSACTION_TYPE_LIST";
  private static final String jdField_int = "TRANSACTION_TYPE";
  private static final String jdField_else = "PLUGIN_CLASS";
  private static final String jdField_char = "PLUGIN_CONFIG";
  private static IntMap jdField_new;
  private String jdField_case = null;
  private String a = null;
  private static final String jdField_goto = "TransactionWarehouse";
  protected static final String SETTINGS_KEY = "TRANSACTION_WAREHOUSE_SETTINGS";
  private static final String jdField_for = "DB_PROPERTIES";
  
  public TransactionWarehouse(HashMap paramHashMap)
  {
    String str = "TransactionWarehouse.constructor";
    try
    {
      HashMap localHashMap = (HashMap)paramHashMap.get("TRANSACTION_WAREHOUSE_SETTINGS");
      Properties localProperties = null;
      if (localHashMap != null) {
        localProperties = (Properties)localHashMap.get("DB_PROPERTIES");
      }
      if (localProperties == null) {
        localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      }
      this.jdField_case = ConnectionPool.init(localProperties);
      this.a = localProperties.getProperty("ConnectionType");
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
    }
  }
  
  public TWTransaction addTransaction(int paramInt, IApprovable paramIApprovable)
    throws TWException
  {
    String str = "TransactionWarehouse.addTransaction1";
    TWTransaction localTWTransaction = null;
    try
    {
      if (paramIApprovable == null) {
        throw new TWException(108, " The transaction could not be added. ");
      }
      localTWTransaction = Transaction.addTransaction(paramInt, paramIApprovable, this.jdField_case, this.a);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransaction;
  }
  
  public TWTransaction addTransaction(int paramInt, String paramString1, String paramString2, IApprovable paramIApprovable)
    throws TWException
  {
    String str = "TransactionWarehouse.addTransaction2";
    TWTransaction localTWTransaction = null;
    try
    {
      if (paramIApprovable == null) {
        throw new TWException(108, " The transaction could not be added. ");
      }
      localTWTransaction = Transaction.addTransaction(paramInt, paramString1, paramString2, paramIApprovable, this.jdField_case, this.a);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransaction;
  }
  
  public void deleteTransaction(int paramInt)
    throws TWException
  {
    String str = "TransactionWarehouse.deleteTransaction";
    try
    {
      Transaction.deleteTransaction(paramInt, this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
  }
  
  public void deleteTransactions(int[] paramArrayOfInt)
    throws TWException
  {
    String str = "TransactionWarehouse.deleteTransactions";
    try
    {
      if (paramArrayOfInt != null)
      {
        if (paramArrayOfInt.length > 0) {
          Transaction.deleteTransactions(paramArrayOfInt, this.jdField_case);
        }
      }
      else {
        throw new TWException(107, " The transactions could not be deleted. ");
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
  }
  
  public void cleanup(int paramInt)
    throws TWException
  {
    String str = "TransactionWarehouse.cleanup";
    try
    {
      if (paramInt < 0) {
        throw new TWException(102);
      }
      Transaction.cleanup(paramInt, this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
  }
  
  public TWTransaction modifyTransaction(int paramInt, IApprovable paramIApprovable)
    throws TWException
  {
    String str = "TransactionWarehouse.modifyTransaction";
    TWTransaction localTWTransaction = null;
    try
    {
      if (paramIApprovable == null) {
        throw new TWException(108, " The transaction could not be modified. ");
      }
      localTWTransaction = Transaction.modifyTransaction(paramInt, paramIApprovable, this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransaction;
  }
  
  public TWTransaction getTransaction(int paramInt)
    throws TWException
  {
    String str = "TransactionWarehouse.getTransaction";
    TWTransaction localTWTransaction = null;
    try
    {
      localTWTransaction = Transaction.getTransaction(paramInt, this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransaction;
  }
  
  public TWTransaction getTransaction(String paramString)
    throws TWException
  {
    String str = "TransactionWarehouse.getTransaction";
    TWTransaction localTWTransaction = null;
    try
    {
      localTWTransaction = Transaction.getTransaction(paramString, this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransaction;
  }
  
  public TWTransaction getTransaction(int paramInt, String paramString)
    throws TWException
  {
    String str = "TransactionWarehouse.getTransaction";
    TWTransaction localTWTransaction = null;
    try
    {
      localTWTransaction = Transaction.getTransaction(paramInt, paramString, this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransaction;
  }
  
  public TWTransactions getTransactions(int[] paramArrayOfInt)
    throws TWException
  {
    String str = "TransactionWarehouse.getTransactions_1";
    TWTransactions localTWTransactions = null;
    try
    {
      if (paramArrayOfInt != null)
      {
        if (paramArrayOfInt.length > 0) {
          localTWTransactions = Transaction.getTransactions(paramArrayOfInt, this.jdField_case);
        }
      }
      else {
        throw new TWException(107, " The transactions could not be obtained. ");
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransactions;
  }
  
  public TWTransactions getTransactions(int paramInt1, int paramInt2)
    throws TWException
  {
    String str = "TransactionWarehouse.getTransactions_2";
    TWTransactions localTWTransactions = null;
    try
    {
      localTWTransactions = Transaction.getTransactions(paramInt1, paramInt2, this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransactions;
  }
  
  public static IApprovable getApprovable(int paramInt, String paramString, HashMap paramHashMap)
    throws TWException
  {
    IApprovable localIApprovable = null;
    try
    {
      ITransactionWarehousePlugin localITransactionWarehousePlugin = (ITransactionWarehousePlugin)jdField_new.get(paramInt);
      if (localITransactionWarehousePlugin == null) {
        throw new TWException(4, " There is no Transaction Warehouse plugin mapped to the transaction type " + paramInt);
      }
      localIApprovable = localITransactionWarehousePlugin.getApprovable(paramInt, paramString, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      throw new TWException(localThrowable, 0);
    }
    return localIApprovable;
  }
  
  public static IApprovable getApprovable(int paramInt, HashMap paramHashMap)
    throws TWException
  {
    IApprovable localIApprovable = null;
    try
    {
      ITransactionWarehousePlugin localITransactionWarehousePlugin = (ITransactionWarehousePlugin)jdField_new.get(paramInt);
      if (localITransactionWarehousePlugin == null) {
        throw new TWException(4, " There is no Transaction Warehouse plugin mapped to the transaction type " + paramInt);
      }
      localIApprovable = localITransactionWarehousePlugin.getApprovable(paramInt, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      throw new TWException(localThrowable, 0);
    }
    return localIApprovable;
  }
  
  public TWTransaction modifyTransaction(int paramInt1, int paramInt2, IApprovable paramIApprovable)
    throws TWException
  {
    String str = "TransactionWarehouse.modifyTransaction";
    TWTransaction localTWTransaction = null;
    try
    {
      localTWTransaction = Transaction.modifyTransaction(paramInt1, paramInt2, paramIApprovable, this.jdField_case);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing(str, localThrowable);
      if ((localThrowable instanceof TWException)) {
        throw ((TWException)localThrowable);
      }
      throw new TWException(localThrowable, 0);
    }
    return localTWTransaction;
  }
  
  static
  {
    try
    {
      jdField_new = new IntMap();
      InputStream localInputStream = null;
      String str1 = null;
      try
      {
        localInputStream = ResourceUtil.getResourceAsStream(new Object(), "transactionwarehouse.xml");
        str1 = Strings.streamToString(localInputStream);
      }
      catch (Exception localException1)
      {
        throw new TWException(3, " Unable to load Transaction Warehouse configuration file transactionwarehouse.xml.  ");
      }
      XMLTag localXMLTag1 = null;
      try
      {
        localXMLTag1 = new XMLTag(true);
        localXMLTag1.build(str1);
        str1 = null;
      }
      catch (Exception localException2)
      {
        throw new TWException(localException2, 3, " An error occurred while parsing the Transaction Warehouse configuration file transactionwarehouse.xml.  ");
      }
      DebugLog.log(Level.INFO, "Processing Transaction Warehouse config xml transactionwarehouse.xml");
      XMLTag localXMLTag2 = localXMLTag1.getContainedTag("TW_PLUGIN_LIST");
      ArrayList localArrayList1 = (ArrayList)XMLUtil.tagToHashMap(localXMLTag2).get("TW_PLUGIN");
      if (localArrayList1 != null)
      {
        Iterator localIterator1 = localArrayList1.iterator();
        while (localIterator1.hasNext())
        {
          HashMap localHashMap1 = (HashMap)localIterator1.next();
          String str2 = (String)localHashMap1.get("PLUGIN_CLASS");
          Class localClass = null;
          try
          {
            localClass = Class.forName(str2);
          }
          catch (ClassNotFoundException localClassNotFoundException)
          {
            DebugLog.log(Level.WARNING, " Unable to load Transaction Warehouse plugin class " + str2);
          }
          continue;
          ITransactionWarehousePlugin localITransactionWarehousePlugin = null;
          try
          {
            localITransactionWarehousePlugin = (ITransactionWarehousePlugin)localClass.newInstance();
          }
          catch (ClassCastException localClassCastException)
          {
            DebugLog.log(Level.WARNING, " The following Transaction Warehouse plugin class could not be loaded as it does not implement ITransactionWarehousePlugin: " + str2);
            continue;
          }
          catch (Exception localException3)
          {
            DebugLog.log(Level.WARNING, " An error occurred while instantiating Transaction Warehouse plugin class " + str2 + ": " + localException3.getMessage());
          }
          continue;
          HashMap localHashMap2 = null;
          Object localObject = localHashMap1.get("PLUGIN_CONFIG");
          if ((localObject instanceof HashMap)) {
            localHashMap2 = (HashMap)localObject;
          }
          DebugLog.log(Level.INFO, "Initializing Transaction Warehouse plugin " + str2);
          try
          {
            localITransactionWarehousePlugin.initialize(localHashMap2);
          }
          catch (Throwable localThrowable2)
          {
            DebugLog.log(Level.WARNING, " An error occurred while initializing Transaction Warehouse plugin " + str2 + ": " + localThrowable2.getMessage());
          }
          continue;
          ArrayList localArrayList2 = (ArrayList)((HashMap)localHashMap1.get("TRANSACTION_TYPE_LIST")).get("TRANSACTION_TYPE");
          Iterator localIterator2 = localArrayList2.iterator();
          while (localIterator2.hasNext())
          {
            String str3 = (String)localIterator2.next();
            int i = Integer.parseInt(str3);
            if (jdField_new.containsKey(i)) {
              DebugLog.log(Level.WARNING, " A Transaction Warehouse plugin mapping for the following transaction type already exists: " + str3);
            } else {
              jdField_new.put(i, localITransactionWarehousePlugin);
            }
          }
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      DebugLog.throwing("TransactionWarehouse static initializer", localThrowable1);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.TransactionWarehouse
 * JD-Core Version:    0.7.0.1
 */