package com.ffusion.ims21.servlet.parse;

import com.ffusion.ims21.util.AcctKey;
import com.ffusion.ims21.util.DemoKey;
import com.ffusion.util.DateFormatUtil;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

public class BannerStats
{
  static final int a = 1000;
  Vector jdField_do = a();
  Object jdField_if = new Object();
  MessageGrabber jdField_int;
  static final String jdField_for = ">\n";
  
  public BannerStats(MessageGrabber paramMessageGrabber)
  {
    this.jdField_int = paramMessageGrabber;
  }
  
  public void addStat(int paramInt1, int paramInt2, int paramInt3, long paramLong, String paramString, DemoKey paramDemoKey)
  {
    DemoKey localDemoKey = paramDemoKey;
    if (localDemoKey == null) {
      localDemoKey = new DemoKey(paramLong);
    }
    Vector localVector = null;
    b localb = new b(null);
    localb.jdField_int = paramInt1;
    localb.jdField_if = paramInt3;
    localb.a = paramInt2;
    localb.jdField_for = localDemoKey;
    localb.jdField_do = paramString;
    synchronized (this.jdField_if)
    {
      this.jdField_do.addElement(localb);
      if (this.jdField_do.size() > 1000)
      {
        localVector = this.jdField_do;
        this.jdField_do = a();
      }
    }
    if (localVector != null) {
      jdField_if(localVector);
    }
  }
  
  private void jdField_if(Vector paramVector)
  {
    new a(paramVector).start();
  }
  
  private static String a(Vector paramVector)
  {
    if (paramVector == null) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer("");
    for (int i = 0; i < paramVector.size(); i++)
    {
      b localb = (b)paramVector.elementAt(i);
      localStringBuffer.append(localb.a());
    }
    return localStringBuffer.toString();
  }
  
  public String getStats()
  {
    Vector localVector = null;
    synchronized (this.jdField_if)
    {
      localVector = this.jdField_do;
      this.jdField_do = a();
    }
    return a(localVector);
  }
  
  private static String a(DemoKey paramDemoKey, String paramString)
  {
    if (paramDemoKey == null) {
      paramDemoKey = new DemoKey(0L);
    }
    StringBuffer localStringBuffer = new StringBuffer("<CI>\n");
    localStringBuffer.append(jdField_if()).append("\n");
    if (paramDemoKey.getState() != null) {
      localStringBuffer.append("<ST=").append(paramDemoKey.getState()).append(",0").append(">\n");
    }
    if (paramDemoKey.getUse() != 0) {
      localStringBuffer.append("<US=").append(paramDemoKey.getUse()).append(">\n");
    }
    if (paramDemoKey.getGender() != 0) {
      localStringBuffer.append("<GN=").append(paramDemoKey.getGender()).append(">\n");
    }
    if (paramDemoKey.getAge() != 0) {
      localStringBuffer.append("<AG=").append(paramDemoKey.getAge()).append(">\n");
    }
    if (paramDemoKey.getIncome() != 0) {
      localStringBuffer.append("<IC=").append(paramDemoKey.getIncome()).append(">\n");
    }
    if (paramDemoKey.getMarital() == 1) {
      localStringBuffer.append("<MR=YES").append(">\n");
    } else if (paramDemoKey.getMarital() == 2) {
      localStringBuffer.append("<MR=NO").append(">\n");
    }
    if (paramDemoKey.getFamily() == 1) {
      localStringBuffer.append("<KD=YES").append(">\n");
    } else if (paramDemoKey.getFamily() == 2) {
      localStringBuffer.append("<KD=NO").append(">\n");
    }
    if (paramDemoKey.getFamily() == 1) {
      localStringBuffer.append("<HM=YES").append(">\n");
    } else if (paramDemoKey.getFamily() == 2) {
      localStringBuffer.append("<HM=NO").append(">\n");
    }
    if (paramDemoKey.getFamily() == 1) {
      localStringBuffer.append("<MR=YES").append(">\n");
    } else if (paramDemoKey.getFamily() == 2) {
      localStringBuffer.append("<MR=NO").append(">\n");
    }
    localStringBuffer.append(a(paramString));
    localStringBuffer.append("</CI>\n");
    return localStringBuffer.toString();
  }
  
  private static String a(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    if (paramString != null)
    {
      int i = 0;
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "-");
      int j = localStringTokenizer.countTokens();
      if (j == 0) {
        return null;
      }
      int[] arrayOfInt = new int[j];
      Object localObject;
      while (localStringTokenizer.hasMoreTokens())
      {
        localObject = localStringTokenizer.nextToken();
        int k = 0;
        try
        {
          k = Integer.parseInt((String)localObject, 16);
        }
        catch (NumberFormatException localNumberFormatException) {}
        arrayOfInt[(i++)] = k;
      }
      if (arrayOfInt.length != 0)
      {
        localStringBuffer.append("<AC=");
        for (i = 0; i < arrayOfInt.length; i++)
        {
          if (i != 0) {
            localStringBuffer.append(";");
          }
          localObject = new AcctKey(arrayOfInt[i]);
          localStringBuffer.append(((AcctKey)localObject).getAcctId());
          localStringBuffer.append(",");
          localStringBuffer.append(((AcctKey)localObject).getAcctBal() / 100);
        }
        localStringBuffer.append(">\n");
      }
    }
    return localStringBuffer.toString();
  }
  
  private static Vector a()
  {
    return new Vector(1000);
  }
  
  private static String jdField_if()
  {
    Calendar localCalendar = Calendar.getInstance();
    StringBuffer localStringBuffer = new StringBuffer("<TS=");
    localStringBuffer.append(DateFormatUtil.getFormatter("yyyyMMddkkmmss").format(localCalendar.getTime())).append(",0>");
    return localStringBuffer.toString();
  }
  
  private class b
  {
    int jdField_int = 0;
    int a = 0;
    int jdField_if = 0;
    DemoKey jdField_for = null;
    String jdField_do = null;
    
    private b() {}
    
    public String a()
    {
      StringBuffer localStringBuffer = new StringBuffer("<AD>\n<ID=");
      localStringBuffer.append(this.jdField_int).append(",").append(this.a).append(",").append(this.jdField_if).append(">\n</AD>\n");
      localStringBuffer.append(BannerStats.a(this.jdField_for, this.jdField_do));
      return localStringBuffer.toString();
    }
    
    b(BannerStats.1 param1)
    {
      this();
    }
  }
  
  private class a
    extends Thread
  {
    Vector a = null;
    
    public a(Vector paramVector)
    {
      this.a = paramVector;
    }
    
    public void run()
    {
      String str = BannerStats.a(this.a);
      try
      {
        BannerStats.this.jdField_int.pushStats(str);
      }
      catch (Exception localException) {}
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.servlet.parse.BannerStats
 * JD-Core Version:    0.7.0.1
 */