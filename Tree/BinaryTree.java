package Tree;
import java.util.*;

class TreeNode<T> {
    T data;
    TreeNode<T> left = null;
    TreeNode<T> right = null;

    public TreeNode(T data) {this.data = data; }
    public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}

public class BinaryTree<T> {
    // 将顺序存储的二叉树转换为链式存储【递归】
    public TreeNode<T> array2TreeNode(int index, T[] dataArr) {
        if (index >= dataArr.length)
            return null;
        TreeNode<T> root = new TreeNode<T>(dataArr[index], array2TreeNode(2 * index + 1, dataArr),
                array2TreeNode(2 * index + 2, dataArr));
        return root;
    }

    /*************************************
     *     【递归】前中后序遍历二叉树
     *************************************/
    public void preOrder(TreeNode<T> root, List<T> result) {
        if (root == null)
            return;
        result.add(root.data);
        System.out.print(root.data);
        preOrder(root.left, result);
        preOrder(root.right, result);
    }
    public void inOrder(TreeNode<T> root, List<T> result) {
        if (root == null)
            return;
        inOrder(root.left, result);
        result.add(root.data);
        System.out.print(root.data);
        inOrder(root.right, result);
    }
    public void postOrder(TreeNode<T> root, List<T> result) {
        if (root == null)
            return;
        postOrder(root.left, result);
        postOrder(root.right, result);
        result.add(root.data);
        System.out.print(root.data);
    }


