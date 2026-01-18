public class SegmentTree {
    private int N;
    private record Node() {}
    private final Node NEUTRAL_ELEMENT = null;
    private Node[] segment_tree;
    private Node merge(Node left, Node right) {
        throw new RuntimeException("Merge function not defined");
    }

    public SegmentTree(int n) {
        this.N = n;
        segment_tree = new Node[N];
    }

    private void updateSeg(int index, Node value, int x, int lx, int rx) {
        if(rx - lx == 1) {
            segment_tree[x] = value;
            return;
        }

        int mid = (lx + rx) / 2;
        if(index < mid) {
            updateSeg(index, value, 2 * x + 1, lx, mid);
        } else {
            updateSeg(index, value, 2 * x + 2, mid, rx);
        }
        segment_tree[index] = merge(segment_tree[2 * x + 1], segment_tree[2 * x + 2]);
    }

    public void update(int index, Node value) {
        updateSeg(index, value, 0, 0, N - 1);
    }

    private Node getValue(int l, int r, int x, int lx, int rx) {
        if(rx <= l || r <= lx) {
            return NEUTRAL_ELEMENT;
        }
        if(l <= lx && rx <= r) {
            return segment_tree[x];
        }
        int mid = (lx + rx) / 2;
        segment_tree[x] = merge(getValue(l, r, 2 * x, lx, mid), getValue(l, r, 2 * x + 2, mid, rx));
        return segment_tree[x];
    }

    public Node get(int l, int r) {
        return getValue(l, r, 0, 0, N - 1);
    }
}