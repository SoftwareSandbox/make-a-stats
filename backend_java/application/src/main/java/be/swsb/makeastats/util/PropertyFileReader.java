package be.swsb.makeastats.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

// Couldn't find a good library within 2 minutes. Don't judge me!
public class PropertyFileReader {

    @SuppressWarnings("ThrowFromFinallyBlock")
    public static String readPubgApiKey() {
        Properties prop = new Properties();
        InputStream input = null;
        String result = "";
        try {
            input = new FileInputStream(Paths.get(System.getProperty("user.home"), ".key", "pubgapi.key").toString());
            prop.load(input);
            result = prop.getProperty("development");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }
}
