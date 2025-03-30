package com.devseok.dailymanager.data

import javax.inject.Inject

class DailyManagerRepository @Inject constructor(
    private val dailyManagerDataStore: DailyManagerDataStore
): DailyManagerApiRepository {

}