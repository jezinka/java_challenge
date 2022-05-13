package java_challenge

import com.opencsv.CSVParser
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import grails.gorm.transactions.Transactional

@Transactional
class DbService {

    def storeData(File file) {
        Reader reader = new BufferedReader(new FileReader(file))

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',' as char)
                .withIgnoreQuotations(true)
                .build()

        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build()

        String[] line
        while ((line = csvReader.readNext()) != null) {
            Report report = new Report(line)
            if (report.validate()) {
                report.save(failOnError: true)
            } else {
                throw new Exception("Invalid report line in file!")
            }
        }
        reader.close()
        csvReader.close()
    }
}
