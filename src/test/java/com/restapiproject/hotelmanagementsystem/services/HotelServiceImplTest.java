package com.restapiproject.hotelmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;

import com.restapiproject.hotelmanagementsystem.exceptions.ResourceNotFoundException;
import com.restapiproject.hotelmanagementsystem.models.Hotel;
import com.restapiproject.hotelmanagementsystem.repository.HotelDAO;

public class HotelServiceImplTest {

	@Mock
	private HotelDAO hotelDAO;
	
	@InjectMocks
	private HotelService hotelService;
	
	private Hotel hotel1;
	private Hotel hotel2;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		
		hotel1 = new Hotel(1L, "Hotel A", "Address A", 10, 5, new BigDecimal("100.0"));
		hotel2 = new Hotel(2L, "Hotel B", "Address B", 20, 10, new BigDecimal("200.0"));
	}
	
	@Test
	void testCreateHotel() {
		when(hotelDAO.save(hotel1)).thenReturn(hotel1);
		Hotel created = hotelService.addHotel(hotel1);
		assertNotNull(created);
		assertEquals("Hotel A", created.getName());
		verify(hotelDAO, times(1)).save(hotel1);
	}
	
	@Test
	void testGetHotelById_Found() {
		when(hotelDAO.findById(1L)).thenReturn(Optional.of(hotel1));
		Hotel found = hotelService.findHotelById(1L);
		assertNotNull(found);
		assertEquals("Hotel A", found.getName());
		verify(hotelDAO, times(1)).findById(1L);
	}
	
	@Test
	void testGetHotelById_NotFound() {
	    when(hotelDAO.findById(3L)).thenReturn(Optional.empty());
	    assertThrows(ResourceNotFoundException.class, () -> hotelService.findHotelById(3L));
	    verify(hotelDAO, times(1)).findById(3L);
	}
	
	@Test
	void testGetAllHotels() {
		when(hotelDAO.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));
		List<Hotel> hotels = hotelService.listAllHotels();
		assertEquals(2, hotels.size());
		verify(hotelDAO, times(1)).findAll();
	}
	
	@Test
	void testUpdateHotel_Success() {
		when(hotelDAO.findById(1L)).thenReturn(Optional.of(hotel1));
		when(hotelDAO.update(any(Hotel.class))).thenReturn(1);
		Hotel updateHotel = new Hotel(null, "Hotel A updated", "Address updated", 15, 8, new BigDecimal("150.00"));
		Hotel result = hotelService.updateHotel(1L, updateHotel);
		assertAll("Update hotel properties",
				() -> assertEquals("Hotel A updated", result.getName()),
				() -> assertEquals("Address updated", result.getAddress()),
				() -> assertEquals(15, result.getTotalRooms()),
				() -> assertEquals(8, result.getAvailableRooms()),
				() -> assertEquals(new BigDecimal("150.00"), result.getPricePerNight())
				);
		verify(hotelDAO, times(1)).update(any(Hotel.class));
	}
	
	@Test
	void testUpdateHotel_Failure() {
		when(hotelDAO.findById(1L)).thenReturn(Optional.of(hotel1));
		when(hotelDAO.update(any(Hotel.class))).thenReturn(0);
		Hotel updateHotel = new Hotel(null, "Hotel A updated", "Address updated", 15, 8, new BigDecimal("150.00"));
		RuntimeException exception = assertThrows(RuntimeException.class, () -> hotelService.updateHotel(1L, updateHotel));
		assertEquals("Update failed for Hotel ID: 1", exception.getMessage());
		verify(hotelDAO, times(1)).update(any(Hotel.class));
	}

	@Test
	void testDeleteHotel_Success() {
		when(hotelDAO.findById(1L)).thenReturn(Optional.of(hotel1));
		when(hotelDAO.deleteById(1L)).thenReturn(1);
		assertDoesNotThrow( () -> hotelService.deleteHotel(1L));
		verify(hotelDAO, times(1)).deleteById(1L);
	}
	
	@Test
	void testDeleteHotel_Failure() {
	    when(hotelDAO.findById(1L)).thenReturn(Optional.of(hotel1));
	    when(hotelDAO.deleteById(1L)).thenReturn(0);
	    RuntimeException exception = assertThrows(RuntimeException.class, () -> hotelService.deleteHotel(1L));
	    assertEquals("Delete failed for Hotel ID: 1", exception.getMessage());
	    verify(hotelDAO, times(1)).deleteById(1L);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Hotel A", "Hotel B", "Hotel C"})
	void testParameterizedHotelNames(String name) {
		Hotel hotel = new Hotel();
		hotel.setName(name);
		assertNotNull(hotel.getName());
		assertTrue(hotel.getName().startsWith("Hotel"));
	}
}
