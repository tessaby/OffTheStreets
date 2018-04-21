package com.example.tessamber.offthestreets;

import com.example.tessamber.offthestreets.model.HomelessShelter;
import com.example.tessamber.offthestreets.model.ShelterCollection;

import static com.example.tessamber.offthestreets.model.ShelterCollection.INSTANCE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("JavaDoc")
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    ShelterCollection shelter = new ShelterCollection();
    HomelessShelter hShelter = new HomelessShelter();

    @Test
    public void testSearchAgeRange(){
       //assertEquals(true, shelter.searchForAgeRange(hShelter, "Anyone"));
       assertEquals(true, shelter.searchForAgeRange(hShelter,"young adults"));

    }


}