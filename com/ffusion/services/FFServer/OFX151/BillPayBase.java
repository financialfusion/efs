package com.ffusion.services.FFServer.OFX151;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillPayMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBillPayMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSubAddressCm;
import com.ffusion.util.DateFormatUtil;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class BillPayBase
  extends Base
  implements BillPayDefines
{
  protected Payees payees;
  protected Payee payee;
  protected Payment payment;
  protected Payments payments;
  protected int defaultDaysToPay = 4;
  
  protected TypeBillPayMsgsRqV1Aggregate formatBillPayMsgsRqV1(TypeOFXRqMsgSetsAggregate paramTypeOFXRqMsgSetsAggregate)
  {
    paramTypeOFXRqMsgSetsAggregate.BillPayMsgsRqUnExists = true;
    paramTypeOFXRqMsgSetsAggregate.BillPayMsgsRqUn = new TypeBillPayMsgsRqUn();
    paramTypeOFXRqMsgSetsAggregate.BillPayMsgsRqUn.__memberName = "BillPayMsgsRqV1";
    paramTypeOFXRqMsgSetsAggregate.BillPayMsgsRqUn.BillPayMsgsRqV1 = new TypeBillPayMsgsRqV1Aggregate();
    return paramTypeOFXRqMsgSetsAggregate.BillPayMsgsRqUn.BillPayMsgsRqV1;
  }
  
  protected void formatPmtInfo(TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate, Account paramAccount, Payment paramPayment, int paramInt)
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
    } else if (localPayee.getID().length() > 0) {
      str2 = localPayee.getID();
    }
    paramTypePmtInfoV1Aggregate.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
    formatBANKACCTFROM(paramTypePmtInfoV1Aggregate.BankAcctFrom, paramAccount);
    Double localDouble = new Double(paramPayment.getAmountValue().doubleValue());
    paramTypePmtInfoV1Aggregate.TrnAmt = localDouble.doubleValue();
    paramTypePmtInfoV1Aggregate.BankAcctToExists = false;
    paramTypePmtInfoV1Aggregate.BillRefInfoExists = false;
    paramTypePmtInfoV1Aggregate.PayeeUn = new TypePayeeUn();
    if (localPayee != null)
    {
      if ((localPayee.getHostID() == null) || (localPayee.getHostID().length() == 0))
      {
        paramTypePmtInfoV1Aggregate.PayeeUn.__memberName = "Payee";
        paramTypePmtInfoV1Aggregate.PayeeUn.Payee = new TypePayeeV1Aggregate();
        formatPAYEE(paramTypePmtInfoV1Aggregate.PayeeUn.Payee, localPayee);
      }
      else
      {
        paramTypePmtInfoV1Aggregate.PayeeUn.__memberName = "PayeeID";
        paramTypePmtInfoV1Aggregate.PayeeUn.PayeeID = localPayee.getHostID();
      }
    }
    else
    {
      paramTypePmtInfoV1Aggregate.PayeeUn.__memberName = "PayeeID";
      paramTypePmtInfoV1Aggregate.PayeeUn.PayeeID = str2;
    }
    paramTypePmtInfoV1Aggregate.PayeeLstIDExists = true;
    paramTypePmtInfoV1Aggregate.PayeeLstID = str2;
    paramTypePmtInfoV1Aggregate.PayAcct = str1;
    paramTypePmtInfoV1Aggregate.DtDue = DateFormatUtil.getFormatter("yyyyMMdd").format(fixPaymentDate(paramPayment, paramInt).getTime());
    if ((paramPayment.getMemo() != null) && (paramPayment.getMemo().length() > 0))
    {
      paramTypePmtInfoV1Aggregate.MemoExists = true;
      paramTypePmtInfoV1Aggregate.Memo = paramPayment.getMemo();
    }
  }
  
  protected void formatPAYEE(TypePayeeV1Aggregate paramTypePayeeV1Aggregate, Payee paramPayee)
  {
    paramTypePayeeV1Aggregate.Name = paramPayee.getName();
    paramTypePayeeV1Aggregate.AddressCm = new TypeAddressCm();
    paramTypePayeeV1Aggregate.AddressCm.Addr1 = paramPayee.getStreet();
    paramTypePayeeV1Aggregate.AddressCm.SubAddressCmExists = false;
    if ((paramPayee.getStreet2() != null) && (paramPayee.getStreet2().length() > 0))
    {
      paramTypePayeeV1Aggregate.AddressCm.SubAddressCmExists = true;
      paramTypePayeeV1Aggregate.AddressCm.SubAddressCm = new TypeSubAddressCm();
      paramTypePayeeV1Aggregate.AddressCm.SubAddressCm.Addr2 = paramPayee.getStreet2();
      paramTypePayeeV1Aggregate.AddressCm.SubAddressCm.Addr3Exists = false;
    }
    paramTypePayeeV1Aggregate.City = paramPayee.getCity();
    paramTypePayeeV1Aggregate.State = paramPayee.getState();
    paramTypePayeeV1Aggregate.PostalCode = paramPayee.getZipCode();
    paramTypePayeeV1Aggregate.Phone = paramPayee.getPhone();
  }
  
  protected Calendar fixPaymentDate(Payment paramPayment, int paramInt)
  {
    DateTime localDateTime = paramPayment.getPayDateValue();
    if (paramInt != -1)
    {
      if (paramPayment.getStatus() != 8)
      {
        i = 0;
        Payee localPayee = paramPayment.getPayee();
        if (localPayee != null) {
          i = localPayee.getDaysToPayValue();
        }
        if (i == 0) {
          i = this.defaultDaysToPay;
        }
        Calendar localCalendar = jdMethod_for(i);
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
  
  private Calendar jdMethod_for(int paramInt)
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
 * Qualified Name:     com.ffusion.services.FFServer.OFX151.BillPayBase
 * JD-Core Version:    0.7.0.1
 */