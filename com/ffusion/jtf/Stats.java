package com.ffusion.jtf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Stats
  implements Serializable
{
  private boolean jdField_if;
  private HashMap jdField_do;
  private int a = 100;
  
  public String getStats()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    HashMap localHashMap = a();
    if (localHashMap != null) {
      jdField_do(localHashMap, localStringBuffer);
    }
    return new String(localStringBuffer);
  }
  
  private void jdField_do(HashMap paramHashMap, StringBuffer paramStringBuffer)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramHashMap.get(str);
      paramStringBuffer.append("<tr><td width=100 valign=\"TOP\">");
      paramStringBuffer.append("<b>");
      paramStringBuffer.append(str);
      paramStringBuffer.append("</b></td>");
      paramStringBuffer.append("<td valign=\"TOP\">");
      if ((localObject instanceof Integer)) {
        paramStringBuffer.append(((Integer)localObject).intValue());
      } else if ((localObject instanceof String)) {
        paramStringBuffer.append(localObject);
      } else if ((localObject instanceof HashMap)) {
        jdField_if((HashMap)localObject, paramStringBuffer, 0);
      } else {
        paramStringBuffer.append(localObject.toString());
      }
      paramStringBuffer.append("</td></tr>\n");
    }
  }
  
  private void jdField_if(HashMap paramHashMap, StringBuffer paramStringBuffer, int paramInt)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      for (int i = 0; i < paramInt; i++) {
        paramStringBuffer.append("&nbsp;&nbsp;&nbsp;");
      }
      String str = (String)localIterator.next();
      Object localObject = paramHashMap.get(str);
      paramStringBuffer.append(str);
      paramStringBuffer.append(" : ");
      if ((localObject instanceof Integer))
      {
        paramStringBuffer.append(((Integer)localObject).intValue());
      }
      else if ((localObject instanceof String))
      {
        paramStringBuffer.append(localObject);
      }
      else if ((localObject instanceof HashMap))
      {
        paramStringBuffer.append("<br>");
        jdField_if((HashMap)localObject, paramStringBuffer, paramInt + 1);
      }
      else
      {
        paramStringBuffer.append(localObject.toString());
      }
      paramStringBuffer.append("<br>\n");
    }
  }
  
  public String getStatsAvg()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    HashMap localHashMap = a();
    if (localHashMap != null) {
      jdField_if(localHashMap, localStringBuffer);
    }
    return new String(localStringBuffer);
  }
  
  private void jdField_if(HashMap paramHashMap, StringBuffer paramStringBuffer)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramHashMap.get(str);
      paramStringBuffer.append("<tr><td width=100 valign=\"TOP\">");
      paramStringBuffer.append("<b>");
      paramStringBuffer.append(str);
      paramStringBuffer.append("</b></td>");
      paramStringBuffer.append("<td valign=\"TOP\">");
      if ((localObject instanceof Integer)) {
        paramStringBuffer.append(((Integer)localObject).intValue());
      } else if ((localObject instanceof String)) {
        paramStringBuffer.append(localObject);
      } else if ((localObject instanceof HashMap)) {
        a((HashMap)localObject, paramStringBuffer, 0);
      } else {
        paramStringBuffer.append(localObject.toString());
      }
      paramStringBuffer.append("</td></tr>\n");
    }
  }
  
  private void a(HashMap paramHashMap, StringBuffer paramStringBuffer, int paramInt)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    long l1 = 0L;
    int i = 0;
    while (localIterator.hasNext())
    {
      for (int j = 0; j < paramInt; j++) {
        paramStringBuffer.append("&nbsp;&nbsp;&nbsp;");
      }
      String str = (String)localIterator.next();
      Object localObject = paramHashMap.get(str);
      if ((localObject instanceof Integer))
      {
        long l2 = new Long(str).longValue();
        int k = ((Integer)localObject).intValue();
        l1 += l2 * k;
        i += k;
      }
      else if ((localObject instanceof String))
      {
        paramStringBuffer.append(str);
        paramStringBuffer.append(" : ");
        paramStringBuffer.append(localObject);
      }
      else
      {
        paramStringBuffer.append(str);
        paramStringBuffer.append(" : ");
        paramStringBuffer.append(localObject.toString());
      }
      if (i == 0) {
        paramStringBuffer.append("<br>\n");
      }
    }
    if (i != 0)
    {
      paramStringBuffer.append("Avg = ");
      paramStringBuffer.append(l1 / i);
      paramStringBuffer.append("<br>");
      paramStringBuffer.append("n = ");
      paramStringBuffer.append(i);
    }
  }
  
  public String getStatSize()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    HashMap localHashMap = a();
    if (localHashMap != null) {
      a(localHashMap, localStringBuffer);
    } else {
      localStringBuffer.append("Empty");
    }
    return new String(localStringBuffer);
  }
  
  private void a(HashMap paramHashMap, StringBuffer paramStringBuffer)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    int i = 0;
    paramStringBuffer.append(" ");
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      Object localObject = paramHashMap.get(str);
      if ((localObject instanceof Integer))
      {
        i++;
      }
      else if ((localObject instanceof String))
      {
        i++;
      }
      else if ((localObject instanceof HashMap))
      {
        i++;
        i += ((HashMap)localObject).size();
      }
      else
      {
        i++;
      }
    }
    paramStringBuffer.append(new Integer(i).toString());
  }
  
  public void setStatsReset(String paramString)
  {
    jdField_if();
  }
  
  public void setStatsEnabled(String paramString)
  {
    this.jdField_if = Boolean.valueOf(paramString).booleanValue();
    if ((this.jdField_if) && (this.jdField_do == null)) {
      this.jdField_do = new HashMap();
    }
  }
  
  public String getStatsEnabled()
  {
    return String.valueOf(this.jdField_if);
  }
  
  public String getNotStatsEnabled()
  {
    return String.valueOf(!this.jdField_if);
  }
  
  public void setRecordingPrecision(String paramString)
  {
    try
    {
      this.a = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.a = 1;
    }
  }
  
  public String getRecordingPrecision()
  {
    return getPrecisionString();
  }
  
  public void incTimer(String paramString, long paramLong)
  {
    if (this.jdField_if)
    {
      String str = new String(new Long((paramLong + this.a / 2) / this.a * this.a).toString());
      a(paramString, str, 1);
    }
  }
  
  private void a(String paramString1, String paramString2, int paramInt)
  {
    if (!this.jdField_if) {
      return;
    }
    HashMap localHashMap1 = this.jdField_do;
    HashMap localHashMap2 = (HashMap)localHashMap1.get(paramString1);
    if (localHashMap2 == null)
    {
      localHashMap2 = new HashMap();
      localHashMap1.put(paramString1, localHashMap2);
    }
    int i = 0;
    Integer localInteger = (Integer)localHashMap2.get(paramString2);
    if (localInteger != null) {
      i = localInteger.intValue();
    }
    i += paramInt;
    localHashMap2.put(paramString2, new Integer(i));
  }
  
  private HashMap a()
  {
    return this.jdField_do;
  }
  
  private void jdField_if()
  {
    if (this.jdField_if) {
      this.jdField_do = new HashMap();
    } else {
      this.jdField_do = null;
    }
  }
  
  public String getPrecisionString()
  {
    return new Integer(this.a).toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.Stats
 * JD-Core Version:    0.7.0.1
 */