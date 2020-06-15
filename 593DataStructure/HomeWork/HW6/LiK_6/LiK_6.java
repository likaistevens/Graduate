import java.util.*;

public class LiK_6 {
    public static double[][] init_MM1K(double c, double u, double lambda) {
        double[][] res = new double[1000][3];
//      randomExponential
        double[] service_Time = new double[1000];
        for (int i = 0; i < 1000; i++) {
            service_Time[i] = -1 / (u * c) * Math.log(Math.random());
        }
//      randomExponential
        double[] arrival_Interval = new double[1000];
        for (int i = 0; i < 1000; i++) {
            arrival_Interval[i] = -1 / lambda * Math.log(Math.random());
        }
//      Arriving Time
        double[] arrival_Time = new double[1000];
        arrival_Time[0] = arrival_Interval[0];
        for (int i = 1; i < 1000; i++) {
            arrival_Time[i] = arrival_Time[i - 1] + arrival_Interval[i];
        }
//      Leaving Time
        double[] leave_Time = new double[1000];
        leave_Time[0] = service_Time[0] + arrival_Time[0];
        for (int i = 1; i < 1000; i++) {
            leave_Time[i] = leave_Time[i - 1] < arrival_Time[i] ? arrival_Time[i] + service_Time[i] : leave_Time[i - 1] + service_Time[i];
        }
        for (int i = 0; i < 1000; i++) {
            double[] temp = new double[3];
            temp[0] = arrival_Time[i];
            temp[1] = service_Time[i];
            temp[2] = leave_Time[i];
            res[i] = temp;
        }
        return res;
    }

    public static double[][] init_MD1K(double C, double mu, double lambda) {
        double[][] res = new double[1000][3];
//      randomExponential
        double[] arrival_Interval = new double[1000];
        for (int i = 0; i < 1000; i++) {
            arrival_Interval[i] = -1 / lambda * Math.log(Math.random());
        }
//      Arriving Time
        double[] arrival_Time = new double[1000];
        arrival_Time[0] = arrival_Interval[0];
        for (int i = 1; i < 1000; i++) {
            arrival_Time[i] = arrival_Time[i - 1] + arrival_Interval[i];
        }
//      Leaving Time
        double[] leave_Time = new double[1000];
        double[] service_Time = new double[1000];
        Arrays.fill(service_Time, 1 / (mu * C));
        leave_Time[0] = service_Time[0] + arrival_Time[0];
        for (int i = 1; i < 1000; i++) {
            leave_Time[i] = leave_Time[i - 1] < arrival_Time[i] ? arrival_Time[i] + service_Time[i] : leave_Time[i - 1] + service_Time[i];
        }
        for (int i = 0; i < 1000; i++) {
            double[] temp = new double[3];
            temp[0] = arrival_Time[i];
            temp[1] = service_Time[i];
            temp[2] = leave_Time[i];
            res[i] = temp;
        }
        return res;
    }

    public static void main(String[] args) {
        int k = 100;
        double C = 1;
        double mu = 1;
        int j = 0;
        double[] waitingTime_MM1K = new double[12];
        double[] waitingTime_MM1KT = new double[12];
        double[] waitingTime_MD1K = new double[12];

        for (double p = 0.1; p <= 1.2; p += 0.1) {
            //  M/M/1/K (simulation)
            myQueue q1 = new myQueue(100);
            double[][] packet1 = init_MM1K(C, mu, p);
            for (int i = 0; i < 1000; i++) {
                q1.add(packet1[i]);
            }
            waitingTime_MM1K[j] = q1.waitingTime / 1000000;
            // M/D/1/K (simulation)
            myQueue q2 = new myQueue(100);
            double[][] packet2 = init_MD1K(C, mu, p);
            for (int i = 0; i < 1000; i++) {
                q2.add(packet2[i]);
            }
            waitingTime_MD1K[j] = q2.waitingTime / 1000000;
            //  M/M/1/K (theoretical)
            double e = p / (1 - p) - (101 * Math.pow(p, 101)) / (1 - Math.pow(p, 101));
            waitingTime_MM1KT[j] = e / 1000;
            
            j++;
        }
        System.out.println("\nρ\t\t M/M/1/K (simulation)\t\tM/M/1/K (theoretical)\t\tM/D/1/K (simulation)");
        
        double p = 0.1;
        for (int i = 0; i < 12; i++) {
            System.out.println((float) p + "\t\t" + waitingTime_MM1K[i] + "\t\t" + waitingTime_MM1KT[i] + "\t\t" + waitingTime_MD1K[i]);
            p += 0.1;
        }
    }
}

class myQueue {
    double[][] packets;
    int head;
    int tail;
    int size;
    int capacity;
    double waitingTime;

    public myQueue(int capacity) {
        this.capacity = capacity;
        this.head = 0;
        this.tail = 0;
        this.size = 0;
        packets = new double[capacity][3];
    }

    public void add(double[] p) {
        if (size == capacity) {
            if (packets[head][2] < p[0]) {
                while (size != 0 && packets[head][2] < p[0]) {
                    head = (head + 1) % capacity;
                    size--;
                }
            } else {
                return;
            }
        }
        waitingTime += size == 0 ? 0 : p[2] - p[0] - p[1];
        packets[tail] = p;
        tail = (tail + 1) % capacity;
        size++;
    }
}
