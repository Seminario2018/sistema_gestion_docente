package modelo.auxiliares.hash;

public class HashSalt {
	private String hash;
	private String salt;

	public HashSalt() {
		this.hash = null;
		this.salt = null;
	}
	
	
	public HashSalt(String hash, String salt) {
		this.hash = hash;
		this.salt = salt;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}