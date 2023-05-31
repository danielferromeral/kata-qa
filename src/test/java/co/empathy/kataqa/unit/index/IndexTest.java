package co.empathy.kataqa.unit.index;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

import co.empathy.kataqa.db.model.ProductDTO;
import co.empathy.kataqa.index.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndexTest {

    private Index index;
    private ProductDTO productDTO;

    private final String VALID = "OK";

    @BeforeEach
    void setup() {
        index = new Index();
    }

    @Test
    void initialize() {
    }

    @Test
    void index() {
    }

    @Test
    void validate_null_is_not_valid() {
        assertThat(Index.validate(productDTO, 0), is(not(VALID)));
    }

    @Test
    void validate_valid_productDto_returns_OK() {
        productDTO = new ProductDTO(1, "name", "type", "color", "s");
        assertThat(Index.validate(productDTO, 0), is(VALID));
    }
}
