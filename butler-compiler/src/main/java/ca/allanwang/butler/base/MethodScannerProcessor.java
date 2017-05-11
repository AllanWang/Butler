package ca.allanwang.butler.base;

import com.sun.source.util.Trees;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;

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

}
