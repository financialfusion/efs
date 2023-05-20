package com.ffusion.checkimaging.adapters;

import com.ffusion.beans.checkimaging.ImageResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class EmailImageAdapter
{
  static final int a = 600500;
  static final int jdField_case = 600501;
  static final int jdField_new = 600502;
  static final int jdField_else = 600503;
  static final int e = 600504;
  static final int d = 600505;
  static final int c = 600506;
  static final int g = 600507;
  static final int jdField_try = 600508;
  static final int jdField_int = 600509;
  static final int jdField_for = 0;
  static final int jdField_goto = 1;
  static final int jdField_char = 2;
  static final int jdField_void = 3;
  static final String[] b = { "png", "jpeg", "gif", "tiff" };
  static final String[] f = { "png", "jpg", "gif", "tif" };
  private ImageResult jdField_long;
  private String jdField_null;
  private String jdField_if;
  private String jdField_do;
  private String jdField_byte;
  
  public EmailImageAdapter(String paramString1, String paramString2, String paramString3, String paramString4, ImageResult paramImageResult)
  {
    this.jdField_null = paramString1;
    this.jdField_long = paramImageResult;
    this.jdField_if = paramString2;
    this.jdField_do = paramString3;
    this.jdField_byte = paramString4;
  }
  
  protected int sendImage()
  {
    i = 0;
    InternetAddress localInternetAddress = null;
    InternetAddress[] arrayOfInternetAddress = null;
    try
    {
      localInternetAddress = new InternetAddress(this.jdField_if);
      arrayOfInternetAddress = InternetAddress.parse(this.jdField_null, false);
    }
    catch (AddressException localAddressException)
    {
      return 600501;
    }
    HashMap localHashMap = this.jdField_long.getImage();
    byte[] arrayOfByte1 = null;
    byte[] arrayOfByte2 = null;
    arrayOfByte1 = (byte[])localHashMap.get(this.jdField_long.getFrontViewHandle());
    arrayOfByte2 = (byte[])localHashMap.get(this.jdField_long.getBackViewHandle());
    Properties localProperties = new Properties();
    if (localProperties == null) {
      return 600502;
    }
    localProperties.put("mail.smtp.host", CheckImagingAdapter.getSMTPHost());
    Session localSession = Session.getInstance(localProperties, null);
    if (localSession == null) {
      return 600503;
    }
    Transport localTransport = null;
    MimeMessage localMimeMessage = null;
    try
    {
      localMimeMessage = new MimeMessage(localSession);
      if (localMimeMessage == null)
      {
        int j = 600504;
        return j;
      }
      localMimeMessage.setFrom(localInternetAddress);
      localMimeMessage.setRecipients(Message.RecipientType.TO, arrayOfInternetAddress);
      localMimeMessage.setSubject(this.jdField_do);
      localMimeMessage.setSentDate(new Date());
      MimeBodyPart localMimeBodyPart = new MimeBodyPart();
      localMimeBodyPart.setText(this.jdField_byte);
      int k = 1;
      try
      {
        k = this.jdField_long.getCompressionType();
      }
      catch (Exception localException3) {}
      Object localObject1 = new MimeBodyPart();
      if (arrayOfByte1 != null)
      {
        ((MimeBodyPart)localObject1).setDataHandler(new DataHandler(new a(arrayOfByte1, "image/" + b[k])));
        ((MimeBodyPart)localObject1).setFileName("Image1." + f[k]);
        ((MimeBodyPart)localObject1).addHeader("Content-Transfer-Encoding", "base64");
        ((MimeBodyPart)localObject1).setDisposition("attachment");
      }
      Object localObject2 = new MimeBodyPart();
      if (arrayOfByte2 != null)
      {
        ((MimeBodyPart)localObject2).setDataHandler(new DataHandler(new a(arrayOfByte2, "image/" + b[k])));
        ((MimeBodyPart)localObject2).setFileName("Image2." + f[k]);
        ((MimeBodyPart)localObject2).addHeader("Content-Transfer-Encoding", "base64");
        ((MimeBodyPart)localObject2).setDisposition("attachment");
      }
      Object localObject3 = new MimeMultipart();
      ((Multipart)localObject3).addBodyPart(localMimeBodyPart);
      if (arrayOfByte1 != null) {
        ((Multipart)localObject3).addBodyPart((BodyPart)localObject1);
      }
      if (arrayOfByte2 != null) {
        ((Multipart)localObject3).addBodyPart((BodyPart)localObject2);
      }
      localMimeMessage.setContent((Multipart)localObject3);
      localMimeMessage.saveChanges();
      localTransport = localSession.getTransport(localInternetAddress);
      localTransport.connect();
      localTransport.sendMessage(localMimeMessage, arrayOfInternetAddress);
      try
      {
        Thread.sleep(5L);
      }
      catch (InterruptedException localInterruptedException2)
      {
        i = 600500;
      }
      Exception localException2;
      Address[] arrayOfAddress;
      int m;
      return i;
    }
    catch (Exception localException1)
    {
      if (localMimeMessage == null) {
        i = 600505;
      }
      try
      {
        Thread.sleep(5L);
      }
      catch (InterruptedException localInterruptedException1)
      {
        i = 600500;
      }
      localException2 = localException1;
      do
      {
        if ((localException2 instanceof SendFailedException))
        {
          localObject1 = (SendFailedException)localException2;
          localObject2 = ((SendFailedException)localObject1).getInvalidAddresses();
          if (localObject2 != null) {
            i = 600506;
          }
          localObject3 = ((SendFailedException)localObject1).getValidUnsentAddresses();
          if (localObject3 != null) {
            i = 600507;
          }
          arrayOfAddress = ((SendFailedException)localObject1).getValidSentAddresses();
          if (arrayOfAddress != null) {
            i = 600508;
          }
        }
        else
        {
          m = 600509;
          return m;
        }
      } while ((localException2 = ((MessagingException)localException2).getNextException()) != null);
    }
    finally
    {
      try
      {
        if (localTransport != null) {
          localTransport.close();
        }
      }
      catch (MessagingException localMessagingException) {}
    }
  }
  
  class a
    implements DataSource
  {
    private byte[] jdField_if;
    private String a;
    
    a(byte[] paramArrayOfByte, String paramString)
    {
      this.jdField_if = paramArrayOfByte;
      this.a = paramString;
    }
    
    public InputStream getInputStream()
      throws IOException
    {
      if (this.jdField_if == null) {
        throw new IOException("No Data");
      }
      return new ByteArrayInputStream(this.jdField_if);
    }
    
    public OutputStream getOutputStream()
      throws IOException
    {
      throw new IOException("Illegal Operation");
    }
    
    public String getContentType()
    {
      return this.a;
    }
    
    public String getName()
    {
      return "Hello";
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.checkimaging.adapters.EmailImageAdapter
 * JD-Core Version:    0.7.0.1
 */