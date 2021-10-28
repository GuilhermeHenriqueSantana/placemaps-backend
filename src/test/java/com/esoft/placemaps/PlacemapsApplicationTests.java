package com.esoft.placemaps;

import com.esoft.placemaps.helpers.DocumentoHelperTeste;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlacemapsApplicationTests {

	@Test
	void contextLoads() {
		new DocumentoHelperTeste();
	}

}
