import scala.quoted._

type Foo
type F[X]
def varargsFunc(funcs0: Foo*) = ???

inline def mcr1: F[String] = ${ mcr1Impl }
def mcr1Impl(using QuoteContext): Expr[F[String]] = '{???}

inline def mcr2: Unit = ${mcr2Impl}
def mcr2Impl(using ctx: QuoteContext): Expr[Unit] =
  val func: Expr[Seq[Foo] => Unit] =
    '{ (esx: Seq[Foo]) => varargsFunc(esx: _*) }
  val trees: Expr[Seq[Foo]] =
    '{Nil}
  Expr.betaReduce('{$func($trees)})