package com.ffusion.tasks.multiuser;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Util;
import com.ffusion.efs.adapters.profile.HistoryAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class FeatureAccessAndLimits
  extends BaseTask
{
  public static final String SESSION_PREFIX = "FAAL";
  public static final String SESSION_MAX_LIMIT_PREFIX = "FAALML";
  public static final String ENT_GROUP_USER_MEMBER = "UserMember";
  public static final String SESSION_OPERATION_NAME = "OperationName";
  public static final String SESSION_GROUP_TYPE = "GroupType";
  public static final char SESSION_DELIMITER = '!';
  public static final int ABORT_PROCESSING = -1;
  private static final String aLX = "com.ffusion.tasks.limit_check_errors";
  private static final String aMb = "Error_1";
  private static final String aL3 = "Error_2";
  private static final String aMc = "Error_3";
  private static final String aL8 = "Error_4";
  private static final String aMf = "Error_5";
  private static final String aL2 = "Error_6";
  private static final String aMd = "Error_7";
  private static final String aL7 = "Error_8";
  private static final String aL4 = "Error_9";
  private static final String aL9 = "Error_10";
  private static final String aLY = "Error_11";
  private static final String aL6 = "Error_12";
  private static final String aMg = "Error_13";
  private static final String aMa = "Error_14";
  private static final int aMe = -28000;
  protected SecureUser sUser = null;
  protected Locale locale = null;
  protected User secondaryUser = null;
  protected String secondaryUserSessionName = "SecondaryUser";
  private String aLV = "Account";
  private Set aMq = new HashSet();
  private Set aMj = new HashSet();
  private Set aLT = new HashSet();
  private com.ffusion.csil.beans.entitlements.Entitlements aMo = null;
  private com.ffusion.csil.beans.entitlements.Entitlements aMn;
  private Limits aMl = null;
  private Limits aLU = null;
  private Limits aLZ;
  private Map aMi = null;
  private Map aL1 = null;
  private Map aMk;
  private boolean aL5;
  private String aMh = null;
  private String aMr = null;
  private String aLW = null;
  private String aMp = null;
  private String aL0;
  private EntitlementTypePropertyLists aMm;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    this.sUser = ((SecureUser)localHttpSession.getAttribute("SecureUser"));
    this.locale = BaseTask.getLocale(localHttpSession, this.sUser);
    if (this.aL5)
    {
      str = beforeProcessing(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
      if (this.error == 0) {
        try
        {
          Z();
          str = afterProcessing(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
        }
        catch (CSILException localCSILException1)
        {
          str = this.serviceErrorURL;
          this.error = MapError.mapError(localCSILException1);
        }
      }
    }
    else
    {
      try
      {
        this.aMm = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
        this.error = performValidation(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
        if (this.error == -28000) {
          str = this.aMp;
        } else if (this.error != 0) {
          str = this.taskErrorURL;
        }
      }
      catch (CSILException localCSILException2)
      {
        str = this.serviceErrorURL;
        this.error = MapError.mapError(localCSILException2);
      }
    }
    if (this.error == 0) {
      t(localHttpSession);
    }
    return str;
  }
  
  protected int performValidation(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    StringBuffer localStringBuffer1 = new StringBuffer("FAAL!");
    StringBuffer localStringBuffer2 = new StringBuffer("FAALML!");
    StringBuffer localStringBuffer3 = new StringBuffer();
    StringBuffer localStringBuffer4 = new StringBuffer();
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    int j = localStringBuffer1.length();
    int k = 0;
    int m = 0;
    int n = localStringBuffer2.length();
    int i1 = 0;
    int i2 = 0;
    boolean bool = false;
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = new com.ffusion.csil.beans.entitlements.Entitlements();
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
    Entitlement localEntitlement = null;
    Limits localLimits1 = new Limits();
    Limits localLimits2 = new Limits();
    Limits localLimits3 = new Limits();
    Limit localLimit = null;
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    if ((i == 0) && (this.sUser == null)) {
      i = 38;
    }
    Iterator localIterator1 = this.aMq.iterator();
    while (localIterator1.hasNext())
    {
      str1 = (String)localIterator1.next();
      localStringBuffer1.delete(j, localStringBuffer1.length());
      localStringBuffer1.append(str1);
      localStringBuffer1.append('!');
      localStringBuffer1.append(this.aLV);
      localStringBuffer1.append('!');
      k = localStringBuffer1.length();
      localStringBuffer2.delete(n, localStringBuffer2.length());
      localStringBuffer2.append(str1);
      localStringBuffer2.append('!');
      localStringBuffer2.append(this.aLV);
      localStringBuffer2.append('!');
      i1 = localStringBuffer2.length();
      Iterator localIterator2 = this.aMj.iterator();
      while (localIterator2.hasNext())
      {
        str2 = (String)localIterator2.next();
        localStringBuffer1.delete(k, localStringBuffer1.length());
        localStringBuffer1.append(str2);
        bool = Boolean.valueOf((String)localHttpSession.getAttribute(localStringBuffer1.toString())).booleanValue();
        localEntitlement = Y(localStringBuffer1.toString());
        if (localEntitlement == null)
        {
          if (i == 0) {
            i = 28046;
          }
        }
        else
        {
          localStringBuffer1.append('!');
          m = localStringBuffer1.length();
          localStringBuffer2.delete(i1, localStringBuffer2.length());
          localStringBuffer2.append(str2);
          localStringBuffer2.append('!');
          i2 = localStringBuffer2.length();
          if (bool)
          {
            localEntitlements1.add(localEntitlement);
            Iterator localIterator3 = this.aLT.iterator();
            while (localIterator3.hasNext())
            {
              str3 = (String)localIterator3.next();
              localStringBuffer1.delete(m, localStringBuffer1.length());
              localStringBuffer1.append(str3);
              localStringBuffer2.delete(i2, localStringBuffer2.length());
              localStringBuffer2.append(str3);
              localStringBuffer3.setLength(0);
              localStringBuffer3.append(localStringBuffer2);
              localStringBuffer3.append('!');
              localStringBuffer3.append("GroupType");
              localStringBuffer4.setLength(0);
              localStringBuffer4.append(localStringBuffer2);
              localStringBuffer4.append('!');
              localStringBuffer4.append("OperationName");
              str4 = (String)localHttpSession.getAttribute(localStringBuffer1.toString());
              str5 = (String)localHttpSession.getAttribute(localStringBuffer2.toString());
              str6 = (String)localHttpSession.getAttribute(localStringBuffer3.toString());
              str7 = (String)localHttpSession.getAttribute(localStringBuffer4.toString());
              if (str4 != null)
              {
                str4 = str4.trim();
                if (str4.length() > 0)
                {
                  if ((!Currency.isValid(str4, this.locale)) && (i == 0)) {
                    i = 28009;
                  }
                  localLimit = new Limit();
                  setLimitCurrencyInformation(localLimit);
                  localLimit.setEntitlement(localEntitlement);
                  localLimit.setPeriod(Integer.parseInt(str3));
                  localLimit.setData(str4);
                  localLimit.setAllowApproval(false);
                  localLimit.setRunningTotalType('U');
                  localLimits1.add(localLimit);
                }
                else
                {
                  localLimit = new Limit();
                  setLimitCurrencyInformation(localLimit);
                  localLimit.setEntitlement(localEntitlement);
                  localLimit.setPeriod(Integer.parseInt(str3));
                  localLimit.setData(str4);
                  localLimit.setAllowApproval(false);
                  localLimit.setRunningTotalType('U');
                  localLimits2.add(localLimit);
                }
              }
              if (str5 != null)
              {
                str5 = str5.trim();
                if (str5.length() > 0)
                {
                  if ((!Currency.isValid(str5, this.locale)) && (i == 0)) {
                    i = 28047;
                  }
                  localLimit = new Limit();
                  setLimitCurrencyInformation(localLimit);
                  localLimit.setEntitlement(localEntitlement);
                  localLimit.setPeriod(Integer.parseInt(str3));
                  localLimit.setData(str5);
                  localLimit.setAllowApproval(false);
                  localLimit.setRunningTotalType('U');
                  localLimits3.add(localLimit);
                  localHashMap2.put(localLimit, str6);
                  localHashMap1.put(localLimit, str7);
                }
              }
            }
          }
          else
          {
            localEntitlements2.add(localEntitlement);
          }
        }
      }
    }
    if (i == 0)
    {
      this.aMo = localEntitlements1;
      this.aMn = localEntitlements2;
      this.aMl = localLimits1;
      this.aLU = localLimits3;
      this.aMi = localHashMap2;
      this.aL1 = localHashMap1;
      this.aLZ = localLimits2;
      i = Y();
    }
    return i;
  }
  
  protected String beforeProcessing(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = this.successURL;
    int i = 1;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((i != 0) && ((this.secondaryUserSessionName == null) || (this.secondaryUserSessionName.length() == 0)))
    {
      this.error = 28001;
      str = this.taskErrorURL;
      i = 0;
    }
    if (i != 0)
    {
      this.secondaryUser = ((User)localHttpSession.getAttribute(this.secondaryUserSessionName));
      if (this.secondaryUser == null)
      {
        this.error = 28002;
        str = this.taskErrorURL;
        i = 0;
      }
    }
    return str;
  }
  
  protected String afterProcessing(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str = this.successURL;
    return str;
  }
  
  protected EntitlementGroupMember getEntitlementGroupMemberForSecondaryUser()
  {
    EntitlementGroupMember localEntitlementGroupMember = null;
    localEntitlementGroupMember = new EntitlementGroupMember();
    localEntitlementGroupMember.setId(this.secondaryUser.getId());
    localEntitlementGroupMember.setMemberType("USER");
    localEntitlementGroupMember.setMemberSubType(Integer.toString(this.sUser.getUserType()));
    localEntitlementGroupMember.setEntitlementGroupId(this.secondaryUser.getEntitlementGroupId());
    return localEntitlementGroupMember;
  }
  
  public void setProcess(String paramString)
  {
    this.aL5 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAddOperationToList(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.aMq.add(paramString.trim());
    }
  }
  
  public void setObjectType(String paramString)
  {
    if (paramString == null) {
      this.aLV = "Account";
    } else {
      this.aLV = paramString;
    }
  }
  
  public void setAddObjectIDToList(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.aMj.add(paramString.trim());
    }
  }
  
  public void setAddLimitPeriodToList(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.aLT.add(paramString.trim());
    }
  }
  
  public void setSecondaryUserSessionName(String paramString)
  {
    if (paramString == null) {
      this.secondaryUserSessionName = "SecondaryUser";
    } else {
      this.secondaryUserSessionName = paramString;
    }
  }
  
  public void setReset(String paramString)
  {
    this.aMq.clear();
    this.aMj.clear();
    this.aLT.clear();
  }
  
  public void setEntitlementOperationName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.aMh = null;
    } else {
      this.aMh = paramString;
    }
  }
  
  public void setEntitlementObjectID(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.aMr = null;
    } else {
      this.aMr = paramString;
    }
  }
  
  public void setLimitPeriod(String paramString)
  {
    this.aLW = paramString;
  }
  
  public void setRedundantLimitsURL(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aMp = null;
    } else {
      this.aMp = paramString;
    }
  }
  
  public String getIsEntitledObject()
  {
    boolean bool = false;
    Entitlement localEntitlement = new Entitlement();
    localEntitlement.setOperationName(this.aMh);
    localEntitlement.setObjectType(this.aLV);
    localEntitlement.setObjectId(this.aMr);
    bool = this.aMo.contains(localEntitlement);
    return Boolean.toString(bool);
  }
  
  public String getObjectLimit()
  {
    String str = null;
    int i = Integer.parseInt(this.aLW);
    Entitlement localEntitlement = new Entitlement();
    Limit localLimit = null;
    localEntitlement.setOperationName(this.aMh);
    localEntitlement.setObjectType(this.aLV);
    localEntitlement.setObjectId(this.aMr);
    Iterator localIterator = this.aMl.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if ((localLimit.getPeriod() == i) && (localEntitlement.equals(localLimit.getEntitlement()))) {
        str = localLimit.getData();
      }
    }
    return str;
  }
  
  public void setRedundantLimitErrorMessageKey(String paramString)
  {
    this.aL0 = paramString;
  }
  
  public String getRedundantLimitErrorMessage()
  {
    String str = (String)this.aMk.get(this.aL0);
    return str;
  }
  
  public boolean getHasDetectedRedundantLimits()
  {
    boolean bool = (this.aMk != null) && (!this.aMk.isEmpty());
    return bool;
  }
  
  private static void t(HttpSession paramHttpSession)
  {
    ArrayList localArrayList = null;
    String str = null;
    localArrayList = new ArrayList();
    Enumeration localEnumeration = paramHttpSession.getAttributeNames();
    while (localEnumeration.hasMoreElements())
    {
      str = (String)localEnumeration.nextElement();
      if (str != null) {
        if (str.startsWith("FAAL")) {
          localArrayList.add(str);
        } else if (str.startsWith("FAALML")) {
          localArrayList.add(str);
        }
      }
    }
    int i = 0;
    int j = localArrayList.size();
    while (i < j)
    {
      paramHttpSession.removeAttribute((String)localArrayList.get(i));
      i++;
    }
  }
  
  private static Entitlement Y(String paramString)
  {
    Entitlement localEntitlement = null;
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, Character.toString('!'));
    if (localStringTokenizer.countTokens() == 4)
    {
      localStringTokenizer.nextToken();
      localEntitlement = new Entitlement(localStringTokenizer.nextToken(), localStringTokenizer.nextToken(), localStringTokenizer.nextToken());
    }
    return localEntitlement;
  }
  
  private static Limits jdMethod_for(Limits paramLimits, Entitlement paramEntitlement)
  {
    Limits localLimits = new Limits();
    Limit localLimit = null;
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if (paramEntitlement.equals(localLimit.getEntitlement())) {
        localLimits.add(localLimit);
      }
    }
    return localLimits;
  }
  
  private void Z()
    throws CSILException
  {
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(this.sUser);
    EntitlementGroupMember localEntitlementGroupMember2 = getEntitlementGroupMemberForSecondaryUser();
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = null;
    com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = null;
    Entitlement localEntitlement = null;
    Limits localLimits1 = null;
    Limits localLimits2 = null;
    Limit localLimit1 = null;
    Limit localLimit2 = null;
    HistoryTracker localHistoryTracker = new HistoryTracker(this.sUser, 1, this.secondaryUser.getId());
    jdMethod_for(this.aMl);
    jdMethod_for(this.aLZ);
    if (this.aMm == null) {
      this.aMm = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    localEntitlements1 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2);
    localLimits1 = com.ffusion.csil.core.Entitlements.getGroupLimits(localEntitlementGroupMember2);
    localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
    Object localObject1 = this.aMn.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localEntitlement = (Entitlement)((Iterator)localObject1).next();
      if (!localEntitlements1.contains(localEntitlement))
      {
        localEntitlements2.add(localEntitlement);
        localHistoryTracker.logEntitlementAdd(localEntitlement, this.aMm);
        localObject2 = localLimits1.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localLimit1 = (Limit)((Iterator)localObject2).next();
          if (localEntitlement.equals(localLimit1.getEntitlement())) {
            com.ffusion.csil.core.Entitlements.deleteGroupLimit(localEntitlementGroupMember1, localLimit1);
          }
        }
      }
    }
    localObject1 = null;
    Object localObject2 = null;
    Iterator localIterator1 = this.aLZ.iterator();
    Iterator localIterator2;
    while (localIterator1.hasNext())
    {
      localObject1 = (Limit)localIterator1.next();
      localIterator2 = localLimits1.iterator();
      while (localIterator2.hasNext())
      {
        localObject2 = (Limit)localIterator2.next();
        if (((Limit)localObject2).isLimitInfoIdentical((Limit)localObject1)) {
          com.ffusion.csil.core.Entitlements.deleteGroupLimit(localEntitlementGroupMember1, (Limit)localObject2);
        }
      }
    }
    com.ffusion.csil.core.Entitlements.addRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements2);
    localEntitlements2 = new com.ffusion.csil.beans.entitlements.Entitlements();
    localIterator1 = this.aMo.iterator();
    while (localIterator1.hasNext())
    {
      localEntitlement = (Entitlement)localIterator1.next();
      localLimits2 = jdMethod_for(this.aMl, localEntitlement);
      if (localEntitlements1.contains(localEntitlement))
      {
        localEntitlements2.add(localEntitlement);
        localHistoryTracker.logEntitlementDelete(localEntitlement, this.aMm);
      }
      localIterator2 = localLimits2.iterator();
      while (localIterator2.hasNext())
      {
        localLimit1 = (Limit)localIterator2.next();
        Iterator localIterator3 = localLimits1.iterator();
        while (localIterator3.hasNext())
        {
          localLimit2 = (Limit)localIterator3.next();
          if (localLimit1.isLimitInfoIdentical(localLimit2))
          {
            localLimit2.setData(localLimit1.getData());
            com.ffusion.csil.core.Entitlements.modifyGroupLimit(localEntitlementGroupMember1, localLimit2);
            localIterator2.remove();
          }
        }
      }
      localIterator2 = localLimits2.iterator();
      while (localIterator2.hasNext())
      {
        localLimit1 = (Limit)localIterator2.next();
        com.ffusion.csil.core.Entitlements.addGroupLimit(localEntitlementGroupMember1, localLimit1);
      }
    }
    com.ffusion.csil.core.Entitlements.removeRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2, localEntitlements2);
    try
    {
      HistoryAdapter.addHistory(localHistoryTracker.getHistories());
    }
    catch (ProfileException localProfileException)
    {
      DebugLog.log(Level.SEVERE, "Add History failed for com.ffusion.tasks.multiuser.FeatureAccessAndLimits: " + localProfileException.toString());
    }
  }
  
  private String jdMethod_case(Limit paramLimit)
  {
    StringBuffer localStringBuffer = new StringBuffer("FAAL!");
    Entitlement localEntitlement = paramLimit.getEntitlement();
    localStringBuffer.append(localEntitlement.getOperationName());
    localStringBuffer.append('!');
    localStringBuffer.append(localEntitlement.getObjectType());
    localStringBuffer.append('!');
    localStringBuffer.append(localEntitlement.getObjectId());
    localStringBuffer.append('!');
    localStringBuffer.append(paramLimit.getPeriod());
    return localStringBuffer.toString();
  }
  
  private String jdMethod_byte(Limit paramLimit)
  {
    StringBuffer localStringBuffer = new StringBuffer("FAALML!");
    Entitlement localEntitlement = paramLimit.getEntitlement();
    localStringBuffer.append(localEntitlement.getOperationName());
    localStringBuffer.append('!');
    localStringBuffer.append(localEntitlement.getObjectType());
    localStringBuffer.append('!');
    localStringBuffer.append(localEntitlement.getObjectId());
    localStringBuffer.append('!');
    localStringBuffer.append(paramLimit.getPeriod());
    return localStringBuffer.toString();
  }
  
  private int Y()
  {
    int i = 0;
    Limit localLimit1 = null;
    Limit localLimit2 = null;
    int j = 0;
    int k = 0;
    BigDecimal localBigDecimal1 = null;
    BigDecimal localBigDecimal2 = null;
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.tasks.limit_check_errors", this.locale);
    int m = 0;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    this.aMk = new HashMap();
    Iterator localIterator1 = this.aMl.iterator();
    Iterator localIterator2;
    while (localIterator1.hasNext())
    {
      localLimit1 = (Limit)localIterator1.next();
      j = localLimit1.getPeriod();
      localIterator2 = this.aLU.iterator();
      while (localIterator2.hasNext())
      {
        localLimit2 = (Limit)localIterator2.next();
        if (localLimit1.isLimitInfoIdentical(localLimit2))
        {
          localBigDecimal1 = new BigDecimal(localLimit1.getData());
          localBigDecimal2 = new BigDecimal(localLimit2.getData());
          if (localBigDecimal1.compareTo(localBigDecimal2) > 0)
          {
            Currency localCurrency = new Currency(localBigDecimal2, this.locale);
            Object[] arrayOfObject = null;
            LocalizableString localLocalizableString = null;
            m = 1;
            str1 = (String)this.aMi.get(localLimit2);
            str2 = (String)this.aL1.get(localLimit2);
            if (str2 != null)
            {
              str3 = (String)this.aMm.getByOperationName(str2).getPropertiesMap().get("display name");
              str3 = str3 == null ? str2 : str3;
            }
            switch (localLimit1.getPeriod())
            {
            case 4: 
              if ((str1 == null) && (str3 == null))
              {
                arrayOfObject = new Object[] { localCurrency.toString() };
                localLocalizableString = new LocalizableString("com.ffusion.tasks.limit_check_errors", "Error_10", arrayOfObject);
                this.aMk.put(jdMethod_byte(localLimit1), localLocalizableString.localize(this.locale));
              }
              else if (("UserMember".equals(str1)) && (str3 != null))
              {
                arrayOfObject = new Object[] { str3, localCurrency.toString() };
                localLocalizableString = new LocalizableString("com.ffusion.tasks.limit_check_errors", "Error_14", arrayOfObject);
                this.aMk.put(jdMethod_byte(localLimit1), localLocalizableString.localize(this.locale));
              }
              break;
            case 3: 
              if ((str1 == null) && (str3 == null))
              {
                arrayOfObject = new Object[] { localCurrency.toString() };
                localLocalizableString = new LocalizableString("com.ffusion.tasks.limit_check_errors", "Error_9", arrayOfObject);
                this.aMk.put(jdMethod_byte(localLimit1), localLocalizableString.localize(this.locale));
              }
              else if (("UserMember".equals(str1)) && (str3 != null))
              {
                arrayOfObject = new Object[] { str3, localCurrency.toString() };
                localLocalizableString = new LocalizableString("com.ffusion.tasks.limit_check_errors", "Error_13", arrayOfObject);
                this.aMk.put(jdMethod_byte(localLimit1), localLocalizableString.localize(this.locale));
              }
              break;
            case 2: 
              if ((str1 == null) && (str3 == null))
              {
                arrayOfObject = new Object[] { localCurrency.toString() };
                localLocalizableString = new LocalizableString("com.ffusion.tasks.limit_check_errors", "Error_8", arrayOfObject);
                this.aMk.put(jdMethod_byte(localLimit1), localLocalizableString.localize(this.locale));
              }
              else if (("UserMember".equals(str1)) && (str3 != null))
              {
                arrayOfObject = new Object[] { str3, localCurrency.toString() };
                localLocalizableString = new LocalizableString("com.ffusion.tasks.limit_check_errors", "Error_12", arrayOfObject);
                this.aMk.put(jdMethod_byte(localLimit1), localLocalizableString.localize(this.locale));
              }
              break;
            case 1: 
              if ((str1 == null) && (str3 == null))
              {
                arrayOfObject = new Object[] { localCurrency.toString() };
                localLocalizableString = new LocalizableString("com.ffusion.tasks.limit_check_errors", "Error_7", arrayOfObject);
                this.aMk.put(jdMethod_byte(localLimit1), localLocalizableString.localize(this.locale));
              }
              else if (("UserMember".equals(str1)) && (str3 != null))
              {
                arrayOfObject = new Object[] { str3, localCurrency.toString() };
                localLocalizableString = new LocalizableString("com.ffusion.tasks.limit_check_errors", "Error_11", arrayOfObject);
                this.aMk.put(jdMethod_byte(localLimit1), localLocalizableString.localize(this.locale));
              }
              break;
            }
          }
        }
      }
    }
    localIterator1 = this.aMl.iterator();
    while (localIterator1.hasNext())
    {
      localLimit1 = (Limit)localIterator1.next();
      j = localLimit1.getPeriod();
      if (j != 4)
      {
        localIterator2 = this.aMl.iterator();
        while (localIterator2.hasNext())
        {
          localLimit2 = (Limit)localIterator2.next();
          k = localLimit2.getPeriod();
          if ((localLimit1.getEntitlement().equals(localLimit2.getEntitlement())) && (j != k))
          {
            localBigDecimal1 = new BigDecimal(localLimit1.getData());
            localBigDecimal2 = new BigDecimal(localLimit2.getData());
            if (localBigDecimal1.compareTo(localBigDecimal2) > 0) {
              switch (j)
              {
              case 4: 
                break;
              case 3: 
                if (k == 4)
                {
                  this.aMk.put(jdMethod_case(localLimit1), localResourceBundle.getString("Error_6"));
                  m = 1;
                }
                break;
              case 2: 
                if (k == 4)
                {
                  this.aMk.put(jdMethod_case(localLimit1), localResourceBundle.getString("Error_5"));
                  m = 1;
                }
                else if (k == 3)
                {
                  this.aMk.put(jdMethod_case(localLimit1), localResourceBundle.getString("Error_4"));
                  m = 1;
                }
                break;
              case 1: 
                if (k == 4)
                {
                  this.aMk.put(jdMethod_case(localLimit1), localResourceBundle.getString("Error_3"));
                  m = 1;
                }
                else if (k == 3)
                {
                  this.aMk.put(jdMethod_case(localLimit1), localResourceBundle.getString("Error_2"));
                  m = 1;
                }
                else if (k == 2)
                {
                  this.aMk.put(jdMethod_case(localLimit1), localResourceBundle.getString("Error_1"));
                  m = 1;
                }
                break;
              }
            }
          }
        }
      }
    }
    if (m != 0) {
      i = -28000;
    }
    return i;
  }
  
  private void jdMethod_for(Limits paramLimits)
  {
    EntitlementGroupMember localEntitlementGroupMember = getEntitlementGroupMemberForSecondaryUser();
    Limit localLimit = null;
    Iterator localIterator = paramLimits.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      localLimit.setGroupId(localEntitlementGroupMember.getEntitlementGroupId());
      localLimit.setMemberType("USER");
      localLimit.setMemberSubType(localEntitlementGroupMember.getMemberSubType());
      localLimit.setMemberId(localEntitlementGroupMember.getId());
    }
  }
  
  protected void setLimitCurrencyInformation(Limit paramLimit)
  {
    String str = Util.getLimitBaseCurrency();
    paramLimit.setCurrencyCode(str);
    paramLimit.setCrossCurrency(true);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.FeatureAccessAndLimits
 * JD-Core Version:    0.7.0.1
 */