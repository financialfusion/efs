package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.AccountGroup;
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

public class GetMaxPerAccountLimits
  extends BaseTask
  implements AdminTask
{
  protected String _listName;
  protected String _accountId;
  protected int _businessDirId = -1;
  protected int _groupId;
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
    if (this._accountId == null)
    {
      this.error = 4554;
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
    jdMethod_byte(localLimits, localArrayList);
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
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser.getUserType() == 1) {
      this._businessDirId = localSecureUser.getBusinessID();
    }
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    HashMap localHashMap3 = new HashMap();
    HashMap localHashMap4 = new HashMap();
    Iterator localIterator2 = localArrayList.iterator();
    while (localIterator2.hasNext())
    {
      String str2 = (String)localIterator2.next();
      jdMethod_for(str2, this._accountId, localSecureUser, this._businessDirId, localLimits, localEntitlementTypePropertyLists2, localHashMap1, localHashMap2, localHashMap3, localHashMap4);
    }
    localHttpSession.setAttribute(this._transactionMapName, localHashMap1);
    localHttpSession.setAttribute(this._dayMapName, localHashMap2);
    localHttpSession.setAttribute(this._weekMapName, localHashMap3);
    localHttpSession.setAttribute(this._monthMapName, localHashMap4);
    return str1;
  }
  
  private static void jdMethod_for(String paramString1, String paramString2, SecureUser paramSecureUser, int paramInt, Limits paramLimits, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3, HashMap paramHashMap4)
  {
    if (paramHashMap1.containsKey(paramString1)) {
      return;
    }
    a locala = new a(paramLimits, paramString1, paramString2, paramSecureUser, paramInt);
    Limit localLimit1 = locala.jdMethod_do();
    Limit localLimit2 = locala.a();
    Limit localLimit3 = locala.jdMethod_for();
    Limit localLimit4 = locala.jdMethod_if();
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = jdMethod_byte(paramEntitlementTypePropertyLists.getByOperationName(paramString1), paramEntitlementTypePropertyLists);
    for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
    {
      String str = ((EntitlementTypePropertyList)localEntitlementTypePropertyLists.get(i)).getOperationName();
      jdMethod_for(str, paramString2, paramSecureUser, paramInt, paramLimits, paramEntitlementTypePropertyLists, paramHashMap1, paramHashMap2, paramHashMap3, paramHashMap4);
      localLimit1 = jdMethod_byte(localLimit1, (Limit)paramHashMap1.get(str));
      localLimit2 = jdMethod_byte(localLimit2, (Limit)paramHashMap2.get(str));
      localLimit3 = jdMethod_byte(localLimit3, (Limit)paramHashMap3.get(str));
      localLimit4 = jdMethod_byte(localLimit4, (Limit)paramHashMap4.get(str));
    }
    paramHashMap1.put(paramString1, localLimit1);
    paramHashMap2.put(paramString1, localLimit2);
    paramHashMap3.put(paramString1, localLimit3);
    paramHashMap4.put(paramString1, localLimit4);
  }
  
  private static EntitlementTypePropertyLists jdMethod_byte(EntitlementTypePropertyList paramEntitlementTypePropertyList, EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
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
  
  private static Limit jdMethod_byte(Limit paramLimit1, Limit paramLimit2)
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
  
  private void jdMethod_byte(Limits paramLimits, ArrayList paramArrayList)
  {
    Iterator localIterator = paramLimits.iterator();
    int i = this._member != null ? 1 : 0;
    boolean bool = false;
    Entitlement localEntitlement = new Entitlement();
    localEntitlement.setObjectType("Account");
    localEntitlement.setObjectId(this._accountId);
    while (localIterator.hasNext())
    {
      Limit localLimit = (Limit)localIterator.next();
      if (i != 0) {
        bool = this._member.equals(localLimit.getMember());
      } else {
        bool = localLimit.getGroupId() == this._groupId;
      }
      if (bool)
      {
        localEntitlement.setOperationName(localLimit.getLimitName());
        if ((paramArrayList.contains(localLimit.getLimitName())) && (localLimit.getEntitlement().equals(localEntitlement))) {
          localIterator.remove();
        }
      }
    }
  }
  
  private static boolean jdMethod_for(Limit paramLimit, Entitlement paramEntitlement, ArrayList paramArrayList)
  {
    if ((paramLimit == null) || (paramEntitlement == null)) {
      return false;
    }
    if ((paramLimit.getLimitName() == null) || (paramLimit.getLimitName().equals(paramEntitlement.getOperationName()))) {
      if ((paramLimit.getObjectType() == null) || (paramLimit.getObjectType().equals(paramEntitlement.getObjectType())))
      {
        if ((paramLimit.getObjectId() == null) || (paramLimit.getObjectId().equals(paramEntitlement.getObjectId()))) {
          return true;
        }
      }
      else if ((paramLimit.getObjectType().equals("AccountGroup")) && (paramLimit.getObjectId() != null) && (paramArrayList != null) && (paramArrayList.contains(paramLimit.getObjectId()))) {
        return true;
      }
    }
    return false;
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
  
  public void setAccountId(String paramString)
  {
    this._accountId = paramString;
  }
  
  public String getBusinessDirId()
  {
    return String.valueOf(this._businessDirId);
  }
  
  public void setBusinessDirId(String paramString)
  {
    try
    {
      this._businessDirId = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this._businessDirId = -1;
    }
  }
  
  private static class a
  {
    private Limit jdField_for;
    private Limit jdField_if;
    private Limit a;
    private Limit jdField_do;
    
    public a(Limits paramLimits, String paramString1, String paramString2, SecureUser paramSecureUser, int paramInt)
    {
      Entitlement localEntitlement = new Entitlement();
      localEntitlement.setOperationName(paramString1);
      localEntitlement.setObjectType("Account");
      localEntitlement.setObjectId(paramString2);
      ArrayList localArrayList1 = null;
      try
      {
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.add(paramString2);
        localArrayList1 = AccountGroup.getAccountGroupIds(paramSecureUser, paramInt, localArrayList2, new HashMap());
      }
      catch (CSILException localCSILException) {}
      Iterator localIterator = paramLimits.iterator();
      while (localIterator.hasNext())
      {
        Limit localLimit = (Limit)localIterator.next();
        if (GetMaxPerAccountLimits.jdField_for(localLimit, localEntitlement, localArrayList1)) {
          switch (localLimit.getPeriod())
          {
          case 1: 
            this.jdField_for = GetMaxPerAccountLimits.jdMethod_byte(this.jdField_for, localLimit);
            break;
          case 2: 
            this.jdField_if = GetMaxPerAccountLimits.jdMethod_byte(this.jdField_if, localLimit);
            break;
          case 3: 
            this.a = GetMaxPerAccountLimits.jdMethod_byte(this.a, localLimit);
            break;
          case 4: 
            this.jdField_do = GetMaxPerAccountLimits.jdMethod_byte(this.jdField_do, localLimit);
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
 * Qualified Name:     com.ffusion.tasks.admin.GetMaxPerAccountLimits
 * JD-Core Version:    0.7.0.1
 */