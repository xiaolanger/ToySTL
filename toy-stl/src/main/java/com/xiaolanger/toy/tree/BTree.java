package com.xiaolanger.toy.tree;

public class BTree<K extends Comparable<K>, V> {
    private class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;
        private Node<K, V> parent;
        private int height = 0;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V> header;
    private int size;
    private boolean isAVL;

    public BTree(boolean isAVL) {
        this.isAVL = isAVL;
    }

    public BTree() {
        this(true);
    }

    private int adjustHeight(Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        Node<K, V> p = node;
        while (p.parent != null) {
            int lh = getHeight(p.parent.left);
            int rh = getHeight(p.parent.right);
            int height = lh > rh ? lh : rh;

            if (p.parent.height != height + 1) {
                p.parent.height = height + 1;
            }

            p = p.parent;
        }
        return p.height;
    }

    public V put(K key, V value) {
        if (header == null) {
            header = new Node<>(key, value);
            header.height = 1;
            adjustHeight(header);
            size++;
            return value;
        }

        Node<K, V> p = header;
        while (true) {
            if (key.compareTo(p.key) < 0) {
                if (p.left != null) {
                    p = p.left;
                    continue;
                }

                Node<K, V> node = new Node<>(key, value);
                p.left = node;
                node.parent = p;
                node.height = 1;

                adjustHeight(node);

                rotate(p);
                break;
            } else {
                if (p.right != null) {
                    p = p.right;
                    continue;
                }

                Node<K, V> node = new Node<>(key, value);
                p.right = node;
                node.parent = p;
                node.height = 1;

                adjustHeight(node);

                rotate(p);
                break;
            }
        }

        size++;
        return value;
    }

    public V get(K key) {
        Node<K, V> p = header;

        while (p != null) {
            if (key.equals(p.key)) {
                return p.value;
            }

            if (key.compareTo(p.key) < 0) {
                p = p.left;
                continue;
            }

            p = p.right;
        }

        return null;
    }

    public V remove(K key) {
        Node<K, V> p = header;

        while (p != null) {
            if (key.equals(p.key)) {
                V value = p.value;
                remove(p);
                size--;
                return value;
            }

            if (key.compareTo(p.key) < 0) {
                p = p.left;
                continue;
            }

            p = p.right;
        }

        return null;
    }

    private Node<K, V> remove(Node<K, V> node) {
        Node<K, V> root = node.parent;

        // sub tree root
        Node<K, V> subroot;
        Node<K, V> delete;

        do {
            // no right tree
            if (node.right == null) {
                Node<K, V> left = node.left;
                subroot = left;
                delete = node;
                break;
            }

            // right tree
            Node<K, V> p = node.right;

            // most left node
            while (p.left != null) {
                p = p.left;
            }

            // swap node.value and p.value
            K key = node.key;
            V value = node.value;
            node.key = p.key;
            node.value = p.value;
            p.key = key;
            p.value = value;

            Node<K, V> parent = p.parent;
            // no left tree
            Node<K, V> right = p.right;

            if (parent.left == p) {
                parent.left = right;
            } else {
                parent.right = right;
            }

            if (right != null) {
                right.parent = parent;
            }

            subroot = node;
            delete = p;
        } while (false);

        // modify sub root link
        if (root == null) {
            if (subroot != null) {
                subroot.parent = null;
            }
            header = subroot;
        } else if (root.left == node) {
            if (subroot != null) {
                subroot.parent = root;
            }
            root.left = subroot;
        } else {
            if (subroot != null) {
                subroot.parent = root;
            }
            root.right = subroot;
        }

        // delete's tree height - 1
        delete.height = delete.height - 1;
        adjustHeight(delete);

        rotate(delete.parent);

        // delete
        delete.left = null;
        delete.right = null;
        delete.parent = null;
        delete.height = 0;

        return subroot;
    }

    private Node<K, V> rotate(Node<K, V> node) {
        // if not AVL tree do nothing
        if (!isAVL) {
            return null;
        }
        Node<K, V> p = node;
        Node<K, V> last = null;
        while (p != null) {
            int lh = getHeight(p.left);
            int rh = getHeight(p.right);
            if (Math.abs(lh - rh) > 1) {
                p = lh > rh ? L(p) : R(p);
                last = p;
                if (p.parent == null) {
                    header = p;
                }
                continue;
            }
            p = p.parent;
        }
        return last;
    }

    private Node<K, V> L(Node<K, V> node) {
        Node<K, V> left = node.left;
        int lh = getHeight(left.left);
        int rh = getHeight(left.right);
        return lh > rh ? LL(node) : LR(node);
    }

    private Node<K, V> R(Node<K, V> node) {
        Node<K, V> right = node.right;
        int lh = getHeight(right.left);
        int rh = getHeight(right.right);
        return lh > rh ? RL(node) : RR(node);
    }

    private Node<K, V> LR(Node<K, V> node) {
        RR(node.left);
        return LL(node);
    }

    private Node<K, V> RL(Node<K, V> node) {
        LL(node.right);
        return RR(node);
    }

    private Node<K, V> LL(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        Node<K, V> left = node.left;

        node.left = left.right;
        if (left.right != null) {
            left.right.parent = node;
        }

        left.right = node;
        node.parent = left;

        left.parent = parent;
        if (parent != null) {
            if (parent.left == node) {
                parent.left = left;
            } else {
                parent.right = left;
            }
        }

        if (node.left != null) {
            adjustHeight(node.left);
        } else if (node.right != null) {
            adjustHeight(node.right);
        } else {
            node.height = 1;
            adjustHeight(node);
        }

        return left;
    }

    private Node<K, V> RR(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        Node<K, V> right = node.right;

        node.right = right.left;
        if (right.left != null) {
            right.left.parent = node;
        }

        right.left = node;
        node.parent = right;

        right.parent = parent;
        if (parent != null) {
            if (parent.left == node) {
                parent.left = right;
            } else {
                parent.right = right;
            }
        }

        if (node.right != null) {
            adjustHeight(node.right);
        } else if (node.left != null) {
            adjustHeight(node.left);
        } else {
            node.height = 1;
            adjustHeight(node);
        }

        return right;
    }

    public int size() {
        return size;
    }

    public int getHeight() {
        return getHeight(header);
    }

    private int getHeight(Node<K, V> node) {
        return node == null ? 0 : node.height;
    }

    public int getHeightSlow() {
        return getHeightSlow(header);
    }

    private int getHeightSlow(Node<K, V> header) {
        if (header == null) {
            return 0;
        }

        int left = getHeightSlow(header.left);
        int right = getHeightSlow(header.right);
        return left > right ? left + 1 : right + 1;
    }
}
