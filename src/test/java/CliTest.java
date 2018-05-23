import inputParser.Cli;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CliTest {
    Cli cli;

    @Before
    public void init() {
        cli = new Cli();
    }

    @Test(expected = MissingOptionException.class)
    public void missingRequiredOptionTest() throws ParseException {
        String[] args = "\"badParse\":\"throwException\",\"data1\":\"example2\"".split(",");
        cli.parse(args);
    }

    @Test
    public void correctParse() throws ParseException {
        String[] args = "-itemsFile items.csv -eventsCount 0".split(" ");
        cli.parse(args);
    }

    @Test
    public void incorrectParse() {
        String[] args = "-itemsFile .txt -eventsCount".split(" ");
        try {
            cli.parse(args);
        } catch (ParseException e) {
            Assert.assertTrue(e instanceof MissingArgumentException);
        }

    }

}
