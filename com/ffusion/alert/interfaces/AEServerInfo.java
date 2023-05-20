package com.ffusion.alert.interfaces;

import java.io.Serializable;

public class AEServerInfo
  implements Serializable
{
  public static final int SERVER_PRIMARY = 1;
  public static final int SERVER_SECONDARY = 2;
  protected String ei;
  protected String ek;
  protected String ej;
  protected String eg;
  protected String eh;
  protected String ee;
  protected int ef = 2;
  
  public AEServerInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt)
  {
    this.ej = paramString1;
    this.ei = paramString2;
    this.ek = paramString3;
    this.eg = paramString4;
    this.eh = paramString5;
    this.ee = paramString6;
    this.ef = paramInt;
  }
  
  public AEServerInfo() {}
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AEServerInfo)) {
      return false;
    }
    AEServerInfo localAEServerInfo = (AEServerInfo)paramObject;
    return (this.ej.equalsIgnoreCase(localAEServerInfo.ej)) && (this.ei.equals(localAEServerInfo.ei)) && (this.ek.equals(localAEServerInfo.ek)) && (this.eg.equals(localAEServerInfo.eg)) && (this.eh.equals(localAEServerInfo.eh)) && (this.ee.equals(localAEServerInfo.ee)) && (this.ef == localAEServerInfo.ef);
  }
  
  public final String getUserName()
  {
    return this.ei;
  }
  
  public final void setUserName(String paramString)
  {
    this.ei = paramString;
  }
  
  public final String getPassword()
  {
    return this.ek;
  }
  
  public final void setPassword(String paramString)
  {
    this.ek = paramString;
  }
  
  public final String getServerURL()
  {
    return this.ej;
  }
  
  public final void setServerURL(String paramString)
  {
    this.ej = paramString;
  }
  
  public final String getContextFactory()
  {
    return this.eg;
  }
  
  public final void setContextFactory(String paramString)
  {
    this.eg = paramString;
  }
  
  public final String getAdminJNDI()
  {
    return this.eh;
  }
  
  public final void setAdminJNDI(String paramString)
  {
    this.eh = paramString;
  }
  
  public final String getClientJNDI()
  {
    return this.ee;
  }
  
  public final void setClientJNDI(String paramString)
  {
    this.ee = paramString;
  }
  
  public final int getServerRole()
  {
    return this.ef;
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.interfaces.AEServerInfo
 * JD-Core Version:    0.7.0.1
 */