package dataGenerator;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {
    private final String name;
    private final BigDecimal quantity;
    private final BigDecimal price;
}
