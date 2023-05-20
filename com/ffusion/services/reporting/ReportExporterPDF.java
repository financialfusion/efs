package com.ffusion.services.reporting;

import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.reporting.RptException;
import com.ffusion.reporting.reportwriter.IPdfWriterHolder;
import com.ffusion.util.logging.DebugLog;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

public class ReportExporterPDF
  extends ReportExporterBase
  implements IPdfWriterHolder, PdfPageEvent
{
  private static final Font a0 = new Font(1, 12.0F, 1);
  private static final Font aP = new Font(1, 11.0F, 1);
  private static final Font a2 = new Font(1, 9.0F, 1);
  private static final Font a6 = new Font(1, 9.0F, 0);
  private static final Font aY = new Font(1, 10.0F, 1);
  private static final Font aW = new Font(1, 8.0F, 1);
  private static final Font a5 = new Font(1, 8.0F, 1);
  private static final Font a4 = new Font(1, 10.0F, 0);
  private static BaseFont aV;
  private static BaseFont a7;
  private static BaseFont aQ;
  private int aR = 0;
  private Document ba = null;
  private Table a1 = null;
  private boolean aT = false;
  private ReportCriteria _criteria = null;
  private boolean aS = false;
  private PdfWriter a9 = null;
  private ArrayList aZ = null;
  private boolean a3 = false;
  private int a8 = 0;
  private int aX = 0;
  private HashMap bb = null;
  private HashMap bc = null;
  private boolean aU = true;
  
  public ReportExporterPDF(ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    super(paramReportCriteria, paramHashMap);
    this._criteria = paramReportCriteria;
    this.aZ = new ArrayList();
    this.bb = new HashMap();
    this.bc = new HashMap();
  }
  
  public void setPDFDocument(Document paramDocument)
  {
    if (paramDocument == null) {
      throw new NullPointerException();
    }
    this.ba = paramDocument;
  }
  
  public void initPDFHeaderFooter()
    throws RptException
  {
    Properties localProperties = this._criteria.getReportOptions();
    if (localProperties != null)
    {
      String str1 = localProperties.getProperty("ORIENTATION");
      if ((str1 != null) && (str1.equals("LANDSCAPE")))
      {
        Rectangle localRectangle = this.ba.getPageSize();
        this.ba.setPageSize(localRectangle.rotate());
      }
      int i = 1;
      String str2 = localProperties.getProperty("SHOWHEADER");
      if ((str2 != null) && (str2.equals("TRUE"))) {
        i = 0;
      }
      int j = 0;
      String str3 = localProperties.getProperty("SHOWFOOTER");
      if ((str3 != null) && (str3.equals("FALSE"))) {
        j = 1;
      }
      int k = 0;
      this.aS = true;
      exportHeader(this._criteria);
      if (j != 0)
      {
        this.ba.open();
        k = 1;
      }
      exportFooter(this._criteria);
      if (k == 0)
      {
        this.ba.open();
        k = 1;
      }
      if (i != 0)
      {
        this.ba.resetHeader();
      }
      else
      {
        this.aS = false;
        exportHeader(this._criteria);
      }
    }
    else
    {
      this.ba.open();
    }
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    return null;
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    try
    {
      Phrase localPhrase = new Phrase();
      Font localFont = jdMethod_new("reportHeader");
      Float localFloat = jdMethod_byte("reportHeader");
      Table localTable1 = new Table(1);
      localTable1.setBorder(0);
      localTable1.setOffset(0.0F);
      localTable1.setWidth(100.0F);
      localTable1.setCellsFitPage(true);
      if (localFloat != null) {
        localPhrase.setLeading(localFloat.floatValue());
      }
      Cell localCell1;
      if ((paramString1 != null) && (paramString1.trim().length() > 0))
      {
        localCell1 = new Cell(new Phrase(paramString1, localFont));
        localCell1.setBorder(0);
        localTable1.addCell(localCell1);
      }
      if ((paramString2 != null) && (paramString2.trim().length() > 0))
      {
        localCell1 = new Cell(new Phrase(paramString2, localFont));
        localCell1.setBorder(0);
        localTable1.addCell(localCell1);
      }
      int i = 0;
      Object localObject2;
      if ((paramString3 != null) && (paramString3.trim().length() > 0))
      {
        localObject1 = paramString3;
        if ((paramString4 != null) && (paramString4.trim().length() > 0))
        {
          localObject1 = (String)localObject1 + " " + paramString4;
          i = 1;
        }
        localObject2 = new Cell(new Phrase((String)localObject1, localFont));
        ((Cell)localObject2).setBorder(0);
        localTable1.addCell((Cell)localObject2);
      }
      if ((paramString4 != null) && (paramString4.trim().length() > 0) && (i == 0))
      {
        localObject1 = new Cell(new Phrase(paramString4, localFont));
        ((Cell)localObject1).setBorder(0);
        localTable1.addCell((Cell)localObject1);
      }
      if (localTable1.size() > 0) {
        localPhrase.add(localTable1);
      }
      if (this.aS)
      {
        localObject1 = this._criteria.getSearchCriteria();
        localObject2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
        ArrayList localArrayList1 = paramReportCriteria.getSearchCriteriaOrder();
        Iterator localIterator = localArrayList1.iterator();
        ArrayList localArrayList2 = new ArrayList();
        ArrayList localArrayList3 = new ArrayList();
        String str1;
        String str2;
        Object localObject5;
        while (localIterator.hasNext())
        {
          localObject3 = (String)localIterator.next();
          str1 = ((Properties)localObject1).getProperty((String)localObject3);
          if ((!((String)localObject3).endsWith("_HIDE")) && (!paramReportCriteria.isSearchCriterionHidden((String)localObject3)))
          {
            localObject4 = ReportConsts.getCriteriaName((String)localObject2, (String)localObject3, this._criteria.getLocale());
            str2 = paramReportCriteria.getDisplayValue((String)localObject3);
            if (str2 == null) {
              if (str1 == null) {
                str2 = new String();
              } else {
                str2 = str1.toString();
              }
            }
            if (str2.length() != 0) {
              if (str2.indexOf("&&") == -1)
              {
                localArrayList2.add(localObject4);
                localArrayList3.add(str2);
              }
              else
              {
                localObject5 = new StringTokenizer(str2, "&&");
                while (((StringTokenizer)localObject5).hasMoreTokens())
                {
                  localArrayList2.add(localObject4);
                  localArrayList3.add(((StringTokenizer)localObject5).nextToken());
                }
              }
            }
          }
        }
        Object localObject3 = ((Properties)localObject1).propertyNames();
        while (((Enumeration)localObject3).hasMoreElements())
        {
          str1 = (String)((Enumeration)localObject3).nextElement();
          localObject4 = ((Properties)localObject1).getProperty(str1);
          if ((!localArrayList1.contains(str1)) && (!str1.endsWith("_HIDE")) && (!paramReportCriteria.isSearchCriterionHidden(str1)))
          {
            str2 = ReportConsts.getCriteriaName((String)localObject2, str1, this._criteria.getLocale());
            localObject5 = paramReportCriteria.getDisplayValue(str1);
            if (localObject5 == null) {
              if (localObject4 == null) {
                localObject5 = new String();
              } else {
                localObject5 = ((String)localObject4).toString();
              }
            }
            if (((String)localObject5).length() != 0) {
              if (((String)localObject5).indexOf("&&") == -1)
              {
                localArrayList2.add(str2);
                localArrayList3.add(localObject5);
              }
              else
              {
                localObject6 = new StringTokenizer((String)localObject5, "&&");
                while (((StringTokenizer)localObject6).hasMoreTokens())
                {
                  localArrayList2.add(str2);
                  localArrayList3.add(((StringTokenizer)localObject6).nextToken());
                }
              }
            }
          }
        }
        int j = (int)Math.ceil(localArrayList2.size() / 2.0D);
        Object localObject4 = new Table(2);
        ((Table)localObject4).setBorder(0);
        ((Table)localObject4).setOffset(0.0F);
        ((Table)localObject4).setWidth(100.0F);
        ((Table)localObject4).setCellsFitPage(true);
        Object localObject7;
        Object localObject8;
        for (int k = 0; k < j; k++)
        {
          localObject5 = new Phrase((String)localArrayList2.get(k) + ": " + (String)localArrayList3.get(k), jdMethod_new("reportHeader"));
          localObject6 = jdMethod_byte("reportHeader");
          if (localObject6 != null) {
            ((Phrase)localObject5).setLeading(((Float)localObject6).floatValue());
          }
          localObject7 = new Cell((Element)localObject5);
          ((Cell)localObject7).setBorder(0);
          ((Cell)localObject7).setHorizontalAlignment(jdMethod_case("LEFT"));
          ((Table)localObject4).addCell((Cell)localObject7);
          localObject5 = null;
          localObject7 = null;
          localObject8 = "";
          if (k + j < localArrayList2.size()) {
            localObject8 = (String)localArrayList2.get(k + j) + ": " + (String)localArrayList3.get(k + j);
          }
          localObject5 = new Phrase((String)localObject8, jdMethod_new("reportHeader"));
          if (localObject6 != null) {
            ((Phrase)localObject5).setLeading(((Float)localObject6).floatValue());
          }
          localObject7 = new Cell((Element)localObject5);
          ((Cell)localObject7).setBorder(0);
          ((Cell)localObject7).setHorizontalAlignment(jdMethod_case("LEFT"));
          ((Table)localObject4).addCell((Cell)localObject7);
          localObject5 = null;
          localObject7 = null;
        }
        localPhrase.add(localObject4);
        ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
        int m = 0;
        Object localObject6 = localReportSortCriteria.iterator();
        while (((Iterator)localObject6).hasNext())
        {
          localObject7 = (ReportSortCriterion)((Iterator)localObject6).next();
          localObject8 = ReportConsts.getCriteriaName((String)localObject2, ((ReportSortCriterion)localObject7).getName(), this._criteria.getLocale()).trim();
          if (((String)localObject8).length() != 0) {
            m++;
          }
        }
        if (m > 0)
        {
          localObject7 = new StringBuffer();
          ((StringBuffer)localObject7).append(ReportConsts.getText(10020, this._criteria.getLocale())).append(" ");
          localObject8 = new ArrayList();
          int n = 0;
          int i1 = 0;
          for (int i2 = 0; i1 < m; i2++)
          {
            localObject6 = localReportSortCriteria.iterator();
            while (((Iterator)localObject6).hasNext())
            {
              localObject9 = (ReportSortCriterion)((Iterator)localObject6).next();
              localObject10 = ReportConsts.getCriteriaName((String)localObject2, ((ReportSortCriterion)localObject9).getName(), this._criteria.getLocale()).trim();
              if (((String)localObject10).length() != 0) {
                if (((ReportSortCriterion)localObject9).getOrdinal() == i2)
                {
                  i1++;
                  if (((ArrayList)localObject8).contains(localObject10)) {
                    break;
                  }
                  if (n > 0) {
                    ((StringBuffer)localObject7).append(", ");
                  }
                  ((StringBuffer)localObject7).append((String)localObject10);
                  ((ArrayList)localObject8).add(localObject10);
                  n++;
                  break;
                }
              }
            }
          }
          Table localTable2 = new Table(1);
          localTable2.setBorder(0);
          localTable2.setOffset(0.0F);
          localTable2.setWidth(100.0F);
          localTable2.setCellsFitPage(true);
          Object localObject9 = new Phrase(((StringBuffer)localObject7).toString(), jdMethod_new("reportHeader"));
          Object localObject10 = jdMethod_byte("reportHeader");
          if (localObject10 != null) {
            ((Phrase)localObject9).setLeading(((Float)localObject10).floatValue());
          }
          Cell localCell2 = new Cell((Element)localObject9);
          localCell2.setBorder(0);
          localTable2.addCell(localCell2);
          localPhrase.add(localTable2);
        }
      }
      Object localObject1 = new HeaderFooter(localPhrase, false);
      ((HeaderFooter)localObject1).setAlignment(0);
      ((HeaderFooter)localObject1).setBorder(0);
      this.ba.setHeader((HeaderFooter)localObject1);
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {
    try
    {
      String str = this._criteria.getReportOptions().getProperty("SHOWPAGENUMBERFORMAT");
      boolean bool = false;
      if ((str != null) && (str.equals("TRUE"))) {
        bool = true;
      }
      HeaderFooter localHeaderFooter = null;
      if ((paramString != null) || (bool))
      {
        Phrase localPhrase = new Phrase();
        Float localFloat = jdMethod_byte("reportFooter");
        if (localFloat != null) {
          localPhrase.setLeading(localFloat.floatValue());
        }
        if (paramString != null)
        {
          localPhrase.add(new Chunk(paramString, jdMethod_new("reportFooter")));
          localPhrase.add(Chunk.NEWLINE);
        }
        localHeaderFooter = new HeaderFooter(localPhrase, bool);
        localHeaderFooter.setAlignment(0);
        localHeaderFooter.setBorder(0);
      }
      if (localHeaderFooter == null) {
        this.ba.resetFooter();
      } else {
        this.ba.setFooter(localHeaderFooter);
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException
  {
    try
    {
      if ((paramString1 == null) || ((paramString1 != null) && (paramString1.trim().length() == 0))) {
        return;
      }
      int i = jdMethod_case(paramString2);
      Phrase localPhrase = new Phrase(paramString1, jdMethod_new("reportHeading"));
      Float localFloat = jdMethod_byte("reportHeading");
      if (localFloat != null) {
        localPhrase.setLeading(localFloat.floatValue());
      }
      Cell localCell = new Cell(localPhrase);
      localCell.setBorder(0);
      Table localTable = new Table(1);
      localTable.setWidth(100.0F);
      localTable.setOffset(0.0F);
      localTable.setBorder(0);
      localTable.setCellsFitPage(true);
      localTable.addCell(localCell);
      localTable.setAlignment(i);
      this.ba.add(localTable);
      this.aT = true;
      this.aU = false;
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException
  {
    try
    {
      if ((paramString1 == null) || ((paramString1 != null) && (paramString1.trim().length() == 0))) {
        return;
      }
      int i = jdMethod_case(paramString2);
      Phrase localPhrase = new Phrase(paramString1, jdMethod_new("reportSectionHeading"));
      Float localFloat = jdMethod_byte("reportSectionHeading");
      if (localFloat != null) {
        localPhrase.setLeading(localFloat.floatValue());
      }
      Cell localCell = new Cell(localPhrase);
      localCell.setBorder(0);
      Table localTable = new Table(1);
      localTable.setWidth(100.0F);
      localTable.setOffset(0.0F);
      localTable.setBorder(0);
      localTable.setCellsFitPage(true);
      localTable.addCell(localCell);
      localTable.setAlignment(i);
      this.ba.add(localTable);
      this.aT = true;
      this.aU = false;
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    try
    {
      int i = jdMethod_case(paramString2);
      if (jdMethod_new(paramString3) == null) {
        paramString3 = "reportData";
      }
      Object localObject1;
      Object localObject2;
      Cell localCell;
      Color localColor;
      if (!"com.ffusion.beans.reporting.Image".equals(paramString1))
      {
        localObject1 = null;
        if (paramString2.equalsIgnoreCase("RIGHT")) {
          localObject1 = new Phrase(convertDataToString(paramObject, paramString1, paramBoolean) + "  ", jdMethod_new(paramString3));
        } else {
          localObject1 = new Phrase(convertDataToString(paramObject, paramString1, paramBoolean), jdMethod_new(paramString3));
        }
        localObject2 = jdMethod_byte(paramString3);
        if (localObject2 != null) {
          ((Phrase)localObject1).setLeading(((Float)localObject2).floatValue());
        }
        localCell = new Cell((Element)localObject1);
        localCell.setBorderWidthTop(2.0F);
        localCell.setBorderWidthBottom(2.0F);
        localCell.setUseBorderPadding(true);
        localCell.setHorizontalAlignment(i);
        localCell.setUseDescender(true);
        localCell.setUseAscender(true);
        localColor = jdMethod_try(paramString4);
        if (localColor != null)
        {
          localCell.setBackgroundColor(localColor);
          localCell.setBorderColor(localColor);
        }
        else
        {
          localCell.setBorderColor(Color.WHITE);
        }
        if (this.a1 != null) {
          this.a1.addCell(localCell);
        }
        this.aZ.add(localCell);
      }
      else
      {
        try
        {
          localObject1 = ((com.ffusion.beans.reporting.Image)paramObject).getImageData();
          localObject2 = com.lowagie.text.Image.getInstance((byte[])localObject1);
          localCell = new Cell((Element)localObject2);
          localCell.setBorderColor(new CMYKColor(0, 0, 0, 0));
          localColor = jdMethod_try(paramString4);
          if (localColor != null) {
            localCell.setBackgroundColor(localColor);
          }
          if (this.a1 != null)
          {
            this.a1.addCell(localCell);
            this.a1.setCellpadding(1.0F);
          }
          this.aZ.add(localCell);
        }
        catch (Exception localException2)
        {
          localObject2 = new Cell(new Chunk("\n\n\n\n\n"));
          ((Cell)localObject2).setBorderColor(new CMYKColor(0, 0, 0, 0));
          if (this.a1 != null)
          {
            this.a1.addCell((Cell)localObject2);
            this.a1.setCellpadding(1.0F);
          }
          this.aZ.add(localObject2);
        }
      }
      this.aX += 1;
      if (this.aX == this._numReportResultColumns) {
        this.a8 += 1;
      }
    }
    catch (Exception localException1)
    {
      if ((localException1 instanceof RptException)) {
        throw ((RptException)localException1);
      }
      throw new RptException(100, localException1);
    }
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    try
    {
      int i = jdMethod_case(paramString);
      int j = paramArrayList.size();
      Phrase localPhrase = new Phrase();
      Float localFloat = jdMethod_byte("reportColumnHeading");
      if (localFloat != null) {
        localPhrase.setLeading(localFloat.floatValue());
      }
      for (int k = 0; k < j; k++)
      {
        Chunk localChunk = null;
        if (paramString.equalsIgnoreCase("RIGHT")) {
          localChunk = new Chunk(paramArrayList.get(k) + "  ", jdMethod_new("reportColumnHeading"));
        } else {
          localChunk = new Chunk((String)paramArrayList.get(k), jdMethod_new("reportColumnHeading"));
        }
        localPhrase.add(localChunk);
        if (k != j - 1) {
          localPhrase.add(Chunk.NEWLINE);
        }
      }
      Cell localCell = new Cell(localPhrase);
      localCell.setBorder(0);
      localCell.setHorizontalAlignment(i);
      localCell.setUseDescender(true);
      if (this.a1 != null)
      {
        this.a1.addCell(localCell);
        this.aR += 1;
        if (this.aR == this._numReportResultColumns) {
          this.a1.endHeaders();
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doInitReportResultTable()
    throws RptException
  {
    try
    {
      this.a1 = new Table(this._numReportResultColumns);
      this.a1.setBorder(0);
      this.a1.setOffset(0.0F);
      this.a1.setWidth(100.0F);
      this.a1.setCellsFitPage(true);
      this.a1.setWidths(this._columnWidths);
      this.aR = 0;
      this.a8 = 0;
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doFiniReportResultTable()
    throws RptException
  {
    try
    {
      if (this.a1 != null)
      {
        this.aU = false;
        this.ba.add(this.a1);
        this.aT = true;
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doWriteBlankLine()
    throws RptException
  {
    try
    {
      Phrase localPhrase = new Phrase("", a6);
      localPhrase.setLeading(0.0F);
      Cell localCell = new Cell(localPhrase);
      localCell.setBorder(0);
      Table localTable = new Table(1);
      localTable.setWidth(100.0F);
      localTable.setOffset(0.0F);
      localTable.setBorder(0);
      localTable.setCellsFitPage(true);
      localTable.addCell(localCell);
      this.ba.add(localTable);
      this.aT = true;
      this.aU = false;
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doWriteParagraph(String paramString)
    throws RptException
  {
    if (paramString == null) {
      return;
    }
    try
    {
      Phrase localPhrase = new Phrase(paramString, jdMethod_new("reportParagraph"));
      Float localFloat = jdMethod_byte("reportParagraph");
      if (localFloat != null) {
        localPhrase.setLeading(localFloat.floatValue());
      }
      Cell localCell = new Cell(localPhrase);
      localCell.setBorder(0);
      Table localTable = new Table(1);
      localTable.setOffset(0.0F);
      localTable.setBorder(0);
      localTable.setCellsFitPage(true);
      localTable.addCell(localCell);
      localTable.setAlignment(0);
      this.ba.add(localTable);
      this.aT = true;
      this.aU = false;
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doInitReportResultRow()
    throws RptException
  {
    this.aX = 0;
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    if ((this.a9 != null) && (!this.a9.fitsPage(this.a1)))
    {
      this.a1.setOffset(0.0F);
      if (this.a8 == 1)
      {
        if (this.aU) {
          addPageBreak();
        }
        if (!this.a9.fitsPage(this.a1)) {
          addPageBreak();
        }
        if (!this.a9.fitsPage(this.a1)) {
          throw new RptException(307, "PDF row too large");
        }
      }
      else
      {
        this.a1.deleteLastRow();
        this.a3 = true;
        addPageBreakInTable();
        Iterator localIterator = this.aZ.iterator();
        while (localIterator.hasNext())
        {
          Cell localCell = (Cell)localIterator.next();
          this.a1.addCell(localCell);
        }
        this.a3 = false;
      }
    }
    if (!this.a3) {
      this.aZ.clear();
    }
  }
  
  protected void doAddPageBreak()
    throws RptException
  {
    try
    {
      if ((!this.a3) || (!this.aU)) {
        this.ba.newPage();
      }
    }
    catch (Exception localException)
    {
      throw new RptException(100, localException);
    }
  }
  
  protected void doFiniReport()
    throws RptException
  {
    if (!this.aT) {
      writeBlankLine();
    }
  }
  
  private int jdMethod_case(String paramString)
    throws Exception
  {
    int i = 0;
    if (paramString == null) {
      return i;
    }
    if (paramString.equalsIgnoreCase("RIGHT")) {
      i = 2;
    } else if (paramString.equalsIgnoreCase("CENTER")) {
      i = 1;
    }
    return i;
  }
  
  private Font a(Properties paramProperties)
    throws RptException
  {
    Font localFont = null;
    try
    {
      String str1 = paramProperties.getProperty("Font");
      String str2 = paramProperties.getProperty("Style");
      float f = Float.parseFloat(paramProperties.getProperty("Size"));
      int i = 1;
      int j = -1;
      BaseFont localBaseFont = null;
      if (str1 != null)
      {
        if (str1.equalsIgnoreCase("Courier"))
        {
          j = 0;
        }
        else if (str1.equalsIgnoreCase("Helvetica"))
        {
          j = 1;
        }
        else if (str1.equalsIgnoreCase("Symbol"))
        {
          j = 3;
        }
        else if (str1.equalsIgnoreCase("TimesRoman"))
        {
          j = 2;
        }
        else if (str1.equalsIgnoreCase("Zapfdingbats"))
        {
          j = 4;
        }
        else if (str1.equalsIgnoreCase("STSong-Light"))
        {
          localBaseFont = aV;
          i = (i != 0) && (localBaseFont != null) ? 1 : 0;
        }
        else if (str1.equalsIgnoreCase("MHei-Medium"))
        {
          localBaseFont = a7;
          i = (i != 0) && (localBaseFont != null) ? 1 : 0;
        }
        else if (str1.equalsIgnoreCase("MSung-Light"))
        {
          localBaseFont = aQ;
          i = (i != 0) && (localBaseFont != null) ? 1 : 0;
        }
        else
        {
          i = 0;
        }
      }
      else {
        i = 0;
      }
      int k = -1;
      if (str2 != null)
      {
        if (str2.equalsIgnoreCase("Bold")) {
          k = 1;
        } else if (str2.equalsIgnoreCase("BoldItalic")) {
          k = 3;
        } else if (str2.equalsIgnoreCase("Italic")) {
          k = 2;
        } else if (str2.equalsIgnoreCase("Normal")) {
          k = 0;
        } else if (str2.equalsIgnoreCase("Underline")) {
          k = 4;
        } else if (str2.equalsIgnoreCase("Strikethru")) {
          k = 8;
        } else {
          i = 0;
        }
      }
      else {
        i = 0;
      }
      if (i != 0) {
        if (localBaseFont != null) {
          localFont = new Font(localBaseFont, f, k);
        } else {
          localFont = new Font(j, f, k);
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
    return localFont;
  }
  
  private Font jdMethod_new(String paramString)
    throws RptException
  {
    if (paramString == null) {
      return null;
    }
    if (this.bb.containsKey(paramString)) {
      return (Font)this.bb.get(paramString);
    }
    Font localFont = null;
    if ((this._styleMap != null) && (this._styleMap.containsKey(paramString))) {
      localFont = a((Properties)this._styleMap.get(paramString));
    }
    if (localFont == null) {
      if (paramString.equalsIgnoreCase("reportHeading")) {
        localFont = a0;
      } else if (paramString.equalsIgnoreCase("reportSectionHeading")) {
        localFont = aP;
      } else if (paramString.equalsIgnoreCase("reportColumnHeading")) {
        localFont = a2;
      } else if (paramString.equalsIgnoreCase("reportHeader")) {
        localFont = aW;
      } else if (paramString.equalsIgnoreCase("reportFooter")) {
        localFont = a5;
      } else if (paramString.equalsIgnoreCase("reportParagraph")) {
        localFont = a4;
      } else if (paramString.equalsIgnoreCase("reportData")) {
        localFont = a6;
      } else if (paramString.equalsIgnoreCase("reportDataSubtotal")) {
        localFont = aY;
      }
    }
    this.bb.put(paramString, localFont);
    return localFont;
  }
  
  private Float jdMethod_byte(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (this.bc.containsKey(paramString)) {
      return (Float)this.bc.get(paramString);
    }
    Float localFloat = null;
    if ((this._styleMap != null) && (this._styleMap.containsKey(paramString)))
    {
      Properties localProperties = (Properties)this._styleMap.get(paramString);
      if (localProperties.containsKey("Leading"))
      {
        String str = localProperties.getProperty("Leading");
        try
        {
          localFloat = Float.valueOf(str);
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
    }
    this.bc.put(paramString, localFloat);
    return localFloat;
  }
  
  private Color jdMethod_try(String paramString)
    throws RptException
  {
    if (paramString == null) {
      return null;
    }
    Properties localProperties = (Properties)this._styleMap.get(paramString);
    if (localProperties == null) {
      return null;
    }
    String str = localProperties.getProperty("BGColor");
    if (str == null) {
      return null;
    }
    try
    {
      return Color.decode(str);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new RptException(308, "Invalid background color: " + str, localNumberFormatException);
    }
  }
  
  public void setPdfWriter(PdfWriter paramPdfWriter)
  {
    this.a9 = paramPdfWriter;
    if (this.a9 != null) {
      this.a9.setPageEvent(this);
    }
  }
  
  public PdfWriter getPdfWriter()
  {
    return this.a9;
  }
  
  public void onOpenDocument(PdfWriter paramPdfWriter, Document paramDocument)
  {
    this.aU = true;
  }
  
  public void onStartPage(PdfWriter paramPdfWriter, Document paramDocument)
  {
    this.aU = true;
  }
  
  public void onEndPage(PdfWriter paramPdfWriter, Document paramDocument)
  {
    this.aU = true;
  }
  
  public void onCloseDocument(PdfWriter paramPdfWriter, Document paramDocument)
  {
    this.aU = false;
  }
  
  public void onParagraph(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat)
  {
    this.aU = false;
  }
  
  public void onParagraphEnd(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat)
  {
    this.aU = false;
  }
  
  public void onChapter(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat, Paragraph paramParagraph)
  {
    this.aU = false;
  }
  
  public void onChapterEnd(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat)
  {
    this.aU = false;
  }
  
  public void onSection(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat, int paramInt, Paragraph paramParagraph)
  {
    this.aU = false;
  }
  
  public void onSectionEnd(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat)
  {
    this.aU = false;
  }
  
  public void onGenericTag(PdfWriter paramPdfWriter, Document paramDocument, Rectangle paramRectangle, String paramString)
  {
    this.aU = false;
  }
  
  static
  {
    try
    {
      aV = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
    }
    catch (Exception localException1)
    {
      aV = null;
      DebugLog.log("ReportExporterPDF failed to initialize the font 'STSong-Light' successfully.");
    }
    try
    {
      a7 = BaseFont.createFont("MHei-Medium", "UniCNS-UCS2-H", false);
    }
    catch (Exception localException2)
    {
      a7 = null;
      DebugLog.log("ReportExporterPDF failed to initialize the font 'MHei-Medium' successfully.");
    }
    try
    {
      aQ = BaseFont.createFont("MSung-Light", "UniCNS-UCS2-H", false);
    }
    catch (Exception localException3)
    {
      aQ = null;
      DebugLog.log("ReportExporterPDF failed to initialize the font 'MSung-Light' successfully.");
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterPDF
 * JD-Core Version:    0.7.0.1
 */