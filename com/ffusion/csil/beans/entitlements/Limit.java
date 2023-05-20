package com.ffusion.csil.beans.entitlements;

import com.ffusion.util.Sortable;
import com.ffusion.util.beans.LocaleableBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

public class Limit
  extends LocaleableBean
  implements Serializable, Cloneable, Sortable
{
  public static final int PERIOD_UNDEFINED = 0;
  public static final int PERIOD_TRANSACTION = 1;
  public static final int PERIOD_DAY = 2;
  public static final int PERIOD_WEEK = 3;
  public static final int PERIOD_MONTH = 4;
  public static final char RUNNING_TOTAL_TYPE_GROUP = 'G';
  public static final char RUNNING_TOTAL_TYPE_USER = 'U';
  public static final String LIMIT_ID = "ID";
  public static final String PERIOD = "Period";
  private int a0;
  private int aX;
  private EntitlementGroupMember aO;
  private int a3;
  private String a5;
  private BigDecimal aT;
  private int a2;
  private Date aR;
  private Date aW;
  private Entitlement aU;
  private boolean aY;
  private char a6;
  private boolean a4;
  private boolean aS;
  private boolean aV;
  private boolean aP = true;
  private boolean aZ = false;
  private String aQ = "USD";
  private boolean a1 = false;
  
  public Limit()
  {
    this.a3 = 0;
  }
  
  public Limit(Locale paramLocale)
  {
    super(paramLocale);
    this.a3 = 0;
  }
  
  public Limit(Limit paramLimit)
  {
    set(paramLimit);
    if (paramLimit != null) {
      setLocale(paramLimit.locale);
    }
  }
  
  public Limit(int paramInt1, int paramInt2, String paramString, Entitlement paramEntitlement, boolean paramBoolean)
  {
    this(paramInt1, paramInt2, paramString, paramEntitlement, paramBoolean, null);
  }
  
  public Limit(int paramInt1, int paramInt2, String paramString, Entitlement paramEntitlement, boolean paramBoolean, Locale paramLocale)
  {
    this.aX = paramInt1;
    this.a3 = paramInt2;
    if (paramString == null) {
      this.a5 = null;
    } else {
      this.a5 = new String(paramString);
    }
    try
    {
      this.aT = new BigDecimal(paramString);
    }
    catch (Exception localException)
    {
      this.aT = new BigDecimal(0.0D);
    }
    setEntitlement(paramEntitlement);
    this.aY = paramBoolean;
    this.a4 = true;
    this.aS = true;
    setLocale(paramLocale);
  }
  
  public void set(Limit paramLimit)
  {
    this.a0 = paramLimit.a0;
    this.aX = paramLimit.aX;
    if (paramLimit.aO == null)
    {
      this.aO = null;
    }
    else
    {
      this.aO = new EntitlementGroupMember();
      this.aO.set(paramLimit.aO);
    }
    this.a3 = paramLimit.a3;
    this.a2 = paramLimit.a2;
    if (paramLimit.a5 == null) {
      this.a5 = null;
    } else {
      this.a5 = new String(paramLimit.a5);
    }
    if (paramLimit.aT == null) {
      this.aT = null;
    } else {
      this.aT = new BigDecimal(paramLimit.aT.toString());
    }
    if (paramLimit.aR == null) {
      this.aR = null;
    } else {
      this.aR = ((Date)paramLimit.aR.clone());
    }
    if (paramLimit.aW == null) {
      this.aW = null;
    } else {
      this.aW = ((Date)paramLimit.aW.clone());
    }
    setEntitlement(paramLimit.aU);
    this.aY = paramLimit.aY;
    this.a6 = paramLimit.a6;
    this.a4 = paramLimit.a4;
    this.aS = paramLimit.aS;
    this.aV = paramLimit.aV;
    this.aP = paramLimit.aP;
    this.aZ = paramLimit.aZ;
    this.aQ = paramLimit.aQ;
    this.a1 = paramLimit.a1;
  }
  
  public Object clone()
  {
    Limit localLimit = new Limit();
    localLimit.a0 = this.a0;
    localLimit.aX = this.aX;
    if (this.aO != null)
    {
      localLimit.aO = new EntitlementGroupMember();
      localLimit.aO.setId(this.aO.getId());
      localLimit.aO.setMemberType(this.aO.getMemberType());
      localLimit.aO.setMemberSubType(this.aO.getMemberSubType());
      localLimit.aO.setEntitlementGroupId(this.aO.getEntitlementGroupId());
    }
    localLimit.a3 = this.a3;
    localLimit.a2 = this.a2;
    if (this.a5 == null) {
      localLimit.a5 = null;
    } else {
      localLimit.a5 = new String(this.a5);
    }
    if (this.aT == null) {
      localLimit.aT = null;
    } else {
      localLimit.aT = new BigDecimal(this.aT.toString());
    }
    if (this.aR == null) {
      localLimit.aR = null;
    } else {
      localLimit.aR = ((Date)this.aR.clone());
    }
    if (this.aW == null) {
      localLimit.aW = null;
    } else {
      localLimit.aW = ((Date)this.aW.clone());
    }
    if (this.aU != null) {
      localLimit.aU = ((Entitlement)this.aU.clone());
    }
    localLimit.aY = this.aY;
    localLimit.a6 = this.a6;
    localLimit.a4 = this.a4;
    localLimit.aS = this.aS;
    localLimit.aV = this.aV;
    localLimit.aP = this.aP;
    localLimit.aZ = this.aZ;
    localLimit.aQ = new String(this.aQ);
    localLimit.a1 = this.a1;
    localLimit.setLocale(this.locale);
    return localLimit;
  }
  
  public boolean isLimitInfoIdentical(Limit paramLimit)
  {
    if (paramLimit == null) {
      return false;
    }
    if ((paramLimit.getEntitlement() == null) && (this.aU == null))
    {
      if ((paramLimit.getMember() == null) && (this.aO == null)) {
        return (paramLimit.getGroupId() == this.aX) && (paramLimit.getPeriod() == this.a3) && (paramLimit.isCrossCurrency() == this.aP) && (paramLimit.getCurrencyCode().equals(this.aQ));
      }
      if ((paramLimit.getMember() != null) && (this.aO != null))
      {
        this.aO.setEntitlementGroupId(this.aX);
        return (paramLimit.getGroupId() == this.aX) && (paramLimit.getPeriod() == this.a3) && (paramLimit.getMember().equals(this.aO)) && (paramLimit.isCrossCurrency() == this.aP) && (paramLimit.getCurrencyCode().equals(this.aQ));
      }
      return false;
    }
    if ((paramLimit.getEntitlement() != null) && (this.aU != null))
    {
      if ((paramLimit.getMember() == null) && (this.aO == null)) {
        return (paramLimit.getGroupId() == this.aX) && (paramLimit.getEntitlement().equals(this.aU)) && (paramLimit.getPeriod() == this.a3) && (paramLimit.isCrossCurrency() == this.aP) && (paramLimit.getCurrencyCode().equals(this.aQ));
      }
      if ((paramLimit.getMember() != null) && (this.aO != null))
      {
        this.aO.setEntitlementGroupId(this.aX);
        return (paramLimit.getGroupId() == this.aX) && (paramLimit.getEntitlement().equals(this.aU)) && (paramLimit.getPeriod() == this.a3) && (paramLimit.getMember().equals(this.aO)) && (paramLimit.isCrossCurrency() == this.aP) && (paramLimit.getCurrencyCode().equals(this.aQ));
      }
      return false;
    }
    return false;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Limit)) {
      return false;
    }
    if (paramObject == null) {
      return false;
    }
    Limit localLimit = (Limit)paramObject;
    if (!isLimitInfoIdentical(localLimit)) {
      return false;
    }
    if ((this.a0 != localLimit.getLimitId()) || (this.aY != localLimit.isAllowedApproval()) || (this.a6 != localLimit.getRunningTotalType())) {
      return false;
    }
    if (((this.a5 != null) && (localLimit.getData() == null)) || ((this.a5 == null) && (localLimit.getData() != null))) {
      return false;
    }
    return (this.a5 == null) || (localLimit.getData() == null) || (this.a5.equals(localLimit.getData()));
  }
  
  public void setLimitName(String paramString)
  {
    if (this.aU == null) {
      setEntitlement(new Entitlement(paramString, null, null));
    } else {
      this.aU.setOperationName(paramString);
    }
  }
  
  public String getLimitName()
  {
    if (this.aU == null) {
      return null;
    }
    return this.aU.getOperationName();
  }
  
  public void setLimitId(int paramInt)
  {
    this.a0 = paramInt;
  }
  
  public int getLimitId()
  {
    return this.a0;
  }
  
  public void setParentId(int paramInt)
  {
    this.a2 = paramInt;
    this.aS = true;
  }
  
  public int getParentId()
  {
    return this.a2;
  }
  
  public boolean isParentSet()
  {
    return this.aS;
  }
  
  public void setGroupId(int paramInt)
  {
    this.aX = paramInt;
  }
  
  public int getGroupId()
  {
    return this.aX;
  }
  
  public void setPeriod(int paramInt)
  {
    this.a3 = paramInt;
  }
  
  public int getPeriod()
  {
    return this.a3;
  }
  
  public void setData(String paramString)
  {
    this.a5 = paramString;
    try
    {
      this.aT = new BigDecimal(paramString);
    }
    catch (Exception localException)
    {
      this.aT = new BigDecimal(0.0D);
    }
  }
  
  public String getData()
  {
    return this.a5;
  }
  
  public BigDecimal getAmount()
  {
    return this.aT;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    this.a5 = paramBigDecimal.toString();
    this.aT = paramBigDecimal;
  }
  
  public void setModifiedDate(Date paramDate)
  {
    this.aR = paramDate;
  }
  
  public Date getModifiedDate()
  {
    return this.aR;
  }
  
  public void setRetrievalDate(Date paramDate)
  {
    this.aW = paramDate;
  }
  
  public Date getRetrievalDate()
  {
    return this.aW;
  }
  
  public void setEntitlement(Entitlement paramEntitlement)
  {
    this.aU = new Entitlement(paramEntitlement);
  }
  
  public Entitlement getEntitlement()
  {
    return new Entitlement(this.aU);
  }
  
  public void setObjectType(String paramString)
  {
    if (this.aU == null) {
      setEntitlement(new Entitlement(null, paramString, null));
    } else {
      this.aU.setObjectType(paramString);
    }
  }
  
  public String getObjectType()
  {
    if (this.aU == null) {
      return null;
    }
    return this.aU.getObjectType();
  }
  
  public void setObjectId(String paramString)
  {
    if (this.aU == null) {
      setEntitlement(new Entitlement(null, null, paramString));
    } else {
      this.aU.setObjectId(paramString);
    }
  }
  
  public String getObjectId()
  {
    if (this.aU == null) {
      return null;
    }
    return this.aU.getObjectId();
  }
  
  public void setAllowApproval(boolean paramBoolean)
  {
    this.aY = paramBoolean;
    this.a4 = true;
  }
  
  public boolean isAllowedApproval()
  {
    return this.aY;
  }
  
  public boolean isAllowApprovalSet()
  {
    return this.a4;
  }
  
  public boolean isCrossCurrency()
  {
    return this.aP;
  }
  
  public void setCrossCurrency(boolean paramBoolean)
  {
    this.aP = paramBoolean;
    this.aZ = true;
  }
  
  public boolean isCrossCurrencySet()
  {
    return this.aZ;
  }
  
  public String getCurrencyCode()
  {
    return this.aQ;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.aQ = paramString;
    this.a1 = true;
  }
  
  public boolean isCurrencyCodeSet()
  {
    return this.a1;
  }
  
  public void setRunningTotalType(char paramChar)
  {
    this.a6 = paramChar;
    this.aV = true;
  }
  
  public char getRunningTotalType()
  {
    return this.a6;
  }
  
  public boolean isRunningTotalTypeSet()
  {
    return this.aV;
  }
  
  public String getAllowApproval()
  {
    return new Boolean(this.aY).toString();
  }
  
  public void setMemberId(String paramString)
  {
    if ((this.aO == null) && (paramString == null)) {
      return;
    }
    if (this.aO == null) {
      this.aO = new EntitlementGroupMember();
    }
    this.aO.setId(paramString);
  }
  
  public String getMemberId()
  {
    if (this.aO == null) {
      return null;
    }
    return this.aO.getId();
  }
  
  public void setMemberType(String paramString)
  {
    if ((this.aO == null) && (paramString == null)) {
      return;
    }
    if (this.aO == null) {
      this.aO = new EntitlementGroupMember();
    }
    this.aO.setMemberType(paramString);
  }
  
  public String getMemberType()
  {
    if (this.aO == null) {
      return null;
    }
    return this.aO.getMemberType();
  }
  
  public void setMemberSubType(String paramString)
  {
    if ((this.aO == null) && (paramString == null)) {
      return;
    }
    if (this.aO == null) {
      this.aO = new EntitlementGroupMember();
    }
    this.aO.setMemberSubType(paramString);
  }
  
  public String getMemberSubType()
  {
    if (this.aO == null) {
      return null;
    }
    return this.aO.getMemberSubType();
  }
  
  public EntitlementGroupMember getMember()
  {
    if (this.aO != null) {
      this.aO.setEntitlementGroupId(this.aX);
    }
    return this.aO;
  }
  
  public boolean isPeriodValid()
  {
    switch (this.a3)
    {
    case 1: 
    case 2: 
    case 3: 
    case 4: 
      return true;
    }
    return false;
  }
  
  public int compare(Object paramObject, String paramString)
    throws ClassCastException
  {
    int i = 0;
    Limit localLimit = (Limit)paramObject;
    if ("ID".equals(paramString)) {
      i = getLimitId() - localLimit.getLimitId();
    } else if ("Period".equals(paramString)) {
      i = getPeriod() - localLimit.getPeriod();
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.csil.beans.entitlements.Limit
 * JD-Core Version:    0.7.0.1
 */