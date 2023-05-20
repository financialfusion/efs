package com.ffusion.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.CollationKey;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Strings
  implements Serializable
{
  public static final char EQUAL = '=';
  public static final char LESS_THAN = '<';
  public static final char GREATER_THAN = '>';
  public static final char SPACE = ' ';
  public static final char QUOTE = '"';
  public static final char PERIOD = '.';
  
  public static int getLastNonSpaceCharIndex(String paramString, int paramInt1, int paramInt2)
  {
    int i = paramInt2;
    while (Character.isWhitespace(paramString.charAt(i - 1)))
    {
      i--;
      if (i <= paramInt1) {
        i = paramInt2;
      }
    }
    return i;
  }
  
  public static String trimChars(String paramString, char paramChar)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      int i = 0;
      int j = paramString.length();
      while ((i < j) && (paramString.charAt(i) == paramChar)) {
        i++;
      }
      if (i > 0)
      {
        if (i == j) {
          paramString = "";
        } else {
          paramString = paramString.substring(i);
        }
        j = paramString.length();
      }
      for (i = j - 1; paramString.charAt(i) == paramChar; i--) {}
      if (i != j - 1) {
        paramString = paramString.substring(0, i + 1);
      }
    }
    return paramString;
  }
  
  public static String removeChars(String paramString, char paramChar)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      for (int i = paramString.indexOf(paramChar); i != -1; i = paramString.indexOf(paramChar)) {
        paramString = paramString.substring(0, i) + paramString.substring(i + 1);
      }
    }
    return paramString;
  }
  
  public static String streamToString(InputStream paramInputStream)
    throws IOException
  {
    int i = 4096;
    StringBuffer localStringBuffer = new StringBuffer();
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream), 4096);
    try
    {
      char[] arrayOfChar = new char[4096];
      int j = 0;
      do
      {
        j = localBufferedReader.read(arrayOfChar, 0, 4096);
        if (j != -1) {
          localStringBuffer.append(arrayOfChar, 0, j);
        }
      } while (j != -1);
    }
    catch (IOException localIOException1)
    {
      try
      {
        localBufferedReader.close();
      }
      catch (IOException localIOException2) {}
      throw localIOException1;
    }
    localBufferedReader.close();
    return localStringBuffer.toString();
  }
  
  public static String streamToString(InputStream paramInputStream, String paramString)
    throws IOException
  {
    int i = 4096;
    StringBuffer localStringBuffer = new StringBuffer();
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, paramString), 4096);
    try
    {
      char[] arrayOfChar = new char[4096];
      int j = 0;
      do
      {
        j = localBufferedReader.read(arrayOfChar, 0, 4096);
        if (j != -1) {
          localStringBuffer.append(arrayOfChar, 0, j);
        }
      } while (j != -1);
    }
    catch (IOException localIOException1)
    {
      try
      {
        localBufferedReader.close();
      }
      catch (IOException localIOException2) {}
      throw localIOException1;
    }
    localBufferedReader.close();
    return localStringBuffer.toString();
  }
  
  public static boolean isInStringArray(String paramString, String[] paramArrayOfString)
  {
    boolean bool = false;
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramString.equalsIgnoreCase(paramArrayOfString[i]))
      {
        bool = true;
        break;
      }
    }
    return bool;
  }
  
  public static String replaceStr(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = paramString1.indexOf(paramString2);
    if (j == -1) {
      return paramString1;
    }
    do
    {
      localStringBuffer.append(paramString1.substring(i, j) + paramString3);
      i = j + paramString2.length();
      j = paramString1.indexOf(paramString2, i);
    } while (j != -1);
    localStringBuffer.append(paramString1.substring(i));
    return localStringBuffer.toString();
  }
  
  public static int countNumbers(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    for (int k = 0; k < j; k++) {
      if (Character.isDigit(paramString.charAt(k))) {
        i++;
      }
    }
    return i;
  }
  
  public static int countLetters(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    for (int k = 0; k < j; k++) {
      if (Character.isLetter(paramString.charAt(k))) {
        i++;
      }
    }
    return i;
  }
  
  public static boolean isAsciiChar(char paramChar)
  {
    char c = '';
    return paramChar / c == 0;
  }
  
  public static boolean verifyStringWithNonAscii(String paramString)
  {
    int i = paramString.length();
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      j++;
      if (!isAsciiChar(paramString.charAt(k))) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean hasSpecialChars(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++) {
      if (!Character.isLetterOrDigit(paramString.charAt(j))) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isValidAccountNumber(String paramString)
  {
    return isValidAccountNumber(paramString, null);
  }
  
  public static boolean isValidAccountNumber(String paramString, Locale paramLocale)
  {
    int i = paramString.length();
    ResourceBundle localResourceBundle = null;
    String str = "";
    if (paramLocale != null)
    {
      localResourceBundle = ResourceUtil.getBundle("com.ffusion.beans.accounts.resources", paramLocale);
      try
      {
        str = localResourceBundle.getString("ExtraAccountValidationChars");
      }
      catch (Exception localException)
      {
        localResourceBundle = null;
      }
    }
    int j;
    if (localResourceBundle != null) {
      for (j = 0; j < i; j++) {
        if ((!Character.isLetterOrDigit(paramString.charAt(j))) && (str.indexOf(paramString.charAt(j)) < 0)) {
          return false;
        }
      }
    } else {
      for (j = 0; j < i; j++) {
        if ((!Character.isLetterOrDigit(paramString.charAt(j))) && (' ' != paramString.charAt(j)) && ('-' != paramString.charAt(j))) {
          return false;
        }
      }
    }
    return true;
  }
  
  public static boolean isValidACHAlphamericString(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if (!Character.isLetterOrDigit(c)) {
        switch (c)
        {
        case ' ': 
        case '!': 
        case '"': 
        case '#': 
        case '$': 
        case '%': 
        case '&': 
        case '\'': 
        case '(': 
        case ')': 
        case '*': 
        case '+': 
        case ',': 
        case '-': 
        case '.': 
        case '/': 
        case ':': 
        case ';': 
        case '<': 
        case '=': 
        case '>': 
        case '?': 
        case '@': 
        case '[': 
        case '\\': 
        case ']': 
        case '^': 
        case '_': 
        case '`': 
        case '{': 
        case '|': 
        case '}': 
        case '~': 
        case '£': 
        case '¥': 
          break;
        case '0': 
        case '1': 
        case '2': 
        case '3': 
        case '4': 
        case '5': 
        case '6': 
        case '7': 
        case '8': 
        case '9': 
        case 'A': 
        case 'B': 
        case 'C': 
        case 'D': 
        case 'E': 
        case 'F': 
        case 'G': 
        case 'H': 
        case 'I': 
        case 'J': 
        case 'K': 
        case 'L': 
        case 'M': 
        case 'N': 
        case 'O': 
        case 'P': 
        case 'Q': 
        case 'R': 
        case 'S': 
        case 'T': 
        case 'U': 
        case 'V': 
        case 'W': 
        case 'X': 
        case 'Y': 
        case 'Z': 
        case 'a': 
        case 'b': 
        case 'c': 
        case 'd': 
        case 'e': 
        case 'f': 
        case 'g': 
        case 'h': 
        case 'i': 
        case 'j': 
        case 'k': 
        case 'l': 
        case 'm': 
        case 'n': 
        case 'o': 
        case 'p': 
        case 'q': 
        case 'r': 
        case 's': 
        case 't': 
        case 'u': 
        case 'v': 
        case 'w': 
        case 'x': 
        case 'y': 
        case 'z': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case '': 
        case ' ': 
        case '¡': 
        case '¢': 
        case '¤': 
        default: 
          return false;
        }
      }
    }
    return true;
  }
  
  public static int compareTo(CollationKey paramCollationKey1, CollationKey paramCollationKey2)
  {
    int i = 0;
    if ((paramCollationKey1 != null) && (paramCollationKey2 != null)) {
      i = paramCollationKey1.compareTo(paramCollationKey2);
    } else if ((paramCollationKey1 == null) && (paramCollationKey2 == null)) {
      i = 0;
    } else if (paramCollationKey1 == null) {
      i = -1;
    } else {
      i = 1;
    }
    return i;
  }
  
  public static int compareTo(String paramString1, String paramString2)
  {
    int i = 0;
    if ((paramString1 != null) && (paramString2 != null)) {
      i = paramString1.compareTo(paramString2);
    } else if ((paramString1 == null) && (paramString2 == null)) {
      i = 0;
    } else if (paramString1 == null) {
      i = -1;
    } else {
      i = 1;
    }
    return i;
  }
  
  public static int compareToIgnoreCase(String paramString1, String paramString2)
  {
    int i = 0;
    if ((paramString1 != null) && (paramString2 != null)) {
      i = paramString1.compareToIgnoreCase(paramString2);
    } else if ((paramString1 == null) && (paramString2 == null)) {
      i = 0;
    } else if (paramString1 == null) {
      i = -1;
    } else {
      i = 1;
    }
    return i;
  }
  
  public static boolean areStringsEqual(String paramString1, String paramString2)
  {
    boolean bool = false;
    if ((paramString1 == null) && (paramString2 == null)) {
      bool = true;
    } else if ((paramString1 == null) || (paramString2 == null)) {
      bool = false;
    } else {
      bool = paramString1.equals(paramString2);
    }
    return bool;
  }
  
  public static String sprintf(String paramString, a parama)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString.length() * 3);
    int i = 0;
    for (int j = 0; j < parama.a(); j++)
    {
      int k = paramString.indexOf("%s", i);
      if (k > -1)
      {
        localStringBuffer.append(paramString.substring(i, k)).append(parama.jdMethod_if(j));
        i = k + 2;
      }
    }
    if (i < paramString.length()) {
      localStringBuffer.append(paramString.substring(i));
    }
    return localStringBuffer.toString();
  }
  
  public static void printf(String paramString, a parama)
  {
    System.out.println(sprintf(paramString, parama));
  }
  
  public static a args()
  {
    return new a(10, null);
  }
  
  public static ArrayList breakupString(String paramString, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = paramInt;
    int k = paramString.length();
    int m = 0;
    if (k < paramInt) {
      j = k;
    }
    while (i < k)
    {
      String str = paramString.substring(i, j);
      m = j;
      for (int n = str.length() - 1; (n >= 0) && (Character.isWhitespace(str.charAt(n))); n--) {
        m -= 1;
      }
      if ((m != i) && (j != m))
      {
        j = m;
        str = paramString.substring(i, j);
      }
      i = j;
      if (k > i + paramInt) {
        j = i + paramInt;
      } else {
        j = k;
      }
      localArrayList.add(str);
    }
    return localArrayList;
  }
  
  public static class a
  {
    private ArrayList a;
    
    private a(int paramInt)
    {
      this.a = new ArrayList(paramInt);
    }
    
    public int a()
    {
      return this.a.size();
    }
    
    public Object jdMethod_if(int paramInt)
    {
      return this.a.get(paramInt);
    }
    
    public a a(String paramString)
    {
      this.a.add(paramString);
      return this;
    }
    
    public a a(int paramInt)
    {
      this.a.add(new Integer(paramInt));
      return this;
    }
    
    public a a(long paramLong)
    {
      this.a.add(new Long(paramLong));
      return this;
    }
    
    public a a(short paramShort)
    {
      this.a.add(new Short(paramShort));
      return this;
    }
    
    public a a(float paramFloat)
    {
      this.a.add(new Float(paramFloat));
      return this;
    }
    
    public a a(double paramDouble)
    {
      this.a.add(new Double(paramDouble));
      return this;
    }
    
    public a a(boolean paramBoolean)
    {
      this.a.add(new Boolean(paramBoolean));
      return this;
    }
    
    public a a(Object paramObject)
    {
      this.a.add(paramObject);
      return this;
    }
    
    a(int paramInt, Strings.1 param1)
    {
      this(paramInt);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.Strings
 * JD-Core Version:    0.7.0.1
 */