package com.github.phf.jb;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlagsTest {
    Flags unit;
    Object dummy;

    @Before
    public void setup() {
        unit = new Flags();
    }

    @Test
    public void noFlagsAtAll() {
        String[] args = {"this", "is", "it"};
        String[] left = unit.parse(args);
        assertArrayEquals(args, left);
    }

    @Test
    public void flagsRegisteredButUnused() {
        unit.bool("b", (Boolean b) -> dummy = b);
        unit.integer("i", (Integer i) -> dummy = i);
        unit.string("s", (String s) -> dummy = s);
        String[] args = {"this", "is", "it"};
        String[] left = unit.parse(args);
        assertArrayEquals(args, left);
    }

    @Test
    public void flagsUsed() {
        ArrayList<String> l = new ArrayList<String>();
        unit.bool("b", (Boolean b) -> l.add(b.toString()));
        unit.integer("i", (Integer i) -> l.add(i.toString()));
        unit.string("s", (String s) -> l.add(s));
        String[] args = {"this", "-s", "is", "it", "-b", "true", "-i", "123", "yay"};
        String[] left = unit.parse(args);
        String[] want = {"this", "it", "yay"};
        assertArrayEquals(want, left);
        String[] dant = {"is", "true", "123"};
        assertArrayEquals(dant, l.toArray());
    }
}
