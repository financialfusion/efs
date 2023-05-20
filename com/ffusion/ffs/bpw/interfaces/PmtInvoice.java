package com.ffusion.ffs.bpw.interfaces;

import java.util.StringTokenizer;
import java.util.Vector;

public class PmtInvoice
  extends BPWInfoBase
{
  private static final String qi = "\001";
  private static final int qj = 14;
  private String qv;
  private String ql;
  private String qm;
  private String qh;
  private String qn;
  private String qr;
  private String qk;
  private String qq;
  private String qs;
  private String qp;
  private String qg;
  private String qt;
  private String qo;
  private String qu;
  
  public PmtInvoice() {}
  
  public PmtInvoice(PmtInvoice paramPmtInvoice)
  {
    this.qv = paramPmtInvoice.qv;
    this.ql = paramPmtInvoice.ql;
    this.qm = paramPmtInvoice.qm;
    this.qh = paramPmtInvoice.qh;
    this.qn = paramPmtInvoice.qn;
    this.qr = paramPmtInvoice.qr;
    this.qk = paramPmtInvoice.qk;
    this.qq = paramPmtInvoice.qq;
    this.qs = paramPmtInvoice.qs;
    this.qp = paramPmtInvoice.qp;
    this.qg = paramPmtInvoice.qg;
    this.qt = paramPmtInvoice.qt;
    this.qo = paramPmtInvoice.qo;
    this.qu = paramPmtInvoice.qu;
  }
  
  public PmtInvoice(String paramString1, String paramString2, String paramString3, double paramDouble1, double paramDouble2, String paramString4, String paramString5, double paramDouble3, double paramDouble4, String paramString6, String paramString7, double paramDouble5, String paramString8, String paramString9)
  {
    this.qv = paramString1;
    this.ql = paramString2;
    this.qm = paramString3;
    this.qh = String.valueOf(paramDouble1);
    this.qn = String.valueOf(paramDouble2);
    this.qr = paramString4;
    this.qk = paramString5;
    this.qq = String.valueOf(paramDouble3);
    this.qs = String.valueOf(paramDouble4);
    this.qp = paramString6;
    this.qg = paramString7;
    this.qt = String.valueOf(paramDouble5);
    this.qo = paramString8;
    this.qu = paramString9;
  }
  
  public PmtInvoice(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14)
  {
    this.qv = paramString1;
    this.ql = paramString2;
    this.qm = paramString3;
    this.qh = paramString4;
    this.qn = paramString5;
    this.qr = paramString6;
    this.qk = paramString7;
    this.qq = paramString8;
    this.qs = paramString9;
    this.qp = paramString10;
    this.qg = paramString11;
    this.qt = paramString12;
    this.qo = paramString13;
    this.qu = paramString14;
  }
  
  public String getInvoiceNum()
  {
    return this.qv;
  }
  
  public void setInvoiceNum(String paramString)
  {
    this.qv = paramString;
  }
  
  public String getInvoiceSeqNum()
  {
    return this.ql;
  }
  
  public void setInvoiceSeqNum(String paramString)
  {
    this.ql = paramString;
  }
  
  public String getVoucherNum()
  {
    return this.qm;
  }
  
  public void setVoucherNum(String paramString)
  {
    this.qm = paramString;
  }
  
  public double getTotalInvoiceAmt()
  {
    return Double.parseDouble(this.qh);
  }
  
  public String getTotalInvoiceAmount()
  {
    return this.qh;
  }
  
  public void setTotalInvoiceAmt(double paramDouble)
  {
    this.qh = String.valueOf(paramDouble);
  }
  
  public void setTotalInvoiceAmount(String paramString)
  {
    this.qh = paramString;
  }
  
  public double getAmtPaid()
  {
    return Double.parseDouble(this.qn);
  }
  
  public String getAmountPaid()
  {
    return this.qn;
  }
  
  public void setAmtPaid(double paramDouble)
  {
    this.qn = String.valueOf(paramDouble);
  }
  
  public void setAmountPaid(String paramString)
  {
    this.qn = paramString;
  }
  
  public String getInvoiceDesc()
  {
    return this.qr;
  }
  
  public void setInvoiceDesc(String paramString)
  {
    this.qr = paramString;
  }
  
  public String getInvoiceDate()
  {
    return this.qk;
  }
  
  public void setInvoiceDate(String paramString)
  {
    this.qk = paramString;
  }
  
  public double getDiscountRate()
  {
    return Double.parseDouble(this.qq);
  }
  
  public String getDiscountRateStr()
  {
    return this.qq;
  }
  
  public void setDiscountRate(double paramDouble)
  {
    this.qq = String.valueOf(paramDouble);
  }
  
  public void setDiscountRateStr(String paramString)
  {
    this.qq = paramString;
  }
  
  public double getDiscountAmt()
  {
    return Double.parseDouble(this.qs);
  }
  
  public String getDiscountAmount()
  {
    return this.qs;
  }
  
  public void setDiscountAmt(double paramDouble)
  {
    this.qs = String.valueOf(paramDouble);
  }
  
  public void setDiscountAmount(String paramString)
  {
    this.qs = paramString;
  }
  
  public String getDiscountDesc()
  {
    return this.qp;
  }
  
  public void setDiscountDesc(String paramString)
  {
    this.qp = paramString;
  }
  
  public String getDiscountDate()
  {
    return this.qg;
  }
  
  public void setDiscountDate(String paramString)
  {
    this.qg = paramString;
  }
  
  public double getAdjustmentAmt()
  {
    return Double.parseDouble(this.qt);
  }
  
  public String getAdjustmentAmount()
  {
    return this.qt;
  }
  
  public void setAdjustmentAmt(double paramDouble)
  {
    this.qt = String.valueOf(paramDouble);
  }
  
  public void setAdjustmentAmount(String paramString)
  {
    this.qt = paramString;
  }
  
  public String getAdjustmentDate()
  {
    return this.qo;
  }
  
  public void setAdjustmentDate(String paramString)
  {
    this.qo = paramString;
  }
  
  public String getAdjustmentDesc()
  {
    return this.qu;
  }
  
  public void setAdjustmentDesc(String paramString)
  {
    this.qu = paramString;
  }
  
  public void parse(String paramString)
    throws Exception
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "\001", true);
    Vector localVector = new Vector();
    while (localStringTokenizer.hasMoreElements())
    {
      String str = localStringTokenizer.nextToken();
      if (str.equals("\001"))
      {
        str = "";
      }
      else
      {
        if (localStringTokenizer.hasMoreElements() != true) {
          throw new Exception("PmtInvoice.parse: Invalid data format.");
        }
        localStringTokenizer.nextToken();
      }
      localVector.add(str);
    }
    if (localVector.size() != 14) {
      throw new Exception("PmtInvoice.parse: Invalid number of fields.");
    }
    this.qv = ((String)localVector.elementAt(0));
    this.ql = ((String)localVector.elementAt(1));
    this.qm = ((String)localVector.elementAt(2));
    this.qh = ((String)localVector.elementAt(3));
    this.qn = ((String)localVector.elementAt(4));
    this.qr = ((String)localVector.elementAt(5));
    this.qk = ((String)localVector.elementAt(6));
    this.qq = ((String)localVector.elementAt(7));
    this.qs = ((String)localVector.elementAt(8));
    this.qp = ((String)localVector.elementAt(9));
    this.qg = ((String)localVector.elementAt(10));
    this.qt = ((String)localVector.elementAt(11));
    this.qo = ((String)localVector.elementAt(12));
    this.qu = ((String)localVector.elementAt(13));
  }
  
  public String build()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.qv != null) {
      localStringBuffer.append(this.qv);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    if (this.ql != null) {
      localStringBuffer.append(this.ql);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    if (this.qm != null) {
      localStringBuffer.append(this.qm);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    localStringBuffer.append(this.qh);
    localStringBuffer.append("\001");
    localStringBuffer.append(this.qn);
    localStringBuffer.append("\001");
    if (this.qr != null) {
      localStringBuffer.append(this.qr);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    if (this.qk != null) {
      localStringBuffer.append(this.qk);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    localStringBuffer.append(this.qq);
    localStringBuffer.append("\001");
    localStringBuffer.append(this.qs);
    localStringBuffer.append("\001");
    if (this.qp != null) {
      localStringBuffer.append(this.qp);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    if (this.qg != null) {
      localStringBuffer.append(this.qg);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    localStringBuffer.append(this.qt);
    localStringBuffer.append("\001");
    if (this.qo != null) {
      localStringBuffer.append(this.qo);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    if (this.qu != null) {
      localStringBuffer.append(this.qu);
    } else {
      localStringBuffer.append("");
    }
    localStringBuffer.append("\001");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PmtInvoice
 * JD-Core Version:    0.7.0.1
 */