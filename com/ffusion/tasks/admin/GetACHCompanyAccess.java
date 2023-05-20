package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHCompanyAccess
  extends BaseTask
  implements Task
{
  private String aeQ;
  private String aeX;
  private String aeO;
  private String aeM;
  private String aeT;
  private int aeN;
  private int aeS = 0;
  private String aeU;
  private String aeL;
  private String aeR;
  private String aeW;
  private String aeV = "Credit";
  private String aeP = "Debit";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = "ACHCompany";
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    EntitlementGroupMember localEntitlementGroupMember1 = EntitlementsUtil.getEntitlementGroupMember(localSecureUser);
    EntitlementGroupMember localEntitlementGroupMember2 = null;
    if ((this.aeU != null) && (!this.aeU.equals("")))
    {
      localEntitlementGroupMember2 = new EntitlementGroupMember();
      localEntitlementGroupMember2.setEntitlementGroupId(this.aeN);
      localEntitlementGroupMember2.setId(this.aeU);
      localEntitlementGroupMember2.setMemberType(this.aeL);
      localEntitlementGroupMember2.setMemberSubType(this.aeR);
    }
    if ((this.aeX == null) || (this.aeX.equals("")))
    {
      this.error = 94;
      return this.taskErrorURL;
    }
    if ((this.aeO == null) || (this.aeO.equals("")))
    {
      this.error = 95;
      return this.taskErrorURL;
    }
    if ((this.aeM == null) || (this.aeM.equals("")))
    {
      this.error = 96;
      return this.taskErrorURL;
    }
    if ((this.aeT == null) || (this.aeT.equals("")))
    {
      this.error = 97;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(this.aeQ);
    if ((this.aeW == null) || (this.aeW.equals(""))) {
      localArrayList1.add("cross ACH company");
    } else {
      localArrayList1.add("per ACH company");
    }
    localHashMap.put("category", localArrayList1);
    EntitlementTypePropertyLists localEntitlementTypePropertyLists1 = null;
    try
    {
      localEntitlementTypePropertyLists1 = com.ffusion.csil.core.Entitlements.getEntitlementTypesWithProperties(localHashMap);
    }
    catch (CSILException localCSILException1)
    {
      this.error = 1000;
      return this.taskErrorURL;
    }
    if (localEntitlementTypePropertyLists1.size() != 0)
    {
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements1 = null;
      try
      {
        if (this.aeS != 0) {
          localEntitlements1 = com.ffusion.csil.core.Entitlements.getCumulativeEntitlements(this.aeS);
        }
      }
      catch (CSILException localCSILException2)
      {
        this.error = 1000;
        return this.taskErrorURL;
      }
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements2 = null;
      try
      {
        if ((this.aeW != null) && (!this.aeW.equals(""))) {
          if (localEntitlementGroupMember2 == null) {
            localEntitlements2 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember1, this.aeN);
          } else {
            localEntitlements2 = com.ffusion.csil.core.Entitlements.getRestrictedEntitlements(localEntitlementGroupMember1, localEntitlementGroupMember2);
          }
        }
      }
      catch (CSILException localCSILException3)
      {
        this.error = 1000;
        return this.taskErrorURL;
      }
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements3 = new com.ffusion.csil.beans.entitlements.Entitlements();
      com.ffusion.csil.beans.entitlements.Entitlements localEntitlements4 = new com.ffusion.csil.beans.entitlements.Entitlements();
      EntitlementTypePropertyLists localEntitlementTypePropertyLists2 = new EntitlementTypePropertyLists();
      Object localObject1 = localEntitlementTypePropertyLists1.iterator();
      Object localObject7;
      Object localObject11;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (EntitlementTypePropertyList)((Iterator)localObject1).next();
        if (!((EntitlementTypePropertyList)localObject2).isPropertySet("hide", "yes"))
        {
          ((EntitlementTypePropertyList)localObject2).clearProperty("hide box");
          ((EntitlementTypePropertyList)localObject2).clearProperty("hide admin box");
          boolean bool1 = true;
          boolean bool2 = false;
          int i = 0;
          boolean bool3 = false;
          if (((EntitlementTypePropertyList)localObject2).isPropertySet("admin partner"))
          {
            i = 1;
            bool2 = true;
          }
          Object localObject8;
          Entitlement localEntitlement;
          if (localEntitlements1 != null)
          {
            localObject7 = null;
            if ((this.aeW == null) || (this.aeW.equals(""))) {
              localObject7 = new Entitlement(((EntitlementTypePropertyList)localObject2).getOperationName(), null, null);
            } else {
              localObject7 = new Entitlement(((EntitlementTypePropertyList)localObject2).getOperationName(), str1, this.aeW);
            }
            localObject8 = localEntitlements1.iterator();
            while ((bool1) && (((Iterator)localObject8).hasNext()))
            {
              localEntitlement = (Entitlement)((Iterator)localObject8).next();
              bool1 = jdMethod_for((Entitlement)localObject7, localEntitlement);
            }
          }
          if ((bool1) && (((EntitlementTypePropertyList)localObject2).isPropertySet("category", "cross ACH company")) && (localEntitlements2 != null))
          {
            localObject7 = new Entitlement(((EntitlementTypePropertyList)localObject2).getOperationName(), null, null);
            localObject8 = localEntitlements2.iterator();
            while ((bool1) && (((Iterator)localObject8).hasNext()))
            {
              localEntitlement = (Entitlement)((Iterator)localObject8).next();
              bool1 = jdMethod_for((Entitlement)localObject7, localEntitlement);
            }
          }
          if (i != 0)
          {
            localObject7 = ((EntitlementTypePropertyList)localObject2).getPropertyValue("admin partner", 0);
            localObject8 = null;
            if ((this.aeW == null) || (this.aeW.equals(""))) {
              localObject8 = new Entitlement((String)localObject7, null, null);
            } else {
              localObject8 = new Entitlement((String)localObject7, str1, this.aeW);
            }
            try
            {
              bool3 = jdMethod_int(-1, EntitlementsUtil.getEntitlementGroupMember(localSecureUser), localEntitlements3, localEntitlementTypePropertyLists1, (Entitlement)localObject8, (EntitlementTypePropertyList)localObject2);
            }
            catch (CSILException localCSILException4)
            {
              this.error = 1000;
              return this.taskErrorURL;
            }
            try
            {
              if (localObject8 != null) {
                bool2 = jdMethod_int(this.aeS, null, localEntitlements4, localEntitlementTypePropertyLists1, (Entitlement)localObject8, (EntitlementTypePropertyList)localObject2);
              }
            }
            catch (CSILException localCSILException5)
            {
              this.error = 1000;
              return this.taskErrorURL;
            }
            Object localObject9;
            Object localObject10;
            if (localEntitlements1 != null)
            {
              localObject9 = localEntitlements1.iterator();
              while ((bool2) && (((Iterator)localObject9).hasNext()))
              {
                localObject10 = (Entitlement)((Iterator)localObject9).next();
                bool2 = jdMethod_for((Entitlement)localObject8, (Entitlement)localObject10);
              }
            }
            if ((bool2) && (((EntitlementTypePropertyList)localObject2).isPropertySet("category", "cross ACH company")) && (localEntitlements2 != null))
            {
              localObject9 = new Entitlement((String)localObject7, null, null);
              localObject10 = localEntitlements2.iterator();
              while ((bool2) && (((Iterator)localObject10).hasNext()))
              {
                localObject11 = (Entitlement)((Iterator)localObject10).next();
                bool2 = jdMethod_for((Entitlement)localObject9, (Entitlement)localObject11);
              }
            }
          }
          if ((bool3) && ((bool1) || (bool2)))
          {
            ((EntitlementTypePropertyList)localObject2).addProperty("form element name", H(((EntitlementTypePropertyList)localObject2).getOperationName()));
            if (!bool1) {
              ((EntitlementTypePropertyList)localObject2).addProperty("hide box", "yes");
            }
            if (!bool2) {
              ((EntitlementTypePropertyList)localObject2).addProperty("hide admin box", "yes");
            }
            localEntitlementTypePropertyLists2.add(localObject2);
          }
          else
          {
            ((EntitlementTypePropertyList)localObject2).addProperty("form element name", H(((EntitlementTypePropertyList)localObject2).getOperationName()));
            ((EntitlementTypePropertyList)localObject2).addProperty("hide box", "yes");
            ((EntitlementTypePropertyList)localObject2).addProperty("hide admin box", "yes");
            localEntitlementTypePropertyLists2.add(localObject2);
          }
        }
      }
      localObject1 = localEntitlementTypePropertyLists2.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (EntitlementTypePropertyList)((Iterator)localObject1).next();
        if ((((EntitlementTypePropertyList)localObject2).isPropertySet("hide box", "yes")) && (((EntitlementTypePropertyList)localObject2).isPropertySet("hide admin box", "yes")) && (!jdMethod_case((EntitlementTypePropertyList)localObject2, localEntitlementTypePropertyLists2))) {
          ((Iterator)localObject1).remove();
        }
      }
      localEntitlementTypePropertyLists2.setSortedBy("display name");
      HandleParentChildDisplay.reorderLists(localEntitlementTypePropertyLists2, null, 0);
      jdMethod_for(localEntitlementTypePropertyLists2, "ACHBatch");
      jdMethod_for(localEntitlementTypePropertyLists2, "Tax");
      jdMethod_for(localEntitlementTypePropertyLists2, "Child Support");
      jdMethod_for(localEntitlementTypePropertyLists2, "ACH");
      localHttpSession.setAttribute(this.aeX, localEntitlementTypePropertyLists2);
      localObject1 = new ArrayList();
      Object localObject2 = localEntitlementTypePropertyLists2.iterator();
      Object localObject5;
      Object localObject6;
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (EntitlementTypePropertyList)((Iterator)localObject2).next();
        if (!((EntitlementTypePropertyList)localObject3).isPropertySet("hide box", "yes"))
        {
          if (((EntitlementTypePropertyList)localObject3).isPropertySet("credit", "yes"))
          {
            localObject4 = new ArrayList();
            ((ArrayList)localObject4).add(((EntitlementTypePropertyList)localObject3).getOperationName());
            ((ArrayList)localObject4).add(this.aeV);
            localObject5 = new EntitlementTypePropertyLists();
            localObject6 = new ArrayList();
            localObject7 = new ArrayList();
            jdMethod_for((EntitlementTypePropertyList)localObject3, localEntitlementTypePropertyLists1, (EntitlementTypePropertyLists)localObject5);
            jdMethod_for((EntitlementTypePropertyLists)localObject5, localEntitlementTypePropertyLists2, (ArrayList)localObject6, (ArrayList)localObject7);
            ((ArrayList)localObject4).add(localObject6);
            ((ArrayList)localObject4).add(localObject7);
            ((ArrayList)localObject1).add(localObject4);
          }
          if (((EntitlementTypePropertyList)localObject3).isPropertySet("debit", "yes"))
          {
            localObject4 = new ArrayList();
            ((ArrayList)localObject4).add(((EntitlementTypePropertyList)localObject3).getOperationName());
            ((ArrayList)localObject4).add(this.aeP);
            localObject5 = new EntitlementTypePropertyLists();
            localObject6 = new ArrayList();
            localObject7 = new ArrayList();
            jdMethod_for((EntitlementTypePropertyList)localObject3, localEntitlementTypePropertyLists1, (EntitlementTypePropertyLists)localObject5);
            jdMethod_for((EntitlementTypePropertyLists)localObject5, localEntitlementTypePropertyLists2, (ArrayList)localObject6, (ArrayList)localObject7);
            ((ArrayList)localObject4).add(localObject6);
            ((ArrayList)localObject4).add(localObject7);
            ((ArrayList)localObject1).add(localObject4);
          }
        }
      }
      localHttpSession.setAttribute(this.aeO, localObject1);
      localObject2 = new ArrayList();
      Object localObject3 = new ArrayList();
      Object localObject4 = localEntitlementTypePropertyLists2.iterator();
      int k;
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (EntitlementTypePropertyList)((Iterator)localObject4).next();
        localObject6 = new ArrayList();
        localObject7 = new ArrayList();
        if (((EntitlementTypePropertyList)localObject5).isPropertySet("control parent"))
        {
          int j = ((EntitlementTypePropertyList)localObject5).numPropertyValues("control parent");
          k = 0;
          if (((EntitlementTypePropertyList)localObject5).isPropertySet("admin partner"))
          {
            k = 1;
            ((ArrayList)localObject7).add(((EntitlementTypePropertyList)localObject5).getPropertyValue("form element name", 0));
          }
          ((ArrayList)localObject6).add(((EntitlementTypePropertyList)localObject5).getPropertyValue("form element name", 0));
          for (int m = 0; m < j; m++)
          {
            localObject11 = ((EntitlementTypePropertyList)localObject5).getPropertyValue("control parent", m);
            Iterator localIterator2 = localEntitlementTypePropertyLists2.iterator();
            while (localIterator2.hasNext())
            {
              EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator2.next();
              if (((String)localObject11).equals(localEntitlementTypePropertyList.getOperationName()))
              {
                ((ArrayList)localObject6).add(localEntitlementTypePropertyList.getPropertyValue("form element name", 0));
                if ((k == 0) || (!localEntitlementTypePropertyList.isPropertySet("admin partner"))) {
                  break;
                }
                ((ArrayList)localObject7).add(H(localEntitlementTypePropertyList.getOperationName()));
                break;
              }
            }
          }
        }
        if (((ArrayList)localObject6).size() > 1) {
          ((ArrayList)localObject3).add(localObject6);
        }
        if (((ArrayList)localObject7).size() > 1) {
          ((ArrayList)localObject3).add(localObject7);
        }
      }
      localHttpSession.setAttribute(this.aeM, localObject3);
      localObject4 = localEntitlementTypePropertyLists2.iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (EntitlementTypePropertyList)((Iterator)localObject4).next();
        localObject6 = ((EntitlementTypePropertyList)localObject5).getOperationName();
        localObject7 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        ((ArrayList)localObject7).add(((EntitlementTypePropertyList)localObject5).getPropertyValue("form element name", 0));
        k = 0;
        if (((EntitlementTypePropertyList)localObject5).isPropertySet("admin partner"))
        {
          k = 1;
          localArrayList2.add(((EntitlementTypePropertyList)localObject5).getPropertyValue("form element name", 0));
        }
        Iterator localIterator1 = localEntitlementTypePropertyLists2.iterator();
        while (localIterator1.hasNext())
        {
          localObject11 = (EntitlementTypePropertyList)localIterator1.next();
          if (((EntitlementTypePropertyList)localObject11).isPropertySet("control parent"))
          {
            int n = ((EntitlementTypePropertyList)localObject11).numPropertyValues("control parent");
            for (int i1 = 0; i1 < n; i1++)
            {
              String str2 = ((EntitlementTypePropertyList)localObject11).getPropertyValue("control parent", i1);
              if (((String)localObject6).equals(str2))
              {
                ((ArrayList)localObject7).add(H(((EntitlementTypePropertyList)localObject11).getPropertyValue("form element name", 0)));
                if ((k == 0) || (!((EntitlementTypePropertyList)localObject11).isPropertySet("admin partner"))) {
                  break;
                }
                localArrayList2.add(H(((EntitlementTypePropertyList)localObject11).getOperationName()));
                break;
              }
            }
          }
        }
        if (((ArrayList)localObject7).size() > 1) {
          ((ArrayList)localObject2).add(localObject7);
        }
        if (localArrayList2.size() > 1) {
          ((ArrayList)localObject2).add(localArrayList2);
        }
      }
      localHttpSession.setAttribute(this.aeT, localObject2);
    }
    return super.getSuccessURL();
  }
  
  private static boolean jdMethod_for(Entitlement paramEntitlement1, Entitlement paramEntitlement2)
  {
    int i = 0;
    if (paramEntitlement2.getOperationName() != null) {
      i += 4;
    }
    if (paramEntitlement2.getObjectType() != null) {
      i += 2;
    }
    if (paramEntitlement2.getObjectId() != null) {
      i++;
    }
    switch (i)
    {
    case 0: 
      return false;
    case 2: 
      return !paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType());
    case 3: 
      return (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType())) || (!paramEntitlement2.getObjectId().equals(paramEntitlement1.getObjectId()));
    case 4: 
      return !paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName());
    case 6: 
      return (!paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName())) || (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType()));
    case 7: 
      return (!paramEntitlement2.getOperationName().equals(paramEntitlement1.getOperationName())) || (!paramEntitlement2.getObjectType().equals(paramEntitlement1.getObjectType())) || (!paramEntitlement2.getObjectId().equals(paramEntitlement1.getObjectId()));
    }
    return false;
  }
  
  private static String H(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramString == null) || (paramString.equals(""))) {
      return "";
    }
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if ((c != '+') && (c != '-') && (c != ' ')) {
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }
  
  private static void jdMethod_for(EntitlementTypePropertyLists paramEntitlementTypePropertyLists, String paramString)
  {
    EntitlementTypePropertyLists localEntitlementTypePropertyLists = new EntitlementTypePropertyLists();
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      if (localEntitlementTypePropertyList.isPropertySet("module_name", paramString)) {
        localEntitlementTypePropertyLists.add(localEntitlementTypePropertyList);
      }
    }
    if (localEntitlementTypePropertyLists.size() == 0) {
      return;
    }
    for (int i = 0; i < localEntitlementTypePropertyLists.size(); i++)
    {
      Object localObject = paramEntitlementTypePropertyLists.remove(paramEntitlementTypePropertyLists.indexOf(localEntitlementTypePropertyLists.get(i)));
      paramEntitlementTypePropertyLists.add(i, localObject);
    }
  }
  
  private static boolean jdMethod_case(EntitlementTypePropertyList paramEntitlementTypePropertyList, EntitlementTypePropertyLists paramEntitlementTypePropertyLists)
  {
    String str = paramEntitlementTypePropertyList.getOperationName();
    Iterator localIterator = paramEntitlementTypePropertyLists.iterator();
    while (localIterator.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
      boolean bool = false;
      if ((localEntitlementTypePropertyList.isPropertySet("display parent")) && (str.equals(localEntitlementTypePropertyList.getPropertyValue("display parent", 0))))
      {
        if ((localEntitlementTypePropertyList.isPropertySet("hide box", "yes")) && (localEntitlementTypePropertyList.isPropertySet("hide admin box", "yes"))) {
          bool = jdMethod_case(localEntitlementTypePropertyList, paramEntitlementTypePropertyLists);
        } else {
          bool = true;
        }
        if (bool) {
          return true;
        }
      }
    }
    return false;
  }
  
  private static void jdMethod_for(EntitlementTypePropertyList paramEntitlementTypePropertyList, EntitlementTypePropertyLists paramEntitlementTypePropertyLists1, EntitlementTypePropertyLists paramEntitlementTypePropertyLists2)
  {
    if (paramEntitlementTypePropertyList.isPropertySet("control parent"))
    {
      int i = paramEntitlementTypePropertyList.numPropertyValues("control parent");
      for (int j = 0; j < i; j++)
      {
        String str = paramEntitlementTypePropertyList.getPropertyValue("control parent", j);
        Iterator localIterator = paramEntitlementTypePropertyLists1.iterator();
        while (localIterator.hasNext())
        {
          EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
          if (localEntitlementTypePropertyList.getOperationName().equals(str))
          {
            if (paramEntitlementTypePropertyLists2.contains(localEntitlementTypePropertyList)) {
              break;
            }
            paramEntitlementTypePropertyLists2.add(localEntitlementTypePropertyList);
            jdMethod_for(localEntitlementTypePropertyList, paramEntitlementTypePropertyLists1, paramEntitlementTypePropertyLists2);
            break;
          }
        }
      }
    }
  }
  
  private static void jdMethod_for(EntitlementTypePropertyLists paramEntitlementTypePropertyLists1, EntitlementTypePropertyLists paramEntitlementTypePropertyLists2, ArrayList paramArrayList1, ArrayList paramArrayList2)
  {
    Iterator localIterator1 = paramEntitlementTypePropertyLists1.iterator();
    while (localIterator1.hasNext())
    {
      EntitlementTypePropertyList localEntitlementTypePropertyList1 = (EntitlementTypePropertyList)localIterator1.next();
      String str = localEntitlementTypePropertyList1.getOperationName();
      int i = 0;
      Iterator localIterator2 = paramEntitlementTypePropertyLists2.iterator();
      while (localIterator2.hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList2 = (EntitlementTypePropertyList)localIterator2.next();
        if (localEntitlementTypePropertyList2.getOperationName().equals(str))
        {
          paramArrayList1.add(str);
          i = 1;
          break;
        }
      }
      if (i == 0) {
        paramArrayList2.add(str);
      }
    }
  }
  
  private boolean jdMethod_int(int paramInt, EntitlementGroupMember paramEntitlementGroupMember, com.ffusion.csil.beans.entitlements.Entitlements paramEntitlements, EntitlementTypePropertyLists paramEntitlementTypePropertyLists, Entitlement paramEntitlement, EntitlementTypePropertyList paramEntitlementTypePropertyList)
    throws CSILException
  {
    if ((paramEntitlement == null) || (paramEntitlementTypePropertyList == null)) {
      return false;
    }
    if ((paramInt < 0) && (paramEntitlementGroupMember == null)) {
      return false;
    }
    if ((!paramEntitlement.getOperationName().equals(paramEntitlementTypePropertyList.getOperationName())) && (!paramEntitlement.getOperationName().equals(paramEntitlementTypePropertyList.getPropertyValue("admin partner", 0)))) {
      return false;
    }
    if (paramEntitlements.contains(paramEntitlement)) {
      return true;
    }
    if (paramEntitlementGroupMember == null)
    {
      if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramInt, paramEntitlement)) {
        return false;
      }
    }
    else if (!com.ffusion.csil.core.Entitlements.checkEntitlement(paramEntitlementGroupMember, paramEntitlement)) {
      return false;
    }
    int i = paramEntitlementTypePropertyList.numPropertyValues("control parent");
    if (i == 0)
    {
      paramEntitlements.add(paramEntitlement.clone());
      return true;
    }
    int j = 0;
    if (paramEntitlement.getOperationName().equals(paramEntitlementTypePropertyList.getPropertyValue("admin partner", 0))) {
      j = 1;
    }
    Entitlement localEntitlement = (Entitlement)paramEntitlement.clone();
    for (int k = 0; k < i; k++)
    {
      String str = paramEntitlementTypePropertyList.getPropertyValue("control parent", k);
      EntitlementTypePropertyList localEntitlementTypePropertyList = paramEntitlementTypePropertyLists.getByOperationName(str);
      if (localEntitlementTypePropertyList == null) {
        localEntitlementTypePropertyList = com.ffusion.csil.core.Entitlements.getEntitlementTypeWithProperties(str);
      }
      if (j != 0)
      {
        if (localEntitlementTypePropertyList.isPropertySet("admin partner"))
        {
          localEntitlement.setOperationName(localEntitlementTypePropertyList.getPropertyValue("admin partner", 0));
          if (!jdMethod_int(paramInt, paramEntitlementGroupMember, paramEntitlements, paramEntitlementTypePropertyLists, localEntitlement, localEntitlementTypePropertyList)) {
            return false;
          }
        }
      }
      else
      {
        localEntitlement.setOperationName(str);
        if (!jdMethod_int(paramInt, paramEntitlementGroupMember, paramEntitlements, paramEntitlementTypePropertyLists, localEntitlement, localEntitlementTypePropertyList)) {
          return false;
        }
      }
    }
    paramEntitlements.add(paramEntitlement.clone());
    return true;
  }
  
  public void setCategoryValue(String paramString)
  {
    this.aeQ = paramString;
  }
  
  public void setEntsListSessionName(String paramString)
  {
    this.aeX = paramString;
  }
  
  public void setEntsLimitsSessionName(String paramString)
  {
    this.aeO = paramString;
  }
  
  public void setParentChildName(String paramString)
  {
    this.aeT = paramString;
  }
  
  public void setChildParentName(String paramString)
  {
    this.aeM = paramString;
  }
  
  public void setGroupId(String paramString)
  {
    this.aeN = Integer.parseInt(paramString);
  }
  
  public void setParentGroupId(String paramString)
  {
    this.aeS = Integer.parseInt(paramString);
  }
  
  public void setMemberId(String paramString)
  {
    this.aeU = paramString;
  }
  
  public void setMemberType(String paramString)
  {
    this.aeL = paramString;
  }
  
  public void setMemberSubType(String paramString)
  {
    this.aeR = paramString;
  }
  
  public void setACHCompanyID(String paramString)
  {
    this.aeW = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetACHCompanyAccess
 * JD-Core Version:    0.7.0.1
 */