    /*************************************
     *     【迭代】前中后序遍历二叉树
     *************************************/
    public List<T> preOrder(TreeNode<T> root) {
        List<T> result = new LinkedList<>();
        if (root == null)
            return result;
        LinkedList<TreeNode<T>> stack = new LinkedList<>();
        stack.addFirst(root);
        while (!stack.isEmpty()) {
            TreeNode<T> cur = stack.removeFirst();
            result.add(cur.data);
            if (cur.right != null) stack.addFirst(cur.right);
            if (cur.left != null) stack.addFirst(cur.left);
        }
        return result;
    }
    public List<T> postOrder(TreeNode<T> root) {
        List<T> result = new LinkedList<>();
        if (root == null)
            return result;
        LinkedList<TreeNode<T>> stack = new LinkedList<>();
        stack.addFirst(root);
        while (!stack.isEmpty()) {
            TreeNode<T> cur = stack.removeFirst();
            result.add(cur.data);
            if (cur.left != null) stack.addFirst(cur.left);
            if (cur.right != null) stack.addFirst(cur.right);
        }
        Collections.reverse(result);
        return result;
    }
    public List<T> inOrder(TreeNode<T> root) {
        List<T> result = new LinkedList<>();
        if (root == null)
            return result;
        LinkedList<TreeNode<T>> stack = new LinkedList<>();
        TreeNode<T> cur = root;
        // 循环结束的条件是：当前指针为空并且迭代栈为空。
        // 因为当前指针不为空，迭代栈为空的情况是：当根节点被处理后，此时栈为空，cur指向左子树的根节点。
        // 当前指针为空，迭代栈不为空的情况是：当前指针指向了目前最左元素的左孩子，而这个孩子肯定是nullptr
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {              // 指针来访问节点，访问到最底层最左边的元素
                stack.addFirst(cur);        // 将节点放进栈
                cur = cur.left;             // 继续遍历左边元素
            } else {                        // 左边的元素为空
                cur = stack.removeFirst();  // 处理中间元素
                result.add(cur.data);
                cur = cur.right;            // 遍历右边元素
            }
        }
        return result;
    }

    /*************************************
     *    【统一迭代】前中后序遍历二叉树
     *************************************/
    public List<T> preOrder2(TreeNode<T> root) {
        List<T> result = new LinkedList<>();
        if (root == null)
            return result;
        LinkedList<TreeNode<T>> stack = new LinkedList<>();
        stack.addFirst(root);
        while (!stack.isEmpty()) {
            TreeNode<T> cur = stack.removeFirst();
            if (cur != null) {                                      // 因为是栈 Stack，所以先进后出。
                if (cur.right != null) stack.addFirst(cur.right);   // 添加右节点（空节点不入栈）
                if (cur.left != null) stack.addFirst(cur.left);     // 添加左节点（空节点不入栈）
                stack.addFirst(cur);                                // 中
                stack.addFirst(null);                               // 添加标记
            } else {                        // 当前节点为空时，表示后面一个节点已经遍历过了，可以加入结果中。
                cur = stack.removeFirst();  // 首先跳过空节点，移除目标节点。
                result.add(cur.data);       // 加入结果链表。
            }
        }
        return result;
    }
    public List<T> inOrder2(TreeNode<T> root) {
        List<T> result = new LinkedList<>();
        if (root == null)
            return result;
        LinkedList<TreeNode<T>> stack = new LinkedList<>();
        stack.addFirst(root);
        while (!stack.isEmpty()) {
            TreeNode<T> cur = stack.removeFirst();
            if (cur != null) {                                      // 因为是栈 Stack，所以先进后出。
                if (cur.right != null) stack.addFirst(cur.right);   // 右
                stack.addFirst(cur);                                // 中
                stack.addFirst(null);
                if (cur.left != null) stack.addFirst(cur.left);     // 左
            } else {
                cur = stack.removeFirst();
                result.add(cur.data);
            }
        }
        return result;
    }
    public List<T> postOrder2(TreeNode<T> root) {
        List<T> result = new LinkedList<>();
        if (root == null)
            return result;
        LinkedList<TreeNode<T>> stack = new LinkedList<>();
        stack.addFirst(root);
        while (!stack.isEmpty()) {
            TreeNode<T> cur = stack.removeFirst();
            if (cur != null) {                                      // 因为是栈 Stack，所以先进后出。
                stack.addFirst(cur);                                // 中
                stack.addFirst(null);
                if (cur.right != null) stack.addFirst(cur.right);   // 右
                if (cur.left != null) stack.addFirst(cur.left);     // 左
            } else {
                cur = stack.removeFirst();
                result.add(cur.data);
            }
        }
        return result;
    }

    /*************************************
     *         层序遍历二叉树
     *************************************/
    public List<T> levelOrder(TreeNode<T> root) {
        LinkedList<T> result = new LinkedList<>();
        if (root == null)
            return result;
        Queue<TreeNode<T>> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode<T> cur = q.poll();
            result.add(cur.data);
            if (cur.left != null) q.offer(cur.left);
            if (cur.right != null) q.offer(cur.right);
        }
        return result;
    }
    public List<List<T>> orderByLevel(TreeNode<T> root) {
        List<List<T>> result = new LinkedList<>();
        if (root == null)
            return result;
        Queue<TreeNode<T>> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            List<T> levelItems = new LinkedList<>();
            int len = q.size();
            while (len > 0) {
                TreeNode<T> tempNode = q.poll();
                levelItems.add(tempNode.data);
                if (tempNode.left != null) q.offer(tempNode.left);
                if (tempNode.right != null) q.offer(tempNode.right);
                len--;
            }
            result.add(levelItems);
        }
        return result;
    }
    public void orderByLevel2(TreeNode<T> node, List<List<T>> result, int level) {
        if (node == null)
            return;
        level++;
        if (result.size() < level) {
            List<T> item = new LinkedList<>();
            result.add(item);
        }
        result.get(level - 1).add(node.data);
        orderByLevel2(node.left, result, level);
        orderByLevel2(node.right, result, level);
        return;
    }

    public static void main(String[] args) {
        Integer[] arr = {5,4,6,1,2,7,8};
        BinaryTree<Integer> bt = new BinaryTree<>();
        TreeNode<Integer> treeRoot = bt.array2TreeNode(0, arr);
        /*************************************
         *     【递归】前中后序遍历二叉树
         *************************************/
        List<Integer> result = new LinkedList<>();
        bt.preOrder(treeRoot, result);
        System.out.println();
        bt.inOrder(treeRoot, result);
        System.out.println();
        bt.postOrder(treeRoot, result);
        System.out.println();

        /*************************************
         *     【迭代】前中后序遍历二叉树
         *************************************/
        result = bt.preOrder(treeRoot);
        Iterator<Integer> it = result.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.println();
        result = bt.inOrder(treeRoot);
        it = result.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.println();
        result = bt.postOrder(treeRoot);
        it = result.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.println();

        /*************************************
         *    【统一迭代】前中后序遍历二叉树
         *************************************/
        result = bt.preOrder2(treeRoot);
        it = result.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.println();
        result = bt.inOrder2(treeRoot);
        it = result.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.println();
        result = bt.postOrder2(treeRoot);
        it = result.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.println();

        /*************************************
         *          层序遍历二叉树
         *************************************/
        result = bt.levelOrder(treeRoot);
        it = result.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
        }
        System.out.println();
        List<List<Integer>> levelList = new LinkedList<>();
        levelList = bt.orderByLevel(treeRoot);
        System.out.println(levelList);
        levelList = new LinkedList<>();
        bt.orderByLevel2(treeRoot, levelList, 0);
        System.out.println(levelList);
    }
}
