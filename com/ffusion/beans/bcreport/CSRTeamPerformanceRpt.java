package com.ffusion.beans.bcreport;

import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocaleableBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class CSRTeamPerformanceRpt
  extends LocaleableBean
  implements IReportResult, ExportFormats
{
  private DateFormat SO = DateFormat.getDateInstance(1);
  private DateFormat SP = DateFormat.getTimeInstance(1);
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private ArrayList SN = null;
  
  public void setCasePerformances(ArrayList paramArrayList)
  {
    this.SN = paramArrayList;
  }
  
  public ArrayList getCasePerformances()
  {
    return this.SN;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    if (paramHashMap != null)
    {
      this.SO = new SimpleDateFormat((String)paramHashMap.get("DateFormat"));
      this.SP = new SimpleDateFormat((String)paramHashMap.get("TimeFormat"));
    }
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = jdMethod_for(',', paramHashMap);
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_for(char paramChar, HashMap paramHashMap)
  {
    String str = "TRUE";
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    int i8 = 0;
    int i9 = 0;
    if (paramHashMap == null)
    {
      localObject1 = "The report criteria used in a call to export a CSRTeamPerformanceRpt did not contain a valid report options object.  This object is required for report exporting.";
      return new StringBuffer((String)localObject1);
    }
    Object localObject1 = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
    Object localObject2 = ((ReportCriteria)localObject1).getReportOptions();
    if (str.equals(((Properties)localObject2).getProperty("NUM_CASES_OPENED"))) {
      i = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("TIME_TO_OPEN_AVG"))) {
      j = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("TIME_TO_OPEN_RANGE"))) {
      k = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("TIME_TO_OPEN_STD_DEV"))) {
      m = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_NUM_SINGLE_AGENT_IN_PROG"))) {
      n = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_PERCENT_SINGLE_AGENT_IN_PROG"))) {
      i1 = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_NUM_SINGLE_AGENT_CLOSED"))) {
      i2 = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_PERCENT_SINGLE_AGENT_CLOSED"))) {
      i3 = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_NUM_HELP_REQ_IN_PROG"))) {
      i4 = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_PERCENT_HELP_REQ_IN_PROG"))) {
      i5 = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_NUM_HELP_REQ_CLOSED"))) {
      i6 = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_PERCENT_HELP_REQ_CLOSED"))) {
      i7 = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_NUM_HELP_PROVIDED_CLOSED"))) {
      i8 = 1;
    }
    if (str.equals(((Properties)localObject2).getProperty("CASE_RES_PERCENT_HELP_PROVIDED_CLOSED"))) {
      i9 = 1;
    }
    localObject1 = new StringBuffer();
    localObject2 = new StringBuffer();
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer("# ");
    StringBuffer localStringBuffer3 = new StringBuffer("Avg Time ");
    StringBuffer localStringBuffer4 = new StringBuffer("Range ");
    StringBuffer localStringBuffer5 = new StringBuffer("Std Deviation ");
    StringBuffer localStringBuffer6 = new StringBuffer("Single Agent # ");
    StringBuffer localStringBuffer7 = new StringBuffer("Single Agent % ");
    StringBuffer localStringBuffer8 = new StringBuffer("Single Agent # Closed ");
    StringBuffer localStringBuffer9 = new StringBuffer("Single Agent % Closed ");
    StringBuffer localStringBuffer10 = new StringBuffer("Help Requested # ");
    StringBuffer localStringBuffer11 = new StringBuffer("Help Requested % ");
    StringBuffer localStringBuffer12 = new StringBuffer("Help Requested # Closed ");
    StringBuffer localStringBuffer13 = new StringBuffer("Help Requested % Closed ");
    StringBuffer localStringBuffer14 = new StringBuffer("Help Provided # Closed ");
    StringBuffer localStringBuffer15 = new StringBuffer("Help Provided % Closed ");
    ArrayList localArrayList = getCasePerformances();
    if (localArrayList != null)
    {
      int i10 = 1;
      Iterator localIterator1 = localArrayList.iterator();
      while (localIterator1.hasNext())
      {
        Iterator localIterator2 = ((ArrayList)localIterator1.next()).iterator();
        int i11 = 1;
        while (localIterator2.hasNext())
        {
          CasePerformanceData localCasePerformanceData = (CasePerformanceData)localIterator2.next();
          if (i10 != 0) {
            if (localCasePerformanceData.getStartDateValue() != null)
            {
              ((StringBuffer)localObject2).append(localCasePerformanceData.getStartDateValue());
              ((StringBuffer)localObject2).append("-");
              ((StringBuffer)localObject2).append(localCasePerformanceData.getEndDateValue());
              ((StringBuffer)localObject2).append(paramChar);
            }
            else
            {
              ((StringBuffer)localObject2).append("Total");
            }
          }
          if (i11 != 0)
          {
            localStringBuffer1.append(localCasePerformanceData.getCaseType());
            i11 = 0;
          }
          if (i != 0)
          {
            localStringBuffer2.append(localCasePerformanceData.getCount());
            localStringBuffer2.append(paramChar);
          }
          if (j != 0)
          {
            localStringBuffer3.append(localCasePerformanceData.getTimeToOpenAvgString());
            localStringBuffer3.append(paramChar);
          }
          if (k != 0)
          {
            localStringBuffer4.append(localCasePerformanceData.getTimeToOpenMinString());
            localStringBuffer4.append("-");
            localStringBuffer4.append(localCasePerformanceData.getTimeToOpenMaxString());
            localStringBuffer4.append(paramChar);
          }
          if (m != 0)
          {
            localStringBuffer5.append(localCasePerformanceData.getTimeToOpenStdDevString());
            localStringBuffer5.append(paramChar);
          }
          if (n != 0)
          {
            localStringBuffer6.append(localCasePerformanceData.getResInProgSingleCount());
            localStringBuffer6.append(paramChar);
          }
          if (i1 != 0)
          {
            localStringBuffer7.append(localCasePerformanceData.getResInProgSinglePct());
            localStringBuffer7.append(paramChar);
          }
          if (i2 != 0)
          {
            localStringBuffer8.append(localCasePerformanceData.getResClosedSingleCount());
            localStringBuffer8.append(paramChar);
          }
          if (i3 != 0)
          {
            localStringBuffer9.append(localCasePerformanceData.getResClosedSinglePct());
            localStringBuffer9.append(paramChar);
          }
          if (i4 != 0)
          {
            localStringBuffer10.append(localCasePerformanceData.getResInProgHelpRqCount());
            localStringBuffer10.append(paramChar);
          }
          if (i5 != 0)
          {
            localStringBuffer11.append(localCasePerformanceData.getResInProgHelpRqPct());
            localStringBuffer11.append(paramChar);
          }
          if (i6 != 0)
          {
            localStringBuffer12.append(localCasePerformanceData.getResClosedHelpRqCount());
            localStringBuffer12.append(paramChar);
          }
          if (i7 != 0)
          {
            localStringBuffer13.append(localCasePerformanceData.getResClosedHelpRqPct());
            localStringBuffer13.append(paramChar);
          }
          if (i8 != 0)
          {
            localStringBuffer14.append(localCasePerformanceData.getResHelpedAndClosedCount());
            localStringBuffer14.append(paramChar);
          }
          if (i9 != 0)
          {
            localStringBuffer15.append(localCasePerformanceData.getResHelpedAndClosedPct());
            localStringBuffer15.append(paramChar);
          }
        }
        if (i10 != 0)
        {
          ((StringBuffer)localObject1).append((StringBuffer)localObject2);
          ((StringBuffer)localObject1).append(_lineSeparator);
          ((StringBuffer)localObject1).append(_lineSeparator);
          i10 = 0;
        }
        int i12 = 1;
        int i13 = 1;
        ((StringBuffer)localObject1).append(localStringBuffer1);
        ((StringBuffer)localObject1).append(_lineSeparator);
        if (i != 0)
        {
          ((StringBuffer)localObject1).append(localStringBuffer2.deleteCharAt(localStringBuffer2.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (j != 0)
        {
          if (i12 != 0)
          {
            ((StringBuffer)localObject1).append("Time to Open ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i12 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer3.deleteCharAt(localStringBuffer3.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (k != 0)
        {
          if (i12 != 0)
          {
            ((StringBuffer)localObject1).append("Time to Open ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i12 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer4.deleteCharAt(localStringBuffer4.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (m != 0)
        {
          if (i12 != 0)
          {
            ((StringBuffer)localObject1).append("Time to Open ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i12 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer5.deleteCharAt(localStringBuffer5.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (n != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer6.deleteCharAt(localStringBuffer6.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i1 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer7.deleteCharAt(localStringBuffer7.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i2 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer8.deleteCharAt(localStringBuffer8.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i3 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer9.deleteCharAt(localStringBuffer9.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i4 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer10.deleteCharAt(localStringBuffer10.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i5 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer11.deleteCharAt(localStringBuffer11.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i6 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer12.deleteCharAt(localStringBuffer12.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i7 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer13.deleteCharAt(localStringBuffer13.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i8 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer14.deleteCharAt(localStringBuffer14.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        if (i9 != 0)
        {
          if (i13 != 0)
          {
            ((StringBuffer)localObject1).append("Resolution ");
            ((StringBuffer)localObject1).append(_lineSeparator);
            i13 = 0;
          }
          ((StringBuffer)localObject1).append(localStringBuffer15.deleteCharAt(localStringBuffer15.length() - 1));
          ((StringBuffer)localObject1).append(_lineSeparator);
        }
        ((StringBuffer)localObject1).append(_lineSeparator);
        localStringBuffer1 = new StringBuffer();
        localStringBuffer2 = new StringBuffer("# ");
        localStringBuffer3 = new StringBuffer("Avg Time ");
        localStringBuffer4 = new StringBuffer("Range ");
        localStringBuffer5 = new StringBuffer("Std Deviation ");
        localStringBuffer6 = new StringBuffer("Single Agent # ");
        localStringBuffer7 = new StringBuffer("Single Agent % ");
        localStringBuffer8 = new StringBuffer("Single Agent # Closed ");
        localStringBuffer9 = new StringBuffer("Single Agent % Closed ");
        localStringBuffer10 = new StringBuffer("Help Requested # ");
        localStringBuffer11 = new StringBuffer("Help Requested % ");
        localStringBuffer12 = new StringBuffer("Help Requested # Closed ");
        localStringBuffer13 = new StringBuffer("Help Requested % Closed ");
        localStringBuffer14 = new StringBuffer("Help Provided # Closed ");
        localStringBuffer15 = new StringBuffer("Help Provided % Closed ");
      }
      ((StringBuffer)localObject1).append(_lineSeparator);
    }
    return localObject1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.CSRTeamPerformanceRpt
 * JD-Core Version:    0.7.0.1
 */