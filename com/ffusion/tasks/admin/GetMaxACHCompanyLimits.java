package com.ffusion.tasks.admin;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
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

public class GetMaxACHCompanyLimits
  extends BaseTask
  implements AdminTask
{
  private String acU;
  private String acY;
  private String ac2;
  private int acX;
  private String ac3;
  private String acW;
  private String ac1;
  private String ac4;
  private static final int acV = 0;
  private static final int ac0 = 1;
  private static final int acT = 2;
  private static final int acZ = 3;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.acX == 0)
    {
      this.error = 4524;
      return this.taskErrorURL;
    }
    EntitlementGroupMember localEntitlementGroupMember = null;
    if (this.acW != null)
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setId(this.ac3);
      localEntitlementGroupMember.setMemberType(this.acW);
      localEntitlementGroupMember.setMemberSubType(this.ac1);
      localEntitlementGroupMember.setEntitlementGroupId(this.acX);
    }
    if ((this.acU == null) || (this.acU.equals("")))
    {
      this.error = 95;
      return this.taskErrorURL;
    }
    ArrayList localArrayList1 = (ArrayList)localHttpSession.getAttribute(this.acU);
    if (localArrayList1 == null)
    {
      this.error = 4558;
      return this.taskErrorURL;
    }
    if (this.acY == null)
    {
      this.error = 4550;
      return this.taskErrorURL;
    }
    if (this.ac2 == null)
    {
      this.error = 4551;
      return this.taskErrorURL;
    }
    Limits localLimits = null;
    try
    {
      if (localEntitlementGroupMember == null) {
        localLimits = Entitlements.getCumulativeLimits(this.acX);
      } else {
        localLimits = Entitlements.getCumulativeLimits(localEntitlementGroupMember);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator1 = localArrayList1.iterator();
    while (localIterator1.hasNext())
    {
      localObject = (ArrayList)localIterator1.next();
      localArrayList2.add(d((String)((ArrayList)localObject).get(0), (String)((ArrayList)localObject).get(1)));
    }
    jdMethod_for(localEntitlementGroupMember, localLimits, localArrayList2);
    Object localObject = new HashMap();
    HashMap localHashMap = new HashMap();
    Iterator localIterator2 = localArrayList2.iterator();
    while (localIterator2.hasNext())
    {
      String str2 = (String)localIterator2.next();
      jdMethod_for(str2, this.ac4, localLimits, localArrayList1, (HashMap)localObject, localHashMap);
    }
    localHttpSession.setAttribute(this.acY, localObject);
    localHttpSession.setAttribute(this.ac2, localHashMap);
    return str1;
  }
  
  private static void jdMethod_for(String paramString1, String paramString2, Limits paramLimits, ArrayList paramArrayList, HashMap paramHashMap1, HashMap paramHashMap2)
  {
    if (paramHashMap1.containsKey(paramString1)) {
      return;
    }
    a locala = new a(paramLimits, paramString1, paramString2);
    Limit localLimit1 = locala.jdMethod_if();
    Limit localLimit2 = locala.a();
    ArrayList localArrayList = jdMethod_for(paramString1, paramArrayList);
    String[] arrayOfString = jdMethod_try(localArrayList);
    for (int i = 0; i < arrayOfString.length; i++)
    {
      String str = arrayOfString[i];
      jdMethod_for(str, paramString2, paramLimits, paramArrayList, paramHashMap1, paramHashMap2);
      localLimit1 = jdMethod_case(localLimit1, (Limit)paramHashMap1.get(str));
      localLimit2 = jdMethod_case(localLimit2, (Limit)paramHashMap2.get(str));
    }
    paramHashMap1.put(paramString1, localLimit1);
    paramHashMap2.put(paramString1, localLimit2);
  }
  
  private static String[] jdMethod_try(ArrayList paramArrayList)
  {
    if (paramArrayList == null) {
      return new String[0];
    }
    String str = (String)paramArrayList.get(1);
    ArrayList localArrayList1 = (ArrayList)paramArrayList.get(2);
    ArrayList localArrayList2 = (ArrayList)paramArrayList.get(3);
    int i = localArrayList1 == null ? 0 : localArrayList1.size();
    int j = localArrayList2 == null ? 0 : localArrayList2.size();
    String[] arrayOfString = new String[i + j];
    int k = 0;
    int m = 0;
    while (m < i)
    {
      arrayOfString[k] = d((String)localArrayList1.get(m), str);
      m++;
      k++;
    }
    m = 0;
    while (m < j)
    {
      arrayOfString[k] = d((String)localArrayList2.get(m), str);
      m++;
      k++;
    }
    return arrayOfString;
  }
  
  private static String d(String paramString1, String paramString2)
  {
    return paramString1 + " (" + paramString2 + ")";
  }
  
  private static ArrayList jdMethod_for(String paramString, ArrayList paramArrayList)
  {
    if (paramString == null) {
      return null;
    }
    ArrayList localArrayList = null;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      localArrayList = (ArrayList)localIterator.next();
      String str1 = (String)localArrayList.get(0);
      String str2 = (String)localArrayList.get(1);
      if ((str1 != null) && (str2 != null))
      {
        String str3 = d(str1, str2);
        if (str3.equals(paramString)) {
          return localArrayList;
        }
      }
    }
    return null;
  }
  
  private static Limit jdMethod_case(Limit paramLimit1, Limit paramLimit2)
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
  
  private void jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits, ArrayList paramArrayList)
  {
    Iterator localIterator = paramLimits.iterator();
    int i = paramEntitlementGroupMember != null ? 1 : 0;
    boolean bool = false;
    Entitlement localEntitlement = new Entitlement();
    if ((this.ac4 != null) && (!this.ac4.equals("")))
    {
      localEntitlement.setObjectType("ACHCompany");
      localEntitlement.setObjectId(this.ac4);
    }
    while (localIterator.hasNext())
    {
      Limit localLimit = (Limit)localIterator.next();
      if (i != 0) {
        bool = paramEntitlementGroupMember.equals(localLimit.getMember());
      } else {
        bool = localLimit.getGroupId() == this.acX;
      }
      if (bool) {
        if ((this.ac4 != null) && (!this.ac4.equals("")))
        {
          localEntitlement.setOperationName(localLimit.getLimitName());
          if ((paramArrayList.contains(localLimit.getLimitName())) && (localLimit.getEntitlement().equals(localEntitlement))) {
            localIterator.remove();
          }
        }
        else if (paramArrayList.contains(localLimit.getLimitName()))
        {
          localIterator.remove();
        }
      }
    }
  }
  
  private static boolean jdMethod_byte(Limit paramLimit, Entitlement paramEntitlement)
  {
    if ((paramLimit.getLimitName() != null) && (!paramLimit.getLimitName().equals(paramEntitlement.getOperationName()))) {
      return false;
    }
    if ((paramLimit.getObjectType() != null) && (!paramLimit.getObjectType().equals(paramEntitlement.getObjectType()))) {
      return false;
    }
    return (paramLimit.getObjectId() == null) || (paramLimit.getObjectId().equals(paramEntitlement.getObjectId()));
  }
  
  public void setPerTransactionMapName(String paramString)
  {
    this.acY = paramString;
  }
  
  public String getPerTransactionMapName()
  {
    return this.acY;
  }
  
  public void setPerDayMapName(String paramString)
  {
    this.ac2 = paramString;
  }
  
  public String getPerDayMapName()
  {
    return this.ac2;
  }
  
  public void setLimitInfoListSessionName(String paramString)
  {
    this.acU = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.acX = Integer.parseInt(paramString);
  }
  
  public void setMemberId(String paramString)
  {
    this.ac3 = paramString;
  }
  
  public void setMemberType(String paramString)
  {
    this.acW = paramString;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.ac1 = paramString;
  }
  
  public void setACHCompanyID(String paramString)
  {
    this.ac4 = paramString;
  }
  
  private static class a
  {
    private Limit jdField_if;
    private Limit a;
    
    public a(Limits paramLimits, String paramString1, String paramString2)
    {
      Entitlement localEntitlement = new Entitlement();
      localEntitlement.setOperationName(paramString1);
      if ((paramString2 != null) && (!paramString2.equals("")))
      {
        localEntitlement.setObjectType("ACHCompany");
        localEntitlement.setObjectId(paramString2);
      }
      Iterator localIterator = paramLimits.iterator();
      while (localIterator.hasNext())
      {
        Limit localLimit = (Limit)localIterator.next();
        if (GetMaxACHCompanyLimits.jdMethod_byte(localLimit, localEntitlement)) {
          switch (localLimit.getPeriod())
          {
          case 1: 
            this.jdField_if = GetMaxACHCompanyLimits.jdMethod_case(this.jdField_if, localLimit);
            break;
          case 2: 
            this.a = GetMaxACHCompanyLimits.jdMethod_case(this.a, localLimit);
          }
        }
      }
    }
    
    public Limit jdField_if()
    {
      return this.jdField_if;
    }
    
    public Limit a()
    {
      return this.a;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetMaxACHCompanyLimits
 * JD-Core Version:    0.7.0.1
 */