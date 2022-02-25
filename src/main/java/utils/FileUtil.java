package utils;

import constants.CharConstants;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

    public static String getXPathAndUpdate(String key, String... params) {
        key = key.replace(CharConstants.CHAR_XPATH, "");
        return readPropertiesFile("element-repository.properties", key, params);
    }

    public static String readPropertiesFile(String filename, String key, String... params) {
        String result, finalResult = "";

        try {
            InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(filename);
            ResourceBundle resources = new PropertyResourceBundle(inputStream);
            result = resources.getString(key);

            if (result.contains("{") && params.length > 0) {
                int count = 0;
                Pattern p = Pattern.compile("\\{.*?\\}");
                Matcher m = p.matcher(result);

                while (m.find()) {
                    finalResult = result.replace(m.group(), params[count]);
                    count++;
                    result = finalResult;
                }

            } else {
                finalResult = result;
            }

        } catch (FileNotFoundException e) {
            LogUtil.warn(FileUtil.class, "FileNotFoundException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            LogUtil.warn(FileUtil.class, "Exception: " + e);

        }
        return finalResult;
    }

    public static String getSetting(String key, String... params) {
        key = key.replace(CharConstants.SPECIALCHAR_SETTINGS, "");
        return readPropertiesFile("application-settings.properties", key, params);
    }

}
