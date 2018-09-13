package lt.baltic.talents.project.cafeteria;

import com.google.gson.Gson;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This class is used to create a special forrmater to format messages
 * in JSON format
 *
 * @author Saulius Jackunas
 */

public class FormatterJSON extends Formatter {

    private Gson gson;

    public FormatterJSON() {
        gson = new Gson();
    }

    @Override
    public String format(LogRecord logRecord) {
        return gson.toJson(logRecord) + "\n";
    }

}
