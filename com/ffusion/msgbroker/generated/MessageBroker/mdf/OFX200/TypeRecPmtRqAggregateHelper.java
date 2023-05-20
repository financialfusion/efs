/*   1:    */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
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
/*  13:    */ public abstract class TypeRecPmtRqAggregateHelper
/*  14:    */ {
/*  15:    */   public static TypeCode _type;
/*  16:    */   
/*  17:    */   public static TypeRecPmtRqAggregate clone(TypeRecPmtRqAggregate paramTypeRecPmtRqAggregate)
/*  18:    */   {
/*  19: 16 */     if (paramTypeRecPmtRqAggregate == null) {
/*  20: 18 */       return null;
/*  21:    */     }
/*  22: 20 */     TypeRecPmtRqAggregate localTypeRecPmtRqAggregate = new TypeRecPmtRqAggregate();
/*  23: 21 */     localTypeRecPmtRqAggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeRecPmtRqAggregate.CurDef)));
/*  24: 22 */     localTypeRecPmtRqAggregate.CurDefExists = paramTypeRecPmtRqAggregate.CurDefExists;
/*  25: 23 */     localTypeRecPmtRqAggregate.RecurrInst = TypeRecurrInstAggregateHelper.clone(paramTypeRecPmtRqAggregate.RecurrInst);
/*  26: 24 */     localTypeRecPmtRqAggregate.PmtInfo = TypePmtInfoAggregateHelper.clone(paramTypeRecPmtRqAggregate.PmtInfo);
/*  27: 25 */     localTypeRecPmtRqAggregate.InitialAmt = paramTypeRecPmtRqAggregate.InitialAmt;
/*  28: 26 */     localTypeRecPmtRqAggregate.InitialAmtExists = paramTypeRecPmtRqAggregate.InitialAmtExists;
/*  29: 27 */     localTypeRecPmtRqAggregate.FinalAmt = paramTypeRecPmtRqAggregate.FinalAmt;
/*  30: 28 */     localTypeRecPmtRqAggregate.FinalAmtExists = paramTypeRecPmtRqAggregate.FinalAmtExists;
/*  31: 29 */     return localTypeRecPmtRqAggregate;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static TypeRecPmtRqAggregate read(InputStream paramInputStream)
/*  35:    */   {
/*  36: 35 */     TypeRecPmtRqAggregate localTypeRecPmtRqAggregate = new TypeRecPmtRqAggregate();
/*  37: 36 */     localTypeRecPmtRqAggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/*  38: 37 */     localTypeRecPmtRqAggregate.CurDefExists = paramInputStream.read_boolean();
/*  39: 38 */     localTypeRecPmtRqAggregate.RecurrInst = TypeRecurrInstAggregateHelper.read(paramInputStream);
/*  40: 39 */     localTypeRecPmtRqAggregate.PmtInfo = TypePmtInfoAggregateHelper.read(paramInputStream);
/*  41: 40 */     localTypeRecPmtRqAggregate.InitialAmt = paramInputStream.read_double();
/*  42: 41 */     localTypeRecPmtRqAggregate.InitialAmtExists = paramInputStream.read_boolean();
/*  43: 42 */     localTypeRecPmtRqAggregate.FinalAmt = paramInputStream.read_double();
/*  44: 43 */     localTypeRecPmtRqAggregate.FinalAmtExists = paramInputStream.read_boolean();
/*  45: 44 */     return localTypeRecPmtRqAggregate;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void write(OutputStream paramOutputStream, TypeRecPmtRqAggregate paramTypeRecPmtRqAggregate)
/*  49:    */   {
/*  50: 51 */     if (paramTypeRecPmtRqAggregate == null) {
/*  51: 53 */       paramTypeRecPmtRqAggregate = new TypeRecPmtRqAggregate();
/*  52:    */     }
/*  53: 55 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeRecPmtRqAggregate.CurDef);
/*  54: 56 */     paramOutputStream.write_boolean(paramTypeRecPmtRqAggregate.CurDefExists);
/*  55: 57 */     TypeRecurrInstAggregateHelper.write(paramOutputStream, paramTypeRecPmtRqAggregate.RecurrInst);
/*  56: 58 */     TypePmtInfoAggregateHelper.write(paramOutputStream, paramTypeRecPmtRqAggregate.PmtInfo);
/*  57: 59 */     paramOutputStream.write_double(paramTypeRecPmtRqAggregate.InitialAmt);
/*  58: 60 */     paramOutputStream.write_boolean(paramTypeRecPmtRqAggregate.InitialAmtExists);
/*  59: 61 */     paramOutputStream.write_double(paramTypeRecPmtRqAggregate.FinalAmt);
/*  60: 62 */     paramOutputStream.write_boolean(paramTypeRecPmtRqAggregate.FinalAmtExists);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String _idl()
/*  64:    */   {
/*  65: 67 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecPmtRqAggregate";
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
/*  78: 82 */         new StructMember("PmtInfo", TypePmtInfoAggregateHelper.type(), null), 
/*  79: 83 */         new StructMember("InitialAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  80: 84 */         new StructMember("InitialAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/*  81: 85 */         new StructMember("FinalAmt", localORB.get_primitive_tc(TCKind.tk_double), null), 
/*  82: 86 */         new StructMember("FinalAmtExists", localORB.get_primitive_tc(TCKind.tk_boolean), null) };
/*  83:    */       
/*  84: 88 */       _type = localORB.create_struct_tc(id(), "TypeRecPmtRqAggregate", arrayOfStructMember);
/*  85:    */     }
/*  86: 90 */     return _type;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void insert(Any paramAny, TypeRecPmtRqAggregate paramTypeRecPmtRqAggregate)
/*  90:    */   {
/*  91: 97 */     OutputStream localOutputStream = paramAny.create_output_stream();
/*  92: 98 */     write(localOutputStream, paramTypeRecPmtRqAggregate);
/*  93: 99 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static TypeRecPmtRqAggregate extract(Any paramAny)
/*  97:    */   {
/*  98:105 */     if (!paramAny.type().equal(type())) {
/*  99:107 */       throw new BAD_OPERATION();
/* 100:    */     }
/* 101:109 */     return read(paramAny.create_input_stream());
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static String id()
/* 105:    */   {
/* 106:114 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecPmtRqAggregate:1.0";
/* 107:    */   }
/* 108:    */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRqAggregateHelper
 * JD-Core Version:    0.7.0.1
 */