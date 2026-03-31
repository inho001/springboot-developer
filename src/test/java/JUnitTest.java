import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {

    @Test
    @DisplayName("1+2=3")
    public void junitTest() {
        int n1 = 1;
        int n2 = 2;
        int sum = n1 + n2;

        Assertions.assertEquals(3, sum);
    }

    @Test
    @DisplayName("1+3은 4 이다")
    public void junitFailedTest() {
        int n1 = 1;
        int n2 = 3;
        int sum = n1 + n2;

        Assertions.assertEquals(3, sum);
    }
}
