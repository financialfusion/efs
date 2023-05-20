package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.Contact;
import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.Transfers;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeBasicHeader;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeHeader;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class MT103TransferMapper
  implements ISWIFTMapper
{
  public TypeMT103 mapTransferToMT103(Transfer paramTransfer)
    throws SWIFTParseException
  {
    TypeMT103 localTypeMT103 = new TypeMT103();
    localTypeMT103.header = new TypeHeader();
    localTypeMT103.header.BasicHeader = new TypeBasicHeader();
    localTypeMT103.header.BasicHeader.ApplicationID = "F";
    localTypeMT103.header.BasicHeader.ServiceID = "01";
    localTypeMT103.header.BasicHeader.LTAddr = "THISISATXEST";
    localTypeMT103.header.BasicHeader.SessionNum = 1000;
    localTypeMT103.header.BasicHeader.SequenceNum = 123456;
    if (paramTransfer.get("SENDERS_REFERENCE") != null)
    {
      localTypeMT103.SendersRef_20 = new TypeTransRefNum_20();
      localTypeMT103.SendersRef_20.TransRefNum = ((String)((ValueInfo)paramTransfer.get("SENDERS_REFERENCE")).getValue());
    }
    else
    {
      localTypeMT103.SendersRef_20 = new TypeTransRefNum_20();
      localTypeMT103.SendersRef_20.TransRefNum = "0123456789ABCDEF";
    }
    Object localObject2;
    Object localObject3;
    if (paramTransfer.get("TIME_INDICATION") != null)
    {
      localObject1 = (ValueInfo)paramTransfer.get("TIME_INDICATION");
      localObject2 = (String)((ValueInfo)localObject1).getValue();
      localObject3 = ((String)localObject2).split(" ");
      try
      {
        localTypeMT103.TimeIndication_13C[0].Code = localObject3[0];
        localTypeMT103.TimeIndication_13C[0].Time = localObject3[1];
        localTypeMT103.TimeIndication_13C[0].Sign = localObject3[2];
        localTypeMT103.TimeIndication_13C[0].TimeOffset = localObject3[3];
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1) {}
      localTypeMT103.TimeIndication_13CExists = true;
    }
    if (paramTransfer.get("BANK_OPERATION_CODE") != null)
    {
      localObject1 = (ValueInfo)paramTransfer.get("BANK_OPERATION_CODE");
      localTypeMT103.BankOperationCode_23B = new TypeBankOperationCode_23B();
      localTypeMT103.BankOperationCode_23B.Type = ((String)((ValueInfo)localObject1).getValue());
    }
    else
    {
      localTypeMT103.BankOperationCode_23B = new TypeBankOperationCode_23B();
      localTypeMT103.BankOperationCode_23B.Type = "CRED";
    }
    Object localObject6;
    if (paramTransfer.get("PAY_INSTRUCT") != null)
    {
      localObject1 = (ValueInfo)paramTransfer.get("PAY_INSTRUCT");
      localObject2 = ((String)((ValueInfo)localObject1).getValue()).trim();
      localObject3 = null;
      localObject6 = null;
      if ((localObject2 != null) && (((String)localObject2).length() > 0))
      {
        localObject3 = ((String)localObject2).split(" ");
        localObject6 = new TypeInstructionCode_23E[localObject3.length];
      }
      if (localObject3 != null) {
        for (int n = 0; n < localObject3.length; n++)
        {
          localObject6[n] = new TypeInstructionCode_23E();
          if (localObject3[n].indexOf("/") > 0)
          {
            String[] arrayOfString5 = localObject3[n].split("/");
            try
            {
              localObject6[n].Type = arrayOfString5[0];
              localObject6[n].Narrative = arrayOfString5[1];
            }
            catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2) {}
            localObject6[n].NarrativeExists = true;
          }
          else
          {
            localObject6[n].Type = localObject3[n];
            localObject6[n].NarrativeExists = false;
          }
        }
      }
      if (localObject6 != null) {
        localTypeMT103.InstructionCode_23EExists = true;
      } else {
        localTypeMT103.InstructionCode_23EExists = false;
      }
      localTypeMT103.InstructionCode_23E = ((TypeInstructionCode_23E[])localObject6);
    }
    if (paramTransfer.get("TRANSACTION_TYPE_CODE") != null)
    {
      localObject1 = null;
      localObject2 = (ValueInfo)paramTransfer.get("TRANSACTION_TYPE_CODE");
      localObject1 = (String)((ValueInfo)localObject2).getValue();
      localTypeMT103.TransTypeCode_26T.Type = ((String)((ValueInfo)localObject2).getValue());
      if (localObject1 != null) {
        localTypeMT103.TransTypeCode_26TExists = true;
      }
    }
    if ((paramTransfer.getSettlementDate() != null) || (paramTransfer.getAmtCurrency() != null) || (paramTransfer.getAmount() != null) || (paramTransfer.getDate() != null))
    {
      localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A = new TypeAmount_32A();
      localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date = paramTransfer.getSettlementDate();
      if ((localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date == null) || ("".equals(localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date.trim()))) {
        localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date = paramTransfer.getDate();
      }
      localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency = paramTransfer.getAmtCurrency();
      if (localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency == null) {
        localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency = paramTransfer.getAmountValue().getCurrencyCode();
      }
      if (localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency == null) {
        localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency = "USD";
      }
      localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Amount = paramTransfer.getAmountValue().getAmountValue().toString();
    }
    if ((paramTransfer.getOrigAmount() != null) || (paramTransfer.getOrigCurrency() != null))
    {
      localTypeMT103.CurrencyInstructedAmount_33B = new TypeAmount_33B();
      localTypeMT103.CurrencyInstructedAmount_33B.Price = paramTransfer.getOrigAmount();
      localTypeMT103.CurrencyInstructedAmount_33B.Currency = paramTransfer.getOrigCurrency();
      localTypeMT103.CurrencyInstructedAmount_33BExists = true;
    }
    if (paramTransfer.get("EXCHANGE_RATE") != null)
    {
      localTypeMT103.ExchangeRate_36Exists = true;
      localTypeMT103.ExchangeRate_36 = new TypeExchangeRate_36();
      localTypeMT103.ExchangeRate_36.Rate = ((String)((ValueInfo)paramTransfer.get("EXCHANGE_RATE")).getValue());
    }
    localTypeMT103.OrderingCustomer_50AK = new TypeCreditor_50AK();
    if (paramTransfer.get("ORDERING_CUSTOMER_MEMBER_NAME") != null)
    {
      localObject1 = (String)((ValueInfo)paramTransfer.get("ORDERING_CUSTOMER_MEMBER_NAME")).getValue();
      localTypeMT103.OrderingCustomer_50AK.__memberName = ((String)localObject1);
    }
    else
    {
      if ((paramTransfer.getFromAccount() != null) && (paramTransfer.getFromAccount().getRoutingNum() == null))
      {
        paramTransfer.getFromAccount().setRoutingNum("01234567");
        paramTransfer.getFromAccount().setBicAccount("01234567");
      }
      if (paramTransfer.getFromAccount().getRoutingNum() != null) {
        localTypeMT103.OrderingCustomer_50AK.__memberName = "Creditor_50A";
      } else {
        localTypeMT103.OrderingCustomer_50AK.__memberName = "Creditor_50K";
      }
    }
    Object localObject1 = paramTransfer.getFromAccount();
    Object localObject8;
    if (localObject1 != null)
    {
      localObject2 = ((Account)localObject1).getRoutingNum();
      localObject3 = ((Account)localObject1).getID();
      if ((localObject2 != null) && (((String)localObject2).length() > 0))
      {
        localTypeMT103.OrderingCustomer_50AK.__preValueTag = "50A";
        localTypeMT103.OrderingCustomer_50AK.Creditor_50A = new TypeCreditor_50A();
        if (localObject3 != null) {
          localTypeMT103.OrderingCustomer_50AK.Creditor_50A.Account = ((String)localObject3);
        } else {
          localTypeMT103.OrderingCustomer_50AK.Creditor_50A.Account = paramTransfer.getFromAccountNum();
        }
        if (localTypeMT103.OrderingCustomer_50AK.Creditor_50A.Account != null) {
          localTypeMT103.OrderingCustomer_50AK.Creditor_50A.AccountExists = true;
        }
        localTypeMT103.OrderingCustomer_50AK.Creditor_50A.BIC = ((String)localObject2);
        if (((String)localObject2).length() > 8)
        {
          localTypeMT103.OrderingCustomer_50AK.Creditor_50A.BIC = ((String)localObject2).substring(0, 8);
          if (!((String)localObject2).endsWith("XXX"))
          {
            localTypeMT103.OrderingCustomer_50AK.Creditor_50A.BICBranchCodeExists = true;
            localTypeMT103.OrderingCustomer_50AK.Creditor_50A.BICBranchCode = ((String)localObject2).substring(8);
          }
          else
          {
            localTypeMT103.OrderingCustomer_50AK.Creditor_50A.BICBranchCodeExists = false;
          }
        }
      }
      else
      {
        localTypeMT103.OrderingCustomer_50AK.__preValueTag = "50K";
        localTypeMT103.OrderingCustomer_50AK.Creditor_50K = new TypeCreditor_50K();
        if (localObject3 != null) {
          localTypeMT103.OrderingCustomer_50AK.Creditor_50K.Account = ((String)localObject3);
        } else {
          localTypeMT103.OrderingCustomer_50AK.Creditor_50K.Account = paramTransfer.getFromAccountNum();
        }
        if (localTypeMT103.OrderingCustomer_50AK.Creditor_50K.Account != null) {
          localTypeMT103.OrderingCustomer_50AK.Creditor_50K.AccountExists = true;
        }
        localObject6 = (ValueInfo)paramTransfer.get("ORDERING_CUSTOMER");
        if ((localObject6 != null) && (((String)((ValueInfo)localObject6).getValue()).trim().length() > 0))
        {
          localTypeMT103.OrderingCustomer_50AK.Creditor_50K.NameAddr = new TypeNarrative();
          localTypeMT103.OrderingCustomer_50AK.Creditor_50K.NameAddr.Line = ((String)((ValueInfo)localObject6).getValue()).split(",");
          if (localTypeMT103.OrderingCustomer_50AK.Creditor_50K.NameAddr.Line.length > 4)
          {
            localObject8 = new String[4];
            localObject8[3] = "";
            for (int i4 = 0; i4 < localTypeMT103.OrderingCustomer_50AK.Creditor_50K.NameAddr.Line.length; i4++) {
              if (i4 <= 3) {
                localObject8[i4] = localTypeMT103.OrderingCustomer_50AK.Creditor_50K.NameAddr.Line[i4];
              } else {
                localObject8[3] = (localObject8[3] + "," + localTypeMT103.OrderingCustomer_50AK.Creditor_50K.NameAddr.Line[i4]);
              }
            }
            localTypeMT103.OrderingCustomer_50AK.Creditor_50K.NameAddr.Line = ((String[])localObject8);
          }
          if (localTypeMT103.OrderingCustomer_50AK.__memberName == null) {
            localTypeMT103.OrderingCustomer_50AK.__memberName = localTypeMT103.OrderingCustomer_50AK.Creditor_50K.NameAddr.Line[0];
          }
        }
      }
    }
    if ((paramTransfer.get("SEND_INSTITUTION_NAME") != null) || (paramTransfer.get("SEND_INSTITUTION_BICCODE") != null))
    {
      localTypeMT103.SendingInsti_51A = new TypeSendingInsti_51A();
      localTypeMT103.SendingInsti_51AExists = true;
      localObject2 = (ValueInfo)paramTransfer.get("SEND_INSTITUTION_NAME");
      if (localObject2 != null) {
        localTypeMT103.SendingInsti_51A.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
      }
      localObject3 = (ValueInfo)paramTransfer.get("SEND_INSTITUTION_BICCODE");
      if (localObject3 != null)
      {
        localObject6 = (String)((ValueInfo)localObject3).getValue();
        localObject8 = "";
        if ((localObject6 != null) && (((String)localObject6).length() > 0))
        {
          if (((String)localObject6).length() == 11) {
            localObject8 = ((String)localObject6).substring(8);
          }
          if (!((String)localObject8).equals("XXX"))
          {
            localTypeMT103.SendingInsti_51A.BICBranchCodeExists = true;
            localTypeMT103.SendingInsti_51A.BICBranchCode = ((String)localObject8);
          }
          else
          {
            localTypeMT103.SendingInsti_51A.BICBranchCodeExists = false;
          }
          localTypeMT103.SendingInsti_51A.BIC = ((String)localObject6).substring(0, 8);
        }
      }
    }
    Object localObject9;
    if ((paramTransfer.get("ORDERING_INSTITUTION_ADDRESS") != null) || (paramTransfer.get("ORDERING_INSTITUTION_BIC") != null))
    {
      localTypeMT103.OrderingInstitution_52ADExists = true;
      localTypeMT103.OrderingInstitution_52AD = new TypeOrderingInstitution_52AD();
      if (paramTransfer.get("ORDERING_INSTITUTION_MEMBER_NAME") != null) {
        localTypeMT103.OrderingInstitution_52AD.__memberName = ((String)((ValueInfo)paramTransfer.get("ORDERING_INSTITUTION_MEMBER_NAME")).getValue());
      }
      if (paramTransfer.get("ORDERING_INSTITUTION_BIC") != null)
      {
        localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A = new TypeOrderingInstitution_52A();
        localTypeMT103.OrderingInstitution_52AD.__preValueTag = "52A";
        localObject2 = null;
        localObject3 = (ValueInfo)paramTransfer.get("ORDERING_INSTITUTION_BIC");
        if (localObject3 != null) {
          localObject2 = ((String)((ValueInfo)localObject3).getValue()).trim();
        }
        if (((String)localObject2).length() > 8)
        {
          if (!"XXX".equals(((String)localObject2).substring(8)))
          {
            localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.BICBranchCodeExists = true;
            localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.BICBranchCode = ((String)localObject2).substring(8);
          }
          else
          {
            localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.BICBranchCodeExists = false;
          }
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.BIC = ((String)localObject2).substring(0, 8);
        }
        else
        {
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.BICBranchCodeExists = false;
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.BIC = ((String)localObject2);
        }
        if (paramTransfer.get("PARTY_IDENTIFIER_52") != null)
        {
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.PartyExists = true;
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.Party = new TypeParty();
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.Party.PartyIdentifierExists = true;
          localObject6 = (ValueInfo)paramTransfer.get("PARTY_IDENTIFIER_52");
          if (localObject6 != null) {
            localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52A.Party.PartyIdentifier = ((String)((ValueInfo)localObject6).getValue());
          }
        }
      }
      else
      {
        localTypeMT103.OrderingInstitution_52AD.__preValueTag = "52D";
        localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D = new TypeOrderingInstitution_52D();
        localObject2 = (ValueInfo)paramTransfer.get("PARTY_IDENTIFIER_52");
        localObject3 = null;
        if (localObject2 != null) {
          localObject3 = (String)((ValueInfo)localObject2).getValue();
        }
        if (localObject3 != null)
        {
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.PartyExists = true;
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.Party = new TypeParty();
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.Party.PartyIdentifierExists = true;
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.Party.PartyIdentifier = ((String)localObject3);
        }
        else
        {
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.PartyExists = false;
        }
        localObject6 = (ValueInfo)paramTransfer.get("ORDERING_INSTITUTION_NAME");
        localObject8 = null;
        if (localObject6 != null) {
          localObject8 = (String)((ValueInfo)localObject6).getValue();
        }
        if ((localObject8 != null) && (((String)localObject8).trim().length() == 0)) {
          localObject8 = null;
        }
        if (paramTransfer.get("ORDERING_INSTITUTION_ADDRESS") != null)
        {
          localObject9 = (ValueInfo)paramTransfer.get("ORDERING_INSTITUTION_ADDRESS");
          String[] arrayOfString7 = null;
          if (localObject9 != null) {
            arrayOfString7 = ((String)((ValueInfo)localObject9).getValue()).split(",");
          }
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.NameAddr = new TypeNarrative();
          int i8 = arrayOfString7.length;
          int i9 = 0;
          if (localObject8 != null) {
            i8++;
          }
          localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.NameAddr.Line = new String[4];
          if (localObject8 != null)
          {
            localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.NameAddr.Line[0] = localObject8;
            i9 = 1;
          }
          for (int i10 = 0; i9 < i8; i10++)
          {
            if (i9 <= 3) {
              localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.NameAddr.Line[i9] = arrayOfString7[i10];
            } else {
              localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.NameAddr.Line[3] = (localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.NameAddr.Line[3] + "," + arrayOfString7[i10]);
            }
            i9++;
          }
          if (localTypeMT103.OrderingInstitution_52AD.__memberName == null) {
            localTypeMT103.OrderingInstitution_52AD.__memberName = localTypeMT103.OrderingInstitution_52AD.OrderingInstitution_52D.NameAddr.Line[0];
          }
        }
      }
    }
    int k;
    int i1;
    int i7;
    if ((paramTransfer.get("SENDER_CORRESPONDENT_BIC") != null) || (paramTransfer.get("SENDER_CORRESPONDENT_LOCATION") != null) || (paramTransfer.get("SENDER_CORRESPONDENT_PARTY_ID") != null) || (paramTransfer.get("SENDER_CORRESPONDENT_ADDRESS") != null))
    {
      localTypeMT103.SendersCorspdt_53ABDExists = true;
      localTypeMT103.SendersCorspdt_53ABD = new TypeSendersCorspdt_53ABD();
      if (paramTransfer.get("SENDER_CORRESPONDENT_BIC") != null)
      {
        localTypeMT103.SendersCorspdt_53ABD.__preValueTag = "53A";
        localTypeMT103.SendersCorspdt_53ABD.__memberName = "SendersCorspdt_53A";
        localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A = new TypeSendersCorspdt_53A();
        if (paramTransfer.get("SENDER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A.PartyExists = true;
          localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("SENDER_CORRESPONDENT_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localObject2 = (ValueInfo)paramTransfer.get("SENDER_CORRESPONDENT_BIC");
        if (localObject2 != null)
        {
          localObject3 = (String)((ValueInfo)localObject2).getValue();
          if (((String)localObject3).length() > 8) {
            if (!"XXX".equals(((String)localObject3).substring(8)))
            {
              localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A.BICBranchCodeExists = true;
              localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A.BICBranchCode = ((String)localObject3).substring(8);
            }
            else
            {
              localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A.BICBranchCodeExists = false;
            }
          }
          localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53A.BIC = ((String)localObject3).substring(0, 8);
        }
      }
      else if (paramTransfer.get("SENDER_CORRESPONDENT_ADDRESS") != null)
      {
        localTypeMT103.SendersCorspdt_53ABD.__preValueTag = "53D";
        localTypeMT103.SendersCorspdt_53ABD.__memberName = "SendersCorspdt_53D";
        localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53D = new TypeSendersCorspdt_53D();
        if (paramTransfer.get("SENDER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53D.PartyExists = true;
          localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53D.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("SENDER_CORRESPONDENT_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53D.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localObject2 = (ValueInfo)paramTransfer.get("SENDER_CORRESPONDENT_ADDRESS");
        localObject3 = null;
        if (localObject2 != null) {
          localObject3 = ((String)((ValueInfo)localObject2).getValue()).split(",");
        }
        k = localObject3.length;
        i1 = 0;
        if (paramTransfer.get("SENDER_CORRESPONDENT_NAME") != null) {
          k++;
        }
        localObject9 = new String[k];
        if (paramTransfer.get("SENDER_CORRESPONDENT_NAME") != null)
        {
          localObject9[0] = ((String)((ValueInfo)paramTransfer.get("SENDER_CORRESPONDENT_NAME")).getValue());
          i1++;
        }
        for (i7 = 0; i1 < k; i7++)
        {
          if (i1 <= 3) {
            localObject9[i1] = localObject3[i7];
          } else {
            localObject9[3] = (localObject9[3] + localObject3[i7]);
          }
          i1++;
        }
        localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53D.NameAddr = new TypeNarrative();
        localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53D.NameAddr.Line = ((String[])localObject9);
      }
      else if ((paramTransfer.get("SENDER_CORRESPONDENT_LOCATION") != null) || (paramTransfer.get("SENDER_CORRESPONDENT_PARTY_ID") != null))
      {
        localTypeMT103.SendersCorspdt_53ABD.__preValueTag = "53B";
        localTypeMT103.SendersCorspdt_53ABD.__memberName = "SendersCorspdt_53B";
        localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53B = new TypeSendersCorspdt_53B();
        if (paramTransfer.get("SENDER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53B.PartyExists = true;
          localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53B.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("SENDER_CORRESPONDENT_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53B.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localObject2 = (ValueInfo)paramTransfer.get("SENDER_CORRESPONDENT_LOCATION");
        if (localObject2 != null) {
          localTypeMT103.SendersCorspdt_53ABD.SendersCorspdt_53B.Location = ((String)((ValueInfo)localObject2).getValue());
        }
      }
    }
    if ((paramTransfer.get("RECEIVER_CORRESPONDENT_BIC") != null) || (paramTransfer.get("RECEIVER_CORRESPONDENT_LOCATION") != null) || (paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID") != null) || (paramTransfer.get("RECEIVER_CORRESPONDENT_ADDRESS") != null))
    {
      localTypeMT103.ReceiversCorspdt_54ABDExists = true;
      localTypeMT103.ReceiversCorspdt_54ABD = new TypeReceiversCorspdt_54ABD();
      if (paramTransfer.get("RECEIVER_CORRESPONDENT_BIC") != null)
      {
        localTypeMT103.ReceiversCorspdt_54ABD.__preValueTag = "54A";
        localTypeMT103.ReceiversCorspdt_54ABD.__memberName = "ReceiversCorspdt_54A";
        localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A = new TypeReceiversCorspdt_54A();
        if (paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A.PartyExists = true;
          localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localObject2 = (ValueInfo)paramTransfer.get("RECEIVER_CORRESPONDENT_BIC");
        if (localObject2 != null)
        {
          localObject3 = (String)((ValueInfo)localObject2).getValue();
          if (((String)localObject3).length() > 8) {
            if (!"XXX".equals(((String)localObject3).substring(8)))
            {
              localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A.BICBranchCodeExists = true;
              localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A.BICBranchCode = ((String)localObject3).substring(8);
            }
            else
            {
              localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A.BICBranchCodeExists = false;
            }
          }
          localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54A.BIC = ((String)localObject3).substring(0, 8);
        }
      }
      else if (paramTransfer.get("RECEIVER_CORRESPONDENT_ADDRESS") != null)
      {
        localTypeMT103.ReceiversCorspdt_54ABD.__preValueTag = "54D";
        localTypeMT103.ReceiversCorspdt_54ABD.__memberName = "ReceiversCorspdt_54D";
        localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54D = new TypeReceiversCorspdt_54D();
        if (paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54D.PartyExists = true;
          localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54D.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54D.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localObject2 = null;
        localObject3 = (ValueInfo)paramTransfer.get("RECEIVER_CORRESPONDENT_ADDRESS");
        if (localObject3 != null) {
          localObject2 = ((String)((ValueInfo)localObject3).getValue()).split(",");
        }
        k = localObject2.length;
        i1 = 0;
        if (paramTransfer.get("RECEIVER_CORRESPONDENT_NAME") != null) {
          k++;
        }
        localObject9 = new String[k];
        if (paramTransfer.get("RECEIVER_CORRESPONDENT_NAME") != null)
        {
          localObject9[0] = ((String)((ValueInfo)paramTransfer.get("RECEIVER_CORRESPONDENT_NAME")).getValue());
          i1++;
        }
        for (i7 = 0; i1 < k; i7++)
        {
          if (i1 <= 3) {
            localObject9[i1] = localObject2[i7];
          } else {
            localObject9[3] = (localObject9[3] + localObject2[i7]);
          }
          i1++;
        }
        localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54D.NameAddr = new TypeNarrative();
        localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54D.NameAddr.Line = ((String[])localObject9);
      }
      else if ((paramTransfer.get("RECEIVER_CORRESPONDENT_LOCATION") != null) || (paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID") != null))
      {
        localTypeMT103.ReceiversCorspdt_54ABD.__preValueTag = "54B";
        localTypeMT103.ReceiversCorspdt_54ABD.__memberName = "ReceiversCorspdt_54B";
        localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54B = new TypeReceiversCorspdt_54B();
        if (paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54B.PartyExists = true;
          localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54B.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54B.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localTypeMT103.ReceiversCorspdt_54ABD.ReceiversCorspdt_54B.Location = ((String)paramTransfer.get("RECEIVER_CORRESPONDENT_LOCATION"));
      }
    }
    int i;
    int i5;
    if ((paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_BIC") != null) || (paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_LOCATION") != null) || (paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_PARTY_ID") != null) || (paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_ADDRESS") != null))
    {
      localTypeMT103.ThirdReimbursementInsti_55ABDExists = true;
      localTypeMT103.ThirdReimbursementInsti_55ABD = new TypeThirdReimbursementInsti_55ABD();
      if (paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_BIC") != null)
      {
        localTypeMT103.ThirdReimbursementInsti_55ABD.__preValueTag = "55A";
        localTypeMT103.ThirdReimbursementInsti_55ABD.__memberName = "ThirdReimbursementInsti_55A";
        localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A = new TypeThirdReimbursementInsti_55A();
        if (paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A.PartyExists = true;
          localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localObject2 = (String)((ValueInfo)paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_BIC")).getValue();
        if (((String)localObject2).length() > 8) {
          if (!"XXX".equals(((String)localObject2).substring(8)))
          {
            localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A.BICBranchCodeExists = true;
            localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A.BICBranchCode = ((String)localObject2).substring(8);
          }
          else
          {
            localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A.BICBranchCodeExists = false;
          }
        }
        localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A.BIC = ((String)localObject2).substring(0, 8);
      }
      else if (paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_ADDRESS") != null)
      {
        localTypeMT103.ThirdReimbursementInsti_55ABD.__preValueTag = "55D";
        localTypeMT103.ThirdReimbursementInsti_55ABD.__memberName = "ThirdReimbursementInsti_55D";
        localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55D = new TypeThirdReimbursementInsti_55D();
        if (paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55D.PartyExists = true;
          localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55D.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55D.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localObject2 = ((String)((ValueInfo)paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_ADDRESS")).getValue()).split(",");
        i = localObject2.length;
        k = 0;
        if (paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_NAME") != null) {
          i++;
        }
        String[] arrayOfString2 = new String[i];
        if (paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_NAME") != null)
        {
          arrayOfString2[0] = ((String)((ValueInfo)paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_NAME")).getValue());
          k++;
        }
        for (i5 = 0; k < i; i5++)
        {
          arrayOfString2[k] = localObject2[i5];
          k++;
        }
        localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55D.NameAddr = new TypeNarrative();
        localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55D.NameAddr.Line = arrayOfString2;
      }
      else if ((paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_LOCATION") != null) || (paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_PARTY_ID") != null))
      {
        localTypeMT103.ThirdReimbursementInsti_55ABD.__preValueTag = "55B";
        localTypeMT103.ThirdReimbursementInsti_55ABD.__memberName = "ThirdReimbursementInsti_55B";
        localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55B = new TypeThirdReimbursementInsti_55B();
        if (paramTransfer.get("RECEIVER_CORRESPONDENT_PARTY_ID") != null)
        {
          localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55B.PartyExists = true;
          localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55B.Party = new TypeParty();
          localObject2 = (ValueInfo)paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55B.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55B.Location = ((String)((ValueInfo)paramTransfer.get("THIRD_REIMBURSEMENT_INSTI_LOCATION")).getValue());
      }
    }
    int i2;
    if ((paramTransfer.get("INTERMEDIARY_PARTY_ID") != null) || (paramTransfer.get("INTERMEDIARY_BIC") != null) || (paramTransfer.get("INTERMEDIARY_ADDRESS") != null))
    {
      localTypeMT103.Intermediary_56ACDExists = true;
      localTypeMT103.Intermediary_56ACD = new TypeIntermediary_56ACD();
      if (paramTransfer.get("INTERMEDIARY_MEMBER_NAME") != null) {
        localTypeMT103.Intermediary_56ACD.__memberName = ((String)((ValueInfo)paramTransfer.get("INTERMEDIARY_MEMBER_NAME")).getValue());
      }
      if (paramTransfer.get("INTERMEDIARY_BIC") != null)
      {
        localTypeMT103.Intermediary_56ACD.__preValueTag = "56A";
        if (localTypeMT103.Intermediary_56ACD.__memberName == null) {
          localTypeMT103.Intermediary_56ACD.__memberName = "Intermediary_56A";
        }
        localTypeMT103.Intermediary_56ACD.Intermediary_56A = new TypeIntermediary_56A();
        if (paramTransfer.get("INTERMEDIARY_PARTY_ID") != null)
        {
          localTypeMT103.Intermediary_56ACD.Intermediary_56A.PartyExists = true;
          localTypeMT103.Intermediary_56ACD.Intermediary_56A.Party = new TypeParty();
          localTypeMT103.Intermediary_56ACD.Intermediary_56A.Party.PartyIdentifierExists = true;
          localObject2 = (ValueInfo)paramTransfer.get("INTERMEDIARY_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.Intermediary_56ACD.Intermediary_56A.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localObject2 = (String)((ValueInfo)paramTransfer.get("INTERMEDIARY_BIC")).getValue();
        if (((String)localObject2).length() == 11)
        {
          localTypeMT103.Intermediary_56ACD.Intermediary_56A.BICBranchCodeExists = true;
          localTypeMT103.Intermediary_56ACD.Intermediary_56A.BIC = ((String)localObject2).substring(0, 8);
          localTypeMT103.Intermediary_56ACD.Intermediary_56A.BICBranchCode = ((String)localObject2).substring(8);
        }
        else
        {
          localTypeMT103.Intermediary_56ACD.Intermediary_56A.BICBranchCodeExists = false;
          localTypeMT103.Intermediary_56ACD.Intermediary_56A.BIC = ((String)localObject2);
        }
      }
      else if (paramTransfer.get("INTERMEDIARY_ADDRESS") != null)
      {
        localTypeMT103.Intermediary_56ACD.__preValueTag = "56D";
        if (localTypeMT103.Intermediary_56ACD.__memberName == null) {
          localTypeMT103.Intermediary_56ACD.__memberName = "Intermediary_56D";
        }
        localTypeMT103.Intermediary_56ACD.Intermediary_56D = new TypeIntermediary_56D();
        if (paramTransfer.get("INTERMEDIARY_PARTY_ID") != null)
        {
          localTypeMT103.Intermediary_56ACD.Intermediary_56D.PartyExists = true;
          localTypeMT103.Intermediary_56ACD.Intermediary_56D.Party = new TypeParty();
          localTypeMT103.Intermediary_56ACD.Intermediary_56D.Party.PartyIdentifierExists = true;
          localObject2 = (ValueInfo)paramTransfer.get("INTERMEDIARY_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.Intermediary_56ACD.Intermediary_56D.Party.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
        localTypeMT103.Intermediary_56ACD.Intermediary_56D.NameAddr = new TypeNarrative();
        localObject2 = ((String)((ValueInfo)paramTransfer.get("INTERMEDIARY_ADDRESS")).getValue()).split(",");
        i = localObject2.length;
        if (paramTransfer.get("INTERMEDIARY_NAME") != null) {
          i++;
        }
        String[] arrayOfString1 = new String[i];
        i2 = 0;
        if (paramTransfer.get("INTERMEDIARY_NAME") != null)
        {
          arrayOfString1[0] = ((String)((ValueInfo)paramTransfer.get("INTERMEDIARY_NAME")).getValue());
          i2++;
        }
        for (i5 = 0; i2 < i; i5++)
        {
          if (i2 <= 3) {
            arrayOfString1[i2] = localObject2[i5];
          } else {
            arrayOfString1[3] = (arrayOfString1[3] + localObject2[i5]);
          }
          i2++;
        }
        localTypeMT103.Intermediary_56ACD.Intermediary_56D.NameAddr.Line = arrayOfString1;
      }
      else
      {
        localTypeMT103.Intermediary_56ACD.__preValueTag = "56C";
        if (localTypeMT103.Intermediary_56ACD.__memberName == null) {
          localTypeMT103.Intermediary_56ACD.__memberName = "Intermediary_56C";
        }
        localTypeMT103.Intermediary_56ACD.Intermediary_56C = new TypeIntermediary_56C();
        if (paramTransfer.get("INTERMEDIARY_PARTY_ID") != null)
        {
          localObject2 = (ValueInfo)paramTransfer.get("INTERMEDIARY_PARTY_ID");
          if (localObject2 != null) {
            localTypeMT103.Intermediary_56ACD.Intermediary_56C.PartyIdentifier = ((String)((ValueInfo)localObject2).getValue());
          }
        }
      }
    }
    int m;
    if ((paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID") != null) || (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_BIC") != null) || (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_ADDRESS") != null) || (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_LOCATION") != null))
    {
      localTypeMT103.AcctWithInsti_57ABCDExists = true;
      localTypeMT103.AcctWithInsti_57ABCD = new TypeAcctWithInsti_57ABCD();
      if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_MEMBER_NAME") != null)
      {
        localObject2 = (String)((ValueInfo)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_MEMBER_NAME")).getValue();
        localTypeMT103.AcctWithInsti_57ABCD.__memberName = ((String)localObject2);
      }
      Object localObject4;
      if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_BIC") != null)
      {
        localTypeMT103.AcctWithInsti_57ABCD.__preValueTag = "57A";
        localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A = new TypeAcctWithInsti_57A();
        if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID") != null)
        {
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.PartyExists = true;
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.Party = new TypeParty();
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.Party.PartyIdentifierExists = true;
          localObject2 = (ValueInfo)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID");
          localObject4 = (String)((ValueInfo)localObject2).getValue();
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.Party.PartyIdentifier = ((String)localObject4);
        }
        localObject2 = (String)((ValueInfo)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_BIC")).getValue();
        if (((String)localObject2).length() > 8)
        {
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.BICBranchCodeExists = true;
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.BIC = ((String)localObject2).substring(0, 8);
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.BICBranchCode = ((String)localObject2).substring(8);
        }
        else
        {
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.BICBranchCodeExists = false;
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.BIC = ((String)localObject2);
        }
      }
      else if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_LOCATION") != null)
      {
        localTypeMT103.AcctWithInsti_57ABCD.__preValueTag = "57B";
        localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57B = new TypeAcctWithInsti_57B();
        if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID") != null)
        {
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57B.PartyExists = true;
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57B.Party = new TypeParty();
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57B.Party.PartyIdentifierExists = true;
          localObject2 = (ValueInfo)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID");
          localObject4 = (String)((ValueInfo)localObject2).getValue();
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57B.Party.PartyIdentifier = ((String)localObject4);
        }
        localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57B.LocationExists = true;
        localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57B.Location = ((String)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_LOCATION"));
      }
      else if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_ADDRESS") != null)
      {
        localTypeMT103.AcctWithInsti_57ABCD.__preValueTag = "57D";
        localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57D = new TypeAcctWithInsti_57D();
        if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID") != null)
        {
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57D.PartyExists = true;
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57D.Party = new TypeParty();
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57D.Party.PartyIdentifierExists = true;
          localObject2 = (ValueInfo)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID");
          localObject4 = (String)((ValueInfo)localObject2).getValue();
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57D.Party.PartyIdentifier = ((String)localObject4);
        }
        localObject2 = (ValueInfo)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_ADDRESS");
        localObject4 = ((String)((ValueInfo)localObject2).getValue()).split(",");
        if (localObject4 != null)
        {
          m = localObject4.length;
          i2 = 0;
          if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_NAME") != null) {
            m++;
          }
          String[] arrayOfString6 = new String[m];
          if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_NAME") != null)
          {
            arrayOfString6[0] = ((String)((ValueInfo)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_NAME")).getValue());
            i2++;
          }
          for (i7 = 0; i2 < m; i7++)
          {
            if (i2 <= 3) {
              arrayOfString6[i2] = localObject4[i7];
            } else {
              arrayOfString6[3] = (arrayOfString6[3] + localObject4[i7]);
            }
            i2++;
          }
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57D.NameAddr = new TypeNarrative();
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57D.NameAddr.Line = arrayOfString6;
        }
      }
      else
      {
        localTypeMT103.AcctWithInsti_57ABCD.__preValueTag = "57C";
        localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57C = new TypeAcctWithInsti_57C();
        if (paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID") != null)
        {
          localObject2 = (ValueInfo)paramTransfer.get("ACCOUNT_WITH_INSTITUTION_PARTY_ID");
          localObject4 = (String)((ValueInfo)localObject2).getValue();
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57C.Id = ((String)localObject4);
        }
      }
    }
    Object localObject5;
    if ((paramTransfer.getToAccountNum() != null) || (paramTransfer.getToAccount() != null))
    {
      localTypeMT103.BeneficiaryCustomer_59AN = new TypeDebtor_59AN();
      if (paramTransfer.get("BENEFICIARY_MEMBER_NAME") != null)
      {
        localObject2 = null;
        localObject2 = (String)((ValueInfo)paramTransfer.get("BENEFICIARY_MEMBER_NAME")).getValue();
        localTypeMT103.BeneficiaryCustomer_59AN.__memberName = ((String)localObject2);
      }
      if ((paramTransfer.get("BENEFICIARY_ADDRESS") != null) || ((paramTransfer.getToAccount() != null) && (paramTransfer.getToAccount().getContact() != null)))
      {
        localTypeMT103.BeneficiaryCustomer_59AN.__preValueTag = "59N";
        localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59 = new TypeDebtor_59();
        if (paramTransfer.getToAccountNum() != null)
        {
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.AccountExists = true;
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.Account = paramTransfer.getToAccountNum();
        }
        if (paramTransfer.get("BENEFICIARY_ADDRESS") != null)
        {
          localObject2 = ((String)((ValueInfo)paramTransfer.get("BENEFICIARY_ADDRESS")).getValue()).split(",");
          int j = localObject2.length;
          m = 0;
          if (paramTransfer.get("BENEFICIARY_NAME") != null) {
            j++;
          }
          String[] arrayOfString3 = new String[j];
          if (paramTransfer.get("BENEFICIARY_NAME") != null)
          {
            arrayOfString3[0] = ((String)((ValueInfo)paramTransfer.get("BENEFICIARY_NAME")).getValue());
            m++;
          }
          for (int i6 = 0; m < j; i6++)
          {
            if (m <= 3) {
              arrayOfString3[m] = localObject2[i6];
            } else {
              arrayOfString3[3] = (arrayOfString3[3] + "," + localObject2[i6]);
            }
            m++;
          }
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.NameAddr = new TypeNarrative();
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.NameAddr.Line = arrayOfString3;
          if (localTypeMT103.BeneficiaryCustomer_59AN.__memberName == null) {
            localTypeMT103.BeneficiaryCustomer_59AN.__memberName = localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.NameAddr.Line[0];
          }
        }
        else if ((paramTransfer.getToAccount() != null) && (paramTransfer.getToAccount().getContact() != null))
        {
          localObject2 = new String[4];
          if (paramTransfer.get("BENEFICIARY_NAME") != null) {
            localObject2[0] = ((String)((ValueInfo)paramTransfer.get("BENEFICIARY_NAME")).getValue());
          } else {
            localObject2[0] = new String("");
          }
          localObject5 = paramTransfer.getToAccount().getContact();
          try
          {
            localObject2[1] = ((Contact)localObject5).getStreet();
            localObject2[2] = ((Contact)localObject5).getCity();
            localObject2[3] = ((Contact)localObject5).getCountry();
          }
          catch (Exception localException) {}
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.NameAddr = new TypeNarrative();
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.NameAddr.Line = ((String[])localObject2);
          if (localTypeMT103.BeneficiaryCustomer_59AN.__memberName == null) {
            localTypeMT103.BeneficiaryCustomer_59AN.__memberName = localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.NameAddr.Line[0];
          }
        }
        if (localTypeMT103.BeneficiaryCustomer_59AN.__memberName == null) {
          localTypeMT103.BeneficiaryCustomer_59AN.__memberName = "Debtor_59";
        }
      }
      else
      {
        localTypeMT103.BeneficiaryCustomer_59AN.__preValueTag = "59A";
        localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A = new TypeDebtor_59A();
        if ((paramTransfer.getToAccountNum() != null) || ((paramTransfer.getToAccount() != null) && (paramTransfer.getToAccount().getNumber() != null)))
        {
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.AccountExists = true;
          localObject2 = null;
          if (paramTransfer.getToAccountNum() != null) {
            localObject2 = paramTransfer.getToAccountNum();
          } else {
            localObject2 = paramTransfer.getToAccount().getNumber();
          }
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.Account = ((String)localObject2);
          if ((paramTransfer.getToAccount() != null) && (paramTransfer.getToAccount().getRoutingNum() != null)) {
            localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.BIC = paramTransfer.getToAccount().getRoutingNum();
          } else if ((paramTransfer.getToAccount() != null) && (paramTransfer.getToAccount().getBicAccount() != null)) {
            localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.BIC = paramTransfer.getToAccount().getBicAccount();
          } else {
            localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.BIC = "01234567";
          }
        }
        if (localTypeMT103.BeneficiaryCustomer_59AN.__memberName == null) {
          localTypeMT103.BeneficiaryCustomer_59AN.__memberName = "Debtor_59A";
        }
        if ((localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.Account == null) || ("".equals(localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.Account.trim()))) {
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.Account = "01234";
        }
        if ((localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.BIC == null) || ("".equals(localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.BIC.trim()))) {
          localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A.BIC = "01234567";
        }
      }
    }
    if (paramTransfer.get("REMITTANCE_INFORMATION") != null)
    {
      localObject2 = (ValueInfo)paramTransfer.get("REMITTANCE_INFORMATION");
      localObject5 = null;
      localObject5 = (String)((ValueInfo)localObject2).getValue();
      if ((localObject5 != null) && (((String)localObject5).trim().length() > 0))
      {
        localTypeMT103.RemittanceInformation_70Exists = true;
        localTypeMT103.RemittanceInformation_70 = new TypeDetailsPayment_70();
        localTypeMT103.RemittanceInformation_70.Narrative = new TypeNarrative();
        localTypeMT103.RemittanceInformation_70.Narrative.Line = ((String)localObject5).split("\n");
      }
    }
    if (paramTransfer.get("DETAILS_OF_CHARGES") != null)
    {
      localTypeMT103.DetailsOfCharges_71A = new TypeCode_71A();
      localObject2 = (ValueInfo)paramTransfer.get("DETAILS_OF_CHARGES");
      localObject5 = (String)((ValueInfo)localObject2).getValue();
      localTypeMT103.DetailsOfCharges_71A.Code = ((String)localObject5);
    }
    else
    {
      localTypeMT103.DetailsOfCharges_71A = new TypeCode_71A();
      localTypeMT103.DetailsOfCharges_71A.Code = "BEN";
    }
    Object localObject7;
    if (paramTransfer.get("SENDERS_CHARGES") != null)
    {
      localTypeMT103.SendersCharges_71FExists = true;
      localObject2 = (ValueInfo)paramTransfer.get("SENDERS_CHARGES");
      localObject5 = ((String)((ValueInfo)localObject2).getValue()).split(" ");
      localObject7 = new TypeDetailsOfCharges_71F[localObject5.length];
      for (int i3 = 0; i3 < localObject7.length; i3++)
      {
        localObject7[i3] = new TypeDetailsOfCharges_71F();
        localObject7[i3].Currency = localObject5[i3].substring(0, 3);
        localObject7[i3].Amount = localObject5[i3].substring(3);
      }
      localTypeMT103.SendersCharges_71F = ((TypeDetailsOfCharges_71F[])localObject7);
    }
    if (paramTransfer.get("RECEIVERS_CHARGES") != null)
    {
      localTypeMT103.ReceiversCharges_71GExists = true;
      localObject2 = (ValueInfo)paramTransfer.get("RECEIVERS_CHARGES");
      localObject5 = (String)((ValueInfo)localObject2).getValue();
      localTypeMT103.ReceiversCharges_71G = new TypeDetailsOfCharges_71G();
      localTypeMT103.ReceiversCharges_71G.Currency = ((String)localObject5).substring(0, 3);
      localTypeMT103.ReceiversCharges_71G.Amount = ((String)localObject5).substring(3);
    }
    if (paramTransfer.get("SENDER_TO_RECEIVER_INFO") != null)
    {
      localObject2 = (ValueInfo)paramTransfer.get("SENDER_TO_RECEIVER_INFO");
      if (((ValueInfo)localObject2).getValue() != null)
      {
        localTypeMT103.SenderToReceiverInfo_72Exists = true;
        localTypeMT103.SenderToReceiverInfo_72 = new TypeSenderToReceiverInfo_72();
        if (paramTransfer.get("SENDER_TO_RECEIVER_MEMBER_NAME") != null) {
          localTypeMT103.SenderToReceiverInfo_72.__memberName = ((String)((ValueInfo)paramTransfer.get("SENDER_TO_RECEIVER_MEMBER_NAME")).getValue());
        }
        localObject5 = (String)((ValueInfo)paramTransfer.get("SENDER_TO_RECEIVER_INFO")).getValue();
        if ((localObject5 != null) && (((String)localObject5).trim().length() > 0)) {
          if (paramTransfer.get("SENDER_TO_RECEIVER_CODE") != null)
          {
            localTypeMT103.SenderToReceiverInfo_72.__preValueTag = "72";
            localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72 = new TypeSenderToReceiverInfoF1_72();
            localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.Code = ((String)((ValueInfo)paramTransfer.get("SENDER_TO_RECEIVER_CODE")).getValue());
            localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.NarrativeText = new TypeNarrativeText();
            localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.NarrativeText.Line = ((String)localObject5).split(",");
            if (paramTransfer.get("SENDER_TO_RECEIVER_ADDITIONAL_INFO") != null)
            {
              localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.AdditionalInformationExists = true;
              localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.AdditionalInformation = ((String)((ValueInfo)paramTransfer.get("SENDER_TO_RECEIVER_ADDITIONAL_INFO")).getValue());
            }
            if (paramTransfer.get("SENDER_TO_RECEIVER_ADDITIONAL_LINES") != null)
            {
              localObject7 = (String)((ValueInfo)paramTransfer.get("SENDER_TO_RECEIVER_ADDITIONAL_LINES")).getValue();
              String[] arrayOfString4 = ((String)localObject7).split("\n");
              TypeAdditionalLines[] arrayOfTypeAdditionalLines = new TypeAdditionalLines[arrayOfString4.length];
              for (i7 = 0; i7 < arrayOfString4.length; i7++)
              {
                arrayOfTypeAdditionalLines[i7] = new TypeAdditionalLines();
                arrayOfTypeAdditionalLines[i7].Line = arrayOfString4[i7];
              }
            }
          }
          else
          {
            localTypeMT103.SenderToReceiverInfo_72.__preValueTag = "INFO2_72";
            localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF2_72 = new TypeSenderToReceiverInfoF2_72();
            localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF2_72.Narrative = new TypeNarrative();
            localTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF2_72.Narrative.Line = ((String)localObject5).split(",");
          }
        }
      }
    }
    if ((paramTransfer.getMemo() != null) && (paramTransfer.getMemo().trim().length() > 0))
    {
      localTypeMT103.RegulatoryReporting_77BExists = true;
      localTypeMT103.RegulatoryReporting_77B = new TypeNarrative_77B();
      localTypeMT103.RegulatoryReporting_77B.Narrative = new TypeNarrative();
      localTypeMT103.RegulatoryReporting_77B.Narrative.Line = null;
      localTypeMT103.RegulatoryReporting_77B.Narrative.Line = paramTransfer.getMemo().split("\n");
    }
    return localTypeMT103;
  }
  
  public Transfer mapMT103ToTransfer(TypeMT103 paramTypeMT103)
    throws SWIFTParseException
  {
    Transfers localTransfers = new Transfers();
    Transfer localTransfer = (Transfer)localTransfers.create();
    String str1 = paramTypeMT103.SendersRef_20.TransRefNum;
    if ((str1 != null) && (str1.length() > 0))
    {
      localObject1 = new ValueInfo();
      ((ValueInfo)localObject1).setValue(str1);
      ((ValueInfo)localObject1).setAction("add");
      localTransfer.put("SENDERS_REFERENCE", localObject1);
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
        localTransfer.put("TIME_INDICATION", localObject2);
      }
    }
    Object localObject1 = paramTypeMT103.BankOperationCode_23B.Type;
    if ((localObject1 != null) && (((String)localObject1).length() > 0))
    {
      localObject2 = new ValueInfo();
      ((ValueInfo)localObject2).setValue(localObject1);
      ((ValueInfo)localObject2).setAction("add");
      localTransfer.put("BANK_OPERATION_CODE", localObject2);
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
        localTransfer.put("PAY_INSTRUCT", localObject4);
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
        localTransfer.put("TRANSACTION_TYPE_CODE", localObject3);
      }
    }
    if (paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A != null)
    {
      localObject2 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date;
      localObject3 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency;
      localObject4 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Amount;
      localTransfer.setDateFormat("yyMMdd");
      localTransfer.setDate((String)localObject2);
      localTransfer.setSettlementDate((String)localObject2);
      localTransfer.setAmount(new BigDecimal((String)localObject4));
      localTransfer.setAmtCurrency((String)localObject3);
    }
    if (paramTypeMT103.CurrencyInstructedAmount_33BExists)
    {
      localTransfer.setOrigAmount(paramTypeMT103.CurrencyInstructedAmount_33B.Price);
      localTransfer.setOrigCurrency(paramTypeMT103.CurrencyInstructedAmount_33B.Currency);
    }
    if (paramTypeMT103.ExchangeRate_36Exists)
    {
      localObject2 = paramTypeMT103.ExchangeRate_36.Rate;
      localObject3 = new ValueInfo();
      ((ValueInfo)localObject3).setValue(localObject2);
      ((ValueInfo)localObject3).setAction("add");
      localTransfer.put("EXCHANGE_RATE", localObject3);
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
      localTransfer.put("ORDERING_CUSTOMER_MEMBER_NAME", localObject6);
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
    Object localObject6 = new Account();
    if (localObject3 != null)
    {
      localTransfer.setFromAccountNumber((String)localObject3);
      ((Account)localObject6).setNumber((String)localObject3);
    }
    if ((localObject5 != null) && (localObject5.length > 0))
    {
      if (localObject5.length > 4)
      {
        localObject7 = new String[4];
        for (j = 0; j < localObject5.length; j++) {
          if (j <= 3) {
            localObject7[j] = localObject5[j];
          } else {
            localObject7[3] = (localObject7[3] + localObject5[j]);
          }
        }
        localObject5 = localObject7;
      }
      localObject7 = "";
      for (int j = 0; j < localObject5.length; j++) {
        localObject7 = (String)localObject7 + localObject5[j] + ",";
      }
      if (((String)localObject7).endsWith(",")) {
        localObject7 = ((String)localObject7).substring(0, ((String)localObject7).length() - 1);
      }
      localObject8 = new ValueInfo();
      ((ValueInfo)localObject8).setValue(localObject7);
      ((ValueInfo)localObject8).setAction("add");
      localTransfer.put("ORDERING_CUSTOMER", localObject8);
      localContact = new Contact();
      try
      {
        localContact.setStreet(localObject5[1]);
        localContact.setCity(localObject5[2]);
        localContact.setCountry(localObject5[3]);
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1) {}
    }
    ((Account)localObject6).setRoutingNum((String)localObject4);
    ((Account)localObject6).setBicAccount((String)localObject4);
    ((Account)localObject6).setID((String)localObject3);
    ((Account)localObject6).setContact(localContact);
    localTransfer.setFromAccount((Account)localObject6);
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
          localTransfer.put("SEND_INSTITUTION_NAME", localObject8);
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
        localTransfer.put("SEND_INSTITUTION_BICCODE", localObject8);
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
        localTransfer.put("ORDERING_INSTITUTION_MEMBER_NAME", localObject9);
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
          localTransfer.put("PARTY_IDENTIFIER_52", localValueInfo1);
        }
        if ((localObject8 != null) && (((String)localObject8).length() > 0))
        {
          localValueInfo1 = new ValueInfo();
          localValueInfo1.setValue(localObject8);
          localValueInfo1.setAction("add");
          localTransfer.put("ORDERING_INSTITUTION_BIC", localValueInfo1);
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
          localTransfer.put("PARTY_IDENTIFIER_52", localValueInfo1);
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
            localTransfer.put("ORDERING_INSTITUTION_NAME", localValueInfo2);
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
            localTransfer.put("ORDERING_INSTITUTION_ADDRESS", localValueInfo2);
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
            localTransfer.put("SENDER_CORRESPONDENT_PARTY_ID", localValueInfo2);
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
          localTransfer.put("SENDER_CORRESPONDENT_BIC", localValueInfo2);
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
            localTransfer.put("SENDER_CORRESPONDENT_PARTY_ID", localValueInfo2);
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
            localTransfer.put("SENDER_CORRESPONDENT_LOCATION", localValueInfo2);
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
            localTransfer.put("SENDER_CORRESPONDENT_PARTY_ID", localValueInfo2);
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
              localTransfer.put("SENDER_CORRESPONDENT_NAME", localObject13);
            }
            while (localObject10.length > m) {
              str6 = str6.concat(localObject10[(m++)] + ",");
            }
            if (str6.substring(str6.length() - 1).equals(",")) {
              str6 = str6.substring(0, str6.length() - 1);
            }
            if ((str6 != null) && (str6.length() > 0))
            {
              localObject13 = new ValueInfo();
              ((ValueInfo)localObject13).setValue(str6);
              ((ValueInfo)localObject13).setAction("add");
              localTransfer.put("SENDER_CORRESPONDENT_ADDRESS", localObject13);
            }
          }
        }
      }
    }
    Object localObject11 = null;
    String str5 = null;
    String str6 = null;
    Object localObject12 = null;
    Object localObject16;
    Object localObject18;
    Object localObject20;
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
            localTransfer.put("RECEIVER_CORRESPONDENT_PARTY_ID", localValueInfo3);
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
          localTransfer.put("RECEIVER_CORRESPONDENT_BIC", localValueInfo3);
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
            localTransfer.put("RECEIVER_CORRESPONDENT_PARTY_ID", localValueInfo3);
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
            localTransfer.put("RECEIVER_CORRESPONDENT_LOCATION", localValueInfo3);
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
            localTransfer.put("RECEIVER_CORRESPONDENT_PARTY_ID", localValueInfo3);
          }
        }
        if (((TypeReceiversCorspdt_54D)localObject13).NameAddr != null) {
          localObject12 = ((TypeReceiversCorspdt_54D)localObject13).NameAddr.Line;
        }
        if ((localObject12 != null) && (localObject12.length > 0))
        {
          int n = 0;
          localObject16 = "";
          localObject18 = "";
          localObject18 = localObject12[(n++)];
          if ((localObject18 != null) && (((String)localObject18).length() > 0))
          {
            localObject20 = new ValueInfo();
            ((ValueInfo)localObject20).setValue(localObject18);
            ((ValueInfo)localObject20).setAction("add");
            localTransfer.put("RECEIVER_CORRESPONDENT_NAME", localObject20);
          }
          while (localObject12.length > n) {
            localObject16 = ((String)localObject16).concat(localObject12[(n++)] + ",");
          }
          if (((String)localObject16).substring(((String)localObject16).length() - 1).equals(",")) {
            localObject16 = ((String)localObject16).substring(0, ((String)localObject16).length() - 1);
          }
          if ((localObject16 != null) && (((String)localObject16).length() > 0))
          {
            localObject20 = new ValueInfo();
            ((ValueInfo)localObject20).setValue(localObject16);
            ((ValueInfo)localObject20).setAction("add");
            localTransfer.put("RECEIVER_CORRESPONDENT_ADDRESS", localObject20);
          }
        }
      }
    }
    Object localObject14;
    String str7;
    Object localObject25;
    ValueInfo localValueInfo8;
    if (paramTypeMT103.ThirdReimbursementInsti_55ABDExists)
    {
      localObject13 = null;
      localObject14 = null;
      localObject16 = null;
      localObject18 = null;
      ValueInfo localValueInfo6;
      if (paramTypeMT103.ThirdReimbursementInsti_55ABD.__preValueTag.equals("55A"))
      {
        localObject20 = paramTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55A;
        if (((TypeThirdReimbursementInsti_55A)localObject20).PartyExists)
        {
          localObject13 = ((TypeThirdReimbursementInsti_55A)localObject20).Party.PartyIdentifier;
          if ((localObject13 != null) && (((String)localObject13).length() > 0))
          {
            localValueInfo6 = new ValueInfo();
            localValueInfo6.setValue(localObject13);
            localValueInfo6.setAction("add");
            localTransfer.put("THIRD_REIMBURSEMENT_INSTI_PARTY_ID", localValueInfo6);
          }
        }
        localObject14 = ((TypeThirdReimbursementInsti_55A)localObject20).BIC;
        if (((TypeThirdReimbursementInsti_55A)localObject20).BICBranchCodeExists) {
          localObject14 = ((String)localObject14).concat(((TypeThirdReimbursementInsti_55A)localObject20).BICBranchCode);
        } else {
          localObject14 = ((String)localObject14).concat("XXX");
        }
        if ((localObject14 != null) && (((String)localObject14).length() > 0))
        {
          localValueInfo6 = new ValueInfo();
          localValueInfo6.setValue(localObject14);
          localValueInfo6.setAction("add");
          localTransfer.put("THIRD_REIMBURSEMENT_INSTI_BIC", localValueInfo6);
        }
      }
      else if (paramTypeMT103.ThirdReimbursementInsti_55ABD.__preValueTag.equals("55B"))
      {
        localObject20 = paramTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55B;
        if (((TypeThirdReimbursementInsti_55B)localObject20).PartyExists)
        {
          localObject13 = ((TypeThirdReimbursementInsti_55B)localObject20).Party.PartyIdentifier;
          if ((localObject13 != null) && (((String)localObject13).length() > 0))
          {
            localValueInfo6 = new ValueInfo();
            localValueInfo6.setValue(localObject13);
            localValueInfo6.setAction("add");
            localTransfer.put("THIRD_REIMBURSEMENT_INSTI_PARTY_ID", localValueInfo6);
          }
        }
        localObject16 = ((TypeThirdReimbursementInsti_55B)localObject20).Location;
        if ((localObject16 != null) && (((String)localObject16).length() > 0))
        {
          localValueInfo6 = new ValueInfo();
          localValueInfo6.setValue(localObject16);
          localValueInfo6.setAction("add");
          localTransfer.put("THIRD_REIMBURSEMENT_INSTI_LOCATION", localValueInfo6);
        }
      }
      else
      {
        localObject20 = paramTypeMT103.ThirdReimbursementInsti_55ABD.ThirdReimbursementInsti_55D;
        if (((TypeThirdReimbursementInsti_55D)localObject20).PartyExists)
        {
          localObject13 = ((TypeThirdReimbursementInsti_55D)localObject20).Party.PartyIdentifier;
          if ((localObject13 != null) && (((String)localObject13).length() > 0))
          {
            localValueInfo6 = new ValueInfo();
            localValueInfo6.setValue(localObject13);
            localValueInfo6.setAction("add");
            localTransfer.put("THIRD_REIMBURSEMENT_INSTI_PARTY_ID", localValueInfo6);
          }
        }
        if (((TypeThirdReimbursementInsti_55D)localObject20).NameAddr != null) {
          localObject18 = ((TypeThirdReimbursementInsti_55D)localObject20).NameAddr.Line;
        }
        if ((localObject18 != null) && (localObject18.length > 0))
        {
          int i8 = 0;
          str7 = "";
          localObject25 = "";
          localObject25 = localObject18[(i8++)];
          if ((localObject25 != null) && (((String)localObject25).length() > 0))
          {
            localValueInfo8 = new ValueInfo();
            localValueInfo8.setValue(localObject25);
            localValueInfo8.setAction("add");
            localTransfer.put("THIRD_REIMBURSEMENT_INSTI_NAME", localValueInfo8);
          }
          while (localObject18.length > i8) {
            str7 = str7.concat(localObject18[(i8++)] + ",");
          }
          if (str7.substring(str7.length() - 1).equals(",")) {
            str7 = str7.substring(0, str7.length() - 1);
          }
          if ((str7 != null) && (str7.length() > 0))
          {
            localValueInfo8 = new ValueInfo();
            localValueInfo8.setValue(str7);
            localValueInfo8.setAction("add");
            localTransfer.put("THIRD_REIMBURSEMENT_INSTI_ADDRESS", localValueInfo8);
          }
        }
      }
    }
    Object localObject22;
    if (paramTypeMT103.Intermediary_56ACDExists)
    {
      localObject13 = paramTypeMT103.Intermediary_56ACD;
      localObject14 = null;
      localObject14 = paramTypeMT103.Intermediary_56ACD.__memberName;
      if ((localObject14 != null) && (((String)localObject14).length() > 0))
      {
        localObject16 = new ValueInfo();
        ((ValueInfo)localObject16).setValue(localObject14);
        ((ValueInfo)localObject16).setAction("add");
        localTransfer.put("INTERMEDIARY_MEMBER_NAME", localObject16);
      }
      if (((TypeIntermediary_56ACD)localObject13).__preValueTag.equals("56A"))
      {
        localObject16 = ((TypeIntermediary_56ACD)localObject13).Intermediary_56A;
        if ((((TypeIntermediary_56A)localObject16).PartyExists) && (((TypeIntermediary_56A)localObject16).Party.PartyIdentifierExists))
        {
          localObject18 = ((TypeIntermediary_56A)localObject16).Party.PartyIdentifier;
          if ((localObject18 != null) && (((String)localObject18).length() > 0))
          {
            localObject20 = new ValueInfo();
            ((ValueInfo)localObject20).setValue(localObject18);
            ((ValueInfo)localObject20).setAction("add");
            localTransfer.put("INTERMEDIARY_PARTY_ID", localObject20);
          }
        }
        localObject18 = new StringBuffer(((TypeIntermediary_56A)localObject16).BIC);
        if (((TypeIntermediary_56A)localObject16).BICBranchCodeExists) {
          ((StringBuffer)localObject18).append(((TypeIntermediary_56A)localObject16).BICBranchCode);
        } else {
          ((StringBuffer)localObject18).append("XXX");
        }
        if ((localObject18 != null) && (((StringBuffer)localObject18).length() > 0))
        {
          localObject20 = new ValueInfo();
          ((ValueInfo)localObject20).setValue(((StringBuffer)localObject18).toString());
          ((ValueInfo)localObject20).setAction("add");
          localTransfer.put("INTERMEDIARY_BIC", localObject20);
        }
      }
      else if (((TypeIntermediary_56ACD)localObject13).__preValueTag.equals("56C"))
      {
        localObject16 = ((TypeIntermediary_56ACD)localObject13).Intermediary_56C;
        localObject18 = ((TypeIntermediary_56C)localObject16).PartyIdentifier;
        if ((localObject18 != null) && (((String)localObject18).length() > 0))
        {
          localObject20 = new ValueInfo();
          ((ValueInfo)localObject20).setValue(localObject18);
          ((ValueInfo)localObject20).setAction("add");
          localTransfer.put("INTERMEDIARY_PARTY_ID", localObject20);
        }
      }
      else
      {
        localObject16 = ((TypeIntermediary_56ACD)localObject13).Intermediary_56D;
        if ((((TypeIntermediary_56D)localObject16).PartyExists) && (((TypeIntermediary_56D)localObject16).Party.PartyIdentifierExists))
        {
          localObject18 = ((TypeIntermediary_56D)localObject16).Party.PartyIdentifier;
          if ((localObject18 != null) && (((String)localObject18).length() > 0))
          {
            localObject20 = new ValueInfo();
            ((ValueInfo)localObject20).setValue(localObject18);
            ((ValueInfo)localObject20).setAction("add");
            localTransfer.put("INTERMEDIARY_PARTY_ID", localObject20);
          }
        }
        localObject18 = ((TypeIntermediary_56D)localObject16).NameAddr.Line;
        if ((localObject18 != null) && (localObject18.length > 0))
        {
          int i5 = 0;
          localObject22 = "";
          str7 = "";
          localObject22 = localObject18[(i5++)];
          if ((localObject22 != null) && (((String)localObject22).length() > 0))
          {
            localObject25 = new ValueInfo();
            ((ValueInfo)localObject25).setValue(localObject22);
            ((ValueInfo)localObject25).setAction("add");
            localTransfer.put("INTERMEDIARY_NAME", localObject25);
          }
          while (localObject18.length > i5) {
            str7 = str7.concat(localObject18[(i5++)] + ",");
          }
          if (str7.substring(str7.length() - 1).equals(",")) {
            str7 = str7.substring(0, str7.length() - 1);
          }
          if ((str7 != null) && (str7.length() > 0))
          {
            localObject25 = new ValueInfo();
            ((ValueInfo)localObject25).setValue(str7);
            ((ValueInfo)localObject25).setAction("add");
            localTransfer.put("INTERMEDIARY_ADDRESS", localObject25);
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
        localObject16 = new ValueInfo();
        ((ValueInfo)localObject16).setValue(localObject14);
        ((ValueInfo)localObject16).setAction("add");
        localTransfer.put("ACCOUNT_WITH_INSTITUTION_MEMBER_NAME", localObject16);
      }
      ValueInfo localValueInfo4;
      if (((TypeAcctWithInsti_57ABCD)localObject13).__preValueTag.equals("57A"))
      {
        localObject16 = ((TypeAcctWithInsti_57ABCD)localObject13).AcctWithInsti_57A;
        if ((((TypeAcctWithInsti_57A)localObject16).PartyExists) && (((TypeAcctWithInsti_57A)localObject16).Party.PartyIdentifierExists))
        {
          localObject18 = ((TypeAcctWithInsti_57A)localObject16).Party.PartyIdentifier;
          if ((localObject18 != null) && (((String)localObject18).length() > 0))
          {
            localValueInfo4 = new ValueInfo();
            localValueInfo4.setValue(localObject18);
            localValueInfo4.setAction("add");
            localTransfer.put("ACCOUNT_WITH_INSTITUTION_PARTY_ID", localValueInfo4);
          }
        }
        localObject18 = new StringBuffer(((TypeAcctWithInsti_57A)localObject16).BIC);
        if (((TypeAcctWithInsti_57A)localObject16).BICBranchCodeExists) {
          ((StringBuffer)localObject18).append(((TypeAcctWithInsti_57A)localObject16).BICBranchCode);
        } else {
          ((StringBuffer)localObject18).append("XXX");
        }
        if ((localObject18 != null) && (((StringBuffer)localObject18).length() > 0))
        {
          localValueInfo4 = new ValueInfo();
          localValueInfo4.setValue(((StringBuffer)localObject18).toString());
          localValueInfo4.setAction("add");
          localTransfer.put("ACCOUNT_WITH_INSTITUTION_BIC", localValueInfo4);
        }
      }
      else if (((TypeAcctWithInsti_57ABCD)localObject13).__preValueTag.equals("57B"))
      {
        localObject16 = ((TypeAcctWithInsti_57ABCD)localObject13).AcctWithInsti_57B;
        if ((((TypeAcctWithInsti_57B)localObject16).PartyExists) && (((TypeAcctWithInsti_57B)localObject16).Party.PartyIdentifierExists))
        {
          localObject18 = ((TypeAcctWithInsti_57B)localObject16).Party.PartyIdentifier;
          if ((localObject18 != null) && (((String)localObject18).length() > 0))
          {
            localValueInfo4 = new ValueInfo();
            localValueInfo4.setValue(localObject18);
            localValueInfo4.setAction("add");
            localTransfer.put("ACCOUNT_WITH_INSTITUTION_PARTY_ID", localValueInfo4);
          }
        }
        if (((TypeAcctWithInsti_57B)localObject16).LocationExists)
        {
          localObject18 = ((TypeAcctWithInsti_57B)localObject16).Location;
          if ((localObject18 != null) && (((String)localObject18).length() > 0))
          {
            localValueInfo4 = new ValueInfo();
            localValueInfo4.setValue(localObject18);
            localValueInfo4.setAction("add");
            localTransfer.put("ACCOUNT_WITH_INSTITUTION_LOCATION", localValueInfo4);
          }
        }
      }
      else if (((TypeAcctWithInsti_57ABCD)localObject13).__preValueTag.equals("57C"))
      {
        localObject16 = ((TypeAcctWithInsti_57ABCD)localObject13).AcctWithInsti_57C;
        localObject18 = ((TypeAcctWithInsti_57C)localObject16).Id;
        if ((localObject18 != null) && (((String)localObject18).length() > 0))
        {
          localValueInfo4 = new ValueInfo();
          localValueInfo4.setValue(localObject18);
          localValueInfo4.setAction("add");
          localTransfer.put("ACCOUNT_WITH_INSTITUTION_PARTY_ID", localValueInfo4);
        }
      }
      else if (((TypeAcctWithInsti_57ABCD)localObject13).__preValueTag.equals("57D"))
      {
        localObject16 = ((TypeAcctWithInsti_57ABCD)localObject13).AcctWithInsti_57D;
        if ((((TypeAcctWithInsti_57D)localObject16).PartyExists) && (((TypeAcctWithInsti_57D)localObject16).Party.PartyIdentifierExists))
        {
          localObject18 = ((TypeAcctWithInsti_57D)localObject16).Party.PartyIdentifier;
          if ((localObject18 != null) && (((String)localObject18).length() > 0))
          {
            localValueInfo4 = new ValueInfo();
            localValueInfo4.setValue(localObject18);
            localValueInfo4.setAction("add");
            localTransfer.put("ACCOUNT_WITH_INSTITUTION_PARTY_ID", localValueInfo4);
          }
        }
        localObject18 = ((TypeAcctWithInsti_57D)localObject16).NameAddr.Line;
        if ((localObject18 != null) && (localObject18.length > 0))
        {
          int i6 = 0;
          localObject22 = "";
          str7 = "";
          localObject22 = localObject18[(i6++)];
          if ((localObject22 != null) && (((String)localObject22).length() > 0))
          {
            localObject25 = new ValueInfo();
            ((ValueInfo)localObject25).setValue(localObject22);
            ((ValueInfo)localObject25).setAction("add");
            localTransfer.put("ACCOUNT_WITH_INSTITUTION_NAME", localObject25);
          }
          while (localObject18.length > i6) {
            str7 = str7.concat(localObject18[(i6++)] + ",");
          }
          if (str7.substring(str7.length() - 1).equals(",")) {
            str7 = str7.substring(0, str7.length() - 1);
          }
          if ((str7 != null) && (str7.length() > 0))
          {
            localObject25 = new ValueInfo();
            ((ValueInfo)localObject25).setValue(str7);
            ((ValueInfo)localObject25).setAction("add");
            localTransfer.put("ACCOUNT_WITH_INSTITUTION_ADDRESS", localObject25);
          }
        }
      }
    }
    Object localObject13 = paramTypeMT103.BeneficiaryCustomer_59AN;
    if ((localObject13 != null) && (paramTypeMT103.BeneficiaryCustomer_59AN.__memberName != null))
    {
      localObject14 = null;
      localObject14 = paramTypeMT103.BeneficiaryCustomer_59AN.__memberName;
      if ((localObject14 != null) && (((String)localObject14).length() > 0))
      {
        localObject16 = new ValueInfo();
        ((ValueInfo)localObject16).setValue(localObject14);
        ((ValueInfo)localObject16).setAction("add");
        localTransfer.put("BENEFICIARY_MEMBER_NAME", localObject16);
      }
    }
    localTransfer.setToAccount(new Account());
    Object localObject21;
    if (((TypeDebtor_59AN)localObject13).__preValueTag.equals("59A"))
    {
      if (((TypeDebtor_59AN)localObject13).Debtor_59A.AccountExists)
      {
        localTransfer.setToAccountNumber(((TypeDebtor_59AN)localObject13).Debtor_59A.Account);
        localTransfer.getToAccount().setNumber(((TypeDebtor_59AN)localObject13).Debtor_59A.Account);
      }
      localObject14 = ((TypeDebtor_59AN)localObject13).Debtor_59A.BIC;
      if (((TypeDebtor_59AN)localObject13).Debtor_59A.BICBranchCodeExists) {
        localObject14 = (String)localObject14 + ((TypeDebtor_59AN)localObject13).Debtor_59A.BICBranchCode;
      }
      localTransfer.getToAccount().setBicAccount((String)localObject14);
      localTransfer.getToAccount().setRoutingNum((String)localObject14);
    }
    else
    {
      if (((TypeDebtor_59AN)localObject13).Debtor_59.AccountExists) {
        localTransfer.setToAccountNumber(((TypeDebtor_59AN)localObject13).Debtor_59.Account);
      }
      localObject14 = ((TypeDebtor_59AN)localObject13).Debtor_59.NameAddr.Line;
      if ((localObject14 != null) && (localObject14.length > 0))
      {
        int i2 = 0;
        localObject18 = "";
        localObject21 = "";
        localObject18 = localObject14[(i2++)];
        if ((localObject18 != null) && (((String)localObject18).length() > 0))
        {
          localObject22 = new ValueInfo();
          ((ValueInfo)localObject22).setValue(localObject18);
          ((ValueInfo)localObject22).setAction("add");
          localTransfer.put("BENEFICIARY_NAME", localObject22);
        }
        while (localObject14.length > i2) {
          localObject21 = ((String)localObject21).concat(localObject14[(i2++)] + ",");
        }
        if (((String)localObject21).substring(((String)localObject21).length() - 1).equals(",")) {
          localObject21 = ((String)localObject21).substring(0, ((String)localObject21).length() - 1);
        }
        if ((localObject21 != null) && (((String)localObject21).length() > 0))
        {
          localObject22 = new ValueInfo();
          ((ValueInfo)localObject22).setValue(localObject21);
          ((ValueInfo)localObject22).setAction("add");
          localTransfer.put("BENEFICIARY_ADDRESS", localObject22);
        }
        localObject22 = new Contact();
        try
        {
          ((Contact)localObject22).setStreet(localObject14[1]);
          ((Contact)localObject22).setCity(localObject14[2]);
          ((Contact)localObject22).setCountry(localObject14[3]);
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2) {}
        localTransfer.getToAccount().setContact((Contact)localObject22);
      }
    }
    Object localObject17;
    if (paramTypeMT103.RemittanceInformation_70Exists)
    {
      int i1 = 0;
      localObject17 = paramTypeMT103.RemittanceInformation_70.Narrative.Line;
      for (localObject18 = ""; localObject17.length > i1; localObject18 = (String)localObject18 + "\n" + localObject17[(i1++)]) {}
      if (((String)localObject18).startsWith("\n")) {
        localObject18 = ((String)localObject18).substring(1);
      }
      if ((localObject18 != null) && (((String)localObject18).length() > 0))
      {
        localObject21 = new ValueInfo();
        ((ValueInfo)localObject21).setValue(localObject18);
        ((ValueInfo)localObject21).setAction("add");
        localTransfer.put("REMITTANCE_INFORMATION", localObject21);
      }
    }
    if (paramTypeMT103.DetailsOfCharges_71A != null)
    {
      localObject15 = paramTypeMT103.DetailsOfCharges_71A.Code;
      if ((localObject15 != null) && (((String)localObject15).length() > 0))
      {
        localObject17 = new ValueInfo();
        ((ValueInfo)localObject17).setValue(localObject15);
        ((ValueInfo)localObject17).setAction("add");
        localTransfer.put("DETAILS_OF_CHARGES", localObject17);
      }
    }
    Object localObject19;
    if (paramTypeMT103.SendersCharges_71FExists)
    {
      localObject15 = new StringBuffer();
      localObject17 = paramTypeMT103.SendersCharges_71F;
      if (localObject17 != null) {
        for (int i4 = 0; i4 < localObject17.length; i4++) {
          if (localObject17[i4] != null)
          {
            ((StringBuffer)localObject15).append(localObject17[i4].Currency);
            ((StringBuffer)localObject15).append(localObject17[i4].Amount);
            ((StringBuffer)localObject15).append(" ");
          }
        }
      }
      if (((StringBuffer)localObject15).length() > 0)
      {
        localObject19 = new ValueInfo();
        ((ValueInfo)localObject19).setValue(((StringBuffer)localObject15).toString().trim());
        ((ValueInfo)localObject19).setAction("add");
        localTransfer.put("SENDERS_CHARGES", localObject19);
      }
    }
    if (paramTypeMT103.ReceiversCharges_71GExists)
    {
      localObject15 = new StringBuffer();
      ((StringBuffer)localObject15).append(paramTypeMT103.ReceiversCharges_71G.Currency);
      ((StringBuffer)localObject15).append(paramTypeMT103.ReceiversCharges_71G.Amount);
      if (((StringBuffer)localObject15).length() > 0)
      {
        localObject17 = new ValueInfo();
        ((ValueInfo)localObject17).setValue(((StringBuffer)localObject15).toString());
        ((ValueInfo)localObject17).setAction("add");
        localTransfer.put("RECEIVERS_CHARGES", localObject17);
      }
    }
    if (paramTypeMT103.SenderToReceiverInfo_72Exists)
    {
      localObject15 = paramTypeMT103.SenderToReceiverInfo_72.__memberName;
      if (localObject15 != null)
      {
        localObject17 = new ValueInfo();
        ((ValueInfo)localObject17).setValue(localObject15);
        ((ValueInfo)localObject17).setAction("add");
        localTransfer.put("SENDER_TO_RECEIVER_MEMBER_NAME", localObject17);
      }
      if (paramTypeMT103.SenderToReceiverInfo_72.__preValueTag.equals("72"))
      {
        localObject17 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.Code;
        if (localObject17 != null)
        {
          localObject19 = new ValueInfo();
          ((ValueInfo)localObject19).setValue(localObject17);
          ((ValueInfo)localObject19).setAction("add");
          localTransfer.put("SENDER_TO_RECEIVER_CODE", localObject19);
        }
        localObject19 = "";
        localObject21 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.NarrativeText.Line;
        for (int i9 = 0; i9 < localObject21.length; i9++) {
          localObject19 = (String)localObject19 + localObject21[i9] + ",";
        }
        if (((String)localObject19).substring(((String)localObject19).length() - 1).equals(",")) {
          localObject19 = ((String)localObject19).substring(0, ((String)localObject19).length() - 1);
        }
        if (((String)localObject19).length() > 0)
        {
          localObject23 = new ValueInfo();
          ((ValueInfo)localObject23).setValue(localObject19);
          ((ValueInfo)localObject23).setAction("add");
          localTransfer.put("SENDER_TO_RECEIVER_INFO", localObject23);
        }
        if (paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.AdditionalInformationExists)
        {
          localObject23 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.AdditionalInformation;
          if ((localObject23 != null) && (((String)localObject23).length() > 0))
          {
            localObject24 = new ValueInfo();
            ((ValueInfo)localObject24).setValue(localObject23);
            ((ValueInfo)localObject24).setAction("add");
            localTransfer.put("SENDER_TO_RECEIVER_ADDITIONAL_INFO", localObject24);
          }
        }
        Object localObject23 = "";
        Object localObject24 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF1_72.AdditionalLines;
        if ((localObject24 != null) && (localObject24.length > 0))
        {
          for (int i10 = 0; i10 < localObject24.length; i10++)
          {
            localValueInfo8 = localObject24[i10];
            localObject23 = (String)localObject23 + localValueInfo8.Line + "\n";
          }
          if (((String)localObject23).substring(((String)localObject23).length() - 1).equals("\n")) {
            localObject23 = ((String)localObject23).substring(0, ((String)localObject23).length() - 1);
          }
          ValueInfo localValueInfo7 = new ValueInfo();
          localValueInfo7.setValue(localObject23);
          localValueInfo7.setAction("add");
          localTransfer.put("SENDER_TO_RECEIVER_ADDITIONAL_LINES", localValueInfo7);
        }
      }
      else
      {
        localObject17 = "";
        localObject19 = paramTypeMT103.SenderToReceiverInfo_72.SenderToReceiverInfoF2_72.Narrative.Line;
        for (int i7 = 0; i7 < localObject19.length; i7++) {
          localObject17 = (String)localObject17 + localObject19[i7] + ",";
        }
        if (((String)localObject17).substring(((String)localObject17).length() - 1).equals(",")) {
          localObject17 = ((String)localObject17).substring(0, ((String)localObject17).length() - 1);
        }
        if (((String)localObject17).length() > 0)
        {
          ValueInfo localValueInfo5 = new ValueInfo();
          localValueInfo5.setValue(localObject17);
          localValueInfo5.setAction("add");
          localTransfer.put("SENDER_TO_RECEIVER_INFO", localValueInfo5);
        }
      }
    }
    if (paramTypeMT103.RegulatoryReporting_77BExists)
    {
      localObject15 = paramTypeMT103.RegulatoryReporting_77B.Narrative.Line;
      int i3 = 0;
      for (localObject19 = ""; localObject15.length > i3; localObject19 = (String)localObject19 + localObject15[(i3++)]) {}
      localTransfer.setMemo((String)localObject19);
    }
    localTransfer.setTransferType("CURRENT");
    localTransfer.mapXferStatusToInt("WILLPROCESSON");
    Object localObject15 = new SimpleDateFormat("yyyy/MM/dd");
    localTransfer.setCreateDate(((SimpleDateFormat)localObject15).format(new Date()));
    return localTransfer;
  }
  
  public Object parse(IMBMessage paramIMBMessage)
    throws SWIFTParseException
  {
    Transfer localTransfer = null;
    if (paramIMBMessage == null)
    {
      localObject = new Transfers();
      localTransfer = (Transfer)((Transfers)localObject).create();
      return localTransfer;
    }
    Object localObject = (TypeMT103)paramIMBMessage.getIDLInstance();
    localTransfer = mapMT103ToTransfer((TypeMT103)localObject);
    return localTransfer;
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
    if (!(paramObject instanceof Transfer))
    {
      localObject = "bean object passed should be instance of com.ffusion.beans.banking.Transfer";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    Object localObject = (Transfer)paramObject;
    if (!"yyMMdd".equals(((Transfer)localObject).getDateFormat())) {
      ((Transfer)localObject).setDateFormat("yyMMdd");
    }
    TypeMT103 localTypeMT103 = mapTransferToMT103((Transfer)localObject);
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
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.MT103TransferMapper
 * JD-Core Version:    0.7.0.1
 */