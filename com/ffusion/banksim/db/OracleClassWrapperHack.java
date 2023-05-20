/*  1:   */ package com.ffusion.banksim.db;
/*  2:   */ 
/*  3:   */ import com.ffusion.banksim.interfaces.BSDBParams;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ import java.io.Writer;
/*  7:   */ import java.sql.SQLException;
/*  8:   */ import oracle.sql.BLOB;
/*  9:   */ import oracle.sql.CLOB;
/* 10:   */ 
/* 11:   */ class OracleClassWrapperHack
/* 12:   */ {
/* 13:   */   void a(DBConnection paramDBConnection, String paramString, int paramInt, Object[] paramArrayOfObject)
/* 14:   */     throws SQLException, IOException
/* 15:   */   {
/* 16:47 */     if ((paramDBConnection.getParams().getConnectionType() != 4) || (paramDBConnection.getParams().isNativeDriver())) {
/* 17:49 */       throw new SQLException("Error: can't execute thin LOB stream on non-Oracle or non-thin driver");
/* 18:   */     }
/* 19:52 */     Object[] arrayOfObject = { new Integer(paramInt) };
/* 20:53 */     DBResultSet localDBResultSet = paramDBConnection.prepareQuery(paramString);
/* 21:54 */     localDBResultSet.open(arrayOfObject);
/* 22:   */     try
/* 23:   */     {
/* 24:56 */       if (localDBResultSet.getNextRow()) {
/* 25:57 */         for (int i = 0; i < paramArrayOfObject.length; i++)
/* 26:   */         {
/* 27:   */           Object localObject1;
/* 28:   */           Object localObject2;
/* 29:58 */           if ((paramArrayOfObject[i] instanceof String))
/* 30:   */           {
/* 31:64 */             localObject1 = (CLOB)localDBResultSet.getColumnClob(i + 1);
/* 32:65 */             localObject2 = ((CLOB)localObject1).getCharacterOutputStream();
/* 33:66 */             ((Writer)localObject2).write((String)paramArrayOfObject[i]);
/* 34:67 */             ((Writer)localObject2).flush();
/* 35:68 */             ((Writer)localObject2).close();
/* 36:   */           }
/* 37:70 */           else if ((paramArrayOfObject[i] instanceof byte[]))
/* 38:   */           {
/* 39:76 */             localObject1 = (BLOB)localDBResultSet.getColumnBlob(i + 1);
/* 40:77 */             localObject2 = ((BLOB)localObject1).getBinaryOutputStream();
/* 41:78 */             ((OutputStream)localObject2).write((byte[])paramArrayOfObject[i]);
/* 42:79 */             ((OutputStream)localObject2).flush();
/* 43:80 */             ((OutputStream)localObject2).close();
/* 44:   */           }
/* 45:   */           else
/* 46:   */           {
/* 47:83 */             throw new SQLException("Error: Unsupported Oracle LOB type");
/* 48:   */           }
/* 49:   */         }
/* 50:   */       }
/* 51:   */     }
/* 52:   */     finally
/* 53:   */     {
/* 54:88 */       localDBResultSet.close();
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.OracleClassWrapperHack
 * JD-Core Version:    0.7.0.1
 */