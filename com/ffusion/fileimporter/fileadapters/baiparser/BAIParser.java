package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.FIException;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;

public class BAIParser
  implements BAIParserConsts
{
  private String aTt = null;
  private String aTo = null;
  private String aTx = null;
  private String aTv = null;
  private String aTw = null;
  private a aTn = new a();
  private static final String aTr = String.valueOf('/');
  private Vector aTs = new Vector();
  private Vector aTq = new Vector();
  private BAIParserCallback aTm = null;
  private boolean aTp = false;
  private BAIFileHeader aTl = null;
  private BAIGroupHeader aTy = null;
  private BAIAccountIdentifier aTu = null;
  private boolean aTk = false;
  
  public BAIParser() {}
  
  public BAIParser(BAIParserCallback paramBAIParserCallback)
  {
    this.aTm = paramBAIParserCallback;
  }
  
  public void processBAIFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    BufferedReader localBufferedReader = null;
    String str = null;
    String[] arrayOfString = null;
    Vector localVector = new Vector();
    int i = 0;
    int j = 0;
    this.aTt = ((String)paramHashMap.get("FILE_NAME"));
    this.aTv = ((String)paramHashMap.get("FROM_SYSTEM"));
    this.aTo = ((String)paramHashMap.get("HOST_NAME"));
    this.aTx = ((String)paramHashMap.get("ACTIVITY_TYPE"));
    this.aTw = ((String)paramHashMap.get("TO_SYSTEM"));
    try
    {
      localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
      Object localObject;
      while ((str = localBufferedReader.readLine()) != null)
      {
        j++;
        if (str.length() != 0)
        {
          if (this.aTk)
          {
            localObject = new FIException("com.ffusion.fileimporter.BAIParser.processBAIFile", 2, "Records found after file trailer record (99)");
            DebugLog.throwing("com.ffusion.fileimporter.BAIParser.processBAIFile", (Throwable)localObject);
            throw ((Throwable)localObject);
          }
          arrayOfString = this.aTn.a(str, j - 1);
          if ((arrayOfString != null) && (arrayOfString.length > 0))
          {
            if ((!arrayOfString[0].equals("88")) && (localVector.size() > 0))
            {
              jdMethod_for(localVector, i, j - 1, paramHashMap);
              localVector.clear();
              i = 0;
            }
            i++;
            localVector.add(arrayOfString);
            arrayOfString = null;
          }
        }
      }
      if (this.aTk)
      {
        localObject = new FIException("com.ffusion.fileimporter.BAIParser.processBAIFile", 2, "Records found after file trailer record (99)");
        DebugLog.throwing("com.ffusion.fileimporter.BAIParser.processBAIFile", (Throwable)localObject);
        throw ((Throwable)localObject);
      }
      if (localVector.size() > 0)
      {
        jdMethod_for(localVector, i, j, paramHashMap);
        localVector.clear();
      }
      if (!this.aTk)
      {
        localObject = new StringBuffer("No file trailer record was found.");
        localFIException2 = new FIException("com.ffusion.fileimporter.BAIParser.processBAIFile", 2, ((StringBuffer)localObject).toString());
        DebugLog.throwing("com.ffusion.fileimporter.BAIParser.processBAIFile", localFIException2);
        throw localFIException2;
      }
    }
    catch (FIException localFIException1)
    {
      DebugLog.log(Level.WARNING, "Throwing the following exception at line " + (j - 1));
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.processBAIFile", localFIException1);
      throw localFIException1;
    }
    catch (Exception localException)
    {
      FIException localFIException2 = new FIException("com.ffusion.fileimporter.BAIParser.processBAIFile", 9702, "Invalid BAI File Format.", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.processBAIFile", localFIException2);
      throw localFIException2;
    }
  }
  
  private void jdMethod_for(Vector paramVector, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws FIException
  {
    String[] arrayOfString = jdMethod_for(paramVector);
    Object localObject1 = null;
    String str = "BAIParser.processBAIRecord";
    Object localObject2;
    FIException localFIException;
    if (arrayOfString[0].equals("01"))
    {
      if (this.aTl != null)
      {
        localObject2 = new StringBuffer("Line ");
        if (paramInt2 == 0) {
          ((StringBuffer)localObject2).append("number unknown:");
        } else {
          ((StringBuffer)localObject2).append(paramInt2).append(":");
        }
        ((StringBuffer)localObject2).append(" Invalid file header record found. Already processed another file header record.");
        localFIException = new FIException(str, 9704, ((StringBuffer)localObject2).toString());
        DebugLog.throwing(str, localFIException);
        throw localFIException;
      }
      this.aTl = new BAIFileHeader(paramInt2, arrayOfString, paramInt1, paramHashMap);
      localObject1 = this.aTl;
    }
    else if (arrayOfString[0].equals("99"))
    {
      if ((this.aTl == null) || (this.aTy != null) || (this.aTu != null))
      {
        localObject2 = new StringBuffer("Line ");
        if (paramInt2 == 0) {
          ((StringBuffer)localObject2).append("number unknown:");
        } else {
          ((StringBuffer)localObject2).append(paramInt2).append(":");
        }
        ((StringBuffer)localObject2).append(" Found unexpected file trailer record.");
        localFIException = new FIException(str, 9711, ((StringBuffer)localObject2).toString());
        DebugLog.throwing(str, localFIException);
        throw localFIException;
      }
      localObject1 = new BAIFileTrailer(paramInt2, this.aTl, arrayOfString, paramInt1, paramHashMap);
      this.aTl = null;
      this.aTk = true;
    }
    else if (arrayOfString[0].equals("02"))
    {
      if (this.aTl == null)
      {
        localObject2 = new StringBuffer("Line ");
        if (paramInt2 == 0) {
          ((StringBuffer)localObject2).append("number unknown:");
        } else {
          ((StringBuffer)localObject2).append(paramInt2).append(":");
        }
        ((StringBuffer)localObject2).append(" Invalid group header record found. No matching file header record.");
        localFIException = new FIException(str, 9706, ((StringBuffer)localObject2).toString());
        DebugLog.throwing(str, localFIException);
        throw localFIException;
      }
      if (this.aTy != null)
      {
        localObject2 = new StringBuffer("Line ");
        if (paramInt2 == 0) {
          ((StringBuffer)localObject2).append("number unknown:");
        } else {
          ((StringBuffer)localObject2).append(paramInt2).append(":");
        }
        ((StringBuffer)localObject2).append(" Found unexpected group header record.");
        localFIException = new FIException(str, 9706, ((StringBuffer)localObject2).toString());
        DebugLog.throwing(str, localFIException);
        throw localFIException;
      }
      this.aTy = new BAIGroupHeader(paramInt2, arrayOfString, paramInt1, paramHashMap);
      localObject1 = this.aTy;
    }
    else if (arrayOfString[0].equals("98"))
    {
      if ((this.aTl == null) || (this.aTy == null) || (this.aTu != null))
      {
        localObject2 = new StringBuffer("Line ");
        if (paramInt2 == 0) {
          ((StringBuffer)localObject2).append("number unknown:");
        } else {
          ((StringBuffer)localObject2).append(paramInt2).append(":");
        }
        ((StringBuffer)localObject2).append(" Found unexpected group trailer record.");
        localFIException = new FIException(str, 9712, ((StringBuffer)localObject2).toString());
        DebugLog.throwing(str, localFIException);
        throw localFIException;
      }
      localObject1 = new BAIGroupTrailer(paramInt2, this.aTl, this.aTy, arrayOfString, paramInt1, paramHashMap);
      this.aTy = null;
    }
    else if (arrayOfString[0].equals("03"))
    {
      if (this.aTu != null)
      {
        localObject2 = new StringBuffer("Line ");
        if (paramInt2 == 0) {
          ((StringBuffer)localObject2).append("number unknown:");
        } else {
          ((StringBuffer)localObject2).append(paramInt2).append(":");
        }
        ((StringBuffer)localObject2).append(" Found unexpected account identifier record.");
        localFIException = new FIException(str, 9710, ((StringBuffer)localObject2).toString());
        DebugLog.throwing(str, localFIException);
        throw localFIException;
      }
      this.aTu = new BAIAccountIdentifier(paramInt2, this.aTy, arrayOfString, paramInt1, paramHashMap);
      localObject1 = this.aTu;
    }
    else if (arrayOfString[0].equals("49"))
    {
      if ((this.aTl == null) || (this.aTy == null) || (this.aTu == null))
      {
        localObject2 = new StringBuffer("Line ");
        if (paramInt2 == 0) {
          ((StringBuffer)localObject2).append("number unknown:");
        } else {
          ((StringBuffer)localObject2).append(paramInt2).append(":");
        }
        ((StringBuffer)localObject2).append(" Invalid account trailer record. No matching account header record.");
        localFIException = new FIException(str, 9711, ((StringBuffer)localObject2).toString());
        DebugLog.throwing(str, localFIException);
        throw localFIException;
      }
      localObject1 = new BAIAccountTrailer(paramInt2, this.aTy, this.aTu, arrayOfString, paramInt1, paramHashMap);
      this.aTu = null;
    }
    else if (arrayOfString[0].equals("16"))
    {
      if ((this.aTl == null) || (this.aTy == null) || (this.aTu == null))
      {
        localObject2 = new StringBuffer("Line ");
        if (paramInt2 == 0) {
          ((StringBuffer)localObject2).append("number unknown:");
        } else {
          ((StringBuffer)localObject2).append(paramInt2).append(":");
        }
        ((StringBuffer)localObject2).append(" Invalid BAI transaction record. No matching account header record.");
        localFIException = new FIException(str, 9714, ((StringBuffer)localObject2).toString());
        DebugLog.throwing(str, localFIException);
        throw localFIException;
      }
      localObject1 = new BAITransactionDetail(paramInt2, this.aTu, arrayOfString, paramInt1, paramHashMap);
    }
    else
    {
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.CreateBAIRecord", 9717, "Unknown BAI record type" + arrayOfString[0]);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.CreateBAIRecord", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (this.aTm != null) {
      this.aTm.processBAIRecord(localObject1);
    } else {
      this.aTq.add(localObject1);
    }
    if (this.aTp) {
      this.aTs.add(arrayOfString);
    }
  }
  
  private String[] jdMethod_for(Vector paramVector)
  {
    int i = 0;
    int j = 0;
    int k = paramVector.size();
    String[] arrayOfString1 = null;
    Vector localVector = new Vector();
    for (i = 0; i < k; i++)
    {
      arrayOfString1 = (String[])paramVector.get(i);
      for (j = 0; j < arrayOfString1.length; j++) {
        if (((j != 0) || (!arrayOfString1[j].equals("88"))) && (!arrayOfString1[j].equals(aTr))) {
          localVector.add(arrayOfString1[j]);
        }
      }
    }
    String[] arrayOfString2 = new String[localVector.size()];
    localVector.toArray(arrayOfString2);
    return arrayOfString2;
  }
  
  public void enableDebug()
  {
    this.aTp = true;
  }
  
  public void dump()
  {
    if (this.aTp)
    {
      int i = 0;
      int j = 0;
      String[] arrayOfString = null;
      for (i = 0; i < this.aTs.size(); i++)
      {
        arrayOfString = (String[])this.aTs.get(i);
        for (j = 0; j < arrayOfString.length; j++)
        {
          if ((j > 0) && (!arrayOfString[j].equals(aTr))) {
            System.out.print(",");
          }
          System.out.print(arrayOfString[j]);
        }
        System.out.println("<==");
      }
    }
  }
  
  protected static int getNumberOfFieldsInCurrentRecord(int paramInt, Object[] paramArrayOfObject)
    throws FIException
  {
    int i = 7;
    if (paramArrayOfObject.length > 3) {
      if (((String)paramArrayOfObject[3]).equals("S")) {
        i = 10;
      } else if (((String)paramArrayOfObject[3]).equals("V")) {
        i = 9;
      } else if ((((String)paramArrayOfObject[3]).equals("D")) && (paramArrayOfObject.length > 4)) {
        try
        {
          i = 8 + Integer.parseInt((String)paramArrayOfObject[4]) * 2;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          StringBuffer localStringBuffer = new StringBuffer("Line ");
          localStringBuffer.append(paramInt).append(":");
          localStringBuffer.append(" The funds type for this record is 'D' but the next field is not a valid number indicating how many availability distributions to expect in this record.");
          FIException localFIException = new FIException("getNumberOfFieldsInCurrentRecord", 9728, localStringBuffer.toString());
          DebugLog.throwing("getNumberOfFieldsInCurrentRecord", localFIException);
          throw localFIException;
        }
      }
    }
    return i;
  }
  
  public class a
  {
    private final int jdField_int = 0;
    private final int jdField_do = 1;
    private final int jdField_if = 2;
    private StringBuffer jdField_new = new StringBuffer();
    private StringBuffer jdField_try = new StringBuffer();
    private Vector jdField_case = new Vector(20);
    private int jdField_for = 0;
    private String jdField_byte = null;
    private Vector a = new Vector(20);
    
    public a() {}
    
    private int a(StringBuffer paramStringBuffer, char paramChar)
    {
      int i = paramStringBuffer.length();
      for (int j = 0; j < i; j++) {
        if (paramStringBuffer.charAt(j) == paramChar) {
          return j;
        }
      }
      return -1;
    }
    
    private int a(int paramInt)
      throws FIException
    {
      this.jdField_try.setLength(0);
      int i = 0;
      try
      {
        int j = a(this.jdField_new, ',');
        int k = a(this.jdField_new, '/');
        int m = 0;
        int n = 0;
        int i1 = 0;
        if (j >= 0)
        {
          int i2 = 0;
          if ((this.jdField_case.size() != 0) && (this.jdField_case.get(0).equals("88"))) {
            i2 = BAIParser.getNumberOfFieldsInCurrentRecord(paramInt, this.a.toArray());
          } else {
            i2 = BAIParser.getNumberOfFieldsInCurrentRecord(paramInt, this.jdField_case.toArray());
          }
          if ((this.jdField_case.size() != 0) && (this.jdField_case.get(0).equals("88")) && (this.jdField_byte.equals("16")))
          {
            if (this.jdField_case.size() + this.a.size() >= i2)
            {
              n = this.jdField_new.length();
              i = 2;
            }
            else
            {
              n = j;
              i1 = j + 1;
            }
          }
          else if ((this.jdField_case.size() + 1 >= i2) && (this.jdField_case.get(0).equals("16")))
          {
            n = this.jdField_new.length();
            i = 2;
          }
          else
          {
            n = j;
            i1 = j + 1;
          }
        }
        else if (k >= 0)
        {
          if ((this.jdField_case.get(0).equals("88")) && (this.jdField_for == 2))
          {
            n = this.jdField_new.length();
            i = 2;
          }
          else
          {
            n = k;
            i = 1;
          }
        }
        else
        {
          n = this.jdField_new.length();
          i = 2;
        }
        for (m = 0; m < n; m++) {
          this.jdField_try.append(this.jdField_new.charAt(m));
        }
        if (i1 > 0) {
          this.jdField_new.delete(0, i1);
        } else {
          this.jdField_new.setLength(0);
        }
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        FIException localFIException = new FIException("com.ffusion.fileimporter.BAIParser.readBAIField", 9701, "Error Reading A BAI Field", localIndexOutOfBoundsException);
        DebugLog.throwing("com.ffusion.fileimporter.BAIParser.readBAIField", localFIException);
        throw localFIException;
      }
      return i;
    }
    
    public String[] a(String paramString, int paramInt)
      throws FIException
    {
      this.jdField_case.clear();
      this.jdField_new.setLength(0);
      this.jdField_new.append(paramString);
      int i = 0;
      while (i == 0)
      {
        i = a(paramInt);
        this.jdField_case.add(this.jdField_try.toString());
      }
      this.jdField_for = i;
      if (!this.jdField_case.get(0).equals("88"))
      {
        this.jdField_byte = ((String)this.jdField_case.get(0));
        this.a.clear();
        this.a.addAll(this.jdField_case);
      }
      else
      {
        for (int j = 1; j < this.jdField_case.size(); j++) {
          this.a.add(this.jdField_case.get(j));
        }
      }
      if (i == 1) {
        this.jdField_case.add(BAIParser.aTr);
      }
      String[] arrayOfString = new String[this.jdField_case.size()];
      this.jdField_case.toArray(arrayOfString);
      return arrayOfString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIParser
 * JD-Core Version:    0.7.0.1
 */