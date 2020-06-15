import numpy as np

def function(X):
    return ((X[0]-1)**2 + (X[1]-3)**2) / 2
# The gradient of function
def dFx(X):
    return np.array([X[0] - 1,X[1] - 3])
# input eta and expected presition
def gradient_descent(eta,presition):
    X = np.array([5,5])
    while True:
        last_X = X
#       update X by using formulation X = X - eta * dF(x) 
        X = X - eta * dFx(X)
        print("[x1,x2] = %s\t" % X)
#       check if the difference between old and new value less than presition  
        if(abs(function(X) - function(last_X)) < presition):
            break
    print("Final [x1,x2] = %s\t" % X,"The Minimum is %.5f" % function(X))
    
gradient_descent(0.001,10e-8)
# set eta = 0.001 and presition = 10e-8 then we can get the minimum of this function is 0.00005 which is close to 0
# and the minimizer is [1.00893519 3.00446759] close to [1,3]

# -------------------------------------------------------------------------------------------------------------------------

import numpy as np

# the formuation of quadratic penality function
def penality_funtion(x, r):
    return 50 / x + r * (max(0, x - 10))**2
# compute the penality parameter
def cp(x, r):
    return r * (max(0, x - 10))**2
def penality_helper(rSet,xSet,presition):
    for r in rSet:
#       set the initial x as 100 for the initial F(x)  
        fx = penality_funtion(100,r)
#       increase x for each loop    
        for x in xSet:
#       check if the value of function is decreasing. So every time the value will be closer to the minimun     
            if penality_funtion(x, r) <= fx:
                fx = penality_funtion(x, r)
                tempX = x
#               check if the penality parameter is less than presition              
                if cp(x,r) <= presition:
                    print("The Minimum is %.5f" % penality_funtion(x, r))
                    return
        print("r = %.f\t" % r, "x = %s\t" % tempX,"f(x) = %s" % fx)
def penality(threshold): 
#   make a set for penality parameter. the penality parameter will multiply 1.25 each time from 1  
    rSet = np.logspace(1, 20, num=20, base=1.25,endpoint=True)
#   make a set for x which is arithmetic distribution from 0 to 11 with 100 elements
    xSet = np.linspace(11, 0, num=1000)
    penality_helper(rSet,xSet,threshold)
    return

penality(0.0001)
#  when the presition is 0.0001, we can get the minimum is 5.001 which is close to 5 with minizor x = 10.009 close to which is 10

# -------------------------------------------------------------------------------------------------------------------------

import numpy as np

# the formuation of barrier function
def barrier_function(x1, x2, c):
    return ((x1-3)**2/2+(x2-2)**2) - (np.log(2*x1-x2)+np.log(4-x1-x2)+np.log(x2))/c
# compute the barrier parameter
def function(x1, x2):
    return ((x1-3)**2/2+(x2-2)**2)
def barrier_helper(a,xSet1,xSet2):
    X = []
    for c in a:
#       set the initial [x1,x2] as [0,0] for the initial F(x)  
        fx = barrier_function(0, 0, c)
#       change x1 and x2 for each loop    
        for x1 in xSet1:
            for x2 in xSet2:
#       check if the value of function is decreasing. So every time the value will be closer to the minimun     
                if barrier_function(x1, x2, c) <= fx:
                    fx = barrier_function(x1, x2, c)
                    X = [x1, x2]
                    print("c = %.5f\t" % (1.0/c), "[x1,x2] = %s\t" % X,"F(x) = %.5f" % function(X[0], X[1]))
    print("The Minimum is %.5f" % function(X[0], X[1]))     
def barrier(): 
#   make a set for barrier parameter. the barrier parameter will multiply 1.25 each time from 1  
    a = np.logspace(1, 20, num=20, base=1.25,endpoint=True)
#   make two sets for x1 and x2 which are arithmetic distribution from 0 to 5 with 100 elements
    xSet1 = xSet2 = np.linspace(0, 5, 100)
    barrier_helper(a,xSet1,xSet2)
    return
barrier()
#  when barrier parameter multiply 1.25 every time. We can get the minimum is 0.34012 which is close to 1/3 
#  and minizor is [x1,x2]=[2.323232323232323, 1.6666666666666667] which is close to [7/3,5/3]











