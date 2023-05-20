package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.beans.lockbox.LockboxSummary;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Lockbox;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxCreditItemsTask
  extends BaseTask
{
  protected String _accountID = null;
  protected String _startDateStr = null;
  protected String _endDateStr = null;
  protected String _lockboxNumber = null;
  protected String _dataClassification = "P";
  
  public void setLockboxNumber(String paramString)
  {
    this._lockboxNumber = paramString;
  }
  
  public String getLockboxNumber()
  {
    return this._lockboxNumber;
  }
  
  public void setDataClassification(String paramString)
  {
    this._dataClassification = paramString;
  }
  
  public String getDataClassification()
  {
    return this._dataClassification;
  }
  
  public void setStartDate(String paramString)
  {
    this._startDateStr = paramString;
  }
  
  public String getStartDate()
  {
    return this._startDateStr == null ? "" : this._startDateStr;
  }
  
  public String getEndDate()
  {
    return this._endDateStr == null ? "" : this._endDateStr;
  }
  
  public void setEndDate(String paramString)
  {
    this._endDateStr = paramString;
  }
  
  public String getAcctID()
  {
    return this._accountID;
  }
  
  public void setAcctID(String paramString)
  {
    this._accountID = paramString;
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
    Object localObject2 = null;
    GregorianCalendar localGregorianCalendar = null;
    if ((this._startDateStr != null) && (this._startDateStr.length() > 0)) {
      try
      {
        localObject2 = new GregorianCalendar(localSecureUser.getLocale());
        Date localDate1 = DateFormatUtil.getFormatter("yyyy-MM-dd").parse(this._startDateStr);
        ((Calendar)localObject2).setTime(localDate1);
      }
      catch (ParseException localParseException1)
      {
        this.error = 44;
        return super.getServiceErrorURL();
      }
    }
    if ((this._endDateStr != null) && (this._endDateStr.length() > 0)) {
      try
      {
        localGregorianCalendar = new GregorianCalendar(localSecureUser.getLocale());
        Date localDate2 = DateFormatUtil.getFormatter("yyyy-MM-dd").parse(this._endDateStr);
        localGregorianCalendar.setTime(localDate2);
      }
      catch (ParseException localParseException2)
      {
        this.error = 44;
        return super.getServiceErrorURL();
      }
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("DATA_CLASSIFICATION", this._dataClassification);
      LockboxCreditItems localLockboxCreditItems = Lockbox.getCreditItems(localSecureUser, localObject1, this._lockboxNumber, (Calendar)localObject2, localGregorianCalendar, localHashMap);
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
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxCreditItemsTask
 * JD-Core Version:    0.7.0.1
 */