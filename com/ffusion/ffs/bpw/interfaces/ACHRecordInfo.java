package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSUtil;
import java.lang.reflect.Field;
import java.util.Hashtable;

public class ACHRecordInfo
  implements FFSConst, ACHConsts, Cloneable
{
  protected String zU = null;
  protected String zV = null;
  protected String Am = null;
  protected String Ak = null;
  protected String z4 = null;
  protected String z6 = null;
  protected String Al = null;
  protected Object zZ = null;
  protected String z2 = null;
  protected int zX = -1;
  protected String z1 = null;
  protected String z5 = null;
  protected String z3 = null;
  protected String z0 = null;
  protected String Ag = null;
  protected String zW = null;
  protected String Ac = null;
  protected int zT = 1;
  protected String zS = null;
  protected String z9 = "MsgSet";
  protected ACHAddendaInfo[] Aa = null;
  protected String Af = "add";
  protected int z7 = -1;
  protected String Ab = null;
  protected String zY = null;
  protected String z8 = null;
  protected String Ai = null;
  protected ACHPayeeInfo Ae = null;
  protected int Ad = 0;
  protected String An = null;
  protected String Aj = null;
  protected Hashtable Ah = null;
  
  public Hashtable getMemo()
  {
    return this.Ah;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.Ah = paramHashtable;
  }
  
  public String getTaxFormId()
  {
    return this.Aj;
  }
  
  public void setTaxFormId(String paramString)
  {
    this.Aj = paramString;
  }
  
  public String getLogId()
  {
    return this.Ai;
  }
  
  public void setLogId(String paramString)
  {
    this.Ai = paramString;
  }
  
  public String getRecordCategory()
  {
    return this.z8;
  }
  
  public void setRecordCategory(String paramString)
  {
    this.z8 = paramString;
  }
  
  public String getPairedID()
  {
    return this.zY;
  }
  
  public void setPairedID(String paramString)
  {
    this.zY = paramString;
  }
  
  public String getRecordId()
  {
    return this.zU;
  }
  
  public void setRecordId(String paramString)
  {
    this.zU = paramString;
  }
  
  public String getPayeeId()
  {
    return this.Ak;
  }
  
  public void setPayeeId(String paramString)
  {
    this.Ak = paramString;
  }
  
  public String getBankAcctType()
  {
    return this.z4;
  }
  
  public void setBankAcctType(String paramString)
  {
    this.z4 = paramString;
  }
  
  public String getPayeeAcct()
  {
    return this.z6;
  }
  
  public String getPayeeAcctForClassCode()
  {
    if ((this.z1 == null) || (this.z6 == null)) {
      return this.z6;
    }
    this.z6 = a(this.z6, 22, 15);
    return this.z6;
  }
  
  public void setPayeeAcct(String paramString)
  {
    this.z6 = paramString;
  }
  
  public String getPayeeName()
  {
    return this.Al;
  }
  
  public String getPayeeNameForClassCode()
  {
    if ((this.z1 == null) || (this.Al == null)) {
      return this.Al;
    }
    this.Al = a(this.Al, 15, 22);
    return this.Al;
  }
  
  public void setPayeeName(String paramString)
  {
    this.Al = paramString;
  }
  
  public String getBatchId()
  {
    return this.zV;
  }
  
  public void setBatchId(String paramString)
  {
    this.zV = paramString;
  }
  
  public String getFileId()
  {
    return this.Am;
  }
  
  public void setFileId(String paramString)
  {
    this.Am = paramString;
  }
  
  public Object getRecord()
  {
    return this.zZ;
  }
  
  public void setRecord(Object paramObject)
  {
    this.zZ = paramObject;
  }
  
  public String getRecordContent()
  {
    return this.z2;
  }
  
  public void setRecordContent(String paramString)
  {
    this.z2 = paramString;
  }
  
  public int getRecordType()
  {
    return this.zX;
  }
  
  public void setRecordType(int paramInt)
  {
    this.zX = paramInt;
  }
  
  public String getClassCode()
  {
    return this.z1;
  }
  
  public void setClassCode(String paramString)
  {
    this.z1 = paramString;
  }
  
  public String getSubmitDate()
  {
    return this.z5;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.z5 = paramString;
  }
  
  public String getSubmittedBy()
  {
    return this.z3;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.z3 = paramString;
  }
  
  public String getStartDate()
  {
    return this.z0;
  }
  
  public void setStartDate(String paramString)
  {
    this.z0 = paramString;
  }
  
  public String getEndDate()
  {
    return this.Ag;
  }
  
  public void setEndDate(String paramString)
  {
    this.Ag = paramString;
  }
  
  public String getSettlementDate()
  {
    return this.zW;
  }
  
  public void setSettlementDate(String paramString)
  {
    this.zW = paramString;
  }
  
  public String getFrequency()
  {
    return this.Ac;
  }
  
  public void setFrequency(String paramString)
  {
    this.Ac = paramString;
  }
  
  public int getPmtsCount()
  {
    return this.zT;
  }
  
  public void setPmtsCount(int paramInt)
  {
    this.zT = paramInt;
  }
  
  public String getRecordStatus()
  {
    return this.zS;
  }
  
  public void setRecordStatus(String paramString)
  {
    this.zS = paramString;
  }
  
  public String getAchVersion()
  {
    return this.z9;
  }
  
  public void setAchVersion(String paramString)
  {
    this.z9 = paramString;
  }
  
  public ACHAddendaInfo[] getAddenda()
  {
    return this.Aa;
  }
  
  public ACHAddendaInfo getAddendaAt(int paramInt)
  {
    if (this.Aa == null) {
      return null;
    }
    if (paramInt < this.Aa.length) {
      return this.Aa[paramInt];
    }
    return null;
  }
  
  public void setAddenda(ACHAddendaInfo[] paramArrayOfACHAddendaInfo)
  {
    this.Aa = paramArrayOfACHAddendaInfo;
  }
  
  public void updateAddendaInArray(ACHAddendaInfo paramACHAddendaInfo)
  {
    if (paramACHAddendaInfo == null) {
      return;
    }
    String str = paramACHAddendaInfo.getAction();
    if (str == null) {
      return;
    }
    if (str.compareTo("add") == 0) {
      a(paramACHAddendaInfo);
    } else if (str.compareTo("mod") == 0) {
      jdMethod_if(paramACHAddendaInfo);
    }
  }
  
  private void a(ACHAddendaInfo paramACHAddendaInfo)
  {
    int i = 0;
    if (this.Aa != null) {
      i = this.Aa.length;
    }
    ACHAddendaInfo[] arrayOfACHAddendaInfo = new ACHAddendaInfo[i + 1];
    arrayOfACHAddendaInfo[i] = paramACHAddendaInfo;
    System.arraycopy(this.Aa, 0, arrayOfACHAddendaInfo, 0, i);
    this.Aa = arrayOfACHAddendaInfo;
  }
  
  private void jdMethod_if(ACHAddendaInfo paramACHAddendaInfo)
  {
    int i = 0;
    if (this.Aa != null) {
      i = this.Aa.length;
    }
    if (i == 0) {
      return;
    }
    if (i == 1)
    {
      this.Aa = null;
      return;
    }
    ACHAddendaInfo[] arrayOfACHAddendaInfo = new ACHAddendaInfo[i - 1];
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      String str = this.Aa[k].getAddendaId();
      if (!str.equals(paramACHAddendaInfo.getAddendaId()))
      {
        arrayOfACHAddendaInfo[j] = this.Aa[k];
        j++;
      }
    }
    this.Aa = arrayOfACHAddendaInfo;
  }
  
  public String getAction()
  {
    return this.Af;
  }
  
  public void setAction(String paramString)
  {
    this.Af = paramString;
  }
  
  public int getStatusCode()
  {
    return this.z7;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.z7 = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.Ab;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.Ab = paramString;
  }
  
  public short getFieldValueShort(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueShort(this.zZ, paramString);
  }
  
  public int getFieldValueInt(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueInt(this.zZ, paramString);
  }
  
  public long getFieldValueLong(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueLong(this.zZ, paramString);
  }
  
  public String getFieldValueString(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueString(this.zZ, paramString);
  }
  
  public Object getFieldValueObject(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueObject(this.zZ, paramString);
  }
  
  public double getFieldValueDouble(String paramString)
  {
    return FFSUtil.getACHMBClassFieldValueDouble(this.zZ, paramString);
  }
  
  public Integer getAddendaCount()
  {
    if (this.Aa == null) {
      return new Integer(0);
    }
    return new Integer(this.Aa.length);
  }
  
  public boolean isValidAddendaIndicator()
    throws FFSException
  {
    if (this.zZ == null) {
      throw new FFSException(17000, "ACHRecordInfo.isValidAddendaIndicator::recordBatch header is missing");
    }
    Short localShort = (Short)getFieldValueObject("Addenda_Record_Indicator");
    if (localShort == null) {
      return false;
    }
    return (localShort.shortValue() == 0) || (localShort.shortValue() == 1);
  }
  
  public boolean hasAddenda()
    throws FFSException
  {
    if (this.zZ == null) {
      throw new FFSException(17000, "ACHRecordInfo.isValidAddendaIndicator::recordBatch header is missing");
    }
    Short localShort = (Short)getFieldValueObject("Addenda_Record_Indicator");
    if (localShort == null) {
      throw new FFSException(22460, "Invalid entry detail record, Addenda indicator is missing");
    }
    if (localShort.shortValue() == 0) {
      return false;
    }
    if (localShort.shortValue() == 1) {
      return true;
    }
    throw new FFSException(22560, "Invalid Addenda Indicator value, not 0 or 1");
  }
  
  public void setFieldValueObject(String paramString, Object paramObject)
  {
    try
    {
      FFSUtil.setACHMBClassFieldValueObject(this.zZ, paramString, paramObject);
    }
    catch (FFSException localFFSException) {}
  }
  
  public boolean isDebit()
    throws FFSException
  {
    String str = getFieldValueString("Transaction_Code");
    int i = 0;
    int j = 0;
    try
    {
      i = Integer.parseInt(str);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new FFSException(localNumberFormatException, "Invalid entry detail record, Transaction code is non-numeric");
    }
    j = i % 10;
    return j >= 5;
  }
  
  public ACHPayeeInfo getPayeeAggregateInfo()
  {
    return this.Ae;
  }
  
  public void setPayeeAggregateInfo(ACHPayeeInfo paramACHPayeeInfo)
  {
    this.Ae = paramACHPayeeInfo;
  }
  
  public void setPayeeInfoToMBRecord()
  {
    setFieldValueObject("DFI_Account_Number", this.Ae.getDFIAccountNumber(getClassCode()));
    setFieldValueObject("Receiving_DFI_Identification", this.Ae.getBankRTN8());
    setFieldValueObject("Check_Digit", new Short(this.Ae.getCheckDigit()));
    String str1 = a(this.Ae.getPayAcct(), 22, 15);
    aL(str1);
    this.Ae.setPayAcct(str1);
    String str2 = a(this.Ae.getPayeeName(), 15, 22);
    aM(str2);
    this.Ae.setPayeeName(str2);
  }
  
  public boolean setFieldValueRecord(String paramString, Object paramObject)
  {
    try
    {
      Field localField = this.zZ.getClass().getDeclaredField(paramString);
      localField.set(this.zZ, paramObject);
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
  
  private void aL(String paramString)
  {
    boolean bool = true;
    if (paramString == null) {
      bool = false;
    }
    if (!setFieldValueRecord("Identification_Number", paramString))
    {
      setFieldValueObject("Individual_Identification_Number", paramString);
      setFieldValueObject("Individual_Identification_NumberExists", new Boolean(bool));
    }
    else
    {
      setFieldValueObject("Identification_NumberExists", new Boolean(bool));
    }
  }
  
  private void aM(String paramString)
  {
    boolean bool = true;
    if (paramString == null) {
      bool = false;
    }
    if (!setFieldValueRecord("Individual_Name", paramString)) {
      setFieldValueObject("Receiving_Company_Name", paramString);
    } else {
      setFieldValueObject("Individual_NameExists", new Boolean(bool));
    }
  }
  
  public ACHPayeeInfo getPayeeInfoFromMBRecord()
  {
    ACHPayeeInfo localACHPayeeInfo = new ACHPayeeInfo();
    localACHPayeeInfo.setManagedPayee("N");
    localACHPayeeInfo.setBankAcctId(getFieldValueString("DFI_Account_Number"));
    localACHPayeeInfo.setBankRTN(getFieldValueString("Receiving_DFI_Identification"));
    localACHPayeeInfo.setCheckDigit(getFieldValueShort("Check_Digit"));
    String str1 = aw();
    String str2 = ax();
    if (str1 == null) {
      str1 = "";
    }
    if (str2 == null) {
      str2 = "";
    }
    localACHPayeeInfo.setPayAcct(str1);
    localACHPayeeInfo.setPayeeName(str2);
    localACHPayeeInfo.setNickName(str1 + "-" + str2);
    return localACHPayeeInfo;
  }
  
  private String aw()
  {
    String str = null;
    str = getFieldValueString("Identification_Number");
    if (str == null)
    {
      str = getFieldValueString("Individual_Identification_Number");
      if (str == null)
      {
        str = getFieldValueString("Check_Serial_Number");
        if (str == null)
        {
          str = getFieldValueString("Receiving_Company_Identification_Number");
          if (str == null) {
            str = getFieldValueString("Individual_Card_Account_Number");
          }
        }
      }
    }
    return str;
  }
  
  private String ax()
  {
    String str = null;
    str = getFieldValueString("Individual_Name");
    if (str == null)
    {
      str = getFieldValueString("Receiving_Company_Name");
      if (str == null)
      {
        str = getFieldValueString("Receiving_Company_Identification_Number");
        if (str == null) {
          str = getFieldValueString("Individual_Card_Account_Number");
        }
      }
    }
    return str;
  }
  
  public int getDirtyFlag()
  {
    return this.Ad;
  }
  
  public void setDirtyFlag(int paramInt)
  {
    this.Ad = paramInt;
  }
  
  public String getOffsetAccountID()
  {
    return this.An;
  }
  
  public void setOffsetAccountID(String paramString)
  {
    this.An = paramString;
  }
  
  private String a(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString == null) {
      return null;
    }
    if (this.z1 == null) {
      return paramString;
    }
    int i = paramString.length();
    int j = 0;
    if ((this.z1.equals("MTE")) || (this.z1.equals("CIE"))) {
      j = i > paramInt1 ? paramInt1 : i;
    } else {
      j = i > paramInt2 ? paramInt2 : i;
    }
    paramString = paramString.substring(0, j);
    return paramString;
  }
  
  public Object clone()
  {
    ACHRecordInfo localACHRecordInfo = null;
    try
    {
      localACHRecordInfo = (ACHRecordInfo)super.clone();
    }
    catch (Exception localException) {}
    return localACHRecordInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHRecordInfo
 * JD-Core Version:    0.7.0.1
 */