package com.example.menulateral

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

//Librerias para exoplayer
import com.google.android.exoplayer2.*
import android.net.*
import android.view.View
import android.widget.ImageView
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {

    //Variables menu lateral
    lateinit var drawer: ActionBarDrawerToggle
    lateinit var disenoDrawer: DrawerLayout
    lateinit var vistaNavegacion: NavigationView

    //Variables explayer
    private lateinit var reproductor: SimpleExoPlayer
    private lateinit var infoMultimedia: com.google.android.exoplayer2.upstream.DataSource.Factory
    lateinit var vistaReproductor: PlayerView

    //Almacenar la URL del video
    var direccion=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declaracion de variables
        val titulo:TextView=findViewById(R.id.titulo)
        val contenido:TextView=findViewById(R.id.contenido)

        //Asignacion de los elementos a una variables
        vistaNavegacion=findViewById(R.id.navigationView)
        disenoDrawer=findViewById(R.id.drawerLayout)

        //Elementos de inicio
        val texto1:TextView=findViewById(R.id.textView6)
        val texto2:TextView=findViewById(R.id.textView7)
        val img:ImageView=findViewById(R.id.imageView2)

        //Configuracion exoplayer
        vistaReproductor=findViewById(R.id.pv)


        //Modo oscuro
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        //Configuracion de barra lateral
        drawer= ActionBarDrawerToggle(this,disenoDrawer,R.string.open,R.string.close)
        disenoDrawer.addDrawerListener(drawer)
        drawer.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Identificar el contenido que sera mostrado segun el item seleccionado
        vistaNavegacion.setNavigationItemSelectedListener(){

            //limpiar la pantalla principal
            texto1.text=""
            texto2.text=""
            img.setVisibility(View.INVISIBLE)

            //Mostrar sinopsis y trailer segun el item
            when(it.itemId){
                R.id.item1->{
                    titulo.text="Avengers: Endgame"
                    contenido.setText(getString(R.string.avengers))
                    direccion="https://resumeajtd181803016.000webhostapp.com/videos/Avengers_Endgame.mp4"
                    initializePlayer()
                }

                R.id.item2->{
                    titulo.text="Spiderman 3"
                    contenido.setText(getString(R.string.spiderman))
                    direccion="https://resumeajtd181803016.000webhostapp.com/videos/Spiderman3.mp4"
                    initializePlayer()
                }

                R.id.item3->{
                    titulo.text="John Wick"
                    contenido.setText(getString(R.string.johnwick))
                    direccion="https://resumeajtd181803016.000webhostapp.com/videos/JohnWick.mp4"
                    initializePlayer()
                }

                R.id.item4->{
                    titulo.text="Back To The Future"
                    contenido.setText(getString(R.string.volveralfuturo))
                    direccion="https://resumeajtd181803016.000webhostapp.com/videos/BackToTheFuture.mp4"
                    initializePlayer()
                }

                R.id.item5->{
                    titulo.text="Batman: The Dark Knight"
                    contenido.setText(getString(R.string.batman))
                    direccion="https://resumeajtd181803016.000webhostapp.com/videos/Batman.mp4"
                    initializePlayer()
                }
            }
            true

        }

    }

    //Esperar los valores que se obtendran
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Saber que item es seleccionado
        if(drawer.onOptionsItemSelected(item)){
            //Si ha seleccionadun una opcion el valor es: true
            true
        }
        return super.onOptionsItemSelected(item)
    }


    //Inicializar el reproductor
    private fun initializePlayer(){
        reproductor=SimpleExoPlayer.Builder(this).build()
        vistaReproductor.player=reproductor
        infoMultimedia=DefaultDataSourceFactory(this, Util.getUserAgent(this,"Reproductor"))
        val mediaSource=ProgressiveMediaSource.Factory(infoMultimedia).createMediaSource(Uri.parse(direccion))
        reproductor.prepare(mediaSource,false,false)
        reproductor.playWhenReady=true
        vistaReproductor.requestFocus()

    }

    fun relasePlayer(){
        reproductor.release()
    }

    //Inicializar el reproductor
    public override fun onStart(){
        super.onStart()

        if(Util.SDK_INT > 23){
            initializePlayer()
        }
    }


    public override fun onResume(){
        super.onResume()

        if(Util.SDK_INT <= 23){
            initializePlayer()
        }
    }


    public override fun onPause(){
        super.onPause()

        if(Util.SDK_INT <= 23){
            relasePlayer()
        }
    }

    public override fun onStop(){
        super.onStop()
        if(Util.SDK_INT<=23) {
            relasePlayer()
        }
    }

}