package com.hazardmanager.users.utilis;

import java.lang.*;
import java.util.Objects;

public class DistanceCalculator
{
    public final static int EARTH_RADIUS = 6371; //Earth's radius in kilometers.

    public static double distance(double latitude1, double longitude1, double latitude2, double longitude2, String unit) {
        double theta = longitude1 - longitude2;
        double distance = Math.sin(degreesToRadians(latitude1)) * Math.sin(degreesToRadians(latitude2)) + Math.cos(degreesToRadians(latitude1)) * Math.cos(degreesToRadians(latitude2)) * Math.cos(degreesToRadians(theta));
        distance = Math.acos(distance);
        distance = radiansToDegrees(distance);
        distance = distance * 60 * 1.1515;
        if (Objects.equals(unit, "K")) {
            distance = distance * 1.609344;
        } else if (Objects.equals(unit, "N")) {
            distance = distance * 0.8684;
        }
        return (distance);
    }

    /**
     * Implementation using the Haversine formula
     */

    public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2){
        double latitudeInRadians1 = degreesToRadians(latitude1);
        double latitudeInRadians2 = degreesToRadians(latitude2);
        double deltaLatitudeInRadians = degreesToRadians(latitude2-latitude1);
        double deltaLongitudeInRadians = degreesToRadians(longitude2-longitude1);

        double a = Math.pow(Math.sin(deltaLatitudeInRadians/2),2)+Math.cos(latitudeInRadians1)*Math.cos(latitudeInRadians2)*Math.pow(Math.sin(deltaLongitudeInRadians/2),2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        double distance = EARTH_RADIUS * c;

        return distance;

    }

    private static double degreesToRadians(double degrees) {
        return (degrees * Math.PI / 180.0);
    }

    private static double radiansToDegrees(double radians) {
        return (radians * 180 / Math.PI);
    }
}