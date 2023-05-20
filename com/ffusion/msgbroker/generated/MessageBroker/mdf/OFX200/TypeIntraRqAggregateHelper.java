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
/* 13:   */ public abstract class TypeIntraRqAggregateHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypeIntraRqAggregate clone(TypeIntraRqAggregate paramTypeIntraRqAggregate)
/* 18:   */   {
/* 19:16 */     if (paramTypeIntraRqAggregate == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypeIntraRqAggregate localTypeIntraRqAggregate = new TypeIntraRqAggregate();
/* 23:21 */     localTypeIntraRqAggregate.CurDef = ((EnumCurrencyEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeIntraRqAggregate.CurDef)));
/* 24:22 */     localTypeIntraRqAggregate.CurDefExists = paramTypeIntraRqAggregate.CurDefExists;
/* 25:23 */     localTypeIntraRqAggregate.XferInfo = TypeXferInfoAggregateHelper.clone(paramTypeIntraRqAggregate.XferInfo);
/* 26:24 */     return localTypeIntraRqAggregate;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeIntraRqAggregate read(InputStream paramInputStream)
/* 30:   */   {
/* 31:30 */     TypeIntraRqAggregate localTypeIntraRqAggregate = new TypeIntraRqAggregate();
/* 32:31 */     localTypeIntraRqAggregate.CurDef = EnumCurrencyEnumHelper.read(paramInputStream);
/* 33:32 */     localTypeIntraRqAggregate.CurDefExists = paramInputStream.read_boolean();
/* 34:33 */     localTypeIntraRqAggregate.XferInfo = TypeXferInfoAggregateHelper.read(paramInputStream);
/* 35:34 */     return localTypeIntraRqAggregate;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static void write(OutputStream paramOutputStream, TypeIntraRqAggregate paramTypeIntraRqAggregate)
/* 39:   */   {
/* 40:41 */     if (paramTypeIntraRqAggregate == null) {
/* 41:43 */       paramTypeIntraRqAggregate = new TypeIntraRqAggregate();
/* 42:   */     }
/* 43:45 */     EnumCurrencyEnumHelper.write(paramOutputStream, paramTypeIntraRqAggregate.CurDef);
/* 44:46 */     paramOutputStream.write_boolean(paramTypeIntraRqAggregate.CurDefExists);
/* 45:47 */     TypeXferInfoAggregateHelper.write(paramOutputStream, paramTypeIntraRqAggregate.XferInfo);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static String _idl()
/* 49:   */   {
/* 50:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX200::TypeIntraRqAggregate";
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static TypeCode type()
/* 54:   */   {
/* 55:59 */     if (_type == null)
/* 56:   */     {
/* 57:61 */       ORB localORB = ORB.init();
/* 58:62 */       StructMember[] arrayOfStructMember = 
/* 59:63 */         {
/* 60:64 */         new StructMember("CurDef", EnumCurrencyEnumHelper.type(), null), 
/* 61:65 */         new StructMember("CurDefExists", localORB.get_primitive_tc(TCKind.tk_boolean), null), 
/* 62:66 */         new StructMember("XferInfo", TypeXferInfoAggregateHelper.type(), null) };
/* 63:   */       
/* 64:68 */       _type = localORB.create_struct_tc(id(), "TypeIntraRqAggregate", arrayOfStructMember);
/* 65:   */     }
/* 66:70 */     return _type;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public static void insert(Any paramAny, TypeIntraRqAggregate paramTypeIntraRqAggregate)
/* 70:   */   {
/* 71:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 72:78 */     write(localOutputStream, paramTypeIntraRqAggregate);
/* 73:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static TypeIntraRqAggregate extract(Any paramAny)
/* 77:   */   {
/* 78:85 */     if (!paramAny.type().equal(type())) {
/* 79:87 */       throw new BAD_OPERATION();
/* 80:   */     }
/* 81:89 */     return read(paramAny.create_input_stream());
/* 82:   */   }
/* 83:   */   
/* 84:   */   public static String id()
/* 85:   */   {
/* 86:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX200/TypeIntraRqAggregate:1.0";
/* 87:   */   }
/* 88:   */ }


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRqAggregateHelper
 * JD-Core Version:    0.7.0.1
 */