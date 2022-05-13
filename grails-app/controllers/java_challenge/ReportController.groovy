package java_challenge

import grails.util.Holders

class ReportController {

    FileService fileService
    DbService dbService

    def index() {
        respond 'hello world'
    }

    def initData() {
        String url = Holders.config.file.url
        File file = fileService.getFileFromTemp()
        dbService.storeData(file)
        render "${Report.count()} rekordów załadowano"

    }
}
