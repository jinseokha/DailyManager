package com.devseok.dailymanager.data

import com.devseok.dailymanager.data.response.HolidayResponse
import io.reactivex.rxjava3.core.Observable

interface DailyManagerApiRepository {

    suspend fun getHoliDeInfo(solMonth: String, solYear: String): Observable<HolidayResponse>

}