package com.ffusion.csil.core;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.approvals.ApprovalsChainItem;
import com.ffusion.beans.approvals.ApprovalsGroup;
import com.ffusion.beans.approvals.ApprovalsGroups;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.approvals.ApprovalsLevels;
import com.ffusion.beans.approvals.ApprovalsStatus;
import com.ffusion.beans.approvals.ApprovalsStatuses;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.CountryDefns;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.beans.util.StateProvinceDefn;
import com.ffusion.beans.util.StateProvinceDefns;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.handlers.ApprovalsHandler;
import com.ffusion.csil.handlers.HandlerUtil;
import com.ffusion.csil.handlers.UserAdminHandler;
import com.ffusion.csil.handlers.UtilHandler;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class Util
  extends Initialize
{
  private static final String[] aqj = { "Bank_defined" };
  private static final String[] aqm = { "Business_defined" };
  private static final String[] aqs = { "Business_defined", "Bank_defined" };
  private static final String[] aqy = { "Bill Payments" };
  private static final String[] aqw = { "Bill Payments", "Bill Payments" };
  private static final String[] aqB = { "Transfers" };
  private static final String[] aqA = { "Transfers", "Transfers" };
  private static final String[] aqH = { "ACH Payments" };
  private static final String[] aqD = { "ACH Payments", "ACH Payments" };
  private static final String[] aqi = { "Cash Concentration Deposits" };
  private static final String[] aqG = { "Cash Concentration Deposits", "Cash Concentration Deposits" };
  private static final String[] aqt = { "Cash Concentration Disbursements" };
  private static final String[] aqM = { "Cash Concentration Disbursements", "Cash Concentration Disbursements" };
  private static final String[] aqK = { "Tax Payments" };
  private static final String[] aqo = { "Tax Payments", "Tax Payments" };
  private static final String[] aqz = { "Child Support Payments" };
  private static final String[] aqu = { "Child Support Payments", "Child Support Payments" };
  private static final String[] aqF = { "Wire Templates Create" };
  private static final String[] aql = { "Wire Templates Create", "Wire Templates Create" };
  private static final String[] aqh = { "Wire Batch Domestic" };
  private static final String[] aqk = { "Wire Batch Domestic", "Wire Batch Domestic" };
  private static final String[] aqI = { "Wire Batch International" };
  private static final String[] aqr = { "Wire Batch International", "Wire Batch International" };
  private static final String[] aqE = { "Wire Batch Host" };
  private static final String[] aqq = { "Wire Batch Host", "Wire Batch Host" };
  private static final String[] aqn = { "Wire Batch Drawdown" };
  private static final String[] aqJ = { "Wire Batch Drawdown", "Wire Batch Drawdown" };
  private static final String[] aqx = { "Wire Batch FED" };
  private static final String[] aqL = { "Wire Batch FED", "Wire Batch FED" };
  private static final String[] aqv = { "Wire Batch Book" };
  private static final String[] aqC = { "Wire Batch Book", "Wire Batch Book" };
  private static String aqp;
  public static final String APPROVALS_RESOURCE_BUNDLE = "com.ffusion.approvals.resources";
  public static final String BANK_EMPLOYEE_DESC = "ApprovalsBankEmployeeDesc";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.initialize");
    UtilHandler.initialize(paramHashMap);
    aqp = HandlerUtil.getNameConvention(paramHashMap);
  }
  
  public static boolean useDemoMode()
    throws CSILException
  {
    boolean bool = false;
    String str = "Util.UseDemoMode";
    long l = System.currentTimeMillis();
    bool = UtilHandler.useDemoMode();
    PerfLog.log(str, l, true);
    debug((SecureUser)null, str);
    return bool;
  }
  
  public static void doApproval(SecureUser paramSecureUser, IApprovable paramIApprovable, int paramInt, HashMap paramHashMap, Limits paramLimits)
    throws CSILException
  {
    String str1 = "Util.doApproval";
    boolean bool1 = false;
    boolean bool2 = false;
    int i = 0;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    Limits localLimits = new Limits();
    if ((paramLimits != null) && (!paramLimits.isEmpty()))
    {
      Iterator localIterator = paramLimits.iterator();
      while (localIterator.hasNext())
      {
        Limit localLimit = (Limit)localIterator.next();
        if (!localLimit.isAllowedApproval())
        {
          i = 1;
          localLimits.add(localLimit);
        }
        else
        {
          String str2 = Entitlements.getEntitlementGroup(localLimit.getGroupId()).getEntGroupType();
          if ((str2.equals("Division")) || (str2.equals("Business")) || (str2.equals("Group"))) {
            bool2 = true;
          } else {
            bool1 = true;
          }
        }
      }
      if (i != 0)
      {
        paramHashMap.put("ExceededLimits", localLimits);
        throw new CSILException(str1, 20003, "Limit with no approval allowed");
      }
      if ((bool2) && (bool1)) {
        arrayOfString1 = aqs;
      } else if (bool1) {
        arrayOfString1 = aqj;
      } else {
        arrayOfString1 = aqm;
      }
      arrayOfString2 = jdMethod_for(paramIApprovable, bool1, bool2, paramLimits, paramSecureUser);
      try
      {
        Approvals.createApprovalItem(paramSecureUser, paramInt, paramIApprovable, arrayOfString1, arrayOfString2, paramHashMap);
      }
      catch (CSILException localCSILException)
      {
        throw localCSILException;
      }
    }
  }
  
  public static void doApproval(SecureUser paramSecureUser, IApprovable paramIApprovable, int paramInt, boolean paramBoolean1, boolean paramBoolean2, HashMap paramHashMap)
    throws CSILException
  {
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    if ((paramBoolean2) && (paramBoolean1)) {
      arrayOfString1 = aqs;
    } else if (paramBoolean1) {
      arrayOfString1 = aqj;
    } else if (paramBoolean2) {
      arrayOfString1 = aqm;
    } else {
      return;
    }
    arrayOfString2 = jdMethod_for(paramIApprovable, paramBoolean1, paramBoolean2, null, paramSecureUser);
    try
    {
      Approvals.createApprovalItem(paramSecureUser, paramInt, paramIApprovable, arrayOfString1, arrayOfString2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      throw localCSILException;
    }
  }
  
  private static String[] jdMethod_for(IApprovable paramIApprovable, boolean paramBoolean1, boolean paramBoolean2, Limits paramLimits, SecureUser paramSecureUser)
    throws CSILException
  {
    String[] arrayOfString = null;
    if ((paramIApprovable instanceof FundsTransaction))
    {
      int i = ((FundsTransaction)paramIApprovable).getType();
      switch (i)
      {
      case 1: 
      case 2: 
        if ((paramBoolean2) && (paramBoolean1)) {
          arrayOfString = aqA;
        } else {
          arrayOfString = aqB;
        }
        break;
      case 9: 
      case 10: 
        if ((paramBoolean2) && (paramBoolean1)) {
          arrayOfString = aqD;
        } else {
          arrayOfString = aqH;
        }
        break;
      case 15: 
        if ((paramBoolean2) && (paramBoolean1)) {
          arrayOfString = aqG;
        } else {
          arrayOfString = aqi;
        }
        break;
      case 16: 
        if ((paramBoolean2) && (paramBoolean1)) {
          arrayOfString = aqM;
        } else {
          arrayOfString = aqt;
        }
        break;
      case 12: 
      case 13: 
        if ((paramBoolean2) && (paramBoolean1)) {
          arrayOfString = aqo;
        } else {
          arrayOfString = aqK;
        }
        break;
      case 17: 
      case 18: 
        if ((paramBoolean2) && (paramBoolean1)) {
          arrayOfString = aqu;
        } else {
          arrayOfString = aqz;
        }
        break;
      case 3: 
      case 4: 
        if ((paramBoolean2) && (paramBoolean1)) {
          arrayOfString = aqw;
        } else {
          arrayOfString = aqy;
        }
        break;
      case 5: 
      case 6: 
        WireTransfer localWireTransfer = (WireTransfer)paramIApprovable;
        String str1 = localWireTransfer.getWireType();
        if ((str1.equalsIgnoreCase("TEMPLATE")) || (str1.equalsIgnoreCase("RECTEMPLATE")))
        {
          if ((paramBoolean2) && (paramBoolean1)) {
            arrayOfString = aql;
          } else {
            arrayOfString = aqF;
          }
        }
        else {
          arrayOfString = jdMethod_for(localWireTransfer, paramLimits, paramBoolean2, paramBoolean1, paramSecureUser);
        }
        break;
      case 14: 
        WireBatch localWireBatch = (WireBatch)paramIApprovable;
        String str2 = localWireBatch.getBatchDestination();
        if (str2.equals("DOMESTIC"))
        {
          if ((paramBoolean2) && (paramBoolean1)) {
            arrayOfString = aqk;
          } else {
            arrayOfString = aqh;
          }
        }
        else if (str2.equals("INTERNATIONAL"))
        {
          if ((paramBoolean2) && (paramBoolean1)) {
            arrayOfString = aqr;
          } else {
            arrayOfString = aqI;
          }
        }
        else if (str2.equals("HOST"))
        {
          if ((paramBoolean2) && (paramBoolean1)) {
            arrayOfString = aqq;
          } else {
            arrayOfString = aqE;
          }
        }
        else if (str2.equals("DRAWDOWN"))
        {
          if ((paramBoolean2) && (paramBoolean1)) {
            arrayOfString = aqJ;
          } else {
            arrayOfString = aqn;
          }
        }
        else if (str2.equals("FEDWIRE"))
        {
          if ((paramBoolean2) && (paramBoolean1)) {
            arrayOfString = aqL;
          } else {
            arrayOfString = aqx;
          }
        }
        else if (str2.equals("BOOKTRANSFER")) {
          if ((paramBoolean2) && (paramBoolean1)) {
            arrayOfString = aqC;
          } else {
            arrayOfString = aqv;
          }
        }
        break;
      case 7: 
      case 8: 
      case 11: 
      default: 
        arrayOfString = null;
      }
    }
    return arrayOfString;
  }
  
  public static SecureUser getSecureUserFromProfile(SecureUser paramSecureUser, int paramInt)
    throws CSILException
  {
    SecureUser localSecureUser = null;
    try
    {
      localSecureUser = CustomerAdapter.getSecureUserByProfileID(paramInt, new HashMap());
    }
    catch (Exception localException)
    {
      throw new CSILException(3005);
    }
    localSecureUser.setEntitlementID(getEntitlementIDFromCustomerProfile(paramInt));
    return localSecureUser;
  }
  
  public static int getEntitlementIDFromCustomerProfile(int paramInt)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = new EntitlementGroupMember(Locale.getDefault());
    localEntitlementGroupMember.setId(Integer.toString(paramInt));
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType(Integer.toString(1));
    localEntitlementGroupMember = Entitlements.getMember(localEntitlementGroupMember);
    return localEntitlementGroupMember.getEntitlementGroupId();
  }
  
  public static BusinessEmployee getBusinessEmployeeFromProfileID(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(String.valueOf(paramInt));
    BusinessEmployees localBusinessEmployees = UserAdminHandler.getBusinessEmployeesByIds(paramSecureUser, localArrayList, null);
    if (localBusinessEmployees.size() == 0) {
      return null;
    }
    return (BusinessEmployee)localBusinessEmployees.get(0);
  }
  
  private static String[] jdMethod_for(WireTransfer paramWireTransfer, Limits paramLimits, boolean paramBoolean1, boolean paramBoolean2, SecureUser paramSecureUser)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    String str1 = paramWireTransfer.getWireDestination();
    String str2 = paramWireTransfer.getWireSource();
    String[] arrayOfString1 = null;
    String str3 = null;
    int i = 0;
    int j = 0;
    try
    {
      debug("com.ffusion.csil.core.Util.getWireOperationTypes");
      debug("Wire Destination: " + str1);
      debug("Wire Source: " + str2);
      debug("useBank: " + paramBoolean2);
      debug("useBusiness: " + paramBoolean1);
      str3 = f(str2, str1);
      debug("Wire Type: " + str3);
      if (P(str3)) {
        return null;
      }
      String[] arrayOfString2 = WireApprovalWorkflowUtil.getOperationsByWireActivityType(str3);
      if (arrayOfString2 != null) {
        for (int k = 0; k < arrayOfString2.length; k++) {
          debug("Wire Approval Flow:" + k + arrayOfString2[k]);
        }
      }
      if (arrayOfString2 == null) {
        return null;
      }
      ApprovalsLevels localApprovalsLevels = null;
      String str4 = null;
      for (int m = 0; m < arrayOfString2.length; m++)
      {
        str4 = arrayOfString2[m];
        debug("approvalWorkflow: " + str4);
        if (paramBoolean1)
        {
          localApprovalsLevels = ApprovalsHandler.getWorkflowLevels(paramSecureUser, paramSecureUser.getBusinessID(), "Business_defined", str4, null);
          if (localApprovalsLevels != null) {
            break;
          }
        }
        else if (paramBoolean2)
        {
          localApprovalsLevels = ApprovalsHandler.getWorkflowLevels(paramSecureUser, paramSecureUser.getBusinessID(), "Bank_defined", str4, null);
          if (localApprovalsLevels != null) {
            break;
          }
        }
      }
      if (localApprovalsLevels == null) {
        return null;
      }
      debug("Selected approvalWorkflow: " + str4);
      if ((paramBoolean1) && (paramBoolean2)) {
        arrayOfString1 = new String[] { str4, str4 };
      } else {
        arrayOfString1 = new String[] { str4 };
      }
      return arrayOfString1;
    }
    catch (Exception localException)
    {
      debug("Unable to determine the wire operation type : Util.getWireOperationTypes()");
    }
    return null;
  }
  
  private static boolean P(String paramString)
  {
    return (paramString == null) || (paramString.trim().equals(""));
  }
  
  private static String f(String paramString1, String paramString2)
  {
    String str = null;
    if (paramString2.equals("HOST")) {
      str = "HOST_WIRE";
    } else if (paramString2.equals("DOMESTIC"))
    {
      if (paramString1.equals("FREEFORM")) {
        str = "DOMESTIC_FREEFORM_WIRE";
      } else if (paramString1.equals("TEMPLATE")) {
        str = "DOMESTIC_TEMPLATED_WIRE";
      }
    }
    else if (paramString2.equals("INTERNATIONAL"))
    {
      if (paramString1.equals("FREEFORM")) {
        str = "INTERNATIONAL_FREEFORM_WIRE";
      } else if (paramString1.equals("TEMPLATE")) {
        str = "INTERNATIONAL_TEMPLATED_WIRE";
      }
    }
    else if (paramString2.equals("FEDWIRE"))
    {
      if (paramString1.equals("FREEFORM")) {
        str = "FED_FREEFORM_WIRE";
      } else if (paramString1.equals("TEMPLATE")) {
        str = "FED_TEMPLATED_WIRE";
      }
    }
    else if (paramString2.equals("DRAWDOWN"))
    {
      if (paramString1.equals("FREEFORM")) {
        str = "DRAWDOWN_FREEFORM_WIRE";
      } else if (paramString1.equals("TEMPLATE")) {
        str = "DRAWDOWN_TEMPLATED_WIRE";
      }
    }
    else if (paramString2.equals("BOOKTRANSFER")) {
      if (paramString1.equals("FREEFORM")) {
        str = "BOOK_FREEFORM_WIRE";
      } else if (paramString1.equals("TEMPLATE")) {
        str = "BOOK_TEMPLATED_WIRE";
      }
    }
    return str;
  }
  
  protected static void insertApprovalInfo(FundsTransaction paramFundsTransaction, SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    if (paramHashMap1 == null) {
      paramHashMap1 = new HashMap();
    }
    String[] arrayOfString = { "Pending", "Rejected" };
    if ((paramHashMap1.get("BusinessID") == null) && (paramHashMap1.get("SubmittingUser") == null)) {
      if (paramSecureUser.getBusinessID() != 0) {
        paramHashMap1.put("BusinessID", new Integer(paramSecureUser.getBusinessID()));
      } else {
        paramHashMap1.put("SubmittingUser", new Integer(paramSecureUser.getProfileID()));
      }
    }
    paramHashMap1.put("TrackingID", paramFundsTransaction.getTrackingID());
    ApprovalsStatuses localApprovalsStatuses = Approvals.getSubmittedItems(1, arrayOfString, paramHashMap1, paramHashMap2);
    if ((localApprovalsStatuses != null) && (localApprovalsStatuses.size() > 0))
    {
      paramFundsTransaction.setExternalID(paramFundsTransaction.getID());
      Iterator localIterator = localApprovalsStatuses.iterator();
      if (localIterator.hasNext())
      {
        ApprovalsStatus localApprovalsStatus = (ApprovalsStatus)localIterator.next();
        ApprovalsItem localApprovalsItem = localApprovalsStatus.getApprovalItem();
        paramFundsTransaction.setApprovalID(String.valueOf(localApprovalsItem.getItemID()));
        Object localObject4;
        if (localApprovalsStatus.getCurrentChainItem() != null)
        {
          Object localObject1;
          if (localApprovalsStatus.getCurrentChainItem().isUsingUser())
          {
            localObject1 = new ArrayList();
            ((ArrayList)localObject1).add("" + localApprovalsStatus.getCurrentChainItem().getGroupOrUserID());
            Object localObject2;
            if (localApprovalsStatus.getCurrentChainItem().getUserType() == 2)
            {
              localObject2 = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsBankEmployeeDesc", null);
              paramFundsTransaction.setApproverName((String)((ILocalizable)localObject2).localize(paramSecureUser.getLocale()));
              paramFundsTransaction.setApproverIsGroup(false);
            }
            else
            {
              try
              {
                localObject2 = CustomerAdapter.getBusinessEmployeesByIds((ArrayList)localObject1);
                if (((BusinessEmployees)localObject2).size() > 0)
                {
                  localObject4 = (BusinessEmployee)((BusinessEmployees)localObject2).get(0);
                  paramFundsTransaction.setApproverName(((BusinessEmployee)localObject4).getName());
                  paramFundsTransaction.setApproverIsGroup(false);
                }
              }
              catch (ProfileException localProfileException) {}
            }
          }
          else if (localApprovalsStatus.getCurrentChainItem().getIsApprovalsGroup())
          {
            localObject1 = ApprovalsHandler.getApprovalGroups(paramSecureUser, paramHashMap2);
            localObject3 = ((ApprovalsGroups)localObject1).getByID(localApprovalsStatus.getCurrentChainItem().getGroupOrUserID());
            paramFundsTransaction.setApproverName(((ApprovalsGroup)localObject3).getGroupName());
            paramFundsTransaction.setApproverIsGroup(true);
          }
          else
          {
            localObject1 = Entitlements.getEntitlementGroup(localApprovalsStatus.getCurrentChainItem().getGroupOrUserID());
            paramFundsTransaction.setApproverName(((EntitlementGroup)localObject1).getGroupName());
            paramFundsTransaction.setApproverIsGroup(true);
          }
        }
        int i = localApprovalsItem.getSubmittingUserID();
        Object localObject3 = null;
        try
        {
          localObject4 = CustomerAdapter.getUserById(i);
          localObject3 = ((User)localObject4).getName();
        }
        catch (Exception localException) {}
        if (localObject3 == null)
        {
          localObject5 = new LocalizableString("com.ffusion.approvals.resources", "ApprovalsBankEmployeeDesc", null);
          localObject3 = (String)((ILocalizable)localObject5).localize(paramSecureUser.getLocale());
        }
        paramFundsTransaction.setUserName((String)localObject3);
        Object localObject5 = localApprovalsStatus.getDecision();
        if ((localObject5 != null) && (((String)localObject5).equals("Rejected")))
        {
          String str = localApprovalsItem.getApprovalItemProperty("RejectReason");
          if ((str != null) && (str.length() > 0)) {
            paramFundsTransaction.setRejectReason(str);
          }
        }
      }
    }
  }
  
  public static ArrayList getValidPhoneFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getValidPhoneFormats");
    return UtilHandler.getValidPhoneFormats(paramSecureUser, paramString, paramHashMap);
  }
  
  public static ArrayList getValidZipCodeFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getValidZipCodeFormats");
    return UtilHandler.getValidZipCodeFormats(paramSecureUser, paramString, paramHashMap);
  }
  
  public static ArrayList getValidSSNFormats(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getValidSSNFormats");
    return UtilHandler.getValidSSNFormats(paramSecureUser, paramString, paramHashMap);
  }
  
  public static String validatePhoneFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.validatePhoneFormat");
    return UtilHandler.validatePhoneFormat(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static String validateZipCodeFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.validateZipCodeFormat");
    return UtilHandler.validateZipCodeFormat(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static String validateSSNFormat(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.validateSSNFormat");
    return UtilHandler.validateSSNFormat(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static boolean isRegisteredCountry(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.isRegisteredCountry");
    return UtilHandler.isRegisteredCountry(paramSecureUser, paramString, paramHashMap);
  }
  
  public static StateProvinceDefns getStatesForCountry(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getStatesForCountry");
    return UtilHandler.getStatesForCountry(paramSecureUser, paramString, paramHashMap);
  }
  
  public static StateProvinceDefn getStateProvinceDefnForState(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getStateProvinceDefnForState");
    return UtilHandler.getStateProvinceDefnForState(paramSecureUser, paramString1, paramString2, paramHashMap);
  }
  
  public static LanguageDefns getLanguageList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getLanguageList");
    return UtilHandler.getLanguageList(paramSecureUser, paramHashMap);
  }
  
  public static LanguageDefn getLanguage(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getLanguage");
    return UtilHandler.getLanguage(paramSecureUser, paramString, paramHashMap);
  }
  
  public static Locale getDefaultLanguage(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getDefaultLangauge");
    return UtilHandler.getDefaultLanguage(paramHashMap);
  }
  
  public static String getCodeForBankLookupCountryName(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getCodeForBankLookupCountryName");
    return UtilHandler.getCodeForBankLookupCountryName(paramSecureUser, paramString, paramHashMap);
  }
  
  public static CountryDefns getBankLookupStandardCountryNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getBankLookupStandardCountryNames");
    return UtilHandler.getBankLookupStandardCountryNames(paramSecureUser, paramHashMap);
  }
  
  public static CountryDefns getCountryList(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Util.getCountryList");
    return UtilHandler.getCountryList(paramSecureUser, paramHashMap);
  }
  
  public static String getNameConvention()
  {
    debug("com.ffusion.csil.core.Util.getNameConvention");
    return aqp;
  }
  
  public static String getLimitBaseCurrency()
  {
    debug("com.ffusion.csil.core.Util.getLimitBaseCurrency");
    return UtilHandler.getLimitBaseCurrency();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Util
 * JD-Core Version:    0.7.0.1
 */