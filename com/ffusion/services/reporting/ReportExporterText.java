package com.ffusion.services.reporting;

import com.ffusion.beans.reporting.Image;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.reporting.RptException;
import com.ffusion.util.ILocalizable;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class ReportExporterText
  extends ReportExporterBase
{
  public static final int DEFAULT_PAGE_WIDTH = 80;
  public static final char SPACE_CHAR = ' ';
  public static final char NEWLINE_CHAR = '\n';
  private int bK = 0;
  private StringBuffer bJ = null;
  private BreakIterator bI = null;
  private String bH;
  private ArrayList bG = null;
  private boolean bL = true;
  
  public ReportExporterText(ReportCriteria paramReportCriteria)
  {
    super(paramReportCriteria);
    String str1 = paramReportCriteria.getReportOptions().getProperty("PAGEWIDTH_TEXT");
    try
    {
      this.bK = Integer.parseInt(str1);
    }
    catch (Exception localException) {}
    if (this.bK == 0) {
      this.bK = 80;
    }
    this.bJ = new StringBuffer();
    this.bI = BreakIterator.getLineInstance(paramReportCriteria.getLocale());
    this.bH = getEOLString();
    this.bG = new ArrayList();
    String str2 = paramReportCriteria.getReportOptions().getProperty("SHOW_ALTERNATE_TEXT_FOR_IMAGE");
    if ((str2 != null) && (str2.equals("FALSE"))) {
      this.bL = false;
    }
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    String str = this.bJ.toString();
    this.bJ.setLength(0);
    return str;
  }
  
  protected void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException
  {
    jdMethod_int(paramString1, paramString2);
    this.bJ.append(this.bH);
  }
  
  protected void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException
  {
    jdMethod_int(paramString1, paramString2);
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    if (paramString1 != null)
    {
      jdMethod_int(paramString1, "LEFT");
      this.bJ.append(this.bH);
    }
    if (paramString2 != null)
    {
      jdMethod_int(paramString2, "LEFT");
      this.bJ.append(this.bH);
    }
    StringBuffer localStringBuffer = new StringBuffer(this.bK);
    if (paramString3 != null) {
      localStringBuffer.append(paramString3);
    }
    if (paramString4 != null)
    {
      if (paramString3 != null) {
        localStringBuffer.append(' ');
      }
      localStringBuffer.append(paramString4);
    }
    if (localStringBuffer.length() > 0)
    {
      jdMethod_int(localStringBuffer.toString(), "LEFT");
      this.bJ.append(this.bH);
    }
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str1 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    ArrayList localArrayList1 = paramReportCriteria.getSearchCriteriaOrder();
    Iterator localIterator1 = localArrayList1.iterator();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    String str2;
    String str3;
    String str4;
    while (localIterator1.hasNext())
    {
      localObject1 = (String)localIterator1.next();
      str2 = localProperties.getProperty((String)localObject1);
      if ((!((String)localObject1).endsWith("_HIDE")) && (!paramReportCriteria.isSearchCriterionHidden((String)localObject1)))
      {
        str3 = ReportConsts.getCriteriaName(str1, (String)localObject1, this._criteria.getLocale());
        str4 = paramReportCriteria.getDisplayValue((String)localObject1);
        if (str4 == null) {
          if (str2 == null) {
            str4 = new String();
          } else {
            str4 = str2.toString();
          }
        }
        if (str4.length() != 0) {
          if (str4.indexOf("&&") == -1)
          {
            localArrayList2.add(str3);
            localArrayList3.add(str4);
          }
          else
          {
            localObject2 = new StringTokenizer(str4, "&&");
            while (((StringTokenizer)localObject2).hasMoreTokens())
            {
              localArrayList2.add(str3);
              localArrayList3.add(((StringTokenizer)localObject2).nextToken());
            }
          }
        }
      }
    }
    Object localObject1 = localProperties.propertyNames();
    while (((Enumeration)localObject1).hasMoreElements())
    {
      str2 = (String)((Enumeration)localObject1).nextElement();
      str3 = localProperties.getProperty(str2);
      if ((!localArrayList1.contains(str2)) && (!str2.endsWith("_HIDE")) && (!paramReportCriteria.isSearchCriterionHidden(str2)))
      {
        str4 = ReportConsts.getCriteriaName(str1, str2, this._criteria.getLocale());
        localObject2 = paramReportCriteria.getDisplayValue(str2);
        if (localObject2 == null) {
          if (str3 == null) {
            localObject2 = new String();
          } else {
            localObject2 = str3.toString();
          }
        }
        if (((String)localObject2).length() != 0) {
          if (((String)localObject2).indexOf("&&") == -1)
          {
            localArrayList2.add(str4);
            localArrayList3.add(localObject2);
          }
          else
          {
            localObject3 = new StringTokenizer((String)localObject2, "&&");
            while (((StringTokenizer)localObject3).hasMoreTokens())
            {
              localArrayList2.add(str4);
              localArrayList3.add(((StringTokenizer)localObject3).nextToken());
            }
          }
        }
      }
    }
    int i = (this.bK - 1) / 2;
    int j = this.bK - 1 - i;
    int k = (int)Math.ceil(localArrayList2.size() / 2.0D);
    Object localObject2 = new ArrayList(2);
    Object localObject3 = new StringBuffer();
    for (int m = 0; m < k; m++)
    {
      ((StringBuffer)localObject3).append((String)localArrayList2.get(m)).append(": ").append((String)localArrayList3.get(m));
      ((ArrayList)localObject2).add(a(((StringBuffer)localObject3).toString(), i, "LEFT", true));
      ((StringBuffer)localObject3).setLength(0);
      if (m + k < localArrayList2.size()) {
        ((StringBuffer)localObject3).append((String)localArrayList2.get(m + k)).append(": ").append((String)localArrayList3.get(m + k));
      }
      ((ArrayList)localObject2).add(a(((StringBuffer)localObject3).toString(), j, "LEFT", true));
      ((StringBuffer)localObject3).setLength(0);
      a((ArrayList)localObject2);
      ((ArrayList)localObject2).clear();
    }
    localObject2 = null;
    ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
    int n = 0;
    Iterator localIterator2 = localReportSortCriteria.iterator();
    Object localObject4;
    while (localIterator2.hasNext())
    {
      localObject4 = (ReportSortCriterion)localIterator2.next();
      String str5 = ReportConsts.getCriteriaName(str1, ((ReportSortCriterion)localObject4).getName(), this._criteria.getLocale()).trim();
      if (str5.length() != 0) {
        n++;
      }
    }
    if (n > 0)
    {
      ((StringBuffer)localObject3).append(ReportConsts.getText(10020, this._criteria.getLocale())).append(' ');
      localObject4 = new ArrayList();
      int i1 = 0;
      int i2 = 0;
      for (int i3 = 0; i2 < n; i3++)
      {
        localIterator2 = localReportSortCriteria.iterator();
        while (localIterator2.hasNext())
        {
          localObject5 = (ReportSortCriterion)localIterator2.next();
          str7 = ReportConsts.getCriteriaName(str1, ((ReportSortCriterion)localObject5).getName(), this._criteria.getLocale()).trim();
          if (str7.length() != 0) {
            if (((ReportSortCriterion)localObject5).getOrdinal() == i3)
            {
              i2++;
              if (((ArrayList)localObject4).contains(str7)) {
                break;
              }
              if (i1 > 0) {
                ((StringBuffer)localObject3).append(", ");
              }
              ((StringBuffer)localObject3).append(str7);
              ((ArrayList)localObject4).add(str7);
              i1++;
              break;
            }
          }
        }
      }
      jdMethod_int(((StringBuffer)localObject3).toString(), "LEFT");
      ((StringBuffer)localObject3).setLength(0);
      String str6 = ReportConsts.getText(3250, this._criteria.getLocale());
      Object localObject5 = this._criteria.getReportOptions();
      String str7 = ((Properties)localObject5).getProperty("ORIENTATION", "PORTRAIT");
      String str8 = ReportConsts.getText("PORTRAIT".equals(str7) ? 3252 : 3251, this._criteria.getLocale());
      boolean bool = Boolean.valueOf(((Properties)localObject5).getProperty("SHOWORIENTATION", "TRUE")).booleanValue();
      if (bool)
      {
        ((StringBuffer)localObject3).append(str6).append(": ").append(str8);
        jdMethod_int(((StringBuffer)localObject3).toString(), "LEFT");
        ((StringBuffer)localObject3).setLength(0);
      }
      localObject3 = null;
    }
    this.bJ.append(this.bH).append(this.bH);
  }
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {
    if (paramString != null)
    {
      this.bJ.append(this.bH);
      jdMethod_int(paramString, "LEFT");
    }
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    ArrayList localArrayList1 = new ArrayList();
    this.bG.add(localArrayList1);
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      ArrayList localArrayList2 = a((String)localIterator.next(), (int)paramFloat, paramString, true);
      localArrayList1.addAll(localArrayList2);
    }
  }
  
  protected void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    this.bG.add(a(convertDataToString(paramObject, paramString1, paramBoolean), (int)paramFloat, paramString2, true));
  }
  
  protected void doInitReportResultTable()
    throws RptException
  {
    int i = this.bK - this._numReportResultColumns + 1;
    if (i < this._numReportResultColumns) {
      i = this._numReportResultColumns;
    }
    int j = 0;
    for (int k = 0; k < this._numReportResultColumns; k++)
    {
      this._columnWidths[k] = Math.round(this._columnWidths[k] / 100.0F * i);
      if (this._columnWidths[k] < 1.0F) {
        this._columnWidths[k] = 1.0F;
      }
      j += (int)this._columnWidths[k];
    }
    if (j != i)
    {
      k = j < i ? 1 : -1;
      int m = 1;
      while ((j != i) && (m != 0))
      {
        m = 0;
        for (int n = 0; (n < this._numReportResultColumns) && (j != i); n++)
        {
          int i1 = (int)this._columnWidths[n] + k;
          if (i1 > 1)
          {
            this._columnWidths[n] = i1;
            j += k;
            m = 1;
          }
        }
      }
    }
  }
  
  protected void doFiniReportResultTable()
    throws RptException
  {
    this.bJ.append(this.bH);
  }
  
  protected void doInitReportResultRow()
    throws RptException
  {
    this.bG.clear();
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    a(this.bG);
    this.bG.clear();
  }
  
  protected void doWriteBlankLine()
    throws RptException
  {
    this.bJ.append(this.bH);
  }
  
  protected void doWriteParagraph(String paramString)
    throws RptException
  {
    jdMethod_int(paramString, "LEFT");
  }
  
  protected void doAddPageBreak()
    throws RptException
  {}
  
  private void jdMethod_int(String paramString1, String paramString2)
  {
    ArrayList localArrayList = a(paramString1, this.bK, paramString2, false);
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      StringBuffer localStringBuffer = (StringBuffer)localIterator.next();
      this.bJ.append(localStringBuffer).append(this.bH);
    }
  }
  
  private void a(ArrayList paramArrayList)
  {
    int i = 1;
    for (int j = 0; i != 0; j++)
    {
      i = 0;
      for (int k = 0; k < paramArrayList.size(); k++)
      {
        ArrayList localArrayList = (ArrayList)paramArrayList.get(k);
        StringBuffer localStringBuffer = null;
        if (localArrayList.size() <= j)
        {
          if (k != paramArrayList.size() - 1)
          {
            int m = ((StringBuffer)localArrayList.get(0)).length();
            localStringBuffer = a(null, 0, -1, m, "LEFT", true);
          }
        }
        else
        {
          localStringBuffer = (StringBuffer)localArrayList.get(j);
          if (j < localArrayList.size() - 1) {
            i = 1;
          }
        }
        if (localStringBuffer != null) {
          this.bJ.append(localStringBuffer);
        }
        if (k != paramArrayList.size() - 1) {
          this.bJ.append(' ');
        }
      }
      this.bJ.append(this.bH);
    }
  }
  
  private ArrayList a(String paramString1, int paramInt, String paramString2, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    this.bI.setText(paramString1);
    int i = 0;
    int j = 0;
    if (!paramString2.equalsIgnoreCase("LEFT")) {
      while ((i < paramString1.length()) && (Character.isSpaceChar(paramString1.charAt(i)))) {
        i++;
      }
    }
    for (j = this.bI.next(); j != -1; j = this.bI.next())
    {
      int k = j - i;
      int m = j - 1;
      for (int n = 0; (m >= 0) && (paramString1.charAt(m) == '\n'); n++) {
        m--;
      }
      if ((k >= paramInt) || (j == paramString1.length()) || (n > 0))
      {
        while ((m >= 0) && (Character.isSpaceChar(paramString1.charAt(m)))) {
          m--;
        }
        k = m - i + 1;
        if (k > paramInt)
        {
          i1 = this.bI.previous();
          if ((i1 == -1) || (i1 <= i))
          {
            j = i + paramInt;
            m = j - 1;
          }
          else
          {
            j = i1;
            for (m = j - 1; (m >= 0) && (Character.isSpaceChar(paramString1.charAt(m))); m--) {}
          }
        }
        localArrayList.add(a(paramString1, i, m, paramInt, paramString2, paramBoolean));
        i = j;
        n--;
        for (int i1 = 0; i1 < n; i1++) {
          localArrayList.add(a(null, 0, -1, paramInt, paramString2, paramBoolean));
        }
        while ((i < paramString1.length()) && (Character.isSpaceChar(paramString1.charAt(i)))) {
          i++;
        }
      }
    }
    if ((paramBoolean) && (localArrayList.size() == 0)) {
      localArrayList.add(a(null, 0, -1, paramInt, paramString2, true));
    }
    return localArrayList;
  }
  
  private StringBuffer a(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramInt3);
    int i = paramInt2 - paramInt1 + 1;
    int j = paramInt3 - i;
    int k = 0;
    int m = 0;
    if (paramString2.equals("RIGHT"))
    {
      k = j;
    }
    else if (paramString2.equals("CENTER"))
    {
      k = j / 2;
      m = j - k;
    }
    else
    {
      m = j;
    }
    if (!paramBoolean) {
      m = 0;
    }
    for (int n = 0; n < k; n++) {
      localStringBuffer.append(' ');
    }
    for (n = paramInt1; n <= paramInt2; n++) {
      localStringBuffer.append(paramString1.charAt(n));
    }
    for (n = 0; n < m; n++) {
      localStringBuffer.append(' ');
    }
    return localStringBuffer;
  }
  
  protected String convertDataToString(Object paramObject, String paramString, boolean paramBoolean)
  {
    if ((!paramString.equalsIgnoreCase("com.ffusion.beans.reporting.Image")) || (paramObject == null)) {
      return super.convertDataToString(paramObject, paramString, paramBoolean);
    }
    Locale localLocale = this._criteria.getLocale();
    ILocalizable localILocalizable = ((Image)paramObject).getAlternateText();
    String str = "";
    if ((localILocalizable != null) && (this.bL)) {
      str = (String)localILocalizable.localize(localLocale);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterText
 * JD-Core Version:    0.7.0.1
 */