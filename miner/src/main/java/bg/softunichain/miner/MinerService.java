package bg.softunichain.miner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import bg.softunichain.miner.models.Miner;
import bg.softunichain.miner.models.RequestBlock;
import bg.softunichain.miner.models.SubmitBlock;

public class MinerService {

	private final String GET_SUBPATH = "/mining/get-block/";
	private final String POST_SUBPATH = "/mining/submit-block/";
	private final String BASE_URL;
	private Miner miner = new Miner();

	public MinerService(String nodeUrl, String minerAddress) {
		super();
		this.BASE_URL = nodeUrl;
		this.miner.setAddress(minerAddress);
	}
	
	private RequestBlock getNextBlock() {

		String url = BASE_URL + GET_SUBPATH + this.miner.getAddress();
		RequestBlock nextBlock = new RequestBlock();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);

		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String responseBody = EntityUtils.toString(entity, "UTF-8");
				System.out.println(responseBody);

				nextBlock = new Gson().fromJson(responseBody, RequestBlock.class);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return nextBlock;
	}
	
	
	private SubmitBlock mineBlockHash(RequestBlock block) {
		int index = block.getIndex();
		int difficulty = block.getDifficulty();
		String prevBlockHash = block.getPrevBlockHash();
		String blockDataHash = block.getBlockDataHash();

		long nonce = 0;
		long nextTimestamp = Instant.now().toEpochMilli();		
		String blockHash = calculateBlockHash(index, prevBlockHash, blockDataHash, nextTimestamp, nonce);
		
		while(true) {
						
			if(isValidHash(blockHash, difficulty)) {
				break;
			}
			
			nonce ++;
			nextTimestamp = Instant.now().toEpochMilli();
			blockHash = calculateBlockHash(index, prevBlockHash, blockDataHash, nextTimestamp, nonce);			
		}
		
		SubmitBlock newBlock = new SubmitBlock();
		newBlock.setNonce(nonce);
		newBlock.setDateCreated(nextTimestamp);
		newBlock.setBlockHash(blockHash);
		
		return newBlock;
	}

	private String calculateBlockHash(int index, String prevBlockHash, String blockDataHash, long nextTimestamp,
			long nonce) {
		String base = index + prevBlockHash + blockDataHash + nextTimestamp + nonce;
		StringBuffer hexString = new StringBuffer();		

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));

		    	for (int i=0;i<hash.length;i++) {
		    		String hex=Integer.toHexString(0xff & hash[i]);
		   	     	if(hex.length()==1) hexString.append('0');
		   	     	hexString.append(hex);
		    	}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hexString.toString();
	}
	
	private boolean isValidHash(String hash, int difficulty) {			
		return hash.substring(0, difficulty) == StringUtils.repeat("0", difficulty) ? true : false;
	}
}
