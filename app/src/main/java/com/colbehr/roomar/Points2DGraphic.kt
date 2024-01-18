package com.colbehr.roomar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun Points2DGraphic(points: List<Offset>, isIconSize: Boolean) {
    if (points.isEmpty()) {
        return
    }
    val pointColor = MaterialTheme.colorScheme.onSurface
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(10)),
        onDraw = {
            /*         Scale the shape            */
            // Find the bounding box of the shape
            val minX = points.map { it.x }.minOrNull() ?: 0f
            val minY = points.map { it.y }.minOrNull() ?: 0f
            val maxX = points.map { it.x }.maxOrNull() ?: 0f
            val maxY = points.map { it.y }.maxOrNull() ?: 0f

            // Calculate the width and height of the bounding box
            val width = maxX - minX
            val height = maxY - minY

            // Calculate the scaling factor based on the dimensions of the canvas and the bounding box
            val scaleFactor =
                minOf(size.width / width, size.height / height) * (if (isIconSize) .6f else .7f)

            // Create a temporary set of points after scaling
            val scaledPoints = points.map { point ->
                Offset(
                    x = (point.x - minX) * scaleFactor,
                    y = (point.y - minY) * scaleFactor
                )
            }

            /*         Center the shape            */
            // Calculate the center of mass of the points
            val centerShapeX = (scaledPoints.map { it.x }.average()).toFloat()
            val centerShapeY = (scaledPoints.map { it.y }.average()).toFloat()

            // Calculate the offset from the center of the canvas to the center of the shapes
            val offsetX = (size.width / 2f) - centerShapeX
            val offsetY = (size.height / 2f) - centerShapeY

            var previousPoint: Offset? = null
            scaledPoints.forEach { point ->
                val scaledX = point.x
                val scaledY = point.y
                // Calculate the position relative to the center of the canvas
                val positionX = scaledX + offsetX
                val positionY = scaledY + offsetY

                drawCircle(
                    center = Offset(positionX, positionY),
                    radius = if (isIconSize) 8f else 24f,
                    color = pointColor
                )
                if (previousPoint != null) {
                    // Draw a line if there's a previous point
                    previousPoint?.let {
                        drawLine(
                            start = Offset(positionX, positionY),
                            end = previousPoint!!,
                            color = pointColor,
                            strokeWidth = if (isIconSize) 4f else 8f
                        )
                    }
                }
                // Save the point for the next iteration
                previousPoint = Offset(positionX, positionY)
            }
            // Draw the last line from end to start
            if (previousPoint != null) {
                previousPoint?.let {
                    drawLine(
                        start = Offset(scaledPoints[0].x + offsetX, scaledPoints[0].y + offsetY),
                        end = previousPoint!!,
                        color = pointColor,
                        strokeWidth = if (isIconSize) 4f else 8f
                    )
                }
            }
        }
    )

}

@Composable
@Preview(showBackground = true)
fun Points2dGraphicPreview() {
    val points = listOf(
        Offset(-100f, -150f),
        Offset(-300f, -250f),
        Offset(-400f, -100f),
        Offset(-200f, -50f)
    )
//    val points = List(7) {
//        Offset(Random.nextFloat(), Random.nextFloat())
//    }
    Points2DGraphic(points, true)
}