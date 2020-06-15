#include <iostream>
#include <fstream>
#include <iomanip>
#include <chrono>
#include <math.h>

using namespace std;
using namespace std::chrono;

void Mul(int n, int **A, int **B, int **C);

int **Sub(int **A, int **B, int n);
int **Add(int **A, int **B, int n);
void Strassen(int **A, int **B, int **C, int n);

void run_time(int **A, int **B, int **C, int n, double* t1, double* t2);
double time_per_item(double* t1);
void output_run_times(double Mul_item_t, double Str_item_t, double* Mul_t, double* Str_t);

void Mul(int n, int **A, int **B, int **C){
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            C[i][j] = 0;
            for(int k = 0; k < n; k++){
                C[i][j] += A[i][k]*B[k][j];
            }
        }
    }
}

void Strassen(int **A, int **B, int **C, int n){
    int h = n / 2;
    if(n == 1){
        C[0][0] = A[0][0] * B[0][0];
        return;
    }

    int **A11 = new int *[h];
    int **A12 = new int *[h];
    int **A21 = new int *[h];
    int **A22 = new int *[h];

    int **B11 = new int *[h];
    int **B12 = new int *[h];
    int **B21 = new int *[h];
    int **B22 = new int *[h];

    int **C11 = new int *[h];
    int **C12 = new int *[h];
    int **C21 = new int *[h];
    int **C22 = new int *[h];

    int **P1 = new int *[h];
    int **P2 = new int *[h];
    int **P3 = new int *[h];
    int **P4 = new int *[h];
    int **P5 = new int *[h];
    int **P6 = new int *[h];
    int **P7 = new int *[h];

    for(int i = 0; i < h;i++){
        A11[i] = new int[h];
        A12[i] = new int[h];
        A21[i] = new int[h];
        A22[i] = new int[h];

        B11[i] = new int[h];
        B12[i] = new int[h];
        B21[i] = new int[h];
        B22[i] = new int[h];

        C11[i] = new int[h];
        C12[i] = new int[h];
        C21[i] = new int[h];
        C22[i] = new int[h];

        P1[i] = new int[h];
        P2[i] = new int[h];
        P3[i] = new int[h];
        P4[i] = new int[h];
        P5[i] = new int[h];
        P6[i] = new int[h];
        P7[i] = new int[h];
    }

    for(int i = 0; i < h; i ++){
        for(int j = 0; j < h; j ++){
            A11[i][j] = A[i][j];
            A12[i][j] = A[i][j + h];
            A21[i][j] = A[i + h][j];
            A22[i][j] = A[i + h][j + h];
           
            B11[i][j] = B[i][j];
            B12[i][j] = B[i][j + h];
            B21[i][j] = B[i + h][j];
            B22[i][j] = B[i + h][j + h];
        }
    }

    Strassen(Add(A11,A22,h),Add(B11,B22,h),P5,h);
    Strassen(Sub(A12,A22,h),Add(B21,B22,h),P6,h);
    Strassen(Sub(A11,A21,h),Add(B11,B12,h),P7,h);
    Strassen(A11,Sub(B12,B22,h),P1,h);
    Strassen(Add(A11,A12,h),B22,P2,h);
    Strassen(Add(A21,A22,h),B11,P3,h);
    Strassen(A22,Sub(B21,B11,h),P4,h);
    
    for (int i = 0; i < h; i++)
     {
        for (int j = 0; j < h; j++)
        {
            C[i][j] = P5[i][j] + P4[i][j] - P2[i][j] + P6[i][j];
            C[i][j + h] = P1[i][j] + P2[i][j];
            C[i + h][j] = P3[i][j] + P4[i][j];
            C[i + h][j + h] = P1[i][j] + P5[i][j] - P3[i][j] - P7[i][j];
        }
    }
}

int **Add(int **A, int **B, int n){
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

int **Sub(int **A, int **B, int n){
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

void run_time(int **A, int **B, int **C, int n, double* t1, double* t2) {
    auto start = high_resolution_clock::now();
    Mul(n, A, B, C);
    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop-start);
    *t1 = 1.0*duration.count();

    start = high_resolution_clock::now();
    Strassen(A,B,C,n);
    stop = high_resolution_clock::now();
    duration = duration_cast<microseconds>(stop-start);
    *t2 = 1.0*duration.count();   
}

double time_per_item(double* t1) {
    double t2 = 0.0;
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

void output_run_times(double Mul_item_t, double Str_item_t, double* Mul_t, double* Str_t) {
    int n = 1;
    int i = 0;
    while (i < 10) {
        int n2=pow(2,n);
        cout << n2 << "\t\t" << Mul_t[i] << "\t\t" << Mul_item_t * pow(n2, 3); 
        cout << "\t\t" << Str_t[i] << "\t\t" << Str_item_t * pow(n2, 2.81);
        cout << endl;
        n += 1;
        ++ i;
    }
}

int main()
{
    srand(time(NULL));
    double Str_runtime[10], Mul_runtime[10];
    double Str_timeperitem = 0.0, Mul_timeperitem = 0.0;
    int n = 2;
    unsigned int loop = 0;

    for (int i = 1; i <= 10 ; ++ i) {
        int n = pow(2,i);
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
       for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                A[i][j] = rand() % (n + 1);
                B[i][j] = rand() % (n + 1);
            }
        }
        run_time(A, B, C, n, &Mul_runtime[loop], &Str_runtime[loop]);
        loop++;
    }

    Str_timeperitem = time_per_item(Str_runtime);
    Mul_timeperitem = time_per_item(Mul_runtime);
    
    cout << "\n#n\t\tMul_TIME\t\tMul_TIM_BOUND\t\tStr_TIME\t\tStr_TIME_BOUND\n\n";
    output_run_times(Mul_timeperitem, Str_timeperitem, Mul_runtime, Str_runtime);
    return 0;
}