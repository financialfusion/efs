package com.ffusion.approvals.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.approvals.IApprovalsPlugin;
import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.Wire;
import com.ffusion.csil.core.WireEntitlementUtil;
import com.ffusion.csil.handlers.WireHandler;
import com.ffusion.util.beans.DateTime;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class WiresPlugin
  implements IApprovalsPlugin
{
  public void initialize(HashMap paramHashMap1, HashMap paramHashMap2)
    throws Throwable
  {}
  
  public boolean isUserEntitledToApprove(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    if (paramSecureUser.getUserType() == 2) {
      return true;
    }
    return WireEntitlementUtil.checkWireApprovalEntitlements(paramSecureUser, (WireTransfer)paramIApprovable, paramHashMap);
  }
  
  public Limits checkApprovalLimits(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    if (paramSecureUser.getUserType() == 2) {
      return null;
    }
    return a(paramSecureUser, paramIApprovable, true);
  }
  
  public void rollbackApprovalLimits(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    if (paramSecureUser.getUserType() == 2) {
      return;
    }
    a(paramSecureUser, paramIApprovable, false);
  }
  
  public void processApprovedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    Object localObject;
    if (paramHashMap != null)
    {
      localObject = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", localObject);
    }
    if ((paramIApprovable instanceof WireTransfer))
    {
      localObject = (WireTransfer)paramIApprovable;
      WireHandler.processApprovalResult(paramSecureUser, (WireTransfer)localObject, "Approved", paramHashMap);
    }
  }
  
  public void processRejectedItem(SecureUser paramSecureUser, IApprovable paramIApprovable, HashMap paramHashMap)
    throws Throwable
  {
    if (paramHashMap != null)
    {
      localObject = (String)paramHashMap.get("BankId");
      paramHashMap = new HashMap();
      paramHashMap.put("BankId", localObject);
    }
    Object localObject = paramIApprovable.getApprovalAmount().getAmountValue();
    if ((paramIApprovable instanceof WireTransfer))
    {
      WireTransfer localWireTransfer = (WireTransfer)paramIApprovable;
      WireHandler.processApprovalResult(paramSecureUser, localWireTransfer, "Rejected", paramHashMap);
    }
  }
  
  private Limits a(SecureUser paramSecureUser, IApprovable paramIApprovable, boolean paramBoolean)
    throws CSILException
  {
    WireTransfer localWireTransfer = (WireTransfer)paramIApprovable;
    MultiEntitlement localMultiEntitlement = null;
    Date localDate = null;
    BigDecimal localBigDecimal = paramIApprovable.getApprovalAmount().getAmountValue();
    String str = localWireTransfer.getWireType();
    if ((str.equals("TEMPLATE")) || (str.equals("RECTEMPLATE")))
    {
      localMultiEntitlement = WireEntitlementUtil.getMultiEntitlementObject(paramSecureUser, "Wire Templates Approval", localWireTransfer);
      localObject = new DateTime(paramSecureUser.getLocale());
      localDate = ((DateTime)localObject).getTime();
    }
    else
    {
      localObject = WireEntitlementUtil.getEntitlementFlowName(localWireTransfer.getWireDestination(), localWireTransfer.getWireSource());
      String[] arrayOfString = WireEntitlementUtil.getApprovalLimitOperationsByFlow((String)localObject);
      localMultiEntitlement = WireEntitlementUtil.getMultiEntitlementObject(paramSecureUser, arrayOfString, localWireTransfer);
      localDate = Wire.getSmartDate(paramSecureUser, localWireTransfer.getDueDateValue());
    }
    Object localObject = new EntitlementGroupMember();
    ((EntitlementGroupMember)localObject).setId(Integer.toString(paramSecureUser.getProfileID()));
    ((EntitlementGroupMember)localObject).setMemberType("USER");
    ((EntitlementGroupMember)localObject).setMemberSubType(Integer.toString(1));
    localObject = Entitlements.getMember((EntitlementGroupMember)localObject);
    if (paramBoolean) {
      return Entitlements.checkLimitsAdd((EntitlementGroupMember)localObject, localMultiEntitlement, localBigDecimal, localDate);
    }
    Entitlements.checkLimitsDelete((EntitlementGroupMember)localObject, localMultiEntitlement, localBigDecimal, localDate);
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.approvals.plugins.WiresPlugin
 * JD-Core Version:    0.7.0.1
 */