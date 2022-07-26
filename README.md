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

This definition generates the following code:

```
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.NullPointerException
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.emptySet
import kotlin.text.buildString

public class MyDataClassJsonAdapter(
  moshi: Moshi
) : JsonAdapter<MyDataClass>() {
  private val options: JsonReader.Options = JsonReader.Options.of("value")

  //Compilation error here
  private val nullableEitherAdapter: JsonAdapter<Either?> = moshi.adapter(Either::class.java,
      emptySet(), "value")

  public override fun toString(): String = buildString(33) {
      append("GeneratedJsonAdapter(").append("MyDataClass").append(')') }

  public override fun fromJson(reader: JsonReader): MyDataClass {
    //Compilation error here
    var value__: Either? = null
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> value__ = nullableEitherAdapter.fromJson(reader)
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    return MyDataClass(
        `value` = value__
    )
  }

  public override fun toJson(writer: JsonWriter, value_: MyDataClass?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("value")
    nullableEitherAdapter.toJson(writer, value_.`value`)
    writer.endObject()
  }
}
```
