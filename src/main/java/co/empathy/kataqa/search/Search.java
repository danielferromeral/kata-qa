package co.empathy.kataqa.search;

import co.empathy.kataqa.db.Database;
import co.empathy.kataqa.db.model.ProductDTO;
import co.empathy.kataqa.index.Index;
import co.empathy.kataqa.log.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search {

    private Database db;

    private static final Logger logger = Logger.getInstance();


    public Search(Database db) {
        this.db = db;
    }

    public List<ProductDTO> search(String... input) {
        StringBuilder log = new StringBuilder("search");
        for (String s : input) {
            log.append(" ").append(s);
        }
        logger.log(log.toString());
        List<ProductDTO> ret = db.getProducts();
        if (input.length == 1) {
            return ret;
        }
        return filter(ret, input[1], input[2], input[3]);
    }

    protected List<ProductDTO> filter(List<ProductDTO> list, String field, String operator, String value) {
        List<ProductDTO> ret = new ArrayList<>();
        for (ProductDTO p : list) {
            switch (field) {
                case "name":
                    if (operator.equals("=")) {
                        if (p.getName().contains(value)) {
                            ret.add(p);
                        }
                    } else if (operator.equals("!=")) {
                        if (!p.getName().contains(value)) {
                            ret.add(p);
                        }
                    }
                    break;
                case "type":
                    if (operator.equals("=")) {
                        if (p.getType().equals(value)) {
                            ret.add(p);
                        }
                    } else if (operator.equals("!=")) {
                        if (!p.getType().equals(value)) {
                            ret.add(p);
                        }
                    }
                    break;
                case "color":
                    if (operator.equals("=")) {
                        if (p.getColor().equals(value)) {
                            ret.add(p);
                        }
                    } else if (operator.equals("!=")) {
                        if (!p.getColor().equals(value)) {
                            ret.add(p);
                        }
                    }
                    break;
                case "size":
                    if (operator.equals("=")) {
                        if (p.getSize().equals(value)) {
                            ret.add(p);
                        }
                    } else if (operator.equals("!=")) {
                        if (!p.getSize().equals(value)) {
                            ret.add(p);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return ret;
    }

    public ProductDTO skusearch(String id) {
        logger.log("skusearch " + id);
        return db.getProduct(Integer.parseInt(id));
    }

    public void delete(String id) {
        logger.log("skusearch " + id);
        db.deleteProduct(Integer.parseInt(id));
    }

    public void update(String path) throws IOException {
        logger.log("upodate " + path);
        Index.index(db, path);
    }

    public void save(String path) throws IOException {
        logger.log("save " + path);
        new File(path).createNewFile();
        FileWriter myWriter = new FileWriter(path);
        for (ProductDTO p : db.getProducts()) {
            myWriter.write(p.toCSV() + "\n");
        }
        myWriter.close();
    }

    public void exit(String path) throws IOException {
        logger.log("exit " + path);
        new File(path).createNewFile();
        FileWriter myWriter = new FileWriter(path);
        for (String s : logger.getList()) {
            myWriter.write(s + "\n");
        }
        myWriter.close();
    }

}
