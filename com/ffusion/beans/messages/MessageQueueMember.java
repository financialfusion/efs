package com.ffusion.beans.messages;

import com.ffusion.beans.bankemployee.BankEmployee;

public class MessageQueueMember
  extends BankEmployee
{
  private String oH;
  
  public MessageQueueMember()
  {
    this.oH = String.valueOf(0);
  }
  
  public MessageQueueMember(BankEmployee paramBankEmployee)
  {
    set(paramBankEmployee);
  }
  
  public void set(BankEmployee paramBankEmployee)
  {
    super.set(paramBankEmployee);
    this.oH = String.valueOf(0);
  }
  
  public void set(MessageQueueMember paramMessageQueueMember)
  {
    super.set(paramMessageQueueMember);
    this.oH = paramMessageQueueMember.getQueueEmpStatus();
  }
  
  public String getQueueEmpStatus()
  {
    return this.oH;
  }
  
  public void setQueueEmpStatus(String paramString)
  {
    this.oH = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageQueueMember
 * JD-Core Version:    0.7.0.1
 */