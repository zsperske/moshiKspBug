package com.sperske.moshiKspBug

import com.squareup.moshi.JsonClass

sealed class Either<out A, out B> {
  data class Left<A>(val value: A) : Either<A, Nothing>()
  data class Right<B>(val value: B) : Either<Nothing, B>()
}

typealias StringOrInt = Either<String, Int>

@JsonClass(generateAdapter = true)
data class MyDataClass(val value: StringOrInt?)
