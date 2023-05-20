package com.ffusion.tasks.reporting;

import com.ffusion.beans.reporting.Image;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DisplayImage
  extends BaseTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  public static String IMAGE_ID = "ImageID";
  private static final int[] Uu = { 71, 73, 70, 56, 57, 97, 1, 0, 1, 0, 128, 255, 0, 192, 192, 192, 0, 0, 0, 33, 249, 4, 1, 0, 0, 0, 0, 44, 0, 0, 0, 0, 1, 0, 1, 0, 0, 2, 2, 68, 1, 0, 59 };
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = (String)localHttpSession.getAttribute(IMAGE_ID);
    HashMap localHashMap = (HashMap)localHttpSession.getAttribute("ReportImageList");
    try
    {
      Image localImage = (Image)localHashMap.get(str1);
      localObject = localImage.getImageData();
      String str2 = localImage.getImageType();
      paramHttpServletResponse.setStatus(200);
      paramHttpServletResponse.setHeader("cache-control", "no-cache");
      paramHttpServletResponse.addHeader("cache-control", "must-revalidate");
      paramHttpServletResponse.setHeader("pragma", "no-cache");
      paramHttpServletResponse.setContentType(str2);
      ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
      paramHttpServletResponse.setContentLength(localObject.length);
      localServletOutputStream.write((byte[])localObject);
      localServletOutputStream.flush();
      localServletOutputStream.close();
    }
    catch (Exception localException)
    {
      try
      {
        paramHttpServletResponse.setContentType("image/gif");
        Object localObject = paramHttpServletResponse.getOutputStream();
        paramHttpServletResponse.setContentLength(Uu.length);
        for (int i = 0; i < Uu.length; i++) {
          ((ServletOutputStream)localObject).write(Uu[i]);
        }
        ((ServletOutputStream)localObject).flush();
        ((ServletOutputStream)localObject).close();
      }
      catch (IOException localIOException) {}
    }
    return this.successURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.DisplayImage
 * JD-Core Version:    0.7.0.1
 */