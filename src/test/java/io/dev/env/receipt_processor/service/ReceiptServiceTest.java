package io.dev.env.receipt_processor.service;

import io.dev.env.receipt_processor.exception.ReceiptNotFoundException;
import io.dev.env.receipt_processor.model.Item;
import io.dev.env.receipt_processor.model.Receipt;
import io.dev.env.receipt_processor.model.ReceiptResponse;
import io.dev.env.receipt_processor.model.PointsResponse;
import io.dev.env.receipt_processor.repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    private Receipt testReceipt;
    private UUID receiptId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        receiptId = UUID.randomUUID();
        testReceipt = new Receipt();
        testReceipt.setId(receiptId);
        testReceipt.setRetailer("Target");
        testReceipt.setPurchaseDate("2022-01-01");
        testReceipt.setPurchaseTime("13:01");
        testReceipt.setTotal("35.35");

        Item item1 = new Item("Mountain Dew 12PK", "6.49");
        Item item2 = new Item("Emils Cheese Pizza", "12.25");
        testReceipt.setItems(List.of(item1, item2));
    }

    @Test
    void processReceipt_ShouldReturnValidReceiptResponse() {
        when(receiptRepository.save(any(Receipt.class))).thenReturn(testReceipt);

        ReceiptResponse response = receiptService.processReceipt(testReceipt);

        assertNotNull(response);
        assertEquals(receiptId, response.getId());
        verify(receiptRepository, times(1)).save(testReceipt);
    }

    @Test
    void getPoints_ValidReceipt_ShouldReturnPoints() {
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(testReceipt));

        PointsResponse response = receiptService.getPoints(receiptId);

        assertNotNull(response);
        assertTrue(response.getPoints() > 0);
        verify(receiptRepository, times(1)).findById(receiptId);
    }

    @Test
    void getPoints_InvalidReceipt_ShouldThrowException() {
        UUID invalidId = UUID.randomUUID();
        when(receiptRepository.findById(invalidId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ReceiptNotFoundException.class, () -> {
            receiptService.getPoints(invalidId);
        });

        assertEquals("No receipt found for that ID: " + invalidId, exception.getMessage());
        verify(receiptRepository, times(1)).findById(invalidId);
    }
}