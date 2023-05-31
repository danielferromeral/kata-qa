package co.empathy.kataqa.unit.log;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import co.empathy.kataqa.log.Logger;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

class LoggerTest {

    @Test
    void getInstance_is_not_null() {
        assertThat(Logger.getInstance(), is(notNullValue()));
    }

    @Test
    void getInstance_is_same_instance() {
        Logger logger = Logger.getInstance();
        assertThat(Logger.getInstance(), is(logger));
    }

    @Test
    void getList_is_empty() {
        assertThat(Logger.getInstance().getList(), is(emptyList()));
    }

    @Test
    void log_stores_data() {
        Logger logger = Logger.getInstance();
        String test = "test string";
        logger.log(test);
        assertThat(logger.getList().size(), is(equalTo(1)));
        assertThat(logger.getList().get(0), is(equalTo(test)));
    }

    @RepeatedTest(4)
    @Disabled
    void log_stores_multiple_data(RepetitionInfo repetitionInfo) {
        int iteration = repetitionInfo.getCurrentRepetition();
        Logger logger = Logger.getInstance();
        List<String> list = new ArrayList<>();
        String s;
        for (int i = 0; i < iteration; i++) {
            s = "test string " + i;
            list.add(s);
            logger.log(s);
        }
        assertThat(logger.getList().size(), is(equalTo(iteration)));
        for (int i = 0; i < iteration; i++) {
            assertThat(logger.getList().get(i), is(equalTo(list.get(i))));
        }
    }
}
