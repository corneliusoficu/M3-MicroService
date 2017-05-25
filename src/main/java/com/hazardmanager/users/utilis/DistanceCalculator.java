package com.hazardmanager.users.utilis;

/**
 * Created by Andrei on 5/14/2017.
 */
public class DistanceCalculator {
    /**
     * <p>This routine calculates the distance between two points (given the
     * latitude/longitude of those points). It is being used to calculate
     * the distance between two locations.</p>
     *
     * <p>Definitions: South latitudes are negative, east longitudes are positive</p>
     *
     * <p>Passed to function:
     * <ul>
     * 		<li>lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)</li>
     * 		<li>lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)</li>
     * 		<li>unit = the unit you desire for results
     * 			<ul>
     * 				<li>where: 'M' is meters</li>
     * 				<li>'K' is kilometers (default) </li>
     * 				<li>'N' is nautical miles</li>
     * 			</ul>
     * 		</li>
     * </ul>
     * Worldwide cities and other features databases with latitude longitude
     * are available at http://www.geodatasource.com</p>
     *
     * <p>For enquiries, please contact sales@geodatasource.com</p>
     * <p>Official Web site: http://www.geodatasource.com</p>
     * <p>GeoDataSource.com (C) All Rights Reserved 2013</p>
     *
     *
     * @param lat1 - latitude point 1
     * @param lon1 - longitude point 1
     * @param lat2 - latitude point 2
     * @param lon2 - longitude point 2
     * @return the distance between the two points
     */
    public static final double distance(double lat1, double lon1, double lat2, double lon2)
    {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.34 ;

        return (dist);
    }

    /**
     * <p>This function converts decimal degrees to radians.</p>
     *
     * @param deg - the decimal to convert to radians
     * @return the decimal converted to radians
     */
    private static final double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }

    /**
     * <p>This function converts radians to decimal degrees.</p>
     *
     * @param rad - the radian to convert
     * @return the radian converted to decimal degrees
     */
    private static final double rad2deg(double rad)
    {
        return (rad * 180 / Math.PI);
    }
}
