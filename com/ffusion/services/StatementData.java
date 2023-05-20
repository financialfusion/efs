package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.beans.istatements.Statements;
import com.ffusion.istatements.IStatementException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

public abstract interface StatementData
  extends Serializable
{
  public static final String SERVICE_INIT_XML = "istatements.xml";
  public static final int ERROR_NONE = 0;
  public static final int SERVICE_ERROR_INVALID_SERVICE = 29200;
  public static final int SERVICE_NOT_INITIALIZED = 29201;
  
  public abstract Statements getStatementList(Accounts paramAccounts)
    throws IStatementException;
  
  public abstract Statement getStatement(Statement paramStatement)
    throws IStatementException;
  
  public abstract Statement getFullStatement(Statement paramStatement)
    throws IStatementException;
  
  public abstract void initialize(HashMap paramHashMap)
    throws IStatementException;
  
  public abstract void addAccountsForIStatement(SecureUser paramSecureUser, Accounts paramAccounts)
    throws IStatementException;
  
  public abstract void removeAccountsForIStatement(Accounts paramAccounts)
    throws IStatementException;
  
  public abstract void getPDF(Statement paramStatement, boolean paramBoolean, OutputStream paramOutputStream, Locale paramLocale)
    throws IStatementException;
  
  public abstract void getCSV(Statement paramStatement, PrintWriter paramPrintWriter, Locale paramLocale)
    throws IStatementException;
  
  public abstract Accounts getAccountsForIStatement(SecureUser paramSecureUser)
    throws IStatementException;
  
  public abstract String getStatementAgreement(SecureUser paramSecureUser)
    throws IStatementException;
  
  public abstract void setStatementAgreement(SecureUser paramSecureUser, String paramString)
    throws IStatementException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.StatementData
 * JD-Core Version:    0.7.0.1
 */