package io.dev.env.receipt_processor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dev.env.receipt_processor.exception.ReceiptNotFoundException;
import io.dev.env.receipt_processor.model.Item;
import io.dev.env.receipt_processor.model.PointsResponse;
import io.dev.env.receipt_processor.model.Receipt;
import io.dev.env.receipt_processor.model.ReceiptResponse;
import io.dev.env.receipt_processor.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReceiptController.class)
class ReceiptControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReceiptService receiptService;

    private Receipt validReceipt;
    private UUID receiptId;

    @BeforeEach
    void setUp() {
        receiptId = UUID.randomUUID();
        validReceipt = new Receipt();
        validReceipt.setId(receiptId);
        validReceipt.setRetailer("Target");
        validReceipt.setPurchaseDate("2022-01-01");
        validReceipt.setPurchaseTime("13:01");
        validReceipt.setTotal("35.35");

        Item item1 = new Item("Mountain Dew 12PK", "6.49");
        Item item2 = new Item("Emils Cheese Pizza", "12.25");
        validReceipt.setItems(List.of(item1, item2));

        // Mock service responses
        when(receiptService.processReceipt(any(Receipt.class))).thenReturn(new ReceiptResponse(receiptId));
        when(receiptService.getPoints(receiptId)).thenReturn(new PointsResponse(32));
    }

    @Test
    void processReceipt_ValidRequest_ShouldReturn200() throws Exception {
        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validReceipt)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void processReceipt_InvalidRequest_ShouldReturn400() throws Exception {
        Receipt invalidReceipt = new Receipt(); // Missing required fields

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReceipt)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPoints_ValidReceipt_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/receipts/" + receiptId + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(32));
    }

    @Test
    void getPoints_InvalidReceipt_ShouldReturn404() throws Exception {
        UUID invalidId = UUID.randomUUID();
        when(receiptService.getPoints(invalidId)).thenThrow(new ReceiptNotFoundException("No receipt found for that ID."));

        mockMvc.perform(get("/receipts/" + invalidId + "/points"))
                .andExpect(status().isNotFound());
    }
}