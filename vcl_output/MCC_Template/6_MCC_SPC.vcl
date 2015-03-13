#set fileName = "MCCID_6_Inst_0", "MCCID_6_Inst_1"
% Here I will set all the place-holder variables

#set SCC105V0 = "getCharL", "getCharB"
#set SCC105V1 = "1", "0"
#set SCC105V2 = "0", "1"

#while SCC105V0, SCC105V1, SCC105V2, fileName
#output ?@fileName?".java"
#break break_start
	#select fileName
		#option MCCID_6_Inst_0
			#insert-after break_start:    		#endinsert
		#endoption
		#option MCCID_6_Inst_1
			#insert-after break_start:    		#endinsert
		#endoption
	#endselect
#adapt: "105_SCC_Frame.vcl"
	#select fileName
		#option MCCID_6_Inst_0
			#insert-after break_105:

			#endinsert
		#endoption
		#option MCCID_6_Inst_1
			#insert-after break_105:

			#endinsert
		#endoption
	#endselect
#endadapt
#endwhile