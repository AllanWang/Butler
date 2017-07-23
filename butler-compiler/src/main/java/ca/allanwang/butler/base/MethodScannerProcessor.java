package ca.allanwang.butler.base;

import com.sun.source.util.Trees;

import javax.annotation.processing.AbstractProcessor;
import javax.lang.model.element.Element;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic.Kind;

/**
 * Created by Allan Wang on 2017-05-10.
 */
public abstract class MethodScannerProcessor extends AbstractProcessor {

    protected Trees trees;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        trees = Trees.instance(processingEnv);
    }

    protected void error(Element element, String message, Object... args) {
        printMessage(Kind.ERROR, element, message, args);
    }

    protected void warn(Element element, String message, Object... args) {
        printMessage(Kind.WARNING, element, message, args);
    }

    private void printMessage(Kind kind, Element element, String message, Object[] args) {
        if (args.length > 0)
            message = String.format(message, args);
        processingEnv.getMessager().printMessage(kind, message, element);
    }

    protected void log(String msg) {
        if (processingEnv.getOptions().containsKey("debug")) {
            processingEnv.getMessager().printMessage(Kind.NOTE, String.format("Butler: %s", msg));
        }
    }

}
