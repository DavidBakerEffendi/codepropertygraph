package io.shiftleft.codepropertygraph.schema

import overflowdb.schema._
import overflowdb.storage.ValueTypes

object TagsAndLocation extends SchemaBase {

  override def index: Int = 7

  override def description: String =
    """
      |
      |""".stripMargin

  def apply(builder: SchemaBuilder,
            base: Base.Schema,
            typeDeclSchema: TypeDecl.Schema,
            methodSchema: Method.Schema,
            ast: Ast.Schema,
            fs: FileSystem.Schema) =
    new Schema(builder, base, typeDeclSchema, methodSchema, ast, fs)

  class Schema(builder: SchemaBuilder,
               base: Base.Schema,
               typeDeclSchema: TypeDecl.Schema,
               methodSchema: Method.Schema,
               ast: Ast.Schema,
               fs: FileSystem.Schema) {
    import base._
    import typeDeclSchema._
    import methodSchema._
    import ast._
    import fs._
    implicit private val schemaInfo = SchemaInfo.forClass(getClass)

// node properties
    val symbol = builder
      .addProperty(
        name = "SYMBOL",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(100)

    val methodShortName = builder
      .addProperty(
        name = "METHOD_SHORT_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(102)

    val packageName = builder
      .addProperty(
        name = "PACKAGE_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(103)

    val className = builder
      .addProperty(
        name = "CLASS_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(104)

    val classShortName = builder
      .addProperty(
        name = "CLASS_SHORT_NAME",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(132)

    val nodeLabel = builder
      .addProperty(
        name = "NODE_LABEL",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(105)

    val sourceType = builder
      .addProperty(
        name = "SOURCE_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(115)

    val sinkType = builder
      .addProperty(
        name = "SINK_TYPE",
        valueType = ValueTypes.STRING,
        cardinality = Cardinality.One,
        comment = ""
      )
      .protoId(116)

    val taggedBy = builder
      .addEdgeType(
        name = "TAGGED_BY",
        comment = "Edges from nodes to tags"
      )
      .protoId(11)

    // node tpes

    val tag: NodeType = builder
      .addNodeType(
        name = "TAG",
        comment = "A string tag"
      )
      .protoId(24)
      .addProperties(name, value)

    val location: NodeType = builder
      .addNodeType(
        name = "LOCATION",
        comment = ""
      )
      .protoId(25)
      .addProperties(symbol,
                     methodFullName,
                     methodShortName,
                     packageName,
                     lineNumber,
                     className,
                     classShortName,
                     nodeLabel,
                     filename)

    val tagNodePair: NodeType = builder
      .addNodeType(
        name = "TAG_NODE_PAIR",
        comment = ""
      )
      .protoId(208)
      .addProperties()

    val source: NodeType = builder
      .addNodeType(
        name = "SOURCE",
        comment = ""
      )
      .protoId(202)
      .addProperties(sourceType)

    val sink: NodeType = builder
      .addNodeType(
        name = "SINK",
        comment = ""
      )
      .protoId(203)
      .addProperties(sinkType)

// node relations
    location
      .addContainedNode(builder.anyNode, "node", Cardinality.ZeroOrOne)

    tagNodePair
      .addContainedNode(tag, "tag", Cardinality.One)
      .addContainedNode(builder.anyNode, "node", Cardinality.One)

    source
      .addContainedNode(trackingPoint, "node", Cardinality.One)
      .addContainedNode(method, "method", Cardinality.One)
      .addContainedNode(tag, "methodTags", Cardinality.List)
      .addContainedNode(method, "callingMethod", Cardinality.ZeroOrOne)
      .addContainedNode(callNode, "callsite", Cardinality.ZeroOrOne)
      .addContainedNode(tag, "tags", Cardinality.List)
      .addContainedNode(tpe, "nodeType", Cardinality.One)

    sink
      .addContainedNode(trackingPoint, "node", Cardinality.One)
      .addContainedNode(tpe, "nodeType", Cardinality.One)
      .addContainedNode(method, "method", Cardinality.One)
      .addContainedNode(tag, "methodTags", Cardinality.List)
      .addContainedNode(method, "callingMethod", Cardinality.ZeroOrOne)
      .addContainedNode(callNode, "callsite", Cardinality.ZeroOrOne)
      .addContainedNode(methodParameterIn, "parameterIn", Cardinality.ZeroOrOne)
      .addContainedNode(tag, "parameterInTags", Cardinality.List)

    method
      .addOutEdge(edge = taggedBy, inNode = tag)

    methodReturn
      .addOutEdge(edge = taggedBy, inNode = tag)

    literal
      .addOutEdge(edge = taggedBy, inNode = tag)

    local
      .addOutEdge(edge = taggedBy, inNode = tag)

    member
      .addOutEdge(edge = taggedBy, inNode = tag)

    callNode
      .addOutEdge(edge = taggedBy, inNode = tag)

    identifier
      .addOutEdge(edge = taggedBy, inNode = tag)

    fieldIdentifier
      .addOutEdge(edge = taggedBy, inNode = tag)

    methodParameterIn
      .addOutEdge(edge = taggedBy, inNode = tag)

    ret
      .addOutEdge(edge = taggedBy, inNode = tag)

    block
      .addOutEdge(edge = taggedBy, inNode = tag)

    unknown
      .addOutEdge(edge = taggedBy, inNode = tag)

    controlStructure
      .addOutEdge(edge = taggedBy, inNode = tag)

    methodRef
      .addOutEdge(edge = taggedBy, inNode = tag)

    typeRef
      .addOutEdge(edge = taggedBy, inNode = tag)

    jumpTarget
      .addOutEdge(edge = taggedBy, inNode = tag)

    file
      .addOutEdge(edge = taggedBy, inNode = tag)

    methodParameterOut
      .addOutEdge(edge = taggedBy, inNode = tag)

// constants

  }

}
