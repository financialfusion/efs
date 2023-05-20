package com.ffusion.beans.messages;

import com.ffusion.beans.ExtendABean;

public class BankMessageInfo
  extends ExtendABean
{
  private String aWN;
  private String aWL;
  private String aWO;
  private String aWM;
  
  public void setBankName(String paramString)
  {
    this.aWN = paramString;
  }
  
  public String getBankName()
  {
    return this.aWN;
  }
  
  public void setNotifyFromEmail(String paramString)
  {
    this.aWL = paramString;
  }
  
  public String getNotifyFromEmail()
  {
    return this.aWL;
  }
  
  public void setNotifyMemo(String paramString)
  {
    this.aWO = paramString;
  }
  
  public String getNotifyMemo()
  {
    return this.aWO;
  }
  
  public void setNotifySubject(String paramString)
  {
    this.aWM = paramString;
  }
  
  public String getNotifySubject()
  {
    return this.aWM;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.BankMessageInfo
 * JD-Core Version:    0.7.0.1
 */