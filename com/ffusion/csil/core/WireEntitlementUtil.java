package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class WireEntitlementUtil
{
  public static Entitlement ENT_BC_WIRE_TEMPLATE_MGMT = new Entitlement("BC Wire Template Management", null, null);
  public static Hashtable entTree = new Hashtable();
  public static Hashtable apprEntTree;
  public static Hashtable apprLmtTree;
  
  public static boolean checkWireEntitlements(SecureUser paramSecureUser, String paramString)
    throws CSILException
  {
    if (paramString == null) {
      return false;
    }
    String[] arrayOfString = (String[])entTree.get(paramString);
    if (arrayOfString == null) {
      return false;
    }
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    for (int i = 0; i < arrayOfString.length; i++)
    {
      Entitlement localEntitlement = new Entitlement(arrayOfString[i], null, null);
      if (!Entitlements.checkEntitlement(localEntitlementGroupMember, localEntitlement)) {
        return false;
      }
    }
    return true;
  }
  
  public static boolean checkWireEntitlements(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = getEntitlementFlowName(paramWireTransfer.getWireDestination(), paramWireTransfer.getWireSource());
    return checkWireEntitlements(paramSecureUser, paramWireTransfer, str, paramHashMap);
  }
  
  public static boolean checkWireEntitlements(SecureUser paramSecureUser, WireTransfer paramWireTransfer, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return false;
    }
    String[] arrayOfString = (String[])entTree.get(paramString);
    if (arrayOfString == null) {
      return false;
    }
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    for (int i = 0; i < arrayOfString.length; i++)
    {
      MultiEntitlement localMultiEntitlement = getMultiEntitlementObject(paramSecureUser, arrayOfString[i], paramWireTransfer, false);
      Entitlement localEntitlement = EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID());
      if (localEntitlement != null)
      {
        if (paramHashMap == null) {
          paramHashMap = new HashMap();
        }
        paramHashMap.put("ENTITLEMENT_KEY", localEntitlement);
        return false;
      }
    }
    return true;
  }
  
  public static MultiEntitlement getMultiEntitlementObject(SecureUser paramSecureUser, String paramString, WireTransfer paramWireTransfer)
    throws CSILException
  {
    return getMultiEntitlementObject(paramSecureUser, paramString, paramWireTransfer, true);
  }
  
  public static MultiEntitlement getMultiEntitlementObject(SecureUser paramSecureUser, String paramString, WireTransfer paramWireTransfer, boolean paramBoolean)
    throws CSILException
  {
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { paramString });
    if (paramWireTransfer.getFromAccountID() == null) {
      return localMultiEntitlement;
    }
    if ((paramString.equals("Wires")) || (paramString.equals("Wires Batch Create"))) {
      return localMultiEntitlement;
    }
    if ((paramString.equals("Wire Entry")) || (paramString.equals("Wire Approval")))
    {
      localMultiEntitlement.setObjects(new String[] { "Wire Template" }, new String[] { paramWireTransfer.getTemplateID() });
      return localMultiEntitlement;
    }
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramWireTransfer) });
    if (paramBoolean == true) {
      localMultiEntitlement = EntitlementsUtil.appendAccountGroups(localMultiEntitlement, paramSecureUser.getBusinessID());
    }
    return localMultiEntitlement;
  }
  
  public static MultiEntitlement getMultiEntitlementObject(SecureUser paramSecureUser, String[] paramArrayOfString, WireTransfer paramWireTransfer)
    throws CSILException
  {
    return getMultiEntitlementObject(paramSecureUser, paramArrayOfString, paramWireTransfer, true);
  }
  
  public static MultiEntitlement getMultiEntitlementObject(SecureUser paramSecureUser, String[] paramArrayOfString, WireTransfer paramWireTransfer, boolean paramBoolean)
    throws CSILException
  {
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(paramArrayOfString);
    try
    {
      if (paramWireTransfer.getFromAccountID() == null) {
        return localMultiEntitlement;
      }
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      if (paramWireTransfer.getWireSource().equals("TEMPLATE"))
      {
        localArrayList1.add("Wire Template");
        localArrayList2.add(paramWireTransfer.getTemplateID());
      }
      localArrayList1.add("Account");
      localArrayList2.add(EntitlementsUtil.getEntitlementObjectId(paramWireTransfer));
      localMultiEntitlement.setObjects((String[])localArrayList1.toArray(new String[0]), (String[])localArrayList2.toArray(new String[0]));
      if (paramBoolean == true) {
        localMultiEntitlement = EntitlementsUtil.appendAccountGroups(localMultiEntitlement, paramSecureUser.getBusinessID());
      }
    }
    catch (Exception localException) {}
    return localMultiEntitlement;
  }
  
  public static boolean checkAccountEntitlements(SecureUser paramSecureUser, Account paramAccount, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return false;
    }
    String[] arrayOfString = (String[])entTree.get(paramString);
    if (arrayOfString == null) {
      return false;
    }
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    for (int i = 0; i < arrayOfString.length; i++)
    {
      MultiEntitlement localMultiEntitlement = getMultiEntitlementObject(paramSecureUser, arrayOfString[i], paramAccount, false);
      Entitlement localEntitlement = EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID());
      if (localEntitlement != null)
      {
        if (paramHashMap == null) {
          paramHashMap = new HashMap();
        }
        paramHashMap.put("ENTITLEMENT_KEY", localEntitlement);
        return false;
      }
    }
    return true;
  }
  
  public static MultiEntitlement getMultiEntitlementObject(SecureUser paramSecureUser, String paramString, Account paramAccount)
    throws CSILException
  {
    return getMultiEntitlementObject(paramSecureUser, paramString, paramAccount, true);
  }
  
  public static MultiEntitlement getMultiEntitlementObject(SecureUser paramSecureUser, String paramString, Account paramAccount, boolean paramBoolean)
    throws CSILException
  {
    MultiEntitlement localMultiEntitlement = new MultiEntitlement();
    localMultiEntitlement.setOperations(new String[] { paramString });
    if (paramAccount == null) {
      return localMultiEntitlement;
    }
    if ((paramString.equals("Wires")) || (paramString.equals("Wires Batch Create"))) {
      return localMultiEntitlement;
    }
    localMultiEntitlement.setObjects(new String[] { "Account" }, new String[] { EntitlementsUtil.getEntitlementObjectId(paramAccount) });
    if (paramBoolean == true) {
      localMultiEntitlement = EntitlementsUtil.appendAccountGroups(localMultiEntitlement, paramSecureUser.getBusinessID());
    }
    return localMultiEntitlement;
  }
  
  public static void checkEntitlements(SecureUser paramSecureUser, Entitlement paramEntitlement, String paramString)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (!Entitlements.checkEntitlement(localEntitlementGroupMember, paramEntitlement))
    {
      Wire.debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + paramString);
      throw new CSILException(paramString, 20001);
    }
  }
  
  public static void checkViewEntitlements(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    if (Entitlements.checkEntitlement(localEntitlementGroupMember, Wire.ENT_WIRES_ADMIN_VIEW) == true)
    {
      Wire.debug("User: " + paramSecureUser.getUserName() + " has permission to view wires submittedBy others in the company");
    }
    else
    {
      Wire.debug("User: " + paramSecureUser.getUserName() + " is not entitled to view wires submittedBy others in the company");
      throw new CSILException(paramString, 20001);
    }
  }
  
  public static String[] getOperationsByFlow(String paramString)
  {
    String[] arrayOfString = (String[])entTree.get(paramString);
    if (arrayOfString != null) {
      return arrayOfString;
    }
    return null;
  }
  
  public static String getEntitlementFlowName(String paramString1, String paramString2)
  {
    String str = "";
    if ((paramString1 == null) || (paramString2 == null)) {
      return str;
    }
    if (paramString2.equalsIgnoreCase("FREEFORM"))
    {
      if (paramString1.equalsIgnoreCase("DOMESTIC") == true) {
        str = "DOMESTIC_FREEFORM_WIRE";
      } else if (paramString1.equalsIgnoreCase("INTERNATIONAL") == true) {
        str = "INTERNATIONAL_FREEFORM_WIRE";
      } else if (paramString1.equalsIgnoreCase("DRAWDOWN") == true) {
        str = "DRAWDOWN_FREEFORM_WIRE";
      } else if (paramString1.equalsIgnoreCase("FEDWIRE") == true) {
        str = "FED_FREEFORM_WIRE";
      } else if (paramString1.equalsIgnoreCase("BOOKTRANSFER") == true) {
        str = "BOOK_FREEFORM_WIRE";
      }
    }
    else if (paramString2.equalsIgnoreCase("TEMPLATE"))
    {
      if (paramString1.equalsIgnoreCase("DOMESTIC") == true) {
        str = "DOMESTIC_TEMPLATED_WIRE";
      } else if (paramString1.equalsIgnoreCase("INTERNATIONAL") == true) {
        str = "INTERNATIONAL_TEMPLATED_WIRE";
      } else if (paramString1.equalsIgnoreCase("DRAWDOWN") == true) {
        str = "DRAWDOWN_TEMPLATED_WIRE";
      } else if (paramString1.equalsIgnoreCase("FEDWIRE") == true) {
        str = "FED_TEMPLATED_WIRE";
      } else if (paramString1.equalsIgnoreCase("BOOKTRANSFER") == true) {
        str = "BOOK_TEMPLATED_WIRE";
      }
    }
    else if (paramString2.equalsIgnoreCase("HOST")) {
      str = "HOST_WIRE";
    }
    return str;
  }
  
  public static boolean checkWireApprovalEntitlements(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = null;
    String str2 = paramWireTransfer.getWireType();
    if ((str2.equalsIgnoreCase("TEMPLATE")) || (str2.equalsIgnoreCase("RECTEMPLATE"))) {
      str1 = "TEMPLATE_WIRE";
    } else {
      str1 = getEntitlementFlowName(paramWireTransfer.getWireDestination(), paramWireTransfer.getWireSource());
    }
    if ((str1 == null) || (str1.length() == 0)) {
      return false;
    }
    String[] arrayOfString = (String[])apprEntTree.get(str1);
    if (arrayOfString == null) {
      return false;
    }
    EntitlementGroupMember localEntitlementGroupMember = EntitlementsUtil.getEntitlementGroupMember(paramSecureUser);
    for (int i = 0; i < arrayOfString.length; i++)
    {
      MultiEntitlement localMultiEntitlement = getMultiEntitlementObject(paramSecureUser, arrayOfString[i], paramWireTransfer, false);
      Entitlement localEntitlement = EntitlementsUtil.checkAccountEntitlement(localEntitlementGroupMember, localMultiEntitlement, paramSecureUser.getBusinessID());
      if (localEntitlement != null)
      {
        if (paramHashMap == null) {
          paramHashMap = new HashMap();
        }
        paramHashMap.put("ENTITLEMENT_KEY", localEntitlement);
        return false;
      }
    }
    return true;
  }
  
  public static String[] getApprovalOperationsByFlow(String paramString)
  {
    String[] arrayOfString = (String[])apprEntTree.get(paramString);
    if (arrayOfString != null) {
      return arrayOfString;
    }
    return null;
  }
  
  public static String[] getApprovalLimitOperationsByFlow(String paramString)
  {
    String[] arrayOfString = (String[])apprLmtTree.get(paramString);
    if (arrayOfString != null) {
      return arrayOfString;
    }
    return null;
  }
  
  public static boolean checkWireTemplateEntitlements(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Wire.checkWireTemplateEntitlements";
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    if (paramSecureUser.getUserType() == 2)
    {
      checkEntitlements(paramSecureUser, ENT_BC_WIRE_TEMPLATE_MGMT, str);
    }
    else
    {
      boolean bool = checkWireEntitlements(paramSecureUser, paramWireTransfer, "TEMPLATE_WIRE", paramHashMap);
      if (!bool)
      {
        if (paramHashMap != null)
        {
          Entitlement localEntitlement = (Entitlement)paramHashMap.get("ENTITLEMENT_KEY");
          if (localEntitlement != null) {
            Wire.debug("User: " + paramSecureUser.getUserName() + " is not entitled to " + localEntitlement.getOperationName());
          }
        }
        else
        {
          Wire.debug("User: " + paramSecureUser.getUserName() + " is not entitled to execute " + str);
        }
        return false;
      }
    }
    return true;
  }
  
  static
  {
    String[] arrayOfString1 = { "Wire Book Freeform", "Wire Book", "Wires Create", "Wires" };
    String[] arrayOfString2 = { "Wire Domestic Freeform", "Wire Domestic", "Wires Create", "Wires" };
    String[] arrayOfString3 = { "Wire International Freeform", "Wire International", "Wires Create", "Wires" };
    String[] arrayOfString4 = { "Wire FED Freeform", "Wire FED", "Wires Create", "Wires" };
    String[] arrayOfString5 = { "Wire Drawdown Freeform", "Wire Drawdown", "Wires Create", "Wires" };
    String[] arrayOfString6 = { "Wire Host", "Wires Create", "Wires" };
    entTree.put("BOOK_FREEFORM_WIRE", arrayOfString1);
    entTree.put("DOMESTIC_FREEFORM_WIRE", arrayOfString2);
    entTree.put("INTERNATIONAL_FREEFORM_WIRE", arrayOfString3);
    entTree.put("FED_FREEFORM_WIRE", arrayOfString4);
    entTree.put("DRAWDOWN_FREEFORM_WIRE", arrayOfString5);
    entTree.put("HOST_WIRE", arrayOfString6);
    String[] arrayOfString7 = { "Wire Entry", "Wire Book Templated", "Wire Book", "Wires Create", "Wires" };
    String[] arrayOfString8 = { "Wire Entry", "Wire Domestic Templated", "Wire Domestic", "Wires Create", "Wires" };
    String[] arrayOfString9 = { "Wire Entry", "Wire International Templated", "Wire International", "Wires Create", "Wires" };
    String[] arrayOfString10 = { "Wire Entry", "Wire FED Templated", "Wire FED", "Wires Create", "Wires" };
    String[] arrayOfString11 = { "Wire Entry", "Wire Drawdown Templated", "Wire Drawdown", "Wires Create", "Wires" };
    entTree.put("BOOK_TEMPLATED_WIRE", arrayOfString7);
    entTree.put("DOMESTIC_TEMPLATED_WIRE", arrayOfString8);
    entTree.put("INTERNATIONAL_TEMPLATED_WIRE", arrayOfString9);
    entTree.put("FED_TEMPLATED_WIRE", arrayOfString10);
    entTree.put("DRAWDOWN_TEMPLATED_WIRE", arrayOfString11);
    String[] arrayOfString12 = { "Wires Batch Create", "Wires" };
    entTree.put("BATCH_WIRE", arrayOfString12);
    String[] arrayOfString13 = { "Wires", "Wire Templates Create" };
    entTree.put("TEMPLATE_WIRE", arrayOfString13);
    apprEntTree = new Hashtable();
    String[] arrayOfString14 = { "Wire Book Freeform Approval" };
    String[] arrayOfString15 = { "Wire Domestic Freeform Approval" };
    String[] arrayOfString16 = { "Wire International Freeform Approval" };
    String[] arrayOfString17 = { "Wire FED Freeform Approval" };
    String[] arrayOfString18 = { "Wire Drawdown Freeform Approval" };
    String[] arrayOfString19 = { "Wire Host Approval" };
    apprEntTree.put("BOOK_FREEFORM_WIRE", arrayOfString14);
    apprEntTree.put("DOMESTIC_FREEFORM_WIRE", arrayOfString15);
    apprEntTree.put("INTERNATIONAL_FREEFORM_WIRE", arrayOfString16);
    apprEntTree.put("FED_FREEFORM_WIRE", arrayOfString17);
    apprEntTree.put("DRAWDOWN_FREEFORM_WIRE", arrayOfString18);
    apprEntTree.put("HOST_WIRE", arrayOfString19);
    String[] arrayOfString20 = { "Wire Approval", "Wire Book Templated Approval" };
    String[] arrayOfString21 = { "Wire Approval", "Wire Domestic Templated Approval" };
    String[] arrayOfString22 = { "Wire Approval", "Wire International Templated Approval" };
    String[] arrayOfString23 = { "Wire Approval", "Wire FED Templated Approval" };
    String[] arrayOfString24 = { "Wire Approval", "Wire Drawdown Templated Approval" };
    apprEntTree.put("BOOK_TEMPLATED_WIRE", arrayOfString20);
    apprEntTree.put("DOMESTIC_TEMPLATED_WIRE", arrayOfString21);
    apprEntTree.put("INTERNATIONAL_TEMPLATED_WIRE", arrayOfString22);
    apprEntTree.put("FED_TEMPLATED_WIRE", arrayOfString23);
    apprEntTree.put("DRAWDOWN_TEMPLATED_WIRE", arrayOfString24);
    String[] arrayOfString25 = { "Wire Templates Approval" };
    apprEntTree.put("TEMPLATE_WIRE", arrayOfString25);
    apprLmtTree = new Hashtable();
    String[] arrayOfString26 = { "Wire Book Freeform Approval" };
    String[] arrayOfString27 = { "Wire Domestic Freeform Approval" };
    String[] arrayOfString28 = { "Wire International Freeform Approval" };
    String[] arrayOfString29 = { "Wire FED Freeform Approval" };
    String[] arrayOfString30 = { "Wire Drawdown Freeform Approval" };
    String[] arrayOfString31 = { "Wire Host Approval" };
    apprLmtTree.put("BOOK_FREEFORM_WIRE", arrayOfString26);
    apprLmtTree.put("DOMESTIC_FREEFORM_WIRE", arrayOfString27);
    apprLmtTree.put("INTERNATIONAL_FREEFORM_WIRE", arrayOfString28);
    apprLmtTree.put("FED_FREEFORM_WIRE", arrayOfString29);
    apprLmtTree.put("DRAWDOWN_FREEFORM_WIRE", arrayOfString30);
    apprLmtTree.put("HOST_WIRE", arrayOfString31);
    String[] arrayOfString32 = { "Wire Book Templated Approval" };
    String[] arrayOfString33 = { "Wire Domestic Templated Approval" };
    String[] arrayOfString34 = { "Wire International Templated Approval" };
    String[] arrayOfString35 = { "Wire FED Templated Approval" };
    String[] arrayOfString36 = { "Wire Drawdown Templated Approval" };
    apprLmtTree.put("BOOK_TEMPLATED_WIRE", arrayOfString32);
    apprLmtTree.put("DOMESTIC_TEMPLATED_WIRE", arrayOfString33);
    apprLmtTree.put("INTERNATIONAL_TEMPLATED_WIRE", arrayOfString34);
    apprLmtTree.put("FED_TEMPLATED_WIRE", arrayOfString35);
    apprLmtTree.put("DRAWDOWN_TEMPLATED_WIRE", arrayOfString36);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.WireEntitlementUtil
 * JD-Core Version:    0.7.0.1
 */