package Resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    public static RequestSpecification request;

    public RequestSpecification requestSpecification() throws IOException {

        request = new RequestSpecBuilder()
                .setBaseUri(getGlobalValue("base_url"))
                .setContentType(ContentType.JSON)
                .build();

        return request;
    }

    public String getGlobalValue(String key) throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/Resources/Global.properties");
        prop.load(fis);
        return prop.getProperty(key);

    }
}
