package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.Contact;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.msgbroker.factory.MBMessageFactory;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57ABCD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAdditionalLines;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAmount_32A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAmount_33B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeBankOperationCode_23B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCode_71A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50AK;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50K;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDebtor_59;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDebtor_59A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDebtor_59AN;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDetailsOfCharges_71F;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDetailsOfCharges_71G;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDetailsPayment_70;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeExchangeRate_36;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionCode_23E;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56ACD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeMT103;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeNarrative;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeNarrativeText;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeNarrative_77B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52AD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeParty;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeReceiversCorspdt_54A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeReceiversCorspdt_54ABD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeReceiversCorspdt_54B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeReceiversCorspdt_54D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfoF1_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfoF2_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfo_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSendersCorspdt_53A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSendersCorspdt_53ABD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSendersCorspdt_53B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSendersCorspdt_53D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSendingInsti_51A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeThirdReimbursementInsti_55A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeThirdReimbursementInsti_55ABD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeThirdReimbursementInsti_55B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeThirdReimbursementInsti_55D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTimeIndication_13C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransRefNum_20;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransTypeCode_26T;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;

public class MT103PaymentMapper
  implements ISWIFTMapper
{
  public TypeMT103 mapPaymentToMT103(Payment paramPayment)
    throws SWIFTParseException
  {
    int i = 1;
    if (i != 0) {
      throw new SWIFTParseException("method MT103PaymentMapper.mapPaymentToMT103 is not implemented");
    }
    return null;
  }
  
  public Payment mapMT103ToPayment(TypeMT103 paramTypeMT103)
    throws SWIFTParseException
  {
    Payments localPayments = new Payments();
    Payment localPayment = (Payment)localPayments.create();
    String str1 = paramTypeMT103.SendersRef_20.TransRefNum;
    if ((str1 != null) && (str1.length() > 0))
    {
      localObject1 = new ValueInfo();
      ((ValueInfo)localObject1).setValue(str1);
      ((ValueInfo)localObject1).setAction("add");
      localPayment.put("SENDERS_REFERENCE", localObject1);
    }
    if (paramTypeMT103.TimeIndication_13CExists)
    {
      localObject1 = new StringBuffer();
      ((StringBuffer)localObject1).append(paramTypeMT103.TimeIndication_13C[0].Code);
      ((StringBuffer)localObject1).append(" ");
      ((StringBuffer)localObject1).append(paramTypeMT103.TimeIndication_13C[0].Time);
      ((StringBuffer)localObject1).append(" ");
      ((StringBuffer)localObject1).append(paramTypeMT103.TimeIndication_13C[0].Sign);
      ((StringBuffer)localObject1).append(" ");
      ((StringBuffer)localObject1).append(paramTypeMT103.TimeIndication_13C[0].TimeOffset);
      if (((StringBuffer)localObject1).length() > 0)
      {
        localObject2 = new ValueInfo();
        ((ValueInfo)localObject2).setValue(((StringBuffer)localObject1).toString());
        ((ValueInfo)localObject2).setAction("add");
        localPayment.put("TIME_INDICATION", localObject2);
      }
    }
    Object localObject1 = paramTypeMT103.BankOperationCode_23B.Type;
    if ((localObject1 != null) && (((String)localObject1).length() > 0))
    {
      localObject2 = new ValueInfo();
      ((ValueInfo)localObject2).setValue(localObject1);
      ((ValueInfo)localObject2).setAction("add");
      localPayment.put("BANK_OPERATION_CODE", localObject2);
    }
    if (paramTypeMT103.InstructionCode_23EExists)
    {
      localObject2 = paramTypeMT103.InstructionCode_23E;
      localObject3 = new StringBuffer();
      for (int i = 0; i < localObject2.length; i++)
      {
        ((StringBuffer)localObject3).append(localObject2[i].Type);
        if (localObject2[i].NarrativeExists)
        {
          ((StringBuffer)localObject3).append("/");
          ((StringBuffer)localObject3).append(localObject2[i].Narrative);
          ((StringBuffer)localObject3).append(" ");
        }
      }
      if (((StringBuffer)localObject3).length() > 0)
      {
        localObject4 = new ValueInfo();
        ((ValueInfo)localObject4).setValue(((StringBuffer)localObject3).toString().trim());
        ((ValueInfo)localObject4).setAction("add");
        localPayment.put("PAY_INSTRUCT", localObject4);
      }
    }
    if (paramTypeMT103.TransTypeCode_26TExists)
    {
      localObject2 = paramTypeMT103.TransTypeCode_26T.Type;
      if ((localObject2 != null) && (((String)localObject2).length() > 0))
      {
        localObject3 = new ValueInfo();
        ((ValueInfo)localObject3).setValue(localObject2);
        ((ValueInfo)localObject3).setAction("add");
        localPayment.put("TRANSACTION_TYPE_CODE", localObject3);
      }
    }
    if (paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A != null)
    {
      localObject2 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date;
      localObject3 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency;
      localObject4 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Amount;
      localPayment.setDateFormat("yyMMdd");
      localPayment.setPayDate((String)localObject2);
      localPayment.setDeliverByDateDate((String)localObject2);
      localPayment.setAmount(new BigDecimal((String)localObject4));
      localPayment.setAmtCurrency((String)localObject3);
    }
    if (paramTypeMT103.CurrencyInstructedAmount_33BExists)
    {
      localObject2 = paramTypeMT103.CurrencyInstructedAmount_33B.Price;
      localObject3 = paramTypeMT103.CurrencyInstructedAmount_33B.Currency;
      if (localObject2 != null)
      {
        localObject4 = new ValueInfo();
        ((ValueInfo)localObject4).setValue(localObject2);
        ((ValueInfo)localObject4).setAction("add");
        localPayment.put("PAYMENT_ORIG_AMOUNT", localObject4);
      }
      if (localObject3 != null)
      {
        localObject4 = new ValueInfo();
        ((ValueInfo)localObject4).setValue(localObject3);
        ((ValueInfo)localObject4).setAction("add");
        localPayment.put("PAYMENT_ORIG_CURRENCY", localObject3);
      }
    }
    if (paramTypeMT103.ExchangeRate_36Exists)
    {
      localObject2 = paramTypeMT103.ExchangeRate_36.Rate;
      localObject3 = new ValueInfo();
      ((ValueInfo)localObject3).setValue(localObject2);
      ((ValueInfo)localObject3).setAction("add");
      localPayment.put("EXCHANGE_RATE", localObject3);
    }
    Object localObject2 = paramTypeMT103.OrderingCustomer_50AK;
    Object localObject3 = "";
    Object localObject4 = "";
    Object localObject5 = null;
    Contact localContact = null;
    String str2 = paramTypeMT103.OrderingCustomer_50AK.__memberName;
    if ((str2 != null) && (str2.length() > 0))
    {
      localObject6 = new ValueInfo();
      ((ValueInfo)localObject6).setValue(str2);
      ((ValueInfo)localObject6).setAction("add");
      localPayment.put("ORDERING_CUSTOMER_MEMBER_NAME", localObject6);
    }
    if (((TypeCreditor_50AK)localObject2).__preValueTag.equals("50A"))
    {
      localObject3 = ((TypeCreditor_50AK)localObject2).Creditor_50A.Account;
      localObject4 = ((TypeCreditor_50AK)localObject2).Creditor_50A.BIC;
      if (((TypeCreditor_50AK)localObject2).Creditor_50A.BICBranchCodeExists) {
        localObject4 = ((String)localObject4).concat(((TypeCreditor_50AK)localObject2).Creditor_50A.BICBranchCode);
      } else {
        localObject4 = ((String)localObject4).concat("XXX");
      }
    }
    else
    {
      localObject3 = ((TypeCreditor_50AK)localObject2).Creditor_50K.Account;
      localObject5 = ((TypeCreditor_50AK)localObject2).Creditor_50K.NameAddr.Line;
    }
    if (localObject3 != null) {
      localPayment.setAccountID((String)localObject3);
    }
    if ((localObject5 != null) && (localObject5.length > 0))
    {
      if (localObject5.length > 4)
      {
        localObject6 = new String[4];
        for (j = 0; j < localObject5.length; j++) {
          if (j <= 3) {
            localObject6[j] = localObject5[j];
          } else {
            localObject6[3] = (localObject6[3] + localObject5[j]);
          }
        }
        localObject5 = localObject6;
      }
      localObject6 = "";
      for (int j = 0; j < localObject5.length; j++) {
        localObject6 = (String)localObject6 + localObject5[j] + ",";
      }
      if (((String)localObject6).endsWith(",")) {
        localObject6 = ((String)localObject6).substring(0, ((String)localObject6).length() - 1);
      }
      localObject7 = new ValueInfo();
      ((ValueInfo)localObject7).setValue(localObject6);
      ((ValueInfo)localObject7).setAction("add");
      localPayment.put("ORDERING_CUSTOMER", localObject7);
      localContact = new Contact();
      try
      {
        localContact.setStreet(localObject5[1]);
        localContact.setCity(localObject5[2]);
        localContact.setCountry(localObject5[3]);
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1) {}
    }
    Object localObject6 = new Account();
    ((Account)localObject6).setRoutingNum((String)localObject4);
    ((Account)localObject6).setID((String)localObject3);
    ((Account)localObject6).setContact(localContact);
    localPayment.setAccount((Account)localObject6);
    if (paramTypeMT103.SendingInsti_51AExists)
    {
      if (paramTypeMT103.SendingInsti_51A.PartyExists)
      {
        localObject7 = paramTypeMT103.SendingInsti_51A.Party.PartyIdentifier;
        if ((localObject7 != null) && (((String)localObject7).length() > 0))
        {
          localObject8 = new ValueInfo();
          ((ValueInfo)localObject8).setValue(localObject7);
          ((ValueInfo)localObject8).setAction("add");
          localPayment.put("SENDING_PARTY_ID", localObject8);
        }
      }
      localObject7 = paramTypeMT103.SendingInsti_51A.BIC;
      if (paramTypeMT103.SendingInsti_51A.BICBranchCodeExists) {
        localObject7 = ((String)localObject7).concat(paramTypeMT103.SendingInsti_51A.BICBranchCode);
      } else {
        localObject7 = ((String)localObject7).concat("XXX");
      }
      if ((localObject7 != null) && (((String)localObject7).length() > 0))
      {
        localObject8 = new ValueInfo();
        ((ValueInfo)localObject8).setValue(localObject7);
        ((ValueInfo)localObject8).setAction("add");
        localPayment.put("SEND_INSTITUTION_BICCODE", localObject8);
      }
    }
    Object localObject7 = null;
    Object localObject8 = null;
    String[] arrayOfString = null;
    ValueInfo localValueInfo2;
    if (paramTypeMT103.OrderingInstitution_52ADExists)
    {
      str3 = null;
      str3 = paramTypeMT103.OrderingInstitution_52AD.__memberName;
      if (str3 != null)
      {
        localObject9 = new ValueInfo();
        ((ValueInfo)localObject9).setValue(str3);
        ((ValueInfo)localObject9).setAction("add");
        localPayment.put("ORDERING_INSTITUTION_MEMBER_NAME", localObject9);
      }
      ValueInfo localValueInfo1;
      if (paramTypeMT103.OrderingInstitution_52AD.__preValueTag.equals("52A"))
      {
        localObject9 = paramTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A;
        if (((TypeOrderingInstitution_52A)localObject9).PartyExists) {
          localObject7 = ((TypeOrderingInstitution_52A)localObject9).Party.PartyIdentifier;
        }
        localObject8 = ((TypeOrderingInstitution_52A)localObject9).BIC;
        if (((TypeOrderingInstitution_52A)localObject9).BICBranchCodeExists) {
          localObject8 = ((String)localObject8).concat(((TypeOrderingInstitution_52A)localObject9).BICBranchCode);
        } else {
          localObject8 = ((String)localObject8).concat("XXX");
        }
        if ((localObject7 != null) && (((String)localObject7).length() > 0))
        {
          localValueInfo1 = new ValueInfo();
          localValueInfo1.setValue(localObject7);
          localValueInfo1.setAction("add");
          localPayment.put("PARTY_IDENTIFIER_52", localValueInfo1);
        }
        if ((localObject8 != null) && (((String)localObject8).length() > 0))
        {
          localValueInfo1 = new ValueInfo();
          localValueInfo1.setValue(localObject8);
          localValueInfo1.setAction("add");
          localPayment.put("ORDERING_INSTITUTION_BIC", localValueInfo1);
        }
      }
      else
      {
        localObject9 = paramTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D;
        if (((TypeOrderingInstitution_52D)localObject9).PartyExists) {
          localObject7 = ((TypeOrderingInstitution_52D)localObject9).Party.PartyIdentifier;
        }
        if (((TypeOrderingInstitution_52D)localObject9).NameAddr != null) {
          arrayOfString = ((TypeOrderingInstitution_52D)localObject9).NameAddr.Line;
        }
        if ((localObject7 != null) && (((String)localObject7).length() > 0))
        {
          localValueInfo1 = new ValueInfo();
          localValueInfo1.setValue(localObject7);
          localValueInfo1.setAction("add");
          localPayment.put("PARTY_IDENTIFIER_52", localValueInfo1);
        }
        if ((arrayOfString != null) && (arrayOfString.length > 0))
        {
          int k = 0;
          localObject10 = "";
          localObject11 = arrayOfString[(k++)];
          if ((localObject11 != null) && (((String)localObject11).length() > 0))
          {
            localValueInfo2 = new ValueInfo();
            localValueInfo2.setValue(localObject11);
            localValueInfo2.setAction("add");
            localPayment.put("ORDERING_INSTITUTION_NAME", localValueInfo2);
          }
          while (arrayOfString.length > k) {
            localObject10 = ((String)localObject10).concat(arrayOfString[(k++)] + ",");
          }
          if (((String)localObject10).substring(((String)localObject10).length() - 1).equals(",")) {
            localObject10 = ((String)localObject10).substring(0, ((String)localObject10).length() - 1);
          }
          if ((localObject10 != null) && (((String)localObject10).length() > 0))
          {
            localValueInfo2 = new ValueInfo();
            localValueInfo2.setValue(localObject10);
            localValueInfo2.setAction("add");
            localPayment.put("ORDERING_INSTITUTION_ADDRESS", localValueInfo2);
          }
        }
      }
    }
    String str3 = null;
    Object localObject9 = null;
    String str4 = null;
    Object localObject10 = null;
    if (paramTypeMT103.SendersCorspdt_53ABDExists) {
      if (paramTypeMT103.SendersCorspdt_53ABD.__preValueTag.equals("53A"))
      {
        localObject11 = paramTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A;
        if (((TypeSendersCorspdt_53A)localObject11).PartyExists)
        {
          str3 = ((TypeSendersCorspdt_53A)localObject11).Party.PartyIdentifier;
          if ((str3 != null) && (str3.length() > 0))
          {
            localValueInfo2 = new ValueInfo();
            localValueInfo2.setValue(str3);
            localValueInfo2.setAction("add");
            localPayment.put("SENDER_CORRESPONDENT_PARTY_ID", localValueInfo2);
          }
        }
        localObject9 = ((TypeSendersCorspdt_53A)localObject11).BIC;
        if (((TypeSendersCorspdt_53A)localObject11).BICBranchCodeExists) {
          localObject9 = ((String)localObject9).concat(((TypeSendersCorspdt_53A)localObject11).BICBranchCode);
        } else {
          localObject9 = ((String)localObject9).concat("XXX");
        }
        if ((localObject9 != null) && (((String)localObject9).length() > 0))
        {
          localValueInfo2 = new ValueInfo();
          localValueInfo2.setValue(localObject9);
          localValueInfo2.setAction("add");
          localPayment.put("SENDER_CORRESPONDENT_BIC", localValueInfo2);
        }
      }
      else if (paramTypeMT103.SendersCorspdt_53ABD.__preValueTag.equals("53B"))
      {
        localObject11 = paramTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53B;
        if (((TypeSendersCorspdt_53B)localObject11).PartyExists)
        {
          str3 = ((TypeSendersCorspdt_53B)localObject11).Party.PartyIdentifier;
          if ((str3 != null) && (str3.length() > 0))
          {
            localValueInfo2 = new ValueInfo();
            localValueInfo2.setValue(str3);
            localValueInfo2.setAction("add");
            localPayment.put("SENDER_CORRESPONDENT_PARTY_ID", localValueInfo2);
          }
        }
        if (((TypeSendersCorspdt_53B)localObject11).LocationExists)
        {
          str4 = ((TypeSendersCorspdt_53B)localObject11).Location;
          if ((str4 != null) && (str4.length() > 0))
          {
            localValueInfo2 = new ValueInfo();
            localValueInfo2.setValue(str4);
            localValueInfo2.setAction("add");
            localPayment.put("SENDER_CORRESPONDENT_LOCATION", localValueInfo2);
          }
        }
      }
      else
      {
        localObject11 = paramTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53D;
        if (((TypeSendersCorspdt_53D)localObject11).PartyExists)
        {
          str3 = ((TypeSendersCorspdt_53D)localObject11).Party.PartyIdentifier;
          if ((str3 != null) && (str3.length() > 0))
          {
            localValueInfo2 = new ValueInfo();
            localValueInfo2.setValue(str3);
            localValueInfo2.setAction("add");
            localPayment.put("SENDER_CORRESPONDENT_PARTY_ID", localValueInfo2);
          }
        }
        if (((TypeSendersCorspdt_53D)localObject11).NameAddr != null)
        {
          localObject10 = ((TypeSendersCorspdt_53D)localObject11).NameAddr.Line;
          if ((localObject10 != null) && (localObject10.length > 0))
          {
            int m = 0;
            str6 = "";
            localObject12 = localObject10[(m++)];
            if ((localObject12 != null) && (((String)localObject12).length() > 0))
            {
              localObject13 = new ValueInfo();
              ((ValueInfo)localObject13).setValue(localObject12);
              ((ValueInfo)localObject13).setAction("add");
              localPayment.put("SENDER_CORRESPONDENT_NAME", localObject13);
            }
            while (localObject5.length > m) {
              str6 = str6.concat(localObject5[(m++)] + ",");
            }
            if (str6.substring(str6.length() - 1).equals(",")) {
              str6 = str6.substring(0, str6.length() - 1);
            }
            if ((str6 != null) && (str6.length() > 0))
            {
              localObject13 = new ValueInfo();
              ((ValueInfo)localObject13).setValue(str6);
              ((ValueInfo)localObject13).setAction("add");
              localPayment.put("SENDER_CORRESPONDENT_ADDRESS", localObject13);
            }
          }
        }
      }
    }
    Object localObject11 = null;
    String str5 = null;
    String str6 = null;
    Object localObject12 = null;
    Object localObject15;
    Object localObject17;
    Object localObject19;
    if (paramTypeMT103.ReceiversCorspdt_54ABDExists)
    {
      ValueInfo localValueInfo3;
      if (paramTypeMT103.ReceiversCorspdt_54ABD.__preValueTag.equals("54A"))
      {
        localObject13 = paramTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A;
        if (((TypeReceiversCorspdt_54A)localObject13).PartyExists)
        {
          localObject11 = ((TypeReceiversCorspdt_54A)localObject13).Party.PartyIdentifier;
          if ((localObject11 != null) && (((String)localObject11).length() > 0))
          {
            localValueInfo3 = new ValueInfo();
            localValueInfo3.setValue(localObject11);
            localValueInfo3.setAction("add");
            localPayment.put("RECEIVER_CORRESPONDENT_PARTY_ID", localValueInfo3);
          }
        }
        str5 = ((TypeReceiversCorspdt_54A)localObject13).BIC;
        if (((TypeReceiversCorspdt_54A)localObject13).BICBranchCodeExists) {
          str5 = str5.concat(((TypeReceiversCorspdt_54A)localObject13).BICBranchCode);
        } else {
          str5 = str5.concat("XXX");
        }
        if ((str5 != null) && (str5.length() > 0))
        {
          localValueInfo3 = new ValueInfo();
          localValueInfo3.setValue(str5);
          localValueInfo3.setAction("add");
          localPayment.put("RECEIVER_CORRESPONDENT_BIC", localValueInfo3);
        }
      }
      else if (paramTypeMT103.ReceiversCorspdt_54ABD.__preValueTag.equals("54B"))
      {
        localObject13 = paramTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54B;
        if (((TypeReceiversCorspdt_54B)localObject13).PartyExists)
        {
          localObject11 = ((TypeReceiversCorspdt_54B)localObject13).Party.PartyIdentifier;
          if ((localObject11 != null) && (((String)localObject11).length() > 0))
          {
            localValueInfo3 = new ValueInfo();
            localValueInfo3.setValue(localObject11);
            localValueInfo3.setAction("add");
            localPayment.put("RECEIVER_CORRESPONDENT_PARTY_ID", localValueInfo3);
          }
        }
        if (((TypeReceiversCorspdt_54B)localObject13).LocationExists)
        {
          str6 = ((TypeReceiversCorspdt_54B)localObject13).Location;
          if ((str6 != null) && (str6.length() > 0))
          {
            localValueInfo3 = new ValueInfo();
            localValueInfo3.setValue(str6);
            localValueInfo3.setAction("add");
            localPayment.put("RECEIVER_CORRESPONDENT_LOCATION", localValueInfo3);
          }
        }
      }
      else
      {
        localObject13 = paramTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54D;
        if (((TypeReceiversCorspdt_54D)localObject13).PartyExists)
        {
          localObject11 = ((TypeReceiversCorspdt_54D)localObject13).Party.PartyIdentifier;
          if ((localObject11 != null) && (((String)localObject11).length() > 0))
          {
            localValueInfo3 = new ValueInfo();
            localValueInfo3.setValue(localObject11);
            localValueInfo3.setAction("add");
            localPayment.put("RECEIVER_CORRESPONDENT_PARTY_ID", localValueInfo3);
          }
        }
        if (((TypeReceiversCorspdt_54D)localObject13).NameAddr != null) {
          localObject12 = ((TypeReceiversCorspdt_54D)localObject13).NameAddr.Line;
        }
        if ((localObject12 != null) && (localObject12.length > 0))
        {
          int n = 0;
          localObject15 = "";
          localObject17 = "";
          localObject17 = localObject12[(n++)];
          if ((localObject17 != null) && (((String)localObject17).length() > 0))
          {
            localObject19 = new ValueInfo();
            ((ValueInfo)localObject19).setValue(localObject17);
            ((ValueInfo)localObject19).setAction("add");
            localPayment.put("RECEIVER_CORRESPONDENT_NAME", localObject19);
          }
          while (localObject5.length > n) {
            localObject15 = ((String)localObject15).concat(localObject5[(n++)] + ",");
          }
          if (((String)localObject15).substring(((String)localObject15).length() - 1).equals(",")) {
            localObject15 = ((String)localObject15).substring(0, ((String)localObject15).length() - 1);
          }
          if ((localObject15 != null) && (((String)localObject15).length() > 0))
          {
            localObject19 = new ValueInfo();
            ((ValueInfo)localObject19).setValue(localObject15);
            ((ValueInfo)localObject19).setAction("add");
            localPayment.put("RECEIVER_CORRESPONDENT_ADDRESS", localObject19);
          }
        }
      }
    }
    String str8;
    Object localObject23;
    if (paramTypeMT103.ThirdReimbursementInsti_55ABDExists)
    {
      localObject13 = null;
      localObject14 = null;
      localObject15 = null;
      localObject17 = null;
      ValueInfo localValueInfo5;
      if (paramTypeMT103.ThirdReimbursementInsti_55ABD.__preValueTag.equals("55A"))
      {
        localObject19 = paramTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A;
        if (((TypeThirdReimbursementInsti_55A)localObject19).PartyExists)
        {
          localObject13 = ((TypeThirdReimbursementInsti_55A)localObject19).Party.PartyIdentifier;
          if ((localObject13 != null) && (((String)localObject13).length() > 0))
          {
            localValueInfo5 = new ValueInfo();
            localValueInfo5.setValue(localObject13);
            localValueInfo5.setAction("add");
            localPayment.put("THIRD_REIMBURSEMENT_INSTI_PARTY_ID", localValueInfo5);
          }
        }
        localObject14 = ((TypeThirdReimbursementInsti_55A)localObject19).BIC;
        if (((TypeThirdReimbursementInsti_55A)localObject19).BICBranchCodeExists) {
          localObject14 = ((String)localObject14).concat(((TypeThirdReimbursementInsti_55A)localObject19).BICBranchCode);
        } else {
          localObject14 = ((String)localObject14).concat("XXX");
        }
        if ((localObject14 != null) && (((String)localObject14).length() > 0))
        {
          localValueInfo5 = new ValueInfo();
          localValueInfo5.setValue(localObject14);
          localValueInfo5.setAction("add");
          localPayment.put("THIRD_REIMBURSEMENT_INSTI_BIC", localValueInfo5);
        }
      }
      else if (paramTypeMT103.ThirdReimbursementInsti_55ABD.__preValueTag.equals("55B"))
      {
        localObject19 = paramTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55B;
        if (((TypeThirdReimbursementInsti_55B)localObject19).PartyExists)
        {
          localObject13 = ((TypeThirdReimbursementInsti_55B)localObject19).Party.PartyIdentifier;
          if ((localObject13 != null) && (((String)localObject13).length() > 0))
          {
            localValueInfo5 = new ValueInfo();
            localValueInfo5.setValue(localObject13);
            localValueInfo5.setAction("add");
            localPayment.put("THIRD_REIMBURSEMENT_INSTI_PARTY_ID", localValueInfo5);
          }
        }
        localObject15 = ((TypeThirdReimbursementInsti_55B)localObject19).Location;
        if ((localObject15 != null) && (((String)localObject15).length() > 0))
        {
          localValueInfo5 = new ValueInfo();
          localValueInfo5.setValue(localObject15);
          localValueInfo5.setAction("add");
          localPayment.put("THIRD_REIMBURSEMENT_INSTI_LOCATION", localValueInfo5);
        }
      }
      else
      {
        localObject19 = paramTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55D;
        if (((TypeThirdReimbursementInsti_55D)localObject19).PartyExists)
        {
          localObject13 = ((TypeThirdReimbursementInsti_55D)localObject19).Party.PartyIdentifier;
          if ((localObject13 != null) && (((String)localObject13).length() > 0))
          {
            localValueInfo5 = new ValueInfo();
            localValueInfo5.setValue(localObject13);
            localValueInfo5.setAction("add");
            localPayment.put("THIRD_REIMBURSEMENT_INSTI_PARTY_ID", localValueInfo5);
          }
        }
        if (((TypeThirdReimbursementInsti_55D)localObject19).NameAddr != null) {
          localObject17 = ((TypeThirdReimbursementInsti_55D)localObject19).NameAddr.Line;
        }
        if ((localObject17 != null) && (localObject17.length > 0))
        {
          int i6 = 0;
          str8 = "";
          localObject23 = "";
          localObject23 = localObject17[(i6++)];
          ValueInfo localValueInfo7;
          if ((localObject23 != null) && (((String)localObject23).length() > 0))
          {
            localValueInfo7 = new ValueInfo();
            localValueInfo7.setValue(localObject23);
            localValueInfo7.setAction("add");
            localPayment.put("THIRD_REIMBURSEMENT_INSTI_NAME", localValueInfo7);
          }
          while (localObject17.length > i6) {
            str8 = str8.concat(localObject17[(i6++)] + ",");
          }
          if (str8.substring(str8.length() - 1).equals(",")) {
            str8 = str8.substring(0, str8.length() - 1);
          }
          if ((str8 != null) && (str8.length() > 0))
          {
            localValueInfo7 = new ValueInfo();
            localValueInfo7.setValue(str8);
            localValueInfo7.setAction("add");
            localPayment.put("THIRD_REIMBURSEMENT_INSTI_ADDRESS", localValueInfo7);
          }
        }
      }
    }
    Object localObject21;
    if (paramTypeMT103.Intermediary_56ACDExists)
    {
      localObject13 = paramTypeMT103.Intermediary_56ACD;
      localObject14 = null;
      localObject14 = paramTypeMT103.Intermediary_56ACD.__memberName;
      if ((localObject14 != null) && (((String)localObject14).length() > 0))
      {
        localObject15 = new ValueInfo();
        ((ValueInfo)localObject15).setValue(localObject14);
        ((ValueInfo)localObject15).setAction("add");
        localPayment.put("INTERMEDIARY_MEMBER_NAME", localObject15);
      }
      if (((TypeIntermediary_56ACD)localObject13).__preValueTag.equals("56A"))
      {
        localObject15 = ((TypeIntermediary_56ACD)localObject13).Intermediary_56A;
        if ((((TypeIntermediary_56A)localObject15).PartyExists) && (((TypeIntermediary_56A)localObject15).Party.PartyIdentifierExists))
        {
          localObject17 = ((TypeIntermediary_56A)localObject15).Party.PartyIdentifier;
          if ((localObject17 != null) && (((String)localObject17).length() > 0))
          {
            localObject19 = new ValueInfo();
            ((ValueInfo)localObject19).setValue(localObject17);
            ((ValueInfo)localObject19).setAction("add");
            localPayment.put("INTERMEDIARY_PARTY_ID", localObject17);
          }
        }
        localObject17 = new StringBuffer(((TypeIntermediary_56A)localObject15).BIC);
        if (((TypeIntermediary_56A)localObject15).BICBranchCodeExists) {
          ((StringBuffer)localObject17).append(((TypeIntermediary_56A)localObject15).BICBranchCode);
        } else {
          ((StringBuffer)localObject17).append("XXX");
        }
        if ((localObject17 != null) && (((StringBuffer)localObject17).length() > 0))
        {
          localObject19 = new ValueInfo();
          ((ValueInfo)localObject19).setValue(((StringBuffer)localObject17).toString());
          ((ValueInfo)localObject19).setAction("add");
          localPayment.put("INTERMEDIARY_BIC", localObject19);
        }
      }
      else if (((TypeIntermediary_56ACD)localObject13).__preValueTag.equals("56C"))
      {
        localObject15 = ((TypeIntermediary_56ACD)localObject13).Intermediary_56C;
        localObject17 = ((TypeIntermediary_56C)localObject15).PartyIdentifier;
        if ((localObject17 != null) && (((String)localObject17).length() > 0))
        {
          localObject19 = new ValueInfo();
          ((ValueInfo)localObject19).setValue(localObject17);
          ((ValueInfo)localObject19).setAction("add");
          localPayment.put("INTERMEDIARY_PARTY_ID", localObject17);
        }
      }
      else if (((TypeIntermediary_56ACD)localObject13).__preValueTag.equals("56D"))
      {
        localObject15 = ((TypeIntermediary_56ACD)localObject13).Intermediary_56D;
        if ((((TypeIntermediary_56D)localObject15).PartyExists) && (((TypeIntermediary_56D)localObject15).Party.PartyIdentifierExists))
        {
          localObject17 = ((TypeIntermediary_56D)localObject15).Party.PartyIdentifier;
          if ((localObject17 != null) && (((String)localObject17).length() > 0))
          {
            localObject19 = new ValueInfo();
            ((ValueInfo)localObject19).setValue(localObject17);
            ((ValueInfo)localObject19).setAction("add");
            localPayment.put("INTERMEDIARY_PARTY_ID", localObject19);
          }
        }
        localObject17 = ((TypeIntermediary_56D)localObject15).NameAddr.Line;
        if ((localObject17 != null) && (localObject17.length > 0))
        {
          int i3 = 0;
          localObject21 = "";
          str8 = "";
          localObject21 = localObject17[(i3++)];
          if ((localObject21 != null) && (((String)localObject21).length() > 0))
          {
            localObject23 = new ValueInfo();
            ((ValueInfo)localObject23).setValue(localObject21);
            ((ValueInfo)localObject23).setAction("add");
            localPayment.put("INTERMEDIARY_NAME", localObject23);
          }
          while (localObject17.length > i3) {
            str8 = str8.concat(localObject17[(i3++)] + ",");
          }
          if (str8.substring(str8.length() - 1).equals(",")) {
            str8 = str8.substring(0, str8.length() - 1);
          }
          if ((str8 != null) && (str8.length() > 0))
          {
            localObject23 = new ValueInfo();
            ((ValueInfo)localObject23).setValue(str8);
            ((ValueInfo)localObject23).setAction("add");
            localPayment.put("INTERMEDIARY_ADDRESS", localObject23);
          }
        }
      }
    }
    if (paramTypeMT103.AcctWithInsti_57ABCDExists)
    {
      localObject13 = paramTypeMT103.AcctWithInsti_57ABCD;
      localObject14 = null;
      localObject14 = paramTypeMT103.AcctWithInsti_57ABCD.__memberName;
      if ((localObject14 != null) && (((String)localObject14).length() > 0))
      {
        localObject15 = new ValueInfo();
        ((ValueInfo)localObject15).setValue(localObject14);
        ((ValueInfo)localObject15).setAction("add");
        localPayment.put("ACCOUNT_WITH_INSTITUTION_MEMBER_NAME", localObject15);
      }
      ValueInfo localValueInfo4;
      if (((TypeAcctWithInsti_57ABCD)localObject13).__preValueTag.equals("57A"))
      {
        localObject15 = ((TypeAcctWithInsti_57ABCD)localObject13).AcctWithInsti_57A;
        if ((((TypeAcctWithInsti_57A)localObject15).PartyExists) && (((TypeAcctWithInsti_57A)localObject15).Party.PartyIdentifierExists))
        {
          localObject17 = ((TypeAcctWithInsti_57A)localObject15).Party.PartyIdentifier;
          if ((localObject17 != null) && (((String)localObject17).length() > 0))
          {
            localValueInfo4 = new ValueInfo();
            localValueInfo4.setValue(localObject17);
            localValueInfo4.setAction("add");
            localPayment.put("ACCOUNT_WITH_INSTITUTION_PARTY_ID", localValueInfo4);
          }
        }
        localObject17 = new StringBuffer(((TypeAcctWithInsti_57A)localObject15).BIC);
        if (((TypeAcctWithInsti_57A)localObject15).BICBranchCodeExists) {
          ((StringBuffer)localObject17).append(((TypeAcctWithInsti_57A)localObject15).BICBranchCode);
        } else {
          ((StringBuffer)localObject17).append("XXX");
        }
        if ((localObject17 != null) && (((StringBuffer)localObject17).length() > 0))
        {
          localValueInfo4 = new ValueInfo();
          localValueInfo4.setValue(((StringBuffer)localObject17).toString());
          localValueInfo4.setAction("add");
          localPayment.put("ACCOUNT_WITH_INSTITUTION_BIC", localValueInfo4);
        }
      }
      else if (((TypeAcctWithInsti_57ABCD)localObject13).__preValueTag.equals("57B"))
      {
        localObject15 = ((TypeAcctWithInsti_57ABCD)localObject13).AcctWithInsti_57B;
        if ((((TypeAcctWithInsti_57B)localObject15).PartyExists) && (((TypeAcctWithInsti_57B)localObject15).Party.PartyIdentifierExists))
        {
          localObject17 = ((TypeAcctWithInsti_57B)localObject15).Party.PartyIdentifier;
          if ((localObject17 != null) && (((String)localObject17).length() > 0))
          {
            localValueInfo4 = new ValueInfo();
            localValueInfo4.setValue(localObject17);
            localValueInfo4.setAction("add");
            localPayment.put("ACCOUNT_WITH_INSTITUTION_PARTY_ID", localValueInfo4);
          }
        }
        if (((TypeAcctWithInsti_57B)localObject15).LocationExists)
        {
          localObject17 = ((TypeAcctWithInsti_57B)localObject15).Location;
          if ((localObject17 != null) && (((String)localObject17).length() > 0))
          {
            localValueInfo4 = new ValueInfo();
            localValueInfo4.setValue(localObject17);
            localValueInfo4.setAction("add");
            localPayment.put("ACCOUNT_WITH_INSTITUTION_LOCATION", localValueInfo4);
          }
        }
      }
      else if (((TypeAcctWithInsti_57ABCD)localObject13).__preValueTag.equals("57C"))
      {
        localObject15 = ((TypeAcctWithInsti_57ABCD)localObject13).AcctWithInsti_57C;
        localObject17 = ((TypeAcctWithInsti_57C)localObject15).Id;
        if ((localObject17 != null) && (((String)localObject17).length() > 0))
        {
          localValueInfo4 = new ValueInfo();
          localValueInfo4.setValue(localObject17);
          localValueInfo4.setAction("add");
          localPayment.put("ACCOUNT_WITH_INSTITUTION_PARTY_ID", localValueInfo4);
        }
      }
      else if (((TypeAcctWithInsti_57ABCD)localObject13).__preValueTag.equals("57D"))
      {
        localObject15 = ((TypeAcctWithInsti_57ABCD)localObject13).AcctWithInsti_57D;
        if ((((TypeAcctWithInsti_57D)localObject15).PartyExists) && (((TypeAcctWithInsti_57D)localObject15).Party.PartyIdentifierExists))
        {
          localObject17 = ((TypeAcctWithInsti_57D)localObject15).Party.PartyIdentifier;
          if ((localObject17 != null) && (((String)localObject17).length() > 0))
          {
            localValueInfo4 = new ValueInfo();
            localValueInfo4.setValue(localObject17);
            localValueInfo4.setAction("add");
            localPayment.put("ACCOUNT_WITH_INSTITUTION_PARTY_ID", localValueInfo4);
          }
        }
        localObject17 = ((TypeAcctWithInsti_57D)localObject15).NameAddr.Line;
        if ((localObject17 != null) && (localObject17.length > 0))
        {
          int i4 = 0;
          localObject21 = "";
          str8 = "";
          localObject21 = localObject17[(i4++)];
          if ((localObject21 != null) && (((String)localObject21).length() > 0))
          {
            localObject23 = new ValueInfo();
            ((ValueInfo)localObject23).setValue(localObject21);
            ((ValueInfo)localObject23).setAction("add");
            localPayment.put("ACCOUNT_WITH_INSTITUTION_NAME", localObject23);
          }
          while (localObject17.length > i4) {
            str8 = str8.concat(localObject17[(i4++)] + ",");
          }
          if (str8.substring(str8.length() - 1).equals(",")) {
            str8 = str8.substring(0, str8.length() - 1);
          }
          if ((str8 != null) && (str8.length() > 0))
          {
            localObject23 = new ValueInfo();
            ((ValueInfo)localObject23).setValue(str8);
            ((ValueInfo)localObject23).setAction("add");
            localPayment.put("ACCOUNT_WITH_INSTITUTION_ADDRESS", localObject23);
          }
        }
      }
    }
    Object localObject13 = paramTypeMT103.BeneficiaryCustomer_59AN;
    if ((localObject13 != null) && (((TypeDebtor_59AN)localObject13).__memberName != null))
    {
      localObject14 = null;
      localObject14 = ((TypeDebtor_59AN)localObject13).__memberName;
      if ((localObject14 != null) && (((String)localObject14).length() > 0))
      {
        localObject15 = new ValueInfo();
        ((ValueInfo)localObject15).setValue(localObject14);
        ((ValueInfo)localObject15).setAction("add");
        localPayment.put("BENEFICIARY_MEMBER_NAME", localObject15);
      }
    }
    Object localObject14 = new Payee();
    localPayment.setPayee((Payee)localObject14);
    if (((TypeDebtor_59AN)localObject13).__preValueTag.equals("59A"))
    {
      if (((TypeDebtor_59AN)localObject13).Debtor_59A.AccountExists) {
        localPayment.getPayee().setUserAccountNumber(((TypeDebtor_59AN)localObject13).Debtor_59A.Account);
      }
      localObject15 = ((TypeDebtor_59AN)localObject13).Debtor_59A.BIC;
      if (((TypeDebtor_59AN)localObject13).Debtor_59A.BICBranchCodeExists) {
        localObject15 = (String)localObject15 + ((TypeDebtor_59AN)localObject13).Debtor_59A.BICBranchCode;
      } else {
        localObject15 = (String)localObject15 + "XXX";
      }
      if (localObject15 != null)
      {
        localObject17 = new ValueInfo();
        ((ValueInfo)localObject17).setValue(localObject15);
        ((ValueInfo)localObject17).setAction("add");
        localPayment.put("BENEFICIARY_BIC", localObject17);
      }
    }
    else
    {
      if (((TypeDebtor_59AN)localObject13).Debtor_59.AccountExists) {
        localPayment.getPayee().setUserAccountNumber(((TypeDebtor_59AN)localObject13).Debtor_59.Account);
      }
      localObject15 = ((TypeDebtor_59AN)localObject13).Debtor_59.NameAddr.Line;
      if ((localObject15 != null) && (localObject15.length > 0)) {
        try
        {
          ((Payee)localObject14).setName(localObject15[0]);
          ((Payee)localObject14).setStreet(localObject15[1]);
          ((Payee)localObject14).setCity(localObject15[2]);
          ((Payee)localObject14).setCountry(localObject15[3]);
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2) {}
      }
    }
    Object localObject18;
    if (paramTypeMT103.RemittanceInformation_70Exists)
    {
      int i1 = 0;
      localObject18 = paramTypeMT103.RemittanceInformation_70.Narrative.Line;
      for (String str7 = ""; localObject18.length > i1; str7 = str7 + "\n" + localObject18[(i1++)]) {}
      if (str7.startsWith("\n")) {
        str7 = str7.substring(1);
      }
      if ((str7 != null) && (str7.length() > 0))
      {
        localObject21 = new ValueInfo();
        ((ValueInfo)localObject21).setValue(str7);
        ((ValueInfo)localObject21).setAction("add");
        localPayment.put("REMITTANCE_INFORMATION", localObject21);
      }
    }
    Object localObject16;
    if (paramTypeMT103.DetailsOfCharges_71A != null)
    {
      localObject16 = paramTypeMT103.DetailsOfCharges_71A.Code;
      if ((localObject16 != null) && (((String)localObject16).length() > 0))
      {
        localObject18 = new ValueInfo();
        ((ValueInfo)localObject18).setValue(localObject16);
        ((ValueInfo)localObject18).setAction("add");
        localPayment.put("DETAILS_OF_CHARGES", localObject18);
      }
    }
    Object localObject20;
    if (paramTypeMT103.SendersCharges_71FExists)
    {
      localObject16 = new StringBuffer();
      localObject18 = paramTypeMT103.SendersCharges_71F;
      if (localObject18 != null) {
        for (int i5 = 0; i5 < localObject18.length; i5++) {
          if (localObject18[i5] != null)
          {
            ((StringBuffer)localObject16).append(localObject18[i5].Currency);
            ((StringBuffer)localObject16).append(localObject18[i5].Amount);
            ((StringBuffer)localObject16).append(" ");
          }
        }
      }
      if (((StringBuffer)localObject16).length() > 0)
      {
        localObject20 = new ValueInfo();
        ((ValueInfo)localObject20).setValue(((StringBuffer)localObject16).toString().trim());
        ((ValueInfo)localObject20).setAction("add");
        localPayment.put("SENDERS_CHARGES", localObject20);
      }
    }
    if (paramTypeMT103.ReceiversCharges_71GExists)
    {
      localObject16 = new StringBuffer();
      ((StringBuffer)localObject16).append(paramTypeMT103.ReceiversCharges_71G.Currency);
      ((StringBuffer)localObject16).append(paramTypeMT103.ReceiversCharges_71G.Amount);
      if (((StringBuffer)localObject16).length() > 0)
      {
        localObject18 = new ValueInfo();
        ((ValueInfo)localObject18).setValue(((StringBuffer)localObject16).toString());
        ((ValueInfo)localObject18).setAction("add");
        localPayment.put("RECEIVERS_CHARGES", localObject18);
      }
    }
    if (paramTypeMT103.SenderToReceiverInfo_72Exists)
    {
      localObject16 = paramTypeMT103.SenderToReceiverInfo_72.__memberName;
      if (localObject16 != null)
      {
        localObject18 = new ValueInfo();
        ((ValueInfo)localObject18).setValue(localObject16);
        ((ValueInfo)localObject18).setAction("add");
        localPayment.put("SENDER_TO_RECEIVER_MEMBER_NAME", localObject18);
      }
      if (paramTypeMT103.SenderToReceiverInfo_72.__preValueTag.equals("72"))
      {
        localObject18 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.Code;
        if (localObject18 != null)
        {
          localObject20 = new ValueInfo();
          ((ValueInfo)localObject20).setValue(localObject18);
          ((ValueInfo)localObject20).setAction("add");
          localPayment.put("SENDER_TO_RECEIVER_CODE", localObject20);
        }
        localObject20 = "";
        localObject21 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.NarrativeText.Line;
        for (int i8 = 0; i8 < localObject21.length; i8++) {
          localObject20 = (String)localObject20 + localObject21[i8] + ",";
        }
        if (((String)localObject20).substring(((String)localObject20).length() - 1).equals(",")) {
          localObject20 = ((String)localObject20).substring(0, ((String)localObject20).length() - 1);
        }
        if (((String)localObject20).length() > 0)
        {
          localObject22 = new ValueInfo();
          ((ValueInfo)localObject22).setValue(localObject20);
          ((ValueInfo)localObject22).setAction("add");
          localPayment.put("SENDER_TO_RECEIVER_INFO", localObject22);
        }
        if (paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.AdditionalInformationExists)
        {
          localObject22 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.AdditionalInformation;
          if ((localObject22 != null) && (((String)localObject22).length() > 0))
          {
            localObject23 = new ValueInfo();
            ((ValueInfo)localObject23).setValue(localObject22);
            ((ValueInfo)localObject23).setAction("add");
            localPayment.put("SENDER_TO_RECEIVER_ADDITIONAL_INFO", localObject23);
          }
        }
        Object localObject22 = "";
        localObject23 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.AdditionalLines;
        if ((localObject23 != null) && (localObject23.length > 0))
        {
          for (int i9 = 0; i9 < localObject23.length; i9++)
          {
            Object localObject24 = localObject23[i9];
            localObject22 = (String)localObject22 + localObject24.Line + "\n";
          }
          if (((String)localObject22).substring(((String)localObject22).length() - 1).equals("\n")) {
            localObject22 = ((String)localObject22).substring(0, ((String)localObject22).length() - 1);
          }
          ValueInfo localValueInfo8 = new ValueInfo();
          localValueInfo8.setValue(localObject22);
          localValueInfo8.setAction("add");
          localPayment.put("SENDER_TO_RECEIVER_ADDITIONAL_LINES", localValueInfo8);
        }
      }
      else
      {
        localObject18 = "";
        localObject20 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF2_72.Narrative.Line;
        for (int i7 = 0; i7 < localObject20.length; i7++) {
          localObject18 = (String)localObject18 + localObject20[i7] + ",";
        }
        if (((String)localObject18).substring(((String)localObject18).length() - 1).equals(",")) {
          localObject18 = ((String)localObject18).substring(0, ((String)localObject18).length() - 1);
        }
        if (((String)localObject18).length() > 0)
        {
          ValueInfo localValueInfo6 = new ValueInfo();
          localValueInfo6.setValue(localObject18);
          localValueInfo6.setAction("add");
          localPayment.put("SENDER_TO_RECEIVER_INFO", localValueInfo6);
        }
      }
    }
    if (paramTypeMT103.RegulatoryReporting_77BExists)
    {
      localObject16 = paramTypeMT103.RegulatoryReporting_77B.Narrative.Line;
      int i2 = 0;
      for (localObject20 = ""; localObject16.length > i2; localObject20 = (String)localObject20 + localObject16[(i2++)]) {}
      localPayment.setMemo((String)localObject20);
    }
    localPayment.setPaymentType("CURRENT");
    localPayment.mapPmtStatusToInt("WILLPROCESSON");
    return localPayment;
  }
  
  public Object parse(IMBMessage paramIMBMessage)
    throws SWIFTParseException
  {
    Payment localPayment = null;
    if (paramIMBMessage == null)
    {
      localObject = new Payments();
      localPayment = (Payment)((Payments)localObject).create();
      return localPayment;
    }
    Object localObject = (TypeMT103)paramIMBMessage.getIDLInstance();
    localPayment = mapMT103ToPayment((TypeMT103)localObject);
    return localPayment;
  }
  
  public IMBMessage build(Object paramObject)
    throws SWIFTParseException
  {
    if (paramObject == null)
    {
      localObject = "bean object passed found null";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    if (!(paramObject instanceof Payment))
    {
      localObject = "bean object passed should be instance of com.ffusion.beans.billpay.Payment";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    Object localObject = (Payment)paramObject;
    TypeMT103 localTypeMT103 = mapPaymentToMT103((Payment)localObject);
    IMBMessage localIMBMessage = null;
    try
    {
      localIMBMessage = MBMessageFactory.createIDLMessage("SWIFT", "MT103", localTypeMT103);
    }
    catch (Exception localException)
    {
      String str = "Error while building IMBMessage from TypeMT103 object:" + localException.getMessage();
      DebugLog.throwing(str, localException);
      throw new SWIFTParseException(localException, str);
    }
    return localIMBMessage;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.MT103PaymentMapper
 * JD-Core Version:    0.7.0.1
 */