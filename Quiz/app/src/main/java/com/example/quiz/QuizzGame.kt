package com.example.quiz



import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_quizz_game.*
import kotlinx.android.synthetic.main.activity_secore_.*
import kotlinx.android.synthetic.main.activity_time_out.*

class QuizzGame : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var BonneReponse: String
    private lateinit var countDownTimer: CountDownTimer
    private var countDownProgress: Int = 100
    private var length_level: Int = 14
    private var level: Int = 0
    private var Correct: Int = 0
    private var Incorrect: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizz_game)
        downTimer()
        readDatabase()
        B1onclick()
        B2onclick()
        B3onclick()
        B4onclick()
    }

    fun downTimer() {
        countDownTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownProgress -= 5
                activeProgress.progress = countDownProgress
            }

            override fun onFinish() {
                setContentView(R.layout.activity_time_out)
                bt_repaly.setOnClickListener {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    setContentView(R.layout.activity_quizz_game)
                }
            }
        }.start()
    }

    fun readDatabase() {
        database = FirebaseDatabase.getInstance().getReference("Questions")
        database.child(level.toString()).get().addOnSuccessListener {
            if (it.exists()) {
                question.text = it.child("question").value.toString()
                choix1.text = it.child("choix1").value.toString()
                choix2.text = it.child("choix2").value.toString()
                choix3.text = it.child("choix3").value.toString()
                choix4.text = it.child("choix4").value.toString()
                BonneReponse = it.child("bonneReponse").value.toString()
            }
        }
    }

    fun B1onclick() {
        choix1.setOnClickListener {
            if (choix1.text.equals(BonneReponse)) {
                Correct++
            } else {
                Incorrect++
            }
            Check_level()
        }
    }

    fun B2onclick() {
        choix2.setOnClickListener {
            if (choix2.text.equals(BonneReponse)) {
                Correct++
            } else {
                Incorrect++
            }
            Check_level()
        }
    }

    fun B3onclick() {
        choix3.setOnClickListener {
            if (choix3.text.equals(BonneReponse)) {
                Correct++
            } else {
                Incorrect++
            }
            Check_level()
        }
    }

    fun B4onclick() {
        choix4.setOnClickListener {
            if (choix4.text.equals(BonneReponse)) {
                Correct++
            } else {
                Incorrect++;
            }
            Check_level()
        }
    }

    fun secore() {
        textView.text = Correct.toString()
        repC.text = Correct.toString()
        textView3.text = Incorrect.toString()
        var progress = 100 / length_level
        var progressTotal = Correct * progress
        textPourcent.text = progressTotal.toString()
        circularProgressBar.apply {
            setProgressWithAnimation(progressTotal.toFloat(), 1000) // =1s
            progressBarColor = Color.GREEN
        }

        bt_rep.setOnClickListener {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.activity_quizz_game)
        }
    }

    fun Check_level() {
        if (level >= length_level - 1) {
            countDownTimer.cancel()
            setContentView(R.layout.activity_secore_)
            secore()
        } else {
            level++
            readDatabase()
        }
    }
}



