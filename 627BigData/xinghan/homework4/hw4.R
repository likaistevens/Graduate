library(aod)
library(ggplot2)
library(pROC)
rm(list = ls())
gc()
setwd("/Users/sean/Desktop/Stevens/627_Data Acquisition/homework/homework4")
Data = read.csv("EE627A_HW3_DataSet1.csv", header = F)

training_data <- (Data[2:3000, 1:476] - Data[1:2999, 1:476]) / Data[1:2999,1:476]
testing_data <- (Data[3002:4000,1:476] - Data[3001:3999,1:476]) / Data[3001:3999,1:476]

response_training <- Data[2:3000, 477]
response_testing <- Data[3002:4000, 477]
all_training_data <- cbind(Data[2:3000,1:476], training_data, response_training)
all_testing_data <- cbind(Data[3002:4000,1:476], testing_data, response_testing)
all_training_data1 <- all_training_data[,]
all_testing_data1 <- all_testing_data[,]

logit <- glm(response_training~., data = all_training_data1, family = "binomial")
prob <- predict(logit, all_training_data1, type = "response")
all_training_data1$prob = prob
g <- roc(response_training ~ prob, data = all_training_data1)
plot(g)
auc(g)

prob2 <- predict(logit, all_testing_data1, type='response')
all_testing_data1$prob = prob2
g1 <- roc(response_testing ~ prob, data = all_testing_data1)
plot(g1, legacy.axes = 1)
auc(g1)
