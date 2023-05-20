package com.ffusion.tasks.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.messages.PerformanceRpt;
import com.ffusion.beans.messages.PerformanceRpts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.MessageAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRptPerformance
  extends BaseTask
  implements Task
{
  private static final int s9 = 6;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error = 0;
  private int th = 0;
  private String s7;
  protected boolean allThreadFlag = false;
  private String td;
  private String te;
  private String tf;
  private String ta;
  private String s5;
  private String tb;
  private String s8;
  private String tc;
  private String s6;
  private String tg;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
    if (localMessageQueues == null)
    {
      this.error = 8020;
      return this.taskErrorURL;
    }
    DateTime localDateTime1 = new DateTime((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localDateTime1.setFormat(ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", (Locale)localHttpSession.getAttribute("java.util.Locale")));
    DateTime localDateTime2 = new DateTime((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localDateTime2.setFormat(ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", (Locale)localHttpSession.getAttribute("java.util.Locale")));
    while (1 != localDateTime1.get(7))
    {
      localDateTime1.add(6, -1);
      localDateTime2.add(6, -1);
    }
    localDateTime1.add(6, -1);
    for (int i = 0; i < this.th; i++)
    {
      localDateTime2.add(3, -1);
      localDateTime1.add(3, -1);
    }
    for (i = 0; i < 6; i++) {
      localDateTime2.add(3, -1);
    }
    localDateTime2.set(11, 0);
    localDateTime2.set(12, 0);
    localDateTime2.set(13, 0);
    localDateTime2.set(14, 0);
    localDateTime1.set(11, 0);
    localDateTime1.set(12, 0);
    localDateTime1.set(13, 0);
    localDateTime1.set(14, 0);
    DateTime localDateTime3 = (DateTime)localDateTime1.clone();
    localDateTime1.add(5, 1);
    localDateTime1.add(14, -1);
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(localDateTime2.toString());
    localStringBuffer.append(",");
    localStringBuffer.append(localDateTime1.toString());
    localStringBuffer.append(",");
    localStringBuffer.append(String.valueOf(this.allThreadFlag));
    if (this.tg != null)
    {
      localStringBuffer.append(",");
      localStringBuffer.append(this.tg);
    }
    MessageThreads localMessageThreads = new MessageThreads((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localHashMap.put("MESSAGETHREADS", localMessageThreads);
    localHashMap.put("OBJECT", localStringBuffer.toString());
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localMessageThreads = MessageAdmin.getMessageThreads(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      str1 = this.serviceErrorURL;
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    com.ffusion.beans.messages.Messages localMessages = new com.ffusion.beans.messages.Messages(localLocale);
    if (this.error == 0)
    {
      localDateTime2.add(3, 1);
      int j = 1;
      int k = 1;
      PerformanceRpts localPerformanceRpts = new PerformanceRpts();
      float f1 = 0.0F;
      float f2 = 0.0F;
      float f3 = 0.0F;
      float f4 = 0.0F;
      float f5 = 0.0F;
      float f6 = 0.0F;
      float f7 = 0.0F;
      try
      {
        if ((this.td != null) && (this.td.length() > 0)) {
          f1 = Float.parseFloat(this.td);
        }
        if ((this.te != null) && (this.te.length() > 0)) {
          f2 = Float.parseFloat(this.te);
        }
        if ((this.tf != null) && (this.tf.length() > 0)) {
          f3 = Float.parseFloat(this.tf);
        }
        if ((this.ta != null) && (this.ta.length() > 0)) {
          f4 = Float.parseFloat(this.ta);
        }
        if ((this.s5 != null) && (this.s5.length() > 0)) {
          f5 = Float.parseFloat(this.s5) / 100.0F;
        }
        if ((this.tb != null) && (this.tb.length() > 0)) {
          f6 = Float.parseFloat(this.tb) / 100.0F;
        }
        if ((this.s8 != null) && (this.s8.length() > 0)) {
          f7 = Float.parseFloat(this.s8);
        }
      }
      catch (NumberFormatException localNumberFormatException) {}
      localPerformanceRpts.setAvgTimeToHandleGoal(f4);
      localPerformanceRpts.setAvgTimeToOpenGoal(f3);
      localPerformanceRpts.setClosedCounterGoal(f2);
      localPerformanceRpts.setCounterGoal(f1);
      localPerformanceRpts.setPctAppDeniedGoal(f7);
      localPerformanceRpts.setPctInternalTransferGoal(f5);
      localPerformanceRpts.setPctSingleEventResolutionsGoal(f6);
      localPerformanceRpts.setReportDate(localDateTime1);
      localMessageQueues.setFilter("QueueType=0");
      Iterator localIterator1 = localMessageQueues.iterator();
      while (localIterator1.hasNext())
      {
        MessageQueue localMessageQueue = (MessageQueue)localIterator1.next();
        String str2 = localMessageQueue.getQueueID();
        String str3 = localMessageQueue.getQueueName();
        PerformanceRpt localPerformanceRpt = localPerformanceRpts.create(str2, str3);
        localMessageThreads.setFilter("QueueID=" + str2);
        Iterator localIterator2 = localMessageThreads.iterator();
        DateTime localDateTime4 = null;
        DateTime localDateTime5 = null;
        DateTime localDateTime6 = null;
        DateTime localDateTime7 = null;
        while (localIterator2.hasNext())
        {
          MessageThread localMessageThread = (MessageThread)localIterator2.next();
          localMessages.clear();
          float f8 = 0.0F;
          float f9 = 0.0F;
          int m = 0;
          int n = 0;
          boolean bool = false;
          String str4 = localMessageThread.getClosedDate();
          bool = localMessageThread.getClosedDateValue() != null;
          localHashMap.clear();
          localHashMap.put("MESSAGES", localMessages);
          localHashMap.put("OBJECT", localMessageThread);
          try
          {
            localMessages = com.ffusion.csil.core.Messages.getMessages(localSecureUser, localHashMap);
          }
          catch (CSILException localCSILException2)
          {
            this.error = MapError.mapError(localCSILException2);
            str1 = this.serviceErrorURL;
          }
          if (this.error == 0)
          {
            localDateTime4 = localMessageThread.getCreateDateValue();
            localDateTime7 = localMessageThread.getClosedDateValue();
            localDateTime6 = jdMethod_int(localMessages);
            if (localDateTime6 == null) {
              localDateTime6 = new DateTime(localLocale);
            }
            if (bool) {
              f9 = localDateTime7.getDiff(localDateTime4, 2);
            }
            f8 = localDateTime6.getDiff(localDateTime4, 2);
            m = jdMethod_new(localMessages);
            int i1 = jdMethod_for(localMessages);
            if ((bool) && (i1 == 1)) {
              n = 1;
            } else if ((!bool) && (i1 == 0)) {
              n = 1;
            }
            localDateTime5 = (DateTime)localDateTime4.clone();
            localDateTime5.set(11, 0);
            localDateTime5.set(12, 0);
            localDateTime5.set(13, 0);
            localDateTime5.set(14, 0);
            float f10 = localDateTime3.getDiff(localDateTime5, 3);
            localPerformanceRpt.RegisterPerformance(f10, f8, f9, m, n, bool);
          }
        }
      }
      localHttpSession.setAttribute("PerformanceReports", localPerformanceRpts);
    }
    if (str1 == null) {
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setWeeksToRoll(String paramString)
  {
    this.th = Integer.valueOf(paramString).intValue();
  }
  
  public void setAllThread(String paramString)
  {
    this.allThreadFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  private int jdMethod_for(com.ffusion.beans.messages.Messages paramMessages)
  {
    Iterator localIterator = paramMessages.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      Message localMessage = (Message)localIterator.next();
      String str = (String)localMessage.get("MESSAGE_TYPE");
      if ((str == null) || (!str.equals("Closed"))) {}
    }
    return i;
  }
  
  private int jdMethod_new(com.ffusion.beans.messages.Messages paramMessages)
  {
    Iterator localIterator = paramMessages.iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      Message localMessage = (Message)localIterator.next();
      String str = (String)localMessage.get("MESSAGE_TYPE");
      if ((str == null) || (!str.equals("Internal Query"))) {}
    }
    return i;
  }
  
  private DateTime jdMethod_int(com.ffusion.beans.messages.Messages paramMessages)
  {
    Iterator localIterator = paramMessages.iterator();
    Object localObject = null;
    while (localIterator.hasNext())
    {
      Message localMessage = (Message)localIterator.next();
      DateTime localDateTime = localMessage.getReadDateValue();
      if ((localDateTime != null) && (localObject == null)) {
        localObject = localDateTime;
      } else if ((localDateTime != null) && (localDateTime.before(localObject))) {
        localObject = localDateTime;
      }
    }
    return localObject;
  }
  
  public void setCounterGoal(String paramString)
  {
    this.td = paramString;
  }
  
  public void setClosedCounterGoal(String paramString)
  {
    this.te = paramString;
  }
  
  public void setAvgTimeToOpenGoal(String paramString)
  {
    this.tf = paramString;
  }
  
  public void setAvgTimeToHandleGoal(String paramString)
  {
    this.ta = paramString;
  }
  
  public void setPctInternalTransferGoal(String paramString)
  {
    this.s5 = paramString;
  }
  
  public void setPctSingleEventResolutionsGoal(String paramString)
  {
    this.tb = paramString;
  }
  
  public void setCSR(String paramString)
  {
    this.tc = paramString;
    StringTokenizer localStringTokenizer = null;
    localStringTokenizer = new StringTokenizer(paramString, ",");
    if (localStringTokenizer.hasMoreTokens()) {
      this.tg = localStringTokenizer.nextToken();
    }
    if (localStringTokenizer.hasMoreTokens()) {
      this.s6 = localStringTokenizer.nextToken();
    }
  }
  
  public String getCSRName()
  {
    return this.s6;
  }
  
  public String getCSRId()
  {
    return this.tg;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetRptPerformance
 * JD-Core Version:    0.7.0.1
 */