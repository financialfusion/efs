package com.ffusion.beans;

import com.ffusion.fx.FXUtil;
import com.ffusion.util.Filterable;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.Localeable;
import com.ffusion.util.Sortable;
import com.ffusion.util.Stringable;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.LocaleableBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

public class Currency
  extends LocaleableBean
  implements Serializable, Sortable, Filterable, Localeable, Stringable, XMLable, Cloneable, ILocalizable
{
  public static final String VALUE = "VALUE";
  public static final String CURRENCY_CODE = "CURRENCYCODE";
  public static final String CURRENCY = "CURRENCY";
  public static final String SEPARATED = "SEPARATED";
  public static final String DECIMAL = "DECIMAL";
  public static final String MASK = "MASK";
  public static final String DEFAULT_CURRENCY_CODE = "USD";
  private static final double aG = 0.0005D;
  private static final BigDecimal aH = new BigDecimal(0.0D);
  private BigDecimal aF;
  private String aC;
  private String aD = "CURRENCY";
  private static HashMap aE = new HashMap();
  
  public Currency()
  {
    setCurrencyCode("USD");
  }
  
  public Currency(String paramString1, String paramString2, Locale paramLocale, String paramString3)
  {
    super(paramLocale);
    setFormat(paramString3);
    setCurrencyCode(paramString2);
    fromString(paramString1);
    if (this.aF == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public Currency(String paramString1, Locale paramLocale, String paramString2)
  {
    this(paramString1, "USD", paramLocale, paramString2);
  }
  
  public Currency(String paramString1, String paramString2, Locale paramLocale)
  {
    super(paramLocale);
    setCurrencyCode(paramString2);
    fromString(paramString1);
    if (this.aF == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public Currency(String paramString, Locale paramLocale)
  {
    this(paramString, "USD", paramLocale);
  }
  
  public Currency(BigDecimal paramBigDecimal, String paramString, Locale paramLocale)
  {
    super(paramLocale);
    if (paramBigDecimal == null) {
      throw new IllegalArgumentException();
    }
    setCurrencyCode(paramString);
    try
    {
      this.aF = paramBigDecimal.setScale(FXUtil.getNumDecimals(paramString, aE));
    }
    catch (Exception localException)
    {
      this.aF = paramBigDecimal;
    }
  }
  
  public Currency(BigDecimal paramBigDecimal, Locale paramLocale)
  {
    this(paramBigDecimal, "USD", paramLocale);
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.aC = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.aC;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    if (paramBigDecimal == null) {
      throw new IllegalArgumentException();
    }
    this.aF = paramBigDecimal;
  }
  
  public void setAmount(String paramString)
  {
    fromString(paramString);
  }
  
  public void addAmount(BigDecimal paramBigDecimal)
  {
    if (paramBigDecimal == null) {
      throw new IllegalArgumentException();
    }
    if (this.aF == null) {
      this.aF = new BigDecimal(0.0D);
    }
    if (paramBigDecimal.doubleValue() < 0.0D) {
      this.aF = this.aF.subtract(paramBigDecimal.negate());
    } else {
      this.aF = this.aF.add(paramBigDecimal);
    }
  }
  
  public BigDecimal getAmountValue()
  {
    return this.aF;
  }
  
  public void addAmount(Currency paramCurrency)
  {
    if (paramCurrency == null) {
      throw new IllegalArgumentException();
    }
    if (this.aF == null) {
      this.aF = new BigDecimal(0.0D);
    }
    BigDecimal localBigDecimal = paramCurrency.getAmountValue();
    if (localBigDecimal == null) {
      throw new IllegalArgumentException();
    }
    if (localBigDecimal.doubleValue() < 0.0D) {
      this.aF = this.aF.subtract(localBigDecimal.negate());
    } else {
      this.aF = this.aF.add(localBigDecimal);
    }
  }
  
  public int compareTo(Currency paramCurrency)
  {
    if (paramCurrency == null) {
      throw new IllegalArgumentException();
    }
    if (equals(paramCurrency.aF)) {
      return 0;
    }
    if ((this.aF == null) && (paramCurrency.aF == null)) {
      return 0;
    }
    if (this.aF == null) {
      return 1;
    }
    if (paramCurrency.aF == null) {
      return -1;
    }
    return this.aF.compareTo(paramCurrency.aF);
  }
  
  public static int compare(Currency paramCurrency1, Currency paramCurrency2)
  {
    if (paramCurrency1 == paramCurrency2) {
      return 0;
    }
    if (paramCurrency1 == null) {
      return -1;
    }
    if (paramCurrency2 == null) {
      return 1;
    }
    return paramCurrency1.compareTo(paramCurrency2);
  }
  
  public double doubleValue()
  {
    return this.aF.doubleValue();
  }
  
  public boolean isNegative()
  {
    return this.aF.doubleValue() < -0.0005D;
  }
  
  public boolean getIsZero()
  {
    return this.aF.compareTo(aH) == 0;
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
  
  public void setString(String paramString)
  {
    fromString(paramString);
  }
  
  public void fromString(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException();
    }
    if (paramString.length() == 0)
    {
      this.aF = new BigDecimal(0.0D);
      return;
    }
    if ((paramString.indexOf('e') != -1) || (paramString.indexOf('E') != -1)) {
      try
      {
        this.aF = new BigDecimal(paramString);
        return;
      }
      catch (Exception localException1) {}
    }
    try
    {
      DecimalFormat localDecimalFormat = DecimalFormatUtil.getFormatter(this.aD, this.locale, null, this.aC);
      char c1 = localDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator();
      char c2 = localDecimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
      String str = localDecimalFormat.getDecimalFormatSymbols().getCurrencySymbol();
      paramString = Strings.removeChars(paramString, c1);
      paramString = Strings.replaceStr(paramString, str, "");
      int i = paramString.indexOf('(');
      if (i != -1)
      {
        paramString = Strings.removeChars(paramString, '(');
        paramString = "-" + paramString;
      }
      i = paramString.indexOf(')');
      if (i != -1) {
        paramString = Strings.removeChars(paramString, ')');
      }
      if (c2 != '.') {
        paramString = paramString.replace(c2, '.');
      }
      this.aF = new BigDecimal(paramString);
    }
    catch (Exception localException2)
    {
      this.aF = new BigDecimal(0.0D);
    }
  }
  
  public boolean validateCurrency(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException();
    }
    if (paramString.length() != 0) {
      try
      {
        DecimalFormat localDecimalFormat = DecimalFormatUtil.getFormatter(this.aD, this.locale, null, this.aC);
        char c1 = localDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator();
        char c2 = localDecimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
        String str = localDecimalFormat.getDecimalFormatSymbols().getCurrencySymbol();
        paramString = Strings.removeChars(paramString, c1);
        paramString = Strings.replaceStr(paramString, str, "");
        int i = paramString.indexOf('(');
        if (i != -1)
        {
          paramString = Strings.removeChars(paramString, '(');
          paramString = "-" + paramString;
        }
        i = paramString.indexOf(')');
        if (i != -1) {
          paramString = Strings.removeChars(paramString, ')');
        }
        if (c2 != '.') {
          paramString = paramString.replace(c2, '.');
        }
        BigDecimal localBigDecimal = new BigDecimal(paramString);
      }
      catch (Exception localException)
      {
        return false;
      }
    }
    return true;
  }
  
  public String removeCharFromString(String paramString, char paramChar)
  {
    char[] arrayOfChar = paramString.toCharArray();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    for (int j = i; j < arrayOfChar.length; j++) {
      if (arrayOfChar[j] != paramChar) {
        localStringBuffer.append(arrayOfChar[j]);
      }
    }
    return localStringBuffer.toString();
  }
  
  public String removeFormatting(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < arrayOfChar.length; i++) {
      if ((Character.isDigit(arrayOfChar[i])) || (arrayOfChar[i] == '.')) {
        localStringBuffer.append(arrayOfChar[i]);
      }
    }
    return localStringBuffer.toString();
  }
  
  public String getString()
  {
    return toString();
  }
  
  public String toString()
  {
    DecimalFormat localDecimalFormat = DecimalFormatUtil.getFormatter(this.aD, this.locale, null, this.aC);
    String str;
    try
    {
      if (this.aD.equalsIgnoreCase("DECIMAL"))
      {
        localDecimalFormat.setGroupingUsed(false);
        str = localDecimalFormat.format(this.aF, new StringBuffer(), new FieldPosition(1)).toString();
      }
      else
      {
        str = localDecimalFormat.format(this.aF.doubleValue());
      }
    }
    catch (Throwable localThrowable)
    {
      return "";
    }
    return str;
  }
  
  public String getCurrencyString()
  {
    String str = toString();
    StringBuffer localStringBuffer = new StringBuffer(str);
    char c;
    for (int i = 0; i < localStringBuffer.length(); i++)
    {
      c = localStringBuffer.charAt(i);
      if (Character.isDigit(c)) {
        break;
      }
      if (c == '.')
      {
        if ((i != localStringBuffer.length() - 1) && (localStringBuffer.charAt(i + 1) == ' '))
        {
          localStringBuffer.deleteCharAt(i);
          i--;
        }
      }
      else if ((c != '(') && (c != ')') && (c != '+') && (c != '-') && (c != ','))
      {
        localStringBuffer.deleteCharAt(i);
        i--;
      }
    }
    for (i = localStringBuffer.length() - 1; i > -1; i--)
    {
      c = localStringBuffer.charAt(i);
      if (Character.isDigit(c)) {
        break;
      }
      if (c == '.')
      {
        if (i == localStringBuffer.length() - 1) {
          localStringBuffer.deleteCharAt(i);
        }
      }
      else if ((c != '(') && (c != ')') && (c != '+') && (c != '-') && (c != ',')) {
        localStringBuffer.deleteCharAt(i);
      }
    }
    return localStringBuffer.toString();
  }
  
  public String getCurrencyStringNoSymbol()
  {
    String str = toStringAbsolute();
    StringBuffer localStringBuffer = new StringBuffer(str);
    char c;
    for (int i = 0; i < localStringBuffer.length(); i++)
    {
      c = localStringBuffer.charAt(i);
      if (Character.isDigit(c)) {
        break;
      }
      if (c == '.')
      {
        if ((i != localStringBuffer.length() - 1) && (localStringBuffer.charAt(i + 1) == ' '))
        {
          localStringBuffer.deleteCharAt(i);
          i--;
        }
      }
      else if ((c != '+') && (c != '-') && (c != ','))
      {
        localStringBuffer.deleteCharAt(i);
        i--;
      }
    }
    for (i = localStringBuffer.length() - 1; i > -1; i--)
    {
      c = localStringBuffer.charAt(i);
      if (Character.isDigit(c)) {
        break;
      }
      if (c == '.')
      {
        if (i == localStringBuffer.length() - 1) {
          localStringBuffer.deleteCharAt(i);
        }
      }
      else if ((c != '+') && (c != '-') && (c != ',')) {
        localStringBuffer.deleteCharAt(i);
      }
    }
    return localStringBuffer.toString();
  }
  
  public String getCurrencyStringNoSymbol_1()
  {
    String str = toString();
    StringBuffer localStringBuffer = new StringBuffer(str);
    char c;
    for (int i = 0; i < localStringBuffer.length(); i++)
    {
      c = localStringBuffer.charAt(i);
      if ((c != '(') && (c != ')'))
      {
        if (Character.isDigit(c)) {
          break;
        }
        if (c == '.')
        {
          if ((i != localStringBuffer.length() - 1) && (localStringBuffer.charAt(i + 1) == ' '))
          {
            localStringBuffer.deleteCharAt(i);
            i--;
          }
        }
        else if ((c != '+') && (c != '-') && (c != ','))
        {
          localStringBuffer.deleteCharAt(i);
          i--;
        }
      }
    }
    for (i = localStringBuffer.length() - 1; i > -1; i--)
    {
      c = localStringBuffer.charAt(i);
      if ((c != '(') && (c != ')'))
      {
        if (Character.isDigit(c)) {
          break;
        }
        if (c == '.')
        {
          if (i == localStringBuffer.length() - 1) {
            localStringBuffer.deleteCharAt(i);
          }
        }
        else if ((c != '+') && (c != '-') && (c != ',')) {
          localStringBuffer.deleteCharAt(i);
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public String getCurrencyStringNoSymbolNoComma()
  {
    String str = toString();
    StringBuffer localStringBuffer = new StringBuffer(str);
    for (int i = localStringBuffer.length() - 1; i > -1; i--)
    {
      char c = localStringBuffer.charAt(i);
      if ((!Character.isDigit(c)) || (c == ',')) {
        if (c == '.')
        {
          if (i == localStringBuffer.length() - 1) {
            localStringBuffer.deleteCharAt(i);
          }
        }
        else if ((c != '+') && (c != '-')) {
          localStringBuffer.deleteCharAt(i);
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  public String toStringAbsolute()
  {
    DecimalFormat localDecimalFormat = DecimalFormatUtil.getFormatter(this.aD, this.locale, null, this.aC);
    return localDecimalFormat.format(this.aF.abs().doubleValue());
  }
  
  public String getStringAbsolute()
  {
    return String.valueOf(this.aF.abs().doubleValue());
  }
  
  public void setFormat(String paramString)
  {
    if (paramString == null) {
      return;
    }
    if (paramString.equalsIgnoreCase("CURRENCY")) {
      this.aD = "CURRENCY";
    } else if (paramString.equalsIgnoreCase("SEPARATED")) {
      this.aD = "SEPARATED";
    } else if (paramString.equalsIgnoreCase("DECIMAL")) {
      this.aD = "DECIMAL";
    } else {
      this.aD = "MASK";
    }
    DecimalFormat localDecimalFormat = DecimalFormatUtil.getFormatter(this.aD, this.locale, paramString, this.aC);
  }
  
  public String getFormat()
  {
    return this.aD;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    return compareTo((Currency)paramObject);
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    String str1 = null;
    String str2 = null;
    String str3 = null;
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      str1 = localStringTokenizer.nextToken();
      str2 = localStringTokenizer.nextToken();
      str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
    }
    else
    {
      str2 = "=";
      str3 = paramString;
    }
    try
    {
      Currency localCurrency = new Currency(str3, this.locale, this.aD);
      int i = compareTo(localCurrency);
      if ((str2.equals("=")) || (str2.equals("=="))) {
        return i == 0;
      }
      if ((str2.equals("!")) || (str2.equals("!!"))) {
        return i != 0;
      }
      if ((str2.equals("<")) || (str2.equals("<<"))) {
        return i == -1;
      }
      if ((str2.equals(">")) || (str2.equals(">>"))) {
        return i == 1;
      }
      if ((str2.equals("=>")) || (str2.equals(">="))) {
        return i >= 0;
      }
      if ((str2.equals("=<")) || (str2.equals("<="))) {
        return i <= 0;
      }
    }
    catch (Exception localException) {}
    return false;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = false;
    if (this == paramObject)
    {
      bool = true;
    }
    else if (this.aF != null)
    {
      BigDecimal localBigDecimal = null;
      Locale localLocale = null;
      if ((paramObject instanceof Currency))
      {
        localBigDecimal = ((Currency)paramObject).getAmountValue();
        localLocale = ((Currency)paramObject).locale;
      }
      else if ((paramObject instanceof BigDecimal))
      {
        localBigDecimal = (BigDecimal)paramObject;
      }
      if ((localBigDecimal != null) && (Math.abs(this.aF.subtract(localBigDecimal).doubleValue()) < 0.0005D))
      {
        bool = true;
        if ((localLocale != null) && (!localLocale.equals(this.locale))) {
          bool = false;
        }
      }
    }
    return bool;
  }
  
  public boolean equals(double paramDouble)
  {
    return equals(new BigDecimal(Double.toString(paramDouble)));
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(null), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CURRENCY");
    XMLHandler.appendTag(localStringBuffer, "CURRENCYCODE", this.aC);
    XMLHandler.appendTag(localStringBuffer, "VALUE", this.aF.toString());
    XMLHandler.appendEndTag(localStringBuffer, "CURRENCY");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler(null));
  }
  
  public static boolean isValid(String paramString, Locale paramLocale)
  {
    if (paramString == null) {
      return false;
    }
    if ((paramString.startsWith("+") == true) || (paramString.startsWith("-") == true))
    {
      paramString = paramString.substring(1);
    }
    else if (paramString.startsWith("(") == true)
    {
      int i = paramString.lastIndexOf(")");
      if (i + 1 != paramString.length()) {
        return false;
      }
      paramString = paramString.substring(1, i);
    }
    DecimalFormat localDecimalFormat = DecimalFormatUtil.getFormatter(null, paramLocale, null);
    char c1 = localDecimalFormat.getDecimalFormatSymbols().getGroupingSeparator();
    char c2 = localDecimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
    for (int j = 0; j < paramString.length(); j++)
    {
      char c3 = paramString.charAt(j);
      if (Character.isDigit(c3) != true)
      {
        if (Character.isLetter(c3) == true) {
          return false;
        }
        if ((c3 != c1) && (c3 != c2)) {
          return false;
        }
      }
    }
    j = localDecimalFormat.getMaximumFractionDigits();
    return a(paramString, c2, j);
  }
  
  private static boolean a(String paramString, char paramChar, int paramInt)
  {
    int i = paramString.indexOf(paramChar);
    if (i == -1) {
      return true;
    }
    int j = paramString.length();
    if (j - 1 - i > paramInt) {
      return false;
    }
    int k = paramString.lastIndexOf(paramChar);
    if (i != k) {
      return false;
    }
    for (int m = i + 1; m < j; m++) {
      if (!Character.isDigit(paramString.charAt(m))) {
        return false;
      }
    }
    return true;
  }
  
  public Object localize(Locale paramLocale)
  {
    DecimalFormat localDecimalFormat = DecimalFormatUtil.getFormatter(this.aD, paramLocale, null, this.aC);
    String str;
    try
    {
      if (this.aD.equalsIgnoreCase("DECIMAL")) {
        try
        {
          str = localDecimalFormat.parse(localDecimalFormat.format(this.aF.doubleValue())).toString();
        }
        catch (ParseException localParseException)
        {
          str = localDecimalFormat.format(this.aF.doubleValue());
        }
      } else {
        str = localDecimalFormat.format(this.aF.doubleValue());
      }
    }
    catch (Throwable localThrowable)
    {
      return "";
    }
    return str;
  }
  
  private class InternalXMLHandler
    extends XMLHandler
  {
    private InternalXMLHandler() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if ((str.length() > 0) && (getElement().equals("VALUE"))) {
        Currency.this.fromString(str);
      } else if ((str.length() > 0) && (getElement().equals("CURRENCYCODE"))) {
        Currency.this.setCurrencyCode(str);
      }
    }
    
    InternalXMLHandler(Currency.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.beans.Currency
 * JD-Core Version:    0.7.0.1
 */