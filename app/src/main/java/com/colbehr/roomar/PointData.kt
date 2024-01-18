package com.colbehr.roomar

data class PointData(
    val name: String,
    val pointsString: String,
    val points: List<Point>,
)

data class Point(
    val x: Float,
    val y: Float
)

