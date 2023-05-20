package com.ffusion.csil.core;

import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementAdmin;
import com.ffusion.csil.beans.entitlements.EntitlementAdmins;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyList;
import com.ffusion.csil.beans.entitlements.LimitTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyList;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.efs.adapters.entitlementsReporting.EntitlementReportingAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.db.DBCookie;
import com.ffusion.util.db.ReopenableDBCookie;
import com.ffusion.util.entitlements.ParentEntitlementsCache;
import com.ffusion.util.logging.PerfLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Properties;

public class Entitlements
  extends Initialize
{
  private static final String av5 = "DB_PROPERTIES";
  private static EntitlementCachedAdapter av6;
  private static EntitlementReportingAdapter av7;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
    HashMap localHashMap = new HashMap();
    localHashMap.put("DB_PROPERTIES", localProperties);
    try
    {
      av6 = new EntitlementCachedAdapter(localHashMap, true);
      av7 = new EntitlementReportingAdapter(av6);
      ParentEntitlementsCache.initialize(av6.getEntitlementTypesWithProperties(new HashMap()));
    }
    catch (Exception localException)
    {
      throw new CSILException(-1007, localException);
    }
  }
  
  public static EntitlementGroup getEntitlementGroup(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementGroup";
    EntitlementGroup localEntitlementGroup;
    try
    {
      localEntitlementGroup = av6.getEntitlementGroup(paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlementGroup;
  }
  
  public static EntitlementGroup getEntitlementGroupNoCache(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementGroupNoCache";
    EntitlementGroup localEntitlementGroup;
    try
    {
      localEntitlementGroup = av6.getEntitlementGroupNoCache(paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlementGroup;
  }
  
  public static boolean checkEntitlement(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws CSILException
  {
    String str = "Entitlements.checkEntitlement";
    try
    {
      return av6.checkEntitlement(paramEntitlementGroupMember, paramEntitlement);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static boolean checkEntitlementUnsafe(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws CSILException
  {
    String str = "Entitlements.checkEntitlement";
    try
    {
      return av6.checkEntitlementUnsafe(paramEntitlementGroupMember, paramEntitlement);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Entitlement checkEntitlement(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement)
    throws CSILException
  {
    String str = "Entitlements.checkEntitlement";
    try
    {
      return av6.checkEntitlement(paramEntitlementGroupMember, paramMultiEntitlement);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static boolean checkEntitlement(int paramInt, Entitlement paramEntitlement)
    throws CSILException
  {
    String str = "Entitlements.checkEntitlement";
    try
    {
      return av6.checkEntitlement(paramInt, paramEntitlement);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Entitlement checkEntitlement(int paramInt, MultiEntitlement paramMultiEntitlement)
    throws CSILException
  {
    String str = "Entitlements.checkEntitlement";
    try
    {
      return av6.checkEntitlement(paramInt, paramMultiEntitlement);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements getCumulativeEntitlements(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeEntitlements";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements;
    try
    {
      localEntitlements = av6.getCumulativeEntitlements(paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlements;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements getCumulativeEntitlements(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeEntitlements";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements;
    try
    {
      localEntitlements = av6.getCumulativeEntitlements(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlements;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements getCumulativeEntitlementsNoCache(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeEntitlements";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements;
    try
    {
      localEntitlements = av6.getCumulativeEntitlementsNoCache(paramEntitlementGroupMember);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlements;
  }
  
  static com.ffusion.csil.beans.entitlements.Entitlements h(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeEntitlements";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements;
    try
    {
      localEntitlements = av6.getCumulativeEntitlementsNoCache(paramInt);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlements;
  }
  
  public static EntitlementGroups getTopEntitlementGroups()
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getTopEntitlementGroups";
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      localEntitlementGroups = av6.getTopEntitlementGroups();
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlementGroups;
  }
  
  public static EntitlementGroups getTopEntitlementGroupsBySvcBureau(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getTopEntitlementGroupsBySvcBureau";
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      localEntitlementGroups = av6.getTopEntitlementGroupsBySvcBureau(paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlementGroups;
  }
  
  public static EntitlementGroup addEntitlementGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addEntitlementGroup";
    try
    {
      EntitlementGroup localEntitlementGroup = av6.addEntitlementGroup(paramEntitlementGroupMember, paramEntitlementGroup);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Add entitlement group '");
      localStringBuffer.append(paramEntitlementGroup.getGroupName());
      localStringBuffer.append("'.");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
      return localEntitlementGroup;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static void jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup)
    throws CSILException
  {
    jdMethod_for(paramEntitlementGroupMember, paramEntitlementGroup, false);
  }
  
  public static void modifyEntitlementGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup)
    throws CSILException
  {
    jdMethod_for(paramEntitlementGroupMember, paramEntitlementGroup, true);
  }
  
  private static void jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup, boolean paramBoolean)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.modifyEntitlementGroup";
    try
    {
      av6.modifyEntitlementGroup(paramEntitlementGroupMember, paramEntitlementGroup, paramBoolean);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Modify entitlement group '");
      localStringBuffer.append(paramEntitlementGroup.getGroupName());
      localStringBuffer.append("'.");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void deleteEntitlementGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementGroup paramEntitlementGroup)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.deleteEntitlementGroup";
    try
    {
      av6.deleteEntitlementGroup(paramEntitlementGroupMember, paramEntitlementGroup);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Delete entitlement group '");
      localStringBuffer.append(paramEntitlementGroup.getGroupName());
      localStringBuffer.append("'.");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements getRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getRestrictedEntitlements";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    try
    {
      localEntitlements = av6.getRestrictedEntitlements(paramEntitlementGroupMember, paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlements;
  }
  
  static com.ffusion.csil.beans.entitlements.Entitlements g(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getRestrictedEntitlementsUnsafe";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    try
    {
      localEntitlements = av6.getRestrictedEntitlementsUnsafe(paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlements;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements getRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getRestrictedEntitlements";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    try
    {
      localEntitlements = av6.getRestrictedEntitlements(paramEntitlementGroupMember1, paramEntitlementGroupMember2);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlements;
  }
  
  static com.ffusion.csil.beans.entitlements.Entitlements jdMethod_int(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getRestrictedEntitlementsUnsafe";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = null;
    try
    {
      localEntitlements = av6.getRestrictedEntitlementsUnsafe(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localEntitlements;
  }
  
  public static void setRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.setRestrictedEntitlements";
    try
    {
      av6.setRestrictedEntitlements(paramEntitlementGroupMember, paramInt, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Set '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' as a restricted entitlement for group with ID ");
        localStringBuffer.append(paramInt);
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void setRestrictedEntitlementsUnsafe(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.setRestrictedEntitlements";
    try
    {
      av6.setRestrictedEntitlementsUnsafe(paramEntitlementGroupMember, paramInt, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Set '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' as a restricted entitlement for group with ID ");
        localStringBuffer.append(paramInt);
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void setRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.setRestrictedEntitlements";
    try
    {
      av6.setRestrictedEntitlements(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Set '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' as a restricted entitlement for group member with ID ");
        localStringBuffer.append(paramEntitlementGroupMember2.getId());
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember1, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void setRestrictedEntitlementsUnsafe(EntitlementGroupMember paramEntitlementGroupMember, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.setRestrictedEntitlementsUnsafe";
    try
    {
      av6.setRestrictedEntitlementsUnsafe(paramEntitlementGroupMember, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Set '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' as a restricted entitlement for group member with ID ");
        localStringBuffer.append(paramEntitlementGroupMember.getId());
        localStringBuffer.append(".");
        debug(localStringBuffer.toString());
      }
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void addRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addRestrictedEntitlements";
    try
    {
      av6.addRestrictedEntitlements(paramEntitlementGroupMember, paramInt, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Add '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' as a restricted entitlement for group with ID ");
        localStringBuffer.append(paramInt);
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static void addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, boolean paramBoolean)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck";
    try
    {
      av6.addRestrictedEntitlementsUnsafeNoDupCheck(paramEntitlementGroupMember, paramInt, paramEntitlements, paramBoolean);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Add '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' as a restricted entitlement for group with ID ");
        localStringBuffer.append(paramInt);
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static void addRestrictedEntitlementsUnsafeNoDupCheck(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, boolean paramBoolean)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck";
    try
    {
      av6.addRestrictedEntitlementsUnsafeNoDupCheck(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramEntitlements, paramBoolean);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Add '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' as a restricted entitlement for group member with ID ");
        localStringBuffer.append(paramEntitlementGroupMember2.getId());
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember1, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void addRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addRestrictedEntitlements";
    try
    {
      av6.addRestrictedEntitlements(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Add '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' as a restricted entitlement for group member with ID ");
        localStringBuffer.append(paramEntitlementGroupMember2.getId());
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember1, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void removeRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.removeRestrictedEntitlements";
    try
    {
      av6.removeRestrictedEntitlements(paramEntitlementGroupMember, paramInt, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Remove restricted entitlement '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' for group with ID ");
        localStringBuffer.append(paramInt);
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static void jdMethod_for(int paramInt, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.removeRestrictedEntitlementsUnsafe";
    try
    {
      av6.removeRestrictedEntitlementsUnsafe(paramInt, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Remove restricted entitlement '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' for group with ID ");
        localStringBuffer.append(paramInt);
        localStringBuffer.append(".");
        debug(localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void removeRestrictedEntitlements(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.removeRestrictedEntitlements";
    try
    {
      av6.removeRestrictedEntitlements(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Remove restricted entitlement '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' for group member with ID ");
        localStringBuffer.append(paramEntitlementGroupMember2.getId());
        localStringBuffer.append(".");
        debug(paramEntitlementGroupMember1, localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static void jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.removeRestrictedEntitlementsUnsafe";
    try
    {
      av6.removeRestrictedEntitlementsUnsafe(paramEntitlementGroupMember, paramEntitlements);
      PerfLog.log(str, l, true);
      ListIterator localListIterator = paramEntitlements.listIterator();
      while (localListIterator.hasNext())
      {
        StringBuffer localStringBuffer = new StringBuffer();
        Entitlement localEntitlement = (Entitlement)localListIterator.next();
        localStringBuffer.append("Remove restricted entitlement '");
        localStringBuffer.append(localEntitlement.getOperationName());
        localStringBuffer.append("' for group member with ID ");
        localStringBuffer.append(paramEntitlementGroupMember.getId());
        localStringBuffer.append(".");
        debug(localStringBuffer.toString());
      }
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroups getChildrenEntitlementGroups(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getChildrenEntitlementGroups";
    try
    {
      EntitlementGroups localEntitlementGroups = av6.getChildrenEntitlementGroups(paramInt);
      PerfLog.log(str, l, true);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroup getChildrenEntitlementGroups(int paramInt, ReopenableDBCookie paramReopenableDBCookie)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getChildrenEntitlementGroups";
    try
    {
      EntitlementGroup localEntitlementGroup = av6.getChildrenEntitlementGroups(paramInt, paramReopenableDBCookie);
      PerfLog.log(str, l, true);
      return localEntitlementGroup;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroups getChildrenByGroupType(int paramInt, String paramString)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getChildrenByGroupType";
    try
    {
      EntitlementGroups localEntitlementGroups = av6.getChildrenByGroupType(paramInt, paramString);
      PerfLog.log(str, l, true);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroup getChildrenByGroupType(int paramInt, String paramString, DBCookie paramDBCookie)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getChildrenByGroupType";
    try
    {
      EntitlementGroup localEntitlementGroup = av6.getChildrenByGroupType(paramInt, paramString, paramDBCookie);
      PerfLog.log(str, l, true);
      return localEntitlementGroup;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroups getEntitlementGroupsByType(String paramString)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementGroupsByType";
    try
    {
      EntitlementGroups localEntitlementGroups = av6.getEntitlementGroupsByType(paramString);
      PerfLog.log(str, l, true);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroups getEntitlementGroupsByTypeAndSvcBureau(String paramString, int paramInt)
    throws CSILException
  {
    String str = "Entitlements.getEntitlementGroupsByTypeAndBureau";
    long l = System.currentTimeMillis();
    try
    {
      EntitlementGroups localEntitlementGroups = av6.getEntitlementGroupsByTypeAndSvcBureau(paramString, paramInt);
      PerfLog.log(str, l, true);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroup getEntitlementGroupByNameAndSvcBureau(String paramString1, String paramString2, int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementGroupByNameAndSvcBureau";
    try
    {
      EntitlementGroup localEntitlementGroup = av6.getEntitlementGroupByNameAndSvcBureau(paramString1, paramString2, paramInt);
      PerfLog.log(str, l, true);
      return localEntitlementGroup;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits getCompressedLimits(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCompressedLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCompressedLimits(paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCumulativeLimits(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCumulativeLimits(paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCompressedLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCompressedLimits(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimit";
    Limits localLimits;
    try
    {
      localLimits = av6.getCumulativeLimits(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCompressedLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCompressedLimits(paramEntitlementGroupMember, paramLimit);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCumulativeLimits(paramEntitlementGroupMember, paramLimit);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCompressedLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCompressedLimits(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramLimit);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCumulativeLimits(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramLimit);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCompressedLimits(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCompressedLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCompressedLimits(paramEntitlementGroupMember, paramInt, paramLimit);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getCumulativeLimits(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getCumulativeLimits(paramEntitlementGroupMember, paramInt, paramLimit);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, Limit paramLimit, HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getGroupLimits(paramEntitlementGroupMember, paramInt, paramLimit, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2, Limit paramLimit, HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getGroupLimits(paramEntitlementGroupMember1, paramEntitlementGroupMember2, paramLimit, paramHashMap);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getGroupLimits(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getGroupLimits(paramInt);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limits getGroupLimits(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupLimits";
    Limits localLimits;
    try
    {
      localLimits = av6.getGroupLimits(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    return localLimits;
  }
  
  public static Limit addGroupLimit(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addGroupLimit";
    try
    {
      Limit localLimit = av6.addGroupLimit(paramEntitlementGroupMember, paramLimit);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Add limit '");
      localStringBuffer.append(paramLimit.getLimitName());
      localStringBuffer.append("' to group with ID ");
      localStringBuffer.append(paramLimit.getGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
      return localLimit;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static Limit jdMethod_for(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addGroupLimit";
    try
    {
      Limit localLimit = av6.addGroupLimitUnsafe(paramEntitlementGroupMember, paramLimit);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Add limit '");
      localStringBuffer.append(paramLimit.getLimitName());
      localStringBuffer.append("' to group with ID ");
      localStringBuffer.append(paramLimit.getGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
      return localLimit;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void deleteGroupLimit(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.deleteGroupLimit";
    try
    {
      av6.deleteGroupLimit(paramEntitlementGroupMember, paramLimit);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Delete limit '");
      localStringBuffer.append(paramLimit.getLimitName());
      localStringBuffer.append("' for group with ID ");
      localStringBuffer.append(paramLimit.getGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static void jdMethod_int(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.deleteGroupLimit";
    try
    {
      av6.deleteGroupLimitUnsafe(paramEntitlementGroupMember, paramLimit);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Delete limit '");
      localStringBuffer.append(paramLimit.getLimitName());
      localStringBuffer.append("' for group with ID ");
      localStringBuffer.append(paramLimit.getGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static void jdMethod_try(Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.deleteGroupLimitUnsafe";
    try
    {
      av6.deleteGroupLimitUnsafe(paramLimit);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Delete limit '");
      localStringBuffer.append(paramLimit.getLimitName());
      localStringBuffer.append("' for group with ID ");
      localStringBuffer.append(paramLimit.getGroupId());
      localStringBuffer.append(".");
      debug(localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limit modifyGroupLimit(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.modifyGroupLimit";
    try
    {
      av6.modifyGroupLimit(paramEntitlementGroupMember, paramLimit);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Modify limit '");
      localStringBuffer.append(paramLimit.getLimitName());
      localStringBuffer.append("' for group with ID ");
      localStringBuffer.append(paramLimit.getGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
      return paramLimit;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static Limit jdMethod_new(EntitlementGroupMember paramEntitlementGroupMember, Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.modifyGroupLimit";
    try
    {
      av6.modifyGroupLimitUnsafe(paramEntitlementGroupMember, paramLimit);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Modify limit '");
      localStringBuffer.append(paramLimit.getLimitName());
      localStringBuffer.append("' for group with ID ");
      localStringBuffer.append(paramLimit.getGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
      return paramLimit;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsDelete";
    try
    {
      av6.checkLimitsDelete(paramEntitlementGroupMember, paramEntitlement, paramFloat, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws CSILException
  {
    checkLimitsDelete(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, Util.getLimitBaseCurrency(), paramDate);
  }
  
  public static void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsDelete";
    try
    {
      av6.checkLimitsDelete(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws CSILException
  {
    checkLimitsDelete(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, Util.getLimitBaseCurrency(), paramDate);
  }
  
  public static void checkLimitsDelete(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsDelete";
    try
    {
      av6.checkLimitsDelete(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.confirmLimitsBeforeAdd";
    try
    {
      return av6.confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramEntitlement, paramFloat, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws CSILException
  {
    return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, Util.getLimitBaseCurrency(), paramDate);
  }
  
  public static Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.confirmLimitsBeforeAdd";
    try
    {
      return av6.confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws CSILException
  {
    return confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, Util.getLimitBaseCurrency(), paramDate);
  }
  
  public static Limits confirmLimitsBeforeAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.confirmLimitsBeforeAdd";
    try
    {
      return av6.confirmLimitsBeforeAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsAdd";
    try
    {
      return av6.checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramFloat, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws CSILException
  {
    return checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, Util.getLimitBaseCurrency(), paramDate);
  }
  
  public static Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsAdd";
    try
    {
      return av6.checkLimitsAdd(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal, paramString, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, Date paramDate)
    throws CSILException
  {
    return checkLimitsAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, Util.getLimitBaseCurrency(), paramDate);
  }
  
  public static Limits checkLimitsAdd(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal, String paramString, Date paramDate)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsAdd";
    try
    {
      return av6.checkLimitsAdd(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal, paramString, paramDate);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, float paramFloat1, float paramFloat2, Date paramDate1, Date paramDate2)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsEdit";
    try
    {
      return av6.checkLimitsEdit(paramEntitlementGroupMember, paramEntitlement, paramFloat1, paramFloat2, paramDate1, paramDate2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2)
    throws CSILException
  {
    return checkLimitsEdit(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, Util.getLimitBaseCurrency(), paramDate1, paramDate2);
  }
  
  public static Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsEdit";
    try
    {
      return av6.checkLimitsEdit(paramEntitlementGroupMember, paramEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, Date paramDate1, Date paramDate2)
    throws CSILException
  {
    return checkLimitsEdit(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, Util.getLimitBaseCurrency(), paramDate1, paramDate2);
  }
  
  public static Limits checkLimitsEdit(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, String paramString, Date paramDate1, Date paramDate2)
    throws CSILException
  {
    String str = "Entitlements.checkLimitsEdit";
    try
    {
      return av6.checkLimitsEdit(paramEntitlementGroupMember, paramMultiEntitlement, paramBigDecimal1, paramBigDecimal2, paramString, paramDate1, paramDate2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroups getGroupsAdministeredBy(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupsAdministeredBy";
    try
    {
      EntitlementGroups localEntitlementGroups = av6.getGroupsAdministeredBy(paramInt);
      PerfLog.log(str, l, true);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroups getGroupsAdministeredBy(int paramInt, String paramString)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupsAdministeredBy";
    try
    {
      EntitlementGroups localEntitlementGroups = av6.getGroupsAdministeredBy(paramInt, paramString);
      PerfLog.log(str, l, true);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroups getGroupsAdministeredBy(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupsAdministeredBy";
    try
    {
      EntitlementGroups localEntitlementGroups = av6.getGroupsAdministeredBy(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroups getAdministratorsFor(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getAdministratorsFor";
    try
    {
      EntitlementGroups localEntitlementGroups = av6.getAdministratorsFor(paramInt);
      PerfLog.log(str, l, true);
      return localEntitlementGroups;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementAdmins getAdminsForGroup(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getAdminsForGroup";
    try
    {
      EntitlementAdmins localEntitlementAdmins = av6.getAdminsForGroup(paramInt);
      PerfLog.log(str, l, true);
      return localEntitlementAdmins;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementAdmins getAdminInfoFor(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getAdminInfoFor";
    try
    {
      EntitlementAdmins localEntitlementAdmins = av6.getAdminInfoFor(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
      return localEntitlementAdmins;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementAdmins getAdminInfoFor(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getAdminInfoFor";
    try
    {
      EntitlementAdmins localEntitlementAdmins = av6.getAdminInfoFor(paramInt);
      PerfLog.log(str, l, true);
      return localEntitlementAdmins;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void addAdministratorGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementAdmin paramEntitlementAdmin)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addAdministratorGroup";
    try
    {
      av6.addAdministratorGroup(paramEntitlementGroupMember, paramEntitlementAdmin);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Add group with ID ");
      localStringBuffer.append(paramEntitlementGroupMember.getEntitlementGroupId());
      localStringBuffer.append(" as an administrator of group with ID ");
      localStringBuffer.append(paramEntitlementAdmin.getTargetGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void setAdministratorGroup(EntitlementGroupMember paramEntitlementGroupMember, int paramInt, EntitlementAdmins paramEntitlementAdmins)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.setAdministratorGroup";
    try
    {
      av6.setAdministratorGroup(paramEntitlementGroupMember, paramInt, paramEntitlementAdmins);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Set new administrative privileges for group with ID ");
      localStringBuffer.append(paramInt);
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void deleteAdministratorGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementAdmin paramEntitlementAdmin)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.deleteAdministratorGroup";
    try
    {
      av6.deleteAdministratorGroup(paramEntitlementGroupMember, paramEntitlementAdmin);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Delete group with ID ");
      localStringBuffer.append(paramEntitlementGroupMember.getEntitlementGroupId());
      localStringBuffer.append(" from the administrators list for group with ID ");
      localStringBuffer.append(paramEntitlementAdmin.getTargetGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void modifyAdministratorGroup(EntitlementGroupMember paramEntitlementGroupMember, EntitlementAdmin paramEntitlementAdmin1, EntitlementAdmin paramEntitlementAdmin2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.modifyAdministratorGroup";
    try
    {
      av6.modifyAdministratorGroup(paramEntitlementGroupMember, paramEntitlementAdmin1, paramEntitlementAdmin2);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Modify group with ID ");
      localStringBuffer.append(paramEntitlementAdmin1.getTargetGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static boolean canAdminister(EntitlementAdmin paramEntitlementAdmin)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.canAdminister";
    try
    {
      boolean bool = av6.canAdminister(paramEntitlementAdmin);
      PerfLog.log(str, l, true);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static boolean canAdministerAnyGroup(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.canAdministerAnyGroup";
    try
    {
      boolean bool = av6.canAdministerAnyGroup(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static boolean canAdministerAnyGroupOfType(EntitlementGroupMember paramEntitlementGroupMember, String paramString)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.canAdministerAnyGroupOfType";
    try
    {
      boolean bool = av6.canAdministerAnyGroupOfType(paramEntitlementGroupMember, paramString);
      PerfLog.log(str, l, true);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static boolean canExtend(EntitlementAdmin paramEntitlementAdmin)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.canExtend";
    try
    {
      boolean bool = av6.canExtend(paramEntitlementAdmin);
      PerfLog.log(str, l, true);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static boolean entitlementGroupExists(String paramString1, String paramString2, int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.entitlementGroupExists";
    try
    {
      boolean bool = av6.entitlementGroupExists(paramString1, paramString2, paramInt);
      PerfLog.log(str, l, true);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static boolean entitlementTypeExists(String paramString)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.entitlementTypeExists";
    try
    {
      boolean bool = av6.entitlementTypeExists(paramString);
      PerfLog.log(str, l, true);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static boolean limitExists(Limit paramLimit)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.limitExists";
    try
    {
      boolean bool = av6.limitExists(paramLimit);
      PerfLog.log(str, l, true);
      return bool;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void addMember(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.addMember";
    try
    {
      av6.addMember(paramEntitlementGroupMember1, paramEntitlementGroupMember2);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Add new member with ID ");
      localStringBuffer.append(paramEntitlementGroupMember2.getId());
      localStringBuffer.append(" to the entitlement group with ID ");
      localStringBuffer.append(paramEntitlementGroupMember2.getEntitlementGroupId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember1, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void modifyMember(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.modifyMember";
    try
    {
      int i = paramEntitlementGroupMember2.getEntitlementGroupId();
      av6.modifyMember(paramEntitlementGroupMember1, paramEntitlementGroupMember2);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Entitlement group member with ID ");
      localStringBuffer.append(paramEntitlementGroupMember2.getId());
      localStringBuffer.append(" is now placed in group with ID ");
      localStringBuffer.append(i);
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember1, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroupMember getMember(EntitlementGroupMember paramEntitlementGroupMember)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getMember";
    try
    {
      EntitlementGroupMember localEntitlementGroupMember = av6.getMember(paramEntitlementGroupMember);
      PerfLog.log(str, l, true);
      return localEntitlementGroupMember;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static EntitlementGroupMembers getMembers(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getMembers";
    try
    {
      EntitlementGroupMembers localEntitlementGroupMembers = av6.getMembers(paramInt);
      PerfLog.log(str, l, true);
      return localEntitlementGroupMembers;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static int getNumMembers(int paramInt)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getNumMembers";
    try
    {
      int i = av6.getNumMembers(paramInt);
      PerfLog.log(str, l, true);
      return i;
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  public static void removeMember(EntitlementGroupMember paramEntitlementGroupMember1, EntitlementGroupMember paramEntitlementGroupMember2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.removeMember";
    try
    {
      av6.removeMember(paramEntitlementGroupMember1, paramEntitlementGroupMember2);
      PerfLog.log(str, l, true);
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("Remove entitlement group member with ID ");
      localStringBuffer.append(paramEntitlementGroupMember2.getId());
      localStringBuffer.append(".");
      debug(paramEntitlementGroupMember1, localStringBuffer.toString());
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static EntitlementCachedAdapter U()
  {
    return av6;
  }
  
  static EntitlementReportingAdapter V()
  {
    return av7;
  }
  
  public static IReportResult getReportData(EntitlementGroupMember paramEntitlementGroupMember, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getReportData";
    if (!av6.verifyMemberExists(paramEntitlementGroupMember)) {
      throw new CSILException(20013);
    }
    IReportResult localIReportResult = null;
    try
    {
      localIReportResult = av7.getReportData(paramEntitlementGroupMember, paramReportCriteria, paramHashMap);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localIReportResult;
  }
  
  public static boolean isObjectInUse(String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.isObjectInUse";
    boolean bool;
    try
    {
      bool = av6.isObjectInUse(paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return bool;
  }
  
  public static ArrayList getEntitlementTypes(String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementTypes1";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getEntitlementTypes(paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static ArrayList getEntitlementTypes(HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementTypes2";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getEntitlementTypes(paramHashMap);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static EntitlementTypePropertyList getEntitlementTypeWithProperties(String paramString)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementTypeWithProperties";
    EntitlementTypePropertyList localEntitlementTypePropertyList;
    try
    {
      localEntitlementTypePropertyList = av6.getEntitlementTypeWithProperties(paramString);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localEntitlementTypePropertyList;
  }
  
  public static EntitlementTypePropertyLists getEntitlementTypesWithProperties(String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementTypesWithProperties1";
    EntitlementTypePropertyLists localEntitlementTypePropertyLists;
    try
    {
      localEntitlementTypePropertyLists = av6.getEntitlementTypesWithProperties(paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localEntitlementTypePropertyLists;
  }
  
  public static EntitlementTypePropertyLists getEntitlementTypesWithProperties(HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getEntitlementTypesWithProperties2";
    EntitlementTypePropertyLists localEntitlementTypePropertyLists;
    try
    {
      localEntitlementTypePropertyLists = av6.getEntitlementTypesWithProperties(paramHashMap);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localEntitlementTypePropertyLists;
  }
  
  public static ArrayList getRestrictedEntitlementTypes(int paramInt, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getRestrictedEntitlementTypes1";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getRestrictedEntitlementTypes(paramInt, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static EntitlementTypePropertyLists getRestrictedEntitlementTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getRestrictedEntitlementTypesWithProperties1";
    EntitlementTypePropertyLists localEntitlementTypePropertyLists;
    try
    {
      localEntitlementTypePropertyLists = av6.getRestrictedEntitlementTypesWithProperties(paramInt, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localEntitlementTypePropertyLists;
  }
  
  public static ArrayList getRestrictedEntitlementTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getRestrictedEntitlementTypes2";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getRestrictedEntitlementTypes(paramEntitlementGroupMember, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static EntitlementTypePropertyLists getRestrictedEntitlementTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getRestrictedEntitlementTypesWithProperties2";
    EntitlementTypePropertyLists localEntitlementTypePropertyLists;
    try
    {
      localEntitlementTypePropertyLists = av6.getRestrictedEntitlementTypesWithProperties(paramEntitlementGroupMember, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localEntitlementTypePropertyLists;
  }
  
  public static ArrayList getCumulativeEntitlementTypes(int paramInt, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeEntitlementTypes1";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getCumulativeEntitlementTypes(paramInt, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static EntitlementTypePropertyLists getCumulativeEntitlementTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeEntitlementTypesWithProperties1";
    EntitlementTypePropertyLists localEntitlementTypePropertyLists;
    try
    {
      localEntitlementTypePropertyLists = av6.getCumulativeEntitlementTypesWithProperties(paramInt, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localEntitlementTypePropertyLists;
  }
  
  public static ArrayList getCumulativeEntitlementTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeEntitlementTypes2";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getCumulativeEntitlementTypes(paramEntitlementGroupMember, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static EntitlementTypePropertyLists getCumulativeEntitlementTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeEntitlementTypesWithProperties2";
    EntitlementTypePropertyLists localEntitlementTypePropertyLists;
    try
    {
      localEntitlementTypePropertyLists = av6.getCumulativeEntitlementTypesWithProperties(paramEntitlementGroupMember, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localEntitlementTypePropertyLists;
  }
  
  public static ArrayList getLimitTypes(String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getLimitTypes1";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getLimitTypes(paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static ArrayList getLimitTypes(HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getLimitTypes2";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getLimitTypes(paramHashMap);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static LimitTypePropertyList getLimitTypeWithProperties(String paramString)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getLimitTypeWithProperties";
    LimitTypePropertyList localLimitTypePropertyList;
    try
    {
      localLimitTypePropertyList = av6.getLimitTypeWithProperties(paramString);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localLimitTypePropertyList;
  }
  
  public static LimitTypePropertyLists getLimitTypesWithProperties(String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getLimitTypesWithProperties1";
    LimitTypePropertyLists localLimitTypePropertyLists;
    try
    {
      localLimitTypePropertyLists = av6.getLimitTypesWithProperties(paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localLimitTypePropertyLists;
  }
  
  public static LimitTypePropertyLists getLimitTypesWithProperties(HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getLimitTypesWithProperties2";
    LimitTypePropertyLists localLimitTypePropertyLists;
    try
    {
      localLimitTypePropertyLists = av6.getLimitTypesWithProperties(paramHashMap);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localLimitTypePropertyLists;
  }
  
  public static ArrayList getGroupLimitTypes(int paramInt, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupLimitTypes1";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getGroupLimitTypes(paramInt, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static LimitTypePropertyLists getGroupLimitTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupLimitTypesWithProperties1";
    LimitTypePropertyLists localLimitTypePropertyLists;
    try
    {
      localLimitTypePropertyLists = av6.getGroupLimitTypesWithProperties(paramInt, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localLimitTypePropertyLists;
  }
  
  public static ArrayList getGroupLimitTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupLimitTypes2";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getGroupLimitTypes(paramEntitlementGroupMember, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static LimitTypePropertyLists getGroupLimitTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getGroupLimitTypesWithProperties2";
    LimitTypePropertyLists localLimitTypePropertyLists;
    try
    {
      localLimitTypePropertyLists = av6.getGroupLimitTypesWithProperties(paramEntitlementGroupMember, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localLimitTypePropertyLists;
  }
  
  public static ArrayList getCumulativeLimitTypes(int paramInt, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimitTypes1";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getCumulativeLimitTypes(paramInt, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static LimitTypePropertyLists getCumulativeLimitTypesWithProperties(int paramInt, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimitTypesWithProperties1";
    LimitTypePropertyLists localLimitTypePropertyLists;
    try
    {
      localLimitTypePropertyLists = av6.getCumulativeLimitTypesWithProperties(paramInt, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localLimitTypePropertyLists;
  }
  
  public static ArrayList getCumulativeLimitTypes(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimitTypes2";
    ArrayList localArrayList;
    try
    {
      localArrayList = av6.getCumulativeLimitTypes(paramEntitlementGroupMember, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static LimitTypePropertyLists getCumulativeLimitTypesWithProperties(EntitlementGroupMember paramEntitlementGroupMember, String paramString1, String paramString2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getCumulativeLimitTypesWithProperties2";
    LimitTypePropertyLists localLimitTypePropertyLists;
    try
    {
      localLimitTypePropertyLists = av6.getCumulativeLimitTypesWithProperties(paramEntitlementGroupMember, paramString1, paramString2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localLimitTypePropertyLists;
  }
  
  public static ObjectTypePropertyList getObjectTypeWithProperties(String paramString)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getObjectTypeWithProperties";
    ObjectTypePropertyList localObjectTypePropertyList;
    try
    {
      localObjectTypePropertyList = av6.getObjectTypeWithProperties(paramString);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localObjectTypePropertyList;
  }
  
  public static com.ffusion.csil.beans.entitlements.Entitlements getPositiveEntitlements(EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.getPositiveEntitlements";
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    try
    {
      localEntitlements = av6.getPositiveEntitlements(paramEntitlementGroupMember, paramHashMap1, paramHashMap2);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
    return localEntitlements;
  }
  
  public static void cleanup(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    long l = System.currentTimeMillis();
    String str = "Entitlements.cleanup";
    try
    {
      av6.cleanup(paramInt1, paramInt2, paramHashMap);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
    PerfLog.log(str, l, true);
  }
  
  public static int cleanupCacheChanges(long paramLong)
    throws CSILException
  {
    String str = "Entitlements.cleanupCacheChanges";
    try
    {
      return av6.cleanupCacheChanges(paramLong);
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(str, i(localEntitlementException.code), localEntitlementException);
    }
    catch (Exception localException)
    {
      throw new CSILException(20006, localException);
    }
  }
  
  static int i(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
      return 20013;
    case 3: 
      return 20012;
    case 16: 
      return 20009;
    case 17: 
      return 20010;
    case 18: 
      return 20007;
    case 19: 
      return 20019;
    case 20: 
      return 20018;
    case 21: 
      return 20017;
    case 22: 
      return 20014;
    case 23: 
      return 20015;
    case 24: 
      return 20016;
    case 26: 
      return 20028;
    case 27: 
      return 20029;
    case 28: 
      return 20030;
    case 29: 
      return 20031;
    case 30: 
      return 20032;
    case 31: 
      return 20033;
    case 32: 
      return 20034;
    case 35: 
      return 20035;
    }
    return 20006;
  }
  
  static void T()
  {
    av6.acquireWriteLock();
  }
  
  static void S()
    throws CSILException
  {
    try
    {
      av6.releaseWriteLock();
    }
    catch (EntitlementException localEntitlementException)
    {
      throw new CSILException(i(localEntitlementException.code), localEntitlementException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Entitlements
 * JD-Core Version:    0.7.0.1
 */