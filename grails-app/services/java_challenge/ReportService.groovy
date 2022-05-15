package java_challenge

import grails.gorm.transactions.Transactional

@Transactional
class ReportService {

    Map getClicksForDatesAndDataSource(Date dateFrom, Date dateTo, List dataSources) {
        return Report.where {
            between("daily", dateFrom, dateTo)
            if (dataSources) {
                'in' 'datasource', dataSources
            }
        }.groupBy { it.datasource }.collectEntries { [(it.key): it.value.clicks.sum()] }
    }

    Map getImpressionsPerDay(Date dateFrom, Date dateTo) {
        return Report.where {
            between("daily", dateFrom, dateTo)
        }.groupBy { it.daily }.collectEntries { [(it.key): it.value.impressions.sum()] }
    }

    Map getCTR(List<String> datasource, List<String> campaign) {
        return Report
                .where {
                    'in' 'datasource', datasource
                    'in' 'campaign', campaign
                }
                .groupBy { [it.datasource, it.campaign] }
                .collectEntries { [(it.key.join("-")): (it.value.clicks.sum() / it.value.impressions.sum())] }
    }
}
