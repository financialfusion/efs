package com.ffusion.jtf.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

class TagHandlerUtil
{
  static boolean a(Throwable paramThrowable, PageContext paramPageContext)
  {
    boolean bool = true;
    if ((paramThrowable instanceof IllegalStateException)) {
      if (paramPageContext.getSession() == null) {
        bool = false;
      } else if ((paramPageContext.getRequest() instanceof HttpServletRequest)) {
        bool = ((HttpServletRequest)paramPageContext.getRequest()).isRequestedSessionIdValid();
      } else {
        try
        {
          paramPageContext.getSession().isNew();
        }
        catch (IllegalStateException localIllegalStateException)
        {
          bool = false;
        }
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.taglib.TagHandlerUtil
 * JD-Core Version:    0.7.0.1
 */