package ca.allanwang.butler;

import ca.allanwang.butler.base.CodeScanner;
import ca.allanwang.butler.base.MethodScannerProcessor;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.source.util.TreePath;
import com.sun.tools.javac.tree.JCTree;
import jdk.nashorn.internal.objects.annotations.*;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import java.util.List;
import java.util.Set;


/**
 * Created by Allan Wang on 2017-05-10.
 */
@AutoService(Processor.class)
public class CallSuperProcessor extends MethodScannerProcessor implements TaskListener {

    @Override
    @jdk.nashorn.internal.objects.annotations.Getter
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(Override.class)) {
            CodeScanner codeScanner = new CodeScanner();
            codeScanner.setMethodName(e.getSimpleName().toString());
            TreePath tp = trees.getPath(e.getEnclosingElement());
            codeScanner.scan(tp, trees);

            if (codeScanner.isCallSuperUsed()) {
                List list = codeScanner.getMethod().getBody().getStatements();
                if (!doesCallSuper(list, codeScanner.getMethodName())) {
                    warn(e, "Overriding method should call super.%s", e.getSimpleName());
                }
            }
        }

        return false;
    }

    private boolean doesCallSuper(List list, String methodName) {
        for (Object object : list) {
            if (object instanceof JCTree.JCExpressionStatement) {
                JCTree.JCExpressionStatement expr = (JCTree.JCExpressionStatement) object;
                String exprString = expr.toString();
                if (exprString.startsWith("super." + methodName) && exprString.endsWith(");")) return true;
            }
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(Override.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public void started(TaskEvent taskEvent) {

    }

    @Override
    public void finished(TaskEvent taskEvent) {

    }
}
