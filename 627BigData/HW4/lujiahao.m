clear all
close all


%HW3 ?????
load dataSet_2.mat
% factors = glmfit(predictor, response, 'binomial');
% prob = glmval(factors, predictor, 'logit');
% [X, Y, thre, AUC] = perfcurve(response, prob, 1);
% figure(1)
% plot(X,Y)
% [AUC]
% 
% predictor_train = predictor(1:3000, :);
% predictor_test = predictor(3001:end, :);
% response_train = response(1:3000);
% response_test = response(3001:end);
% 
% factors_train = glmfit(predictor_train, response_train, 'binomial');
% prob_train = glmval(factors_train, predictor_train, 'logit');
% prob_test = glmval(factors_train, predictor_test, 'logit');
% [X_train, Y_train, Thre1, AUC_train] = perfcurve(response_train, prob_train, 1);
% [X_test, Y_test, Thre2, AUC_test] = perfcurve(response_test, prob_test, 1);
% figure(1)
% plot(X_train, Y_train, '-b', X_test, Y_test, '-r')
% 
% [AUC_train AUC_test]



%HW4 ?????
new_predictor_train = ( predictor_train(2:end,:) - predictor_train(1:4499,:)) ./ predictor_train(1:4499,:);
new_predictor_test = ( predictor_test(2:end,:) - predictor_train(1:1421,:)) ./ predictor_train(1:1421,:);

new_response_train = response_train(2:end);
new_response_test = response_test(2:end);

all_predictor_train = [predictor_train(2:end,:) new_predictor_train];
all_predictor_test = [predictor_test(2:end,:) new_predictor_test];

all_factors_train = glmfit(all_predictor_train, new_response_train, 'binomial');
all_prob_train = glmval(all_factors_train, all_predictor_train, 'logit');
all_prob_test = glmval(all_factors_train, all_predictor_test, 'logit');
[X_train_all, Y_train_all, Thre1, AUC_train_all] = perfcurve(new_response_train, all_prob_train, 1);
[X_test_all, Y_test_all, Thre2, AUC_test_all] = perfcurve(new_response_test, all_prob_test, 1);
figure(2)
plot(X_train_all, Y_train_all, '-b', X_test_all, Y_test_all, '-r')

[AUC_train_all AUC_test_all]
% 
% [SelectedFeatureInd] = featureSelection(all_predictor_train, new_response_train);
% 
% %???????????????
% selected_train_predictor = all_predictor_train(:,SelectedFeatureInd);
% selected_test_predictor = all_predictor_test(:,SelectedFeatureInd);
% 
% new_factors_train = glmfit(selected_train_predictor, new_response_train, 'binomial');
% new_prob_train = glmval(new_factors_train, selected_train_predictor, 'logit');
% new_prob_test = glmval(new_factors_train, selected_test_predictor, 'logit');
% 
% [X_train_new, Y_train_new, Thre3, AUC_train_new] = perfcurve(new_response_train, new_prob_train, 1);
% [X_test_new, Y_test_new, Thre4, AUC_test_new] = perfcurve(new_response_test, new_prob_test, 1);
% figure(2)
% plot(X_train_new, Y_train_new, '-g', X_test_new, Y_test_new, '-y')
% [AUC_train_new AUC_test_new]
% 
% 
% 
% % 
% % selected_train_predictor = all_predictor_train(:,[2 4 6 7 8 15 16 17 26 30 35 40 42 45 46 49 61 66 68 74 81 89 92 94 105 118 119 145 160 161 165 169 204 216 227 231 241 242 252 266 281 285 292 294 301 317 325 334 335 349 358 367 371 379 385 386 389 393 401 409 418 447 448 458 463 475 483 492 505 510 515 519 524 532 536 544 550 556 567 573 613 614 629 653 661 702 723 748 765 800 810 825 837 849 900 923 926 987 988 1013 1024 1036 1037 1044 1047 1051 1058 1068 1073 1098 1119 1121 1150 1159 1181 1188 1203 1221 1224 1226 1231 1232 1235 1266 1268 1270 1274 1276 1284 1304 1308 1311 1312 1349 1359 1380 1392 1427 1435 1456 1458 1466 1492 1498 1510 1529 1537 1558 1568 1595 1609 1639 1641 1643 1696 1727 1759 1780 1796 1818 1821 1823]);
% % selected_test_predictor = all_predictor_test(:,[2 4 6 7 8 15 16 17 26 30 35 40 42 45 46 49 61 66 68 74 81 89 92 94 105 118 119 145 160 161 165 169 204 216 227 231 241 242 252 266 281 285 292 294 301 317 325 334 335 349 358 367 371 379 385 386 389 393 401 409 418 447 448 458 463 475 483 492 505 510 515 519 524 532 536 544 550 556 567 573 613 614 629 653 661 702 723 748 765 800 810 825 837 849 900 923 926 987 988 1013 1024 1036 1037 1044 1047 1051 1058 1068 1073 1098 1119 1121 1150 1159 1181 1188 1203 1221 1224 1226 1231 1232 1235 1266 1268 1270 1274 1276 1284 1304 1308 1311 1312 1349 1359 1380 1392 1427 1435 1456 1458 1466 1492 1498 1510 1529 1537 1558 1568 1595 1609 1639 1641 1643 1696 1727 1759 1780 1796 1818 1821 1823]);
% % 
% % new_factors_train = glmfit(selected_train_predictor, new_response_train, 'binomial');
% % new_prob_train = glmval(new_factors_train, selected_train_predictor, 'logit');
% % new_prob_test = glmval(new_factors_train, selected_test_predictor, 'logit');
% % 
% % [X_train_new, Y_train_new, Thre3, AUC_train_new] = perfcurve(new_response_train, new_prob_train, 1);
% % [X_test_new, Y_test_new, Thre4, AUC_test_new] = perfcurve(new_response_test, new_prob_test, 1);
% % figure(2)
% % plot(X_train_new, Y_train_new, '-g', X_test_new, Y_test_new, '-y')
% % [AUC_train_new AUC_test_new]