package censusanalyser;

public class CensusDTO {

    public long densityPerSqKm;
    public long areaInSqKm;
    public long population;
    public double totalArea;
    public double populationDensity;
    public String state;
    public String stateId;
    public String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }

    public CensusDTO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        stateId = usCensusCSV.stateId;
        population = usCensusCSV.population;
        totalArea = usCensusCSV.totalArea;
        populationDensity = usCensusCSV.populationDensity;
    }


}
