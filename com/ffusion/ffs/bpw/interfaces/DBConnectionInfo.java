package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class DBConnectionInfo
  implements Serializable
{
  public String dbType;
  public String userName;
  public String password;
  public String host;
  public int port;
  public String dbName;
  public String url;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.DBConnectionInfo
 * JD-Core Version:    0.7.0.1
 */