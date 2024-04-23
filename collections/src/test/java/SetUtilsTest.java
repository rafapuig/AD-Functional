import es.rafapuig.exercises.SetUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class SetUtilsTest {

    @RepeatedTest(10)
    public void mutableShuffleTest() {
        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>(
                List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
        );
        SetUtils.shuffle(set);
        System.out.println(set);
    }

}
