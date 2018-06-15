import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

/**
 * Comparing three different ways of appending to a string. The conclusion is
 * that "+" is horribly inefficient and should be used extremely sparingly.
 *
 * See https://gist.github.com/a2800276/2000141 for the original.
 *
 * Note that we could use "sum.setLength(0)" to avoid allocating objects over
 * and over but the performance gain is fairly small (around 6% maybe).
 */
public final class StringAppend {
    private static final int SIZE = 1000;

    @Bench
    public void string(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            String sum = "";
            for (int i = 0; i < SIZE; i++) {
                sum += i;
            }
            b.bytes(sum.length());
        }
    }

    @Bench
    public void stringBuffer(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            StringBuffer sum = new StringBuffer();
            for (int i = 0; i < SIZE; i++) {
                sum.append(i);
            }
            b.bytes(sum.length());
        }
    }

    @Bench
    public void stringBuilder(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            StringBuilder sum = new StringBuilder();
            for (int i = 0; i < SIZE; i++) {
                sum.append(i);
            }
            b.bytes(sum.length());
        }
    }
}
