package censusanalyser;

public class CensusAnalyserException extends RuntimeException {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PASS, INCORRECT_FILE_TYPE ;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
