import numpy as np

def penality_funtion(x, r):
    return 50 / x + r * (max(0, x - 10))**2
def cp(x, r):
    return r * (max(0, x - 10))**2
def penality_helper(rSet,xSet,threshold):
    for r in rSet:
        fx = penality_funtion(100,r)
        for x in xSet:
            if penality_funtion(x, r) <= fx:
                fx = penality_funtion(x, r)
                tempX = x
                if cp(x,r) <= threshold:
                    print("The Minimum is %.5f" % penality_funtion(x, r))
                    return
        print("r = %.f\t" % r, "x = %s\t" % tempX,"f(x) = %s" % fx)
def penality(threshold):  
    rSet = np.logspace(1, 20, num=20, base=1.25,endpoint=True)
    xSet = np.linspace(11, 0, num=1000)
    penality_helper(rSet,xSet,threshold)
    return
penality(0.0001)