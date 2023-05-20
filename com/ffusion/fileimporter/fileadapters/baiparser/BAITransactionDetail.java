package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.csil.core.DataConsolidator;
import com.ffusion.dataconsolidator.adapter.DCUtil;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

public class BAITransactionDetail
  implements BAIParserConsts
{
  public static final int NUMBER_FIELDS = 7;
  public int _typeCode = -1;
  public BigDecimal _amount = null;
  public String _fundsType = "Z";
  public BigDecimal _immediateAvailabilityAmount = null;
  public BigDecimal _oneDayAvailabilityAmount = null;
  public BigDecimal _moreThanOneDayAvailabilityAmount = null;
  public Calendar _valueDate = null;
  public int _numberOfDistributions = 0;
  public Vector _distributions = null;
  public String _bankReferenceNumber = null;
  public String _customerReferenceNumber = null;
  public String _text = null;
  public ArrayList _warnings = new ArrayList();
  public int _lineNumber = 0;
  private static final Locale aTi = ;
  private HashMap aTj = null;
  
  public BAITransactionDetail(BAIAccountIdentifier paramBAIAccountIdentifier, String[] paramArrayOfString, int paramInt)
    throws FIException
  {
    this(-1, paramBAIAccountIdentifier, paramArrayOfString, paramInt, null);
  }
  
  public BAITransactionDetail(int paramInt1, BAIAccountIdentifier paramBAIAccountIdentifier, String[] paramArrayOfString, int paramInt2, HashMap paramHashMap)
    throws FIException
  {
    this._lineNumber = paramInt1;
    Object localObject1;
    if (paramBAIAccountIdentifier == null)
    {
      StringBuffer localStringBuffer = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        localStringBuffer.append(paramInt1);
      } else {
        localStringBuffer.append("number unknown");
      }
      localStringBuffer.append(": Invalid BAI transaction record. No matching account header record.");
      localObject1 = new FIException("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", 9714, localStringBuffer.toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    this.aTj = new HashMap();
    int i = 1;
    Object localObject2;
    if (paramArrayOfString.length < BAIParser.getNumberOfFieldsInCurrentRecord(paramInt1, paramArrayOfString))
    {
      localObject1 = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject1).append(paramInt1);
      } else {
        ((StringBuffer)localObject1).append("number unknown");
      }
      ((StringBuffer)localObject1).append(": Invalid BAI transaction record. Expected ").append(BAIParser.getNumberOfFieldsInCurrentRecord(paramInt1, paramArrayOfString)).append(" records. ");
      ((StringBuffer)localObject1).append("Found ").append(paramArrayOfString.length).append(".");
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", 9714, ((StringBuffer)localObject1).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if ((!paramArrayOfString[0].equals("16")) || (paramArrayOfString[1].length() == 0))
    {
      localObject1 = new FIException("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", 9714, "Error Parsing BAI Account BAITransactionDetail. Invalid BAI record code '" + paramArrayOfString[0] + "'. Exptected '16'.");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    try
    {
      localObject1 = null;
      localObject2 = null;
      if (paramArrayOfString[1].length() > 0) {
        this._typeCode = Integer.parseInt(paramArrayOfString[1]);
      }
      BAITypeCodeInfo localBAITypeCodeInfo = null;
      try
      {
        localBAITypeCodeInfo = DataConsolidator.getBAITypeCodeInfo(this._typeCode);
      }
      catch (CSILException localCSILException)
      {
        localObject4 = new StringBuffer("Line ");
        if (paramInt1 > 0) {
          ((StringBuffer)localObject4).append(paramInt1);
        } else {
          ((StringBuffer)localObject4).append("number unknown");
        }
        ((StringBuffer)localObject4).append(": BAI type code ").append(this._typeCode).append(" is not recognized but is loaded.");
        this._warnings.add(((StringBuffer)localObject4).toString());
        localBAITypeCodeInfo = DCUtil.genericObjectForUnknownBAITypeCodes(this._typeCode, "TypeCode " + this._typeCode + " not found from " + "baiTypeCodes.xml", 2, 0, -1);
      }
      if (localBAITypeCodeInfo.getDataType() == 0)
      {
        this._amount = BAIParserUtil.convertStrToAmount(paramArrayOfString[2], paramBAIAccountIdentifier._numberOfDecimals);
        if (this._amount.doubleValue() < 0.0D)
        {
          FIException localFIException2 = new FIException("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", 9727, "Negative amount in transaction detail record.");
          DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", localFIException2);
          throw localFIException2;
        }
        paramBAIAccountIdentifier.addAmountToRunningTotal(paramArrayOfString[2], paramInt2);
        int j = 0;
        if (localBAITypeCodeInfo.getTransaction() == 2) {
          j = 1;
        } else if (localBAITypeCodeInfo.getTransaction() == 1) {
          j = 0;
        }
        if ((this._amount != null) && (j != 0)) {
          this._amount = this._amount.negate();
        }
      }
      else
      {
        paramBAIAccountIdentifier.addAmountToRunningTotal(null, paramInt2);
        if (localBAITypeCodeInfo.getDataType() == 1)
        {
          this.aTj.put("Field" + i, paramArrayOfString[2]);
          i++;
        }
        else
        {
          Object localObject3;
          if (localBAITypeCodeInfo.getDataType() == 2)
          {
            localObject3 = BAIParserUtil.parseDateTime(this._lineNumber, paramArrayOfString[2], null);
            localObject4 = new DateTime((Calendar)localObject3, aTi);
            this.aTj.put("Field" + i, localObject4);
            i++;
          }
          else if (localBAITypeCodeInfo.getDataType() == 3)
          {
            localObject3 = BAIParserUtil.parseXMLableDate(paramArrayOfString[2], "HHmm");
            this.aTj.put("Field" + i, localObject3);
            i++;
          }
          else if (localBAITypeCodeInfo.getDataType() == 4)
          {
            localObject3 = BAIParserUtil.convertStrToAmount(paramArrayOfString[2], localBAITypeCodeInfo.getNumDecimals());
            this.aTj.put("Field" + i, localObject3);
            i++;
          }
        }
      }
      BigDecimal localBigDecimal1 = 4;
      if (paramArrayOfString[3].length() > 0)
      {
        this._fundsType = paramArrayOfString[3];
        if (this._fundsType.equals("S"))
        {
          this._immediateAvailabilityAmount = BAIParserUtil.convertStrToAmount(paramArrayOfString[(localBigDecimal1++)], paramBAIAccountIdentifier._numberOfDecimals);
          this._oneDayAvailabilityAmount = BAIParserUtil.convertStrToAmount(paramArrayOfString[(localBigDecimal1++)], paramBAIAccountIdentifier._numberOfDecimals);
          this._moreThanOneDayAvailabilityAmount = BAIParserUtil.convertStrToAmount(paramArrayOfString[(localBigDecimal1++)], paramBAIAccountIdentifier._numberOfDecimals);
        }
        else if (this._fundsType.equals("V"))
        {
          localObject1 = paramArrayOfString[(localBigDecimal1++)];
          localObject2 = paramArrayOfString[(localBigDecimal1++)];
          this._valueDate = BAIParserUtil.parseDateTime(this._lineNumber, (String)localObject1, (String)localObject2);
        }
        else if (this._fundsType.equals("D"))
        {
          localObject1 = paramArrayOfString[(localBigDecimal1++)];
          if (((String)localObject1).length() > 0) {
            this._numberOfDistributions = BAIParserUtil.parseInt((String)localObject1);
          }
          if (this._numberOfDistributions > 0)
          {
            localObject4 = null;
            localObject5 = null;
            localBigDecimal2 = null;
            this._distributions = new Vector();
            for (int k = 0; k < this._numberOfDistributions; k++)
            {
              localObject1 = paramArrayOfString[(localBigDecimal1++)];
              localObject2 = paramArrayOfString[(localBigDecimal1++)];
              BAIAvailabilityDistributions localBAIAvailabilityDistributions = new BAIAvailabilityDistributions(paramBAIAccountIdentifier, (String)localObject1, (String)localObject2);
              this._distributions.add(localBAIAvailabilityDistributions);
              switch (localBAIAvailabilityDistributions._availabilityInDays)
              {
              case 0: 
                if (localObject4 == null) {
                  localObject4 = new BigDecimal(0.0D);
                }
                localObject4 = ((BigDecimal)localObject4).add(localBAIAvailabilityDistributions._availableAmount);
                break;
              case 1: 
                if (localObject5 == null) {
                  localObject5 = new BigDecimal(0.0D);
                }
                localObject5 = ((BigDecimal)localObject5).add(localBAIAvailabilityDistributions._availableAmount);
                break;
              default: 
                if (localBigDecimal2 == null) {
                  localBigDecimal2 = new BigDecimal(0.0D);
                }
                localBigDecimal2 = localBigDecimal2.add(localBAIAvailabilityDistributions._availableAmount);
              }
            }
            this._immediateAvailabilityAmount = ((BigDecimal)localObject4);
            this._oneDayAvailabilityAmount = ((BigDecimal)localObject5);
            this._moreThanOneDayAvailabilityAmount = localBigDecimal2;
          }
        }
        else if (this._fundsType.equals("0"))
        {
          if (this._amount != null) {
            this._immediateAvailabilityAmount = new BigDecimal(this._amount.toString());
          }
        }
        else if (this._fundsType.equals("1"))
        {
          if (this._amount != null) {
            this._oneDayAvailabilityAmount = new BigDecimal(this._amount.toString());
          }
        }
        else if (this._fundsType.equals("2"))
        {
          if (this._amount != null) {
            this._moreThanOneDayAvailabilityAmount = new BigDecimal(this._amount.toString());
          }
        }
        else if (!this._fundsType.equals("Z"))
        {
          localObject4 = new StringBuffer("Line ");
          if (paramInt1 > 0) {
            ((StringBuffer)localObject4).append(paramInt1);
          } else {
            ((StringBuffer)localObject4).append("number unknown");
          }
          ((StringBuffer)localObject4).append(": Invalid funds type '").append(this._fundsType).append("' found.");
          localObject5 = new FIException("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", 9714, ((StringBuffer)localObject4).toString());
          DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", (Throwable)localObject5);
          throw ((Throwable)localObject5);
        }
      }
      localObject1 = paramArrayOfString[(localBigDecimal1++)];
      if (((String)localObject1).length() > 0) {
        this._bankReferenceNumber = ((String)localObject1);
      }
      localObject1 = paramArrayOfString[(localBigDecimal1++)];
      if (((String)localObject1).length() > 0) {
        this._customerReferenceNumber = ((String)localObject1);
      }
      Object localObject4 = new StringBuffer();
      Object localObject5 = System.getProperty("line.separator");
      BigDecimal localBigDecimal2 = localBigDecimal1;
      while (localBigDecimal1 < paramArrayOfString.length)
      {
        if (localBigDecimal1 != localBigDecimal2) {
          ((StringBuffer)localObject4).append((String)localObject5);
        }
        ((StringBuffer)localObject4).append(paramArrayOfString[(localBigDecimal1++)]);
      }
      this._text = ((StringBuffer)localObject4).toString();
    }
    catch (FIException localFIException1)
    {
      throw localFIException1;
    }
    catch (Exception localException)
    {
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", 9714, "Error Parsing BAI Account BAITransactionDetail", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAITransactionDetail", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
  }
  
  public HashMap getCustomData()
  {
    return this.aTj;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAITransactionDetail
 * JD-Core Version:    0.7.0.1
 */