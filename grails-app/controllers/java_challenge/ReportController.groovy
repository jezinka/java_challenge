package java_challenge

import grails.converters.JSON
import grails.util.Holders

import java.text.SimpleDateFormat

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

        Map clicksCounter = reportService.getClicksForDatesAndDataSource(dateFrom, dateTo, datasources)

        render(text: "{status: 'OK', clicks: ${clicksCounter as JSON}", contentType: "application/json", encoding: "UTF-8")
    }
}
