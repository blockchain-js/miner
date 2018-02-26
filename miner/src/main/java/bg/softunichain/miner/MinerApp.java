package bg.softunichain.miner;

import bg.softunichain.miner.models.RequestBlock;

public class MinerApp 
{
	private final static String DEFAULT_MINER_ADDRESS = "0xB282E73A4A6da70200E328e2d5427b15EEFC20b3";
	private final static String DEFAULT_NODE_BASEURL = "http://localhost:5555";
	
    public static void main( String[] args )
    {
    	MinerService ms = new MinerService(DEFAULT_NODE_BASEURL, DEFAULT_MINER_ADDRESS);

		RequestBlock nextBlock = ms.getNextBlock();
		ms.mineBlockHash(nextBlock);
    }
}
