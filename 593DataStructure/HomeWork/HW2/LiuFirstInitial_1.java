
import java.util.Arrays;
import java.util.Random;

public class assignment2 {
    public static void initializeMatrix(int[][] A, int n){
        Random r = new Random();
        for(int i = 0; i < n; i++){
            for(int j = 0; j<n;j++){
                A[i][j] = r.nextInt(n);
            }
        }
    }
    public static void squareMatrix(int[][] A,int[][] B,int[][] C,int n){
        for(int i = 0; i<n;i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k<n; k++){
                    C[i][j] += A[i][k]*B[k][j];
                }
            }
        }
    }
    public static void strassenMatrix(int[][] A,int[][] B,int[][] C,int n){
        if(n <= 64){
            squareMatrix(A,B,C,n);
            return;
        }
        int halfSize = n /2;

        int[][] A11 = new int[halfSize][halfSize];
        int[][] A12 = new int[halfSize][halfSize];
        int[][] A21 = new int[halfSize][halfSize];
        int[][] A22 = new int[halfSize][halfSize];

        int[][] B11 = new int[halfSize][halfSize];
        int[][] B12 = new int[halfSize][halfSize];
        int[][] B21 = new int[halfSize][halfSize];
        int[][] B22 = new int[halfSize][halfSize];

        int[][] C11 = new int[halfSize][halfSize];
        int[][] C12 = new int[halfSize][halfSize];
        int[][] C21 = new int[halfSize][halfSize];
        int[][] C22 = new int[halfSize][halfSize];

        int[][] S1 = new int[halfSize][halfSize];
        int[][] S2 = new int[halfSize][halfSize];
        int[][] S3 = new int[halfSize][halfSize];
        int[][] S4 = new int[halfSize][halfSize];
        int[][] S5 = new int[halfSize][halfSize];
        int[][] S6 = new int[halfSize][halfSize];
        int[][] S7 = new int[halfSize][halfSize];
        int[][] S8 = new int[halfSize][halfSize];
        int[][] S9 = new int[halfSize][halfSize];
        int[][] S10 = new int[halfSize][halfSize];

        int[][] P1 = new int[halfSize][halfSize];
        int[][] P2 = new int[halfSize][halfSize];
        int[][] P3 = new int[halfSize][halfSize];
        int[][] P4 = new int[halfSize][halfSize];
        int[][] P5 = new int[halfSize][halfSize];
        int[][] P6 = new int[halfSize][halfSize];
        int[][] P7 = new int[halfSize][halfSize];

        for(int i = 0; i < halfSize; i++){
            for(int j = 0; j < halfSize; j++){
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j+halfSize];
                A21[i][j] = A[i+halfSize][j];
                A22[i][j] = A[i+halfSize][j+halfSize];

                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j+halfSize];
                B21[i][j] = B[i+halfSize][j];
                B22[i][j] = B[i+halfSize][j+halfSize];

            }
        }

        matrixSub(B12,B22,S1,halfSize);
        matrixAdd(A11,A12,S2,halfSize);
        matrixAdd(A21,A22,S3,halfSize);
        matrixSub(B21,B11,S4,halfSize);
        matrixAdd(A11,A22,S5,halfSize);
        matrixAdd(B11,B22,S6,halfSize);
        matrixSub(A12,A22,S7,halfSize);
        matrixAdd(B21,B22,S8,halfSize);
        matrixSub(A11,A21,S9,halfSize);
        matrixAdd(B11,B12,S10,halfSize);

        strassenMatrix(A11,S1,P1,halfSize);
        strassenMatrix(S2,B22,P2,halfSize);
        strassenMatrix(S3,B11,P3,halfSize);
        strassenMatrix(A22,S4,P4,halfSize);
        strassenMatrix(S5,S6,P5,halfSize);
        strassenMatrix(S7,S8,P6,halfSize);
        strassenMatrix(S9,S10,P7,halfSize);

        matrixAdd(P5,P4,C11,halfSize);
        matrixSub(C11,P2,C11,halfSize);
        matrixAdd(C11,P6,C11,halfSize);

        matrixAdd(P1,P2,C12,halfSize);

        matrixAdd(P3,P4,C21,halfSize);

        matrixAdd(P5,P1,C22,halfSize);
        matrixSub(C22,P3,C22,halfSize);
        matrixSub(C22,P7,C22,halfSize);

        for(int i = 0; i<halfSize; i++){
            for(int j = 0; j < halfSize; j++){
                C[i][j] = C11[i][j];
                C[i][j+halfSize] = C12[i][j];
                C[i+halfSize][j] = C21[i][j];
                C[i+halfSize][j+halfSize] = C22[i][j];
            }
        }
    }
    public static void matrixAdd(int[][] A, int[][] B, int[][] C, int n){
        for(int i = 0; i < n; i++){
            for(int j = 0; j<n;j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
    }
    public static void matrixSub(int[][] A, int[][] B, int[][] C, int n){
        for(int i = 0; i < n; i++){
            for(int j = 0; j<n;j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
    }
    public static void main(String[] args){
        int n = 2;
        float[] SQ_runTime = new float[10];
        float[] ST_runTime = new float[10];

        int i = 0;
        while(n <= 1024){
            int[][] a = new int[n][n];
            int[][] b = new int[n][n];
            int[][] c = new int[n][n];
            int[][] d = new int[n][n];
            initializeMatrix(a,n);
            initializeMatrix(b,n);
            runTime(a,b,c,d,n,SQ_runTime,ST_runTime,i);
            if(n == 4){
                System.out.println("matrix A:");
                print3(a,n);
                System.out.println();
                System.out.println("matrix B:");
                print3(b,n);
                System.out.println();
                System.out.println("the product matrix by standard method:");
                print3(c,n);
                System.out.println();
                System.out.println("the product matrix by Strassen's method:");
                print3(d,n);
                System.out.println();
            }
            n *= 2;
            i++;
        }
        float SQ_timePerItem = time_per_item(SQ_runTime);
        float ST_timePerItem = time_per_item(ST_runTime);
        System.out.print("\n#n\t\tSQ_t\t\tSQ_o\t\tST_t\t\tST_o\n\n");
        output_run_times(SQ_timePerItem,ST_timePerItem,SQ_runTime,ST_runTime);
    }
    public static void output_run_times(float SQ_timePerItem, float ST_timePerItem, float[] SQ_runTime,float[] ST_runTime){
        int n = 2;
        int i = 0;
        while(n <= 1024){
            System.out.print(n+"\t\t"+SQ_runTime[i]+"\t\t"+SQ_timePerItem*n*n*n);
            System.out.println("\t\t"+ST_runTime[i]+"\t\t"+ST_timePerItem*Math.pow(n,2.81));
            i++;
            n = n * 2;
        }
    }
    public static float time_per_item(float[] t1){
        float t2 = 0;
        int n = 2;
        for(int k = 0; k < 10; k++){
            t2 += t1[k]/n/n;
            n *= 2;
        }
        return t2/10;
    }
    public static void runTime(int[][] A,int[][] B,int[][] C,int[][] D,int n,float[] t1, float[] t2, int i){
        long startTime = System.nanoTime()/1000;
        squareMatrix(A,B,C,n);
        long endTime =  System.nanoTime()/1000;
        t1[i] = endTime - startTime;

        startTime = System.nanoTime()/1000;
        strassenMatrix(A,B,D,n);
        endTime = System.nanoTime()/1000;
        t2[i] = endTime-startTime;
    }
    public static void print3(int[][] A, int n){
        for(int i = 0; i < n; i++){
            for(int j = 0; j<n;j++){
               System.out.print(A[i][j]);
               System.out.print(" ");
            }
            System.out.println();
        }
    }
}
