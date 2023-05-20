package com.ffusion.efs.adapters.entitlements;

import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Entitlements;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.util.ReadWriteLock;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class EntitlementCacheManager
{
  private int a;
  private int jdField_char;
  private HashMap jdField_for;
  private HashMap jdField_long;
  private String[] jdField_new;
  private String[] jdField_int;
  private static final String jdField_try = "*";
  public static final int NO_MAX_SIZE = 0;
  private static final double jdField_case = 0.1D;
  private static final boolean jdField_goto = false;
  public static final int ENT_FALSE = 0;
  public static final int ENT_TRUE = 1;
  public static final int ENT_NOENTITLEMENT = 2;
  private HashMap jdField_if = new HashMap();
  private boolean jdField_do;
  private ReadWriteLock jdField_byte = new ReadWriteLock();
  private int jdField_else;
  
  public EntitlementCacheManager(HashMap paramHashMap1, HashMap paramHashMap2, int paramInt, boolean paramBoolean)
  {
    this.jdField_for = new HashMap(paramHashMap1);
    this.jdField_long = new HashMap(paramHashMap2);
    this.jdField_do = paramBoolean;
    this.a = this.jdField_for.size();
    this.jdField_char = this.jdField_long.size();
    this.jdField_new = new String[this.a];
    this.jdField_int = new String[this.jdField_char];
    a(this.jdField_new, this.jdField_for);
    a(this.jdField_int, this.jdField_long);
    this.jdField_else = paramInt;
  }
  
  public int checkEntitlement(int paramInt, Entitlement paramEntitlement)
    throws EntitlementException
  {
    String str1 = paramEntitlement.getOperationName();
    String str2 = paramEntitlement.getObjectType();
    String str3 = paramEntitlement.getObjectId();
    if (!a(str1, str2, str3)) {
      throw new EntitlementException(16);
    }
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_for(paramInt);
      if ((localb == null) || (!localb.jdField_do))
      {
        i = 2;
        return i;
      }
      jdField_do("checkEntitlements for group " + paramInt);
      int i = a(localb, paramEntitlement);
      return i;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public int checkEntitlement(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws EntitlementException
  {
    String str1 = paramEntitlement.getOperationName();
    String str2 = paramEntitlement.getObjectType();
    String str3 = paramEntitlement.getObjectId();
    if (!a(str1, str2, str3)) {
      throw new EntitlementException(16);
    }
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_do(paramEntitlementGroupMember);
      if ((localb == null) || (!localb.jdField_do))
      {
        i = 2;
        return i;
      }
      int i = a(localb, paramEntitlement);
      return i;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  private int a(b paramb, Entitlement paramEntitlement)
    throws EntitlementException
  {
    String str1 = paramEntitlement.getOperationName();
    String str2 = paramEntitlement.getObjectType();
    String str3 = paramEntitlement.getObjectId();
    if (paramb.jdField_else)
    {
      jdField_do("all off");
      return 0;
    }
    if ((str1 != null) && (paramb.jdField_int.get(a(str1))))
    {
      jdField_do(str1 + " is fully off");
      return 0;
    }
    if (str2 != null)
    {
      if (!a(paramb, jdField_if(str2), str3)) {
        return 0;
      }
      if (str1 != null)
      {
        int i = this.jdField_char + jdField_if(str2) * this.a + a(str1);
        return a(paramb, i, str3) ? 1 : 0;
      }
    }
    return 1;
  }
  
  private boolean a(b paramb, int paramInt, String paramString)
  {
    HashSet localHashSet = paramb.jdField_for[paramInt];
    jdField_do("looked up position " + paramInt);
    jdField_do("array entry is null: " + (localHashSet == null));
    if ((localHashSet != null) && ((localHashSet.contains("*")) || (localHashSet.contains(paramString))))
    {
      jdField_do("either null or contains obj_id");
      return false;
    }
    return true;
  }
  
  private boolean a(String paramString1, String paramString2, String paramString3)
  {
    if (paramString2 == null)
    {
      if ((paramString1 == null) || (paramString3 != null)) {
        return false;
      }
    }
    else if (jdField_if(paramString2) == -1) {
      return false;
    }
    return (paramString1 == null) || (a(paramString1) != -1);
  }
  
  public Entitlements getRestrictedEntitlements(int paramInt)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_for(paramInt);
      if ((localb == null) || (!localb.jdField_do))
      {
        localEntitlements = null;
        return localEntitlements;
      }
      Entitlements localEntitlements = jdField_do(localb);
      return localEntitlements;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public Entitlements getRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_do(paramEntitlementGroupMember);
      if ((localb == null) || (!localb.jdField_do))
      {
        localEntitlements = null;
        return localEntitlements;
      }
      Entitlements localEntitlements = jdField_do(localb);
      return localEntitlements;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  private Entitlements jdField_do(b paramb)
  {
    Entitlements localEntitlements = new Entitlements();
    if (paramb.jdField_else) {
      localEntitlements.add(new Entitlement(null, null, null));
    }
    int i = paramb.jdField_int.size();
    for (int j = 0; j < i; j++) {
      if (paramb.jdField_int.get(j)) {
        localEntitlements.add(new Entitlement(a(j), null, null));
      }
    }
    Object localObject1;
    Object localObject2;
    for (j = 0; j < this.jdField_char; j++)
    {
      HashSet localHashSet = paramb.jdField_for[j];
      if (localHashSet != null)
      {
        localObject1 = localHashSet.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (String)((Iterator)localObject1).next();
          if (((String)localObject2).equals("*")) {
            localObject2 = null;
          }
          localEntitlements.add(new Entitlement(null, jdField_do(j), (String)localObject2));
        }
      }
    }
    for (j = 0; j < this.jdField_char; j++) {
      for (int k = 0; k < this.a; k++)
      {
        localObject1 = paramb.jdField_for[(this.jdField_char + j * this.a + k)];
        if (localObject1 != null)
        {
          localObject2 = ((HashSet)localObject1).iterator();
          while (((Iterator)localObject2).hasNext())
          {
            String str = (String)((Iterator)localObject2).next();
            if (str.equals("*")) {
              str = null;
            }
            localEntitlements.add(new Entitlement(a(k), jdField_do(j), str));
          }
        }
      }
    }
    return localEntitlements;
  }
  
  public void setRestrictedEntitlements(int paramInt, Entitlements paramEntitlements)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_int(paramInt);
      a(localb);
      a(localb, paramEntitlements);
      localb.jdField_do = true;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void setRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_if(paramEntitlementGroupMember);
      a(localb);
      a(localb, paramEntitlements);
      localb.jdField_do = true;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void addRestrictedEntitlements(int paramInt, Entitlements paramEntitlements)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_for(paramInt);
      if ((localb == null) || (!localb.jdField_do)) {
        return;
      }
      a(localb, paramEntitlements);
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void addRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_do(paramEntitlementGroupMember);
      if ((localb == null) || (!localb.jdField_do)) {
        return;
      }
      a(localb, paramEntitlements);
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  private void a(b paramb, Entitlements paramEntitlements)
  {
    for (int i = 0; i < paramEntitlements.size(); i++)
    {
      Entitlement localEntitlement = (Entitlement)paramEntitlements.get(i);
      String str1 = localEntitlement.getOperationName();
      String str2 = localEntitlement.getObjectType();
      String str3 = localEntitlement.getObjectId();
      if (str1 == null)
      {
        if (str2 == null) {
          paramb.jdField_else = true;
        } else {
          jdField_if(paramb, jdField_if(str2), str3);
        }
      }
      else if (str2 == null) {
        paramb.jdField_int.set(a(str1));
      } else {
        jdField_if(paramb, this.jdField_char + jdField_if(str2) * this.a + a(str1), str3);
      }
    }
  }
  
  public void removeRestrictedEntitlements(int paramInt, Entitlements paramEntitlements)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_for(paramInt);
      if (localb == null) {
        return;
      }
      if (localb.jdField_do)
      {
        a(localb);
        localb.jdField_do = false;
      }
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void removeRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, Entitlements paramEntitlements)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_do(paramEntitlementGroupMember);
      if (localb == null) {
        return;
      }
      if (localb.jdField_do)
      {
        a(localb);
        localb.jdField_do = false;
      }
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void modifyRestrictedEntitlement(int paramInt, Entitlement paramEntitlement1, Entitlement paramEntitlement2)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_for(paramInt);
      if (localb == null) {
        return;
      }
      if (localb.jdField_do)
      {
        a(localb);
        localb.jdField_do = false;
      }
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void modifyRestrictedEntitlement(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement1, Entitlement paramEntitlement2)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_do(paramEntitlementGroupMember);
      if (localb == null) {
        return;
      }
      if (localb.jdField_do)
      {
        a(localb);
        localb.jdField_do = false;
      }
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public Limits getGroupLimits(int paramInt)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_for(paramInt);
      if (localb == null)
      {
        localLimits = null;
        return localLimits;
      }
      if (localb.jdField_new == null)
      {
        localLimits = null;
        return localLimits;
      }
      Limits localLimits = (Limits)localb.jdField_new.clone();
      return localLimits;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public Limits getCompressedLimits(int paramInt)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_for(paramInt);
      if (localb == null)
      {
        localLimits = null;
        return localLimits;
      }
      if (localb.jdField_case == null)
      {
        localLimits = null;
        return localLimits;
      }
      Limits localLimits = (Limits)localb.jdField_case.clone();
      return localLimits;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_do(paramEntitlementGroupMember);
      if (localb == null)
      {
        localLimits = null;
        return localLimits;
      }
      if (localb.jdField_new == null)
      {
        localLimits = null;
        return localLimits;
      }
      Limits localLimits = (Limits)localb.jdField_new.clone();
      return localLimits;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_do(paramEntitlementGroupMember);
      if (localb == null)
      {
        localLimits = null;
        return localLimits;
      }
      if (localb.jdField_case == null)
      {
        localLimits = null;
        return localLimits;
      }
      Limits localLimits = (Limits)localb.jdField_case.clone();
      return localLimits;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void addGroupLimits(int paramInt, Limits paramLimits)
  {
    Limits localLimits = (Limits)paramLimits.clone();
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_int(paramInt);
      localb.jdField_new = localLimits;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void addCompressedLimits(int paramInt, Limits paramLimits)
  {
    Limits localLimits = (Limits)paramLimits.clone();
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_int(paramInt);
      localb.jdField_case = localLimits;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void addGroupLimits(EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits)
  {
    Limits localLimits = (Limits)paramLimits.clone();
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_if(paramEntitlementGroupMember);
      localb.jdField_new = localLimits;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void addCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits)
  {
    Limits localLimits = (Limits)paramLimits.clone();
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_if(paramEntitlementGroupMember);
      localb.jdField_case = localLimits;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void addGroupLimit(Limit paramLimit)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = null;
      if (paramLimit.getMember() == null)
      {
        int i = paramLimit.getGroupId();
        localb = jdField_for(i);
      }
      else
      {
        localb = jdField_do(paramLimit.getMember());
      }
      if ((localb == null) || (localb.jdField_new == null)) {
        return;
      }
      localb.jdField_new.add((Limit)paramLimit.clone());
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void deleteGroupLimit(Limit paramLimit)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = null;
      if (paramLimit.getMember() == null)
      {
        int i = paramLimit.getGroupId();
        localb = jdField_for(i);
      }
      else
      {
        localb = jdField_do(paramLimit.getMember());
      }
      if (localb != null) {
        localb.jdField_new = null;
      }
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void modifyGroupLimit(Limit paramLimit)
  {
    deleteGroupLimit(paramLimit);
  }
  
  public boolean limitExists(Limit paramLimit)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = null;
      int i;
      if (paramLimit.getMember() == null)
      {
        i = paramLimit.getGroupId();
        localb = jdField_for(i);
      }
      else
      {
        localb = jdField_do(paramLimit.getMember());
      }
      if ((localb == null) || (localb.jdField_new == null))
      {
        i = 0;
        return i;
      }
      for (int j = 0; j < localb.jdField_new.size(); j++)
      {
        Limit localLimit = (Limit)localb.jdField_new.get(j);
        if (localLimit.isLimitInfoIdentical(paramLimit))
        {
          boolean bool = true;
          return bool;
        }
      }
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
    return false;
  }
  
  public void addEntitlementGroup(EntitlementGroup paramEntitlementGroup)
  {
    EntitlementGroup localEntitlementGroup = (EntitlementGroup)paramEntitlementGroup.clone();
    this.jdField_byte.getWriteLock();
    try
    {
      b localb = jdField_int(paramEntitlementGroup.getGroupId());
      localb.a = localEntitlementGroup;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void deleteEntitlementGroup(int paramInt)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      jdField_if(paramInt);
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void modifyEntitlementGroup(EntitlementGroup paramEntitlementGroup)
  {
    addEntitlementGroup(paramEntitlementGroup);
  }
  
  public EntitlementGroup getEntitlementGroup(int paramInt)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_for(paramInt);
      if (localb == null)
      {
        localEntitlementGroup = null;
        return localEntitlementGroup;
      }
      if (localb.a == null)
      {
        localEntitlementGroup = null;
        return localEntitlementGroup;
      }
      EntitlementGroup localEntitlementGroup = (EntitlementGroup)localb.a.clone();
      return localEntitlementGroup;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public void deleteEntitlementGroupMember(EntitlementGroupMember paramEntitlementGroupMember)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      a(paramEntitlementGroupMember);
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public boolean isGroupInCache(int paramInt)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_for(paramInt);
      if (localb == null)
      {
        bool = false;
        return bool;
      }
      if (localb.a == null)
      {
        bool = false;
        return bool;
      }
      boolean bool = true;
      return bool;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  public boolean isGroupMemberInCache(EntitlementGroupMember paramEntitlementGroupMember)
  {
    this.jdField_byte.getReadLock();
    try
    {
      b localb = jdField_do(paramEntitlementGroupMember);
      if (localb == null)
      {
        bool = false;
        return bool;
      }
      if (localb.jdField_char == null)
      {
        bool = false;
        return bool;
      }
      boolean bool = true;
      return bool;
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  private void jdField_if(b paramb, int paramInt, String paramString)
  {
    HashSet localHashSet = paramb.jdField_for[paramInt];
    if (localHashSet == null)
    {
      localHashSet = new HashSet();
      paramb.jdField_for[paramInt] = localHashSet;
    }
    localHashSet.add(paramString == null ? "*" : paramString);
  }
  
  private b jdField_int(int paramInt)
  {
    String str = Integer.toString(paramInt);
    b localb = (b)this.jdField_if.get(str);
    if (localb == null)
    {
      localb = new b();
      this.jdField_if.put(str, localb);
      localb.jdField_byte = System.currentTimeMillis();
      a();
    }
    else if (this.jdField_do)
    {
      localb.jdField_byte = System.currentTimeMillis();
    }
    return localb;
  }
  
  private b jdField_if(EntitlementGroupMember paramEntitlementGroupMember)
  {
    b localb = (b)this.jdField_if.get(paramEntitlementGroupMember);
    if (localb == null)
    {
      localb = new b();
      EntitlementGroupMember localEntitlementGroupMember = (EntitlementGroupMember)paramEntitlementGroupMember.clone();
      this.jdField_if.put(localEntitlementGroupMember, localb);
      localb.jdField_char = localEntitlementGroupMember;
      localb.jdField_byte = System.currentTimeMillis();
      a();
    }
    else if (this.jdField_do)
    {
      localb.jdField_byte = System.currentTimeMillis();
    }
    return localb;
  }
  
  private void a()
  {
    if ((this.jdField_else == 0) || (this.jdField_if.size() < this.jdField_else)) {
      return;
    }
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.jdField_if.keySet().iterator();
    while (localIterator.hasNext())
    {
      a locala = new a(null);
      locala.jdField_if = localIterator.next();
      locala.a = ((b)this.jdField_if.get(locala.jdField_if));
      localArrayList.add(locala);
    }
    Collections.sort(localArrayList);
    int i = (int)(this.jdField_else * 0.9D);
    while (this.jdField_if.size() > i)
    {
      b localb1 = ((a)localArrayList.get(0)).a;
      b localb2;
      if (localb1.jdField_char != null)
      {
        localb2 = (b)this.jdField_if.get(Integer.toString(localb1.jdField_char.getEntitlementGroupId()));
        if ((localb2 != null) && (localb2.jdField_try != null)) {
          localb2.jdField_try.remove(localb1.jdField_char);
        }
      }
      else if (localb1.a != null)
      {
        localb2 = (b)this.jdField_if.get(Integer.toString(localb1.a.getParentId()));
        if ((localb2 != null) && (localb2.jdField_if != null)) {
          localb2.jdField_if.remove(Integer.toString(localb1.a.getGroupId()));
        }
        a(localb1, localArrayList);
      }
      this.jdField_if.remove(((a)localArrayList.get(0)).jdField_if);
      localArrayList.remove(0);
    }
  }
  
  private void a(b paramb, ArrayList paramArrayList)
  {
    Iterator localIterator;
    Object localObject;
    if (paramb.jdField_if != null)
    {
      localIterator = paramb.jdField_if.iterator();
      while (localIterator.hasNext())
      {
        localObject = (String)localIterator.next();
        b localb = (b)this.jdField_if.get(localObject);
        if (localb != null) {
          a(localb, paramArrayList);
        }
        int j = paramArrayList.indexOf(localObject);
        if (j != -1) {
          paramArrayList.remove(j);
        }
        this.jdField_if.remove(localObject);
      }
    }
    if (paramb.jdField_try != null)
    {
      localIterator = paramb.jdField_try.iterator();
      while (localIterator.hasNext())
      {
        localObject = (EntitlementGroupMember)localIterator.next();
        int i = paramArrayList.indexOf(localObject);
        if (i != -1) {
          paramArrayList.remove(i);
        }
        this.jdField_if.remove(localObject);
      }
    }
  }
  
  public ArrayList getChildGroups(int paramInt)
  {
    b localb = jdField_for(paramInt);
    if (localb == null) {
      return null;
    }
    if (localb.jdField_if == null) {
      localb.jdField_if = new ArrayList();
    }
    return localb.jdField_if;
  }
  
  public ArrayList getGroupMembers(int paramInt)
  {
    b localb = jdField_for(paramInt);
    if (localb == null) {
      return null;
    }
    if (localb.jdField_try == null) {
      localb.jdField_try = new ArrayList();
    }
    return localb.jdField_try;
  }
  
  private b jdField_for(int paramInt)
  {
    b localb = (b)this.jdField_if.get(Integer.toString(paramInt));
    if ((localb != null) && (this.jdField_do)) {
      localb.jdField_byte = System.currentTimeMillis();
    }
    return localb;
  }
  
  private b jdField_do(EntitlementGroupMember paramEntitlementGroupMember)
  {
    b localb = (b)this.jdField_if.get(paramEntitlementGroupMember);
    if ((localb != null) && (this.jdField_do)) {
      localb.jdField_byte = System.currentTimeMillis();
    }
    return localb;
  }
  
  private void jdField_if(int paramInt)
  {
    this.jdField_if.remove(Integer.toString(paramInt));
  }
  
  private void a(EntitlementGroupMember paramEntitlementGroupMember)
  {
    this.jdField_if.remove(paramEntitlementGroupMember);
  }
  
  public void cleanup(long paramLong)
  {
    this.jdField_byte.getWriteLock();
    try
    {
      Iterator localIterator = this.jdField_if.keySet().iterator();
      long l = System.currentTimeMillis();
      while (localIterator.hasNext())
      {
        b localb = (b)this.jdField_if.get(localIterator.next());
        if (l - localb.jdField_byte > paramLong) {
          localIterator.remove();
        }
      }
    }
    finally
    {
      this.jdField_byte.releaseLock();
    }
  }
  
  private void a(String[] paramArrayOfString, HashMap paramHashMap)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Integer localInteger = (Integer)paramHashMap.get(str);
      paramArrayOfString[localInteger.intValue()] = str;
    }
  }
  
  private void jdField_do(String paramString) {}
  
  private void jdField_if(b paramb)
  {
    paramb.jdField_int = new BitSet(this.a);
    paramb.jdField_for = new HashSet[this.jdField_char + this.a * this.jdField_char];
  }
  
  private void a(b paramb)
  {
    if (paramb.jdField_int == null)
    {
      jdField_if(paramb);
    }
    else
    {
      for (int i = 0; i < paramb.jdField_int.size(); i++) {
        paramb.jdField_int.clear(i);
      }
      for (i = 0; i < paramb.jdField_for.length; i++) {
        paramb.jdField_for[i] = null;
      }
    }
  }
  
  private String a(int paramInt)
  {
    return this.jdField_new[paramInt];
  }
  
  private String jdField_do(int paramInt)
  {
    return this.jdField_int[paramInt];
  }
  
  private int a(String paramString)
  {
    Integer localInteger = (Integer)this.jdField_for.get(paramString);
    return localInteger == null ? -1 : localInteger.intValue();
  }
  
  private int jdField_if(String paramString)
  {
    Integer localInteger = (Integer)this.jdField_long.get(paramString);
    return localInteger == null ? -1 : localInteger.intValue();
  }
  
  private class a
    implements Comparable
  {
    public Object jdField_if;
    public EntitlementCacheManager.b a;
    
    private a() {}
    
    public int compareTo(Object paramObject)
    {
      if (this.a.jdField_byte < ((a)paramObject).a.jdField_byte) {
        return -1;
      }
      if (this.a.jdField_byte > ((a)paramObject).a.jdField_byte) {
        return 1;
      }
      return 0;
    }
    
    public boolean equals(Object paramObject)
    {
      return this.jdField_if.equals(paramObject);
    }
    
    a(EntitlementCacheManager.1 param1)
    {
      this();
    }
  }
  
  private class b
  {
    public EntitlementGroup a;
    public EntitlementGroupMember jdField_char;
    public Limits jdField_new;
    public Limits jdField_case;
    public boolean jdField_else;
    public BitSet jdField_int;
    public HashSet[] jdField_for;
    public boolean jdField_do;
    public long jdField_byte;
    public ArrayList jdField_if;
    public ArrayList jdField_try;
    
    public b() {}
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.EntitlementCacheManager
 * JD-Core Version:    0.7.0.1
 */