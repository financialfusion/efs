package com.ffusion.ffs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FFSJarTool
{
  static final String jdField_byte = "FFSJarTool";
  private ZipOutputStream jdField_do;
  private ZipInputStream jdField_new;
  private byte[] jdField_try = new byte[1024];
  private boolean jdField_for = false;
  private final int jdField_if = 1024;
  private final String jdField_case = "/";
  private final char a = '/';
  private CRC32 jdField_int = new CRC32();
  
  public void setCompress(boolean paramBoolean)
  {
    this.jdField_for = paramBoolean;
  }
  
  public void jarIt(String paramString, String[] paramArrayOfString, boolean paramBoolean)
    throws Exception
  {
    File localFile = new File(paramString);
    System.out.println("FFSJarTool: Creating jar file: " + localFile.getPath());
    localFile = localFile.getParentFile();
    if ((localFile != null) && (!localFile.exists())) {
      localFile.mkdirs();
    }
    this.jdField_do = new ZipOutputStream(new FileOutputStream(paramString));
    this.jdField_do.setMethod(this.jdField_for ? 8 : 0);
    this.jdField_do.setLevel(-1);
    String str = ".";
    try
    {
      for (int i = 0; i < paramArrayOfString.length; i++) {
        if (paramArrayOfString[i] != null)
        {
          localObject = paramArrayOfString[i];
          localFile = new File((String)localObject);
          if (paramBoolean) {
            str = ".";
          } else {
            str = "tmp/" + i;
          }
          if (!localFile.exists()) {
            throw new Exception("File: " + localFile.getPath());
          }
          System.out.println("FFSJarTool: processing: " + localFile.getPath());
          if (localFile.isDirectory())
          {
            jdField_if(str + "/", localFile);
          }
          else
          {
            if (paramBoolean) {
              str = localFile.getName();
            } else {
              str = str + "/" + localFile.getName();
            }
            a(str, localFile);
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.jdField_do.close();
      this.jdField_do = null;
      Object localObject = new File(paramString);
      ((File)localObject).delete();
      throw localException;
    }
    this.jdField_do.close();
    this.jdField_do = null;
  }
  
  private void jdField_if(String paramString, File paramFile)
    throws Exception
  {
    String[] arrayOfString = paramFile.list();
    if ((arrayOfString == null) || (arrayOfString.length == 0)) {
      return;
    }
    for (int i = 0; i < arrayOfString.length; i++)
    {
      File localFile = new File(paramFile, arrayOfString[i]);
      String str = paramString + arrayOfString[i];
      if (localFile != null) {
        if (localFile.isDirectory()) {
          jdField_if(str + "/", localFile);
        } else {
          a(str, localFile);
        }
      }
    }
  }
  
  private void a(String paramString, File paramFile)
    throws Exception
  {
    int i = (int)paramFile.length();
    if (this.jdField_try.length < i) {
      this.jdField_try = new byte[i];
    }
    if (File.separatorChar != '/') {
      paramString = paramString.replace(File.separatorChar, '/');
    }
    ZipEntry localZipEntry = new ZipEntry(paramString);
    FileInputStream localFileInputStream = new FileInputStream(paramFile);
    int j;
    Object localObject;
    if (this.jdField_for)
    {
      j = localFileInputStream.read(this.jdField_try, 0, i);
    }
    else
    {
      localObject = new CheckedInputStream(localFileInputStream, this.jdField_int);
      this.jdField_int.reset();
      j = ((CheckedInputStream)localObject).read(this.jdField_try, 0, i);
      localZipEntry.setSize(i);
      localZipEntry.setCrc(this.jdField_int.getValue());
    }
    localFileInputStream.close();
    if (j != i)
    {
      localObject = "ERR_JAR_WRONG_LENGTH: " + paramFile.getPath() + new Integer(j).toString() + new Integer(i).toString();
      throw new Exception((String)localObject);
    }
    this.jdField_do.putNextEntry(localZipEntry);
    this.jdField_do.write(this.jdField_try, 0, i);
    this.jdField_do.closeEntry();
  }
  
  public void unJarIt(String paramString1, String paramString2)
    throws Exception
  {
    File localFile1 = new File(paramString2);
    if (!localFile1.exists()) {
      localFile1.mkdirs();
    }
    System.out.println("extracting: " + paramString1);
    this.jdField_new = new ZipInputStream(new FileInputStream(paramString1));
    while (this.jdField_new.available() == 1)
    {
      ZipEntry localZipEntry = this.jdField_new.getNextEntry();
      if ((localZipEntry != null) && (!localZipEntry.isDirectory()))
      {
        String str1 = paramString2 + File.separator + localZipEntry.getName().replace('/', File.separatorChar);
        localFile1 = new File(str1);
        File localFile2 = localFile1.getParentFile();
        if (!localFile2.exists()) {
          localFile2.mkdirs();
        }
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile1);
        int i = (int)localZipEntry.getSize();
        if (this.jdField_try.length < i) {
          this.jdField_try = new byte[i];
        }
        int j = this.jdField_new.read(this.jdField_try, 0, i);
        if (j != i)
        {
          String str2 = "ERR_JAR_WRONG_LENGTH: " + localFile1.getPath() + new Integer(j).toString() + new Integer(i).toString();
          throw new Exception(str2);
        }
        localFileOutputStream.write(this.jdField_try, 0, i);
        localFileOutputStream.close();
      }
    }
    this.jdField_new.close();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println("args.length: " + paramArrayOfString.length);
    if (paramArrayOfString.length < 3)
    {
      System.out.println("Usage: java FFSJarTool JAR jar-file source-folder0 [ source-folder1] ...");
      System.out.println("Or, java FFSJarTool UNJAR jar-file dest-folder");
      return;
    }
    try
    {
      FFSJarTool localFFSJarTool = new FFSJarTool();
      localFFSJarTool.setCompress(false);
      String str;
      Object localObject;
      if (paramArrayOfString[0].compareTo("JAR") == 0)
      {
        str = paramArrayOfString[1];
        localObject = new String[paramArrayOfString.length - 2];
        for (int i = 0; i < paramArrayOfString.length - 2; i++) {
          localObject[i] = paramArrayOfString[(i + 2)];
        }
        System.out.println("Jaring ...");
        localFFSJarTool.jarIt(str, (String[])localObject, true);
      }
      else if (paramArrayOfString[0].compareTo("UNJAR") == 0)
      {
        System.out.println("Unjaring ...");
        str = paramArrayOfString[1];
        localObject = paramArrayOfString[2];
        localFFSJarTool.unJarIt(str, (String)localObject);
      }
      else
      {
        System.out.println("Invalid option");
        System.out.println("Usage: java FFSJarTool JAR|UNJAR jar-file source-folder0 [ source-folder1] ...");
        System.out.println("Or, java FFSJarTool UNJAR jar-file dest-folder");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.util.FFSJarTool
 * JD-Core Version:    0.7.0.1
 */