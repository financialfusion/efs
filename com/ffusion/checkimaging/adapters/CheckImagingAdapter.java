package com.ffusion.checkimaging.adapters;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageRequest;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.checkimaging.CheckImagingException;
import com.ffusion.imagesim.irb.IrbRequestClient;
import com.ffusion.imagesim.irb.IrbRequestClientInterface;
import com.ffusion.util.PropertiesUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class CheckImagingAdapter
  implements CheckImageDefines, CheckImageTags
{
  private static String Wf;
  private static String VU;
  private static int V4;
  private static String Vz;
  private static String VM;
  private static String Vt;
  private static String VE;
  private static String Wn;
  private static String Vr;
  private static String VI;
  private static String VN;
  private static String VJ;
  private static String VK;
  private static String Wd;
  private static String Vu;
  private static String Vs;
  private static String Vm;
  private static String VO;
  private static String Vy;
  private static String Wm;
  private static String VB;
  private static String Vx;
  private static String VC;
  private static String VR;
  private static String Vv;
  private static String VP;
  private static String Wl;
  private static String V9;
  private static String VA;
  private static String VW;
  private static int VD = 500;
  private static int VF = 20;
  private static boolean VH = false;
  private static String Wc;
  private static String Wg = "60";
  private static String Wk = "100";
  private static String VV = "100";
  private static String Vo = "0";
  private static String VQ = "FTB01-RSL";
  private static String Vn = "FBW";
  private static String VZ = "BGS";
  private static String Vp = "JPG";
  private static String Wj = null;
  private static boolean Vw = false;
  private static String Vq;
  private static XMLTag VL;
  private static final Boolean VS = new Boolean(true);
  private static final String Wi = "select * from PENDING_IMAGES where user_id=?";
  private static final String VT = "insert into PENDING_IMAGES (image_handle,user_id,account_id,check_number,routing_transit_number,trans_code,dr_cr,sequence_number,capture_sequence,deposit_trace_id,entry_number,tracer_number,package_id,bank_id,status,estimate,amount,fee,charge_account,dep_id,fee_flag,fee_processed_flag,front_view_handle,back_view_handle,storage_tier,compression_type,return_code,reason_code,error_code,date_placed,posting_date,requested_date)  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  private static final String VG = "update PENDING_IMAGES set status=? where image_handle=? AND user_id=? AND account_id=?";
  private static final String Wh = "delete from PENDING_IMAGES where image_handle=? AND user_id=? AND account_id=?";
  private static String V8 = "<?xml version=\"1.0\"?>\n<!DOCTYPE requestPackage SYSTEM \"irbRequestPackage.dtd\">\n<requestPackage>\n\t<itemRequest>\n\t\t<userRequestId></userRequestId>\n\t\t<description></description>\n\t\t<itemSelection>\n\t\t\t<timeout>";
  private static String V6 = "</timeout>\n\t\t\t<maxItemsReturned>";
  private static String V5 = "</maxItemsReturned>\n\t\t\t<ignoreTierOnFailover>";
  private static String V3 = "</ignoreTierOnFailover>\n\t\t\t<itemType>";
  private static String V2 = "</itemType>\n\t\t\t<repository>";
  private static String V1 = "</repository>\n\t\t\t<criteria>\n";
  private static String V0 = "\t\t\t</criteria>\n";
  private static String VY = "\t\t\t<view>\n\t\t\t\t<viewType>";
  private static String VX = "</viewType>\n\t\t\t\t<compression width='725' height='331'>";
  private static String We = "</compression>\n\t\t\t</view>\n\t\t\t<view>\n\t\t\t\t<viewType>";
  private static String Wb = "</viewType>\n\t\t\t\t<compression width='725' height='331'>";
  private static String Wa = "</compression>\n\t\t\t</view>\n";
  private static String V7 = "\t\t</itemSelection>\n\t</itemRequest>\n</requestPackage>\n";
  
  public static void initialize(HashMap paramHashMap)
    throws CheckImagingException
  {
    String str1 = "CheckImagingAdapter.initialize";
    try
    {
      if (!Vw)
      {
        Vq = (String)paramHashMap.get("URL");
        if ((Vq == null) || (Vq.equals(""))) {
          throw new MalformedURLException("Empty URL");
        }
        InputStream localInputStream = ResourceUtil.getResourceAsStream(new CheckImagingAdapter(), Vq);
        if (localInputStream != null)
        {
          String str2 = null;
          String str3 = null;
          String str4 = null;
          String str5 = null;
          str2 = Strings.streamToString(localInputStream);
          XMLTag localXMLTag1 = new XMLTag();
          localXMLTag1.build(str2);
          HashMap localHashMap1 = localXMLTag1.getTagHashMap();
          XMLTag localXMLTag2 = new XMLTag();
          localXMLTag2 = localXMLTag1.getContainedTag("CHECKIMAGING_DATASOURCE");
          HashMap localHashMap2 = localXMLTag2.getTagHashMap();
          VO = jdMethod_byte(localHashMap2, "ConnectionType".toUpperCase(), null);
          Vy = jdMethod_byte(localHashMap2, "Server".toUpperCase(), null);
          Wm = jdMethod_byte(localHashMap2, "DBName".toUpperCase(), null);
          VB = jdMethod_byte(localHashMap2, "Port".toUpperCase(), null);
          Vx = jdMethod_byte(localHashMap2, "DefaultConnections".toUpperCase(), null);
          VC = jdMethod_byte(localHashMap2, "DefaultConnections".toUpperCase(), null);
          VR = jdMethod_byte(localHashMap2, "PoolType".toUpperCase(), null);
          Vv = jdMethod_byte(localHashMap2, "User".toUpperCase(), null);
          VP = jdMethod_byte(localHashMap2, "Password".toUpperCase(), null);
          Wl = jdMethod_byte(localHashMap2, "PoolName".toUpperCase(), "");
          V9 = jdMethod_byte(localHashMap2, "JDBCUrl".toUpperCase(), "");
          VA = jdMethod_byte(localHashMap2, "DriverClass".toUpperCase(), "");
          VW = jdMethod_byte(localHashMap2, "License".toUpperCase(), "");
          M();
          VL = localXMLTag1.getContainedTag("TRACEID_ERRORS");
          Wf = jdMethod_byte(localHashMap1, "SERVER_URL", null);
          VI = jdMethod_byte(localHashMap1, "SMTP_HOST", null);
          VN = jdMethod_byte(localHashMap1, "EMAIL_FROM_ADDRESS", null);
          str3 = jdMethod_byte(localHashMap1, "IMAGE_MAX_AGE", null);
          if ((str3 != null) && (!str3.equals(""))) {
            V4 = Integer.parseInt(str3);
          }
          VU = jdMethod_byte(localHashMap1, "DO_NOT_SEARCH_BEFORE_DATE", null);
          Vz = jdMethod_byte(localHashMap1, "FEE_LOG_FILE", null);
          VM = jdMethod_byte(localHashMap1, "FEE_LOGGING", null);
          Vt = jdMethod_byte(localHashMap1, "IMAGE_FEE", null);
          VE = jdMethod_byte(localHashMap1, "IMAGE_FEE_MESSAGE_CODE", null);
          Wn = jdMethod_byte(localHashMap1, "IMAGE_FEE_MESSAGE_RETAIL", null);
          Vr = jdMethod_byte(localHashMap1, "IMAGE_FEE_MESSAGE_BIZ", null);
          VJ = jdMethod_byte(localHashMap1, "IMAGING_RETAIL_ON", null);
          VK = jdMethod_byte(localHashMap1, "IMAGING_RETAIL_OFFSETS", null);
          Wd = jdMethod_byte(localHashMap1, "IMAGING_BIZ_ON", null);
          Vu = jdMethod_byte(localHashMap1, "IMAGING_BIZ_OFFSETS", null);
          Wg = jdMethod_byte(localHashMap1, "TIMEOUT", Wg);
          Wk = jdMethod_byte(localHashMap1, "MAXSEARCHITEMS", Wk);
          Wk = jdMethod_byte(localHashMap1, "MAXIMAGEITEMS", Wk);
          Vo = jdMethod_byte(localHashMap1, "IGNORETIER", Vo);
          VQ = jdMethod_byte(localHashMap1, "REPOSITORY", VQ);
          Vn = jdMethod_byte(localHashMap1, "FRONTVIEWTYPE", Vn);
          VZ = jdMethod_byte(localHashMap1, "BACKVIEWTYPE", VZ);
          Vp = jdMethod_byte(localHashMap1, "COMPRESSION", Vp);
          Vs = jdMethod_byte(localHashMap1, "STATEMENT_LOGO_PATH", null);
          Vm = jdMethod_byte(localHashMap1, "STATEMENT_MAX_IMAGES", null);
          str4 = jdMethod_byte(localHashMap1, "WAIT_LENGTH", null);
          if ((str4 != null) && (!str4.equals(""))) {
            VD = Integer.parseInt(str4);
          }
          str5 = jdMethod_byte(localHashMap1, "MAX_RETRY", null);
          if ((str5 != null) && (!str5.equals(""))) {
            VF = Integer.parseInt(str5);
          }
          VH = Boolean.valueOf(jdMethod_byte(localHashMap1, "DUMMY_IRB", null)).booleanValue();
          Wc = jdMethod_byte(localHashMap1, "DUMMY_IMAGE_URL", null);
          Vw = true;
        }
      }
    }
    catch (Exception localException)
    {
      Vw = false;
      jdMethod_for(localException, str1, "Unable to initialize checkimaging adapter", 29101);
    }
  }
  
  public static ImageResults getImageIndex(SecureUser paramSecureUser, ImageRequest paramImageRequest)
    throws CheckImagingException
  {
    String str1 = "CheckImagingAdapter.getImageIndex";
    int i = 0;
    int j = VD;
    int k = 0;
    try
    {
      Thread localThread = Thread.currentThread();
      String str2 = paramImageRequest.xmlSearchCriteria();
      if (str2.equalsIgnoreCase("ERROR"))
      {
        jdMethod_for(str1, "Invalid XML request for getting image index", 1011247);
      }
      else
      {
        ImageResults localImageResults = new ImageResults(paramSecureUser.getLocale());
        i = jdMethod_for(paramImageRequest.xmlSearchCriteria(), "index", localImageResults);
        if (i != 0) {
          jdMethod_for(str1, "Error while getting items", i);
        }
        do
        {
          try
          {
            Thread.sleep(j);
          }
          catch (InterruptedException localInterruptedException) {}
          if (j >= 500) {
            j = 300;
          }
          i = jdMethod_int("index", localImageResults.getPackageId(), localImageResults);
          if (i != 0) {
            jdMethod_for(str1, "Error while getting results", i);
          }
        } while ((i == 0) && (!localImageResults.getStatus().equals("Complete")));
        return localImageResults;
      }
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str1);
    }
    return null;
  }
  
  public static ImageResult getImage(SecureUser paramSecureUser, ImageResult paramImageResult)
    throws CheckImagingException
  {
    String str1 = "CheckImagingAdapter.getImage";
    try
    {
      String str2 = paramImageResult.getPackageId();
      String str3 = paramImageResult.getStatus();
      if ((str2 == null) || (str2.length() == 0))
      {
        jdMethod_for(str1, "Empty package Id", 1010800);
      }
      else if (str3.equals("AVAILABLE"))
      {
        HashMap localHashMap = new HashMap();
        if (VH)
        {
          IrbRequestClient localIrbRequestClient = new IrbRequestClient();
          localHashMap = localIrbRequestClient.getImages(str2, Wc);
        }
        if (localHashMap.isEmpty())
        {
          jdMethod_for(str1, "Error while retrieving image", 1011253);
        }
        else
        {
          paramImageResult.setImage(localHashMap);
          paramImageResult.setStatus("AVAILABLE");
          paramImageResult.setFee(getImageFee());
          paramImageResult.setCompressionType(1);
          if (Vp.equalsIgnoreCase("PNG")) {
            paramImageResult.setCompressionType(0);
          } else if (Vp.equalsIgnoreCase("JPG")) {
            paramImageResult.setCompressionType(1);
          } else if (Vp.equalsIgnoreCase("GIF")) {
            paramImageResult.setCompressionType(2);
          } else if (Vp.equalsIgnoreCase("TIF")) {
            paramImageResult.setCompressionType(3);
          }
        }
      }
      return paramImageResult;
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str1);
    }
    return null;
  }
  
  public static ImageResult getImageStatus(SecureUser paramSecureUser, ImageResult paramImageResult)
    throws CheckImagingException
  {
    String str = "CheckImagingAdapter.getImageStatus";
    int i = 0;
    try
    {
      ImageResults localImageResults = new ImageResults();
      Thread localThread = Thread.currentThread();
      for (int j = 0; j < VF; j++)
      {
        i = jdMethod_int("both", paramImageResult.getPackageId(), localImageResults);
        if (i != 0) {
          jdMethod_for(str, "Error while getting results", i);
        }
        if (localImageResults.getStatus().equalsIgnoreCase("Complete")) {
          break;
        }
        try
        {
          Thread.sleep(VD);
        }
        catch (InterruptedException localInterruptedException) {}
      }
      if ((i == 0) && (localImageResults.getStatus().equalsIgnoreCase("Complete")))
      {
        ImageResult localImageResult = (ImageResult)localImageResults.iterator().next();
        paramImageResult.setFrontViewHandle(localImageResult.getFrontViewHandle());
        paramImageResult.setBackViewHandle(localImageResult.getBackViewHandle());
        paramImageResult.setStatus("AVAILABLE");
      }
      else if (i != 0)
      {
        paramImageResult.setStatus("ERROR");
        paramImageResult.setErrorCode(String.valueOf(i));
      }
      else
      {
        paramImageResult.setStatus("PENDING");
      }
      return paramImageResult;
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str);
    }
    return null;
  }
  
  public static ImageResults getImagePackageId(SecureUser paramSecureUser, ImageResults paramImageResults)
    throws CheckImagingException
  {
    String str = "CheckImagingAdapter.getImagePackageId";
    int i = 0;
    try
    {
      ImageResults localImageResults = new ImageResults();
      Iterator localIterator = paramImageResults.iterator();
      while (localIterator.hasNext())
      {
        ImageResult localImageResult = (ImageResult)localIterator.next();
        i = jdMethod_for(localImageResult.xmlImageRequest(), "both", localImageResults);
        if (i != 0) {
          jdMethod_for(str, "Error while getting image package id", i);
        }
        localImageResult.setPackageId(localImageResults.getPackageId());
      }
      return paramImageResults;
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str);
    }
    return null;
  }
  
  public static ImageResults getPendingImages(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CheckImagingException
  {
    String str = "CheckImagingAdapter.getPendingImages";
    StringBuffer localStringBuffer = new StringBuffer("select * from PENDING_IMAGES where user_id=?");
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    ImageResults localImageResults = null;
    try
    {
      localConnection = DBUtil.getConnection(Wj, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      localPreparedStatement.setString(1, paramSecureUser.getId());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, localStringBuffer.toString());
      localImageResults = jdMethod_for(localResultSet);
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Wj, localConnection, localPreparedStatement, localResultSet);
    }
    return localImageResults;
  }
  
  public static ImageResults setPendingImages(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException
  {
    String str = "CheckImagingAdapter.setPendingImages";
    try
    {
      if ((paramHashMap == null) || (paramHashMap.get("operation") == null) || (!paramHashMap.get("operation").toString().equalsIgnoreCase("UPDATE"))) {
        return jdMethod_for(paramSecureUser, paramImageResults);
      }
      return jdMethod_int(paramSecureUser, paramImageResults);
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str);
    }
    return paramImageResults;
  }
  
  public static ImageResults emailPendingImageStatus(SecureUser paramSecureUser, ImageResults paramImageResults, HashMap paramHashMap)
    throws CheckImagingException
  {
    String str1 = "CheckImagingAdapter.emailPendingImageStatus";
    int i = 0;
    try
    {
      String str2 = (String)paramHashMap.get("TOADDRESS");
      if ((str2 == null) || (str2.length() == 0)) {
        jdMethod_for(str1, "Error while sending pending image status", 29106);
      }
      ImageStatusNotifyThread localImageStatusNotifyThread = new ImageStatusNotifyThread(paramImageResults, str2);
      localImageStatusNotifyThread.start();
      i = localImageStatusNotifyThread.getInitStatus();
      if (i != 0) {
        jdMethod_for(str1, "Error while sending pending image status", 29101);
      }
      return paramImageResults;
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str1);
    }
    return null;
  }
  
  public static ImageResult emailImage(SecureUser paramSecureUser, ImageResult paramImageResult, HashMap paramHashMap)
    throws CheckImagingException
  {
    String str1 = "CheckImagingAdapter.emailImage";
    int i = 0;
    try
    {
      String str2 = (String)paramHashMap.get("TOADDRESS");
      String str3 = (String)paramHashMap.get("FROMADDRESS");
      String str4 = (String)paramHashMap.get("SUBJECT");
      String str5 = (String)paramHashMap.get("MESSAGE");
      EmailImageAdapter localEmailImageAdapter = new EmailImageAdapter(str2, str3, str4, str5, paramImageResult);
      i = localEmailImageAdapter.sendImage();
      if (i != 0) {
        jdMethod_for(str1, "Error while sending images in email", 29107);
      }
      return paramImageResult;
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str1);
    }
    return null;
  }
  
  public static ImageResults deleteImageResults(SecureUser paramSecureUser, ImageResults paramImageResults)
    throws CheckImagingException
  {
    String str1 = "CheckImagingAdapter.deleteImageResults";
    String str2 = null;
    String str3 = null;
    XMLResponseProcessor localXMLResponseProcessor = null;
    try
    {
      str2 = paramImageResults.getPackageId();
      if ((str2 == null) || (str2.equals("")))
      {
        jdMethod_for(str1, "Empty package Id", 1010800);
      }
      else
      {
        str3 = "<?xml version=\"1.0\"?>\n<!DOCTYPE deleteRequest SYSTEM \"irbRequestPackage.dtd\">\n<deleteRequest>\n\t<packageId>" + str2 + "</packageId>\n" + "</deleteRequest>\n";
        if (VH)
        {
          String str4 = null;
          IrbRequestClient localIrbRequestClient = new IrbRequestClient();
          str4 = C(localIrbRequestClient.deleteRequestUsingXml(str3));
          localXMLResponseProcessor = new XMLResponseProcessor(str4);
          if (!localXMLResponseProcessor.isValidXML()) {
            jdMethod_for(str1, "Error while deleting image requests", 29108);
          }
        }
      }
      return paramImageResults;
    }
    catch (Exception localException)
    {
      jdMethod_for(localException, str1);
    }
    return null;
  }
  
  public static ImageResults deleteImageRequests(SecureUser paramSecureUser, ImageResults paramImageResults)
    throws CheckImagingException
  {
    String str = "CheckImagingAdapter.deleteImageRequests";
    StringBuffer localStringBuffer = new StringBuffer("delete from PENDING_IMAGES where image_handle=? AND user_id=? AND account_id=?");
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    try
    {
      localConnection = DBUtil.getConnection(Wj, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      Iterator localIterator = paramImageResults.iterator();
      while (localIterator.hasNext())
      {
        ImageResult localImageResult = (ImageResult)localIterator.next();
        localPreparedStatement.setString(1, localImageResult.getImageHandle());
        localPreparedStatement.setString(2, localImageResult.getUserId());
        localPreparedStatement.setString(3, localImageResult.getAccountID());
        DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      if (localConnection != null) {
        DBUtil.rollback(localConnection);
      }
      jdMethod_for(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Wj, localConnection, localPreparedStatement);
    }
    return paramImageResults;
  }
  
  private static ImageResults jdMethod_for(SecureUser paramSecureUser, ImageResults paramImageResults)
    throws CheckImagingException
  {
    String str = "CheckImagingAdapter.addPendingImages";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      localConnection = DBUtil.getConnection(Wj, true, 2);
      DBUtil.beginTransaction(localConnection);
      Iterator localIterator = paramImageResults.iterator();
      while (localIterator.hasNext()) {
        jdMethod_for(paramSecureUser, (ImageResult)localIterator.next(), localConnection, localPreparedStatement);
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      if (localConnection != null) {
        DBUtil.rollback(localConnection);
      }
      jdMethod_for(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Wj, localConnection, localPreparedStatement);
    }
    return paramImageResults;
  }
  
  private static ImageResults jdMethod_int(SecureUser paramSecureUser, ImageResults paramImageResults)
    throws CheckImagingException
  {
    String str = "CheckImagingAdapter.addPendingImages";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer = new StringBuffer("update PENDING_IMAGES set status=? where image_handle=? AND user_id=? AND account_id=?");
    try
    {
      localConnection = DBUtil.getConnection(Wj, true, 2);
      DBUtil.beginTransaction(localConnection);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, localStringBuffer.toString());
      Iterator localIterator = paramImageResults.iterator();
      while (localIterator.hasNext())
      {
        ImageResult localImageResult = (ImageResult)localIterator.next();
        localPreparedStatement.setString(1, localImageResult.getStatus());
        localPreparedStatement.setString(2, localImageResult.getImageHandle());
        localPreparedStatement.setString(3, localImageResult.getUserId());
        localPreparedStatement.setString(4, localImageResult.getAccountID());
        DBUtil.executeUpdate(localPreparedStatement, localStringBuffer.toString());
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      if (localConnection != null) {
        DBUtil.rollback(localConnection);
      }
      jdMethod_for(localException, str);
    }
    finally
    {
      DBUtil.closeAll(Wj, localConnection, localPreparedStatement);
    }
    return paramImageResults;
  }
  
  private static void M()
    throws PoolException
  {
    String str = "CheckImagingAdapter.initConnection";
    Properties localProperties = new Properties();
    localProperties.put("PoolType", VR);
    localProperties.put("ConnectionType", VO);
    localProperties.put("Server", Vy);
    localProperties.put("DBName", Wm);
    localProperties.put("Port", VB);
    localProperties.put("User", Vv);
    localProperties.put("Password", VP);
    localProperties.put("DefaultConnections", Vx);
    localProperties.put("MaxConnections", VC);
    localProperties.put("JDBCUrl", V9);
    localProperties.put("PoolName", Wl);
    localProperties.put("DriverClass", VA);
    localProperties.put("License", VW);
    Wj = ConnectionPool.init(localProperties);
  }
  
  private static ImageResults jdMethod_for(ResultSet paramResultSet)
    throws Exception
  {
    String str = "CheckImagingAdapter.processImageResultRS";
    ImageResults localImageResults = new ImageResults();
    while (paramResultSet.next())
    {
      ImageResult localImageResult = new ImageResult();
      localImageResult.setDateFormat("yyyyMMdd");
      localImageResult.setImageHandle(paramResultSet.getString("image_handle"));
      localImageResult.setUserId(paramResultSet.getString("user_id"));
      localImageResult.setAccountID(paramResultSet.getString("account_id"));
      localImageResult.setCheckNumber(paramResultSet.getString("check_number"));
      localImageResult.setRoutingTransitNumber(paramResultSet.getString("routing_transit_number"));
      localImageResult.setTransCode(paramResultSet.getString("trans_code"));
      localImageResult.setDrCr(paramResultSet.getString("dr_cr"));
      localImageResult.setSequenceNumber(paramResultSet.getString("sequence_number"));
      localImageResult.setCaptureSequence(paramResultSet.getString("capture_sequence"));
      localImageResult.setDepositTraceID(paramResultSet.getString("deposit_trace_id"));
      localImageResult.setEntryNumber(paramResultSet.getString("entry_number"));
      localImageResult.setTracerNumber(paramResultSet.getString("tracer_number"));
      localImageResult.setPackageId(paramResultSet.getString("package_id"));
      localImageResult.setBankId(paramResultSet.getString("bank_id"));
      localImageResult.setStatus(paramResultSet.getString("status"));
      localImageResult.setEstimate(paramResultSet.getString("estimate"));
      localImageResult.setAmount(paramResultSet.getString("amount"));
      localImageResult.setFee(paramResultSet.getString("fee"));
      localImageResult.setChargeAccount(paramResultSet.getString("charge_account"));
      localImageResult.setDepositID(paramResultSet.getString("dep_id"));
      localImageResult.setFeeFlag(paramResultSet.getString("fee_flag"));
      localImageResult.setFeeProcessed(paramResultSet.getString("fee_processed_flag"));
      localImageResult.setFrontViewHandle(paramResultSet.getString("front_view_handle"));
      localImageResult.setBackViewHandle(paramResultSet.getString("back_view_handle"));
      localImageResult.setStorageTier(paramResultSet.getString("storage_tier"));
      localImageResult.setCompressionType(paramResultSet.getString("compression_type"));
      localImageResult.setReturnCode(paramResultSet.getString("return_code"));
      localImageResult.setReasonCode(paramResultSet.getString("reason_code"));
      localImageResult.setErrorCode(paramResultSet.getString("error_code"));
      localImageResult.setDatePlaced(paramResultSet.getString("date_placed"));
      localImageResult.setPostingDate(paramResultSet.getString("posting_date"));
      localImageResult.setRequestedDate(paramResultSet.getString("requested_date"));
      localImageResults.add(localImageResult);
    }
    return localImageResults;
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, ImageResult paramImageResult, Connection paramConnection, PreparedStatement paramPreparedStatement)
    throws Exception
  {
    String str1 = "CheckImagingAdapter.addPendingImages";
    StringBuffer localStringBuffer = new StringBuffer("insert into PENDING_IMAGES (image_handle,user_id,account_id,check_number,routing_transit_number,trans_code,dr_cr,sequence_number,capture_sequence,deposit_trace_id,entry_number,tracer_number,package_id,bank_id,status,estimate,amount,fee,charge_account,dep_id,fee_flag,fee_processed_flag,front_view_handle,back_view_handle,storage_tier,compression_type,return_code,reason_code,error_code,date_placed,posting_date,requested_date)  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    int i = 0;
    int j = 0;
    int k = 0;
    paramImageResult.setDateFormat("yyyyMMdd");
    str2 = paramImageResult.getAmount();
    if ((str2 != null) && (!str2.equals("")))
    {
      Currency localCurrency = paramImageResult.getAmountValue();
      if (localCurrency != null)
      {
        localCurrency.setFormat("DECIMAL");
        str2 = localCurrency.toString();
      }
    }
    str3 = paramImageResult.getDatePlaced();
    if ((str3 != null) && (!str3.equals(""))) {
      i = Integer.parseInt(str3);
    }
    str4 = paramImageResult.getPostingDate();
    if ((str4 != null) && (!str4.equals(""))) {
      j = Integer.parseInt(str4);
    }
    str5 = paramImageResult.getRequestedDate();
    if ((str5 != null) && (!str5.equals(""))) {
      k = Integer.parseInt(str5);
    }
    paramPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
    paramPreparedStatement.setString(1, paramImageResult.getImageHandle());
    paramPreparedStatement.setString(2, paramSecureUser.getId());
    paramPreparedStatement.setString(3, paramImageResult.getAccountID());
    paramPreparedStatement.setString(4, paramImageResult.getCheckNumber());
    paramPreparedStatement.setString(5, paramImageResult.getRoutingTransitNumber());
    paramPreparedStatement.setString(6, paramImageResult.getTransCode());
    paramPreparedStatement.setString(7, paramImageResult.getDrCr());
    paramPreparedStatement.setString(8, paramImageResult.getSequenceNumber());
    paramPreparedStatement.setString(9, paramImageResult.getCaptureSequence());
    paramPreparedStatement.setString(10, paramImageResult.getDepositTraceID());
    paramPreparedStatement.setString(11, paramImageResult.getEntryNumber());
    paramPreparedStatement.setString(12, paramImageResult.getTracerNumber());
    paramPreparedStatement.setString(13, paramImageResult.getPackageId());
    paramPreparedStatement.setString(14, paramImageResult.getBankId());
    paramPreparedStatement.setString(15, paramImageResult.getStatus());
    paramPreparedStatement.setString(16, paramImageResult.getEstimate());
    paramPreparedStatement.setString(17, str2);
    paramPreparedStatement.setString(18, paramImageResult.getFee());
    paramPreparedStatement.setString(19, paramImageResult.getChargeAccount());
    paramPreparedStatement.setString(20, paramImageResult.getDepositID());
    paramPreparedStatement.setString(21, paramImageResult.getFeeFlag());
    paramPreparedStatement.setString(22, paramImageResult.getFeeProcessed());
    paramPreparedStatement.setString(23, paramImageResult.getFrontViewHandle());
    paramPreparedStatement.setString(24, paramImageResult.getBackViewHandle());
    paramPreparedStatement.setString(25, paramImageResult.getStorageTier());
    paramPreparedStatement.setString(26, paramImageResult.getCompressionTypeValue());
    paramPreparedStatement.setString(27, paramImageResult.getReturnCode());
    paramPreparedStatement.setString(28, paramImageResult.getReasonCode());
    paramPreparedStatement.setString(29, paramImageResult.getErrorCode());
    paramPreparedStatement.setInt(30, i);
    paramPreparedStatement.setInt(31, j);
    paramPreparedStatement.setInt(32, k);
    DBUtil.executeUpdate(paramPreparedStatement, localStringBuffer.toString());
  }
  
  private static int jdMethod_for(String paramString1, String paramString2, ImageResults paramImageResults)
  {
    String str1 = "CheckImagingAdapter.getItems";
    int i = 0;
    XMLResponseProcessor localXMLResponseProcessor = null;
    String str2 = null;
    String str3 = null;
    str2 = jdMethod_for(paramString1, paramString2.equalsIgnoreCase("both"));
    try
    {
      if (VH)
      {
        IrbRequestClient localIrbRequestClient = new IrbRequestClient();
        String str4 = localIrbRequestClient.getItemsUsingXml(str2);
        localXMLResponseProcessor = new XMLResponseProcessor(paramString2, C(str4));
      }
      if (!localXMLResponseProcessor.isValidXML()) {
        throw new Exception("invalid XML response from IRB");
      }
      localXMLResponseProcessor.processResponsePackage(paramImageResults);
      str3 = paramImageResults.getReturnCode();
      if ((str3 != null) && (!str3.equals(""))) {
        i = Integer.parseInt(paramImageResults.getReturnCode());
      }
      if (i != 0) {
        i = mapError(i, Integer.parseInt(paramImageResults.getReasonCode()));
      }
    }
    catch (Exception localException)
    {
      i = 1010002;
    }
    return i;
  }
  
  private static int jdMethod_for(String paramString, ImageResults paramImageResults)
  {
    return jdMethod_int(paramString, paramImageResults.getPackageId(), paramImageResults);
  }
  
  private static int jdMethod_int(String paramString1, String paramString2, ImageResults paramImageResults)
  {
    String str1 = "CheckImagingAdapter.getResults";
    Object localObject = null;
    int i = 0;
    try
    {
      XMLResponseProcessor localXMLResponseProcessor = null;
      String str2 = "<?xml version=\"1.0\"?>\n<!DOCTYPE resultsRequest SYSTEM \"irbRequestPackage.dtd\">\n<resultsRequest>\n\t<packageId>" + paramString2 + "</packageId>\n" + "</resultsRequest>\n";
      if (VH)
      {
        IrbRequestClient localIrbRequestClient = new IrbRequestClient();
        localXMLResponseProcessor = new XMLResponseProcessor(paramString1, C(localIrbRequestClient.getResultsUsingXml(str2)));
      }
      if (!localXMLResponseProcessor.isValidXML()) {
        throw new Exception("invalid XML response from IRB");
      }
      localXMLResponseProcessor.processResponsePackage(paramImageResults);
      if ((localObject != null) && (!localObject.equals(""))) {
        i = Integer.parseInt(paramImageResults.getReturnCode());
      }
      if (i != 0) {
        i = mapError(i, Integer.parseInt(paramImageResults.getReasonCode()));
      }
    }
    catch (Exception localException)
    {
      i = 1010001;
    }
    return i;
  }
  
  private static InputStream B(String paramString)
  {
    InputStream localInputStream = null;
    try
    {
      PropertiesUtil localPropertiesUtil = new PropertiesUtil();
      localInputStream = localPropertiesUtil.getClass().getClassLoader().getResourceAsStream(paramString);
    }
    catch (Exception localException) {}
    if (localInputStream == null) {
      localInputStream = ClassLoader.getSystemResourceAsStream(paramString);
    }
    if (localInputStream == null)
    {
      ClassLoader.getSystemClassLoader();
      localInputStream = ClassLoader.getSystemResourceAsStream(paramString);
    }
    return localInputStream;
  }
  
  private static String jdMethod_byte(HashMap paramHashMap, String paramString1, String paramString2)
    throws Exception
  {
    Object localObject = paramHashMap.get(paramString1);
    if (localObject == null)
    {
      if (paramString2 == null) {
        throw new Exception("missing " + paramString1 + " tag from settings");
      }
      return paramString2;
    }
    return localObject.toString();
  }
  
  private static String C(String paramString)
  {
    String str = "";
    int i = 0;
    for (int j = 0; j < paramString.length(); j++)
    {
      char c = paramString.charAt(j);
      if ((c == '\n') || (i == 1))
      {
        if (c == '\n') {
          i++;
        }
      }
      else {
        str = str + c;
      }
    }
    return str;
  }
  
  private static void jdMethod_for(Exception paramException, String paramString)
    throws CheckImagingException
  {
    if ((paramException instanceof CheckImagingException)) {
      throw ((CheckImagingException)paramException);
    }
    throw new CheckImagingException(paramException, paramString, 29109, "");
  }
  
  private static void jdMethod_for(Exception paramException, String paramString1, String paramString2, int paramInt)
    throws CheckImagingException
  {
    if ((paramException instanceof CheckImagingException)) {
      throw ((CheckImagingException)paramException);
    }
    throw new CheckImagingException(paramException, paramString1, paramInt, paramString2);
  }
  
  private static void jdMethod_for(String paramString1, String paramString2, int paramInt)
    throws CheckImagingException
  {
    throw new CheckImagingException(paramString1, paramInt, paramString2);
  }
  
  public static int mapError(int paramInt1, int paramInt2)
  {
    int i = 1010003;
    switch (paramInt1)
    {
    case 0: 
      i = 0;
      break;
    case 4: 
      i = 1010400;
      switch (paramInt2)
      {
      case 1: 
        i = 0;
        break;
      case 2: 
        i = 1010402;
        break;
      case 3: 
        i = 1010403;
      }
      break;
    case 6: 
      i = 1010600;
      break;
    case 8: 
      i = 1010800;
      switch (paramInt2)
      {
      case 2: 
        i = 1010802;
        break;
      case 20: 
        i = 1010820;
        break;
      case 21: 
        i = 1010821;
        break;
      case 22: 
        i = 1010822;
        break;
      case 23: 
        i = 1010823;
        break;
      case 24: 
        i = 1010824;
        break;
      case 25: 
        i = 1010825;
        break;
      case 26: 
        i = 1010826;
        break;
      case 27: 
        i = 1010827;
        break;
      case 28: 
        i = 1010828;
        break;
      case 29: 
        i = 1010829;
        break;
      case 30: 
        i = 1010830;
        break;
      case 31: 
        i = 1010831;
        break;
      case 32: 
        i = 1010832;
        break;
      case 33: 
        i = 1010833;
        break;
      case 34: 
        i = 1010834;
        break;
      case 35: 
        i = 1010835;
      }
      break;
    case 12: 
      i = 1011200;
      switch (paramInt2)
      {
      case 40: 
        i = 1011240;
        break;
      case 41: 
        i = 1011241;
        break;
      case 43: 
        i = 1011243;
        break;
      case 44: 
        i = 1011244;
        break;
      case 45: 
        i = 1011245;
        break;
      case 46: 
        i = 1011246;
        break;
      case 47: 
        i = 1011247;
        break;
      case 48: 
        i = 1011248;
        break;
      case 49: 
        i = 1011249;
        break;
      case 50: 
        i = 1011250;
        break;
      case 51: 
        i = 1011251;
        break;
      case 52: 
        i = 1011252;
        break;
      case 53: 
        i = 1011253;
        break;
      case 54: 
        i = 1011254;
        break;
      case 55: 
        i = 1011255;
      }
      break;
    case 16: 
      i = 1011600;
      break;
    case 20: 
      i = 1012000;
      break;
    case 28: 
      i = 1012800;
    }
    return i;
  }
  
  private static String jdMethod_int(boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(500);
    localStringBuffer.append(V8);
    localStringBuffer.append(Wg);
    localStringBuffer.append(V6);
    if (paramBoolean) {
      localStringBuffer.append(VV);
    } else {
      localStringBuffer.append(Wk);
    }
    localStringBuffer.append(V5);
    localStringBuffer.append(Vo);
    localStringBuffer.append(V3);
    if (paramBoolean) {
      localStringBuffer.append("both");
    } else {
      localStringBuffer.append("index");
    }
    localStringBuffer.append(V2);
    localStringBuffer.append(VQ);
    localStringBuffer.append(V1);
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_for(boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(500);
    localStringBuffer.append(V0);
    if (paramBoolean)
    {
      localStringBuffer.append(VY);
      localStringBuffer.append(Vn);
      localStringBuffer.append(VX);
      localStringBuffer.append(Vp);
      localStringBuffer.append(We);
      if (VZ.equals("BBW")) {
        localStringBuffer.append("BGS");
      } else if (VZ.equals("BGS")) {
        localStringBuffer.append("BBW");
      }
      localStringBuffer.append(Wb);
      localStringBuffer.append(Vp);
      localStringBuffer.append(We);
      localStringBuffer.append(VZ);
      localStringBuffer.append(Wb);
      localStringBuffer.append(Vp);
      localStringBuffer.append(Wa);
    }
    localStringBuffer.append(V7);
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_new(ArrayList paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer(200);
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof String)) {
        localStringBuffer.append("\t\t\t\t<handle>" + (String)localObject + "</handle>\n");
      }
    }
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_int(ArrayList paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer(1000);
    localStringBuffer.append(jdMethod_int(true));
    localStringBuffer.append(jdMethod_new(paramArrayList));
    localStringBuffer.append(jdMethod_for(true));
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_for(String paramString, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(1000);
    localStringBuffer.append(jdMethod_int(paramBoolean));
    localStringBuffer.append(paramString);
    localStringBuffer.append(jdMethod_for(paramBoolean));
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_for(ArrayList paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer(1000);
    localStringBuffer.append(jdMethod_int(true));
    localStringBuffer.append(jdMethod_new(paramArrayList));
    localStringBuffer.append(jdMethod_new(true));
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_int(String paramString, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(1000);
    localStringBuffer.append(jdMethod_int(paramBoolean));
    localStringBuffer.append(paramString);
    localStringBuffer.append(jdMethod_new(paramBoolean));
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_new(boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(500);
    localStringBuffer.append(V0);
    if (paramBoolean)
    {
      localStringBuffer.append(VY);
      localStringBuffer.append(Vn);
      localStringBuffer.append(VX);
      localStringBuffer.append(Vp);
      localStringBuffer.append(Wa);
    }
    localStringBuffer.append(V7);
    return localStringBuffer.toString();
  }
  
  public static String getServerURL()
  {
    return Wf;
  }
  
  public static String getSMTPHost()
  {
    return VI;
  }
  
  public static String getEmailFrom()
  {
    return VN;
  }
  
  public static String getMaxAge()
  {
    return Integer.toString(V4);
  }
  
  public static int getMaxAgeInt()
  {
    return V4;
  }
  
  public static String getImageFee()
  {
    return Vt;
  }
  
  public static String getSearchDate()
  {
    return VU;
  }
  
  public static String getFeeLogging()
  {
    return VM;
  }
  
  public static String getImageFeeMessageCode()
  {
    return VE;
  }
  
  public static String getImageFeeMessageRetail()
  {
    return Wn;
  }
  
  public static String getImageFeeMessageBiz()
  {
    return Vr;
  }
  
  public static XMLTag getErrorTag()
  {
    return VL;
  }
  
  public static String getImagingRetailOn()
  {
    return VJ;
  }
  
  public static String getImagingRetailOffsets()
  {
    return VK;
  }
  
  public static String getImagingBizOn()
  {
    return Wd;
  }
  
  public static String getImagingBizOffsets()
  {
    return Vu;
  }
  
  public static String getLogoPath()
  {
    return Vs;
  }
  
  public static String getStmtMaxImages()
  {
    return Vm;
  }
  
  public static String getLogFileName()
  {
    return Vz;
  }
  
  public static String getTimeOut()
  {
    return Wg;
  }
  
  public static String getMaxSearchItems()
  {
    return Wk;
  }
  
  public static String getMaxImageItems()
  {
    return VV;
  }
  
  public static String getIgnoreTier()
  {
    return Vo;
  }
  
  public static String getRepository()
  {
    return VQ;
  }
  
  public static String getFrontViewType()
  {
    return Vn;
  }
  
  public static String getBackViewType()
  {
    return VZ;
  }
  
  public static String getCompression()
  {
    return Vp;
  }
  
  public static int getWaitLength()
  {
    return VD;
  }
  
  public static int getMaxRetries()
  {
    return VF;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.checkimaging.adapters.CheckImagingAdapter
 * JD-Core Version:    0.7.0.1
 */