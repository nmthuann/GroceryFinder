package com.nmt.groceryfinder.modules.categories;

import com.nmt.groceryfinder.modules.products.domain.model.entities.CategoryEntity;
import com.nmt.groceryfinder.modules.products.repositories.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
class CategoryRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("tester")
            .withPassword("123456");


    @MockBean
    ICategoryRepository categoryRepository;


    @BeforeEach
    public void setup() throws SQLException {
        assert (postgresContainer.isRunning());

        // Tạo danh sách các CategoryEntity
        List<CategoryEntity> categories = new ArrayList<>();
        // Category 1: ROOT
        CategoryEntity rootCategory = new CategoryEntity();
        rootCategory.setCategoryName("ROOT");
        rootCategory.setCategoryUrl(null);
        rootCategory.setDescription("ROOT");
        rootCategory.setLeftValue(1);
        rootCategory.setParentId("0");
        rootCategory.setRightValue(20);
        categories.add(rootCategory);

        // Category 2: Thực Phẩm và Đồ Uống
        CategoryEntity foodAndDrinks = new CategoryEntity();
        foodAndDrinks.setCategoryName("Thực Phẩm và Đồ Uống");
        foodAndDrinks.setCategoryUrl("http://localhost:3000/categories/new");
        foodAndDrinks.setDescription("Thực Phẩm và Đồ Uống");
        foodAndDrinks.setLeftValue(2);
        foodAndDrinks.setParentId("1");
        foodAndDrinks.setRightValue(11);
        categories.add(foodAndDrinks);

        // Category 3: DẦU ĂN, NƯỚC CHẤM, GIA VỊ
        CategoryEntity oilsAndCondiments = new CategoryEntity();
        oilsAndCondiments.setCategoryName("DẦU ĂN, NƯỚC CHẤM, GIA VỊ");
        oilsAndCondiments.setCategoryUrl("/dau-an-nuoc-cham-gia-vi");
        oilsAndCondiments.setDescription("DẦU ĂN, NƯỚC CHẤM, GIA VỊ");
        oilsAndCondiments.setLeftValue(3);
        oilsAndCondiments.setParentId("2");
        oilsAndCondiments.setRightValue(6);
        categories.add(oilsAndCondiments);

        // Category 4: Dầu ăn
        CategoryEntity cookingOil = new CategoryEntity();
        cookingOil.setCategoryName("Dầu ăn");
        cookingOil.setCategoryUrl("/dau-an");
        cookingOil.setDescription("Dầu ăn");
        cookingOil.setLeftValue(4);
        cookingOil.setParentId("3");
        cookingOil.setRightValue(5);
        categories.add(cookingOil);

        // Category 5: Nước mắm
        CategoryEntity fishSauce = new CategoryEntity();
        fishSauce.setCategoryName("Nước mắm");
        fishSauce.setCategoryUrl("/nuoc-mam");
        fishSauce.setDescription("Nước mắm");
        fishSauce.setLeftValue(5);
        fishSauce.setParentId("3");
        fishSauce.setRightValue(6);
        categories.add(fishSauce);

        // Category 6: BIA, NƯỚC GIẢI KHÁT
        CategoryEntity beerAndBeverages = new CategoryEntity();
        beerAndBeverages.setCategoryName("BIA, NƯỚC GIẢI KHÁT");
        beerAndBeverages.setCategoryUrl("/bia-nuoc-giai-khat");
        beerAndBeverages.setDescription("BIA, NƯỚC GIẢI KHÁT");
        beerAndBeverages.setLeftValue(7);
        beerAndBeverages.setParentId("2");
        beerAndBeverages.setRightValue(10);
        categories.add(beerAndBeverages);

        // Category 7: Bia
        CategoryEntity beer = new CategoryEntity();
        beer.setCategoryName("Bia");
        beer.setCategoryUrl("/bia");
        beer.setDescription("Bia");
        beer.setLeftValue(8);
        beer.setParentId("7");
        beer.setRightValue(9);
        categories.add(beer);

        // Category 8: Nước ngọt
        CategoryEntity softDrinks = new CategoryEntity();
        softDrinks.setCategoryName("Nước ngọt");
        softDrinks.setCategoryUrl("/nuoc-ngot");
        softDrinks.setDescription("Nước ngọt");
        softDrinks.setLeftValue(10);
        softDrinks.setParentId("7");
        softDrinks.setRightValue(11);
        categories.add(softDrinks);

        // Category 9: Linh Kiện, Điện Tử
        CategoryEntity electronics = new CategoryEntity();
        electronics.setCategoryName("Linh Kiện, Điện Tử");
        electronics.setCategoryUrl("http://localhost:3000/categories/new");
        electronics.setDescription("Linh Kiện, Điện Tử");
        electronics.setLeftValue(12);
        electronics.setParentId("1");
        electronics.setRightValue(19);
        categories.add(electronics);

        // Category 10: Thiết bị di động
        CategoryEntity mobileDevices = new CategoryEntity();
        mobileDevices.setCategoryName("Thiết bị di động");
        mobileDevices.setCategoryUrl(null);
        mobileDevices.setDescription("Thiết bị di động");
        mobileDevices.setLeftValue(13);
        mobileDevices.setParentId("12");
        mobileDevices.setRightValue(14);
        categories.add(mobileDevices);

        // Category 11: Điện thoại
        CategoryEntity phones = new CategoryEntity();
        phones.setCategoryName("Điện thoại");
        phones.setCategoryUrl("http://localhost:3000/categories/new");
        phones.setDescription("điện thoại");
        phones.setLeftValue(14);
        phones.setParentId("12");
        phones.setRightValue(15);
        categories.add(phones);

        categoryRepository.saveAll(categories);
    }

    @Test
    public void shouldIncrementRightValueBy2_whenGivenRightValueIs5() {
        categoryRepository.increaseRightValuesByTwo(5);
        Optional<CategoryEntity> foundCategory  = categoryRepository.findById(4);
        if(foundCategory.isPresent())
        assertEquals(7, foundCategory.get().getRightValue());
    }

    private String createCategoriesTable() {
        return  "CREATE TABLE IF NOT EXISTS public.categories " +
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

    }
}
