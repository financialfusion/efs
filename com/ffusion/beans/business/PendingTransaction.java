package com.ffusion.beans.business;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.approvals.ApprovalsItem;
import com.ffusion.beans.tw.TWTransaction;
import com.ffusion.beans.user.BusinessEmployee;

public class PendingTransaction
  extends ExtendABean
{
  private Business aVN;
  private BusinessEmployee aVP;
  private ApprovalsItem aVO;
  private TWTransaction aVM;
  
  public PendingTransaction(ApprovalsItem paramApprovalsItem, Business paramBusiness, BusinessEmployee paramBusinessEmployee)
  {
    this.aVO = paramApprovalsItem;
    this.aVM = ((TWTransaction)paramApprovalsItem.getItem());
    this.aVN = paramBusiness;
    this.aVP = paramBusinessEmployee;
  }
  
  public String getItemID()
  {
    return String.valueOf(this.aVO.getItemID());
  }
  
  public int getItemIDValue()
  {
    return this.aVO.getItemID();
  }
  
  public String getItemType()
  {
    return String.valueOf(this.aVO.getItemType());
  }
  
  public int getItemTypeValue()
  {
    return this.aVO.getItemType();
  }
  
  public String getItemSubType()
  {
    return String.valueOf(this.aVO.getItemSubType());
  }
  
  public int getItemSubTypeValue()
  {
    return this.aVO.getItemSubType();
  }
  
  public String getSubmittingUserID()
  {
    return String.valueOf(this.aVO.getSubmittingUserID());
  }
  
  public int getSubmittingUserIDValue()
  {
    return this.aVO.getSubmittingUserID();
  }
  
  public String getTransactionType()
  {
    return this.aVM.getTypeAsString();
  }
  
  public int getTransactionTypeValue()
  {
    return this.aVM.getTransactionType();
  }
  
  public String getTransactionAmount()
  {
    Currency localCurrency = this.aVM.getAmount();
    return localCurrency.toString();
  }
  
  public String getTransactionDate()
  {
    DateTime localDateTime = this.aVM.getSubmissionDateTime();
    return localDateTime.toString();
  }
  
  public String getBusinessName()
  {
    if (this.aVN != null) {
      return this.aVN.getBusinessName();
    }
    return "";
  }
  
  public String getBusinessCIF()
  {
    if (this.aVN != null) {
      return this.aVN.getBusinessCIF();
    }
    return "";
  }
  
  public String getPersonalCIF()
  {
    if (this.aVN != null) {
      return this.aVN.getPersonalCIF();
    }
    return "";
  }
  
  public String getPrimaryContactName()
  {
    if (this.aVP != null) {
      return this.aVP.getName();
    }
    return null;
  }
  
  public String getPrimaryContactID()
  {
    if (this.aVP != null) {
      return this.aVP.getId();
    }
    return null;
  }
  
  public String getPrimaryContactEMail()
  {
    if (this.aVP != null) {
      return this.aVP.getEmail();
    }
    return null;
  }
  
  public ApprovalsItem getApprovalsItem()
  {
    return this.aVO;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("BusinessName=").append(getBusinessName());
    localStringBuffer.append(" BusinessCIF=").append(getBusinessCIF());
    localStringBuffer.append(" PersonalCIF=").append(getPersonalCIF());
    localStringBuffer.append(" PrimaryContactName=").append(getPrimaryContactName());
    localStringBuffer.append(" TransactionType=").append(getTransactionType());
    localStringBuffer.append(" TransactionAmount=").append(getTransactionAmount());
    localStringBuffer.append(" TransactionDate=").append(getTransactionDate());
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.PendingTransaction
 * JD-Core Version:    0.7.0.1
 */