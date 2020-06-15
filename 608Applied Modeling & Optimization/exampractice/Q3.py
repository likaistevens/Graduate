import numpy as np

def barrier_function(x1, x2, c):
    return ((x1-3)**2/2+(x2-2)**2) - (np.log(2*x1-x2)+np.log(4-x1-x2)+np.log(x2))/c
def function(x1, x2):
    return ((x1-3)**2/2+(x2-2)**2)
def barrier_helper(a,xSet1,xSet2):
    X = []
    for c in a:
        fx = barrier_function(0, 0, c)
        for x1 in xSet1:
            for x2 in xSet2:
                if barrier_function(x1, x2, c) <= fx:
                    fx = barrier_function(x1, x2, c)
                    X = [x1, x2]
                    print("c = %.5f\t" % (1.0/c), "[x1,x2] = %s\t" % X)
    print("The Minimum is %.5f" % function(X[0], X[1]))     
def barrier(): 
    a = np.logspace(1, 20, num=20, base=1.25,endpoint=True)
    xSet1 = xSet2 = np.linspace(0, 5, 100)
    barrier_helper(a,xSet1,xSet2)
    return
barrier()
