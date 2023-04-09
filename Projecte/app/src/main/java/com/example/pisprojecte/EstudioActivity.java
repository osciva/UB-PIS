package com.example.pisprojecte;


import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Environment;


import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pisprojecte.Entidad.AdaptadorBase;
import com.example.pisprojecte.Entidad.Base;


public class EstudioActivity extends AppCompatActivity implements DialogElegirLyric.DialogElegirListener {

    private Button buttonStart;
    TextView songText;
    private Button buttonStop;
    private Button buttonStopPlayingRecording;
    private String AudioSavePathInDevice = null;
    private MediaRecorder mediaRecorder ;
    private Random random ;
    RecyclerView bases;
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    ArrayList<Base> arrayBases;

    public void goToInicio(View view){
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudio);

        buttonStart = findViewById(R.id.start);
        buttonStop = findViewById(R.id.restart);
        bases = findViewById(R.id.listado_grabaciones_recyclerView);
        songText = findViewById(R.id.textView_Lyrics);
        songText.setMovementMethod(new ScrollingMovementMethod());

        buttonStopPlayingRecording = findViewById(R.id.PauseOrResume);
        Button lyricButton = findViewById(R.id.chooseSong);
        TextView textViewLyrics = findViewById(R.id.textView_Lyrics);

        buttonStop.setEnabled(false);

        buttonStopPlayingRecording.setEnabled(false);
        bases.setLayoutManager(new LinearLayoutManager(this));
        arrayBases = new ArrayList<>();
        arrayBases.add(new Base("Canción 1", "sonidoUno"));
        arrayBases.add(new Base("Canción 2", "sonidoDos"));
        arrayBases.add(new Base("Canción 3", "sonidoTres"));
        arrayBases.add(new Base("Canción 4", "sonidoCuatro"));
        RecyclerView.Adapter adapter = new AdaptadorBase(EstudioActivity.this, arrayBases);
        bases.setAdapter(adapter);

        lyricButton.setOnClickListener(view -> openDialog());

        random = new Random();

        buttonStart.setOnClickListener(view -> {

            if(checkPermission()) {

                AudioSavePathInDevice =
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                MediaRecorderReady();

                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (IllegalStateException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);

                Toast.makeText(EstudioActivity.this, "Recording started",
                        Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }

        });

        buttonStop.setOnClickListener(view -> {
            mediaRecorder.stop();
            buttonStop.setEnabled(false);
            //buttonPlayLastRecordAudio.setEnabled(true);
            buttonStart.setEnabled(true);
            buttonStopPlayingRecording.setEnabled(false);

            Toast.makeText(EstudioActivity.this, "Recording Completed",
                    Toast.LENGTH_LONG).show();
        });



        buttonStopPlayingRecording.setOnClickListener(view -> {
            buttonStop.setEnabled(false);
            buttonStart.setEnabled(true);
            buttonStopPlayingRecording.setEnabled(false);
            //buttonPlayLastRecordAudio.setEnabled(true);

            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.release();
                MediaRecorderReady();
            }
        });

    }

    public void openDialog(){
        DialogElegirLyric elegirLyric = new DialogElegirLyric();
        elegirLyric.show(getSupportFragmentManager(), "Elegir Lyrics dialog");
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            String randomAudioFileName = "ABCDEFGHIJKLMNOP";
            stringBuilder.append(randomAudioFileName.
                    charAt(random.nextInt(randomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(EstudioActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RequestPermissionCode) {
            if (grantResults.length > 0) {
                boolean StoragePermission = grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED;
                boolean RecordPermission = grantResults[1] ==
                        PackageManager.PERMISSION_GRANTED;

                if (StoragePermission && RecordPermission) {
                    Toast.makeText(EstudioActivity.this, "Permission Granted",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EstudioActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
    public void setLyricsEstudio(String text){
        songText.setText(text);


    }



}