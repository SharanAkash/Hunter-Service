package BuildingRequest;

import pojo.CreateUserPojo;

public class CreateUserPayload {

    public static CreateUserPojo createUserPayload(String name, String email, String gender, String status){
        CreateUserPojo createUserPojo= new CreateUserPojo();
        createUserPojo.setName(name);
        createUserPojo.setEmail(email);
        createUserPojo.setGender(gender);
        createUserPojo.setStatus(status);
        return createUserPojo;
    }
}
