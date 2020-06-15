clear all
close all
clc

load dataSet_2.mat
% generate new predictor matrix [X(t)-X(t-1)]/X(t-1)
new_predictor_train = ( predictor_train(2:end,:) - predictor_train(1:4499,:)) ./ predictor_train(1:4499,:);
new_predictor_test = ( predictor_test(2:end,:) - predictor_train(1:1421,:)) ./ predictor_train(1:1421,:);
new_response_train = response_train(2:end);
new_response_test = response_test(2:end);

all_predictor_train = [predictor_train(2:end,:) new_predictor_train];
all_predictor_test = [predictor_test(2:end,:) new_predictor_test];

% plot AUC for 1846 column martix after combination
% all_factors_train = glmfit(all_predictor_train, new_response_train, 'binomial');
% all_prob_train = glmval(all_factors_train, all_predictor_train, 'logit');
% all_prob_test = glmval(all_factors_train, all_predictor_test, 'logit');
% [X_train_all, Y_train_all, Thre1, AUC_train_all] = perfcurve(new_response_train, all_prob_train, 1);
% [X_test_all, Y_test_all, Thre2, AUC_test_all] = perfcurve(new_response_test, all_prob_test, 1);
% figure(2)
% plot(X_train_all, Y_train_all, '-b', X_test_all, Y_test_all, '-r')
% 
% [AUC_train_all AUC_test_all]

% [ SelectedFeatureInd]=featureSelection(all_predictor_train, new_response_train);
% 
% selected_train_predictor = all_predictor_train(:,SelectedFeatureInd);
% selected_test_predictor = all_predictor_test(:,SelectedFeatureInd);

selected_train_predictor = all_predictor_train(:,[349,825,281,401,119,81,89,2,317,1696,325,448,42,1268,447,1458,505,285,1150,241]);
selected_test_predictor = all_predictor_test(:,[349,825,281,401,119,81,89,2,317,1696,325,448,42,1268,447,1458,505,285,1150,241]);

new_factors_train = glmfit(selected_train_predictor, new_response_train, 'binomial');
new_prob_train = glmval(new_factors_train, selected_train_predictor, 'logit');
new_prob_test = glmval(new_factors_train, selected_test_predictor, 'logit');

[X_train_new, Y_train_new, Thre3, AUC_train_new] = perfcurve(new_response_train, new_prob_train, 1);
[X_test_new, Y_test_new, Thre4, AUC_test_new] = perfcurve(new_response_test, new_prob_test, 1);
figure(2)
plot(X_train_new, Y_train_new, '-b', X_test_new, Y_test_new, '-r')
[AUC_train_new AUC_test_new]