package SuffixArray;

public class SuffixArray {

    // Length of the suffix array
    private final int N;

    // the original text
    private final String text;

    // The sorted suffix array values.
    private int[] sa;

    // Longest Common Prefix array
    private int[] lcp;


    public SuffixArray(String text) {
        if (text == null)
            throw new IllegalArgumentException("Text cannot be null.");
        this.text = text;
        this.N = text.length();
        // must build suffix array first.
        buildSuffixArray();
        buildLcpArray();
    }

    public int getTextLength() {
        return this.N;
    }

    // Returns the suffix array.
    public int[] getSa() {
        return sa;
    }

    // Returns the LCP array.
    public int[] getLcpArray() {
        return lcp;
    }

    // Builds the suffix array, there are multiple ways to do this, we implement the
    // simplest one .
    public void buildSuffixArray() {
        constructSlow();
    }

    // Builds the LCP array by the kasai algorithm.
    // NOTICE: this method must be called after Suffix Array construction.
    // http://www.mi.fu-berlin.de/wiki/pub/ABI/RnaSeqP4/suffix-array.pdf
    public void buildLcpArray() {
        if(sa == null){
            throw new IllegalArgumentException("Must initialize the Suffix Array first!");
        }
        lcp = new int[N];
        int[] inv = new int[N];

        for (int i = 0; i < N; i++)
            inv[sa[i]] = i;

        for (int i = 0; i < N; i++) {
            int len = 0;
            if (inv[i] > 0) {
                int k = sa[inv[i] - 1];
                while ((i + len < N) && (k + len < N) && text.charAt(i + len) == text.charAt(k + len))
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
        String[] tmp = new String[N];
        for (int i = 0; i < N; i++) {
            tmp[i] = text.substring(i);
        }

        // sort the suffix array based on the lexicographical order.
        java.util.Arrays.sort(tmp);

        sa = new int[N];
        for (int i = 0; i < N; i++) {
            int k = tmp[i].length();
            sa[i] = N - k;
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
                suffixArray[k] = text.charAt(j);
            String suffix = new String(suffixArray);
            String formattedStr = String.format("% 7d % 7d % 7d %s\n", i, sa[i], lcp[i], suffix);
            sb.append(formattedStr);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        SuffixArray sa = new SuffixArray("ABBABAABAAC");
        System.out.println(sa);
    }
}
