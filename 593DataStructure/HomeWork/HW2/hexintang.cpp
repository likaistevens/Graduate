#include <iostream>
#include <fstream>
#include <iomanip>
#include <chrono>
#include <math.h>

using namespace std;
using namespace std::chrono;

void initialize_matrix(int **array, int num);
void SQUARE_MATRIX_MULTIPLY(int **A, int **B, int **C, int n);
void print1(int **A,int n);
void STRASSEN_MATRIX_MULTIPLY(int **A, int **B, int **C, int n);
int **MARTIX_SUB(int **A, int **B, int n);
int **MARTIX_ADD(int **A, int **B, int n);
void run_time(int **A, int **B, int **C, int n, float* t1, float* t2);
float time_per_item(float* t1);
void output_run_times(float QM_item_t, float SM_item_t, float* QM_t,\
float* SM_t);

int main()
{
    srand(time(NULL));
    float SM_runtime[10], QM_runtime[10];
    float SM_timeperitem = 0.0, QM_timeperitem = 0.0;
    unsigned int n = 2;
    unsigned int loop = 0;

    while (n <= 128)
    {
        int **A = new int *[n];
        int **B = new int *[n];
        int **C = new int *[n];
        int **D = new int *[n];
        for (int i = 0; i < n; i++)
        {
            A[i] = new int[n];
            B[i] = new int[n];
            C[i] = new int[n];
            D[i] = new int[n];
        }
        initialize_matrix(A, n);
        initialize_matrix(B, n);
        // STRASSEN_MATRIX_MULTIPLY(A,B,C,n);
        // SQUARE_MATRIX_MULTIPLY(A,B,D,n);

        run_time(A, B, C, n, &QM_runtime[loop], &SM_runtime[loop]);

        n = n * 2;
        loop++;
    }

    SM_timeperitem = time_per_item(SM_runtime);
    QM_timeperitem = time_per_item(QM_runtime);
    
    cout << "\n#n\t\tQM_TIME\t\tB_QM_TIME\t\tSM_TIME\t\tB_SM_TIME\n\n";
    output_run_times(QM_timeperitem, SM_timeperitem, QM_runtime, SM_runtime);
    return 0;
}

void initialize_matrix(int **array, int n)
{
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            array[i][j] = rand() % (n);
        }
    }
}

