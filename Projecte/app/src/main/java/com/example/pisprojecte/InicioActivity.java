package com.example.pisprojecte;

import android.content.Intent;
//import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
//import android.view.View;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.HashMap;

public class InicioActivity extends AppCompatActivity {

    Button estudio, bases, creacion, letras, ajustes;
    TextView email, apodo;
    ImageView perfil;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseUser user;
    StorageReference imgRef;
    final long TAMANO_IMG = 3072 * 3072;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainicio);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(user.getUid());

        estudio = findViewById(R.id.Estudio_icon);
        bases = findViewById(R.id.Bases_icon);
        creacion = findViewById(R.id.Creación_icon);
        letras = findViewById(R.id.Letras_icon);
        email = findViewById(R.id.textEmail);
        apodo = findViewById(R.id.textApodo);
        perfil = findViewById(R.id.imagePerfilImg);
        ajustes = findViewById(R.id.buttonAjustesInicio);

        email.setText(user.getEmail());
        apodo.setText(user.getDisplayName());


        estudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, EstudioActivity.class);
                startActivity(intent);
            }
        });


        bases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, ListadoBasesActivity.class);
                startActivity(intent);
            }
        });


        creacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, CreacioBasesActivity.class);
                startActivity(intent);
            }
        });


        letras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, LyricsActivity.class);
                startActivity(intent);
            }
        });

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioActivity.this, AjustesActivity.class);
                startActivity(intent);
            }
        });



        imgRef = FirebaseStorage.getInstance().getReference("imagenesPerfil/" + user.getUid());

        imgRef.getBytes(TAMANO_IMG).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmapIMG = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                perfil.setImageBitmap(bitmapIMG);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error visualización imagen\n",Toast.LENGTH_LONG);
            }
        });


    }
}