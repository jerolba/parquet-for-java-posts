// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: organizations.proto

package com.jerolba.xbuffers.protocol;

public final class OrganizationsCollection {
  private OrganizationsCollection() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_jerolba_xbuffers_protocol_Organization_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_jerolba_xbuffers_protocol_Organization_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_jerolba_xbuffers_protocol_Organization_Attribute_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_jerolba_xbuffers_protocol_Organization_Attribute_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_jerolba_xbuffers_protocol_Organizations_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_jerolba_xbuffers_protocol_Organizations_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023organizations.proto\022\035com.jerolba.xbuff" +
      "ers.protocol\"\357\002\n\014Organization\022\014\n\004name\030\001 " +
      "\001(\t\022\020\n\010category\030\002 \001(\t\022J\n\004type\030\003 \001(\0162<.co" +
      "m.jerolba.xbuffers.protocol.Organization" +
      ".OrganizationType\022\017\n\007country\030\004 \001(\t\022I\n\nat" +
      "tributes\030\005 \003(\01325.com.jerolba.xbuffers.pr" +
      "otocol.Organization.Attribute\032h\n\tAttribu" +
      "te\022\n\n\002id\030\001 \001(\t\022\020\n\010quantity\030\002 \001(\005\022\016\n\006amou" +
      "nt\030\003 \001(\005\022\014\n\004size\030\004 \001(\005\022\017\n\007percent\030\005 \001(\001\022" +
      "\016\n\006active\030\006 \001(\010\"-\n\020OrganizationType\022\007\n\003F",
      "OO\020\000\022\007\n\003BAR\020\001\022\007\n\003BAZ\020\002\"S\n\rOrganizations\022" +
      "B\n\rorganizations\030\001 \003(\0132+.com.jerolba.xbu" +
      "ffers.protocol.OrganizationB:\n\035com.jerol" +
      "ba.xbuffers.protocolB\027OrganizationsColle" +
      "ctionP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_jerolba_xbuffers_protocol_Organization_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_jerolba_xbuffers_protocol_Organization_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_jerolba_xbuffers_protocol_Organization_descriptor,
        new java.lang.String[] { "Name", "Category", "Type", "Country", "Attributes", });
    internal_static_com_jerolba_xbuffers_protocol_Organization_Attribute_descriptor =
      internal_static_com_jerolba_xbuffers_protocol_Organization_descriptor.getNestedTypes().get(0);
    internal_static_com_jerolba_xbuffers_protocol_Organization_Attribute_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_jerolba_xbuffers_protocol_Organization_Attribute_descriptor,
        new java.lang.String[] { "Id", "Quantity", "Amount", "Size", "Percent", "Active", });
    internal_static_com_jerolba_xbuffers_protocol_Organizations_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_jerolba_xbuffers_protocol_Organizations_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_jerolba_xbuffers_protocol_Organizations_descriptor,
        new java.lang.String[] { "Organizations", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
