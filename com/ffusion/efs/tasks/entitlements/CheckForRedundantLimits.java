package com.ffusion.efs.tasks.entitlements;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.util.LastRequest;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.FX;
import com.ffusion.csil.core.Util;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckForRedundantLimits
  extends BaseTask
  implements Task
{
  public static final String ENTITLEMENT = "entitlement";
  public static final String TRANSACTION_LIMIT = "transaction_limit";
  public static final String TRANSACTION_EXCEED = "transaction_exceed";
  public static final String DAY_LIMIT = "day_limit";
  public static final String DAY_EXCEED = "day_exceed";
  public static final String WEEK_LIMIT = "week_limit";
  public static final String WEEK_EXCEED = "week_exceed";
  public static final String MONTH_LIMIT = "month_limit";
  public static final String MONTH_EXCEED = "month_exceed";
  public static final String PREFIX_ACH = "ach";
  public static final String PREFIX_TAX = "tax";
  private static final String bv = "limit_error";
  private static final String bt = "period_";
  private static final String[] bx = { "transaction", "day", "week", "month" };
  private static final String[] bf = { "transaction_limit", "day_limit", "week_limit", "month_limit" };
  private String bD = "entitlement";
  private String a8;
  private String bo;
  private String a3 = "";
  private String bp;
  private String bK;
  private String aX;
  private String bj;
  private String a9;
  private String aY;
  private int a7;
  private String aV;
  private String br;
  private String bn;
  private String aT;
  private String bC;
  private boolean bz = true;
  private String ba = null;
  private static final String bH = "FALSE";
  private static final String bh = "TRUE";
  private static final String bE = "com.ffusion.tasks.resources";
  private static final String a5 = "exceed_with_no_limit";
  private static final String aZ = "exceed_with_no_limit_w_prefix";
  private static final String bB = "not_negative";
  private static final String by = "not_negative_w_prefix";
  private static final String aU = "limit_cant_exceed";
  private static final String a2 = "limit_cant_exceed_w_prefix";
  private static final String be = "period_error_bus_user";
  private static final String bu = "period_error_bus_group";
  private static final String bA = "period_error_bank";
  private static final String bJ = "period_tax_suffix";
  private static final String a0 = "batch";
  private static final String bw = "tax_payment";
  private static final String aW = "limit_type_cross_account";
  private static final String aS = "limit_type_account_access";
  private static final String bq = "limit_type_per_account";
  private static final String bi = "limit_type_per_account_group";
  private static final String a6 = "limit_type_per_location";
  private static final String bk = "limit_type_ACH_company";
  private static final String bG = "limit_type_wire_template";
  private static final String bc = "blank_limit_with_exceed_found";
  private static final String bl = "negative_found";
  private static final String bd = "redundant_found";
  private static final String bm = "error";
  private static final String a1 = "business";
  private static final String bI = "division";
  private static final String bg = "group";
  private static final String bb = "control_parent_group";
  private static final String a4 = "control_parent_bank";
  private static final String bF = "control_parent_user";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localHttpSession.setAttribute("LastRequest", new LastRequest(paramHttpServletRequest));
    if (this.ba == null) {
      this.ba = Util.getLimitBaseCurrency();
    }
    str = jdMethod_for(localSecureUser, localHttpSession, paramHttpServletRequest);
    return str;
  }
  
  private String jdMethod_for(SecureUser paramSecureUser, HttpSession paramHttpSession, HttpServletRequest paramHttpServletRequest)
  {
    String str1 = this.successURL;
    HashMap localHashMap1 = null;
    HashMap localHashMap2 = null;
    HashMap localHashMap3 = null;
    HashMap localHashMap4 = null;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = null;
    ArrayList localArrayList1 = null;
    int i = 0;
    if (this.a8 == null) {
      return this.taskErrorURL;
    }
    if ((this.bp == null) || (this.bK == null) || (this.aX == null) || (this.bj == null))
    {
      this.error = 35039;
      return this.taskErrorURL;
    }
    ArrayList localArrayList2 = (ArrayList)paramHttpSession.getAttribute(this.a8);
    if (localArrayList2 == null) {
      return this.taskErrorURL;
    }
    localHashMap1 = (HashMap)paramHttpSession.getAttribute(this.bp);
    localHashMap2 = (HashMap)paramHttpSession.getAttribute(this.bK);
    localHashMap3 = (HashMap)paramHttpSession.getAttribute(this.aX);
    localHashMap4 = (HashMap)paramHttpSession.getAttribute(this.bj);
    if ((localHashMap1 == null) || (localHashMap2 == null) || (localHashMap3 == null) || (localHashMap4 == null))
    {
      this.error = 35040;
      return this.taskErrorURL;
    }
    if (this.a9 != null) {
      try
      {
        localEntitlementTypePropertyLists1 = (EntitlementTypePropertyLists)paramHttpSession.getAttribute(this.a9);
      }
      catch (Exception localException1)
      {
        this.error = 35041;
        return this.taskErrorURL;
      }
    }
    if (this.aY != null)
    {
      i = 1;
      try
      {
        localArrayList1 = (ArrayList)paramHttpSession.getAttribute(this.aY);
      }
      catch (Exception localException2)
      {
        this.error = 35042;
        return this.taskErrorURL;
      }
    }
    else
    {
      localArrayList1 = new ArrayList();
      localArrayList1.add(null);
    }
    EntitlementGroupMember localEntitlementGroupMember = null;
    if (this.br != null)
    {
      localEntitlementGroupMember = new EntitlementGroupMember();
      localEntitlementGroupMember.setId(this.aV);
      localEntitlementGroupMember.setMemberType(this.br);
      localEntitlementGroupMember.setMemberSubType(this.bn);
      localEntitlementGroupMember.setEntitlementGroupId(this.a7);
    }
    Limits localLimits = null;
    EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = null;
    try
    {
      if (localEntitlementGroupMember == null) {
        localLimits = Entitlements.getGroupLimits(this.a7);
      } else {
        localLimits = Entitlements.getGroupLimits(localEntitlementGroupMember);
      }
      localEntitlementTypePropertyLists2 = Entitlements.getEntitlementTypesWithProperties(new HashMap());
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      str1 = this.serviceErrorURL;
    }
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    ArrayList localArrayList3 = new ArrayList();
    if ((this.a3 == null) || (this.a3.length() == 0))
    {
      localArrayList3.add("");
    }
    else
    {
      localObject1 = new StringTokenizer(this.a3, ",");
      while (((StringTokenizer)localObject1).hasMoreTokens())
      {
        String str2 = ((StringTokenizer)localObject1).nextToken();
        localArrayList3.add(str2);
      }
    }
    Object localObject1 = new HashMap();
    if (localEntitlementTypePropertyLists1 != null) {
      for (i1 = 0; i1 < localEntitlementTypePropertyLists1.size(); i1++) {
        ((HashMap)localObject1).put(((EntitlementTypePropertyList)localEntitlementTypePropertyLists1.get(i1)).getOperationName(), new Integer(i1));
      }
    }
    for (int i1 = 0; i1 < localArrayList1.size(); i1++)
    {
      int i2 = 0;
      int i3 = 0;
      int i4 = localArrayList2.size();
      while (i3 < i4)
      {
        String str3 = i != 0 ? "_" + i1 : "";
        Object localObject2 = localArrayList2.get(i3);
        String str4 = null;
        String str5 = null;
        if ((localObject2 instanceof EntitlementTypePropertyList)) {
          str4 = ((EntitlementTypePropertyList)localObject2).getOperationName();
        } else if ((localObject2 instanceof Account)) {
          str4 = ((Account)localObject2).getID();
        } else if ((localObject2 instanceof BusinessAccountGroup)) {
          str4 = Integer.toString(((BusinessAccountGroup)localObject2).getId());
        }
        if (localEntitlementTypePropertyLists1 != null) {
          for (int i5 = 0; i5 < localEntitlementTypePropertyLists1.size(); i5++)
          {
            EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localEntitlementTypePropertyLists1.get(i5);
            if (str4.equals(localEntitlementTypePropertyList.getOperationName()))
            {
              i2 = i5++;
              break;
            }
          }
        } else {
          i2 = i3;
        }
        str5 = (String)paramHttpSession.getAttribute(this.bD + i2 + str3);
        if (str5 != null)
        {
          StringBuffer localStringBuffer2 = new StringBuffer();
          for (int i6 = 0; i6 < localArrayList3.size(); i6++)
          {
            String str6 = (String)localArrayList3.get(i6);
            String[] arrayOfString1 = { (String)paramHttpSession.getAttribute(str6 + "transaction_limit" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "day_limit" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "week_limit" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "month_limit" + i2 + str3) };
            String[] arrayOfString2 = { (String)paramHttpSession.getAttribute(str6 + "transaction_exceed" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "day_exceed" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "week_exceed" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "month_exceed" + i2 + str3) };
            String[] arrayOfString3 = { (String)paramHttpSession.getAttribute(str6 + "transaction_exceed" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "day_exceed" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "week_exceed" + i2 + str3), (String)paramHttpSession.getAttribute(str6 + "month_exceed" + i2 + str3) };
            BigDecimal[] arrayOfBigDecimal = { new BigDecimal(0.0D), new BigDecimal(0.0D), new BigDecimal(0.0D), new BigDecimal(0.0D) };
            BigDecimal localBigDecimal = null;
            int i7 = 0;
            for (int i8 = 3; i8 >= 0; i8--)
            {
              Object localObject3;
              Object localObject4;
              if (((arrayOfString1[i8] == null) || (arrayOfString1[i8].equals(""))) && (arrayOfString3[i8] != null) && (arrayOfString3[i8].equalsIgnoreCase("true")))
              {
                n = 1;
                str1 = this.bo;
                localObject3 = null;
                if (str6.length() > 0)
                {
                  localObject3 = new Object[2];
                  localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[i8], null);
                  localObject3[1] = str6;
                  localObject4 = new LocalizableString("com.ffusion.tasks.resources", "exceed_with_no_limit_w_prefix", (Object[])localObject3);
                }
                else
                {
                  localObject3 = new Object[1];
                  localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[i8], null);
                  localObject4 = new LocalizableString("com.ffusion.tasks.resources", "exceed_with_no_limit", (Object[])localObject3);
                }
                localStringBuffer2.append(((ILocalizable)localObject4).localize(paramSecureUser.getLocale())).append("<br>");
              }
              else if ((arrayOfString1[i8] != null) && (arrayOfString1[i8].length() > 0))
              {
                try
                {
                  arrayOfBigDecimal[i8] = new BigDecimal(arrayOfString1[i8]);
                  if (arrayOfBigDecimal[i8].signum() < 0)
                  {
                    k = 1;
                    str1 = this.bo;
                    localObject3 = null;
                    if (str6.length() > 0)
                    {
                      localObject3 = new Object[2];
                      localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[i8], null);
                      localObject3[1] = str6;
                      localObject4 = new LocalizableString("com.ffusion.tasks.resources", "not_negative_w_prefix", (Object[])localObject3);
                    }
                    else
                    {
                      localObject3 = new Object[1];
                      localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[i8], null);
                      localObject4 = new LocalizableString("com.ffusion.tasks.resources", "not_negative", (Object[])localObject3);
                    }
                    localStringBuffer2.append(((ILocalizable)localObject4).localize(paramSecureUser.getLocale())).append("<br>");
                  }
                  else if ((localBigDecimal != null) && (arrayOfBigDecimal[i8].compareTo(localBigDecimal) > 0))
                  {
                    localObject3 = null;
                    if (str6.length() > 0)
                    {
                      localObject3 = new Object[4];
                      localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[i8], null);
                      localObject3[1] = str6;
                      localObject3[2] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[i7], null);
                      localObject3[3] = str6;
                      localObject4 = new LocalizableString("com.ffusion.tasks.resources", "limit_cant_exceed_w_prefix", (Object[])localObject3);
                    }
                    else
                    {
                      localObject3 = new Object[2];
                      localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[i8], null);
                      localObject3[1] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[i7], null);
                      localObject4 = new LocalizableString("com.ffusion.tasks.resources", "limit_cant_exceed", (Object[])localObject3);
                    }
                    localStringBuffer2.append(((ILocalizable)localObject4).localize(paramSecureUser.getLocale())).append("<br>");
                    j = 1;
                    str1 = this.bo;
                  }
                  else
                  {
                    localBigDecimal = arrayOfBigDecimal[i8];
                    i7 = i8;
                    localObject3 = null;
                    localObject4 = null;
                    if ((str6.equals("ach")) || (str6.equals("tax")) || (i != 0))
                    {
                      localObject5 = new Entitlement();
                      if ((str6.equals("ach")) || (str6.equals("tax")))
                      {
                        ((Entitlement)localObject5).setObjectType("ACHCompany");
                        ((Entitlement)localObject5).setObjectId(str5);
                        if (str6.equals("ach")) {
                          ((Entitlement)localObject5).setOperationName("ACHBatch");
                        } else if (str6.equals("tax")) {
                          ((Entitlement)localObject5).setOperationName("Tax Payments");
                        }
                        switch (i8)
                        {
                        case 0: 
                          localObject4 = (Limit)localHashMap1.get(localObject5);
                          if (jdMethod_for((Limit)localObject4)) {
                            localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                          }
                        case 1: 
                          localObject4 = (Limit)localHashMap2.get(localObject5);
                          if (jdMethod_for((Limit)localObject4)) {
                            localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                          }
                        case 2: 
                          localObject4 = (Limit)localHashMap3.get(localObject5);
                          if (jdMethod_for((Limit)localObject4)) {
                            localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                          }
                        case 3: 
                          localObject4 = (Limit)localHashMap4.get(localObject5);
                          if (!jdMethod_for((Limit)localObject4)) {
                            break label2635;
                          }
                          localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                          break;
                        }
                      }
                      else if (i != 0)
                      {
                        ((Entitlement)localObject5).setObjectType("Wire Template");
                        ((Entitlement)localObject5).setObjectId((String)localArrayList1.get(i1));
                        ((Entitlement)localObject5).setOperationName(str5);
                        switch (i8)
                        {
                        case 0: 
                          localObject4 = (Limit)localHashMap1.get(((Entitlement)localObject5).toString());
                          if (jdMethod_for((Limit)localObject4)) {
                            localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                          }
                        case 1: 
                          localObject4 = (Limit)localHashMap2.get(((Entitlement)localObject5).toString());
                          if (jdMethod_for((Limit)localObject4)) {
                            localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                          }
                        case 2: 
                          localObject4 = (Limit)localHashMap3.get(((Entitlement)localObject5).toString());
                          if (jdMethod_for((Limit)localObject4)) {
                            localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                          }
                        case 3: 
                          localObject4 = (Limit)localHashMap4.get(((Entitlement)localObject5).toString());
                          if (jdMethod_for((Limit)localObject4)) {
                            localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                          }
                          break;
                        }
                      }
                    }
                    else
                    {
                      label2635:
                      switch (i8)
                      {
                      case 0: 
                        localObject4 = (Limit)localHashMap1.get(str4);
                        if (jdMethod_for((Limit)localObject4)) {
                          localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                        }
                      case 1: 
                        localObject4 = (Limit)localHashMap2.get(str4);
                        if (jdMethod_for((Limit)localObject4)) {
                          localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                        }
                      case 2: 
                        localObject4 = (Limit)localHashMap3.get(str4);
                        if (jdMethod_for((Limit)localObject4)) {
                          localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                        }
                      case 3: 
                        localObject4 = (Limit)localHashMap4.get(str4);
                        if (jdMethod_for((Limit)localObject4)) {
                          localObject3 = jdMethod_for(paramSecureUser, (Limit)localObject3, (Limit)localObject4);
                        }
                        break;
                      }
                    }
                    Object localObject5 = null;
                    Object localObject7;
                    Object localObject8;
                    Object localObject9;
                    Object localObject10;
                    if ((localObject3 != null) && (((Limit)localObject3).getAmount() != null))
                    {
                      localObject6 = new StringBuffer();
                      String str7 = null;
                      localObject7 = null;
                      localObject8 = null;
                      localObject9 = ((Limit)localObject3).getEntitlement().getOperationName();
                      localObject10 = ((Limit)localObject3).getEntitlement().getObjectType();
                      String str8 = ((Limit)localObject3).getEntitlement().getObjectId();
                      EntitlementGroup localEntitlementGroup = null;
                      NumberFormat localNumberFormat = NumberFormat.getCurrencyInstance(paramSecureUser.getLocale());
                      try
                      {
                        localEntitlementGroup = Entitlements.getEntitlementGroup(((Limit)localObject3).getGroupId());
                      }
                      catch (CSILException localCSILException3)
                      {
                        this.error = localCSILException3.getCode();
                        return this.serviceErrorURL;
                      }
                      Object[] arrayOfObject;
                      switch (i8)
                      {
                      case 0: 
                        if (str6.equals("ach")) {
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_batch", null).localize(paramSecureUser.getLocale());
                        } else if (str6.equals("tax")) {
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_tax_payment", null).localize(paramSecureUser.getLocale());
                        } else {
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[0], null).localize(paramSecureUser.getLocale());
                        }
                        break;
                      case 1: 
                        if (str6.equals("tax"))
                        {
                          arrayOfObject = new Object[1];
                          arrayOfObject[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[1], null);
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_tax_suffix", arrayOfObject).localize(paramSecureUser.getLocale());
                        }
                        else
                        {
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[1], null).localize(paramSecureUser.getLocale());
                        }
                        break;
                      case 2: 
                        if (str6.equals("tax"))
                        {
                          arrayOfObject = new Object[1];
                          arrayOfObject[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[2], null);
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_tax_suffix", arrayOfObject).localize(paramSecureUser.getLocale());
                        }
                        else
                        {
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[2], null).localize(paramSecureUser.getLocale());
                        }
                        break;
                      case 3: 
                        if (str6.equals("tax"))
                        {
                          arrayOfObject = new Object[1];
                          arrayOfObject[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[3], null);
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_tax_suffix", arrayOfObject).localize(paramSecureUser.getLocale());
                        }
                        else
                        {
                          str7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[3], null).localize(paramSecureUser.getLocale());
                        }
                        break;
                      default: 
                        str7 = null;
                      }
                      switch (((Limit)localObject3).getPeriod())
                      {
                      case 1: 
                        if (("ACHBatch".equals(localObject9)) && ("ACHCompany".equals(localObject10))) {
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_batch", null).localize(paramSecureUser.getLocale());
                        } else if (("Tax Payments".equals(localObject9)) && ("ACHCompany".equals(localObject10))) {
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_tax_payment", null).localize(paramSecureUser.getLocale());
                        } else {
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[0], null).localize(paramSecureUser.getLocale());
                        }
                        break;
                      case 2: 
                        if (("Tax Payments".equals(localObject9)) && ("ACHCompany".equals(localObject10)))
                        {
                          arrayOfObject = new Object[1];
                          arrayOfObject[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[1], null);
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_tax_suffix", arrayOfObject).localize(paramSecureUser.getLocale());
                        }
                        else
                        {
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[1], null).localize(paramSecureUser.getLocale());
                        }
                        break;
                      case 3: 
                        if (("Tax Payments".equals(localObject9)) && ("ACHCompany".equals(localObject10)))
                        {
                          arrayOfObject = new Object[1];
                          arrayOfObject[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[2], null);
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_tax_suffix", arrayOfObject).localize(paramSecureUser.getLocale());
                        }
                        else
                        {
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[2], null).localize(paramSecureUser.getLocale());
                        }
                        break;
                      case 4: 
                        if (("Tax Payments".equals(localObject9)) && ("ACHCompany".equals(localObject10)))
                        {
                          arrayOfObject = new Object[1];
                          arrayOfObject[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[3], null);
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_tax_suffix", arrayOfObject).localize(paramSecureUser.getLocale());
                        }
                        else
                        {
                          localObject7 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[3], null).localize(paramSecureUser.getLocale());
                        }
                        break;
                      default: 
                        localObject7 = null;
                      }
                      if ((localObject10 == null) && (str8 == null)) {
                        localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_cross_account", null).localize(paramSecureUser.getLocale());
                      } else if ((localObject9 == null) && ("Account".equals(localObject10)) && (str8 != null)) {
                        localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_account_access", null).localize(paramSecureUser.getLocale());
                      } else if ((localObject9 != null) && ("Account".equals(localObject10)) && (str8 != null)) {
                        localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_per_account", null).localize(paramSecureUser.getLocale());
                      } else if ((localObject9 != null) && ("AccountGroup".equals(localObject10)) && (str8 != null)) {
                        localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_per_account_group", null).localize(paramSecureUser.getLocale());
                      } else if ((localObject9 != null) && ("Location".equals(localObject10)) && (str8 != null)) {
                        localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_per_location", null).localize(paramSecureUser.getLocale());
                      } else if ((localObject9 != null) && ("ACHCompany".equals(localObject10)) && (str8 != null)) {
                        localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_ACH_company", null).localize(paramSecureUser.getLocale());
                      } else if ((localObject9 != null) && ("Wire Template".equals(localObject10)) && (str8 != null)) {
                        localObject8 = (String)new LocalizableString("com.ffusion.tasks.resources", "limit_type_wire_template", null).localize(paramSecureUser.getLocale());
                      } else {
                        localObject8 = "";
                      }
                      if ((localEntitlementGroup.getEntGroupType() != null) && (localEntitlementGroup.getEntGroupType().equals("BusinessAdmin")))
                      {
                        arrayOfObject = new Object[4];
                        arrayOfObject[0] = str7;
                        arrayOfObject[1] = localObject7;
                        arrayOfObject[2] = localObject8;
                        arrayOfObject[3] = localNumberFormat.format(((Limit)localObject3).getAmount().doubleValue());
                        ((StringBuffer)localObject6).append(new LocalizableString("com.ffusion.tasks.resources", "period_error_bank", arrayOfObject).localize(paramSecureUser.getLocale()));
                      }
                      else if (((Limit)localObject3).getMemberType() != null)
                      {
                        arrayOfObject = new Object[5];
                        arrayOfObject[0] = str7;
                        arrayOfObject[1] = localObject7;
                        arrayOfObject[2] = localObject8;
                        arrayOfObject[3] = localNumberFormat.format(((Limit)localObject3).getAmount().doubleValue());
                        ((StringBuffer)localObject6).append(new LocalizableString("com.ffusion.tasks.resources", "period_error_bus_user", arrayOfObject).localize(paramSecureUser.getLocale()));
                      }
                      else
                      {
                        arrayOfObject = new Object[6];
                        arrayOfObject[0] = str7;
                        arrayOfObject[1] = localObject7;
                        arrayOfObject[2] = localObject8;
                        arrayOfObject[3] = localEntitlementGroup.getEntGroupType().toLowerCase();
                        arrayOfObject[4] = localEntitlementGroup.getGroupName();
                        arrayOfObject[5] = localNumberFormat.format(((Limit)localObject3).getAmount().doubleValue());
                        ((StringBuffer)localObject6).append(new LocalizableString("com.ffusion.tasks.resources", "period_error_bus_group", arrayOfObject).localize(paramSecureUser.getLocale()));
                      }
                      ((StringBuffer)localObject6).append("<br>");
                      localObject5 = new a(((Limit)localObject3).getAmount(), ((StringBuffer)localObject6).toString(), ((Limit)localObject3).getCurrencyCode(), ((Limit)localObject3).getPeriod() - 1);
                    }
                    Object localObject6 = null;
                    if ((localObject2 instanceof EntitlementTypePropertyList)) {
                      try
                      {
                        localObject6 = jdMethod_for(paramHttpSession, paramSecureUser, NumberFormat.getCurrencyInstance(paramSecureUser.getLocale()), this.a7, localEntitlementGroupMember, localLimits, localEntitlementTypePropertyLists2, (EntitlementTypePropertyList)localObject2, (HashMap)localObject1, i8, str6, str3);
                      }
                      catch (CSILException localCSILException2)
                      {
                        this.error = MapError.mapError(localCSILException2);
                        str1 = this.serviceErrorURL;
                      }
                    }
                    a locala = jdMethod_for(paramSecureUser, (a)localObject5, (a)localObject6);
                    if ((locala != null) && (locala.jdMethod_do() != null)) {
                      if (locala.a().equals(this.ba))
                      {
                        if (locala.jdMethod_do().compareTo(new BigDecimal(arrayOfString1[i8])) < 0)
                        {
                          str1 = this.bo;
                          j = 1;
                          localStringBuffer2.append(locala.jdMethod_if());
                        }
                      }
                      else
                      {
                        localObject7 = locala.jdMethod_do();
                        localObject8 = new BigDecimal(arrayOfString1[i8]);
                        if (locala.jdMethod_for() < i8)
                        {
                          localObject9 = FX.getFXRate(paramSecureUser, locala.a(), this.ba, new HashMap());
                          localObject10 = ((FXRate)localObject9).getBuyPrice().getAmountValue();
                          localObject7 = ((BigDecimal)localObject7).multiply((BigDecimal)localObject10);
                        }
                        else
                        {
                          localObject9 = FX.getFXRate(paramSecureUser, this.ba, locala.a(), new HashMap());
                          localObject10 = ((FXRate)localObject9).getBuyPrice().getAmountValue();
                          localObject8 = ((BigDecimal)localObject8).multiply((BigDecimal)localObject10);
                        }
                        if (((BigDecimal)localObject7).compareTo((BigDecimal)localObject8) < 0)
                        {
                          str1 = this.bo;
                          j = 1;
                          localStringBuffer2.append(locala.jdMethod_if());
                        }
                      }
                    }
                  }
                }
                catch (Exception localException3) {}
              }
            }
          }
          if (localStringBuffer2.length() > 0) {
            paramHttpSession.setAttribute("limit_error" + i2 + str3, localStringBuffer2.toString());
          }
        }
        i3++;
      }
    }
    if ((n != 0) || (k != 0) || (j != 0))
    {
      StringBuffer localStringBuffer1 = new StringBuffer((String)new LocalizableString("com.ffusion.tasks.resources", "error", null).localize(paramSecureUser.getLocale()));
      if (n != 0) {
        localStringBuffer1.append((String)new LocalizableString("com.ffusion.tasks.resources", "blank_limit_with_exceed_found", null).localize(paramSecureUser.getLocale()));
      }
      if (k != 0) {
        localStringBuffer1.append((String)new LocalizableString("com.ffusion.tasks.resources", "negative_found", null).localize(paramSecureUser.getLocale()));
      }
      if (j != 0) {
        localStringBuffer1.append((String)new LocalizableString("com.ffusion.tasks.resources", "redundant_found", null).localize(paramSecureUser.getLocale()));
      }
      paramHttpSession.setAttribute("limit_error", localStringBuffer1.toString());
    }
    return str1;
  }
  
  private a jdMethod_for(HttpSession paramHttpSession, SecureUser paramSecureUser, NumberFormat paramNumberFormat, int paramInt1, EntitlementGroupMember paramEntitlementGroupMember, Limits paramLimits, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, EntitlementTypePropertyList paramEntitlementTypePropertyList, HashMap paramHashMap, int paramInt2, String paramString1, String paramString2)
    throws CSILException
  {
    if (paramInt1 == 0) {
      return null;
    }
    if (!paramEntitlementTypePropertyList.isPropertySet("control parent")) {
      return null;
    }
    a locala = null;
    int i = paramEntitlementTypePropertyList.numPropertyValues("control parent");
    for (int j = 0; j < i; j++)
    {
      String str1 = paramEntitlementTypePropertyList.getPropertyValue("control parent", j);
      EntitlementTypePropertyList localEntitlementTypePropertyList = paramEntitlementTypePropertyLists.getByOperationName(str1);
      if ((paramHashMap.keySet().contains(str1)) && (paramInt1 == this.a7) && ((paramEntitlementGroupMember != null) || (this.aV == null)))
      {
        int k = ((Integer)paramHashMap.get(str1)).intValue();
        Object localObject1 = null;
        String str2 = null;
        int m = 0;
        for (int n = paramInt2; n < bf.length; n++)
        {
          String str3 = (String)paramHttpSession.getAttribute(paramString1 + bf[n] + k + paramString2);
          BigDecimal localBigDecimal = null;
          if ((str3 != null) && (str3.length() > 0)) {
            localBigDecimal = new BigDecimal(str3);
          }
          if ((localObject1 == null) || ((localBigDecimal != null) && (localObject1.compareTo(localBigDecimal) > 0)))
          {
            localObject1 = localBigDecimal;
            str2 = (String)new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[n], null).localize(paramSecureUser.getLocale());
            m = n;
          }
        }
        StringBuffer localStringBuffer = new StringBuffer();
        double d = localObject1 == null ? 1.7976931348623157E+308D : localObject1.doubleValue();
        paramNumberFormat.setCurrency(java.util.Currency.getInstance(this.ba));
        if (paramEntitlementGroupMember == null)
        {
          localObject2 = Entitlements.getEntitlementGroup(paramInt1);
          if (((EntitlementGroup)localObject2).getEntGroupType().equals("Business"))
          {
            localObject3 = new Object[6];
            localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[paramInt2], null);
            localObject3[1] = paramNumberFormat.format(d);
            localObject3[2] = str2;
            localObject3[3] = localEntitlementTypePropertyList.getPropertyValue("display name", 0);
            localObject3[4] = ((EntitlementGroup)localObject2).getGroupName();
            localObject3[5] = new LocalizableString("com.ffusion.tasks.resources", "business", null);
            localStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_group", (Object[])localObject3).localize(paramSecureUser.getLocale()));
          }
          else if (((EntitlementGroup)localObject2).getEntGroupType().equals("Division"))
          {
            localObject3 = new Object[6];
            localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[paramInt2], null);
            localObject3[1] = paramNumberFormat.format(d);
            localObject3[2] = str2;
            localObject3[3] = localEntitlementTypePropertyList.getPropertyValue("display name", 0);
            localObject3[4] = ((EntitlementGroup)localObject2).getGroupName();
            localObject3[5] = new LocalizableString("com.ffusion.tasks.resources", "division", null);
            localStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_group", (Object[])localObject3).localize(paramSecureUser.getLocale()));
          }
          else if (((EntitlementGroup)localObject2).getEntGroupType().equals("Group"))
          {
            localObject3 = new Object[6];
            localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[paramInt2], null);
            localObject3[1] = paramNumberFormat.format(d);
            localObject3[2] = str2;
            localObject3[3] = localEntitlementTypePropertyList.getPropertyValue("display name", 0);
            localObject3[4] = ((EntitlementGroup)localObject2).getGroupName();
            localObject3[5] = new LocalizableString("com.ffusion.tasks.resources", "group", null);
            localStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_group", (Object[])localObject3).localize(paramSecureUser.getLocale()));
          }
          else
          {
            localObject3 = new Object[4];
            localObject3[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[paramInt2], null);
            localObject3[1] = paramNumberFormat.format(d);
            localObject3[2] = str2;
            localObject3[3] = localEntitlementTypePropertyList.getPropertyValue("display name", 0);
            localStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_bank", (Object[])localObject3).localize(paramSecureUser.getLocale()));
          }
        }
        else
        {
          localObject2 = new Object[4];
          localObject2[0] = new LocalizableString("com.ffusion.tasks.resources", "period_" + bx[paramInt2], null);
          localObject2[1] = paramNumberFormat.format(d);
          localObject2[2] = str2;
          localObject2[3] = localEntitlementTypePropertyList.getPropertyValue("display name", 0);
          localStringBuffer.append((String)new LocalizableString("com.ffusion.tasks.resources", "control_parent_user", (Object[])localObject2).localize(paramSecureUser.getLocale()));
        }
        localStringBuffer.append("<br>");
        Object localObject2 = new a(localObject1, localStringBuffer.toString(), this.ba, m);
        locala = jdMethod_for(paramSecureUser, locala, (a)localObject2);
        Object localObject3 = jdMethod_for(paramHttpSession, paramSecureUser, paramNumberFormat, paramInt1, paramEntitlementGroupMember, paramLimits, paramEntitlementTypePropertyLists, localEntitlementTypePropertyList, paramHashMap, paramInt2, paramString1, paramString2);
        locala = jdMethod_for(paramSecureUser, locala, (a)localObject3);
      }
    }
    return locala;
  }
  
  public void setEntitlementPrefix(String paramString)
  {
    this.bD = paramString;
  }
  
  public String getEntitlementPrefix()
  {
    return this.bD;
  }
  
  public void setLimitListName(String paramString)
  {
    this.a8 = paramString;
  }
  
  public void setBadLimitURL(String paramString)
  {
    this.bo = paramString;
  }
  
  public void setLimitPrefix(String paramString)
  {
    this.a3 = paramString;
  }
  
  public void setMaxPerTransactionLimitMapName(String paramString)
  {
    this.bp = paramString;
  }
  
  public void setMaxPerDayLimitMapName(String paramString)
  {
    this.bK = paramString;
  }
  
  public void setMaxPerWeekLimitMapName(String paramString)
  {
    this.aX = paramString;
  }
  
  public void setMaxPerMonthLimitMapName(String paramString)
  {
    this.bj = paramString;
  }
  
  public void setMergedListName(String paramString)
  {
    this.a9 = paramString;
  }
  
  public void setWireTemplatesListName(String paramString)
  {
    this.aY = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.a7 = Integer.parseInt(paramString);
  }
  
  public void setMemberId(String paramString)
  {
    this.aV = paramString;
  }
  
  public void setMemberType(String paramString)
  {
    this.br = paramString;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.bn = paramString;
  }
  
  public void setObjectType(String paramString)
  {
    this.aT = paramString;
  }
  
  public void setObjectId(String paramString)
  {
    this.bC = paramString;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.ba = paramString;
  }
  
  public void setCrossCurrency(String paramString)
  {
    if (paramString.equalsIgnoreCase("TRUE")) {
      this.bz = true;
    } else if (paramString.equalsIgnoreCase("FALSE")) {
      this.bz = false;
    }
  }
  
  private static a jdMethod_for(SecureUser paramSecureUser, a parama1, a parama2)
  {
    if ((parama1 == null) || (parama1.jdMethod_do() == null)) {
      return parama2;
    }
    if ((parama2 == null) || (parama2.jdMethod_do() == null)) {
      return parama1;
    }
    if (parama1.a().equals(parama2.a()))
    {
      if (parama1.jdMethod_do().compareTo(parama2.jdMethod_do()) > 0) {
        return parama2;
      }
      return parama1;
    }
    BigDecimal localBigDecimal1 = parama1.jdMethod_do();
    BigDecimal localBigDecimal2 = parama2.jdMethod_do();
    try
    {
      FXRate localFXRate;
      BigDecimal localBigDecimal3;
      if (parama2.jdMethod_for() < parama1.jdMethod_for())
      {
        localFXRate = FX.getFXRate(paramSecureUser, parama1.a(), parama2.a(), new HashMap());
        localBigDecimal3 = localFXRate.getBuyPrice().getAmountValue();
        localBigDecimal1 = localBigDecimal1.multiply(localBigDecimal3);
      }
      else
      {
        localFXRate = FX.getFXRate(paramSecureUser, parama2.a(), parama1.a(), new HashMap());
        localBigDecimal3 = localFXRate.getBuyPrice().getAmountValue();
        localBigDecimal2 = localBigDecimal2.multiply(localBigDecimal3);
      }
    }
    catch (Exception localException) {}
    if (localBigDecimal1.compareTo(localBigDecimal2) > 0) {
      return parama2;
    }
    return parama1;
  }
  
  private Limit jdMethod_for(SecureUser paramSecureUser, Limit paramLimit1, Limit paramLimit2)
  {
    if ((paramLimit1 == null) || (paramLimit1.getAmount() == null)) {
      return paramLimit2;
    }
    if ((paramLimit2 == null) || (paramLimit2.getAmount() == null)) {
      return paramLimit1;
    }
    if (paramLimit1.getCurrencyCode().equals(paramLimit2.getCurrencyCode()))
    {
      if (paramLimit1.getAmount().compareTo(paramLimit2.getAmount()) > 0) {
        return paramLimit2;
      }
      return paramLimit1;
    }
    BigDecimal localBigDecimal1 = paramLimit1.getAmount();
    BigDecimal localBigDecimal2 = paramLimit2.getAmount();
    try
    {
      FXRate localFXRate;
      BigDecimal localBigDecimal3;
      if (paramLimit2.getPeriod() < paramLimit1.getPeriod())
      {
        localFXRate = FX.getFXRate(paramSecureUser, paramLimit1.getCurrencyCode(), paramLimit2.getCurrencyCode(), new HashMap());
        localBigDecimal3 = localFXRate.getBuyPrice().getAmountValue();
        localBigDecimal1 = localBigDecimal1.multiply(localBigDecimal3);
      }
      else
      {
        localFXRate = FX.getFXRate(paramSecureUser, paramLimit2.getCurrencyCode(), paramLimit1.getCurrencyCode(), new HashMap());
        localBigDecimal3 = localFXRate.getBuyPrice().getAmountValue();
        localBigDecimal2 = localBigDecimal2.multiply(localBigDecimal3);
      }
    }
    catch (Exception localException) {}
    if (localBigDecimal1.compareTo(localBigDecimal2) > 0) {
      return paramLimit2;
    }
    return paramLimit1;
  }
  
  private boolean jdMethod_for(Limit paramLimit)
  {
    if (paramLimit == null) {
      return true;
    }
    if (((!paramLimit.isCrossCurrency()) && (this.bz)) || ((paramLimit.isCrossCurrency()) && (!this.bz))) {
      return false;
    }
    return paramLimit.getCurrencyCode().equals(this.ba);
  }
  
  private static class a
  {
    private BigDecimal jdField_for;
    private String a;
    private String jdField_do;
    private int jdField_if;
    
    public a(BigDecimal paramBigDecimal, String paramString1, String paramString2, int paramInt)
    {
      this.jdField_for = paramBigDecimal;
      this.a = paramString1;
      this.jdField_do = paramString2;
      this.jdField_if = paramInt;
    }
    
    public BigDecimal jdField_do()
    {
      return this.jdField_for;
    }
    
    public String jdField_if()
    {
      return this.a;
    }
    
    public String a()
    {
      return this.jdField_do;
    }
    
    public int jdField_for()
    {
      return this.jdField_if;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.tasks.entitlements.CheckForRedundantLimits
 * JD-Core Version:    0.7.0.1
 */