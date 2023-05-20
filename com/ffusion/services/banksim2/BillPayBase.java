package com.ffusion.services.banksim2;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBillPayMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSubAddressCm;
import com.ffusion.util.DateFormatUtil;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class BillPayBase
  extends Base
{
  protected Payees payees;
  protected Payee payee;
  protected Payment payment;
  protected Payments payments;
  protected int defaultDaysToPay = 4;
  
  protected static TypeBillPayMsgsRqV1Aggregate formatBillPayMsgsRqV1(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate)
  {
    paramTypeOFXRqMsgSetsAggregate.BillPayMsgsRqV1Exists = true;
    paramTypeOFXRqMsgSetsAggregate.BillPayMsgsRqV1 = new TypeBillPayMsgsRqV1Aggregate();
    return paramTypeOFXRqMsgSetsAggregate.BillPayMsgsRqV1;
  }
  
  protected static void formatPmtInfo(TypePmtInfoAggregate paramTypePmtInfoAggregate, Account paramAccount, Payment paramPayment, int paramInt1, int paramInt2)
  {
    Payee localPayee = paramPayment.getPayee();
    String str2 = "";
    String str1;
    if ((localPayee != null) && (localPayee.getUserAccountNumber().length() > 0)) {
      str1 = localPayee.getUserAccountNumber();
    } else {
      str1 = "NA";
    }
    if (localPayee == null) {
      str2 = paramPayment.getID();
    } else if ((localPayee.getID() != null) && (localPayee.getID().length() > 0)) {
      str2 = localPayee.getID();
    }
    paramTypePmtInfoAggregate.BankAcctFrom = new TypeBankAcctFromAggregate();
    formatBANKACCTFROM(paramTypePmtInfoAggregate.BankAcctFrom, paramAccount);
    Double localDouble = new Double(paramPayment.getAmountValue().doubleValue());
    paramTypePmtInfoAggregate.TrnAmt = localDouble.doubleValue();
    paramTypePmtInfoAggregate.BankAcctToExists = false;
    paramTypePmtInfoAggregate.BillRefInfoExists = false;
    paramTypePmtInfoAggregate.PayeeUn = new TypePayeeUn();
    if (localPayee != null)
    {
      if ((localPayee.getHostID() == null) || (localPayee.getHostID().length() == 0))
      {
        paramTypePmtInfoAggregate.PayeeUn.__memberName = "Payee";
        paramTypePmtInfoAggregate.PayeeUn.Payee = new TypePayeeAggregate();
        formatPAYEE(paramTypePmtInfoAggregate.PayeeUn.Payee, localPayee);
      }
      else
      {
        paramTypePmtInfoAggregate.PayeeUn.__memberName = "PayeeID";
        paramTypePmtInfoAggregate.PayeeUn.PayeeID = localPayee.getHostID();
      }
    }
    else
    {
      paramTypePmtInfoAggregate.PayeeUn.__memberName = "PayeeID";
      paramTypePmtInfoAggregate.PayeeUn.PayeeID = str2;
    }
    paramTypePmtInfoAggregate.PayeeLstIDExists = true;
    paramTypePmtInfoAggregate.PayeeLstID = str2;
    paramTypePmtInfoAggregate.PayAcct = str1;
    paramTypePmtInfoAggregate.DtDue = DateFormatUtil.getFormatter("yyyyMMdd").format(fixPaymentDate(paramPayment, paramInt1, paramInt2).getTime());
    if ((paramPayment.getMemo() != null) && (paramPayment.getMemo().length() > 0))
    {
      paramTypePmtInfoAggregate.MemoExists = true;
      paramTypePmtInfoAggregate.Memo = paramPayment.getMemo();
    }
  }
  
  protected static void formatPAYEE(TypePayeeAggregate paramTypePayeeAggregate, Payee paramPayee)
  {
    paramTypePayeeAggregate.Name = paramPayee.getName();
    paramTypePayeeAggregate.AddressCm = new TypeAddressCm();
    paramTypePayeeAggregate.AddressCm.Addr1 = paramPayee.getStreet();
    paramTypePayeeAggregate.AddressCm.SubAddressCmExists = false;
    if ((paramPayee.getStreet2() != null) && (paramPayee.getStreet2().length() > 0))
    {
      paramTypePayeeAggregate.AddressCm.SubAddressCmExists = true;
      paramTypePayeeAggregate.AddressCm.SubAddressCm = new TypeSubAddressCm();
      paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr2 = paramPayee.getStreet2();
      paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3Exists = false;
      if ((paramPayee.getStreet3() != null) && (paramPayee.getStreet3().length() > 0))
      {
        paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3Exists = true;
        paramTypePayeeAggregate.AddressCm.SubAddressCm.Addr3 = paramPayee.getStreet3();
      }
    }
    paramTypePayeeAggregate.City = paramPayee.getCity();
    paramTypePayeeAggregate.State = paramPayee.getState();
    paramTypePayeeAggregate.PostalCode = paramPayee.getZipCode();
    paramTypePayeeAggregate.Phone = paramPayee.getPhone();
    if ((paramPayee.getCountry() != null) && (paramPayee.getCountry().length() > 0))
    {
      paramTypePayeeAggregate.CountryExists = true;
      paramTypePayeeAggregate.Country = paramPayee.getCountry();
    }
  }
  
  protected static Calendar fixPaymentDate(Payment paramPayment, int paramInt1, int paramInt2)
  {
    DateTime localDateTime = paramPayment.getPayDateValue();
    if ((paramInt1 != -1) && (paramPayment.getStatus() != 9))
    {
      if (paramPayment.getStatus() != 8)
      {
        i = 0;
        Payee localPayee = paramPayment.getPayee();
        if (localPayee != null) {
          i = localPayee.getDaysToPayValue();
        }
        if (i == 0) {
          i = paramInt2;
        }
        Calendar localCalendar = a(i);
        if (localDateTime.before(localCalendar)) {
          localDateTime.setTime(localCalendar.getTime());
        }
      }
      else
      {
        localDateTime.add(5, 1);
      }
      int i = localDateTime.get(7);
      if (i == 7) {
        localDateTime.add(5, 2);
      } else if (i == 1) {
        localDateTime.add(5, 1);
      }
    }
    return localDateTime;
  }
  
  private static Calendar a(int paramInt)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int i = localGregorianCalendar.get(7);
    if (i == 1)
    {
      localGregorianCalendar.add(5, 1);
      i = 2;
    }
    else if (i == 7)
    {
      localGregorianCalendar.add(5, 2);
      i = 2;
    }
    while (paramInt != 0)
    {
      localGregorianCalendar.add(5, 1);
      paramInt--;
      i++;
      if (i == 7)
      {
        localGregorianCalendar.add(5, 2);
        i = 2;
      }
    }
    return localGregorianCalendar;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.banksim2.BillPayBase
 * JD-Core Version:    0.7.0.1
 */