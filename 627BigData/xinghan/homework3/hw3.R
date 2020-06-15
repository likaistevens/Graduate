library(aod)
library(ggplot2)
library(pROC)
rm(list = ls())
gc()
setwd("/Users/sean/Desktop/Stevens/627_Data Acquisition/homework/homework3")
Data = read.csv("EE627A_HW3_DataSet1.csv", header = F)

sapply(Data, sd)
logit <- glm(V477~., data = Data, family = "binomial")
prob <- predict(logit, type = "response")
Data$prob = prob
g <- roc(V477 ~ prob, data = Data)
plot(g, legacy.axes = 1)
auc(g)

Data2 <- Data[1:3000,]
logit2 <- glm(V477~., Data2, family = "binomial")
prob2 <- predict(logit2, type = "response")
Data2$prob = prob2
g1 <- roc(V477 ~ prob, data = Data2)
plot(g1, legacy.axes = 1)
auc(g1)

Data3 <- Data[3001:4000,]
prob3 <- predict(logit2, Data3, type='response')
Data3$prob = prob3
g2 <- roc(V477 ~ prob, data = Data3)
plot(g2, legacy.axes = 1)
auc(g2)
