package com.wildma.testmodule

import org.junit.Test
import java.io.File
import java.nio.file.Paths

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {


    /**
     * 从统计 1w+ 设备信息中计算最小宽度
     */
    @Test
    fun calculateSW() {
        val devicesFile = File(Paths.get("").toAbsolutePath().parent.toAbsolutePath().toString(), "devices_simple.csv")
        var index = 0

        val resultMap = HashMap<String, Int>()

        devicesFile.forEachLine { deviceInfo ->
            if (index == 0) {
                index++
                return@forEachLine
            }
            index++

            deviceInfo.split(",").let {
                val widthPixels = it[1].toInt()
                val heightPixels = it[0].toInt()
                val density = it[3].toFloat()

                if (widthPixels < heightPixels) {
                    val sw = swFormat(widthPixels / density)
                    resultMap[sw] = resultMap.getOrDefault(sw, 0) + 1
                }
            }
        }

        // 输出统计结果
        resultMap
            .toSortedMap { o1, o2 ->
                if (o1.toFloat() >= o2.toFloat()) 1 else -1
            }
            .forEach { (sw, count) ->
                println("$sw -> $count")
            }
    }

    private fun swFormat(sw: Float): String {
        // 360.0 -> 360
        // 454.73685 -> 454.7368
        return "%.5f"
            .format(sw)
            .let {
                val result = it.substring(0, it.length - 1).split(".")
                result[0] + ".${result[1]}".dropLastWhile { char ->
                    char == '.' || char == '0'
                }
            }
    }



    @Test
    fun generateMathDPString() {
        val file = File(Paths.get("").toAbsolutePath().parent.toAbsolutePath().toString(), "swlist")
        val dpStrBuilder = StringBuilder()
        file.forEachLine {
            if (dpStrBuilder.isNotEmpty()) {
                dpStrBuilder.append(",")
            }
            dpStrBuilder.append(it)
        }
        println(dpStrBuilder.toString())
    }
}