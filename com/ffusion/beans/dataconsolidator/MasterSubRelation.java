package com.ffusion.beans.dataconsolidator;

import com.ffusion.beans.DateTime;
import com.ffusion.util.Sortable;
import java.text.Collator;

public class MasterSubRelation
  extends com.ffusion.beans.ExtendABean
  implements Sortable
{
  public static final String SORTFIELD_DATA_DATE = "DataDate";
  public static final String SORTFIELD_MASTER_ROUTING_NUM = "MasterRoutingNumber";
  public static final String SORTFIELD_MASTER_ACCOUNT_ID = "MasterAccountId";
  public static final String SORTFIELD_SUB_ROUTING_NUM = "SubRoutingNumber";
  public static final String SORTFIELD_SUB_ACCOUNT_ID = "SubAccountId";
  public static final String SORTFIELD_WITHHOLD_NZB_SUB_ACCOUNTS = "WithholdNZBSubAccounts";
  public static final String SORTFIELD_INCLUDE_ZBA_CR_IN_ROLLUP = "IncludeZBACreditInRollup";
  public static final String SORTFIELD_INCLUDE_ZBA_DB_IN_ROLLUP = "IncludeZBADebitInRollup";
  public static final String SORTFIELD_LOCATION_ID = "LocationId";
  public static final String SORTFIELD_LOCATION_ID_PLACEMENT = "LocationIdPlacement";
  public static final String SORTFIELD_BAI_FILE_IDENTIFIER = "BAIFileIdentifier";
  public static final String SORTFIELD_BAI_FILE_NAME = "BAIFileName";
  public static final String SORTFIELD_BAI_FILE_DATE = "BAIFileDate";
  private DateTime aX3;
  private int aXW;
  private String aX2;
  private String aX6;
  private String aX9;
  private String aXZ;
  private String aXY;
  private boolean aX4;
  private boolean aX1;
  private boolean aXX;
  private String aX7;
  private int aX0;
  private String aX5;
  private String aYa;
  private DateTime aX8;
  
  public DateTime getDataDate()
  {
    return this.aX3;
  }
  
  public void setDataDate(DateTime paramDateTime)
  {
    this.aX3 = paramDateTime;
  }
  
  public String getBankId()
  {
    return this.aX2;
  }
  
  public void setBankId(String paramString)
  {
    this.aX2 = paramString;
  }
  
  public int getBusinessId()
  {
    return this.aXW;
  }
  
  public void setBusinessId(int paramInt)
  {
    this.aXW = paramInt;
  }
  
  public String getMasterRoutingNumber()
  {
    return this.aX6;
  }
  
  public void setMasterRoutingNumber(String paramString)
  {
    this.aX6 = paramString;
  }
  
  public String getMasterAccountId()
  {
    return this.aX9;
  }
  
  public void setMasterAccountId(String paramString)
  {
    this.aX9 = paramString;
  }
  
  public String getSubRoutingNumber()
  {
    return this.aXZ;
  }
  
  public void setSubRoutingNumber(String paramString)
  {
    this.aXZ = paramString;
  }
  
  public String getSubAccountId()
  {
    return this.aXY;
  }
  
  public void setSubAccountId(String paramString)
  {
    this.aXY = paramString;
  }
  
  public boolean getWithholdNZBSubAccounts()
  {
    return this.aX4;
  }
  
  public void setWithholdNZBSubAccounts(boolean paramBoolean)
  {
    this.aX4 = paramBoolean;
  }
  
  public boolean getIncludeZBACreditInRollup()
  {
    return this.aX1;
  }
  
  public void setIncludeZBACreditInRollup(boolean paramBoolean)
  {
    this.aX1 = paramBoolean;
  }
  
  public boolean getIncludeZBADebitInRollup()
  {
    return this.aXX;
  }
  
  public void setIncludeZBADebitInRollup(boolean paramBoolean)
  {
    this.aXX = paramBoolean;
  }
  
  public String getLocationId()
  {
    return this.aX7;
  }
  
  public void setLocationId(String paramString)
  {
    this.aX7 = paramString;
  }
  
  public int getLocationIdPlacement()
  {
    return this.aX0;
  }
  
  public void setLocationIdPlacement(int paramInt)
  {
    this.aX0 = paramInt;
  }
  
  public String getBAIFileName()
  {
    return this.aYa;
  }
  
  public void setBAIFileName(String paramString)
  {
    this.aYa = paramString;
  }
  
  public String getBAIFileIdentifier()
  {
    return this.aX5;
  }
  
  public void setBAIFileIdentifier(String paramString)
  {
    this.aX5 = paramString;
  }
  
  public DateTime getBAIFileDate()
  {
    return this.aX8;
  }
  
  public void setBAIFileDate(DateTime paramDateTime)
  {
    this.aX8 = paramDateTime;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    MasterSubRelation localMasterSubRelation = (MasterSubRelation)paramObject;
    Collator localCollator = doGetCollator();
    int i = 0;
    if ("DataDate".equals(paramString)) {
      i = this.aX3.compare(localMasterSubRelation.getDataDate());
    } else if ("MasterRoutingNumber".equals(paramString)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aX6, localMasterSubRelation.getMasterRoutingNumber(), localCollator);
    } else if ("MasterAccountId".equals(paramString)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aX9, localMasterSubRelation.getMasterAccountId(), localCollator);
    } else if ("SubRoutingNumber".equals(paramString)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aXZ, localMasterSubRelation.getSubRoutingNumber(), localCollator);
    } else if ("SubAccountId".equals(paramString)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aXY, localMasterSubRelation.getSubAccountId(), localCollator);
    } else if ("WithholdNZBSubAccounts".equals(paramString)) {
      i = jdMethod_for(this.aX4, localMasterSubRelation.getWithholdNZBSubAccounts());
    } else if ("IncludeZBACreditInRollup".equals(paramString)) {
      i = jdMethod_for(this.aX1, localMasterSubRelation.getIncludeZBACreditInRollup());
    } else if ("IncludeZBADebitInRollup".equals(paramString)) {
      i = jdMethod_for(this.aXX, localMasterSubRelation.getIncludeZBADebitInRollup());
    } else if ("LocationId".equals(paramString)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aX7, localMasterSubRelation.getLocationId(), localCollator);
    } else if ("LocationIdPlacement".equals(paramString)) {
      i = jdMethod_new(this.aX0, localMasterSubRelation.getLocationIdPlacement());
    } else if ("BAIFileIdentifier".equals(paramString)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aX5, localMasterSubRelation.getBAIFileIdentifier(), localCollator);
    } else if ("BAIFileName".equals(paramString)) {
      i = com.ffusion.util.beans.ExtendABean.compareStrings(this.aYa, localMasterSubRelation.getBAIFileName(), localCollator);
    } else if ("BAIFileDate".equals(paramString)) {
      i = this.aX8.compare(localMasterSubRelation.getBAIFileDate());
    }
    return i;
  }
  
  private int jdMethod_for(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1 == paramBoolean2) {
      return 0;
    }
    if (!paramBoolean1) {
      return -1;
    }
    return 1;
  }
  
  private int jdMethod_new(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      return 1;
    }
    if (paramInt1 < paramInt2) {
      return -1;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.dataconsolidator.MasterSubRelation
 * JD-Core Version:    0.7.0.1
 */