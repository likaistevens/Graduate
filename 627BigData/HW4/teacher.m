clear all
close all
clc
load dataSet_1.mat
who

factors=glmfit(predictor, response, 'binomial');
prob=glmval(factors, predictor, 'logit');
[X, Y, thre, AUC]=perfcurve(response, prob,1);
figure(1)
plot(X,Y)
[AUC]

predictor_train = predictor(1:3000,:);
predictor_test = predictor(3001:end,:);
response_train = response(1:3000,:);
response_test = response(3001:end,:);
%use predictor_train to obtain the logistic regression factors
factors_train = glmfit(predictor_train, response_train, 'binomial');
prob_train = glmval(factors_train, predictor_train, 'logit');

% Apply the logistic regression factor from training to test data
prob_test = glmval(factors_train, predictor_test, 'logit');

[X_train, Y_train, Thre1, AUC_train] = perfcurve(response_train, prob_train, 1);
[X_test, Y_test, Thre2, AUC_test] = perfcurve(response_test, prob_test, 1);
figure(2)
plot(X_train,Y_train,'-b',X_test,Y_test,'-r')
[AUC_train AUC_test]

% generate new predictor matrix [X(t)-X(t-1)]/X(t-1)
new_predictor_train = (predictor_train(2:3000,:)-predictor_train(1:2999,:) )./predictor_train(1:2999,:);
new_predictor_test = (predictor_test(2:1000,:)-predictor_test(1:999,:) )./predictor_test(1:999,:);
new_response_train = response_train(2:3000);
new_response_test = response_test(2:1000);

all_predictor_train=[predictor_train(2:3000,:) new_predictor_train];
all_predictor_test=[predictor_test(2:1000,:) new_predictor_test];

[ SelectedFeatureInd]=featureSelection(all_predictor_train, new_response_train);

selected_train_predictor=all_predictor_train(:,SelectedFeatureInd);
selected_test_predictor=all_predictor_test(:,SelectedFeatureInd);

new_factors_train=glmfit(selected_train_predictor, new_respose_train,'binary');
new_prob_train=glmval(new_factors_train, selected_train_predictor,'logit');
new_prob_test=glmval(new_factors_train, selected_test_predictor,'logit');

