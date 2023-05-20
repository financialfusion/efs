package com.ffusion.ffs.util;

import java.math.BigDecimal;

public class ACHFileCache
  extends FFSCache
{
  public int curBatch = 0;
  public long recCount = 0L;
  public BigDecimal fileDebitSum = new BigDecimal("0");
  public BigDecimal fileCreditSum = new BigDecimal("0");
  public long fileEntryCount = 0L;
  public long fileHash = 0L;
  public int fileBatchCount = 0;
  public int fileBlockCount = 0;
  public Object ioObj = null;
  public ACHBatchCache batchCache = null;
  public String fileName = null;
  public boolean batchHeaderFound = false;
  public boolean batchCtrlFound = true;
  public StringBuffer content = null;
  public long debitCount = 0L;
  public long creditCount = 0L;
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.ACHFileCache
 * JD-Core Version:    0.7.0.1
 */