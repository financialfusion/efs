package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.IFXConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class IFXUtil
  implements FFSConst, IFXConsts
{
  private static final String jdField_case = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  private static final String jdField_goto = "<!DOCTYPE";
  private static final String jdField_char = " IFX SYSTEM \"file:";
  private static final String jdField_else = "\">";
  
  public static String getIFXRequestWithDTD(String paramString)
    throws BPWException
  {
    if (paramString == null) {
      throw new BPWException("IFX Request is Null !");
    }
    int i = paramString.indexOf("<?xml");
    int j = 0;
    if (i != -1)
    {
      j = paramString.indexOf(">");
      if (j != -1) {
        paramString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString.substring(j + 1);
      }
    }
    else
    {
      paramString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString;
    }
    int k = paramString.indexOf("<!DOCTYPE");
    if (k != -1)
    {
      j = paramString.indexOf(">", k + 1);
      paramString = paramString.substring(0, k) + " " + "<!DOCTYPE" + " IFX SYSTEM \"file:" + jdMethod_for() + "\">" + paramString.substring(j + 1);
    }
    else
    {
      j = paramString.indexOf(">");
      paramString = paramString.substring(0, j + 1) + "<!DOCTYPE" + " IFX SYSTEM \"file:" + jdMethod_for() + "\">" + paramString.substring(j + 1);
    }
    return paramString;
  }
  
  private static String jdMethod_for()
    throws BPWException
  {
    FFSDebug.log("IFXUtil.getServerIFXDTDLocation : start ", 6);
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str = BPWRegistryUtil.getProperty("bpw.install.dir", ".") + File.separator + "dtd" + File.separator + "ifx140.dtd";
    FFSDebug.log("IFXUtil.getServerIFXDTDLocation : dtdFile =" + str, 6);
    return str;
  }
  
  public static void setUserDefinedObject(TypeUserData paramTypeUserData, String paramString1, String paramString2, String paramString3)
  {
    FFSDebug.log("IFXUtil.setUserDefinedObject start, tagName =" + paramString1 + ", tagValue =" + paramString2 + ", action =" + paramString3, 6);
    if (paramTypeUserData._userDefined == null) {
      FFSDebug.log("*** IFXUtil.setUserDefinedObject failed : _userDefined object is null in TypeUserData", 0);
    }
    ValueInfo localValueInfo = new ValueInfo();
    localValueInfo.setAction(paramString3);
    localValueInfo.setValue(paramString2);
    ((Hashtable)paramTypeUserData._userDefined).put(paramString1, localValueInfo);
    FFSDebug.log("IFXUtil.setUserDefinedObject end", 6);
  }
  
  public static String getExtdPmtInfo(String paramString1, String paramString2)
  {
    FFSDebug.log("IFXUtil.getExtdPmtInfo start, tagName =" + paramString2, 6);
    String str = (String)FFSUtil.stringToHashtable(paramString1).get(paramString2);
    FFSDebug.log("IFXUtil.setUserDefinedObject end, tagValue = " + str, 6);
    return str;
  }
  
  public static String getEffDate(String paramString)
    throws BPWException
  {
    FFSDebug.log("IFXUtil.getEffDate start : dateStr = " + paramString, 6);
    String str = null;
    try
    {
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");
      ParsePosition localParsePosition = new ParsePosition(0);
      Date localDate = localSimpleDateFormat1.parse(paramString, localParsePosition);
      str = localSimpleDateFormat2.format(localDate);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
    FFSDebug.log("IFXUtil.getEffDate end : rtnDate = " + str, 6);
    return str;
  }
  
  public static String getCurrDate()
    throws BPWException
  {
    FFSDebug.log("IFXUtil.getCurrDate start", 6);
    Date localDate = null;
    String str = null;
    SimpleDateFormat localSimpleDateFormat = null;
    try
    {
      localDate = new Date();
      localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
      str = localSimpleDateFormat.format(localDate);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
    FFSDebug.log("IFXUtil.getCurrDate end : currDate = " + str, 6);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.IFXUtil
 * JD-Core Version:    0.7.0.1
 */