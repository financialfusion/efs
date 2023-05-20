package com.ffusion.beans.util;

import java.util.ArrayList;

public class Paging
  extends ArrayList
{
  private int jdField_if = 0;
  private int jdField_new = 0;
  private int jdField_int = 0;
  private int jdField_do = 0;
  private int jdField_char = 0;
  private int jdField_byte = 0;
  private int jdField_case = 0;
  private boolean jdField_try = false;
  private boolean jdField_for = false;
  private boolean a = false;
  
  protected void calculate()
  {
    clear();
    int i;
    if (this.a)
    {
      if (this.jdField_if > 0)
      {
        i = 0;
        if (this.jdField_for) {
          i++;
        }
        if (this.jdField_try) {
          i++;
        }
        int j = this.jdField_do + this.jdField_char + (this.jdField_if - 1) * i;
        this.jdField_int = (j / this.jdField_if);
        if (j % this.jdField_if != 0) {
          this.jdField_int += 1;
        }
        for (int k = 1; k <= this.jdField_if; k++) {
          add(String.valueOf(k));
        }
      }
    }
    else
    {
      i = this.jdField_do + this.jdField_char;
      this.jdField_if = 0;
      while (i > 0)
      {
        this.jdField_if += 1;
        add(String.valueOf(this.jdField_if));
        if ((this.jdField_for) && (this.jdField_if != 1)) {
          i++;
        }
        if ((this.jdField_try) && (i > this.jdField_int)) {
          i++;
        }
        i -= this.jdField_int;
      }
      setPage(0);
    }
  }
  
  protected static int convertToInt(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 0;
    }
    return i;
  }
  
  public void setAllowForNext(String paramString)
  {
    this.jdField_try = Boolean.valueOf(paramString).booleanValue();
    calculate();
  }
  
  public String getAllowForNext()
  {
    return String.valueOf(this.jdField_try);
  }
  
  public String getHasNext()
  {
    return String.valueOf((this.jdField_try) && (this.jdField_new + 1 < this.jdField_if));
  }
  
  public void setAllowForPrev(String paramString)
  {
    this.jdField_for = Boolean.valueOf(paramString).booleanValue();
    calculate();
  }
  
  public String getAllowForPrev()
  {
    return String.valueOf(this.jdField_for);
  }
  
  public String getHasPrev()
  {
    return String.valueOf((this.jdField_for) && (this.jdField_new != 0));
  }
  
  public String getHasNextPage()
  {
    return String.valueOf(this.jdField_new + 1 < this.jdField_if);
  }
  
  public String getHasPrevPage()
  {
    return String.valueOf(this.jdField_new != 0);
  }
  
  public void setPages(String paramString)
  {
    this.jdField_if = convertToInt(paramString);
    if (this.jdField_if == 0) {
      this.a = false;
    } else {
      this.a = true;
    }
    calculate();
  }
  
  public String getPages()
  {
    return String.valueOf(this.jdField_if);
  }
  
  protected void setPage(int paramInt)
  {
    this.jdField_new = paramInt;
    if (this.jdField_new < 0) {
      this.jdField_new = 0;
    } else if (this.jdField_new > this.jdField_if) {
      this.jdField_new = (this.jdField_if - 1);
    }
    this.jdField_byte = 1;
    for (int i = 0; i < this.jdField_new; i++)
    {
      this.jdField_byte += this.jdField_int;
      if ((this.jdField_for) && (i != 0)) {
        this.jdField_byte -= 1;
      }
      if (this.jdField_try) {
        this.jdField_byte -= 1;
      }
    }
    if (this.jdField_new != 0) {
      this.jdField_byte -= this.jdField_char;
    }
    this.jdField_case = (this.jdField_byte + this.jdField_int - 1);
    if ((this.jdField_for) && (this.jdField_new != 0)) {
      this.jdField_case -= 1;
    }
    if ((this.jdField_try) && (this.jdField_new + 1 < this.jdField_if)) {
      this.jdField_case -= 1;
    }
    if (this.jdField_new == 0) {
      this.jdField_case -= this.jdField_char;
    }
    if (this.jdField_case > this.jdField_do) {
      this.jdField_case = this.jdField_do;
    }
  }
  
  public void setPage(String paramString)
  {
    setPage(convertToInt(paramString) - 1);
  }
  
  public String getPage()
  {
    return String.valueOf(this.jdField_new + 1);
  }
  
  public void setAdjustPage(String paramString)
  {
    setPage(this.jdField_new + convertToInt(paramString));
  }
  
  public void setItemsPerPage(String paramString)
  {
    this.jdField_int = convertToInt(paramString);
    if (this.jdField_int < 1) {
      this.jdField_int = 1;
    }
    calculate();
  }
  
  public String getItemsPerPage()
  {
    return String.valueOf(this.jdField_int);
  }
  
  public void setNumberItems(String paramString)
  {
    this.jdField_do = convertToInt(paramString);
    calculate();
  }
  
  public String getNumberItems()
  {
    return String.valueOf(this.jdField_do);
  }
  
  public void setAdditionalItems(String paramString)
  {
    this.jdField_char = convertToInt(paramString);
    calculate();
  }
  
  public String getAdditionalItems()
  {
    return String.valueOf(this.jdField_char);
  }
  
  public String getStartIndex()
  {
    return String.valueOf(this.jdField_byte);
  }
  
  public String getEndIndex()
  {
    return String.valueOf(this.jdField_case);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.Paging
 * JD-Core Version:    0.7.0.1
 */