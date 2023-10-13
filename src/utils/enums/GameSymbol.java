package utils.enums;

public enum GameSymbol {
	X("X"), O("O"), EMPTY("-");

	private final String symbol;

	GameSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}
