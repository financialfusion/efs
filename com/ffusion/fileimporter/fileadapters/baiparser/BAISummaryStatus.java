package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;

public class BAISummaryStatus
  implements BAIParserConsts
{
  public int _typeCode = -1;
  public int _itemCount = -1;
  public String _fundsType = "Z";
  public BigDecimal _immediateAvailabilityAmount = null;
  public BigDecimal _oneDayAvailabilityAmount = null;
  public BigDecimal _moreThanOneDayAvailabilityAmount = null;
  public Calendar _valueDate = null;
  public int _numberOfDistributions = 0;
  public Vector _distributions = null;
  public Object _data = null;
  public Locale _defaultLocale = Locale.getDefault();
  public boolean _isEmptySummary = false;
  
  public int createBAISummaryStatus(BAIAccountIdentifier paramBAIAccountIdentifier, String[] paramArrayOfString, int paramInt)
    throws FIException
  {
    try
    {
      int i = paramInt;
      if (((paramArrayOfString[i] == null) || (paramArrayOfString[(i++)].equals(""))) && ((paramArrayOfString[i] == null) || (paramArrayOfString[(i++)].equals(""))) && ((paramArrayOfString[i] == null) || (paramArrayOfString[(i++)].equals(""))) && ((paramArrayOfString[i] == null) || (paramArrayOfString[(i++)].equals(""))))
      {
        this._isEmptySummary = true;
        return i;
      }
      localObject = null;
      String str = null;
      localObject = paramArrayOfString[(paramInt++)];
      if (((String)localObject).length() > 0) {
        this._typeCode = BAIParserUtil.parseInt((String)localObject);
      }
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(this._typeCode);
      }
      catch (CSILException localCSILException)
      {
        DebugLog.log(Level.WARNING, "TypeCode " + this._typeCode + " not found from " + "baiTypeCodes.xml");
        localBAITypeCodeInfo = DCUtil.genericObjectForUnknownBAITypeCodes(this._typeCode, "TypeCode " + this._typeCode + " not found from " + "baiTypeCodes.xml", 1, 0, -1);
      }
      if (localBAITypeCodeInfo.getDataType() == 0)
      {
        this._data = BAIParserUtil.convertStrToAmount(paramArrayOfString[paramInt], paramBAIAccountIdentifier._numberOfDecimals);
        paramBAIAccountIdentifier.addAmountToRunningTotal(paramArrayOfString[(paramInt++)], 0);
      }
      else if (localBAITypeCodeInfo.getDataType() == 1)
      {
        this._data = paramArrayOfString[(paramInt++)];
      }
      else if (localBAITypeCodeInfo.getDataType() == 2)
      {
        Calendar localCalendar = BAIParserUtil.parseDateTime(paramBAIAccountIdentifier._lineNumber, paramArrayOfString[(paramInt++)], null);
        this._data = new DateTime(localCalendar, this._defaultLocale);
      }
      else if (localBAITypeCodeInfo.getDataType() == 3)
      {
        this._data = BAIParserUtil.parseXMLableDate(paramArrayOfString[(paramInt++)], "HHmm");
      }
      else if (localBAITypeCodeInfo.getDataType() == 4)
      {
        this._data = BAIParserUtil.convertStrToAmount(paramArrayOfString[(paramInt++)], localBAITypeCodeInfo.getNumDecimals());
      }
      localObject = paramArrayOfString[(paramInt++)];
      if (((String)localObject).length() > 0) {
        this._itemCount = BAIParserUtil.parseInt((String)localObject);
      }
      localObject = paramArrayOfString[(paramInt++)];
      if (((String)localObject).length() > 0)
      {
        this._fundsType = ((String)localObject);
        BAIParserUtil.checkFundsType(this._fundsType);
        if (this._fundsType.equals("S"))
        {
          this._immediateAvailabilityAmount = BAIParserUtil.convertStrToAmount(paramArrayOfString[(paramInt++)], paramBAIAccountIdentifier._numberOfDecimals);
          this._oneDayAvailabilityAmount = BAIParserUtil.convertStrToAmount(paramArrayOfString[(paramInt++)], paramBAIAccountIdentifier._numberOfDecimals);
          this._moreThanOneDayAvailabilityAmount = BAIParserUtil.convertStrToAmount(paramArrayOfString[(paramInt++)], paramBAIAccountIdentifier._numberOfDecimals);
        }
        else if (this._fundsType.equals("V"))
        {
          localObject = paramArrayOfString[(paramInt++)];
          str = paramArrayOfString[(paramInt++)];
          this._valueDate = BAIParserUtil.parseDateTime(paramBAIAccountIdentifier._lineNumber, (String)localObject, str);
        }
        else if (this._fundsType.equals("D"))
        {
          localObject = paramArrayOfString[(paramInt++)];
          if (((String)localObject).length() > 0) {
            this._numberOfDistributions = BAIParserUtil.parseInt((String)localObject);
          }
          if (this._numberOfDistributions > 0)
          {
            this._distributions = new Vector();
            for (int j = 0; j < this._numberOfDistributions; j++)
            {
              localObject = paramArrayOfString[(paramInt++)];
              str = paramArrayOfString[(paramInt++)];
              this._distributions.add(new BAIAvailabilityDistributions(paramBAIAccountIdentifier, (String)localObject, str));
            }
          }
        }
      }
    }
    catch (FIException localFIException)
    {
      throw localFIException;
    }
    catch (Exception localException)
    {
      Object localObject = new FIException("com.ffusion.fileimporter.BAIParser.BAISummaryStatus", 9709, "Error Parsing BAI Summary Status", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAISummaryStatus", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    return paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAISummaryStatus
 * JD-Core Version:    0.7.0.1
 */