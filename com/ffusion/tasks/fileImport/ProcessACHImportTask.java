package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHClassCode;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.fileimporter.ErrorMessage;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessACHImportTask
  extends BaseTask
  implements ACHClassCode
{
  protected boolean clearACHEntries = false;
  protected boolean partialImport = false;
  protected boolean showImportedItems = true;
  protected int defaultUpdateRecordsBy = 1;
  protected int defaultMatchRecordsBy = -1;
  private static String PG = "Counts";
  private static String PF = "UpdateBy";
  private static String PI = "MatchBy";
  private static String PL = "Name";
  private static String PJ = "SourceEntries";
  private static String PE = "SourceEntryCount";
  private static String PH = "SourceEntryCountNotImported";
  private static String PK = "DidClearEntries";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str1 = super.getSuccessURL();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap1 = (HashMap)localHttpSession.getAttribute("UploadResults");
    if (localHashMap1 == null)
    {
      this.error = 23002;
      return super.getTaskErrorURL();
    }
    ACHEntries localACHEntries1 = null;
    ACHBatch localACHBatch = (ACHBatch)localHttpSession.getAttribute("AddEditACHBatch");
    if (localACHBatch == null)
    {
      this.error = 16002;
      return super.getTaskErrorURL();
    }
    Integer localInteger = (Integer)localHashMap1.get("AchClassCode");
    if (localInteger == null)
    {
      this.error = 23004;
      return super.getTaskErrorURL();
    }
    int i = localInteger.intValue();
    ACHEntries localACHEntries2 = (ACHEntries)localHashMap1.get("ACHEntries");
    if (localACHEntries2 == null)
    {
      this.error = 16015;
      return super.getTaskErrorURL();
    }
    String str2 = (String)localHashMap1.get("AchClassCodeText");
    if (str2 == null)
    {
      this.error = 23005;
      return super.getTaskErrorURL();
    }
    String str3 = (String)localHashMap1.get("AchServiceClassCodeText");
    localACHEntries1 = localACHBatch.getACHEntries();
    MappingDefinition localMappingDefinition = (MappingDefinition)localHashMap1.get("MappingDefinition");
    String str4 = null;
    int j;
    int k;
    if (localMappingDefinition != null)
    {
      j = localMappingDefinition.getMatchRecordsBy();
      k = localMappingDefinition.getUpdateRecordsBy();
      str4 = localMappingDefinition.getName();
      if ((localMappingDefinition.getDescription() != null) && (localMappingDefinition.getDescription().length() > 0)) {
        str4 = str4 + " (" + localMappingDefinition.getDescription() + ")";
      }
      if (k == 1) {
        j = -1;
      }
    }
    else
    {
      k = this.defaultUpdateRecordsBy;
      j = this.defaultMatchRecordsBy;
      if (this.partialImport)
      {
        j = 4;
        k = 3;
      }
    }
    if ((i != localACHBatch.getStandardEntryClassCodeValue()) && ((this.clearACHEntries) || (localACHEntries1.size() == 0))) {
      try
      {
        if (!ACH.checkACHSECEntitlement(localSecureUser, str2, localACHBatch.getCompanyID(), "ACH Payment Entry", false, new HashMap()))
        {
          this.error = 23015;
          return super.getTaskErrorURL();
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = 23015;
        return super.getTaskErrorURL();
      }
    }
    int m = 0;
    if ((this.clearACHEntries) && (k != 3))
    {
      localACHEntries1.clear();
      this.clearACHEntries = false;
      m = 1;
    }
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap2 = new HashMap();
    ArrayList localArrayList2 = new ArrayList();
    localHashMap1.put(PJ, localArrayList1);
    localHashMap1.put(PG, localHashMap2);
    localHashMap1.put(PF, new Integer(k));
    localHashMap1.put(PI, new Integer(j));
    if (str4 != null) {
      localHashMap1.put(PL, str4);
    }
    localHashMap2.put(PE, "" + localACHEntries2.size());
    if (m != 0) {
      localHashMap2.put(PK, "true");
    }
    String str5 = "";
    if ((i == 1) || (i == 10) || (i == 18) || (i == 5) || (i == 17) || (i == 9)) {
      str5 = "1";
    } else if (i == 2) {
      str5 = "0";
    } else if ("220".equals(str3)) {
      str5 = "0";
    } else if ("225".equals(str3)) {
      str5 = "1";
    }
    if ((i != localACHBatch.getStandardEntryClassCodeValue()) && (localACHEntries1.size() == 0))
    {
      localACHBatch.setStandardEntryClassCode(str2);
      localACHBatch.setDebitBatch(str5);
    }
    else
    {
      str5 = localACHBatch.getDebitBatch();
    }
    Iterator localIterator = localACHEntries1.iterator();
    ACHEntry localACHEntry1;
    while (localIterator.hasNext())
    {
      localACHEntry1 = (ACHEntry)localIterator.next();
      localACHEntry1.setAction("");
    }
    localIterator = localACHEntries2.iterator();
    Object localObject2;
    while (localIterator.hasNext())
    {
      localACHEntry1 = (ACHEntry)localIterator.next();
      localObject1 = localACHEntry1.getAchPayee();
      if (localObject1 == null)
      {
        this.error = 16152;
        return super.getTaskErrorURL();
      }
      if (("0".equals(str5)) && (localACHEntry1.isAmountIsDebit()))
      {
        localACHEntry1.addValidationError(getImportError("NotImported", localACHEntry1), getImportError("DebitInCreditOnly", localACHEntry1));
        localArrayList1.add(localACHEntry1);
      }
      else if (("1".equals(str5)) && (!localACHEntry1.isAmountIsDebit()))
      {
        localACHEntry1.addValidationError(getImportError("NotImported", localACHEntry1), getImportError("CreditInDebitOnly", localACHEntry1));
        localArrayList1.add(localACHEntry1);
      }
      else
      {
        localObject2 = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        switch (j)
        {
        case -1: 
          break;
        case 1: 
          i3 = 1;
          break;
        case 0: 
          i2 = 1;
          break;
        case 2: 
          i2 = 1;
          i3 = 1;
          break;
        case 4: 
          i2 = 1;
          i3 = 1;
          i4 = 1;
          break;
        case 3: 
          i4 = 1;
        }
        if ((i2 != 0) || (i3 != 0) || (i4 != 0))
        {
          String str6 = "";
          if (i2 != 0) {
            if (i == 18) {
              str6 = str6 + (localACHEntry1.getProcessControlField() != null ? localACHEntry1.getProcessControlField().trim() : "") + (localACHEntry1.getItemResearchNumber() != null ? localACHEntry1.getItemResearchNumber().trim() : "");
            } else {
              str6 = str6 + ((ACHPayee)localObject1).getName().trim();
            }
          }
          if (i3 != 0) {
            if (i == 5)
            {
              str6 = str6 + (localACHEntry1.getCheckSerialNumber() != null ? localACHEntry1.getCheckSerialNumber().trim() : "") + (localACHEntry1.getTerminalCity() != null ? localACHEntry1.getTerminalCity().trim() : "") + (localACHEntry1.getTerminalState() != null ? localACHEntry1.getTerminalState().trim() : "");
            }
            else if ((i == 1) || (i == 17) || (i == 18))
            {
              str6 = str6 + (localACHEntry1.getCheckSerialNumber() != null ? localACHEntry1.getCheckSerialNumber().trim() : "");
            }
            else if ((i == 12) || (i == 13))
            {
              localObject3 = localACHEntry1.getIdentificationNumber();
              if ((localObject3 == null) || (((String)localObject3).length() == 0)) {
                localObject3 = ((ACHPayee)localObject1).getUserAccountNumber();
              }
              str6 = str6 + (localObject3 != null ? ((String)localObject3).trim() : "");
            }
            else
            {
              str6 = str6 + ((ACHPayee)localObject1).getUserAccountNumber();
            }
          }
          if (i4 != 0) {
            str6 = str6 + ((ACHPayee)localObject1).getRoutingNumber() + "-" + ((ACHPayee)localObject1).getAccountNumberNoSpaces() + "-" + ((ACHPayee)localObject1).getAccountTypeValue();
          }
          Object localObject3 = localACHEntries1.iterator();
          while (((Iterator)localObject3).hasNext())
          {
            ACHEntry localACHEntry2 = (ACHEntry)((Iterator)localObject3).next();
            ACHPayee localACHPayee = localACHEntry2.getAchPayee();
            String str7 = "";
            if (i2 != 0) {
              if (i == 18) {
                str7 = str7 + (localACHEntry2.getProcessControlField() != null ? localACHEntry2.getProcessControlField().trim() : "") + (localACHEntry2.getItemResearchNumber() != null ? localACHEntry2.getItemResearchNumber().trim() : "");
              } else {
                str7 = str7 + localACHPayee.getName().trim();
              }
            }
            if (i3 != 0) {
              if (i == 5)
              {
                str7 = str7 + (localACHEntry2.getCheckSerialNumber() != null ? localACHEntry2.getCheckSerialNumber().trim() : "") + (localACHEntry2.getTerminalCity() != null ? localACHEntry2.getTerminalCity().trim() : "") + (localACHEntry2.getTerminalState() != null ? localACHEntry2.getTerminalState().trim() : "");
              }
              else if ((i == 1) || (i == 17) || (i == 18))
              {
                str7 = str7 + (localACHEntry2.getCheckSerialNumber() != null ? localACHEntry2.getCheckSerialNumber().trim() : "");
              }
              else if ((i == 12) || (i == 13))
              {
                String str8 = localACHEntry2.getIdentificationNumber();
                if ((str8 == null) || (str8.length() == 0)) {
                  str8 = localACHPayee.getUserAccountNumber();
                }
                str7 = str7 + (str8 != null ? str8.trim() : "");
              }
              else
              {
                str7 = str7 + (localACHPayee.getUserAccountNumber() != null ? localACHPayee.getUserAccountNumber().trim() : "");
              }
            }
            if (i4 != 0) {
              str7 = str7 + localACHPayee.getRoutingNumber() + "-" + localACHPayee.getAccountNumberNoSpaces() + "-" + localACHPayee.getAccountTypeValue();
            }
            if (str7.equals(str6))
            {
              localObject2 = localACHEntry2;
              break;
            }
          }
        }
        if ((localObject2 != null) && ("MATCHED".equals(((ACHEntry)localObject2).getAction())))
        {
          localACHEntry1.addValidationError(getImportError("NotImported", localACHEntry1), getImportError("MultipleMatches", localACHEntry1));
          localArrayList1.add(localACHEntry1);
        }
        else
        {
          switch (k)
          {
          case 1: 
            localACHEntry1.setAction("ADDED");
            localArrayList2.add(localACHEntry1);
            break;
          case 0: 
            if (localObject2 != null) {
              ((ACHEntry)localObject2).merge(localACHEntry1);
            }
            break;
          case 2: 
            if (localObject2 != null)
            {
              ((ACHEntry)localObject2).merge(localACHEntry1);
            }
            else
            {
              localACHEntry1.setAction("ADDED");
              localArrayList2.add(localACHEntry1);
            }
            break;
          case 3: 
            if (localObject2 != null) {
              ((ACHEntry)localObject2).setAmount(localACHEntry1.getAmount());
            }
          default: 
            if (localObject2 == null)
            {
              localACHEntry1.addValidationError(getImportError("NotImported", localACHEntry1), getImportError("NoMatch", localACHEntry1));
              localArrayList1.add(localACHEntry1);
            }
            else
            {
              ((ACHEntry)localObject2).setAction("MATCHED");
            }
            break;
          }
        }
      }
    }
    int n = 0;
    int i1 = 0;
    Object localObject1 = localACHEntries1.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (ACHEntry)((Iterator)localObject1).next();
      if ("MATCHED".equals(((ACHEntry)localObject2).getAction()))
      {
        n++;
      }
      else
      {
        ((ACHEntry)localObject2).setAction("UNMATCHED");
        i1++;
      }
    }
    localACHEntries1.addAll(localArrayList2);
    localHashMap2.put("ADDED", "" + localArrayList2.size());
    localHashMap2.put("MATCHED", "" + n);
    localHashMap2.put("UNMATCHED", "" + i1);
    localHashMap2.put(PH, "" + localArrayList1.size());
    this.showImportedItems = true;
    return str1;
  }
  
  public boolean isClearACHEntries()
  {
    return this.clearACHEntries;
  }
  
  public boolean getClearACHEntries()
  {
    return this.clearACHEntries;
  }
  
  public void setClearACHEntries(boolean paramBoolean)
  {
    this.clearACHEntries = paramBoolean;
  }
  
  public void setClearACHEntries(String paramString)
  {
    this.clearACHEntries = new Boolean(paramString).booleanValue();
  }
  
  public int getDefaultUpdateRecordsBy()
  {
    return this.defaultUpdateRecordsBy;
  }
  
  public void setDefaultUpdateRecordsBy(int paramInt)
  {
    this.defaultUpdateRecordsBy = paramInt;
  }
  
  public void setDefaultUpdateRecordsBy(String paramString)
  {
    try
    {
      this.defaultUpdateRecordsBy = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.defaultUpdateRecordsBy = 2;
    }
  }
  
  public int getDefaultMatchRecordsBy()
  {
    return this.defaultMatchRecordsBy;
  }
  
  public void setDefaultMatchRecordsBy(int paramInt)
  {
    this.defaultMatchRecordsBy = paramInt;
  }
  
  public void setDefaultMatchRecordsBy(String paramString)
  {
    try
    {
      this.defaultMatchRecordsBy = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.defaultMatchRecordsBy = -1;
    }
  }
  
  public void setPartialImport(String paramString)
  {
    this.partialImport = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getPartialImport()
  {
    return "" + this.partialImport;
  }
  
  public String getShowImportedItems()
  {
    return "" + this.showImportedItems;
  }
  
  public void setShowImportedItems(String paramString)
  {
    this.showImportedItems = new Boolean(paramString).booleanValue();
  }
  
  public static IReportResult generateReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    Locale localLocale = paramSecureUser.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    ArrayList localArrayList = (ArrayList)paramHashMap.get(PJ);
    HashMap localHashMap = (HashMap)paramHashMap.get(PG);
    localReportResult.init(paramReportCriteria);
    Object localObject1 = ReportConsts.getText(2368, localLocale);
    Object localObject2 = new ReportResult(localLocale);
    localReportResult.addSubReport((ReportResult)localObject2);
    Object localObject3 = new ReportHeading(localLocale);
    ((ReportHeading)localObject3).setLabel((String)localObject1);
    ((ReportResult)localObject2).setHeading((ReportHeading)localObject3);
    Object localObject4 = new ReportDataDimensions(localLocale);
    ((ReportDataDimensions)localObject4).setNumColumns(3);
    ((ReportDataDimensions)localObject4).setNumRows(-1);
    ((ReportResult)localObject2).setDataDimensions((ReportDataDimensions)localObject4);
    Object localObject5 = new ReportColumn(localLocale);
    Object localObject6 = new ArrayList();
    ((ArrayList)localObject6).add(ReportConsts.getColumnName(2357, localLocale));
    ((ReportColumn)localObject5).setLabels((ArrayList)localObject6);
    ((ReportColumn)localObject5).setDataType("java.lang.String");
    ((ReportColumn)localObject5).setWidthAsPercent(33);
    ((ReportResult)localObject2).addColumn((ReportColumn)localObject5);
    Object localObject7 = new ReportColumn(localLocale);
    Object localObject8 = new ArrayList();
    ((ArrayList)localObject8).add(ReportConsts.getColumnName(2358, localLocale));
    ((ReportColumn)localObject7).setLabels((ArrayList)localObject8);
    ((ReportColumn)localObject7).setDataType("java.lang.String");
    ((ReportColumn)localObject7).setWidthAsPercent(33);
    ((ReportResult)localObject2).addColumn((ReportColumn)localObject7);
    Object localObject9 = new ReportColumn(localLocale);
    Object localObject10 = new ArrayList();
    ((ArrayList)localObject10).add(ReportConsts.getColumnName(2356, localLocale));
    ((ReportColumn)localObject9).setLabels((ArrayList)localObject10);
    ((ReportColumn)localObject9).setDataType("java.lang.String");
    ((ReportColumn)localObject9).setWidthAsPercent(34);
    ((ReportResult)localObject2).addColumn((ReportColumn)localObject9);
    Object localObject11 = new ReportRow(localLocale);
    Object localObject12 = new ReportDataItems(localLocale);
    ((ReportRow)localObject11).setDataItems((ReportDataItems)localObject12);
    Object localObject13 = ((ReportDataItems)localObject12).add();
    Object localObject14 = (String)paramHashMap.get(PL);
    if (localObject14 == null) {
      localObject14 = ReportConsts.getText(2369, localLocale);
    }
    jdMethod_for((ReportDataItem)localObject13, localObject14);
    Object localObject15 = ((ReportDataItems)localObject12).add();
    Object localObject16 = null;
    switch (((Integer)paramHashMap.get(PF)).intValue())
    {
    case 1: 
      localObject16 = ReportConsts.getText(2380, localLocale);
      break;
    case 0: 
      localObject16 = ReportConsts.getText(2382, localLocale);
      break;
    case 2: 
      localObject16 = ReportConsts.getText(2381, localLocale);
      break;
    case 3: 
      localObject16 = ReportConsts.getText(2383, localLocale);
    }
    jdMethod_for((ReportDataItem)localObject15, localObject16);
    Object localObject17 = ((ReportDataItems)localObject12).add();
    String str = null;
    switch (((Integer)paramHashMap.get(PI)).intValue())
    {
    case -1: 
      str = ReportConsts.getText(2370, localLocale);
      break;
    case 1: 
      str = ReportConsts.getText(2372, localLocale);
      break;
    case 0: 
      str = ReportConsts.getText(2371, localLocale);
      break;
    case 2: 
      str = ReportConsts.getText(2373, localLocale);
      break;
    case 4: 
      str = ReportConsts.getText(2376, localLocale);
      break;
    case 3: 
      str = ReportConsts.getText(2374, localLocale);
    }
    jdMethod_for((ReportDataItem)localObject17, str);
    ((ReportResult)localObject2).addRow((ReportRow)localObject11);
    localObject1 = new ReportResult(localLocale);
    localReportResult.addSubReport((ReportResult)localObject1);
    localObject2 = new ReportHeading(localLocale);
    ((ReportHeading)localObject2).setLabel(ReportConsts.getText(2365, localLocale));
    ((ReportResult)localObject1).setHeading((ReportHeading)localObject2);
    localObject3 = new ReportDataDimensions(localLocale);
    ((ReportDataDimensions)localObject3).setNumColumns(3);
    ((ReportDataDimensions)localObject3).setNumRows(-1);
    ((ReportResult)localObject1).setDataDimensions((ReportDataDimensions)localObject3);
    localObject4 = new ReportColumn(localLocale);
    localObject5 = new ArrayList();
    ((ArrayList)localObject5).add(ReportConsts.getColumnName(2359, localLocale));
    ((ReportColumn)localObject4).setLabels((ArrayList)localObject5);
    ((ReportColumn)localObject4).setDataType("java.lang.String");
    ((ReportColumn)localObject4).setWidthAsPercent(50);
    ((ReportResult)localObject1).addColumn((ReportColumn)localObject4);
    localObject6 = new ReportColumn(localLocale);
    localObject7 = new ArrayList();
    ((ArrayList)localObject7).add(ReportConsts.getColumnName(2355, localLocale));
    ((ReportColumn)localObject6).setLabels((ArrayList)localObject7);
    ((ReportColumn)localObject6).setDataType("java.lang.String");
    ((ReportColumn)localObject6).setWidthAsPercent(50);
    ((ReportResult)localObject1).addColumn((ReportColumn)localObject6);
    localObject8 = new ReportColumn(localLocale);
    localObject9 = new ArrayList();
    ((ArrayList)localObject9).add(ReportConsts.getColumnName(2363, localLocale));
    ((ReportColumn)localObject8).setLabels((ArrayList)localObject9);
    ((ReportColumn)localObject8).setDataType("java.lang.String");
    ((ReportColumn)localObject8).setWidthAsPercent(50);
    ((ReportResult)localObject1).addColumn((ReportColumn)localObject8);
    localObject10 = new ReportRow(localLocale);
    localObject11 = new ReportDataItems(localLocale);
    ((ReportRow)localObject10).setDataItems((ReportDataItems)localObject11);
    localObject12 = ((ReportDataItems)localObject11).add();
    localObject13 = (String)paramHashMap.get("FILE_NAME");
    jdMethod_for((ReportDataItem)localObject12, localObject13);
    localObject14 = ((ReportDataItems)localObject11).add();
    localObject15 = (String)localHashMap.get(PE);
    jdMethod_for((ReportDataItem)localObject14, localObject15);
    localObject16 = ((ReportDataItems)localObject11).add();
    localObject17 = (String)localHashMap.get(PH);
    jdMethod_for((ReportDataItem)localObject16, localObject17);
    ((ReportResult)localObject1).addRow((ReportRow)localObject10);
    if ((localArrayList != null) && (localArrayList.size() > 0))
    {
      localObject1 = new ReportResult(localLocale);
      localReportResult.addSubReport((ReportResult)localObject1);
      localObject2 = new ReportHeading(localLocale);
      ((ReportHeading)localObject2).setLabel(ReportConsts.getText(2360, localLocale));
      ((ReportResult)localObject1).setSectionHeading((ReportHeading)localObject2);
      localObject3 = new ReportDataDimensions(localLocale);
      ((ReportDataDimensions)localObject3).setNumColumns(2);
      ((ReportDataDimensions)localObject3).setNumRows(-1);
      ((ReportResult)localObject1).setDataDimensions((ReportDataDimensions)localObject3);
      localObject4 = new ReportColumn(localLocale);
      localObject5 = new ArrayList();
      ((ArrayList)localObject5).add(ReportConsts.getColumnName(2350, localLocale));
      ((ReportColumn)localObject4).setLabels((ArrayList)localObject5);
      ((ReportColumn)localObject4).setDataType("java.lang.String");
      ((ReportColumn)localObject4).setWidthAsPercent(50);
      ((ReportResult)localObject1).addColumn((ReportColumn)localObject4);
      localObject6 = new ReportColumn(localLocale);
      localObject7 = new ArrayList();
      ((ArrayList)localObject7).add(ReportConsts.getColumnName(2351, localLocale));
      ((ReportColumn)localObject6).setLabels((ArrayList)localObject7);
      ((ReportColumn)localObject6).setDataType("java.lang.String");
      ((ReportColumn)localObject6).setWidthAsPercent(50);
      ((ReportResult)localObject1).addColumn((ReportColumn)localObject6);
      localObject8 = localArrayList.iterator();
      while (((Iterator)localObject8).hasNext())
      {
        localObject9 = (ACHEntry)((Iterator)localObject8).next();
        localObject10 = new ReportRow(localLocale);
        localObject11 = new ReportDataItems(localLocale);
        ((ReportRow)localObject10).setDataItems((ReportDataItems)localObject11);
        localObject12 = ((ReportDataItems)localObject11).add();
        localObject13 = ((ACHEntry)localObject9).getLineNumber() + " : " + ((ACHEntry)localObject9).getLineContent();
        jdMethod_for((ReportDataItem)localObject12, localObject13);
        localObject14 = ((ReportDataItems)localObject11).add();
        localObject15 = (ErrorMessage)((ACHEntry)localObject9).getValidationErrors().get(0);
        localObject16 = ((ErrorMessage)localObject15).getTitle() + " : " + ((ErrorMessage)localObject15).getMessage();
        jdMethod_for((ReportDataItem)localObject14, localObject16);
        ((ReportResult)localObject1).addRow((ReportRow)localObject10);
      }
    }
    int i = 0;
    localObject2 = new ReportResult(localLocale);
    localReportResult.addSubReport((ReportResult)localObject2);
    localObject3 = new ReportHeading(localLocale);
    ((ReportHeading)localObject3).setLabel(ReportConsts.getText(2366, localLocale));
    ((ReportResult)localObject2).setHeading((ReportHeading)localObject3);
    if (localHashMap.get(PK) != null)
    {
      localObject4 = new ReportHeading(localLocale);
      ((ReportHeading)localObject4).setLabel(ReportConsts.getText(2367, localLocale));
      ((ReportResult)localObject2).setSectionHeading((ReportHeading)localObject4);
    }
    localObject4 = new ReportDataDimensions(localLocale);
    ((ReportDataDimensions)localObject4).setNumColumns(3);
    ((ReportDataDimensions)localObject4).setNumRows(-1);
    ((ReportResult)localObject2).setDataDimensions((ReportDataDimensions)localObject4);
    localObject5 = new ReportColumn(localLocale);
    localObject6 = new ArrayList();
    ((ArrayList)localObject6).add(ReportConsts.getColumnName(2352, localLocale));
    ((ReportColumn)localObject5).setLabels((ArrayList)localObject6);
    ((ReportColumn)localObject5).setDataType("java.lang.String");
    ((ReportColumn)localObject5).setWidthAsPercent(33);
    ((ReportResult)localObject2).addColumn((ReportColumn)localObject5);
    localObject7 = new ReportColumn(localLocale);
    localObject8 = new ArrayList();
    ((ArrayList)localObject8).add(ReportConsts.getColumnName(2353, localLocale));
    ((ReportColumn)localObject7).setLabels((ArrayList)localObject8);
    ((ReportColumn)localObject7).setDataType("java.lang.String");
    ((ReportColumn)localObject7).setWidthAsPercent(33);
    ((ReportResult)localObject2).addColumn((ReportColumn)localObject7);
    localObject9 = new ReportColumn(localLocale);
    localObject10 = new ArrayList();
    ((ArrayList)localObject10).add(ReportConsts.getColumnName(2354, localLocale));
    ((ReportColumn)localObject9).setLabels((ArrayList)localObject10);
    ((ReportColumn)localObject9).setDataType("java.lang.String");
    ((ReportColumn)localObject9).setWidthAsPercent(34);
    ((ReportResult)localObject2).addColumn((ReportColumn)localObject9);
    localObject11 = new ReportRow(localLocale);
    localObject12 = new ReportDataItems(localLocale);
    ((ReportRow)localObject11).setDataItems((ReportDataItems)localObject12);
    localObject13 = ((ReportDataItems)localObject12).add();
    localObject14 = (String)localHashMap.get("ADDED");
    jdMethod_for((ReportDataItem)localObject13, localObject14);
    localObject15 = ((ReportDataItems)localObject12).add();
    localObject16 = (String)localHashMap.get("MATCHED");
    jdMethod_for((ReportDataItem)localObject15, localObject16);
    if ((Integer.parseInt((String)localObject14) > 0) || (Integer.parseInt((String)localObject16) > 0)) {
      i = 1;
    }
    localObject17 = ((ReportDataItems)localObject12).add();
    str = (String)localHashMap.get("UNMATCHED");
    jdMethod_for((ReportDataItem)localObject17, str);
    ((ReportResult)localObject2).addRow((ReportRow)localObject11);
    if (i != 0)
    {
      localObject2 = new ReportResult(localLocale);
      localReportResult.addSubReport((ReportResult)localObject2);
      localObject3 = new ReportHeading(localLocale);
      ((ReportHeading)localObject3).setLabel(ReportConsts.getText(2362, localLocale));
      ((ReportResult)localObject2).setHeading((ReportHeading)localObject3);
    }
    localReportResult.fini();
    return localReportResult;
  }
  
  private static void jdMethod_for(ReportDataItem paramReportDataItem, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItem.setData("");
    } else {
      paramReportDataItem.setData(paramObject);
    }
  }
  
  public String getImportError(String paramString, ACHEntry paramACHEntry)
  {
    return ResourceUtil.getString("ACHImportError_" + paramString, "com.ffusion.beans.ach.resources", paramACHEntry.getLocale());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.ProcessACHImportTask
 * JD-Core Version:    0.7.0.1
 */