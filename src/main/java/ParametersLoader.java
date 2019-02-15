import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ParametersLoader {
    protected static String url;

    public static String loadProperty(String name) {

        Properties props = new Properties();
        InputStream is = ClassLoader.getSystemResourceAsStream("application.properties");
        try {
            props.load(is);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return props.getProperty(name);
    }
}

