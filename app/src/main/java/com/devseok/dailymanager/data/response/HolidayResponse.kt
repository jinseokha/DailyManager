package com.devseok.dailymanager.data.response

import com.google.gson.annotations.SerializedName
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class HolidayResponse(
    @field:Element(name = "header")
    var header: Header? = null,

    @field:Element(name = "body")
    var body: Body? = null
)

@Root(name = "header", strict = false)
data class Header(
    @field:Element(name = "resultCode")
    var resultCode: String = "",

    @field:Element(name = "resultMsg")
    var resultMsg: String = ""
)

@Root(name = "body", strict = false)
data class Body(
    @field:Element(name = "items")
    var items: Items? = null,

    @field:Element(name = "numOfRows")
    var numOfRows: Int = 0,

    @field:Element(name = "pageNo")
    var pageNo: Int = 0,

    @field:Element(name = "totalCount")
    var totalCount: Int = 0
)

@Root(name = "items", strict = false)
data class Items(
    @field:ElementList(inline = true, entry = "item", required = false)
    var item: MutableList<Item> = mutableListOf()
)

@Root(name = "item", strict = false)
data class Item(
    @field:Element(name = "dateKind", required = false)
    var dateKind: String? = null,

    @field:Element(name = "dateName", required = false)
    var dateName: String? = null,

    @field:Element(name = "isHoliday", required = false)
    var isHoliday: String? = null,

    @field:Element(name = "locdate", required = false)
    var locdate: String? = null,

    @field:Element(name = "seq", required = false)
    var seq: Int? = null
)