package com.ffusion.csil.core;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.approvals.ApprovalsHistoryRecord;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.user.User;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireBatches;
import com.ffusion.beans.wiretransfers.WireHistories;
import com.ffusion.beans.wiretransfers.WireHistory;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.handlers.BankEmployeeAdmin;
import com.ffusion.csil.handlers.UserAdminHandler;
import com.ffusion.csil.handlers.WireHandler;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class WireUtil
{
  public static WireTransfers filterWiresByType(WireTransfers paramWireTransfers, String paramString)
    throws CSILException
  {
    String str1 = "WireUtil.filterWiresByType";
    WireTransfers localWireTransfers1 = new WireTransfers();
    WireTransfers localWireTransfers2 = new WireTransfers();
    Iterator localIterator = paramWireTransfers.iterator();
    while (localIterator.hasNext())
    {
      WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
      String str2 = localWireTransfer.getWireType();
      if ((str2 != null) && ((str2.equals("TEMPLATE")) || (str2.equals("RECTEMPLATE")))) {
        localWireTransfers2.add(localWireTransfer);
      } else {
        localWireTransfers1.add(localWireTransfer);
      }
    }
    if ((paramString != null) && (paramString.equals("TEMPLATE"))) {
      return localWireTransfers2;
    }
    return localWireTransfers1;
  }
  
  public static WireTransfers filterWiresByDest(WireTransfers paramWireTransfers, String paramString)
    throws CSILException
  {
    String str = "WireUtil.filterWiresByDest";
    WireTransfers localWireTransfers = new WireTransfers();
    if ((paramString != null) && (paramString.length() > 0))
    {
      Iterator localIterator = paramWireTransfers.iterator();
      while (localIterator.hasNext())
      {
        WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
        if (paramString.equals(localWireTransfer.getWireDestination())) {
          localWireTransfers.add(localWireTransfer);
        }
      }
    }
    return localWireTransfers;
  }
  
  public static WireBatches filterWireBatchesByDest(WireBatches paramWireBatches, String paramString)
    throws CSILException
  {
    String str = "WireUtil.filterWireBatchesByDest";
    WireBatches localWireBatches = new WireBatches();
    if ((paramString != null) && (paramString.length() > 0))
    {
      Iterator localIterator = paramWireBatches.iterator();
      while (localIterator.hasNext())
      {
        WireBatch localWireBatch = (WireBatch)localIterator.next();
        if (paramString.equals(localWireBatch.getBatchDestination())) {
          localWireBatches.add(localWireBatch);
        }
      }
    }
    return localWireBatches;
  }
  
  public static WireTransfers filterTemplatesByScopeAndDest(WireTransfers paramWireTransfers, String paramString1, String paramString2)
    throws CSILException
  {
    String str = "WireUtil.filterTemplatesByScopeAndDest";
    WireTransfers localWireTransfers = new WireTransfers();
    Iterator localIterator = paramWireTransfers.iterator();
    while (localIterator.hasNext())
    {
      WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
      if ((paramString2 != null) && (paramString2.length() > 0))
      {
        if ((paramString2.equals(localWireTransfer.getWireDestination())) && (paramString1.equals(localWireTransfer.getWireScope()))) {
          localWireTransfers.add(localWireTransfer);
        }
      }
      else if (paramString1.equals(localWireTransfer.getWireScope())) {
        localWireTransfers.add(localWireTransfer);
      }
    }
    return localWireTransfers;
  }
  
  public static boolean isNullOrEmpty(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }
  
  public static EntitlementGroupMember getEntGroupMember(SecureUser paramSecureUser, WireTransfer paramWireTransfer)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = null;
    if (paramSecureUser.getUserType() == 2) {
      try
      {
        localEntitlementGroupMember = new EntitlementGroupMember();
        localEntitlementGroupMember.setId(paramWireTransfer.getCustomerID());
        localEntitlementGroupMember.setMemberType("BUSINESS");
        localEntitlementGroupMember.setMemberSubType("0");
        localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log("WireUtil.getEntGroupMember: Error occurred in gettting EntitlementGroupMember for business with ID " + paramWireTransfer.getCustomerID());
        throw localCSILException;
      }
      catch (Exception localException)
      {
        DebugLog.log("WireUtil.getEntGroupMember: Error occurred in gettting EntitlementGroupMember for business with ID " + paramWireTransfer.getCustomerID());
        throw new CSILException("WireUtil.getEntGroupMember", -1009, localException.getMessage());
      }
    } else {
      localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    }
    return localEntitlementGroupMember;
  }
  
  public static void setWireApprovingUser(SecureUser paramSecureUser, ApprovalsHistoryRecord paramApprovalsHistoryRecord, WireTransfer paramWireTransfer)
    throws CSILException
  {
    String str = "WireUtil.setWireApprovingUser";
    int i = paramApprovalsHistoryRecord.getApprovingUserType();
    Object localObject;
    if (i == 1)
    {
      localObject = new User();
      ((User)localObject).setId(String.valueOf(paramApprovalsHistoryRecord.getApprovingUserID()));
      try
      {
        localObject = UserAdminHandler.getUserById(paramSecureUser, (User)localObject, null);
      }
      catch (CSILException localCSILException1)
      {
        DebugLog.log(str + ": The business customer ID " + paramApprovalsHistoryRecord.getApprovingUserID() + "does not exist.");
        localCSILException1.printStackTrace(System.err);
      }
      paramWireTransfer.setApprovingUser(((User)localObject).getUserName());
    }
    else if (i == 2)
    {
      localObject = new BankEmployee(paramSecureUser.getLocale());
      ((BankEmployee)localObject).setId(String.valueOf(paramApprovalsHistoryRecord.getApprovingUserID()));
      try
      {
        localObject = BankEmployeeAdmin.getBankEmployeeById(paramSecureUser, (BankEmployee)localObject, null);
      }
      catch (CSILException localCSILException2)
      {
        DebugLog.log(str + ": The bank employee ID " + paramApprovalsHistoryRecord.getApprovingUserID() + "does not exist.");
        localCSILException2.printStackTrace(System.err);
      }
      paramWireTransfer.setApprovingUser(((BankEmployee)localObject).getUserName());
    }
    else
    {
      paramWireTransfer.setApprovingUser("Not Available");
    }
  }
  
  public static void logWireTransfer(SecureUser paramSecureUser, WireTransfer paramWireTransfer, int paramInt, String paramString1, String paramString2)
  {
    if (paramInt == 3302)
    {
      Wire.audit(paramWireTransfer.getAuditRecord(paramSecureUser, paramString1, paramInt, paramString2));
    }
    else
    {
      Wire.audit(paramWireTransfer.getAuditRecord(paramSecureUser, paramString1, paramInt, paramString2));
      String str = paramWireTransfer.getWirePayeeID();
      if ((str == null) || (str.length() == 0))
      {
        WireTransferPayee localWireTransferPayee = paramWireTransfer.getWirePayee();
        if (localWireTransferPayee != null) {
          Wire.audit(localWireTransferPayee.getAuditRecord(paramSecureUser, "Added a wire beneficiary within a WireTransfer.", 3303, "ADDED"));
        }
      }
    }
  }
  
  public static void logWireBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, int paramInt, String paramString)
  {
    WireTransfers localWireTransfers = paramWireBatch.getWires();
    if (localWireTransfers == null) {
      localWireTransfers = new WireTransfers();
    }
    StringBuffer localStringBuffer;
    String str1;
    int j;
    WireTransfer localWireTransfer2;
    Object localObject1;
    String str2;
    Object localObject2;
    if (paramInt == 3309)
    {
      localStringBuffer = new StringBuffer("Sent a wire batch of amount ");
      localStringBuffer.append(paramWireBatch.getAmount());
      localStringBuffer.append(paramString);
      str1 = "ADDED";
      if (paramWireBatch.getStatus() >= 100) {
        str1 = "APPROVAL_PENDING";
      }
      Wire.audit(paramWireBatch.getAuditRecord(paramSecureUser, localStringBuffer.toString(), paramInt, str1));
      localStringBuffer = new StringBuffer("Sent a wire transfer within a batch of amount ");
      for (j = 0; j < localWireTransfers.size(); j++)
      {
        localWireTransfer2 = (WireTransfer)localWireTransfers.get(j);
        localStringBuffer.append(localWireTransfer2.getAmount());
        localObject1 = "ADDED";
        if (localWireTransfer2.getStatus() >= 100)
        {
          localObject1 = "APPROVAL_PENDING";
          localStringBuffer.append(" that exceeded limits for approvals.");
        }
        else
        {
          localStringBuffer.append(paramString);
        }
        Wire.audit(localWireTransfer2.getAuditRecord(paramSecureUser, localStringBuffer.toString(), 3300, (String)localObject1));
        str2 = localWireTransfer2.getWirePayeeID();
        if ((str2 == null) || (str2.length() == 0))
        {
          localObject2 = localWireTransfer2.getWirePayee();
          if (localObject2 != null) {
            Wire.audit(((WireTransferPayee)localObject2).getAuditRecord(paramSecureUser, "Added a wire beneficiary within a WireTransfer.", 3303, "ADDED"));
          }
        }
      }
    }
    else if (paramInt == 3310)
    {
      localStringBuffer = new StringBuffer("Sent a modified wire batch of amount ");
      localStringBuffer.append(paramWireBatch.getAmount());
      localStringBuffer.append(paramString);
      str1 = "MODIFIED";
      if (paramWireBatch.getStatus() >= 100) {
        str1 = "APPROVAL_PENDING";
      }
      Wire.audit(paramWireBatch.getAuditRecord(paramSecureUser, localStringBuffer.toString(), paramInt, str1));
      for (j = 0; j < localWireTransfers.size(); j++)
      {
        localWireTransfer2 = (WireTransfer)localWireTransfers.get(j);
        localObject1 = new StringBuffer();
        WireTransferPayee localWireTransferPayee;
        if (localWireTransfer2.getAction().equals("add"))
        {
          ((StringBuffer)localObject1).append("Sent a wire transfer within a modified batch of amount ");
          ((StringBuffer)localObject1).append(localWireTransfer2.getAmount());
          str2 = "ADDED";
          if (localWireTransfer2.getStatus() >= 100)
          {
            str2 = "APPROVAL_PENDING";
            ((StringBuffer)localObject1).append(" that exceeded limits for approvals.");
          }
          else
          {
            ((StringBuffer)localObject1).append(paramString);
          }
          Wire.audit(localWireTransfer2.getAuditRecord(paramSecureUser, ((StringBuffer)localObject1).toString(), 3300, str2));
          localObject2 = localWireTransfer2.getWirePayeeID();
          if ((localObject2 == null) || (((String)localObject2).length() == 0))
          {
            localWireTransferPayee = localWireTransfer2.getWirePayee();
            if (localWireTransferPayee != null) {
              Wire.audit(localWireTransferPayee.getAuditRecord(paramSecureUser, "Added a wire beneficiary within a WireTransfer.", 3303, "ADDED"));
            }
          }
        }
        else if (localWireTransfer2.getAction().equals("mod"))
        {
          ((StringBuffer)localObject1).append("Modified a wire transfer within a modified batch of amount");
          ((StringBuffer)localObject1).append(localWireTransfer2.getAmount());
          str2 = "ADDED";
          if (localWireTransfer2.getStatus() >= 100)
          {
            str2 = "APPROVAL_PENDING";
            ((StringBuffer)localObject1).append(" that exceeded limits for approvals.");
          }
          else
          {
            ((StringBuffer)localObject1).append(paramString);
          }
          Wire.audit(localWireTransfer2.getAuditRecord(paramSecureUser, ((StringBuffer)localObject1).toString(), 3301, str2));
          localObject2 = localWireTransfer2.getWirePayeeID();
          if ((localObject2 == null) || (((String)localObject2).length() == 0))
          {
            localWireTransferPayee = localWireTransfer2.getWirePayee();
            if (localWireTransferPayee != null) {
              Wire.audit(localWireTransferPayee.getAuditRecord(paramSecureUser, "Added a wire beneficiary within a WireTransfer.", 3303, "ADDED"));
            }
          }
        }
        else if (localWireTransfer2.getAction().equals("del"))
        {
          ((StringBuffer)localObject1).append("Cancelled a wire transfer within a modified batch of amount ");
          ((StringBuffer)localObject1).append(localWireTransfer2.getAmount());
          ((StringBuffer)localObject1).append(paramString);
          Wire.audit(localWireTransfer2.getAuditRecord(paramSecureUser, ((StringBuffer)localObject1).toString(), 3302, "DELETED"));
        }
      }
    }
    else if (paramInt == 3311)
    {
      localStringBuffer = new StringBuffer("Cancelled a wire batch of amount ");
      localStringBuffer.append(paramWireBatch.getAmount());
      localStringBuffer.append(paramString);
      Wire.audit(paramWireBatch.getAuditRecord(paramSecureUser, localStringBuffer.toString(), paramInt, "DELETED"));
      localStringBuffer = new StringBuffer("Cancelled a wire transfer within a cancelled batch of amount ");
      for (int i = 0; i < localWireTransfers.size(); i++)
      {
        WireTransfer localWireTransfer1 = (WireTransfer)localWireTransfers.get(i);
        localStringBuffer.append(localWireTransfer1.getAmount());
        localStringBuffer.append(paramString);
        Wire.audit(localWireTransfer1.getAuditRecord(paramSecureUser, localStringBuffer.toString(), 3302, "DELETED"));
      }
    }
  }
  
  public static String getBPWFIId(SecureUser paramSecureUser, String paramString)
  {
    String str = null;
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap = com.ffusion.util.entitlements.EntitlementsUtil.allowEntitlementBypass(localHashMap);
      com.ffusion.beans.business.Business localBusiness = new com.ffusion.beans.business.Business();
      localBusiness.setBankId(paramSecureUser.getBankID());
      localBusiness.setId(Integer.parseInt(paramString));
      localBusiness = com.ffusion.csil.handlers.Business.getBusiness(paramSecureUser, localBusiness, localHashMap);
      AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(paramSecureUser, localBusiness.getAffiliateBankID(), localHashMap);
      str = localAffiliateBank.getFIBPWID();
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("WARNING: Error in retrieving BPW_FI_ID for business with id " + paramString);
      localCSILException.printStackTrace();
    }
    catch (Exception localException)
    {
      DebugLog.log("WARNING: Error in retrieving BPW_FI_ID for business with id " + paramString);
      localException.printStackTrace();
    }
    return str;
  }
  
  public static WireTransfers getApprovalItems(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str1 = "WireUtil.getApprovalItems";
    WireTransfers localWireTransfers = new WireTransfers();
    paramHashMap1.put("ItemSubType", new Integer(5));
    ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, paramHashMap1, paramHashMap2);
    Accounts localAccounts = (Accounts)paramHashMap2.get("ACCOUNTS");
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator = localApprovalsStatuses.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)localIterator.next();
        ApprovalsItem localApprovalsItem = localApprovalsStatus.getApprovalItem();
        WireTransfer localWireTransfer1 = (WireTransfer)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
        localWireTransfer1.setExternalID(localWireTransfer1.getID());
        localWireTransfer1.setID(String.valueOf(localApprovalsItem.getItemID()));
        localWireTransfer1.setType(5);
        if ((localApprovalsStatus.getDecision() != null) && (localApprovalsStatus.getDecision().equals("Rejected")))
        {
          localWireTransfer1.setStatus(101);
          str2 = localApprovalsItem.getApprovalItemProperty("RejectReason");
          if ((str2 != null) && (str2.trim().length() > 0)) {
            localWireTransfer1.setRejectReason(str2);
          }
        }
        else
        {
          localWireTransfer1.setStatus(100);
        }
        String str2 = localWireTransfer1.getRecurringID();
        if ((str2 != null) && (str2.length() > 0))
        {
          localWireTransfer1.setType(6);
          WireTransfer localWireTransfer2 = (WireTransfer)localHashMap.get(str2);
          if (localWireTransfer2 == null)
          {
            localWireTransfer2 = WireHandler.getRecWireTransferById(paramSecureUser, str2, paramHashMap2);
            if (localWireTransfer2 != null) {
              localHashMap.put(str2, localWireTransfer2);
            }
          }
          if (localWireTransfer2 != null)
          {
            localWireTransfer1.setFrequency(localWireTransfer2.getFrequencyValue());
            localWireTransfer1.setNumberTransfers(localWireTransfer2.getNumberTransfers());
          }
        }
        setWireApprovalInfo(paramSecureUser, localWireTransfer1, localAccounts, paramHashMap2);
        localWireTransfers.add(localWireTransfer1);
      }
    }
    Wire.debug(paramSecureUser, str1);
    return localWireTransfers;
  }
  
  public static WireTransfers getRecApprovalItems(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str1 = "WireUtil.getRecApprovalItems";
    WireTransfers localWireTransfers = new WireTransfers();
    paramHashMap1.put("ItemSubType", new Integer(6));
    ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, paramHashMap1, paramHashMap2);
    Accounts localAccounts = (Accounts)paramHashMap2.get("ACCOUNTS");
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      Iterator localIterator = localApprovalsStatuses.iterator();
      while (localIterator.hasNext())
      {
        ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)localIterator.next();
        ApprovalsItem localApprovalsItem = localApprovalsStatus.getApprovalItem();
        WireTransfer localWireTransfer = (WireTransfer)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
        if ((localApprovalsStatus.getDecision() != null) && (localApprovalsStatus.getDecision().equals("Rejected")))
        {
          localWireTransfer.setStatus(101);
          String str2 = localApprovalsItem.getApprovalItemProperty("RejectReason");
          if ((str2 != null) && (str2.trim().length() > 0)) {
            localWireTransfer.setRejectReason(str2);
          }
        }
        else
        {
          localWireTransfer.setStatus(100);
        }
        localWireTransfer.setExternalID(localWireTransfer.getID());
        localWireTransfer.setID(String.valueOf(localApprovalsItem.getItemID()));
        localWireTransfer.setType(6);
        setWireApprovalInfo(paramSecureUser, localWireTransfer, localAccounts, paramHashMap2);
        localWireTransfers.add(localWireTransfer);
      }
    }
    Wire.debug(paramSecureUser, str1);
    return localWireTransfers;
  }
  
  public static WireTransfers getAllWireApprovalItems(SecureUser paramSecureUser, DateTime paramDateTime, String paramString, boolean paramBoolean, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.getAllWireApprovalItems";
    WireTransfers localWireTransfers = new WireTransfers();
    HashMap localHashMap = new HashMap();
    localHashMap.put("BusinessID", new Integer(paramSecureUser.getBusinessID()));
    if (paramDateTime != null)
    {
      localObject1 = DateFormatUtil.getFormatter("MM/dd/yyyy").format(paramDateTime.getTime());
      localHashMap.put("StartDueDate", localObject1);
      localHashMap.put("EndDueDate", localObject1);
    }
    Object localObject1 = { "Pending" };
    localHashMap.put("ItemSubType", new Integer(5));
    ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, (String[])localObject1, localHashMap, paramHashMap);
    Iterator localIterator;
    ApprovalsItem localApprovalsItem;
    Object localObject2;
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      localIterator = localApprovalsStatuses.iterator();
      while (localIterator.hasNext()) {
        try
        {
          ApprovalsStatus localApprovalsStatus1 = (ApprovalsStatus)localIterator.next();
          localApprovalsItem = localApprovalsStatus1.getApprovalItem();
          localObject2 = (WireTransfer)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
          if ((paramString != null) && (paramString.length() > 0))
          {
            if ((!((WireTransfer)localObject2).getWireType().equals("TEMPLATE")) && (((WireTransfer)localObject2).getWireDestination().equals(paramString))) {
              localWireTransfers.add(localObject2);
            }
          }
          else {
            localWireTransfers.add(localObject2);
          }
        }
        catch (Exception localException1)
        {
          DebugLog.log(str + ": " + localException1.toString());
          DebugLog.log(str + ": Continuing ...");
        }
      }
    }
    localHashMap.put("ItemSubType", new Integer(10));
    localApprovalsStatuses = Approvals.getSubmittedItems(1, (String[])localObject1, localHashMap, paramHashMap);
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      localIterator = localApprovalsStatuses.iterator();
      while (localIterator.hasNext()) {
        try
        {
          ApprovalsStatus localApprovalsStatus2 = (ApprovalsStatus)localIterator.next();
          localApprovalsItem = localApprovalsStatus2.getApprovalItem();
          localObject2 = (WireBatch)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
          if ((paramString != null) && (paramString.length() > 0))
          {
            if (((WireBatch)localObject2).getBatchDestination().equals(paramString)) {
              localWireTransfers.addAll(((WireBatch)localObject2).getWires());
            }
          }
          else {
            localWireTransfers.addAll(((WireBatch)localObject2).getWires());
          }
        }
        catch (Exception localException2)
        {
          DebugLog.log(str + ": " + localException2.toString());
          DebugLog.log(str + ": Continuing ...");
        }
      }
    }
    if (paramBoolean == true)
    {
      localHashMap.put("ItemSubType", new Integer(6));
      localApprovalsStatuses = Approvals.getSubmittedItems(1, (String[])localObject1, localHashMap, paramHashMap);
      if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
      {
        localIterator = localApprovalsStatuses.iterator();
        while (localIterator.hasNext()) {
          try
          {
            ApprovalsStatus localApprovalsStatus3 = (ApprovalsStatus)localIterator.next();
            localApprovalsItem = localApprovalsStatus3.getApprovalItem();
            localObject2 = (WireTransfer)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
            if ((paramString != null) && (paramString.length() > 0))
            {
              if ((!((WireTransfer)localObject2).getWireType().equals("RECTEMPLATE")) && (((WireTransfer)localObject2).getWireDestination().equals(paramString))) {
                localWireTransfers.add(localObject2);
              }
            }
            else {
              localWireTransfers.add(localObject2);
            }
          }
          catch (Exception localException3)
          {
            DebugLog.log(str + ": " + localException3.toString());
            DebugLog.log(str + ": Continuing ...");
          }
        }
      }
    }
    return localWireTransfers;
  }
  
  public static WireTransfers getAllWireApprovalItems(SecureUser paramSecureUser, DateTime paramDateTime1, DateTime paramDateTime2, String paramString, boolean paramBoolean1, boolean paramBoolean2, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "Wire.getAllWireApprovalItems";
    WireTransfers localWireTransfers = new WireTransfers();
    HashMap localHashMap = new HashMap();
    if (paramSecureUser != null) {
      localHashMap.put("BusinessID", new Integer(paramSecureUser.getBusinessID()));
    }
    String str2 = null;
    String str3 = null;
    if (paramDateTime1 != null) {
      if (paramBoolean2 == true)
      {
        str2 = "MM/dd/yyyy";
        str3 = DateFormatUtil.getFormatter(str2).format(paramDateTime1.getTime());
        localHashMap.put("StartDueDate", str3);
      }
      else
      {
        str2 = "MM/dd/yyyy HH:mm:ss";
        str3 = DateFormatUtil.getFormatter(str2).format(paramDateTime1.getTime());
        localHashMap.put("StartDate", str3);
      }
    }
    if (paramDateTime2 != null) {
      if (paramBoolean2 == true)
      {
        str3 = DateFormatUtil.getFormatter(str2).format(paramDateTime2.getTime());
        localHashMap.put("EndDueDate", str3);
      }
      else
      {
        str3 = DateFormatUtil.getFormatter(str2).format(paramDateTime2.getTime());
        localHashMap.put("EndDate", str3);
      }
    }
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      paramArrayOfString = new String[] { "Pending" };
    }
    localHashMap.put("ItemSubType", new Integer(5));
    ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, localHashMap, paramHashMap);
    Iterator localIterator;
    ApprovalsItem localApprovalsItem;
    Object localObject1;
    Object localObject2;
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      localIterator = localApprovalsStatuses.iterator();
      while (localIterator.hasNext()) {
        try
        {
          ApprovalsStatus localApprovalsStatus1 = (ApprovalsStatus)localIterator.next();
          localApprovalsItem = localApprovalsStatus1.getApprovalItem();
          localObject1 = (WireTransfer)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
          localObject2 = localApprovalsItem.getSubmissionDate();
          ((DateTime)localObject2).setFormat("yyyy-MM-dd hh:mm:ss");
          ((WireTransfer)localObject1).setLogDate(((DateTime)localObject2).toString());
          ((WireTransfer)localObject1).setDatePosted(((WireTransfer)localObject1).getDateToPostValue());
          if ("Rejected".equals(localApprovalsStatus1.getDecision())) {
            ((WireTransfer)localObject1).setStatus(101);
          } else {
            ((WireTransfer)localObject1).setStatus(100);
          }
          if ((paramString != null) && (paramString.length() > 0))
          {
            if ((!((WireTransfer)localObject1).getWireType().equals("TEMPLATE")) && ((((WireTransfer)localObject1).getWireDestination().equals(paramString)) || ("ALL".equals(paramString)))) {
              localWireTransfers.add(localObject1);
            }
          }
          else {
            localWireTransfers.add(localObject1);
          }
        }
        catch (Exception localException1)
        {
          DebugLog.log(str1 + ": " + localException1.toString());
          DebugLog.log(str1 + ": Continuing ...");
        }
      }
    }
    localHashMap.put("ItemSubType", new Integer(10));
    localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, localHashMap, paramHashMap);
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      localIterator = localApprovalsStatuses.iterator();
      while (localIterator.hasNext()) {
        try
        {
          ApprovalsStatus localApprovalsStatus2 = (ApprovalsStatus)localIterator.next();
          localApprovalsItem = localApprovalsStatus2.getApprovalItem();
          localObject1 = (WireBatch)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
          localObject2 = ((WireBatch)localObject1).getWires();
          for (int i = 0; i < ((WireTransfers)localObject2).size(); i++)
          {
            WireTransfer localWireTransfer = (WireTransfer)((WireTransfers)localObject2).get(i);
            DateTime localDateTime = localApprovalsItem.getSubmissionDate();
            localDateTime.setFormat("yyyy-MM-dd hh:mm:ss");
            localWireTransfer.setLogDate(localDateTime.toString());
            localWireTransfer.setDatePosted(((WireBatch)localObject1).getDateToPostValue());
            localWireTransfer.setSubmittedBy(((WireBatch)localObject1).getSubmittedBy());
            localWireTransfer.setUserID(((WireBatch)localObject1).getUserID());
            localWireTransfer.setWireDestination(((WireBatch)localObject1).getBatchDestination());
            if ("Rejected".equals(localApprovalsStatus2.getDecision())) {
              localWireTransfer.setStatus(101);
            } else {
              localWireTransfer.setStatus(100);
            }
          }
          if ((paramString != null) && (paramString.length() > 0))
          {
            if ((((WireBatch)localObject1).getBatchDestination().equals(paramString)) || ("ALL".equals(paramString))) {
              localWireTransfers.addAll((Collection)localObject2);
            }
          }
          else {
            localWireTransfers.addAll((Collection)localObject2);
          }
        }
        catch (Exception localException2)
        {
          DebugLog.log(str1 + ": " + localException2.toString());
          DebugLog.log(str1 + ": Continuing ...");
        }
      }
    }
    if (paramBoolean1 == true)
    {
      localHashMap.put("ItemSubType", new Integer(6));
      localApprovalsStatuses = Approvals.getSubmittedItems(1, paramArrayOfString, localHashMap, paramHashMap);
      if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
      {
        localIterator = localApprovalsStatuses.iterator();
        while (localIterator.hasNext()) {
          try
          {
            ApprovalsStatus localApprovalsStatus3 = (ApprovalsStatus)localIterator.next();
            localApprovalsItem = localApprovalsStatus3.getApprovalItem();
            localObject1 = (WireTransfer)((TWTransaction)localApprovalsItem.getItem()).getTransaction();
            localObject2 = localApprovalsItem.getSubmissionDate();
            ((DateTime)localObject2).setFormat("yyyy-MM-dd hh:mm:ss");
            ((WireTransfer)localObject1).setLogDate(((DateTime)localObject2).toString());
            ((WireTransfer)localObject1).setDatePosted(((WireTransfer)localObject1).getDateToPostValue());
            if ("Rejected".equals(localApprovalsStatus3.getDecision())) {
              ((WireTransfer)localObject1).setStatus(101);
            } else {
              ((WireTransfer)localObject1).setStatus(100);
            }
            if ((paramString != null) && (paramString.length() > 0))
            {
              if ((!((WireTransfer)localObject1).getWireType().equals("RECTEMPLATE")) && ((((WireTransfer)localObject1).getWireDestination().equals(paramString)) || ("ALL".equals(paramString)))) {
                localWireTransfers.add(localObject1);
              }
            }
            else {
              localWireTransfers.add(localObject1);
            }
          }
          catch (Exception localException3)
          {
            DebugLog.log(str1 + ": " + localException3.toString());
            DebugLog.log(str1 + ": Continuing ...");
          }
        }
      }
    }
    return localWireTransfers;
  }
  
  public static void setWireApprovalInfo(SecureUser paramSecureUser, WireTransfer paramWireTransfer, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    if ((paramWireTransfer.getWireType().equals("TEMPLATE")) || (paramWireTransfer.getWireType().equals("RECTEMPLATE")))
    {
      paramWireTransfer.setCanEdit(true);
      paramWireTransfer.setCanDelete(true);
    }
    else if ((paramAccounts != null) && (paramAccounts.size() > 0))
    {
      Account localAccount = paramAccounts.getByAccountNumberAndType(paramWireTransfer.getFromAccountNum(), paramWireTransfer.getFromAccountType());
      if ((localAccount != null) || (paramWireTransfer.getWireSource().equals("HOST")))
      {
        paramWireTransfer.setCanEdit(true);
        paramWireTransfer.setCanDelete(true);
      }
    }
    setWirePayee(paramSecureUser, paramWireTransfer, paramHashMap);
  }
  
  public static void setWirePayee(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    Object localObject;
    if (paramWireTransfer.getWireSource().equals("HOST"))
    {
      localObject = new WireTransferPayee();
      ((WireTransferPayee)localObject).setPayeeName("HOST");
      paramWireTransfer.setFromAccountID("HOST");
      paramWireTransfer.setWirePayee((WireTransferPayee)localObject);
    }
    else if (paramWireTransfer.getWirePayee() == null)
    {
      localObject = paramWireTransfer.getWirePayeeID();
      if ((localObject != null) && (((String)localObject).length() > 0))
      {
        WireTransferPayee localWireTransferPayee = WireHandler.getWireTransferPayeeById(paramSecureUser, (String)localObject, paramHashMap);
        paramWireTransfer.setWirePayee(localWireTransferPayee);
      }
      else
      {
        DebugLog.log("WireUtil.getApprovalItems: WARNING - No Beneficiary found for the pending wire/template with trackingId = " + paramWireTransfer.getTrackingID());
        paramWireTransfer.setWirePayee(new WireTransferPayee());
      }
    }
  }
  
  public static WireHistories getApprWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "WireUtil.getApprWireHistories";
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("VIEW");
    }
    HashMap localHashMap = new HashMap();
    if ((str2 != null) && (str2.equals("ALL")))
    {
      WireEntitlementUtil.checkViewEntitlements(paramSecureUser, str1, paramHashMap);
      localHashMap.put("BusinessID", new Integer(paramSecureUser.getBusinessID()));
    }
    else
    {
      WireEntitlementUtil.checkEntitlements(paramSecureUser, Wire.ENT_WIRES, str1);
      localHashMap.put("SubmittingUser", new Integer(paramSecureUser.getProfileID()));
    }
    localHashMap.put("ItemSubType", new Integer(5));
    WireHistories localWireHistories = WireHandler.getApprovalWireHistories(paramSecureUser, paramPagingContext, paramHashMap);
    if (localWireHistories != null)
    {
      Iterator localIterator = localWireHistories.iterator();
      while (localIterator.hasNext())
      {
        WireHistory localWireHistory = (WireHistory)localIterator.next();
        if (localWireHistory != null) {
          if (("RECURRING".equals(localWireHistory.getTransType())) || ("SINGLE".equals(localWireHistory.getTransType()))) {
            Util.insertApprovalInfo(localWireHistory, paramSecureUser, localHashMap, paramHashMap);
          }
        }
      }
    }
    return localWireHistories;
  }
  
  public static boolean isDuplicateWire(SecureUser paramSecureUser, WireTransfer paramWireTransfer, boolean paramBoolean, HashMap paramHashMap)
  {
    String str1 = "WireUtil.isDuplicateWire";
    WireTransfers localWireTransfers = null;
    try
    {
      WireTransfer localWireTransfer1 = (WireTransfer)paramWireTransfer.clone();
      if (paramBoolean == true)
      {
        localWireTransfer1.setOrigAmount(paramWireTransfer.getAmountForBPW());
        localWireTransfer1.setOrigCurrency(paramWireTransfer.getAmtCurrencyType());
      }
      localWireTransfers = WireHandler.getDuplicateWires(paramSecureUser, localWireTransfer1, paramHashMap);
      if (localWireTransfers.size() > 0) {
        for (int i = 0; i < localWireTransfers.size(); i++)
        {
          String str2 = paramWireTransfer.getTrackingID();
          WireTransfer localWireTransfer2 = (WireTransfer)localWireTransfers.get(i);
          String str3 = localWireTransfer2.getTrackingID();
          if (!str3.equals(str2))
          {
            DebugLog.log(str1 + ": Found a duplicate of this wire in BPW");
            return true;
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("WARNING: CSILException thrown during Duplicate Wire check!");
      DebugLog.log(localCSILException.toString());
    }
    catch (Exception localException)
    {
      DebugLog.log("WARNING: Exception thrown during Duplicate Wire check!");
      DebugLog.log(localException.toString());
    }
    return false;
  }
  
  public static boolean isDuplicateWireInBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, boolean paramBoolean, HashMap paramHashMap)
  {
    String str1 = "Wire.isDuplicateWireInBatch";
    try
    {
      WireTransfers localWireTransfers1 = paramWireBatch.getWires();
      WireTransfer localWireTransfer1;
      WireTransfer localWireTransfer2;
      BigDecimal localBigDecimal;
      Object localObject;
      if (localWireTransfers1.size() > 1) {
        for (int i = 0; i < localWireTransfers1.size(); i++) {
          for (j = 0; j < localWireTransfers1.size(); j++) {
            if (i != j)
            {
              localWireTransfer1 = (WireTransfer)localWireTransfers1.get(i);
              localWireTransfer2 = (WireTransfer)localWireTransfers1.get(j);
              localBigDecimal = localWireTransfer1.getAmountValue().getAmountValue().setScale(2, 5);
              localObject = localWireTransfer2.getAmountValue().getAmountValue().setScale(2, 5);
              if (!paramBoolean)
              {
                if (!localWireTransfer1.getAction().equals("add")) {
                  localBigDecimal = new BigDecimal(localWireTransfer1.getOrigAmount()).setScale(2, 5);
                }
                if (!localWireTransfer2.getAction().equals("add")) {
                  localObject = new BigDecimal(localWireTransfer2.getOrigAmount()).setScale(2, 5);
                }
              }
              if ((localBigDecimal.compareTo((BigDecimal)localObject) == 0) && (a(localWireTransfer1, localWireTransfer2) == true))
              {
                DebugLog.log(str1 + ": Found a duplicate of this wire in this batch");
                return true;
              }
            }
          }
        }
      }
      WireTransfers localWireTransfers2 = null;
      for (int j = 0; j < localWireTransfers1.size(); j++)
      {
        localWireTransfer1 = (WireTransfer)localWireTransfers1.get(j);
        localWireTransfer2 = (WireTransfer)localWireTransfer1.clone();
        if (paramBoolean == true)
        {
          localWireTransfer2.setOrigAmount(localWireTransfer1.getAmountForBPW());
          localWireTransfer2.setOrigCurrency(localWireTransfer1.getAmtCurrencyType());
        }
        else if (localWireTransfer1.getAction().equals("add") == true)
        {
          localWireTransfer2.setOrigAmount(localWireTransfer1.getAmountForBPW());
          localWireTransfer2.setOrigCurrency(localWireTransfer1.getAmtCurrencyType());
        }
        else
        {
          localBigDecimal = new BigDecimal(localWireTransfer2.getOrigAmount()).setScale(2, 5);
          localWireTransfer2.setOrigAmount(localBigDecimal.toString());
        }
        localWireTransfer2.setDateToPost(paramWireBatch.getDateToPost());
        localWireTransfer2.setWireType("SINGLE");
        localWireTransfers2 = WireHandler.getDuplicateWires(paramSecureUser, localWireTransfer2, paramHashMap);
        if (localWireTransfers2.size() > 0) {
          for (int k = 0; k < localWireTransfers2.size(); k++)
          {
            localObject = localWireTransfer1.getTrackingID();
            WireTransfer localWireTransfer3 = (WireTransfer)localWireTransfers2.get(k);
            String str2 = localWireTransfer3.getTrackingID();
            if (!str2.equals(localObject))
            {
              DebugLog.log(str1 + ": Found a duplicate of this wire in BPW");
              return true;
            }
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("WARNING: CSILException thrown during Duplicate Wire check!");
      DebugLog.log(localCSILException.toString());
    }
    catch (Exception localException)
    {
      DebugLog.log("WARNING: Exception thrown during Duplicate Wire check!");
      DebugLog.log(localException.toString());
    }
    return false;
  }
  
  private static boolean a(WireTransfer paramWireTransfer, WireTransfers paramWireTransfers, boolean paramBoolean)
    throws Exception
  {
    String str1 = "Wire.hasDuplicate";
    if (paramWireTransfers.size() == 0) {
      return false;
    }
    BigDecimal localBigDecimal1 = paramWireTransfer.getAmountValue().getAmountValue().setScale(2, 5);
    if (!paramBoolean) {
      localBigDecimal1 = new BigDecimal(paramWireTransfer.getOrigAmount()).setScale(2, 5);
    }
    String str2 = paramWireTransfer.getTrackingID();
    Iterator localIterator = paramWireTransfers.iterator();
    while (localIterator.hasNext()) {
      try
      {
        WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
        BigDecimal localBigDecimal2 = new BigDecimal(localWireTransfer.getOrigAmount()).setScale(2, 5);
        String str3 = localWireTransfer.getTrackingID();
        if (((str2 == null) || (str3.compareTo(str2) != 0)) && (localBigDecimal1.compareTo(localBigDecimal2) == 0) && (a(paramWireTransfer, localWireTransfer))) {
          return true;
        }
      }
      catch (Exception localException)
      {
        DebugLog.log(str1 + ": " + localException.toString());
        DebugLog.log(str1 + ": Continuing ...");
      }
    }
    return false;
  }
  
  private static boolean a(WireTransfer paramWireTransfer1, WireTransfer paramWireTransfer2)
  {
    try
    {
      String str1 = paramWireTransfer1.getWirePayeeID();
      WireTransferPayee localWireTransferPayee1 = paramWireTransfer1.getWirePayee();
      WireTransferPayee localWireTransferPayee2 = paramWireTransfer2.getWirePayee();
      if (!isNullOrEmpty(str1)) {
        return str1.equals(paramWireTransfer2.getWirePayeeID());
      }
      if (localWireTransferPayee1 != null)
      {
        if (localWireTransferPayee2 == null) {
          return false;
        }
        String str2 = localWireTransferPayee1.getAccountNum();
        String str3 = localWireTransferPayee1.getDestinationBank().getRoutingNumber();
        String str4 = localWireTransferPayee2.getAccountNum();
        String str5 = localWireTransferPayee2.getDestinationBank().getRoutingNumber();
        return (str2.equals(str4)) && (str3.equals(str5));
      }
    }
    catch (Exception localException)
    {
      DebugLog.log("Wire.isSameWirePayee: " + localException.toString());
      DebugLog.log("Wire.isSameWirePayee: Continuing ...");
    }
    return false;
  }
  
  public static boolean checkWireExistsInApprovals(SecureUser paramSecureUser, WireTransferPayee paramWireTransferPayee, HashMap paramHashMap)
  {
    boolean bool = false;
    String str = paramWireTransferPayee.getID();
    try
    {
      WireTransfers localWireTransfers = getAllWireApprovalItems(paramSecureUser, null, null, true, paramHashMap);
      for (int i = 0; i < localWireTransfers.size(); i++)
      {
        WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.get(i);
        if (str.equals(localWireTransfer.getWirePayeeID()))
        {
          bool = true;
          break;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("Wire.deleteWireTransferPayee: Check for unprocessed wires in approvals failed.");
    }
    return bool;
  }
  
  public static boolean supportRelease(SecureUser paramSecureUser, HashMap paramHashMap)
  {
    try
    {
      HashMap localHashMap = Wire.getWiresConfiguration(paramSecureUser, paramHashMap);
      if (localHashMap != null)
      {
        String str = (String)localHashMap.get("bpw.wires.config.supportrelease");
        if ("false".equalsIgnoreCase(str)) {
          return false;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log("Wire.supportRelease: Failed get configuration for Wires Release functionality.");
    }
    return true;
  }
  
  public static void setCanEditDelete(SecureUser paramSecureUser, WireHistory paramWireHistory, HashMap paramHashMap)
  {
    try
    {
      FundsTransaction localFundsTransaction = paramWireHistory.getTransaction();
      WireTransfer localWireTransfer;
      if ((localFundsTransaction != null) && ((localFundsTransaction instanceof WireTransfer)))
      {
        localWireTransfer = (WireTransfer)localFundsTransaction;
        if (WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, paramHashMap) == true)
        {
          paramWireHistory.setCanEdit(true);
          paramWireHistory.setCanDelete(true);
        }
      }
      else if (paramWireHistory.getTransType().equals("RECURRING"))
      {
        localWireTransfer = Wire.getRecWireTransferById(paramSecureUser, paramWireHistory.getRecurringID(), paramHashMap);
        if (WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, paramHashMap) == true)
        {
          paramWireHistory.setCanEdit(true);
          paramWireHistory.setCanDelete(true);
        }
      }
      else if (paramWireHistory.getTransType().equals("SINGLE"))
      {
        localWireTransfer = Wire.getWireTransferById(paramSecureUser, paramWireHistory.getID(), paramHashMap);
        if (WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, paramHashMap) == true)
        {
          paramWireHistory.setCanEdit(true);
          paramWireHistory.setCanDelete(true);
        }
      }
      else
      {
        paramWireHistory.setCanEdit(true);
        paramWireHistory.setCanDelete(true);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log("WireUtil.setCanEditDelete: Exception thrown.");
    }
  }
  
  public static void setCanEditDeleteForBatch(SecureUser paramSecureUser, WireBatch paramWireBatch, HashMap paramHashMap)
    throws CSILException
  {
    if (paramWireBatch != null)
    {
      int i = paramWireBatch.getStatus();
      if ((i == 1) || (i == 21) || (i == 2) || (i == 105) || (i == 26) || (i == 100) || (i == 101))
      {
        paramWireBatch.setCanEdit(true);
        paramWireBatch.setCanDelete(true);
      }
      WireTransfers localWireTransfers = paramWireBatch.getWires();
      if (localWireTransfers != null)
      {
        Iterator localIterator = localWireTransfers.iterator();
        while (localIterator.hasNext())
        {
          WireTransfer localWireTransfer = (WireTransfer)localIterator.next();
          if (localWireTransfer != null)
          {
            int j = localWireTransfer.getStatus();
            if (((j == 1) || (j == 21) || (j == 2) || (j == 105) || (j == 100) || (j == 101)) && (WireEntitlementUtil.checkWireEntitlements(paramSecureUser, localWireTransfer, paramHashMap) == true))
            {
              localWireTransfer.setCanEdit(true);
              localWireTransfer.setCanDelete(true);
            }
          }
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.WireUtil
 * JD-Core Version:    0.7.0.1
 */