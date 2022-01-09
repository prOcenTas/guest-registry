package eu.exadelpractice.registry.person.model;

public enum WorkerType {
	FULL_TIME("Full time"), TEMPORARY("Temporary");

	private String displayName;

	WorkerType(String displayName) {
		this.displayName = displayName;
	}

	public String displayName() {
		return displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
