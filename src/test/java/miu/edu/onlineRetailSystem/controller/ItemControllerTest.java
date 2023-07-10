package miu.edu.onlineRetailSystem.controller;

import miu.edu.onlineRetailSystem.contract.IndividualItemResponse;
import miu.edu.onlineRetailSystem.contract.ItemResponse;
import miu.edu.onlineRetailSystem.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ItemControllerTest {
    @Mock
    private ItemService itemService;
    @Mock
    private ItemResponse itemResponse;
    @Mock
    private IndividualItemResponse individualItemResponse;
    @Mock
    private Page<ItemResponse> itemResponsePage;
    @Mock
    private Pageable pageable;
    @InjectMocks
    private ItemController itemController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateIndividualItem() {
        when(itemService.save(itemResponse)).thenReturn(itemResponse);

        ResponseEntity<?> response = itemController.createIndividualItem(itemResponse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemResponse, response.getBody());

        verify(itemService, times(1)).save(itemResponse);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    void testAddSubItem() {
        int id = 1;

        itemController.addSubItem(id, itemResponse);

        verify(itemService, times(1)).addSubItem(id, itemResponse);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    void testFindAllItems() {
        when(itemService.findAll(pageable)).thenReturn(itemResponsePage);

        Page<ItemResponse> result = itemController.findAllItems(pageable);

        assertEquals(itemResponsePage, result);

        verify(itemService, times(1)).findAll(pageable);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    void testSearchItemByName() {
        String name = "test";
        List<ItemResponse> searchResults = Collections.singletonList(itemResponse);

        when(itemService.search(name)).thenReturn(searchResults);

        ResponseEntity<?> response = itemController.searchItemByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(searchResults, response.getBody());

        verify(itemService, times(1)).search(name);
        verifyNoMoreInteractions(itemService);
    }


    @Test
    void testFindItemByID() {
        int id = 1;

        when(itemService.findByID(id)).thenReturn(itemResponse);

        ResponseEntity<?> response = itemController.findItemByID(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemResponse, response.getBody());

        verify(itemService, times(1)).findByID(id);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    void testUpdateIndividualItem() {
        int id = 1;

        when(itemService.update(id, individualItemResponse)).thenReturn(itemResponse);

        ResponseEntity<?> response = itemController.updateIndividualItem(id, individualItemResponse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemResponse, response.getBody());

        verify(itemService, times(1)).update(id, individualItemResponse);
        verifyNoMoreInteractions(itemService);
    }

    @Test
    void testDeleteItemByID() {
        int id = 1;

        when(itemService.remove(id)).thenReturn(itemResponse);

        ResponseEntity<?> response = itemController.deleteItemByID(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemResponse, response.getBody());

        verify(itemService, times(1)).remove(id);
        verifyNoMoreInteractions(itemService);
    }
}
