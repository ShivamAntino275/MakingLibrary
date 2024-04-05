package com.indigo.custom.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.indigo.custom.databinding.ProgressLayoutBinding
import com.indigo.custom.views.activity.CustomScreenActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private var customDialog: AlertDialog? = null

fun Context.showToast(msg:String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}


fun <T> List<T>.toArrayList():ArrayList<T>{
    val arrayList = ArrayList<T>()
    this.forEach{
        arrayList.add(it)
    }
    return arrayList
}
fun View.hideSoftKeyboard() {

    try {
        val inputMethodManager = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }catch (ex:Exception){
        ex.printStackTrace()
    }
}
fun Context.alertBox(title:String="Alert",message:String,onClick:(Boolean)->Unit){
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(true)

    builder.setPositiveButton("Yes",
        DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
            onClick(true)
            dialog?.dismiss()
        } as DialogInterface.OnClickListener)

    builder.setNegativeButton("No",
        DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
            onClick(false)
            dialog.dismiss()
        } as DialogInterface.OnClickListener)


    val alertDialog = builder.create()
    alertDialog.show()

}
fun hideProgress() {
    if (customDialog != null && customDialog?.isShowing!!) {
        customDialog?.dismiss()
    }
}
fun Context.showProgress() {
    hideProgress()
    val customAlertBuilder = AlertDialog.Builder(this)
    val customAlertView =ProgressLayoutBinding.inflate(LayoutInflater.from(this), null, false)
    customAlertBuilder.setView(customAlertView.root)
    customAlertBuilder.setCancelable(true)
    customDialog = customAlertBuilder.create()
    customDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    customDialog!!.show()
}

fun convertTimestampToReadableDate(timestamp: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC");
    val outputFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
    val date = inputFormat.parse(timestamp)
    return outputFormat.format(date)
}
fun showAlartMessage(message: String, context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Decor Plus")
        .setMessage(message)
        .setPositiveButton(
            "ok"
        ) { dialog, whichButton -> dialog.dismiss() }.show()
}

fun String?.toCamelCase(): String {
    val words = this?.split(" ", " ")?.map { it.lowercase().replaceFirstChar { it.uppercase() } }
    return words?.joinToString(" ")?:""
}

fun formatDate(date1: String, date2: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormat1 = SimpleDateFormat("MMM", Locale.ENGLISH)
    val outputFormat2 = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)

    val calendar1 = Calendar.getInstance()
    val calendar2 = Calendar.getInstance()

    calendar1.time = inputFormat.parse(date1) ?: Date()
    calendar2.time = inputFormat.parse(date2) ?: Date()

    return if (calendar1[Calendar.MONTH] == calendar2[Calendar.MONTH]) {
        // If both dates have the same month, display only the month and year
        outputFormat2.format(calendar2.time)
    } else {
        // If the months are different, display both the month and the year for the second date,
        // and only the month for the first date
        "${outputFormat1.format(calendar1.time)} - ${outputFormat2.format(calendar2.time)}"
    }
}


