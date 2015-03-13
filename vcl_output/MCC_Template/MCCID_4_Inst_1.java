:    		
			
		
			
		static void putFloat(long a, short x, boolean bigEndian) {
        if (bigEndian)
            putFloatB(a, x);
        else
            putFloatL(a, x);
    }:

			