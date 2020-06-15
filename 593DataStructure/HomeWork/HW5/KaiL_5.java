import java.util.*;

public class KaiL_5 {
    // A[n] = k      get number n k times
    public static void Initial_A(int[] A, int n) {
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i] = (int) (Math.random() * 13 + 1);
        }
        for (int i : temp) {
            A[i]++;
        }
    }

    // B[i] = A[i]/n
    public static void Initial_B(int[] A, double[] B, int n) {
        for (int i = 0; i < A.length; i++) {
            B[i] = ((double) A[i]) / n;
        }
    }

    // text book 9.3
    public static int Randomized_Select(int[] A, int p, int r, int i) {
        if (p == r) {
            return A[p];
        }
        int q = Randomized_Partition(A, p, r);
        int k = q - p + 1;
        if (i == k) {
            return A[q];
        } else if (i < k) {
            return Randomized_Select(A, p, q - 1, i);
        } else {
            return Randomized_Select(A, q + 1, r, i - k);
        }
    }

    // text book 7.3
    public static int Randomized_Partition(int[] A, int p, int r) {
        Random rand = new Random();
        int i = rand.nextInt(r - p + 1) + p;
        int temp = A[i];
        A[i] = A[r];
        A[r] = temp;
        return Partition(A, p, r);
    }

    // text book 7.1
    public static int Partition(int[] A, int p, int r) {
        int x = A[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (A[j] <= x) {
                i = i + 1;
                int temp1 = A[i];
                A[i] = A[j];
                A[j] = temp1;
            }
        }
        int temp2 = A[i + 1];
        A[i + 1] = A[r];
        A[r] = temp2;
        return i + 1;
    }

    // text book 8.2
    public static void Counting_Sort(int[] A, int[] B, int k) {
        int[] C = new int[k + 1];
        for (int j = 1; j < A.length; j++) {
            C[A[j]]++;
        }
        // C[i] now contains the number of element equal to i
        for (int i = 1; i < C.length; i++) {
            C[i] = C[i] + C[i - 1];
        }
        // C[i] now contains the number of element less than or equal to i
        for (int j = A.length - 1; j >= 1; j--) {
            B[C[A[j]]] = A[j];
            C[A[j]]--;
        }
    }

    // text book 8.4
    public static double[] Bucket_Sort(double[] A) {
        ArrayList<Double>[] B = new ArrayList[11];
        int n = 10, j = 1;
        for (int i = 0; i <= n; i++) {
            B[i] = new ArrayList<Double>();
        }
        for (int i = 1; i < A.length; i++) {
            B[(int)(A[i]*n)].add(A[i]);
        }
        double[] res = new double[14];
        for (int i = 0; i <= 10; i++) {
            Collections.sort(B[i]);
            for (double k : B[i]) {
                res[j] = k;
                j ++;
            }
        }
        return res;
    }

    public static int indexOf(int[] A, int n) {
        for (int i = 1; i < A.length; i++) {
            if (A[i] == n) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOf(double[] A, double n) {
        for (int i = 1; i < A.length; i++) {
            if (A[i] == n) {
                return i;
            }
        }
        return -1;
    }

    public static void output_run_times(double[] RS_runtime, double[] CS_runtime, double[] BS_runtime, int[] lucky_num, int[] unlucky_num, double[] lucky_prob, double[] unlucky_prob) {
        System.out.println("\n#n\t\tRanddom_Select\t\tCount_Sort\t\tBucket_Sort");
        int n = 10;
        for (int k = 0; k < 100; k++) {
            System.out.println(n + "\t\t" + RS_runtime[k] + "\t\t\t" + CS_runtime[k] + "\t\t\t" + BS_runtime[k]);
            n += 10;
        }

        System.out.println("\n#n\tlucky_num\tlucky_prob\tunlucky_num\tunlucky_prob");
        int[] arr = new int[]{10, 100, 500, 1000};
        for (int k = 0; k < 4; k++) {
            System.out.println(arr[k] + "\t\t" + lucky_num[k] + "\t\t" + lucky_prob[k] + "\t\t" + unlucky_num[k] + "\t\t" + unlucky_prob[k]);
        }
    }

    public static void runTime(int[] A, double[] RS_runtime, double[] CS_runtime, double[] BS_runtime, int[] lucky_num, int[] unlucky_num, double[] lucky_prob, double[] unlucky_prob, int i, int n) {
        int lucky_number = 0;
        int unlucky_number = 0;
        
        int[] A1 = A.clone();
        int[] A2 = A.clone();
        long startTime = System.nanoTime();
        lucky_number = indexOf(A, Randomized_Select(A1, 1, 13, 1));
        unlucky_number = indexOf(A, Randomized_Select(A2, 1, 13, 13));
        long endTime = System.nanoTime();
        RS_runtime[i] = endTime - startTime;

        int[] CS = new int[14];
        startTime = System.nanoTime();
        Counting_Sort(A, CS, n);
        lucky_number = indexOf(A, CS[1]);
        unlucky_number = indexOf(A, CS[13]);
        endTime = System.nanoTime();
        CS_runtime[i] = endTime - startTime;

        double[] B = new double[14];
        Initial_B(A, B, n);
        startTime = System.nanoTime();
        double[] BS = Bucket_Sort(B);
        lucky_number = indexOf(B, BS[1]);
        unlucky_number = indexOf(B, BS[13]);
        endTime = System.nanoTime();
        BS_runtime[i] = endTime - startTime;

        if (n == 10 || n == 100 || n == 500 || n == 1000) {
            lucky_num[j] = lucky_number;
            lucky_prob[j] = ((double) A[lucky_number]) / n;
            unlucky_num[j] = unlucky_number;
            unlucky_prob[j] = ((double) A[unlucky_number]) / n;
            j++;
        }
    }

    static int j = 0;

    public static void main(String[] arg) {
        double[] RS_runtime = new double[100];
        double[] CS_runtime = new double[100];
        double[] BS_runtime = new double[100];
        int[] lucky_num = new int[4];
        int[] unlucky_num = new int[4];
        double[] lucky_prob = new double[4];
        double[] unlucky_prob = new double[4];

        int i = 0, n = 10;
        while (n <= 1000) {
            int[] A = new int[14];
            Initial_A(A, n);
            runTime(A, RS_runtime, CS_runtime, BS_runtime, lucky_num, unlucky_num, lucky_prob, unlucky_prob, i, n);
            n += 10;
            i++;
        }
        output_run_times(RS_runtime, CS_runtime, BS_runtime, lucky_num, unlucky_num, lucky_prob, unlucky_prob);
    }
}
