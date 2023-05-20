package com.ffusion.services.FFServer.OFX200;

import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumAccountEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;

public abstract interface Defines
  extends com.ffusion.services.FFServer.Defines
{
  public static final int[] acctMap = { 1, 2, 7, 12, 4, 5, 6, 8, 9, 10, 11, 13, 3, 14, 15 };
  public static final int[] FreqMap = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
  public static final EnumAccountEnum[] acctTypes = { EnumAccountEnum.CHECKING, EnumAccountEnum.SAVINGS, EnumAccountEnum.CREDITLINE, EnumAccountEnum.MONEYMRKT, EnumAccountEnum.CREDITLINE, EnumAccountEnum.CREDITLINE, EnumAccountEnum.CREDITLINE, EnumAccountEnum.MONEYMRKT, EnumAccountEnum.MONEYMRKT, EnumAccountEnum.MONEYMRKT, EnumAccountEnum.MONEYMRKT, EnumAccountEnum.CREDITLINE, EnumAccountEnum.CREDITLINE, EnumAccountEnum.MONEYMRKT, EnumAccountEnum.MONEYMRKT };
  public static final EnumFreqEnum[] FreqEnums = { EnumFreqEnum.WEEKLY, EnumFreqEnum.BIWEEKLY, EnumFreqEnum.TWICEMONTHLY, EnumFreqEnum.MONTHLY, EnumFreqEnum.FOURWEEKS, EnumFreqEnum.BIMONTHLY, EnumFreqEnum.QUARTERLY, EnumFreqEnum.SEMIANNUALLY, EnumFreqEnum.ANUALLY };
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX200.Defines
 * JD-Core Version:    0.7.0.1
 */