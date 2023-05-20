package com.ffusion.tasks.init;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.User;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.accounts.MergeAccounts;
import com.ffusion.tasks.accounts.SetAccount;
import com.ffusion.tasks.billpay.FindPayments;
import com.ffusion.tasks.billpay.GetExtPayees;
import com.ffusion.tasks.billpay.GetPayments;
import com.ffusion.tasks.billpay.GetRecPayments;
import com.ffusion.tasks.billpay.UpdateService;
import com.ffusion.tasks.messages.GetMessageQueues;
import com.ffusion.tasks.register.SetRegisterAccountsData;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginUser
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    b localb = new b(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse, Thread.currentThread());
    localb.start();
    a locala = new a(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse, Thread.currentThread());
    locala.start();
    String str = null;
    long l = System.currentTimeMillis();
    int i = 1;
    while (i != 0) {
      try
      {
        if ((!localb.isAlive()) && (!locala.isAlive()))
        {
          if (localb.jdField_if != 0)
          {
            this.error = localb.jdField_if;
            str = localb.a;
            i = 0;
          }
          else if (locala.jdField_if != 0)
          {
            this.error = locala.jdField_if;
            str = locala.a;
            i = 0;
          }
          break;
        }
        Thread.sleep(500L);
      }
      catch (InterruptedException localInterruptedException) {}
    }
    return str;
  }
  
  private class a
    extends Thread
  {
    Thread jdField_for = null;
    HttpServlet jdField_new = null;
    HttpServletRequest jdField_int = null;
    HttpServletResponse jdField_do = null;
    int jdField_if = 0;
    public String a = null;
    
    a(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Thread paramThread)
    {
      this.jdField_new = paramHttpServlet;
      this.jdField_int = paramHttpServletRequest;
      this.jdField_do = paramHttpServletResponse;
      this.jdField_for = paramThread;
    }
    
    public void run()
    {
      HttpSession localHttpSession = this.jdField_int.getSession();
      try
      {
        User localUser = (User)localHttpSession.getAttribute("User");
        com.ffusion.tasks.messages.SignOn localSignOn = (com.ffusion.tasks.messages.SignOn)localHttpSession.getAttribute("SignOnMessageCenter");
        localSignOn.setUserName(localUser.getId() + ",CUSTOMER" + localUser.getName());
        this.a = localSignOn.process(this.jdField_new, this.jdField_int, this.jdField_do);
        this.jdField_if = Integer.valueOf(localSignOn.getError()).intValue();
        localHttpSession.setAttribute("SignOnMessageCenter", localSignOn);
        GetMessageQueues localGetMessageQueues = new GetMessageQueues();
        if (this.jdField_if == 0)
        {
          this.a = localGetMessageQueues.process(this.jdField_new, this.jdField_int, this.jdField_do);
          this.jdField_if = Integer.valueOf(localGetMessageQueues.getError()).intValue();
        }
        Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BankingAccounts");
        localAccounts.setFilter("All");
        localHttpSession.setAttribute("BankingAccounts", localAccounts);
        if (this.jdField_if == 0)
        {
          GetExtPayees localGetExtPayees = new GetExtPayees();
          if (this.jdField_if == 0)
          {
            localGetExtPayees.process(this.jdField_new, this.jdField_int, this.jdField_do);
            this.jdField_if = Integer.valueOf(localGetExtPayees.getError()).intValue();
            localHttpSession.setAttribute("GetPayees", localGetExtPayees);
            GetPayments localGetPayments = new GetPayments();
            if (this.jdField_if == 0)
            {
              this.a = localGetPayments.process(this.jdField_new, this.jdField_int, this.jdField_do);
              this.jdField_if = Integer.valueOf(localGetPayments.getError()).intValue();
              localHttpSession.setAttribute("GetPayments", localGetPayments);
              GetRecPayments localGetRecPayments = new GetRecPayments();
              if (this.jdField_if == 0)
              {
                this.a = localGetRecPayments.process(this.jdField_new, this.jdField_int, this.jdField_do);
                this.jdField_if = Integer.valueOf(localGetRecPayments.getError()).intValue();
                localHttpSession.setAttribute("GetRecPayments", localGetRecPayments);
                FindPayments localFindPayments = new FindPayments();
                if (this.jdField_if == 0)
                {
                  this.a = localFindPayments.process(this.jdField_new, this.jdField_int, this.jdField_do);
                  this.jdField_if = Integer.valueOf(localFindPayments.getError()).intValue();
                  localHttpSession.setAttribute("FindPayments", localFindPayments);
                  SetAccount localSetAccount = new SetAccount();
                  if (this.jdField_if == 0)
                  {
                    localSetAccount.setAccountsName("BankingAccounts");
                    localSetAccount.setAccountName("Account");
                    localObject = (Account)localAccounts.get(0);
                    localSetAccount.setID(((Account)localObject).getID());
                    this.a = localSetAccount.process(this.jdField_new, this.jdField_int, this.jdField_do);
                    this.jdField_if = Integer.valueOf(localSetAccount.getError()).intValue();
                    localHttpSession.setAttribute("SetAccount", localSetAccount);
                  }
                  Object localObject = (Accounts)localHttpSession.getAttribute("BillPayAccounts");
                  ((Accounts)localObject).setFilter("All");
                  localHttpSession.setAttribute("BillPayAccounts", localObject);
                }
              }
            }
          }
        }
      }
      catch (Exception localException) {}
      this.jdField_for.interrupt();
    }
  }
  
  private class b
    extends Thread
  {
    Thread jdField_for = null;
    HttpServlet jdField_new = null;
    HttpServletRequest jdField_int = null;
    HttpServletResponse jdField_do = null;
    public int jdField_if = 0;
    public String a = null;
    
    b(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Thread paramThread)
    {
      this.jdField_new = paramHttpServlet;
      this.jdField_int = paramHttpServletRequest;
      this.jdField_do = paramHttpServletResponse;
      this.jdField_for = paramThread;
    }
    
    public void run()
    {
      HttpSession localHttpSession = this.jdField_int.getSession();
      try
      {
        com.ffusion.tasks.accounts.GetAccounts localGetAccounts1 = new com.ffusion.tasks.accounts.GetAccounts();
        localGetAccounts1.setServiceName("com.ffusion.services.Banking");
        localGetAccounts1.setAccountsName("BankingAccounts");
        this.a = localGetAccounts1.process(this.jdField_new, this.jdField_int, this.jdField_do);
        this.jdField_if = Integer.valueOf(localGetAccounts1.getError()).intValue();
        localHttpSession.setAttribute("GetAccounts", localGetAccounts1);
        User localUser = (User)localHttpSession.getAttribute("User");
        com.ffusion.tasks.accounts.GetAccounts localGetAccounts2 = new com.ffusion.tasks.accounts.GetAccounts();
        if (this.jdField_if == 0)
        {
          localGetAccounts2.setServiceName("com.ffusion.service.AccountService");
          localGetAccounts2.setAccountsName("Accounts");
          this.a = localGetAccounts2.process(this.jdField_new, this.jdField_int, this.jdField_do);
          this.jdField_if = Integer.valueOf(localGetAccounts2.getError()).intValue();
          localHttpSession.setAttribute("GetDirectoryAccounts", localGetAccounts2);
          MergeAccounts localMergeAccounts = new MergeAccounts();
          if (this.jdField_if == 0)
          {
            localMergeAccounts.setPrimaryAccountsName("BankingAccounts");
            localMergeAccounts.setSecondaryAccountsName("Accounts");
            this.a = localMergeAccounts.process(this.jdField_new, this.jdField_int, this.jdField_do);
            this.jdField_if = Integer.valueOf(localMergeAccounts.getError()).intValue();
            localHttpSession.setAttribute("MergeAccounts", localMergeAccounts);
            SetRegisterAccountsData localSetRegisterAccountsData = new SetRegisterAccountsData();
            if (this.jdField_if == 0)
            {
              localSetRegisterAccountsData.setOverlapDays("0");
              localHttpSession.setAttribute("SetRegisterAccountsData", localSetRegisterAccountsData);
            }
            Accounts localAccounts = (Accounts)localHttpSession.getAttribute("BankingAccounts");
            localAccounts.setFilter("All");
            localHttpSession.setAttribute("BankingAccounts", localAccounts);
            com.ffusion.tasks.billpay.SignOn localSignOn = new com.ffusion.tasks.billpay.SignOn();
            localSignOn.setServiceName("DirectoryBPService");
            localSignOn.setUserName(localUser.getId());
            localSignOn.setPassword(localUser.getId());
            this.a = localSignOn.process(this.jdField_new, this.jdField_int, this.jdField_do);
            this.jdField_if = Integer.valueOf(localSignOn.getError()).intValue();
            localHttpSession.setAttribute("DirectoryBPSignOn", localSignOn);
            UpdateService localUpdateService = new UpdateService();
            if (this.jdField_if == 0)
            {
              localUpdateService.setUserName((String)localHttpSession.getAttribute("TransID"));
              localUpdateService.setPassword((String)localHttpSession.getAttribute("TransPassword"));
              this.a = localUpdateService.process(this.jdField_new, this.jdField_int, this.jdField_do);
              this.jdField_if = Integer.valueOf(localUpdateService.getError()).intValue();
              localHttpSession.setAttribute("BillPayUpdateService", localUpdateService);
              com.ffusion.tasks.billpay.GetAccounts localGetAccounts = new com.ffusion.tasks.billpay.GetAccounts();
              if (this.jdField_if == 0)
              {
                localGetAccounts.setUseAccounts("BankingAccounts");
                this.a = localGetAccounts.process(this.jdField_new, this.jdField_int, this.jdField_do);
                this.jdField_if = Integer.valueOf(localGetAccounts.getError()).intValue();
                localHttpSession.setAttribute("GetBillPayAccounts", localGetAccounts);
              }
            }
          }
        }
      }
      catch (Exception localException) {}
      this.jdField_for.interrupt();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.init.LoginUser
 * JD-Core Version:    0.7.0.1
 */