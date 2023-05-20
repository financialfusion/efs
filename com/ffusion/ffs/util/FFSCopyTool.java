package com.ffusion.ffs.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class FFSCopyTool
{
  private final int a = 10240;
  
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    FFSCopyTool localFFSCopyTool = new FFSCopyTool();
    try
    {
      localFFSCopyTool.copy(paramArrayOfString[0], paramArrayOfString[1]);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void copy(String paramString1, String paramString2)
    throws Exception
  {
    try
    {
      File localFile1 = new File(paramString1);
      File localFile2 = new File(paramString2);
      System.out.println("Copying from: " + localFile1.getPath());
      System.out.println("          to: " + localFile2.getPath());
      if (!localFile1.exists()) {
        throw new Exception("Source does not exist, copy failed!");
      }
      if (localFile1.isFile())
      {
        if (!localFile2.exists())
        {
          File localFile3 = localFile2.getParentFile();
          if ((localFile3 != null) && (!localFile3.exists())) {
            localFile3.mkdirs();
          }
          copyFileToFile(localFile1, localFile2);
          return;
        }
        if (localFile2.isFile())
        {
          copyFileToFile(localFile1, localFile2);
          return;
        }
        if (localFile2.isDirectory()) {
          copyFileToFolder(localFile1, localFile2);
        }
      }
      else if (localFile1.isDirectory())
      {
        if ((localFile2.exists()) && (localFile2.isFile())) {
          throw new Exception("Can't copy folder to file");
        }
        copyFolderToFolder(localFile1, localFile2);
      }
    }
    catch (Exception localException)
    {
      throw localException;
    }
  }
  
  public void copyFileToFile(File paramFile1, File paramFile2)
    throws Exception
  {
    byte[] arrayOfByte = new byte[10240];
    try
    {
      Object localObject = new FileInputStream(paramFile1);
      localObject = new BufferedInputStream((InputStream)localObject);
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile2));
      System.out.println("Copying file " + paramFile1.getPath() + " to " + paramFile2.getPath() + " ...");
      int i = -1;
      for (;;)
      {
        i = ((InputStream)localObject).read(arrayOfByte, 0, 10240);
        if (i == -1) {
          break;
        }
        localBufferedOutputStream.write(arrayOfByte, 0, i);
      }
      localBufferedOutputStream.flush();
      ((InputStream)localObject).close();
      localBufferedOutputStream.close();
    }
    catch (Exception localException)
    {
      throw localException;
    }
  }
  
  public void copyFileToFolder(File paramFile1, File paramFile2)
    throws Exception
  {
    if (!paramFile2.exists()) {
      return;
    }
    String str = paramFile2.getPath() + File.separator + paramFile1.getName();
    copyFileToFile(paramFile1, new File(str));
  }
  
  public void copyFolderToFolder(File paramFile1, File paramFile2)
    throws Exception
  {
    if (!paramFile2.exists()) {
      paramFile2.mkdirs();
    }
    File[] arrayOfFile = paramFile1.listFiles();
    if ((arrayOfFile == null) || (arrayOfFile.length == 0)) {
      return;
    }
    for (int i = 0; i < arrayOfFile.length; i++) {
      if (arrayOfFile[i] != null)
      {
        System.out.println("dirList: " + arrayOfFile[i].getPath());
        if (arrayOfFile[i].isDirectory()) {
          copyFolderToFolder(arrayOfFile[i], new File(paramFile2.getPath() + File.separator + arrayOfFile[i].getName()));
        } else {
          copyFileToFolder(arrayOfFile[i], paramFile2);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSCopyTool
 * JD-Core Version:    0.7.0.1
 */