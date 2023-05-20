package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItem;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Lockbox;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxNextCreditItemsTask
  extends BaseTask
{
  protected String _accountID = null;
  protected int _pageSize;
  protected long _nextIdx;
  protected String _lockboxNumber;
  protected long _lastIndex;
  protected long _firstIndex;
  protected boolean _moreCreditItemsForDisplay = true;
  protected long _minCreditItemInDay = -1L;
  protected long _maxCreditItemInDay = -1L;
  String aNt = "P";
  
  public void setDataClassification(String paramString)
  {
    this.aNt = paramString;
  }
  
  public String getDataClassification()
  {
    return this.aNt;
  }
  
  public boolean getMoreCreditItemsForDisplay()
  {
    return this._moreCreditItemsForDisplay;
  }
  
  public long getMinCreditItemInDay()
  {
    return this._minCreditItemInDay;
  }
  
  public void setMinCreditItemInDay(String paramString)
  {
    this._minCreditItemInDay = Long.parseLong(paramString);
  }
  
  public long getMaxCreditItemInDay()
  {
    return this._maxCreditItemInDay;
  }
  
  public void setMaxCreditItemInDay(String paramString)
  {
    this._maxCreditItemInDay = Long.parseLong(paramString);
  }
  
  public void setLockboxNumber(String paramString)
  {
    this._lockboxNumber = paramString;
  }
  
  public String getLockboxNumber()
  {
    return this._lockboxNumber;
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
  
  public String getAcctID()
  {
    return this._accountID;
  }
  
  public void setAcctID(String paramString)
  {
    this._accountID = paramString;
  }
  
  public long getNextIndex()
  {
    return this._nextIdx;
  }
  
  public void setNextIndex(long paramLong)
  {
    this._nextIdx = paramLong;
  }
  
  public void setNextIndex(String paramString)
  {
    this._nextIdx = Long.parseLong(paramString);
  }
  
  public void setLastIndex(LockboxCreditItems paramLockboxCreditItems)
  {
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramLockboxCreditItems.get(paramLockboxCreditItems.size() - 1);
    this._lastIndex = (localLockboxCreditItem.getItemIndex() + 1L);
  }
  
  public void setFirstIndex(LockboxCreditItems paramLockboxCreditItems)
  {
    LockboxCreditItem localLockboxCreditItem = (LockboxCreditItem)paramLockboxCreditItems.get(0);
    this._firstIndex = (localLockboxCreditItem.getItemIndex() - 1L);
  }
  
  public void processCreditItems(LockboxCreditItems paramLockboxCreditItems)
  {
    LockboxCreditItem localLockboxCreditItem1 = (LockboxCreditItem)paramLockboxCreditItems.get(paramLockboxCreditItems.size() - 1);
    if (localLockboxCreditItem1.getItemIndex() > this._maxCreditItemInDay)
    {
      this._moreCreditItemsForDisplay = false;
      for (int i = paramLockboxCreditItems.size() - 1; i > 0; i--)
      {
        LockboxCreditItem localLockboxCreditItem2 = (LockboxCreditItem)paramLockboxCreditItems.get(i);
        if (localLockboxCreditItem2.getItemIndex() <= this._maxCreditItemInDay) {
          break;
        }
        paramLockboxCreditItems.remove(i);
      }
    }
    else if (localLockboxCreditItem1.getItemIndex() == this._maxCreditItemInDay)
    {
      this._moreCreditItemsForDisplay = false;
    }
    else
    {
      this._moreCreditItemsForDisplay = true;
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
    if ((this._lockboxNumber == null) || (this._lockboxNumber.length() <= 0))
    {
      this.error = 71;
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
      ((HashMap)localObject2).put("DATA_CLASSIFICATION", this.aNt);
      if (this._minCreditItemInDay != -1L) {
        ((HashMap)localObject2).put("MINIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(this._minCreditItemInDay));
      }
      if (this._maxCreditItemInDay != -1L) {
        ((HashMap)localObject2).put("MAXIMUM_TRANSACTION_INDEX_FOR_RANGE", new Long(this._maxCreditItemInDay));
      }
      LockboxCreditItems localLockboxCreditItems = Lockbox.getNextCreditItems(localSecureUser, localObject1, this._lockboxNumber, this._nextIdx, (HashMap)localObject2);
      if (localLockboxCreditItems.size() != 0)
      {
        processCreditItems(localLockboxCreditItems);
        setLastIndex(localLockboxCreditItems);
        setFirstIndex(localLockboxCreditItems);
      }
      else
      {
        this._moreCreditItemsForDisplay = false;
      }
      localHttpSession.setAttribute("LOCKBOX_CREDIT_ITEMS", localLockboxCreditItems);
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
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxNextCreditItemsTask
 * JD-Core Version:    0.7.0.1
 */