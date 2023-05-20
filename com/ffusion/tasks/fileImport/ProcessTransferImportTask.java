package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.FilteredList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessTransferImportTask
  extends BaseTask
{
  protected boolean showImportedItems = true;
  protected int defaultUpdateRecordsBy = 1;
  protected int defaultMatchRecordsBy = -1;
  private static String PR = "Counts";
  private static String PQ = "UpdateBy";
  private static String PT = "MatchBy";
  private static String PW = "Name";
  private static String PU = "SourceEntries";
  private static String PP = "SourceEntryCount";
  private static String PS = "SourceEntryCountNotImported";
  private static String PV = "DidClearEntries";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    String str1 = super.getSuccessURL();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute("TransferAccounts");
    localAccounts.setFilter("All");
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap1 = (HashMap)localHttpSession.getAttribute("UploadResults");
    if (localHashMap1 == null)
    {
      this.error = 23002;
      return super.getTaskErrorURL();
    }
    TransferBatch localTransferBatch = (TransferBatch)localHttpSession.getAttribute("AddTransferBatch");
    if (localTransferBatch == null)
    {
      this.error = 1003;
      return super.getTaskErrorURL();
    }
    Transfers localTransfers1 = localTransferBatch.getTransfers();
    Transfers localTransfers2 = (Transfers)localHashMap1.get("ImportTransfers");
    if (localTransfers2 == null)
    {
      this.error = 1003;
      return super.getTaskErrorURL();
    }
    localTransfers2.setDateFormat(localTransferBatch.getDateFormat());
    MappingDefinition localMappingDefinition = (MappingDefinition)localHashMap1.get("MappingDefinition");
    String str2 = null;
    int i;
    int j;
    if (localMappingDefinition != null)
    {
      i = localMappingDefinition.getMatchRecordsBy();
      j = localMappingDefinition.getUpdateRecordsBy();
      str2 = localMappingDefinition.getName();
      if ((localMappingDefinition.getDescription() != null) && (localMappingDefinition.getDescription().length() > 0)) {
        str2 = str2 + " (" + localMappingDefinition.getDescription() + ")";
      }
      if (j == 1) {
        i = -1;
      }
    }
    else
    {
      j = this.defaultUpdateRecordsBy;
      i = this.defaultMatchRecordsBy;
    }
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap2 = new HashMap();
    ArrayList localArrayList2 = new ArrayList();
    localHashMap1.put(PU, localArrayList1);
    localHashMap1.put(PR, localHashMap2);
    localHashMap1.put(PQ, new Integer(j));
    localHashMap1.put(PT, new Integer(i));
    if (str2 != null) {
      localHashMap1.put(PW, str2);
    }
    localHashMap2.put(PP, "" + localTransfers2.size());
    Iterator localIterator1 = localTransfers2.iterator();
    while (localIterator1.hasNext())
    {
      Transfer localTransfer1 = (Transfer)localIterator1.next();
      Object localObject = null;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      switch (i)
      {
      case -1: 
        break;
      case 1: 
        i1 = 1;
        break;
      case 0: 
        n = 1;
        break;
      case 2: 
        n = 1;
        i1 = 1;
        break;
      case 4: 
        n = 1;
        i1 = 1;
        i2 = 1;
        break;
      case 3: 
        i2 = 1;
      }
      if ((n != 0) || (i1 != 0) || (i2 != 0))
      {
        String str3 = "";
        if ((n == 0) || ((i1 == 0) || (i2 != 0))) {
          str3 = localTransfer1.getFromAccountDisplayText() + localTransfer1.getToAccountDisplayText();
        }
        Iterator localIterator2 = localTransfers1.iterator();
        while (localIterator2.hasNext())
        {
          Transfer localTransfer2 = (Transfer)localIterator2.next();
          String str4 = "";
          if ((n == 0) || ((i1 == 0) || (i2 != 0)))
          {
            if (localTransfer2.getFromAccount() == null) {
              localTransfer2.setFromAccount(localAccounts.getByID(localTransfer2.getFromAccountID()));
            }
            if (localTransfer2.getToAccount() == null) {
              localTransfer2.setToAccount(localAccounts.getByID(localTransfer2.getToAccountID()));
            }
            str4 = localTransfer2.getFromAccountDisplayText() + localTransfer2.getToAccountDisplayText();
          }
          if (str4.equals(str3))
          {
            localObject = localTransfer2;
            break;
          }
        }
      }
      if ((localObject != null) && ("Matched".equals(localObject.getAction())))
      {
        localObject.resetValidationErrors();
        localObject.addValidationError(localObject.getImportError("MultipleMatches"), localObject.getImportError("DuplicateMatchesTransfer"));
        localArrayList1.add(localTransfer1);
      }
      else
      {
        switch (j)
        {
        case 1: 
          localTransfer1.setAction("Added");
          localArrayList2.add(localTransfer1);
          break;
        case 0: 
          if (localObject != null) {
            localObject.merge(localTransfer1);
          }
          break;
        case 2: 
          if (localObject != null)
          {
            localObject.merge(localTransfer1);
          }
          else
          {
            localTransfer1.setAction("Added");
            localArrayList2.add(localTransfer1);
          }
          break;
        case 3: 
          if (localObject != null) {
            localObject.setAmount(localTransfer1.getAmount());
          }
        default: 
          if (localObject == null)
          {
            localTransfer1.addValidationError("NotImported", "NoMatch");
            localArrayList1.add(localTransfer1);
          }
          else
          {
            localObject.setAction("Matched");
          }
          break;
        }
      }
    }
    int k = 0;
    int m = 0;
    localTransfers1.addAll(0, localArrayList2);
    return str1;
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
  
  public static IReportResult generateReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    Locale localLocale = paramSecureUser.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    localReportResult.fini();
    return localReportResult;
  }
  
  private static void jdMethod_int(ReportDataItem paramReportDataItem, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItem.setData("");
    } else {
      paramReportDataItem.setData(paramObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.ProcessTransferImportTask
 * JD-Core Version:    0.7.0.1
 */