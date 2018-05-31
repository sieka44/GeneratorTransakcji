package fileWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import dataGenerator.Item;
import dataGenerator.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

@Service("xml")
public class FileXmlWriter implements FileWriter {
    private static final Logger LOGGER = LogManager.getLogger(FileXmlWriter.class.getName());

    @Override
    public void saveTransaction(Transaction transaction, int idNumber, String outDir) {
        String path = System.getProperty("user.dir") + outDir.replace(".", "").replace("\"", "/");
        File file = new File(path + "/xml" + (idNumber + 1) + ".xml");
        LOGGER.trace("Path to save dir: " + path);
        File parent = file.getParentFile();
        parent.mkdirs();
        XStream xStream = new XStream();
        xStream.alias("Transaction", Transaction.class);
        xStream.alias("Item", Item.class);
        ZonedDateConverter conv = new ZonedDateConverter();
        xStream.registerConverter(conv);
        try {
            xStream.toXML(transaction, new java.io.FileWriter(file));
            LOGGER.trace("XML" + idNumber + " saved successfully");
        } catch (IOException e) {
            LOGGER.error("Error with saving Xml file nr : " + idNumber);
            e.printStackTrace();
        }
    }

    private class ZonedDateConverter extends AbstractSingleValueConverter {

        public boolean canConvert(Class type) {
            return (type != null) && ZonedDateTime.class.getPackage().equals(type.getPackage());
        }

        public String toString(Object source) {
            return source.toString();
        }

        public Object fromString(String str) {
            try {
                return ZonedDateTime.parse(str);
            } catch (Exception e) {
                LOGGER.error("CANT CONVERT DATE TO XML");
                e.printStackTrace();
            }
            return "";
        }

    }
}