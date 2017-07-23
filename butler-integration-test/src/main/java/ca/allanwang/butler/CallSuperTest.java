package ca.allanwang.butler;

/**
 * Created by Allan Wang on 2017-05-10.
 */
public class CallSuperTest extends CallSuperBase {

    public static void main(String[] args) {
        new CallSuperTest().hello();
    }

    @Override
    void hello() {
//        super.hello();
        System.out.println("World");
    }
}
