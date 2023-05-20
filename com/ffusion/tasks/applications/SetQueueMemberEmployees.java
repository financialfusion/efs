package com.ffusion.tasks.applications;

import com.ffusion.beans.applications.Product;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.messages.MessageQueue;
import com.ffusion.beans.messages.MessageQueueMembers;
import com.ffusion.beans.messages.MessageQueues;
import com.ffusion.beans.util.StringList;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetQueueMemberEmployees
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      MessageQueues localMessageQueues = (MessageQueues)localHttpSession.getAttribute("MessageQueues");
      if (localMessageQueues == null)
      {
        this.error = 8001;
        return this.taskErrorURL;
      }
      BankEmployees localBankEmployees = (BankEmployees)localHttpSession.getAttribute("BankEmployees");
      localBankEmployees.setFilter("All");
      if (localBankEmployees == null)
      {
        this.error = 5503;
        return this.taskErrorURL;
      }
      StringList localStringList = new StringList();
      HashMap localHashMap = new HashMap();
      Product localProduct = (Product)localHttpSession.getAttribute("Product");
      if (localProduct == null)
      {
        this.error = 7252;
        return this.taskErrorURL;
      }
      localMessageQueues.setFilter("PRODUCT_ID=" + localProduct.getProductID());
      Iterator localIterator1 = localMessageQueues.iterator();
      while (localIterator1.hasNext())
      {
        MessageQueue localMessageQueue = (MessageQueue)localIterator1.next();
        MessageQueueMembers localMessageQueueMembers = localMessageQueue.getActiveQueueMembers();
        Iterator localIterator2 = localMessageQueueMembers.iterator();
        while (localIterator2.hasNext())
        {
          BankEmployee localBankEmployee1 = (BankEmployee)localIterator2.next();
          String str = localBankEmployee1.getId();
          BankEmployee localBankEmployee2 = localBankEmployees.getByID(str);
          if ((localBankEmployee2 != null) && (localHashMap.get(localBankEmployee2.getName()) == null))
          {
            localHashMap.put(localBankEmployee2.getName(), "");
            if (localBankEmployee2.getName() != null) {
              localStringList.add(localBankEmployee2.getName());
            } else {
              localStringList.add("No Name for Employee.  Employee ID = " + localBankEmployee2.getEmployeeId());
            }
          }
        }
      }
      localHttpSession.setAttribute("QueueMemberEmployees", localStringList);
    }
    catch (Exception localException)
    {
      this.error = 7323;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.SetQueueMemberEmployees
 * JD-Core Version:    0.7.0.1
 */