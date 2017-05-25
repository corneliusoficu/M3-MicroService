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
    public void when_getLatitude_is_called_should_return_10() {
        double latitude = 10;
        location.setLatitude(latitude);
        Assert.assertEquals(latitude, location.getLatitude(), 0.001);
    }

    @Test
    public void when_getLongitude_is_called_should_return_20() {
        double longitude = 20;
        location.setLongitude(longitude);
        Assert.assertEquals(longitude, location.getLongitude(), 0.001);
    }

    @Test
    public void when_getAlias_is_called_should_return_test() {
        String alias = "Test";
        location.setAlias(alias);
        Assert.assertEquals(alias, location.getAlias());
    }

    @Test(expected = IllegalArgumentException.class)
    public void when_setLatitude_is_called_should_throw_IllegalArgumentException() {
        double latitude = 2000;
        location.setLatitude(latitude);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLongitude_should_throw_IllegalArgumentException() {
        double longitude = 2000;
        location.setLongitude(longitude);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAlias_should_throw_IllegalArgumentException() {
        String alias = "";
        location.setAlias(alias);
    }
}
