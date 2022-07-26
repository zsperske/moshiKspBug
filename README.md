# moshiKspBug

Reproduction project for Moshi KSP bug.

Run the project and you'll see the following error.

`e: /MoshiKspBug/app/build/generated/ksp/debug/kotlin/com/sperske/moshiKspBug/MyDataClassJsonAdapter.kt: (24, 50): 2 type arguments expected for class Either<out A, out B>`

Code in question:

```
sealed class Either<out A, out B> {
  data class Left<A>(val value: A) : Either<A, Nothing>()
  data class Right<B>(val value: B) : Either<Nothing, B>()
}

typealias StringOrInt = Either<String, Int>

@JsonClass(generateAdapter = true)
data class MyDataClass(val value: StringOrInt?)
```
