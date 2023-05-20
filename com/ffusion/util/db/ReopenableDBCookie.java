package com.ffusion.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReopenableDBCookie
{
  private String jdField_byte;
  private Connection jdField_do;
  private ResultSet a;
  private PreparedStatement jdField_for;
  private String jdField_int;
  private boolean jdField_if = false;
  private boolean jdField_try = false;
  private boolean jdField_new = false;
  
  public void initialize(String paramString1, Connection paramConnection, PreparedStatement paramPreparedStatement, String paramString2)
  {
    this.jdField_byte = paramString1;
    this.jdField_do = paramConnection;
    this.jdField_for = paramPreparedStatement;
    this.jdField_int = paramString2;
    this.jdField_if = true;
    this.jdField_new = true;
  }
  
  public void open()
    throws Exception
  {
    if ((this.jdField_if) && (this.jdField_new))
    {
      this.a = DBUtil.executeQuery(this.jdField_for, this.jdField_int);
      this.jdField_try = true;
      this.jdField_new = false;
    }
  }
  
  public void reset()
  {
    if (!this.jdField_if) {
      return;
    }
    if (this.jdField_try) {
      close();
    }
  }
  
  public Connection getConnection()
  {
    return this.jdField_do;
  }
  
  public PreparedStatement getPreparedStatement()
  {
    return this.jdField_for;
  }
  
  public ResultSet getResultSet()
  {
    return this.a;
  }
  
  public boolean isInitialized()
  {
    return this.jdField_if;
  }
  
  public boolean isReset()
  {
    return this.jdField_new;
  }
  
  public boolean isOpened()
  {
    return this.jdField_try;
  }
  
  public void close()
  {
    if (this.jdField_try)
    {
      DBUtil.closeResultSet(this.a);
      this.a = null;
      this.jdField_try = false;
    }
  }
  
  public void uninitialize()
  {
    if (this.jdField_try) {
      close();
    }
    if (this.jdField_if)
    {
      DBUtil.closeAll(this.jdField_byte, this.jdField_do, this.jdField_for);
      this.jdField_byte = null;
      this.jdField_do = null;
      this.jdField_for = null;
      this.a = null;
      this.jdField_if = false;
      this.jdField_new = false;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.ReopenableDBCookie
 * JD-Core Version:    0.7.0.1
 */