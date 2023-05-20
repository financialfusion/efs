package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayImage
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected int error;
  protected String viewId;
  protected String imageSide;
  protected int rotation = 0;
  protected int rotatedImageFormat = 0;
  protected int displayImageError = 0;
  public static final int ROTATION_0 = 0;
  public static final int ROTATION_90 = 90;
  public static final int ROTATION_180 = 180;
  public static final int ROTATION_MINUS90 = -90;
  private static final int[] Vl = { 71, 73, 70, 56, 57, 97, 1, 0, 1, 0, 128, 255, 0, 192, 192, 192, 0, 0, 0, 33, 249, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59 };
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.displayImageError = 0;
    int i = 1;
    String str = null;
    byte[] arrayOfByte = null;
    HttpSession localHttpSession = null;
    HashMap localHashMap = null;
    ImageResult localImageResult = null;
    ImageResults localImageResults = null;
    localHttpSession = paramHttpServletRequest.getSession();
    localImageResults = (ImageResults)localHttpSession.getAttribute("AvailableImages");
    try
    {
      localImageResult = localImageResults.getByHandle(this.viewId);
      localHashMap = localImageResult.getImage();
      if (this.imageSide.equalsIgnoreCase("BACK")) {
        str = localImageResult.getBackViewHandle();
      } else {
        str = localImageResult.getFrontViewHandle();
      }
      try
      {
        i = localImageResult.getCompressionType();
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace(System.out);
      }
      arrayOfByte = (byte[])localHashMap.get(str);
      jdMethod_for(paramHttpServletResponse, arrayOfByte, i);
    }
    catch (Exception localException2)
    {
      DebugLog.log(Level.FINE, "IOException: Unable to read image", this);
      setDisplayImageErrorValue(400050);
      try
      {
        jdMethod_for(paramHttpServletResponse, Vl, 2);
      }
      catch (Exception localException3) {}
    }
    return "";
  }
  
  private void jdMethod_for(HttpServletResponse paramHttpServletResponse, byte[] paramArrayOfByte, int paramInt)
    throws Exception
  {
    paramHttpServletResponse.setStatus(200);
    paramHttpServletResponse.setHeader("cache-control", "no-cache");
    paramHttpServletResponse.addHeader("cache-control", "must-revalidate");
    paramHttpServletResponse.setHeader("pragma", "no-cache");
    if (getRotationValue() == 0) {
      try
      {
        paramHttpServletResponse.setContentType("image/" + ImageTask.MIMETYPES[paramInt]);
        ServletOutputStream localServletOutputStream1 = paramHttpServletResponse.getOutputStream();
        paramHttpServletResponse.setContentLength(paramArrayOfByte.length);
        localServletOutputStream1.write(paramArrayOfByte);
        localServletOutputStream1.flush();
        localServletOutputStream1.close();
      }
      catch (IOException localIOException1)
      {
        localIOException1.printStackTrace(System.out);
        setDisplayImageErrorValue(400053);
        throw localIOException1;
      }
    } else {
      try
      {
        MemoryCacheImageInputStream localMemoryCacheImageInputStream = new MemoryCacheImageInputStream(new ByteArrayInputStream(paramArrayOfByte));
        BufferedImage localBufferedImage1 = ImageIO.read(localMemoryCacheImageInputStream);
        String str = "image/" + ImageTask.MIMETYPES[getRotatedImageFormatValue()];
        String[] arrayOfString = ImageIO.getWriterMIMETypes();
        int i = 0;
        for (int j = 0; j < arrayOfString.length; j++) {
          if (str.equals(arrayOfString[j]))
          {
            i = 1;
            break;
          }
        }
        if (i == 0)
        {
          DebugLog.log(Level.FINE, "IOException: Unable to find writer for type: " + str, this);
          setDisplayImageErrorValue(400052);
          throw new IOException("No writer exists for type: " + str);
        }
        try
        {
          Object localObject = { 0.0D };
          double[] arrayOfDouble1 = { 0.0D, 1.0D, -1.0D, 0.0D, localBufferedImage1.getHeight(), 0.0D };
          double[] arrayOfDouble2 = { -1.0D, 0.0D, 0.0D, -1.0D, localBufferedImage1.getWidth(), localBufferedImage1.getHeight() };
          double[] arrayOfDouble3 = { 0.0D, -1.0D, 1.0D, 0.0D, 0.0D, localBufferedImage1.getWidth() };
          BufferedImage localBufferedImage2 = null;
          if (getRotationValue() == 90)
          {
            localObject = arrayOfDouble3;
            localBufferedImage2 = new BufferedImage(localBufferedImage1.getHeight(), localBufferedImage1.getWidth(), localBufferedImage1.getType());
          }
          else if (getRotationValue() == 180)
          {
            localObject = arrayOfDouble2;
            localBufferedImage2 = new BufferedImage(localBufferedImage1.getWidth(), localBufferedImage1.getHeight(), localBufferedImage1.getType());
          }
          else if (getRotationValue() == -90)
          {
            localObject = arrayOfDouble1;
            localBufferedImage2 = new BufferedImage(localBufferedImage1.getHeight(), localBufferedImage1.getWidth(), localBufferedImage1.getType());
          }
          AffineTransform localAffineTransform = new AffineTransform((double[])localObject);
          AffineTransformOp localAffineTransformOp = new AffineTransformOp(localAffineTransform, 1);
          localAffineTransformOp.filter(localBufferedImage1, localBufferedImage2);
          paramHttpServletResponse.setContentType("image/" + ImageTask.MIMETYPES[getRotatedImageFormatValue()]);
          try
          {
            ServletOutputStream localServletOutputStream2 = paramHttpServletResponse.getOutputStream();
            ImageIO.write(localBufferedImage2, ImageTask.MIMETYPES[getRotatedImageFormatValue()], localServletOutputStream2);
            localServletOutputStream2.flush();
            localServletOutputStream2.close();
          }
          catch (IOException localIOException3)
          {
            DebugLog.log(Level.FINE, "IOException: Unable to write image", this);
            setDisplayImageErrorValue(400053);
            throw localIOException3;
          }
        }
        catch (Exception localException)
        {
          DebugLog.log(Level.FINE, "Exception: Unable to rotate image", this);
          setDisplayImageErrorValue(400054);
          throw localException;
        }
      }
      catch (IOException localIOException2)
      {
        DebugLog.log(Level.FINE, "IOException: Unable to read image", this);
        setDisplayImageErrorValue(400050);
        throw localIOException2;
      }
    }
  }
  
  private void jdMethod_for(HttpServletResponse paramHttpServletResponse, int[] paramArrayOfInt, int paramInt)
    throws IOException
  {
    paramHttpServletResponse.setStatus(200);
    paramHttpServletResponse.setContentType("image/" + ImageTask.MIMETYPES[paramInt]);
    paramHttpServletResponse.setHeader("Cache-Control", "no-cache");
    paramHttpServletResponse.setHeader("Pragma", "no-cache");
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    paramHttpServletResponse.setContentLength(paramArrayOfInt.length);
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      localServletOutputStream.write(paramArrayOfInt[i]);
    }
    localServletOutputStream.flush();
    localServletOutputStream.close();
  }
  
  public void setViewerID(String paramString)
  {
    this.viewId = paramString;
  }
  
  public String getViewerID()
  {
    return this.viewId;
  }
  
  public void setImageSide(String paramString)
  {
    this.imageSide = paramString.toUpperCase();
  }
  
  public String getImageSide()
  {
    return this.imageSide;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public boolean setError(int paramInt, HttpSession paramHttpSession)
  {
    this.error = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public int getRotationValue()
  {
    return this.rotation;
  }
  
  public String getRotation()
  {
    return String.valueOf(this.rotation);
  }
  
  public void setRotationValue(int paramInt)
  {
    BigInteger localBigInteger = new BigInteger(String.valueOf(paramInt));
    localBigInteger = localBigInteger.mod(new BigInteger("360"));
    int i = localBigInteger.intValue();
    if (i == 270) {
      i = -90;
    }
    if ((i == 0) || (i == 90) || (i == 180) || (i == -90)) {
      this.rotation = i;
    } else {
      this.rotation = 0;
    }
  }
  
  public void setRotation(String paramString)
  {
    try
    {
      setRotationValue(Integer.parseInt(paramString));
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public int getRotatedImageFormatValue()
  {
    return this.rotatedImageFormat;
  }
  
  public String getRotatedImageFormat()
  {
    return String.valueOf(this.rotatedImageFormat);
  }
  
  public void setRotatedImageFormatValue(int paramInt)
  {
    if ((paramInt < ImageTask.FILEEXTENSIONS.length) && (paramInt >= 0)) {
      this.rotatedImageFormat = paramInt;
    }
  }
  
  public void setRotatedImageFormat(String paramString)
  {
    try
    {
      setRotatedImageFormatValue(Integer.parseInt(paramString));
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setRotateByValue(int paramInt)
  {
    setRotationValue(this.rotation + paramInt);
  }
  
  public void setRotateBy(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      setRotationValue(this.rotation + i);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public int getDisplayImageErrorValue()
  {
    return this.displayImageError;
  }
  
  public String getDisplayImageError()
  {
    return String.valueOf(this.displayImageError);
  }
  
  public void setDisplayImageErrorValue(int paramInt)
  {
    this.displayImageError = paramInt;
  }
  
  public void setDisplayImageError(String paramString)
  {
    try
    {
      this.displayImageError = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.checkimaging.DisplayImage
 * JD-Core Version:    0.7.0.1
 */