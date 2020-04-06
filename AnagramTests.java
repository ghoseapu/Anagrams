import org.junit.Test;
import static org.junit.Assert.*;

public class AnagramTests {

    @Test
    public void AnagramUnitTest() {
        ApuAnagram tester = new ApuAnagram();
		boolean val = true;
		assertEquals(val, tester.areAnagrams("cinema", "iceman"));
        assertTrue(tester.areAnagrams("state", "taste"));
		assertEquals(val, tester.areAnagrams("cinema", "icomon"));
    }
}