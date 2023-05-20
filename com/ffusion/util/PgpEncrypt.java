package com.ffusion.util;

import cryptix.pgp.ArmouredMessage;
import cryptix.pgp.KeyGlob;
import cryptix.pgp.Passphrase;
import cryptix.pgp.PublicKeyRing;
import cryptix.pgp.SecretKeyRing;
import cryptix.security.rsa.PublicKey;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.util.Vector;

public class PgpEncrypt
{
  private String jdField_try;
  private String jdField_int;
  private String jdField_char;
  private String jdField_do;
  private Passphrase jdField_for = null;
  private PublicKeyRing jdField_goto = null;
  private KeyGlob jdField_byte = null;
  private Vector jdField_case = null;
  private static final int jdField_new = 10;
  private static final int jdField_if = 10;
  private static final int jdField_else = 1;
  private static final int a = 1024;
  
  public PgpEncrypt(String paramString1, String paramString2)
    throws Exception
  {
    this.jdField_try = paramString1;
    this.jdField_int = paramString2;
    this.jdField_goto = new PublicKeyRing(paramString2);
    PublicKey localPublicKey = this.jdField_goto.getKey(paramString1);
    this.jdField_case = new Vector();
    if (localPublicKey != null) {
      this.jdField_case.add(localPublicKey);
    } else {
      throw new Exception("Couldn't find key for userId: " + paramString1);
    }
    try
    {
      a(new PrintWriter(System.out, true), "Cryptix", "cryptix.provider.Cryptix", "Cryptix V3");
    }
    catch (RuntimeException localRuntimeException)
    {
      System.err.println(localRuntimeException.getMessage());
      System.err.println();
      System.err.println("Cryptix has not been installed.");
    }
  }
  
  public PgpEncrypt(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    this(paramString1, paramString2);
    this.jdField_char = paramString3;
    SecretKeyRing localSecretKeyRing = new SecretKeyRing(paramString3);
    this.jdField_for = new Passphrase(paramString4);
    this.jdField_byte = new KeyGlob();
    this.jdField_byte.addKeyRing(this.jdField_goto);
    this.jdField_byte.addKeyRing(localSecretKeyRing);
  }
  
  public boolean encryptFile(File paramFile1, File paramFile2)
  {
    FileInputStream localFileInputStream = null;
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileInputStream = new FileInputStream(paramFile1);
      int i = 0;
      byte[] arrayOfByte = new byte[1024];
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      while ((i = localFileInputStream.read(arrayOfByte)) > -1) {
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      ArmouredMessage localArmouredMessage = new ArmouredMessage(localByteArrayOutputStream.toString(), null, this.jdField_case);
      localFileOutputStream = new FileOutputStream(paramFile2);
      new PrintStream(localFileOutputStream).print(localArmouredMessage.ciphertext());
    }
    catch (Exception localException1)
    {
      System.out.println("Error during encryption: " + localException1);
      boolean bool = false;
      return bool;
    }
    finally
    {
      try
      {
        if (localFileInputStream != null) {
          localFileInputStream.close();
        }
        localFileOutputStream.close();
      }
      catch (Exception localException2) {}
    }
    return true;
  }
  
