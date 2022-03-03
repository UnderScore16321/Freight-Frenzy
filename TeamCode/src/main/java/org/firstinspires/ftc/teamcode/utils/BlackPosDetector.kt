package org.firstinspires.ftc.teamcode.utils

import android.graphics.Bitmap
import android.graphics.Color
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.utils.PosDetector.ImRegion

class BlackPosDetector(val telemetry: Telemetry?, val searchRegions: List<ImRegion>) {
    companion object {
        private val BLACK_THRESHOLD = 0.1
        private val BLACK_PERCENT_THRESHOLD = 0.8
    }

    fun regionOfImage(image: Bitmap): Int {
        val blackness = searchRegions.map { blackPercentInRegion(image, it) }
        val max = blackness.maxOf { it }
        return when {
            max < BLACK_PERCENT_THRESHOLD -> -1
            else -> blackness.indexOf(max)
        }
    }

    private fun blackPercentInRegion(image: Bitmap, region: PosDetector.ImRegion): Double {
        val xStart = (image.width * region.x1).toInt()
        val xEnd = (image.width * region.x2).toInt()
        val yStart = (image.height * region.y1).toInt()
        val yEnd = (image.height * region.y2).toInt()

        var count = 0
        var blackPixels = 0

        for (i in xStart until xEnd) {
            for (j in yStart until yEnd) {
                count++
                val pixel = image.getPixel(i, j)
                val hsl =
                    PosDetector.rgbToHSL(Color.red(pixel), Color.green(pixel), Color.blue(pixel))
                val l = hsl[2]
                if (l < BLACK_THRESHOLD) blackPixels++
            }
        }

        return blackPixels.toDouble() / count.toDouble()
    }
}