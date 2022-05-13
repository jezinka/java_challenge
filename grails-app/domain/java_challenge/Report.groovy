package java_challenge

import java.text.SimpleDateFormat

class Report {

    Long id

    String datasource
    String campaign
    Date daily
    Long clicks
    Long impressions

    static constraints = {
    }

    Report(String[] line) {
        this.datasource = line[0]
        this.campaign = line[1]
        this.daily = new SimpleDateFormat('MM/dd/yy').parse(line[2])
        this.clicks = Long.valueOf(line[3])
        this.impressions = Long.valueOf(line[4])
    }

    static mapping = {
        table 'REPORT'
        version false

        id column: 'ID', type: "long", sqlType: "int", generator: 'identity'
        datasource column: 'DATASOURCE'
        campaign column: 'CAMPAIGN'
        daily column: 'DAILY'
        clicks column: 'CLICKS'
        impressions column: 'IMPRESSIONS'
    }

}
