package com.devseok.dailymanager.data

import com.devseok.dailymanager.data.response.HolidayResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyManagerService {

    @GET("getHoliDeInfo")
    fun getHoliDeInfo(
        @Query("serviceKey") serviceKey: String = "7479274658b08bc2460549a3ddf372e49526805bd24216116f9a074c170620b8",
        @Query("solYear") solYear: String,
        @Query("solMonth") solMonth: String
    ) : Observable<HolidayResponse>

}