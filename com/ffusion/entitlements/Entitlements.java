package com.ffusion.entitlements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Entitlements
  extends ArrayList
  implements EntitlementCodes
{
  private String N8;
  private String N4 = String.valueOf(1);
  private ArrayList N2;
  private Entitlement N1;
  private Limit N3;
  private Entitlements N5;
  private EntitlementService N9;
  private boolean N6 = true;
  private HashMap Ob = new HashMap();
  private static final String N7 = "ID";
  private static final String Oa = "TP";
  
  protected Entitlements() {}
  
  public Entitlements(String paramString1, String paramString2, EntitlementService paramEntitlementService, Entitlements paramEntitlements)
  {
    this.N8 = paramString1;
    this.N4 = paramString2;
    this.N9 = paramEntitlementService;
    if ((paramEntitlements != null) && (paramString1.equals(paramEntitlements.getUserId())) && (paramString2.equals(paramEntitlements.getUserType()))) {
      this.N5 = null;
    } else {
      this.N5 = paramEntitlements;
    }
  }
  
  public boolean addAll(Collection paramCollection)
  {
    return false;
  }
  
  public boolean addAll(int paramInt, Collection paramCollection)
  {
    return false;
  }
  
  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof Entitlement))
    {
      ((Entitlement)paramObject).setEntService(this.N9);
      if ((this.N6) || (jdMethod_int((Entitlement)paramObject) == 0)) {
        return super.add(paramObject);
      }
    }
    return false;
  }
  
  public void add(int paramInt, Object paramObject)
  {
    if ((paramObject instanceof Entitlement))
    {
      ((Entitlement)paramObject).setEntService(this.N9);
      if ((this.N6) || (jdMethod_int((Entitlement)paramObject) == 0)) {
        super.add(paramInt, paramObject);
      }
    }
  }
  
  public boolean remove(String paramString)
  {
    return remove(get(paramString));
  }
  
  public boolean remove(Object paramObject)
  {
    if (((paramObject instanceof Entitlement)) && ((this.N6) || (jdMethod_for((Entitlement)paramObject) == 0))) {
      return super.remove(paramObject);
    }
    return false;
  }
  
  public Object remove(int paramInt)
  {
    return super.remove(paramInt);
  }
  
  public int deleteLimit(String paramString1, String paramString2)
  {
    int i = jdMethod_void(paramString1, paramString2);
    if (i == 0)
    {
      Entitlement localEntitlement = get(paramString2);
      localEntitlement.deleteLimitByName(paramString1);
    }
    return i;
  }
  
  public int modifyLimit(Limit paramLimit, String paramString)
  {
    int i = jdMethod_for(paramLimit, paramString);
    if (i == 0)
    {
      Limit localLimit = (Limit)this.Ob.get(paramLimit.getId());
      if ((localLimit == null) && (jdMethod_for(paramLimit, paramString) == 0))
      {
        deleteLimit(paramLimit.getName(), paramString);
        jdMethod_int(paramLimit, get(paramString));
      }
      else
      {
        localLimit.set(paramLimit);
      }
    }
    return i;
  }
  
  private void jdMethod_for(Limit paramLimit, Entitlement paramEntitlement)
  {
    Limit localLimit = paramEntitlement.getLimitByName(paramLimit.getName());
    if (localLimit == null)
    {
      paramEntitlement.addLimit(paramLimit);
    }
    else
    {
      paramEntitlement.deleteLimitByName(paramLimit.getName());
      paramLimit.setId(localLimit.getId());
      paramEntitlement.addLimit(paramLimit);
    }
  }
  
  private int jdMethod_for(Entitlement paramEntitlement)
  {
    if (this.N5 == null) {
      return 14000;
    }
    if ((paramEntitlement.getName() == null) || (paramEntitlement.getName().equals(""))) {
      return 14107;
    }
    if (paramEntitlement == null) {
      return 14103;
    }
    if (!r(paramEntitlement.getName())) {
      return 14000;
    }
    return 0;
  }
  
  private int jdMethod_int(Entitlement paramEntitlement)
  {
    int i = jdMethod_for(paramEntitlement);
    if (i != 0) {
      return i;
    }
    Entitlement localEntitlement = this.N5.get(paramEntitlement.getName());
    ArrayList localArrayList = localEntitlement.getLimits();
    Limit localLimit1;
    Limit localLimit2;
    for (int j = 0; j < localArrayList.size(); j++)
    {
      localLimit1 = (Limit)localArrayList.get(j);
      localLimit2 = paramEntitlement.getLimitByName(localLimit1.getName());
      if ((localLimit2 != null) && (localLimit1.isLimitExceeded(localLimit2))) {
        return 14000;
      }
    }
    for (j = 0; j < localArrayList.size(); j++)
    {
      localLimit1 = (Limit)localArrayList.get(j);
      localLimit2 = paramEntitlement.getLimitByName(localLimit1.getName());
      if (localLimit2 != null)
      {
        modifyLimit(localLimit2, paramEntitlement.getName());
      }
      else
      {
        Limit localLimit3 = localLimit1.copy();
        jdMethod_int(localLimit3, paramEntitlement);
      }
    }
    return i;
  }
  
  private int jdMethod_void(String paramString1, String paramString2)
  {
    int i = 0;
    if (this.N5 == null) {
      return 14000;
    }
    if (paramString1 == null) {
      return 14110;
    }
    if (paramString2 == null) {
      return 14107;
    }
    i = isEntitled(paramString2);
    if (i != 0) {
      return 14000;
    }
    Entitlement localEntitlement = this.N5.get(paramString2);
    if ((localEntitlement != null) && (localEntitlement.getLimitByName(paramString1) != null)) {
      return 14000;
    }
    return 0;
  }
  
  private int jdMethod_for(Limit paramLimit, String paramString)
  {
    int i = 0;
    if (this.N5 == null) {
      return 14000;
    }
    if (paramLimit == null) {
      return 14003;
    }
    if (paramString == null) {
      return 14107;
    }
    if ((paramLimit.getName() == null) || (paramLimit.getName().equals(""))) {
      return 14110;
    }
    i = isEntitled(paramString);
    if (i != 0) {
      return 14000;
    }
    if (!r(paramString)) {
      return 14000;
    }
    Entitlement localEntitlement = this.N5.get(paramString);
    Limit localLimit = localEntitlement.getLimitByName(paramLimit.getName());
    if ((localLimit != null) && (localLimit.isLimitExceeded(paramLimit))) {
      return 14001;
    }
    return 0;
  }
  
  public int addLimit(Limit paramLimit, String paramString)
  {
    int i = jdMethod_for(paramLimit, paramString);
    if ((this.N6) || (i == 0))
    {
      Entitlement localEntitlement = get(paramString);
      jdMethod_int(paramLimit, localEntitlement);
    }
    return i;
  }
  
  private void jdMethod_int(Limit paramLimit, Entitlement paramEntitlement)
  {
    if ((paramLimit != null) && (paramEntitlement != null))
    {
      jdMethod_for(paramLimit);
      if (this.Ob.get(paramLimit.getId()) != null) {
        paramLimit = (Limit)this.Ob.get(paramLimit.getId());
      } else {
        this.Ob.put(paramLimit.getId(), paramLimit);
      }
      paramEntitlement.addLimit(paramLimit);
    }
  }
  
  private void C()
  {
    Object localObject1 = null;
    Iterator localIterator = this.Ob.values().iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof Limit)) {
        jdMethod_for((Limit)localObject2);
      }
    }
  }
  
  private void jdMethod_new(Entitlement paramEntitlement)
  {
    Object localObject1 = null;
    Iterator localIterator = paramEntitlement.getLimits().iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof Limit)) {
        jdMethod_for((Limit)localObject2);
      }
    }
  }
  
  private void jdMethod_for(Limit paramLimit)
  {
    String str = paramLimit.getId();
    if (str.indexOf("ID") != -1) {
      str = str.substring(0, str.indexOf("ID"));
    }
    str = str + "ID" + getUserId() + "TP" + getUserType();
    paramLimit.setId(str);
    if (this.Ob.get(str) == null) {
      this.Ob.put(str, paramLimit);
    }
  }
  
  public void copy(Entitlements paramEntitlements)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    paramEntitlements.clear();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localObject1 = localIterator.next();
      if ((localObject1 instanceof Entitlement)) {
        paramEntitlements.add(((Entitlement)localObject1).copy());
      }
    }
  }
  
  public String getUserId()
  {
    return this.N8;
  }
  
  public String getUserType()
  {
    return this.N4;
  }
  
  public Entitlement get(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      if (paramString.equals(localEntitlement.getName())) {
        return localEntitlement;
      }
    }
    return null;
  }
  
  public Entitlement getById(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      if (localEntitlement.getId().equals(paramString)) {
        return localEntitlement;
      }
    }
    return null;
  }
  
  public Limit getLimitById(String paramString1, String paramString2)
  {
    Entitlement localEntitlement = get(paramString1);
    if (localEntitlement != null) {
      return localEntitlement.getLimitById(paramString2);
    }
    return null;
  }
  
  public Limit getLimitByName(String paramString1, String paramString2)
  {
    Entitlement localEntitlement = get(paramString1);
    if (localEntitlement != null) {
      return localEntitlement.getLimitByName(paramString2);
    }
    return null;
  }
  
  public int isEntitled(String paramString, Limitable paramLimitable)
  {
    int i = 0;
    this.N2 = new ArrayList();
    Entitlement localEntitlement = get(paramString);
    if (localEntitlement == null)
    {
      i = 14000;
    }
    else
    {
      i = localEntitlement.getExceededLimits(paramLimitable, this.N2);
      int j = this.N2.size();
      if ((i == 0) && ((paramLimitable instanceof ValueLimitable))) {
        i = localEntitlement.updateRunningTotals((ValueLimitable)paramLimitable);
      }
    }
    return i;
  }
  
  public int rollbackRunningTotals(String paramString, Limitable paramLimitable)
  {
    int i = 0;
    Entitlement localEntitlement = get(paramString);
    if (localEntitlement == null) {
      i = 14000;
    } else if ((paramLimitable instanceof ValueLimitable)) {
      i = localEntitlement.rollbackRunningTotals((ValueLimitable)paramLimitable);
    }
    return i;
  }
  
  private boolean r(String paramString)
  {
    return this.N5.get(paramString) != null;
  }
  
  public int isEntitled(String paramString)
  {
    Entitlement localEntitlement = get(paramString);
    if (localEntitlement == null) {
      return 14000;
    }
    return 0;
  }
  
  public void setCurrentEntitlement(String paramString)
  {
    this.N1 = null;
    if (paramString == null) {
      return;
    }
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      if (localEntitlement.getName().equals(paramString))
      {
        this.N1 = localEntitlement;
        return;
      }
    }
  }
  
  public boolean getCurrentEntitlement()
  {
    return this.N1 != null;
  }
  
  public void setCurrentLimit(String paramString)
  {
    this.N3 = null;
    if ((paramString == null) || (this.N1 == null)) {
      return;
    }
    this.N3 = this.N1.getLimitByName(paramString);
  }
  
  public String getCurrentLimitValue()
  {
    if ((this.N3 != null) && (this.N3.getLimitValue() != null)) {
      return this.N3.getLimitValue();
    }
    return "";
  }
  
  public ArrayList getExceededLimits()
  {
    return this.N2;
  }
  
  public void setInitialize()
  {
    this.N6 = false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.Entitlements
 * JD-Core Version:    0.7.0.1
 */