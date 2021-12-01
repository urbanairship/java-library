package com.urbanairship.api.push.parse.audience;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.urbanairship.api.common.parse.APIParsingException;
import com.urbanairship.api.push.model.audience.BasicValueSelector;
import com.urbanairship.api.push.model.audience.CompoundSelector;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.ValueSelector;
import com.urbanairship.api.push.parse.PushObjectMapper;
import org.junit.Test;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SelectorDeserializerTest {
    private static final ObjectMapper mapper = PushObjectMapper.getInstance();

    @Test
    public void testDeserializeBroadcast() throws Exception {
        Selector value = mapper.readValue("\"all\"", Selector.class);
        assertEquals(value.getType(), SelectorType.ALL);
    }

    @Test
    public void testDeserializeTriggered() throws Exception {
        Selector value = mapper.readValue("\"triggered\"", Selector.class);
        assertEquals(value.getType(), SelectorType.TRIGGERED);
    }

    @Test
    public void testIosChannelCase() throws Exception {
        String iosChannel = UUID.randomUUID().toString();
        String json = "{\"ios_channel\": \"" + iosChannel + "\"}";
        BasicValueSelector value = (BasicValueSelector) mapper.readValue(json, Selector.class);
        assertTrue(value.getType() == SelectorType.IOS_CHANNEL);
        assertEquals(value.getValue(), iosChannel);
    }

    @Test
    public void testAndroidChannelCase() throws Exception {
        String androidChannel = UUID.randomUUID().toString();
        String json = "{\"android_channel\": \"" + androidChannel + "\"}";
        BasicValueSelector value = (BasicValueSelector) mapper.readValue(json, Selector.class);
        assertTrue(value.getType() == SelectorType.ANDROID_CHANNEL);
        assertEquals(value.getValue(), androidChannel);
    }

    @Test
    public void testAmazonChannelCase() throws Exception {
        String amazonChannel = UUID.randomUUID().toString();
        String json = "{\"amazon_channel\": \"" + amazonChannel + "\"}";
        BasicValueSelector value = (BasicValueSelector) mapper.readValue(json, Selector.class);
        assertTrue(value.getType() == SelectorType.AMAZON_CHANNEL);
        assertEquals(value.getValue(), amazonChannel);
    }

    @Test
    public void testChannelCase() throws Exception {
        String channel = UUID.randomUUID().toString();
        String json = "{\"channel\": \"" + channel + "\"}";
        BasicValueSelector value = (BasicValueSelector) mapper.readValue(json, Selector.class);
        assertTrue(value.getType() == SelectorType.CHANNEL);
        assertEquals(value.getValue(), channel);
    }


    @Test
    public void testNamedUserCase() throws Exception {
        String namedUser = "FakeNamedUser";
        String json = "{\"named_user\": \"" + namedUser + "\"}";
        BasicValueSelector value = (BasicValueSelector) mapper.readValue(json, Selector.class);
        assertTrue(value.getType() == SelectorType.NAMED_USER);
        assertEquals(value.getValue(), namedUser);
    }

    @Test
    public void testDeserializeTag() throws Exception {
        Selector value = mapper.readValue("{ \"tag\" : \"derp\" }", Selector.class);
        assertTrue(value.getType() == SelectorType.TAG);
        assertTrue(value instanceof ValueSelector);
    }

    @Test
    public void testDeserializeAttribute() throws Exception {
        String json = "{\n"
                + "  \"attribute\" : \"birthday\",\n"
                + "  \"operator\" : \"equals\",\n"
                + "  \"value\" : \"now\",\n"
                + "  \"match_precision\" : \"month_day\"\n"
                + "}";
        Selector value = mapper.readValue(json, Selector.class);
        assertTrue(value.getType() == SelectorType.ATTRIBUTE);
        assertTrue(value instanceof ValueSelector);
    }

    @Test
    public void testTagClass() throws Exception {
        String json = "{\n"
                + "  \"tag\" : \"1\",\n"
                + "  \"tag_class\" : \"autogroup\"\n"
                + "}";
        Selector value = mapper.readValue(json, Selector.class);
        assertTrue(value.getType() == SelectorType.TAG);
        assertTrue(value instanceof ValueSelector);
        ValueSelector vs = (ValueSelector) value;
        assertTrue(vs.getAttributes().isPresent());
        assertEquals(1, vs.getAttributes().get().size());
        assertEquals("autogroup", vs.getAttributes().get().get("tag_class"));
    }

    @Test
    public void testTagGroup() throws Exception {
        String json = "{\n" +
            "   \"tag\": \"tag1\",\n" +
            "   \"group\": \"group1\"\n" +
            "}";

        Selector value = mapper.readValue(json, Selector.class);
        assertTrue(value.getType() == SelectorType.TAG);
        assertTrue(value instanceof ValueSelector);
        ValueSelector vs = (ValueSelector) value;
        assertTrue(vs.getAttributes().isPresent());
        assertEquals(1, vs.getAttributes().get().size());
        assertEquals("group1", vs.getAttributes().get().get("group"));
    }

    @Test
    public void testStaticList() throws Exception {
        String json = "{\"static_list\":\"list123\"}";
        Selector value = mapper.readValue(json, Selector.class);
        assertNotNull(value);
        assertEquals(value.getType(), SelectorType.STATIC_LIST);
        assertEquals(((ValueSelector)value).getValue(), "list123");
    }

    @Test
    public void testAtomicCaseInsensitivity() throws Exception {
        assertEquals(SelectorType.ALL, mapper.readValue("\"all\"", Selector.class).getType());
        assertEquals(SelectorType.ALL, mapper.readValue("\"ALL\"", Selector.class).getType());
        assertEquals(SelectorType.ALL, mapper.readValue("\"aLl\"", Selector.class).getType());
        assertEquals(SelectorType.TRIGGERED, mapper.readValue("\"triggered\"", Selector.class).getType());
        assertEquals(SelectorType.TRIGGERED, mapper.readValue("\"TRIGGERED\"", Selector.class).getType());
        assertEquals(SelectorType.TRIGGERED, mapper.readValue("\"trIGGeRed\"", Selector.class).getType());
    }

    @Test
    public void testCompoundSelector() throws Exception {
        String json = "{\n"
                + "  \"and\" : [\n"
                + "    { \"tag\" : \"herp\" }, \n"
                + "    { \"tag\" : \"derp\" }, \n"
                + "    { \"static_list\"  :  \"test123\"} \n"
                + "  ]\n"
                + "}";
        Selector s = mapper.readValue(json, Selector.class);
        assertTrue(s instanceof CompoundSelector);
        assertEquals(SelectorType.AND, s.getType());

        CompoundSelector cs = (CompoundSelector) s;
        assertEquals(3, Iterables.size(cs.getChildren()));

        Iterator<Selector> i = cs.getChildren().iterator();

        Selector c = i.next();
        assertTrue(c instanceof ValueSelector);
        ValueSelector vs = (ValueSelector) c;
        assertEquals(SelectorType.TAG, c.getType());
        assertEquals("herp", vs.getValue());

        c = i.next();
        assertTrue(c instanceof ValueSelector);
        vs = (ValueSelector) c;
        assertEquals(SelectorType.TAG, c.getType());
        assertEquals("derp", vs.getValue());

        c = i.next();
        assertTrue(c instanceof ValueSelector);
        vs = (ValueSelector) c;
        assertEquals(SelectorType.STATIC_LIST, c.getType());
        assertEquals("test123", vs.getValue());
    }

    @Test
    public void testGroupCompoundSelector() throws Exception {
        String json = "{\n"
            + "  \"and\" : [\n"
            + "    { \"tag\" : \"tag1\", \"group\" : \"group1\" }, \n"
            + "    { \"tag\" : \"tag2\", \"group\" : \"group2\" }, \n"
            + "    { \"tag\" : \"tag3\"}\n"
            + "  ]\n"
            + "}";
        Selector s = mapper.readValue(json, Selector.class);
        assertTrue(s instanceof CompoundSelector);
        assertEquals(SelectorType.AND, s.getType());

        CompoundSelector cs = (CompoundSelector) s;
        assertEquals(3, Iterables.size(cs.getChildren()));

        Iterator<Selector> i = cs.getChildren().iterator();

        Selector c = i.next();
        assertTrue(c instanceof ValueSelector);
        ValueSelector vs = (ValueSelector) c;
        assertEquals(SelectorType.TAG, c.getType());
        assertEquals("group1", vs.getAttributes().get().get("group"));
        assertEquals("tag1", vs.getValue());

        c = i.next();
        assertTrue(c instanceof ValueSelector);
        vs = (ValueSelector) c;
        assertEquals(SelectorType.TAG, c.getType());
        assertEquals("group2", vs.getAttributes().get().get("group"));
        assertEquals("tag2", vs.getValue());

        c = i.next();
        assertTrue(c instanceof ValueSelector);
        vs = (ValueSelector) c;
        assertEquals(SelectorType.TAG, c.getType());
        assertEquals("tag3", vs.getValue());
    }

    @Test
    public void testNOT() throws Exception {
        String json = "{"
                + "  \"not\" : {"
                + "    \"tag\" : \"derp\""
                + "  }"
                + "}";

        Selector s = mapper.readValue(json, Selector.class);
        assertTrue(s instanceof CompoundSelector);
        assertEquals(SelectorType.NOT, s.getType());
        CompoundSelector cs = (CompoundSelector) s;
        assertEquals(1, Iterables.size(cs.getChildren()));
    }

    @Test
    public void testImplicitOR() throws Exception {
        String json = "{\n"
                + "  \"tag\": [\n"
                + "    \"Joy\",\n"
                + "    \"Division\",\n"
                + "    \"New\",\n"
                + "    \"Order\"\n"
                + "  ]\n"
                + "}";

        Selector s = mapper.readValue(json, Selector.class);
        assertTrue(s instanceof CompoundSelector);
        assertEquals(SelectorType.OR, s.getType());

        CompoundSelector cs = (CompoundSelector) s;
        assertEquals(4, Iterables.size(cs.getChildren()));

        Iterator<Selector> i = cs.getChildren().iterator();

        s = i.next();
        assertTrue(s instanceof ValueSelector);
        assertEquals(SelectorType.TAG, s.getType());
        assertEquals("Joy", ((ValueSelector) s).getValue());

        s = i.next();
        assertTrue(s instanceof ValueSelector);
        assertEquals(SelectorType.TAG, s.getType());
        assertEquals("Division", ((ValueSelector) s).getValue());

        s = i.next();
        assertTrue(s instanceof ValueSelector);
        assertEquals(SelectorType.TAG, s.getType());
        assertEquals("New", ((ValueSelector) s).getValue());

        s = i.next();
        assertTrue(s instanceof ValueSelector);
        assertEquals(SelectorType.TAG, s.getType());
        assertEquals("Order", ((ValueSelector) s).getValue());
    }

    @Test
    public void testImplicitORForPlatform() throws Exception {
        String apid1 = UUID.randomUUID().toString();
        String apid2 = UUID.randomUUID().toString();

        String json = "{\n"
                + "  \"apid\": [\n"
                + "    \"" + apid1 + "\",\n"
                + "    \"" + apid2 + "\"\n"
                + "  ]\n"
                + "}";

        Selector s = mapper.readValue(json, Selector.class);
        assertTrue(s instanceof CompoundSelector);
        assertEquals(SelectorType.OR, s.getType());

        CompoundSelector cs = (CompoundSelector) s;
        assertEquals(2, Iterables.size(cs.getChildren()));

        Iterator<Selector> i = cs.getChildren().iterator();

        s = i.next();
        assertTrue(s instanceof ValueSelector);
        assertEquals(SelectorType.APID, s.getType());
        assertEquals(apid1, ((ValueSelector) s).getValue());

        s = i.next();
        assertTrue(s instanceof ValueSelector);
        assertEquals(SelectorType.APID, s.getType());
        assertEquals(apid2, ((ValueSelector) s).getValue());

    }

    @Test
    public void testNestedCompound() throws Exception {
        String json = "{\n"
                + "  \"and\" : [\n"
                + "    { \"or\" : [\n"
                + "        { \"alias\" : \"s1\" },\n"
                + "        { \"alias\" : \"s2\" }\n"
                + "      ] },\n"
                + "    { \"or\" : [\n"
                + "        { \"tag\" : \"t1\" },\n"
                + "        { \"tag\" : \"t2\" }\n"
                + "      ] }\n"
                + "  ]\n"
                + "}";
        Selector s = mapper.readValue(json, Selector.class);
        assertEquals(SelectorType.AND, s.getType());

        assertEquals(2, Iterables.size(((CompoundSelector) s).getChildren()));

        Iterator<Selector> i = ((CompoundSelector) s).getChildren().iterator();
        Selector c = i.next();
        assertTrue(c instanceof CompoundSelector);
        assertEquals(SelectorType.OR, c.getType());
        assertEquals(SelectorType.ALIAS, ((CompoundSelector) c).getChildren().iterator().next().getType());
    }

    @Test
    public void testCase() throws Exception {
        String json = "{\"and\" : [{\"tag\" : \"t1\"}]}";
        Selector s = mapper.readValue(json, Selector.class);
        assertEquals(SelectorType.AND, s.getType());


        json = "{\"AND\" : [{\"TAG\" : \"t1\"}]}";
        s = mapper.readValue(json, Selector.class);
        assertEquals(SelectorType.AND, s.getType());

        json = "{\"Or\" : [{\"tag\" : \"t1\"}]}";
        s = mapper.readValue(json, Selector.class);
        assertEquals(SelectorType.OR, s.getType());
    }

    /*
     * Illegal expressions
     */

    @Test(expected = APIParsingException.class)
    public void testInvalidAtomicSelector() throws Exception {
        mapper.readValue("\"derped\"", Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testAtomicWithArgument() throws Exception {
        mapper.readValue("{ \"all\" : \"some\" }", Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testBadTagValue() throws Exception {
        mapper.readValue("{ \"tag\" : 10 }", Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testUnknownSelectorType() throws Exception {
        mapper.readValue("{ \"derp\" : \"value\" }", Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testAtomicNestedInCompound() throws Exception {
        String json = "{\n"
                + "  \"or\" : [\n"
                + "    \"all\",\n"
                + "    \"triggered\"\n"
                + "  ]\n"
                + "}";
        mapper.readValue(json, Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testEmptyCompoundExpression() throws Exception {
        mapper.readValue("{ \"OR\" : [ ] }", Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testTooManyArgumentsToNOT() throws Exception {
        String json = "{\n"
                + "  \"not\" : [\n"
                + "    { \"tag\" : \"wat\" },\n"
                + "    { \"tag\" : \"derp\" }\n"
                + "  ]\n"
                + "}";
        mapper.readValue(json, Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testBadImplicitOR() throws Exception {
        String json = "{\n"
                + "  \"alias\" : [\n"
                + "    \"seg1\",\n"
                + "    { \"tag\" : \"whoops\" }\n"
                + "  ]\n"
                + "}";
        mapper.readValue(json, Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testCompoundValidation_AND1() throws Exception {
        mapper.readValue("{\"and\" : \"foo\"}", Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testCompoundValidation_NOT() throws Exception {
        mapper.readValue("{\"not\" : [ { \"tag\" : \"foo\" }, { \"tag\" : \"bar\" } ] }", Selector.class);
    }

    @Test(expected = APIParsingException.class)
    public void testCompoundValidation_ContainsAtomic() throws Exception {
        mapper.readValue("{\"or\" : [ \"all\", \"triggered\" ] }", Selector.class);
    }
}
