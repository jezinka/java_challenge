package java_challenge

import grails.converters.JSON
import grails.util.Holders
import groovy.util.logging.Slf4j

import java.text.SimpleDateFormat

@Slf4j
class ReportController {

    FileService fileService
    DbService dbService
    ReportService reportService

    def index() {
        respond 'hello world'
    }

    def initData() {
        String url = Holders.config.file.url
        File file = fileService.getFileFromURL(url, 'data.csv')
        dbService.storeData(file)
        render "${Report.count()} rekordów załadowano"
    }

    def clicksPerDatasourceAndDateRange() {
        Date dateFrom = new SimpleDateFormat('MM/dd/yy').parse(params.dateFrom)
        Date dateTo = new SimpleDateFormat('MM/dd/yy').parse(params.dateTo)
        List<String> datasources = params['datasource'].split(',')

        try {
            Map clicksCounter = reportService.getClicksForDatesAndDataSource(dateFrom, dateTo, datasources)
            return render(text: "{status: 'OK', clicks: ${clicksCounter as JSON}", contentType: "application/json", encoding: "UTF-8")
        } catch (RuntimeException e) {
            log.error(e.message)
            return render(text: "{status: 'ERROR'}", contentType: "application/json", encoding: "UTF-8")
        }
    }

    def impressionsPerDay() {
        Date dateFrom = new SimpleDateFormat('MM/dd/yy').parse(params.dateFrom)
        Date dateTo = new SimpleDateFormat('MM/dd/yy').parse(params.dateTo)

        try {
            Map impressionsPerDay = reportService.getImpressionsPerDay(dateFrom, dateTo)
            render(text: "{status: 'OK', impressions: ${impressionsPerDay as JSON}", contentType: "application/json", encoding: "UTF-8")
        } catch (RuntimeException e) {
            log.error(e.message)
            return render(text: "{status: 'ERROR'}", contentType: "application/json", encoding: "UTF-8")
        }

    }
}
