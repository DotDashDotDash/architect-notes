package jvm;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class JavaFourReferences {

    /**
     * Java default declaration is strong reference
     */
    public static void strongReference() {
        //define a strong reference
        Object obj = new Object();

        //terminate strong reference for GC
        //otherwise, obj will never be collected by GC
        obj = null;
    }

    /**
     * Different test case shows the different options
     * that GC takes
     */
    private static List<Object> list = new ArrayList<>();

    public static void softReference() {
        //test case 1: VM parameter set to -Xms2M -Xmx3M
        //byte[] bytes1 = new byte[1024 * 1024];        //pass

        //test case 2: VM parameter set to -Xms2M -Xmx3M
        //byte[] bytes2 = new byte[1024 * 1024 * 5];      //OOM error

        //test case 3: trigger gc
        for (int i = 0; i < 10; i++) {
            byte[] buff = new byte[1024 * 1024];
            SoftReference<byte[]> sr = new SoftReference<>(buff);
            list.add(sr);
        }
        System.gc(); //gc
        for (int i = 0; i < list.size(); i++) {
            Object obj = ((SoftReference) list.get(i)).get();
            System.out.println(obj);
        }
    }

    private static void weakReference() {
        for (int i = 0; i < 10; i++) {
            byte[] buff = new byte[1024 * 1024];
            WeakReference<byte[]> sr = new WeakReference<>(buff);
            list.add(sr);
        }
        System.gc(); //gc
        for (int i = 0; i < list.size(); i++) {
            Object obj = ((WeakReference) list.get(i)).get();
            System.out.println(obj);
        }
    }

    public static void main(String[] args) {
        JavaFourReferences.softReference();
    }
}
