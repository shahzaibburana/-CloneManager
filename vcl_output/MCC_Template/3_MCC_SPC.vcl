#set fileName = "MCCID_3_Inst_0", "MCCID_3_Inst_1"
% Here I will set all the place-holder variables

#set SCC104V0 = "putShortL", "putShortB"
#set SCC104V1 = "short0", "short1"
#set SCC104V2 = "short1", "short0"

#while SCC104V0, SCC104V1, SCC104V2, fileName
#output ?@fileName?".java"
#break break_start
	#select fileName
		#option MCCID_3_Inst_0
			#insert-after break_start:    		#endinsert
		#endoption
		#option MCCID_3_Inst_1
			#insert-after break_start:    		#endinsert
		#endoption
	#endselect
#adapt: "104_SCC_Frame.vcl"
	#select fileName
		#option MCCID_3_Inst_0
			#insert-after break_104:

			#endinsert
		#endoption
		#option MCCID_3_Inst_1
			#insert-after break_104:

			#endinsert
		#endoption
	#endselect
#endadapt
#endwhile