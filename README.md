# SoftUniChain - Miner

SoftUniChain Miner implemented in Java and Maven. Based on SHA256.

How to start Mining?

1. Download the project.

2. Go to miner folder.

3. Compile and create packege usin Maven.
```
$ mvn clean packege  
```

4. Go to target folder.

5. Start the miner.
```
$ java -jar minerApp.jar ${nodeUrl} ${your_wallet_address}  
```

${nodeUrl} - the URL, where your Node is running. Default value is set to http://localhost:5555.

${your_wallet_address} - the address of your wallet. There is a default value, but that means that all transactions fees will come in my wallet.

---
