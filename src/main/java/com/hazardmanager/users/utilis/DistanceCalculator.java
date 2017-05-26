package com.hazardmanager.users.utilis;

import java.lang.*;
import java.util.Objects;

public class DistanceCalculator
{
    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double distance = Math.sin(degreesToRadians(lat1)) * Math.sin(degreesToRadians(lat2)) + Math.cos(degreesToRadians(lat1)) * Math.cos(degreesToRadians(lat2)) * Math.cos(degreesToRadians(theta));
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

    private static double degreesToRadians(double degrees) {
        return (degrees * Math.PI / 180.0);
    }

    private static double radiansToDegrees(double radians) {
        return (radians * 180 / Math.PI);
    }
}