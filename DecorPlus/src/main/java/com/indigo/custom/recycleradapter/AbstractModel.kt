package com.indigo.custom.recycleradapter


abstract class AbstractModel{
    var adapterPosition: Int = -1
    var onItemClick: RecyclerAdapter.OnItemClick? = null
}