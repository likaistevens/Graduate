%M = csvread('Newinput.csv');
M = xlsread('Newinput.xls'); 
N = 120000;
P1 = 0.80075;
P2 = 0.83256;
P3 = 0.85442;
P4 = 0.74292;
STX = [N*(2*P1-1);N*(2*P2-1);N*(2*P3-1);N*(2*P4-1)];
INVTM = inv(M'*M);
M*INVTM*STX