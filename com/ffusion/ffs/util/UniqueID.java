package com.ffusion.ffs.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.UID;

public class UniqueID
{
  String a;
  
  public UniqueID()
  {
    try
    {
      this.a = (InetAddress.getLocalHost().getAddress() + new UID().toString());
    }
    catch (UnknownHostException localUnknownHostException)
    {
      this.a = ("UnknownHost" + new UID().toString());
    }
  }
  
  public String toString()
  {
    return this.a;
  }
  
  public static String getID()
  {
    try
    {
      return InetAddress.getLocalHost().getAddress() + new UID().toString();
    }
    catch (UnknownHostException localUnknownHostException) {}
    return "UnknownHost" + new UID().toString();
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.UniqueID
 * JD-Core Version:    0.7.0.1
 */