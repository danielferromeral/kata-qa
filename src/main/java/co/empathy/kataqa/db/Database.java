package co.empathy.kataqa.db;

import co.empathy.kataqa.db.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    private final HashMap<Integer, Product> products = new HashMap<>();

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public List<String> getNames() {
        List<String> ret = new ArrayList<>();
        for (Product p : products.values()) {
            ret.add(p.getName());
        }
        return ret;
    }

    public Product getProduct(int index) {
        return products.get(index);
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void deleteProduct(int id) {
        products.remove(id);
    }

}
