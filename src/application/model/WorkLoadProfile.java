package application.model;

public class WorkLoadProfile {
	private String[] labels;
	private String separator = " ";
	private boolean strict = true;
	
	public static final String SKIP = "!s";

	public WorkLoadProfile(String... labels) {
		if(labels != null) {
			this.labels = labels;
		}
	}
	
	public String getSeparator() {
		return separator;
	}

	public WorkLoadProfile setSeparator(String separator) {
		this.separator = separator;
		return this;
	}

	public boolean isOnlyDefinedFields() {
		return strict;
	}

	public WorkLoadProfile setOnlyDefinedFields(boolean strict) {
		this.strict = strict;
		return this;
	}

	public String[] getLabels() {
		return labels;
	}
	
	@Override
	public String toString() {
		return labels.toString();
	}
}
