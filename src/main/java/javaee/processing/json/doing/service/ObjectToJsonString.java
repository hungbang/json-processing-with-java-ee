package javaee.processing.json.doing.service;

import javaee.processing.json.doing.vo.Device;
import javaee.processing.json.doing.vo.Person;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KAI on 1/26/17.
 */
public class ObjectToJsonString {

    private Person person;

    public ObjectToJsonString(Person person) {
        this.person = person;
    }

    public String convertToJsonString() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("personName", person.getName()).add("age", person.getAge())
                .add("address", person.getAddress())
                .add("id", person.getId())
                .add("dob", new SimpleDateFormat("DD/MM/YYYY")
                        .format(person.getBirthDay()));
        List<Device> deviceList = person.getDeviceList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        deviceList.forEach(device -> {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("deviceId", device.getId())
                    .add("name", device.getName())
                    .add("modelType", device.getModelType());
            arrayBuilder.add(objectBuilder);
        });

        builder.add("deviceList", arrayBuilder);
        JsonObject jsonObject = builder.build();
        Map<String, Boolean> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory factory = Json.createWriterFactory(config);

        String jsonString = null;
        try (Writer writer = new StringWriter()) {
            factory.createWriter(writer).writeObject(jsonObject);
            jsonString = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static final class ObjectToStringBuilder{
        private Person person;

        public ObjectToStringBuilder(Person person) {
            this.person = person;
        }

        public static ObjectToStringBuilder builder(Person person){
            return new ObjectToStringBuilder(person);
        }

        public ObjectToJsonString getObjectToJsonString(){
           return new ObjectToJsonString(person);
        }
    }
}
