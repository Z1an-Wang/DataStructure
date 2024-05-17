package Tree;

import java.util.HashMap;


public class Trie {

    private TrieNode root; // 字典树的根

    // 初始化字典树
    Trie() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        return this.root;
    }

    // 字典树节点
    public static class TrieNode {
        char ch;
        int num;                // 有多少单词通过这个节点，即由根至该节点组成的字符串模式出现的次数
        int wordEnding = 0;     // 以这个节点结尾的单词个数。
        // 节点存储子节点，可以使用长度为 26 的数组，但是比较浪费空间。
        HashMap<Character, TrieNode> children;

        TrieNode(char c, int n) {
            num = n;
            ch = c;
            children = new HashMap<>();
        }
        TrieNode() {
            this('\0', 0);
        }

        int getOccurrence() {
            return num;
        }
        void addOccurrence(int n) {
            num += n;
        }

        public void addChild(char c, TrieNode node) {
            this.children.put(c, node);
        }
    }


    // 建立字典树 / 在字典树中插入 1 个单词
    public void insert(String str) {
        insert(str, 1);
    }

    // 建立字典树 / 在字典树中插入 numInsert 个单词
    public void insert(String str, int numInsert) {
        if (str == null || str.length() == 0) {
            return;
        }
        if (numInsert <= 0)
            throw new IllegalArgumentException("numInsert has to be positive");

        TrieNode node = root;
        char[] letters = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {
            TrieNode nextNode = node.children.getOrDefault(letters[i], null);

            // The next character in this string does not yet exist in trie
            if (nextNode == null) {
                node.addChild(letters[i], new TrieNode(letters[i], numInsert));
            } else {
                nextNode.addOccurrence(numInsert);
            }

            node = node.children.get(letters[i]);
        }

        // mark this node is the end of a word in the trie.
        if (node != root)
            node.wordEnding += 1;
    }

    // 在字典树中查找一个完全匹配的单词.
    public boolean contains(String str) {
        return contains(str, true);
    }

    public boolean contains(String str, boolean isWord) {
        if (str == null || str.length() == 0) {
            return false;
        }
        TrieNode node = root;
        char[] letters = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {
            if (node.children.containsKey(letters[i]) && node.children.get(letters[i]).getOccurrence() > 0) {
                node = node.children.get(letters[i]);
            } else {
                return false;
            }
        }

        if (isWord) {
            return node.getOccurrence() > 0 && node.wordEnding > 0;
        } else {
            return node.getOccurrence() > 0;
        }
    }

    // 计算单词前缀的数量
    public int countPrefix(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return -1;
        }
        TrieNode node = root;
        char[] letters = prefix.toCharArray();
        for (int i = 0; i < prefix.length(); i++) {

            if (node.children.containsKey(letters[i]) && node.children.get(letters[i]).getOccurrence() > 0) {
                node = node.children.get(letters[i]);
            } else {
                return 0;
            }
        }
        return node.getOccurrence();
    }

    // 求两个字符串的 LCP（Longest Common Prefix，最长公共前缀）
    public String LCP(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        TrieNode node = root;
        char[] letters = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {
            if (node.children.containsKey(letters[i]) && node.children.get(letters[i]).getOccurrence() > 0) {
                node = node.children.get(letters[i]);
            } else {
                return str.substring(0, i);
            }
        }

        return node.getOccurrence() > 0 ? str : str.substring(0, str.length() - 1);
    }

    // 遍历经过此节点的单词.
    public void preTraverse(TrieNode node, String prefix, boolean isWord) {

        if (isWord) {
            if (node.wordEnding > 0) {
                System.out.println(prefix);
            }
        } else {
            System.out.println(prefix);
        }

        if (node.children.size() > 0) {
            for (TrieNode child : node.children.values()) {
                if (child.getOccurrence() > 0) {
                    preTraverse(child, prefix + child.ch, isWord);
                }
            }
        }
    }

    public static void main(String[] args) {
        Trie tree = new Trie();
        String[] strs = { "banana", "band", "bee", "absolute", "acm", "b", "a"};
        String[] prefixs = { "ba", "b", "ban", "abc", "ac", "abb", "asb", "band", "acm"};

        for (String str : strs) {
            tree.insert(str);
        }

        for (String prefix : prefixs) {
            System.out.println("Contains: " + prefix + " - " + tree.contains(prefix) + " - " + tree.countPrefix(prefix));
            System.out.println("Contain Word: " + prefix + " - " + tree.contains(prefix, true));
            System.out.println("---------");
        }

        tree.preTraverse(tree.getRoot(), "", false);
        System.out.println("---------");
        tree.preTraverse(tree.getRoot(), "", true);
        System.out.println("---------");
        System.out.println(tree.LCP("banana"));
        System.out.println(tree.LCP("bananb"));
        System.out.println(tree.LCP("ban"));
    }
}