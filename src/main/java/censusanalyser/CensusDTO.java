package censusanalyser;

public class CensusDTO {

    public double totalArea;
    public int population;
    public double densityPerSqKm;
    public String state;
    public String stateId;
    public String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        totalArea = indiaCensusCSV.totalArea;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

    public CensusDTO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        stateId = usCensusCSV.stateId;
        population = usCensusCSV.population;
        totalArea = usCensusCSV.totalArea;
        densityPerSqKm = usCensusCSV.densityPerSqKm;
    }
}
