package com.ffusion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil
{
  private static final int a = 1024;
  
  public static boolean zipFiles(File[] paramArrayOfFile, File paramFile)
  {
    return zipFiles(paramArrayOfFile, paramFile, true);
  }
  
  public static boolean zipFiles(File[] paramArrayOfFile, File paramFile, boolean paramBoolean)
  {
    ZipOutputStream localZipOutputStream = null;
    FileInputStream localFileInputStream = null;
    try
    {
      localZipOutputStream = new ZipOutputStream(new FileOutputStream(paramFile));
      if (paramBoolean) {
        localZipOutputStream.setMethod(8);
      } else {
        localZipOutputStream.setMethod(0);
      }
      int i = 0;
      byte[] arrayOfByte = new byte[1024];
      for (int j = 0; j < paramArrayOfFile.length; j++)
      {
        File localFile = paramArrayOfFile[j];
        if (!localFile.isDirectory()) {
          try
          {
            localFileInputStream = new FileInputStream(localFile);
            ZipEntry localZipEntry = new ZipEntry(localFile.getName());
            localZipOutputStream.putNextEntry(localZipEntry);
            while ((i = localFileInputStream.read(arrayOfByte)) > -1) {
              localZipOutputStream.write(arrayOfByte, 0, i);
            }
          }
          finally
          {
            localFileInputStream.close();
          }
        }
      }
      boolean bool;
      return true;
    }
    catch (Exception localException2)
    {
      bool = false;
      return bool;
    }
    finally
    {
      try
      {
        localZipOutputStream.close();
      }
      catch (Exception localException4) {}
    }
  }
  
  public static boolean zipFiles(ArrayList paramArrayList, File paramFile)
  {
    File[] arrayOfFile = new File[paramArrayList.size()];
    Iterator localIterator = paramArrayList.iterator();
    int i = 0;
    while (localIterator.hasNext()) {
      arrayOfFile[(i++)] = ((File)localIterator.next());
    }
    return zipFiles(arrayOfFile, paramFile);
  }
  
  public static boolean zipFile(File paramFile1, File paramFile2)
  {
    if (paramFile1.isDirectory()) {
      return zipFiles(paramFile1.listFiles(), paramFile2);
    }
    File[] arrayOfFile = { paramFile1 };
    return zipFiles(arrayOfFile, paramFile2);
  }
  
  public static boolean unzipFile(File paramFile1, File paramFile2)
  {
    if ((!paramFile1.exists()) || (!paramFile2.exists())) {
      return false;
    }
    ZipInputStream localZipInputStream = null;
    ZipEntry localZipEntry = null;
    FileOutputStream localFileOutputStream = null;
    try
    {
      localZipInputStream = new ZipInputStream(new FileInputStream(paramFile1));
      while ((localZipEntry = localZipInputStream.getNextEntry()) != null) {
        try
        {
          localFileOutputStream = new FileOutputStream(paramFile2.getAbsolutePath() + File.separatorChar + localZipEntry.getName());
          int i = 0;
          byte[] arrayOfByte = new byte[1024];
          while ((i = localZipInputStream.read(arrayOfByte)) > -1) {
            localFileOutputStream.write(arrayOfByte, 0, i);
          }
          localFileOutputStream.close();
        }
        finally
        {
          localFileOutputStream.close();
        }
      }
      boolean bool;
      return true;
    }
    catch (Exception localException2)
    {
      bool = false;
      return bool;
    }
    finally
    {
      try
      {
        localZipInputStream.close();
      }
      catch (Exception localException4) {}
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ZipUtil
 * JD-Core Version:    0.7.0.1
 */