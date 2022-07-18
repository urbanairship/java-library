package com.urbanairship.api.push.model.audience.SelectorsTest;

import com.google.common.collect.Iterables;
import com.urbanairship.api.push.model.audience.BasicCompoundSelector;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.Selectors;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SelectorsTest {

    @Test
    public void testCompoundStaticMethods() {
        SelectorType type = SelectorType.ALL;
        ArrayList<Selector> sl = new ArrayList<>();
        sl.add(Selectors.tag("tag1"));
        sl.add(Selectors.tag("tag2"));
        Selector compound = Selectors.compound(type, sl);
        assertTrue("Should be BasicCompoundSelector", compound instanceof BasicCompoundSelector);
        BasicCompoundSelector cpdSelector = (BasicCompoundSelector) compound;
        Selector[] selectors = Iterables.toArray(cpdSelector.getChildren(), Selector.class);
        assertEquals("There should be two selectors", selectors.length, 2);
    }
}
