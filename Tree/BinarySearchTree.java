package Tree;
import java.util.*;

public class BinarySearchTree<T extends Comparable<T>> {
    // Track the numbe of nodes in the BST
    private int count = 0;
    // Track the root node of the BST
    private Node root = null;

    // Internal Tree Node, containing data and child node.
    private class Node {
        T data;
        Node left, right;
        public Node(Node left, Node right, T elem){
            this.data = elem;
            this.left = left;
            this.right = right;
        }
    }
    // Identifier for the tree Traversal Order.
    public enum TreeTraversalOrder {
        PRE_ORDER,
        IN_ORDER,
        POST_ORDER,
        LEVEL_ORDER
    }

    public BinarySearchTree(T[] elems) {
        for (T i : elems)
            add(i);
    }
    public int size(){
        return count;
    }
    public boolean isEmpty(){
        return count==0;
    }

    /*********************************************
     *                  Contains
     ********************************************/
    // Return true if the element exists in the tree
    public boolean contains(T elem) {
        return contains(root, elem);
    }
    // Recursive method to find an element in the tree
    private boolean contains(Node node, T elem) {
        // Case 1: 如果节点为空，则不存在该节点。
        if (node == null) {
            return false;
        }
        // BST的属性：左子树小于根节点，右子树大于根节点。
        int comp = elem.compareTo(node.data);
        if (comp == 0) {
            // Case 2: 目标节点等于根节点，则找到目标节点。
            return true;
        } else if (comp < 0) {
            // Case 3: 目标节点小于根节点，则继续遍历左子树。
            return contains(node.left, elem);
        } else {
            // Case 4: 目标节点大于根节点，则继续遍历右子树。
            return contains(node.right, elem);
        }
    }
    // Search whether the specific Node is in BST.
    private Node search(T elem) {
        Node cur = root;
        while (cur != null) {
            int comp = elem.compareTo(cur.data);
            if (comp < 0) {
                // 当要查找的值小于当前节点的情况
                cur = cur.left;
            } else if (comp > 0) {
                // 当前要查找的值大于当前节点的情况
                cur = cur.right;
            } else {
                return cur;
            }
        }
        // 没找到相应的值
        return null;
    }

    /*********************************************
     *                  Add
     ********************************************/
    // Add an element to this BST, return True if success.
    public boolean add(T elem) {
        // If the BST contains the target node. Skip.
        // Suppose we not accept the duplicate items.
        if (contains(elem)) {
            return false;
        } else {
            // Recursively call add to find the place and add the node.
            root = add(root, elem);
            count++;
            return true;
        }
    }
    // To recursively find the place and add the node in the BST.
    private Node add(Node node, T elem) {
        // terminate situation - find a null node to insert target value.
        if (node == null) {
            node = new Node(null, null, elem);
            return node;
        }
        // Already check the if the BST contains the target node.
        // so we ignore processing duplicate item.
        if (elem.compareTo(node.data) < 0) {
            // Search the left subtree, and if found, update the left subroot.
            node.left = add(node.left, elem);
        } else {
            // Search the right subtree, and if found, update the right subroot.
            node.right = add(node.right, elem);
        }
        return node;
    }

