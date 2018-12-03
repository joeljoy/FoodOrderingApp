package org.upgrad.controllers;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.upgrad.models.Category;
import org.upgrad.services.CategoryService;
import org.upgrad.services.ItemService;
import org.upgrad.services.RestaurantService;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This class contains all the test cases regarding the category controller
@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void getAllCategories() throws Exception{
        Category category = new Category();
        category.setCategoryName("Italian");
        List<Category> categories = singletonList(category);
        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);
        String url = "/category";
        mvc.perform(get(url))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].categoryName", Matchers.is("Italian")));
    }

    @Test
    public void getCategoryByIncorrectName() throws Exception{
        Mockito.when(categoryService.getCategory("italian")).thenReturn(null);
        String url = "/category/italian";
        mvc.perform(get(url))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("No Category by this name!")));
    }

    @Test
    public void getCategoryByName() throws Exception{
        Category category = new Category();
        category.setCategoryName("Italian");
        Mockito.when(categoryService.getCategory("italian")).thenReturn(category);
        String url = "/category/italian";
        mvc.perform(get(url))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.categoryName", Matchers.is("Italian")));
    }

}
