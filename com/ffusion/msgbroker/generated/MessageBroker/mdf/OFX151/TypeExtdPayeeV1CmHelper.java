/*  1:   */ package com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151;
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
/* 13:   */ public abstract class TypeExtdPayeeV1CmHelper
/* 14:   */ {
/* 15:   */   public static TypeCode _type;
/* 16:   */   
/* 17:   */   public static TypeExtdPayeeV1Cm clone(TypeExtdPayeeV1Cm paramTypeExtdPayeeV1Cm)
/* 18:   */   {
/* 19:16 */     if (paramTypeExtdPayeeV1Cm == null) {
/* 20:18 */       return null;
/* 21:   */     }
/* 22:20 */     TypeExtdPayeeV1Cm localTypeExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
/* 23:21 */     localTypeExtdPayeeV1Cm.PayeeID = paramTypeExtdPayeeV1Cm.PayeeID;
/* 24:22 */     localTypeExtdPayeeV1Cm.IDScope = ((EnumIDScopeEnum)ObjectVal.readObject(ObjectVal.writeObject(paramTypeExtdPayeeV1Cm.IDScope)));
/* 25:23 */     localTypeExtdPayeeV1Cm.Name = paramTypeExtdPayeeV1Cm.Name;
/* 26:24 */     return localTypeExtdPayeeV1Cm;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static TypeExtdPayeeV1Cm read(InputStream paramInputStream)
/* 30:   */   {
/* 31:30 */     TypeExtdPayeeV1Cm localTypeExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
/* 32:31 */     localTypeExtdPayeeV1Cm.PayeeID = paramInputStream.read_string();
/* 33:32 */     localTypeExtdPayeeV1Cm.IDScope = EnumIDScopeEnumHelper.read(paramInputStream);
/* 34:33 */     localTypeExtdPayeeV1Cm.Name = paramInputStream.read_string();
/* 35:34 */     return localTypeExtdPayeeV1Cm;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static void write(OutputStream paramOutputStream, TypeExtdPayeeV1Cm paramTypeExtdPayeeV1Cm)
/* 39:   */   {
/* 40:41 */     if (paramTypeExtdPayeeV1Cm == null) {
/* 41:43 */       paramTypeExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
/* 42:   */     }
/* 43:45 */     paramOutputStream.write_string(paramTypeExtdPayeeV1Cm.PayeeID);
/* 44:46 */     EnumIDScopeEnumHelper.write(paramOutputStream, paramTypeExtdPayeeV1Cm.IDScope);
/* 45:47 */     paramOutputStream.write_string(paramTypeExtdPayeeV1Cm.Name);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static String _idl()
/* 49:   */   {
/* 50:52 */     return "com::ffusion::msgbroker::generated::MessageBroker::mdf::OFX151::TypeExtdPayeeV1Cm";
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static TypeCode type()
/* 54:   */   {
/* 55:59 */     if (_type == null)
/* 56:   */     {
/* 57:61 */       ORB localORB = ORB.init();
/* 58:62 */       StructMember[] arrayOfStructMember = 
/* 59:63 */         {
/* 60:64 */         new StructMember("PayeeID", localORB.get_primitive_tc(TCKind.tk_string), null), 
/* 61:65 */         new StructMember("IDScope", EnumIDScopeEnumHelper.type(), null), 
/* 62:66 */         new StructMember("Name", localORB.get_primitive_tc(TCKind.tk_string), null) };
/* 63:   */       
/* 64:68 */       _type = localORB.create_struct_tc(id(), "TypeExtdPayeeV1Cm", arrayOfStructMember);
/* 65:   */     }
/* 66:70 */     return _type;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public static void insert(Any paramAny, TypeExtdPayeeV1Cm paramTypeExtdPayeeV1Cm)
/* 70:   */   {
/* 71:77 */     OutputStream localOutputStream = paramAny.create_output_stream();
/* 72:78 */     write(localOutputStream, paramTypeExtdPayeeV1Cm);
/* 73:79 */     paramAny.read_value(localOutputStream.create_input_stream(), type());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public static TypeExtdPayeeV1Cm extract(Any paramAny)
/* 77:   */   {
/* 78:85 */     if (!paramAny.type().equal(type())) {
/* 79:87 */       throw new BAD_OPERATION();
/* 80:   */     }
/* 81:89 */     return read(paramAny.create_input_stream());
/* 82:   */   }
/* 83:   */   
/* 84:   */   public static String id()
/* 85:   */   {
/* 86:94 */     return "IDL:com/ffusion/msgbroker/generated/MessageBroker/mdf/OFX151/TypeExtdPayeeV1Cm:1.0";
/* 87:   */   }
/* 88:   */ }


/* Location:           D:\drops\jd\jars\OFX151BPWServices.jar
 * Qualified Name:     com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1CmHelper
 * JD-Core Version:    0.7.0.1
 */