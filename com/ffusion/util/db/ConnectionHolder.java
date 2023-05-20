package com.ffusion.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConnectionHolder
{
  private String jdField_for;
  private Connection jdField_if;
  private PreparedStatement jdField_do;
  private boolean a = false;
  
  public void initialize(String paramString, Connection paramConnection, PreparedStatement paramPreparedStatement)
  {
    this.jdField_for = paramString;
    this.jdField_if = paramConnection;
    this.jdField_do = paramPreparedStatement;
    this.a = true;
  }
  
  public Connection getConnection()
  {
    return this.jdField_if;
  }
  
  public PreparedStatement getPreparedStatement()
  {
    return this.jdField_do;
  }
  
  public boolean isInitialized()
  {
    return this.a;
  }
  
  public void close()
  {
    if (this.a)
    {
      DBUtil.closeAll(this.jdField_for, this.jdField_if, this.jdField_do);
      this.jdField_for = null;
      this.jdField_if = null;
      this.jdField_do = null;
      this.a = false;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.ConnectionHolder
 * JD-Core Version:    0.7.0.1
 */