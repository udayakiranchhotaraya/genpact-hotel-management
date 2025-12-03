package com.restapiproject.hotelmanagementsystem.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
 

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapiproject.hotelmanagementsystem.dtos.HotelDTO;
import com.restapiproject.hotelmanagementsystem.models.Hotel;
import com.restapiproject.hotelmanagementsystem.services.HotelService;

public class HotelControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private HotelService hotelService;
	
	@InjectMocks
	private HotelController hotelController;
	
	private Hotel hotel1;
	private Hotel hotel2;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
		
		hotel1 = new Hotel(1L, "Hotel A", "Address A", 10, 5, new BigDecimal("100.0"));
		hotel2 = new Hotel(2L, "Hotel B", "Address B", 20, 10, new BigDecimal("200.0"));
	}
	
	@Test
	void testGetAllHotels() throws Exception {
		when(hotelService.listAllHotels()).thenReturn(Arrays.asList(hotel1, hotel2));
		mockMvc.perform(get("/api/hotels"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(2))
			.andExpect(jsonPath("$[0].name").value("Hotel A"))
			.andExpect(jsonPath("$[1].name").value("Hotel B"));
		
		verify(hotelService, times(1)).listAllHotels();
	}
	
	@Test
	void testGetHotelById() throws Exception {
		when(hotelService.findHotelById(1L)).thenReturn(hotel1);
		mockMvc.perform(get("/api/hotels/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("Hotel A"))
		.andExpect(jsonPath("$.totalRooms").value(10));
		
		verify(hotelService, times(1)).findHotelById(1L);
	}
	
	@Test
	void testCreateHotel() throws Exception {
		HotelDTO hotelDTO = new HotelDTO(null, "Hotel C", "Address C", 15, 8, new BigDecimal("150.0"));
		Hotel createdHotel = new Hotel(3L, "Hotel C", "Address C", 15, 8, new BigDecimal("150.0"));
		
		when(hotelService.addHotel(any(Hotel.class))).thenReturn(createdHotel);
		
		mockMvc.perform(post("/api/hotels")
			    .contentType(MediaType.APPLICATION_JSON)
			    .content(objectMapper.writeValueAsString(hotelDTO)))
			    .andExpect(status().isCreated())
			    .andExpect(jsonPath("$.id").value(3L))
			    .andExpect(jsonPath("$.name").value("Hotel C"));
		
		verify(hotelService, times(1)).addHotel(any(Hotel.class));		
	}
	
	@Test
	void testUpdateHotel() throws Exception {
		HotelDTO hotelDTO = new HotelDTO(null, "Hotel A updated", "Address A updated", 12, 9, new BigDecimal("120.0"));
		Hotel updatedHotel = new Hotel(1L, "Hotel A updated", "Address A updated", 12, 9, new BigDecimal("120.0"));
		
		when(hotelService.updateHotel(eq(1L), any(Hotel.class))).thenReturn(updatedHotel);
		
		mockMvc.perform(put("/api/hotels/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(hotelDTO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name").value("Hotel A updated"))
		.andExpect(jsonPath("$.availableRooms").value(9));
		
		verify(hotelService, times(1)).updateHotel(eq(1L), any(Hotel.class));		
	}
	
	@Test
	void testDeleteHotel() throws Exception {
		doNothing().when(hotelService).deleteHotel(1L);
		
		mockMvc.perform(delete("/api/hotels/1"))
		.andExpect(status().isNoContent());
		verify(hotelService, times(1)).deleteHotel(1L);
	}
}
