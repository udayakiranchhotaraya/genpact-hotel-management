package com.restapiproject.hotelmanagementsystem.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import com.restapiproject.hotelmanagementsystem.models.Hotel;
import com.restapiproject.hotelmanagementsystem.utils.HotelRowMapper;

public class HotelDAOImplTest {

	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	private HotelDAOImpl hotelDAOImpl;
	
	private Hotel hotel1;
	private Hotel hotel2;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		
		hotel1 = new Hotel(1L, "Hotel A", "Address A", 10, 5, new BigDecimal("100.0"));
		hotel2 = new Hotel(2L, "Hotel B", "Address B", 20, 10, new BigDecimal("200.0"));
	}
	
	@Test
	void testSave() {
		doAnswer(invocation -> {
			KeyHolder keyHolder = invocation.getArgument(1);
			keyHolder.getKeyList().add(Map.of("GENERATED_KEY", 1L));
			return 1;
		}).when(jdbcTemplate).update(any(), any(KeyHolder.class));
		
		Hotel result = hotelDAOImpl.save(hotel1);
		assertNotNull(result);
	}
	
	@Test
	void testFindById_Found() {
		when(jdbcTemplate.query(anyString(), any(HotelRowMapper.class), eq(1L)))
			.thenReturn(Arrays.asList(hotel1));
		Optional<Hotel> result = hotelDAOImpl.findById(1L);
		assertTrue(result.isPresent());
		assertEquals("Hotel A", result.get().getName());
	}
	
	@Test
	void testFindById_NotFound() {
		when(jdbcTemplate.query(anyString(), any(HotelRowMapper.class), eq(3L)))
			.thenReturn(Arrays.asList(hotel1));
		Optional<Hotel> result = hotelDAOImpl.findById(31L);
		assertFalse(result.isPresent());
	}
	
	@Test
	void testFindAll() {
		when(jdbcTemplate.query(anyString(), any(HotelRowMapper.class)))
			.thenReturn(Arrays.asList(hotel1, hotel2));
		List<Hotel> result = hotelDAOImpl.findAll();
		assertEquals(2, result.size());
	}
	
	@Test
	void testUpdate() {
		when (jdbcTemplate.update(anyString(),
				anyString(),anyString(),anyInt(),anyInt(),any(BigDecimal.class),anyLong()))
				.thenReturn(1);
		int rows = hotelDAOImpl.update(hotel1);
		assertEquals(1, rows);
	}
	
	@Test
	void testDelete() {
		when (jdbcTemplate.update(anyString(),
				anyString(),anyString(),anyInt(),anyInt(),any(BigDecimal.class),anyLong()))
		.thenReturn(1);
		int rows = hotelDAOImpl.update(hotel1);
		assertEquals(1, rows);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"Hotel X","Hotel Y","Hotel Z"})
	void testParameterizedHotelNames(String name) {
		Hotel hotel=new Hotel();
		hotel.setName(name);
		assertNotNull(hotel.getName());
		assertTrue(hotel.getName().startsWith("Hotel"));
	}
	
	@Disabled("Example skipped Dao test")
	@Test
	void testDiableExample() {
		fail("This DAO test is disabled and skipped");
	}
}
