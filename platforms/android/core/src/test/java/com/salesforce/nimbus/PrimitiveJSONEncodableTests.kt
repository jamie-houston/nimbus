//
// Copyright (c) 2019, Salesforce.com, inc.
// All rights reserved.
// SPDX-License-Identifier: BSD-3-Clause
// For full license text, see the LICENSE file in the repo root or https://opensource.org/licenses/BSD-3-Clause
//

package com.salesforce.nimbus

import io.kotlintest.properties.Gen
import io.kotlintest.properties.forAll
import io.kotlintest.shouldThrow
import io.kotlintest.specs.AnnotationSpec
import io.kotlintest.specs.Test
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class PrimitiveJSONEncodableTests : AnnotationSpec() {

    @Test
    fun testListFromJSON_String() {
        forAll(Gen.string(), Gen.string(), Gen.string()) { value1, value2, value3 ->
            val json = """
            [
                ${JSONObject.quote(value1)},
                ${JSONObject.quote(value2)},
                ${JSONObject.quote(value3)}
            ]
            """.trimIndent()

            val array =
                listFromJSON<String>(json)
            value1 == array[0] &&
            value2 == array[1] &&
            value3 == array[2]
        }
    }

    @Test
    fun testListFromJSON_Int() {
        forAll(Gen.int(), Gen.int(), Gen.int()) { value1, value2, value3 ->
            val json = """
            [
                $value1,
                $value2,
                $value3
            ]
            """.trimIndent()

            val array =
                listFromJSON<Int>(json)
            value1 == array[0] &&
            value2 == array[1] &&
            value3 == array[2]
        }
    }

    @Test
    fun testListFromJSON_Any() {
        forAll(Gen.string(), Gen.string(), Gen.int()) { value1, value2, value3 ->
            val json = """
            [
                ${JSONObject.quote(value1)},
                ${JSONObject.quote(value2)},
                $value3
            ]
            """.trimIndent()

            val array =
                listFromJSON<Any>(json)
            value1 == array[0] as String &&
            value2 == array[1] as String &&
            value3 == array[2] as Int
        }
    }

    @Test
    fun testListFromJSON_String_throws() {
        forAll(Gen.string(), Gen.string(), Gen.int()) { value1, value2, value3 ->
            val json = """
            [
                ${JSONObject.quote(value1)},
                ${JSONObject.quote(value2)},
                $value3
            ]
            """.trimIndent()

            shouldThrow<JSONException> {
                listFromJSON<String>(json)
            }

            true
        }
    }

    @Test
    fun testMapFromJSON_String_String() {
        forAll(Gen.string(), Gen.string(), Gen.string()) { value1, value2, value3 ->

            val json = """
            {
                "key1": ${JSONObject.quote(value1)},
                "key2": ${JSONObject.quote(value2)},
                "key3": ${JSONObject.quote(value3)}
            }
            """.trimIndent()

            val map =
                mapFromJSON<String, String>(json)
            value1 == map["key1"] &&
            value2 == map["key2"] &&
            value3 == map["key3"]
        }
    }

    @Test
    fun testMapFromJSON_String_Int() {
        forAll(Gen.int(), Gen.int(), Gen.int()) { value1, value2, value3 ->

            val json = """
            {
                "key1": $value1,
                "key2": $value2,
                "key3": $value3
            }
            """.trimIndent()

            val map =
                mapFromJSON<String, Int>(json)
            value1 == map["key1"] as Int &&
            value2 == map["key2"] as Int &&
            value3 == map["key3"] as Int
        }
    }

    @Test
    fun testMapFromJSON_String_Any() {
        forAll(Gen.string(), Gen.string(), Gen.int()) { value1, value2, value3 ->

            val json = """
            {
                "key1": ${JSONObject.quote(value1)},
                "key2": ${JSONObject.quote(value2)},
                "key3": $value3
            }
            """.trimIndent()

            val map =
                mapFromJSON<String, Any>(json)
            value1 == map["key1"] as String &&
            value2 == map["key2"] as String &&
            value3 == map["key3"] as Int
        }
    }

    @Test
    fun testMapFromJSON_String_String_throws() {
        forAll(Gen.string(), Gen.string(), Gen.int()) { value1, value2, value3 ->

            val json = """
            {
                "key1": ${JSONObject.quote(value1)},
                "key2": ${JSONObject.quote(value2)},
                "key3": $value3
            }
            """.trimIndent()

            shouldThrow<JSONException> {
                mapFromJSON<String, String>(json)
            }

            true
        }
    }

    @Test
    fun testArrayFromJSON_String() {
        forAll(Gen.string(), Gen.string(), Gen.string()) { value1, value2, value3 ->
            val json = """
            [
                ${JSONObject.quote(value1)},
                ${JSONObject.quote(value2)},
                ${JSONObject.quote(value3)}
            ]
            """.trimIndent()

            val array =
                arrayFromJSON<String>(json)
            value1 == array[0] &&
                value2 == array[1] &&
                value3 == array[2]
        }
    }

    @Test
    fun testArrayFromJSON_Int() {
        forAll(Gen.int(), Gen.int(), Gen.int()) { value1, value2, value3 ->
            val json = """
            [
                $value1,
                $value2,
                $value3
            ]
            """.trimIndent()

            val array =
                arrayFromJSON<Int>(json)
            value1 == array[0] &&
                value2 == array[1] &&
                value3 == array[2]
        }
    }

    @Test
    fun testArrayFromJSON_Any() {
        forAll(Gen.string(), Gen.string(), Gen.int()) { value1, value2, value3 ->
            val json = """
            [
                ${JSONObject.quote(value1)},
                ${JSONObject.quote(value2)},
                $value3
            ]
            """.trimIndent()

            val array =
                arrayFromJSON<Any>(json)
            value1 == array[0] as String &&
                value2 == array[1] as String &&
                value3 == array[2] as Int
        }
    }

    @Test
    fun testArrayFromJSON_String_throws() {
        forAll(Gen.string(), Gen.string(), Gen.int()) { value1, value2, value3 ->
            val json = """
            [
                ${JSONObject.quote(value1)},
                ${JSONObject.quote(value2)},
                $value3
            ]
            """.trimIndent()

            shouldThrow<JSONException> {
                arrayFromJSON<String>(json)
            }

            true
        }
    }

    @Test
    fun testIntListToJsonEncodable() {
        forAll(Gen.list(Gen.int())) { a ->
            val jsonString = a.toJSONEncodable().encode()
            val jsonArray = JSONArray(jsonString)
            a.indices.all { i -> a[i] == jsonArray[i] }
        }
    }

    @Test
    fun testJSONEncodableListToJSONEncodable() {
        forAll(Gen.list(Gen.string())) { a ->
            val jsonString = a.map { it.toJSONEncodable() }.toJSONEncodable().encode()
            val jsonArray = JSONArray(jsonString)
            a.indices.all { i -> a[i] == JSONObject(jsonArray[i] as String).get("") }
        }
    }

    @Test
    fun testStringStringMapToJSONEncodable() {
        forAll(Gen.map(Gen.string(), Gen.string())) { a ->
            val jsonString = a.toJSONEncodable().encode()
            val jsonObject = JSONObject(jsonString)
            a.entries.all { (key, value) -> value == jsonObject[key] }
        }
    }

    @Test
    fun testStringJSONEncodableMapToJSONEncodable() {
        forAll(Gen.map(Gen.string(), Gen.string())) { a ->
            val jsonString = a.mapValues { it.value.toJSONEncodable() }.toJSONEncodable().encode()
            val jsonObject = JSONObject(jsonString)
            a.entries.all { (key, value) -> value == JSONObject(jsonObject[key] as String).get("") }
        }
    }

    @Test
    fun testIntArrayToJSONEncodable() {
        forAll(Gen.list(Gen.int())) { a ->
            val jsonString = a.toTypedArray().toJSONEncodable().encode()
            val jsonArray = JSONArray(jsonString)
            a.indices.all { i -> a[i] == jsonArray[i] }
        }
    }

    @Test
    fun testJSONEncodableArrayToJSONEncodable() {
        forAll(Gen.list(Gen.string())) { a ->
            val jsonString = a.map { it.toJSONEncodable() }.toTypedArray().toJSONEncodable().encode()
            val jsonArray = JSONArray(jsonString)
            a.indices.all { i -> a[i] == JSONObject(jsonArray[i] as String).get("") }
        }
    }

    @Test
    fun testDoubleToJSON() {
        forAll(Gen.double()) { a ->
            // Comparing NaN requires a different way
            // https://stackoverflow.com/questions/37884133/comparing-nan-in-kotlin
            if (a == Double.POSITIVE_INFINITY || a == Double.NEGATIVE_INFINITY || a.equals(Double.NaN as Number)) {
                var sameExceptionMessage = false
                try {
                    a.toJSONEncodable()
                } catch (e: Exception) {
                    sameExceptionMessage = e.message.equals("Double value should be finite.")
                }
                sameExceptionMessage
            } else {
                val jsonString = a.toJSONEncodable().encode()
                val jsonObject = JSONObject(jsonString)
                val value = jsonObject.get("")
                if (a == value) {
                    true
                } else {
                    // If the generated value that is, for example, like 1.0, the fractional values
                    // are dropped by JSON serializer and returns a whole number.  So the check here seeks
                    // if the whole number comparison, assuming that fractional part was 0, would be equal.
                    val convertedToInt = a.toInt()
                    convertedToInt == value ?: false
                }
            }
        }
    }

    @Test
    fun testIntToJSON() {
        forAll(Gen.int()) { a ->
            val jsonString = a.toJSONEncodable().encode()
            val jsonObject = JSONObject(jsonString)
            val value = jsonObject.get("")
            a == value
        }
    }

    @Test
    fun testBooleanToJSON() {
        forAll(Gen.bool()) { a ->
            val jsonString = a.toJSONEncodable().encode()
            val jsonObject = JSONObject(jsonString)
            val value = jsonObject.get("")
            a == value
        }
    }

    @Test
    fun testLongToJSON() {
        forAll(Gen.long()) { a ->
            val jsonString = a.toJSONEncodable().encode()
            val jsonObject = JSONObject(jsonString)
            val value = jsonObject.get("")
            a == value
        }
    }

    @Test
    fun testStringToJSON() {
        forAll(Gen.string()) { a ->
            val jsonString = a.toJSONEncodable().encode()
            val jsonObject = JSONObject(jsonString)
            val value = jsonObject.get("")
            a == value
        }
    }
}
