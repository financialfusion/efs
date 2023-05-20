package com.ffusion.ims21.util;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class DateCalc
  implements Cloneable
{
  static final String[] jdField_try = { " ", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
  static final String[] jdField_for = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
  static final int[][] jdField_int = { { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 365 }, { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 366 } };
  int jdField_char;
  int jdField_if;
  int jdField_do;
  long jdField_case;
  int jdField_else;
  String jdField_byte;
  int jdField_new;
  String a;
  
  public DateCalc(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    this.jdField_char = localCalendar.get(1);
    this.jdField_if = (localCalendar.get(2) + 1);
    this.jdField_do = localCalendar.get(5);
    this.jdField_case = 0L;
    this.jdField_else = 0;
    jdField_for();
    jdField_do();
  }
  
  public DateCalc(Calendar paramCalendar)
  {
    this.jdField_char = paramCalendar.get(1);
    this.jdField_if = (paramCalendar.get(2) + 1);
    this.jdField_do = paramCalendar.get(5);
    this.jdField_case = 0L;
    this.jdField_else = 0;
    jdField_for();
    jdField_do();
  }
  
  public DateCalc()
  {
    Calendar localCalendar = Calendar.getInstance();
    this.jdField_char = localCalendar.get(1);
    this.jdField_if = (localCalendar.get(2) + 1);
    this.jdField_do = localCalendar.get(5);
    this.jdField_case = 0L;
    this.jdField_else = 0;
    jdField_for();
    jdField_do();
  }
  
  public DateCalc(int paramInt1, int paramInt2, int paramInt3)
  {
    this.jdField_char = paramInt1;
    this.jdField_if = paramInt2;
    this.jdField_do = paramInt3;
    this.jdField_case = 0L;
    this.jdField_else = 0;
    jdField_for();
    jdField_do();
  }
  
  public DateCalc(long paramLong)
  {
    this.jdField_case = paramLong;
    jdField_if();
    jdField_do();
  }
  
  public DateCalc(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "/\\-,.");
    int[] arrayOfInt = new int[3];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 0;
    }
    for (i = 0; (localStringTokenizer.hasMoreTokens()) && (i < 3); i++)
    {
      String str = localStringTokenizer.nextToken();
      int j;
      try
      {
        j = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        j = 0;
      }
      arrayOfInt[i] = j;
    }
    this.jdField_char = arrayOfInt[2];
    this.jdField_if = arrayOfInt[0];
    this.jdField_do = arrayOfInt[1];
    this.jdField_case = 0L;
    this.jdField_else = 0;
    jdField_for();
    jdField_do();
  }
  
  public long add(int paramInt)
  {
    this.jdField_case += paramInt;
    jdField_if();
    jdField_do();
    return this.jdField_case;
  }
  
  public static boolean validateDate(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "/\\-,.");
    int[] arrayOfInt = new int[3];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = 0;
    }
    for (i = 0; (localStringTokenizer.hasMoreTokens()) && (i < 3); i++)
    {
      String str = localStringTokenizer.nextToken();
      int j;
      try
      {
        j = Integer.parseInt(str);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        return false;
      }
      arrayOfInt[i] = j;
    }
    return validateDate(arrayOfInt[2], arrayOfInt[0], arrayOfInt[1]);
  }
  
  public static boolean validateDate(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 <= 0) || (paramInt2 <= 0) || (paramInt3 <= 0)) {
      return false;
    }
    if (paramInt1 < 1998) {
      return false;
    }
    if ((paramInt2 < 1) || (paramInt2 > 12)) {
      return false;
    }
    int i = isLeapYear(paramInt1) ? 1 : 0;
    return paramInt3 <= jdField_int[i][paramInt2];
  }
  
  private void jdField_do()
  {
    this.jdField_byte = jdField_try[this.jdField_if];
    this.jdField_new = ((int)((this.jdField_case + 1L) % 7L));
    this.a = jdField_for[this.jdField_new];
  }
  
  public int getDow()
  {
    return this.jdField_new;
  }
  
  public int getFirstDom()
  {
    DateCalc localDateCalc = new DateCalc(getYear(), getMonth(), 1);
    return localDateCalc.getDow();
  }
  
  private void jdField_if()
  {
    int i = 1901;
    long l = 0L;
    int j;
    for (;;)
    {
      int k = ((i % 4 == 0) && (i % 100 != 0)) || (i % 400 == 0) ? 1 : 0;
      j = k == 1 ? 1 : 0;
      if (jdField_int[j][13] + l > this.jdField_case - 1L)
      {
        this.jdField_char = i;
        break;
      }
      l += jdField_int[j][13];
      i++;
    }
    i = (int)(this.jdField_case - l);
    this.jdField_else = i;
    int m = 1;
    int n = 0;
    while (i - 1 >= jdField_int[j][m] + n)
    {
      n += jdField_int[j][m];
      m++;
    }
    i -= n;
    this.jdField_do = i;
    this.jdField_if = m;
  }
  
  public long getLongDate()
  {
    return this.jdField_case;
  }
  
  public static boolean isLeapYear(int paramInt)
  {
    return ((paramInt % 4 == 0) && (paramInt % 100 != 0)) || (paramInt % 400 == 0);
  }
  
  private void jdField_for()
  {
    for (long l = 1901L; l < this.jdField_char; l += 1L)
    {
      int j = ((l % 4L == 0L) && (l % 100L != 0L)) || (l % 400L == 0L) ? 1 : 0;
      int i = j == 1 ? 1 : 0;
      this.jdField_case += jdField_int[i][13];
    }
    a();
    this.jdField_case += this.jdField_else;
  }
  
  private void a()
  {
    int j = ((this.jdField_char % 4 == 0) && (this.jdField_char % 100 != 0)) || (this.jdField_char % 400 == 0) ? 1 : 0;
    int i = j == 1 ? 1 : 0;
    for (int k = 1; k < this.jdField_if; k++) {
      this.jdField_else += jdField_int[i][k];
    }
    this.jdField_else += this.jdField_do;
  }
  
  public int getDay()
  {
    return this.jdField_do;
  }
  
  public int getMonth()
  {
    return this.jdField_if;
  }
  
  public int getYear()
  {
    return this.jdField_char;
  }
  
  public int getDayOfYear()
  {
    return this.jdField_else;
  }
  
  public String getMonthTitle()
  {
    return this.jdField_byte;
  }
  
  public static String getMonth(int paramInt)
  {
    if (paramInt > jdField_try.length - 1) {
      paramInt = jdField_try.length - 1;
    }
    return jdField_try[paramInt];
  }
  
  public DateCalc getFirstDayOfCurrentWeek()
  {
    if (getWeekOfYear() == 1) {
      return new DateCalc(getYear(), 1, 1);
    }
    int i = getDow();
    long l = this.jdField_case - i;
    return new DateCalc(l);
  }
  
  public DateCalc getLastDayOfCurrentWeek()
  {
    int i = getDow();
    if (getWeekOfYear() == 1) {
      return new DateCalc(getYear(), 1, getDay() + 6 - i);
    }
    long l = this.jdField_case + (6 - i);
    DateCalc localDateCalc = new DateCalc(l);
    if (getYear() < localDateCalc.getYear()) {
      localDateCalc = new DateCalc(getYear(), 12, 31);
    }
    return localDateCalc;
  }
  
  public int getTotalWeeksForYear()
  {
    DateCalc localDateCalc = new DateCalc(getYear(), 12, 31);
    return localDateCalc.getWeekOfYear();
  }
  
  public int getWeekOfYear()
  {
    int i = getDayOfYear() + getYearFirstDow() - 1;
    return i / 7 + 1;
  }
  
  public int getYearFirstDow()
  {
    DateCalc localDateCalc = new DateCalc(getYear(), 1, 1);
    return localDateCalc.getDow();
  }
  
  public int getMonthLastDay()
  {
    int j = ((this.jdField_char % 4 == 0) && (this.jdField_char % 100 != 0)) || (this.jdField_char % 400 == 0) ? 1 : 0;
    int i = j == 1 ? 1 : 0;
    return jdField_int[i][getMonth()];
  }
  
  public String toString()
  {
    NumberFormat localNumberFormat = NumberFormat.getInstance();
    localNumberFormat.setMinimumIntegerDigits(2);
    String str = localNumberFormat.format(this.jdField_if) + "/" + localNumberFormat.format(this.jdField_do) + "/" + String.valueOf(this.jdField_char);
    return str;
  }
  
  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void test()
  {
    System.out.println(String.valueOf(this.jdField_if) + "/" + String.valueOf(this.jdField_do) + "/" + String.valueOf(this.jdField_char));
    System.out.println("CentDay " + String.valueOf(this.jdField_case));
    System.out.println("YearDay " + String.valueOf(this.jdField_else));
    System.out.println("Dow " + String.valueOf(this.jdField_new));
    System.out.println("Month " + this.jdField_byte + " DOW " + this.a);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.ims21.util.DateCalc
 * JD-Core Version:    0.7.0.1
 */