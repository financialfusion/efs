package com.ffusion.alert.shared;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AELog
{
  private static AELogParams jdField_do = null;
  private static PrintWriter a = null;
  private static PrintWriter jdField_for = null;
  private static final SimpleDateFormat jdField_if = new SimpleDateFormat("EEE MMM dd HH:mm:ss.SSS z yyyy: ");
  
  public static synchronized void jdField_if()
  {
    try
    {
      if (a != null)
      {
        a.flush();
        a = null;
      }
      if (jdField_for != null)
      {
        jdField_for.close();
        jdField_for = null;
      }
    }
    catch (Exception localException)
    {
      a(localException, "Failed to shutdown output streams");
    }
  }
  
  public static synchronized void a()
  {
    if (a != null) {
      a.flush();
    }
    if (jdField_for != null) {
      jdField_for.flush();
    }
  }
  
  public static synchronized void a(AELogParams paramAELogParams)
  {
    jdField_do = paramAELogParams;
    try
    {
      if (jdField_do.jdField_do())
      {
        if (a != null) {
          a.flush();
        }
        a = new PrintWriter(System.out, true);
      }
      else if (a != null)
      {
        a.flush();
        a = null;
      }
      if (jdField_do.jdField_if())
      {
        if (jdField_for != null) {
          jdField_for.close();
        }
        jdField_for = new PrintWriter(new BufferedWriter(new FileWriter(jdField_do.jdMethod_int(), true)), true);
      }
      else if (jdField_for != null)
      {
        jdField_for.close();
        jdField_for = null;
      }
    }
    catch (Exception localException)
    {
      a(localException, "Failed to set output stream");
    }
  }
  
  public static synchronized AELogParams jdField_do()
  {
    return jdField_do;
  }
  
  private static void a(String paramString)
  {
    paramString = jdField_if.format(new Date()) + paramString;
    if ((a != null) && (jdField_do.jdField_do())) {
      a.println(paramString);
    }
    if ((jdField_for != null) && (jdField_do.jdField_if())) {
      jdField_for.println(paramString);
    }
  }
  
  private static void a(Throwable paramThrowable)
  {
    if ((a != null) && (jdField_do.jdField_do())) {
      paramThrowable.printStackTrace(a);
    }
    if ((jdField_for != null) && (jdField_do.jdField_if())) {
      paramThrowable.printStackTrace(jdField_for);
    }
  }
  
  private static void a(Throwable paramThrowable, String paramString)
  {
    paramString = jdField_if.format(new Date()) + paramString;
    if ((a != null) && (jdField_do.jdField_do()))
    {
      a.println(paramString);
      paramThrowable.printStackTrace(a);
    }
    if ((jdField_for != null) && (jdField_do.jdField_if()))
    {
      jdField_for.println(paramString);
      paramThrowable.printStackTrace(jdField_for);
    }
  }
  
  public static synchronized void a(Throwable paramThrowable, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for()) {
      a(paramThrowable);
    }
  }
  
  public static synchronized void a(Throwable paramThrowable, String paramString, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      paramString = paramThrowable.getClass().getName() + ": " + paramString;
      a(paramThrowable, paramString);
    }
  }
  
  public static synchronized void a(String paramString, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for()) {
      a(paramString);
    }
  }
  
  public static synchronized void a(String paramString1, String paramString2, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for()) {
      a(paramString1 + paramString2);
    }
  }
  
  public static synchronized void a(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for()) {
      a(paramString1 + paramString2 + paramString3);
    }
  }
  
  public static synchronized void a(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for()) {
      a(paramString1 + paramString2 + paramString3 + paramString4);
    }
  }
  
  public static synchronized void a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for()) {
      a(paramString1 + paramString2 + paramString3 + paramString4 + paramString5);
    }
  }
  
  public static synchronized void a(String paramString1, long paramLong1, boolean paramBoolean1, String paramString2, long paramLong2, boolean paramBoolean2, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for()) {
      a(paramString1 + (paramBoolean1 ? jdField_if.format(new Date(paramLong1)) + " (" + paramLong1 + ")" : String.valueOf(paramLong1)) + paramString2 + (paramBoolean2 ? jdField_if.format(new Date(paramLong2)) + " (" + paramLong2 + ")" : String.valueOf(paramLong2)));
    }
  }
  
  public static synchronized void a(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt2 <= jdField_do.jdField_for()) {
      a(paramString + paramInt1);
    }
  }
  
  public static synchronized void a(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3)
  {
    if (paramInt3 <= jdField_do.jdField_for()) {
      a(paramString1 + paramInt1 + paramString2 + paramInt2);
    }
  }
  
  public static synchronized void a(String paramString, long paramLong, boolean paramBoolean, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for()) {
      a(paramString + (paramBoolean ? jdField_if.format(new Date(paramLong)) + " (" + paramLong + ")" : String.valueOf(paramLong)));
    }
  }
  
  public static synchronized void a(Throwable paramThrowable, AEResourceBundle paramAEResourceBundle, String paramString, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString);
      a(paramThrowable, str);
    }
  }
  
  public static synchronized void a(Throwable paramThrowable, AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2);
      a(paramThrowable, str);
    }
  }
  
  public static synchronized void a(Throwable paramThrowable, AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramString3);
      a(paramThrowable, str);
    }
  }
  
  public static synchronized void a(Throwable paramThrowable, AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramString3, paramString4);
      a(paramThrowable, str);
    }
  }
  
  public static synchronized void a(Throwable paramThrowable, AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramString3, paramString4, paramString5);
      a(paramThrowable, str);
    }
  }
  
  public static synchronized void a(Throwable paramThrowable, AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
      a(paramThrowable, str);
    }
  }
  
  public static synchronized void a(AEResourceBundle paramAEResourceBundle, String paramString, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString);
      a(str);
    }
  }
  
  public static synchronized void a(AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2);
      a(str);
    }
  }
  
  public static synchronized void a(AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramString3);
      a(str);
    }
  }
  
  public static synchronized void a(AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramString3, paramString4);
      a(str);
    }
  }
  
  public static synchronized void a(AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramString3, paramString4, paramString5);
      a(str);
    }
  }
  
  public static synchronized void a(AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
      a(str);
    }
  }
  
  public static synchronized void a(AEResourceBundle paramAEResourceBundle, String paramString1, String paramString2, long paramLong, boolean paramBoolean, String paramString3, int paramInt)
  {
    if (paramInt <= jdField_do.jdField_for())
    {
      String str = paramAEResourceBundle.a(paramString1, paramString2, paramBoolean ? jdField_if.format(new Date(paramLong)) + " (" + paramLong + ")" : String.valueOf(paramLong), paramString3);
      a(str);
    }
  }
}


/* Location:           D:\drops\jd\jars\UAE20.jar
 * Qualified Name:     com.ffusion.alert.shared.AELog
 * JD-Core Version:    0.7.0.1
 */