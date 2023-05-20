package com.ffusion.csil.core;

import com.ffusion.accountgroups.adapter.AccountGroupAdapter;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.cashcon.LocationSearchResult;
import com.ffusion.beans.disbursement.DisbursementAccount;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementExpression;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroupProperties;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.beans.entitlements.TypePropertyList;
import com.ffusion.util.Strings;
import com.ffusion.util.beans.accountgroups.BusinessAccountGroupException;
import com.ffusion.util.entitlements.ParentEntitlementsCache;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class EntitlementsUtil
{
  public static EntitlementGroupMember getEntitlementGroupMember(SecureUser paramSecureUser)
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(Integer.toString(paramSecureUser.getProfileID()));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType(Integer.toString(paramSecureUser.getUserType()));
    localEntitlementGroupMember.setEntitlementGroupId(paramSecureUser.getEntitlementID());
    localEntitlementGroupMember.setBusinessID(paramSecureUser.getBusinessID());
    if (paramSecureUser.getAgent() != null) {
      if (paramSecureUser.getAgent().getProfileID() > 0)
      {
        localEntitlementGroupMember.setAgentID(String.valueOf(paramSecureUser.getAgent().getProfileID()));
        localEntitlementGroupMember.setAgentType(String.valueOf(paramSecureUser.getAgent().getUserType()));
      }
      else
      {
        localEntitlementGroupMember.setAgentID(paramSecureUser.getAgent().getUserName());
      }
    }
    return localEntitlementGroupMember;
  }
  
  public static boolean checkReportTypeEntitlement(SecureUser paramSecureUser, String paramString, boolean paramBoolean)
    throws CSILException
  {
    ReportCriteria localReportCriteria = Reporting.getDefaultReportCriteria(paramString);
    EntitlementExpression localEntitlementExpression = localReportCriteria.getEntitlementExpression();
    if (localEntitlementExpression != null) {
      return a(paramSecureUser, localEntitlementExpression);
    }
    return true;
  }
  
  private static boolean a(SecureUser paramSecureUser, EntitlementExpression paramEntitlementExpression)
    throws CSILException
  {
    int i = paramEntitlementExpression.getOperation();
    Object localObject;
    Iterator localIterator;
    if (i == 1)
    {
      localObject = paramEntitlementExpression.getChildren();
      localIterator = ((ArrayList)localObject).iterator();
      while (localIterator.hasNext()) {
        if (!a(paramSecureUser, (EntitlementExpression)localIterator.next())) {
          return false;
        }
      }
      return true;
    }
    if (i == 0)
    {
      localObject = paramEntitlementExpression.getChildren();
      localIterator = ((ArrayList)localObject).iterator();
      while (localIterator.hasNext()) {
        if (a(paramSecureUser, (EntitlementExpression)localIterator.next())) {
          return true;
        }
      }
      return false;
    }
    if (i == 2)
    {
      localObject = (String)paramEntitlementExpression.getChildren().get(0);
      int j = paramSecureUser.getUserType() == 2 ? 1 : 0;
      if (j != 0) {
        return a(paramSecureUser, (String)localObject);
      }
      Entitlement localEntitlement = new Entitlement((String)localObject, null, null);
      return Entitlements.checkEntitlement(getEntitlementGroupMember(paramSecureUser), localEntitlement);
    }
    throw new CSILException("When evaluating an EntitlementExpression, unsupported operation was encountered.", 20117);
  }
  
  private static boolean a(SecureUser paramSecureUser, String paramString)
    throws CSILException
  {
    String str1 = "checkBCRestrictedEntitlement";
    boolean bool1 = true;
    String str2 = paramString;
    if ((str2 == null) || (str2.equals("")))
    {
      localObject = "Unable to get entitlement for this report.";
      throw new CSILException(str1, 27, (String)localObject);
    }
    Object localObject = getEntitlementGroupMember(paramSecureUser);
    String str3 = null;
    boolean bool2 = false;
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = Entitlements.getRestrictedEntitlements((EntitlementGroupMember)localObject, paramSecureUser.getEntitlementID());
    Iterator localIterator = localEntitlements.iterator();
    while ((bool1) && (localIterator.hasNext()))
    {
      Entitlement localEntitlement = (Entitlement)localIterator.next();
      str3 = localEntitlement.getOperationName();
      bool2 = Strings.areStringsEqual(str2, str3);
      bool1 = !bool2;
    }
    return bool1;
  }
  
  private static String jdMethod_if(String paramString1, String paramString2)
  {
    return paramString1 + '-' + paramString2;
  }
  
  public static String getEntitlementObjectId(Account paramAccount)
  {
    return jdMethod_if(paramAccount.getRoutingNum(), paramAccount.getID());
  }
  
  public static String getEntitlementObjectId(LockboxAccount paramLockboxAccount)
  {
    return jdMethod_if(paramLockboxAccount.getRoutingNumber(), paramLockboxAccount.getAccountID());
  }
  
  public static String getEntitlementObjectId(PPayAccount paramPPayAccount)
  {
    return jdMethod_if(paramPPayAccount.getRoutingNumber(), paramPPayAccount.getAccountID());
  }
  
  public static String getEntitlementObjectId(LocationSearchResult paramLocationSearchResult)
  {
    return paramLocationSearchResult.getLocationBPWID();
  }
  
  public static String getEntitlementObjectId(PPayCheckRecord paramPPayCheckRecord)
  {
    return jdMethod_if(paramPPayCheckRecord.getRoutingNumber(), paramPPayCheckRecord.getAccountID());
  }
  
  public static String getEntitlementObjectId(DisbursementAccount paramDisbursementAccount)
  {
    return jdMethod_if(paramDisbursementAccount.getRoutingNumber(), paramDisbursementAccount.getAccountID());
  }
  
  public static String getEntitlementObjectId(ACHCompany paramACHCompany)
  {
    return paramACHCompany.getCompanyID();
  }
  
  public static String getEntitlementObjectId(BusinessAccountGroup paramBusinessAccountGroup)
  {
    return String.valueOf(paramBusinessAccountGroup.getId());
  }
  
  public static String getEntitlementObjectId(WireTransfer paramWireTransfer)
  {
    return jdMethod_if(paramWireTransfer.getFromAccountRoutingNum(), paramWireTransfer.getFromAccountID());
  }
  
  public static void OBOViewOnlyCheck(SecureUser paramSecureUser)
    throws CSILException
  {
    SecureUser localSecureUser = paramSecureUser.getAgent();
    if ((localSecureUser != null) && (localSecureUser.getViewOnlyOBOValue())) {
      throw new CSILException(20021);
    }
  }
  
  public static boolean removeEntitlementsAndLimitsForObjectUnsafe(int paramInt, String paramString1, String paramString2)
  {
    boolean bool1 = true;
    Object localObject1;
    Object localObject2;
    try
    {
      EntitlementGroups localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(paramInt);
      localObject1 = localEntitlementGroups.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (EntitlementGroup)((Iterator)localObject1).next();
        boolean bool2 = removeEntitlementsAndLimitsForObjectUnsafe(((EntitlementGroup)localObject2).getGroupId(), paramString1, paramString2);
        bool1 &= bool2;
      }
      localObject1 = null;
    }
    catch (CSILException localCSILException1)
    {
      bool1 = false;
    }
    try
    {
      EntitlementGroupMembers localEntitlementGroupMembers = Entitlements.getMembers(paramInt);
      localObject1 = localEntitlementGroupMembers.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (EntitlementGroupMember)((Iterator)localObject1).next();
        Object localObject3;
        Object localObject4;
        try
        {
          Limits localLimits2 = Entitlements.getGroupLimits((EntitlementGroupMember)localObject2);
          localObject3 = localLimits2.iterator();
          while (((Iterator)localObject3).hasNext())
          {
            localObject4 = (Limit)((Iterator)localObject3).next();
            if ((a(paramString1, ((Limit)localObject4).getObjectType())) && (a(paramString2, ((Limit)localObject4).getObjectId()))) {
              try
              {
                Entitlements.jdMethod_try((Limit)localObject4);
              }
              catch (CSILException localCSILException7)
              {
                bool1 = false;
              }
            }
          }
          localObject3 = null;
          localLimits2 = null;
        }
        catch (CSILException localCSILException5)
        {
          bool1 = false;
        }
        try
        {
          com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = Entitlements.jdMethod_int((EntitlementGroupMember)localObject2);
          localObject3 = new com.ffusion.csil.beans.entitlements.Entitlements();
          localObject4 = localEntitlements2.iterator();
          while (((Iterator)localObject4).hasNext())
          {
            Entitlement localEntitlement2 = (Entitlement)((Iterator)localObject4).next();
            if ((a(paramString1, localEntitlement2.getObjectType())) && (a(paramString2, localEntitlement2.getObjectId()))) {
              ((com.ffusion.csil.beans.entitlements.Entitlements)localObject3).add(localEntitlement2);
            }
          }
          localObject4 = null;
          localEntitlements2 = null;
          Entitlements.jdMethod_for((EntitlementGroupMember)localObject2, (com.ffusion.csil.beans.entitlements.Entitlements)localObject3);
          localObject3 = null;
        }
        catch (CSILException localCSILException6)
        {
          bool1 = false;
        }
      }
      localObject1 = null;
    }
    catch (CSILException localCSILException2)
    {
      bool1 = false;
    }
    try
    {
      Limits localLimits1 = Entitlements.getGroupLimits(paramInt);
      localObject1 = localLimits1.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Limit)((Iterator)localObject1).next();
        if ((a(paramString1, ((Limit)localObject2).getObjectType())) && (a(paramString2, ((Limit)localObject2).getObjectId()))) {
          Entitlements.jdMethod_try((Limit)localObject2);
        }
      }
      localObject1 = null;
    }
    catch (CSILException localCSILException3)
    {
      bool1 = false;
    }
    try
    {
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = Entitlements.g(paramInt);
      localObject1 = new com.ffusion.csil.beans.entitlements.Entitlements();
      localObject2 = localEntitlements1.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        Entitlement localEntitlement1 = (Entitlement)((Iterator)localObject2).next();
        if ((a(paramString1, localEntitlement1.getObjectType())) && (a(paramString2, localEntitlement1.getObjectId()))) {
          ((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).add(localEntitlement1);
        }
      }
      localObject2 = null;
      localEntitlements1 = null;
      Entitlements.jdMethod_for(paramInt, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1);
      localObject1 = null;
    }
    catch (CSILException localCSILException4)
    {
      bool1 = false;
    }
    return bool1;
  }
  
  private static boolean a(String paramString1, String paramString2)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return true;
    }
    if ((paramString1 == null) || (paramString2 == null)) {
      return false;
    }
    return paramString1.equals(paramString2);
  }
  
  public static MultiEntitlement appendAccountGroups(MultiEntitlement paramMultiEntitlement, int paramInt)
    throws CSILException
  {
    String str = "EntitlementsUtil.appendAccountGroups";
    String[] arrayOfString1 = paramMultiEntitlement.getObjectTypes();
    String[] arrayOfString2 = paramMultiEntitlement.getObjectIds();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = null;
    if ((arrayOfString1 == null) || (arrayOfString2 == null)) {
      return paramMultiEntitlement;
    }
    if (arrayOfString1.length != arrayOfString2.length) {
      throw new CSILException(str, 20031);
    }
    for (int i = 0; i < arrayOfString1.length; i++) {
      if ((arrayOfString1[i] != null) && (arrayOfString2[i] != null) && (arrayOfString1[i].equals("Account"))) {
        localArrayList1.add(arrayOfString2[i]);
      }
    }
    try
    {
      localArrayList2 = AccountGroupAdapter.getAccountGroupIds(paramInt, localArrayList1, new HashMap());
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      localObject = new CSILException(40004, localBusinessAccountGroupException.code, localBusinessAccountGroupException);
      DebugLog.throwing(str, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    if (localArrayList2 == null) {
      return paramMultiEntitlement;
    }
    int j = arrayOfString1.length + localArrayList2.size();
    Object localObject = new String[j];
    String[] arrayOfString3 = new String[j];
    for (int k = 0; k < j; k++) {
      if (k < arrayOfString1.length)
      {
        localObject[k] = arrayOfString1[k];
        arrayOfString3[k] = arrayOfString2[k];
      }
      else
      {
        localObject[k] = "AccountGroup";
        arrayOfString3[k] = ((String)localArrayList2.get(k - arrayOfString1.length));
      }
    }
    paramMultiEntitlement.setObjects((String[])localObject, arrayOfString3);
    String[] arrayOfString4 = paramMultiEntitlement.getOperations();
    String[] arrayOfString5 = new String[arrayOfString4 == null ? 1 : arrayOfString4.length + 1];
    if (arrayOfString4 != null) {
      System.arraycopy(arrayOfString4, 0, arrayOfString5, 0, arrayOfString4.length);
    }
    arrayOfString5[(arrayOfString5.length - 1)] = "Access";
    paramMultiEntitlement.setOperations(arrayOfString5);
    return paramMultiEntitlement;
  }
  
  public static void filterEntitlementsOnAccountGroup(com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, EntitlementGroupMember paramEntitlementGroupMember)
  {
    int i = 0;
    String[] arrayOfString1 = { "Account" };
    String[] arrayOfString2 = new String[1];
    String[] arrayOfString3 = new String[1];
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    while (i < paramEntitlements.size())
    {
      Entitlement localEntitlement = paramEntitlements.getEntitlement(i);
      if ((localEntitlement.getObjectType() != null) && (localEntitlement.getObjectType().equals("Account")))
      {
        arrayOfString2[0] = localEntitlement.getObjectId();
        localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
        arrayOfString3[0] = localEntitlement.getOperationName();
        localMultiEntitlement.setOperations(arrayOfString3);
        try
        {
          appendAccountGroups(localMultiEntitlement, paramEntitlementGroupMember.getBusinessID());
          if (Entitlements.checkEntitlement(paramEntitlementGroupMember, localMultiEntitlement) != null)
          {
            paramEntitlements.remove(i);
            continue;
          }
          i++;
        }
        catch (CSILException localCSILException)
        {
          paramEntitlements.remove(i);
        }
      }
      else
      {
        i++;
      }
    }
  }
  
  public static Entitlement checkAccountEntitlement(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, int paramInt)
    throws CSILException
  {
    return a(paramEntitlementGroupMember, -1, true, paramMultiEntitlement, paramInt, true);
  }
  
  public static Entitlement checkAccountEntitlement(int paramInt1, MultiEntitlement paramMultiEntitlement, int paramInt2)
    throws CSILException
  {
    return a(null, paramInt1, false, paramMultiEntitlement, paramInt2, true);
  }
  
  public static Entitlement checkAccountAndAccountGroupEntitlement(int paramInt1, MultiEntitlement paramMultiEntitlement, int paramInt2)
    throws CSILException
  {
    return a(null, paramInt1, false, paramMultiEntitlement, paramInt2, false);
  }
  
  public static Entitlement checkAccountAndAccountGroupEntitlement(EntitlementGroupMember paramEntitlementGroupMember, MultiEntitlement paramMultiEntitlement, int paramInt)
    throws CSILException
  {
    return a(paramEntitlementGroupMember, -1, true, paramMultiEntitlement, paramInt, false);
  }
  
  private static Entitlement a(EntitlementGroupMember paramEntitlementGroupMember, int paramInt1, boolean paramBoolean1, MultiEntitlement paramMultiEntitlement, int paramInt2, boolean paramBoolean2)
    throws CSILException
  {
    String str = "EntitlementsUtil.checkAccountEntitlement";
    String[] arrayOfString1 = paramMultiEntitlement.getObjectTypes();
    String[] arrayOfString2 = paramMultiEntitlement.getObjectIds();
    String[] arrayOfString3 = paramMultiEntitlement.getOperations();
    if ((arrayOfString1 == null) && (arrayOfString2 == null)) {
      return null;
    }
    if ((arrayOfString1 == null) || (arrayOfString2 == null) || (arrayOfString1.length != arrayOfString2.length)) {
      throw new CSILException(str, 20031);
    }
    int i = -1;
    String[] arrayOfString4 = new String[arrayOfString1.length];
    String[] arrayOfString5 = new String[arrayOfString2.length];
    String[] arrayOfString6 = arrayOfString3;
    Object localObject;
    for (int j = 0; j < arrayOfString1.length; j++)
    {
      arrayOfString4[j] = arrayOfString1[j];
      arrayOfString5[j] = arrayOfString2[j];
      if (arrayOfString1[j] != null) {
        if (arrayOfString1[j].equals("Account"))
        {
          if (i != -1)
          {
            localObject = new CSILException(20032);
            DebugLog.throwing(str, (Throwable)localObject);
            throw ((Throwable)localObject);
          }
          i = j;
          if (paramBoolean2)
          {
            arrayOfString6 = new String[arrayOfString3 == null ? 1 : arrayOfString3.length + 1];
            if (arrayOfString3 != null) {
              System.arraycopy(arrayOfString3, 0, arrayOfString6, 0, arrayOfString3.length);
            }
            arrayOfString6[(arrayOfString6.length - 1)] = "Access";
          }
        }
        else if (arrayOfString1[j].equals("AccountGroup"))
        {
          localObject = new CSILException(20033);
          DebugLog.throwing(str, (Throwable)localObject);
          throw ((Throwable)localObject);
        }
      }
    }
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setObjects(arrayOfString4, arrayOfString5);
    localMultiEntitlement.setOperations(arrayOfString6);
    if (paramBoolean1) {
      localObject = Entitlements.checkEntitlement(paramEntitlementGroupMember, localMultiEntitlement);
    } else {
      localObject = Entitlements.checkEntitlement(paramInt1, localMultiEntitlement);
    }
    if (localObject == null) {
      return null;
    }
    if (!((Entitlement)localObject).getObjectType().equals("Account")) {
      return localObject;
    }
    if (i == -1) {
      return localObject;
    }
    if (arrayOfString2[i] == null) {
      return localObject;
    }
    if (paramInt2 == 0) {
      return localObject;
    }
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(arrayOfString2[i]);
    ArrayList localArrayList2 = null;
    try
    {
      localArrayList2 = AccountGroupAdapter.getAccountGroupIds(paramInt2, localArrayList1, new HashMap());
    }
    catch (BusinessAccountGroupException localBusinessAccountGroupException)
    {
      CSILException localCSILException = new CSILException(20034, localBusinessAccountGroupException.code, localBusinessAccountGroupException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    if (localArrayList2 == null) {
      return localObject;
    }
    String[] arrayOfString7 = new String[1];
    for (int k = 0; k < arrayOfString6.length; k++)
    {
      arrayOfString7[0] = arrayOfString6[k];
      localMultiEntitlement.setOperations(arrayOfString7);
      arrayOfString4[i] = "Account";
      arrayOfString5[i] = arrayOfString2[i];
      localMultiEntitlement.setObjects(arrayOfString4, arrayOfString5);
      Entitlement localEntitlement;
      if (paramBoolean1) {
        localEntitlement = Entitlements.checkEntitlement(paramEntitlementGroupMember, localMultiEntitlement);
      } else {
        localEntitlement = Entitlements.checkEntitlement(paramInt1, localMultiEntitlement);
      }
      if (localEntitlement != null)
      {
        arrayOfString4[i] = "AccountGroup";
        Iterator localIterator = localArrayList2.iterator();
        while (localIterator.hasNext())
        {
          arrayOfString5[i] = ((String)localIterator.next());
          localMultiEntitlement.setObjects(arrayOfString4, arrayOfString5);
          if (paramBoolean1) {
            localEntitlement = Entitlements.checkEntitlement(paramEntitlementGroupMember, localMultiEntitlement);
          } else {
            localEntitlement = Entitlements.checkEntitlement(paramInt1, localMultiEntitlement);
          }
          if (localEntitlement == null) {
            break;
          }
        }
        if (localEntitlement != null) {
          return localObject;
        }
      }
    }
    return null;
  }
  
  public static Entitlement checkEntitlementAndParents(EntitlementGroupMember paramEntitlementGroupMember, Entitlement paramEntitlement)
    throws CSILException
  {
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { paramEntitlement.getOperationName() });
    localMultiEntitlement.setObjects(new String[] { paramEntitlement.getObjectType() }, new String[] { paramEntitlement.getObjectId() });
    localMultiEntitlement = ParentEntitlementsCache.appendParentEntitlements(localMultiEntitlement);
    return Entitlements.checkEntitlement(paramEntitlementGroupMember, localMultiEntitlement);
  }
  
  public static Entitlement checkEntitlementAndParents(int paramInt, Entitlement paramEntitlement)
    throws CSILException
  {
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { paramEntitlement.getOperationName() });
    localMultiEntitlement.setObjects(new String[] { paramEntitlement.getObjectType() }, new String[] { paramEntitlement.getObjectId() });
    localMultiEntitlement = ParentEntitlementsCache.appendParentEntitlements(localMultiEntitlement);
    return Entitlements.checkEntitlement(paramInt, localMultiEntitlement);
  }
  
  public static boolean isCrossAccount(EntitlementTypePropertyList paramEntitlementTypePropertyList)
  {
    return ((!paramEntitlementTypePropertyList.isPropertySet("category", "per account")) && (!paramEntitlementTypePropertyList.isPropertySet("category", "per wire template")) && (!paramEntitlementTypePropertyList.isPropertySet("category", "per ACH company")) && (!paramEntitlementTypePropertyList.isPropertySet("category", "cross ACH company")) && (!paramEntitlementTypePropertyList.isPropertySet("category", "per location"))) || (paramEntitlementTypePropertyList.isPropertySet("category", "cross account"));
  }
  
  public static String getPropertyValue(TypePropertyList paramTypePropertyList, String paramString1, String paramString2, String paramString3)
  {
    if (paramTypePropertyList.isPropertySet(paramString1)) {
      return paramTypePropertyList.getPropertyValue(paramString1, 0);
    }
    if (paramTypePropertyList.isPropertySet(paramString2)) {
      return paramTypePropertyList.getPropertyValue(paramString2, 0);
    }
    return paramTypePropertyList.getPropertyValue(paramString3, 0);
  }
  
  public static String getTypePropertyCategoryValue(String paramString)
  {
    String str = "";
    if (paramString != null) {
      if (paramString.equals("Employee")) {
        str = "per bank employee";
      } else if (paramString.equals("ServicesPackage")) {
        str = "per package";
      } else if (paramString.equals("BusinessAdmin")) {
        str = "per business";
      } else if (paramString.equals("Business")) {
        str = "per company";
      } else if (paramString.equals("Division")) {
        str = "per division";
      } else if (paramString.equals("Group")) {
        str = "per group";
      }
    }
    return str;
  }
  
  public static String getPropertyValue(EntitlementGroupProperties paramEntitlementGroupProperties, String paramString1, String paramString2, String paramString3)
  {
    paramEntitlementGroupProperties.setCurrentProperty(paramString1);
    if (paramEntitlementGroupProperties.getValueOfCurrentProperty() != null) {
      return paramEntitlementGroupProperties.getValueOfCurrentProperty();
    }
    paramEntitlementGroupProperties.setCurrentProperty(paramString2);
    if (paramEntitlementGroupProperties.getValueOfCurrentProperty() != null) {
      return paramEntitlementGroupProperties.getValueOfCurrentProperty();
    }
    paramEntitlementGroupProperties.setCurrentProperty(paramString3);
    return paramEntitlementGroupProperties.getValueOfCurrentProperty();
  }
  
  public static String getPropertyValue(TypePropertyList paramTypePropertyList, String paramString, Locale paramLocale)
  {
    String str1 = getPropertyNameLanguageCountry(paramString, paramLocale);
    String str2 = getPropertyNameLanguage(paramString, paramLocale);
    return getPropertyValue(paramTypePropertyList, str1, str2, paramString);
  }
  
  public static String getPropertyValue(EntitlementGroupProperties paramEntitlementGroupProperties, String paramString, Locale paramLocale)
  {
    String str1 = getPropertyNameLanguageCountry(paramString, paramLocale);
    String str2 = getPropertyNameLanguage(paramString, paramLocale);
    return getPropertyValue(paramEntitlementGroupProperties, str1, str2, paramString);
  }
  
  public static void setPropertyValue(TypePropertyList paramTypePropertyList, String paramString1, String paramString2, Locale paramLocale)
  {
    String str1 = getPropertyNameLanguageCountry(paramString1, paramLocale);
    String str2 = getPropertyNameLanguage(paramString1, paramLocale);
    if (paramTypePropertyList.isPropertySet(str1))
    {
      paramTypePropertyList.clearProperty(str1);
      paramTypePropertyList.addProperty(str1, paramString2);
    }
    else if (paramTypePropertyList.isPropertySet(str2))
    {
      paramTypePropertyList.clearProperty(str2);
      paramTypePropertyList.addProperty(str2, paramString2);
    }
    else
    {
      paramTypePropertyList.clearProperty(paramString1);
      paramTypePropertyList.addProperty(paramString1, paramString2);
    }
  }
  
  public static String getPropertyNameLanguage(String paramString, Locale paramLocale)
  {
    return paramString + "_" + paramLocale.getLanguage();
  }
  
  public static String getPropertyNameLanguageCountry(String paramString, Locale paramLocale)
  {
    return paramString + "_" + paramLocale.getLanguage() + "_" + paramLocale.getCountry();
  }
  
  public static void addSecondaryUserAccountRestrictions(SecureUser paramSecureUser, Users paramUsers, boolean paramBoolean, Accounts paramAccounts)
    throws CSILException
  {
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("category", "per subconsumer");
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = new com.ffusion.csil.beans.entitlements.Entitlements();
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Account)localIterator.next();
      localEntitlements.add(new Entitlement("Access", "Account", getEntitlementObjectId((Account)localObject1)));
    }
    localIterator = localEntitlementTypePropertyLists.iterator();
    Object localObject2;
    while (localIterator.hasNext())
    {
      localObject1 = (EntitlementTypePropertyList)localIterator.next();
      if (paramBoolean) {
        localEntitlements.add(new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName(), null, null));
      }
      if (((EntitlementTypePropertyList)localObject1).isPropertySet("category", "per account"))
      {
        localObject2 = paramAccounts.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          Account localAccount = (Account)((Iterator)localObject2).next();
          localEntitlements.add(new Entitlement(((EntitlementTypePropertyList)localObject1).getOperationName(), "Account", getEntitlementObjectId(localAccount)));
        }
      }
    }
    Object localObject1 = getEntitlementGroupMember(paramSecureUser);
    localIterator = paramUsers.iterator();
    while (localIterator.hasNext())
    {
      localObject2 = (User)localIterator.next();
      ((User)localObject2).setEntitlementGroupId(paramSecureUser.getEntitlementID());
      Entitlements.addRestrictedEntitlementsUnsafeNoDupCheck((EntitlementGroupMember)localObject1, ((User)localObject2).getEntitlementGroupMember(), localEntitlements, true);
    }
  }
  
  public static void fillUserEntitlementInfo(User paramUser)
    throws CSILException
  {
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setProfileID(paramUser.getIdValue());
    localSecureUser.setUserType(1);
    EntitlementGroupMember localEntitlementGroupMember = getEntitlementGroupMember(localSecureUser);
    localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
    EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroupMember.getEntitlementGroupId());
    paramUser.setEntitlementGroupId(localEntitlementGroup.getGroupId());
    for (int i = localEntitlementGroup.getParentId(); i != 0; i = localEntitlementGroup.getParentId())
    {
      localEntitlementGroup = Entitlements.getEntitlementGroup(i);
      if (localEntitlementGroup == null) {
        break;
      }
      if (localEntitlementGroup.getEntGroupType().equals("ServicesPackage"))
      {
        paramUser.setServicesPackageId(i);
        break;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.EntitlementsUtil
 * JD-Core Version:    0.7.0.1
 */