package ro.rasel.time;

import org.hamcrest.core.StringStartsWith;
import org.junit.Test;
import ro.rasel.time.format.IDurationFormatter;

import java.time.temporal.ChronoUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TimerImplTest {

    @Test
    public void getTimePassed() throws InterruptedException {
        Timer timer = new TimerImpl();
        Thread.sleep(1234);
        IDurationFormatter timePassed = timer.getTimePassed();
        assertThat(timePassed.getAsInt(ChronoUnit.SECONDS), is(1));
        assertThat(timePassed.toString(), StringStartsWith.startsWith("1s:"));
    }
}