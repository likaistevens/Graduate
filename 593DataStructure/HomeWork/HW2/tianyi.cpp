#include <iostream>
#include <fstream>
#include <iomanip>
#include <chrono>
#include <math.h>

using namespace std;
using namespace std::chrono;

class Matrix {
private:
    int size;
    int **M;
public:
    int get_Size(){
        return size;
    }
    //overload operator
    int* operator[](int row)const ;
//    Matrix& operator=(const Matrix& M);
    Matrix operator +(const Matrix& right);
    Matrix operator -(const Matrix& right);
    Matrix operator *(const Matrix& right);
    //constractor
    Matrix(int n);
    //copy constractor
    Matrix(const Matrix&copy);
    //init the elements in Matrix
    void Matrix_init();
    ~Matrix();
    //print the M
    void show();
    //split the Matrix to Matrix11 Matrix12 Matrix21 Matrix22
    Matrix split_element(int m,int n);
};

Matrix::Matrix(int n):size(n){
    M=new int* [size];
    for (int i = 0; i < size; ++i) {
        M[i]=new int[size];
    }

}
Matrix::Matrix(const Matrix&copy){
    size=copy.size;
    M=new int* [size];
    for (int i = 0; i < size; ++i) {
        M[i]=new int[size];
    }
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            M[i][j] = copy[i][j];
        }
    }
}
void Matrix::Matrix_init(){
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            M[i][j] = 1+rand() % (size);
        }
    }
}
Matrix::~Matrix(){
    for (int i = 0; i < size; ++i) {
        delete[]M[i];
    }
    delete[]M;
}
int* Matrix::operator[](int row)const{
    return M[row];
}
//Matrix& Matrix::operator=(const Matrix& M){
//    return *this;
//}
Matrix Matrix::operator +(const Matrix& right){
    Matrix add_M(size);
    for (int i = 0; i < size; ++i) {
        for (int j = 0; j < size; ++j) {
            add_M[i][j]=M[i][j]+right[i][j];
        }
    }
    return add_M;
}
Matrix Matrix::operator -(const Matrix& right){
    Matrix sub_M(size);
    for (int i = 0; i < size; ++i) {
        for (int j = 0; j < size; ++j) {
            sub_M[i][j]=M[i][j]-right[i][j];
        }
    }
    return sub_M;
}
Matrix Matrix::operator *(const Matrix& right){
    Matrix mul_M(size);
    for (int m = 0; m < size; m++)
        for (int n = 0; n < size; n++){
            mul_M[m][n] = 0;
            for (int k = 0; k < size; k++)
                mul_M[m][n] += M[m][k] * right[k][n];
        }
    return mul_M;
}
void Matrix::show(){
    cout<<"the M is:"<<endl;
    for (int i = 0; i < size; ++i) {
        for (int j = 0; j < size; ++j) {
            cout<<M[i][j]<<" ";
        }
        cout<<endl;
    }
}

Matrix Matrix::split_element(int m,int n){
    int new_size=size/2;
    Matrix element(new_size);
    for (int i = 0; i < new_size; ++i) {
        for (int j = 0; j < new_size; ++j) {
            if (m==1&&n==1){
                element[i][j]=M[i][j];
            } else if (m==1&&n==2){
                element[i][j]=M[i][j+new_size];
            } else if(m==2&&n==1){
                element[i][j]=M[i+new_size][j];
            } else if(m==2&&n==2){
                element[i][j]=M[i+new_size][j+new_size];
            }
        }
    }
    return element;
}

Matrix Strassen(Matrix& A,Matrix& B, int size){
    if (size==1){
        Matrix mul=A*B;
        return mul;
    } else {
        Matrix C(size);
        Matrix A11 = A.split_element(1, 1);
        Matrix A12 = A.split_element(1, 2);
        Matrix A21 = A.split_element(2, 1);
        Matrix A22 = A.split_element(2, 2);
        Matrix B11 = B.split_element(1, 1);
        Matrix B12 = B.split_element(1, 2);
        Matrix B21 = B.split_element(2, 1);
        Matrix B22 = B.split_element(2, 2);
        Matrix S1 = B12 - B22;
        Matrix S2 = A11 + A12;
        Matrix S3 = A21 + A22;
        Matrix S4 = B21 - B11;
        Matrix S5 = A11 + A22;
        Matrix S6 = B11 + B22;
        Matrix S7 = A12 - A22;
        Matrix S8 = B21 + B22;
        Matrix S9 = A11 - A21;
        Matrix S10 = B11 + B12;
        size /= 2;
        Matrix P1 = Strassen(A11, S1, size);
        Matrix P2 = Strassen(S2, B22, size);
        Matrix P3 = Strassen(S3, B11, size);
        Matrix P4 = Strassen(A22, S4, size);
        Matrix P5 = Strassen(S5, S6, size);
        Matrix P6 = Strassen(S7, S8, size);
        Matrix P7 = Strassen(S9, S10, size);

        Matrix C11 = P5 + P4 - P2 + P6;
        Matrix C12 = P1 + P2;
        Matrix C21 = P3 + P4;
        Matrix C22 = P5 + P1 - P3 - P7;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                C[i][j] = C11[i][j];//11
                C[i][j + size] = C12[i][j];//12
                C[i + size][j] = C21[i][j];//21
                C[i + size][j + size] = C22[i][j];//22
            }

        }
