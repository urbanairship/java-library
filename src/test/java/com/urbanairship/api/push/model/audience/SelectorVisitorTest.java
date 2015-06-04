package com.urbanairship.api.push.model.audience;

import org.junit.Test;

import static com.urbanairship.api.push.model.audience.Selectors.alias;
import static com.urbanairship.api.push.model.audience.Selectors.and;
import static com.urbanairship.api.push.model.audience.Selectors.apid;
import static com.urbanairship.api.push.model.audience.Selectors.deviceToken;
import static com.urbanairship.api.push.model.audience.Selectors.or;
import static com.urbanairship.api.push.model.audience.Selectors.segment;
import static com.urbanairship.api.push.model.audience.Selectors.tag;
import static org.junit.Assert.assertEquals;

public class SelectorVisitorTest {

    @Test
    public void testVisitor() throws Exception {
        Selector s = newTree();
        CountingVisitor v = new CountingVisitor();
        s.accept(v);
        assertEquals(14, v.count);
    }

    private Selector newTree() {
        // 14 nodes
        return
                or(
                        and(
                                or(
                                        alias("test1"),
                                        tag("foo"),
                                        tag("bar")),
                                or(
                                        alias("test2"),
                                        tag("baz"))),
                        segment("seg1"),
                        deviceToken("EE139068625F0514D12E661A1DA7FDE84150505E74251320ADFF04C539D12D03"),
                        deviceToken("EE139068625F0514D12E661A1DA7FDE84150505E74251320ADFF04C539D12D03"),
                        apid("4ef648d9-92a2-6f32-42bc-0210cc7f8823"),
                        apid("7a60aa49-1b24-1ef4-f425-f9ce8ce7ca55"));
    }

    public static class CountingVisitor implements SelectorVisitor {
        public int count = 0;

        @Override
        public void enter(Selector s) {
            count += 1;
        }

        @Override
        public void exit(Selector s) {
        }
    }
}
