package com.ffusion.tasks.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.messages.FacilityAgingQueue;
import com.ffusion.beans.messages.FacilityAgingQueues;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.MessageThread;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRptAging
  extends BaseTask
  implements Task
{
  public static final String FACILITY_AGING_QUEUES = "FacilityAgingQueues";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
    if (localMessageQueues == null)
    {
      this.error = 8020;
      return this.taskErrorURL;
    }
    MessageThreads localMessageThreads = (MessageThreads)localHttpSession.getAttribute("MessageThreads");
    if (localMessageThreads == null)
    {
      this.error = 8025;
      return this.taskErrorURL;
    }
    DateTime localDateTime1 = new DateTime((Locale)localHttpSession.getAttribute("java.util.Locale"));
    FacilityAgingQueues localFacilityAgingQueues = new FacilityAgingQueues();
    localFacilityAgingQueues.setReportDate(localDateTime1);
    localMessageQueues.setFilter("QueueType=0");
    Iterator localIterator1 = localMessageQueues.iterator();
    while (localIterator1.hasNext())
    {
      MessageQueue localMessageQueue = (MessageQueue)localIterator1.next();
      String str2 = localMessageQueue.getQueueID();
      String str3 = localMessageQueue.getQueueName();
      FacilityAgingQueue localFacilityAgingQueue = new FacilityAgingQueue(str2, str3);
      localFacilityAgingQueues.add(localFacilityAgingQueue);
      localMessageThreads.setFilter("QueueID=" + str2);
      Iterator localIterator2 = localMessageThreads.iterator();
      while (localIterator2.hasNext())
      {
        MessageThread localMessageThread = (MessageThread)localIterator2.next();
        DateTime localDateTime2 = localMessageThread.getCreateDateValue();
        DateTime localDateTime3 = localMessageThread.getClosedDateValue();
        if ((str3 != null) && (localDateTime3 == null))
        {
          float f = localDateTime1.getDiff(localDateTime2, 3);
          if (f < 1.0F)
          {
            localFacilityAgingQueue.setUnderOneDayThread(Integer.parseInt(localFacilityAgingQueue.getUnderOneDayThread()) + 1);
            localFacilityAgingQueues.setUnderOneDayQueueTotal(Integer.parseInt(localFacilityAgingQueues.getUnderOneDayQueueTotal()) + 1);
          }
          else if ((f >= 1.0F) && (f <= 2.0F))
          {
            localFacilityAgingQueue.setOne_TwoDayThread(Integer.parseInt(localFacilityAgingQueue.getOne_TwoDayThread()) + 1);
            localFacilityAgingQueues.setOne_TwoDayQueueTotal(Integer.parseInt(localFacilityAgingQueues.getOne_TwoDayQueueTotal()) + 1);
          }
          else if ((f > 2.0F) && (f <= 4.0F))
          {
            localFacilityAgingQueue.setTwo_FourDayThread(Integer.parseInt(localFacilityAgingQueue.getTwo_fourDayThread()) + 1);
            localFacilityAgingQueues.setTwo_FourDayQueueTotal(Integer.parseInt(localFacilityAgingQueues.getTwo_FourDayQueueTotal()) + 1);
          }
          else if ((f > 4.0F) && (f <= 7.0F))
          {
            localFacilityAgingQueue.setFive_SevenDayThread(Integer.parseInt(localFacilityAgingQueue.getFive_SevenDayThread()) + 1);
            localFacilityAgingQueues.setFive_SevenDayQueueTotal(Integer.parseInt(localFacilityAgingQueues.getFive_SevenDayQueueTotal()) + 1);
          }
          else
          {
            localFacilityAgingQueue.setOverSevenDayThread(Integer.parseInt(localFacilityAgingQueue.getOverSevenDayThread()) + 1);
            localFacilityAgingQueues.setOverSevenDayQueueTotal(Integer.parseInt(localFacilityAgingQueues.getOverSevenDayQueueTotal()) + 1);
          }
        }
      }
    }
    localHttpSession.setAttribute("FacilityAgingQueues", localFacilityAgingQueues);
    localMessageQueues.setFilter("All");
    localMessageThreads.setFilter("All");
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.messages.GetRptAging
 * JD-Core Version:    0.7.0.1
 */