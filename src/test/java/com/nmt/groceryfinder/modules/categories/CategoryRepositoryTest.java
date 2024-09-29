package com.nmt.groceryfinder.modules.categories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import com.nmt.groceryfinder.modules.products.repositories.ICategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 9/28/2024
 */
@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("tester")
            .withPassword("123456");


    @MockBean
    ICategoryRepository categoryRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        // Xóa dữ liệu trong bảng trước khi thêm dữ liệu mới
        jdbcTemplate.execute("DELETE FROM public.categories");
        // Tạo bảng categories nếu chưa tồn tại
        createCategoriesTable();
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('ROOT', NULL, 'ROOT', 1, '0', 20)");  // Nút gốc
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('Thực Phẩm và Đồ Uống', 'http://localhost:3000/categories/new', 'Thực Phẩm và Đồ Uống', 2, '1', 11)");  // Nút con 1
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('DẦU ĂN, NƯỚC CHẤM, GIA VỊ', '/dau-an-nuoc-cham-gia-vi', 'DẦU ĂN, NƯỚC CHẤM, GIA VỊ', 3, '2', 6)");  // Nút con 1.1
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('Dầu ăn', '/dau-an', 'Dầu ăn', 4, '3', 5)");  // Nút con 1.1.1
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('Nước mắm', '/nuoc-mam', 'Nước mắm', 5, '3', 6)");  // Nút con 1.1.2
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('BIA, NƯỚC GIẢI KHÁT', '/bia-nuoc-giai-khat', 'BIA, NƯỚC GIẢI KHÁT', 7, '2', 10)");  // Nút con 1.2
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('Bia', '/bia', 'Bia', 8, '7', 9)");  // Nút con 1.2.1
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('Nước ngọt', '/nuoc-ngot', 'Nước ngọt', 10, '7', 11)");  // Nút con 1.2.2
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('Linh Kiện, Điện Tử', 'http://localhost:3000/categories/new', 'Linh Kiện, Điện Tử', 12, '1', 19)");  // Nút con 2
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('Thiết bị di động', NULL, 'Thiết bị di động', 13, '12', 14)");  // Nút con 2.1
        jdbcTemplate.execute("INSERT INTO public.categories (category_name, category_url, description, left_value, parent_id, right_value) VALUES ('Điện thoại', 'http://localhost:3000/categories/new', 'điện thoại', 14, '12', 15)");  // Nút con 2.2
    }


    @Test
    @Transactional
    public void shouldIncrementRightValueBy2_whenGivenRightValueIs5() {
        // Arrange: Set up the mock to return a valid CategoryEntity when ID is 4
        CategoryEntity category = new CategoryEntity();
        category.setId(4);
        category.setCategoryName("Dầu ăn");
        category.setCategoryUrl("/dau-an");
        category.setDescription("Dầu ăn");
        category.setLeftValue(5);
        category.setParentId("3");
        category.setRightValue(5);

        Mockito.when(categoryRepository.findById(4)).thenReturn(Optional.of(category));

        // Act: Perform the operation that increments right values
        categoryRepository.increaseRightValuesByTwo(4);

        // Assert: Verify the right value has been incremented
        Optional<CategoryEntity> foundCategory  = categoryRepository.findById(4);
        assertEquals(7, category.getRightValue()); // Check if the value increased
    }

    private void createCategoriesTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS public.categories " +
                "( " +
                "id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY (INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1), " +
                "category_name character varying(255) COLLATE pg_catalog.\"default\" NOT NULL, " +
                "category_url character varying(255) COLLATE pg_catalog.\"default\", " +
                "description character varying(255) COLLATE pg_catalog.\"default\", " +
                "left_value integer NOT NULL, " +
                "parent_id character varying(255) COLLATE pg_catalog.\"default\" NOT NULL, " +
                "right_value integer NOT NULL, " +
                "CONSTRAINT categories_pkey PRIMARY KEY (id), " +
                "CONSTRAINT uk41g4n0emuvcm3qyf1f6cn43c0 UNIQUE (category_name) " +
                ") TABLESPACE pg_default;"; // Removed the OWNER clause

        jdbcTemplate.execute(createTableSQL);
    }
}
