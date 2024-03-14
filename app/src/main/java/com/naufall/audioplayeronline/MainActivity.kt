package com.naufall.audioplayeronline

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import com.naufall.audioplayeronline.databinding.ActivityMainBinding
import com.naufall.audioplayeronline.model.Song

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mp: MediaPlayer? = null
    private var currentSeekBar: SeekBar? = null
    private var currentButton: ImageButton? = null
    private var currentSongUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val songList = ArrayList<Song>()
        songList.add(Song("Al Fatihah", "https://server8.mp3quran.net/ayyub/001.mp3"))
        songList.add(Song("An Naba", "https://server8.mp3quran.net/ayyub/078.mp3"))
        songList.add(Song("An Naziat", "https://server8.mp3quran.net/ayyub/079.mp3"))
        songList.add(Song("Abasa", "https://server8.mp3quran.net/ayyub/080.mp3"))
        songList.add(Song("At Takwir", "https://server8.mp3quran.net/ayyub/081.mp3"))
        songList.add(Song("Al Infitar", "https://server8.mp3quran.net/ayyub/082.mp3"))
        songList.add(Song("Al Mutaffifin", "https://server8.mp3quran.net/ayyub/083.mp3"))
        songList.add(Song("Al Insyiqaq", "https://server8.mp3quran.net/ayyub/084.mp3"))
        songList.add(Song("Al Buruj", "https://server8.mp3quran.net/ayyub/085.mp3"))
        songList.add(Song("At Tariq", "https://server8.mp3quran.net/ayyub/086.mp3"))
        songList.add(Song("Al A'la", "https://server8.mp3quran.net/ayyub/087.mp3"))
        songList.add(Song("Al Gasyiyah", "https://server8.mp3quran.net/ayyub/088.mp3"))
        songList.add(Song("Al Fajr", "https://server8.mp3quran.net/ayyub/089.mp3"))
        songList.add(Song("Al Balad", "https://server8.mp3quran.net/ayyub/090.mp3"))
        songList.add(Song("Asy Syams", "https://server8.mp3quran.net/ayyub/091.mp3"))
        songList.add(Song("Al Lail", "https://server8.mp3quran.net/ayyub/092.mp3"))
        songList.add(Song("Ad Duha", "https://server8.mp3quran.net/ayyub/093.mp3"))
        songList.add(Song("Asy Syarh", "https://server8.mp3quran.net/ayyub/094.mp3"))
        songList.add(Song("At Tin", "https://server8.mp3quran.net/ayyub/095.mp3"))
        songList.add(Song("Al Alaq", "https://server8.mp3quran.net/ayyub/096.mp3"))
        songList.add(Song("Al Qadr", "https://server8.mp3quran.net/ayyub/097.mp3"))
        songList.add(Song("Al Bayyinah", "https://server8.mp3quran.net/ayyub/098.mp3"))
        songList.add(Song("Az Zalzalah", "https://server8.mp3quran.net/ayyub/099.mp3"))
        songList.add(Song("Al Adiyat", "https://server8.mp3quran.net/ayyub/100.mp3"))
        songList.add(Song("Al Qari'ah", "https://server8.mp3quran.net/ayyub/101.mp3"))
        songList.add(Song("At Takasur", "https://server8.mp3quran.net/ayyub/102.mp3"))
        songList.add(Song("Al Asr", "https://server8.mp3quran.net/ayyub/103.mp3"))
        songList.add(Song("Al Humazah", "https://server8.mp3quran.net/ayyub/104.mp3"))
        songList.add(Song("Al Fil", "https://server8.mp3quran.net/ayyub/105.mp3"))
        songList.add(Song("Quraisy", "https://server8.mp3quran.net/ayyub/106.mp3"))
        songList.add(Song("Al Ma'un", "https://server8.mp3quran.net/ayyub/107.mp3"))
        songList.add(Song("Al Kautsar", "https://server8.mp3quran.net/ayyub/108.mp3"))
        songList.add(Song("Al Kafirun", "https://server8.mp3quran.net/ayyub/109.mp3"))
        songList.add(Song("An Nasr", "https://server8.mp3quran.net/ayyub/110.mp3"))
        songList.add(Song("Al Lahab", "https://server8.mp3quran.net/ayyub/111.mp3"))
        songList.add(Song("Al Ikhlas", "https://server8.mp3quran.net/ayyub/112.mp3"))
        songList.add(Song("An Nas", "https://server8.mp3quran.net/ayyub/114.mp3"))

        val adapter = SongAdapter()
        adapter.setList(songList)
        binding.rvSongs.adapter = adapter

    }

    fun playSong(url: String, seekBar: SeekBar, button: ImageButton) {
        /*
        di awal currentSongUrl kosong, isi currentSongUrl dengan url, play
        jika currentSongUrl sudah ada isinya, cek apakah sama dengan url
        jika sama, pause jika sedang play, resume jika sedang pause
        jika tidak sama, stop currentSongUrl, isi currentSongUrl dengan url, play
        * */
        if(currentSongUrl.isNullOrEmpty()) {
            currentSongUrl = url
            currentSeekBar = seekBar
            currentButton = button
            startPlaying(url)
            initializeSeekBar()
            Toast.makeText(this, "Playing song\n$url", Toast.LENGTH_SHORT).show()
            return
        }else{
            if(currentSongUrl == url) {
                if(mp?.isPlaying == true) {
                    pauseSong()
                }else{
                    resumeSong()
                }
                return
            }else{
                stopPlaying()
                playSong(url, seekBar, button)
            }
        }
    }

    private fun pauseSong() {
        mp?.pause()
        currentButton?.setImageDrawable(getDrawable(R.drawable.ic_play))
    }

    private fun resumeSong() {
        mp?.start()
        currentButton?.setImageDrawable(getDrawable(R.drawable.ic_pause))
    }

    private fun startPlaying(url: String) {
        mp = MediaPlayer().apply {
            setDataSource(url)
            prepare()
            start()
        }
        currentSeekBar?.isEnabled = true
        currentButton?.setImageDrawable(getDrawable(R.drawable.ic_pause))
        mp?.setOnCompletionListener {
            stopPlaying()
        }
    }

    private fun stopPlaying() {
        mp?.release()
        mp = null
        currentSeekBar?.progress = 0
        currentSeekBar?.isEnabled = false
        currentSeekBar = null
        currentButton?.setImageDrawable(getDrawable(R.drawable.ic_play))
        currentButton = null
        currentSongUrl = null
    }

    private fun initializeSeekBar() {
        currentSeekBar?.max = mp?.duration ?: 0

        // listener buat seekbar kalo digeser
        currentSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mp?.seekTo(progress)
                    currentButton?.setImageDrawable(getDrawable(R.drawable.ic_pause))
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mp?.pause()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mp?.start()
            }
        })

        // handler buat update seekbar tiap 1 detik, sesuai dengan durasi lagu
        val handler = android.os.Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentSeekBar?.progress = mp?.currentPosition ?: 0
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    // stop media player kalo activity di pause
    override fun onPause() {
        super.onPause()
        stopPlaying()
    }
}