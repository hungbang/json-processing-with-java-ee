package javaee.processing.json.doing.service;

import javaee.processing.json.doing.vo.Device;
import javaee.processing.json.doing.vo.Person;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KAI on 1/26/17.
 */
public class JsonStringToObject {

    private String jsonString;

    public JsonStringToObject(String jsonString) {
        this.jsonString = jsonString;
    }

    public Person convertToObject() throws ParseException {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));

        JsonObject jsonObject = jsonReader.readObject();

        Person person = new Person();

        person.setId(jsonObject.getInt("id"));
        person.setName(jsonObject.getString("personName"));
        person.setAddress(jsonObject.getString("address"));
        person.setAge(jsonObject.getInt("age"));
        person.setBirthDay(new SimpleDateFormat("DD/MM/YYYY").parse(jsonObject.getString("dob")));

        List<Device> devices = new ArrayList<>();

        JsonArray jsonArray = jsonObject.getJsonArray("deviceList");

        jsonArray.getValuesAs(JsonObject.class).forEach(jsonObj ->{
            Device device = new Device();
            device.setId(jsonObj.getInt("deviceId"));
            device.setName(jsonObj.getString("name"));
            device.setModelType(jsonObj.getString("modelType"));
            devices.add(device);
        });
        person.setDeviceList(devices);

        return person;
    }

    public static final class JsonStringToObjectBuilder{
        private String jsonString;

        public JsonStringToObjectBuilder(String jsonString) {
            this.jsonString = jsonString;
        }

        public static JsonStringToObjectBuilder builder(String jsonString){
            return new JsonStringToObjectBuilder(jsonString);
        }

        public JsonStringToObject getJsonStringToObject(){
            return new JsonStringToObject(jsonString);
        }
    }
}
