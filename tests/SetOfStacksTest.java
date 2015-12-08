import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by schandramouli on 9/28/15.
 */
public class SetOfStacksTest {
    SetOfStacks SOS;
    @Before
    public void setUp() throws Exception {
        SOS = new SetOfStacks();
        int i = 0;
        // check if pushing is done properly across stacks
        while (i < 21) {
            SOS.push(i);
            i++;
        }
    }
    @Test
    public void testPush() throws Exception {
        // assert that pushing 21 elements results in 3 mini stacks
        assertEquals(3, SOS.setOfStacks.size());
    }

    @Test
    public void testPop() throws Exception {
        // assert that popping one element reduces stack size by 1
        SOS.pop();
        assertEquals(2, SOS.setOfStacks.size());
        int i = 0;
        while (i < 20) {
            SOS.pop();
            i++;
        }
        // assert that popping 20 more elements results in empty stack
        assertEquals(0, SOS.setOfStacks.size());

        // assert that popping an element from empty stack returns -10000
        assertEquals(-10000, SOS.pop());
    }

    @Test
    public void testToString() throws Exception {
        String s = "";
        assertEquals(s.getClass(), SOS.toString().getClass());
    }

    @After
    public void tearDown() throws Exception {
        SetOfStacks.main(new String[]{});
    }
}