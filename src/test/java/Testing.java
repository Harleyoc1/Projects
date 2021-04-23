import org.junit.jupiter.api.Test;

/**
 * @author Harley O'Connor
 */
public final class Testing {

    @Test
    public void test() {
        final String path = "/icons/plus-dark.png";

        System.out.println(path.substring(0, path.lastIndexOf('.')) + " " + path.substring(path.lastIndexOf('.')));
    }

}
