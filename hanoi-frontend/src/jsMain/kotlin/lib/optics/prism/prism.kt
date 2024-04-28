package lib.optics.prism

import lib.maths.Maths
import lib.maths.o

@Maths
sealed class Either<out L, out R> {
    data class Left<L>(val value: L) : Either<L, Nothing>()
    data class Right<R>(val value: R) : Either<Nothing, R>()

    @Maths
    infix fun <K> mapLeft(f: (L) -> K): Either<K, R> = when (this) {
        is Right -> this
        is Left -> Left(f(value))
    }

    @Maths
    infix fun <S> mapRight(f: (R) -> S): Either<L, S> = when (this) {
        is Left -> this
        is Right -> Right(f(value))
    }

    @Maths
    fun switch(): Either<R, L> = when (this) {
        is Left -> Right(value)
        is Right -> Left(value)
    }
}

@Maths
fun <L, R> Either<L, Either<L, R>>.multLeft(): Either<L, R> = when (this) {
    is Either.Left -> this
    is Either.Right -> value
}

@Maths
fun <L, R> Either<Either<L, R>, R>.multRight(): Either<L, R> = when (this) {
    is Either.Left -> value
    is Either.Right -> this
}

@Maths
fun <K, L, R> liftLeft(f: (K) -> L): (Either<K, R>) -> Either<L, R> = { e -> e.mapLeft(f) }

@Maths
fun <K, L, R> liftRight(f: (K) -> L): (Either<R, K>) -> Either<R, L> = { e -> e.mapRight(f) }

@Maths
fun <L, R> multRight(): (Either<Either<L, R>, R>) -> Either<L, R> = {
    e ->
    e.multRight()
}

@Maths
fun <L, R> multLeft(): (Either<L, Either<L, R>>) -> Either<L, R> = {
    e ->
    e.multLeft()
}

@Maths
fun <L, R> switch(): (Either<L, R>) -> Either<R, L> = {
    e ->
    e.switch()
}

@Maths
operator fun <L, K, R, S> ((L) -> R).plus(other: (K) -> S): (Either<L, K>) -> Either<R, S> = {
    e ->
    e.mapLeft(this).mapRight(other)
}

data class Prism<S, T, A, B>(
    val match: (S) -> Either<A, T>,
    val build: (B) -> T
)

operator fun <R, S, T, A, B, C> Prism<S, T, A, B>.times(other: Prism<R, B, S, C>): Prism<R, T, A, C> = Prism(
    multRight<A, T>() o liftRight(build) o liftLeft(match) o other.match,
    build o other.build
)