  public boolean decryptFile(File paramFile1, File paramFile2)
    throws Exception
  {
    FileInputStream localFileInputStream = null;
    FileOutputStream localFileOutputStream = null;
    if (this.jdField_for == null) {
      throw new Exception("Secret key file or passphrase information not given.");
    }
    try
    {
      localFileInputStream = new FileInputStream(paramFile1);
      int i = 0;
      byte[] arrayOfByte = new byte[1024];
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      while ((i = localFileInputStream.read(arrayOfByte)) > -1) {
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      ArmouredMessage localArmouredMessage = new ArmouredMessage(localByteArrayOutputStream.toString(), this.jdField_byte, this.jdField_for);
      localFileOutputStream = new FileOutputStream(paramFile2);
      new PrintStream(localFileOutputStream).print(localArmouredMessage.plaintext());
    }
    catch (Exception localException1)
    {
      System.out.println("Error during decryption: " + localException1);
      boolean bool = false;
      return bool;
    }
    finally
    {
      try
      {
        if (localFileInputStream != null) {
          localFileInputStream.close();
        }
        localFileOutputStream.close();
      }
      catch (Exception localException2) {}
    }
    return true;
  }
  
  public String getuserId()
  {
    return this.jdField_try;
  }
  
  public String getPublicKeyFile()
  {
    return this.jdField_int;
  }
  
  public String getSecretKeyFile()
  {
    return this.jdField_char;
  }
  
  public String getPassPhrase()
  {
    return this.jdField_do;
  }
  
  private void a(PrintWriter paramPrintWriter, String paramString1, String paramString2, String paramString3)
  {
    int i = 1;
    String str1 = System.getProperty("java.home");
    String str2 = System.getProperty("line.separator");
    Properties localProperties = new Properties();
    File localFile1 = new File(str1, "lib" + File.separator + "security" + File.separator);
    File localFile2 = new File(localFile1, "java.security");
    try
    {
      try
      {
        BufferedInputStream localBufferedInputStream1 = new BufferedInputStream(new FileInputStream(localFile2));
        localProperties.load(localBufferedInputStream1);
        localBufferedInputStream1.close();
      }
      catch (FileNotFoundException localFileNotFoundException1)
      {
        try
        {
          localFile2 = new File(localFile1, "JAVA.SEC");
          BufferedInputStream localBufferedInputStream2 = new BufferedInputStream(new FileInputStream(localFile2));
          localProperties.load(localBufferedInputStream2);
          localBufferedInputStream2.close();
        }
        catch (FileNotFoundException localFileNotFoundException3)
        {
          localFile2 = new File(localFile1, "java.security");
          throw localFileNotFoundException1;
        }
      }
    }
    catch (FileNotFoundException localFileNotFoundException2)
    {
      try
      {
        if (localFile1.exists())
        {
          if (!localFile1.isDirectory())
          {
            paramPrintWriter.println("The installation program needs to create the directory");
            paramPrintWriter.println("  " + localFile1.getPath() + File.separator);
            paramPrintWriter.println("but a file already exists with that name.");
            throw new RuntimeException("Could not create directory.");
          }
        }
        else {
          localFile1.mkdirs();
        }
        DataOutputStream localDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(localFile2)));
        localDataOutputStream.writeBytes("# " + paramString3 + " (do not edit or delete this line)" + str2 + "# This is the \"master security properties file\"." + str2 + "#" + str2 + "# The " + paramString1 + " installation program was unable to find an existing" + str2 + "# java.security file, so it created this one." + str2 + str2 + "security.provider.1=" + paramString2 + str2);
        localDataOutputStream.close();
        i = 10;
        paramPrintWriter.println("The file " + localFile2);
        paramPrintWriter.println("has been created.");
        return;
      }
      catch (IOException localIOException2)
      {
        i = 1;
        paramPrintWriter.println("The file " + localFile2);
        paramPrintWriter.println("could not be created because of an I/O exception.");
        throw new RuntimeException(localIOException2.toString());
      }
    }
    catch (IOException localIOException1)
    {
      i = 1;
      paramPrintWriter.println("Failed to load the java.security file.");
      throw new RuntimeException(localIOException1.toString());
    }
    catch (SecurityException localSecurityException1)
    {
      i = 1;
      paramPrintWriter.println("Not allowed to load the java.security file.");
      throw new RuntimeException(localSecurityException1.toString());
    }
    for (int j = 1;; j++)
    {
      str3 = localProperties.getProperty("security.provider." + j);
      if (str3 == null) {
        break;
      }
      if (str3.equals(paramString2))
      {
        i = 10;
        paramPrintWriter.println(paramString1 + " is already installed.");
        return;
      }
    }
    if (localProperties.getProperty("security.provider." + (j + 1)) != null)
    {
      paramPrintWriter.println("Warning: additional providers may have been added that were not previously");
      paramPrintWriter.println("recognized, because a gap in the sequence of provider numbers has been filled.");
      paramPrintWriter.println("You should edit the java.security file and check that it is correct.");
      paramPrintWriter.println();
    }
    String str3 = str2 + "# Added by " + paramString3 + " installation program:" + str2 + "security.provider." + j + "=" + paramString2 + str2;
    try
    {
      RandomAccessFile localRandomAccessFile = new RandomAccessFile(localFile2, "rw");
      long l = localFile2.length();
      localRandomAccessFile.seek(l);
      localRandomAccessFile.writeBytes(str3);
      localRandomAccessFile.close();
      i = 10;
      paramPrintWriter.println("The following lines were added to");
      paramPrintWriter.println("  " + localFile2 + ":");
      paramPrintWriter.println(str3);
      paramPrintWriter.println("To uninstall " + paramString1 + ", remove these lines manually.");
    }
    catch (IOException localIOException3)
    {
      i = 1;
      paramPrintWriter.println("The file " + localFile2);
      paramPrintWriter.println("could not be written to because of an I/O exception.");
      throw new RuntimeException(localIOException3.toString());
    }
    catch (SecurityException localSecurityException2)
    {
      i = 1;
      paramPrintWriter.println("The file " + localFile2);
      paramPrintWriter.println("could not be written to because of a security exception.");
      throw new RuntimeException(localSecurityException2.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.PgpEncrypt
 * JD-Core Version:    0.7.0.1
 */