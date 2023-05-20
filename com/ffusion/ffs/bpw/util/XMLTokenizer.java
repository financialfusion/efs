package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.interfaces.FFSException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public final class XMLTokenizer
{
  private String jdField_byte;
  private String jdField_try;
  private String jdField_for;
  private boolean jdField_do;
  private StringBuffer jdField_new;
  private BufferedReader jdField_case;
  private boolean a;
  private boolean jdField_char;
  private Stack jdField_else;
  private int jdField_int;
  private String jdField_if;
  
  public XMLTokenizer(String paramString1, String paramString2, boolean paramBoolean)
    throws FFSException
  {
    try
    {
      File localFile = new File(paramString1);
      if (!localFile.exists()) {
        throw new FFSException("File not exist: " + paramString1);
      }
      if (!localFile.canRead()) {
        throw new FFSException("File read access denied: " + paramString1);
      }
      this.jdField_case = new BufferedReader(new FileReader(localFile));
    }
    catch (Exception localException)
    {
      throw new FFSException("Error opening file for read " + paramString1 + ". " + localException.toString());
    }
    if ((paramString2 == null) || (paramString2.length() == 0)) {
      throw new FFSException("Delimiter is null or empty string: " + paramString2);
    }
    this.jdField_try = ("<" + paramString2 + ">");
    this.jdField_for = ("</" + paramString2 + ">");
    this.a = false;
    this.jdField_char = paramBoolean;
    this.jdField_else = new Stack();
    this.jdField_byte = paramString1;
    this.jdField_if = System.getProperty("line.separator");
    jdField_if();
  }
  
  public XMLTokenizer(String paramString1, String paramString2)
    throws FFSException
  {
    this(paramString1, paramString2, false);
  }
  
  public boolean hasMoreTokens()
    throws FFSException
  {
    if (this.a == true) {
      throw new FFSException("Tokenzer is closed.");
    }
    return this.jdField_do;
  }
  
  public String nextToken()
    throws FFSException
  {
    if (this.a == true) {
      throw new FFSException("Tokenzer is closed.");
    }
    String str = null;
    if (this.jdField_new != null) {
      str = this.jdField_new.toString();
    }
    jdField_if();
    if (this.jdField_char == true) {
      str = this.jdField_try + str + this.jdField_for;
    }
    return str;
  }
  
  public void close()
  {
    this.a = true;
    if (this.jdField_case != null) {
      try
      {
        this.jdField_case.close();
      }
      catch (Exception localException) {}
    }
    this.jdField_case = null;
  }
  
  private void jdField_if()
    throws FFSException
  {
    this.jdField_new = new StringBuffer();
    int i = 0;
    int j = 0;
    while (j == 0)
    {
      String str = a();
      if (str == null) {
        break;
      }
      int k = str.indexOf(this.jdField_try);
      int m = str.indexOf(this.jdField_for);
      if ((k != -1) && (m != -1))
      {
        if (k > m) {
          throw new FFSException("End delimiter appears before beginIndex delimiter. Can not process this xml file " + this.jdField_byte);
        }
        if (i == 0)
        {
          this.jdField_new.append(str.substring(k + this.jdField_try.length(), m));
          j = 1;
        }
      }
      else
      {
        int n;
        if (m != -1)
        {
          if (str.lastIndexOf(this.jdField_for) != m) {
            throw new FFSException("Two end delimiters appear in one line. Can not process this xml file " + this.jdField_byte);
          }
          i--;
          if (i == 0)
          {
            n = str.indexOf(this.jdField_for);
            this.jdField_new.append(str.substring(0, n));
            j = 1;
          }
        }
        else if (k != -1)
        {
          if (str.lastIndexOf(this.jdField_try) != k) {
            throw new FFSException("Two begin delimiters appear in one line. Can not process this xml file " + this.jdField_byte);
          }
          i++;
          if (i == 1)
          {
            n = str.indexOf(this.jdField_try);
            this.jdField_new.append(str.substring(n + this.jdField_try.length()));
          }
        }
        else if (i != 0)
        {
          this.jdField_new.append(str);
        }
      }
    }
    if (j == 1)
    {
      this.jdField_do = true;
      return;
    }
    this.jdField_do = false;
    this.jdField_new = null;
  }
  
  private String a()
    throws FFSException
  {
    String str = null;
    try
    {
      str = this.jdField_case.readLine();
      if (str != null) {
        str = str + this.jdField_if;
      }
    }
    catch (IOException localIOException)
    {
      close();
      throw new FFSException(localIOException, "Can't read from file " + this.jdField_byte);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.XMLTokenizer
 * JD-Core Version:    0.7.0.1
 */