package ca.allanwang.butler.base;

import ca.allanwang.butler.CallSuper;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Scope;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.code.Type.ClassType;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCTypeApply;

import java.lang.annotation.Annotation;

/**
 * Created by Allan Wang on 2017-05-10.
 */
public class CodeScanner extends TreeScanner<Object, Trees> {

    private String methodName;
    private MethodTree method;
    private boolean callSuperUsed;

    @Override
    public Object visitClass(ClassTree classTree, Trees trees) {
        Tree extendTree = classTree.getExtendsClause();

        if (extendTree instanceof JCTypeApply) { //generic classes case
            JCTypeApply generic = (JCTypeApply) extendTree;
            extendTree = generic.clazz;
        }

        if (extendTree instanceof JCIdent) {
            JCIdent tree = (JCIdent) extendTree;
            Scope members = tree.sym.members();

            if (checkScope(members))
                return super.visitClass(classTree, trees);

            if (checkSuperTypes((ClassType) tree.type))
                return super.visitClass(classTree, trees);

        }
        callSuperUsed = false;

        return super.visitClass(classTree, trees);
    }

    public boolean checkSuperTypes (ClassType type) {
        if (type.supertype_field != null && type.supertype_field.tsym != null)
            return checkScope(type.supertype_field.tsym.members()) || checkSuperTypes((ClassType) type.supertype_field);
        return false;
    }

    public boolean checkScope (Scope members) {
        for (Symbol s : members.getElements()) {
            if (s instanceof MethodSymbol) {
                MethodSymbol ms = (MethodSymbol) s;
                if (ms.getSimpleName().toString().equals(methodName)) {
                    Annotation annotation = ms.getAnnotation(CallSuper.class);
                    if (annotation != null) {
                        callSuperUsed = true;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public Object visitMethod (MethodTree methodTree, Trees trees) {
        if (methodTree.getName().toString().equals(methodName))
            method = methodTree;
        return super.visitMethod(methodTree, trees);
    }

    public void setMethodName (String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName () {
        return methodName;
    }

    public MethodTree getMethod () {
        return method;
    }

    public boolean isCallSuperUsed () {
        return callSuperUsed;
    }
}
