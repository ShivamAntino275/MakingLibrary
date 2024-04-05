package com.indigo.custom.customview

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.indigo.custom.R
import com.indigo.custom.views.activity.MainDecorActivity

class ReferAndEarnView:ConstraintLayout {

    private val view = LayoutInflater.from(context).inflate(R.layout.item_reward, this, true)
    private lateinit var clRefer: ConstraintLayout

    // Constructors for creating the view programmatically
    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    // Constructors for inflating the view from XML
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    // Constructors for inflating the view from XML with a default style
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        // Initialization logic here
        clRefer = view.findViewById<ConstraintLayout>(R.id.clRefer)


        clRefer.setOnClickListener {
            it.context.startActivity(Intent(it.context,MainDecorActivity::class.java).putExtra(it.context.getString(R.string.screenType),it.context.getString(R.string.Refer)))
        }




        // Access attributes if needed
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomConstraintLayout)
            // Access custom attributes using typedArray
            typedArray.recycle()
        }
    }


}