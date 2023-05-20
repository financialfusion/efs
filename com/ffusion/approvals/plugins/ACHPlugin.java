package com.ffusion.approvals.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.approvals.IApprovalsPlugin;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ACHPlugin
  implements IApprovalsPlugin
{
  private static String jdField_do = "SEC_KEY";
  private HashMap jdField_if;
  private HashMap a;
  
  public void initialize(HashMap paramHashMap1, HashMap paramHashMap2)
    throws Throwable
  {
    a();
  }
  
  public boolean isUserEntitledToApprove(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    if (paramSecureUser.getUserType() == 2) {
      return true;
    }
    return com.ffusion.csil.core.ACH.checkACHSECEntitlement(paramSecureUser, (ACHBatch)paramIApprovable, "ACH Payment Approval", paramHashMap);
  }
  
  public Limits checkApprovalLimits(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    if (paramSecureUser.getUserType() == 2) {
      return null;
    }
    return jdField_if(paramSecureUser, paramIApprovable, true);
  }
  
  public void rollbackApprovalLimits(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    if (paramSecureUser.getUserType() == 2) {
      return;
    }
    jdField_if(paramSecureUser, paramIApprovable, false);
  }
  
  public void processApprovedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    ACHBatch localACHBatch = null;
    localACHBatch = (ACHBatch)paramIApprovable;
    if (paramHashMap != null)
    {
      str = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", str);
    }
    String str = localACHBatch.getID();
    if ((str != null) && (str.length() != 0)) {
      com.ffusion.csil.handlers.ACH.processApprovalResult(paramSecureUser, localACHBatch, "Approved", paramHashMap);
    } else {
      com.ffusion.csil.handlers.ACH.addACHBatch(paramSecureUser, localACHBatch, paramHashMap);
    }
  }
  
  public void processRejectedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    ACHBatch localACHBatch = null;
    localACHBatch = (ACHBatch)paramIApprovable;
    if (paramHashMap != null)
    {
      str = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", str);
    }
    String str = localACHBatch.getID();
    if ((str != null) && (str.length() != 0)) {
      com.ffusion.csil.handlers.ACH.processApprovalResult(paramSecureUser, localACHBatch, "Rejected", paramHashMap);
    } else {
      com.ffusion.csil.core.ACH.checkLimitsDelete(paramSecureUser, localACHBatch);
    }
  }
  
  private Limits jdField_if(SecureUser paramSecureUser, IApprovable paramIApprovable, boolean paramBoolean)
    throws CSILException
  {
    ACHBatch localACHBatch = (ACHBatch)paramIApprovable;
    Date localDate = localACHBatch.getDateValue().getTime();
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(Integer.toString(paramSecureUser.getProfileID()));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType(Integer.toString(1));
    localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
    Object localObject = null;
    BigDecimal localBigDecimal = localACHBatch.getTotalCreditAmountValue().getAmountValue();
    MultiEntitlement localMultiEntitlement;
    if (localBigDecimal.doubleValue() != 0.0D)
    {
      localMultiEntitlement = jdField_if(localACHBatch, false);
      if (paramBoolean) {
        localObject = Entitlements.checkLimitsAdd(localEntitlementGroupMember, localMultiEntitlement, localBigDecimal, localDate);
      } else {
        Entitlements.checkLimitsDelete(localEntitlementGroupMember, localMultiEntitlement, localBigDecimal, localDate);
      }
    }
    localBigDecimal = localACHBatch.getTotalDebitAmountValue().getAmountValue();
    if (localBigDecimal.doubleValue() != 0.0D)
    {
      localMultiEntitlement = jdField_if(localACHBatch, true);
      if (paramBoolean)
      {
        Limits localLimits = Entitlements.checkLimitsAdd(localEntitlementGroupMember, localMultiEntitlement, localBigDecimal, localDate);
        if (localLimits != null) {
          if (localObject != null) {
            ((Limits)localObject).addAll(localLimits);
          } else {
            localObject = localLimits;
          }
        }
      }
      else
      {
        Entitlements.checkLimitsDelete(localEntitlementGroupMember, localMultiEntitlement, localBigDecimal, localDate);
      }
    }
    return localObject;
  }
  
  private MultiEntitlement jdField_if(ACHBatch paramACHBatch, boolean paramBoolean)
  {
    String[] arrayOfString1 = a(paramACHBatch, paramBoolean);
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(arrayOfString1);
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = "ACHCompany";
    String[] arrayOfString3 = new String[1];
    arrayOfString3[0] = paramACHBatch.getCompanyID();
    localMultiEntitlement.setObjects(arrayOfString2, arrayOfString3);
    return localMultiEntitlement;
  }
  
  private String[] a(ACHBatch paramACHBatch, boolean paramBoolean)
  {
    String str1 = paramACHBatch.getSECEntitlementName();
    ArrayList localArrayList = a(str1, paramBoolean);
    String str2 = "ACH Payment Approval";
    String str3 = EntitlementsUtil.getACHLimitName(str1, str2, paramBoolean);
    int i = 1;
    if (localArrayList != null) {
      i += localArrayList.size();
    }
    String[] arrayOfString = new String[i];
    arrayOfString[0] = str3;
    for (int j = 1; j < i; j++) {
      arrayOfString[j] = ((String)localArrayList.get(j - 1));
    }
    return arrayOfString;
  }
  
  private ArrayList a(String paramString, boolean paramBoolean)
  {
    ArrayList localArrayList = null;
    HashMap localHashMap = null;
    if (paramBoolean) {
      localHashMap = this.a;
    } else {
      localHashMap = this.jdField_if;
    }
    localArrayList = (ArrayList)localHashMap.get(paramString);
    if (localArrayList == null) {
      localArrayList = (ArrayList)localHashMap.get(jdField_do);
    }
    return (ArrayList)localArrayList.clone();
  }
  
  private void a()
  {
    this.jdField_if = new HashMap();
    this.a = new HashMap();
    a(false);
    a(true);
  }
  
  private void a(boolean paramBoolean)
  {
    HashMap localHashMap = null;
    if (paramBoolean) {
      localHashMap = this.a;
    } else {
      localHashMap = this.jdField_if;
    }
    String str1 = "ACH Payment Approval";
    String str2 = "Overall";
    String str3 = EntitlementsUtil.getACHLimitName(str2, str1, paramBoolean);
    String str4 = "ACHBatch";
    String str5 = EntitlementsUtil.getACHLimitName(str4, str1, paramBoolean);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(str3);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(str3);
    localArrayList2.add(str5);
    localHashMap.put("CCD + TXP", localArrayList1);
    localHashMap.put("CCD + DED", localArrayList1);
    localHashMap.put(jdField_do, localArrayList2);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.plugins.ACHPlugin
 * JD-Core Version:    0.7.0.1
 */