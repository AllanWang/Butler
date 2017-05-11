package ca.allanwang.butler.callsuper;

import ca.allanwang.butler.base.CodeScanner;
import ca.allanwang.butler.base.MethodScannerProcessor;
import com.sun.source.util.TreePath;
import com.sun.tools.javac.tree.JCTree;

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
@SupportedAnnotationTypes("java.lang.Override")
@AutoService(Processor.class)
public class CallSuperProcessor extends MethodScannerProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("HI");;
        for (Element e : roundEnv.getElementsAnnotatedWith(Override.class)) {
            error(e);
            CodeScanner codeScanner = new CodeScanner();
            codeScanner.setMethodName(e.getSimpleName().toString());
            TreePath tp = trees.getPath(e.getEnclosingElement());
            codeScanner.scan(tp, trees);

            if (codeScanner.isCallSuperUsed()) {
                List list = codeScanner.getMethod().getBody().getStatements();

                if (!doesCallSuper(list, codeScanner.getMethodName())) {
                    error(e);
                }
            }
        }

        return false;
    }

    private void error(Element e) {
        processingEnv.getMessager().printMessage(Kind.ERROR, String.format("Overriding method should call super.%s", e.getSimpleName()), e);
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
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}
