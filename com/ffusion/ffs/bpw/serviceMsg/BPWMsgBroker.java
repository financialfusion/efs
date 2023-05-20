package com.ffusion.ffs.bpw.serviceMsg;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.config.MBConfigInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.msgbroker.factory.MBInstanceFactory;
import com.ffusion.msgbroker.factory.MBMessageFactory;
import com.ffusion.msgbroker.interfaces.IBuilder;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.msgbroker.interfaces.IParser;
import com.ffusion.msgbroker.interfaces.MBException;
import java.io.PrintStream;

public class BPWMsgBroker
  implements BPWMsgConsts, FFSConst
{
  private IMBInstance jdField_try = null;
  private static final int jdField_new = 20;
  private final IBuilder[] jdField_int = new IBuilder[20];
  private int a = 0;
  private final IParser[] jdField_if = new IParser[20];
  private int jdField_for = 0;
  private IParser jdField_do = null;
  
  public void start(MBConfigInfo paramMBConfigInfo)
    throws BPWException
  {
    initialize(paramMBConfigInfo);
  }
  
  public IMBInstance getIMBInstance()
  {
    return this.jdField_try;
  }
  
  private IParser jdField_int()
  {
    IParser localIParser = null;
    synchronized (this.jdField_if)
    {
      if (this.jdField_for > 0)
      {
        localIParser = this.jdField_if[(--this.jdField_for)];
        this.jdField_if[this.jdField_for] = null;
      }
    }
    return localIParser;
  }
  
  private void a(IParser paramIParser)
  {
    synchronized (this.jdField_if)
    {
      if (this.jdField_for < 20) {
        this.jdField_if[(this.jdField_for++)] = paramIParser;
      }
    }
  }
  
  private IBuilder jdField_do()
  {
    IBuilder localIBuilder = null;
    synchronized (this.jdField_int)
    {
      if (this.a > 0)
      {
        localIBuilder = this.jdField_int[(--this.a)];
        this.jdField_int[this.a] = null;
      }
    }
    return localIBuilder;
  }
  
  private void a(IBuilder paramIBuilder)
  {
    synchronized (this.jdField_int)
    {
      if (this.a < 20) {
        this.jdField_int[(this.a++)] = paramIBuilder;
      }
    }
  }
  
  public void initialize(MBConfigInfo paramMBConfigInfo)
    throws BPWException
  {
    if (this.jdField_try != null)
    {
      FFSDebug.log("BPW MsgBroker was already initialized.", 2);
      FFSDebug.console("BPW MsgBroker was already initialized.");
      return;
    }
    FFSDebug.log("Initializing BPW message broker connection ... ", 6);
    try
    {
      String str1 = paramMBConfigInfo.getDBType();
      String str2 = paramMBConfigInfo.getUrl();
      if ((str2 != null) && (str2.trim().length() > 0))
      {
        str2 = str2.trim();
        this.jdField_try = MBInstanceFactory.createInstanceFromURL(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), str2);
      }
      else if (str1.equalsIgnoreCase("ORACLE:thin"))
      {
        this.jdField_try = MBInstanceFactory.createInstanceFromOracle(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), paramMBConfigInfo.getHost(), paramMBConfigInfo.getPort(), paramMBConfigInfo.getDatabaseName(), true);
      }
      else if (str1.equalsIgnoreCase("ORACLE:oci8"))
      {
        this.jdField_try = MBInstanceFactory.createInstanceFromOracle(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), paramMBConfigInfo.getHost(), paramMBConfigInfo.getPort(), paramMBConfigInfo.getDatabaseName());
      }
      else if (str1.equalsIgnoreCase("MSSQL:thin"))
      {
        this.jdField_try = MBInstanceFactory.createInstanceFromMSSQL(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), paramMBConfigInfo.getHost(), paramMBConfigInfo.getPort(), paramMBConfigInfo.getDatabaseName());
      }
      else if (str1.equalsIgnoreCase("ASA"))
      {
        this.jdField_try = MBInstanceFactory.createInstanceFromJConnect(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), paramMBConfigInfo.getHost(), paramMBConfigInfo.getPort(), paramMBConfigInfo.getDatabaseName());
      }
      else if (str1.equalsIgnoreCase("ASE"))
      {
        this.jdField_try = MBInstanceFactory.createInstanceFromJConnect(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), paramMBConfigInfo.getHost(), paramMBConfigInfo.getPort(), paramMBConfigInfo.getDatabaseName());
      }
      else if (str1.equalsIgnoreCase("DB2:app"))
      {
        this.jdField_try = MBInstanceFactory.createInstanceFromDB2(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), paramMBConfigInfo.getHost(), paramMBConfigInfo.getPort(), paramMBConfigInfo.getDatabaseName(), true);
      }
      else if (str1.equalsIgnoreCase("DB2:un2"))
      {
        System.out.println("#######################################################");
        System.out.println("mbInfo.getUser() = " + paramMBConfigInfo.getUser());
        System.out.println("mbInfo.getPassword() = " + paramMBConfigInfo.getPassword());
        System.out.println("mbInfo.getUrl() = " + paramMBConfigInfo.getUrl());
        System.out.println("#######################################################");
        this.jdField_try = MBInstanceFactory.createInstanceFromDB2UniversalDriver(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), paramMBConfigInfo.getUrl());
      }
      else if (str1.equalsIgnoreCase("DB2:net"))
      {
        this.jdField_try = MBInstanceFactory.createInstanceFromDB2(paramMBConfigInfo.getUser(), paramMBConfigInfo.getPassword(), paramMBConfigInfo.getHost(), paramMBConfigInfo.getPort(), paramMBConfigInfo.getDatabaseName(), false);
      }
      else
      {
        throw new BPWException("DB type not supported, dbType=" + str1);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException.getLocalizedMessage(), 0);
      throw new BPWException("BPW MB error:" + localException.getLocalizedMessage());
    }
    String[] arrayOfString = { "OFX151", "OFX200", "ACHMsgSet", "CheckFree", "IFX14", "ORCC_1999", "PositivePayCheckRecord", "PositivePayDecisions", "PositivePayIssues", "RPPSMsgSet", "RPPSBillerMsgSet", "SWIFT" };
    a(arrayOfString);
    FFSDebug.log("Initializing BPW message broker connection done", 6);
  }
  
  private void a(String[] paramArrayOfString)
    throws BPWException
  {
    FFSDebug.log("Loading BPW Message Sets...", 6);
    if (this.jdField_try == null) {
      return;
    }
    if (paramArrayOfString == null) {
      return;
    }
    String str1 = null;
    for (int i = 0; i < paramArrayOfString.length; i++) {
      try
      {
        str1 = paramArrayOfString[i];
        this.jdField_try.loadMessageSet(str1);
      }
      catch (Throwable localThrowable)
      {
        String str2 = "Unable to load a Message Broker message set (" + str1 + ")";
        FFSDebug.log("Warning: " + str2, 1);
        FFSDebug.console("Warning: " + str2);
      }
    }
  }
  
  public Object parseMsg(String paramString1, String paramString2, String paramString3)
    throws BPWException
  {
    IParser localIParser = jdField_new();
    IMBMessage localIMBMessage = null;
    try
    {
      if (paramString2 == null) {
        localIMBMessage = localIParser.parseToIDL(paramString1, paramString3);
      } else {
        localIMBMessage = localIParser.parseToIDL(paramString1, paramString3, paramString2);
      }
      a(localIParser);
      FFSDebug.log("Successfully parsed message:", paramString1, 6);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to parse message:", paramString1, 0);
      FFSDebug.log(localException.getLocalizedMessage(), 0);
      a(localIParser);
      throw new BPWException("BPW MB parser error:" + localException.getLocalizedMessage());
    }
    return localIMBMessage == null ? null : localIMBMessage.getIDLInstance();
  }
  
  public Object parseMsg(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    throws BPWException
  {
    IParser localIParser = paramBoolean ? this.jdField_do : jdField_new();
    IMBMessage localIMBMessage = null;
    try
    {
      if ((paramBoolean) && (localIParser == null))
      {
        localIParser = this.jdField_try.createDebugParser();
        this.jdField_do = localIParser;
      }
      if (paramString2 == null) {
        localIMBMessage = localIParser.parseToIDL(paramString1, paramString3);
      } else {
        localIMBMessage = localIParser.parseToIDL(paramString1, paramString3, paramString2);
      }
      a(localIParser);
      FFSDebug.log("Successfully parsed message:", paramString1, 6);
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to parse message:", paramString1, 0);
      FFSDebug.log(localException.getLocalizedMessage(), 0);
      a(localIParser);
      throw new BPWException("BPW MB parser error:" + localException.getLocalizedMessage());
    }
    return localIMBMessage == null ? null : localIMBMessage.getIDLInstance();
  }
  
  private IParser jdField_new()
    throws BPWException
  {
    jdField_for();
    IParser localIParser = jdField_int();
    if (localIParser == null) {
      try
      {
        try
        {
          localIParser = this.jdField_try.createParser();
        }
        catch (MBException localMBException1)
        {
          a();
          localIParser = this.jdField_try.createParser();
        }
        FFSDebug.log("Message Broker Parser created successfully.", 4);
      }
      catch (MBException localMBException2)
      {
        String str = "Unable to create a Message Broker Parser!";
        FFSDebug.log(str, 0);
        throw new BPWException("BPW MB parser error:" + str);
      }
    }
    return localIParser;
  }
  
  private void jdField_for()
    throws BPWException
  {
    if (this.jdField_try == null)
    {
      String str = "Message Broker was not successfully initialized!";
      FFSDebug.log(str, 0);
      throw new BPWException("BPW MB error:" + str);
    }
  }
  
  public String buildMsg(Object paramObject, String paramString1, String paramString2)
    throws BPWException
  {
    IBuilder localIBuilder = jdField_if();
    try
    {
      IMBMessage localIMBMessage = MBMessageFactory.createIDLMessage(paramString2, paramString1, paramObject);
      String str = localIBuilder.buildString(localIMBMessage);
      a(localIBuilder);
      FFSDebug.log("Successfully built message:", str, 6);
      return str;
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException.getLocalizedMessage(), 0);
      a(localIBuilder);
      throw new BPWException("BPW MB builder error:" + localException.getLocalizedMessage());
    }
  }
  
  public void printIDLMsg(Object paramObject, String paramString1, String paramString2)
    throws BPWException
  {
    IBuilder localIBuilder = jdField_if();
    try
    {
      IMBMessage localIMBMessage = MBMessageFactory.createIDLMessage(paramString2, paramString1, paramObject);
      localIMBMessage.print();
      a(localIBuilder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException.getLocalizedMessage(), 0);
      a(localIBuilder);
      throw new BPWException("BPW MB builder error:" + localException.getLocalizedMessage());
    }
  }
  
  private IBuilder jdField_if()
    throws BPWException
  {
    jdField_for();
    IBuilder localIBuilder = jdField_do();
    if (localIBuilder == null) {
      try
      {
        try
        {
          localIBuilder = this.jdField_try.createBuilder();
        }
        catch (MBException localMBException1)
        {
          a();
          localIBuilder = this.jdField_try.createBuilder();
        }
        FFSDebug.log("Message Broker Builder created successfully.", 4);
      }
      catch (MBException localMBException2)
      {
        String str = "Unable to create a Message Broker Builder!";
        FFSDebug.log(str, 0);
        throw new BPWException("BPW MB builder error:" + str);
      }
    }
    return localIBuilder;
  }
  
  private final void a()
    throws MBException
  {
    this.jdField_try.disconnect();
    this.jdField_try.connect();
  }
  
  public void stop()
    throws MBException
  {
    FFSDebug.log("BPWMsgBroker.stop() start", 6);
    this.jdField_try.disconnect();
    this.jdField_try = null;
    this.jdField_do = null;
    this.jdField_for = 0;
    this.a = 0;
    FFSDebug.log("BPWMsgBroker.stop() done", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker
 * JD-Core Version:    0.7.0.1
 */