package ca.allanwang.butler;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import java.util.Collections;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;


/**
 * Created by Allan Wang on 2017-05-11.
 */
public class CallSuperTest {

//    @Test
    public void test() {

        assert_().about(javaSources())
                .that(Collections.singletonList(JavaFileObjects.forResource("test/CallSuperOver.java")))
                .processedWith(new CallSuperProcessor())
                .compilesWithoutError();
    }
}
