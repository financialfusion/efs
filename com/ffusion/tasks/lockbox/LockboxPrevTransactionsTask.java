package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.beans.lockbox.LockboxTransaction;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Lockbox;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxPrevTransactionsTask
  extends BaseTask
{
  protected String _accountID = null;
  protected long _prevIdx;
  protected int _pageSize;
  protected long _lastIndex;
  protected long _firstIndex;
  protected long _goBackIndex;
  protected boolean _moreTransactionsForDisplay = true;
  protected long _minTransactionInDay = -1L;
  protected long _maxTransactionInDay = -1L;
  String aNi = "P";
  
  public long getMinTransactionInDay()
  {
    return this._minTransactionInDay;
  }
  
  public void setMinTransactionInDay(String paramString)
  {
    this._minTransactionInDay = Long.parseLong(paramString);
  }
  
  public long getMaxTransactionInDay()
  {
    return this._maxTransactionInDay;
  }
  
  public void setMaxTransactionInDay(String paramString)
  {
    this._maxTransactionInDay = Long.parseLong(paramString);
  }
  
  public boolean getMoreTransactionsForDisplay()
  {
    return this._moreTransactionsForDisplay;
  }
  
  public void setGoBackIndex(String paramString)
  {
    this._goBackIndex = Long.parseLong(paramString);
  }
  
  public void setFirstIndex(String paramString)
  {
    this._firstIndex = Long.parseLong(paramString);
  }
  
  public long getFirstIndex()
  {
    return this._firstIndex;
  }
  
  public void setLastIndex(String paramString)
  {
    this._lastIndex = Long.parseLong(paramString);
  }
  
  public long getLastIndex()
  {
    return this._lastIndex;
  }
  
  public void setPageSize(String paramString)
  {
    this._pageSize = Integer.parseInt(paramString);
  }
  
  public int getPageSize()
  {
    return this._pageSize;
  }
  
  public String getAcctID()
  {
    return this._accountID;
  }
  
  public void setAcctID(String paramString)
  {
    this._accountID = paramString;
  }
  
  public long getPrevIndex()
  {
    return this._prevIdx;
  }
  
  public void setPrevIndex(long paramLong)
  {
    this._prevIdx = paramLong;
  }
  
  public void setPrevIndex(String paramString)
  {
    this._prevIdx = Long.parseLong(paramString);
  }
  
  public void setLastIndex(LockboxTransactions paramLockboxTransactions)
  {
    this._lastIndex = -9223372036854775808L;
    Iterator localIterator = paramLockboxTransactions.iterator();
    while (localIterator.hasNext())
    {
      LockboxTransaction localLockboxTransaction = (LockboxTransaction)localIterator.next();
      if (this._lastIndex < localLockboxTransaction.getTransactionIndex()) {
        this._lastIndex = localLockboxTransaction.getTransactionIndex();
      }
    }
    this._lastIndex += 1L;
  }
  
  public void setFirstIndex(LockboxTransactions paramLockboxTransactions)
  {
    this._firstIndex = 9223372036854775807L;
    Iterator localIterator = paramLockboxTransactions.iterator();
    while (localIterator.hasNext())
    {
      LockboxTransaction localLockboxTransaction = (LockboxTransaction)localIterator.next();
      if (this._firstIndex > localLockboxTransaction.getTransactionIndex()) {
        this._firstIndex = localLockboxTransaction.getTransactionIndex();
      }
    }
    this._firstIndex -= 1L;
  }
  
  public void setDataClassification(String paramString)
  {
    this.aNi = paramString;
  }
  
  public String getDataClassification()
  {
    return this.aNi;
  }
  
  public void processTransactions(LockboxTransactions paramLockboxTransactions)
  {
    LockboxTransaction localLockboxTransaction1 = (LockboxTransaction)paramLockboxTransactions.get(0);
    if (localLockboxTransaction1.getTransactionIndex() < this._minTransactionInDay)
    {
      this._moreTransactionsForDisplay = false;
      Iterator localIterator = paramLockboxTransactions.iterator();
      while (localIterator.hasNext())
      {
        LockboxTransaction localLockboxTransaction2 = (LockboxTransaction)localIterator.next();
        if (localLockboxTransaction2.getTransactionIndex() >= this._minTransactionInDay) {
          break;
        }
        localIterator.remove();
      }
    }
    else if (localLockboxTransaction1.getTransactionIndex() == this._minTransactionInDay)
    {
      this._moreTransactionsForDisplay = false;
    }
    else
    {
      this._moreTransactionsForDisplay = true;
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    LockboxSummaries localLockboxSummaries = (LockboxSummaries)localHttpSession.getAttribute("LOCKBOX_SUMMARIES");
    if ((this._accountID == null) || (this._accountID.length() <= 0))
    {
      this.error = 50;
      return super.getServiceErrorURL();
    }
    Object localObject1 = null;
    Object localObject2;
    if (localLockboxSummaries != null)
    {
      localObject2 = null;
      for (int i = 0; i < localLockboxSummaries.size(); i++)
      {
        localObject2 = ((LockboxSummary)localLockboxSummaries.get(i)).getLockboxAccount();
        if (this._accountID.equals(((LockboxAccount)localObject2).getAccountID()))
        {
          localObject1 = localObject2;
          break;
        }
      }
    }
    if (localObject1 == null)
    {
      this.error = 62;
      return super.getServiceErrorURL();
    }
    try
    {
      localObject2 = new HashMap();
      ((HashMap)localObject2).put("PAGESIZE", new Integer(this._pageSize));
      ((HashMap)localObject2).put("DATA_CLASSIFICATION", this.aNi);
      if (this._minTransactionInDay != -1L) {
        ((HashMap)localObject2).put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(this._minTransactionInDay));
      }
      if (this._maxTransactionInDay != -1L) {
        ((HashMap)localObject2).put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(this._maxTransactionInDay));
      }
      LockboxTransactions localLockboxTransactions = Lockbox.getPreviousTransactions(localSecureUser, localObject1, this._prevIdx, (HashMap)localObject2);
      if (localLockboxTransactions.size() != 0)
      {
        processTransactions(localLockboxTransactions);
        setLastIndex(localLockboxTransactions);
        setFirstIndex(localLockboxTransactions);
      }
      else
      {
        this._moreTransactionsForDisplay = false;
        this._lastIndex = (this._goBackIndex - this._pageSize);
        this._firstIndex = (--this._goBackIndex);
      }
      localHttpSession.setAttribute("LOCKBOX_TRANSACTIONS", localLockboxTransactions);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxPrevTransactionsTask
 * JD-Core Version:    0.7.0.1
 */