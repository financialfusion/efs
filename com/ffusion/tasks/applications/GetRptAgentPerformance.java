package com.ffusion.tasks.applications;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.messages.PerformanceRpt;
import com.ffusion.beans.messages.PerformanceRpts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRptAgentPerformance
  extends BaseTask
  implements Task, com.ffusion.tasks.messages.Task
{
  private int fj = 0;
  private String fn = "";
  private String fo = "";
  private String fm = "";
  private String fl = "";
  private String fk = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    int i = 0;
    float f1 = 0.0F;
    try
    {
      i = Integer.parseInt(this.fm);
      f1 = Float.parseFloat(this.fl);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 47;
      return this.taskErrorURL;
    }
    MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute("AppQueueAssignmentsActive");
    if (localMessageQueues == null)
    {
      this.error = 8020;
      return this.taskErrorURL;
    }
    ApplicationHistories localApplicationHistories1 = (ApplicationHistories)localHttpSession.getAttribute("ApplicationHistory");
    if (localApplicationHistories1 == null)
    {
      this.error = 7381;
      return this.taskErrorURL;
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    DateTime localDateTime1 = new DateTime(localLocale);
    localDateTime1.set(11, 0);
    localDateTime1.set(12, 0);
    localDateTime1.set(13, 0);
    DateTime localDateTime2 = new DateTime(localLocale);
    localDateTime2.set(11, 23);
    localDateTime2.set(12, 59);
    localDateTime2.set(13, 59);
    while (1 != localDateTime2.get(7))
    {
      localDateTime2.add(6, -1);
      localDateTime1.add(6, -1);
    }
    for (int j = 0; j < this.fj; j++)
    {
      localDateTime1.add(3, -1);
      localDateTime2.add(3, -1);
    }
    localDateTime1.add(3, -1);
    localDateTime2.add(6, -1);
    Iterator localIterator1 = localApplicationHistories1.iterator();
    Object localObject3;
    String str2;
    while (localIterator1.hasNext())
    {
      localObject1 = (ApplicationHistory)localIterator1.next();
      localObject2 = ((ApplicationHistory)localObject1).getModifiedDate();
      localObject3 = ((ApplicationHistory)localObject1).getStatusID();
      str2 = ((ApplicationHistory)localObject1).getAppID();
      if (((String)localObject3).equals("5")) {
        localIterator1.remove();
      } else if (((((String)localObject3).equals("2")) || (((String)localObject3).equals("3"))) && ((((DateTime)localObject2).before(localDateTime1)) || (((DateTime)localObject2).after(localDateTime2)))) {
        localIterator1.remove();
      }
    }
    Object localObject1 = new PerformanceRpts();
    ((PerformanceRpts)localObject1).setReportDate(localDateTime2);
    ((PerformanceRpts)localObject1).setAgentName(this.fo);
    ((PerformanceRpts)localObject1).setClosedCounterGoal(i);
    ((PerformanceRpts)localObject1).setAvgTimeToHandleGoal(f1);
    Object localObject2 = localMessageQueues.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (MessageQueue)((Iterator)localObject2).next();
      str2 = ((MessageQueue)localObject3).getQueueID();
      String str3 = ((MessageQueue)localObject3).getQueueProductDesc();
      String str4 = ((MessageQueue)localObject3).getQueueProductID();
      String str5 = ((MessageQueue)localObject3).getQueueStatusName();
      String str6 = ((MessageQueue)localObject3).getQueueStatusID();
      if (!str6.equals("5"))
      {
        PerformanceRpt localPerformanceRpt = ((PerformanceRpts)localObject1).create(str2, str3 + "-" + str5);
        localApplicationHistories1.setFilter("PRODUCT_ID=" + str4);
        localApplicationHistories1.setFilterSortedBy("APP_ID");
        ApplicationHistories localApplicationHistories2 = jdMethod_for(localApplicationHistories1);
        int k = 0;
        ApplicationHistories localApplicationHistories3 = new ApplicationHistories(localLocale);
        while (k < localApplicationHistories2.size())
        {
          k = jdMethod_for(localApplicationHistories3, localApplicationHistories2, k);
          localApplicationHistories3.setSortedBy("MODIFIED_DATE");
          DateTime localDateTime3 = null;
          float f2 = 0.0F;
          ApplicationHistory localApplicationHistory1 = null;
          ApplicationHistory localApplicationHistory2 = null;
          ApplicationHistory localApplicationHistory3 = null;
          Iterator localIterator2 = localApplicationHistories3.iterator();
          while (localIterator2.hasNext())
          {
            localApplicationHistory2 = localApplicationHistory1;
            localApplicationHistory1 = (ApplicationHistory)localIterator2.next();
            String str7 = localApplicationHistory1.getStatusID();
            String str8 = localApplicationHistory1.getOwnerID();
            if (str7.equals(str6))
            {
              if (localApplicationHistory3 == null)
              {
                localApplicationHistory3 = localApplicationHistory1;
              }
              else if (!str8.equals(localApplicationHistory2.getOwnerID()))
              {
                localApplicationHistory3 = null;
                localDateTime3 = null;
              }
            }
            else if ((localApplicationHistory3 != null) && (str8.equals(this.fk)))
            {
              localDateTime3 = localApplicationHistory1.getModifiedDate();
              f2 += localDateTime3.getDiff(localApplicationHistory3.getModifiedDate(), 2);
              localApplicationHistory3 = null;
            }
          }
          if ((localDateTime3 != null) && (localApplicationHistory3 == null))
          {
            float f3 = localDateTime2.getDiff(localDateTime3, 3);
            localPerformanceRpt.RegisterPerformance(f3, f2, 0, true);
          }
        }
        localHttpSession.setAttribute("PerformanceReports", localObject1);
        localApplicationHistories1.setFilter("All");
      }
    }
    return str1;
  }
  
  private boolean jdMethod_for(ApplicationHistory paramApplicationHistory1, ApplicationHistory paramApplicationHistory2)
  {
    return !paramApplicationHistory1.getOwnerID().equals(paramApplicationHistory2.getOwnerID());
  }
  
  private int jdMethod_for(ApplicationHistories paramApplicationHistories1, ApplicationHistories paramApplicationHistories2, int paramInt)
  {
    paramApplicationHistories1.clear();
    ApplicationHistory localApplicationHistory1 = (ApplicationHistory)paramApplicationHistories2.get(paramInt);
    String str1 = localApplicationHistory1.getAppID();
    String str2 = null;
    do
    {
      ApplicationHistory localApplicationHistory2 = paramApplicationHistories1.add();
      localApplicationHistory2.set(localApplicationHistory1);
      paramInt++;
      if (paramInt == paramApplicationHistories2.size()) {
        return paramInt;
      }
      localApplicationHistory1 = (ApplicationHistory)paramApplicationHistories2.get(paramInt);
      str2 = localApplicationHistory1.getAppID();
    } while (str1.equals(str2));
    return paramInt;
  }
  
  private ApplicationHistories jdMethod_for(ApplicationHistories paramApplicationHistories)
  {
    Locale localLocale = paramApplicationHistories.getLocale();
    ApplicationHistories localApplicationHistories = new ApplicationHistories(localLocale);
    Iterator localIterator = paramApplicationHistories.iterator();
    while (localIterator.hasNext())
    {
      ApplicationHistory localApplicationHistory = (ApplicationHistory)localIterator.next();
      localApplicationHistories.add(localApplicationHistory);
    }
    return localApplicationHistories;
  }
  
  public void setAgentID(String paramString)
  {
    int i = paramString.indexOf(",");
    this.fn = paramString.substring(0, i);
    this.fo = paramString.substring(i + 1, paramString.length());
  }
  
  public void setNumAppProcessedGoal(String paramString)
  {
    if (paramString.equals("")) {
      this.fm = "0";
    } else {
      this.fm = paramString;
    }
  }
  
  public void setAveProcessTimeGoal(String paramString)
  {
    if (paramString.equals("")) {
      this.fl = "0";
    } else {
      this.fl = paramString;
    }
  }
  
  public void setWeeksToRoll(String paramString)
  {
    this.fj = Integer.valueOf(paramString).intValue();
  }
  
  public void setEmployeeID(String paramString)
  {
    this.fk = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetRptAgentPerformance
 * JD-Core Version:    0.7.0.1
 */