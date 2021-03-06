package com.kelsos.mbrc.ui.activities

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.kelsos.mbrc.R
import kotterknife.bindView

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

  private val toolbar: Toolbar by bindView(R.id.toolbar)

  protected fun setupToolbar(title: String = "") {
    setSupportActionBar(toolbar)

    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setHomeButtonEnabled(true)

      if (title.isNotBlank()) {
        this.title = title
      }
    }
  }
}