package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FilterEntitlementsForBusiness
  extends BaseTask
  implements AdminTask
{
  protected String _inListName;
  protected String _outListName;
  protected String _objectType;
  protected String _objectId;
  protected int _entitlementGroupId;
  protected HashMap _servicePackagePermissionsMap;
  protected String OPNAME_OVERALL_ACH_PAYMENT_APPROVAL = EntitlementsUtil.getACHEntitlementName("Overall", "ACH Payment Approval");
  protected String OPNAME_OVERALL_ACH_PAYMENT_ENTRY = EntitlementsUtil.getACHEntitlementName("Overall", "ACH Payment Entry");
  protected Entitlement ENT_APPROVALS_ADMIN = new Entitlement("Approvals Admin", null, null);
  protected Entitlement ENT_ACH_BATCH = new Entitlement("ACHBatch", null, null);
  protected Entitlement ENT_CHILD_BATCH = new Entitlement("CCD + DED", null, null);
  protected Entitlement ENT_TAX_BATCH = new Entitlement("CCD + TXP", null, null);
  protected Entitlement ENT_CASHCON_DEPOSIT = new Entitlement("Cash Con - Deposit Entry", null, null);
  protected Entitlement ENT_CASHCON_DISBURSEMENT = new Entitlement("Cash Con - Disbursement Request", null, null);
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if ((this._inListName == null) || (this._inListName.equals("")) || (this._outListName == null) || (this._outListName.equals("")))
    {
      this.error = 94;
      return super.getTaskErrorURL();
    }
    EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = (EntitlementTypePropertyLists)localHttpSession.getAttribute(this._inListName);
    if (localEntitlementTypePropertyLists1 == null)
    {
      this.error = 4549;
      return super.getTaskErrorURL();
    }
    EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = new EntitlementTypePropertyLists();
    try
    {
      initServicePackagePermissionsMap();
      int i = getServicePackageEntGroupId(this._entitlementGroupId);
      Iterator localIterator = localEntitlementTypePropertyLists1.iterator();
      while (localIterator.hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
        if (allowedByServicePackageFeatures(localEntitlementTypePropertyList.getOperationName(), i))
        {
          MultiEntitlement localMultiEntitlement = new MultiEntitlement();
          String[] arrayOfString1 = new String[1];
          arrayOfString1[0] = this._objectType;
          String[] arrayOfString2 = new String[1];
          arrayOfString2[0] = this._objectId;
          localMultiEntitlement.setObjects(arrayOfString1, arrayOfString2);
          Vector localVector = new Vector();
          localVector.add(localEntitlementTypePropertyList.getOperationName());
          jdMethod_for(localEntitlementTypePropertyList, localVector);
          String[] arrayOfString3 = new String[localVector.size()];
          for (int j = 0; j < localVector.size(); j++) {
            arrayOfString3[j] = ((String)localVector.get(j));
          }
          localMultiEntitlement.setOperations(arrayOfString3);
          if (Entitlements.checkEntitlement(this._entitlementGroupId, localMultiEntitlement) == null) {
            localEntitlementTypePropertyLists2.add(localEntitlementTypePropertyList);
          }
        }
      }
      localHttpSession.setAttribute(this._outListName, localEntitlementTypePropertyLists2);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = super.getServiceErrorURL();
    }
    catch (Exception localException)
    {
      this.error = -1009;
      str = super.getServiceErrorURL();
    }
    return str;
  }
  
  private void jdMethod_for(EntitlementTypePropertyList paramEntitlementTypePropertyList, Vector paramVector)
    throws Exception
  {
    int i = paramEntitlementTypePropertyList.numPropertyValues("control parent");
    for (int j = 0; j < i; j++)
    {
      String str = paramEntitlementTypePropertyList.getPropertyValue("control parent", j);
      paramVector.add(str);
      EntitlementTypePropertyList localEntitlementTypePropertyList = Entitlements.getEntitlementTypeWithProperties(str);
      jdMethod_for(localEntitlementTypePropertyList, paramVector);
    }
  }
  
  protected int getServicePackageEntGroupId(int paramInt)
    throws Exception
  {
    for (EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramInt); !localEntitlementGroup.getEntGroupType().equals("ServicesPackage"); localEntitlementGroup = Entitlements.getEntitlementGroup(localEntitlementGroup.getParentId())) {}
    return localEntitlementGroup.getGroupId();
  }
  
  protected boolean allowedByServicePackageFeatures(String paramString, int paramInt)
    throws Exception
  {
    if (this._servicePackagePermissionsMap.containsKey(paramString))
    {
      MultiEntitlement localMultiEntitlement = (MultiEntitlement)this._servicePackagePermissionsMap.get(paramString);
      return Entitlements.checkEntitlement(paramInt, localMultiEntitlement) == null;
    }
    if (paramString.equals("Cash Con - Reporting")) {
      return (Entitlements.checkEntitlement(paramInt, this.ENT_CASHCON_DEPOSIT)) || (Entitlements.checkEntitlement(paramInt, this.ENT_CASHCON_DISBURSEMENT));
    }
    if (paramString.equals(this.OPNAME_OVERALL_ACH_PAYMENT_ENTRY)) {
      return (Entitlements.checkEntitlement(paramInt, this.ENT_ACH_BATCH)) || (Entitlements.checkEntitlement(paramInt, this.ENT_CHILD_BATCH)) || (Entitlements.checkEntitlement(paramInt, this.ENT_TAX_BATCH));
    }
    if (paramString.equals(this.OPNAME_OVERALL_ACH_PAYMENT_APPROVAL)) {
      return (Entitlements.checkEntitlement(paramInt, this.ENT_APPROVALS_ADMIN)) && ((Entitlements.checkEntitlement(paramInt, this.ENT_ACH_BATCH)) || (Entitlements.checkEntitlement(paramInt, this.ENT_CHILD_BATCH)) || (Entitlements.checkEntitlement(paramInt, this.ENT_TAX_BATCH)));
    }
    return true;
  }
  
  protected void initServicePackagePermissionsMap()
  {
    String[] arrayOfString1 = { "Approvals Admin" };
    MultiEntitlement localMultiEntitlement1 = new MultiEntitlement();
    localMultiEntitlement1.setOperations(arrayOfString1);
    String[] arrayOfString2 = { "Approvals Admin", "Wires" };
    MultiEntitlement localMultiEntitlement2 = new MultiEntitlement();
    localMultiEntitlement2.setOperations(arrayOfString2);
    String[] arrayOfString3 = { "Cash Con - Disbursement Request" };
    MultiEntitlement localMultiEntitlement3 = new MultiEntitlement();
    localMultiEntitlement3.setOperations(arrayOfString3);
    String[] arrayOfString4 = { "Cash Con - Deposit Entry" };
    MultiEntitlement localMultiEntitlement4 = new MultiEntitlement();
    localMultiEntitlement4.setOperations(arrayOfString4);
    String[] arrayOfString5 = { "ACHBatch" };
    MultiEntitlement localMultiEntitlement5 = new MultiEntitlement();
    localMultiEntitlement5.setOperations(arrayOfString5);
    this._servicePackagePermissionsMap = new HashMap();
    this._servicePackagePermissionsMap.put("Manage ACH Participants", localMultiEntitlement5);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("ACHBatch", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("ARC", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("CCD", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("CCD + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("CCD + DED", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("CCD + TXP", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("CIE", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("CIE + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("CTX", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("CTX + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("XCK", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("WEB", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("WEB + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("PPD", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("PPD + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("POP", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("RCK", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put(EntitlementsUtil.getACHEntitlementName("TEL", "ACH Payment Approval"), localMultiEntitlement1);
    this._servicePackagePermissionsMap.put("Wire Domestic Templated Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire Domestic Freeform Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire International Templated Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire International Freeform Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire Host Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire Drawdown Templated Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire Drawdown Freeform Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire FED Templated Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire FED Freeform Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire Book Templated Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire Book Freeform Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire Approval", localMultiEntitlement2);
    this._servicePackagePermissionsMap.put("Wire Templates Approval", localMultiEntitlement1);
    this._servicePackagePermissionsMap.put("Cash Con - Disbursement Request View Other", localMultiEntitlement3);
    this._servicePackagePermissionsMap.put("Cash Con - Disbursement Request Delete Other", localMultiEntitlement3);
    this._servicePackagePermissionsMap.put("Cash Con - Disbursement Request Edit Other", localMultiEntitlement3);
    this._servicePackagePermissionsMap.put("Cash Con - Deposit Entry View Other", localMultiEntitlement4);
    this._servicePackagePermissionsMap.put("Cash Con - Deposit Entry Delete Other", localMultiEntitlement4);
    this._servicePackagePermissionsMap.put("Cash Con - Deposit Entry Edit Other", localMultiEntitlement4);
  }
  
  public void setEntitlementTypePropertyListsBeforeFilter(String paramString)
  {
    this._inListName = paramString;
  }
  
  public void setEntitlementTypePropertyListsAfterFilter(String paramString)
  {
    this._outListName = paramString;
  }
  
  public void setObjectType(String paramString)
  {
    this._objectType = paramString;
  }
  
  public String getObjectType()
  {
    return this._objectType;
  }
  
  public void setObjectId(String paramString)
  {
    this._objectId = paramString;
  }
  
  public String getObjectId()
  {
    return this._objectId;
  }
  
  public void setEntitlementGroupId(String paramString)
  {
    this._entitlementGroupId = Integer.parseInt(paramString);
  }
  
  public String getEntitlementGroupId()
  {
    return Integer.toString(this._entitlementGroupId);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.FilterEntitlementsForBusiness
 * JD-Core Version:    0.7.0.1
 */