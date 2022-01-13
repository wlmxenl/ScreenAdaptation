package com.wildma.testmodule

import org.junit.Test
import java.io.File
import java.math.RoundingMode
import java.nio.file.Paths
import java.text.DecimalFormat

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

        // 截断保留 4 位小数
        val df = DecimalFormat("#.####").apply{
            roundingMode = RoundingMode.FLOOR
        }

        devicesFile.forEachLine { deviceInfo ->
            if (index == 0) {
                index++
                return@forEachLine
            }
            deviceInfo.split(",").let {
                val widthPixels = it[1].toInt()
                val heightPixels = it[0].toInt()
                val density = it[2].toFloat()
                // println("w = $widthPixels, h = $heightPixels, density = $density")
                if (widthPixels < heightPixels) {
                    val swDp = widthPixels / density
//                    if (swDp == 800f) {
//                        println(index)
//                    }
                    val swDpFormatStr = df.format(swDp)
                    resultMap[swDpFormatStr] = resultMap.getOrDefault(swDpFormatStr, 0) + 1
                }
            }
            index++
        }

        val ignoredSWdp = arrayOf(
            "343.5387", "345.6", "349.0908", "350.5071", "352.3595", "376.4705", "379.5671", "380.3076", "391.8367", "392", "393.174",
            "423.5293", "432.9411", "450.7041", "451.7647", "481.203",
            "600.9389")

        // 过滤设备总类别数大于9的
        val filterMap = resultMap
                .filter { it.value > 9 && !ignoredSWdp.contains(it.key)}
                .toSortedMap()
        println("filterMap.size = ${filterMap.size}")

        val matchDp = StringBuilder()

        filterMap.forEach {
            println("${it.key} -> ${it.value}")
            if (matchDp.isNotEmpty()) {
                matchDp.append(",")
            }
            matchDp.append(it.key)
        }
        println("match_dp=240,270,306,${matchDp}")
    }
}