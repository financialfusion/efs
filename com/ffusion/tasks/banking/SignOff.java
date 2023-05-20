package com.ffusion.tasks.banking;

import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    paramHttpSession.removeAttribute("com.ffusion.services.Banking");
    paramHttpSession.removeAttribute("LiveBankingService");
    paramHttpSession.removeAttribute("Account");
    paramHttpSession.removeAttribute("BankingAccounts");
    paramHttpSession.removeAttribute("Statement");
    paramHttpSession.removeAttribute("Transaction");
    paramHttpSession.removeAttribute("Transactions");
    paramHttpSession.removeAttribute("Transfer");
    paramHttpSession.removeAttribute("Transfers");
    paramHttpSession.removeAttribute("RecTransfer");
    paramHttpSession.removeAttribute("RecTransfers");
    paramHttpSession.removeAttribute("AccountHistories");
    paramHttpSession.removeAttribute("AccountSummaries");
    paramHttpSession.removeAttribute("ExtAccountSummaries");
    paramHttpSession.removeAttribute("BalanceSummmariesByGroup");
    paramHttpSession.removeAttribute("TransferReport");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SignOff
 * JD-Core Version:    0.7.0.1
 */