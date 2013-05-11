package jp.recruit.bootcamp.filter;

public class Route {
	String pattern, controller, action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Route(String pattern, String controller, String action) {
		super();
		this.pattern = pattern;
		this.controller = controller;
		this.action = action;
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
