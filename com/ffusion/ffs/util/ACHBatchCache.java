package com.ffusion.ffs.util;

import java.math.BigDecimal;
import java.util.HashSet;

public class ACHBatchCache
  extends FFSCache
{
  public int curRec = 0;
  public BigDecimal batchDebitSum = FFSUtil.BD_ZERO;
  public BigDecimal batchCreditSum = FFSUtil.BD_ZERO;
  public int batchEntryCount = 0;
  public long batchHash = 0L;
  public String compId = null;
  public String orgDfiId = null;
  public String compACHId = null;
  public String batchBalanceType = null;
  public int recordCount = 0;
  public String orgDfiRTN = null;
  public String batchNo = null;
  public String stdEntryClassCode = null;
  public String serviceClassCode = null;
  public String dueDate = null;
  public String settlementDate = null;
  public HashSet offsetAcctIds = null;
  public long debitCount = 0L;
  public long creditCount = 0L;
  public BigDecimal nonOffBatchDebitSum = FFSUtil.BD_ZERO;
  public BigDecimal nonOffBatchCreditSum = FFSUtil.BD_ZERO;
  public int nonOffBatchEntryCount = 0;
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.ACHBatchCache
 * JD-Core Version:    0.7.0.1
 */