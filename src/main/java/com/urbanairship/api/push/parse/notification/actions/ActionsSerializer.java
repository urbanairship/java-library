package com.urbanairship.api.push.parse.notification.actions;

import com.urbanairship.api.push.model.notification.actions.Actions;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * ActionSerializer
 * @author <a href="mailto:n.sowen@2scale.net">Nils Sowen</a>
 */
public class ActionsSerializer extends JsonSerializer<Actions> {

    @Override
    public void serialize(Actions actions, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();

        if (actions.getAddTag().isPresent()) {
            jsonGenerator.writeObjectField("add_tag", actions.getAddTag().get());
        }

        if (actions.getRemoveTag().isPresent()) {
            jsonGenerator.writeObjectField("remove_tag", actions.getRemoveTag().get());
        }

        if (actions.getOpen().isPresent()) {
            jsonGenerator.writeObjectField("open", actions.getOpen().get());
        }

        if (actions.getAppDefined().isPresent()) {
            jsonGenerator.writeObjectField("app_defined", actions.getAppDefined());
        }

        jsonGenerator.writeEndObject();
    }

}
