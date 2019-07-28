package com.hlandim.customersmeeting.util

class Util {

    companion object {
        private const val EARTH_RADIUS = 6371
        fun distanceInKm(startLati: Double, startLong: Double, endLati: Double, endLong: Double): Double {

            val diffLati = Math.toRadians(endLati - startLati)
            val diffLong = Math.toRadians(endLong - startLong)

            val radiusStartLati = Math.toRadians(startLati)
            val radiusEndLati = Math.toRadians(endLati)

            // A and C are the 'sides' from the spherical triangle.
            val a = Math.pow(Math.sin(diffLati / 2), 2.0) + Math.pow(
                Math.sin(diffLong / 2),
                2.0
            ) * Math.cos(radiusStartLati) * Math.cos(radiusEndLati)
            val c = 2 * Math.asin(Math.sqrt(a))

            return EARTH_RADIUS * c
        }
    }


}