clear all
close all
clc

load dataSet_1.mat
newpredictor =predictor;
newpredictor(2:4000,:) = (predictor(2:4000,:)-predictor(1:3999,:))./(predictor(2:4000,:));
newpredictor(isinf (newpredictor)) = 0;
newpredictor=[predictor,newpredictor];


%-----top5------
newpredictor1 = newpredictor(:,[323,570,538,774,324]);
factors11=glmfit(newpredictor1, response, 'binomial');
prob=glmval(factors11, newpredictor1, 'logit');
[X, Y, thre1, AUC1]=perfcurve(response, prob,1);
figure(1)
subplot(1,6,1),plot(X,Y),title('top5');
[AUC1]
%-----top10------
newpredictor2 = newpredictor(:,[323,570,538,774,324,471,457,47,101,272]);
factors=glmfit(newpredictor2, response, 'binomial');
prob=glmval(factors, newpredictor2, 'logit');
[X, Y, thre2, AUC2]=perfcurve(response, prob,1);
figure(1)
subplot(1,6,2),plot(X,Y),title('top10');
[AUC2]
%-----top20------
newpredictor3 = newpredictor(:,[323,570,538,774,324,471,457,47,101,272,...
    199,229,201,449,445,369,438,159,114,264]);
factors=glmfit(newpredictor3, response, 'binomial');
prob=glmval(factors, newpredictor3, 'logit');
[X, Y, thre3, AUC3]=perfcurve(response, prob,1);
figure(1)
subplot(1,6,3),plot(X,Y),title('top20');
[AUC3]
%-----top30------
newpredictor4 = newpredictor(:,[175,299,63,95,187,119,387,203,287,444,144,...
152,365,306,324,193,232,348,47,402,94,143,343,295,219,4,328,58,62,250]);
factors=glmfit(newpredictor4, response, 'binomial');
prob=glmval(factors, newpredictor4, 'logit');
[X, Y, thre4, AUC4]=perfcurve(response, prob,1);
figure(1)
subplot(1,6,4),plot(X,Y),title('top30');
[AUC4]
%-----top50------
newpredictor5 = newpredictor(:,[175,299,63,95,187,119,387,203,287,444,144,...
152,365,306,324,193,232,348,47,402,94,143,343,295,219,4,328,58,62,250,407,...
290,240,108,212,430,242,368,427,44,334,273,228,316,53,102,162,186,78,364]);
factors=glmfit(newpredictor5, response, 'binomial');
prob=glmval(factors, newpredictor5, 'logit');
[X, Y, thre5, AUC5]=perfcurve(response, prob,1);
figure(1)
subplot(1,6,5),plot(X,Y),title('top50');
[AUC5]
%-----top80------
newpredictor6 = newpredictor(:,[175,299,63,95,187,119,387,203,287,444,144,...
152,365,306,324,193,232,348,47,402,94,143,343,295,219,4,328,58,62,250,407,...
290,240,108,212,430,242,368,427,44,334,273,228,316,53,102,162,186,78,364,...
178,383,391,447,467,159,173,249,61,64,297,300,442,34,421,190,39,158,172,...
146,281,321,455,83,113,342,451,434,184]);
factors=glmfit(newpredictor6, response, 'binomial');
prob=glmval(factors, newpredictor6, 'logit');
[X, Y, thre6, AUC6]=perfcurve(response, prob,1);
figure(1)
subplot(1,6,6),plot(X,Y),title('top80');
[AUC6]