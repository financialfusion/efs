package com.ffusion.efs.adapters.profile.servicecharge;

import com.ffusion.beans.DateTime;
import com.ffusion.efs.adapters.profile.ServiceChargeReportCriteria;
import com.ffusion.services.bcreport.BCReportService;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public abstract class DurationReportHandler
  extends BaseReportServiceCharge
{
  private a jdField_try = null;
  
  public DurationReportHandler(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void initialize(Connection paramConnection, ServiceChargeReportCriteria paramServiceChargeReportCriteria)
  {
    super.initialize(paramConnection, paramServiceChargeReportCriteria);
    setPreviousRecord(null);
  }
  
  protected boolean invalidLogRecord(String[] paramArrayOfString, int paramInt, Date paramDate)
  {
    boolean bool = false;
    if ((paramDate != null) && (isStart(paramArrayOfString, paramInt)) && (paramDate.getTime() > getReportCriteria().getEnd().getTime().getTime())) {
      bool = true;
    }
    return bool;
  }
  
  protected String[] getTransTypePairs(HashMap paramHashMap, String paramString)
  {
    String[] arrayOfString = new String[2];
    HashMap localHashMap = null;
    Object localObject = null;
    if (paramHashMap != null)
    {
      localHashMap = getReportMap(paramHashMap);
      if (localHashMap != null)
      {
        localObject = localHashMap.get(paramString);
        if ((localObject != null) && ((localObject instanceof ArrayList)))
        {
          arrayOfString[0] = ((Integer)((ArrayList)localObject).get(0)).toString();
          arrayOfString[1] = ((Integer)((ArrayList)localObject).get(1)).toString();
        }
      }
    }
    return arrayOfString;
  }
  
  private boolean a(String[] paramArrayOfString, int paramInt1, int paramInt2)
  {
    boolean bool = false;
    String str = Integer.toString(paramInt1);
    if ((paramArrayOfString != null) && (paramInt2 < paramArrayOfString.length) && (paramArrayOfString[paramInt2] != null) && (paramArrayOfString[paramInt2].trim().equalsIgnoreCase(str))) {
      bool = true;
    }
    return bool;
  }
  
  protected String formatSqlTranTypes(HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = getTransTypesDelimited(getReportCriteria().getChargeableOperationsAsArray(), this, paramHashMap, "");
    if ((str != null) && (str.trim().length() > 0))
    {
      localStringBuffer.append("AND ");
      localStringBuffer.append(DBUtil.generateSQLNumericInClause(str, "a.TRAN_TYPE"));
    }
    return localStringBuffer.toString();
  }
  
  protected boolean isStart(String[] paramArrayOfString, int paramInt)
  {
    return a(paramArrayOfString, paramInt, 0);
  }
  
  protected boolean isEnd(String[] paramArrayOfString, int paramInt)
  {
    return a(paramArrayOfString, paramInt, 1);
  }
  
  protected long calcDurationInMinutes(Date paramDate1, Date paramDate2)
  {
    long l1 = 0L;
    long l2 = 0L;
    if ((paramDate1 != null) && (paramDate2 != null))
    {
      l1 = Math.abs(paramDate2.getTime() - paramDate1.getTime());
      l2 = l1 / 1000L;
      l1 = l2;
    }
    return l1;
  }
  
  protected void storeRow()
    throws SQLException
  {
    a locala = new a();
    if (getResultSet() != null)
    {
      locala.jdMethod_if(getResultSet().getInt(1));
      locala.a(getResultSet().getLong(2));
      locala.a(getResultSet().getString(3));
      locala.a(getResultSet().getDate(4));
    }
    setPreviousRecord(locala);
  }
  
  protected void prepareReport(a parama, Date paramDate, String[] paramArrayOfString)
  {
    int i = parama.jdField_try();
    long l = parama.jdMethod_byte();
    Date localDate = null;
    if (isStart(paramArrayOfString, parama.jdMethod_for()))
    {
      parama.a(paramDate);
    }
    else if (isEnd(paramArrayOfString, parama.jdMethod_for()))
    {
      localDate = paramDate;
      i++;
      l += calcDurationInMinutes(parama.jdMethod_do(), localDate);
      parama.jdMethod_if(l);
      parama.a(i);
      parama.a(null);
    }
  }
  
  public void onKeyChange(long paramLong)
  {
    setId(paramLong);
  }
  
  public HashMap getReportMap(HashMap paramHashMap)
  {
    return (HashMap)paramHashMap.get(BCReportService.DURATION_MAP_KEY);
  }
  
  public a getPreviousRecord()
  {
    return this.jdField_try;
  }
  
  public void setPreviousRecord(a parama)
  {
    this.jdField_try = parama;
  }
  
  public static class a
  {
    private String jdField_if = null;
    private int jdField_do = 0;
    private long jdField_try = 0L;
    private int jdField_byte = 0;
    private String a = null;
    private long jdField_for = 0L;
    private String jdField_int = null;
    private Date jdField_new = null;
    
    public long jdField_byte()
    {
      return this.jdField_try;
    }
    
    public void jdField_if(long paramLong)
    {
      this.jdField_try = paramLong;
    }
    
    public String jdField_new()
    {
      return this.jdField_if;
    }
    
    public void jdField_if(String paramString)
    {
      this.jdField_if = paramString;
    }
    
    public int jdField_try()
    {
      return this.jdField_do;
    }
    
    public void a(int paramInt)
    {
      this.jdField_do = paramInt;
    }
    
    public String jdField_int()
    {
      return this.a;
    }
    
    public void a(String paramString)
    {
      this.a = paramString;
    }
    
    public Date jdField_do()
    {
      return this.jdField_new;
    }
    
    public void a(Date paramDate)
    {
      this.jdField_new = paramDate;
    }
    
    public String a()
    {
      return this.jdField_int;
    }
    
    public void jdField_do(String paramString)
    {
      this.jdField_int = paramString;
    }
    
    public long jdField_if()
    {
      return this.jdField_for;
    }
    
    public void a(long paramLong)
    {
      this.jdField_for = paramLong;
    }
    
    public int jdField_for()
    {
      return this.jdField_byte;
    }
    
    public void jdField_if(int paramInt)
    {
      this.jdField_byte = paramInt;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.servicecharge.DurationReportHandler
 * JD-Core Version:    0.7.0.1
 */