package com.ffusion.tasks.fileImport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.PaymentBatch;
import com.ffusion.beans.billpay.Payments;
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

public class ProcessPaymentImportTask
  extends BaseTask
{
  protected boolean showImportedItems = true;
  protected int defaultUpdateRecordsBy = 1;
  protected int defaultMatchRecordsBy = -1;
  private static String PZ = "Counts";
  private static String PY = "UpdateBy";
  private static String P1 = "MatchBy";
  private static String P4 = "Name";
  private static String P2 = "SourceEntries";
  private static String PX = "SourceEntryCount";
  private static String P0 = "SourceEntryCountNotImported";
  private static String P3 = "DidClearEntries";
  
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
    PaymentBatch localPaymentBatch = (PaymentBatch)localHttpSession.getAttribute("AddPaymentBatch");
    if (localPaymentBatch == null)
    {
      this.error = 2034;
      return super.getTaskErrorURL();
    }
    Payments localPayments1 = localPaymentBatch.getPayments();
    Payments localPayments2 = (Payments)localHashMap1.get("ImportPayments");
    if (localPayments2 == null)
    {
      this.error = 2003;
      return super.getTaskErrorURL();
    }
    localPayments2.setDateFormat(localPayments1.getDateFormat());
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
    localHashMap1.put(P2, localArrayList1);
    localHashMap1.put(PZ, localHashMap2);
    localHashMap1.put(PY, new Integer(j));
    localHashMap1.put(P1, new Integer(i));
    if (str2 != null) {
      localHashMap1.put(P4, str2);
    }
    localHashMap2.put(PX, "" + localPayments2.size());
    Iterator localIterator1 = localPayments2.iterator();
    Object localObject2;
    while (localIterator1.hasNext())
    {
      Payment localPayment1 = (Payment)localIterator1.next();
      localObject1 = localPayment1.getPayee();
      if (localObject1 == null)
      {
        this.error = 2013;
        return super.getTaskErrorURL();
      }
      localObject2 = null;
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
        if (n != 0) {
          str3 = str3 + ((Payee)localObject1).getName().trim().toUpperCase();
        }
        if ((i1 == 0) || (i2 != 0)) {}
        Iterator localIterator2 = localPayments1.iterator();
        while (localIterator2.hasNext())
        {
          Payment localPayment2 = (Payment)localIterator2.next();
          Payee localPayee = localPayment2.getPayee();
          String str4 = "";
          if (n != 0) {
            str4 = str4 + localPayee.getName().trim().toUpperCase();
          }
          if ((i1 == 0) || ((i2 == 0) || (str4.equals(str3))))
          {
            localObject2 = localPayment2;
            break;
          }
        }
      }
      if ((localObject2 != null) && ("Matched".equals(((Payment)localObject2).getAction())))
      {
        ((Payment)localObject2).resetValidationErrors();
        ((Payment)localObject2).addValidationError(((Payment)localObject2).getImportError("MultipleMatches"), ((Payment)localObject2).getImportError("MultipleMatchesPayee"));
        localArrayList1.add(localPayment1);
      }
      else
      {
        switch (j)
        {
        case 1: 
          localPayment1.setAction("Added");
          localArrayList2.add(localPayment1);
          break;
        case 0: 
          if (localObject2 != null) {
            ((Payment)localObject2).merge(localPayment1);
          }
          break;
        case 2: 
          if (localObject2 != null)
          {
            ((Payment)localObject2).merge(localPayment1);
          }
          else
          {
            localPayment1.setAction("Added");
            localArrayList2.add(localPayment1);
          }
          break;
        default: 
          if (localObject2 == null)
          {
            localPayment1.addValidationError(((Payment)localObject2).getImportError("NotImported"), ((Payment)localObject2).getImportError("NoMatch"));
            localArrayList1.add(localPayment1);
          }
          else
          {
            ((Payment)localObject2).setAction("Matched");
          }
          break;
        }
      }
    }
    int k = 0;
    int m = 0;
    Object localObject1 = localPayments1.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Payment)((Iterator)localObject1).next();
      if ("Matched".equals(((Payment)localObject2).getAction())) {
        k++;
      } else {
        m++;
      }
    }
    if ((k == 0) && (localArrayList2.size() == 0)) {}
    localPayments1.addAll(localArrayList2);
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
  
  private static void jdMethod_new(ReportDataItem paramReportDataItem, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItem.setData("");
    } else {
      paramReportDataItem.setData(paramObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.ProcessPaymentImportTask
 * JD-Core Version:    0.7.0.1
 */