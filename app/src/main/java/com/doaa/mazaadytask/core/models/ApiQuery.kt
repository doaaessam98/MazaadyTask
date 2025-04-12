package com.doaa.mazaadytask.core.models

//sealed class ApiQuery{
//    object Popular :ApiQuery()
//    class Filter(val category: Category) : ApiQuery()
//    class Search(val query: String):ApiQuery()
//
//}

sealed class ApiQuery(val query: String){
    class Popular() : ApiQuery("popular")
    class Trending(): ApiQuery("trending")
    class Upcoming(): ApiQuery("upcoming")
    data class Search(val searchQuery: String): ApiQuery(query = searchQuery)
}
