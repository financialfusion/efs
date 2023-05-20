package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class CustomerRouteInfo
  extends BPWInfoBase
  implements Serializable
{
  public String CustomerID;
  public int RouteID;
  public String Status;
  public String SubmitDate;
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    return "CustomerID = " + (this.CustomerID == null ? "null" : new StringBuffer().append("\"").append(this.CustomerID).append("\"").toString()) + str + "RouteID = " + this.RouteID + str + "Status = " + (this.Status == null ? "null" : new StringBuffer().append("\"").append(this.Status).append("\"").toString()) + str + "submitDate = " + (this.SubmitDate == null ? "null" : new StringBuffer().append("\"").append(this.SubmitDate).append("\"").toString());
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CustomerRouteInfo
 * JD-Core Version:    0.7.0.1
 */