package com.ffusion.tasks.multiuser;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMaxLimits
  extends BaseTask
{
  private LimitTypePropertyLists aLP;
  private Limits aLQ;
  private String aLN;
  private String aLR;
  private String aLS;
  private String aLM;
  private String aLO;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    if (this.aLN == null)
    {
      this.error = 28051;
      str = this.taskErrorURL;
    }
    else
    {
      int i = 0;
      try
      {
        i = Integer.parseInt(this.aLN);
      }
      catch (Exception localException)
      {
        i = 0;
      }
      try
      {
        this.aLQ = Entitlements.getCumulativeLimits(i);
        this.aLP = Entitlements.getLimitTypesWithProperties(new HashMap());
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setGroupID(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aLN = null;
    } else {
      this.aLN = paramString;
    }
  }
  
  public void setOperationName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aLR = null;
    } else {
      this.aLR = paramString;
    }
  }
  
  public void setObjectType(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aLS = null;
    } else {
      this.aLS = paramString;
    }
  }
  
  public void setObjectID(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aLM = null;
    } else {
      this.aLM = paramString;
    }
  }
  
  public void setPeriod(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aLO = null;
    } else {
      this.aLO = paramString;
    }
  }
  
  public String getMaxLimit()
  {
    String str = null;
    Entitlement localEntitlement = null;
    Object localObject = null;
    Limit localLimit = null;
    int i = 0;
    localEntitlement = new Entitlement();
    localEntitlement.setOperationName(this.aLR);
    localEntitlement.setObjectType(this.aLS);
    localEntitlement.setObjectId(this.aLM);
    try
    {
      i = Integer.parseInt(this.aLO);
    }
    catch (Exception localException)
    {
      i = 0;
    }
    Iterator localIterator = this.aLQ.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if ((jdMethod_else(localLimit, localEntitlement)) && (jdMethod_int(localLimit.getPeriod(), i))) {
        if (localObject == null) {
          localObject = localLimit;
        } else {
          localObject = jdMethod_char((Limit)localObject, localLimit);
        }
      }
    }
    str = localObject == null ? null : ((Limit)localObject).getData();
    return str;
  }
  
  private void n(int paramInt)
  {
    Limit localLimit = null;
    Iterator localIterator = this.aLQ.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if ((localLimit.getGroupId() == paramInt) && (localLimit.getMemberType() == null) && (localLimit.getMemberSubType() == null) && (localLimit.getMemberId() == null)) {
        localIterator.remove();
      }
    }
  }
  
  private void jdMethod_new(EntitlementGroupMember paramEntitlementGroupMember)
  {
    Limit localLimit = null;
    Iterator localIterator = this.aLQ.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if (paramEntitlementGroupMember.equals(localLimit.getMember())) {
        localIterator.remove();
      }
    }
  }
  
  private static Limit jdMethod_char(Limit paramLimit1, Limit paramLimit2)
  {
    if ((paramLimit1 == null) || (paramLimit1.getAmount() == null)) {
      return paramLimit2;
    }
    if ((paramLimit2 == null) || (paramLimit2.getAmount() == null)) {
      return paramLimit1;
    }
    if (paramLimit1.getAmount().compareTo(paramLimit2.getAmount()) > 0) {
      return paramLimit2;
    }
    return paramLimit1;
  }
  
  private boolean jdMethod_else(Limit paramLimit, Entitlement paramEntitlement)
  {
    boolean bool = false;
    if ((paramLimit == null) || (paramEntitlement == null)) {
      bool = false;
    } else if (((paramLimit.getLimitName() == null) || (paramLimit.getLimitName().equals(paramEntitlement.getOperationName())) || (g(paramLimit.getLimitName(), paramEntitlement.getOperationName()))) && ((paramLimit.getObjectType() == null) || (paramLimit.getObjectType().equals(paramEntitlement.getObjectType()))) && ((paramLimit.getObjectId() == null) || (paramLimit.getObjectId().equals(paramEntitlement.getObjectId())))) {
      bool = true;
    }
    return bool;
  }
  
  private boolean jdMethod_int(int paramInt1, int paramInt2)
  {
    boolean bool = false;
    switch (paramInt2)
    {
    case 4: 
      bool = paramInt1 == 4;
      break;
    case 3: 
      bool = (paramInt1 == 4) || (paramInt1 == 3);
      break;
    case 2: 
      bool = (paramInt1 == 4) || (paramInt1 == 3) || (paramInt1 == 2);
      break;
    case 1: 
      bool = (paramInt1 == 4) || (paramInt1 == 3) || (paramInt1 == 2) || (paramInt1 == 1);
      break;
    default: 
      bool = false;
    }
    return bool;
  }
  
  private boolean g(String paramString1, String paramString2)
  {
    boolean bool = false;
    LimitTypePropertyList localLimitTypePropertyList = this.aLP.getByOperationName(paramString2);
    String str = null;
    int i = 0;
    int j = localLimitTypePropertyList.numPropertyValues("control parent");
    while ((!bool) && (i < j))
    {
      str = localLimitTypePropertyList.getPropertyValue("control parent", i);
      bool = str.equals(paramString1);
      i++;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.GetMaxLimits
 * JD-Core Version:    0.7.0.1
 */