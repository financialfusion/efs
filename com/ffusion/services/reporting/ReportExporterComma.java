package com.ffusion.services.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.reporting.Image;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.reporting.RptException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.DateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class ReportExporterComma
  extends ReportExporterBase
{
  protected static final String QUOTE = "\"";
  protected static final String SPACER = " ";
  protected static final String COMMA = ",";
  protected static final String ESCAPED_QUOTE = "\"\"";
  private StringBuffer bC = null;
  private boolean bB;
  private boolean bA = true;
  
  public ReportExporterComma(ReportCriteria paramReportCriteria)
  {
    super(paramReportCriteria);
    DateTime localDateTime = new DateTime();
    localDateTime.setFormat(this._dateFormat);
    String str1 = localDateTime.toString();
    if (str1.indexOf(",") != -1) {
      this._dateFormat = "MM/dd/yyyy";
    }
    String str2 = paramReportCriteria.getReportOptions().getProperty("SHOW_ALTERNATE_TEXT_FOR_IMAGE");
    if ((str2 != null) && (str2.equals("FALSE"))) {
      this.bA = false;
    }
    this.bC = new StringBuffer();
  }
  
  protected String convertDataToString(Object paramObject, String paramString)
  {
    if ((paramObject == null) || ((!paramString.equalsIgnoreCase("com.ffusion.beans.Currency")) && (!paramString.equalsIgnoreCase("com.ffusion.beans.reporting.Image")))) {
      return super.convertDataToString(paramObject, paramString);
    }
    String str = null;
    Object localObject1;
    Object localObject2;
    if (paramString.equalsIgnoreCase("com.ffusion.beans.reporting.Image"))
    {
      localObject1 = this._criteria.getLocale();
      localObject2 = ((Image)paramObject).getAlternateText();
      str = "";
      if ((localObject2 != null) && (this.bA)) {
        str = (String)((ILocalizable)localObject2).localize((Locale)localObject1);
      }
    }
    else if (paramString.equalsIgnoreCase("com.ffusion.beans.Currency"))
    {
      localObject1 = (Currency)paramObject;
      localObject2 = ((Currency)localObject1).getFormat();
      ((Currency)localObject1).setFormat("DECIMAL");
      str = ((Currency)localObject1).getCurrencyStringNoSymbolNoComma();
      ((Currency)localObject1).setFormat((String)localObject2);
    }
    return str;
  }
  
  protected String convertDataToString(Object paramObject, String paramString, boolean paramBoolean)
  {
    if ((paramObject == null) || (!paramString.equalsIgnoreCase("com.ffusion.beans.Currency"))) {
      return super.convertDataToString(paramObject, paramString, paramBoolean);
    }
    return convertDataToString(paramObject, paramString);
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    String str = this.bC.toString();
    this.bC.setLength(0);
    return str;
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    try
    {
      if (paramString1 != null)
      {
        this.bC.append(paramString1);
        this.bC.append(getEOLString());
      }
      if (paramString2 != null)
      {
        this.bC.append(paramString2);
        this.bC.append(getEOLString());
      }
      if (paramString3 != null)
      {
        this.bC.append(paramString3);
        if (paramString4 != null) {
          this.bC.append(",");
        } else {
          this.bC.append(getEOLString());
        }
      }
      if (paramString4 != null)
      {
        this.bC.append(paramString4);
        this.bC.append(getEOLString());
      }
      this.bC.append(getEOLString());
      Properties localProperties = paramReportCriteria.getSearchCriteria();
      String str1 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
      ArrayList localArrayList = paramReportCriteria.getSearchCriteriaOrder();
      Iterator localIterator = localArrayList.iterator();
      String str2;
      Object localObject4;
      while (localIterator.hasNext())
      {
        localObject1 = (String)localIterator.next();
        localObject2 = localProperties.getProperty((String)localObject1);
        if ((!((String)localObject1).endsWith("_HIDE")) && (!paramReportCriteria.isSearchCriterionHidden((String)localObject1)))
        {
          str2 = ReportConsts.getCriteriaName(str1, (String)localObject1, this._criteria.getLocale());
          localObject3 = paramReportCriteria.getDisplayValue((String)localObject1);
          if (localObject3 == null) {
            if (localObject2 == null) {
              localObject3 = new String();
            } else {
              localObject3 = ((String)localObject2).toString();
            }
          }
          if (((String)localObject3).length() != 0) {
            if (((String)localObject3).indexOf("&&") == -1)
            {
              this.bC.append(str2).append(": ").append((String)localObject3);
              this.bC.append(getEOLString());
            }
            else
            {
              localObject4 = new StringTokenizer((String)localObject3, "&&");
              while (((StringTokenizer)localObject4).hasMoreTokens())
              {
                this.bC.append(str2).append(": ").append(((StringTokenizer)localObject4).nextToken());
                this.bC.append(getEOLString());
              }
            }
          }
        }
      }
      Object localObject1 = localProperties.propertyNames();
      Object localObject5;
      while (((Enumeration)localObject1).hasMoreElements())
      {
        localObject2 = (String)((Enumeration)localObject1).nextElement();
        str2 = localProperties.getProperty((String)localObject2);
        if ((!localArrayList.contains(localObject2)) && (!((String)localObject2).endsWith("_HIDE")) && (!paramReportCriteria.isSearchCriterionHidden((String)localObject2)))
        {
          localObject3 = ReportConsts.getCriteriaName(str1, (String)localObject2, this._criteria.getLocale());
          localObject4 = paramReportCriteria.getDisplayValue((String)localObject2);
          if (localObject4 == null) {
            if (str2 == null) {
              localObject4 = new String();
            } else {
              localObject4 = str2.toString();
            }
          }
          if (((String)localObject4).length() != 0) {
            if (((String)localObject4).indexOf("&&") == -1)
            {
              this.bC.append((String)localObject3).append(": ").append((String)localObject4);
              this.bC.append(getEOLString());
            }
            else
            {
              localObject5 = new StringTokenizer((String)localObject4, "&&");
              while (((StringTokenizer)localObject5).hasMoreTokens())
              {
                this.bC.append((String)localObject3).append(": ").append(((StringTokenizer)localObject5).nextToken());
                this.bC.append(getEOLString());
              }
            }
          }
        }
      }
      Object localObject2 = paramReportCriteria.getSortCriteria();
      int i = 0;
      Object localObject3 = ((ReportSortCriteria)localObject2).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (ReportSortCriterion)((Iterator)localObject3).next();
        localObject5 = ReportConsts.getCriteriaName(str1, ((ReportSortCriterion)localObject4).getName(), this._criteria.getLocale()).trim();
        if (((String)localObject5).length() != 0) {
          i++;
        }
      }
      if (i > 0)
      {
        this.bC.append(ReportConsts.getText(10020, this._criteria.getLocale())).append(" ");
        localObject4 = new ArrayList();
        int j = 0;
        int k = 0;
        for (int m = 0; k < i; m++)
        {
          localObject3 = ((ReportSortCriteria)localObject2).iterator();
          while (((Iterator)localObject3).hasNext())
          {
            ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)((Iterator)localObject3).next();
            String str3 = ReportConsts.getCriteriaName(str1, localReportSortCriterion.getName(), this._criteria.getLocale()).trim();
            if (str3.length() != 0) {
              if (localReportSortCriterion.getOrdinal() == m)
              {
                k++;
                if (((ArrayList)localObject4).contains(str3)) {
                  break;
                }
                if (j > 0) {
                  this.bC.append(", ");
                }
                this.bC.append(str3);
                ((ArrayList)localObject4).add(str3);
                j++;
                break;
              }
            }
          }
        }
        this.bC.append(getEOLString());
      }
      this.bC.append(getEOLString());
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
      if (paramString != null)
      {
        this.bC.append(getEOLString());
        this.bC.append(paramString);
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
      this.bC.append("\"");
      this.bC.append(paramString1);
      this.bC.append("\"");
      this.bC.append(getEOLString());
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
      this.bC.append("\"");
      this.bC.append(paramString1);
      this.bC.append("\"");
      this.bC.append(getEOLString());
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
      if (this.bB) {
        this.bC.append(",");
      }
      int i = 0;
      if ((paramString1.equals("java.lang.String")) || (paramString1.equals("java.lang.Character"))) {
        i = 1;
      }
      if ((i != 0) && (paramObject != null)) {
        this.bC.append("\"");
      }
      String str = convertDataToString(paramObject, paramString1, paramBoolean);
      if ((i != 0) && (str != null) && (str.indexOf("\"") != -1)) {
        str = str.replaceAll("\"", "\"\"");
      }
      this.bC.append(str);
      if ((i != 0) && (paramObject != null)) {
        this.bC.append("\"");
      }
      this.bB = true;
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    try
    {
      int i = paramArrayList.size();
      if (i > 0)
      {
        if (this.bB) {
          this.bC.append(",");
        }
        for (int j = 0; j < i; j++)
        {
          if (j > 0) {
            this.bC.append(" ");
          }
          this.bC.append((String)paramArrayList.get(j));
        }
        this.bB = true;
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
    this.bB = false;
  }
  
  protected void doFiniReportResultTable()
    throws RptException
  {}
  
  protected void doInitReportResultRow()
    throws RptException
  {}
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    try
    {
      if (this.bB)
      {
        this.bB = false;
        this.bC.append(getEOLString());
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
      this.bC.append(getEOLString());
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
      this.bC.append(paramString).append(getEOLString());
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doAddPageBreak()
    throws RptException
  {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterComma
 * JD-Core Version:    0.7.0.1
 */