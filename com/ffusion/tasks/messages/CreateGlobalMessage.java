package com.ffusion.tasks.messages;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.beans.util.LanguageDefn;
import com.ffusion.beans.util.LanguageDefns;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Messages;
import com.ffusion.csil.handlers.UtilHandler;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.InvalidDateTimeException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateGlobalMessage
  extends BaseTask
  implements Task
{
  protected GlobalMessage _createdMessage = null;
  private boolean rJ = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (!validate(localHttpSession))
    {
      if (this.rJ) {
        return this.serviceErrorURL;
      }
      return this.taskErrorURL;
    }
    this._createdMessage.setFromID(localSecureUser.getProfileID());
    try
    {
      Messages.createGlobalMessage(localSecureUser, this._createdMessage, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validate(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if ((this._createdMessage.getRecordTypeValue() == 2) && ((this._createdMessage.getTemplateName() == null) || (this._createdMessage.getTemplateName().trim().length() == 0)))
    {
      this.error = 8068;
      return false;
    }
    if (!d(this._createdMessage.getToGroupID()))
    {
      this.error = 8004;
      return false;
    }
    if ((this._createdMessage.getMsgTypeValue() == 2) && (this._createdMessage.getFromName().indexOf(" ") > -1))
    {
      this.error = 8067;
      return false;
    }
    LanguageDefns localLanguageDefns = null;
    String str = null;
    try
    {
      localLanguageDefns = UtilHandler.getLanguageList(localSecureUser, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.rJ = true;
      return false;
    }
    Object localObject1;
    Object localObject2;
    if (localLanguageDefns != null)
    {
      localObject1 = localLanguageDefns.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (LanguageDefn)((Iterator)localObject1).next();
        if (localObject2 == null)
        {
          this.error = 8499;
          return false;
        }
        this._createdMessage.setCurrentLanguage(((LanguageDefn)localObject2).getLanguage());
        if ((!d(this._createdMessage.getFromName())) && (this._createdMessage.getMsgTypeValue() != 6) && (this._createdMessage.getMsgTypeValue() != 5) && (this._createdMessage.getMsgTypeValue() != 3) && (this._createdMessage.getMsgTypeValue() != 4) && (this._createdMessage.getMsgTypeValue() != 7) && (this._createdMessage.getMsgTypeValue() != 8) && (this._createdMessage.getMsgTypeValue() != 9))
        {
          this.error = 8003;
          return false;
        }
        if ((this._createdMessage.getFromName() != null) && (this._createdMessage.getFromName().length() > 100))
        {
          this.error = 8059;
          return false;
        }
        if (this._createdMessage.getMsgTypeValue() == 2) {
          if (str == null)
          {
            str = this._createdMessage.getFromName();
          }
          else if (!this._createdMessage.getFromName().equals(str))
          {
            this.error = 8086;
            return false;
          }
        }
        if (!d(this._createdMessage.getSubject()))
        {
          this.error = 8005;
          return false;
        }
        if ((this._createdMessage.getSubject() != null) && (this._createdMessage.getSubject().length() > 100))
        {
          this.error = 8060;
          return false;
        }
        if (!d(this._createdMessage.getMsgText()))
        {
          this.error = 8002;
          return false;
        }
      }
    }
    if (this._createdMessage.getMsgTypeValue() == 1) {
      this._createdMessage.setPriority(1);
    }
    if ((this._createdMessage.getRecordTypeValue() == 2) && (this._createdMessage.getTemplateName() != null) && (this._createdMessage.getTemplateName().length() > 255))
    {
      this.error = 8061;
      return false;
    }
    if ((this._createdMessage.getToGroupTypeValue() == 1) && (this._createdMessage.getToGroupIDValue() == -1))
    {
      this.error = 8073;
      return false;
    }
    if ((this._createdMessage.getMsgTypeValue() == 3) || (this._createdMessage.getMsgTypeValue() == 7) || (this._createdMessage.getMsgTypeValue() == 5) || (this._createdMessage.getMsgTypeValue() == 6) || (this._createdMessage.getMsgTypeValue() == 4) || (this._createdMessage.getMsgTypeValue() == 8) || (this._createdMessage.getMsgTypeValue() == 9))
    {
      localObject1 = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      try
      {
        if ((this._createdMessage.getDisplayFromDate() == null) || (this._createdMessage.getDisplayToDate() == null))
        {
          this.error = 8075;
          return false;
        }
        localObject2 = new DateTime(this._createdMessage.getDisplayFromDate(), this._createdMessage.getLocale(), this._createdMessage.getDateFormat());
        DateTime localDateTime = new DateTime(this._createdMessage.getDisplayToDate(), this._createdMessage.getLocale(), this._createdMessage.getDateFormat());
        if (((DateTime)localObject2).compare(localDateTime) == 1)
        {
          this.error = 8074;
          return false;
        }
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault(), (Locale)localObject1);
        localCalendar.set(11, 0);
        localCalendar.set(12, 0);
        localCalendar.set(13, 0);
        localCalendar.set(14, 0);
        if (((DateTime)localObject2).compare(new DateTime(localCalendar, (Locale)localObject1)) == -1)
        {
          this.error = 8076;
          return false;
        }
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        this.error = 8075;
        return false;
      }
    }
    return true;
  }
  
  public void setToID(String paramString)
  {
    this._createdMessage.setToGroupID(paramString);
  }
  
  public String getToGroupID()
  {
    return this._createdMessage.getToGroupID();
  }
  
  public void setToGroupID(String paramString)
  {
    this._createdMessage.setToGroupID(paramString);
  }
  
  public String getToID()
  {
    return this._createdMessage.getToGroupID();
  }
  
  public void setFrom(String paramString)
  {
    this._createdMessage.setFromName(paramString);
  }
  
  public String getFrom()
  {
    return this._createdMessage.getFromName();
  }
  
  public void setFromName(String paramString)
  {
    this._createdMessage.setFromName(paramString);
  }
  
  public String getFromName()
  {
    return this._createdMessage.getFromName();
  }
  
  public void setSubject(String paramString)
  {
    this._createdMessage.setSubject(paramString);
  }
  
  public String getSubject()
  {
    return this._createdMessage.getSubject();
  }
  
  public void setMessage(String paramString)
  {
    this._createdMessage.setMsgText(paramString);
  }
  
  public String getMessage()
  {
    return this._createdMessage.getMsgText();
  }
  
  public void setMsgText(String paramString)
  {
    this._createdMessage.setMsgText(paramString);
  }
  
  public String getMsgText()
  {
    return this._createdMessage.getMsgText();
  }
  
  public void setBankId(String paramString)
  {
    this._createdMessage.setBankId(paramString);
  }
  
  public String getBankId()
  {
    return this._createdMessage.getBankId();
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this._createdMessage.setAffiliateBankId(paramString);
  }
  
  public String getAffiliateBankId()
  {
    return this._createdMessage.getAffiliateBankId();
  }
  
  public void setRecordType(String paramString)
  {
    this._createdMessage.setRecordType(paramString);
  }
  
  public String getRecordType()
  {
    return this._createdMessage.getRecordType();
  }
  
  public void setMsgType(String paramString)
  {
    this._createdMessage.setMsgType(paramString);
  }
  
  public String getMsgType()
  {
    return this._createdMessage.getMsgType();
  }
  
  public void setToGroupType(String paramString)
  {
    this._createdMessage.setToGroupType(paramString);
  }
  
  public String getToGroupType()
  {
    return this._createdMessage.getToGroupType();
  }
  
  public void setColor(String paramString)
  {
    this._createdMessage.setColor(paramString);
  }
  
  public String getColor()
  {
    return this._createdMessage.getColor();
  }
  
  public void setPriority(String paramString)
  {
    this._createdMessage.setPriority(paramString);
  }
  
  public String getPriority()
  {
    return this._createdMessage.getPriority();
  }
  
  public void setTemplateName(String paramString)
  {
    this._createdMessage.setTemplateName(paramString);
  }
  
  public String getTemplateName()
  {
    return this._createdMessage.getTemplateName();
  }
  
  public void setSendNow(String paramString)
  {
    this._createdMessage.setSendNow(paramString);
  }
  
  public String getSendNow()
  {
    return this._createdMessage.getSendNow();
  }
  
  public void setDisplayFromDate(String paramString)
  {
    this._createdMessage.setDisplayFromDate(paramString);
  }
  
  public String getDisplayFromDate()
  {
    return this._createdMessage.getDisplayFromDate();
  }
  
  public void setDisplayToDate(String paramString)
  {
    this._createdMessage.setDisplayToDate(paramString);
  }
  
  public String getDisplayToDate()
  {
    return this._createdMessage.getDisplayToDate();
  }
  
  private boolean d(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() != 0);
  }
  
  public void setToGroupName(String paramString)
  {
    this._createdMessage.setToGroupName(paramString);
  }
  
  public String getToGroupName()
  {
    return this._createdMessage.getToGroupName();
  }
  
  public void setCurrentLanguage(String paramString)
  {
    this._createdMessage.setCurrentLanguage(paramString);
  }
  
  public String getCurrentLanguage()
  {
    return this._createdMessage.getCurrentLanguage();
  }
  
  public void setDateFormat(String paramString)
  {
    this._createdMessage.setDateFormat(paramString);
  }
  
  public String getDateFormat()
  {
    return this._createdMessage.getDateFormat();
  }
  
  public Object get(Object paramObject)
  {
    return this._createdMessage.get(paramObject);
  }
  
  public void put(Object paramObject1, Object paramObject2)
  {
    this._createdMessage.put(paramObject1, paramObject2);
  }
  
  public GlobalMessage getGlobalMessage()
  {
    return this._createdMessage;
  }
  
  public int getFilterCount()
  {
    if (this._createdMessage != null)
    {
      FilteredList localFilteredList = (FilteredList)this._createdMessage.get("_filters");
      if (localFilteredList != null) {
        return localFilteredList.size();
      }
    }
    return 0;
  }
  
  public void setGlobalMessageInformation(GlobalMessage paramGlobalMessage)
  {
    this._createdMessage.set(paramGlobalMessage);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.CreateGlobalMessage
 * JD-Core Version:    0.7.0.1
 */