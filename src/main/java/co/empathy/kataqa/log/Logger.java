package co.empathy.kataqa.log;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final Logger instance = new Logger();

    private static final List<String> list = new ArrayList();

    private Logger() {
    }

    public static Logger getInstance() {
        return instance;
    }

    public void log(String text) {
        list.add(text);
    }

    public List<String> getList() {
        return list;
    }

}
