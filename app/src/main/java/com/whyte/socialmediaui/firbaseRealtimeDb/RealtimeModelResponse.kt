package com.whyte.socialmediaui.firbaseRealtimeDb

data class RealtimeModelResponse(val item:RealtimeItems?, val key:String? = "") {

    data class RealtimeItems(val title:String? = "", val description:String? = "", var imageUri:String? = "", var videoUri:String? = "")

}