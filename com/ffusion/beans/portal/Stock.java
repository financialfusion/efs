package com.ffusion.beans.portal;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Stock
  extends ExtendABean
  implements Serializable, Comparable
{
  public static final String STOCK = "STOCK";
  public static final String STOCK_NEWS = "NEWS";
  public static final String SHARES = "SHARES";
  public static final String PURCHASE_PRICE = "PURCHASE_PRICE";
  public static final String ASK_PRICE = "ASKPRICE";
  public static final String ASK_SIZE = "ASKSIZE";
  public static final String BID_PRICE = "BIDPRICE";
  public static final String BID_SIZE = "BIDSIZE";
  public static final String CLOSE_PRICE = "CLOSEPRICE";
  public static final String CLOSE_TICK = "CLOSETICK";
  public static final String COMPANY_NAME = "COMPANYNAME";
  public static final String COUNTRY = "COUNTRY";
  public static final String DIVIDEND = "DIVIDEND";
  public static final String DIVIDEND_FREQUENCY = "DIVIDENDFREQUENCY";
  public static final String EARNINGS_PER_SHARE = "EARNINGSPERSHARE";
  public static final String EXDIVIDEND_DATE = "EXDIVIDENDDATE";
  public static final String ITEM_EXCHANGE_CODE = "ITEMEXCHANGECODE";
  public static final String LAST_SALE_PRICE = "LASTSALEPRICE";
  public static final String LAST_SALE_SIZE = "LASTSALESIZE";
  public static final String LAST_SALE_TIME = "LASTSALETIME";
  public static final String HIGH_PRICE = "HIGHPRICE";
  public static final String LOW_PRICE = "LOWPRICE";
  public static final String CHANGE_PERCENT = "CHANGEPERCENT";
  public static final String CHANGE_PRICE = "CHANGEPRICE";
  public static final String OPEN_PRICE = "OPENPRICE";
  public static final String OPEN_TICK = "OPENTICK";
  public static final String PE_RATIO = "PERATIO";
  public static final String SHARES_OUTSTANDING = "SHARESOUTSTANDING";
  public static final String SYMBOL = "SYMBOL";
  public static final String VOLATILITY = "VOLATILITY";
  public static final String VOLUME = "VOLUME";
  public static final String YEAR_HIGH = "YEARHIGH";
  public static final String YEAR_LOW = "YEARLOW";
  public static final String YIELD = "YIELD";
  public static final String NEWS = "NEWS";
  private String lK = "";
  private String lo = "";
  private String lN = "";
  private String lS = "";
  private String l2 = "";
  private String lx = "";
  private String l3 = "";
  private String lT = "";
  private String lL = "";
  private String lI = "";
  private String lr = "";
  private String lp = "";
  private String lv = "";
  private String lz = "";
  private String lq = "";
  private String lw = "";
  private String lP = "";
  private String lt = "";
  private String lR = "";
  private String ly = "";
  private String lJ = "";
  private String lu = "";
  private String lO = "";
  private String ls = "";
  private String lC = "";
  private String l0 = "";
  private String lH = "";
  private String lF = "";
  private String lX = "";
  private String lU = "";
  private int lY = 0;
  private boolean lE = false;
  private String lW = "";
  private String lB = "";
  private String l1 = "";
  private String lV = "";
  private double lD = 0.0D;
  private String lQ = "";
  private double lM = 0.0D;
  private String lA = "";
  private double lZ = 0.0D;
  private NumberFormat lG = null;
  
  public Stock()
  {
    this.lG.setGroupingUsed(true);
  }
  
  public int getCounter()
  {
    return this.lY;
  }
  
  public void setCounter(int paramInt)
  {
    this.lY = paramInt;
  }
  
  public String getAskPrice()
  {
    return this.lK;
  }
  
  public void setAskPrice(String paramString)
  {
    this.lK = paramString;
  }
  
  public String getAskSize()
  {
    return this.lK;
  }
  
  public void setAskSize(String paramString)
  {
    this.lo = paramString;
  }
  
  public String getBidPrice()
  {
    return this.lN;
  }
  
  public void setBidPrice(String paramString)
  {
    this.lN = paramString;
  }
  
  public String getBidSize()
  {
    return this.lS;
  }
  
  public void setBidSize(String paramString)
  {
    this.lS = paramString;
  }
  
  public String getClosePrice()
  {
    return this.l2;
  }
  
  public void setClosePrice(String paramString)
  {
    this.l2 = paramString;
  }
  
  public String getCloseTick()
  {
    return this.lx;
  }
  
  public void setCloseTick(String paramString)
  {
    this.lx = paramString;
  }
  
  public String getCompanyName()
  {
    return this.l3;
  }
  
  public void setCompanyName(String paramString)
  {
    this.l3 = paramString;
  }
  
  public String getCountry()
  {
    return this.lT;
  }
  
  public void setCountry(String paramString)
  {
    this.lT = paramString;
  }
  
  public String getDividend()
  {
    return this.lL;
  }
  
  public void setDividend(String paramString)
  {
    this.lL = paramString;
  }
  
  public String getDividendFrequency()
  {
    return this.lI;
  }
  
  public void setDividendFrequency(String paramString)
  {
    this.lI = paramString;
  }
  
  public String getEarningsPerShare()
  {
    return this.lr;
  }
  
  public void setEarningsPerShare(String paramString)
  {
    this.lr = paramString;
  }
  
  public String getExdividendDate()
  {
    return this.lp;
  }
  
  public void setExdividendDate(String paramString)
  {
    this.lp = paramString;
  }
  
  public String getHighPrice()
  {
    return this.lP;
  }
  
  public void setHighPrice(String paramString)
  {
    this.lP = paramString;
  }
  
  public String getItemExchangeCode()
  {
    return this.lv;
  }
  
  public void setItemExchangeCode(String paramString)
  {
    this.lv = paramString;
  }
  
  public String getLastSalePrice()
  {
    return this.lz;
  }
  
  public void setLastSalePrice(String paramString)
  {
    this.lz = paramString;
  }
  
  public String getLastSaleSize()
  {
    return this.lq;
  }
  
  public void setLastSaleSize(String paramString)
  {
    this.lq = paramString;
  }
  
  public String getLastSaleTime()
  {
    return this.lw;
  }
  
  public void setLastSaleTime(String paramString)
  {
    this.lw = paramString;
  }
  
  public String getLowPrice()
  {
    return this.lt;
  }
  
  public void setLowPrice(String paramString)
  {
    this.lt = paramString;
  }
  
  public String getChangePercent()
  {
    return this.lR;
  }
  
  public void setChangePercent(String paramString)
  {
    this.lR = paramString;
  }
  
  public String getChangePrice()
  {
    return this.ly;
  }
  
  public void setChangePrice(String paramString)
  {
    this.ly = paramString;
  }
  
  public String getOpenPrice()
  {
    return this.lJ;
  }
  
  public void setOpenPrice(String paramString)
  {
    this.lJ = paramString;
  }
  
  public String getOpenTick()
  {
    return this.lu;
  }
  
  public void setOpenTick(String paramString)
  {
    this.lu = paramString;
  }
  
  public String getPeRatio()
  {
    return this.lO;
  }
  
  public void setPeRatio(String paramString)
  {
    this.lO = paramString;
  }
  
  public String getSharesOutstanding()
  {
    if ((this.ls != null) && (this.ls.length() > 0)) {
      return this.lG.format(getSharesOutstandingDouble());
    }
    return this.ls;
  }
  
  public double getSharesOutstandingDouble()
  {
    return Double.parseDouble(this.ls);
  }
  
  public void setSharesOutstanding(String paramString)
  {
    this.ls = paramString;
  }
  
  public String getSymbol()
  {
    return this.lC;
  }
  
  public void setSymbol(String paramString)
  {
    this.lC = paramString;
  }
  
  public String getVolatility()
  {
    return this.l0;
  }
  
  public void setVolatility(String paramString)
  {
    this.l0 = paramString;
  }
  
  public String getVolume()
  {
    if ((this.lH != null) && (this.lH.length() > 0)) {
      return this.lG.format(Double.parseDouble(this.lH));
    }
    return this.lH;
  }
  
  public void setVolume(String paramString)
  {
    this.lH = paramString;
  }
  
  public String getYearHigh()
  {
    return this.lF;
  }
  
  public void setYearHigh(String paramString)
  {
    this.lF = paramString;
  }
  
  public String getYearLow()
  {
    return this.lX;
  }
  
  public void setYearLow(String paramString)
  {
    this.lX = paramString;
  }
  
  public String getYield()
  {
    return this.lU;
  }
  
  public void setYield(String paramString)
  {
    this.lU = paramString;
  }
  
  public String getShares()
  {
    return this.lB;
  }
  
  public void setShares(String paramString)
  {
    this.lB = paramString;
  }
  
  public String getPurchasePrice()
  {
    return this.l1;
  }
  
  public void setPurchasePrice(String paramString)
  {
    this.l1 = paramString;
  }
  
  public String getPosition()
  {
    calculateTotals();
    return this.lV;
  }
  
  public double getPositionDouble()
  {
    calculateTotals();
    return this.lD;
  }
  
  public void setPosition(String paramString)
  {
    this.lV = paramString;
  }
  
  public String getCurrentValue()
  {
    calculateTotals();
    return this.lQ;
  }
  
  public double getCurrentValueDouble()
  {
    calculateTotals();
    return this.lM;
  }
  
  public void setCurrentValue(String paramString)
  {
    this.lQ = paramString;
  }
  
  public boolean getNewsFlag()
  {
    return this.lE;
  }
  
  public void setNewsFlag(boolean paramBoolean)
  {
    this.lE = paramBoolean;
  }
  
  public String getNews()
  {
    return this.lW;
  }
  
  public void setNews(String paramString)
  {
    this.lW = paramString;
  }
  
  public String getMarketCap()
  {
    calculateTotals();
    return this.lA;
  }
  
  public double getMarketCapDouble()
  {
    calculateTotals();
    return this.lZ;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (paramLocale != null)
    {
      this.lG = NumberFormat.getNumberInstance(paramLocale);
      this.lG.setGroupingUsed(true);
    }
  }
  
  public void calculateTotals()
  {
    NumberFormat localNumberFormat1 = NumberFormat.getNumberInstance(new Locale("en", "US"));
    localNumberFormat1.setGroupingUsed(false);
    localNumberFormat1.setMaximumFractionDigits(2);
    localNumberFormat1.setMinimumFractionDigits(2);
    NumberFormat localNumberFormat2 = NumberFormat.getCurrencyInstance(this.locale);
    try
    {
      double d1 = getDoubleValue(this.lB);
      double d2 = getDoubleValue(this.l1);
      double d3 = d1 * d2;
      double d4 = getDoubleValue(this.lz);
      this.lM = (d1 * d4);
      this.lD = (this.lM - d3);
      this.lD = new Double(localNumberFormat1.format(this.lD)).doubleValue();
      this.lV = localNumberFormat2.format(this.lD);
      this.lM = new Double(localNumberFormat1.format(this.lM)).doubleValue();
      this.lQ = localNumberFormat2.format(this.lM);
      double d5 = Double.parseDouble(this.ls);
      this.lZ = (d5 * d4);
      this.lA = localNumberFormat2.format(this.lZ);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  protected double getDoubleValue(String paramString)
  {
    double d1 = 0.0D;
    if (paramString.equals("")) {
      paramString = "0";
    }
    if (paramString.indexOf("/") != -1)
    {
      if (paramString.indexOf(" ") == -1) {
        paramString = "0 " + paramString;
      }
      String str1 = paramString.substring(0, paramString.indexOf(" "));
      int i = Integer.parseInt(str1);
      String str2 = paramString.substring(paramString.indexOf(" ") + 1);
      String str3 = str2.substring(0, str2.indexOf("/"));
      double d2 = Double.valueOf(str3).doubleValue();
      String str4 = str2.substring(str2.indexOf("/") + 1);
      double d3 = Double.valueOf(str4).doubleValue();
      double d4 = d2 / d3;
      d1 = i + d4;
    }
    else
    {
      d1 = Double.valueOf(paramString).doubleValue();
    }
    return d1;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.lE)
    {
      XMLHandler.openTag(localStringBuffer, "STOCK");
      XMLHandler.appendAttribute(localStringBuffer, "NEWS", "TRUE");
      XMLHandler.closeTag(localStringBuffer, false);
    }
    else
    {
      XMLHandler.appendBeginTag(localStringBuffer, "STOCK");
    }
    XMLHandler.appendTag(localStringBuffer, "SHARES", this.lB);
    XMLHandler.appendTag(localStringBuffer, "PURCHASE_PRICE", this.l1);
    XMLHandler.appendTag(localStringBuffer, "ASKPRICE", this.lK);
    XMLHandler.appendTag(localStringBuffer, "ASKSIZE", this.lo);
    XMLHandler.appendTag(localStringBuffer, "BIDPRICE", this.lN);
    XMLHandler.appendTag(localStringBuffer, "BIDSIZE", this.lS);
    XMLHandler.appendTag(localStringBuffer, "CLOSEPRICE", this.l2);
    XMLHandler.appendTag(localStringBuffer, "CLOSETICK", this.lx);
    XMLHandler.appendTag(localStringBuffer, "COMPANYNAME", this.l3);
    XMLHandler.appendTag(localStringBuffer, "COUNTRY", this.lT);
    XMLHandler.appendTag(localStringBuffer, "DIVIDEND", this.lL);
    XMLHandler.appendTag(localStringBuffer, "DIVIDENDFREQUENCY", this.lI);
    XMLHandler.appendTag(localStringBuffer, "EARNINGSPERSHARE", this.lr);
    XMLHandler.appendTag(localStringBuffer, "EXDIVIDENDDATE", this.lp);
    XMLHandler.appendTag(localStringBuffer, "ITEMEXCHANGECODE", this.lv);
    XMLHandler.appendTag(localStringBuffer, "LASTSALEPRICE", this.lz);
    XMLHandler.appendTag(localStringBuffer, "LASTSALESIZE", this.lq);
    XMLHandler.appendTag(localStringBuffer, "LASTSALETIME", this.lw);
    XMLHandler.appendTag(localStringBuffer, "HIGHPRICE", this.lP);
    XMLHandler.appendTag(localStringBuffer, "LOWPRICE", this.lt);
    XMLHandler.appendTag(localStringBuffer, "CHANGEPERCENT", this.lR);
    XMLHandler.appendTag(localStringBuffer, "CHANGEPRICE", this.ly);
    XMLHandler.appendTag(localStringBuffer, "OPENPRICE", this.lJ);
    XMLHandler.appendTag(localStringBuffer, "OPENTICK", this.lu);
    XMLHandler.appendTag(localStringBuffer, "PERATIO", this.lO);
    XMLHandler.appendTag(localStringBuffer, "SHARESOUTSTANDING", this.ls);
    XMLHandler.appendTag(localStringBuffer, "SYMBOL", this.lC);
    XMLHandler.appendTag(localStringBuffer, "VOLATILITY", this.l0);
    XMLHandler.appendTag(localStringBuffer, "VOLUME", this.lH);
    XMLHandler.appendTag(localStringBuffer, "YEARHIGH", this.lF);
    XMLHandler.appendTag(localStringBuffer, "YEARLOW", this.lX);
    XMLHandler.appendTag(localStringBuffer, "YIELD", this.lU);
    if (this.lE) {
      XMLHandler.appendTag(localStringBuffer, "NEWS", this.lW);
    }
    XMLHandler.appendEndTag(localStringBuffer, "STOCK");
    return localStringBuffer.toString();
  }
  
  public void set(Stock paramStock)
  {
    this.lK = paramStock.getAskPrice();
    this.lo = paramStock.getAskSize();
    this.lN = paramStock.getBidPrice();
    this.lS = paramStock.getBidSize();
    this.l2 = paramStock.getClosePrice();
    this.lx = paramStock.getCloseTick();
    this.l3 = paramStock.getCompanyName();
    this.lT = paramStock.getCountry();
    this.lL = paramStock.getDividend();
    this.lI = paramStock.getDividendFrequency();
    this.lr = paramStock.getEarningsPerShare();
    this.lp = paramStock.getExdividendDate();
    this.lv = paramStock.getItemExchangeCode();
    this.lz = paramStock.getLastSalePrice();
    this.lq = paramStock.getLastSaleSize();
    this.lw = paramStock.getLastSaleTime();
    this.lP = paramStock.getHighPrice();
    this.lt = paramStock.getLowPrice();
    this.lR = paramStock.getChangePercent();
    this.ly = paramStock.getChangePrice();
    this.lJ = paramStock.getOpenPrice();
    this.lu = paramStock.getOpenTick();
    this.lO = paramStock.getPeRatio();
    this.ls = paramStock.getSharesOutstanding();
    this.lC = paramStock.getSymbol().toUpperCase();
    this.l0 = paramStock.getVolatility();
    this.lH = paramStock.getVolume();
    this.lF = paramStock.getYearHigh();
    this.lX = paramStock.getYearLow();
    this.lU = paramStock.getYield();
    this.lB = paramStock.getShares();
    this.l1 = paramStock.getPurchasePrice();
    this.lW = paramStock.getNews();
    this.lE = paramStock.getNewsFlag();
    this.lY = paramStock.getCounter();
  }
  
  public int compareTo(Object paramObject)
  {
    if ((paramObject instanceof Stock)) {
      return this.lC.compareTo(((Stock)paramObject).getSymbol());
    }
    return 0;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("ASKPRICE")) {
      this.lK = paramString2;
    } else if (paramString1.equalsIgnoreCase("ASKSIZE")) {
      this.lo = paramString2;
    } else if (paramString1.equalsIgnoreCase("BIDPRICE")) {
      this.lN = paramString2;
    } else if (paramString1.equalsIgnoreCase("BIDSIZE")) {
      this.lS = paramString2;
    } else if (paramString1.equalsIgnoreCase("CLOSEPRICE")) {
      this.l2 = paramString2;
    } else if (paramString1.equalsIgnoreCase("CLOSETICK")) {
      this.lx = paramString2;
    } else if (paramString1.equalsIgnoreCase("COMPANYNAME")) {
      this.l3 = paramString2;
    } else if (paramString1.equalsIgnoreCase("COUNTRY")) {
      this.lT = paramString2;
    } else if (paramString1.equalsIgnoreCase("DIVIDEND")) {
      this.lL = paramString2;
    } else if (paramString1.equalsIgnoreCase("DIVIDENDFREQUENCY")) {
      this.lI = paramString2;
    } else if (paramString1.equalsIgnoreCase("EARNINGSPERSHARE")) {
      this.lr = paramString2;
    } else if (paramString1.equalsIgnoreCase("EXDIVIDENDDATE")) {
      this.lp = paramString2;
    } else if (paramString1.equalsIgnoreCase("ITEMEXCHANGECODE")) {
      this.lv = paramString2;
    } else if (paramString1.equalsIgnoreCase("LASTSALEPRICE")) {
      this.lz = paramString2;
    } else if (paramString1.equalsIgnoreCase("LASTSALESIZE")) {
      this.lq = paramString2;
    } else if (paramString1.equalsIgnoreCase("LASTSALETIME")) {
      this.lw = paramString2;
    } else if (paramString1.equalsIgnoreCase("HIGHPRICE")) {
      this.lP = paramString2;
    } else if (paramString1.equalsIgnoreCase("LOWPRICE")) {
      this.lt = paramString2;
    } else if (paramString1.equalsIgnoreCase("CHANGEPERCENT")) {
      this.lR = paramString2;
    } else if (paramString1.equalsIgnoreCase("CHANGEPRICE")) {
      this.ly = paramString2;
    } else if (paramString1.equalsIgnoreCase("OPENPRICE")) {
      this.lJ = paramString2;
    } else if (paramString1.equalsIgnoreCase("OPENTICK")) {
      this.lu = paramString2;
    } else if (paramString1.equalsIgnoreCase("PERATIO")) {
      this.lO = paramString2;
    } else if (paramString1.equalsIgnoreCase("SHARESOUTSTANDING")) {
      this.ls = paramString2;
    } else if (paramString1.equalsIgnoreCase("SYMBOL")) {
      this.lC = paramString2.toUpperCase();
    } else if (paramString1.equalsIgnoreCase("VOLATILITY")) {
      this.l0 = paramString2;
    } else if (paramString1.equalsIgnoreCase("VOLUME")) {
      this.lH = paramString2;
    } else if (paramString1.equalsIgnoreCase("YEARHIGH")) {
      this.lF = paramString2;
    } else if (paramString1.equalsIgnoreCase("YEARLOW")) {
      this.lX = paramString2;
    } else if (paramString1.equalsIgnoreCase("YIELD")) {
      this.lU = paramString2;
    } else if (paramString1.equalsIgnoreCase("SHARES")) {
      this.lB = paramString2;
    } else if (paramString1.equalsIgnoreCase("PURCHASE_PRICE")) {
      this.l1 = paramString2;
    } else if (paramString1.equalsIgnoreCase("NEWS")) {
      this.lW = paramString2;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.Stock
 * JD-Core Version:    0.7.0.1
 */