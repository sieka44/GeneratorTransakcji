package dataGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneratorValues {
    final String itemsFile;
    final String customersIds;
    final String dateRange;
    final String itemsCount;
    final String itemsQuantity;
    final String eventsCount;
    final String outDir;
    final String format;

}
