#set fileName = "MCCID_4_Inst_0", "MCCID_4_Inst_1"
% Here I will set all the place-holder variables

#set SCC103V0 = "putShort", "putFloat"
#set SCC103V1 = "putShortB", "putFloatB"
#set SCC103V2 = "putShortL", "putFloatL"

#while SCC103V0, SCC103V1, SCC103V2, fileName
#output ?@fileName?".java"
#break break_start
	#select fileName
		#option MCCID_4_Inst_0
			#insert-after break_start:    		#endinsert
		#endoption
		#option MCCID_4_Inst_1
			#insert-after break_start:    		#endinsert
		#endoption
	#endselect
#adapt: "103_SCC_Frame.vcl"
	#select fileName
		#option MCCID_4_Inst_0
			#insert-after break_103:

			#endinsert
		#endoption
		#option MCCID_4_Inst_1
			#insert-after break_103:

			#endinsert
		#endoption
	#endselect
#endadapt
#endwhile