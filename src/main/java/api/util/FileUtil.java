package api.util;

import java.io.InputStream;

public class FileUtil {

    public static InputStream getSchemaStream() {
        InputStream stream =
                Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("json/schema.json");
        return stream;
    }
}
