package bg.softunichain.miner;

import java.io.IOException;

import bg.softunichain.miner.models.RequestBlock;
import bg.softunichain.miner.models.SubmitBlock;

public class MinerApp 
{
	private final static String DEFAULT_MINER_ADDRESS = "0xB282E73A4A6da70200E328e2d5427b15EEFC20b3";
	private final static String DEFAULT_NODE_BASEURL = "http://localhost:5555";
	
    public static void main( String[] args ) throws IOException
    {
    	
    	String url = (args != null && args.length > 0) ? args[0] : DEFAULT_NODE_BASEURL;
    	String minerAddress = (args != null && args.length > 1) ? args[1] : DEFAULT_MINER_ADDRESS;
    		
    	
    	
    	MinerService ms = new MinerService(url, minerAddress);
		RequestBlock nextBlock;
		SubmitBlock sb;
	
		//int count = 0;
		
//		while (count < 10) {
		while (true) {
			
			nextBlock = ms.getNextBlock();
			sb = ms.mineBlockHash(nextBlock);
			ms.submitNextBlock(sb);
			System.out.println(sb.getBlockHash());
			
//			count++;
		}
    }
}
