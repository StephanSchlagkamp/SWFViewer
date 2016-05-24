package application.model;

/**
 * Contains the Labels for data lines, the value-separator used for reading in data and a boolean that determines the behaviour when there are more data entries than labels
 */
public class WorkLoadProfile {
	private String[] labels;
	private String separator = "\\s+";
	private boolean strict = true;
	
	/**The label that tells the {@link WorkLoad} to skip a value.*/
	public static final String SKIP_FIELD = "!s";

	public WorkLoadProfile(String... labels) {
		if(labels != null) {
			this.labels = labels;
		}
	}
	
	public String getSeparator() {
		return separator;
	}

	/**
	 * @param separator The value separator.
	 * @return This object for chaining.
	 */
	public WorkLoadProfile setSeparator(String separator) {
		this.separator = separator;
		return this;
	}

	public boolean isOnlyDefinedFields() {
		return strict;
	}

	/**
	 * @param strict True means all unlabeled data will not be added to the {@link WorkLoad}.
	 * @return This object for chaining.
	 */
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
