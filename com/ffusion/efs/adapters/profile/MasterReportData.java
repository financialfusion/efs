package com.ffusion.efs.adapters.profile;

public class MasterReportData
{
  public String marketSegment = null;
  public String servicePackage = null;
  
  public boolean hasMarketSegmentChanged(MasterReportData paramMasterReportData)
  {
    boolean bool = false;
    if (paramMasterReportData == null) {
      bool = true;
    } else if (((this.marketSegment != null) && (!this.marketSegment.equals(paramMasterReportData.marketSegment))) || ((this.servicePackage != null) && (!this.servicePackage.equals(paramMasterReportData.servicePackage)))) {
      bool = true;
    }
    return bool;
  }
  
  public static MasterReportData clone(MasterReportData paramMasterReportData)
  {
    MasterReportData localMasterReportData = new MasterReportData();
    localMasterReportData.marketSegment = paramMasterReportData.marketSegment;
    localMasterReportData.servicePackage = paramMasterReportData.servicePackage;
    return localMasterReportData;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.MasterReportData
 * JD-Core Version:    0.7.0.1
 */