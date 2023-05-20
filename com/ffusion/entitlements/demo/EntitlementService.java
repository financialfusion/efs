package com.ffusion.entitlements.demo;

import com.ffusion.entitlements.Entitlement;
import com.ffusion.entitlements.EntitlementCodes;
import com.ffusion.entitlements.Entitlements;
import com.ffusion.entitlements.Limit;
import com.ffusion.entitlements.PeriodLimit;
import com.ffusion.entitlements.ValueLimitable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EntitlementService
  implements com.ffusion.entitlements.EntitlementService, EntitlementCodes
{
  private static final String Nh = "DATA";
  private static final String Nj = "ENTITLEMENTS";
  private static final String Nz = "USER_ID";
  private static final String NC = "USER_TYPE";
  private static final String Ne = "E";
  private static final String Nk = "LIMIT";
  private static final String Ng = "XML";
  private static final String Nn = "LPS";
  private static final String Nw = "LP";
  private static final String Nf = "LPID";
  private static final String Ny = "LPN";
  private static final String NA = "LPV";
  private static final String LIMIT_NAME = "LN";
  private static final String NB = "LIMITS";
  private static final String LIMIT_ID = "LIMIT_ID";
  private static final String Nx = "LC";
  private static final String Ni = "EN";
  private static final String Ns = "EID";
  private static final String PERIOD = "PERIOD";
  private static final String Nq = "RUNNING_AMOUNT";
  private static final String Na = "RUNNING_TOTAL_ID";
  private static final String Nv = "RUNNING_TOTALS";
  private static final String Np = "RUNNING_TOTAL";
  private static final String Nu = "DEFAULT_ENTITLEMENTS";
  private static final String No = "DISABLED_UI_ENTITLEMENTS";
  private static final String Nd = "Id";
  private static final String Nt = "Name";
  private static final String M9 = "Limit";
  private static final String Nr = "LimitValue";
  private static final String Nc = "PeriodType";
  private String Nm;
  private String Nb;
  private HashMap M8 = new HashMap();
  private HashMap Nl = new HashMap();
  protected static HashMap m_InitHashMap;
  
  public void setUserName(String paramString)
  {
    this.Nm = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.Nb = paramString;
  }
  
  private void jdMethod_for(Entitlements paramEntitlements)
  {
    Entitlements localEntitlements = (Entitlements)this.Nl.get("DISABLED_UI_ENTITLEMENTS");
    if (localEntitlements == null) {
      return;
    }
    Entitlement localEntitlement1 = null;
    Entitlement localEntitlement2 = null;
    Iterator localIterator = localEntitlements.iterator();
    while (localIterator.hasNext())
    {
      localEntitlement1 = (Entitlement)localIterator.next();
      localEntitlement2 = paramEntitlements.get(localEntitlement1.getName());
      if (localEntitlement2 != null) {
        paramEntitlements.remove(localEntitlement2);
      }
    }
  }
  
  public int initialize(String paramString)
  {
    int i = 0;
    try
    {
      if (m_InitHashMap == null) {
        m_InitHashMap = new HashMap();
      }
      if ((m_InitHashMap != null) && (m_InitHashMap.get(paramString) != null))
      {
        this.Nl = ((HashMap)m_InitHashMap.get(paramString));
      }
      else
      {
        InputStream localInputStream = ResourceUtil.getResourceAsStream(this, paramString);
        if (localInputStream == null)
        {
          i = 14506;
        }
        else
        {
          String str = Strings.streamToString(localInputStream);
          XMLTag localXMLTag1 = new XMLTag();
          localXMLTag1.build(str);
          XMLTag localXMLTag2;
          if ((localXMLTag1 != null) && ((localXMLTag2 = localXMLTag1.getContainedTag("DATA")) != null))
          {
            ArrayList localArrayList = localXMLTag2.getContainedTagList();
            if (localArrayList != null)
            {
              Iterator localIterator = localArrayList.iterator();
              while (localIterator.hasNext())
              {
                XMLTag localXMLTag3 = (XMLTag)localIterator.next();
                Entitlements localEntitlements = new Entitlements(localXMLTag3.getTagName(), "", this, null);
                updateEntitlementListFromXML(localXMLTag3, localEntitlements);
                jdMethod_for(localEntitlements);
                localEntitlements.setInitialize();
                this.Nl.put(localXMLTag3.getTagName(), localEntitlements);
              }
              m_InitHashMap.put(paramString, this.Nl);
            }
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Error initializing entitlements file " + paramString, localThrowable);
      i = 14506;
    }
    return i;
  }
  
  public int getEntitlements(Entitlements paramEntitlements)
  {
    int i = 0;
    String str = paramEntitlements.getUserId();
    Entitlements localEntitlements = (Entitlements)this.Nl.get(str);
    if (localEntitlements == null) {
      localEntitlements = (Entitlements)this.Nl.get("DEFAULT_ENTITLEMENTS");
    }
    if (localEntitlements != null)
    {
      paramEntitlements.clear();
      Iterator localIterator = localEntitlements.iterator();
      while (localIterator.hasNext())
      {
        Entitlement localEntitlement = (Entitlement)localIterator.next();
        paramEntitlements.add(localEntitlement);
      }
    }
    else
    {
      i = 14506;
    }
    paramEntitlements.setInitialize();
    return i;
  }
  
  public int saveEntitlements(Entitlements paramEntitlements)
  {
    this.Nl.put(paramEntitlements.getUserId(), paramEntitlements);
    return 0;
  }
  
  protected void updateEntitlementFromXML(XMLTag paramXMLTag, Entitlement paramEntitlement, Entitlements paramEntitlements)
  {
    ArrayList localArrayList = null;
    localArrayList = paramXMLTag.getContainedTagList();
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      paramXMLTag = (XMLTag)localIterator.next();
      String str1 = paramXMLTag.getTagName();
      String str2 = paramXMLTag.getTagContent();
      if (str1.equals("EN")) {
        paramEntitlement.setName(str2);
      } else if (str1.equals("USER_ID")) {
        paramEntitlement.setUserName(str2);
      } else if (str1.equals("USER_TYPE")) {
        paramEntitlement.setUserType(str2);
      } else if (str1.equals("EID")) {
        paramEntitlement.setId(str2);
      } else if (str1.equals("LIMITS")) {
        jdMethod_for(paramXMLTag, paramEntitlement, paramEntitlements);
      }
    }
  }
  
  protected void updateEntitlementListFromXML(XMLTag paramXMLTag, Entitlements paramEntitlements)
  {
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        if (localXMLTag.getTagName().equals("E"))
        {
          Entitlement localEntitlement = new Entitlement();
          localEntitlement.setName("Temp");
          paramEntitlements.add(localEntitlement);
          updateEntitlementFromXML(localXMLTag, localEntitlement, paramEntitlements);
        }
      }
    }
  }
  
  private void jdMethod_for(XMLTag paramXMLTag, Entitlement paramEntitlement, Entitlements paramEntitlements)
  {
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        if (localXMLTag.getTagName().equalsIgnoreCase("LIMIT")) {
          jdMethod_int(localXMLTag, paramEntitlement, paramEntitlements);
        }
      }
    }
  }
  
  private void jdMethod_int(XMLTag paramXMLTag, Entitlement paramEntitlement, Entitlements paramEntitlements)
  {
    Object localObject1 = null;
    Limit localLimit = null;
    XMLTag localXMLTag1 = paramXMLTag.getContainedTag("LC");
    Object localObject2;
    try
    {
      if ((localXMLTag1 != null) && (localXMLTag1.getTagContent() != null))
      {
        String str = localXMLTag1.getTagContent();
        localObject2 = Class.forName(str);
        localLimit = (Limit)((Class)localObject2).newInstance();
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception getting limit info", localException);
    }
    if (localLimit != null)
    {
      XMLTag localXMLTag2 = paramXMLTag.getContainedTag("LN");
      if (localXMLTag2 != null) {
        localLimit.setName(localXMLTag2.getTagContent());
      }
      localObject2 = paramXMLTag.getContainedTag("LIMIT_ID");
      if (localObject2 != null) {
        localLimit.setId(((XMLTag)localObject2).getTagContent());
      }
      XMLTag localXMLTag3 = paramXMLTag.getContainedTag("LPS");
      if (localXMLTag3 != null) {
        jdMethod_int(localXMLTag3, localLimit);
      }
      paramEntitlements.addLimit(localLimit, paramEntitlement.getName());
    }
  }
  
  private void jdMethod_int(XMLTag paramXMLTag, Limit paramLimit)
  {
    ArrayList localArrayList = paramXMLTag.getContainedTagList();
    if (localArrayList != null)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        XMLTag localXMLTag = (XMLTag)localIterator.next();
        if (localXMLTag.getTagName().equalsIgnoreCase("LP")) {
          jdMethod_for(localXMLTag, paramLimit);
        }
      }
    }
  }
  
  private void jdMethod_for(XMLTag paramXMLTag, Limit paramLimit)
  {
    XMLTag localXMLTag1 = paramXMLTag.getContainedTag("LPN");
    String str1 = localXMLTag1.getTagContent();
    XMLTag localXMLTag2 = paramXMLTag.getContainedTag("LPV");
    String str2 = localXMLTag2.getTagContent();
    if ((str1 != null) && (str2 != null)) {
      try
      {
        Class[] arrayOfClass = new Class[1];
        arrayOfClass[0] = Class.forName("java.lang.String");
        Method localMethod = paramLimit.getClass().getMethod("set" + str1, arrayOfClass);
        if (localMethod != null)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = str2;
          localMethod.invoke(paramLimit, arrayOfObject);
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException) {}catch (IllegalAccessException localIllegalAccessException) {}catch (Throwable localThrowable) {}
    }
  }
  
  private double q(String paramString)
  {
    Double localDouble = (Double)this.M8.get(paramString);
    if (localDouble == null) {
      return 0.0D;
    }
    return localDouble.doubleValue();
  }
  
  public int getRunningTotals(ArrayList paramArrayList)
  {
    Limit localLimit = null;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if ((localLimit instanceof PeriodLimit)) {
        ((PeriodLimit)localLimit).setRunningTotalValue(q(localLimit.getId()));
      }
    }
    return 0;
  }
  
  public int updateRunningTotals(ValueLimitable paramValueLimitable, ArrayList paramArrayList)
  {
    Limit localLimit = null;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if ((localLimit instanceof PeriodLimit))
      {
        double d = q(localLimit.getId());
        d += paramValueLimitable.getLimitableValue().doubleValue();
        this.M8.put(localLimit.getId(), new Double(d));
        ((PeriodLimit)localLimit).setRunningTotalValue(d);
      }
    }
    return 0;
  }
  
  public int rollbackRunningTotals(ValueLimitable paramValueLimitable, ArrayList paramArrayList)
  {
    Limit localLimit = null;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      localLimit = (Limit)localIterator.next();
      if ((localLimit instanceof PeriodLimit))
      {
        double d = q(localLimit.getId());
        d -= paramValueLimitable.getLimitableValue().doubleValue();
        this.M8.put(localLimit.getId(), new Double(d));
        ((PeriodLimit)localLimit).setRunningTotalValue(d);
      }
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.entitlements.demo.EntitlementService
 * JD-Core Version:    0.7.0.1
 */