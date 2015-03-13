:    		
			
		
			
		static char getCharB(ByteBuffer bb, int bi) {
        return makeChar(bb._get(bi + 0),
                        bb._get(bi + 1));
    }:

			