package com.ffusion.tw;

public class TWConstants
{
  public static final String ERR_MSG_DB_EXCEPTION = " A database related error has occurred. ";
  public static final String ERR_MSG_POOL_EXCEPTION = " A database connectionpool related error has occurred. ";
  public static final String ERR_MSG_INVALID_TRANSACTION_TYPE = " The provided transaction type is  not supported. ";
  public static final String ERR_MSG_INVALID_TRANSACTION_ID = " There is no transaction in the database with the given transaction id ";
  public static final String ERR_MSG_INVALID_TRANSACTION_CLEANUP_INT_VALUE = " The value specified for number of days is not a positive integer. ";
  public static final String ERR_MSG_NULL_TRANSACTION_AMOUNT = " The amount for the given transaction has not been specified. ";
  public static final String ERR_MSG_NULL_TRANSACTION_TRACKINGID = " The trackingID for the given transaction has not been specified. ";
  public static final String ERR_MSG_NULL_TRANSACTION_XMLDATA = " The XML data for the given transaction has not been specified. ";
  public static final String ERR_MSG_NEGATIVE_TRANSACTION_AMOUNT = " The amount for the given transaction cannot be negative. ";
  public static final String ERR_MSG_NULL_TRANSACTION_IDS = " The transaction IDs have not been specified.  ";
  public static final String ERR_MSG_NULL_TRANSACTION = " The transaction beans have not been specified.  ";
  public static final String ERR_MSG_CANNOT_MODIFY_TRACKINGID = " The trackingID cannot be modified.  ";
  public static final String ERR_MSG_CANNOT_MODIFY_TRANSACTIONTYPE = " The transaction type cannot be modified.  ";
  public static final String ERR_MSG_NULL_USERNAME = " The username has not been specified.  ";
  public static final String ERR_MSG_NULL_PASSWORD = " The password has not been specified.  ";
  public static final String ERR_MSG_CANNOT_LOAD_CONFIG_XML = " Unable to load Transaction Warehouse configuration file transactionwarehouse.xml.  ";
  public static final String ERR_MSG_CANNOT_PARSE_CONFIG_XML = " An error occurred while parsing the Transaction Warehouse configuration file transactionwarehouse.xml.  ";
  public static final String ERR_MSG_INIT_FAILED = " The Transaction Warehouse could not be initialized. ";
  public static final String ERR_MSG_ADD_TRANSACTION_FAILED = " The transaction could not be added. ";
  public static final String ERR_MSG_MODIFY_TRANSACTION_FAILED = " The transaction could not be modified. ";
  public static final String ERR_MSG_DELETE_TRANSACTION_FAILED = " The transaction could not be deleted. ";
  public static final String ERR_MSG_DELETE_TRANSACTIONS_FAILED = " The transactions could not be deleted. ";
  public static final String ERR_MSG_PURGE_TRANSACTIONS_FAILED = " The transactions could not be purged. ";
  public static final String ERR_MSG_GET_TRANSACTION_FAILED = " The transaction could not be obtained. ";
  public static final String ERR_MSG_GET_TRANSACTIONS_FAILED = " The transactions could not be obtained. ";
  public static final String ERR_MSG_CONN_POOL_INIT_FAILED = " The database connection pool could not be initialized. ";
  public static final String ERR_MSG_INVALID_SQL_DATA_TYPE = " The given SQL data type is not supported. ";
  public static final String ERR_MSG_CANNOT_LOAD_PLUGIN_CLASS = " Unable to load Transaction Warehouse plugin class ";
  public static final String ERR_MSG_PLUGIN_CLASS_INVALID_TYPE = " The following Transaction Warehouse plugin class could not be loaded as it does not implement ITransactionWarehousePlugin: ";
  public static final String ERR_MSG_CANNOT_INSTANTIATE_PLUGIN_CLASS = " An error occurred while instantiating Transaction Warehouse plugin class ";
  public static final String ERR_MSG_ERROR_INITIALIZING_PLUGIN = " An error occurred while initializing Transaction Warehouse plugin ";
  public static final String ERR_MSG_PLUGIN_MAPPING_ALREADY_EXISTS = " A Transaction Warehouse plugin mapping for the following transaction type already exists: ";
  public static final String ERR_MSG_PLUGIN_LOOKUP_FAILED = " Unable to lookup Transaction Warehouse plugin. ";
  public static final String ERR_MSG_PLUGIN_MAPPING_MISSING = " There is no Transaction Warehouse plugin mapped to the transaction type ";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.TWConstants
 * JD-Core Version:    0.7.0.1
 */