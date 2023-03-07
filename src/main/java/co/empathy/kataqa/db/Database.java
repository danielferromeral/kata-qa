package co.empathy.kataqa.db;

import co.empathy.kataqa.db.model.ProductDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    private final HashMap<Integer, ProductDTO> products = new HashMap<>();

    public List<ProductDTO> getProducts() {
        return new ArrayList<>(products.values());
    }

    public List<String> getNames() {
        List<String> ret = new ArrayList<>();
        for (ProductDTO p : products.values()) {
            ret.add(p.getName());
        }
        return ret;
    }

    public ProductDTO getProduct(int index) {
        return products.get(index);
    }

    public void addProduct(ProductDTO productDTO) {
        products.put(productDTO.getId(), productDTO);
    }

    public void deleteProduct(int id) {
        products.remove(id);
    }

}
