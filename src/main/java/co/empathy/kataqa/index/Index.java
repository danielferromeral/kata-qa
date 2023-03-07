package co.empathy.kataqa.index;

import co.empathy.kataqa.db.Database;
import co.empathy.kataqa.db.model.Product;
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
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> values = new ArrayList<>(List.of(line.split(",")));
                while (values.size() != 4) {
                    values.add("");
                }
                products.add(new Product(-1, values.get(0).toLowerCase(), values.get(1).toLowerCase(), values.get(2).toLowerCase(),
                    values.get(3).toLowerCase()));
            }
        }
        int j = 0;
        int i = 0;
        for (Product p : products) {
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

        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> values = new ArrayList<>(List.of(line.split(",")));
                while (values.size() != 4) {
                    values.add("");
                }
                products.add(new Product(-1, values.get(0).toLowerCase(), values.get(1).toLowerCase(), values.get(2).toLowerCase(),
                    values.get(3).toLowerCase()));
            }
        }

        int j = 0;
        int i = 0;
        for (Product p : db.getProducts()) {
            if (i == p.getId()) {
                i++;
            } else {
                break;
            }
        }

        for (Product p : products) {
            logger.log(p.toString());
            String valid = validate(p, j);
            if (valid.equals("OK")) {
                if (!db.getNames().contains(p.getName())) {
                    p.setId(i);
                    db.addProduct(p);
                    i++;
                } else {
                    for (Product pDb : db.getProducts()) {
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

    protected static String validate(Product product, int i) {
        if (product.getName().isEmpty() || product.getName().isBlank()) {
            return "Missing Name in iteration " + i;
        }
        if (product.getType().isEmpty() || product.getType().isBlank()) {
            return "Missing Type in iteration " + i;
        }
        if ((product.getType().equals(VALID_TYPE_SEAT) || product.getType().equals(VALID_TYPE_COUCH)) && (product.getColor().isEmpty()
            || product.getColor().isBlank())) {
            return "Missing Color with Type " + product.getType() + "in iteration " + i;
        }
        if (!product.getSize().equals(VALID_SIZE_S) && !product.getSize().equals(VALID_SIZE_M) && !product.getSize().equals(VALID_SIZE_L)) {
            product.setSize(VALID_SIZE_M);
        }
        return "OK";
    }
}
