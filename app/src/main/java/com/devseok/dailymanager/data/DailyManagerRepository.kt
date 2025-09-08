package com.devseok.dailymanager.data

import com.devseok.dailymanager.data.response.HolidayResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DailyManagerRepository @Inject constructor(
    private val dailyManagerDataStore: DailyManagerDataStore
): DailyManagerApiRepository {

    override suspend fun getHoliDeInfo(
        solMonth: String,
        solYear: String
    ): Observable<HolidayResponse> {
        return dailyManagerDataStore.fetchGetHoliDeInfo(
            solYear = solYear,
            solMonth = solMonth
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}