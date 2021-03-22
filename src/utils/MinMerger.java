package utils;

/**
 * 返回最小值
 * @param <E>
 */
public class MinMerger<E extends Comparable> implements Merger {

    @Override
    public E merge(Object a, Object b) {
        if (a == null && b == null){
            return null;
        }
        if (a == null){
            return (E) b;
        }
        if (b == null){
            return (E) a;
        }
        return ((E)a).compareTo((E)b) > 0? ((E)b):((E)a);
    }
}
