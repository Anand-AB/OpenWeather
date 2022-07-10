package net.anand.openweather.presentation.core.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import net.anand.openweather.presentation.utility.AppConstants.Companion.QUEENS_PARK_NORMAL_TTF

class QueensParkTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        val font: Typeface = Typeface.createFromAsset(context.assets, QUEENS_PARK_NORMAL_TTF)
        this.setTypeface(font, Typeface.NORMAL)
    }
}