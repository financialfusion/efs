/*   1:    */ package com.ffusion.banksim.db;
/*   2:    */ 
/*   3:    */ import com.ffusion.banksim.interfaces.BSDBParams;
/*   4:    */ import com.ffusion.banksim.interfaces.BSException;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ 
/*  10:    */ public class DBConnectionPool
/*  11:    */ {
/*  12:    */   private int jdField_do;
/*  13:    */   private BSDBParams jdField_if;
/*  14:    */   private HashSet a;
/*  15:    */   private ArrayList jdField_for;
/*  16:    */   
/*  17:    */   public DBConnectionPool(BSDBParams paramBSDBParams, int paramInt)
/*  18:    */   {
/*  19: 21 */     this.jdField_do = (paramInt < 1 ? 1 : paramInt);
/*  20: 22 */     this.jdField_if = paramBSDBParams;
/*  21: 23 */     this.jdField_for = new ArrayList(paramInt);
/*  22:    */     
/*  23: 25 */     int i = this.jdField_do < 5 ? 11 : 2 * this.jdField_do + 1;
/*  24: 26 */     this.a = new HashSet(i);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public synchronized DBConnection getConnection()
/*  28:    */     throws BSException
/*  29:    */   {
/*  30: 34 */     return a(true);
/*  31:    */   }
/*  32:    */   
/*  33:    */   private DBConnection a(boolean paramBoolean)
/*  34:    */     throws BSException
/*  35:    */   {
/*  36: 44 */     DBConnection localDBConnection = null;
/*  37: 46 */     while (localDBConnection == null)
/*  38:    */     {
/*  39: 47 */       int i = this.jdField_for.size();
/*  40: 48 */       if (i == 0)
/*  41:    */       {
/*  42: 51 */         if ((paramBoolean) && (this.a.size() == this.jdField_do)) {
/*  43: 52 */           throw new BSException(10);
/*  44:    */         }
/*  45: 54 */         localDBConnection = DBConnection.create(this.jdField_if);
/*  46: 55 */         localDBConnection.open();
/*  47: 56 */         this.a.add(localDBConnection);
/*  48:    */       }
/*  49:    */       else
/*  50:    */       {
/*  51: 59 */         localDBConnection = (DBConnection)this.jdField_for.remove(i - 1);
/*  52: 60 */         if (!localDBConnection.isAlive())
/*  53:    */         {
/*  54: 62 */           this.a.remove(localDBConnection);
/*  55: 63 */           localDBConnection.close();
/*  56: 64 */           localDBConnection = null;
/*  57:    */         }
/*  58:    */       }
/*  59:    */     }
/*  60: 69 */     return localDBConnection;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public synchronized void releaseConnection(DBConnection paramDBConnection)
/*  64:    */     throws BSException
/*  65:    */   {
/*  66: 78 */     if (!this.a.contains(paramDBConnection)) {
/*  67: 81 */       throw new BSException(11);
/*  68:    */     }
/*  69: 86 */     if (!paramDBConnection.isAutoCommit()) {
/*  70: 87 */       throw new BSException(12);
/*  71:    */     }
/*  72: 93 */     if (this.a.size() <= this.jdField_do)
/*  73:    */     {
/*  74: 94 */       this.jdField_for.add(paramDBConnection);
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78: 96 */       this.a.remove(paramDBConnection);
/*  79: 97 */       paramDBConnection.close();
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized DBConnection renewConnection(DBConnection paramDBConnection)
/*  84:    */     throws BSException
/*  85:    */   {
/*  86:112 */     DBConnection localDBConnection = paramDBConnection;
/*  87:114 */     if (!this.a.contains(paramDBConnection)) {
/*  88:117 */       throw new BSException(11);
/*  89:    */     }
/*  90:120 */     if (paramDBConnection.isAlive())
/*  91:    */     {
/*  92:123 */       if (!paramDBConnection.isAutoCommit()) {
/*  93:    */         try
/*  94:    */         {
/*  95:125 */           paramDBConnection.setAutoCommit(true);
/*  96:    */         }
/*  97:    */         catch (SQLException localSQLException)
/*  98:    */         {
/*  99:128 */           localDBConnection = null;
/* 100:    */         }
/* 101:    */       }
/* 102:    */     }
/* 103:    */     else {
/* 104:133 */       localDBConnection = null;
/* 105:    */     }
/* 106:136 */     if (localDBConnection == null)
/* 107:    */     {
/* 108:139 */       localDBConnection = a(false);
/* 109:140 */       this.a.remove(paramDBConnection);
/* 110:141 */       paramDBConnection.close();
/* 111:142 */       paramDBConnection = null;
/* 112:    */     }
/* 113:144 */     return localDBConnection;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public synchronized void clear()
/* 117:    */   {
/* 118:152 */     Iterator localIterator = this.a.iterator();
/* 119:153 */     while (localIterator.hasNext()) {
/* 120:154 */       ((DBConnection)localIterator.next()).close();
/* 121:    */     }
/* 122:156 */     this.a.clear();
/* 123:157 */     this.jdField_for.clear();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public BSDBParams getParams()
/* 127:    */   {
/* 128:165 */     return this.jdField_if;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public final int getMaxConnections()
/* 132:    */   {
/* 133:173 */     return this.jdField_do;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public synchronized void setMaxConnections(int paramInt)
/* 137:    */   {
/* 138:181 */     int i = paramInt < 1 ? 1 : paramInt;
/* 139:182 */     if (this.jdField_do > i) {
/* 140:183 */       a(this.jdField_do - i);
/* 141:    */     }
/* 142:185 */     this.jdField_do = i;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void a(int paramInt)
/* 146:    */   {
/* 147:194 */     int i = this.jdField_for.size();
/* 148:195 */     int j = paramInt > i ? i : paramInt;
/* 149:197 */     for (int k = 1; k <= j; k++)
/* 150:    */     {
/* 151:198 */       DBConnection localDBConnection = (DBConnection)this.jdField_for.remove(i - k);
/* 152:199 */       this.a.remove(localDBConnection);
/* 153:200 */       localDBConnection.close();
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected void finalize()
/* 158:    */   {
/* 159:206 */     clear();
/* 160:    */   }
/* 161:    */ }


/* Location:           D:\drops\jd\jars\BankSim.jar
 * Qualified Name:     com.ffusion.banksim.db.DBConnectionPool
 * JD-Core Version:    0.7.0.1
 */