    // To iteratively add the value in the BST
    public boolean insert(T elem) {
        if (root == null) {
            root = new Node(null, null, elem);
            count++;
            return true;
        }
        // 因为插入节点需要知道父节点，所以采用双指针的思路。
        Node cur = root;
        Node parent = null;
        // 循环遍历到一个空节点（即新节点的插入位置）终止
        while (cur != null) {
            int comp = elem.compareTo(cur.data);
            parent = cur;
            if (comp < 0) {
                cur = cur.left;
            } else if (comp > 0) {
                cur = cur.right;
            } else {
                // 在找到相同元素的情况下，跳过，不插入。
                return false;
            }
        }
        // 创建新节点，比较新节点的值和父节点，确定插入左子树还是右子树。
        Node newNode = new Node(null, null, elem);
        if (elem.compareTo(parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        count++;
        return true;
    }

    /*********************************************
     *                  Remove
     ********************************************/
    // Remove an element to this BST, return `true` if success.
    public boolean remove(T elem) {
        // If the BST doesn't contain the target node. Skip.
        if (!contains(elem)) {
            return false;
        } else {
            // Find the target node and remove the node.
            root = remove(root, elem);
            count--;
            return true;
        }
    }
    // To recursively find the target value and remove the node in the BST.
    private Node remove(Node node, T elem) {
        // Phase 1 - Find the target node.
        int comp = elem.compareTo(node.data);
        if (comp < 0) {
            // Search the left subtree, and if found, update the left subroot.
            node.left = remove(node.left, elem);
        } else if (comp > 0) {
            // Search the right subtree, and if found, update the right subroot.
            node.right = remove(node.right, elem);
        } else {
            // Found the target value that need to be removed
            // Phase 2 - Apply remove procedure
            if (node.left == null) {
                // 该判断包含了节点是叶子节点的情况，即 node.left==null 且 node.right==null，返回 null。
                // If target node only have right subtree, return it.
                return node.right;
            } else if (node.right == null) {
                // If target node only have left subtree, return it.
                return node.left;
            } else {
                // If target node have BOTH right and left subtree.
                // Find the leftmost(smallest) node in the right subtree.
                Node temp = findMin(node.right);
                // Swap the node value.
                node.data = temp.data;
                // Recursively call remove to deal with the leftmost node (must be leaf node)
                node.right = remove(node.right, temp.data);
            }
        }
        return node;
    }
    // To find the Max/Min value in the BST. (rightmost/leftmost node in subtree)
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public boolean delete(T elem) {
        Node cur = root;
        Node parent = null;
        // 根据节点值遍历 BST，直到找到目标节点。
        while (cur != null) {
            int comp = elem.compareTo(cur.data);
            if (comp == 0) {
                // 移除目标节点，并返回 true。
                deleteNode(parent, cur);
                count--;
                return true;
            } else if (comp < 0) {
                parent = cur;
                cur = cur.left;
            } else {
                parent = cur;
                cur = cur.right;
            }
        }
        return false;
    }

    private void deleteNode(Node parent, Node cur) {
        // Case 1 & 2：节点的左子树为空（包含节点为叶子节点的情况），所以父节点连接到右子树，
        if (cur.left == null) {
            // 如果父节点为空，即表示删除的是根节点。
            if (parent == null) {
                root = cur.right;
            }
            // 判断连接到父节点的左子树还是右子树。
            else if (cur == parent.left) {
                parent.left = cur.right;
            } else {
                parent.right = cur.right;
            }
        }
        // Case 3：节点的右子树为空，所以父节点连接到左子树，
        else if (cur.right == null) {
            // 如果父节点为空，即表示删除的是根节点。
            if (parent == null) {
                root = cur.left;
            }
            // 判断连接到父节点的左子树还是右子树。
            else if (cur == parent.left) {
                parent.left = cur.left;
            } else {
                parent.right = cur.left;
            }
        }
        // Case 4：如果节点左右子树都不为空。
        else {
            // 替换根节点为：右子树的最小节点。
            Node target = cur.right;
            Node targetParent = cur;
            // 找到右子树的最小节点（leftmost）。
            while (target.left != null) {
                targetParent = target;
                target = target.left;
            }
            // 交换根节点和该节点的值。
            cur.data = target.data;
            // 将 Leftmost 节点的右子树复制给其父节点。
            targetParent.left = target.right;
            // target 已经被转移到右子树的根节点。
            // target.right = target = null;
        }
    }


    /*********************************************
     *              Utilites
     ********************************************/
    // Computes the height of the tree, O(n)
    public int height(){
        return height(root);
    }
    private int height(Node node){
        if(node==null){
            return 0;
        }
        return Math.max(height(node.left), height(node.right))+1;
    }

    // Returns an iterator for a given TreeTraversalOrder.
    public Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER:
                return preOrder();
            case IN_ORDER:
                return inOrder();
            case POST_ORDER:
                return postOrder();
            case LEVEL_ORDER:
                return levelOrder();
            default:
                return null;
        }
    }

    private Iterator<T> preOrder() {
        final int expectedNodeCount = count;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != count) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }
            @Override
            public T next() {
                if (expectedNodeCount != count) throw new ConcurrentModificationException();
                Node node = stack.pop();
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
                return node.data;
            }
            @Override
            public void remove() { throw new UnsupportedOperationException();}
        };
    }

    private Iterator<T> inOrder() {
        final int expectedNodeCount = count;
        final Stack<Node> stack = new Stack<>();
        stack.push(root);
        return new Iterator<T>() {
            Node cur = root;
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != count) throw new ConcurrentModificationException();
                return root != null && !stack.isEmpty();
            }
            @Override
            public T next() {
                if (expectedNodeCount != count) throw new ConcurrentModificationException();
                while (cur != null && cur.left != null) {
                    stack.push(cur.left);
                    cur = cur.left;
                }
                Node node = stack.pop();
                if (node.right != null) {
                    stack.push(node.right);
                    cur = node.right;
                }
                return node.data;
            }
            @Override
            public void remove() { throw new UnsupportedOperationException();}
        };
    }

    private Iterator<T> postOrder() {
        final int expectedNodeCount = count;
        final Stack<Node> s1 = new Stack<>();
        final Stack<Node> s2 = new Stack<>();
        s1.push(root);
        while (!s1.isEmpty()) {
            Node node = s1.pop();
            if (node != null) {
                s2.push(node);
                if (node.left != null) s1.push(node.left);
                if (node.right != null) s1.push(node.right);
            }
        }
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != count) throw new ConcurrentModificationException();
                return root != null && !s2.isEmpty();
            }
            @Override
            public T next() {
                if (expectedNodeCount != count) throw new ConcurrentModificationException();
                return s2.pop().data;
            }
            @Override
            public void remove() { throw new UnsupportedOperationException();}
        };
    }

    private Iterator<T> levelOrder() {
        final int expectedNodeCount = count;
        final Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedNodeCount != count) throw new ConcurrentModificationException();
                return root != null && !queue.isEmpty();
            }
            @Override
            public T next() {
                if (expectedNodeCount != count) throw new ConcurrentModificationException();
                Node node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                return node.data;
            }
            @Override
            public void remove() { throw new UnsupportedOperationException();}
        };
    }
}
