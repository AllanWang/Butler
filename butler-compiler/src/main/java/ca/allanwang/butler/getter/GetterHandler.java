package ca.allanwang.butler.getter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;


/**
 * Created by Allan Wang on 2017-05-10.
 */
public class GetterHandler extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
//        roundEnv.getElementsAnnotatedWith(Getter.class).forEach();
        return false;
    }
}
