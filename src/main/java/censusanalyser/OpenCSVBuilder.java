package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder {
    @Override
    public Iterator<E> getCSVIterator(Reader reader, Class csvClass) {
        return this.getCSVtoBean(reader, csvClass).iterator();
    }

    @Override
    public List<E> getCSVFileList(Reader reader, Class csvClass) {
        return this.getCSVtoBean(reader, csvClass).parse();
    }

    private CsvToBean getCSVtoBean(Reader reader, Class csvClass) {
        try {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean;
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PASS);
        }
    }
}