void STRASSEN_MATRIX_MULTIPLY(int **A, int **B, int **C, int n){
    if(n == 1){
        C[0][0] = A[0][0] * B[0][0];
        return;
    }
    int halfsize = n / 2;

    int **A11 = new int *[halfsize];
    int **A12 = new int *[halfsize];
    int **A21 = new int *[halfsize];
    int **A22 = new int *[halfsize];
    int **B11 = new int *[halfsize];
    int **B12 = new int *[halfsize];
    int **B21 = new int *[halfsize];
    int **B22 = new int *[halfsize];
    int **C11 = new int *[halfsize];
    int **C12 = new int *[halfsize];
    int **C21 = new int *[halfsize];
    int **C22 = new int *[halfsize];

    int **P1 = new int *[halfsize];
    int **P2 = new int *[halfsize];
    int **P3 = new int *[halfsize];
    int **P4 = new int *[halfsize];
    int **P5 = new int *[halfsize];
    int **P6 = new int *[halfsize];
    int **P7 = new int *[halfsize];

    for(int i = 0; i < halfsize;i++){

        A11[i] = new int[halfsize];
        A12[i] = new int[halfsize];
        A21[i] = new int[halfsize];
        A22[i] = new int[halfsize];
        B11[i] = new int[halfsize];
        B12[i] = new int[halfsize];
        B21[i] = new int[halfsize];
        B22[i] = new int[halfsize];
        C11[i] = new int[halfsize];
        C12[i] = new int[halfsize];
        C21[i] = new int[halfsize];
        C22[i] = new int[halfsize];

        P1[i] = new int[halfsize];
        P2[i] = new int[halfsize];
        P3[i] = new int[halfsize];
        P4[i] = new int[halfsize];
        P5[i] = new int[halfsize];
        P6[i] = new int[halfsize];
        P7[i] = new int[halfsize];
       
    }
    for(int i = 0; i < halfsize; i++){
        for(int j = 0; j < halfsize; j++){
            A11[i][j] = A[i][j];
            A12[i][j] = A[i][j+halfsize];
            A21[i][j] = A[i+halfsize][j];
            A22[i][j] = A[i+halfsize][j+halfsize];
           
            B11[i][j] = B[i][j];
            B12[i][j] = B[i][j+halfsize];
            B21[i][j] = B[i+halfsize][j];
            B22[i][j] = B[i+halfsize][j+halfsize];

        }
    }

    STRASSEN_MATRIX_MULTIPLY(A11,MARTIX_SUB(B12,B22,halfsize),P1,halfsize);
    STRASSEN_MATRIX_MULTIPLY(MARTIX_ADD(A11,A12,halfsize),B22,P2,halfsize);
    STRASSEN_MATRIX_MULTIPLY(MARTIX_ADD(A21,A22,halfsize),B11,P3,halfsize);
    STRASSEN_MATRIX_MULTIPLY(A22,MARTIX_SUB(B21,B11,halfsize),P4,halfsize);
    STRASSEN_MATRIX_MULTIPLY(MARTIX_ADD(A11,A22,halfsize),MARTIX_ADD(B11,B22,halfsize),P5,halfsize);
    STRASSEN_MATRIX_MULTIPLY(MARTIX_SUB(A12,A22,halfsize),MARTIX_ADD(B21,B22,halfsize),P6,halfsize);
    STRASSEN_MATRIX_MULTIPLY(MARTIX_SUB(A11,A21,halfsize),MARTIX_ADD(B11,B12,halfsize),P7,halfsize);

    for (int i = 0; i < halfsize; i++)
     {
        for (int j = 0; j < halfsize; j++)
        {
            C[i][j] = P5[i][j] + P4[i][j] - P2[i][j] + P6[i][j];
            C[i][j + halfsize] = P1[i][j] + P2[i][j];
            C[i + halfsize][j] = P3[i][j] + P4[i][j];
            C[i + halfsize][j + halfsize] = P1[i][j] + P5[i][j] - P3[i][j] - P7[i][j];
        }
    }
}
int **MARTIX_ADD(int **A, int **B, int n){
    int** C = new int*[n];
    for(int i=0;i<n;i++){
        C[i] = new int[n];
    }
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            C[i][j] = A[i][j] + B[i][j];
        }
    } 
    return C;   
}
int **MARTIX_SUB(int **A, int **B, int n){
    int** C = new int*[n];
    for(int i=0;i<n;i++){
        C[i] = new int[n];
    }
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            C[i][j] = A[i][j] - B[i][j];
        }
    }
    return C;
}


void SQUARE_MATRIX_MULTIPLY(int **A, int **B, int **C, int n){
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            C[i][j] = 0;
            for(int k = 0; k < n; k++){
                C[i][j] += A[i][k]*B[k][j];
            }
        }
    }
}

void print1(int **A, int n){
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            printf("%d ",A[i][j]);
        }
        printf("\n");
    }
}

void run_time(int **A, int **B, int **C, int n, float* t1, float* t2) {

    auto start = high_resolution_clock::now();
    SQUARE_MATRIX_MULTIPLY(A, B, C,n);
    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop-start);
    *t1 = 1.0*duration.count();

    start = high_resolution_clock::now();
    STRASSEN_MATRIX_MULTIPLY(A,B,C,n);
    stop = high_resolution_clock::now();
    duration = duration_cast<microseconds>(stop-start);
    *t2 = 1.0*duration.count();   
}

float time_per_item(float* t1) {
    float t2 = 0.0;
    for (int k = 0; k < 10; ++k){
        t2 += t1[k];
    }
    int total=0;
    for (int i = 1; i <=10; ++i) {
        total+=pow(pow(2,i),2);
    }
    t2 = t2/(total);
    return t2;
}

void output_run_times(float QM_item_t, float SM_item_t, float* QM_t,\
float* SM_t) {
    int n = 1;
    int i = 0;
    while (i < 10) {
        int pow_n=pow(2,n);
        cout << n << "\t\t" << QM_t[i] << "\t\t" << QM_item_t*pow(pow_n,3); 
        cout << "\t\t" << SM_t[i] << "\t\t" << SM_item_t*pow(pow_n,2.81);
        cout << endl;
        ++i;
        n +=1;
    }
}