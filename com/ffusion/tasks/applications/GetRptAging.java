package com.ffusion.tasks.applications;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
import com.ffusion.beans.messages.FacilityAgingQueue;
import com.ffusion.beans.messages.FacilityAgingQueues;
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
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    Products localProducts = (Products)localHttpSession.getAttribute("Products");
    if (localProducts == null)
    {
      this.error = 7250;
      return this.taskErrorURL;
    }
    ApplicationHistories localApplicationHistories1 = (ApplicationHistories)localHttpSession.getAttribute("ApplicationHistory");
    if (localApplicationHistories1 == null)
    {
      this.error = 7381;
      return this.taskErrorURL;
    }
    DateTime localDateTime1 = new DateTime((Locale)localHttpSession.getAttribute("java.util.Locale"));
    FacilityAgingQueues localFacilityAgingQueues = new FacilityAgingQueues();
    localFacilityAgingQueues.setReportDate(localDateTime1);
    Iterator localIterator = localProducts.iterator();
    while (localIterator.hasNext())
    {
      Product localProduct = (Product)localIterator.next();
      String str2 = localProduct.getProductID();
      String str3 = localProduct.getTitle();
      FacilityAgingQueue localFacilityAgingQueue = new FacilityAgingQueue(str2, str3);
      localFacilityAgingQueues.add(localFacilityAgingQueue);
      localApplicationHistories1.setFilter("PRODUCT_ID=" + str2);
      localApplicationHistories1.setFilterSortedBy("APP_ID");
      int i = 0;
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      ApplicationHistories localApplicationHistories2 = new ApplicationHistories(localLocale);
      int j = Integer.parseInt(localApplicationHistories1.getSize());
      while (i < j)
      {
        i = jdMethod_for(localLocale, i, localApplicationHistories2, localApplicationHistories1);
        localApplicationHistories2.setSortedBy("MODIFIED_DATE");
        ApplicationHistory localApplicationHistory = (ApplicationHistory)localApplicationHistories2.get(0);
        DateTime localDateTime2 = localApplicationHistory.getModifiedDate();
        localApplicationHistory = (ApplicationHistory)localApplicationHistories2.get(localApplicationHistories2.size() - 1);
        int k = 0;
        String str4 = localApplicationHistory.getStatusID();
        if ((str4.equals("2")) || (str4.equals("3")) || (str4.equals("5"))) {
          k = 1;
        }
        if (k == 0)
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
    localApplicationHistories1.setFilter("All");
    return str1;
  }
  
  private int jdMethod_for(Locale paramLocale, int paramInt, ApplicationHistories paramApplicationHistories1, ApplicationHistories paramApplicationHistories2)
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetRptAging
 * JD-Core Version:    0.7.0.1
 */