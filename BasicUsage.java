import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

/**
 * Proper format (annotations, parameter lists, etc.) for benchmark methods.
 */
public class BasicUsage {
    /**
     * Incorrect: Must be static.
     */
    @Bench
    public void one(Bee b) {
        System.out.println("one");
    }

    /**
     * Incorrect: Missing annotation.
     */
    public static void two(Bee b) {
        System.out.println("two");
    }

    /**
     * Correct: Annotation and proper parameter list.
     */
    @Bench
    public static void three(Bee b) {
        System.out.println("three");
    }

    /**
     * Incorrect: Exactly one parameter of type Bee.
     */
    @Bench
    public static void four(Bee b, int x) {
        System.out.println("four");
    }

    /**
     * Incorrect: Exactly one parameter of type Bee.
     */
    @Bench
    public static void five(int x) {
        System.out.println("five");
    }

    /**
     * Incorrect: No return type.
     */
    @Bench
    public static Bee six(Bee b) {
        System.out.println("six");
        return null;
    }

    /**
     * Problematic: Throws an exception.
     */
    @Bench
    public static void seven(Bee b) {
        System.out.println("seven");
        String x = null;
        x.toString();
    }

}
