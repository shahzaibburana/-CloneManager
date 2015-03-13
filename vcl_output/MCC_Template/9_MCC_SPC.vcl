#set fileName = "MCCID_9_Inst_0", "MCCID_9_Inst_1"
% Here I will set all the place-holder variables

#set SCC113V0 = "nextGetIndex", "nextPutIndex"
#set SCC113V1 = "BufferUnderflowException", "BufferOverflowException"

#while SCC113V0, SCC113V1, fileName
#output ?@fileName?".java"
#break break_start
	#select fileName
		#option MCCID_9_Inst_0
			#insert-after break_start:    final 		#endinsert
		#endoption
		#option MCCID_9_Inst_1
			#insert-after break_start:    final 		#endinsert
		#endoption
	#endselect
#adapt: "113_SCC_Frame.vcl"
	#select fileName
		#option MCCID_9_Inst_0
			#insert-after break_113:

			#endinsert
		#endoption
		#option MCCID_9_Inst_1
			#insert-after break_113:

			#endinsert
		#endoption
	#endselect
#endadapt
#endwhile