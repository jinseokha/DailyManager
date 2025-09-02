package com.devseok.dailymanager.custom.picker

/**
 * @author Ha Jin Seok
 * @created 2025-09-02
 * @desc
 */
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.unit.IntSize

internal fun Canvas.drawImage(image: ImageBitmap, topLeft: Offset = Offset.Zero) {
    drawImage(image, topLeft, emptyPaint)
}

internal fun Canvas.drawImageCenterAt(image: ImageBitmap, center: Offset = Offset.Zero) {
    drawImage(image, center - image.size.center)
}

internal fun Canvas.drawRect(size: IntSize, paint: Paint) {
    drawRect(0f, 0f, size.width.toFloat(), size.height.toFloat(), paint)
}

internal fun Canvas.drawRoundRect(size: IntSize, radius: Float, paint: Paint) {
    drawRoundRect(0f, 0f, size.width.toFloat(), size.height.toFloat(), radius, radius, paint)
}

internal val emptyPaint = Paint()
