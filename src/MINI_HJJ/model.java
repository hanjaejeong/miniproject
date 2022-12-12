package MINI_HJJ;


public class model {

	private int stnum = 0;
	private String logId = null;
	private int logPass = 0;
	private boolean logClear = false;
	private boolean gameStart = false;
	private String testnum = null;
	private String word = null;
	private String mean = null;
	private String error = null;
	private int score = 0;

	private int num = 0;
	private String levels = null;

	public int getScore() {
		return score;
	}
	
	public model(String logId, int score) {

	      this.logId = logId;
	      this.score = score;

	   }

	public void setScore(int score) {
		this.score = score;
	}

	public model(int stnum, String logId, int logPass, boolean logClear, boolean gameStart, String testnum, String word,
			String mean, String error, int score) {
		super();
		this.stnum = stnum;
		this.logId = logId;
		this.logPass = logPass;
		this.logClear = logClear;
		this.gameStart = gameStart;
		this.testnum = testnum;
		this.word = word;
		this.mean = mean;
		this.error = error;
		this.score = score;
	}

	public model(int stnum, String logId, int logPass, boolean logClear, boolean gameStart, String testnum, String word,
			String mean) {
		super();
		this.stnum = stnum;
		this.logId = logId;
		this.logPass = logPass;
		this.logClear = logClear;
		this.gameStart = gameStart;
		this.testnum = testnum;
		this.word = word;
		this.mean = mean;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public model() {

	}

	public model(int stnum, String logId, int logPass, boolean logClear, boolean gameStart, String testnum) {
		this.stnum = stnum;
		this.logId = logId;
		this.logPass = logPass;
		this.logClear = logClear;
		this.gameStart = gameStart;
		this.testnum = testnum;
	}

	public model(int num, String word, String mean, String levels) {
		this.num = num;
		this.word = word;
		this.mean = mean;
		this.levels = levels;
	}

	public model(String logId, String word, String mean) {
		this.logId = logId;
		this.word = word;
		this.mean = mean;
	}

	public int getStnum() {
		return stnum;
	}

	public void setStnum(int stnum) {
		this.stnum = stnum;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public int getLogPass() {
		return logPass;
	}

	public void setLogPass(int logPass) {
		this.logPass = logPass;
	}

	public boolean isLogClear() {
		return logClear;
	}

	public void setLogClear(boolean logClear) {
		this.logClear = logClear;
	}

	public boolean isGameStart() {
		return gameStart;
	}

	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}

	public String getTestnum() {
		return testnum;
	}

	public void setTestnum(String testnum) {
		this.testnum = testnum;
	}

	public String getWord() {
		return word;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getMean() {
		return mean;
	}

	public void setMean(String mean) {
		this.mean = mean;
	}

}