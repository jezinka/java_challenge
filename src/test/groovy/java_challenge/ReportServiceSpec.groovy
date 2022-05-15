package java_challenge

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import groovy.time.TimeCategory
import spock.lang.Specification

class ReportServiceSpec extends Specification implements ServiceUnitTest<ReportService>, DataTest {

    @Override
    Class<?>[] getDomainClassesToMock() {
        return [Report]
    }

    def setup() {
    }

    def cleanup() {
    }

    def "getClicks for empty everything test"() {
        when:
        service.getClicksForDatesAndDataSource(new Date(), new Date(), [])

        then:
        noExceptionThrown()
    }

    def "getClicks"() {
        given:
        Date dateFrom
        Date dateTo

        use(TimeCategory) {
            dateFrom = new Date() - 1.day
            dateTo = new Date() + 1.day
        }
        new Report(id: 1, daily: new Date(), clicks: 2, datasource: "testA", campaign: 12, impressions: 1).save(failOnError: true)
        new Report(id: 2, daily: new Date(), clicks: 3, datasource: "testA", campaign: 12, impressions: 1).save(failOnError: true)

        when:
        Map result = service.getClicksForDatesAndDataSource(dateFrom, dateTo, ['testA'])

        then:
        result['testA'] == 5
    }

    def "getClicks for 2 datasources"() {
        given:
        Date dateFrom
        Date dateTo

        use(TimeCategory) {
            dateFrom = new Date() - 1.day
            dateTo = new Date() + 1.day
        }
        new Report(id: 1, daily: new Date(), clicks: 2, datasource: "testA", campaign: 12, impressions: 1).save(failOnError: true)
        new Report(id: 2, daily: new Date(), clicks: 3, datasource: "testB", campaign: 12, impressions: 1).save(failOnError: true)

        when:
        Map result = service.getClicksForDatesAndDataSource(dateFrom, dateTo, ['testA', 'testB'])

        then:
        result['testA'] == 2
        result['testB'] == 3
    }

    def "impressions per day test"() {
        given:
        Date dateFrom
        Date dateTo

        use(TimeCategory) {
            dateFrom = new Date() - 1.day
            dateTo = new Date() + 1.day
        }
        new Report(id: 1, daily: dateFrom, clicks: 2, datasource: "testA", campaign: 'astoalipies', impressions: 11).save(failOnError: true)
        new Report(id: 3, daily: dateFrom, clicks: 2, datasource: "testA", campaign: 'alamakota', impressions: 32).save(failOnError: true)
        new Report(id: 2, daily: dateTo, clicks: 3, datasource: "testB", campaign: 'alamakota', impressions: 1).save(failOnError: true)

        when:
        Map result = service.getImpressionsPerDay(dateFrom, dateTo)

        then:
        result.keySet().size() == 2
        result[dateFrom] == 43
        result[dateTo] == 1
    }
}
