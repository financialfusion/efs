package com.ffusion.tasks.applications;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.beans.applications.Product;
import com.ffusion.beans.applications.Products;
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

public class GetRptFacilityPerformance
  extends BaseTask
  implements Task
{
  private static final int uE = 6;
  private int uA = 0;
  private String uC = "";
  private String uB = "";
  private String uD = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    int i = 0;
    float f1 = 0.0F;
    float f2 = 0.0F;
    try
    {
      i = Integer.parseInt(this.uC);
      f1 = Float.parseFloat(this.uB);
      f2 = Float.parseFloat(this.uD);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.error = 47;
      return this.taskErrorURL;
    }
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
    localDateTime2.add(6, -1);
    for (int j = 0; j < this.uA; j++)
    {
      localDateTime1.add(3, -1);
      localDateTime2.add(3, -1);
    }
    for (j = 0; j < 6; j++) {
      localDateTime1.add(3, -1);
    }
    Iterator localIterator = localApplicationHistories1.iterator();
    Object localObject3;
    while (localIterator.hasNext())
    {
      localObject1 = (ApplicationHistory)localIterator.next();
      localObject2 = ((ApplicationHistory)localObject1).getModifiedDate();
      localObject3 = ((ApplicationHistory)localObject1).getStatusID();
      if (((((String)localObject3).equals("2")) || (((String)localObject3).equals("3"))) && ((((DateTime)localObject2).before(localDateTime1)) || (((DateTime)localObject2).after(localDateTime2)))) {
        localIterator.remove();
      }
    }
    Object localObject1 = new PerformanceRpts();
    ((PerformanceRpts)localObject1).setReportDate(localDateTime2);
    ((PerformanceRpts)localObject1).setClosedCounterGoal(i);
    ((PerformanceRpts)localObject1).setAvgTimeToHandleGoal(f1);
    ((PerformanceRpts)localObject1).setPctAppDeniedGoal(f2);
    Object localObject2 = localProducts.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Product)((Iterator)localObject2).next();
      String str2 = ((Product)localObject3).getProductID();
      String str3 = ((Product)localObject3).getTitle();
      PerformanceRpt localPerformanceRpt = ((PerformanceRpts)localObject1).create(str2, str3);
      localApplicationHistories1.setFilter("PRODUCT_ID=" + str2);
      localApplicationHistories1.setFilterSortedBy("APP_ID");
      ApplicationHistories localApplicationHistories2 = jdMethod_new(localApplicationHistories1);
      int k = 0;
      ApplicationHistories localApplicationHistories3 = new ApplicationHistories(localLocale);
      while (k < localApplicationHistories2.size())
      {
        k = jdMethod_new(localApplicationHistories3, localApplicationHistories2, k);
        localApplicationHistories3.setSortedBy("MODIFIED_DATE");
        ApplicationHistory localApplicationHistory = (ApplicationHistory)localApplicationHistories3.get(0);
        DateTime localDateTime3 = localApplicationHistory.getModifiedDate();
        localApplicationHistory = (ApplicationHistory)localApplicationHistories3.get(localApplicationHistories3.size() - 1);
        String str4 = localApplicationHistory.getStatusID();
        if ((str4.equals("2")) || (str4.equals("3")))
        {
          float f3 = 0.0F;
          DateTime localDateTime4 = localApplicationHistory.getModifiedDate();
          if ((localDateTime4 != null) && (localDateTime3 != null)) {
            f3 = localDateTime4.getDiff(localDateTime3, 2);
          }
          float f4 = localDateTime2.getDiff(localDateTime4, 3);
          int m = 0;
          if (str4.equals("3")) {
            m = 1;
          }
          localPerformanceRpt.RegisterPerformance(f4, f3, m, true);
        }
      }
    }
    localHttpSession.setAttribute("PerformanceReports", localObject1);
    localApplicationHistories1.setFilter("All");
    return str1;
  }
  
  private int jdMethod_new(ApplicationHistories paramApplicationHistories1, ApplicationHistories paramApplicationHistories2, int paramInt)
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
  
  private ApplicationHistories jdMethod_new(ApplicationHistories paramApplicationHistories)
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
  
  public void setWeeksToRoll(String paramString)
  {
    this.uA = Integer.valueOf(paramString).intValue();
  }
  
  public void setNumAppProcessedGoal(String paramString)
  {
    if (paramString.equals("")) {
      this.uC = "0";
    } else {
      this.uC = paramString;
    }
  }
  
  public void setAveProcessTimeGoal(String paramString)
  {
    if (paramString.equals("")) {
      this.uB = "0";
    } else {
      this.uB = paramString;
    }
  }
  
  public void setPercentDeniedGoal(String paramString)
  {
    if (paramString.equals("")) {
      this.uD = "0";
    } else {
      this.uD = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetRptFacilityPerformance
 * JD-Core Version:    0.7.0.1
 */