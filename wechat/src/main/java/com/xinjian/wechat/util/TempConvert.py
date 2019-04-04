#TempConvert.py
val = input('please input a temperature with the symbol')
if val[-1] in ('c','C'):
	f = 1.8 * float(val[0:-1]) + 32
	print("result is $.2fF"$f)

elif val[-1] in ('f','F'):
	c = (float(val(0:-1))-32)/1.8
	print("result is $.2fC"$c)

else:
	print('error!!!!!!!')