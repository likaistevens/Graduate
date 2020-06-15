import numpy as np

def dFx(X):
    return np.array([X[0] - 1,X[1] - 3])
def function(X):
    return ((X[0]-1)**2 + (X[1]-3)**2) / 2
def gradient_descent(eta,presition):
    X = np.array([5,5])
    while True:
        gradient = dFx(X)
        last_X = X
        X = X - eta * gradient
        print("[x1,x2] = %s\t" % X)
        if(abs(function(X) - function(last_X)) < presition):
            break
    print("Final [x1,x2] = %s\t" % X)
gradient_descent(0.001,10e-8)