package dev.gumil.games.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

fun Context.openPage(url: String) {
    try {
        val webpage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Log.e("GamesApp", e.message, e)
        Toast.makeText(
            this,
            "No application can handle this request. Please install a web browser or check your URL.",
            Toast.LENGTH_LONG
        ).show()
    }
}
