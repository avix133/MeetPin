package com.coding.team.meetpin;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class OperationTest {

    @Test
    public void add()
    {
        Operation operation = new Operation();
        Assert.assertEquals(8,operation.add(3,5));

    }

    @Test
    public void substract()
    {
        Operation operation = new Operation();
        Assert.assertEquals(3,operation.substract(8,5));
    }
}
