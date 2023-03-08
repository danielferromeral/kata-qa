package co.empathy.kataqa.index;

import co.empathy.kataqa.db.Database;
import co.empathy.kataqa.db.model.ProductDTO;
import co.empathy.kataqa.log.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Index {

    private static final String VALID_TYPE_COUCH = "cough";
    private static final String VALID_TYPE_SEAT = "seat";
    private static final String VALID_SIZE_S = "s";
    private static final String VALID_SIZE_M = "m";
    private static final String VALID_SIZE_L = "l";


    private static final Logger logger = Logger.getInstance();

    public Database initialize(String path) throws IOException {
        logger.log("initialize " + path);

        Database db = new Database();
        List<ProductDTO> productDTOS = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> values = new ArrayList<>(List.of(line.split(",")));
                while (values.size() != 4) {
                    values.add("");
                }
                productDTOS.add(new ProductDTO(-1, values.get(0).toLowerCase(), values.get(1).toLowerCase(), values.get(2).toLowerCase(),
                    values.get(3).toLowerCase()));
            }
        }
        int j = 0;
        int i = 0;
        for (ProductDTO p : productDTOS) {
            logger.log(p.toString());
            String valid = validate(p, j);
            if (valid.equals("OK")) {
                if (!db.getNames().contains(p.getName())) {
                    p.setId(i);
                    db.addProduct(p);
                    i++;
                } else {
                    logger.log("Name " + p.getName() + " already inside the database in iteration " + j);
                }
            } else {
                logger.log(valid);
            }
            j++;
        }

        return db;
    }

    public static void index(Database db, String path) throws IOException {
        logger.log("index " + path);

        List<ProductDTO> productDTOS = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> values = new ArrayList<>(List.of(line.split(",")));
                while (values.size() != 4) {
                    values.add("");
                }
                productDTOS.add(new ProductDTO(-1, values.get(0).toLowerCase(), values.get(1).toLowerCase(), values.get(2).toLowerCase(),
                    values.get(3).toLowerCase()));
            }
        }

        int j = 0;
        int i = 0;
        for (ProductDTO p : db.getProducts()) {
            if (i == p.getId()) {
                i++;
            } else {
                break;
            }
        }

        for (ProductDTO p : productDTOS) {
            logger.log(p.toString());
            String valid = validate(p, j);
            if (valid.equals("OK")) {
                if (!db.getNames().contains(p.getName())) {
                    p.setId(i);
                    db.addProduct(p);
                    i++;
                } else {
                    for (ProductDTO pDb : db.getProducts()) {
                        if (pDb.getName().equals(p.getName())) {
                            p.setId(pDb.getId());
                            db.addProduct(p);
                        }
                    }
                }
            } else {
                logger.log(valid);
            }
            j++;
        }
    }

    public static String validate(ProductDTO productDTO, int i) {
        if (productDTO.getName().isEmpty() || productDTO.getName().isBlank()) {
            return "Missing Name in iteration " + i;
        }
        if (productDTO.getType().isEmpty() || productDTO.getType().isBlank()) {
            return "Missing Type in iteration " + i;
        }
        if ((productDTO.getType().equals(VALID_TYPE_SEAT) || productDTO.getType().equals(VALID_TYPE_COUCH)) && (productDTO.getColor().isEmpty()
            || productDTO.getColor().isBlank())) {
            return "Missing Color with Type " + productDTO.getType() + "in iteration " + i;
        }
        if (!productDTO.getSize().equals(VALID_SIZE_S) && !productDTO.getSize().equals(VALID_SIZE_M) && !productDTO.getSize().equals(VALID_SIZE_L)) {
            productDTO.setSize(VALID_SIZE_M);
        }
        return "OK";
    }
}
