import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

/**
 * Proper format (annotations, parameter lists, etc.) for benchmark methods.
 */
public class Usage {
    /**
     * Incorrect: Must not be static.
     */
    @Bench
    public static void one(Bee b) {
        System.out.println("one");
    }

    /**
     * Incorrect: Missing annotation.
     */
    public void two(Bee b) {
        System.out.println("two");
    }

    /**
     * Correct: Annotation and proper parameter list.
     */
    @Bench
    public void three(Bee b) {
        System.out.println("three");
    }

    /**
     * Incorrect: Exactly one parameter of type Bee.
     */
    @Bench
    public void four(Bee b, int x) {
        System.out.println("four");
    }

    /**
     * Incorrect: Exactly one parameter of type Bee.
     */
    @Bench
    public void five(int x) {
        System.out.println("five");
    }

    /**
     * Incorrect: No return type.
     */
    @Bench
    public Bee six(Bee b) {
        System.out.println("six");
        return null;
    }

    /**
     * Problematic: Throws an exception.
     */
    @Bench
    public void seven(Bee b) {
        System.out.println("seven");
        String x = null;
        x.toString();
    }
}
