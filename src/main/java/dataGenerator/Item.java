package dataGenerator;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Data
public class Item {
    private final String name;
    private final BigDecimal quantity;
    private final BigDecimal price;
}
