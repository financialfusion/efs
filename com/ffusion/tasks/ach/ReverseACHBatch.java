package com.ffusion.tasks.ach;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.beans.ach.ACHEntries;
import com.ffusion.beans.ach.ACHEntry;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.PagingContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReverseACHBatch
  extends ACHBatch
  implements Task
{
  protected boolean initialize;
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean clearEntriesFlag = true;
  protected DateTime origDate;
  protected String batchName = "ACHBatch";
  protected String batchesName = "ACHBatches";
  protected ACHEntry currentEntry;
  protected boolean entriesAlreadyReversed = false;
  protected boolean acceptedDateRestriction = false;
  protected ACHEntries alreadyReversedEntries;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = null;
    if (this.initFlag)
    {
      str1 = this.successURL;
      this.initFlag = false;
      this.error = 0;
      ACHBatch localACHBatch = (ACHBatch)localHttpSession.getAttribute(this.batchName);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.alreadyReversedEntries = new ACHEntries();
      if (localACHBatch == null)
      {
        this.error = 16002;
        str1 = this.taskErrorURL;
        return str1;
      }
      this.origDate = ((DateTime)localACHBatch.getDateValue().clone());
      set(localACHBatch);
      localHttpSession.setAttribute("ACHEntries", getACHEntries());
      DateTime localDateTime1 = (DateTime)this.origDate.clone();
      DateTime localDateTime2 = new DateTime();
      int i = 0;
      BankIdentifier localBankIdentifier = new BankIdentifier(Locale.getDefault());
      while (localDateTime1.before(localDateTime2))
      {
        int j = localDateTime1.get(7);
        if (j == 7) {
          localDateTime1.add(5, 2);
        } else if (j == 1) {
          localDateTime1.add(5, 1);
        }
        boolean bool = true;
        try
        {
          bool = SmartCalendar.isBankingDay(localSecureUser, localBankIdentifier, localDateTime1.getTime(), new HashMap());
        }
        catch (CSILException localCSILException1) {}
        if (bool) {
          i++;
        }
        localDateTime1.add(6, 1);
      }
      if (i > 5)
      {
        this.error = 16016;
        str1 = this.taskErrorURL;
        return str1;
      }
      HashMap localHashMap = new HashMap();
      PagingContext localPagingContext = new PagingContext(null, null);
      localHashMap.put("ACH_VIEW", "ALL");
      localHashMap.put("ACH_TYPE", "REVERSAL");
      localHashMap.put("ACH_STATUS", "ACH_STATUS_REVERSAL");
      localHashMap.put("TrackingID", localACHBatch.getTrackingID());
      localHashMap.put("PAGESIZE", "100");
      ACHBatches localACHBatches = null;
      try
      {
        localACHBatches = ACH.getPagedACHBatchHistories(localSecureUser, localPagingContext, localHashMap);
      }
      catch (CSILException localCSILException2) {}
      Object localObject;
      if (localACHBatches != null)
      {
        localIterator1 = localACHBatches.iterator();
        while (localIterator1.hasNext())
        {
          localObject = (ACHBatch)localIterator1.next();
          if ((((ACHBatch)localObject).getOriginalID() != null) && (((ACHBatch)localObject).getOriginalID().equals(localACHBatch.getID())))
          {
            try
            {
              ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute("ACHPayees");
              localHashMap.put("ACHPayees", localACHPayees);
              localObject = ACH.getACHBatch(localSecureUser, (ACHBatch)localObject, localHashMap);
            }
            catch (CSILException localCSILException3) {}
            this.alreadyReversedEntries.addAll(((ACHBatch)localObject).getACHEntries());
          }
        }
      }
      Iterator localIterator1 = this.entries.iterator();
      while (localIterator1.hasNext())
      {
        localObject = (ACHEntry)localIterator1.next();
        ((ACHEntry)localObject).setActive("false");
        ((ACHEntry)localObject).setCanReverse("true");
        String str2 = ((ACHRecordInfo)((ACHEntry)localObject).getBpwEntryObject()).getRecordContent();
        if (this.alreadyReversedEntries.size() > 0)
        {
          Iterator localIterator2 = this.alreadyReversedEntries.iterator();
          while (localIterator2.hasNext())
          {
            ACHEntry localACHEntry = (ACHEntry)localIterator2.next();
            if ((localACHEntry.getAction() == null) || (!localACHEntry.getAction().equals("REVERSE")))
            {
              localACHEntry.setAction("REVERSE");
              localACHEntry.setCanReverse("true");
              localACHEntry.setAmountIsDebit(!localACHEntry.getAmountIsDebitValue());
            }
            String str3 = ((ACHRecordInfo)localACHEntry.getBpwEntryObject()).getRecordContent();
            if ((str3 != null) && (str2 != null) && (str3.length() == str2.length()) && (str2.length() >= 94) && (((ACHEntry)localObject).getTransactionCodeValue(localACHBatch.getStandardEntryClassCodeValue()) == localACHEntry.getTransactionCodeValue(localACHBatch.getStandardEntryClassCodeValue())) && (str3.substring(3, 79).equals(str2.substring(3, 79))) && (localACHEntry.getCanReverseValue() == true))
            {
              ((ACHEntry)localObject).setCanReverse("false");
              localACHEntry.setCanReverse("false");
              break;
            }
          }
        }
      }
    }
    else if (this.processFlag)
    {
      this.processFlag = false;
      str1 = revertACHBatch(localHttpSession);
    }
    else
    {
      str1 = this.successURL;
    }
    return str1;
  }
  
  protected String revertACHBatch(HttpSession paramHttpSession)
  {
    String str = null;
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      ACHBatch localACHBatch = null;
      if (!this.entriesAlreadyReversed)
      {
        Iterator localIterator = this.entries.iterator();
        while (localIterator.hasNext())
        {
          ACHEntry localACHEntry = (ACHEntry)localIterator.next();
          localACHEntry.setAmountIsDebit(!localACHEntry.getAmountIsDebitValue());
        }
        if (getFrequencyValue() != 0) {
          setFrequency(0);
        }
        setCoEntryDesc("REVERSAL");
        setOriginalID(getID());
        this.entriesAlreadyReversed = true;
      }
      localACHBatch = ACH.addACHBatch(localSecureUser, this, localHashMap);
      set(localACHBatch);
      str = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public final void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.error);
  }
  
  public final void setBatchName(String paramString)
  {
    this.batchName = paramString;
  }
  
  public final String getBatchName()
  {
    return this.batchName;
  }
  
  public final void setBatchesName(String paramString)
  {
    this.batchesName = paramString;
  }
  
  public String getDateWithinRangeForReverse()
  {
    return String.valueOf(Boolean.TRUE);
  }
  
  public void setCurrentEntry(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.currentEntry = getACHEntries().getByID(paramString);
    } else {
      this.currentEntry = null;
    }
  }
  
  public void setEntryActive(String paramString)
  {
    if ((this.currentEntry != null) && (this.currentEntry.getCanReverseValue() == true)) {
      this.currentEntry.setActive(paramString);
    }
  }
  
  public String getAcceptedDateRestriction()
  {
    return String.valueOf(this.acceptedDateRestriction);
  }
  
  public void setAcceptedDateRestriction(String paramString)
  {
    if ((paramString != null) && (paramString.equalsIgnoreCase("true"))) {
      this.acceptedDateRestriction = true;
    } else {
      this.acceptedDateRestriction = false;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.ReverseACHBatch
 * JD-Core Version:    0.7.0.1
 */