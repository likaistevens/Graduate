#!/usr/bin/python

"""
Python Core object Types
"""

import numpy as np


#### arr: 		the input array
#### nRow: 		the # of row in the reformed array
#### nCol: 		the # of column in the reformed array
#### new_arr:	the new reformed array as the output
def reform_array_dimension_col_wise(arr, nRow, nCol):
	new_arr = arr.reshape(nRow,nCol,order='F') 					# write your code here
	return new_arr


#### arr: 		the input array
#### new_arr:	the new generated array as the output
#### append_sum_of_array(arr) is to stack the column summation below the bottom of the array
def append_sum_of_array(arr):
	new_arr = np.vstack((arr, arr.sum(0))) 					# write your code here
	return new_arr 


#### arr: 		the input array
#### new_arr:	the new generated array as the output
#### remove_topRow_endCol_from_array(arr) is to delete the top row and ending column from the array
def remove_topRow_endCol_from_array(arr):
	new_arr = arr[1:,:-1] 					# write your code here
	return new_arr

#### arr: 		the input array
#### new_arr:	the new generated array as the output
#### add_row_product_to_array(arr) is to calculate the product of each row and append to the array
def add_row_product_to_array(arr):
	row_product = arr.prod(1)[...,None]				# write your code here
	new_arr = np.hstack((arr, row_product)) 		# write your code here
	return new_arr





arr_ori = np.arange(15)
nRow = 3
nCol = 5

#### the last COLUMN of arr_1 is [12, 13, 14]
arr_1 = reform_array_dimension_col_wise(arr_ori, nRow, nCol)   
#### the last COLUMN of arr_1 is [12, 13, 14]
arr_2 = append_sum_of_array(arr_1)
arr_3 = remove_topRow_endCol_from_array(arr_1)
arr_4 = add_row_product_to_array(arr_1)
arr_1
arr_2
arr_3
arr_4









