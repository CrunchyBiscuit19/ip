package nemo.task;

/**
 * Enum class to prioritize importance of tasks
 */
public enum Priority {
    LOW(0),
    MED(1),
    HIGH(2);

    private final int rank;

    Priority(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    /**
     * Convert string to priority enum that represents it.
     *
     * @param s string to convert to priority enum
     * @return priority enum
     */
    public static Priority fromString(String s) throws IllegalArgumentException {
        switch (s.toUpperCase()) {
        case "LOW":
            return Priority.LOW;
        case "MED":
        case "MEDIUM":
            return Priority.MED;
        case "HIGH":
            return Priority.HIGH;
        default:
            throw new IllegalArgumentException("Invalid priority level.");
        }
    }
}
