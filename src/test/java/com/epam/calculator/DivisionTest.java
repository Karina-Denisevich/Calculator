package com.epam.calculator;

import com.epam.calculator.mathematics.Division;
import org.junit.*;

import static org.junit.Assert.*;

public class DivisionTest {

    private Division division;

    @Before
    public void setUp() {
        division = new Division();
    }

    @After
    public void down() {
        division = null;
    }

    @Test
    public void divisionTest() {
        assertEquals(10.0, division.compute(20.0, 2.0), 0.001);
    }

    @Test(expected = ArithmeticException.class)
    public void divisionNegativeTest() {
        division.compute(10.0, 0.0);
    }
}
