package com.hazardmanager.users.services;

import com.hazardmanager.users.DTO.AreaDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.utilis.DistanceCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class LocationServiceTest {

    private Location location;

    private LocationService locationService;

    private AreaDto area;

    @Before
    public void buildUp() {
        area = new AreaDto();
        locationService = new LocationServiceImpl();
    }
    @Test
    public void when_DistanceBetweenTwoPoints_is_58km_should_return_58km(){
        double latitude1 =  47.1585;
        double longitude1 = 27.6014;
        double latitude2 =  46.6407;
        double longitude2 = 27.7276;
        double result = DistanceCalculator.calculateDistance(latitude1,longitude1,latitude2,longitude2);
        Assert.assertEquals(58.37 ,result,0.01);
    }
    @Test
    public void when_DistanceBetweenTwoPoints_is_2km_should_return_2km(){
        double latitude1 =  47.15407;
        double longitude1 = 27.57088;
        double latitude2 =  47.1585;
        double longitude2 = 27.6014;
        double result = DistanceCalculator.calculateDistance(latitude1,longitude1,latitude2,longitude2);
        Assert.assertEquals(2.36 ,result,0.01);
    }

    @Test
    public void when_DistanceBetweenTwoPoints_is_15410_should_return_15410(){
        double latitude1 = 73.356499;
        double longitude1 = 179.630882;
        double latitude2 = -33.353073;
        double longitude2 = 20.857945;
        double result = DistanceCalculator.calculateDistance(latitude1,longitude1,latitude2,longitude2);
        Assert.assertEquals(15410,result,10);



    }
    @Test
    public void when_point_insideRadius_should_return_true(){
        area.latitude = 47.1585;
        area.longitude = 27.6014;
        area.radius = 2.5;
        Location inCity = new Location("5917f88d5579aa05c4b1c1d2");
        inCity.setLatitude(47.15407);
        inCity.setLongitude(27.57088);
        boolean isPointInArea = locationService.isInArea(inCity,area);
        Assert.assertTrue(isPointInArea);
    }

    @Test
    public void when_point_outsideRadius_should_return_false(){
        area.latitude = 47.1585;
        area.longitude = 27.6014;
        area.radius = 2.35;
        Location inCity = new Location("5917f88d5579aa05c4b1c1d2");
        inCity.setLatitude(47.15407);
        inCity.setLongitude(27.57088);
        boolean isPointInArea = locationService.isInArea(inCity,area);
        Assert.assertFalse(isPointInArea);
    }



    @Test(expected = NullPointerException.class)
    public void when_getLocationsWithinEventName_called_with_null_should_throw_NullPointerException() {
        List<Location> locations = locationService.getLocationsWithinEventArea(null);
    }

}
