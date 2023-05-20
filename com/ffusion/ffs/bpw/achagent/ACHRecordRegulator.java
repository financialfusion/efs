package com.ffusion.ffs.bpw.achagent;

import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeACKAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeACKEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeADVBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeADVEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeADVFileHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeARCEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeATXAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeATXEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeAcknowledgement_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCBRAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCBREntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCBRandPBRBatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCCDAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCCDEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCIEAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCIEEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCTXAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCTXEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeContestedDishonoredReturn_AddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeContestedDishonoredReturn_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeContestedDishonoredReturn_CorpEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeContestedDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeContestedDishonoredReturn_EntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeDNEAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeDNEEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeDishonoredReturn_AddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeDishonoredReturn_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeDishonoredReturn_CorpEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeDishonoredReturn_EntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeENRAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeENREntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeMTEAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeMTEEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeNotificationOfChange_AddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeNotificationOfChange_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeNotificationOfChange_CorpEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeNotificationOfChange_CrossBorder_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeNotificationOfChange_EntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePBRAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePBREntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePOPEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePOSAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePOSEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePPDAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePPDEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRCKEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRefusedACKEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRefusedATXCorpEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRefusedAcknowledgement_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRefusedNotificationOfChange_AddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRefusedNotificationOfChange_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRefusedNotificationOfChange_CorpEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRefusedNotificationOfChange_CrossBorder_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRefusedNotificationOfChange_EntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeReturn_AddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeReturn_CorpEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeReturn_CrossBorder_AddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeReturn_CrossBorder_BatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeReturn_EntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeSHRAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeSHREntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeTELEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeTRCEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeTRXAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeTRXEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeWEBAddendaRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeWEBEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeXCKEntryDetailRecord;

public class ACHRecordRegulator
{
  private static final String a = "";
  
  public static final String regulateACHId(String paramString)
  {
    String str = "          ";
    if ((paramString != null) || (paramString.length() >= 10)) {
      str = paramString;
    } else {
      str = str + paramString;
    }
    return str.substring(str.length() - 10, str.length());
  }
  
  public static final void regulateImmediateOriginAndDestination(ACHRecordInfo paramACHRecordInfo)
  {
    if ((paramACHRecordInfo != null) && (paramACHRecordInfo.getRecordType() == 1))
    {
      String str = paramACHRecordInfo.getFieldValueString("Immediate_Destination");
      paramACHRecordInfo.setFieldValueObject("Immediate_Destination", regulateACHId(str));
      str = paramACHRecordInfo.getFieldValueString("Immediate_Origin");
      paramACHRecordInfo.setFieldValueObject("Immediate_Origin", regulateACHId(str));
    }
  }
  
  public static final void regulateRecordEntryHash(ACHRecordInfo paramACHRecordInfo)
  {
    int i = paramACHRecordInfo.getRecordType();
    if ((i == 8) || (i == 9))
    {
      long l1 = paramACHRecordInfo.getFieldValueLong("Entry_Hash");
      if (l1 > 9999999999L)
      {
        long l2 = l1 % 10000000000L;
        paramACHRecordInfo.setFieldValueObject("Entry_Hash", new Long(l2));
      }
    }
  }
  
