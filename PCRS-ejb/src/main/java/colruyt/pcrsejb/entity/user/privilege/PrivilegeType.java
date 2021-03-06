package colruyt.pcrsejb.entity.user.privilege;

public enum PrivilegeType {
	/*
	 * VALUES
	 */
	TEAMMEMBER("M", "Team member"), TEAMMANAGER("T", "Team manager"), SURVEYDEFINITIONRESPONSIBLE("F", "Survey Definition Responsible"), ADMINISTRATOR("A", "Administrator");
	/*
	 * PROPERTIES
	 */
	private String shortName;
	private String fullName;
	/*
	 * CONSTRUCTOR
	 */
	private PrivilegeType(String shortName, String fullName) {
		this.shortName = shortName;
		this.fullName = fullName;
	}
	/*
	 * GETTERS
	 */
	public String getShortName() {
		return shortName;
	}

	public String getFullName() {
		return fullName;
	}
}
