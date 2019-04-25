package io.shiftleft.queryprimitives.dsl

import scala.collection.GenTraversableOnce

class RealPipeOperations extends PipeOperations[RealPipe] {
  override def toRealPipe[ElemType](pipe: RealPipe[ElemType]): RealPipe[ElemType] = {
    pipe
  }

  override def map[SrcType, DstType](pipe: RealPipe[SrcType],
                                     function: SrcType => DstType): RealPipe[DstType] = {
    new RealPipe(pipe.impl.map(function))
  }

  override def flatMap[SrcType, DstType](pipe: RealPipe[SrcType],
                                         function: SrcType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    new RealPipe(pipe.impl.flatMap(function.apply))
  }

  override def flatMap[SrcType, DstType](pipe: RealPipe[SrcType],
                                         function: SrcType => RealPipe[DstType])
                                        (implicit unused: TypeErasureResolve1): RealPipe[DstType] = {
    val applyAndUnwrap = (sourceElement: SrcType) => function.apply(sourceElement).impl
    new RealPipe(pipe.impl.flatMap(applyAndUnwrap))
  }

  override def filter[ElemType](pipe: RealPipe[ElemType],
                                function: ElemType => Boolean): RealPipe[ElemType] = {
    new RealPipe(pipe.impl.filter(function))
  }

  override def head[ElemType](pipe: RealPipe[ElemType]): ElemType = {
    pipe.impl.head
  }

}