  public static final void regulateRecord(Object paramObject)
  {
    Class localClass = paramObject.getClass();
    if ((localClass.equals(TypeACKEntryDetailRecord.class)) || (localClass.equals(TypeACKAddendaRecord.class)) || (localClass.equals(TypeFileControlRecord.class)) || (localClass.equals(TypeNotificationOfChange_AddendaRecord.class)) || (localClass.equals(TypeRefusedACKEntryDetailRecord.class)) || (localClass.equals(TypeRefusedATXCorpEntryDetailRecord.class)) || (localClass.equals(TypeRefusedNotificationOfChange_AddendaRecord.class)) || (localClass.equals(TypeSHREntryDetailRecord.class))) {
      return;
    }
    Object localObject;
    if (localClass.equals(TypeAcknowledgement_BatchHeaderRecord.class))
    {
      localObject = (TypeAcknowledgement_BatchHeaderRecord)paramObject;
      if (!((TypeAcknowledgement_BatchHeaderRecord)localObject).Company_Discretionary_DataExists)
      {
        ((TypeAcknowledgement_BatchHeaderRecord)localObject).Company_Discretionary_Data = "";
        ((TypeAcknowledgement_BatchHeaderRecord)localObject).Company_Discretionary_DataExists = true;
      }
      if (!((TypeAcknowledgement_BatchHeaderRecord)localObject).Company_Descriptive_DateExists)
      {
        ((TypeAcknowledgement_BatchHeaderRecord)localObject).Company_Descriptive_Date = "";
        ((TypeAcknowledgement_BatchHeaderRecord)localObject).Company_Descriptive_DateExists = true;
      }
      if (!((TypeAcknowledgement_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeAcknowledgement_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeAcknowledgement_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeADVBatchControlRecord.class))
    {
      localObject = (TypeADVBatchControlRecord)paramObject;
      if (!((TypeADVBatchControlRecord)localObject).ACH_Operator_DataExists)
      {
        ((TypeADVBatchControlRecord)localObject).ACH_Operator_Data = "";
        ((TypeADVBatchControlRecord)localObject).ACH_Operator_DataExists = true;
      }
    }
    else if (localClass.equals(TypeADVEntryDetailRecord.class))
    {
      localObject = (TypeADVEntryDetailRecord)paramObject;
      if (!((TypeADVEntryDetailRecord)localObject).File_IdentificationExists)
      {
        ((TypeADVEntryDetailRecord)localObject).File_Identification = "";
        ((TypeADVEntryDetailRecord)localObject).File_IdentificationExists = true;
      }
      if (!((TypeADVEntryDetailRecord)localObject).ACH_Entry_Operator_DataExists)
      {
        ((TypeADVEntryDetailRecord)localObject).ACH_Entry_Operator_Data = "";
        ((TypeADVEntryDetailRecord)localObject).ACH_Entry_Operator_DataExists = true;
      }
      if (!((TypeADVEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeADVEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeADVEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeADVFileHeaderRecord.class))
    {
      localObject = (TypeADVFileHeaderRecord)paramObject;
      if (!((TypeADVFileHeaderRecord)localObject).File_Creation_TimeExists)
      {
        ((TypeADVFileHeaderRecord)localObject).File_Creation_Time = "";
        ((TypeADVFileHeaderRecord)localObject).File_Creation_TimeExists = true;
      }
      if (!((TypeADVFileHeaderRecord)localObject).Reference_CodeExists)
      {
        ((TypeADVFileHeaderRecord)localObject).Reference_Code = "";
        ((TypeADVFileHeaderRecord)localObject).Reference_CodeExists = true;
      }
    }
    else if (localClass.equals(TypeARCEntryDetailRecord.class))
    {
      localObject = (TypeARCEntryDetailRecord)paramObject;
      if (!((TypeARCEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeARCEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeARCEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeATXAddendaRecord.class))
    {
      localObject = (TypeATXAddendaRecord)paramObject;
      if (!((TypeATXAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypeATXAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypeATXAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeATXEntryDetailRecord.class))
    {
      localObject = (TypeATXEntryDetailRecord)paramObject;
      if (!((TypeATXEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeATXEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeATXEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeBatchControlRecord.class))
    {
      localObject = (TypeBatchControlRecord)paramObject;
      if (!((TypeBatchControlRecord)localObject).Message_Authentication_CodeExists)
      {
        ((TypeBatchControlRecord)localObject).Message_Authentication_Code = "";
        ((TypeBatchControlRecord)localObject).Message_Authentication_CodeExists = true;
      }
    }
    else if (localClass.equals(TypeBatchHeaderRecord.class))
    {
      localObject = (TypeBatchHeaderRecord)paramObject;
      if (!((TypeBatchHeaderRecord)localObject).Company_Descriptive_DateExists)
      {
        ((TypeBatchHeaderRecord)localObject).Company_Descriptive_Date = "";
        ((TypeBatchHeaderRecord)localObject).Company_Descriptive_DateExists = true;
      }
      if (!((TypeBatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeBatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeBatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
      if ((((TypeBatchHeaderRecord)localObject).Company_Discretionary_Data == null) || (((TypeBatchHeaderRecord)localObject).Company_Discretionary_Data.length() == 0)) {
        ((TypeBatchHeaderRecord)localObject).Company_Discretionary_Data = "";
      }
    }
    else if (localClass.equals(TypeCBRAddendaRecord.class))
    {
      localObject = (TypeCBRAddendaRecord)paramObject;
      if (!((TypeCBRAddendaRecord)localObject).Foreign_Trace_NumberExists)
      {
        ((TypeCBRAddendaRecord)localObject).Foreign_Trace_Number = 0L;
        ((TypeCBRAddendaRecord)localObject).Foreign_Trace_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeCBREntryDetailRecord.class))
    {
      localObject = (TypeCBREntryDetailRecord)paramObject;
      if (!((TypeCBREntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeCBREntryDetailRecord)localObject).Identification_Number = "";
        ((TypeCBREntryDetailRecord)localObject).Identification_NumberExists = true;
      }
      if (!((TypeCBREntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeCBREntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeCBREntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeCCDAddendaRecord.class))
    {
      localObject = (TypeCCDAddendaRecord)paramObject;
      if (!((TypeCCDAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypeCCDAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypeCCDAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeCCDEntryDetailRecord.class))
    {
      localObject = (TypeCCDEntryDetailRecord)paramObject;
      if (!((TypeCCDEntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeCCDEntryDetailRecord)localObject).Identification_Number = "";
        ((TypeCCDEntryDetailRecord)localObject).Identification_NumberExists = true;
      }
      if (!((TypeCCDEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeCCDEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeCCDEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeCIEAddendaRecord.class))
    {
      localObject = (TypeCIEAddendaRecord)paramObject;
      if (!((TypeCIEAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypeCIEAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypeCIEAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeCIEEntryDetailRecord.class))
    {
      localObject = (TypeCIEEntryDetailRecord)paramObject;
      if (!((TypeCIEEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeCIEEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeCIEEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeContestedDishonoredReturn_BatchHeaderRecord.class))
    {
      localObject = (TypeContestedDishonoredReturn_BatchHeaderRecord)paramObject;
      if (!((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Company_Discretionary_DataExists)
      {
        ((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Company_Discretionary_Data = "";
        ((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Company_Discretionary_DataExists = true;
      }
      if (!((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Company_Descriptive_DateExists)
      {
        ((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Company_Descriptive_Date = "";
        ((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Company_Descriptive_DateExists = true;
      }
      if (!((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeContestedDishonoredReturn_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeContestedDishonoredReturn_CorpEntryDetailRecord.class))
    {
      localObject = (TypeContestedDishonoredReturn_CorpEntryDetailRecord)paramObject;
      if (!((TypeContestedDishonoredReturn_CorpEntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeContestedDishonoredReturn_CorpEntryDetailRecord)localObject).Identification_Number = "";
        ((TypeContestedDishonoredReturn_CorpEntryDetailRecord)localObject).Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeContestedDishonoredReturn_EntryDetailRecord.class))
    {
      localObject = (TypeContestedDishonoredReturn_EntryDetailRecord)paramObject;
      if (!((TypeContestedDishonoredReturn_EntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypeContestedDishonoredReturn_EntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypeContestedDishonoredReturn_EntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeCTXAddendaRecord.class))
    {
      localObject = (TypeCTXAddendaRecord)paramObject;
      if (!((TypeCTXAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypeCTXAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypeCTXAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeCTXEntryDetailRecord.class))
    {
      localObject = (TypeCTXEntryDetailRecord)paramObject;
      if (!((TypeCTXEntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeCTXEntryDetailRecord)localObject).Identification_Number = "";
        ((TypeCTXEntryDetailRecord)localObject).Identification_NumberExists = true;
      }
      if (!((TypeCTXEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeCTXEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeCTXEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeDishonoredReturn_BatchHeaderRecord.class))
    {
      localObject = (TypeDishonoredReturn_BatchHeaderRecord)paramObject;
      if (!((TypeDishonoredReturn_BatchHeaderRecord)localObject).Company_Discretionary_DataExists)
      {
        ((TypeDishonoredReturn_BatchHeaderRecord)localObject).Company_Discretionary_Data = "";
        ((TypeDishonoredReturn_BatchHeaderRecord)localObject).Company_Discretionary_DataExists = true;
      }
      if (!((TypeDishonoredReturn_BatchHeaderRecord)localObject).Company_Descriptive_DateExists)
      {
        ((TypeDishonoredReturn_BatchHeaderRecord)localObject).Company_Descriptive_Date = "";
        ((TypeDishonoredReturn_BatchHeaderRecord)localObject).Company_Descriptive_DateExists = true;
      }
      if (!((TypeDishonoredReturn_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeDishonoredReturn_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeDishonoredReturn_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeDishonoredReturn_CorpEntryDetailRecord.class))
    {
      localObject = (TypeDishonoredReturn_CorpEntryDetailRecord)paramObject;
      if (!((TypeDishonoredReturn_CorpEntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeDishonoredReturn_CorpEntryDetailRecord)localObject).Identification_Number = "";
        ((TypeDishonoredReturn_CorpEntryDetailRecord)localObject).Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeDishonoredReturn_EntryDetailRecord.class))
    {
      localObject = (TypeDishonoredReturn_EntryDetailRecord)paramObject;
      if (!((TypeDishonoredReturn_EntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypeDishonoredReturn_EntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypeDishonoredReturn_EntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeDNEAddendaRecord.class))
    {
      localObject = (TypeDNEAddendaRecord)paramObject;
      if (!((TypeDNEAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypeDNEAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypeDNEAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeDNEEntryDetailRecord.class))
    {
      localObject = (TypeDNEEntryDetailRecord)paramObject;
      if (!((TypeDNEEntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypeDNEEntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypeDNEEntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
      if (!((TypeDNEEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeDNEEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeDNEEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeENRAddendaRecord.class))
    {
      localObject = (TypeENRAddendaRecord)paramObject;
      if (!((TypeENRAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypeENRAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypeENRAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeENREntryDetailRecord.class))
    {
      localObject = (TypeENREntryDetailRecord)paramObject;
      if (!((TypeENREntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeENREntryDetailRecord)localObject).Identification_Number = "";
        ((TypeENREntryDetailRecord)localObject).Identification_NumberExists = true;
      }
      if (!((TypeENREntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeENREntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeENREntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeFileHeaderRecord.class))
    {
      localObject = (TypeFileHeaderRecord)paramObject;
      if (!((TypeFileHeaderRecord)localObject).File_Creation_TimeExists)
      {
        ((TypeFileHeaderRecord)localObject).File_Creation_Time = "";
        ((TypeFileHeaderRecord)localObject).File_Creation_TimeExists = true;
      }
      if (!((TypeFileHeaderRecord)localObject).Immediate_Destination_NameExists)
      {
        ((TypeFileHeaderRecord)localObject).Immediate_Destination_Name = "";
        ((TypeFileHeaderRecord)localObject).Immediate_Destination_NameExists = true;
      }
      if (!((TypeFileHeaderRecord)localObject).Immediate_Origin_NameExists)
      {
        ((TypeFileHeaderRecord)localObject).Immediate_Origin_Name = "";
        ((TypeFileHeaderRecord)localObject).Immediate_Origin_NameExists = true;
      }
      if (!((TypeFileHeaderRecord)localObject).Reference_CodeExists)
      {
        ((TypeFileHeaderRecord)localObject).Reference_Code = "";
        ((TypeFileHeaderRecord)localObject).Reference_CodeExists = true;
      }
    }
    else if (localClass.equals(TypeMTEAddendaRecord.class))
    {
      localObject = (TypeMTEAddendaRecord)paramObject;
      if (!((TypeMTEAddendaRecord)localObject).Network_Identification_CodeExists)
      {
        ((TypeMTEAddendaRecord)localObject).Network_Identification_Code = "";
        ((TypeMTEAddendaRecord)localObject).Network_Identification_CodeExists = true;
      }
    }
    else if (localClass.equals(TypeMTEEntryDetailRecord.class))
    {
      localObject = (TypeMTEEntryDetailRecord)paramObject;
      if (!((TypeMTEEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeMTEEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeMTEEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeNotificationOfChange_BatchHeaderRecord.class))
    {
      localObject = (TypeNotificationOfChange_BatchHeaderRecord)paramObject;
      if (!((TypeNotificationOfChange_BatchHeaderRecord)localObject).Company_Discretionary_DataExists)
      {
        ((TypeNotificationOfChange_BatchHeaderRecord)localObject).Company_Discretionary_Data = "";
        ((TypeNotificationOfChange_BatchHeaderRecord)localObject).Company_Discretionary_DataExists = true;
      }
      if (!((TypeNotificationOfChange_BatchHeaderRecord)localObject).Company_Descriptive_DateExists)
      {
        ((TypeNotificationOfChange_BatchHeaderRecord)localObject).Company_Descriptive_Date = "";
        ((TypeNotificationOfChange_BatchHeaderRecord)localObject).Company_Descriptive_DateExists = true;
      }
      if (!((TypeNotificationOfChange_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeNotificationOfChange_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeNotificationOfChange_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeNotificationOfChange_CorpEntryDetailRecord.class))
    {
      localObject = (TypeNotificationOfChange_CorpEntryDetailRecord)paramObject;
      if (!((TypeNotificationOfChange_CorpEntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeNotificationOfChange_CorpEntryDetailRecord)localObject).Identification_Number = "";
        ((TypeNotificationOfChange_CorpEntryDetailRecord)localObject).Identification_NumberExists = true;
      }
      if (!((TypeNotificationOfChange_CorpEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeNotificationOfChange_CorpEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeNotificationOfChange_CorpEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeNotificationOfChange_EntryDetailRecord.class))
    {
      localObject = (TypeNotificationOfChange_EntryDetailRecord)paramObject;
      if (!((TypeNotificationOfChange_EntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypeNotificationOfChange_EntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypeNotificationOfChange_EntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
      if (!((TypeNotificationOfChange_EntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeNotificationOfChange_EntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeNotificationOfChange_EntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypePBRAddendaRecord.class))
    {
      localObject = (TypePBRAddendaRecord)paramObject;
      if (!((TypePBRAddendaRecord)localObject).Foreign_Trace_NumberExists)
      {
        ((TypePBRAddendaRecord)localObject).Foreign_Trace_Number = 0L;
        ((TypePBRAddendaRecord)localObject).Foreign_Trace_NumberExists = true;
      }
    }
    else if (localClass.equals(TypePBREntryDetailRecord.class))
    {
      localObject = (TypePBREntryDetailRecord)paramObject;
      if (!((TypePBREntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypePBREntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypePBREntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
      if (!((TypePBREntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypePBREntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypePBREntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypePOPEntryDetailRecord.class))
    {
      localObject = (TypePOPEntryDetailRecord)paramObject;
      if (!((TypePOPEntryDetailRecord)localObject).Individual_NameExists)
      {
        ((TypePOPEntryDetailRecord)localObject).Individual_Name = "";
        ((TypePOPEntryDetailRecord)localObject).Individual_NameExists = true;
      }
      if (!((TypePOPEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypePOPEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypePOPEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypePOSAddendaRecord.class))
    {
      localObject = (TypePOSAddendaRecord)paramObject;
      if (!((TypePOSAddendaRecord)localObject).Reference_Information1Exists)
      {
        ((TypePOSAddendaRecord)localObject).Reference_Information1 = "";
        ((TypePOSAddendaRecord)localObject).Reference_Information1Exists = true;
      }
      if (!((TypePOSAddendaRecord)localObject).Reference_Information2Exists)
      {
        ((TypePOSAddendaRecord)localObject).Reference_Information2 = "";
        ((TypePOSAddendaRecord)localObject).Reference_Information2Exists = true;
      }
      if (!((TypePOSAddendaRecord)localObject).Authorization_Code_Or_Card_Expiration_DateExists)
      {
        ((TypePOSAddendaRecord)localObject).Authorization_Code_Or_Card_Expiration_Date = "";
        ((TypePOSAddendaRecord)localObject).Authorization_Code_Or_Card_Expiration_DateExists = true;
      }
    }
    else if (localClass.equals(TypePOSEntryDetailRecord.class))
    {
      localObject = (TypePOSEntryDetailRecord)paramObject;
      if (!((TypePOSEntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypePOSEntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypePOSEntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypePPDAddendaRecord.class))
    {
      localObject = (TypePPDAddendaRecord)paramObject;
      if (!((TypePPDAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypePPDAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypePPDAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypePPDEntryDetailRecord.class))
    {
      localObject = (TypePPDEntryDetailRecord)paramObject;
      if (!((TypePPDEntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypePPDEntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypePPDEntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
      if (!((TypePPDEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypePPDEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypePPDEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeRCKEntryDetailRecord.class))
    {
      localObject = (TypeRCKEntryDetailRecord)paramObject;
      if (!((TypeRCKEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeRCKEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeRCKEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeRefusedAcknowledgement_BatchHeaderRecord.class))
    {
      localObject = (TypeRefusedAcknowledgement_BatchHeaderRecord)paramObject;
      if (!((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Company_Discretionary_DataExists)
      {
        ((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Company_Discretionary_Data = "";
        ((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Company_Discretionary_DataExists = true;
      }
      if (!((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Company_Descriptive_DateExists)
      {
        ((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Company_Descriptive_Date = "";
        ((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Company_Descriptive_DateExists = true;
      }
      if (!((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeRefusedAcknowledgement_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeRefusedNotificationOfChange_BatchHeaderRecord.class))
    {
      localObject = (TypeRefusedNotificationOfChange_BatchHeaderRecord)paramObject;
      if (!((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Company_Discretionary_DataExists)
      {
        ((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Company_Discretionary_Data = "";
        ((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Company_Discretionary_DataExists = true;
      }
      if (!((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Company_Descriptive_DateExists)
      {
        ((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Company_Descriptive_Date = "";
        ((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Company_Descriptive_DateExists = true;
      }
      if (!((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeRefusedNotificationOfChange_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeRefusedNotificationOfChange_CorpEntryDetailRecord.class))
    {
      localObject = (TypeRefusedNotificationOfChange_CorpEntryDetailRecord)paramObject;
      if (!((TypeRefusedNotificationOfChange_CorpEntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeRefusedNotificationOfChange_CorpEntryDetailRecord)localObject).Identification_Number = "";
        ((TypeRefusedNotificationOfChange_CorpEntryDetailRecord)localObject).Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeRefusedNotificationOfChange_EntryDetailRecord.class))
    {
      localObject = (TypeRefusedNotificationOfChange_EntryDetailRecord)paramObject;
      if (!((TypeRefusedNotificationOfChange_EntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypeRefusedNotificationOfChange_EntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypeRefusedNotificationOfChange_EntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeReturn_AddendaRecord.class))
    {
      localObject = (TypeReturn_AddendaRecord)paramObject;
      if (!((TypeReturn_AddendaRecord)localObject).Date_Of_DeathExists)
      {
        ((TypeReturn_AddendaRecord)localObject).Date_Of_Death = "";
        ((TypeReturn_AddendaRecord)localObject).Date_Of_DeathExists = true;
      }
      if (!((TypeReturn_AddendaRecord)localObject).Addenda_InformationExists)
      {
        ((TypeReturn_AddendaRecord)localObject).Addenda_Information = "";
        ((TypeReturn_AddendaRecord)localObject).Addenda_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeReturn_CorpEntryDetailRecord.class))
    {
      localObject = (TypeReturn_CorpEntryDetailRecord)paramObject;
      if (!((TypeReturn_CorpEntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeReturn_CorpEntryDetailRecord)localObject).Identification_Number = "";
        ((TypeReturn_CorpEntryDetailRecord)localObject).Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeReturn_CrossBorder_AddendaRecord.class))
    {
      localObject = (TypeReturn_CrossBorder_AddendaRecord)paramObject;
      if (!((TypeReturn_CrossBorder_AddendaRecord)localObject).Date_Of_DeathExists)
      {
        ((TypeReturn_CrossBorder_AddendaRecord)localObject).Date_Of_Death = "";
        ((TypeReturn_CrossBorder_AddendaRecord)localObject).Date_Of_DeathExists = true;
      }
      if (!((TypeReturn_CrossBorder_AddendaRecord)localObject).Addenda_Information8Exists)
      {
        ((TypeReturn_CrossBorder_AddendaRecord)localObject).Addenda_Information8 = "";
        ((TypeReturn_CrossBorder_AddendaRecord)localObject).Addenda_Information8Exists = true;
      }
    }
    else if (localClass.equals(TypeReturn_EntryDetailRecord.class))
    {
      localObject = (TypeReturn_EntryDetailRecord)paramObject;
      if (!((TypeReturn_EntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypeReturn_EntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypeReturn_EntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeSHRAddendaRecord.class))
    {
      localObject = (TypeSHRAddendaRecord)paramObject;
      if (!((TypeSHRAddendaRecord)localObject).Reference_Information1Exists)
      {
        ((TypeSHRAddendaRecord)localObject).Reference_Information1 = "";
        ((TypeSHRAddendaRecord)localObject).Reference_Information1Exists = true;
      }
      if (!((TypeSHRAddendaRecord)localObject).Reference_Information2Exists)
      {
        ((TypeSHRAddendaRecord)localObject).Reference_Information2 = "";
        ((TypeSHRAddendaRecord)localObject).Reference_Information2Exists = true;
      }
      if (!((TypeSHRAddendaRecord)localObject).Authorization_Code_Or_Card_Expiration_DateExists)
      {
        ((TypeSHRAddendaRecord)localObject).Authorization_Code_Or_Card_Expiration_Date = "";
        ((TypeSHRAddendaRecord)localObject).Authorization_Code_Or_Card_Expiration_DateExists = true;
      }
    }
    else if (localClass.equals(TypeTELEntryDetailRecord.class))
    {
      localObject = (TypeTELEntryDetailRecord)paramObject;
      if (!((TypeTELEntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypeTELEntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypeTELEntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
      if (!((TypeTELEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeTELEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeTELEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeTRCEntryDetailRecord.class))
    {
      localObject = (TypeTRCEntryDetailRecord)paramObject;
      if (!((TypeTRCEntryDetailRecord)localObject).Check_Serial_NumberExists)
      {
        ((TypeTRCEntryDetailRecord)localObject).Check_Serial_Number = "";
        ((TypeTRCEntryDetailRecord)localObject).Check_Serial_NumberExists = true;
      }
      if (!((TypeTRCEntryDetailRecord)localObject).Item_Type_IndicatorExists)
      {
        ((TypeTRCEntryDetailRecord)localObject).Item_Type_Indicator = "";
        ((TypeTRCEntryDetailRecord)localObject).Item_Type_IndicatorExists = true;
      }
    }
    else if (localClass.equals(TypeTRXAddendaRecord.class))
    {
      localObject = (TypeTRXAddendaRecord)paramObject;
      if (!((TypeTRXAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypeTRXAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypeTRXAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeTRXEntryDetailRecord.class))
    {
      localObject = (TypeTRXEntryDetailRecord)paramObject;
      if (!((TypeTRXEntryDetailRecord)localObject).Identification_NumberExists)
      {
        ((TypeTRXEntryDetailRecord)localObject).Identification_Number = "";
        ((TypeTRXEntryDetailRecord)localObject).Identification_NumberExists = true;
      }
      if (!((TypeTRXEntryDetailRecord)localObject).Item_Type_IndicatorExists)
      {
        ((TypeTRXEntryDetailRecord)localObject).Item_Type_Indicator = "";
        ((TypeTRXEntryDetailRecord)localObject).Item_Type_IndicatorExists = true;
      }
    }
    else if (localClass.equals(TypeWEBAddendaRecord.class))
    {
      localObject = (TypeWEBAddendaRecord)paramObject;
      if (!((TypeWEBAddendaRecord)localObject).Payment_Related_InformationExists)
      {
        ((TypeWEBAddendaRecord)localObject).Payment_Related_Information = "";
        ((TypeWEBAddendaRecord)localObject).Payment_Related_InformationExists = true;
      }
    }
    else if (localClass.equals(TypeWEBEntryDetailRecord.class))
    {
      localObject = (TypeWEBEntryDetailRecord)paramObject;
      if (!((TypeWEBEntryDetailRecord)localObject).Individual_Identification_NumberExists)
      {
        ((TypeWEBEntryDetailRecord)localObject).Individual_Identification_Number = "";
        ((TypeWEBEntryDetailRecord)localObject).Individual_Identification_NumberExists = true;
      }
    }
    else if (localClass.equals(TypeXCKEntryDetailRecord.class))
    {
      localObject = (TypeXCKEntryDetailRecord)paramObject;
      if (!((TypeXCKEntryDetailRecord)localObject).Discretionary_DataExists)
      {
        ((TypeXCKEntryDetailRecord)localObject).Discretionary_Data = "";
        ((TypeXCKEntryDetailRecord)localObject).Discretionary_DataExists = true;
      }
    }
    else if (localClass.equals(TypeCBRandPBRBatchHeaderRecord.class))
    {
      localObject = (TypeCBRandPBRBatchHeaderRecord)paramObject;
      if (!((TypeCBRandPBRBatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeCBRandPBRBatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeCBRandPBRBatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeContestedDishonoredReturn_AddendaRecord.class))
    {
      localObject = (TypeContestedDishonoredReturn_AddendaRecord)paramObject;
      if (!((TypeContestedDishonoredReturn_AddendaRecord)localObject).Return_Settlement_DateExists)
      {
        ((TypeContestedDishonoredReturn_AddendaRecord)localObject).Return_Settlement_Date = "";
        ((TypeContestedDishonoredReturn_AddendaRecord)localObject).Return_Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeContestedDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord.class))
    {
      localObject = (TypeContestedDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord)paramObject;
      if (!((TypeContestedDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeContestedDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeContestedDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeDishonoredReturn_AddendaRecord.class))
    {
      localObject = (TypeDishonoredReturn_AddendaRecord)paramObject;
      if (!((TypeDishonoredReturn_AddendaRecord)localObject).Return_Settlement_DateExists)
      {
        ((TypeDishonoredReturn_AddendaRecord)localObject).Return_Settlement_Date = "";
        ((TypeDishonoredReturn_AddendaRecord)localObject).Return_Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord.class))
    {
      localObject = (TypeDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord)paramObject;
      if (!((TypeDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeDishonoredReturn_CrossBorderAutomated_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeNotificationOfChange_CrossBorder_BatchHeaderRecord.class))
    {
      localObject = (TypeNotificationOfChange_CrossBorder_BatchHeaderRecord)paramObject;
      if (!((TypeNotificationOfChange_CrossBorder_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeNotificationOfChange_CrossBorder_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeNotificationOfChange_CrossBorder_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeRefusedNotificationOfChange_CrossBorder_BatchHeaderRecord.class))
    {
      localObject = (TypeRefusedNotificationOfChange_CrossBorder_BatchHeaderRecord)paramObject;
      if (!((TypeRefusedNotificationOfChange_CrossBorder_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeRefusedNotificationOfChange_CrossBorder_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeRefusedNotificationOfChange_CrossBorder_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
    else if (localClass.equals(TypeReturn_CrossBorder_BatchHeaderRecord.class))
    {
      localObject = (TypeReturn_CrossBorder_BatchHeaderRecord)paramObject;
      if (!((TypeReturn_CrossBorder_BatchHeaderRecord)localObject).Settlement_DateExists)
      {
        ((TypeReturn_CrossBorder_BatchHeaderRecord)localObject).Settlement_Date = "";
        ((TypeReturn_CrossBorder_BatchHeaderRecord)localObject).Settlement_DateExists = true;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.achagent.ACHRecordRegulator
 * JD-Core Version:    0.7.0.1
 */