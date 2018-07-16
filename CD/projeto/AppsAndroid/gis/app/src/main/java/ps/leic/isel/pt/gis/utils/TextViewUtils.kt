package ps.leic.isel.pt.gis.utils

import android.widget.TextView

object TextViewUtils {

    fun incNumberText(textView: TextView, min: Int, max: Int): Int {
        val countText = textView.text.toString()
        var count: Int
        if (countText.isEmpty()) {
            count = min + 1
        } else {
            count = Integer.parseInt(countText)
            if (count < max)
                count += 1
        }
        textView.text = count.toString()
        return count
    }

    fun decNumberText(textView: TextView, min: Int): Int {
        val countText = textView.text.toString()
        var count: Int
        if (countText.isEmpty()) {
            count = min
        } else {
            count = Integer.parseInt(countText)
            if (count > min)
                count -= 1
        }
        textView.text = count.toString()
        return count
    }
}