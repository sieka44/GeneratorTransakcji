package dataGenerator;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class Transaction {
    private final int id;
    private final String timestamp;
    private final int customer_id;
    private final Item[] items;
    private final BigDecimal sum;
}
