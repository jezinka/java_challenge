package java_challenge

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

import static java_challenge.FileService.TEMP_DIRECTORY

class FileServiceSpec extends Specification implements ServiceUnitTest<FileService>, DataTest {

    def setup() {
        new File(TEMP_DIRECTORY).mkdir()
    }

    def cleanup() {
    }

    def "test getFileFromURL"() {
        when:
        service.getFileFromURL('http://google.com', 'test.csv')

        then:
        noExceptionThrown()
    }

    def "test getFileFromURL - test url"() {
        when:
        def file = service.getFileFromURL('http://google.com', 'data.csv')

        then:
        noExceptionThrown()
        file?.path?.endsWith('data.csv')
    }

    def "test getFileFromURL - empty url"() {
        when:
        def file = service.getFileFromURL(null, 'data.csv')

        then:
        noExceptionThrown()
        !file
    }
}
