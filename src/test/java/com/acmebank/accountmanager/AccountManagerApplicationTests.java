package com.acmebank.accountmanager;

import com.acmebank.accountmanager.service.TransactionService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountManagerApplicationTests {

	@Autowired
	TransactionService transactionService;

	@Test
	@DisplayName("Get Balance")
	@Order(1)
	void testGetBalance() throws Exception{
		BigDecimal balance = transactionService.getBalance(1L);
		assertEquals(new BigDecimal(500.0).stripTrailingZeros(), balance.stripTrailingZeros());
	}

	@DisplayName("Get Balance - User not found")
	@Test
	@Order(2)
	void testGetBalanceCustomerNotFound() throws Exception{
		Exception exception = assertThrows(Exception.class, () -> {
			transactionService.getBalance(5L);
		});
		String expectedMessage = "Customer(" +5L+")  Not Found";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage,actualMessage);
	}
	@DisplayName("Transfer")
	@Test
	@Order(3)
	void testTransfer() throws Exception{
		BigDecimal accountAOriginal = transactionService.getBalance(88888888L);
		BigDecimal accountBOriginal = transactionService.getBalance(12345678L);
		BigDecimal amount = new BigDecimal(50);
		assertTrue(transactionService.transfer(88888888L,12345678L,amount));
		assertEquals(accountAOriginal.subtract(amount),transactionService.getBalance(88888888L));
		assertEquals(accountBOriginal.add(amount),transactionService.getBalance(12345678L));
	}

	@DisplayName("Transfer - Insufficient balance")
	@Test
	@Order(4)
	void testTransferInsufficientBalance() throws Exception{
		Exception exception = assertThrows(Exception.class, () -> {
			transactionService.transfer(2L,1L,new BigDecimal(1000));
		});
		String expectedMessage = "Insufficient Balance";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage,actualMessage);
	}
	@DisplayName("Transfer - Target Account not exist")
	@Test
	@Order(5)
	void testTransferTargetAccountNotFound() throws Exception{
		Exception exception = assertThrows(Exception.class, () -> {
			transactionService.transfer(1L,5L,new BigDecimal(50));
		});
		String expectedMessage = "Customer(" +5L+")  Not Found";
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage,actualMessage);
	}


}
