package com.hazardmanager.users.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void buildUp() {
        user = new User();
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void when_firstName_is_test_isFirstNameValid_should_return_true() {
        Assert.assertTrue(user.isFirstNameValid("test"));
    }

    @Test
    public void when_firstName_is_too_long_isLastNameValid_should_return_false() {
        Assert.assertFalse(user.isFirstNameValid("testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"));
    }

    @Test
    public void when_firstName_is_too_short_isLastNameValid_should_return_false() {
        Assert.assertFalse(user.isFirstNameValid("t"));
    }

    @Test
    public void when_lastName_is_test_isLastNameValid_should_return_true() {
        Assert.assertTrue(user.isLastNameValid("test"));
    }

    @Test
    public void when_lastName_is_too_long_isLastNameValid_should_return_false() {
        Assert.assertFalse(user.isLastNameValid("testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"));
    }

    @Test
    public void when_lastName_is_too_short_isLastNameValid_should_return_false() {
        Assert.assertFalse(user.isLastNameValid("t"));
    }

    @Test
    public void when_userName_is_test_isUserNameValid_should_return_true() {
        Assert.assertTrue(user.isUserNameValid("test"));
    }

    @Test
    public void when_userName_is_too_long_isUserNameValid_should_return_false() {
        Assert.assertFalse(user.isUserNameValid("testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"));
    }

    @Test
    public void when_userName_is_too_short_isUserNameValid_should_return_false() {
        Assert.assertFalse(user.isUserNameValid("t"));
    }

    @Test
    public void when_password_is_test_isPasswordValid_should_return_true() {
        Assert.assertTrue(user.isPasswordValid("test"));
    }

    @Test
    public void when_password_is_too_long_isPasswordValid_should_return_false() {
        Assert.assertFalse(user.isPasswordValid("testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"));
    }

    @Test
    public void when_password_is_too_short_isPasswordValid_should_return_false() {
        Assert.assertFalse(user.isPasswordValid("t"));
    }

    @Test
    public void when_email_is_testtest_isEmailValid_should_return_true() {
        Assert.assertTrue(user.isEmailValid("test@test"));
    }

    @Test
    public void when_email_does_not_contain_at_sign_isEmailValid_should_return_false() {
        Assert.assertFalse(user.isEmailValid("testtest"));
    }

    @Test
    public void when_email_is_too_long_isEmailValid_should_return_false() {
        Assert.assertFalse(user.isEmailValid("testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"+"testtesttt"));
    }

    @Test
    public void when_email_is_too_short_isEmailValid_should_return_false() {
        Assert.assertFalse(user.isEmailValid("t"));
    }

    @Test
    public void when_phoneNumber_is_0700000000_isPhoneNumberValid_should_return_true() {
        Assert.assertTrue(user.isPhoneNumberValid("0700000000"));
    }

    @Test
    public void when_phoneNumber_is_too_long_isPhoneNumberValid_should_return_false() {
        Assert.assertFalse(user.isPhoneNumberValid("0000000000"+"0000000000"+"0000000000"+"0000000000"+"0000000000"+"0000000000"+"0000000000"+"0000000000"));
    }

    @Test
    public void when_phoneNumber_is_too_short_isPhoneNumberValid_should_return_false() {
        Assert.assertFalse(user.isPhoneNumberValid("0"));
    }

}
