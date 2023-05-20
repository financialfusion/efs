package com.ffusion.tasks.admin;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
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

public class GetMaxAccountAccessLimits
  extends BaseTask
  implements AdminTask
{
  protected String _accountsName;
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
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this._accountsName);
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
    if (localAccounts == null)
    {
      this.error = 39;
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
    Iterator localIterator1 = localAccounts.iterator();
    while (localIterator1.hasNext()) {
      localArrayList.add(EntitlementsUtil.getEntitlementObjectId((Account)localIterator1.next()));
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
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    jdMethod_case(localLimits, localArrayList);
    localIterator1 = localAccounts.iterator();
    Entitlement localEntitlement = new Entitlement();
    localEntitlement.setOperationName("Access");
    localEntitlement.setObjectType("Account");
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    HashMap localHashMap3 = new HashMap();
    HashMap localHashMap4 = new HashMap();
    while (localIterator1.hasNext())
    {
      Account localAccount = (Account)localIterator1.next();
      localEntitlement.setObjectId(EntitlementsUtil.getEntitlementObjectId(localAccount));
      Object localObject1 = null;
      Object localObject2 = null;
      Object localObject3 = null;
      Object localObject4 = null;
      BigDecimal localBigDecimal1 = null;
      BigDecimal localBigDecimal2 = null;
      BigDecimal localBigDecimal3 = null;
      BigDecimal localBigDecimal4 = null;
      Iterator localIterator2 = localLimits.iterator();
      while (localIterator2.hasNext())
      {
        Limit localLimit = (Limit)localIterator2.next();
        if (jdMethod_case(localLimit, localEntitlement)) {
          switch (localLimit.getPeriod())
          {
          case 1: 
            if ((localBigDecimal1 == null) || (localBigDecimal1.compareTo(localLimit.getAmount()) > 0))
            {
              localObject1 = localLimit;
              localBigDecimal1 = localLimit.getAmount();
            }
            break;
          case 2: 
            if ((localBigDecimal2 == null) || (localBigDecimal2.compareTo(localLimit.getAmount()) > 0))
            {
              localObject2 = localLimit;
              localBigDecimal2 = localLimit.getAmount();
            }
            break;
          case 3: 
            if ((localBigDecimal3 == null) || (localBigDecimal3.compareTo(localLimit.getAmount()) > 0))
            {
              localObject3 = localLimit;
              localBigDecimal3 = localLimit.getAmount();
            }
            break;
          case 4: 
            if ((localBigDecimal4 == null) || (localBigDecimal4.compareTo(localLimit.getAmount()) > 0))
            {
              localObject4 = localLimit;
              localBigDecimal4 = localLimit.getAmount();
            }
            break;
          }
        }
      }
      localHashMap1.put(localAccount.getID(), localObject1);
      localHashMap2.put(localAccount.getID(), localObject2);
      localHashMap3.put(localAccount.getID(), localObject3);
      localHashMap4.put(localAccount.getID(), localObject4);
    }
    localHttpSession.setAttribute(this._transactionMapName, localHashMap1);
    localHttpSession.setAttribute(this._dayMapName, localHashMap2);
    localHttpSession.setAttribute(this._weekMapName, localHashMap3);
    localHttpSession.setAttribute(this._monthMapName, localHashMap4);
    return str;
  }
  
  private void jdMethod_case(Limits paramLimits, ArrayList paramArrayList)
  {
    Iterator localIterator1 = paramLimits.iterator();
    int i = this._member != null ? 1 : 0;
    boolean bool = false;
    Entitlement localEntitlement = new Entitlement();
    localEntitlement.setOperationName("Access");
    localEntitlement.setObjectType("Account");
    while (localIterator1.hasNext())
    {
      Limit localLimit = (Limit)localIterator1.next();
      if (i != 0) {
        bool = this._member.equals(localLimit.getMember());
      } else {
        bool = localLimit.getGroupId() == this._groupId;
      }
      if (bool)
      {
        Iterator localIterator2 = paramArrayList.iterator();
        while (localIterator2.hasNext())
        {
          localEntitlement.setObjectId((String)localIterator2.next());
          if (localLimit.getEntitlement().equals(localEntitlement)) {
            localIterator1.remove();
          }
        }
      }
    }
  }
  
  private boolean jdMethod_case(Limit paramLimit, Entitlement paramEntitlement)
  {
    if ((paramLimit.getLimitName() != null) && (!paramLimit.getLimitName().equals(paramEntitlement.getOperationName()))) {
      return false;
    }
    if ((paramLimit.getObjectType() != null) && (!paramLimit.getObjectType().equals(paramEntitlement.getObjectType()))) {
      return false;
    }
    return (paramLimit.getObjectId() == null) || (paramLimit.getObjectId().equals(paramEntitlement.getObjectId()));
  }
  
  public void setAccountsName(String paramString)
  {
    this._accountsName = paramString;
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetMaxAccountAccessLimits
 * JD-Core Version:    0.7.0.1
 */