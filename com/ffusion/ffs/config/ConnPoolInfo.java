package com.ffusion.ffs.config;

import com.ffusion.ffs.util.FFSProperties;
import java.io.Serializable;

public class ConnPoolInfo
  implements Serializable
{
  private static final long a = 12345678910L;
  public DBConnInfo _dbConnInfo = null;
  public boolean _disconnect = false;
  public int _maxPoolSize = 100;
  public int _optimalPoolSize = 40;
  public boolean _poolGuaranteeConnectionProperties = false;
  
  public void setDisconnect(boolean paramBoolean)
  {
    this._disconnect = paramBoolean;
  }
  
  public boolean getDisconnect()
  {
    return this._disconnect;
  }
  
  public void setPoolGuaranteeConnectionProperties(boolean paramBoolean)
  {
    this._poolGuaranteeConnectionProperties = paramBoolean;
  }
  
  public boolean getPoolGuaranteeConnectionProperties()
  {
    return this._poolGuaranteeConnectionProperties;
  }
  
  public boolean supportIdentity()
  {
    return (!this._dbConnInfo._dbType.startsWith("DB2")) && (!this._dbConnInfo._dbType.startsWith("ORACLE"));
  }
  
  public void setDBConnInfo(DBConnInfo paramDBConnInfo)
  {
    this._dbConnInfo = paramDBConnInfo;
  }
  
  public DBConnInfo getDBConnInfo()
  {
    return this._dbConnInfo;
  }
  
  public void setOptimalPoolSize(int paramInt)
  {
    this._optimalPoolSize = paramInt;
  }
  
  public int getOptimalPoolSize()
  {
    return this._optimalPoolSize;
  }
  
  public void setMaxPoolSize(int paramInt)
  {
    this._maxPoolSize = paramInt;
  }
  
  public int getMaxPoolSize()
  {
    return this._maxPoolSize;
  }
  
  public FFSProperties convertToProperties()
  {
    FFSProperties localFFSProperties = new FFSProperties();
    localFFSProperties.setProperty("optimalPoolSize", String.valueOf(this._optimalPoolSize));
    localFFSProperties.setProperty("maxPoolSize", String.valueOf(this._maxPoolSize));
    this._dbConnInfo.convertToProperties(localFFSProperties);
    return localFFSProperties;
  }
  
  public FFSProperties convertToProperties(FFSProperties paramFFSProperties)
  {
    paramFFSProperties.setProperty("optimalPoolSize", String.valueOf(this._optimalPoolSize));
    paramFFSProperties.setProperty("maxPoolSize", String.valueOf(this._maxPoolSize));
    this._dbConnInfo.convertToProperties(paramFFSProperties);
    return paramFFSProperties;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.config.ConnPoolInfo
 * JD-Core Version:    0.7.0.1
 */