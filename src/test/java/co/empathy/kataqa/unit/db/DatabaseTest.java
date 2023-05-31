package co.empathy.kataqa.unit.db;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

import co.empathy.kataqa.db.Database;
import co.empathy.kataqa.db.model.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DatabaseTest {

    private static Database db;

    @BeforeEach
    void setUp() {
        db = new Database();
    }

    @Test
    void getProducts_is_empty_by_default() {
        assertThat(db.getProducts(), is(emptyList()));
    }

    @Test
    void getNames_is_empty_by_default() {
        assertThat(db.getNames(), is(emptyList()));
    }

    @ParameterizedTest(name = "Index value {0}")
    @ValueSource(ints = {-1, 0, 1})
    void getProducts_null_for_non_existing_product(int index) {
        assertThat(db.getProduct(index), is(nullValue()));
    }

    @ParameterizedTest(name = "Index value {0}")
    @ValueSource(ints = {-1, 0, 1})
    void deleteProduct_that_no_exist_does_not_throw(int index) {
        db.deleteProduct(index);
    }

    @Test
    void addProduct_does_not_accept_null() {
        db.addProduct(null);
        assertThat(db.getProducts(), is(emptyList()));
    }


    @Nested
    @DisplayName("addProduct adds a new productDto")
    class addProduct {

        private ProductDTO productDTO = new ProductDTO(1, "foo", "bar", "fog", "bam");

        @BeforeEach
        void addProduct() {
            db.addProduct(productDTO);
        }

        @Test
        void addProduct_adds_a_new_productDto() {
            assertThat(db.getProducts(), is(not(emptyList())));
            assertThat(db.getProduct(productDTO.getId()), is(equalTo(productDTO)));
        }

        @Test
        void deleteProduct_deletes_the_productDto() {
            db.deleteProduct(productDTO.getId());
            assertThat(db.getProducts(), is(emptyList()));
            assertThat(db.getProduct(productDTO.getId()), is(nullValue()));
        }

        @Test
        void getNames_retrieves_all_names() {
            assertThat(db.getNames(), hasItem(productDTO.getName()));
            ProductDTO productDTO2 = new ProductDTO(2, "car", "cor", "cir", "cur");
            db.addProduct(productDTO2);
            assertThat(db.getNames(), hasItem(productDTO2.getName()));
        }

        @Test
        void getNames_does_not_retrieves_delete_products() {
            db.deleteProduct(productDTO.getId());
            assertThat(db.getNames(), not(hasItem(productDTO.getName())));
        }

        @Test
        void a() {

        }


    }
}
