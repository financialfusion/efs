/*  1:   */ package com.ffusion.ffs.bpw.interfaces;
/*  2:   */ 
/*  3:   */ import BCD.BinaryHelper;
/*  4:   */ import com.sybase.CORBA.ObjectVal;
/*  5:   */ import org.omg.CORBA.Any;
/*  6:   */ import org.omg.CORBA.BAD_OPERATION;
/*  7:   */ import org.omg.CORBA.ORB;
/*  8:   */ import org.omg.CORBA.TypeCode;
/*  9:   */ import org.omg.CORBA.portable.InputStream;
/* 10:   */ import org.omg.CORBA.portable.OutputStream;
/* 11:   */ 
/* 12:   */ public abstract class InstructionTypeSeqHelper
/* 13:   */ {
/* 14:   */   public static TypeCode _type;
/* 15:   */   
/* 16:   */   public static InstructionType[] clone(InstructionType[] paramArrayOfInstructionType)
/* 17:   */   {
/* 18:16 */     if (paramArrayOfInstructionType == null) {
/* 19:18 */       return null;
/* 20:   */     }
/* 21:20 */     int i = paramArrayOfInstructionType.length;
/* 22:21 */     InstructionType[] arrayOfInstructionType = new InstructionType[i];
/* 23:22 */     for (int j = 0; j < i; j++) {
/* 24:24 */       arrayOfInstructionType[j] = ((InstructionType)ObjectVal.readObject(ObjectVal.writeObject(paramArrayOfInstructionType[j])));
/* 25:   */     }
/* 26:26 */     return arrayOfInstructionType;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static InstructionType[] read(InputStream paramInputStream)
/* 30:   */   {
/* 31:32 */     int i = paramInputStream.read_ulong();
/* 32:33 */     InstructionType[] arrayOfInstructionType = new InstructionType[i];
/* 33:34 */     for (int j = 0; j < i; j++) {
/* 34:36 */       arrayOfInstructionType[j] = ((InstructionType)ObjectVal.readObject(BinaryHelper.read(paramInputStream)));
/* 35:   */     }
/* 36:38 */     return arrayOfInstructionType;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void write(OutputStream paramOutputStream, InstructionType[] paramArrayOfInstructionType)
/* 40:   */   {
/* 41:45 */     if (paramArrayOfInstructionType == null) {
/* 42:47 */       paramArrayOfInstructionType = new InstructionType[0];
/* 43:   */     }
/* 44:49 */     int i = paramArrayOfInstructionType.length;
/* 45:50 */     paramOutputStream.write_ulong(i);
/* 46:51 */     for (int j = 0; j < i; j++) {
/* 47:53 */       BinaryHelper.write(paramOutputStream, ObjectVal.writeObject(paramArrayOfInstructionType[j]));
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static TypeCode type()
/* 52:   */   {
/* 53:61 */     if (_type == null)
/* 54:   */     {
/* 55:63 */       ORB localORB = ORB.init();
/* 56:64 */       _type = localORB.create_sequence_tc(0, BinaryHelper.type());
/* 57:   */     }
/* 58:66 */     return _type;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public static void insert(Any paramAny, InstructionType[] paramArrayOfInstructionType)
/* 62:   */   {
/* 63:73 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 64:74 */     write(localOutputStream, paramArrayOfInstructionType);
/* 65:75 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 66:   */   }
/* 67:   */   
/* 68:   */   public static InstructionType[] extract(Any paramAny)
/* 69:   */   {
/* 70:81 */     if (!paramAny.type().equal(type())) {
/* 71:83 */       throw new BAD_OPERATION();
/* 72:   */     }
/* 73:85 */     return read(paramAny.create_input_stream());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static String id()
/* 77:   */   {
/* 78:90 */     return "IDL:com/ffusion/ffs/bpw/interfaces/InstructionTypeSeq:1.0";
/* 79:   */   }
/* 80:   */ }


/* Location:           D:\drops\jd\jars\BPWAdmin.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.InstructionTypeSeqHelper
 * JD-Core Version:    0.7.0.1
 */