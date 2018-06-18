import controler.MqController;
import dataGenerator.Generator;
import fileWriter.FileJsonWriter;
import fileWriter.FileXmlWriter;
import inputParser.FileInputController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ConfigSpringTest {
    @Bean
    public FileInputController fileInputController() {
        return new FileInputController("items.csv");
    }

    @Bean
    public Generator generator() {
        return new Generator("1:20",
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")) + ":" + ZonedDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")),
                "2:2",
                "1:5",
                "1",
                "",
                new FileJsonWriter(),
                new MqController("","","")
        );
    }
}
