import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author kaili
 */
public class KaiL_4 {

    public static int[] initialize(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    private static int partition(int[] arr, int l, int r) {
        swap(arr, l, (int) Math.random() * (r - l + 1) + l);
        int v = arr[l];
        int i = l + 1, j = r;
        while (true) {
            while (i <= r && arr[i] < v) {
                i++;
            }
            while (j >= l + 1 && arr[j] > v) {
                j--;
            }
            if (i >= j) {
                break;
            }
            swap(arr, i, j);
            i++;
            j--;
        }
        int t = arr[l];
        arr[l] = arr[j];
        arr[j] = t;
        return j;
    }
    // Recursive call quick sort, Sort arr with range[l...r]
    private static void quickSort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int p = partition(arr, l, r);
        quickSort(arr, l, p - 1);
        quickSort(arr, p + 1, r);
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static int waitingTime(int[] arr, int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }
        return sum;
    }

    public static int queueingTime(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i ++) {
            sum += waitingTime(arr, i) + arr[i];
        }
        return sum / arr.length;
    }
    
    public static void main(String[] args) {
        int[] QTA = new int[11];
        int[] QTB = new int[11];
        int[] QTC = new int[11];

        int[] WTA1 = new int[11];
        int[] WTA2 = new int[11];
        int[] WTA3 = new int[11];

        int[] WTB1 = new int[11];
        int[] WTB2 = new int[11];
        int[] WTB3 = new int[11];

        int[] WTC1 = new int[11];
        int[] WTC2 = new int[11];
        int[] WTC3 = new int[11];

        int i = 0;
        int n = 10;
        while (n <= 1010) {
            int[] A = initialize(n);
            int[] B = Arrays.copyOf(A, n);

            double startTime = System.nanoTime() / 1000;
            quickSort(B,0,n-1);
            double endTime = System.nanoTime() / 1000;
            int sortTime = (int)(endTime - startTime);

            int[] C = new int[n];
            for (int k = 0; k < n; k++) {
                C[n - 1 - k] = B[k];
            }

            WTA1[i] = waitingTime(A, n / 3);
            WTA2[i] = waitingTime(A, n / 3 * 2);
            WTA3[i] = waitingTime(A, n);

            WTB1[i] = waitingTime(B, n / 3) + (sortTime * ((n - 1) / 3 - 1)) / n;
            WTB2[i] = waitingTime(B, n / 3 * 2) + (sortTime * ((n - 1) / 3 * 2 - 1)) / n;
            WTB3[i] = waitingTime(B, n) + (sortTime * (n - 1)) / n;

            WTC1[i] = waitingTime(C, n / 3) + (sortTime * (n / 3 - 1)) / n;
            WTC2[i] = waitingTime(C, n / 3 * 2) + (sortTime * (n / 3 * 2 - 1)) / n;
            WTC3[i] = waitingTime(C, n) + (sortTime * (n - 1)) / n;

            QTA[i] = queueingTime(A);
            QTB[i] = queueingTime(B) + sortTime / n;
            QTC[i] = queueingTime(C) + sortTime / n;

            n += 100;
            i++;
        }
        output_run_times(QTA,QTB,QTC,WTA1,WTA2,WTA3,WTB1,WTB2,WTB3,WTC1,WTC2,WTC3);
    }

    public static void output_run_times(int[] QTA,int[] QTB,int[] QTC,int[] WTA1,int[] WTA2,int[] WTA3,int[] WTB1,int[] WTB2,int[] WTB3,int[] WTC1,int[] WTC2,int[] WTC3) {
        System.out.print("Average Queue Time :");
        System.out.println("\nn\t\tA\t\tB\t\tC");
        int i = 0, j = 10;
        while (j <= 1010) {
            System.out.println(j + "\t\t" + QTA[i] + "\t\t" + QTB[i] + "\t\t" + QTC[i]);
            i++;
            j += 100;
        }
        System.out.println();
        System.out.print("Waiting Time :");
        System.out.println("\nn\t\tA:n/3\t\tB:n/3\t\tC:n/3\t\tA:2n/3\t\tB:2n/3\t\tC:2n/3\t\tA:n\t\tB:n\t\tC:n");
        i = 0;
        j = 10;
        while (j <= 1010) {
            System.out.println(j + "\t\t" + WTA1[i] + "\t\t" + WTB1[i] + "\t\t" + WTC1[i] + "\t\t" + WTA2[i] + "\t\t" + WTB2[i] + "\t\t" + WTC2[i] + "\t\t" + WTA3[i] + "\t\t" + WTB3[i] + "\t\t" + WTC3[i]);
            i++;
            j += 100;
        }
    }
}
