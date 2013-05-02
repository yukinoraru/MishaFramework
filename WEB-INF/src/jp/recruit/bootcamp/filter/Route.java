package jp.recruit.bootcamp.filter;

public class Route {
	String pattern, controller;

	public Route(String pattern, String controller) {
		super();
		this.pattern = pattern;
		this.controller = controller;
	}

	public String getController() {
		return controller;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setController(String controller) {
		this.controller = controller;
	}
}
