package SuffixArray;

public class SuffixArray {

    private static class Suffix implements Comparable<Suffix> {
        // Starting position of suffix in text
        final int index, len;
        final int[] text;

        public Suffix(int[] text, int index) {
            this.len = text.length - index;
            this.index = index;
            this.text = text;
        }

        // Compare the two suffixes inspired by Robert Sedgewick and Kevin Wayne
        @Override
        public int compareTo(Suffix other) {
            if (this == other)
                return 0;
            int min_len = Math.min(len, other.len);
            for (int i = 0; i < min_len; i++) {
                if (text[index + i] < other.text[other.index + i])
                    return -1;
                if (text[index + i] > other.text[other.index + i])
                    return +1;
            }
            return len - other.len;
        }

        @Override
        public String toString() {
            return new String(text, index, len);
        }
    }

    public static void main(String[] args) {
        SuffixArray sa = new SuffixArray("ABBABAABAAC");
        System.out.println(sa);
    }

    // Length of the suffix array
    private final int N;

    // T is the text
    private int[] T;

    // Contains all the suffixes of the SuffixArray
    private Suffix[] suffixes;

    // The sorted suffix array values.
    private int[] sa;

    // Longest Common Prefix array
    private int[] lcp;

    private boolean constructedSA = false;
    private boolean constructedLCP = false;

    public SuffixArray(String text) {
        if (text == null)
            throw new IllegalArgumentException("Text cannot be null.");
        this.T = toIntArray(text);
        this.N = text.length();
        buildLcpArray();
    }

    public int getTextLength() {
        return T.length;
    }

    // Returns the suffix array.
    public int[] getSa() {
        buildSuffixArray();
        return sa;
    }

    // Returns the LCP array.
    public int[] getLcpArray() {
        buildLcpArray();
        return lcp;
    }

    // The suffix array construction algorithm, there are multiple ways to do this.
    public void construct() {
        constructSlow();
    }

    // Builds the suffix array by calling the construct() method.
    public void buildSuffixArray() {
        if (constructedSA)
            return;
        construct();
        constructedSA = true;
    }

    // Builds the LCP array by first creating the SA and then running the kasai
    // algorithm.
    public void buildLcpArray() {
        if (constructedLCP)
            return;
        buildSuffixArray();
        kasai();
        constructedLCP = true;
    }

    private static int[] toIntArray(String s) {
        if (s == null)
            return null;
        int[] t = new int[s.length()];
        for (int i = 0; i < s.length(); i++)
            t[i] = s.charAt(i);
        return t;
    }

    // Use Kasai algorithm to build LCP array
    // http://www.mi.fu-berlin.de/wiki/pub/ABI/RnaSeqP4/suffix-array.pdf
    private void kasai() {
        lcp = new int[N];
        int[] inv = new int[N];

        for (int i = 0; i < N; i++)
            inv[sa[i]] = i;

        for (int i = 0; i < N; i++) {
            int len = 0;
            if (inv[i] > 0) {
                int k = sa[inv[i] - 1];
                while ((i + len < N) && (k + len < N) && T[i + len] == T[k + len])
                    len++;
                lcp[inv[i]] = len;
                if (len > 0)
                    len--;
            }
        }
    }

    // Compared based Suffix array construction.
    // This actually takes O(n^2log(n)) time complexity since sorting takes on
    // average O(nlog(n)) and each String comparison takes O(n).
    private void constructSlow() {
        sa = new int[N];
        suffixes = new Suffix[N];

        for (int i = 0; i < N; i++)
            suffixes[i] = new Suffix(T, i);

        java.util.Arrays.sort(suffixes);

        for (int i = 0; i < N; i++) {
            Suffix suffix = suffixes[i];
            sa[i] = suffix.index;
            suffixes[i] = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----i-----SA-----LCP---Suffix\n");

        for (int i = 0; i < N; i++) {
            int suffixLen = N - sa[i];
            char[] suffixArray = new char[suffixLen];
            for (int j = sa[i], k = 0; j < N; j++, k++)
                suffixArray[k] = (char) T[j];
            String suffix = new String(suffixArray);
            String formattedStr = String.format("% 7d % 7d % 7d %s\n", i, sa[i], lcp[i], suffix);
            sb.append(formattedStr);
        }
        return sb.toString();
    }
}
