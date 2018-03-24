package com.example.vilma.biometricrecognition;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by jclew on 3/12/2018.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    int rowForData = 3;
    int columnForData = 2;
    int rowForTestCase = 4;
    int columnForTestCase = 2;
    String[][] dataBase = new String[rowForData][columnForData];
    String[][] testCase = new String[rowForTestCase][columnForTestCase];


    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void testCases() {
        dataBase[0][0] = "gag";
        dataBase[0][1] = "1234";
        dataBase[1][0] = "jcl1";
        dataBase[1][1] = "test1234";
        dataBase[2][0] = "vrc";
        dataBase[2][1] = "123";

        testCase[0][0] = "";
        testCase[0][1] = "";
        testCase[1][0] = "gag";
        testCase[1][1] = "";
        testCase[2][0] = "";
        testCase[2][1] = "1234";
        testCase[3][0] = "gag";
        testCase[3][1] = "1234";

    }

    @Test
    public void testValidUsernameLog(){
        for(int i =0; i<rowForTestCase; i++) {
            onView(withId(R.id.txtUsername)).perform(clearText(), typeText(testCase[i][0]), closeSoftKeyboard());
            onView(withId(R.id.txtPassWord)).perform(clearText(), typeText(testCase[i][1]), closeSoftKeyboard());

            if (testCase[i][0] == "gag" || testCase[i][0] == "jcl1" || testCase[i][0] == "vrc"){
                onView(withId(R.id.txtUsername)).perform(clearText(), typeText("gag"), closeSoftKeyboard())
                        .check(matches(withText("gag")));
            }
        }
    }

    @Test
    public void testValidPasswordLog() {
        onView(withId(R.id.txtPassWord)).perform(clearText(), typeText("1234"), closeSoftKeyboard())
                .check(matches(withText("1234")));
    }

    @Test
    public void inDatabaseLog() {
        for(int i =0; i<rowForTestCase; i++){
            onView(withId(R.id.txtUsername)).perform(clearText(), typeText(testCase[i][0]), closeSoftKeyboard());
            onView(withId(R.id.txtPassWord)).perform(clearText(), typeText(testCase[i][1]), closeSoftKeyboard());

            if(testCase[i][0] == "gag" && testCase[i][1] == "1234"){
                onView(withId(R.id.txtPassWord)).perform(clearText(), typeText("gag"), closeSoftKeyboard())
                        .check(matches(withText("gag")));

                onView(withId(R.id.txtPassWord)).perform(clearText(), typeText("1234"), closeSoftKeyboard())
                        .check(matches(withText("1234")));
            }
        }
    }

    //@Test
    //publcic void t

    /*@Test
    public void testInDataBase() {
        // Type text and then press the button.

        for(int j; j <dataBase.length; j++){
            onView(
                    anyOf(withId(R.id.txtUsername), withId(R.id.txtPassWord))
            ).check(matches);
        }
        onView(withId(R.id.txtUsername))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard())
                .check(matches(withText(mStringToBetyped)));
    } */

}