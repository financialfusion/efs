package com.ffusion.services.demo;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHCompanySummaries;
import com.ffusion.beans.ach.ACHCompanySummary;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.beans.ach.ACHPayee;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxFormCache;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.PagingContext;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class ACH
  extends Service
  implements com.ffusion.services.ACH
{
  public static ACHBatches achBatches = null;
  public static ACHPayees achPayees = null;
  protected static TaxFormCache taxFormCache = null;
  
  public int initialize(String paramString)
  {
    int i = super.initialize("demoACH.xml");
    jdMethod_for();
    return i;
  }
  
  private void jdMethod_for()
  {
    achBatches = new ACHBatches(Locale.getDefault());
    achPayees = new ACHPayees();
    jdMethod_do();
    if (achBatches.size() > 0)
    {
      Calendar localCalendar = Calendar.getInstance();
      int i = localCalendar.get(1);
      int j = localCalendar.get(2);
      Iterator localIterator = achBatches.iterator();
      while (localIterator.hasNext())
      {
        ACHBatch localACHBatch = (ACHBatch)localIterator.next();
        DateTime localDateTime1 = localACHBatch.getDateValue();
        DateTime localDateTime2 = localACHBatch.getProcessedOnDateValue();
        if ((localDateTime1 != null) && (localDateTime2 != null))
        {
          localDateTime1.set(1, i);
          localDateTime1.set(2, j);
          localDateTime2.set(1, i);
          localDateTime2.set(2, j);
          if (localACHBatch.getStatusValue() == 5)
          {
            if (localDateTime1.after(localCalendar))
            {
              localDateTime1.add(2, -1);
              localDateTime2.add(2, -1);
            }
          }
          else if ((localACHBatch.getStatusValue() == 2) && (localDateTime1.before(localCalendar)))
          {
            localDateTime1.add(2, 1);
            localDateTime2.add(2, 1);
          }
        }
        localACHBatch.fixupEntriesAmount();
      }
    }
    taxFormCache = new TaxFormCache();
    taxFormCache.loadFromXMLFile("taxforms.xml");
  }
  
  public ACHBatch addACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    return paramACHBatch;
  }
  
  public ACHBatch modifyACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatch localACHBatch = achBatches.getByID(paramACHBatch1.getID());
    localACHBatch.set(paramACHBatch1);
    localACHBatch.fixupEntriesAmount();
    return paramACHBatch1;
  }
  
  public void deleteACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {}
  
  public ACHBatch addRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    return paramACHBatch;
  }
  
  public ACHBatch modifyRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch1, ACHBatch paramACHBatch2, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatch localACHBatch = achBatches.getByID(paramACHBatch1.getID());
    localACHBatch.set(paramACHBatch1);
    localACHBatch.fixupEntriesAmount();
    return paramACHBatch1;
  }
  
  public void deleteRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {}
  
  public ACHBatches getACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches = new ACHBatches(Locale.getDefault());
    Iterator localIterator = achBatches.iterator();
    while (localIterator.hasNext())
    {
      ACHBatch localACHBatch1 = (ACHBatch)localIterator.next();
      if ((localACHBatch1.getACHType().equals(paramString2)) && (localACHBatch1.getNumberPaymentsValue() <= 1) && (localACHBatch1.getCoID().equals(paramString1)))
      {
        ACHBatch localACHBatch2 = new ACHBatch();
        localACHBatch2.set(localACHBatch1);
        localACHBatch2.setACHEntries(new ACHEntries());
        localACHBatches.add(localACHBatch2);
      }
    }
    return localACHBatches;
  }
  
  public ACHBatches getRecACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches = new ACHBatches(Locale.getDefault());
    Iterator localIterator = achBatches.iterator();
    while (localIterator.hasNext())
    {
      ACHBatch localACHBatch1 = (ACHBatch)localIterator.next();
      if ((localACHBatch1.getNumberPaymentsValue() > 1) && (localACHBatch1.getCoID().equals(paramString1)))
      {
        ACHBatch localACHBatch2 = new ACHBatch();
        localACHBatch2.set(localACHBatch1);
        localACHBatch2.setACHEntries(new ACHEntries());
        localACHBatches.add(localACHBatch2);
      }
    }
    return localACHBatches;
  }
  
  public ACHBatch getACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatch localACHBatch = achBatches.getByID(paramACHBatch.getID());
    if (localACHBatch != null) {
      paramACHBatch.set(localACHBatch);
    }
    return paramACHBatch;
  }
  
  public ACHBatch getRecACHBatch(SecureUser paramSecureUser, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatch localACHBatch = achBatches.getByID(paramACHBatch.getID());
    if (localACHBatch != null) {
      paramACHBatch.set(localACHBatch);
    }
    return paramACHBatch;
  }
  
  public ACHBatches getPagedPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("FIRST");
    return a(paramSecureUser, paramString1, paramString2, paramPagingContext, 2, paramHashMap);
  }
  
  public ACHBatches getNextPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("NEXT");
    return a(paramSecureUser, paramString1, paramString2, paramPagingContext, 2, paramHashMap);
  }
  
  public ACHBatches getPreviousPendingACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(paramSecureUser, paramString1, paramString2, paramPagingContext, 2, paramHashMap);
  }
  
  public ACHBatches getPagedCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("FIRST");
    return a(paramSecureUser, paramString1, paramString2, paramPagingContext, 5, paramHashMap);
  }
  
  public ACHBatches getNextCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("NEXT");
    return a(paramSecureUser, paramString1, paramString2, paramPagingContext, 5, paramHashMap);
  }
  
  public ACHBatches getPreviousCompletedACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    paramPagingContext.setDirection("PREVIOUS");
    return a(paramSecureUser, paramString1, paramString2, paramPagingContext, 5, paramHashMap);
  }
  
  public ACHPayees getACHPayees(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    ACHPayees localACHPayees = new ACHPayees();
    Iterator localIterator = achPayees.iterator();
    while (localIterator.hasNext())
    {
      ACHPayee localACHPayee1 = (ACHPayee)localIterator.next();
      ACHPayee localACHPayee2 = new ACHPayee();
      localACHPayee2.set(localACHPayee1);
      localACHPayees.add(localACHPayee2);
    }
    return localACHPayees;
  }
  
  public ACHPayee addACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    paramACHPayee.setID(String.valueOf(Calendar.getInstance().getTime().getTime()));
    return paramACHPayee;
  }
  
  public void deleteACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {}
  
  public ACHPayee modifyACHPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    return paramACHPayee;
  }
  
  public ACHBatches getAllBatchesWithPayee(SecureUser paramSecureUser, ACHPayee paramACHPayee, HashMap paramHashMap)
    throws CSILException
  {
    String str = paramACHPayee.getID();
    ACHBatches localACHBatches = new ACHBatches(Locale.getDefault());
    Iterator localIterator1 = achBatches.iterator();
    while (localIterator1.hasNext())
    {
      ACHBatch localACHBatch = (ACHBatch)localIterator1.next();
      ACHEntries localACHEntries = localACHBatch.getACHEntries();
      Iterator localIterator2 = localACHEntries.iterator();
      int i = 0;
      while (localIterator2.hasNext())
      {
        ACHEntry localACHEntry = (ACHEntry)localIterator1.next();
        if (localACHEntry.getAchPayeeID().equals(str))
        {
          localACHBatches.add(localACHBatch);
          i = 1;
          break;
        }
      }
      if (i != 0) {
        break;
      }
    }
    return localACHBatches;
  }
  
  public void deletePayeeFromBatch(SecureUser paramSecureUser, ACHPayee paramACHPayee, ACHBatch paramACHBatch, HashMap paramHashMap)
    throws CSILException
  {}
  
  public void uploadACHFile(SecureUser paramSecureUser, StringBuffer paramStringBuffer, HashMap paramHashMap)
    throws CSILException
  {}
  
  public IReportResult getReportData(SecureUser paramSecureUser, String paramString, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getACHBatches", 19003);
  }
  
  public String[] getStatesWithTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return TaxFormCache.getStatesWithTaxForms();
  }
  
  public TaxForms getTaxForms(SecureUser paramSecureUser, int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return TaxFormCache.getTaxForms(paramInt, paramString);
  }
  
  public TaxForm getTaxForm(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    return TaxFormCache.getTaxForm(paramString);
  }
  
  public ACHCompany addACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.addACHCompany", 19003);
  }
  
  public ACHCompanies getACHCompanies(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    ACHCompanies localACHCompanies = new ACHCompanies(Locale.getDefault());
    localACHCompanies.create("1", "18", "123456789", "Payroll West");
    localACHCompanies.create("2", "18", "234567890", "Payroll East");
    localACHCompanies.create("3", "18", "234567890", "Corporate");
    return localACHCompanies;
  }
  
  public void modifyACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.modACHCompany", 19003);
  }
  
  public void deleteACHCompany(SecureUser paramSecureUser, ACHCompany paramACHCompany, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.deleteACHCompany", 19003);
  }
  
  public void addOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.addOffsetAccount", 19003);
  }
  
  public ACHOffsetAccounts getOffsetAccounts(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getOffsetAccounts", 19003);
  }
  
  public void modifyOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.modifyOffsetAccount", 19003);
  }
  
  public void deleteOffsetAccount(SecureUser paramSecureUser, ACHOffsetAccount paramACHOffsetAccount, ACHCompany paramACHCompany, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.deleteOffsetAccount", 19003);
  }
  
  public AffiliateBank addAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.addAffiliateBank", 19003);
  }
  
  public AffiliateBank modifyAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.modifyAffiliateBank", 19003);
  }
  
  public AffiliateBank deleteAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.deleteAffiliateBank", 19003);
  }
  
  public AffiliateBank getAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getAffiliateBank", 19003);
  }
  
  public AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.getAffiliateBanks", 19003);
  }
  
  public void addCustomer(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    throw new CSILException("ACH.addCustomer", 19003);
  }
  
  public ACHCompanySummaries getACHCompanySummaries(SecureUser paramSecureUser, String[] paramArrayOfString, HashMap paramHashMap)
    throws CSILException
  {
    ACHCompanySummaries localACHCompanySummaries = new ACHCompanySummaries(Locale.getDefault());
    ACHCompanySummary localACHCompanySummary = localACHCompanySummaries.create("123456789");
    localACHCompanySummary.setNumBatches(10L);
    localACHCompanySummary.setNumBatchEntries(1L);
    localACHCompanySummary.setTotalCredit(new Currency("1111.11", localACHCompanySummary.getLocale()));
    localACHCompanySummary.setTotalDebit(new Currency("2222.22", localACHCompanySummary.getLocale()));
    localACHCompanySummary.setGrandTotal(new Currency("11111.11", localACHCompanySummary.getLocale()));
    localACHCompanySummary = localACHCompanySummaries.create("345678901");
    localACHCompanySummary.setNumBatches(2L);
    localACHCompanySummary.setNumBatchEntries(20L);
    localACHCompanySummary.setTotalCredit(new Currency("2222.22", localACHCompanySummary.getLocale()));
    localACHCompanySummary.setTotalDebit(new Currency("4444.44", localACHCompanySummary.getLocale()));
    localACHCompanySummary.setGrandTotal(new Currency("2222.22", localACHCompanySummary.getLocale()));
    localACHCompanySummary = localACHCompanySummaries.create("234567890");
    localACHCompanySummary.setNumBatches(3L);
    localACHCompanySummary.setNumBatchEntries(35L);
    localACHCompanySummary.setTotalCredit(new Currency("3333.33", localACHCompanySummary.getLocale()));
    localACHCompanySummary.setTotalDebit(new Currency("6666.66", localACHCompanySummary.getLocale()));
    localACHCompanySummary.setGrandTotal(new Currency("3333.33", localACHCompanySummary.getLocale()));
    return localACHCompanySummaries;
  }
  
  private int a(HashMap paramHashMap)
  {
    int i = 10;
    if (paramHashMap != null)
    {
      String str = (String)paramHashMap.get("PAGESIZE");
      if (str != null) {
        try
        {
          i = Integer.parseInt(str);
        }
        catch (Exception localException) {}
      }
    }
    return i;
  }
  
  private ACHBatches a(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, int paramInt, HashMap paramHashMap)
  {
    long l = 0L;
    String str1 = "ID,REVERSE";
    if (paramPagingContext.getDirection().equalsIgnoreCase("NEXT"))
    {
      str1 = "ID";
      l = paramPagingContext.getFirstIndex();
    }
    else if (paramPagingContext.getDirection().equalsIgnoreCase("PREVIOUS"))
    {
      l = paramPagingContext.getLastIndex();
    }
    String str2 = String.valueOf(l);
    ACHBatches localACHBatches1 = new ACHBatches();
    ACHBatches localACHBatches2 = null;
    try
    {
      localACHBatches2 = getACHBatches(paramSecureUser, paramString1, paramString2, paramHashMap);
    }
    catch (Exception localException) {}
    if (localACHBatches2 != null)
    {
      localACHBatches2.setSortedBy(str1);
      int i = 0;
      int j = a(paramHashMap);
      int k = 0;
      if (l == 0L) {
        k = 1;
      }
      Object localObject;
      for (int m = 0; (m < localACHBatches2.size()) && (i < j); m++)
      {
        localObject = (ACHBatch)localACHBatches2.get(m);
        if ((k != 0) && (((ACHBatch)localObject).getStatusValue() == paramInt) && ((paramPagingContext.getStartDate() == null) || (((ACHBatch)localObject).getDateValue().after(paramPagingContext.getStartDate()))) && ((paramPagingContext.getEndDate() == null) || (((ACHBatch)localObject).getDateValue().before(paramPagingContext.getEndDate()))))
        {
          localACHBatches1.add(localObject);
          i++;
        }
        else if ((k == 0) && (((ACHBatch)localObject).getID().equals(str2)))
        {
          k = 1;
        }
      }
      paramPagingContext.setFirstPage(false);
      paramPagingContext.setLastPage(false);
      if (localACHBatches1.size() < j) {
        paramPagingContext.setLastPage(true);
      }
      HashMap localHashMap;
      if (paramPagingContext.getDirection().equalsIgnoreCase("FIRST"))
      {
        paramPagingContext.setFirstPage(true);
        if (localACHBatches1.size() > 0)
        {
          localHashMap = new HashMap();
          localHashMap.put("FIRSTPAGEINDEX", ((ACHBatch)localACHBatches1.get(0)).getID());
          paramPagingContext.setMap(localHashMap);
        }
      }
      else
      {
        localHashMap = paramPagingContext.getMap();
        if ((localHashMap != null) && (localACHBatches1.size() > 0))
        {
          localObject = (String)localHashMap.get("FIRSTPAGEINDEX");
          if (localACHBatches1.getByID((String)localObject) != null) {
            paramPagingContext.setFirstPage(true);
          }
        }
      }
      if (localACHBatches1.size() == 0)
      {
        paramPagingContext.setFirstIndex(0L);
        paramPagingContext.setLastIndex(0L);
      }
      else
      {
        if (str1.equals("ID")) {
          localACHBatches1.setSortedBy("ID,REVERSE");
        }
        paramPagingContext.setFirstIndex(Integer.parseInt(((ACHBatch)localACHBatches1.get(0)).getID()));
        paramPagingContext.setLastIndex(Integer.parseInt(((ACHBatch)localACHBatches1.get(localACHBatches1.size() - 1)).getID()));
      }
      localACHBatches1.setPagingContext(paramPagingContext);
    }
    return localACHBatches1;
  }
  
  private void jdMethod_do()
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), getXMLReader(this, this.m_URL));
    }
    catch (Throwable localThrowable) {}
  }
  
  protected class a
    extends Service.InternalXMLHandler
  {
    public a()
    {
      super();
    }
    
    public void startElement(String paramString)
    {
      if ((paramString.equals("ACHBATCHES")) && (ACH.achBatches != null)) {
        ACH.achBatches.continueXMLParsing(getHandler());
      }
      if ((paramString.equals("ACHPAYEES")) && (ACH.achPayees != null)) {
        ACH.achPayees.continueXMLParsing(getHandler());
      } else {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.ACH
 * JD-Core Version:    0.7.0.1
 */