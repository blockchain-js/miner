package bg.softunichain.miner.models;

public class RequestBlock {

	private int index;
	private int difficulty;
	private long expectedReward;
	private String blockDataHash; // this is the transactions hash
	private String prevBlockHash;
	
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	public String getBlockDataHash() {
		return blockDataHash;
	}
	public void setBlockDataHash(String blockDataHash) {
		this.blockDataHash = blockDataHash;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getPrevBlockHash() {
		return prevBlockHash;
	}
	public void setPrevBlockHash(String prevBlockHash) {
		this.prevBlockHash = prevBlockHash;
	}

	public long getExpectedReward() {
		return expectedReward;
	}

	public void setExpectedReward(long expectedReward) {
		this.expectedReward = expectedReward;
	}
}