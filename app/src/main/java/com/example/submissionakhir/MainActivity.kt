package com.example.submissionakhir

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvStrawhat: RecyclerView
    private val list = ArrayList<Strawhat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Add a callback that's called when the splash screen is animating to the
        // app content.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // Create your custom animation.
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 200L

                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        }


        rvStrawhat = findViewById(R.id.rv_strawhat)
        rvStrawhat.setHasFixedSize(true)

        list.addAll(getListStrawhat())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about_author -> {
                val intent = Intent(this@MainActivity, About::class.java)
                startActivity(intent)            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSelectedStrawhatDetail(strawhat: Strawhat) {
        val intent = Intent(this@MainActivity, Detail::class.java)
        intent.putExtra(Detail.EXTRA_STRAWHAT, strawhat)
        startActivity(intent)
    }

    private fun getListStrawhat(): Collection<Strawhat> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataPosition = resources.getStringArray(R.array.data_position)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataMoreDescription = resources.getStringArray(R.array.data_more_description)
        val dataBounty = resources.getStringArray(R.array.data_bounty)
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val listStrawhat = ArrayList<Strawhat>()
        for (i in dataName.indices) {
            val strawhat = Strawhat(dataName[i], dataPosition[i], dataDescription[i], dataMoreDescription[i], dataBounty[i], dataPhoto[i])
            listStrawhat.add(strawhat)
        }
        return listStrawhat
    }

    private fun showRecyclerList() {
        rvStrawhat.layoutManager = LinearLayoutManager(this)
        val listStrawhatAdapter = ListStrawhatAdapter(list)
        rvStrawhat.adapter = listStrawhatAdapter

        listStrawhatAdapter.setOnItemClickCallback(object : ListStrawhatAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Strawhat) {
                showSelectedStrawhatDetail(data)
            }
        })
    }
}
