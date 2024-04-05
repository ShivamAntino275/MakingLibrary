package com.indigo.custom.recycleradapter


data class DummyModel(
    var text : String = "",
    var costText : String = "",
    var ids:Int=0,
    var isPast: Boolean = false,
    var isSelected : Boolean = false,
    var quantity:Int =1,
    var parentPosition:Int=-1,
    var innerAdapter: RecyclerAdapter<DummyModel>?=null
) : AbstractModel()

