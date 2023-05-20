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

public class SendGlobalMessage
  extends BaseTask
  implements Task
{
  private boolean rT = false;
  boolean rS = false;
  private String rR = "GlobalMessage";
  private boolean rU = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    boolean bool = true;
    if (this.rT) {
      bool = initialize(localHttpSession);
    }
    if (bool)
    {
      if (this.rS)
      {
        int i = 1;
        GlobalMessage localGlobalMessage1 = (GlobalMessage)localHttpSession.getAttribute(this.rR);
        GlobalMessage localGlobalMessage2 = (GlobalMessage)localHttpSession.getAttribute("OldGlobalMessage");
        if ((localGlobalMessage1 == null) || (localGlobalMessage2 == null))
        {
          this.error = 8048;
          return this.taskErrorURL;
        }
        try
        {
          if (!validateMessage(localGlobalMessage1, localHttpSession))
          {
            i = 0;
            if (this.rU)
            {
              str2 = this.serviceErrorURL;
              return str2;
            }
            String str2 = this.taskErrorURL;
            return str2;
          }
          if (jdMethod_for(localGlobalMessage1, localGlobalMessage2, localSecureUser)) {
            localHashMap.put("TextModified", "Y");
          } else {
            localHashMap.put("TextModified", "N");
          }
          Messages.sendGlobalMessage(localSecureUser, localGlobalMessage1, localHashMap);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str1 = this.serviceErrorURL;
        }
        finally
        {
          if (i != 0) {
            localHttpSession.removeAttribute("OldGlobalMessage");
          }
        }
      }
    }
    else {
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.rR = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.rR;
  }
  
  public void setInitialize(String paramString)
  {
    this.rT = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.rS = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean initialize(HttpSession paramHttpSession)
  {
    GlobalMessage localGlobalMessage1 = (GlobalMessage)paramHttpSession.getAttribute(this.rR);
    if (localGlobalMessage1 == null)
    {
      this.error = 8048;
      return false;
    }
    GlobalMessage localGlobalMessage2 = new GlobalMessage();
    localGlobalMessage2.set(localGlobalMessage1);
    paramHttpSession.setAttribute("OldGlobalMessage", localGlobalMessage2);
    this.rT = false;
    return true;
  }
  
  public boolean checkMessageModified(GlobalMessage paramGlobalMessage1, GlobalMessage paramGlobalMessage2)
  {
    boolean bool = false;
    bool = checkValueModified(paramGlobalMessage1.getFromName(), paramGlobalMessage2.getFromName());
    if (!bool)
    {
      bool = checkValueModified(paramGlobalMessage1.getSubject(), paramGlobalMessage2.getSubject());
      if (!bool) {
        bool = (bool) || (checkValueModified(paramGlobalMessage1.getMsgText(), paramGlobalMessage2.getMsgText()));
      }
    }
    return bool;
  }
  
  public boolean checkValueModified(String paramString1, String paramString2)
  {
    return !paramString1.equals(paramString2);
  }
  
  protected boolean validateMessage(GlobalMessage paramGlobalMessage, HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (!e(paramGlobalMessage.getToGroupID()))
    {
      this.error = 8004;
      return false;
    }
    LanguageDefns localLanguageDefns;
    try
    {
      localLanguageDefns = UtilHandler.getLanguageList(localSecureUser, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.rU = true;
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
        paramGlobalMessage.setCurrentLanguage(((LanguageDefn)localObject2).getLanguage());
        if ((!e(paramGlobalMessage.getFromName())) && (paramGlobalMessage.getMsgTypeValue() != 6) && (paramGlobalMessage.getMsgTypeValue() != 5) && (paramGlobalMessage.getMsgTypeValue() != 3) && (paramGlobalMessage.getMsgTypeValue() != 4) && (paramGlobalMessage.getMsgTypeValue() != 7) && (paramGlobalMessage.getMsgTypeValue() != 8) && (paramGlobalMessage.getMsgTypeValue() != 9))
        {
          this.error = 8003;
          return false;
        }
        if ((paramGlobalMessage.getFromName() != null) && (paramGlobalMessage.getFromName().length() > 100))
        {
          this.error = 8059;
          return false;
        }
        String str = null;
        if (paramGlobalMessage.getMsgTypeValue() == 2) {
          if (str == null)
          {
            str = paramGlobalMessage.getFromName();
          }
          else if (!paramGlobalMessage.getFromName().equals(str))
          {
            this.error = 8086;
            return false;
          }
        }
        if (!e(paramGlobalMessage.getSubject()))
        {
          this.error = 8005;
          return false;
        }
        if ((paramGlobalMessage.getSubject() != null) && (paramGlobalMessage.getSubject().length() > 100))
        {
          this.error = 8060;
          return false;
        }
        if (!e(paramGlobalMessage.getMsgText()))
        {
          this.error = 8002;
          return false;
        }
      }
    }
    if (paramGlobalMessage.getMsgTypeValue() == 1) {
      paramGlobalMessage.setPriority(1);
    }
    if ((paramGlobalMessage.getMsgTypeValue() == 3) || (paramGlobalMessage.getMsgTypeValue() == 7) || (paramGlobalMessage.getMsgTypeValue() == 5) || (paramGlobalMessage.getMsgTypeValue() == 6) || (paramGlobalMessage.getMsgTypeValue() == 4) || (paramGlobalMessage.getMsgTypeValue() == 8) || (paramGlobalMessage.getMsgTypeValue() == 9))
    {
      localObject1 = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      if ((paramGlobalMessage.getDisplayFromDateValue() == null) || (paramGlobalMessage.getDisplayToDateValue() == null))
      {
        this.error = 8075;
        return false;
      }
      if (paramGlobalMessage.getDisplayFromDateValue().compare(paramGlobalMessage.getDisplayToDateValue()) == 1)
      {
        this.error = 8074;
        return false;
      }
      localObject2 = Calendar.getInstance(TimeZone.getDefault(), (Locale)localObject1);
      ((Calendar)localObject2).set(11, 0);
      ((Calendar)localObject2).set(12, 0);
      ((Calendar)localObject2).set(13, 0);
      ((Calendar)localObject2).set(14, 0);
      if (paramGlobalMessage.getDisplayFromDateValue().compare(new com.ffusion.util.beans.DateTime((Calendar)localObject2, (Locale)localObject1)) == -1)
      {
        this.error = 8076;
        return false;
      }
    }
    return true;
  }
  
  private boolean e(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() != 0);
  }
  
  private boolean jdMethod_for(GlobalMessage paramGlobalMessage1, GlobalMessage paramGlobalMessage2, SecureUser paramSecureUser)
  {
    try
    {
      LanguageDefns localLanguageDefns = UtilHandler.getLanguageList(paramSecureUser, null);
      Iterator localIterator = localLanguageDefns.iterator();
      if (localIterator.hasNext())
      {
        LanguageDefn localLanguageDefn = (LanguageDefn)localIterator.next();
        String str1 = paramGlobalMessage1.getMsgText(localLanguageDefn.getLanguage());
        String str2 = paramGlobalMessage2.getMsgText(localLanguageDefn.getLanguage());
        if (!str1.equals(str2)) {
          return false;
        }
      }
    }
    catch (CSILException localCSILException)
    {
      return true;
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.SendGlobalMessage
 * JD-Core Version:    0.7.0.1
 */