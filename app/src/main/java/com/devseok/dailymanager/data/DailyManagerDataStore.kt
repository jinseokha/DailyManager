package com.devseok.dailymanager.data

import com.devseok.dailymanager.data.response.HolidayResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DailyManagerDataStore @Inject constructor(
    private val dailyManagerService: DailyManagerService
) {

    suspend fun fetchGetHoliDeInfo(
        solMonth: String,
        solYear: String
    ) : Observable<HolidayResponse> {
        return dailyManagerService.getHoliDeInfo(
            solMonth = solMonth,
            solYear = solYear
        )
    }

}