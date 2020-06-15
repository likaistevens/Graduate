rm(list = ls())
gc()
setwd("/Users/kaili/Stevens/627BigData/HW7")
Data = read.csv("EE627A_HW1_Data.csv")
Data = Data[1:948, c(2:4,6)]
d2cov = cov(Data)
deig = eigen(d2cov)
v1 <- sum(deig$values[1])/sum(deig$values)
v2 <- sum(deig$values[1:2])/sum(deig$values)

M = scale(Data)
mypca <- prcomp(Data)
pc1_coeff <- mypca$rotation[,1]
pc2_coeff <- mypca$rotation[,2]
plot(pc1_coeff, type = "line")
plot(pc2_coeff, type = "line")