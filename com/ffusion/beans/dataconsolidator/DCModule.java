package com.ffusion.beans.dataconsolidator;

import com.ffusion.beans.ExtendABean;

public class DCModule
  extends ExtendABean
{
  private final String aXU;
  private final String aXV;
  
  public DCModule(String paramString1, String paramString2)
  {
    this.aXU = paramString1;
    this.aXV = paramString2;
  }
  
  public String getModuleName()
  {
    return this.aXU;
  }
  
  public String getColumnName()
  {
    return this.aXV;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof DCModule))) {
      return false;
    }
    DCModule localDCModule = (DCModule)paramObject;
    return (this.aXU.equalsIgnoreCase(localDCModule.getModuleName())) && (this.aXV.equalsIgnoreCase(localDCModule.getColumnName()));
  }
  
  public int hashCode()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.aXU);
    localStringBuffer.append(" - ");
    localStringBuffer.append(this.aXV);
    return localStringBuffer.toString().hashCode();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.dataconsolidator.DCModule
 * JD-Core Version:    0.7.0.1
 */