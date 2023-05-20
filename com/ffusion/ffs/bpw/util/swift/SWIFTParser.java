package com.ffusion.ffs.bpw.util.swift;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.msgbroker.interfaces.IBuilder;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.msgbroker.interfaces.IParser;
import com.ffusion.msgbroker.interfaces.MBException;
import com.ffusion.util.EnhancedStringTokenizer;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;

public class SWIFTParser
{
  private static SWIFTParser jdField_do = null;
  private static IMBInstance jdField_if = null;
  boolean a = false;
  
  private SWIFTParser()
    throws Exception
  {}
  
  public void initialize(IMBInstance paramIMBInstance)
    throws SWIFTParseException
  {
    if (!this.a)
    {
      if (paramIMBInstance == null)
      {
        DebugLog.log("SWIFTParser : This methods only accepts initialised MB instance");
        throw new SWIFTParseException("This methods only accepts initialised MB instance");
      }
      jdField_if = paramIMBInstance;
      this.a = true;
    }
  }
  
  public static synchronized SWIFTParser getInstance()
    throws Exception
  {
    if (jdField_do == null) {
      jdField_do = new SWIFTParser();
    }
    return jdField_do;
  }
  
  private IMBMessage a(String paramString1, String paramString2)
    throws SWIFTParseException, MBException
  {
    if (jdField_if == null)
    {
      DebugLog.log("SWIFTParser : This methods needs initialised MB instance");
      throw new SWIFTParseException("This methods needs initialised MB instance");
    }
    IParser localIParser = null;
    localIParser = jdField_if.createParser();
    if (localIParser == null)
    {
      localObject = "MB Parser is null:createParser returned null";
      DebugLog.log((String)localObject);
      throw new SWIFTParseException((String)localObject);
    }
    Object localObject = null;
    localObject = localIParser.parseToIDL(paramString1, "SWIFT", paramString2);
    if (localObject == null)
    {
      String str = "MB IParser.parseToIDL returned null";
      DebugLog.log(str);
      throw new SWIFTParseException(100, str);
    }
    return localObject;
  }
  
  private String a(IMBMessage paramIMBMessage)
    throws SWIFTParseException
  {
    if (jdField_if == null)
    {
      DebugLog.log("SWIFTParser : This methods needs initialised MB instance");
      throw new SWIFTParseException("This methods needs initialised MB instance");
    }
    String str1 = null;
    try
    {
      IBuilder localIBuilder = jdField_if.createBuilder();
      str1 = localIBuilder.buildString(paramIMBMessage);
    }
    catch (MBException localMBException)
    {
      String str2 = "A message broker error has occurred while creating the decision output file.";
      DebugLog.throwing(str2, localMBException);
      throw new SWIFTParseException(localMBException, str2);
    }
    return str1;
  }
  
  private static String a(String paramString)
  {
    int i = paramString.indexOf("{");
    if (i != -1) {
      paramString = paramString.substring(i);
    }
    return paramString.trim();
  }
  
  public boolean isInitialized()
  {
    return this.a;
  }
  
  private Object a(String paramString1, String paramString2, String paramString3)
    throws SWIFTParseException
  {
    IMBMessage localIMBMessage = null;
    String str = null;
    int i = 0;
    try
    {
      localIMBMessage = a(paramString1, paramString3);
    }
    catch (MBException localMBException)
    {
      i = localMBException.getErrorCode();
      str = localMBException.getMessage();
      localIMBMessage = null;
    }
    catch (SWIFTParseException localSWIFTParseException)
    {
      i = localSWIFTParseException.getErrCode();
      str = localSWIFTParseException.getMessage();
      localIMBMessage = null;
    }
    SWIFTFactory localSWIFTFactory = SWIFTFactory.getInstance();
    ISWIFTMapper localISWIFTMapper = localSWIFTFactory.getMapper(paramString2, paramString3);
    Object localObject2;
    if (localISWIFTMapper == null)
    {
      localObject1 = "No mapping available in config file for bean class=" + paramString2 + " and SWIFT mwssage type " + paramString3;
      localObject2 = new SWIFTParseException((String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = localISWIFTMapper.parse(localIMBMessage);
    if (localIMBMessage == null) {
      if ((localObject1 instanceof BPWInfoBase))
      {
        localObject2 = (BPWInfoBase)localObject1;
        ((BPWInfoBase)localObject2).setStatusCode(i);
        ((BPWInfoBase)localObject2).setStatusMsg(str);
      }
      else if ((localObject1 instanceof FundsTransaction))
      {
        localObject2 = (FundsTransaction)localObject1;
        ((FundsTransaction)localObject2).addValidationError("" + i, str);
      }
    }
    return localObject1;
  }
  
  private String a(Object paramObject, String paramString)
    throws SWIFTParseException
  {
    SWIFTFactory localSWIFTFactory = SWIFTFactory.getInstance();
    ISWIFTMapper localISWIFTMapper = localSWIFTFactory.getMapper(paramObject.getClass().getName(), paramString);
    IMBMessage localIMBMessage = localISWIFTMapper.build(paramObject);
    return a(localIMBMessage);
  }
  
  public String build(Object[] paramArrayOfObject, String paramString)
    throws SWIFTParseException
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    for (int i = 0; i < paramArrayOfObject.length; i++)
    {
      String str = null;
      try
      {
        str = a(paramArrayOfObject[i], paramString);
      }
      catch (SWIFTParseException localSWIFTParseException)
      {
        str = null;
      }
      if (str != null) {
        localStringBuffer.append(str);
      }
    }
    return localStringBuffer.toString();
  }
  
  public Object[] parse(String paramString1, String paramString2, String paramString3)
    throws SWIFTParseException
  {
    if (paramString1 == null)
    {
      localObject1 = "SWIFTParser.parse : swiftMessage cannot be null";
      localObject2 = new SWIFTParseException((String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (paramString2 == null)
    {
      localObject1 = "SWIFTParser.parse : beanClassName cannot be null";
      localObject2 = new SWIFTParseException((String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (paramString3 == null)
    {
      localObject1 = "SWIFTParser.parse : swiftMsgType cannot be null";
      localObject2 = new SWIFTParseException((String)localObject1);
      DebugLog.throwing((String)localObject1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = null;
    Object localObject2 = "-}";
    ArrayList localArrayList = new ArrayList();
    String str;
    try
    {
      localArrayList = EnhancedStringTokenizer.getTokens(paramString1, (String)localObject2, true);
    }
    catch (Exception localException)
    {
      str = "Error while tokenizing input message String";
      SWIFTParseException localSWIFTParseException = new SWIFTParseException(localException, str);
      DebugLog.throwing("com.ffusion.fileimporter.WireImportAdapter.processFile", localSWIFTParseException);
      throw localSWIFTParseException;
    }
    localObject1 = new Object[localArrayList.size()];
    for (int i = 0; i < localArrayList.size(); i++)
    {
      str = a((String)localArrayList.get(i));
      localObject1[i] = a(str, paramString2, paramString3);
    }
    return localObject1;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.swift.SWIFTParser
 * JD-Core Version:    0.7.0.1
 */