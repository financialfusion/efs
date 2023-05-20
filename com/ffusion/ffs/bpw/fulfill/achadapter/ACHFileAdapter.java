package com.ffusion.ffs.bpw.fulfill.achadapter;

import com.ffusion.ffs.bpw.db.ACHFile;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFileInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IACHFileAdapter;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.ACHAdapterConsts;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSStringTokenizer;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ACHFileAdapter
  implements IACHFileAdapter, ACHAdapterConsts
{
  private String gh = "";
  private String gk = "";
  private String gj = "";
  private int gg;
  private String gf;
  private String gi;
  private String gl = null;
  private String gm = null;
  
  public void start(String paramString1, String paramString2)
    throws Exception
  {
    FFSDebug.log("ACHFileAdapter.start is called", 6);
    this.gl = paramString1;
    this.gm = paramString2;
    String str1 = ACHAdapterUtil.getProperty("ach.export.dir", "export");
    this.gh = ACHAdapterUtil.getFileNameBase(str1);
    String str2 = this.gh + "temp";
    this.gk = ACHAdapterUtil.getFileNameBase(str2);
    String str3 = ACHAdapterUtil.getProperty("ach.error.dir", "error");
    this.gj = ACHAdapterUtil.getFileNameBase(str3);
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.gg = localPropertyConfig.LogLevel;
    this.gi = ACHAdapterUtil.getProperty("ach.export.achupload.file.FileExtension", "");
  }
  
  public void processACHFiles(ACHFileInfo[] paramArrayOfACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("ACHFileAdapter.processACHFiles is called", 6);
    if (paramArrayOfACHFileInfo == null) {
      return;
    }
    for (int i = 0; i < paramArrayOfACHFileInfo.length; i++) {
      jdMethod_if(paramArrayOfACHFileInfo[i], paramFFSConnectionHolder);
    }
  }
  
  public void shutdown()
    throws Exception
  {
    FFSDebug.log("ACHFileAdapter.shutdown is called", 6);
  }
  
  private void jdMethod_if(ACHFileInfo paramACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "ACHFileAdapter.processACHFile: ";
    if (paramACHFileInfo == null) {
      return;
    }
    String str2 = paramACHFileInfo.getFileId();
    if (str2 == null) {
      return;
    }
    FFSDebug.log(str1 + "File id: " + str2, 6);
    paramACHFileInfo = ACHFile.getACHFileInfo(paramFFSConnectionHolder, paramACHFileInfo, false);
    if (paramACHFileInfo.getStatusCode() != 0)
    {
      FFSDebug.log(str1 + "Can not get ACH File Info from DB, skipping this file: " + str2, 0);
      return;
    }
    String str3 = ACHFile.getACHFileExportFileName(paramFFSConnectionHolder, str2, ".");
    if ((str3 == null) || (str3.length() == 0))
    {
      FFSDebug.log(str1 + "Can not get export file name, skipping this file: " + str2, 0);
      return;
    }
    str3 = str3 + ".ACH";
    if ((this.gi != null) && (this.gi.length() > 0)) {
      str3 = str3 + "." + this.gi;
    }
    String str4 = H(str3);
    FFSDebug.log(str1 + "Temp file name : " + str4, 6);
    int i = ACHFile.getMinACHFileChunkId(paramFFSConnectionHolder, str2);
    FFSDebug.log(str1 + "min chunk id: " + i, 6);
    int j = ACHFile.getMaxACHFileChunkId(paramFFSConnectionHolder, str2);
    FFSDebug.log(str1 + "max chunk id: " + j, 6);
    boolean bool = true;
    PrintWriter localPrintWriter = new PrintWriter(new FileWriter(str4, bool));
    try
    {
      for (int k = i; k <= j; k++)
      {
        localObject1 = ACHFile.getACHFileChunkByFileIdAndChunkId(paramFFSConnectionHolder, str2, k);
        if ((localObject1 != null) && (((String)localObject1).length() != 0))
        {
          localObject2 = null;
          if (FFSStringTokenizer.hasDelimeter((String)localObject1, ACHConsts.REC_DELIMS)) {
            localObject2 = new FFSStringTokenizer((String)localObject1, ACHConsts.REC_DELIMS);
          } else {
            localObject2 = new FFSStringTokenizer((String)localObject1, 94);
          }
          StringBuffer localStringBuffer = new StringBuffer();
          while (((FFSStringTokenizer)localObject2).hasMoreTokens())
          {
            String str6 = ((FFSStringTokenizer)localObject2).nextToken();
            localStringBuffer.append(str6).append(DBConsts.LINE_SEPARATOR);
          }
          localPrintWriter.write(localStringBuffer.toString());
          ACHFile.updateACHFileChunkStatus(paramFFSConnectionHolder, str2, k, "POSTEDON");
        }
      }
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    finally
    {
      localPrintWriter.close();
    }
    String str5 = I(str3);
    FFSDebug.log(str1 + "Export file name : " + str5, 6);
    Object localObject1 = new File(str4);
    Object localObject2 = new File(str5);
    ((File)localObject1).renameTo((File)localObject2);
    paramACHFileInfo.setExportFileName(str5);
    ACHFile.updateFilePostedOn(paramACHFileInfo, this.gm, paramFFSConnectionHolder);
    a(paramFFSConnectionHolder, paramACHFileInfo, "Successfully process an ACH file.");
    ACHAdapterUtil.doFMLoggingForACH(paramFFSConnectionHolder, ((File)localObject2).getCanonicalPath(), str1);
  }
  
  private final String I(String paramString)
    throws Exception
  {
    String str1 = this.gh + paramString;
    File localFile1 = new File(str1);
    if (localFile1.exists())
    {
      FFSDebug.log("ACHFileAdapter.getFullFileName, export ACH file exist: " + str1, 0);
      String str2 = this.gj + paramString + ".ACH" + "." + String.valueOf(System.currentTimeMillis());
      File localFile2 = new File(str2);
      localFile1.renameTo(localFile2);
      FFSDebug.log("ACHFileAdapter.getFullFileName, the existing file has been moved to  " + str2, 0);
    }
    return str1;
  }
  
  private final String H(String paramString)
    throws Exception
  {
    String str1 = "ACHFileAdapter.getFullTempFileName: ";
    String str2 = this.gk + paramString;
    File localFile = new File(str2);
    if (localFile.exists())
    {
      FFSDebug.log(str1 + " export ACH file exist. Deleting: " + str2, 0);
      localFile.delete();
    }
    return str2;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHFileInfo paramACHFileInfo, String paramString)
    throws FFSException
  {
    if (this.gg >= 4)
    {
      paramACHFileInfo = ACHFile.getACHFileInfo(paramFFSConnectionHolder, paramACHFileInfo, true);
      int i = 4303;
      if (paramACHFileInfo.getFileStatus().equals("POSTEDON")) {
        i = 3600;
      }
      ACHFile.doTransAuditLog(paramFFSConnectionHolder, paramACHFileInfo, paramACHFileInfo.getSubmittedBy(), paramString, i);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.achadapter.ACHFileAdapter
 * JD-Core Version:    0.7.0.1
 */