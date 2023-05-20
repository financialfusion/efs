package com.ffusion.fileimporter.fileadapters.banklookup;

import com.ffusion.csil.FIException;
import com.ffusion.fileimporter.fileadapters.IFileAdapter;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class TFPRtsuBaseAdapter
  implements IFileAdapter
{
  int aQS = 100;
  boolean aQR = true;
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {
    try
    {
      
    }
    catch (Exception localException)
    {
      FIException localFIException = new FIException("com.ffusion.fileimporter.banklookup.TFPRtsuBaseAdapter.initialize", 3, "Unable to initialize TFPRtsuBase file: ", localException);
      DebugLog.throwing("com.ffusion.fileimporter.banklookup.TFPRtsuBaseAdapter.initialize", localFIException);
      throw localFIException;
    }
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {
    try
    {
      com.ffusion.banklookup.datasource.adapters.TFPRtsuBaseAdapter.purge(this.aQS, false);
    }
    catch (Exception localException)
    {
      FIException localFIException = new FIException("com.ffusion.fileimporter.banklookup.TFPRtsuBaseAdapter.open", 9500, "Unable to purge TFPRtsuBase data: ", localException);
      DebugLog.throwing("com.ffusion.fileimporter.banklookup.TFPRtsuBaseAdapter.open", localFIException);
      throw localFIException;
    }
  }
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  public void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    String str = null;
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      for (str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine())
      {
        DebugLog.log("Processing..." + str);
        if (str.length() < 1798)
        {
          DebugLog.log("Error processing..." + str);
          throw new FIException("com.ffusion.fileimporter.banklookup.TFPRtsuBaseAdapter.processFile", 9501, "Unable to process TFP RtsuBase file");
        }
        HashMap localHashMap = new HashMap(118);
        localHashMap.put("FileKey", str.substring(0, 17));
        localHashMap.put("DateUpdated", str.substring(17, 23));
        localHashMap.put("InstitutionCode", str.substring(23, 25));
        localHashMap.put("BranchCode", str.substring(25, 27));
        localHashMap.put("RoutingNumber", str.substring(27, 36));
        localHashMap.put("FFRoutingNumber", str.substring(36, 47));
        localHashMap.put("InstitutionName", str.substring(47, 205));
        localHashMap.put("AbbrInstitutionName", str.substring(205, 255));
        localHashMap.put("Street", str.substring(255, 295));
        localHashMap.put("City", str.substring(295, 325));
        localHashMap.put("StateCode", str.substring(325, 327));
        localHashMap.put("ZipCode", str.substring(327, 332));
        localHashMap.put("ZipExtension", str.substring(332, 336));
        localHashMap.put("MailAddress", str.substring(336, 376));
        localHashMap.put("MailCity", str.substring(376, 406));
        localHashMap.put("MailStateCode", str.substring(406, 408));
        localHashMap.put("MailZip", str.substring(408, 413));
        localHashMap.put("MailZipExtension", str.substring(413, 417));
        localHashMap.put("BranchOffice", str.substring(417, 447));
        localHashMap.put("HeadOfficeRtgNumber", str.substring(447, 456));
        localHashMap.put("PhoneAreaCode", str.substring(456, 459));
        localHashMap.put("PhoneNumber", str.substring(459, 466));
        localHashMap.put("PhoneExtension", str.substring(466, 471));
        localHashMap.put("FaxAreaCode", str.substring(471, 474));
        localHashMap.put("FaxNumber", str.substring(474, 481));
        localHashMap.put("FaxExtension", str.substring(481, 486));
        localHashMap.put("HOAssetSize", str.substring(486, 499));
        localHashMap.put("FedResDistrictCode", str.substring(499, 505));
        localHashMap.put("Yr2000LastUpdated", str.substring(505, 513));
        localHashMap.put("HOFileKey", str.substring(513, 530));
        localHashMap.put("RoutingNumberType", str.substring(530, 558));
        localHashMap.put("RoutingNumberStatus", str.substring(569, 570));
        localHashMap.put("EmployerTaxID", str.substring(570, 580));
        localHashMap.put("ACHFlag", str.substring(580, 581));
        localHashMap.put("FedResRoutingNumber", str.substring(581, 590));
        localHashMap.put("InstitutionIdentifier", str.substring(590, 598));
        localHashMap.put("DateLastUpdated", str.substring(650, 656));
        localHashMap.put("WireXferContact", str.substring(656, 706));
        localHashMap.put("WireXferAreaCode", str.substring(706, 709));
        localHashMap.put("WireXferPhone", str.substring(709, 716));
        localHashMap.put("WireXferExtension", str.substring(716, 721));
        localHashMap.put("EFTContactName", str.substring(721, 771));
        localHashMap.put("EFTAreaCode", str.substring(771, 774));
        localHashMap.put("EFTPhoneNumber", str.substring(774, 781));
        localHashMap.put("EFTExtension", str.substring(781, 786));
        localHashMap.put("EDIContactName", str.substring(786, 836));
        localHashMap.put("EDIContactAreaCode", str.substring(836, 839));
        localHashMap.put("EDIContactPhone", str.substring(839, 846));
        localHashMap.put("EDIContactExtension", str.substring(846, 851));
        localHashMap.put("FedwireFundsStatus", str.substring(851, 852));
        localHashMap.put("FedWireSecStatus", str.substring(852, 853));
        localHashMap.put("FedwireTelName", str.substring(853, 871));
        localHashMap.put("SWIFTAddress", str.substring(871, 896));
        localHashMap.put("BICAddress", str.substring(896, 921));
        localHashMap.put("WireXactCode", str.substring(927, 928));
        localHashMap.put("FedCorresp", str.substring(928, 978));
        localHashMap.put("FedCorrespFdStatus", str.substring(978, 979));
        localHashMap.put("FedCorrespMICR", str.substring(979, 988));
        localHashMap.put("FedCorrespAssets", str.substring(988, 1001));
        localHashMap.put("Yr2000LastUpdated2", str.substring(1001, 1009));
        localHashMap.put("InstitutionIdentifier2", str.substring(1009, 1017));
        localHashMap.put("FedCorrespFileKey", str.substring(1017, 1034));
        localHashMap.put("MainOfficeFileKey", str.substring(1034, 1051));
        localHashMap.put("DateUpdated2", str.substring(1068, 1074));
        localHashMap.put("ACHContactName", str.substring(1074, 1124));
        localHashMap.put("ACHContactAreaCode", str.substring(1124, 1127));
        localHashMap.put("ACHContactPhone", str.substring(1127, 1134));
        localHashMap.put("ACHContactExtension", str.substring(1134, 1139));
        localHashMap.put("EDIContactName2", str.substring(1139, 1189));
        localHashMap.put("EDIContactAreaCode2", str.substring(1189, 1192));
        localHashMap.put("EDIContactPhone2", str.substring(1192, 1199));
        localHashMap.put("EDIContactExtension2", str.substring(1199, 1204));
        localHashMap.put("PaperRetContactName", str.substring(1204, 1254));
        localHashMap.put("PaperRetAreaCode", str.substring(1254, 1257));
        localHashMap.put("PaperRetPhone", str.substring(1257, 1264));
        localHashMap.put("PaperRetExtension", str.substring(1264, 1269));
        localHashMap.put("ACHRetContactName", str.substring(1269, 1319));
        localHashMap.put("ACHRetAreaCode", str.substring(1319, 1322));
        localHashMap.put("ACHRetPhone", str.substring(1322, 1329));
        localHashMap.put("ACHRetExtension", str.substring(1329, 1334));
        localHashMap.put("PaperAdjContactName", str.substring(1334, 1384));
        localHashMap.put("PaperAdjAreaCode", str.substring(1384, 1387));
        localHashMap.put("PaperAdjPhone", str.substring(1387, 1394));
        localHashMap.put("PaperAdjExtension", str.substring(1394, 1399));
        localHashMap.put("ACHAdjContactName", str.substring(1399, 1449));
        localHashMap.put("ACHAdjAreaCode", str.substring(1449, 1452));
        localHashMap.put("ACHAdjPhone", str.substring(1452, 1459));
        localHashMap.put("ACHAdjExtension", str.substring(1459, 1464));
        localHashMap.put("OriginationStatus", str.substring(1464, 1465));
        localHashMap.put("ReceiverStatus", str.substring(1465, 1466));
        localHashMap.put("ACHRoutingNumber", str.substring(1466, 1475));
        localHashMap.put("ACHProvider", str.substring(1475, 1525));
        localHashMap.put("ACHMICRStatus", str.substring(1525, 1526));
        localHashMap.put("CUPaperPayAbbr", str.substring(1526, 1576));
        localHashMap.put("CUPaperPayMICR", str.substring(1576, 1585));
        localHashMap.put("CUAccountId", str.substring(1585, 1595));
        localHashMap.put("CUIDPosition", str.substring(1595, 1597));
        localHashMap.put("ACHPayableThrough", str.substring(1597, 1598));
        localHashMap.put("SavingsAccounts", str.substring(1598, 1599));
        localHashMap.put("CheckingAccounts", str.substring(1599, 1600));
        localHashMap.put("LocalACHAcrony", str.substring(1600, 1679));
        localHashMap.put("ACHMembershipInd", str.substring(1679, 1680));
        localHashMap.put("FedReserveACH", str.substring(1680, 1689));
        localHashMap.put("FedWireFundsXferStatus", str.substring(1689, 1690));
        localHashMap.put("EDICashDisbursmt", str.substring(1690, 1691));
        localHashMap.put("EDICorpTradePayment", str.substring(1691, 1692));
        localHashMap.put("EDICorpTradeExch", str.substring(1692, 1693));
        localHashMap.put("EDICustomerInEntry", str.substring(1693, 1694));
        localHashMap.put("PSACHAssociation", str.substring(1694, 1773));
        localHashMap.put("PSACHMICR", str.substring(1773, 1782));
        localHashMap.put("Yr2000datelastup", str.substring(1782, 1790));
        localHashMap.put("InstitutionIdentifier3", str.substring(1790, 1798));
        com.ffusion.banklookup.datasource.adapters.TFPRtsuBaseAdapter.insert(localHashMap, null, this.aQR);
      }
    }
    catch (Exception localException)
    {
      DebugLog.log("Error processing..." + str);
      FIException localFIException = new FIException("com.ffusion.fileimporter.banklookup.TFPRtsuBaseAdapter.processFile", 9502, "Unable to load TFP RTSUBASE file", localException);
      DebugLog.throwing("com.ffusion.fileimporter.banklookup.TFPRtsuBaseAdapter.processFile", localFIException);
      throw localFIException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.banklookup.TFPRtsuBaseAdapter
 * JD-Core Version:    0.7.0.1
 */