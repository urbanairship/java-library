package com.urbanairship.api.push.model.notification.actions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:n.sowen@2scale.net">Nils Sowen</a>
 */
public class ActionsTest {

    @Test
    public void testBuild() {
        Actions a = Actions.newBuilder().setOpen(Open.deepLink("Meinvf://deeplink/Bill")).build();
        assertEquals("Other value expected", "Meinvf://deeplink/Bill", a.getOpen().get().getContentData().get().getContent().get());
        assertEquals("Other value expected", OpenType.DEEP_LINK, a.getOpen().get().getType().get());
    }



}
