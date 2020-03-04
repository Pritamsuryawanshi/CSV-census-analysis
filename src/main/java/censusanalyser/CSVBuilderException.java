package censusanalyser;

public class CSVBuilderException extends RuntimeException {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PASS, INCORRECT_FILE_TYPE;
    }

    CSVBuilderException.ExceptionType type;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
