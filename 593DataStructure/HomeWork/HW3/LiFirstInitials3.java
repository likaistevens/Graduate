package datastructure;

import java.util.HashSet;

public class malware {

    public static void Select(int[][] malwares, int n) {
        for (int i = 0; i < n; i++) {
            int score = 0;
            for (int j = 1; j < 11; j++) {
                if (score > 70) {
                    System.out.println("Source <"+ malwares[i][0]  +"> reported a malware with maliciousness score of <"+ score  +"> or higher. "
                + "The source found after interviewing <"+ i  +"> candidates.");
                    return;
                }
                score += malwares[i][j];
            }
        }
        //printmalwares(malwares);
        System.out.println("None of the reports met the threat threshold.");
    }

    public static int[][] randommalwares(int[][] malwares, int n) {
        int[] tem = new int[n];
        int[] order = new int[n];
        HashSet<Integer> set = new HashSet<>();
        int cur = 0;

        while (set.size() < n) {
            int r = (int) (Math.random() * n);
            if (!set.contains(r)) {
                order[cur] = r;
                cur++;
                set.add(r);
            }
        }

        for (int i = 0; i < n; i++) {
            tem[i] = i;
        }

        int[][] temp = initialarray(n);
        for (int i = 0; i < n; i++) {
            temp[i] = malwares[order[i]];
        }
        return temp;
    }

    public static int[][] initialarray(int n) {
        int[][] malwares = new int[n][11];
        for (int i = 0; i < n; i++) {
            malwares[i] = generateArray();
            malwares[i][0] = i;
        }
        return malwares;
    }

    public static int[] generateArray() {
        int[] array = new int[11];
        for (int i = 1; i < 11; i++) {
            array[i] = (int) (Math.random() * 10);
        }
        return array;
    }

    public static void printmalwares(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.print("-------" + sumarray(array[i]));
//            if (sumarray(array[i]) > 70) {
//                System.out.print("   ***Out of threshold***");
//            }
            System.out.println();
        }
    }

    public static int sumarray(int[] array) {
        int sum = 0;
        for (int i = 1; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

//    public static void main(String[] args) {
//        int n = 10;
//        while (n <= 1000) {
//            int[][] malwares = initialarray(n);
//            int[][] randommalwares = randommalwares(malwares, n);
//            printmalwares(Select(malwares, n));
//            System.out.println("——————————————");
//            printmalwares(Select(randommalwares, n));
//            System.out.println("——————————————");
//            n *= 10;
//        }
//    }
    public static void main(String[] args) {
        int n = 10;
        double[] OS_runTime = new double[4];
        double[] RS_runTime = new double[4];

        int i = 0;
        while (n <= 10000) {
            int[][] malwares = initialarray(n);
            int[][] randommalwares = randommalwares(malwares, n);
            runTime(malwares, randommalwares, n, OS_runTime, RS_runTime, i);
            n *= 10;
            i++;
        }
        
        System.out.print("\n#n\t\tOS_t\t\tRS_t\n\n");
        output_run_times(OS_runTime, RS_runTime);
    }

    public static void output_run_times(double[] OS_runTime, double[] RS_runTime) {
        int n = 10;
        int i = 0;
        while (n <= 10000) {
            System.out.print(n + "\t\t" + OS_runTime[i]);
            System.out.println("\t\t" + RS_runTime[i]);
            i++;
            n = n * 10;
        }
    }


    public static void runTime(int[][] malwares,int[][] randommalwares,int n, double[] t1, double[] t2, int i) {
        double startTime = System.nanoTime() / 1000;
        Select(malwares,n);
        //System.out.println("ordered end");
        double endTime = System.nanoTime() / 1000;
        t1[i] = endTime - startTime;
        
        startTime = System.nanoTime() / 1000;
        Select(randommalwares,n);
        //System.out.println("random end");
        endTime = System.nanoTime() / 1000;
        t2[i] = endTime - startTime;
    }
}
