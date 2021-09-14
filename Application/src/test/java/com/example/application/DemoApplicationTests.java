package com.example.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

//@SpringBootTest
class DemoApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void addTwoNumbers() {
		// given
		int numbweOne = 20;
		int numbweTwo = 30;

		// when
		int result = underTest.add(numbweOne, numbweTwo);

		// then
		int expected = 50;
		assertThat(result).isEqualTo(expected);

	}

	class Calculator {
		public int add(int a, int b) {return a + b;}
	}
}
