import javaee.processing.json.doing.service.JsonStringToObject;
import javaee.processing.json.doing.service.ObjectToJsonString;
import javaee.processing.json.doing.vo.Device;
import javaee.processing.json.doing.vo.Person;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException {
        System.out.println("Hello World!");

        Person person = new Person();
        person.setId(1);
        person.setName("hung bang");
        person.setAge(28);
        person.setAddress("Da Nang, Viet Nam");
        person.setBirthDay(new Date());

        List<Device> devices = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Device device = new Device();
            device.setId(i);
            device.setName("Sam Sung "+ i);
            device.setModelType("Samsung");
            devices.add(device);
        }
        person.setDeviceList(devices);

        String jsonValue = ObjectToJsonString.ObjectToStringBuilder.builder(person).getObjectToJsonString().convertToJsonString();
        System.out.println("====jsonValue: "+ jsonValue);


        Person personResult = JsonStringToObject.JsonStringToObjectBuilder
                .builder(jsonValue)
                .getJsonStringToObject()
                .convertToObject();

        System.out.println(personResult);
    }
}
