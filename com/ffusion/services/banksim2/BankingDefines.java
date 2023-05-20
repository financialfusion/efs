package com.ffusion.services.banksim2;

import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumTransactionEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumXferStatusEnum;

public abstract interface BankingDefines
  extends Defines
{
  public static final int[] transMap = { 4, 5, 11, 18, 10, 23, 1, 7, 9, 16, 3, 25, 28, 29, 12, 13, 30 };
  public static final int[] xferStatMap = { 3, 6, 7, 5, 2 };
  public static final EnumTransactionEnum[] transType = { EnumTransactionEnum.CREDIT, EnumTransactionEnum.DEBIT, EnumTransactionEnum.INT, EnumTransactionEnum.DIV, EnumTransactionEnum.FEE, EnumTransactionEnum.SRVCHG, EnumTransactionEnum.DEP, EnumTransactionEnum.ATM, EnumTransactionEnum.POS, EnumTransactionEnum.XFER, EnumTransactionEnum.CHECK, EnumTransactionEnum.PAYMENT, EnumTransactionEnum.CASH, EnumTransactionEnum.DIRECTDEP, EnumTransactionEnum.DIRECTDEBIT, EnumTransactionEnum.REPEATPMT, EnumTransactionEnum.OTHER };
  public static final EnumXferStatusEnum[] xferStatTypes = { EnumXferStatusEnum.CANCELEDON, EnumXferStatusEnum.FAILEDON, EnumXferStatusEnum.NOFUNDSON, EnumXferStatusEnum.POSTEDON, EnumXferStatusEnum.WILLPROCESSON };
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.BankingDefines
 * JD-Core Version:    0.7.0.1
 */