//        cout<<"size is:"<<size<<endl;
//        C.show();
        return C;
    }
}

void run_time(Matrix& A, Matrix& B, float* t1, float* t2);
float time_per_item(float* t1);
void output_run_times(float IS_item_t, float MS_item_t, float* IS_t,float* MS_t);

int main(){
//    int size=4;
//    Matrix A=Matrix(size);
//    Matrix B=Matrix(size);
//    A.show();
//    B.show();
//    cout<<"---------------"<<endl;
//    Matrix mul_A_B=Strassen(A,B,size);
//    mul_A_B.show();
//    cout<<"-----------"<<endl;
//    Matrix mul_A_B_norm=A*B;
//    mul_A_B_norm.show();
//    return 0;
/*----------------------------------*/
    srand(time(NULL));
    float NORMAL_MUL_runtime[10], Strassen_runtime[10];
    float NORMAL_MUL_timeperitem = 0.0, Strassen_timeperitem = 0.0;
    for (int i = 1; i <=10 ; ++i) {
        int size=pow(2,i);
        Matrix A=Matrix(size);
        Matrix B=Matrix(size);
        A.Matrix_init();
        B.Matrix_init();
        run_time(A,B,(NORMAL_MUL_runtime+(i-1)),(Strassen_runtime+(i-1)));
        cout<<"n:"<<i<< left<<" normal time:"<< setw(12)<<NORMAL_MUL_runtime[i-1]<< " STR time:"<<setw(12)<< left<<Strassen_runtime[i-1]<<endl;
    }
    NORMAL_MUL_timeperitem = time_per_item(NORMAL_MUL_runtime);
    Strassen_timeperitem = time_per_item(Strassen_runtime);
    cout << "\n#n  "<< setw(15)<< left<<"NORM_t"<< setw(15)<< left<<"NORM_O"<< setw(15)<< left<<"STR_t"<< setw(15)<< left<<"STR_O"<<endl<<endl;
    output_run_times(NORMAL_MUL_timeperitem, Strassen_timeperitem, NORMAL_MUL_runtime, Strassen_runtime);
}

void run_time(Matrix& A, Matrix& B, float* t1, float* t2) {
    //t1 is the time which normal multiply cost
    //t2 is the time which Strassen cost
    auto start = high_resolution_clock::now();
    A*B;
    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop-start);
    *t1 = 1.0*duration.count();

    start = high_resolution_clock::now();
    Strassen(A,B,A.get_Size());
    cout << endl;
    stop = high_resolution_clock::now();
    duration = duration_cast<microseconds>(stop-start);
    *t2 = 1.0*duration.count();
}

float time_per_item(float* t1) {
    float t2 = 0.0;
    for (int k = 0; k < 10; ++k){
        t2 += t1[k];
    }
    int total_element=0;
    //total=pow(2**n) where n =1,2,....10
    for (int i = 1; i <=10; ++i) {
        total_element+=pow(pow(2,i),2);
    }
    t2 = t2/(total_element);
    return t2;
}

void output_run_times(float IS_item_t, float MS_item_t, float* IS_t,float* MS_t) {
    int n = 1;
    int i = 0;
    while (i < 10) {
        int pow_n=pow(2,n);
        cout << pow_n<< "\t"<<setw(15) << left<< IS_t[i] << setw(15) << left<< IS_item_t*pow(pow_n,3);
        cout << setw(15) << left<< MS_t[i] << setw(15) << left<< MS_item_t*pow(pow_n,2.81);
        cout << endl;
        ++i;
        n +=1;
    }
}