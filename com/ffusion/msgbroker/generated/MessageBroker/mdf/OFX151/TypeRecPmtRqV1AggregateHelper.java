/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
/*   2:    */ 
/*   3:    */ import com.sybase.CORBA.ObjectVal;
/*   4:    */ import org.omg.CORBA.Any;
/*   5:    */ import org.omg.CORBA.BAD_OPERATION;
/*   6:    */ import org.omg.CORBA.ORB;
/*   7:    */ import org.omg.CORBA.StructMember;
/*   8:    */ import org.omg.CORBA.TCKind;
/*   9:    */ import org.omg.CORBA.TypeCode;
/*  10:    */ import org.omg.CORBA.portable.InputStream;
/*  11:    */ import org.omg.CORBA.portable.OutputStream;
/*  12:    */ 
/*  13:    */ public abstract class TypeRecPmtRqV1AggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeRecPmtRqV1Aggregate clone(TypeRecPmtRqV1Aggregate paramTypeRecPmtRqV1Aggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeRecPmtRqV1Aggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeRecPmtRqV1Aggregate localTypeRecPmtRqV1Aggregate = new TypeRecPmtRqV1Aggregate();
/*  23: 21 */     localTypeRecPmtRqV1Aggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeRecPmtRqV1Aggregate.CurDef)));
/*  24: 22 */     localTypeRecPmtRqV1Aggregate.CurDefExists = paramTypeRecPmtRqV1Aggregate.CurDefExists;
/*  25: 23 */     localTypeRecPmtRqV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecPmtRqV1Aggregate.RecurrInst);
/*  26: 24 */     localTypeRecPmtRqV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.clone(paramTypeRecPmtRqV1Aggregate.PmtInfo);
/*  27: 25 */     localTypeRecPmtRqV1Aggregate.InitialAmt = paramTypeRecPmtRqV1Aggregate.InitialAmt;
/*  28: 26 */     localTypeRecPmtRqV1Aggregate.InitialAmtExists = paramTypeRecPmtRqV1Aggregate.InitialAmtExists;
/*  29: 27 */     localTypeRecPmtRqV1Aggregate.FinalAmt = paramTypeRecPmtRqV1Aggregate.FinalAmt;
/*  30: 28 */     localTypeRecPmtRqV1Aggregate.FinalAmtExists = paramTypeRecPmtRqV1Aggregate.FinalAmtExists;
/*  31: 29 */     return localTypeRecPmtRqV1Aggregate;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static TypeRecPmtRqV1Aggregate read(InputStream paramInputStream)
/*  35:    */   {
/*  36: 35 */     TypeRecPmtRqV1Aggregate localTypeRecPmtRqV1Aggregate = new TypeRecPmtRqV1Aggregate();
/*  37: 36 */     localTypeRecPmtRqV1Aggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  38: 37 */     localTypeRecPmtRqV1Aggregate.CurDefExists = paramInputStream.read_boolean();
/*  39: 38 */     localTypeRecPmtRqV1Aggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/*  40: 39 */     localTypeRecPmtRqV1Aggregate.PmtInfo = TypePmtInfoV1AggregateHelper.read(paramInputStream);
/*  41: 40 */     localTypeRecPmtRqV1Aggregate.InitialAmt = paramInputStream.read_double();
/*  42: 41 */     localTypeRecPmtRqV1Aggregate.InitialAmtExists = paramInputStream.read_boolean();
/*  43: 42 */     localTypeRecPmtRqV1Aggregate.FinalAmt = paramInputStream.read_double();
/*  44: 43 */     localTypeRecPmtRqV1Aggregate.FinalAmtExists = paramInputStream.read_boolean();
/*  45: 44 */     return localTypeRecPmtRqV1Aggregate;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtRqV1Aggregate paramTypeRecPmtRqV1Aggregate)
/*  49:    */   {
/*  50: 51 */     if (paramTypeRecPmtRqV1Aggregate == null) {
/*  51: 53 */       paramTypeRecPmtRqV1Aggregate = new TypeRecPmtRqV1Aggregate();
/*  52:    */     }
/*  53: 55 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeRecPmtRqV1Aggregate.CurDef);
/*  54: 56 */     paramOutputStream.write_boolean(paramTypeRecPmtRqV1Aggregate.CurDefExists);
/*  55: 57 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecPmtRqV1Aggregate.RecurrInst);
/*  56: 58 */     TypePmtInfoV1AggregateHelper.write(paramOutputStream, paramTypeRecPmtRqV1Aggregate.PmtInfo);
/*  57: 59 */     paramOutputStream.write_double(paramTypeRecPmtRqV1Aggregate.InitialAmt);
/*  58: 60 */     paramOutputStream.write_boolean(paramTypeRecPmtRqV1Aggregate.InitialAmtExists);
/*  59: 61 */     paramOutputStream.write_double(paramTypeRecPmtRqV1Aggregate.FinalAmt);
/*  60: 62 */     paramOutputStream.write_boolean(paramTypeRecPmtRqV1Aggregate.FinalAmtExists);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String _idl()
/*  64:    */   {
/*  65: 67 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeRecPmtRqV1Aggregate";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static TypeCode type()
/*  69:    */   {
/*  70: 74 */     if (_type == null)
/*  71:    */     {
/*  72: 76 */       ORB localORB = ORB.init();
/*  73: 77 */       StructMember[] arrayOfStructMember = 
/*  74: 78 */         {
/*  75: 79 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/*  76: 80 */         new StructMember("CurDefExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  77: 81 */         new StructMember("RecurrInst", TypeRecurrInstAggregateHelper.type(), null), 
/*  78: 82 */         new StructMember("PmtInfo", TypePmtInfoV1AggregateHelper.type(), null), 
/*  79: 83 */         new StructMember("InitialAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  80: 84 */         new StructMember("InitialAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  81: 85 */         new StructMember("FinalAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  82: 86 */         new StructMember("FinalAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  83:    */       
/*  84: 88 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtRqV1Aggregate", arrayOfStructMember);
/*  85:    */     }
/*  86: 90 */     return _type;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void insert(Any paramAny, TypeRecPmtRqV1Aggregate paramTypeRecPmtRqV1Aggregate)
/*  90:    */   {
/*  91: 97 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  92: 98 */     write(localOutputStream, paramTypeRecPmtRqV1Aggregate);
/*  93: 99 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static TypeRecPmtRqV1Aggregate extract(Any paramAny)
/*  97:    */   {
/*  98:105 */     if (!paramAny.type().equal(type())) {
/*  99:107 */       throw new BAD_OPERATION();
/* 100:    */     }
/* 101:109 */     return read(paramAny.create_input_stream());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static String id()
/* 105:    */   {
/* 106:114 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeRecPmtRqV1Aggregate:1.0";
/* 107:    */   }
/* 108:    */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRqV1AggregateHelper
 * JD-Core Version:    0.7.0.1
 */