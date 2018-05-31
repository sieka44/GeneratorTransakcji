package dataGenerator;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {
    private final int id;
    private final String timestamp;
    private final int customer_id;
    private final Item[] items;
    private final BigDecimal sum;
}
