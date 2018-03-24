import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CliTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
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

}
