package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.util.FFSDebug;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class AccountTypesMap
  implements Serializable
{
  private HashMap a = null;
  private HashMap jdField_do = null;
  private boolean jdField_if;
  public static final String UNKNOWN = "UNKNOWN";
  public static final int TYPE_UNKNOWN = 0;
  
  public void init()
  {
    if (!this.jdField_if)
    {
      FFSDebug.log("AccountTypesMap.init() Initializing...:", 6);
      FFSConnection localFFSConnection = null;
      try
      {
        localFFSConnection = DBUtil.getConnection();
        init(localFFSConnection.getConnection());
      }
      catch (Exception localException)
      {
        FFSDebug.log("AccountTypesMap.init() failed:" + localException.toString(), 0);
      }
      finally
      {
        DBUtil.freeConnection(localFFSConnection);
      }
    }
    else
    {
      FFSDebug.log("AccountTypesMap.init() Already initialized...:", 6);
    }
  }
  
  public void init(Connection paramConnection)
  {
    if (!this.jdField_if)
    {
      FFSDebug.log("AccountTypesMap.init(Connection conn) Initializing...:", 6);
      String str = "SELECT AccountTypeInt,AccountTypeString  FROM AccountTypesMapping  ";
      ResultSet localResultSet = null;
      this.a = new HashMap();
      this.jdField_do = new HashMap();
      Statement localStatement = null;
      try
      {
        localStatement = paramConnection.createStatement(1004, 1007);
        localResultSet = localStatement.executeQuery(str);
        while (localResultSet.next())
        {
          this.jdField_do.put(new Integer(localResultSet.getInt(1)), localResultSet.getString(2));
          this.a.put(localResultSet.getString(2), new Integer(localResultSet.getInt(1)));
        }
        this.jdField_if = true;
        FFSDebug.log("AccountTypesMap.init() accountMapToInt...:" + this.a, 6);
        FFSDebug.log("AccountTypesMap.init() accountMapToStr...:" + this.jdField_do, 6);
      }
      catch (Exception localException1)
      {
        FFSDebug.log("AccountTypesMap.init() failed:" + localException1.toString(), 0);
      }
      finally
      {
        try
        {
          if (localResultSet != null) {
            localResultSet.close();
          }
          if (localStatement != null) {
            localStatement.close();
          }
        }
        catch (Exception localException2)
        {
          FFSDebug.log("AccountTypesMap.init() failed:" + localException2.toString(), 0);
        }
      }
    }
    else
    {
      FFSDebug.log("AccountTypesMap.init() Already initialized...:", 6);
    }
  }
  
  public int getAccountType(String paramString)
    throws BPWException
  {
    FFSDebug.log("AccountTypesMap.getAccountType() start: accountTypeStr=" + paramString, 6);
    if (this.a == null)
    {
      FFSDebug.log("AccountTypesMap.getAccountType()::AccountTypesMap table is empty ", 0);
      if (!this.jdField_if) {
        throw new BPWException("AccountTypesMap is not initialized ");
      }
      throw new BPWException("AccountTypesMap table is empty");
    }
    if (paramString == null)
    {
      FFSDebug.log("AccountTypesMap.getAccountType()::accountTypeStr is null ", 0);
      throw new BPWException("Invalid Account Type. accountTypeString is null");
    }
    paramString = paramString.toUpperCase();
    Integer localInteger = (Integer)this.a.get(paramString);
    if (localInteger == null)
    {
      FFSDebug.log("AccountTypesMap.getAccountType()::acctType is invalid for accountTypeStr= " + paramString, 6);
      throw new BPWException("Unknown Account Type . accountTypeStr= " + paramString);
    }
    int i = localInteger.intValue();
    FFSDebug.log("AccountTypesMap.getAccountType() end: accTypeInt=" + i, 6);
    return i;
  }
  
  public String getAccountType(int paramInt)
    throws BPWException
  {
    if (this.jdField_do == null)
    {
      FFSDebug.log("AccountTypesMap.getAccountType()::AccountTypesMap table is empty ", 0);
      if (!this.jdField_if) {
        throw new BPWException("AccountTypesMap is not initialized ");
      }
      throw new BPWException("AccountTypesMap table is empty");
    }
    String str = (String)this.jdField_do.get(new Integer(paramInt));
    if (str == null)
    {
      FFSDebug.log("AccountTypesMap.getAccountType():: Invalid acctType for accountType=" + paramInt, 0);
      throw new BPWException("Unknown Account Type. accountType= " + paramInt);
    }
    return str;
  }
  
  public String getAccountTypeStr(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum paramEnumAccountEnum)
    throws BPWException
  {
    String str = MsgBuilder.getAcctType(paramEnumAccountEnum);
    return str;
  }
  
  public String getAccountTypeStr(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum paramEnumAccountEnum)
    throws BPWException
  {
    String str = MsgBuilder.getAcctType(paramEnumAccountEnum);
    return str;
  }
  
  public int getAccountTypeInt(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum paramEnumAccountEnum)
    throws BPWException
  {
    String str = MsgBuilder.getAcctType(paramEnumAccountEnum);
    return getAccountType(str);
  }
  
  public int getAccountTypeInt(com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum paramEnumAccountEnum)
    throws BPWException
  {
    String str = MsgBuilder.getAcctType(paramEnumAccountEnum);
    return getAccountType(str);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum getAccountEnumOFX151(String paramString)
    throws BPWException
  {
    return MsgBuilder.getOFX151AcctEnum(paramString);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum getAccountEnumOFX200(String paramString)
    throws BPWException
  {
    return MsgBuilder.getOFX200AcctEnum(paramString);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumAccountEnum getAccountEnumOFX151(int paramInt)
    throws BPWException
  {
    String str = getAccountType(paramInt);
    return MsgBuilder.getOFX151AcctEnum(str);
  }
  
  public com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum getAccountEnumOFX200(int paramInt)
    throws BPWException
  {
    String str = getAccountType(paramInt);
    return MsgBuilder.getOFX200AcctEnum(str);
  }
  
  public Map getAccountTypesMapInt()
  {
    return this.a;
  }
  
  public Map getAccountTypesMapString()
  {
    return this.jdField_do;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.AccountTypesMap
 * JD-Core Version:    0.7.0.1
 */