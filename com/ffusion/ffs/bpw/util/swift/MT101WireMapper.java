package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransferBank;
import com.ffusion.beans.wiretransfers.WireTransferBanks;
import com.ffusion.beans.wiretransfers.WireTransferPayee;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.msgbroker.factory.MBMessageFactory;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57ACD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAcctWithInsti_57D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAmount_32B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeAmount_33B;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeChargesAccount_25A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCode_71A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50G;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeCreditor_50H;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDate_30;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDebtor_59;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDebtor_59AN;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeDetailsPayment_70;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeExchangeRate_36;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeFXDealRef_21F;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeGeneralInformationSeq_101;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionCode_23E;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionParty_50C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionParty_50CL;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeInstructionParty_50L;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56ACD;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeIntermediary_56D;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeMT101;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeNarrative;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingCustomer_50GH;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52A;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52AC;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeOrderingInstitution_52C;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeParty;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeRelatedRef_21;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransRefNum_20;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.SWIFT.TypeTransactionDetailsSeq_101;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.util.logging.DebugLog;
import java.util.StringTokenizer;

public class MT101WireMapper
  implements ISWIFTMapper
{
  public TypeMT101 mapWireTransferToMT101(WireTransfer paramWireTransfer)
    throws SWIFTParseException
  {
    int i = 1;
    if (i != 0) {
      throw new SWIFTParseException("method MT101WireMapper.mapWireTransferToMT101 is not implemented");
    }
    return null;
  }
  
  public WireTransfer mapMT101ToWireTransfer(TypeMT101 paramTypeMT101)
    throws SWIFTParseException
  {
    String str = "MT101WireTransferMapper.mapMT101ToWireTransfer";
    WireTransfers localWireTransfers = new WireTransfers();
    TypeTransactionDetailsSeq_101[] arrayOfTypeTransactionDetailsSeq_101 = paramTypeMT101.TransactionDetailsSeq_101;
    WireTransfer localWireTransfer = (WireTransfer)localWireTransfers.create();
    localWireTransfer.setWireType("SINGLE");
    WireTransferPayee localWireTransferPayee = new WireTransferPayee();
    localWireTransfer.setWirePayee(localWireTransferPayee);
    try
    {
      a(paramTypeMT101, localWireTransfer);
      a(arrayOfTypeTransactionDetailsSeq_101[0], localWireTransfer);
    }
    catch (Exception localException)
    {
      throw new SWIFTParseException(localException, "Exception at:" + str + ":Could not map MT101 message");
    }
    return localWireTransfer;
  }
  
  public WireInfo mapMT101ToWireInfo(TypeMT101 paramTypeMT101)
    throws SWIFTParseException
  {
    WireTransfer localWireTransfer = null;
    WireInfo localWireInfo = null;
    localWireTransfer = mapMT101ToWireTransfer(paramTypeMT101);
    localWireInfo = localWireTransfer.getWireInfo();
    return localWireInfo;
  }
  
  private void a(TypeMT101 paramTypeMT101, WireTransfer paramWireTransfer)
  {
    TypeGeneralInformationSeq_101 localTypeGeneralInformationSeq_101 = paramTypeMT101.GeneralInformationSeq_101;
    String str1 = localTypeGeneralInformationSeq_101.TransRefNum_20.TransRefNum;
    String str2 = localTypeGeneralInformationSeq_101.Date_30.Date;
    paramWireTransfer.setComment(str1);
    paramWireTransfer.setDateFormat("yyMMdd");
    paramWireTransfer.setDueDate(str2);
    paramWireTransfer.setSettlementDate(str2);
    paramWireTransfer.setDateFormat("SHORT");
  }
  
  private void a(TypeTransactionDetailsSeq_101 paramTypeTransactionDetailsSeq_101, WireTransfer paramWireTransfer)
    throws Exception
  {
    WireTransferPayee localWireTransferPayee = paramWireTransfer.getWirePayee();
    WireTransferBank localWireTransferBank = new WireTransferBank();
    localWireTransferPayee.setDestinationBank(localWireTransferBank);
    String str = paramTypeTransactionDetailsSeq_101.RelatedRef_21.RelatedRef;
    if ((str != null) && (str.length() > 0))
    {
      localObject1 = new ValueInfo();
      ((ValueInfo)localObject1).setValue(str);
      ((ValueInfo)localObject1).setAction("add");
      paramWireTransfer.put("TRANSACTION_REFERENCE", localObject1);
    }
    if (paramTypeTransactionDetailsSeq_101.InstructionCode_23EExists)
    {
      localObject1 = paramTypeTransactionDetailsSeq_101.InstructionCode_23E;
      localObject2 = new StringBuffer();
      for (int i = 0; i < localObject1.length; i++)
      {
        ((StringBuffer)localObject2).append(localObject1[i].Type);
        if (localObject1[i].NarrativeExists)
        {
          ((StringBuffer)localObject2).append("/");
          ((StringBuffer)localObject2).append(localObject1[i].Narrative);
          ((StringBuffer)localObject2).append(" ");
        }
      }
      if (((StringBuffer)localObject2).length() > 0)
      {
        localObject3 = new ValueInfo();
        ((ValueInfo)localObject3).setValue(((StringBuffer)localObject2).toString().trim());
        ((ValueInfo)localObject3).setAction("add");
        paramWireTransfer.put("PAY_INSTRUCT", localObject3);
      }
    }
    Object localObject1 = paramTypeTransactionDetailsSeq_101.Amount_32B.Currency;
    Object localObject2 = paramTypeTransactionDetailsSeq_101.Amount_32B.Amount;
    paramWireTransfer.setAmtCurrencyType((String)localObject1);
    paramWireTransfer.setAmount((String)localObject2);
    Object localObject5;
    Object localObject7;
    if (paramTypeTransactionDetailsSeq_101.InstructionParty_50CLExists)
    {
      localObject3 = "";
      localObject4 = "";
      localObject5 = paramTypeTransactionDetailsSeq_101.InstructionParty_50CL;
      if (((TypeInstructionParty_50CL)localObject5).__preValueTag.equals("50C"))
      {
        localObject4 = ((TypeInstructionParty_50CL)localObject5).InstructionParty_50C.BIC;
        if (((TypeInstructionParty_50CL)localObject5).InstructionParty_50C.BICBranchCodeExists) {
          localObject4 = ((String)localObject4).concat(((TypeInstructionParty_50CL)localObject5).InstructionParty_50C.BICBranchCode);
        } else {
          localObject4 = ((String)localObject4).concat("XXX");
        }
      }
      else
      {
        localObject3 = ((TypeInstructionParty_50CL)localObject5).InstructionParty_50L.PartyIdentifier;
      }
      if (((String)localObject3).length() > 0)
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject3);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("INSTRUCTING_PARTY_NAME", localObject7);
      }
      if (((String)localObject4).length() > 0)
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject4);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("INSTRUCTING_PARTY_BICCODE", localObject7);
      }
    }
    Object localObject8;
    Object localObject10;
    Object localObject11;
    if (paramTypeTransactionDetailsSeq_101.OrderingCustomer_50GHExists)
    {
      localObject3 = null;
      localObject4 = "";
      localObject5 = "";
      localObject7 = "";
      localObject8 = "";
      localObject10 = null;
      localObject11 = paramTypeTransactionDetailsSeq_101.OrderingCustomer_50GH;
      if (((TypeOrderingCustomer_50GH)localObject11).__preValueTag.equals("50G"))
      {
        localObject3 = ((TypeOrderingCustomer_50GH)localObject11).Creditor_50G.Account;
        localObject10 = ((TypeOrderingCustomer_50GH)localObject11).Creditor_50G.BIC;
        if (((TypeOrderingCustomer_50GH)localObject11).Creditor_50G.BICBranchCodeExists) {
          localObject10 = ((String)localObject10).concat(((TypeOrderingCustomer_50GH)localObject11).Creditor_50G.BICBranchCode);
        } else {
          localObject10 = ((String)localObject10).concat("XXX");
        }
      }
      else
      {
        localObject3 = ((TypeOrderingCustomer_50GH)localObject11).Creditor_50H.Account;
        String[] arrayOfString = ((TypeOrderingCustomer_50GH)localObject11).Creditor_50H.NameAddr.Line;
        localObject4 = arrayOfString[0];
        if (arrayOfString.length > 1) {
          localObject5 = arrayOfString[1];
        }
        if (arrayOfString.length > 2) {
          localObject7 = arrayOfString[2];
        }
        if (arrayOfString.length > 3) {
          localObject8 = arrayOfString[3];
        }
      }
      paramWireTransfer.setFromAccountNum((String)localObject3);
    }
    if (paramTypeTransactionDetailsSeq_101.OrderingInstitution_52ACExists)
    {
      localObject3 = "";
      localObject4 = "";
      localObject5 = paramTypeTransactionDetailsSeq_101.OrderingInstitution_52AC;
      if (((TypeOrderingInstitution_52AC)localObject5).__preValueTag.equals("52A"))
      {
        localObject3 = ((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.BIC;
        if (((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.BICBranchCodeExists) {
          localObject3 = ((String)localObject3).concat(((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.BICBranchCode);
        } else {
          localObject3 = ((String)localObject3).concat("XXX");
        }
        if ((((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.PartyExists) && (((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.Party.PartyIdentifierExists)) {
          localObject4 = ((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52A.Party.PartyIdentifier;
        }
      }
      else
      {
        localObject4 = ((TypeOrderingInstitution_52AC)localObject5).OrderingInstitution_52C.PartyIdentifier;
      }
      if (((String)localObject4).length() > 0)
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject4);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("ACCT_SVC_INSTITUTION_NAME", localObject7);
      }
      if (((String)localObject3).length() > 0)
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject3);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("ACCT_SVC_INSTITUTION_BICCODE", localObject7);
      }
    }
    Object localObject3 = paramTypeTransactionDetailsSeq_101.Beneficiary_59AN;
    if (((TypeDebtor_59AN)localObject3).__preValueTag.equals("59A"))
    {
      DebugLog.log("WARNING: Tag 59A is not supported. Benficiary Account/Address is missing!");
    }
    else
    {
      if (((TypeDebtor_59AN)localObject3).Debtor_59.AccountExists) {
        localWireTransferPayee.setAccountNum(((TypeDebtor_59AN)localObject3).Debtor_59.Account);
      }
      localObject4 = ((TypeDebtor_59AN)localObject3).Debtor_59.NameAddr.Line;
      if ((localObject4 != null) && (localObject4.length > 0)) {
        a(localWireTransferPayee, (String[])localObject4);
      }
    }
    if (paramTypeTransactionDetailsSeq_101.AcctWithInsti_57ACDExists)
    {
      localObject4 = paramTypeTransactionDetailsSeq_101.AcctWithInsti_57ACD;
      if (((TypeAcctWithInsti_57ACD)localObject4).__preValueTag.equals("57A"))
      {
        paramWireTransfer.setWireDestination("INTERNATIONAL");
        localObject5 = ((TypeAcctWithInsti_57ACD)localObject4).AcctWithInsti_57A;
        if ((((TypeAcctWithInsti_57A)localObject5).PartyExists) && (((TypeAcctWithInsti_57A)localObject5).Party.PartyIdentifierExists))
        {
          localObject7 = ((TypeAcctWithInsti_57A)localObject5).Party.PartyIdentifier;
          if ((localObject7 != null) && (((String)localObject7).length() > 0))
          {
            localObject8 = new ValueInfo();
            ((ValueInfo)localObject8).setValue(localObject7);
            ((ValueInfo)localObject8).setAction("add");
            paramWireTransfer.put("PARTY_IDENTIFIER_57", localObject8);
          }
        }
        localObject7 = new StringBuffer(((TypeAcctWithInsti_57A)localObject5).BIC);
        if (((TypeAcctWithInsti_57A)localObject5).BICBranchCodeExists) {
          ((StringBuffer)localObject7).append(((TypeAcctWithInsti_57A)localObject5).BICBranchCode);
        } else {
          ((StringBuffer)localObject7).append("XXX");
        }
        localWireTransferBank.setRoutingSwift(((StringBuffer)localObject7).toString());
      }
      else if (((TypeAcctWithInsti_57ACD)localObject4).__preValueTag.equals("57D"))
      {
        localObject5 = ((TypeAcctWithInsti_57ACD)localObject4).AcctWithInsti_57D;
        if ((((TypeAcctWithInsti_57D)localObject5).PartyExists) && (((TypeAcctWithInsti_57D)localObject5).Party.PartyIdentifierExists))
        {
          localObject7 = ((TypeAcctWithInsti_57D)localObject5).Party.PartyIdentifier;
          int k = ((String)localObject7).indexOf("FW");
          if (k != -1)
          {
            localObject10 = ((String)localObject7).substring(k + 2);
            localWireTransferBank.setRoutingFedWire((String)localObject10);
          }
          else
          {
            DebugLog.log("WARNING: 57D is not allowed without //FW prefix!");
          }
        }
        localObject7 = ((TypeAcctWithInsti_57D)localObject5).NameAddr.Line;
        if ((localObject7 != null) && (localObject7.length > 0)) {
          try
          {
            localWireTransferBank.setBankName(localObject7[0]);
            localWireTransferBank.setStreet(localObject7[1]);
            localWireTransferBank.setStreet2(localObject7[2]);
            localWireTransferBank.setStreet3(localObject7[3]);
          }
          catch (Exception localException1) {}
        }
      }
    }
    if ((!paramWireTransfer.getWireDestination().equals("BOOKTRANSFER")) && (paramTypeTransactionDetailsSeq_101.Intermediary_56ACDExists))
    {
      localObject4 = new WireTransferBanks();
      localWireTransferPayee.setIntermediaryBanks((WireTransferBanks)localObject4);
      localObject5 = ((WireTransferBanks)localObject4).create();
      localObject7 = paramTypeTransactionDetailsSeq_101.Intermediary_56ACD;
      Object localObject9;
      if (((TypeIntermediary_56ACD)localObject7).__preValueTag.equals("56A"))
      {
        localObject9 = ((TypeIntermediary_56ACD)localObject7).Intermediary_56A;
        if ((((TypeIntermediary_56A)localObject9).PartyExists) && (((TypeIntermediary_56A)localObject9).Party.PartyIdentifierExists))
        {
          localObject10 = ((TypeIntermediary_56A)localObject9).Party.PartyIdentifier;
          if ((localObject10 != null) && (((String)localObject10).length() > 0))
          {
            localObject11 = new ValueInfo();
            ((ValueInfo)localObject11).setValue(localObject10);
            ((ValueInfo)localObject11).setAction("add");
            paramWireTransfer.put("PARTY_IDENTIFIER_56", localObject11);
          }
        }
        localObject10 = new StringBuffer(((TypeIntermediary_56A)localObject9).BIC);
        if (((TypeIntermediary_56A)localObject9).BICBranchCodeExists) {
          ((StringBuffer)localObject10).append(((TypeIntermediary_56A)localObject9).BICBranchCode);
        } else {
          ((StringBuffer)localObject10).append("XXX");
        }
        ((WireTransferBank)localObject5).setRoutingSwift(((StringBuffer)localObject10).toString());
      }
      else if (((TypeIntermediary_56ACD)localObject7).__preValueTag.equals("56D"))
      {
        localObject9 = ((TypeIntermediary_56ACD)localObject7).Intermediary_56D;
        if ((((TypeIntermediary_56D)localObject9).PartyExists) && (((TypeIntermediary_56D)localObject9).Party.PartyIdentifierExists))
        {
          localObject10 = ((TypeIntermediary_56D)localObject9).Party.PartyIdentifier;
          int m = ((String)localObject10).indexOf("FW");
          if (m != -1) {
            ((WireTransferBank)localObject5).setRoutingFedWire(((String)localObject10).substring(m + 2));
          }
        }
        localObject10 = ((TypeIntermediary_56D)localObject9).NameAddr.Line;
        if ((localObject10 != null) && (localObject10.length > 0)) {
          try
          {
            ((WireTransferBank)localObject5).setBankName(localObject10[0]);
            ((WireTransferBank)localObject5).setStreet(localObject10[1]);
            ((WireTransferBank)localObject5).setStreet2(localObject10[2]);
            ((WireTransferBank)localObject5).setStreet3(localObject10[3]);
          }
          catch (Exception localException2) {}
        }
      }
    }
    if (paramTypeTransactionDetailsSeq_101.DetailsPayment_70Exists)
    {
      localObject4 = paramTypeTransactionDetailsSeq_101.DetailsPayment_70.Narrative.Line;
      for (int j = 0; j < localObject4.length; j++)
      {
        localObject7 = localObject4[j];
        if (j == 0) {
          paramWireTransfer.setOrigToBeneficiaryInfo1((String)localObject7);
        } else if (j == 1) {
          paramWireTransfer.setOrigToBeneficiaryInfo2((String)localObject7);
        } else if (j == 2) {
          paramWireTransfer.setOrigToBeneficiaryInfo3((String)localObject7);
        } else if (j == 3) {
          paramWireTransfer.setOrigToBeneficiaryInfo4((String)localObject7);
        }
      }
    }
    if (paramTypeTransactionDetailsSeq_101.Amount_33BExists)
    {
      paramWireTransfer.setOrigAmount(paramTypeTransactionDetailsSeq_101.Amount_33B.Price);
      paramWireTransfer.setOrigCurrency(paramTypeTransactionDetailsSeq_101.Amount_33B.Currency);
    }
    Object localObject4 = paramTypeTransactionDetailsSeq_101.DetailsOfCharges_71A.Code;
    Object localObject6;
    if ((localObject4 != null) && (((String)localObject4).length() > 0))
    {
      localObject6 = new ValueInfo();
      ((ValueInfo)localObject6).setValue(localObject4);
      ((ValueInfo)localObject6).setAction("add");
      paramWireTransfer.put("DETAILS_OF_CHARGES", localObject6);
    }
    if ((paramTypeTransactionDetailsSeq_101.ChargesAccount_25AExists) && (paramTypeTransactionDetailsSeq_101.ChargesAccount_25A.AccountIdentificationExists))
    {
      localObject6 = paramTypeTransactionDetailsSeq_101.ChargesAccount_25A.AccountIdentification;
      if ((localObject6 != null) && (((String)localObject6).length() > 0))
      {
        localObject7 = new ValueInfo();
        ((ValueInfo)localObject7).setValue(localObject6);
        ((ValueInfo)localObject7).setAction("add");
        paramWireTransfer.put("CHARGES_ACCOUNT", localObject7);
      }
    }
    if (paramTypeTransactionDetailsSeq_101.FXDealRef_21FExists) {
      paramWireTransfer.setContractNumber(paramTypeTransactionDetailsSeq_101.FXDealRef_21F.FXDealRef);
    }
    if (paramTypeTransactionDetailsSeq_101.ExchangeRate_36Exists)
    {
      localObject6 = paramTypeTransactionDetailsSeq_101.ExchangeRate_36.Rate;
      if (localObject6 != null) {
        paramWireTransfer.setExchangeRate((String)localObject6);
      }
    }
  }
  
  private void a(WireTransferPayee paramWireTransferPayee, String[] paramArrayOfString)
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
    Object localObject = (TypeMT101)paramIMBMessage.getIDLInstance();
    localWireTransfer = mapMT101ToWireTransfer((TypeMT101)localObject);
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
    if (!(paramObject instanceof Transfer))
    {
      localObject = "bean object passed should be instance of com.ffusion.beans.wiretransfers.WireTransfer";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    Object localObject = (WireTransfer)paramObject;
    TypeMT101 localTypeMT101 = mapWireTransferToMT101((WireTransfer)localObject);
    IMBMessage localIMBMessage = null;
    try
    {
      localIMBMessage = MBMessageFactory.createIDLMessage("SWIFT", "MT101", localTypeMT101);
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
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.MT101WireMapper
 * JD-Core Version:    0.7.0.1
 */