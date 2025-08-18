package kr.co.hs.kmp.data.sample

import kotlinx.serialization.json.JsonObject
import kr.co.hs.kmp.data.KotlinJson
import kr.co.hs.kmp.data.extension.StringExtension.toMD5
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeAppCommonTest {

    @Test
    fun example() {
        assertEquals(3, 1 + 2)

//        val kotlinJson = Kotlinjs
        val md5 = "안녕하십니까 여러부운!!!!".toMD5()
//        println(md5)
        assertEquals("f1429fdc48d7bddd00ef8e955ba96667", md5)


        val strJson = "{\n" +
                "  \"array\": [\n" +
                "    1,\n" +
                "    2,\n" +
                "    3\n" +
                "  ],\n" +
                "  \"boolean\": true,\n" +
                "  \"color\": \"gold\",\n" +
                "  \"null\": null,\n" +
                "  \"number\": 123,\n" +
                "  \"object\": {\n" +
                "    \"a\": \"b\",\n" +
                "    \"c\": \"d\"\n" +
                "  },\n" +
                "  \"string\": \"Hello World\"\n" +
                "}"

        val kotlinJson = KotlinJson()
        val json = kotlinJson.fromJson<JsonObject>(strJson)
        println(json)
    }
}