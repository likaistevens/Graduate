import numpy as np
import matplotlib.pyplot as plt

"""
Question1
"""
#the target function
def function(value):
    x=value[0]
    y=value[1]
    z=value[2]
    return (1/2)*((x-1)**2+2*(y-2)**2+3*(z-3)**2)

#the gradient of target function
def gradient(value):
    x=value[0]
    y=value[1]
    z=value[2]
    deriv_x=x-1
    deriv_y=2*y-4
    deriv_z=3*z-9
    return np.array([deriv_x,deriv_y,deriv_z])

#initial value
initial=np.array([0, 0, 0])

function_value_list=[]
gradient_value_list={"x1":[],"x2":[],"x3":[]}

def gradient_descent(grad,fun_value,grad_list,cur_x=initial, n_iteration=0,
                     learning_rate=0.01, precision=0.0001, max_iters=3000):
    print("For Question 1")
    print("The inital value for x1, x2 and x3 is:({},{})".format(initial[0],initial[1],initial[2]))
    print("The learning rate is {}".format(learning_rate))
    print("the stopping criterion is L2(gradient)<{}".format(precision))
    for i in range(max_iters):
        #count the iteration
        n_iteration=n_iteration+1
        #get the current function value
        fun_value.append(function(cur_x))
        #get the current gradient
        grad_cur = grad(cur_x)
        grad_list["x1"].append(grad_cur[0])
        grad_list["x2"].append(grad_cur[1])
        grad_list["x3"].append(grad_cur[2])
        if np.linalg.norm(grad_cur, ord=2) < precision:
            break
        cur_x = cur_x - grad_cur * learning_rate
        # print("for interation", i, "the value for x1 and x2 is", cur_x)

    print("After {} iteration,the min point is ({},{},{}) and the min value is "
          "{}\n".format(n_iteration,cur_x[0],cur_x[1],cur_x[2],fun_value[-1]))
    return cur_x,n_iteration

min_value,n_total_iteration=gradient_descent(gradient,function_value_list,gradient_value_list)

#plot the Question1
index_1 = np.arange(1, n_total_iteration + 1, 1)
plt.figure("Question1")
plt.subplot(411)
plt.title("Question 1\niteration vs the function value")
plt.plot(index_1, function_value_list)
plt.subplot(412)
plt.title("iteration vs the gradient about x1 at")
plt.plot(index_1, gradient_value_list["x1"])
plt.subplot(413)
plt.title("iteration vs the gradient about x2 at")
plt.plot(index_1, gradient_value_list["x2"])
plt.subplot(414)
plt.title("iteration vs the gradient about x3 at")
plt.plot(index_1, gradient_value_list["x3"])


"""
Question2
"""
def penalty_function(value,c):
    """
    :param value: it is a list [x,y]
    :param c: int
    """
    x=value[0]
    y=value[1]
    return ((x-6)**2 + (y - 7)**2) + (c*max(0,-3*x-2*y+6)**2) + \
           (c*max(0,-x+y-3)**2) + (c*max(0,x+y-7)**2) + (c*max(0,(2/3)*x-y-4/3)**2)

def penalty_gradient(value,c):
    """
        :param value: it is a list [x,y]
        :param c: int
    """
    x=value[0]
    y=value[1]
    gradient_without_panal=np.array([2*(x-6),2*(y-7)])
    if(-3*x-2*y+6)>0:
        gradient_without_panal[0]+=2*c*-3*(-3*x-2*y+6)
        gradient_without_panal[1]+=2*c*-2*(-3*x-2*y+6)
    if(-x+y-3)>0:
        gradient_without_panal[0] += -2 * c *(-x+y-3)
        gradient_without_panal[1] += 2 * c*(-x+y-3)
    if(x+y-7)>0:
        gradient_without_panal[0] +=2 * c*(x+y-7)
        gradient_without_panal[1] += 2 * c * (x + y - 7)
    if((2/3)*x-y-4/3)>0:
        gradient_without_panal[0] += 2 * c *2/3*(2/3)*x-y-4/3
        gradient_without_panal[0] +=-2 * c*(2/3)*x-y-4/3

    return gradient_without_panal

def gradient_descent(gradient_of_function,function_value,para,input_x,learning_rate=0.001, precision=0.005, max_iters=3000):
    """
    :param para: c or r
    :param input_x: list  [x,y]
    :return:
    """
    #initial value
    n_iteration = 0
    # store the value of function during the iteration
    function_value_list = []
    for i in range(max_iters):
        n_iteration = n_iteration + 1
        # get the current function value
        function_value_list.append(function_value(input_x,para))
        #get the current decent
        grad_cur = gradient_of_function(input_x,para)
        #update the x
        input_x = input_x - grad_cur * learning_rate
        #output each step of gradicent
        # print("After {} iteration,the min point is ({},{}) and the min value is "
        #       "{}\n".format(n_iteration, input_x[0], input_x[1], function_value_list[-1]))

        if  np.linalg.norm(grad_cur, ord=2) < precision:
            break
    print("After {} iteration,the min point is ({},{}) and the min value is "
          "{}\n".format(n_iteration, input_x[0], input_x[1], function_value_list[-1]))

    return input_x,n_iteration,function_value_list

initial_panalty=np.array([2, 1])

print("For Question 3")
print("The inital value for x1 and x2 is:({},{})".format(initial_panalty[0], initial_panalty[1]))
print("The learning rate is {}".format(0.001))
print("the stopping criterion is L2(gradient)<{}".format(0.005))

final_min_x,n_total_iteration,func_value=gradient_descent(gradient_of_function=penalty_gradient,
                                                      function_value=penalty_function,
                                                      para=5000,
                                                      input_x=initial_panalty)
#plot the Q2
index_2 = np.arange(1, n_total_iteration + 1, 1)
plt.figure("Question2")
plt.title("Question2\niteration vs the penalty function value")
plt.plot(index_2, func_value)

"""
Question3
"""
def barrier_function(value,r):
    """
    log_barrier_function:-1/g(x)  g(x)=-x-y+4
    """
    x=value[0]
    y=value[1]
    return 2*(x**2)+9*y-r/(-x-y+4)

def grad_log_barrier_function(value,r):
    x=value[0]
    y=value[1]
    deriv_x=4*x-r/((-x-y+4)**2)
    deriv_y=9-r/((-x-y+4)**2)
    return np.array([deriv_x,deriv_y])

initial_barrier=np.array([3, 3])

print("For Question 3")
print("The inital value for x1 and x2 is:({},{})".format(initial_barrier[0], initial_barrier[1]))
print("The learning rate is {}".format(0.001))
print("the stopping criterion is L2(gradient)<{}".format(0.005))

barrier_min_x,barrier_iteration,barrier_func_value=gradient_descent(gradient_of_function=grad_log_barrier_function,
                                                      function_value=barrier_function,
                                                      para=0.005,
                                                      input_x=initial_barrier)
index_3 = np.arange(1, barrier_iteration + 1, 1)
plt.figure("Question3")
plt.title("Question3\niteration vs the barrior function value")
plt.plot(index_3, barrier_func_value)

plt.show()




