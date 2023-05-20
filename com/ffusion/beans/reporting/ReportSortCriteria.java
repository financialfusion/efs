package com.ffusion.beans.reporting;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;

public class ReportSortCriteria
  extends FilteredList
{
  static final String jdField_byte = "SORT_CRITERIA";
  
  public ReportSortCriterion create(int paramInt, String paramString, boolean paramBoolean)
  {
    return create(paramInt, paramString, paramBoolean, false);
  }
  
  public ReportSortCriterion create(int paramInt, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    ReportSortCriterion localReportSortCriterion1 = new ReportSortCriterion(paramInt, paramString, paramBoolean1);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ReportSortCriterion localReportSortCriterion2 = (ReportSortCriterion)localIterator.next();
      if (localReportSortCriterion2.getName().equals(paramString)) {
        localIterator.remove();
      } else if ((paramBoolean2 == true) && (localReportSortCriterion2.getOrdinal() == localReportSortCriterion1.getOrdinal())) {
        localIterator.remove();
      }
    }
    add(localReportSortCriterion1);
    return localReportSortCriterion1;
  }
  
  public ReportSortCriterion set(int paramInt, String paramString, boolean paramBoolean)
  {
    ReportSortCriterion localReportSortCriterion1 = new ReportSortCriterion(paramInt, paramString, paramBoolean);
    for (int i = 0; i < size(); i++)
    {
      ReportSortCriterion localReportSortCriterion2 = (ReportSortCriterion)get(i);
      if (localReportSortCriterion2.getOrdinal() == paramInt)
      {
        remove(i);
        i--;
      }
      else if (localReportSortCriterion2.getName().equals(paramString))
      {
        remove(i);
        i--;
      }
    }
    super.add(localReportSortCriterion1);
    return localReportSortCriterion1;
  }
  
  public void removeCriteriaByName(String paramString)
  {
    for (int i = 0; i < size(); i++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)get(i);
      if (localReportSortCriterion.getName().equals(paramString))
      {
        remove(i);
        i--;
      }
    }
  }
  
  public int getOrdinalOfCriteriaByName(String paramString)
  {
    for (int i = 0; i < size(); i++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)get(i);
      if (localReportSortCriterion.getName().equals(paramString)) {
        return localReportSortCriterion.getOrdinal();
      }
    }
    return -1;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "SORT_CRITERIA");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ReportSortCriterion)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "SORT_CRITERIA");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("SORT_CRITERION"))
      {
        ReportSortCriterion localReportSortCriterion = new ReportSortCriterion();
        localReportSortCriterion.continueXMLParsing(getHandler());
        ReportSortCriteria.this.add(localReportSortCriterion);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportSortCriteria
 * JD-Core Version:    0.7.0.1
 */