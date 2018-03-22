import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) {
        try {
            new Cli().parse(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
