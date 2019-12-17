package top.cyixlq.example;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String moduleName = "_app";
        moduleName = moduleName.replaceAll("[^0-9a-zA-Z_]+", "");
        System.out.println(moduleName);
        assertEquals(4, 2 + 2);
    }
}