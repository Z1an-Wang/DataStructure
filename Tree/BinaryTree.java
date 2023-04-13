package Tree;

public class BinaryTree<T> {
    private class TreeNode<T>{
        T val;
        TreeNode<T> left = null;
        TreeNode<T> right = null;

        public TreeNode(T val){this.val = val;}
        public TreeNode(T val, TreeNode<T> left, TreeNode<T> right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
