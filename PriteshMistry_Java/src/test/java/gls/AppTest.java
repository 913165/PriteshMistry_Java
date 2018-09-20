package gls;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.abcbank.gl.EndOfDayProcessor;

/**
 * Unit test for simple App.
 */

public class AppTest {
	static EndOfDayProcessor processor;

	@BeforeClass
	public static void setUpClass() throws Exception {
		processor = new EndOfDayProcessor();
		String positionPath = "D://gls//Input_StartOfDay_Positions.txt";
		String transactionPath = "D://gls//Input_Transactions.txt";
		String endOfDayPositionPath = "D://gls//out.txt";
		processor.process(positionPath, transactionPath, endOfDayPositionPath);
	}

	@Before
	public void setUp() throws Exception {
		// Code executed before each test
	}

	@Test
	public void testEndOfPDayPositions() throws IOException, ParseException, org.json.simple.parser.ParseException {
		List<String> positions = checkOutputFile();
		assertTrue("IBM,101,E,101000,1000".equals(positions.get(0)));
		assertTrue("IBM,201,I,-101000,-1000".equals(positions.get(1)));
		assertTrue("MSFT,101,E,4999750,-250".equals(positions.get(2)));
		assertTrue("MSFT,201,I,-4999750,250".equals(positions.get(3)));
		assertTrue("APPL,101,E,-1100,-11100".equals(positions.get(4)));
		assertTrue("APPL,201,I,1100,11100".equals(positions.get(5)));
		assertTrue("AMZN,101,E,-24850,-14850".equals(positions.get(6)));
		assertTrue("AMZN,201,I,24850,14850".equals(positions.get(7)));
		assertTrue("NFLX,101,E,100000000,0".equals(positions.get(8)));
	    assertTrue("NFLX,201,I,-100000000,0".equals(positions.get(9)));

	}

	@After
	public void tearDown() throws Exception {
		// Code executed after each test
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		// Code executed after the last test method
	}

	private static List<String> checkOutputFile() throws IOException {
		return Files.lines(Paths.get("D://gls//out.txt")).skip(1)
				.collect(Collectors.toList());
	}
}
