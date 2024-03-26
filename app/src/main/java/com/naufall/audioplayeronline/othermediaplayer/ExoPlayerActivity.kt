package com.naufall.audioplayeronline.othermediaplayer

import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.naufall.audioplayeronline.R
import com.naufall.audioplayeronline.databinding.ActivityExoPlayerBinding
import com.naufall.audioplayeronline.model.Song


class ExoPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExoPlayerBinding
    private var player: ExoPlayer? = null

    private var currentSeekBar: SeekBar? = null
    private var currentButton: ImageButton? = null
    private var currentSongUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val songList = ArrayList<Song>()
        songList.add(Song("download url dev panjang", "https://apihafalan.tkilocal.biz.id/hafalan/v1/audio/get/21-03-2024-kzAFyxvQ.m4a0"))
        songList.add(Song("stream url dev panjang", "https://apihafalan.tkilocal.biz.id/hafalan/v1/audio/get/stream/21-03-2024-kzAFyxvQ.m4a"))
        songList.add(Song("stream prod panjang", "https://api.teknologikartu.com/hafalan/v1/audio/get/21-03-2024-ctJqBlyT.mp3"))

        val adapter = ExoSongAdapter()
        adapter.setList(songList)
        binding.rvSong.adapter = adapter

//        player = ExoPlayer.Builder(this).build()
//        val mediaItem = MediaItem.fromUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
//        player.setMediaItem(mediaItem)
//        player.prepare()


    }
    fun playSong(url: String, seekBar: SeekBar, button: ImageButton) {
        if(currentSongUrl.isNullOrEmpty()) {
            currentSongUrl = url
            currentSeekBar = seekBar
            currentButton = button
            startPlaying(url)
            initializeSeekBar()
//            Toast.makeText(this, "Playing song\n$url", Toast.LENGTH_SHORT).show()
            return
        }else{
            if(currentSongUrl == url) {
                if(player?.isPlaying == true) {
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
        player?.pause()
        currentButton?.setImageDrawable(getDrawable(R.drawable.ic_play))
    }

    private fun resumeSong(){
        player?.play()
        currentButton?.setImageDrawable(getDrawable(R.drawable.ic_pause))
    }

    private fun startPlaying(url: String){
        player = ExoPlayer.Builder(this).build()
        val mediaItem = MediaItem.fromUri(url)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
        currentSeekBar?.isEnabled = true
        currentButton?.setImageDrawable(getDrawable(R.drawable.ic_pause))
        player?.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Toast.makeText(this@ExoPlayerActivity, "Error: ${error.message}\n ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                stopPlaying()
            }
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_READY) {
                    currentSeekBar?.max = player?.duration?.toInt() ?: 0
                }
                if(playbackState == ExoPlayer.STATE_ENDED) {
                    stopPlaying()
                }
                if (playbackState == ExoPlayer.EVENT_PLAYER_ERROR){
                    Toast.makeText(this@ExoPlayerActivity, "Error2: ", Toast.LENGTH_SHORT).show()
                    stopPlaying()
                }
            }
        })

    }
    private fun stopPlaying() {
        player?.release()
        player = null
        currentSeekBar?.progress = 0
        currentSeekBar?.isEnabled = false
        currentSeekBar = null
        currentButton?.setImageDrawable(getDrawable(R.drawable.ic_play))
        currentButton = null
        currentSongUrl = null
    }

    private fun initializeSeekBar() {

        // listener buat seekbar kalo digeser
        currentSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player?.seekTo(progress.toLong())
                    currentButton?.setImageDrawable(getDrawable(R.drawable.ic_pause))
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                player?.pause()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                player?.play()
            }
        })

        // handler buat update seekbar tiap 1 detik, sesuai dengan durasi lagu
        val handler = android.os.Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                currentSeekBar?.progress = (player?.currentPosition ?: 0).toInt()
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }
    override fun onPause() {
        super.onPause()
        stopPlaying()
    }


}