package com.coding.team.meetpin;

import org.junit.Assert;
import org.junit.Test;

public class OperationTest {
    @Test
    public void add()
    {
        Operation operation = new Operation();
        Assert.assertEquals(8,operation.add(3,5));
    }

    @Test
    public void subtract()
    {
        Operation operation = new Operation();
        Assert.assertEquals(3,operation.subtract(8,5));
    }
}
