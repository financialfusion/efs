package com.ffusion.efs.adapters.entitlementsReporting;

import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.beans.entitlements.MultiEntitlement;
import com.ffusion.csil.beans.entitlements.ObjectTypePropertyList;
import com.ffusion.csil.beans.entitlements.TypePropertyList;
import com.ffusion.csil.beans.entitlementsReporting.ReportEntitlementComparator;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.efs.adapters.entitlements.EntitlementException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.util.beans.DateTime;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class EntitlementReportingAdapter
{
  public static final int USERTYPE_USER = 1;
  public static final int USERTYPE_BUSINESS = 2;
  public static final int USERTYPE_AGENT = 3;
  private static final String jdField_int = com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("Overall", "ACH Payment Approval");
  private static final String jdField_do = com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("Overall", "ACH Payment Entry");
  private static final Entitlement jdField_case = new Entitlement("Approvals Admin", null, null);
  private static final Entitlement jdField_char = new Entitlement("ACHBatch", null, null);
  private static final Entitlement jdField_for = new Entitlement("CCD + DED", null, null);
  private static final Entitlement jdField_if = new Entitlement("CCD + TXP", null, null);
  private static final Entitlement jdField_byte = new Entitlement("Cash Con - Disbursement Request", null, null);
  private static final Entitlement a = new Entitlement("Cash Con - Deposit Entry", null, null);
  private EntitlementCachedAdapter jdField_try;
  private HashMap jdField_new;
  
  public EntitlementReportingAdapter(EntitlementCachedAdapter paramEntitlementCachedAdapter)
    throws EntitlementException
  {
    this.jdField_try = paramEntitlementCachedAdapter;
    a();
  }
  
  public IReportResult getReportData(EntitlementGroupMember paramEntitlementGroupMember, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws EntitlementException
  {
    String str1 = "EntitlementReportingAdapter.getReportData";
    ReportResult localReportResult = new ReportResult(paramReportCriteria.getLocale());
    Locale localLocale = localReportResult.getLocale();
    int i = 0;
    int j = Integer.parseInt(a(paramReportCriteria.getReportOptions(), "MAXDISPLAYSIZE", String.valueOf(2147483647)));
    int k = 0;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str2 = localProperties.getProperty("Group");
    String str3 = localProperties.getProperty("User");
    BusinessEmployees localBusinessEmployees = (BusinessEmployees)paramHashMap.get("BusinessEmployeesForReport");
    Object localObject1;
    if (str2.equals("AllGroups"))
    {
      paramReportCriteria.setDisplayValue("Group", ReportConsts.getText(10021, localLocale));
    }
    else
    {
      localObject1 = this.jdField_try.getEntitlementGroup(Integer.parseInt(str2));
      paramReportCriteria.setDisplayValue("Group", ((EntitlementGroup)localObject1).getGroupName());
    }
    if (str3.equals("AllUsers"))
    {
      paramReportCriteria.setDisplayValue("User", ReportConsts.getText(10017, localLocale));
    }
    else
    {
      localObject1 = localBusinessEmployees.getByID(str3);
      paramReportCriteria.setDisplayValue("User", ((BusinessEmployee)localObject1).getName());
    }
    try
    {
      localReportResult.init(paramReportCriteria);
      localObject1 = new BusinessEmployees(paramReportCriteria.getLocale());
      Iterator localIterator = localBusinessEmployees.iterator();
      while (localIterator.hasNext())
      {
        BusinessEmployee localBusinessEmployee1 = (BusinessEmployee)localIterator.next();
        if ((str2.equals("AllGroups")) || (localBusinessEmployee1.getEntitlementGroupId() == Integer.parseInt(str2))) {
          if ((str3.equals("AllUsers")) || (str3.equals(localBusinessEmployee1.getId()))) {
            ((BusinessEmployees)localObject1).add(localBusinessEmployee1);
          }
        }
      }
      ((BusinessEmployees)localObject1).setSortedBy("LAST");
      int m = 0;
      if (((BusinessEmployees)localObject1).size() > 0)
      {
        localObject2 = (BusinessEmployee)((BusinessEmployees)localObject1).get(0);
        m = a(((BusinessEmployee)localObject2).getEntitlementGroupId());
      }
      Object localObject2 = new HashMap();
      for (int n = 0; n < ((BusinessEmployees)localObject1).size(); n++)
      {
        BusinessEmployee localBusinessEmployee2 = (BusinessEmployee)((BusinessEmployees)localObject1).get(n);
        i = a(localBusinessEmployee2, localReportResult, i, j, m, (HashMap)localObject2, paramHashMap);
        if (i == j) {
          break;
        }
      }
    }
    catch (Exception localException)
    {
      k = 1;
      localReportResult.setError(localException);
      throw new EntitlementException(localException, str1, 11);
    }
    finally
    {
      if (i == j)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(i));
        localReportResult.setProperties(localHashMap);
      }
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException)
      {
        if (k == 0) {
          throw new EntitlementException(localRptException, str1, 11);
        }
      }
    }
    return localReportResult;
  }
  
  private int a(BusinessEmployee paramBusinessEmployee, ReportResult paramReportResult, int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap1, HashMap paramHashMap2)
    throws RptException, EntitlementException
  {
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    Object[] arrayOfObject = { paramBusinessEmployee.getName() };
    localReportHeading.setLabel(MessageFormat.format(ReportConsts.getText(10001, localLocale), arrayOfObject));
    localReportResult.setHeading(localReportHeading);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(4);
    localReportDataDimensions.setNumRows(1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportRow localReportRow = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportColumn localReportColumn1 = new ReportColumn(localLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(1930, localLocale));
    localReportColumn1.setLabels(localArrayList1);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn1);
    ReportDataItem localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(paramBusinessEmployee.getName());
    ReportColumn localReportColumn2 = new ReportColumn(localLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(1931, localLocale));
    localReportColumn2.setLabels(localArrayList2);
    localReportColumn2.setDataType("java.lang.String");
    localReportColumn2.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn2);
    localReportDataItem = localReportDataItems.add();
    EntitlementGroup localEntitlementGroup = this.jdField_try.getEntitlementGroup(paramBusinessEmployee.getEntitlementGroupId());
    String str = localEntitlementGroup.getGroupName();
    if (str == null) {
      localReportDataItem.setData("");
    } else {
      localReportDataItem.setData(str);
    }
    ReportColumn localReportColumn3 = new ReportColumn(localLocale);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(1932, localLocale));
    localReportColumn3.setLabels(localArrayList3);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn3);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(paramBusinessEmployee.getUserName());
    ReportColumn localReportColumn4 = new ReportColumn(localLocale);
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add(ReportConsts.getColumnName(1933, localLocale));
    localReportColumn4.setLabels(localArrayList4);
    localReportColumn4.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn4.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn4);
    localReportDataItem = localReportDataItems.add();
    DateTime localDateTime1 = (DateTime)paramBusinessEmployee.get("LAST_ACTIVE_DATE");
    DateTime localDateTime2 = (DateTime)paramBusinessEmployee.get("CREATE_DATE");
    if ((localDateTime1 != null) && (!localDateTime1.equals(localDateTime2))) {
      localReportDataItem.setData(localDateTime1);
    }
    if (localReportDataItem.getData() == null)
    {
      localReportDataItem.setData(ReportConsts.getText(10013, localLocale));
      localReportColumn4.setDataType("java.lang.String");
    }
    localReportResult.addRow(localReportRow);
    paramInt1++;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = this.jdField_try.getEntitlementTypesWithProperties("category", "per user");
    paramInt1 = a(paramReportResult, paramBusinessEmployee, localEntitlementTypePropertyLists, paramInt1, paramInt2, paramHashMap2);
    paramInt1 = a(paramReportResult, paramBusinessEmployee, localEntitlementTypePropertyLists, paramInt1, paramInt2, paramInt3, paramHashMap1, paramHashMap2);
    return paramInt1;
  }
  
  private int a(ReportResult paramReportResult, BusinessEmployee paramBusinessEmployee, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws RptException, EntitlementException
  {
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    Locale localLocale = paramReportResult.getLocale();
    EntitlementGroupMember localEntitlementGroupMember = paramBusinessEmployee.getEntitlementGroupMember();
    EntitlementGroups localEntitlementGroups = null;
    localEntitlementGroups = this.jdField_try.getGroupsAdministeredBy(localEntitlementGroupMember);
    if ((localEntitlementGroups != null) && (localEntitlementGroups.size() > 0))
    {
      ReportResult localReportResult = new ReportResult(localLocale);
      paramReportResult.addSubReport(localReportResult);
      ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
      localReportDataDimensions.setNumColumns(1);
      localReportDataDimensions.setNumRows(-1);
      localReportResult.setDataDimensions(localReportDataDimensions);
      ReportColumn localReportColumn = new ReportColumn(localLocale);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(ReportConsts.getColumnName(1934, localLocale));
      localReportColumn.setLabels(localArrayList);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(100);
      localReportResult.addColumn(localReportColumn);
      localEntitlementGroups.setSortedBy("NAME");
      int i = 0;
      for (int j = 0; j < localEntitlementGroups.size(); j++)
      {
        ReportRow localReportRow = new ReportRow(localLocale);
        if (j % 2 == 1) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
        localReportRow.setDataItems(localReportDataItems);
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localEntitlementGroups.get(j);
        String str = localEntitlementGroup.getGroupName() + " (" + ReportConsts.getEntGroupType(localEntitlementGroup.getEntGroupType(), localLocale) + ")";
        ReportDataItem localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(str);
        localReportResult.addRow(localReportRow);
        paramInt1++;
        i++;
        if (paramInt1 == paramInt2)
        {
          localReportDataDimensions.setNumRows(i);
          return paramInt2;
        }
      }
    }
    return paramInt1;
  }
  
  private int a(ReportResult paramReportResult, BusinessEmployee paramBusinessEmployee, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, int paramInt1, int paramInt2, int paramInt3, HashMap paramHashMap1, HashMap paramHashMap2)
    throws RptException, EntitlementException
  {
    if (paramInt1 == paramInt2) {
      return paramInt2;
    }
    Locale localLocale = paramReportResult.getLocale();
    String str1 = ReportConsts.getText(10007, localLocale);
    String str2 = ReportConsts.getText(10008, localLocale);
    String str3 = ReportConsts.getText(10009, localLocale);
    String str4 = ReportConsts.getText(10012, localLocale);
    String str5 = ReportConsts.getText(10010, localLocale);
    String str6 = ReportConsts.getText(10011, localLocale);
    String str7 = ReportConsts.getText(10415, localLocale);
    String str8 = ReportConsts.getText(2308, localLocale);
    String str9 = ReportConsts.getText(2309, localLocale);
    EntitlementGroupMember localEntitlementGroupMember = paramBusinessEmployee.getEntitlementGroupMember();
    HashMap localHashMap1 = (HashMap)paramHashMap2.get("OpToObjTypeMapForReport" + localEntitlementGroupMember.getId());
    HashMap localHashMap2 = (HashMap)paramHashMap2.get("ObjTypeToIdsMapForReport" + localEntitlementGroupMember.getId());
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements = this.jdField_try.getPositiveEntitlements(localEntitlementGroupMember, localHashMap1, localHashMap2);
    EntitlementTypePropertyList localEntitlementTypePropertyList1 = null;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = null;
    try
    {
      localEntitlementTypePropertyLists = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    catch (CSILException localCSILException) {}
    for (int i = 0; i < paramEntitlementTypePropertyLists.size(); i++)
    {
      localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)paramEntitlementTypePropertyLists.get(i);
      localObject1 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList1, "display name", localLocale);
      if (localObject1 == null)
      {
        localEntitlementTypePropertyList1.setCurrentProperty("display name");
        localObject1 = localEntitlementTypePropertyList1.getValue();
      }
      localEntitlementTypePropertyList1.setCurrentProperty("display parent");
      localObject2 = localEntitlementTypePropertyList1.getValue();
      if ((localEntitlementTypePropertyList1.isPropertySet("category", "per ACH company")) || (localEntitlementTypePropertyList1.isPropertySet("category", "cross ACH company")))
      {
        localEntitlementTypePropertyList1.setCurrentProperty("module_name");
        localObject3 = localEntitlementTypePropertyList1.getValue();
        if ((((String)localObject2).equals("")) && (((String)localObject3).equals("ACH")))
        {
          localEntitlementTypePropertyList1.clearProperty("display name");
          localEntitlementTypePropertyList1.addProperty("display name", str7 + " - " + (String)localObject1);
        }
        else if (!((String)localObject2).equals(""))
        {
          localObject4 = localEntitlementTypePropertyLists.getByOperationName((String)localObject2);
          localObject5 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue((TypePropertyList)localObject4, "display name", localLocale);
          if (localObject5 == null)
          {
            ((EntitlementTypePropertyList)localObject4).setCurrentProperty("display name");
            localObject5 = ((EntitlementTypePropertyList)localObject4).getValue();
          }
          com.ffusion.csil.core.EntitlementsUtil.setPropertyValue(localEntitlementTypePropertyList1, "display name", (String)localObject5 + " - " + (String)localObject1, localLocale);
        }
      }
    }
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    Object localObject1 = new ReportDataDimensions(localLocale);
    ((ReportDataDimensions)localObject1).setNumColumns(7);
    ((ReportDataDimensions)localObject1).setNumRows(-1);
    localReportResult.setDataDimensions((ReportDataDimensions)localObject1);
    if ((localEntitlements == null) || (localEntitlements.size() == 0))
    {
      ((ReportDataDimensions)localObject1).setNumColumns(1);
      ((ReportDataDimensions)localObject1).setNumRows(1);
      localObject2 = new ReportColumn(localLocale);
      localObject3 = new ArrayList();
      ((ArrayList)localObject3).add("");
      ((ReportColumn)localObject2).setLabels((ArrayList)localObject3);
      ((ReportColumn)localObject2).setDataType("java.lang.String");
      ((ReportColumn)localObject2).setWidthAsPercent(100);
      localReportResult.addColumn((ReportColumn)localObject2);
      localObject4 = new ReportRow(localLocale);
      localObject5 = new ReportDataItems(localLocale);
      ((ReportRow)localObject4).setDataItems((ReportDataItems)localObject5);
      localObject6 = ((ReportDataItems)localObject5).add();
      ((ReportDataItem)localObject6).setData(ReportConsts.getText(10006, localLocale));
      localReportResult.addRow((ReportRow)localObject4);
      paramInt1++;
      return paramInt1;
    }
    Object localObject2 = new ReportColumn(localLocale);
    Object localObject3 = new ArrayList();
    ((ArrayList)localObject3).add(ReportConsts.getColumnName(1920, localLocale));
    ((ReportColumn)localObject2).setLabels((ArrayList)localObject3);
    ((ReportColumn)localObject2).setDataType("java.lang.String");
    ((ReportColumn)localObject2).setWidthAsPercent(30);
    localReportResult.addColumn((ReportColumn)localObject2);
    Object localObject4 = new ReportColumn(localLocale);
    Object localObject5 = new ArrayList();
    ((ArrayList)localObject5).add(ReportConsts.getColumnName(1924, localLocale));
    ((ReportColumn)localObject4).setLabels((ArrayList)localObject5);
    ((ReportColumn)localObject4).setDataType("java.lang.String");
    ((ReportColumn)localObject4).setWidthAsPercent(15);
    localReportResult.addColumn((ReportColumn)localObject4);
    Object localObject6 = new ReportColumn(localLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(1925, localLocale));
    ((ReportColumn)localObject6).setLabels(localArrayList1);
    ((ReportColumn)localObject6).setDataType("java.lang.String");
    ((ReportColumn)localObject6).setWidthAsPercent(20);
    localReportResult.addColumn((ReportColumn)localObject6);
    ReportColumn localReportColumn1 = new ReportColumn(localLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(1921, localLocale));
    localReportColumn1.setLabels(localArrayList2);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn1);
    ReportColumn localReportColumn2 = new ReportColumn(localLocale);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(1922, localLocale));
    localReportColumn2.setLabels(localArrayList3);
    localReportColumn2.setDataType("java.lang.String");
    localReportColumn2.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn2);
    ReportColumn localReportColumn3 = new ReportColumn(localLocale);
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add(ReportConsts.getColumnName(1926, localLocale));
    localReportColumn3.setLabels(localArrayList4);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(10);
    localReportResult.addColumn(localReportColumn3);
    ReportColumn localReportColumn4 = new ReportColumn(localLocale);
    ArrayList localArrayList5 = new ArrayList();
    localArrayList5.add(ReportConsts.getColumnName(1923, localLocale));
    localReportColumn4.setLabels(localArrayList5);
    localReportColumn4.setDataType("java.lang.String");
    localReportColumn4.setWidthAsPercent(15);
    localReportResult.addColumn(localReportColumn4);
    Object[] arrayOfObject = localEntitlements.toArray();
    ReportEntitlementComparator localReportEntitlementComparator = new ReportEntitlementComparator(paramEntitlementTypePropertyLists, localLocale);
    Arrays.sort(arrayOfObject, localReportEntitlementComparator);
    int j = 0;
    Object localObject7 = "-";
    Object localObject8 = "-";
    Object localObject9 = "-";
    Object localObject10 = "-";
    Limits localLimits1 = this.jdField_try.getCompressedLimits(localEntitlementGroupMember);
    int k = 0;
    for (int m = 0; m < arrayOfObject.length; m++)
    {
      Entitlement localEntitlement1 = (Entitlement)arrayOfObject[m];
      Entitlement localEntitlement2 = null;
      Entitlement localEntitlement3 = null;
      String str10 = localEntitlement1.getOperationName();
      if (str10 != null)
      {
        try
        {
          if ((!a(str10, paramInt3, paramHashMap1)) || (!a(str10, localEntitlementGroupMember))) {
            continue;
          }
        }
        catch (Exception localException) {}
        String str11 = localEntitlement1.getObjectId();
        String str12 = localEntitlement1.getObjectType();
        str11 = str11 == null ? "" : str11;
        str12 = str12 == null ? "" : str12;
        String str14 = str10;
        String str15 = str12;
        String str16 = str11;
        EntitlementTypePropertyList localEntitlementTypePropertyList2 = paramEntitlementTypePropertyLists.getByOperationName(str10);
        int n = 0;
        int i1 = 0;
        if (localEntitlementTypePropertyList2 != null)
        {
          str14 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localEntitlementTypePropertyList2, "display name", localLocale);
          if (localEntitlementTypePropertyList2.isPropertySet("credit", "yes"))
          {
            n = 1;
            localEntitlement2 = new Entitlement(str10 + " (Credit)", str12, str11);
          }
          if (localEntitlementTypePropertyList2.isPropertySet("debit", "yes"))
          {
            i1 = 1;
            localEntitlement3 = new Entitlement(str10 + " (Debit)", str12, str11);
          }
        }
        ObjectTypePropertyList localObjectTypePropertyList = null;
        localObjectTypePropertyList = this.jdField_try.getObjectTypeWithProperties(str12);
        if (localObjectTypePropertyList != null) {
          str15 = com.ffusion.csil.core.EntitlementsUtil.getPropertyValue(localObjectTypePropertyList, "display name", localLocale);
        }
        HashMap localHashMap3 = (HashMap)localHashMap2.get(str12 + "DisplayValues");
        if (localHashMap3 != null) {
          str16 = (String)localHashMap3.get(str11);
        }
        Iterator localIterator = localLimits1.iterator();
        Limits localLimits2 = new Limits();
        Limits localLimits3 = new Limits();
        Limits localLimits4 = new Limits();
        Object localObject13;
        while (localIterator.hasNext())
        {
          Limit localLimit = (Limit)localIterator.next();
          int i3;
          int i4;
          int i5;
          if (a(localLimit, localEntitlement1))
          {
            if (localLimits2.size() == 0)
            {
              localLimits2.add(localLimit);
              continue;
            }
            i3 = 0;
            for (i4 = 0; i4 < localLimits2.size(); i4++)
            {
              localObject13 = (Limit)localLimits2.get(i4);
              i5 = a(localLimit.getPeriod(), ((Limit)localObject13).getPeriod());
              if (i5 == -1)
              {
                localLimits2.add(i4, localLimit);
                i3 = 1;
                break;
              }
              if (i5 == 0)
              {
                if (((((Limit)localObject13).isAllowedApproval()) && (localLimit.isAllowedApproval())) || ((!((Limit)localObject13).isAllowedApproval()) && (!localLimit.isAllowedApproval())))
                {
                  if (localLimit.getAmount().compareTo(((Limit)localObject13).getAmount()) == -1) {
                    localLimits2.set(i4, localLimit);
                  }
                  i3 = 1;
                  break;
                }
                if ((!((Limit)localObject13).isAllowedApproval()) && (localLimit.isAllowedApproval()))
                {
                  localLimits2.add(i4, localLimit);
                  i3 = 1;
                  break;
                }
                if (i4 == localLimits2.size())
                {
                  localLimits2.add(localLimit);
                  i3 = 1;
                  break;
                }
              }
            }
            if (i3 == 0) {
              localLimits2.add(localLimit);
            }
          }
          if ((n != 0) && (a(localLimit, localEntitlement2)))
          {
            if (localLimits3.size() == 0)
            {
              localLimits3.add(localLimit);
              continue;
            }
            i3 = 0;
            for (i4 = 0; i4 < localLimits3.size(); i4++)
            {
              localObject13 = (Limit)localLimits3.get(i4);
              i5 = a(localLimit.getPeriod(), ((Limit)localObject13).getPeriod());
              if (i5 == -1)
              {
                localLimits3.add(i4, localLimit);
                i3 = 1;
                break;
              }
              if (i5 == 0)
              {
                if (((((Limit)localObject13).isAllowedApproval()) && (localLimit.isAllowedApproval())) || ((!((Limit)localObject13).isAllowedApproval()) && (!localLimit.isAllowedApproval())))
                {
                  if (localLimit.getAmount().compareTo(((Limit)localObject13).getAmount()) == -1) {
                    localLimits3.set(i4, localLimit);
                  }
                  i3 = 1;
                  break;
                }
                if ((!((Limit)localObject13).isAllowedApproval()) && (localLimit.isAllowedApproval()))
                {
                  localLimits3.add(i4, localLimit);
                  i3 = 1;
                  break;
                }
                if (i4 == localLimits3.size())
                {
                  localLimits3.add(localLimit);
                  i3 = 1;
                  break;
                }
              }
            }
            if (i3 == 0) {
              localLimits3.add(localLimit);
            }
          }
          if ((i1 != 0) && (a(localLimit, localEntitlement3))) {
            if (localLimits4.size() == 0)
            {
              localLimits4.add(localLimit);
            }
            else
            {
              i3 = 0;
              for (i4 = 0; i4 < localLimits4.size(); i4++)
              {
                localObject13 = (Limit)localLimits4.get(i4);
                i5 = a(localLimit.getPeriod(), ((Limit)localObject13).getPeriod());
                if (i5 == -1)
                {
                  localLimits4.add(i4, localLimit);
                  i3 = 1;
                  break;
                }
                if (i5 == 0)
                {
                  if (((((Limit)localObject13).isAllowedApproval()) && (localLimit.isAllowedApproval())) || ((!((Limit)localObject13).isAllowedApproval()) && (!localLimit.isAllowedApproval())))
                  {
                    if (localLimit.getAmount().compareTo(((Limit)localObject13).getAmount()) == -1) {
                      localLimits4.set(i4, localLimit);
                    }
                    i3 = 1;
                    break;
                  }
                  if ((!((Limit)localObject13).isAllowedApproval()) && (localLimit.isAllowedApproval()))
                  {
                    localLimits4.add(i4, localLimit);
                    i3 = 1;
                    break;
                  }
                  if (i4 == localLimits4.size())
                  {
                    localLimits4.add(localLimit);
                    i3 = 1;
                    break;
                  }
                }
              }
              if (i3 == 0) {
                localLimits4.add(localLimit);
              }
            }
          }
        }
        int i2 = 0;
        if (((String)localObject7).equals(str10))
        {
          str14 = "";
        }
        else
        {
          localObject8 = "-";
          localObject9 = "-";
          k++;
          i2 = 1;
        }
        if (((String)localObject8).equals(str12))
        {
          str15 = "";
        }
        else
        {
          localObject9 = "-";
          if (i2 == 0)
          {
            k++;
            i2 = 1;
          }
        }
        if (((String)localObject9).equals(str11))
        {
          str16 = "";
        }
        else if (i2 == 0)
        {
          k++;
          i2 = 1;
        }
        localLimits2.addAll(localLimits3);
        localLimits2.addAll(localLimits4);
        Object localObject11;
        Object localObject12;
        if (localLimits2.size() == 0)
        {
          localObject11 = new ReportRow(localLocale);
          if (k % 2 == 0) {
            ((ReportRow)localObject11).set("CELLBACKGROUND", "reportDataShaded");
          }
          localObject12 = new ReportDataItems(localLocale);
          ((ReportRow)localObject11).setDataItems((ReportDataItems)localObject12);
          localObject13 = ((ReportDataItems)localObject12).add();
          ((ReportDataItem)localObject13).setData((!((String)localObject7).equals(str10)) && (str10.equals("")) ? str1 : str14);
          localObject13 = ((ReportDataItems)localObject12).add();
          ((ReportDataItem)localObject13).setData((!((String)localObject8).equals(str12)) && (str12.equals("")) ? str2 : str15);
          localObject13 = ((ReportDataItems)localObject12).add();
          ((ReportDataItem)localObject13).setData((!((String)localObject9).equals(str11)) && (str11.equals("")) ? str3 : str16);
          localObject13 = ((ReportDataItems)localObject12).add();
          ((ReportDataItem)localObject13).setData("");
          localObject13 = ((ReportDataItems)localObject12).add();
          ((ReportDataItem)localObject13).setData(str4);
          localObject13 = ((ReportDataItems)localObject12).add();
          ((ReportDataItem)localObject13).setData("");
          localObject13 = ((ReportDataItems)localObject12).add();
          ((ReportDataItem)localObject13).setData("");
          localObject7 = str10;
          localObject8 = str12;
          localObject9 = str11;
          if (paramInt1 == paramInt2)
          {
            ((ReportDataDimensions)localObject1).setNumRows(j);
            return paramInt2;
          }
          localReportResult.addRow((ReportRow)localObject11);
          paramInt1++;
          j++;
        }
        if (localLimits2.size() != 0)
        {
          localIterator = localLimits2.iterator();
          localObject10 = "-";
          while (localIterator.hasNext())
          {
            localObject11 = (Limit)localIterator.next();
            if (((String)localObject7).equals(str10))
            {
              str14 = "";
            }
            else
            {
              localObject8 = "-";
              localObject9 = "-";
            }
            if (((String)localObject8).equals(str12)) {
              str15 = "";
            } else {
              localObject9 = "-";
            }
            if (((String)localObject9).equals(str11)) {
              str16 = "";
            }
            localObject12 = new ReportRow(localLocale);
            if (k % 2 == 0) {
              ((ReportRow)localObject12).set("CELLBACKGROUND", "reportDataShaded");
            }
            localObject13 = new ReportDataItems(localLocale);
            ((ReportRow)localObject12).setDataItems((ReportDataItems)localObject13);
            ReportDataItem localReportDataItem = ((ReportDataItems)localObject13).add();
            localReportDataItem.setData((!((String)localObject7).equals(str10)) && (str10.equals("")) ? str1 : str14);
            localReportDataItem = ((ReportDataItems)localObject13).add();
            localReportDataItem.setData((!((String)localObject8).equals(str12)) && (str12.equals("")) ? str2 : str15);
            localReportDataItem = ((ReportDataItems)localObject13).add();
            localReportDataItem.setData((!((String)localObject9).equals(str11)) && (str11.equals("")) ? str3 : str16);
            localReportDataItem = ((ReportDataItems)localObject13).add();
            String str13 = a((Limit)localObject11, localLocale);
            localReportDataItem.setData(((String)localObject10).equals(str13) ? "" : str13);
            localReportDataItem = ((ReportDataItems)localObject13).add();
            String str17 = ((Limit)localObject11).getData() + " " + ((Limit)localObject11).getCurrencyCode();
            if (localLimits3.contains(localObject11)) {
              str17 = str17 + " " + ReportConsts.getText(10110, localLocale);
            }
            if (localLimits4.contains(localObject11)) {
              str17 = str17 + " " + ReportConsts.getText(10111, localLocale);
            }
            localReportDataItem.setData(str17);
            localReportDataItem = ((ReportDataItems)localObject13).add();
            if (((Limit)localObject11).isCrossCurrency()) {
              localReportDataItem.setData(str8);
            } else {
              localReportDataItem.setData(str9);
            }
            localReportDataItem = ((ReportDataItems)localObject13).add();
            localReportDataItem.setData(((Limit)localObject11).isAllowedApproval() ? str5 : str6);
            if (paramInt1 == paramInt2)
            {
              ((ReportDataDimensions)localObject1).setNumRows(j);
              return paramInt2;
            }
            localObject7 = str10;
            localObject8 = str12;
            localObject9 = str11;
            localObject10 = str13;
            localReportResult.addRow((ReportRow)localObject12);
            paramInt1++;
            j++;
          }
        }
      }
    }
    ((ReportDataDimensions)localObject1).setNumRows(j);
    return paramInt1;
  }
  
  private static String a(Properties paramProperties, String paramString1, String paramString2)
  {
    String str = paramProperties.getProperty(paramString1);
    if ((str == null) || (str.equals(""))) {
      str = paramString2;
    } else {
      str = str.trim();
    }
    return str;
  }
  
  private static String a(Limit paramLimit, Locale paramLocale)
  {
    int i = paramLimit == null ? 0 : paramLimit.getPeriod();
    switch (i)
    {
    case 0: 
      return ReportConsts.getText(10100, paramLocale);
    case 1: 
      return ReportConsts.getText(10101, paramLocale);
    case 2: 
      return ReportConsts.getText(10102, paramLocale);
    case 3: 
      return ReportConsts.getText(10103, paramLocale);
    case 4: 
      return ReportConsts.getText(10104, paramLocale);
    }
    return ReportConsts.getText(10100, paramLocale);
  }
  
  private boolean a(Limit paramLimit, Entitlement paramEntitlement)
    throws EntitlementException
  {
    boolean bool = true;
    if ((paramLimit.getLimitName() != null) && (!paramLimit.getLimitName().equalsIgnoreCase(paramEntitlement.getOperationName()))) {
      return false;
    }
    if ((paramLimit.getObjectType() != null) && (!paramLimit.getObjectType().equalsIgnoreCase(paramEntitlement.getObjectType()))) {
      return false;
    }
    if ((paramLimit.getObjectId() != null) && (!paramLimit.getObjectId().equalsIgnoreCase(paramEntitlement.getObjectId()))) {
      return false;
    }
    return bool;
  }
  
  private int a(int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2) {
      return 0;
    }
    switch (paramInt1)
    {
    case 1: 
      return -1;
    case 2: 
      if (paramInt2 == 1) {
        return 1;
      }
      return -1;
    case 3: 
      if (paramInt2 == 4) {
        return -1;
      }
      return 1;
    }
    return 1;
  }
  
  private void a()
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
    this.jdField_new = new HashMap();
    this.jdField_new.put("Manage ACH Participants", localMultiEntitlement5);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("ACHBatch", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("ARC", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CCD", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CCD + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CCD + DED", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CCD + TXP", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CIE", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CIE + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CTX", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("CTX + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("XCK", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("WEB", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("WEB + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("PPD", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("PPD + Addenda", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("POP", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("RCK", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put(com.ffusion.util.entitlements.EntitlementsUtil.getACHEntitlementName("TEL", "ACH Payment Approval"), localMultiEntitlement1);
    this.jdField_new.put("Wire Domestic Templated Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire Domestic Freeform Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire International Templated Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire International Freeform Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire Host Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire Drawdown Templated Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire Drawdown Freeform Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire FED Templated Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire FED Freeform Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire Book Templated Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire Book Freeform Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire Approval", localMultiEntitlement2);
    this.jdField_new.put("Wire Templates Approval", localMultiEntitlement1);
    this.jdField_new.put("Cash Con - Disbursement Request View Other", localMultiEntitlement3);
    this.jdField_new.put("Cash Con - Disbursement Request Delete Other", localMultiEntitlement3);
    this.jdField_new.put("Cash Con - Disbursement Request Edit Other", localMultiEntitlement3);
    this.jdField_new.put("Cash Con - Deposit Entry View Other", localMultiEntitlement4);
    this.jdField_new.put("Cash Con - Deposit Entry Delete Other", localMultiEntitlement4);
    this.jdField_new.put("Cash Con - Deposit Entry Edit Other", localMultiEntitlement4);
  }
  
  private int a(int paramInt)
    throws Exception
  {
    for (EntitlementGroup localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(paramInt); !localEntitlementGroup.getEntGroupType().equals("ServicesPackage"); localEntitlementGroup = com.ffusion.csil.core.Entitlements.getEntitlementGroup(localEntitlementGroup.getParentId())) {}
    return localEntitlementGroup.getGroupId();
  }
  
  private boolean a(String paramString, int paramInt, HashMap paramHashMap)
    throws Exception
  {
    boolean bool;
    if (paramHashMap.containsKey(paramString))
    {
      bool = Boolean.TRUE.equals(paramHashMap.get(paramString));
    }
    else
    {
      Object localObject1;
      if (this.jdField_new.containsKey(paramString))
      {
        localObject1 = new com.ffusion.csil.beans.entitlements.Entitlements();
        Entitlement localEntitlement = new Entitlement(paramString, null, null);
        a(localEntitlement, (com.ffusion.csil.beans.entitlements.Entitlements)localObject1);
        ArrayList localArrayList = new ArrayList();
        Object localObject2;
        for (int i = 0; i < ((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).size(); i++)
        {
          localObject2 = (Entitlement)((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).get(i);
          localArrayList.add(((Entitlement)localObject2).getOperationName());
        }
        MultiEntitlement localMultiEntitlement = (MultiEntitlement)this.jdField_new.get(paramString);
        if (((com.ffusion.csil.beans.entitlements.Entitlements)localObject1).size() > 0)
        {
          localObject2 = localMultiEntitlement.getOperations();
          for (int j = 0; j < localObject2.length; j++) {
            localArrayList.add(localObject2[j]);
          }
          localMultiEntitlement.setOperations((String[])localArrayList.toArray(new String[localArrayList.size()]));
        }
        bool = com.ffusion.csil.core.Entitlements.checkEntitlement(paramInt, localMultiEntitlement) == null;
      }
      else if (paramString.equals("Cash Con - Reporting"))
      {
        bool = (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, a) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_byte) == null);
      }
      else if (paramString.equals(jdField_do))
      {
        bool = (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_char) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_for) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_if) == null);
      }
      else if (paramString.equals(jdField_int))
      {
        bool = (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_case) == null) && ((com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_char) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_for) == null) || (com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, jdField_if) == null));
      }
      else
      {
        localObject1 = new Entitlement(paramString, null, null);
        bool = com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramInt, (Entitlement)localObject1) == null;
      }
      if (bool) {
        paramHashMap.put(paramString, Boolean.TRUE);
      } else {
        paramHashMap.put(paramString, Boolean.FALSE);
      }
    }
    return bool;
  }
  
  private boolean a(String paramString, EntitlementGroupMember paramEntitlementGroupMember)
    throws Exception
  {
    boolean bool = true;
    if ((paramString.equals("Cash Con - Deposit Entry View Other")) || (paramString.equals("Cash Con - Deposit Entry Delete Other")) || (paramString.equals("Cash Con - Deposit Entry Edit Other"))) {
      bool = com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramEntitlementGroupMember, a) == null;
    } else if ((paramString.equals("Cash Con - Disbursement Request View Other")) || (paramString.equals("Cash Con - Disbursement Request Delete Other")) || (paramString.equals("Cash Con - Disbursement Request Edit Other"))) {
      bool = com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramEntitlementGroupMember, jdField_byte) == null;
    } else {
      bool = com.ffusion.csil.core.EntitlementsUtil.checkEntitlementAndParents(paramEntitlementGroupMember, new Entitlement(paramString, null, null)) == null;
    }
    return bool;
  }
  
  private static void a(Entitlement paramEntitlement, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements)
    throws CSILException
  {
    if (paramEntitlements.indexOf(paramEntitlement) == -1)
    {
      paramEntitlements.add(paramEntitlement);
      EntitlementTypePropertyList localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(paramEntitlement.getOperationName());
      if (localEntitlementTypePropertyList == null) {
        return;
      }
      int i = localEntitlementTypePropertyList.numPropertyValues("control parent");
      for (int j = 0; j < i; j++)
      {
        String str = localEntitlementTypePropertyList.getPropertyValue("control parent", j);
        Entitlement localEntitlement = new Entitlement();
        localEntitlement.setOperationName(str);
        a(localEntitlement, paramEntitlements);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlementsReporting.EntitlementReportingAdapter
 * JD-Core Version:    0.7.0.1
 */