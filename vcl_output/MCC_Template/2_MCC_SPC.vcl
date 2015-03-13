#set fileName = "MCCID_2_Inst_0", "MCCID_2_Inst_1"
% Here I will set all the place-holder variables


#while fileName
#output ?@fileName?".java"
#break break_start
	#select fileName
		#option MCCID_2_Inst_0
			#insert-after break_start:    		#endinsert
		#endoption
		#option MCCID_2_Inst_1
			#insert-after break_start:    		#endinsert
		#endoption
	#endselect
#adapt: "45_SCC_Frame.vcl"
	#select fileName
		#option MCCID_2_Inst_0
			#insert-after break_45:

			#endinsert
		#endoption
		#option MCCID_2_Inst_1
			#insert-after break_45:

			#endinsert
		#endoption
	#endselect
#endadapt
#endwhile