package com.ffusion.tasks.ach;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.util.BeansConverter;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.util.GetPagedData;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.FilteredList;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.PagingContext;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetPagedACHBatchHistories
  extends GetPagedData
{
  protected boolean viewAll = false;
  protected String ACHType = "";
  protected String companyID;
  protected String status;
  protected HashMap compIDs;
  
  public GetPagedACHBatchHistories()
  {
    setDataSetName("PendingACHBatches");
    this.ACHType = "ACHBatch";
    this.status = "ACH_STATUS_PENDING";
    this.compIDs = new HashMap();
  }
  
  public void setFirstPage(String paramString)
  {
    setPage("first");
  }
  
  public void setNextPage(String paramString)
  {
    setPage("next");
  }
  
  public void setPreviousPage(String paramString)
  {
    setPage("previous");
  }
  
  public void setCollectionSessionName(String paramString)
  {
    setDataSetName(paramString);
  }
  
  public String getCollectionSessionName()
  {
    return getDataSetName();
  }
  
  public void setViewAll(String paramString)
  {
    this.viewAll = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setACHType(String paramString)
  {
    this.ACHType = paramString;
  }
  
  public String getACHType()
  {
    return this.ACHType;
  }
  
  public String getViewAll()
  {
    if (this.viewAll == true) {
      return "true";
    }
    return "false";
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString;
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  protected FilteredList getFirstPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("FIRST");
    if ((this.ACHType != null) && (this.ACHType.length() > 0)) {
      paramHashMap.put("ACH_TYPE", this.ACHType);
    }
    ACHCompanies localACHCompanies = (ACHCompanies)localHttpSession.getAttribute("ACHCOMPANIES");
    this.compIDs = getCompanySECCodes(localACHCompanies);
    paramHashMap.put("ACH_CompanyIDs", this.compIDs);
    if (this.viewAll == true) {
      paramHashMap.put("ACH_VIEW", "ALL");
    }
    paramHashMap.put("ACH_STATUS", this.status);
    ACHBatches localACHBatches = ACH.getPagedACHBatchHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localACHBatches.setDateFormat(getDateFormat());
    }
    return localACHBatches;
  }
  
  protected FilteredList getNextPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("NEXT");
    if ((this.ACHType != null) && (this.ACHType.length() > 0)) {
      paramHashMap.put("ACH_TYPE", this.ACHType);
    }
    ACHCompanies localACHCompanies = (ACHCompanies)localHttpSession.getAttribute("ACHCOMPANIES");
    this.compIDs = getCompanySECCodes(localACHCompanies);
    paramHashMap.put("ACH_CompanyIDs", this.compIDs);
    if (this.viewAll == true) {
      paramHashMap.put("ACH_VIEW", "ALL");
    }
    paramHashMap.put("ACH_STATUS", this.status);
    ACHBatches localACHBatches = ACH.getPagedACHBatchHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localACHBatches.setDateFormat(getDateFormat());
    }
    return localACHBatches;
  }
  
  protected FilteredList getPreviousPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null) {
      return null;
    }
    this._pagingContext.setDirection("PREVIOUS");
    if ((this.ACHType != null) && (this.ACHType.length() > 0)) {
      paramHashMap.put("ACH_TYPE", this.ACHType);
    }
    ACHCompanies localACHCompanies = (ACHCompanies)localHttpSession.getAttribute("ACHCOMPANIES");
    this.compIDs = getCompanySECCodes(localACHCompanies);
    paramHashMap.put("ACH_CompanyIDs", this.compIDs);
    if (this.viewAll == true) {
      paramHashMap.put("ACH_VIEW", "ALL");
    }
    paramHashMap.put("ACH_STATUS", this.status);
    ACHBatches localACHBatches = ACH.getPagedACHBatchHistories(localSecureUser, paramPagingContext, paramHashMap);
    if (getDateFormat() != null) {
      localACHBatches.setDateFormat(getDateFormat());
    }
    return localACHBatches;
  }
  
  protected FilteredList getLastPage(HttpServletRequest paramHttpServletRequest, PagingContext paramPagingContext, HashMap paramHashMap)
    throws Exception
  {
    this.error = 20112;
    return null;
  }
  
  protected HashMap getCompanySECCodes(ACHCompanies paramACHCompanies)
  {
    HashMap localHashMap = new HashMap();
    ACHCompany localACHCompany = null;
    if (paramACHCompanies == null) {
      return localHashMap;
    }
    String str = ResourceUtil.getString("ACHClassCodes", "com.ffusion.beans.ach.resources", Locale.US);
    Object localObject1;
    ArrayList localArrayList;
    Iterator localIterator;
    Object localObject2;
    if ((this.companyID != null) && (this.companyID.length() > 0))
    {
      localObject1 = new ArrayList();
      localACHCompany = paramACHCompanies.getByCompanyID(this.companyID);
      localArrayList = localACHCompany.getEntryClasses();
      localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (String)localIterator.next();
        if ((this.ACHType != null) && (this.ACHType.equals("ACHBatch")) ? (((String)localObject2).indexOf("TXP") <= 0) || (((String)localObject2).indexOf("DED") <= 0) : ((((String)localObject2).indexOf("TXP") != -1) || (((String)localObject2).indexOf("DED") != -1)) && ((((String)localObject2).indexOf("TXP") <= 0) || (this.ACHType.equals("TaxPayment"))) && ((((String)localObject2).indexOf("DED") <= 0) || (this.ACHType.equals("ChildSupportPayment"))))
        {
          if (((String)localObject2).length() >= 3) {
            localObject2 = ((String)localObject2).substring(0, 3);
          }
          if (str.indexOf((String)localObject2) != -1) {
            if (!((ArrayList)localObject1).contains(localObject2)) {
              ((ArrayList)localObject1).add(localObject2);
            }
          }
        }
      }
      localObject2 = (String[])((ArrayList)localObject1).toArray(new String[0]);
      localHashMap.put(this.companyID, localObject2);
    }
    else
    {
      localObject1 = paramACHCompanies.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localACHCompany = (ACHCompany)((Iterator)localObject1).next();
        localArrayList = localACHCompany.getEntryClasses();
        localIterator = localArrayList.iterator();
        localObject2 = new ArrayList();
        while (localIterator.hasNext())
        {
          localObject3 = (String)localIterator.next();
          if ((this.ACHType != null) && (this.ACHType.equals("ACHBatch")) ? (((String)localObject3).indexOf("TXP") <= 0) || (((String)localObject3).indexOf("DED") <= 0) : ((((String)localObject3).indexOf("TXP") != -1) || (((String)localObject3).indexOf("DED") != -1)) && ((((String)localObject3).indexOf("TXP") <= 0) || (this.ACHType.equals("TaxPayment"))) && ((((String)localObject3).indexOf("DED") <= 0) || (this.ACHType.equals("ChildSupportPayment"))))
          {
            if (((String)localObject3).length() >= 3) {
              localObject3 = ((String)localObject3).substring(0, 3);
            }
            if (str.indexOf((String)localObject3) != -1) {
              if (!((ArrayList)localObject2).contains(localObject3)) {
                ((ArrayList)localObject2).add(localObject3);
              }
            }
          }
        }
        Object localObject3 = (String[])((ArrayList)localObject2).toArray(new String[0]);
        if (((ArrayList)localObject2).size() > 0) {
          localHashMap.put(localACHCompany.getCompanyID(), localObject3);
        }
      }
    }
    return localHashMap;
  }
  
  protected Object getValueForSortCriterion(ReportSortCriterion paramReportSortCriterion, Object paramObject)
  {
    ACHBatch localACHBatch = (ACHBatch)paramObject;
    if (paramReportSortCriterion.getName().equals("DueDate"))
    {
      String str1 = "";
      DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
      if (localACHBatch.getDate() != null) {
        str1 = localDateFormat.format(localACHBatch.getDateValue().getTime());
      }
      return str1;
    }
    long l;
    if (paramReportSortCriterion.getName().equals("TotalCredit"))
    {
      l = localACHBatch.getTotalCreditAmountValue().getAmountValue().multiply(new BigDecimal(100.0D)).longValue();
      return "" + l;
    }
    if (paramReportSortCriterion.getName().equals("TotalDebit"))
    {
      l = localACHBatch.getTotalDebitAmountValue().getAmountValue().multiply(new BigDecimal(100.0D)).longValue();
      return "" + l;
    }
    if (paramReportSortCriterion.getName().equals("Status")) {
      return ACHBatch.mapStatusToBPWStatus(localACHBatch.getStatusValue());
    }
    if (paramReportSortCriterion.getName().equals("Frequency"))
    {
      if (localACHBatch.getFrequencyValue() == 0) {
        return "NONE";
      }
      String str2 = BeansConverter.getBPWFrequency(localACHBatch.getFrequencyValue());
      if (str2 == null) {
        str2 = "NONE";
      }
      return str2;
    }
    if (paramReportSortCriterion.getName().equals("NumberOfEntries")) {
      return localACHBatch.getNumberEntries();
    }
    if (paramReportSortCriterion.getName().equals("CompACHId")) {
      return localACHBatch.getCompanyID();
    }
    if (paramReportSortCriterion.getName().equals("BatchName")) {
      return localACHBatch.getName();
    }
    if (paramReportSortCriterion.getName().equals("TransactionIndex")) {
      return new Long(localACHBatch.getTransactionIndex());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetPagedACHBatchHistories
 * JD-Core Version:    0.7.0.1
 */