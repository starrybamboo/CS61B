package flik;
import static org.junit.Assert.*;
import org.junit.Test;

public class MyTest {
    @Test
    public void insert128(){
        int i = 128;
        int j = 128;
        assertFalse(!Flik.isSameNumber(i, j));
    }
}
