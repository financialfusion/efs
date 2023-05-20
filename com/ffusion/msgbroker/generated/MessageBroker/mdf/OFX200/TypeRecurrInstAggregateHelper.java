/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200;
/*  2:   */ 
/*  3:   */ import com.sybase.CORBA.ObjectVal;
/*  4:   */ import org.omg.CORBA.Any;
/*  5:   */ import org.omg.CORBA.BAD_OPERATION;
/*  6:   */ import org.omg.CORBA.ORB;
/*  7:   */ import org.omg.CORBA.StructMember;
/*  8:   */ import org.omg.CORBA.TCKind;
/*  9:   */ import org.omg.CORBA.TypeCode;
/* 10:   */ import org.omg.CORBA.portable.InputStream;
/* 11:   */ import org.omg.CORBA.portable.OutputStream;
/* 12:   */ 
/* 13:   */ public abstract class TypeRecurrInstAggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypeRecurrInstAggregate clone(TypeRecurrInstAggregate paramTypeRecurrInstAggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypeRecurrInstAggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypeRecurrInstAggregate localTypeRecurrInstAggregate = new TypeRecurrInstAggregate();
/* 23:21 */     localTypeRecurrInstAggregate.NInsts = paramTypeRecurrInstAggregate.NInsts;
/* 24:22 */     localTypeRecurrInstAggregate.NInstsExists = paramTypeRecurrInstAggregate.NInstsExists;
/* 25:23 */     localTypeRecurrInstAggregate.Freq = ((EnumFreqEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeRecurrInstAggregate.Freq)));
/* 26:24 */     return localTypeRecurrInstAggregate;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeRecurrInstAggregate read(InputStream paramInputStream)
/* 30:   */   {
/* 31:30 */     TypeRecurrInstAggregate localTypeRecurrInstAggregate = new TypeRecurrInstAggregate();
/* 32:31 */     localTypeRecurrInstAggregate.NInsts = paramInputStream.read_long();
/* 33:32 */     localTypeRecurrInstAggregate.NInstsExists = paramInputStream.read_boolean();
/* 34:33 */     localTypeRecurrInstAggregate.Freq = EnumFreqEnumHelper.read(paramInputStream);
/* 35:34 */     return localTypeRecurrInstAggregate;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static void write(OutputStream paramOutputStream, TypeRecurrInstAggregate paramTypeRecurrInstAggregate)
/* 39:   */   {
/* 40:41 */     if (paramTypeRecurrInstAggregate == null) {
/* 41:43 */       paramTypeRecurrInstAggregate = new TypeRecurrInstAggregate();
/* 42:   */     }
/* 43:45 */     paramOutputStream.write_long(paramTypeRecurrInstAggregate.NInsts);
/* 44:46 */     paramOutputStream.write_boolean(paramTypeRecurrInstAggregate.NInstsExists);
/* 45:47 */     EnumFreqEnumHelper.write(paramOutputStream, paramTypeRecurrInstAggregate.Freq);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static String _idl()
/* 49:   */   {
/* 50:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeRecurrInstAggregate";
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static TypeCode type()
/* 54:   */   {
/* 55:59 */     if (_type == null)
/* 56:   */     {
/* 57:61 */       ORB localORB = ORB.init();
/* 58:62 */       StructMember[] arrayOfStructMember = 
/* 59:63 */         {
/* 60:64 */         new StructMember("NInsts", localORB.get_primitive_tc(TCKind.tk_long), null), 
/* 61:65 */         new StructMember("NInstsExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 62:66 */         new StructMember("Freq", EnumFreqEnumHelper.type(), null) };
/* 63:   */       
/* 64:68 */       _type = localORB.create_struct_tc(id(), "TypeRecurrInstAggregate", arrayOfStructMember);
/* 65:   */     }
/* 66:70 */     return _type;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public static void insert(Any paramAny, TypeRecurrInstAggregate paramTypeRecurrInstAggregate)
/* 70:   */   {
/* 71:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 72:78 */     write(localOutputStream, paramTypeRecurrInstAggregate);
/* 73:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static TypeRecurrInstAggregate extract(Any paramAny)
/* 77:   */   {
/* 78:85 */     if (!paramAny.type().equal(type())) {
/* 79:87 */       throw new BAD_OPERATION();
/* 80:   */     }
/* 81:89 */     return read(paramAny.create_input_stream());
/* 82:   */   }
/* 83:   */   
/* 84:   */   public static String id()
/* 85:   */   {
/* 86:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeRecurrInstAggregate:1.0";
/* 87:   */   }
/* 88:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregateHelper
 * JD-Core Version:    0.7.0.1
 */