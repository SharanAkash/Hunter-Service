package constants;

import java.util.Random;
import java.util.UUID;

public class UserConstants {

    static Random random = new Random();
    public static final String NAME = "John Doe" + random.nextInt(100000);
    public static  final String EMAIL = "johndoe" + random.nextInt(100000) + "@example.com";
    public static  final String GENDER = "male";
    public static  final String STATUS = "active";

}
