package fi.plasmonics.inventory.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fi.plasmonics.inventory.InventoryTestUtil;
import fi.plasmonics.inventory.advice.RestResponseEntityExceptionHandler;
import fi.plasmonics.inventory.entity.Item;
import fi.plasmonics.inventory.services.ItemOrderService;
import fi.plasmonics.inventory.services.ItemService;
import lombok.extern.slf4j.Slf4j;

@WebMvcTest(ItemsController.class)
@Slf4j
@ContextConfiguration(classes = {ItemsController.class})
@ComponentScan({"fi.plasmonics.inventory.controllers"})
public class ItemsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @MockBean
    ItemService itemService;


    @MockBean ItemOrderService itemOrderService;

    @Autowired ObjectMapper jackson2ObjectMapper;

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    Logger errorLogger = (Logger) LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @BeforeEach
    void setup() {
        listAppender.start();
        errorLogger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        listAppender.start();
        errorLogger.detachAppender(listAppender);
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        HttpMessageConverter<?> mappingJackson2HttpMessageConverter = Arrays.stream(converters)
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

        assertNotNull(mappingJackson2HttpMessageConverter, "the JSON message converter must not be null");
    }

    @Test
    void createItem() throws Exception {
        Item item = InventoryTestUtil.createItem();
        when(itemService.save(item)).thenReturn(any(Item.class));

        mvc.perform(MockMvcRequestBuilders.post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(item)))
            .andExpect(status().isCreated());

        Mockito.verify(itemService, only()).save(any(Item.class));
    }

    @Test
    void getItems() throws Exception {
        List<Item> items = List.of(InventoryTestUtil.createItem());
        when(itemService.getItems()).thenReturn(items);

        mvc.perform(MockMvcRequestBuilders.get("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(items)))
            .andExpect(status().isOk());

        Mockito.verify(itemService, only()).getItems();
    }






    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        String result = mockHttpOutputMessage.getBodyAsString();
        log.info("Json string is : " + result);
        return result;
    }


}
