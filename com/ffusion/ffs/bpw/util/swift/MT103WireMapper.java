package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.Currency;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.msgbroker.factory.MBMessageFactory;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57ABCD;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeEnvelopeContents_77T;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeExchangeRate_36;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionCode_23E;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56ACD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeLine2;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeMT103;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeNarrative;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeNarrativeText;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52AD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeParty;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfoF1_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfoF2_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSenderToReceiverInfo_72;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeSendingInsti_51A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTimeIndication_13C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransRefNum_20;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransTypeCode_26T;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MT103WireMapper
  implements ISWIFTMapper
{
  private HashMap a = null;
  
  public TypeMT103 mapWireTransferToMT103(WireTransfer paramWireTransfer)
    throws SWIFTParseException
  {
    int i = 1;
    if (i != 0) {
      throw new SWIFTParseException("method MT103WireTransferMapper.mapWireTransferToMT103 is not implemented");
    }
    TypeMT103 localTypeMT103 = new TypeMT103();
    WireTransferPayee localWireTransferPayee = paramWireTransfer.getWirePayee();
    localTypeMT103.SendersRef_20.TransRefNum = paramWireTransfer.getComment();
    Object localObject2;
    Object localObject3;
    if (paramWireTransfer.get("TIME_INDICATION") != null)
    {
      localObject1 = (ValueInfo)paramWireTransfer.get("TIME_INDICATION");
      localObject2 = (String)((ValueInfo)localObject1).getValue();
      localObject3 = ((String)localObject2).split(" ");
      try
      {
        localTypeMT103.TimeIndication_13C[0].Code = localObject3[0];
        localTypeMT103.TimeIndication_13C[0].Time = localObject3[1];
        localTypeMT103.TimeIndication_13C[0].Sign = localObject3[2];
        localTypeMT103.TimeIndication_13C[0].TimeOffset = localObject3[3];
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
      localTypeMT103.TimeIndication_13CExists = true;
    }
    if (paramWireTransfer.get("BANK_OPERATION_CODE") != null)
    {
      localObject1 = (ValueInfo)paramWireTransfer.get("BANK_OPERATION_CODE");
      localTypeMT103.BankOperationCode_23B.Type = ((String)((ValueInfo)localObject1).getValue());
    }
    Object localObject4;
    if (paramWireTransfer.get("PAY_INSTRUCT") != null)
    {
      localObject1 = (ValueInfo)paramWireTransfer.get("PAY_INSTRUCT");
      localObject2 = ((String)((ValueInfo)localObject1).getValue()).trim();
      localObject3 = null;
      localObject4 = null;
      if ((localObject2 != null) && (((String)localObject2).length() > 0))
      {
        localObject3 = ((String)localObject2).split(" ");
        localObject4 = new TypeInstructionCode_23E[localObject3.length];
      }
      if (localObject3 != null) {
        for (int j = 0; j < localObject3.length; j++)
        {
          localObject4[j] = new TypeInstructionCode_23E();
          if (localObject3[j].indexOf("/") > 0)
          {
            String[] arrayOfString = localObject3[j].split("/");
            localObject4[j].Type = arrayOfString[0];
            localObject4[j].Narrative = arrayOfString[1];
            localObject4[j].NarrativeExists = true;
          }
          else
          {
            localObject4[j].Type = localObject3[j];
            localObject4[j].NarrativeExists = false;
          }
        }
      }
      if (localObject4 != null) {
        localTypeMT103.InstructionCode_23EExists = true;
      } else {
        localTypeMT103.InstructionCode_23EExists = false;
      }
      localTypeMT103.InstructionCode_23E = ((TypeInstructionCode_23E[])localObject4);
    }
    if (paramWireTransfer.get("TRANSACTION_TYPE_CODE") != null)
    {
      localObject1 = (ValueInfo)paramWireTransfer.get("TRANSACTION_TYPE_CODE");
      localTypeMT103.TransTypeCode_26T.Type = ((String)((ValueInfo)localObject1).getValue());
      localTypeMT103.TransTypeCode_26TExists = true;
    }
    if ((paramWireTransfer.getSettlementDate() != null) || (paramWireTransfer.getAmtCurrencyType() != null) || (paramWireTransfer.getAmount() != null) || (paramWireTransfer.getDueDate() != null))
    {
      localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A = new TypeAmount_32A();
      localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date = paramWireTransfer.getSettlementDate();
      if (localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date == null) {
        localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date = paramWireTransfer.getDueDate();
      }
      localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency = paramWireTransfer.getAmtCurrencyType();
      localTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Amount = paramWireTransfer.getAmountValue().getAmountValue().toString();
    }
    if ((paramWireTransfer.getOrigAmount() != null) || (paramWireTransfer.getOrigCurrency() != null))
    {
      localTypeMT103.CurrencyInstructedAmount_33B = new TypeAmount_33B();
      localTypeMT103.CurrencyInstructedAmount_33B.Price = paramWireTransfer.getOrigAmount();
      localTypeMT103.CurrencyInstructedAmount_33B.Currency = paramWireTransfer.getOrigCurrency();
      localTypeMT103.CurrencyInstructedAmount_33BExists = true;
    }
    if (paramWireTransfer.getExchangeRate() != null)
    {
      localTypeMT103.ExchangeRate_36.Rate = paramWireTransfer.getExchangeRate();
      localTypeMT103.ExchangeRate_36Exists = true;
    }
    localTypeMT103.OrderingCustomer_50AK = new TypeCreditor_50AK();
    if (paramWireTransfer.getFromAccountNum() != null) {
      localTypeMT103.OrderingCustomer_50AK.Creditor_50A.Account = paramWireTransfer.getFromAccountNum();
    }
    if ((paramWireTransfer.get("SEND_INSTITUTION_NAME") != null) || (paramWireTransfer.get("SEND_INSTITUTION_BICCODE") != null))
    {
      localTypeMT103.SendingInsti_51A = new TypeSendingInsti_51A();
      localTypeMT103.SendingInsti_51AExists = true;
      localObject1 = (ValueInfo)paramWireTransfer.get("SEND_INSTITUTION_NAME");
      if (localObject1 != null) {
        localTypeMT103.SendingInsti_51A.Party.PartyIdentifier = ((String)((ValueInfo)localObject1).getValue());
      }
      localObject2 = (ValueInfo)paramWireTransfer.get("SEND_INSTITUTION_BICCODE");
      if (localObject2 != null)
      {
        localObject3 = (String)((ValueInfo)localObject2).getValue();
        localObject4 = ((String)localObject3).substring(((String)localObject3).length() - 3);
        localTypeMT103.SendingInsti_51A.BICBranchCode = ((String)localObject4);
        localTypeMT103.SendingInsti_51A.BICBranchCodeExists = true;
        localTypeMT103.SendingInsti_51A.BIC = ((String)localObject3).substring(0, ((String)localObject3).length() - 2);
      }
    }
    localTypeMT103.BeneficiaryCustomer_59AN = new TypeDebtor_59AN();
    localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59A = new TypeDebtor_59A();
    if (localWireTransferPayee.getAccountNum() != null)
    {
      localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.Account = localWireTransferPayee.getAccountNum().trim();
      localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.AccountExists = true;
    }
    Object localObject1 = new String[4];
    localObject1[0] = localWireTransferPayee.getName();
    localObject1[1] = localWireTransferPayee.getStreet();
    localObject1[3] = localWireTransferPayee.getCountry();
    if (localObject1[3].equals("UNITED STATES")) {
      localObject1[3] = "USA";
    }
    localObject1[2] = (localWireTransferPayee.getCity() + "," + localWireTransferPayee.getState() + " " + localWireTransferPayee.getZipCode());
    localTypeMT103.BeneficiaryCustomer_59AN.Debtor_59.NameAddr.Line = ((String[])localObject1);
    localTypeMT103.AcctWithInsti_57ABCD = new TypeAcctWithInsti_57ABCD();
    localTypeMT103.AcctWithInsti_57ABCDExists = true;
    if (paramWireTransfer.getWireDestination().equals("INTERNATIONAL"))
    {
      localTypeMT103.AcctWithInsti_57ABCD.__preValueTag = "57A";
      localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A = new TypeAcctWithInsti_57A();
      localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.Party = new TypeParty();
      localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.PartyExists = true;
      if (paramWireTransfer.get("PARTY_IDENTIFIER_57") != null)
      {
        localObject2 = (ValueInfo)paramWireTransfer.get("PARTY_IDENTIFIER_57");
        localObject3 = (String)((ValueInfo)localObject2).getValue();
        if (localObject3 != null)
        {
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.Party.PartyIdentifier = ((String)localObject3);
          localTypeMT103.AcctWithInsti_57ABCD.AcctWithInsti_57A.Party.PartyIdentifierExists = true;
        }
      }
    }
    return localTypeMT103;
  }
  
  public WireTransfer mapMT103ToWireTransfer(TypeMT103 paramTypeMT103)
    throws SWIFTParseException
  {
    WireTransfers localWireTransfers = new WireTransfers();
    WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
    localWireTransfer.setWireType("SINGLE");
    WireTransferPayee localWireTransferPayee = new WireTransferPayee();
    WireTransferBank localWireTransferBank = new WireTransferBank();
    localWireTransferPayee.setDestinationBank(localWireTransferBank);
    localWireTransfer.setWirePayee(localWireTransferPayee);
    String str = paramTypeMT103.SendersRef_20.TransRefNum;
    localWireTransfer.setComment(str);
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
        localWireTransfer.put("TIME_INDICATION", localObject2);
      }
    }
    Object localObject1 = paramTypeMT103.BankOperationCode_23B.Type;
    if ((localObject1 != null) && (((String)localObject1).length() > 0))
    {
      localObject2 = new ValueInfo();
      ((ValueInfo)localObject2).setValue(localObject1);
      ((ValueInfo)localObject2).setAction("add");
      localWireTransfer.put("BANK_OPERATION_CODE", localObject2);
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
        localWireTransfer.put("PAY_INSTRUCT", localObject4);
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
        localWireTransfer.put("TRANSACTION_TYPE_CODE", localObject3);
      }
    }
    if (paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A != null)
    {
      localObject2 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Date;
      localObject3 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Currency;
      localObject4 = paramTypeMT103.ValueDateCurrencyInterbankSettledAmount_32A.Amount;
      localWireTransfer.setDateFormat("yyMMdd");
      localWireTransfer.setSettlementDate((String)localObject2);
      localWireTransfer.setDueDate((String)localObject2);
      localWireTransfer.setDateFormat("SHORT");
      localWireTransfer.setAmount((String)localObject4);
      localWireTransfer.setAmtCurrencyType((String)localObject3);
    }
    if (paramTypeMT103.CurrencyInstructedAmount_33BExists)
    {
      localWireTransfer.setOrigAmount(paramTypeMT103.CurrencyInstructedAmount_33B.Price);
      localWireTransfer.setOrigCurrency(paramTypeMT103.CurrencyInstructedAmount_33B.Currency);
    }
    if (paramTypeMT103.ExchangeRate_36Exists)
    {
      localObject2 = paramTypeMT103.ExchangeRate_36.Rate;
      if (localObject2 != null) {
        localWireTransfer.setExchangeRate((String)localObject2);
      }
    }
    Object localObject2 = paramTypeMT103.OrderingCustomer_50AK;
    Object localObject3 = "";
    Object localObject4 = "";
    String[] arrayOfString1 = null;
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
      arrayOfString1 = ((TypeCreditor_50AK)localObject2).Creditor_50K.NameAddr.Line;
    }
    if (localObject3 != null) {
      localWireTransfer.setFromAccountNum((String)localObject3);
    }
    if (paramTypeMT103.SendingInsti_51AExists)
    {
      if (paramTypeMT103.SendingInsti_51A.PartyExists)
      {
        localObject5 = paramTypeMT103.SendingInsti_51A.Party.PartyIdentifier;
        if ((localObject5 != null) && (((String)localObject5).length() > 0))
        {
          localObject6 = new ValueInfo();
          ((ValueInfo)localObject6).setValue(localObject5);
          ((ValueInfo)localObject6).setAction("add");
          localWireTransfer.put("SEND_INSTITUTION_NAME", localObject6);
        }
      }
      localObject5 = paramTypeMT103.SendingInsti_51A.BIC;
      if (paramTypeMT103.SendingInsti_51A.BICBranchCodeExists) {
        localObject5 = ((String)localObject5).concat(paramTypeMT103.SendingInsti_51A.BICBranchCode);
      } else {
        localObject5 = ((String)localObject5).concat("XXX");
      }
      if ((localObject5 != null) && (((String)localObject5).length() > 0))
      {
        localObject6 = new ValueInfo();
        ((ValueInfo)localObject6).setValue(localObject5);
        ((ValueInfo)localObject6).setAction("add");
        localWireTransfer.put("SEND_INSTITUTION_BICCODE", localObject6);
      }
    }
    Object localObject5 = paramTypeMT103.BeneficiaryCustomer_59AN;
    if (((TypeDebtor_59AN)localObject5).__preValueTag.equals("59A"))
    {
      DebugLog.log("WARNING: Tag 59A is not supported. Benficiary Account/Address is missing!");
    }
    else
    {
      if (((TypeDebtor_59AN)localObject5).Debtor_59.AccountExists) {
        localWireTransferPayee.setAccountNum(((TypeDebtor_59AN)localObject5).Debtor_59.Account);
      }
      localObject6 = ((TypeDebtor_59AN)localObject5).Debtor_59.NameAddr.Line;
      if ((localObject6 != null) && (localObject6.length > 0)) {
        jdMethod_if(localWireTransferPayee, (String[])localObject6);
      }
    }
    Object localObject7;
    Object localObject9;
    Object localObject12;
    if (paramTypeMT103.AcctWithInsti_57ABCDExists)
    {
      localObject6 = paramTypeMT103.AcctWithInsti_57ABCD;
      if (((TypeAcctWithInsti_57ABCD)localObject6).__preValueTag.equals("57A"))
      {
        localWireTransfer.setWireDestination("INTERNATIONAL");
        localObject7 = ((TypeAcctWithInsti_57ABCD)localObject6).AcctWithInsti_57A;
        if ((((TypeAcctWithInsti_57A)localObject7).PartyExists) && (((TypeAcctWithInsti_57A)localObject7).Party.PartyIdentifierExists))
        {
          localObject9 = ((TypeAcctWithInsti_57A)localObject7).Party.PartyIdentifier;
          if ((localObject9 != null) && (((String)localObject9).length() > 0))
          {
            ValueInfo localValueInfo1 = new ValueInfo();
            localValueInfo1.setValue(localObject9);
            localValueInfo1.setAction("add");
            localWireTransfer.put("PARTY_IDENTIFIER_57", localValueInfo1);
          }
        }
        localObject9 = new StringBuffer(((TypeAcctWithInsti_57A)localObject7).BIC);
        if (((TypeAcctWithInsti_57A)localObject7).BICBranchCodeExists) {
          ((StringBuffer)localObject9).append(((TypeAcctWithInsti_57A)localObject7).BICBranchCode);
        } else {
          ((StringBuffer)localObject9).append("XXX");
        }
        localWireTransferBank.setRoutingSwift(((StringBuffer)localObject9).toString());
      }
      else if (((TypeAcctWithInsti_57ABCD)localObject6).__preValueTag.equals("57D"))
      {
        localObject7 = ((TypeAcctWithInsti_57ABCD)localObject6).AcctWithInsti_57D;
        if ((((TypeAcctWithInsti_57D)localObject7).PartyExists) && (((TypeAcctWithInsti_57D)localObject7).Party.PartyIdentifierExists))
        {
          localObject9 = ((TypeAcctWithInsti_57D)localObject7).Party.PartyIdentifier;
          int k = ((String)localObject9).indexOf("FW");
          if (k != -1)
          {
            localObject12 = ((String)localObject9).substring(k + 2);
            localWireTransferBank.setRoutingFedWire((String)localObject12);
          }
          else
          {
            DebugLog.log("WARNING: 57D is not allowed without //FW prefix!");
          }
        }
        localObject9 = ((TypeAcctWithInsti_57D)localObject7).NameAddr.Line;
        if ((localObject9 != null) && (localObject9.length > 0)) {
          try
          {
            localWireTransferBank.setBankName(localObject9[0]);
            localWireTransferBank.setStreet(localObject9[1]);
            localWireTransferBank.setStreet2(localObject9[2]);
            localWireTransferBank.setStreet3(localObject9[3]);
          }
          catch (Exception localException1)
          {
            DebugLog.log(localException1.getMessage());
          }
        }
      }
    }
    if (paramTypeMT103.Intermediary_56ACDExists)
    {
      localObject6 = new WireTransferBanks();
      localWireTransferPayee.setIntermediaryBanks((WireTransferBanks)localObject6);
      localObject7 = ((WireTransferBanks)localObject6).create();
      localObject9 = paramTypeMT103.Intermediary_56ACD;
      Object localObject10;
      if (((TypeIntermediary_56ACD)localObject9).__preValueTag.equals("56A"))
      {
        localObject10 = ((TypeIntermediary_56ACD)localObject9).Intermediary_56A;
        if ((((TypeIntermediary_56A)localObject10).PartyExists) && (((TypeIntermediary_56A)localObject10).Party.PartyIdentifierExists))
        {
          localObject12 = ((TypeIntermediary_56A)localObject10).Party.PartyIdentifier;
          if ((localObject12 != null) && (((String)localObject12).length() > 0))
          {
            ValueInfo localValueInfo2 = new ValueInfo();
            localValueInfo2.setValue(localObject12);
            localValueInfo2.setAction("add");
            localWireTransfer.put("PARTY_IDENTIFIER_56", localValueInfo2);
          }
        }
        localObject12 = new StringBuffer(((TypeIntermediary_56A)localObject10).BIC);
        if (((TypeIntermediary_56A)localObject10).BICBranchCodeExists) {
          ((StringBuffer)localObject12).append(((TypeIntermediary_56A)localObject10).BICBranchCode);
        } else {
          ((StringBuffer)localObject12).append("XXX");
        }
        ((WireTransferBank)localObject7).setRoutingSwift(((StringBuffer)localObject12).toString());
      }
      else if (((TypeIntermediary_56ACD)localObject9).__preValueTag.equals("56D"))
      {
        localObject10 = ((TypeIntermediary_56ACD)localObject9).Intermediary_56D;
        if ((((TypeIntermediary_56D)localObject10).PartyExists) && (((TypeIntermediary_56D)localObject10).Party.PartyIdentifierExists))
        {
          localObject12 = ((TypeIntermediary_56D)localObject10).Party.PartyIdentifier;
          int i1 = ((String)localObject12).indexOf("FW");
          if (i1 != -1) {
            ((WireTransferBank)localObject7).setRoutingFedWire(((String)localObject12).substring(i1 + 2));
          }
        }
        localObject12 = ((TypeIntermediary_56D)localObject10).NameAddr.Line;
        if ((localObject12 != null) && (localObject12.length > 0)) {
          try
          {
            ((WireTransferBank)localObject7).setBankName(localObject12[0]);
            ((WireTransferBank)localObject7).setStreet(localObject12[1]);
            ((WireTransferBank)localObject7).setStreet2(localObject12[2]);
            ((WireTransferBank)localObject7).setStreet3(localObject12[3]);
          }
          catch (Exception localException2) {}
        }
      }
    }
    if (paramTypeMT103.RemittanceInformation_70Exists)
    {
      localObject6 = paramTypeMT103.RemittanceInformation_70.Narrative.Line;
      for (int j = 0; j < localObject6.length; j++)
      {
        localObject9 = localObject6[j];
        if (j == 0) {
          localWireTransfer.setOrigToBeneficiaryInfo1((String)localObject9);
        } else if (j == 1) {
          localWireTransfer.setOrigToBeneficiaryInfo2((String)localObject9);
        } else if (j == 2) {
          localWireTransfer.setOrigToBeneficiaryInfo3((String)localObject9);
        } else if (j == 3) {
          localWireTransfer.setOrigToBeneficiaryInfo4((String)localObject9);
        }
      }
    }
    Object localObject6 = paramTypeMT103.DetailsOfCharges_71A.Code;
    Object localObject8;
    if ((localObject6 != null) && (((String)localObject6).length() > 0))
    {
      localObject8 = new ValueInfo();
      ((ValueInfo)localObject8).setValue(localObject6);
      ((ValueInfo)localObject8).setAction("add");
      localWireTransfer.put("DETAILS_OF_CHARGES", localObject8);
    }
    Object localObject11;
    if (paramTypeMT103.SendersCharges_71FExists)
    {
      localObject8 = new StringBuffer();
      localObject9 = paramTypeMT103.SendersCharges_71F;
      if (localObject9 != null) {
        for (int m = 0; m < localObject9.length; m++) {
          if (localObject9[m] != null)
          {
            ((StringBuffer)localObject8).append(localObject9[m].Currency);
            ((StringBuffer)localObject8).append(localObject9[m].Amount);
            ((StringBuffer)localObject8).append(" ");
          }
        }
      }
      if (((StringBuffer)localObject8).length() > 0)
      {
        localObject11 = new ValueInfo();
        ((ValueInfo)localObject11).setValue(((StringBuffer)localObject8).toString().trim());
        ((ValueInfo)localObject11).setAction("add");
        localWireTransfer.put("SENDERS_CHARGES", localObject11);
      }
    }
    if (paramTypeMT103.ReceiversCharges_71GExists)
    {
      localObject8 = new StringBuffer();
      ((StringBuffer)localObject8).append(paramTypeMT103.ReceiversCharges_71G.Currency);
      ((StringBuffer)localObject8).append(paramTypeMT103.ReceiversCharges_71G.Amount);
      if (((StringBuffer)localObject8).length() > 0)
      {
        localObject9 = new ValueInfo();
        ((ValueInfo)localObject9).setValue(((StringBuffer)localObject8).toString());
        ((ValueInfo)localObject9).setAction("add");
        localWireTransfer.put("RECEIVERS_CHARGES", localObject9);
      }
    }
    if (paramTypeMT103.SenderToReceiverInfo_72Exists)
    {
      localObject8 = paramTypeMT103.SenderToReceiverInfo_72;
      if (((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72 != null)
      {
        localObject9 = new StringBuffer();
        localObject11 = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.Code;
        if (localObject11 != null) {
          ((StringBuffer)localObject9).append((String)localObject11);
        }
        if (((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.AdditionalInformationExists)
        {
          localObject12 = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.AdditionalInformation;
          ((StringBuffer)localObject9).append("/");
          ((StringBuffer)localObject9).append((String)localObject12);
        }
        localWireTransfer.setBankToBankInfo1(((StringBuffer)localObject9).toString());
        localObject12 = new String[5];
        int i2 = 0;
        TypeAdditionalLines[] arrayOfTypeAdditionalLines = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.AdditionalLines;
        if ((arrayOfTypeAdditionalLines != null) && (arrayOfTypeAdditionalLines.length > 0)) {
          for (int i3 = 0; i3 < arrayOfTypeAdditionalLines.length; i3++) {
            if (i2 < 5) {
              if (arrayOfTypeAdditionalLines[i3].Line != null)
              {
                localObject12[(i2++)] = arrayOfTypeAdditionalLines[i3].Line;
              }
              else
              {
                StringBuffer localStringBuffer = new StringBuffer(arrayOfTypeAdditionalLines[i3].Line2.Code);
                if (arrayOfTypeAdditionalLines[i3].Line2.NarrativeExists)
                {
                  localStringBuffer.append("/");
                  localStringBuffer.append(arrayOfTypeAdditionalLines[i3].Line2.Narrative);
                }
                localObject12[(i2++)] = localStringBuffer.toString();
              }
            }
          }
        }
        String[] arrayOfString3 = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF1_72.NarrativeText.Line;
        if ((arrayOfString3 != null) && (arrayOfString3.length > 0)) {
          for (i4 = 0; i4 < arrayOfString3.length; i4++) {
            if (i2 < 5) {
              localObject12[(i2++)] = arrayOfString3[i4];
            }
          }
        }
        for (int i4 = 0; i4 < localObject12.length; i4++) {
          if (i4 == 0) {
            localWireTransfer.setBankToBankInfo2(localObject12[i4]);
          } else if (i4 == 1) {
            localWireTransfer.setBankToBankInfo3(localObject12[i4]);
          } else if (i4 == 2) {
            localWireTransfer.setBankToBankInfo4(localObject12[i4]);
          } else if (i4 == 3) {
            localWireTransfer.setBankToBankInfo5(localObject12[i4]);
          } else if (i4 == 4) {
            localWireTransfer.setBankToBankInfo6(localObject12[i4]);
          }
        }
      }
      if (((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF2_72 != null)
      {
        localObject9 = ((TypeSenderToReceiverInfo_72)localObject8).SenderToReceiverInfoF2_72.Narrative.Line;
        for (int n = 0; n < localObject9.length; n++) {
          if (n == 0) {
            localWireTransfer.setBankToBankInfo1(localObject9[n]);
          } else if (n == 1) {
            localWireTransfer.setBankToBankInfo2(localObject9[n]);
          } else if (n == 2) {
            localWireTransfer.setBankToBankInfo3(localObject9[n]);
          } else if (n == 3) {
            localWireTransfer.setBankToBankInfo4(localObject9[n]);
          } else if (n == 4) {
            localWireTransfer.setBankToBankInfo5(localObject9[n]);
          } else if (n == 5) {
            localWireTransfer.setBankToBankInfo6(localObject9[n]);
          }
        }
      }
    }
    if (paramTypeMT103.OrderingInstitution_52ADExists)
    {
      localObject8 = paramTypeMT103.OrderingInstitution_52AD;
      if (((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D != null)
      {
        localObject9 = "";
        if ((((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D.PartyExists) && (((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D.Party.PartyIdentifierExists)) {
          localObject9 = ((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D.Party.PartyIdentifier;
        }
        localWireTransfer.setByOrderOfAccount((String)localObject9);
        String[] arrayOfString2 = ((TypeOrderingInstitution_52AD)localObject8).OrderingInstitution_52D.NameAddr.Line;
        if (arrayOfString2.length > 0) {
          localWireTransfer.setByOrderOfName(arrayOfString2[0]);
        }
        if (arrayOfString2.length > 1) {
          localWireTransfer.setByOrderOfAddress1(arrayOfString2[1]);
        }
        if (arrayOfString2.length > 2) {
          localWireTransfer.setByOrderOfAddress2(arrayOfString2[2]);
        }
        if (arrayOfString2.length > 3) {
          localWireTransfer.setByOrderOfAddress3(arrayOfString2[3]);
        }
      }
    }
    if (paramTypeMT103.EnvelopeContents_77TExists)
    {
      localObject8 = paramTypeMT103.EnvelopeContents_77T.Code;
      if ((localObject8 != null) && (((String)localObject8).length() != 0))
      {
        localObject9 = new ValueInfo();
        ((ValueInfo)localObject9).setValue(localObject8);
        ((ValueInfo)localObject9).setAction("add");
        localWireTransfer.put("ENVELOPE_CONTENTS", localObject9);
      }
    }
    return localWireTransfer;
  }
  
  public TypeMT103 mapWireInfoToMT103(WireInfo paramWireInfo)
    throws SWIFTParseException
  {
    return null;
  }
  
  public WireInfo mapMT103ToWireInfo(TypeMT103 paramTypeMT103)
    throws SWIFTParseException
  {
    WireTransfer localWireTransfer = null;
    WireInfo localWireInfo = null;
    localWireTransfer = mapMT103ToWireTransfer(paramTypeMT103);
    if (localWireTransfer != null) {
      localWireInfo = localWireTransfer.getWireInfo();
    }
    return localWireInfo;
  }
  
  private void jdMethod_if(WireTransferPayee paramWireTransferPayee, String[] paramArrayOfString)
  {
    try
    {
      paramWireTransferPayee.setName(paramArrayOfString[0]);
      paramWireTransferPayee.setStreet(paramArrayOfString[1]);
      paramWireTransferPayee.setCountry(paramArrayOfString[3]);
      if (paramWireTransferPayee.getCountry().equals("USA")) {
        paramWireTransferPayee.setCountry("UNITED STATES");
      }
      String str = paramArrayOfString[2];
      StringTokenizer localStringTokenizer = new StringTokenizer(str, ",");
      paramWireTransferPayee.setCity(localStringTokenizer.nextToken());
      str = localStringTokenizer.nextToken();
      localStringTokenizer = new StringTokenizer(str.trim(), " ");
      paramWireTransferPayee.setState(localStringTokenizer.nextToken());
      paramWireTransferPayee.setZipCode(localStringTokenizer.nextToken());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public Object parse(IMBMessage paramIMBMessage)
    throws SWIFTParseException
  {
    WireTransfer localWireTransfer = null;
    if (paramIMBMessage == null)
    {
      localObject = new WireTransfers();
      localWireTransfer = (WireTransfer)((WireTransfers)localObject).create();
      return localWireTransfer;
    }
    Object localObject = (TypeMT103)paramIMBMessage.getIDLInstance();
    localWireTransfer = mapMT103ToWireTransfer((TypeMT103)localObject);
    return localWireTransfer;
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
    if (!(paramObject instanceof WireTransfer))
    {
      localObject = "bean object passed should be instance of com.ffusion.beans.wiretransfers.WireTransfer";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    Object localObject = (WireTransfer)paramObject;
    TypeMT103 localTypeMT103 = mapWireTransferToMT103((WireTransfer)localObject);
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
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.MT103WireMapper
 * JD-Core Version:    0.7.0.1
 */