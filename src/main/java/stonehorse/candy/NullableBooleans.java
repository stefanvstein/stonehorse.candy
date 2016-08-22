package stonehorse.candy;

import static stonehorse.candy.Choices.ifelse;
import static java.util.Objects.nonNull;

/*
 * Boolean operations on Boolean where null is false
 */
public class NullableBooleans {
    private NullableBooleans(){}
    /**
     * false if TRUE
     */
    public static boolean not(Boolean b) {
        return !nonNull(b) || !b;
    }

    /**
     * true if TRUE and TRUE
     */
    public static boolean and(Boolean a, Boolean b) {
        return ifelse(nonNull(a) && nonNull(b),
                ()->a && b,
                ()->false);
    }

    /**
     * true if any is TRUE
     */
    public static boolean or(Boolean a, Boolean b) {
        return nonNull(a) && a || nonNull(b) && b;
    }

    /**
     * true if TRUE
     */
    public static boolean truth(Boolean b){
        return nonNull(b) && b;
    }

}
