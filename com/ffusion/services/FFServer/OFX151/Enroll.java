package com.ffusion.services.FFServer.OFX151;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.User;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.IOFX151Callback;
import com.ffusion.msgbroker.generated.MessageBroker.api.OFX151.Callback.OFX151CallbackBean;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeEnrollTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRequest;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeOFXRqMsgSetsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignOnMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignUpMsgsRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignUpMsgsRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSignUpMsgsRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSubAddressCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.services.Enroll2;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;

public class Enroll
  extends Base
  implements Defines, Enroll2
{
  private static final String bL = "EnrollID";
  private static final String bM = "EnrollPassword";
  protected static String enrollID;
  protected static String enrollPassword;
  
  public int initialize(String paramString)
  {
    return initialize(paramString, new a());
  }
  
  public String getSettings()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ENROLLSERVICE");
    if (this.debugService)
    {
      XMLHandler.appendTag(localStringBuffer, "EnrollID", enrollID);
      XMLHandler.appendTag(localStringBuffer, "EnrollPassword", enrollPassword);
    }
    localStringBuffer.append(super.getSettings());
    XMLHandler.appendEndTag(localStringBuffer, "ENROLLSERVICE");
    return localStringBuffer.toString();
  }
  
  public int enroll(HashMap paramHashMap)
  {
    if (this.debugService) {
      DebugLog.log(Level.INFO, "Enroll Message");
    }
    formatEnrollRequest(paramHashMap);
    processOFXRequest();
    return 0;
  }
  
  public int activateAccount(User paramUser, Accounts paramAccounts)
  {
    return 0;
  }
  
  protected void formatEnrollRequest(HashMap paramHashMap)
  {
    this.rqmes = new TypeOFXRequest();
    this.rqmes.OFXRequest = new TypeOFXRqMsgSetsAggregate();
    if (this.debugService) {
      DebugLog.log(Level.INFO, getClass().getName() + ".formatEnrollRequest:");
    }
    setUserID(enrollID);
    this.password = enrollPassword;
    formatSIGNONMSGSRQV1(this.rqmes.OFXRequest);
    this.rqmes.OFXRequest.SignUpMsgsRqUnExists = true;
    this.rqmes.OFXRequest.SignUpMsgsRqUn = new TypeSignUpMsgsRqUn();
    this.rqmes.OFXRequest.SignUpMsgsRqUn.__memberName = "SignUpMsgsRqV1";
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1 = new TypeSignUpMsgsRqV1Aggregate();
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1.SignUpMsgsRqV1Un = new TypeSignUpMsgsRqV1Un[1];
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1.SignUpMsgsRqV1Un[0] = new TypeSignUpMsgsRqV1Un();
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1.SignUpMsgsRqV1Un[0].__memberName = "EnrollTrnRq";
    TypeEnrollTrnRqV1Aggregate localTypeEnrollTrnRqV1Aggregate = new TypeEnrollTrnRqV1Aggregate();
    this.rqmes.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1.SignUpMsgsRqV1Un[0].EnrollTrnRq = localTypeEnrollTrnRqV1Aggregate;
    localTypeEnrollTrnRqV1Aggregate.TrnRqCm = new TypeTrnRqCm();
    localTypeEnrollTrnRqV1Aggregate.TrnRqCm.TrnUID = getUniqueSeqNum();
    localTypeEnrollTrnRqV1Aggregate.EnrollRq = new TypeEnrollRqV1Aggregate();
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.FirstName = ((String)paramHashMap.get("FIRST_NAME"));
    if ((paramHashMap.get("MiddleName") != null) && (((String)paramHashMap.get("MiddleName")).trim().length() > 0)) {
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.MiddleName = ((String)paramHashMap.get("MiddleName"));
    } else {
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.MiddleName = "none";
    }
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.LastName = ((String)paramHashMap.get("LAST_NAME"));
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.AddressCm = new TypeAddressCm();
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.AddressCm.Addr1 = ((String)paramHashMap.get("ADDR"));
    if (paramHashMap.get("ADDR2") != null)
    {
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.AddressCm.SubAddressCmExists = true;
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.AddressCm.SubAddressCm = new TypeSubAddressCm();
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.AddressCm.SubAddressCm.Addr2 = ((String)paramHashMap.get("ADDR2"));
      if (paramHashMap.get("ADDR3") != null)
      {
        localTypeEnrollTrnRqV1Aggregate.EnrollRq.AddressCm.SubAddressCm.Addr3Exists = true;
        localTypeEnrollTrnRqV1Aggregate.EnrollRq.AddressCm.SubAddressCm.Addr3 = ((String)paramHashMap.get("ADDR3"));
      }
    }
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.City = ((String)paramHashMap.get("CITY"));
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.State = ((String)paramHashMap.get("STATE"));
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.PostalCode = ((String)paramHashMap.get("ZIPCODE"));
    if (paramHashMap.get("COUNTRY") != null) {
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.Country = ((String)paramHashMap.get("COUNTRY"));
    } else {
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.Country = "USA";
    }
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.DayPhone = ((String)paramHashMap.get("Phone"));
    if (paramHashMap.get("EVEPHONE") != null) {
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.EvePhone = ((String)paramHashMap.get("EVEPHONE"));
    } else {
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.EvePhone = "none";
    }
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.Email = ((String)paramHashMap.get("EMAIL_ADDRESS"));
    if (paramHashMap.get("CUSTID") != null)
    {
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.UserID = ((String)paramHashMap.get("CUSTID"));
      localTypeEnrollTrnRqV1Aggregate.EnrollRq.UserIDExists = true;
    }
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.TaxID = ((String)paramHashMap.get("SSN"));
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.SecurityName = ((String)paramHashMap.get("MothersMaidenName"));
    String str = (String)paramHashMap.get("Birthdate");
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    try
    {
      localGregorianCalendar.setTime(DateFormatUtil.getFormatter("MM/dd/yyyy").parse(str));
    }
    catch (Exception localException) {}
    localTypeEnrollTrnRqV1Aggregate.EnrollRq.DateBirth = DateFormatUtil.getFormatter("yyyyMMdd").format(localGregorianCalendar.getTime());
  }
  
  protected void processOFXRequest()
  {
    TypeOFXRequest localTypeOFXRequest = this.rqmes;
    this.objStatus.clear();
    getHandler();
    try
    {
      if (localTypeOFXRequest.OFXRequest.SignOnMsgsRqUn.__memberName.equals("SignOnMsgsRqV1")) {
        processTransactionsInSignOnMsgsRqV1(localTypeOFXRequest.OFXRequest.SignOnMsgsRqUn.SignOnMsgsRqV1);
      }
      if ((localTypeOFXRequest.OFXRequest.SignUpMsgsRqUnExists) && (localTypeOFXRequest.OFXRequest.SignUpMsgsRqUn.__memberName.equals("SignUpMsgsRqV1"))) {
        processTransactionsInSignUpMsgsRqV1(localTypeOFXRequest.OFXRequest.SignUpMsgsRqUn.SignUpMsgsRqV1);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if (this.signonProcessed == true) {
      processDisconnectRq(null);
    }
    removeHandler();
  }
  
  protected void processTransactionsInSignUpMsgsRqV1(TypeSignUpMsgsRqV1Aggregate paramTypeSignUpMsgsRqV1Aggregate)
  {
    for (int i = 0; i < paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqV1Un.length; i++) {
      if (paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqV1Un[i].__memberName.equals("EnrollTrnRq"))
      {
        TypeEnrollTrnRqV1 localTypeEnrollTrnRqV1 = new TypeEnrollTrnRqV1(paramTypeSignUpMsgsRqV1Aggregate.SignUpMsgsRqV1Un[i].EnrollTrnRq);
        TypeEnrollTrnRsV1 localTypeEnrollTrnRsV1 = null;
        localTypeEnrollTrnRsV1 = processEnrollTrnRqV1(localTypeEnrollTrnRqV1);
        if (localTypeEnrollTrnRsV1 != null) {
          processEnrollTrnRs(localTypeEnrollTrnRsV1.EnrollTrnRs);
        }
      }
    }
  }
  
  protected void processEnrollTrnRs(TypeEnrollTrnRsV1Aggregate paramTypeEnrollTrnRsV1Aggregate)
  {
    processSTATUS(paramTypeEnrollTrnRsV1Aggregate.TrnRsV1Cm.Status);
    if ((paramTypeEnrollTrnRsV1Aggregate.EnrollRsExists) && ((!paramTypeEnrollTrnRsV1Aggregate.EnrollRs.TempPassExists) || ((!paramTypeEnrollTrnRsV1Aggregate.EnrollRs.UserIDExists) || (paramTypeEnrollTrnRsV1Aggregate.EnrollRs.DtExpireExists)))) {}
  }
  
  protected TypeEnrollTrnRsV1 processEnrollTrnRqV1(TypeEnrollTrnRqV1 paramTypeEnrollTrnRqV1)
  {
    try
    {
      if (this._OFXTransactionBean != null) {
        return this._OFXTransactionBean.processEnrollTrnRqV1(paramTypeEnrollTrnRqV1, this._ud);
      }
      return this._OFXTransactionHandler.processEnrollTrnRqV1(paramTypeEnrollTrnRqV1, this._ud);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception in processEnrollTrnRqV1", localException);
    }
    return null;
  }
  
  public class a
    extends Base.a
  {
    public a()
    {
      super();
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equals("EnrollID")) {
        Enroll.enrollID = paramString2;
      } else if (paramString1.equals("EnrollPassword")) {
        Enroll.enrollPassword = paramString2;
      } else {
        super.attribute(paramString1, paramString2, paramBoolean);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.FFServer.OFX151.Enroll
 * JD-Core Version:    0.7.0.1
 */