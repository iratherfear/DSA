import java.util.Arrays;
import java.util.function.BinaryOperator;

class SegmentTree<T> {

    private final int N;
    private final T NEUTRAL_ELEMENT;
    private final T[] segmentTree;
    private final BinaryOperator<T> merge;

    @SuppressWarnings("unchecked")
    public SegmentTree(int n, T neutralElement, BinaryOperator<T> merge) {
        this.N = n;
        this.NEUTRAL_ELEMENT = neutralElement;
        this.merge = merge;
        this.segmentTree = (T[]) new Object[4 * n];
        Arrays.fill(segmentTree, NEUTRAL_ELEMENT);
    }

    private void updateSeg(int index, T value, int x, int lx, int rx) {
        if (rx - lx == 1) {
            segmentTree[x] = value;
            return;
        }

        int mid = (lx + rx) / 2;
        if (index < mid) {
            updateSeg(index, value, 2 * x + 1, lx, mid);
        } else {
            updateSeg(index, value, 2 * x + 2, mid, rx);
        }

        segmentTree[x] = merge.apply(
                segmentTree[2 * x + 1],
                segmentTree[2 * x + 2]
        );
    }

    public void update(int index, T value) {
        updateSeg(index, value, 0, 0, N);
    }

    private T query(int l, int r, int x, int lx, int rx) {
        if (rx <= l || r <= lx) {
            return NEUTRAL_ELEMENT;
        }
        if (l <= lx && rx <= r) {
            return segmentTree[x];
        }

        int mid = (lx + rx) / 2;
        T left = query(l, r, 2 * x + 1, lx, mid);
        T right = query(l, r, 2 * x + 2, mid, rx);
        return merge.apply(left, right);
    }

    public T get(int l, int r) {
        return query(l, r, 0, 0, N);
    }
}
