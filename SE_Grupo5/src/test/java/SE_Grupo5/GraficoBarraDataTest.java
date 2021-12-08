package SE_Grupo5;

import static org.junit.Assert.*;

import org.junit.Test;

public class GraficoBarraDataTest {

	@Test
	public void test() {
		GraficoBarraData data = new GraficoBarraData("x",10);
		assertEquals("x",data.getXData());
		assertEquals(10,data.getYData(),0.1);
		
		data.addYData(5);
		assertEquals(15,data.getYData(),0.1);
	}

}
