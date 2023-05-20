package com.ffusion.tasks.admin;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMaxCrossAccountLimits
  extends BaseTask
  implements AdminTask
{
  protected String _listName;
  protected int _groupId;
  protected boolean _isServicePackage = false;
  protected String _memberId;
  protected String _memberType;
  protected String _memberSubType;
  protected EntitlementGroupMember _member;
  protected String _transactionMapName;
  protected String _dayMapName;
  protected String _weekMapName;
  protected String _monthMapName;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this._listName);
    if (this._groupId == 0)
    {
      this.error = 4524;
      return this.taskErrorURL;
    }
    if (this._memberType != null)
    {
      this._member = new EntitlementGroupMember();
      this._member.setMemberType(this._memberType);
      this._member.setMemberSubType(this._memberSubType);
      this._member.setId(this._memberId);
      this._member.setEntitlementGroupId(this._groupId);
    }
    if (localEntitlementTypePropertyLists1 == null)
    {
      this.error = 4549;
      return this.taskErrorURL;
    }
    if (this._transactionMapName == null)
    {
      this.error = 4550;
      return this.taskErrorURL;
    }
    if (this._dayMapName == null)
    {
      this.error = 4551;
      return this.taskErrorURL;
    }
    if (this._weekMapName == null)
    {
      this.error = 4552;
      return this.taskErrorURL;
    }
    if (this._monthMapName == null)
    {
      this.error = 4553;
      return this.taskErrorURL;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = localEntitlementTypePropertyLists1.iterator();
    while (localIterator1.hasNext()) {
      localArrayList.add(((EntitlementTypePropertyList)localIterator1.next()).getOperationName());
    }
    Limits localLimits = null;
    try
    {
      if (this._member == null) {
        localLimits = Entitlements.getCumulativeLimits(this._groupId);
      } else {
        localLimits = Entitlements.getCumulativeLimits(this._member);
      }
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      return this.serviceErrorURL;
    }
    if (!this._isServicePackage) {
      jdMethod_int(localLimits, localArrayList);
    }
    EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = null;
    try
    {
      localEntitlementTypePropertyLists2 = Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      return this.serviceErrorURL;
    }
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    HashMap localHashMap3 = new HashMap();
    HashMap localHashMap4 = new HashMap();
    Iterator localIterator2 = localArrayList.iterator();
    while (localIterator2.hasNext())
    {
      String str2 = (String)localIterator2.next();
      jdMethod_for(str2, localLimits, localEntitlementTypePropertyLists2, localHashMap1, localHashMap2, localHashMap3, localHashMap4);
    }
    localHttpSession.setAttribute(this._transactionMapName, localHashMap1);
    localHttpSession.setAttribute(this._dayMapName, localHashMap2);
    localHttpSession.setAttribute(this._weekMapName, localHashMap3);
    localHttpSession.setAttribute(this._monthMapName, localHashMap4);
    return str1;
  }
  
  private static void jdMethod_for(String paramString, Limits paramLimits, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3, HashMap paramHashMap4)
  {
    if (paramHashMap1.containsKey(paramString)) {
      return;
    }
    a locala = new a(paramLimits, paramString);
    Limit localLimit1 = locala.jdMethod_do();
    Limit localLimit2 = locala.a();
    Limit localLimit3 = locala.jdMethod_for();
    Limit localLimit4 = locala.jdMethod_if();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = jdMethod_int(paramEntitlementTypePropertyLists.getByOperationName(paramString), paramEntitlementTypePropertyLists);
    for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
    {
      String str = ((EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i)).getOperationName();
      jdMethod_for(str, paramLimits, paramEntitlementTypePropertyLists, paramHashMap1, paramHashMap2, paramHashMap3, paramHashMap4);
      localLimit1 = jdMethod_int(localLimit1, (Limit)paramHashMap1.get(str));
      localLimit2 = jdMethod_int(localLimit2, (Limit)paramHashMap2.get(str));
      localLimit3 = jdMethod_int(localLimit3, (Limit)paramHashMap3.get(str));
      localLimit4 = jdMethod_int(localLimit4, (Limit)paramHashMap4.get(str));
    }
    paramHashMap1.put(paramString, localLimit1);
    paramHashMap2.put(paramString, localLimit2);
    paramHashMap3.put(paramString, localLimit3);
    paramHashMap4.put(paramString, localLimit4);
  }
  
  private static EntitlementTypePropertyLists jdMethod_int(EntitlementTypePropertyList paramEntitlementTypePropertyList, EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    int i = paramEntitlementTypePropertyList.numPropertyValues("control parent");
    for (int j = 0; j < i; j++)
    {
      String str = paramEntitlementTypePropertyList.getPropertyValue("control parent", j);
      EntitlementTypePropertyList localEntitlementTypePropertyList = paramEntitlementTypePropertyLists.getByOperationName(str);
      localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList);
    }
    return localEntitlementTypePropertyLists;
  }
  
  private static Limit jdMethod_int(Limit paramLimit1, Limit paramLimit2)
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
  
  private void jdMethod_int(Limits paramLimits, ArrayList paramArrayList)
  {
    Iterator localIterator = paramLimits.iterator();
    int i = this._member != null ? 1 : 0;
    boolean bool = false;
    while (localIterator.hasNext())
    {
      Limit localLimit = (Limit)localIterator.next();
      if (i != 0) {
        bool = this._member.equals(localLimit.getMember());
      } else {
        bool = localLimit.getGroupId() == this._groupId;
      }
      if ((bool) && (paramArrayList.contains(localLimit.getLimitName()))) {
        localIterator.remove();
      }
    }
  }
  
  private static boolean jdMethod_int(Limit paramLimit, Entitlement paramEntitlement)
  {
    if ((paramLimit.getLimitName() != null) && (!paramLimit.getLimitName().equals(paramEntitlement.getOperationName()))) {
      return false;
    }
    if ((paramLimit.getObjectType() != null) && (!paramLimit.getObjectType().equals(paramEntitlement.getObjectType()))) {
      return false;
    }
    return (paramLimit.getObjectId() == null) || (paramLimit.getObjectId().equals(paramEntitlement.getObjectId()));
  }
  
  public void setEntitlementTypePropertyListsName(String paramString)
  {
    this._listName = paramString;
  }
  
  public void setMemberId(String paramString)
  {
    this._memberId = paramString;
  }
  
  public String getMemberId()
  {
    return this._memberId;
  }
  
  public void setMemberType(String paramString)
  {
    this._memberType = paramString;
  }
  
  public String getMemberType()
  {
    return this._memberType;
  }
  
  public void setMemberSubType(String paramString)
  {
    this._memberSubType = paramString;
  }
  
  public String getMemberSubType()
  {
    return this._memberSubType;
  }
  
  public void setGroupId(String paramString)
  {
    this._groupId = Integer.parseInt(paramString);
  }
  
  public void setPerTransactionMapName(String paramString)
  {
    this._transactionMapName = paramString;
  }
  
  public String getPerTransactionMapName()
  {
    return this._transactionMapName;
  }
  
  public void setPerDayMapName(String paramString)
  {
    this._dayMapName = paramString;
  }
  
  public String getPerDayMapName()
  {
    return this._dayMapName;
  }
  
  public void setPerWeekMapName(String paramString)
  {
    this._weekMapName = paramString;
  }
  
  public String getPerWeekMapName()
  {
    return this._weekMapName;
  }
  
  public void setPerMonthMapName(String paramString)
  {
    this._monthMapName = paramString;
  }
  
  public String getPerMonthMapName()
  {
    return this._monthMapName;
  }
  
  public void setIsServicePackage(String paramString)
  {
    this._isServicePackage = new Boolean(paramString).booleanValue();
  }
  
  private static class a
  {
    private Limit jdField_for;
    private Limit jdField_if;
    private Limit a;
    private Limit jdField_do;
    
    public a(Limits paramLimits, String paramString)
    {
      Entitlement localEntitlement = new Entitlement();
      localEntitlement.setOperationName(paramString);
      Iterator localIterator = paramLimits.iterator();
      while (localIterator.hasNext())
      {
        Limit localLimit = (Limit)localIterator.next();
        if (GetMaxCrossAccountLimits.jdMethod_int(localLimit, localEntitlement)) {
          switch (localLimit.getPeriod())
          {
          case 1: 
            this.jdField_for = GetMaxCrossAccountLimits.jdMethod_int(this.jdField_for, localLimit);
            break;
          case 2: 
            this.jdField_if = GetMaxCrossAccountLimits.jdMethod_int(this.jdField_if, localLimit);
            break;
          case 3: 
            this.a = GetMaxCrossAccountLimits.jdMethod_int(this.a, localLimit);
            break;
          case 4: 
            this.jdField_do = GetMaxCrossAccountLimits.jdMethod_int(this.jdField_do, localLimit);
          }
        }
      }
    }
    
    public Limit jdField_do()
    {
      return this.jdField_for;
    }
    
    public Limit a()
    {
      return this.jdField_if;
    }
    
    public Limit jdField_for()
    {
      return this.a;
    }
    
    public Limit jdField_if()
    {
      return this.jdField_do;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetMaxCrossAccountLimits
 * JD-Core Version:    0.7.0.1
 */