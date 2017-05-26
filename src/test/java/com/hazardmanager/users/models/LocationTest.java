package com.hazardmanager.users.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class LocationTest {

    private Location location;

    @Before
    public void buildUp() {
        location = new Location("591755495579aa2b44533c25");
    }

    @After
    public void tearDown() {
        location = null;
    }

    @Test
    public void when_latitude_is_10_getLatitude_should_return_10() {
        double latitude = 10;
        location.setLatitude(latitude);
        Assert.assertEquals(latitude, location.getLatitude(), 0.001);
    }

    @Test
    public void when_longitude_is_20_getLongitude_should_return_20() {
        double longitude = 20;
        location.setLongitude(longitude);
        Assert.assertEquals(longitude, location.getLongitude(), 0.001);
    }

    @Test
    public void when_alias_is_test_getAlias_should_return_test() {
        String alias = "test";
        location.setAlias(alias);
        Assert.assertEquals(alias, location.getAlias());
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_setLatitude_is_called_with_negative_2000_should_throw_IllegalArgumentException() {
        double latitude = -2000;
        location.setLatitude(latitude);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_setLongitude_is_called_with_negative_2000_should_throw_IllegalArgumentException() {
        double longitude = -2000;
        location.setLongitude(longitude);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_setLatitude_is_called_with_2000_should_throw_IllegalArgumentException() {
        double latitude = 2000;
        location.setLatitude(latitude);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_setLongitude_is_called_with_2000_should_throw_IllegalArgumentException() {
        double longitude = 2000;
        location.setLongitude(longitude);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_setAlias_is_called_with_empty_string_should_throw_IllegalArgumentException() {
        String alias = "";
        location.setAlias(alias);
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_setAlias_is_called_with_null_should_throw_IllegalArgumentException() {
        location.setAlias(null);
    }

}
