package ps.leic.isel.pt.gis.utils

import android.widget.EditText

object EditTextUtils {

    fun incNumberText(editText: EditText, min: Int, max: Int) {
        val countText = editText.text.toString()
        var count: Int
        if (countText.isEmpty()) {
            count = min + 1
        } else {
            count = Integer.parseInt(countText)
            if (count < max)
                count += 1
        }
        editText.setText(count.toString())
    }

    fun decNumberText(editText: EditText, min: Int) {
        val countText = editText.text.toString()
        var count: Int
        if (countText.isEmpty()) {
            count = min
        } else {
            count = Integer.parseInt(countText)
            if (count > min)
                count -= 1
        }
        editText.setText(count.toString())
    }
}