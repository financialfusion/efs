package com.ffusion.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBCookie
{
  private String jdField_new;
  private Connection jdField_do;
  private ResultSet a;
  private PreparedStatement jdField_for;
  private boolean jdField_if = false;
  private boolean jdField_int = false;
  
  public void initialize(String paramString1, Connection paramConnection, PreparedStatement paramPreparedStatement, String paramString2)
    throws Exception
  {
    this.jdField_new = paramString1;
    this.jdField_do = paramConnection;
    this.jdField_for = paramPreparedStatement;
    this.a = DBUtil.executeQuery(this.jdField_for, paramString2);
    this.jdField_int = true;
    this.jdField_if = true;
  }
  
  public Connection getConnection()
  {
    return this.jdField_do;
  }
  
  public ResultSet getResultSet()
  {
    return this.a;
  }
  
  public boolean isInitialized()
  {
    return this.jdField_if;
  }
  
  public boolean isOpened()
  {
    return this.jdField_int;
  }
  
  public void close()
  {
    if (this.jdField_int)
    {
      DBUtil.closeAll(this.jdField_new, this.jdField_do, this.jdField_for, this.a);
      this.jdField_new = null;
      this.jdField_do = null;
      this.jdField_for = null;
      this.a = null;
      this.jdField_int = false;
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.DBCookie
 * JD-Core Version:    0.7.0.1
 */