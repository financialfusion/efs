package com.ffusion.bankreport;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.bankreport.ProcessorInfo;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Reporting;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;

public class BankReportUtil
{
  public static boolean checkEntitlementForBusiness(int paramInt, String paramString, Account paramAccount)
    throws BRException
  {
    try
    {
      EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember(Locale.getDefault());
      localEntitlementGroupMember.setId(String.valueOf(paramInt));
      localEntitlementGroupMember.setMemberType("BUSINESS");
      localEntitlementGroupMember.setMemberSubType("0");
      localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      localMultiEntitlement.setOperations(new String[] { "Bank Reporting" });
      if (paramAccount != null)
      {
        localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember.getEntitlementGroupId(), localMultiEntitlement, paramInt) != null) {
          return false;
        }
      }
      else
      {
        localMultiEntitlement.setObjects(new String[] { null }, new String[] { null });
        if (Entitlements.checkEntitlement(localEntitlementGroupMember.getEntitlementGroupId(), localMultiEntitlement) != null) {
          return false;
        }
      }
      ReportCriteria localReportCriteria = Reporting.getDefaultReportCriteria(paramString);
      Iterator localIterator = localReportCriteria.getEntitlementTypes().iterator();
      String[] arrayOfString = new String[1];
      while (localIterator.hasNext())
      {
        arrayOfString[0] = ((String)localIterator.next());
        localMultiEntitlement.setOperations(arrayOfString);
        if (Entitlements.checkEntitlement(localEntitlementGroupMember.getEntitlementGroupId(), localMultiEntitlement) != null) {
          return false;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.SEVERE, "Failed to check business entitlements in BankReportUtil.");
      throw new BRException(60400, "Entitlement check failed in BankReportUtil.", localCSILException);
    }
    return true;
  }
  
  public static boolean checkEntitlementForUser(int paramInt, String paramString, Account paramAccount)
    throws BRException
  {
    try
    {
      EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember(Locale.getDefault());
      localEntitlementGroupMember.setId(String.valueOf(paramInt));
      localEntitlementGroupMember.setMemberType("USER");
      localEntitlementGroupMember.setMemberSubType(String.valueOf(1));
      localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
      MultiEntitlement localMultiEntitlement = new MultiEntitlement();
      localMultiEntitlement.setOperations(new String[] { "Bank Reporting" });
      if (paramAccount != null)
      {
        localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
        if (EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember.getEntitlementGroupId(), localMultiEntitlement, paramInt) != null) {
          return false;
        }
      }
      else
      {
        localMultiEntitlement.setObjects(new String[] { null }, new String[] { null });
        if (Entitlements.checkEntitlement(localEntitlementGroupMember.getEntitlementGroupId(), localMultiEntitlement) != null) {
          return false;
        }
      }
      ReportCriteria localReportCriteria = Reporting.getDefaultReportCriteria(paramString);
      Iterator localIterator = localReportCriteria.getEntitlementTypes().iterator();
      String[] arrayOfString = new String[1];
      while (localIterator.hasNext())
      {
        arrayOfString[0] = ((String)localIterator.next());
        localMultiEntitlement.setOperations(arrayOfString);
        if (Entitlements.checkEntitlement(localEntitlementGroupMember.getEntitlementGroupId(), localMultiEntitlement) != null) {
          return false;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.SEVERE, "Failed to check user entitlements in BankReportUtil.");
      throw new BRException(60400, "Entitlement check failed in BankReportUtil.", localCSILException);
    }
    return true;
  }
  
  public static IBankReportProcessor createProcessorInstance(ProcessorInfo paramProcessorInfo)
    throws BRException
  {
    try
    {
      Class localClass = Class.forName(paramProcessorInfo.getProcessorClassName());
      IBankReportProcessor localIBankReportProcessor = (IBankReportProcessor)localClass.newInstance();
      return localIBankReportProcessor;
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.SEVERE, "Failed to construct an instance of the specified processor.");
      throw new BRException(60300, "Failed to initialize processor.", localException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.bankreport.BankReportUtil
 * JD-Core Version:    0.7.0.1
 */