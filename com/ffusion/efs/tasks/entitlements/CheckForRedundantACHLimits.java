package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.FX;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckForRedundantACHLimits
  extends BaseTask
  implements Task
{
  private static final String b4 = "EntitlementGroupMember";
  private static final String bY = "approvePerBatch";
  private static final String bP = "approveDaily";
  private static final String ct = "perBatch";
  private static final String cl = "daily";
  private static final String cf = "limit_error";
  private static final int b1 = 0;
  private static final int ci = 1;
  private static final int cd = 2;
  private static final int cb = 3;
  private String cc;
  private String bU;
  private String cj;
  private String bL;
  private int bW;
  private String bO;
  private String ce;
  private String ca;
  private String bR;
  private String bN = "ACHCompany";
  private ArrayList cq;
  private boolean cg = true;
  private String bX = null;
  private static final String co = "FALSE";
  private static final String b5 = "TRUE";
  private static final String cm = "com.ffusion.tasks.resources";
  private static final String cr = "exceed_with_no_limit_w_type";
  private static final String bM = "period_per_batch";
  private static final String b9 = "period_daily";
  private static final String cs = "bad_limit";
  private static final String b8 = "no_sign";
  private static final String ch = "invalid_amount";
  private static final String b6 = "ACH_control_parent_group";
  private static final String bS = "ACH_control_parent_bank";
  private static final String ck = "ACH_control_parent_user";
  private static final String b0 = "control_parent_group";
  private static final String bV = "control_parent_bank";
  private static final String cn = "control_parent_user";
  private static final String b7 = "limit_type_ACH_company";
  private static final String b3 = "limit_type_cross_ACH";
  private static final String bT = "business";
  private static final String cp = "division";
  private static final String b2 = "group";
  private static final String bZ = "period_with_per_batch";
  private static final String bQ = "period_with_daily";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    if (this.bX == null) {
      this.bX = Util.getLimitBaseCurrency();
    }
    EntitlementGroupMember localEntitlementGroupMember1 = (EntitlementGroupMember)localHttpSession.getAttribute("EntitlementGroupMember");
    if (localEntitlementGroupMember1 == null)
    {
      localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
      localHttpSession.setAttribute("EntitlementGroupMember", localEntitlementGroupMember1);
    }
    EntitlementGroupMember localEntitlementGroupMember2 = null;
    if (this.ce != null)
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setId(this.bO);
      localEntitlementGroupMember2.setMemberType(this.ce);
      localEntitlementGroupMember2.setMemberSubType(this.ca);
      localEntitlementGroupMember2.setEntitlementGroupId(this.bW);
    }
    str = jdMethod_for(localHttpSession, localEntitlementGroupMember2);
    return str;
  }
  
  private String jdMethod_for(HttpSession paramHttpSession, EntitlementGroupMember paramEntitlementGroupMember)
  {
    String str1 = this.successURL;
    if ((this.bL == null) || (this.bL.equals("")))
    {
      this.error = 95;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.cq = ((ArrayList)paramHttpSession.getAttribute(this.bL));
    if (this.cq == null)
    {
      this.error = 4558;
      return this.taskErrorURL;
    }
    if ((this.bU == null) || (this.bU.equals("")))
    {
      this.error = 4550;
      return this.taskErrorURL;
    }
    if ((this.cj == null) || (this.cj.equals("")))
    {
      this.error = 4551;
      return this.taskErrorURL;
    }
    Limits localLimits = null;
    try
    {
      if (paramEntitlementGroupMember == null) {
        localLimits = Entitlements.getGroupLimits(this.bW);
      } else {
        localLimits = Entitlements.getGroupLimits(paramEntitlementGroupMember);
      }
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      str1 = this.serviceErrorURL;
    }
    HashMap localHashMap1 = (HashMap)paramHttpSession.getAttribute(this.bU);
    HashMap localHashMap2 = (HashMap)paramHttpSession.getAttribute(this.cj);
    Object localObject = this.cq.iterator();
    while (((Iterator)localObject).hasNext())
    {
      ArrayList localArrayList = (ArrayList)((Iterator)localObject).next();
      String str2 = jdMethod_for((String)localArrayList.get(1), (String)localArrayList.get(0));
      String str3 = (String)paramHttpSession.getAttribute("perBatch" + str2);
      String str4 = (String)paramHttpSession.getAttribute("daily" + str2);
      String str5 = (String)paramHttpSession.getAttribute("approvePerBatch" + str2);
      String str6 = (String)paramHttpSession.getAttribute("approveDaily" + str2);
      StringBuffer localStringBuffer = new StringBuffer();
      BigDecimal localBigDecimal1 = null;
      BigDecimal localBigDecimal2 = null;
      int i = 1;
      int j = 1;
      if ((str3 != null) && (!str3.equals("")))
      {
        if (!jdMethod_for(localSecureUser, str3, 0, localStringBuffer))
        {
          i = 0;
          str1 = this.cc;
        }
      }
      else
      {
        i = 0;
        if ((str5 != null) && (str5.equalsIgnoreCase("true")))
        {
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = new LocalizableString("com.ffusion.tasks.resources", "period_per_batch", null);
          arrayOfObject1[1] = ((String)localArrayList.get(1)).toLowerCase();
          localStringBuffer.append(new LocalizableString("com.ffusion.tasks.resources", "exceed_with_no_limit_w_type", arrayOfObject1).localize(localSecureUser.getLocale()));
          localStringBuffer.append("<br>");
          str1 = this.cc;
        }
      }
      if (i != 0) {
        try
        {
          localBigDecimal1 = new BigDecimal(str3);
        }
        catch (NumberFormatException localNumberFormatException1) {}
      }
      if ((str4 != null) && (!str4.equals("")))
      {
        if (!jdMethod_for(localSecureUser, str4, 0, localStringBuffer))
        {
          j = 0;
          str1 = this.cc;
        }
      }
      else
      {
        j = 0;
        if ((str6 != null) && (str6.equalsIgnoreCase("true")))
        {
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = new LocalizableString("com.ffusion.tasks.resources", "period_daily", null);
          arrayOfObject2[1] = ((String)localArrayList.get(1)).toLowerCase();
          localStringBuffer.append(new LocalizableString("com.ffusion.tasks.resources", "exceed_with_no_limit_w_type", arrayOfObject2).localize(localSecureUser.getLocale()));
          localStringBuffer.append("<br>");
          str1 = this.cc;
        }
      }
      if (j != 0) {
        try
        {
          localBigDecimal2 = new BigDecimal(str4);
        }
        catch (NumberFormatException localNumberFormatException2) {}
      }
      if ((i != 0) && (j != 0) && (localBigDecimal2.compareTo(localBigDecimal1) < 0))
      {
        Object[] arrayOfObject3 = new Object[2];
        arrayOfObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_per_batch", null);
        arrayOfObject3[1] = ((String)localArrayList.get(1)).toLowerCase();
        arrayOfObject3[2] = new LocalizableString("com.ffusion.tasks.resources", "period_daily", null);
        arrayOfObject3[3] = ((String)localArrayList.get(1)).toLowerCase();
        localStringBuffer.append(new LocalizableString("com.ffusion.tasks.resources", "exceed_with_no_limit_w_type", arrayOfObject3).localize(localSecureUser.getLocale()));
        localStringBuffer.append("<br>");
        str1 = this.cc;
      }
      try
      {
        if (!jdMethod_for(paramHttpSession, localArrayList, localHashMap1, localHashMap2, localBigDecimal1, localBigDecimal2, localStringBuffer)) {
          str1 = this.cc;
        } else if (!jdMethod_for(paramHttpSession, this.bW, paramEntitlementGroupMember, localLimits, localArrayList, localBigDecimal1, localBigDecimal2, localStringBuffer)) {
          str1 = this.cc;
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = localCSILException2.getCode();
        str1 = this.serviceErrorURL;
      }
      paramHttpSession.setAttribute("limit_error" + str2, localStringBuffer.toString());
    }
    if (str1.equals(this.cc))
    {
      localObject = (String)new LocalizableString("com.ffusion.tasks.resources", "bad_limit", null).localize(localSecureUser.getLocale());
      paramHttpSession.setAttribute("limit_error", localObject);
    }
    return str1;
  }
  
  private String jdMethod_for(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    for (int i = 0; i < paramString2.length(); i++)
    {
      char c = paramString2.charAt(i);
      if ((c != '+') && (c != '-') && (c != ' ')) {
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }
  
  private boolean jdMethod_for(SecureUser paramSecureUser, String paramString, int paramInt, StringBuffer paramStringBuffer)
  {
    int i = 0;
    if (paramString.length() < 1) {
      return true;
    }
    if ((paramInt == 0) && ((paramString.charAt(0) == '+') || (paramString.charAt(0) == '-')))
    {
      if (jdMethod_for(paramSecureUser, paramString.substring(1), 1, paramStringBuffer))
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramString;
        paramStringBuffer.append(new LocalizableString("com.ffusion.tasks.resources", "no_sign", arrayOfObject1).localize(paramSecureUser.getLocale()));
        paramStringBuffer.append("<br>");
      }
      return false;
    }
    for (int j = paramInt; j < paramString.length(); j++)
    {
      char c = paramString.charAt(j);
      Object[] arrayOfObject2;
      if (c == '.')
      {
        i++;
        if (i > 1)
        {
          arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = paramString;
          paramStringBuffer.append(new LocalizableString("com.ffusion.tasks.resources", "invalid_amount", arrayOfObject2).localize(paramSecureUser.getLocale()));
          paramStringBuffer.append("<br>");
          return false;
        }
      }
      else if (!Character.isDigit(c))
      {
        arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = paramString;
        paramStringBuffer.append(new LocalizableString("com.ffusion.tasks.resources", "invalid_amount", arrayOfObject2).localize(paramSecureUser.getLocale()));
        paramStringBuffer.append("<br>");
        return false;
      }
    }
    return true;
  }
  
  private BigDecimal jdMethod_int(String paramString)
  {
    int i = 0;
    if ((paramString == null) || (paramString.length() == 0)) {
      return null;
    }
    for (int j = 0; j < paramString.length(); j++)
    {
      char c = paramString.charAt(j);
      if (c == '.')
      {
        i++;
        if (i > 1) {
          return null;
        }
      }
      else if (!Character.isDigit(c))
      {
        return null;
      }
    }
    BigDecimal localBigDecimal = null;
    try
    {
      localBigDecimal = new BigDecimal(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return localBigDecimal;
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, ArrayList paramArrayList, HashMap paramHashMap1, HashMap paramHashMap2, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, StringBuffer paramStringBuffer)
    throws CSILException
  {
    boolean bool = true;
    String str1 = (String)paramArrayList.get(0) + " (" + (String)paramArrayList.get(1) + ")";
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    BigDecimal localBigDecimal1 = null;
    BigDecimal localBigDecimal2 = null;
    Limit localLimit1 = null;
    Limit localLimit2 = null;
    if (paramHashMap2.get(str1) != null)
    {
      localLimit2 = (Limit)paramHashMap2.get(str1);
      localBigDecimal2 = localLimit2.getAmount();
    }
    if (paramHashMap1.get(str1) != null)
    {
      localLimit1 = (Limit)paramHashMap1.get(str1);
      localBigDecimal1 = localLimit1.getAmount();
    }
    Object localObject1;
    Object localObject2;
    if ((paramBigDecimal2 != null) && (localBigDecimal2 != null) && (jdMethod_for(localSecureUser, localLimit2, localBigDecimal2, paramBigDecimal2)))
    {
      localObject1 = new Object[1];
      localObject1[0] = ((String)paramArrayList.get(1));
      localObject2 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_daily", (Object[])localObject1).localize(localSecureUser.getLocale());
      jdMethod_for(paramHttpSession, localLimit2, localBigDecimal2, (String)localObject2, (String)localObject2, paramStringBuffer);
      bool = false;
    }
    if (paramBigDecimal1 != null)
    {
      localObject1 = null;
      localObject2 = null;
      String str2 = null;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = ((String)paramArrayList.get(1));
      String str3 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_per_batch", arrayOfObject).localize(localSecureUser.getLocale());
      if ((localBigDecimal1 != null) && (jdMethod_for(localSecureUser, localLimit1, localBigDecimal1, paramBigDecimal1)))
      {
        localObject1 = localLimit1;
        localObject2 = localBigDecimal1;
        arrayOfObject = new Object[1];
        arrayOfObject[0] = ((String)paramArrayList.get(1));
        str2 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_per_batch", arrayOfObject).localize(localSecureUser.getLocale());
      }
      if ((localBigDecimal2 != null) && (jdMethod_for(localSecureUser, localLimit2, localBigDecimal2, paramBigDecimal1)) && ((localObject1 == null) || (jdMethod_for(localSecureUser, localLimit2, localBigDecimal2, (BigDecimal)localObject2))))
      {
        localObject1 = localLimit2;
        localObject2 = localBigDecimal2;
        arrayOfObject = new Object[1];
        arrayOfObject[0] = ((String)paramArrayList.get(1));
        str2 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_daily", arrayOfObject).localize(localSecureUser.getLocale());
      }
      if (localObject1 != null)
      {
        jdMethod_for(paramHttpSession, (Limit)localObject1, (BigDecimal)localObject2, str2, str3, paramStringBuffer);
        bool = false;
      }
    }
    return bool;
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, Limit paramLimit, BigDecimal paramBigDecimal, String paramString1, String paramString2, StringBuffer paramStringBuffer)
    throws CSILException
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    NumberFormat localNumberFormat = NumberFormat.getCurrencyInstance(localSecureUser.getLocale());
    EntitlementGroup localEntitlementGroup = Entitlements.getEntitlementGroup(paramLimit.getGroupId());
    String str = null;
    if ((paramLimit.getObjectType() != null) && (paramLimit.getObjectType().equals("ACHCompany"))) {
      str = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_ACH_company", null).localize(localSecureUser.getLocale());
    } else {
      str = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_cross_ACH", null).localize(localSecureUser.getLocale());
    }
    localNumberFormat.setCurrency(java.util.Currency.getInstance(paramLimit.getCurrencyCode()));
    Object[] arrayOfObject;
    if ((localEntitlementGroup.getEntGroupType() != null) && (localEntitlementGroup.getEntGroupType().equals("BusinessAdmin")))
    {
      arrayOfObject = new Object[4];
      arrayOfObject[0] = paramString2.toLowerCase();
      arrayOfObject[1] = paramString1.toLowerCase();
      arrayOfObject[2] = str;
      arrayOfObject[3] = localNumberFormat.format(paramBigDecimal.doubleValue());
      paramStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "ACH_control_parent_bank", arrayOfObject).localize(localSecureUser.getLocale()));
    }
    else if (paramLimit.getMemberType() != null)
    {
      arrayOfObject = new Object[4];
      arrayOfObject[0] = paramString2.toLowerCase();
      arrayOfObject[1] = paramString1.toLowerCase();
      arrayOfObject[2] = str;
      arrayOfObject[3] = localNumberFormat.format(paramBigDecimal.doubleValue());
      paramStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "ACH_control_parent_user", arrayOfObject).localize(localSecureUser.getLocale()));
    }
    else
    {
      arrayOfObject = new Object[6];
      arrayOfObject[0] = paramString2.toLowerCase();
      arrayOfObject[1] = paramString1.toLowerCase();
      arrayOfObject[2] = str;
      arrayOfObject[3] = localEntitlementGroup.getEntGroupType().toLowerCase();
      arrayOfObject[4] = localEntitlementGroup.getGroupName();
      arrayOfObject[5] = localNumberFormat.format(paramBigDecimal.doubleValue());
      paramStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "ACH_control_parent_group", arrayOfObject).localize(localSecureUser.getLocale()));
    }
    paramStringBuffer.append("<br>");
  }
  
  private boolean jdMethod_for(HttpSession paramHttpSession, int paramInt, EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits, ArrayList paramArrayList, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, StringBuffer paramStringBuffer)
    throws CSILException
  {
    boolean bool = true;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject5 = null;
    Object localObject6 = null;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    ArrayList localArrayList = (ArrayList)paramArrayList.get(2);
    String str1 = (String)paramArrayList.get(1);
    Object localObject7;
    Object localObject8;
    String str3;
    Object localObject9;
    Object localObject10;
    if ((paramInt == this.bW) && ((paramEntitlementGroupMember != null) || (this.bO == null))) {
      for (int i = 0; i < localArrayList.size(); i++)
      {
        localObject7 = (String)localArrayList.get(i);
        localObject8 = (String)paramHttpSession.getAttribute("perBatch" + jdMethod_for(str1, (String)localObject7));
        str3 = (String)paramHttpSession.getAttribute("daily" + jdMethod_for(str1, (String)localObject7));
        localObject9 = jdMethod_int((String)localObject8);
        localObject10 = jdMethod_int(str3);
        if ((localObject9 != null) && ((localObject3 == null) || (((BigDecimal)localObject3).compareTo((BigDecimal)localObject9) > 0)))
        {
          localObject1 = localObject7;
          localObject3 = localObject9;
        }
        if ((localObject10 != null) && ((localObject5 == null) || (((BigDecimal)localObject5).compareTo((BigDecimal)localObject10) > 0)))
        {
          localObject2 = localObject7;
          localObject5 = localObject10;
        }
        if ((this.bR != null) && (!this.bR.equals(""))) {
          for (int j = 0; j < paramLimits.size(); j++)
          {
            Limit localLimit = (Limit)paramLimits.get(j);
            if ((localLimit.getLimitName().equals((String)localObject7 + " (" + str1 + ")")) && (localLimit.getObjectType() == null)) {
              switch (localLimit.getPeriod())
              {
              case 1: 
                if ((localObject3 == null) || (((BigDecimal)localObject3).compareTo(localLimit.getAmount()) > 0))
                {
                  localObject1 = localObject7;
                  localObject3 = localLimit.getAmount();
                  localObject4 = localLimit;
                }
                break;
              case 2: 
                if ((localObject5 == null) || (((BigDecimal)localObject5).compareTo(localLimit.getAmount()) > 0))
                {
                  localObject2 = localObject7;
                  localObject5 = localLimit.getAmount();
                  localObject6 = localLimit;
                }
                break;
              }
            }
          }
        }
      }
    }
    String str2 = null;
    if (localObject6 != null) {
      str2 = localObject6.getCurrencyCode();
    }
    if ((localObject5 != null) && (paramBigDecimal2 != null) && (jdMethod_for(localSecureUser, localObject6, (BigDecimal)localObject5, paramBigDecimal2)))
    {
      localObject7 = new Object[1];
      localObject7[0] = ((String)paramArrayList.get(1));
      localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_daily", (Object[])localObject7).localize(localSecureUser.getLocale());
      jdMethod_for(paramHttpSession, paramInt, paramEntitlementGroupMember, localObject2, (BigDecimal)localObject5, (String)localObject8, (String)localObject8, str2, paramStringBuffer);
      bool = false;
    }
    else if ((localObject5 != null) && (paramBigDecimal2 != null) && (localObject6 == null) && (((BigDecimal)localObject5).compareTo(paramBigDecimal2) < 0))
    {
      localObject7 = new Object[1];
      localObject7[0] = ((String)paramArrayList.get(1));
      localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_daily", (Object[])localObject7).localize(localSecureUser.getLocale());
      jdMethod_for(paramHttpSession, paramInt, paramEntitlementGroupMember, localObject2, (BigDecimal)localObject5, (String)localObject8, (String)localObject8, str2, paramStringBuffer);
      bool = false;
    }
    if (paramBigDecimal1 != null)
    {
      localObject7 = null;
      localObject8 = null;
      str3 = null;
      localObject9 = new Object[1];
      localObject9[0] = ((String)paramArrayList.get(1));
      localObject10 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_per_batch", (Object[])localObject9).localize(localSecureUser.getLocale());
      if ((localObject3 != null) && (jdMethod_for(localSecureUser, localObject4, (BigDecimal)localObject3, paramBigDecimal1)))
      {
        localObject7 = localObject3;
        localObject8 = localObject1;
        localObject9 = new Object[1];
        localObject9[0] = ((String)paramArrayList.get(1));
        str3 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_per_batch", (Object[])localObject9).localize(localSecureUser.getLocale());
      }
      if ((localObject5 != null) && (jdMethod_for(localSecureUser, localObject6, (BigDecimal)localObject5, paramBigDecimal1)) && ((localObject7 == null) || (jdMethod_for(localSecureUser, localObject6, (BigDecimal)localObject5, (BigDecimal)localObject7))))
      {
        localObject7 = localObject5;
        localObject8 = localObject2;
        localObject9 = new Object[1];
        localObject9[0] = ((String)paramArrayList.get(1));
        str3 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_with_daily", (Object[])localObject9).localize(localSecureUser.getLocale());
      }
      if (localObject7 != null)
      {
        jdMethod_for(paramHttpSession, paramInt, paramEntitlementGroupMember, (String)localObject8, (BigDecimal)localObject7, str3, (String)localObject10, str2, paramStringBuffer);
        bool = false;
      }
    }
    if (paramEntitlementGroupMember == null)
    {
      localObject7 = Entitlements.getEntitlementGroup(paramInt);
      if (((EntitlementGroup)localObject7).getParentId() != 0)
      {
        localObject8 = Entitlements.getGroupLimits(((EntitlementGroup)localObject7).getParentId());
        bool = (bool) && (jdMethod_for(paramHttpSession, ((EntitlementGroup)localObject7).getParentId(), null, (Limits)localObject8, paramArrayList, paramBigDecimal1, paramBigDecimal2, paramStringBuffer));
      }
    }
    else
    {
      localObject7 = Entitlements.getGroupLimits(paramInt);
      bool = (bool) && (jdMethod_for(paramHttpSession, paramInt, null, (Limits)localObject7, paramArrayList, paramBigDecimal1, paramBigDecimal2, paramStringBuffer));
    }
    return bool;
  }
  
  private void jdMethod_for(HttpSession paramHttpSession, int paramInt, EntitlementGroupMember paramEntitlementGroupMember, String paramString1, BigDecimal paramBigDecimal, String paramString2, String paramString3, String paramString4, StringBuffer paramStringBuffer)
    throws CSILException
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    NumberFormat localNumberFormat = NumberFormat.getCurrencyInstance(localSecureUser.getLocale());
    if (paramString4 == null) {
      localNumberFormat.setCurrency(java.util.Currency.getInstance(Util.getLimitBaseCurrency()));
    } else {
      localNumberFormat.setCurrency(java.util.Currency.getInstance(paramString4));
    }
    Object localObject;
    if (paramEntitlementGroupMember == null)
    {
      localObject = Entitlements.getEntitlementGroup(paramInt);
      Object[] arrayOfObject;
      if (((EntitlementGroup)localObject).getEntGroupType().equals("Business"))
      {
        arrayOfObject = new Object[6];
        arrayOfObject[0] = paramString3.toLowerCase();
        arrayOfObject[1] = localNumberFormat.format(paramBigDecimal.doubleValue());
        arrayOfObject[2] = paramString2.toLowerCase();
        arrayOfObject[3] = paramString1;
        arrayOfObject[4] = ((EntitlementGroup)localObject).getGroupName();
        arrayOfObject[5] = new LocalizableString("com.ffusion.tasks.resources", "business", null);
        paramStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_group", arrayOfObject).localize(localSecureUser.getLocale()));
      }
      else if (((EntitlementGroup)localObject).getEntGroupType().equals("Division"))
      {
        arrayOfObject = new Object[6];
        arrayOfObject[0] = paramString3.toLowerCase();
        arrayOfObject[1] = localNumberFormat.format(paramBigDecimal.doubleValue());
        arrayOfObject[2] = paramString2.toLowerCase();
        arrayOfObject[3] = paramString1;
        arrayOfObject[4] = ((EntitlementGroup)localObject).getGroupName();
        arrayOfObject[5] = new LocalizableString("com.ffusion.tasks.resources", "division", null);
        paramStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_group", arrayOfObject).localize(localSecureUser.getLocale()));
      }
      else if (((EntitlementGroup)localObject).getEntGroupType().equals("Group"))
      {
        arrayOfObject = new Object[6];
        arrayOfObject[0] = paramString3.toLowerCase();
        arrayOfObject[1] = localNumberFormat.format(paramBigDecimal.doubleValue());
        arrayOfObject[2] = paramString2.toLowerCase();
        arrayOfObject[3] = paramString1;
        arrayOfObject[4] = ((EntitlementGroup)localObject).getGroupName();
        arrayOfObject[5] = new LocalizableString("com.ffusion.tasks.resources", "group", null);
        paramStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_group", arrayOfObject).localize(localSecureUser.getLocale()));
      }
      else
      {
        arrayOfObject = new Object[4];
        arrayOfObject[0] = paramString3.toLowerCase();
        arrayOfObject[1] = localNumberFormat.format(paramBigDecimal.doubleValue());
        arrayOfObject[2] = paramString2.toLowerCase();
        arrayOfObject[3] = paramString1;
        paramStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_bank", arrayOfObject).localize(localSecureUser.getLocale()));
      }
    }
    else
    {
      localObject = new Object[4];
      localObject[0] = paramString3.toLowerCase();
      localObject[1] = localNumberFormat.format(paramBigDecimal.doubleValue());
      localObject[2] = paramString2.toLowerCase();
      localObject[3] = paramString1;
      paramStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_user", (Object[])localObject).localize(localSecureUser.getLocale()));
    }
    paramStringBuffer.append("<br>");
  }
  
  public void setBadLimitURL(String paramString)
  {
    this.cc = paramString;
  }
  
  public void setPerTransactionMapName(String paramString)
  {
    this.bU = paramString;
  }
  
  public String getPerTransactionMapName()
  {
    return this.bU;
  }
  
  public void setPerDayMapName(String paramString)
  {
    this.cj = paramString;
  }
  
  public String getPerDayMapName()
  {
    return this.cj;
  }
  
  public void setLimitInfoListSessionName(String paramString)
  {
    this.bL = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.bW = Integer.parseInt(paramString);
  }
  
  public void setMemberId(String paramString)
  {
    this.bO = paramString;
  }
  
  public void setMemberType(String paramString)
  {
    this.ce = paramString;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.ca = paramString;
  }
  
  public void setACHCompanyID(String paramString)
  {
    this.bR = paramString;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.bX = paramString;
  }
  
  public void setCrossCurrency(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.cg = true;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.cg = false;
    }
  }
  
  private boolean jdMethod_int(Limit paramLimit)
  {
    if (paramLimit == null) {
      return true;
    }
    if (((!paramLimit.isCrossCurrency()) && (this.cg)) || ((paramLimit.isCrossCurrency()) && (!this.cg))) {
      return false;
    }
    if (paramLimit.isCrossCurrency()) {
      return true;
    }
    return paramLimit.getCurrencyCode().equals(this.bX);
  }
  
  private boolean jdMethod_for(SecureUser paramSecureUser, Limit paramLimit, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
  {
    if (paramLimit == null) {
      return paramBigDecimal1.compareTo(paramBigDecimal2) < 0;
    }
    if (paramLimit.getCurrencyCode().equals(this.bX)) {
      return paramLimit.getAmount().compareTo(paramBigDecimal2) < 0;
    }
    if (!jdMethod_int(paramLimit)) {
      return false;
    }
    BigDecimal localBigDecimal1 = paramLimit.getAmount();
    try
    {
      FXRate localFXRate = FX.getFXRate(paramSecureUser, this.bX, paramLimit.getCurrencyCode(), new HashMap());
      BigDecimal localBigDecimal2 = localFXRate.getBuyPrice().getAmountValue();
      paramBigDecimal2 = paramBigDecimal2.multiply(localBigDecimal2);
    }
    catch (Exception localException) {}
    return localBigDecimal1.compareTo(paramBigDecimal2) < 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckForRedundantACHLimits
 * JD-Core Version:    0.7.0.1
 */